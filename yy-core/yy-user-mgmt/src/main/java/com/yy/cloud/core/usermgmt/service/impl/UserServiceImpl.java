package com.yy.cloud.core.usermgmt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.dto.sysbase.PasswordProfile;
import com.yy.cloud.common.data.dto.sysbase.UserProfile;
import com.yy.cloud.common.data.otd.usermgmt.FoxUserItem;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleItem;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.UserItem;
import com.yy.cloud.common.exception.NoRecordFoundException;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.common.utils.AssertHelper;
import com.yy.cloud.common.utils.DateUtils;
import com.yy.cloud.core.usermgmt.constant.AdUserMgmtConstants;
import com.yy.cloud.core.usermgmt.constant.UserMgmtConstants;
import com.yy.cloud.core.usermgmt.data.domain.YYOrganization;
import com.yy.cloud.core.usermgmt.data.domain.YYRole;
import com.yy.cloud.core.usermgmt.data.domain.YYUser;
import com.yy.cloud.core.usermgmt.data.domain.YYUserInfo;
import com.yy.cloud.core.usermgmt.data.domain.YYUserRole;
import com.yy.cloud.core.usermgmt.data.repositories.YYOrganizationRepository;
import com.yy.cloud.core.usermgmt.data.repositories.YYRoleRepository;
import com.yy.cloud.core.usermgmt.data.repositories.YYUserInfoRepository;
import com.yy.cloud.core.usermgmt.data.repositories.YYUserOrganizationRepository;
import com.yy.cloud.core.usermgmt.data.repositories.YYUserRepository;
import com.yy.cloud.core.usermgmt.data.repositories.YYUserRoleRepository;
import com.yy.cloud.core.usermgmt.exception.PasswordNotMatchException;
import com.yy.cloud.core.usermgmt.exception.UserExistException;
import com.yy.cloud.core.usermgmt.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private YYUserRepository foxUserRepository;
    
    
    @Autowired
    private YYUserInfoRepository yyUserInfoRepository;

    @Autowired
    private YYUserOrganizationRepository foxUserOrganizationRepository;

    @Autowired
    private YYUserRoleRepository foxUserRoleRepository;


    @Autowired
    private YYOrganizationRepository yyOrganzationRepository;

    
    @Autowired
    private YYRoleRepository foxRoleRepository;


    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private SecurityService securityService;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 创建用户
     * @param _userProfile
     * @return
     */
    @Override
    @Transactional
    public String createUser(UserProfile _userProfile) {
    	
        YYUser foxUserExist = foxUserRepository.findByLoginNameAndStatusLessThan(_userProfile.getLoginName() , CommonConstant.DIC_GLOBAL_STATUS_DELETED);
        if(null != foxUserExist){
            log.info(CommonConstant.LOG_DEBUG_TAG + "该用户名已存在：" + _userProfile.getLoginName());
            throw new UserExistException();
        }
        YYUser foxUser = modelMapper.map(_userProfile, YYUser.class);
        foxUser.setStatus(CommonConstant.DIC_GLOBAL_STATUS_INITIAL);
        foxUser.setType(UserMgmtConstants.ACCOUNT_TYPE_PERSONAL);
        String encodedPassword = encoder.encode(_userProfile.getPassword());
        foxUser.setPassword(encodedPassword);
        
        /**
         * 基本信息
         */
        YYUserInfo userInfo = new YYUserInfo();
        userInfo.setBirthiday(DateUtils.formatDate(_userProfile.getBirthday(), "yyyy-MM-dd"));
        userInfo.setUserName(_userProfile.getUserName());
        userInfo.setAdministrativePost(_userProfile.getAdministrativePost());
        userInfo.setAdministrativeRank(_userProfile.getAdministrativeRank());
        userInfo.setOccupationType(_userProfile.getOccupationType());
        userInfo.setProfessionalTitle(_userProfile.getProfessionalTitle());
        userInfo.setPhone(_userProfile.getPhone());
        userInfo.setEmail(_userProfile.getEmail());
        userInfo.setGender(_userProfile.getGender());
        userInfo.setAddress(_userProfile.getAddress());
        
        /**
         * 部门信息
         */
        userInfo.setDeptId(_userProfile.getOrgId());
        
        userInfo.setUser(foxUser);
        foxUser.setUserInfo(userInfo);
        foxUser=   foxUserRepository.save(foxUser);
        // 绑定角色
      /*  if (_userProfile.getRoles() != null && !_userProfile.getRoles().isEmpty()) {
            _userProfile.getRoles().forEach(roleProfile -> {
                YYUserRole foxUserRole = new YYUserRole();
                foxUserRole.setRoleId(roleProfile.getId());
                foxUserRole.setUserId(foxUser.getId());
                foxUserRoleRepository.save(foxUserRole);
            });
        }*/
        

        
        /**
         * 设置默认角色
         */
        YYRole yyrole=foxRoleRepository.findOneByRoleName(UserMgmtConstants.ACCOUNT_DEFAULTROLE);
        YYUserRole foxUserRole = new YYUserRole();
        foxUserRole.setRoleId(yyrole.getId());
        foxUserRole.setUserId(foxUser.getId());
        foxUserRoleRepository.save(foxUserRole);
        return foxUser.getId();
    }
    
    @Override
    public GeneralContentResult<List<com.yy.cloud.common.data.otd.usermgmt.OrganizationItem>> findAllorgnazation(){
    	GeneralContentResult<List<OrganizationItem>> reslut = new GeneralContentResult<List<OrganizationItem>>();
    	
    	List<OrganizationItem> orglis= new ArrayList<OrganizationItem>();
    	
    	List<YYOrganization> items=yyOrganzationRepository.findAll();
    	
    	log.debug("The value of items is \'"+items+"\'");
    	
    	if(items!=null && items.size()>0){
    		for(YYOrganization yyOrganization :items){
    			OrganizationItem org= new OrganizationItem();
    			org.setId(yyOrganization.getId());
    			org.setOrganizitionName(yyOrganization.getName());
    			org.setDesc(yyOrganization.getDescription());
    			orglis.add(org);
    		}
    	}
    	
    	reslut.setResultContent(orglis);
    	reslut.setDetailDescription(ResultCode.OPERATION_SUCCESS);
    	reslut.setResultCode(ResultCode.OPERATION_SUCCESS);
    	
    	return reslut;
    }

    @Override
    @Transactional
	public void modifyUser(UserProfile _userProfile) {
    	
			log.debug("The method modifyUser is begin");
			YYUser foxUser = Optional.ofNullable(foxUserRepository.findOne(_userProfile.getId())).orElseThrow(
					() -> new NoRecordFoundException(String.format("user %s not found.", _userProfile.getId())));

			String userId = _userProfile.getId();
			String passwordTem=_userProfile.getPassword();
			String password = null;
			if(AssertHelper.notEmpty(passwordTem)){
				password=  encoder.encode(_userProfile.getPassword());;
			}else{
				password = foxUser.getPassword();
			}
			
			Byte status = foxUser.getStatus();
			Byte type = foxUser.getType();
			String loginName = foxUser.getLoginName();

			foxUser = modelMapper.map(_userProfile, YYUser.class);
			
			log.debug("The value foxUser.loginName is \'"+foxUser.getLoginName()+"\'");
			foxUser.setStatus(status);
			foxUser.setPassword(password);
			foxUser.setType(type);
			foxUser.setLoginName(loginName);
			UserDetailsItem userDetailItem = securityService.getCurrentUser();
			log.debug(CommonConstant.LOG_DEBUG_TAG + "获取当前用户：{}", userDetailItem);

			YYUserInfo yyUserInfo = yyUserInfoRepository.findByUser(foxUser);
			
			log.debug(CommonConstant.LOG_DEBUG_TAG + "获取用户Info",  yyUserInfo);
			
			if (AssertHelper.notEmpty(_userProfile.getAddress())) {
				yyUserInfo.setAddress(_userProfile.getAddress());
			}
			if (AssertHelper.notEmpty(_userProfile.getOrgId())) {

				yyUserInfo.setDeptId(_userProfile.getOrgId());

			}
			if (AssertHelper.notEmpty(_userProfile.getEmail())) {

				yyUserInfo.setEmail(_userProfile.getEmail());
			}
			if (AssertHelper.notEmpty(_userProfile.getGender())) {
				yyUserInfo.setGender(_userProfile.getGender());
			}
			
			
			if (AssertHelper.notEmpty(_userProfile.getPhone())) {
				yyUserInfo.setPhone(_userProfile.getPhone());
			}
			
			if (AssertHelper.notEmpty(_userProfile.getAdministrativePost())) {
				yyUserInfo.setAdministrativePost(_userProfile.getAdministrativePost());
			}
			
			
			if (AssertHelper.notEmpty(_userProfile.getAdministrativeRank())) {
				yyUserInfo.setAdministrativeRank(_userProfile.getAdministrativeRank());
			}
			
			if (AssertHelper.notEmpty(_userProfile.getOccupationType())) {
				yyUserInfo.setOccupationType(_userProfile.getOccupationType());
			}
			
			if (AssertHelper.notEmpty(_userProfile.getProfessionalTitle())) {
				yyUserInfo.setProfessionalTitle(_userProfile.getProfessionalTitle());
			}
			yyUserInfo.setBirthiday( DateUtils.formatDate(_userProfile.getBirthday(), "yyyy-MM-dd") );
			

			yyUserInfo.setUser(foxUser);
			foxUser.setUserInfo(yyUserInfo);
			foxUserRepository.save(foxUser);
			// 绑定角色
			if (_userProfile.getRoles() != null && !_userProfile.getRoles().isEmpty()) {
				List<YYUserRole> foxUserRoles = foxUserRoleRepository.findByUserId(_userProfile.getId());
				foxUserRoleRepository.deleteInBatch(foxUserRoles);

				// 过滤重复的roleId
				Set<String> roleIdSet = new HashSet<String>();
				_userProfile.getRoles().forEach(roleProfile -> {
					roleIdSet.add(roleProfile.getId());
				});

				roleIdSet.forEach(roleId -> {
					YYUserRole foxUserRole = new YYUserRole();
					foxUserRole.setRoleId(roleId);
					foxUserRole.setUserId(userId);
					foxUserRole.setCreateDate(new Date());
					foxUserRoleRepository.save(foxUserRole);
				});
			}
	}

    @Override
    public void updateUserStatus(String _userId, Byte _status) {
        YYUser foxUser = Optional.ofNullable(foxUserRepository.findOne(_userId))
                .orElseThrow(() -> new NoRecordFoundException(String.format("user %s not found.", _userId)));
        foxUser.setStatus(_status);
        foxUserRepository.save(foxUser);
    }

    @Override
    public UserItem findUserById(String _userId) {
        YYUser foxUser = Optional.ofNullable(foxUserRepository.findOne(_userId))
                .orElseThrow(() -> new NoRecordFoundException(String.format("user %s not found.", _userId)));

        UserItem userItem = modelMapper.map(foxUser, UserItem.class);
        return userItem;
    }

    @Override
    public List<UserItem> listUsersByPage(PageInfo _pageInfo, Byte _status) {
    	
    	
    	return new ArrayList<UserItem>();
    }

    @Override
    public List<UserDetailsItem> listUsersByUserName(PageInfo _pageInfo, String _userName) {
		PageRequest pageRequest = new PageRequest(_pageInfo.getCurrentPage(), _pageInfo.getPageSize(),
				Sort.Direction.DESC, "createDate");

		UserDetailsItem userDetailsItem = securityService.getCurrentUser();
		log.debug(CommonConstant.LOG_DEBUG_TAG + "获取当前用户信息：{}" + userDetailsItem);
		Page<YYUser> foxUsers;
		List<UserDetailsItem> UserDetailsItems = new ArrayList<UserDetailsItem>();
		if (StringUtils.isBlank(_userName)) {
			foxUsers = foxUserRepository.findByStatusLessThan(CommonConstant.DIC_GLOBAL_STATUS_DELETED,
					pageRequest);
		} else {
			foxUsers = foxUserRepository.findByStatusLessThanAndUserInfoUserNameLike(
					 CommonConstant.DIC_GLOBAL_STATUS_DELETED,
					"%" + _userName + "%", pageRequest);
		}
		log.info(CommonConstant.LOG_DEBUG_TAG + "查询当前登录用户下所属企业的用户结果：{}", foxUsers);
		foxUsers.forEach(foxUser -> {
			UserDetailsItem userDetailTemp = restructUserBean(foxUser);
			
			
			// 获取每个用户的部门
			YYOrganization foxOrganizations = yyOrganzationRepository.findOne(userDetailTemp.getDeptId());
			if (foxOrganizations!=null) {
				userDetailTemp.setOrganizationName(foxOrganizations.getName());
				userDetailTemp.setOrganizationId(foxOrganizations.getId());
			}
			List<RoleItem> roleItems = new ArrayList<>();
	        foxUserRoleRepository.findByUserId(foxUser.getId()).forEach(foxUserRole -> {
	            YYRole foxRole = foxRoleRepository.findOne(foxUserRole.getRoleId());
	            RoleItem roleItem = new RoleItem();
	            roleItem.setId(foxRole.getId());
	            roleItem.setName(foxRole.getName());
	            roleItem.setRoleName(foxRole.getRoleName());
	            roleItem.setStatus(foxRole.getStatus());
	            roleItems.add(roleItem);
	        });
	        userDetailTemp.setRoles(roleItems);
			UserDetailsItems.add(userDetailTemp);
		});
	

		_pageInfo.setTotalPage(foxUsers.getTotalPages());
		_pageInfo.setTotalRecords(new Long(foxUsers.getTotalElements()));

		return UserDetailsItems;
	}
    
    
    /**
     * 
     * @param yyUser
     * @return
     */
    private UserDetailsItem  restructUserBean(YYUser yyUser){
    	UserDetailsItem userDetailsItem= new UserDetailsItem();
    	 userDetailsItem.setUserId(yyUser.getId());
         userDetailsItem.setLoginName(yyUser.getLoginName());
         userDetailsItem.setPassword(yyUser.getPassword());
         userDetailsItem.setUserName(yyUser.getUserInfo().getUserName());
         userDetailsItem.setEmail(yyUser.getUserInfo().getEmail());
         userDetailsItem.setPhone(yyUser.getUserInfo().getPhone());
         userDetailsItem.setStatus(yyUser.getStatus());
         userDetailsItem.setDescription(yyUser.getDescription());
         userDetailsItem.setBirthday(yyUser.getUserInfo().getBirthiday());
    	
         userDetailsItem.setAdministrativePost(yyUser.getUserInfo().getAdministrativePost());
         userDetailsItem.setGender(yyUser.getUserInfo().getGender());
         userDetailsItem.setAdministrativeRank(yyUser.getUserInfo().getAdministrativeRank());
         userDetailsItem.setOrganizationId(yyUser.getUserInfo().getDeptId());
         userDetailsItem.setProfessionalTitle(yyUser.getUserInfo().getProfessionalTitle());
         userDetailsItem.setOccupationType(yyUser.getUserInfo().getOccupationType());
         userDetailsItem.setDeptId(yyUser.getUserInfo().getDeptId());
         
    	return userDetailsItem;
    }

    @Override
    public List<UserItem> listNonOrganizationMembers(PageInfo _pageInfo) {
        PageRequest pageRequest = new PageRequest(_pageInfo.getCurrentPage(), _pageInfo.getPageSize(),
                Sort.Direction.DESC, "createDate");
        Page<String> userIds = foxUserOrganizationRepository.findNonOrganizationUserIds(pageRequest);

        List<UserItem> userItems = new ArrayList<>();
        userIds.forEach(userId -> {
            YYUser foxUser = foxUserRepository.findOne(userId);
            UserItem userItem = modelMapper.map(foxUser, UserItem.class);
            userItems.add(userItem);
        });

        _pageInfo.setTotalPage(userIds.getTotalPages());
        _pageInfo.setTotalRecords(new Long(userIds.getTotalElements()));

        return userItems;
    }

    @Override
	public List<UserDetailsItem> listUsersInOrganization(String _organizationId, PageInfo _pageInfo) {
		PageRequest pageRequest = new PageRequest(_pageInfo.getCurrentPage(), _pageInfo.getPageSize());

		UserDetailsItem userDetailsItem = securityService.getCurrentUser();
		log.debug(CommonConstant.LOG_DEBUG_TAG + "获取当前用户信息：{}" + userDetailsItem);
		Page<YYUser> foxUsers;
		List<UserDetailsItem> UserDetailsItems = new ArrayList<UserDetailsItem>();
		foxUsers = foxUserRepository.findByStatusLessThanAndUserInfoDeptId(CommonConstant.DIC_GLOBAL_STATUS_DELETED,_organizationId, pageRequest);
		log.info(CommonConstant.LOG_DEBUG_TAG + "查询当前登录用户下所属企业的用户结果：{}", foxUsers);
		foxUsers.forEach(foxUser -> {
			UserDetailsItem userDetailTemp = restructUserBean(foxUser);
			// 获取每个用户的部门
			List<YYOrganization> foxOrganizations = yyOrganzationRepository.findOrganizationByUserId(foxUser.getId());
			if (!CollectionUtils.isEmpty(foxOrganizations)) {
				userDetailTemp.setOrganizationName(foxOrganizations.get(0).getName());

				List<OrganizationItem> organizations = new ArrayList<OrganizationItem>();
				OrganizationItem item = new OrganizationItem();

				item.setId(foxOrganizations.get(0).getId());
				item.setDesc(foxOrganizations.get(0).getDescription());
				item.setOrganizitionName(foxOrganizations.get(0).getName());
				item.setLeaderId(foxOrganizations.get(0).getLeadeShip());
				userDetailTemp.setOrganizations(organizations);
			}
			List<RoleItem> roleItems = new ArrayList<>();
			foxUserRoleRepository.findByUserId(foxUser.getId()).forEach(foxUserRole -> {
				YYRole foxRole = foxRoleRepository.findOne(foxUserRole.getRoleId());
				RoleItem roleItem = new RoleItem();
				roleItem.setId(foxRole.getId());
				roleItem.setName(foxRole.getName());
				roleItem.setRoleName(foxRole.getRoleName());
				roleItem.setStatus(foxRole.getStatus());
				roleItems.add(roleItem);
			});
			userDetailTemp.setRoles(roleItems);
			UserDetailsItems.add(userDetailTemp);
		});

		_pageInfo.setTotalPage(foxUsers.getTotalPages());
		_pageInfo.setTotalRecords(new Long(foxUsers.getTotalElements()));

		return UserDetailsItems;
	}

    @Override
    public void modifyPassword(PasswordProfile _passwordProfile) {
        YYUser foxUser = foxUserRepository.findOne(_passwordProfile.getId());
        if (!encoder.matches(_passwordProfile.getPassword(), foxUser.getPassword())) {
            throw new PasswordNotMatchException();
        }

        foxUser.setPassword(encoder.encode(_passwordProfile.getNewPassword()));
        foxUserRepository.save(foxUser);
    }

    @Override
    public UserDetailsItem loadUserByLoginName(String _loginName) {
        UserDetailsItem userDetailsItem = new UserDetailsItem();
        YYUser foxUser = foxUserRepository.findByLoginNameAndStatusLessThan(_loginName , CommonConstant.DIC_GLOBAL_STATUS_DELETED);
        
        if(foxUser==null){
        	throw new NoRecordFoundException(String.format("user with login name %s not exist.", _loginName));
        }

        userDetailsItem.setUserId(foxUser.getId());
        userDetailsItem.setLoginName(foxUser.getLoginName());
        userDetailsItem.setPassword(foxUser.getPassword());
        userDetailsItem.setUserName(foxUser.getUserInfo().getUserName());
        userDetailsItem.setEmail(foxUser.getUserInfo().getEmail());
        userDetailsItem.setPhone(foxUser.getUserInfo().getPhone());
        userDetailsItem.setStatus(foxUser.getStatus());
        userDetailsItem.setDescription(foxUser.getDescription());
        
        userDetailsItem.setOrganizationId(foxUser.getUserInfo().getDeptId());

        List<RoleItem> roleItems = new ArrayList<>();
        foxUserRoleRepository.findByUserId(foxUser.getId()).forEach(foxUserRole -> {
            YYRole foxRole = foxRoleRepository.findOne(foxUserRole.getRoleId());
            RoleItem roleItem = new RoleItem();
            roleItem.setId(foxRole.getId());
            roleItem.setName(foxRole.getName());
            roleItem.setRoleName(foxRole.getRoleName());
            roleItem.setStatus(foxRole.getStatus());
            roleItems.add(roleItem);
        });
        userDetailsItem.setRoles(roleItems);

        return userDetailsItem;
    }

    @Override
    public UserDetailsItem loadUserByUserId(String _userId) {
        UserDetailsItem userDetailsItem = new UserDetailsItem();
        YYUser foxUser = Optional.ofNullable(foxUserRepository.findOne(_userId))
                .orElseThrow(() -> new NoRecordFoundException(String.format("user %s not exist.", _userId)));

        userDetailsItem.setUserId(foxUser.getId());
        userDetailsItem.setLoginName(foxUser.getLoginName());
        userDetailsItem.setPassword(foxUser.getPassword());
        userDetailsItem.setUserName(foxUser.getUserInfo().getUserName());
        userDetailsItem.setEmail(foxUser.getUserInfo().getEmail());
        userDetailsItem.setPhone(foxUser.getUserInfo().getPhone());
        userDetailsItem.setStatus(foxUser.getStatus());
        userDetailsItem.setDescription(foxUser.getDescription());

        List<RoleItem> roleItems = new ArrayList<>();
        foxUserRoleRepository.findByUserId(foxUser.getId()).forEach(foxUserRole -> {
            YYRole foxRole = foxRoleRepository.findOne(foxUserRole.getRoleId());
            RoleItem roleItem = new RoleItem();
            roleItem.setId(foxRole.getId());
            roleItem.setName(foxRole.getName());
            roleItem.setRoleName(foxRole.getRoleName());
            roleItem.setStatus(foxRole.getStatus());
            roleItems.add(roleItem);
        });
        userDetailsItem.setRoles(roleItems);

        List<OrganizationItem> organizationItems = new ArrayList<>();
       

        OrganizationItem organizationItem = organizationItems.get(0);

        userDetailsItem.setOrganizationId(organizationItem.getId());


        userDetailsItem.setIsAD(false);
        if (foxUser.getType() != null && foxUser.getType().byteValue() == AdUserMgmtConstants.USER_TYPE_AD) {
        }
        return userDetailsItem;
    }

    @Override
    public UserDetailsItem loadUserByLoginNameOrId(String loginNameOrId){
        UserDetailsItem userDetailsItem = new UserDetailsItem();
        log.debug(CommonConstant.LOG_DEBUG_TAG + "根据登录名或者ID获取用户信息：{}", loginNameOrId);
        YYUser foxUser = foxUserRepository.findByLoginNameAndStatusLessThan(loginNameOrId.trim(),CommonConstant.DIC_GLOBAL_STATUS_DELETED);
        
        log.debug(CommonConstant.LOG_DEBUG_TAG + "foxUser：{}", loginNameOrId);
        if(null == foxUser){
            throw new NoRecordFoundException(String.format("user %s not exist.", loginNameOrId));
        }

        userDetailsItem.setUserId(foxUser.getId());
        userDetailsItem.setLoginName(foxUser.getLoginName());
        userDetailsItem.setPassword(foxUser.getPassword());
        userDetailsItem.setUserName(foxUser.getUserInfo().getUserName());
        userDetailsItem.setEmail(foxUser.getUserInfo().getEmail());
        userDetailsItem.setPhone(foxUser.getUserInfo().getPhone());
        userDetailsItem.setStatus(foxUser.getStatus());
        userDetailsItem.setDescription(foxUser.getDescription());
        userDetailsItem.setOrganizationId(foxUser.getUserInfo().getDeptId());
        
        userDetailsItem.setGender(foxUser.getUserInfo().getGender());
        
        userDetailsItem.setBirthday(foxUser.getUserInfo().getBirthiday());

        log.debug(CommonConstant.LOG_DEBUG_TAG + "根据登录名或者ID获取角色信息：{}", loginNameOrId);
        List<RoleItem> roleItems = new ArrayList<>();
        foxUserRoleRepository.findByUserId(foxUser.getId()).forEach(foxUserRole -> {
            YYRole foxRole = foxRoleRepository.findOne(foxUserRole.getRoleId());
            RoleItem roleItem = new RoleItem();
            roleItem.setId(foxRole.getId());
            roleItem.setName(foxRole.getName());
            roleItem.setRoleName(foxRole.getRoleName());
            roleItem.setStatus(foxRole.getStatus());
            roleItems.add(roleItem);
        });
        userDetailsItem.setRoles(roleItems);

        log.debug(CommonConstant.LOG_DEBUG_TAG + "根据登录名或者ID获取机构/部门信息：{}", loginNameOrId);
       /* List<YYOrganization> organizationItems = yyOrganzationRepository.findOrganizationByUserId(foxUser.getId());
       
        if(organizationItems!=null && organizationItems.size()>0){
        	YYOrganization yyOrganization = organizationItems.get(0);
        	userDetailsItem.setOrganizationId(yyOrganization.getId());
        }*/
        userDetailsItem.setIsAD(false);     //TODO 这里可能有问题，需要确认
        if (foxUser.getType() != null && foxUser.getType().byteValue() == AdUserMgmtConstants.USER_TYPE_AD) {
        }

        log.info(CommonConstant.LOG_DEBUG_TAG + "根据登录名或者ID获取用户信息结果：{}", userDetailsItem);
        return userDetailsItem;
    }

    @Override
    /**
     * 判断是否为后台用户：后台用户返回true
     */
    public boolean isProviderUser(){
        UserDetailsItem userDetailsItem = securityService.getCurrentUser();
        log.debug(CommonConstant.LOG_DEBUG_TAG + "获取当前用户信息，判断用户是前台还是后台：{}", userDetailsItem);
        String tenantId = userDetailsItem.getEnterpriseId();
        Byte tenantType = userDetailsItem.getEnterpriseType();
        if(null == tenantId || null == tenantType || CommonConstant.DIC_TENANT_TYPE_PROVIDER.equals(tenantType)){
            return true;
        }else{
            return false;
        }
    }

    public GeneralContentResult<String> validateLoginName(String loginName){
        log.debug(CommonConstant.LOG_DEBUG_TAG + "验证登录名是否存在：{}", loginName);
        GeneralContentResult<String> generalContentResult = new GeneralContentResult<String>();
        generalContentResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        YYUser foxUser = foxUserRepository.findByLoginNameAndStatusLessThan(loginName, CommonConstant.DIC_GLOBAL_STATUS_DELETED);
        if(null != foxUser){
            log.debug(CommonConstant.LOG_DEBUG_TAG + "登录名已存在：{}", loginName);
            generalContentResult.setResultCode(ResultCode.USERMGMT_UNEXPECTED_EXCEPTION);
            generalContentResult.setResultContent("用户名 " + loginName +" 已存在");
        }
        return generalContentResult;
    }

    @Override
    public GeneralContentResult<FoxUserItem> validateUserType(String loginName) {
        log.info(CommonConstant.LOG_DEBUG_TAG + "通过登录名判断该用户是前台用户还是后台用户：{}", loginName);
        GeneralContentResult<FoxUserItem> generalContentResult = new GeneralContentResult<FoxUserItem>();
        generalContentResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        FoxUserItem foxUserItem = new FoxUserItem();
        foxUserItem.setLoginName(loginName);//设置返回值的的登录名为当前登录名
        // 首先取当前用户进行判断，如果不能取到当前用户，再通过loginName判断
        generalContentResult.setResultContent(foxUserItem);
        log.info(CommonConstant.LOG_DEBUG_TAG + "通过登录名判断该用户是前台用户还是后台用户,结果：{}", generalContentResult);
        return generalContentResult;
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED)
	@Override
	public GeneralResult deleteUser(String id) {
    	foxUserRepository.setStatusFor(UserMgmtConstants.STATUS_GLOBAL_DELETED, id);
    	GeneralResult result = new GeneralResult();
    	result.setResultCode(ResultCode.OPERATION_SUCCESS);
    	return result;
    	
	}

    
}
