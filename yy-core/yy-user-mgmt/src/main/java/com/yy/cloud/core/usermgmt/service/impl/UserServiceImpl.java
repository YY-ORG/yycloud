package com.yy.cloud.core.usermgmt.service.impl;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.dto.accountcenter.PasswordProfile;
import com.yy.cloud.common.data.dto.accountcenter.UserProfile;
import com.yy.cloud.common.data.otd.organization.OrganizationItem;
import com.yy.cloud.common.data.otd.role.RoleItem;
import com.yy.cloud.common.data.otd.user.FoxUserItem;
import com.yy.cloud.common.data.otd.user.UserDetailsItem;
import com.yy.cloud.common.data.otd.user.UserItem;
import com.yy.cloud.common.exception.NoRecordFoundException;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.core.usermgmt.constant.AdUserMgmtConstants;
import com.yy.cloud.core.usermgmt.data.domain.*;
import com.yy.cloud.core.usermgmt.data.repositories.*;
import com.yy.cloud.core.usermgmt.exception.PasswordNotMatchException;
import com.yy.cloud.core.usermgmt.exception.UserExistException;
import com.yy.cloud.core.usermgmt.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private FoxUserRepository foxUserRepository;

    @Autowired
    private FoxUserOrganizationRepository foxUserOrganizationRepository;

    @Autowired
    private FoxUserRoleRepository foxUserRoleRepository;

    @Autowired
    private FoxOrganizationRepository foxOrganizationRepository;

    @Autowired
    private FoxRoleRepository foxRoleRepository;

    @Autowired
    private FoxTenantRepository foxTenantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ExtLdapUserRepository extLdapUserRepository;

    @Autowired
    private ExtLdapSourceRepository extLdapSourceRepository;

    @Autowired
    private SecurityService securityService;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * MPP 和 ADM创建用户接口都是调用该方法
     * @param _userProfile
     * @return
     */
    @Override
    @Transactional
    public String createUser(UserProfile _userProfile) {
//        foxUserRepository.findByLoginName(_userProfile.getLoginName()).ifPresent(foxUser -> {
//            throw new UserExistException();
//        });
        //增加唯一性验证: 登录名，类型(本地/AD), 企业ID三者联合唯一
//        foxUserRepository.findByLoginNameAndTypeAndTenantId(_userProfile.getLoginName(), AdUserMgmtConstants.USER_TYPE_LOCAL, _userProfile.getTenantId()).ifPresent(foxUser -> {
//            log.info(CommonConstant.LOG_DEBUG_TAG + "该用户名已存在：" + _userProfile.getLoginName());
//            throw new UserExistException();
//        });

        // 只校验本地
//        foxUserRepository.findByLoginNameAndType(_userProfile.getLoginName(), AdUserMgmtConstants.USER_TYPE_LOCAL).ifPresent(foxUser -> {
//            log.info(CommonConstant.LOG_DEBUG_TAG + "该用户名已存在：" + _userProfile.getLoginName());
//            throw new UserExistException();
//        });

        // 只校验本地
        FoxUser foxUserExist = foxUserRepository.findByLoginNameAndType(_userProfile.getLoginName(), AdUserMgmtConstants.USER_TYPE_LOCAL);
        if(null != foxUserExist){
            log.info(CommonConstant.LOG_DEBUG_TAG + "该用户名已存在：" + _userProfile.getLoginName());
            throw new UserExistException();
        }

        FoxUser foxUser = modelMapper.map(_userProfile, FoxUser.class);
        foxUser.setStatus(CommonConstant.DIC_GLOBAL_STATUS_INITIAL);
        if (_userProfile.getAuthMode() != null && _userProfile.getAuthMode().equals(AdUserMgmtConstants.USER_TYPE_AD) ) {
            foxUser.setType(AdUserMgmtConstants.USER_TYPE_AD);
        } else {
            foxUser.setType(AdUserMgmtConstants.USER_TYPE_LOCAL);
        }
        // foxUser.setType(AdUserMgmtConstants.USER_TYPE_LOCAL);
        String encodedPassword = encoder.encode("12345");
        foxUser.setPassword(encodedPassword);
        foxUserRepository.save(foxUser);

        // 绑定角色
        if (_userProfile.getRoles() != null && !_userProfile.getRoles().isEmpty()) {
            _userProfile.getRoles().forEach(roleProfile -> {
                FoxUserRole foxUserRole = new FoxUserRole();
                foxUserRole.setRoleId(roleProfile.getId());
                foxUserRole.setUserId(foxUser.getId());
                foxUserRoleRepository.save(foxUserRole);
            });
        }

        // 绑定机构
        if (_userProfile.getOrganizations() != null && !_userProfile.getOrganizations().isEmpty()) {
            _userProfile.getOrganizations().forEach(organizationProfile -> {
                FoxUserOrganization foxUserOrganization = new FoxUserOrganization();
                foxUserOrganization.setUserId(foxUser.getId());
                foxUserOrganization.setOrganizationId(organizationProfile.getId());
                foxUserOrganizationRepository.save(foxUserOrganization);
            });
        }

        return foxUser.getId();
    }

    @Override
    @Transactional
    public void modifyUser(UserProfile _userProfile) {
        FoxUser foxUser = Optional.ofNullable(foxUserRepository.findOne(_userProfile.getId())).orElseThrow(
                () -> new NoRecordFoundException(String.format("user %s not found.", _userProfile.getId())));

        String userId = _userProfile.getId();
        String password = foxUser.getPassword();
        Byte status = foxUser.getStatus();
        Byte type = foxUser.getType();
        String loginName = foxUser.getLoginName();

        foxUser = modelMapper.map(_userProfile, FoxUser.class);
        foxUser.setStatus(status);
        foxUser.setPassword(password);
        foxUser.setType(type);
        foxUser.setLoginName(loginName);
        UserDetailsItem userDetailItem = securityService.getCurrentUser();
        log.debug(CommonConstant.LOG_DEBUG_TAG + "获取当前用户：{}", userDetailItem );
        foxUser.setTenantId(userDetailItem.getEnterpriseId()); //将企业设置为当前用户企业，避免前端未传企业ID

        foxUserRepository.save(foxUser);

        // 绑定角色
        if (_userProfile.getRoles() != null && !_userProfile.getRoles().isEmpty()) {
            List<FoxUserRole> foxUserRoles = foxUserRoleRepository.findByUserId(_userProfile.getId());
            foxUserRoleRepository.deleteInBatch(foxUserRoles);

            // 过滤重复的roleId
            Set<String> roleIdSet = new HashSet<String>();
            _userProfile.getRoles().forEach(roleProfile -> {
                roleIdSet.add(roleProfile.getId());
            });

            roleIdSet.forEach(roleId -> {
                FoxUserRole foxUserRole = new FoxUserRole();
                foxUserRole.setRoleId(roleId);
                foxUserRole.setUserId(userId);
                foxUserRoleRepository.save(foxUserRole);
            });
        }

        // 绑定机构
        if (_userProfile.getOrganizations() != null && !_userProfile.getOrganizations().isEmpty()) {
            List<FoxUserOrganization> foxUserOrganizations = foxUserOrganizationRepository.findByUserId(userId);
            foxUserOrganizationRepository.deleteInBatch(foxUserOrganizations);

            Set<String> organIdSet = new HashSet<String>();
            _userProfile.getOrganizations().forEach(organizationProfile -> {
                organIdSet.add(organizationProfile.getId());
            });

            organIdSet.forEach(organId -> {
                FoxUserOrganization foxUserOrganization = new FoxUserOrganization();
                foxUserOrganization.setUserId(userId);
                foxUserOrganization.setOrganizationId(organId);
                foxUserOrganizationRepository.save(foxUserOrganization);
            });
        }
    }

    @Override
    public void updateUserStatus(String _userId, Byte _status) {
        FoxUser foxUser = Optional.ofNullable(foxUserRepository.findOne(_userId))
                .orElseThrow(() -> new NoRecordFoundException(String.format("user %s not found.", _userId)));
        foxUser.setStatus(_status);
        foxUserRepository.save(foxUser);
    }

    @Override
    public UserItem findUserById(String _userId) {
        FoxUser foxUser = Optional.ofNullable(foxUserRepository.findOne(_userId))
                .orElseThrow(() -> new NoRecordFoundException(String.format("user %s not found.", _userId)));

        UserItem userItem = modelMapper.map(foxUser, UserItem.class);
        return userItem;
    }

    @Override
    public List<UserItem> listUsersByPage(PageInfo _pageInfo, Byte _status) {

        PageRequest pageRequest = new PageRequest(_pageInfo.getCurrentPage(), _pageInfo.getPageSize(),
                Sort.Direction.DESC, "createDate");

        UserDetailsItem userDetailsItem = securityService.getCurrentUser();
        //当前登录用户的部门/机构
        String organizationName = userDetailsItem.getOrganizationName();
        log.debug(CommonConstant.LOG_DEBUG_TAG + "获取当前用户信息：{}" + userDetailsItem);

        List<UserItem> userItems = new ArrayList<>();

        Page<FoxUser> foxUsers;
        // 后台需要根据机构查询：
        String organizationId = userDetailsItem.getOrganizationId();
        if(CommonConstant.DIC_TENANT_TYPE_PROVIDER.equals(userDetailsItem.getEnterpriseType()) || null == userDetailsItem.getEnterpriseType()){
            List<FoxUserOrganization> userOrganizations =  foxUserOrganizationRepository.findByOrganizationId(organizationId);
            List<String> userIdList = new ArrayList<String>();
            userOrganizations.forEach(foxUserOrganization -> {
                userIdList.add(foxUserOrganization.getUserId());
            });
            foxUsers = foxUserRepository.findByIdInAndStatusLessThan(userIdList, CommonConstant.DIC_GLOBAL_STATUS_DELETED, pageRequest);
            log.info(CommonConstant.LOG_DEBUG_TAG + "查询当前登录用户下所属机构的用户结果：{}", foxUsers);
            foxUsers.forEach(foxUser -> {
                UserItem userItem = modelMapper.map(foxUser, UserItem.class);
                //给每个用户赋值相同的机构
                userItem.setOrganizationName(organizationName);
                //获取每个用户的角色
                List<FoxRole> foxRoles = foxRoleRepository.findRolesByUserId(foxUser.getId());
                if(!CollectionUtils.isEmpty(foxRoles)){
                    userItem.setRoleName(foxRoles.get(0).getName());
                }
                userItems.add(userItem);
            });
        }else {
            //前台根据企业ID查询，
            // TODO: 角色和部门
            if (null == _status) {
                // 过滤掉已删除用户

                foxUsers = foxUserRepository.findByTenantIdAndStatusLessThan(userDetailsItem.getEnterpriseId(), CommonConstant.DIC_GLOBAL_STATUS_DELETED, pageRequest);
            } else {
                foxUsers = foxUserRepository.findByStatusAndTenantId(_status, userDetailsItem.getEnterpriseId(), pageRequest);
            }
            log.info(CommonConstant.LOG_DEBUG_TAG + "查询当前登录用户下所属企业的用户结果：{}", foxUsers);
            foxUsers.forEach(foxUser -> {
                UserItem userItem = modelMapper.map(foxUser, UserItem.class);
                //获取每个用户的部门
                List<FoxOrganization> foxOrganizations = foxOrganizationRepository.findOrganizationByUserId(foxUser.getId());
                if (!CollectionUtils.isEmpty(foxOrganizations)){
                    userItem.setOrganizationName(foxOrganizations.get(0).getName());
                }
                //获取每个用户的角色
                List<FoxRole> foxRoles = foxRoleRepository.findRolesByUserId(foxUser.getId());
                if(!CollectionUtils.isEmpty(foxRoles)){
                    userItem.setRoleName(foxRoles.get(0).getName());
                }
                userItems.add(userItem);
            });
        }

        _pageInfo.setTotalPage(foxUsers.getTotalPages());
        _pageInfo.setTotalRecords(new Long(foxUsers.getTotalElements()).intValue());

        return userItems;
    }

    @Override
    public List<UserItem> listUsersByUserName(PageInfo _pageInfo, String _userName) {
        PageRequest pageRequest = new PageRequest(_pageInfo.getCurrentPage(), _pageInfo.getPageSize(),
                Sort.Direction.DESC, "createDate");

        UserDetailsItem userDetailsItem = securityService.getCurrentUser();
        //当前登录用户的机构
        String organizationName = userDetailsItem.getOrganizationName();
        log.debug(CommonConstant.LOG_DEBUG_TAG + "获取当前用户信息：{}" + userDetailsItem);

        Page<FoxUser> foxUsers;

        List<UserItem> userItems = new ArrayList<>();

//        if (StringUtils.isBlank(_userName)) {
//            foxUsers = foxUserRepository.findAll(pageRequest);
//        } else {
//            foxUsers = foxUserRepository.findByUserNameLike("%" + _userName + "%", pageRequest);
//        }

        // 后台需要根据机构查询：
        String organizationId = userDetailsItem.getOrganizationId();
        if(CommonConstant.DIC_TENANT_TYPE_PROVIDER.equals(userDetailsItem.getEnterpriseType()) || null == userDetailsItem.getEnterpriseType()){
            List<FoxUserOrganization> userOrganizations =  foxUserOrganizationRepository.findByOrganizationId(organizationId);
            List<String> userIdList = new ArrayList<String>();
            userOrganizations.forEach(foxUserOrganization -> {
                userIdList.add(foxUserOrganization.getUserId());
            });
            if (StringUtils.isBlank(_userName)) {
                // foxUsers = foxUserRepository.findAll(pageRequest);
                foxUsers = foxUserRepository.findByIdInAndStatusLessThan(userIdList, CommonConstant.DIC_GLOBAL_STATUS_DELETED, pageRequest);
            } else {
                foxUsers = foxUserRepository.findByIdInAndStatusLessThanAndUserNameLike(userIdList, CommonConstant.DIC_GLOBAL_STATUS_DELETED, "%" + _userName + "%", pageRequest);
            }
            log.info(CommonConstant.LOG_DEBUG_TAG + "查询当前登录用户下所属机构的用户结果：{}", foxUsers);
            foxUsers.forEach(foxUser -> {
                UserItem userItem = modelMapper.map(foxUser, UserItem.class);
                //给每个用户赋值相同的机构
                userItem.setOrganizationName(organizationName);
                //获取每个用户的角色
                List<FoxRole> foxRoles = foxRoleRepository.findRolesByUserId(foxUser.getId());
                if(!CollectionUtils.isEmpty(foxRoles)){
                    userItem.setRoleName(foxRoles.get(0).getName());
                }
                userItems.add(userItem);
            });
        }else {
//            if (null == _status) {
//                // 过滤掉已删除用户
//                foxUsers = foxUserRepository.findByTenantIdAndStatusLessThan(userDetailsItem.getEnterpriseId(), CommonConstant.DIC_GLOBAL_STATUS_DELETED, pageRequest);
//            } else {
//                foxUsers = foxUserRepository.findByStatusAndTenantId(_status, userDetailsItem.getEnterpriseId(), pageRequest);
//            }
            if(StringUtils.isBlank(_userName)){
                foxUsers = foxUserRepository.findByTenantIdAndStatusLessThan(userDetailsItem.getEnterpriseId(), CommonConstant.DIC_GLOBAL_STATUS_DELETED, pageRequest);
            }else {
                foxUsers = foxUserRepository.findByTenantIdAndStatusLessThanAndUserNameLike(userDetailsItem.getEnterpriseId(), CommonConstant.DIC_GLOBAL_STATUS_DELETED, "%" + _userName + "%", pageRequest);
            }
            log.info(CommonConstant.LOG_DEBUG_TAG + "查询当前登录用户下所属企业的用户结果：{}", foxUsers);
            foxUsers.forEach(foxUser -> {
                UserItem userItem = modelMapper.map(foxUser, UserItem.class);
                //获取每个用户的部门
                List<FoxOrganization> foxOrganizations = foxOrganizationRepository.findOrganizationByUserId(foxUser.getId());
                if (!CollectionUtils.isEmpty(foxOrganizations)){
                    userItem.setOrganizationName(foxOrganizations.get(0).getName());
                }
                //获取每个用户的角色
                List<FoxRole> foxRoles = foxRoleRepository.findRolesByUserId(foxUser.getId());
                if(!CollectionUtils.isEmpty(foxRoles)){
                    userItem.setRoleName(foxRoles.get(0).getName());
                }
                userItems.add(userItem);
            });
        }

        _pageInfo.setTotalPage(foxUsers.getTotalPages());
        _pageInfo.setTotalRecords(new Long(foxUsers.getTotalElements()).intValue());

        return userItems;
    }

    @Override
    public List<UserItem> listNonOrganizationMembers(PageInfo _pageInfo) {
        PageRequest pageRequest = new PageRequest(_pageInfo.getCurrentPage(), _pageInfo.getPageSize(),
                Sort.Direction.DESC, "createDate");
        Page<String> userIds = foxUserOrganizationRepository.findNonOrganizationUserIds(pageRequest);

        List<UserItem> userItems = new ArrayList<>();
        userIds.forEach(userId -> {
            FoxUser foxUser = foxUserRepository.findOne(userId);
            UserItem userItem = modelMapper.map(foxUser, UserItem.class);
            userItems.add(userItem);
        });

        _pageInfo.setTotalPage(userIds.getTotalPages());
        _pageInfo.setTotalRecords(new Long(userIds.getTotalElements()).intValue());

        return userItems;
    }

    @Override
    public List<UserItem> listUsersInOrganization(String _organizationId) {
        List<FoxUserOrganization> foxUserOrganizations = foxUserOrganizationRepository
                .findByOrganizationId(_organizationId);
        List<String> userIds = foxUserOrganizations.stream().map(foxUserOrganization -> foxUserOrganization.getUserId())
                .collect(Collectors.toList());

        List<FoxUser> foxUsers = foxUserRepository.findAll(userIds);
        List<UserItem> userItems = foxUsers.stream().map(foxUser -> modelMapper.map(foxUser, UserItem.class))
                .sorted((u1, u2) -> u1.getId().compareTo(u2.getId())).collect(Collectors.toList());

        return userItems;
    }

    @Override
    public void modifyPassword(PasswordProfile _passwordProfile) {
        FoxUser foxUser = foxUserRepository.findOne(_passwordProfile.getId());
        if (!encoder.matches(_passwordProfile.getPassword(), foxUser.getPassword())) {
            throw new PasswordNotMatchException();
        }

        foxUser.setPassword(encoder.encode(_passwordProfile.getNewPassword()));
        foxUserRepository.save(foxUser);
    }

    @Override
    public UserDetailsItem loadUserByLoginName(String _loginName) {
        UserDetailsItem userDetailsItem = new UserDetailsItem();
        FoxUser foxUser = foxUserRepository.findByLoginName(_loginName).orElseThrow(
                () -> new NoRecordFoundException(String.format("user with login name %s not exist.", _loginName)));

        userDetailsItem.setUserId(foxUser.getId());
        userDetailsItem.setLoginName(foxUser.getLoginName());
        userDetailsItem.setPassword(foxUser.getPassword());
        userDetailsItem.setUserName(foxUser.getUserName());
        userDetailsItem.setEmail(foxUser.getEmail());
        userDetailsItem.setPhone(foxUser.getPhone());
        userDetailsItem.setStatus(foxUser.getStatus());
        userDetailsItem.setDescription(foxUser.getDescription());

        List<RoleItem> roleItems = new ArrayList<>();
        foxUserRoleRepository.findByUserId(foxUser.getId()).forEach(foxUserRole -> {
            FoxRole foxRole = foxRoleRepository.findOne(foxUserRole.getRoleId());
            RoleItem roleItem = new RoleItem();
            roleItem.setId(foxRole.getId());
            roleItem.setName(foxRole.getName());
            roleItem.setRoleName(foxRole.getRoleName());
            roleItem.setStatus(foxRole.getStatus());
            roleItems.add(roleItem);
        });
        userDetailsItem.setRoles(roleItems);

        List<OrganizationItem> organizationItems = new ArrayList<>();
        foxUserOrganizationRepository.findByUserId(foxUser.getId()).forEach(foxUserOrganization -> {
            FoxOrganization foxOrganization = foxOrganizationRepository
                    .findOne(foxUserOrganization.getOrganizationId());
            OrganizationItem organizationItem = new OrganizationItem();
            organizationItem.setId(foxOrganization.getId());
            organizationItem.setName(foxOrganization.getName());
            organizationItem.setLeaderId(foxOrganization.getLeaderId());
            organizationItem.setStatus(foxOrganization.getStatus());
            organizationItems.add(organizationItem);
        });
        userDetailsItem.setOrganizations(organizationItems);

        OrganizationItem organizationItem = organizationItems.get(0);

        userDetailsItem.setOrganizationId(organizationItem.getId());
        userDetailsItem.setOrganizationName(organizationItem.getName());

        String leaderId = organizationItem.getLeaderId();
        if (!StringUtils.isBlank(leaderId)) {
            userDetailsItem.setLeaderId(leaderId);
            Optional.ofNullable(foxUserRepository.findOne(leaderId)).ifPresent(
                    leaderUser -> userDetailsItem.setLeaderName(leaderUser.getUserName())
            );
        }

        String tenantId = foxUser.getTenantId();
        if (!StringUtils.isBlank(tenantId)) {
            userDetailsItem.setEnterpriseId(tenantId);
            Optional.ofNullable(foxTenantRepository.findOne(tenantId)).ifPresent(
                    foxTenant -> {
                        userDetailsItem.setEnterpriseName(foxTenant.getName());
                        userDetailsItem.setEnterpriseType(foxTenant.getType());
                        userDetailsItem.setEnterpriseAuthMode(foxTenant.getAuthmode());
                    }
            );
        }

        userDetailsItem.setIsAD(false);
        if (foxUser.getType() != null && foxUser.getType().byteValue() == AdUserMgmtConstants.USER_TYPE_AD) {
            userDetailsItem.setIsAD(true);
            ExtLdapUser ldapUser = extLdapUserRepository.findOneByUserId(foxUser.getId());
            ExtLdapSource ldap = extLdapSourceRepository.findOne(ldapUser.getLdapSourceId());
            userDetailsItem.setLdapId(ldapUser.getLdapSourceId());
            userDetailsItem.setLoginName(ldapUser.getLogin());
            userDetailsItem.setLdapName(ldap.getName());
        }
        return userDetailsItem;
    }

    @Override
    public UserDetailsItem loadUserByUserId(String _userId) {
        UserDetailsItem userDetailsItem = new UserDetailsItem();
        FoxUser foxUser = Optional.ofNullable(foxUserRepository.findOne(_userId))
                .orElseThrow(() -> new NoRecordFoundException(String.format("user %s not exist.", _userId)));

        userDetailsItem.setUserId(foxUser.getId());
        userDetailsItem.setLoginName(foxUser.getLoginName());
        userDetailsItem.setPassword(foxUser.getPassword());
        userDetailsItem.setUserName(foxUser.getUserName());
        userDetailsItem.setEmail(foxUser.getEmail());
        userDetailsItem.setPhone(foxUser.getPhone());
        userDetailsItem.setStatus(foxUser.getStatus());
        userDetailsItem.setDescription(foxUser.getDescription());

        List<RoleItem> roleItems = new ArrayList<>();
        foxUserRoleRepository.findByUserId(foxUser.getId()).forEach(foxUserRole -> {
            FoxRole foxRole = foxRoleRepository.findOne(foxUserRole.getRoleId());
            RoleItem roleItem = new RoleItem();
            roleItem.setId(foxRole.getId());
            roleItem.setName(foxRole.getName());
            roleItem.setRoleName(foxRole.getRoleName());
            roleItem.setStatus(foxRole.getStatus());
            roleItems.add(roleItem);
        });
        userDetailsItem.setRoles(roleItems);

        List<OrganizationItem> organizationItems = new ArrayList<>();
        foxUserOrganizationRepository.findByUserId(foxUser.getId()).forEach(foxUserOrganization -> {
            FoxOrganization foxOrganization = foxOrganizationRepository
                    .findOne(foxUserOrganization.getOrganizationId());
            OrganizationItem organizationItem = new OrganizationItem();
            organizationItem.setId(foxOrganization.getId());
            organizationItem.setName(foxOrganization.getName());
            organizationItem.setLeaderId(foxOrganization.getLeaderId());
            organizationItem.setStatus(foxOrganization.getStatus());
            organizationItems.add(organizationItem);
        });
        userDetailsItem.setOrganizations(organizationItems);

        OrganizationItem organizationItem = organizationItems.get(0);

        userDetailsItem.setOrganizationId(organizationItem.getId());
        userDetailsItem.setOrganizationName(organizationItem.getName());

        String leaderId = organizationItem.getLeaderId();
        if (!StringUtils.isBlank(leaderId)) {
            userDetailsItem.setLeaderId(leaderId);
            Optional.ofNullable(foxUserRepository.findOne(leaderId)).ifPresent(
                    leaderUser -> userDetailsItem.setLeaderName(leaderUser.getUserName())
            );
        }

        String tenantId = foxUser.getTenantId();
        if (!StringUtils.isBlank(tenantId)) {
            userDetailsItem.setEnterpriseId(tenantId);
            Optional.ofNullable(foxTenantRepository.findOne(tenantId)).ifPresent(
                    foxTenant -> {
                        userDetailsItem.setEnterpriseName(foxTenant.getName());
                        userDetailsItem.setEnterpriseType(foxTenant.getType());
                    }
            );
        }

        userDetailsItem.setIsAD(false);
        if (foxUser.getType() != null && foxUser.getType().byteValue() == AdUserMgmtConstants.USER_TYPE_AD) {
            userDetailsItem.setIsAD(true);
            ExtLdapUser ldapUser = extLdapUserRepository.findOneByUserId(foxUser.getId());
            ExtLdapSource ldap = extLdapSourceRepository.findOne(ldapUser.getLdapSourceId());
            userDetailsItem.setLdapId(ldapUser.getLdapSourceId());
            userDetailsItem.setLoginName(ldapUser.getLogin());
            userDetailsItem.setLdapName(ldap.getName());
        }
        return userDetailsItem;
    }

    @Override
    public UserDetailsItem loadUserByLoginNameOrId(String loginNameOrId){
        UserDetailsItem userDetailsItem = new UserDetailsItem();
        log.info(CommonConstant.LOG_DEBUG_TAG + "根据登录名或者ID获取用户信息：{}", loginNameOrId);
        FoxUser foxUser = foxUserRepository.findByLoginNameOrId(loginNameOrId.trim(), loginNameOrId.trim());
        if(null == foxUser){
            throw new NoRecordFoundException(String.format("user %s not exist.", loginNameOrId));
        }

        userDetailsItem.setUserId(foxUser.getId());
        userDetailsItem.setLoginName(foxUser.getLoginName());
        userDetailsItem.setPassword(foxUser.getPassword());
        userDetailsItem.setUserName(foxUser.getUserName());
        userDetailsItem.setEmail(foxUser.getEmail());
        userDetailsItem.setPhone(foxUser.getPhone());
        userDetailsItem.setStatus(foxUser.getStatus());
        userDetailsItem.setDescription(foxUser.getDescription());

        log.debug(CommonConstant.LOG_DEBUG_TAG + "根据登录名或者ID获取角色信息：{}", loginNameOrId);
        List<RoleItem> roleItems = new ArrayList<>();
        foxUserRoleRepository.findByUserId(foxUser.getId()).forEach(foxUserRole -> {
            FoxRole foxRole = foxRoleRepository.findOne(foxUserRole.getRoleId());
            RoleItem roleItem = new RoleItem();
            roleItem.setId(foxRole.getId());
            roleItem.setName(foxRole.getName());
            roleItem.setRoleName(foxRole.getRoleName());
            roleItem.setStatus(foxRole.getStatus());
            roleItems.add(roleItem);
        });
        userDetailsItem.setRoles(roleItems);

        log.debug(CommonConstant.LOG_DEBUG_TAG + "根据登录名或者ID获取机构/部门信息：{}", loginNameOrId);
        List<OrganizationItem> organizationItems = new ArrayList<>();
        foxUserOrganizationRepository.findByUserId(foxUser.getId()).forEach(foxUserOrganization -> {
            FoxOrganization foxOrganization = foxOrganizationRepository
                    .findOne(foxUserOrganization.getOrganizationId());
            OrganizationItem organizationItem = new OrganizationItem();
            organizationItem.setId(foxOrganization.getId());
            organizationItem.setName(foxOrganization.getName());
            organizationItem.setLeaderId(foxOrganization.getLeaderId());
            organizationItem.setStatus(foxOrganization.getStatus());
            organizationItems.add(organizationItem);
        });
        userDetailsItem.setOrganizations(organizationItems);

        OrganizationItem organizationItem = organizationItems.get(0);

        userDetailsItem.setOrganizationId(organizationItem.getId());
        userDetailsItem.setOrganizationName(organizationItem.getName());

        String leaderId = organizationItem.getLeaderId();
        if (!StringUtils.isBlank(leaderId)) {
            userDetailsItem.setLeaderId(leaderId);
            Optional.ofNullable(foxUserRepository.findOne(leaderId)).ifPresent(
                    leaderUser -> userDetailsItem.setLeaderName(leaderUser.getUserName())
            );
        }

        String tenantId = foxUser.getTenantId();
        if (!StringUtils.isBlank(tenantId)) {
            userDetailsItem.setEnterpriseId(tenantId);
            Optional.ofNullable(foxTenantRepository.findOne(tenantId)).ifPresent(
                    foxTenant -> {
                        userDetailsItem.setEnterpriseName(foxTenant.getName());
                        userDetailsItem.setEnterpriseType(foxTenant.getType());
                    }
            );
        }

        userDetailsItem.setIsAD(false);     //TODO 这里可能有问题，需要确认
        if (foxUser.getType() != null && foxUser.getType().byteValue() == AdUserMgmtConstants.USER_TYPE_AD) {
            userDetailsItem.setIsAD(true);
            ExtLdapUser ldapUser = extLdapUserRepository.findOneByUserId(foxUser.getId());
            ExtLdapSource ldap = extLdapSourceRepository.findOne(ldapUser.getLdapSourceId());
            userDetailsItem.setLdapId(ldapUser.getLdapSourceId());
            userDetailsItem.setLoginName(ldapUser.getLogin());
            userDetailsItem.setLdapName(ldap.getName());
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
        FoxUser foxUser = foxUserRepository.findByLoginNameAndType(loginName, AdUserMgmtConstants.USER_TYPE_LOCAL);
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
        Optional<FoxUser> optional = foxUserRepository.findByLoginName(loginName);
        if(!optional.isPresent()){
            log.warn(CommonConstant.LOG_DEBUG_TAG + "当前用户不存在：{}", loginName);
            foxUserItem.setUserType(CommonConstant.USER_NOT_FOUND);
        }else{
            FoxUser foxUser = optional.get();
            String tenantId = foxUser.getTenantId();
            foxUserItem.setPassword(foxUser.getPassword());
            if(StringUtils.isBlank(tenantId)){
                foxUserItem.setUserType(CommonConstant.USER_TYPE_PROVIDER);
            }else{
                foxUserItem.setUserType(CommonConstant.USER_TYPE_BUYER);
            }
        }
        generalContentResult.setResultContent(foxUserItem);
        log.info(CommonConstant.LOG_DEBUG_TAG + "通过登录名判断该用户是前台用户还是后台用户,结果：{}", generalContentResult);
        return generalContentResult;
    }
}
