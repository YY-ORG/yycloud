package com.yy.cloud.core.usermgmt.service.impl;

import com.gemii.lizcloud.cloudconnector.dto.BatchEnterpriseCreate;
import com.gemii.lizcloud.cloudconnector.dto.Enterprise;
import com.gemii.lizcloud.cloudconnector.dto.EnterpriseCreate;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.dto.accountcenter.LdapSourceProfile;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseProfile;
import com.yy.cloud.common.data.dto.platformmgmt.PlatformProfile;
import com.yy.cloud.common.data.enterpise.EnterpriseUsedQuota;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformItem;
import com.yy.cloud.common.data.otd.user.OrganizationResourceItem;
import com.yy.cloud.common.data.otd.user.UserDetailsItem;
import com.yy.cloud.common.data.usermgmt.TenantModel;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.core.usermgmt.clients.ConnectorClient;
import com.yy.cloud.core.usermgmt.clients.PlatformAccessClient;
import com.yy.cloud.core.usermgmt.data.domain.*;
import com.yy.cloud.core.usermgmt.data.repositories.*;
import com.yy.cloud.core.usermgmt.service.LdapMgmtService;
import com.yy.cloud.core.usermgmt.service.TenantService;
import com.yy.cloud.core.usermgmt.utils.BeanCopyHelper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guof on 2016/9/27.
 */
@Service
@Slf4j
public class TenantServiceImpl implements TenantService {

    @Autowired
    private FoxTenantRepository foxTenantRepository;
    
    @Autowired
    private FoxTenantPaltformRelationRepository foxTenantPaltformRelationRepository;

    @Autowired
    private FoxOrganizationRepository foxOrganizationRepository;

    @Autowired
    private FoxOrganizationResourceQuotaRepository foxOrganizationResourceQuotaRepository;

    @Autowired
    private FoxUserOrganizationRepository foxUserOrganizationRepository;

    @Autowired
    private PlatformAccessClient platformAccessClient;

    @Autowired
    private ConnectorClient connectorClient;

    /**
     * 用来获取当前登录用户信息
     */
    @Autowired
    private SecurityService securityService;

    @Autowired
    private LdapMgmtService ldapMgmtService;

    @Override
    @Transactional
    public FoxTenant createTenant(EnterpriseProfile profile) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "新建企业信息为：{}", profile);
        FoxTenant foxTenant = BeanCopyHelper.copyEnterpriseProfile2FoxTenant(profile);
        // foxTenant.setStatus(new Byte("0"));
        foxTenant.setStatus(CommonConstant.DIC_GLOBAL_STATUS_INITIAL);
        // 将企业类型设置为前台企业（服务购买方）
        foxTenant.setType(CommonConstant.DIC_TENANT_TYPE_BUYER);
        // 设置企业的创建者ID：TODO 等xuejin接口
        //foxTenant.setCreatorId(null);
        foxTenant.setCreatorId(securityService.getCurrentUser().getUserId());
        //todo 更新平台额度
        FoxTenant savedTenant = foxTenantRepository.save(foxTenant);
        createDefaultOrgForEnterprise(foxTenant);
        // 构建platformProfile List, 用于查询平台信息
        List<String> platformIdList = profile.getPlatformIdList();
        List<PlatformProfile> platformProfiles = new ArrayList<PlatformProfile>();
        platformIdList.forEach(id -> {
            PlatformProfile platformProfile = new PlatformProfile();
            platformProfile.setId(id);
            platformProfiles.add(platformProfile);
        });
        GeneralContentResult<List<PlatformItem>> result = platformAccessClient.findPlatformByIds(platformProfiles);
        List<PlatformItem> platformItems = result.getResultContent();
        List<String> openstackPlatformId = new ArrayList<String>();
        // 遍历查询到的平台列表，筛选出类型是OPENSTACK和HOS类型的平台，并将ID放入list中，用于调用connector接口创建project
        platformItems.forEach(platformItem -> {
            if(!CommonConstant.DIC_PLATFORM_TYPE_VMWARE.equals(platformItem.getPlatformType())){
                openstackPlatformId.add(platformItem.getId());
            }
        });
        
        //构建Tenant与Platform之间的联系
        platformIdList = profile.getPlatformIdList();
        for(String platformId : platformIdList){
        	FoxTenantPaltformRelation foxTenantPaltformRelation = new FoxTenantPaltformRelation();
        	foxTenantPaltformRelation.setPlatformId(platformId);
        	foxTenantPaltformRelation.setTenantId(foxTenant.getId());
        	foxTenantPaltformRelation.setCreateDate(new Timestamp(System.currentTimeMillis()));
        	
        	foxTenantPaltformRelationRepository.save(foxTenantPaltformRelation);
        }
        
        

        // 构建connector接口入参
        EnterpriseCreate enterpriseCreate = new EnterpriseCreate();
        enterpriseCreate.setName(profile.getName());
        enterpriseCreate.setEnterpriseId(savedTenant.getId());
        BatchEnterpriseCreate batchEnterpriseCreate = new BatchEnterpriseCreate();
        batchEnterpriseCreate.setEnterprise(enterpriseCreate);
        batchEnterpriseCreate.setPlatforms(openstackPlatformId);

        log.debug(CommonConstant.LOG_DEBUG_TAG + "开始调用connector接口创建project：{}", batchEnterpriseCreate);

        List<Enterprise> enterpriseList = connectorClient.createEnterpriseBatch(batchEnterpriseCreate);

        if(profile.getAuthenticationMode() == CommonConstant.DIC_AUTHENTICATION_MODE_LDAP) {
            // 创建企业认证源：
            LdapSourceProfile ldapSourceProfile = new LdapSourceProfile();
            ldapSourceProfile.setEnterpriseId(foxTenant.getId());
            ldapSourceProfile.setUrl(profile.getUrl());
            ldapSourceProfile.setName(profile.getAdname());
            ldapSourceProfile.setUserName(profile.getUserName());
            ldapSourceProfile.setPassword(profile.getPassword()); // AD认证源明文保存密码
            ldapSourceProfile.setLoginProp(profile.getLoginProp());
            ldapSourceProfile.setDesc(profile.getAdDescription());

            log.debug(CommonConstant.LOG_DEBUG_TAG + "开始创建企业认证源：{}" + ldapSourceProfile);
            ldapMgmtService.createLdapSource(ldapSourceProfile);
        }
        log.debug(CommonConstant.LOG_DEBUG_TAG + "新建企业完毕, 返回结果：{}", enterpriseList);
        return savedTenant;
    }
    
    //获取某个平台下所有的企业信息
    @Override
    public List<EnterpriseProfile> getEnterprisesByPlatformId(String _platformId){
    	List<EnterpriseProfile> enterpriseProfiles = new ArrayList<EnterpriseProfile>();
    	//获取平台下包含的企业ID列表
    	List<String> enterpriseId = findEnterpriseIdByPlatformId(_platformId);
    	//通过企业ID获取企业信息
    	List<EnterpriseProfile> enterprises = new ArrayList<EnterpriseProfile>();
    	for(String entId : enterpriseId){
    		FoxTenant enterprise = foxTenantRepository.findOne(entId);
    		EnterpriseProfile enterpriseProfile = new EnterpriseProfile();
    		
    		enterpriseProfile.setAuthenticationMode(enterprise.getAuthmode());
    		enterpriseProfile.setCode(enterprise.getCode());
    		enterpriseProfile.setDescription(enterprise.getDescription());
    		enterpriseProfile.setId(enterprise.getId());
    		//获取企业图片enterpriseProfile.setImageId(enterprise.get);
    		enterpriseProfile.setName(enterprise.getName());
    		enterpriseProfile.setUrl(enterprise.getUrl());
    		enterpriseProfile.setSslEnable(enterprise.getSslEnable());
    		
    		enterpriseProfiles.add(enterpriseProfile);
    	}
    	
    	return enterpriseProfiles;
    }
    
    //通过平台获取企业ID
    @Override
	public List<String> findEnterpriseIdByPlatformId(String _platformId){
    	List<String> enterpriseId = new ArrayList<String>();
    	
    	List<FoxTenantPaltformRelation> tenantPlatformRelations = foxTenantPaltformRelationRepository.findByPlatformId(_platformId);
    	String tenantId = new String();
    	for(FoxTenantPaltformRelation tenantPlatformRelation:tenantPlatformRelations){
    		tenantId = tenantPlatformRelation.getTenantId();
    		enterpriseId.add(tenantId);
    	}
    	return enterpriseId;
    }

    @Override
    public FoxTenant updateTenant(TenantModel tenantModel) {
        FoxTenant foxTenant = foxTenantRepository.findOne(tenantModel.getId());

        foxTenant.setName(tenantModel.getName());
        foxTenant.setContactor(tenantModel.getAdmin());
        foxTenant.setPhone(tenantModel.getPhone());
        foxTenant.setEmail(tenantModel.getEmail());
        foxTenant.setDescription(tenantModel.getDescription());

        FoxTenant savedTenant = foxTenantRepository.saveAndFlush(foxTenant);
        return savedTenant;
    }

    @Override
    public List<FoxTenant> findAll() {
        return foxTenantRepository.findAll();
    }

    /**
     * 查询当前登录用户所属机构下的所有企业
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<FoxTenant> findAllByPaging(Integer page, Integer size) {
        //PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC, "CREATE_DATE");
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC, "createDate");
        //PageRequest pageRequest = new PageRequest(page, size);

        UserDetailsItem userDetailsItem = securityService.getCurrentUser();
        log.debug(CommonConstant.LOG_DEBUG_TAG + "获取当前用户信息：{}", userDetailsItem);
        List<FoxUserOrganization> userOrganizations =  foxUserOrganizationRepository.findByOrganizationId(userDetailsItem.getOrganizationId());
        List<String> userIdList = new ArrayList<String>();
        userOrganizations.forEach(foxUserOrganization -> {
            userIdList.add(foxUserOrganization.getUserId());
        });
        log.debug("根据当前登录用户ID查询企业：{}", userIdList );
        Page<FoxTenant> pageTenant = foxTenantRepository.findByCreatorIdIn(userIdList, pageRequest);
        //Page<FoxTenant> pageTenant = foxTenantRepository.findTenantList(userIdList, pageRequest);
        //Page<FoxTenant> pageTenant = foxTenantRepository.findTenantList(userIdList, pageRequest);
        log.debug(CommonConstant.LOG_DEBUG_TAG + "获取当前用户（后台）所属机构下所有企业：{}", pageTenant);

        return pageTenant;
        //return foxTenantRepository.findAll(pageRequest);
    }

    @Override
    public FoxTenant findById(String tenantId) {
        return foxTenantRepository.findOne(tenantId);
    }

    @Override
    public void updateStatus(String _tenantId, Byte _status) {
        FoxTenant foxTenant = foxTenantRepository.findOne(_tenantId);
        foxTenant.setStatus(_status);
        foxTenantRepository.saveAndFlush(foxTenant);
    }

    @Override
    public void updateStatusByBatch(List<EnterpriseProfile> _enterprises, Byte _status) {
        _enterprises.forEach(
                _enterprise -> {
                    FoxTenant foxTenant = foxTenantRepository.findOne(_enterprise.getId());
                    if (foxTenant != null) {
                        foxTenant.setStatus(_status);
                        foxTenantRepository.saveAndFlush(foxTenant);
                    }
                }
        );

    }

    public GeneralContentResult<EnterpriseUsedQuota> getAllOrganUsedQuotaByTenantId(String tenantId){
        GeneralContentResult<EnterpriseUsedQuota> generalContentResult = new GeneralContentResult<EnterpriseUsedQuota>();
        generalContentResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        // 返回参数
        EnterpriseUsedQuota enterpriseUsedQuota = new EnterpriseUsedQuota();
        List<FoxOrganization> foxOrganizationList = foxOrganizationRepository.findByTenantId(tenantId);
        int usedCpu = 0;
        int usedMem = 0;;
        int usedPhysical = 0;
        int usedDisk = 0;
        int usedStorage = 0;
        int usedSnapshot = 0;
        int usedImage = 0;
        int usedIpaddress = 0;
        int usedVm = 0;
        int usedNetwork = 0 ;

        List<OrganizationResourceItem> organizationResourceItemList = new ArrayList<OrganizationResourceItem>();
        if(CollectionUtils.isNotEmpty(foxOrganizationList)) {
            // 遍历机构列表，并取得机构资源信息
            for (FoxOrganization org : foxOrganizationList) {
                FoxOrganizationResourceQuota quota = foxOrganizationResourceQuotaRepository.findByOrganizationId(org.getId());
                if (null != quota) {
                    usedCpu = usedCpu + getQuotaValue(quota.getUsedCpu());
                    usedMem = usedMem + getQuotaValue(quota.getUsedMem());
                    usedPhysical = usedPhysical + getQuotaValue(quota.getUsedPhysical());
                    usedDisk = usedDisk + getQuotaValue(quota.getUsedDisk());
                    usedStorage = usedStorage + getQuotaValue(quota.getUsedStorage());
                    usedSnapshot = usedSnapshot + getQuotaValue(quota.getUsedSnapshot());
                    usedImage = usedImage + getQuotaValue(quota.getImage());
                    usedIpaddress = usedIpaddress + getQuotaValue(quota.getUsedIpaddress());
                    usedVm = usedVm + getQuotaValue(quota.getUsedVm());
                    usedNetwork = usedNetwork + getQuotaValue(quota.getUsedNetwork());
                    OrganizationResourceItem resourceItem =  new OrganizationResourceItem();
                    BeanUtils.copyProperties(quota, resourceItem);
                    organizationResourceItemList.add(resourceItem);
                }
            }
            enterpriseUsedQuota.setOrganizationResourceItemList(organizationResourceItemList);
            enterpriseUsedQuota.setTenantId(tenantId);
            enterpriseUsedQuota.setUsedCpu(usedCpu);
            enterpriseUsedQuota.setUsedDisk(usedDisk);
            enterpriseUsedQuota.setUsedPhysical(usedPhysical);
            enterpriseUsedQuota.setUsedMem(usedMem);
            enterpriseUsedQuota.setUsedStorage(usedStorage);
            enterpriseUsedQuota.setUsedImage(usedImage);
            enterpriseUsedQuota.setUsedSnapshot(usedSnapshot);
            enterpriseUsedQuota.setUsedIpaddress(usedIpaddress);
            enterpriseUsedQuota.setUsedVm(usedVm);
            enterpriseUsedQuota.setUsedNetwork(usedNetwork);
        }
        generalContentResult.setResultContent(enterpriseUsedQuota);
        log.debug(CommonConstant.LOG_DEBUG_TAG + "返回该企业下所有部门已使用资源总和：{}", generalContentResult);
        return generalContentResult;
    }

    /**
     * 获取value，判断入参是否为null，如果null则返回0
     * @param i
     * @return
     */
    private int getQuotaValue(Integer i){
        int result = 0;
        if(null == i){
            return 0;
        }else{
            return result + i;
        }
    }


    private void createDefaultOrgForEnterprise(FoxTenant foxTenant){
        FoxOrganization foxOrganization= new FoxOrganization();
        foxOrganization.setTenantId(foxTenant.getId());
        foxOrganization.setCode(CommonConstant.DEFAULT_ADMIN_DEPARTMENT_OF_ENTERPRISE_CODE);
        foxOrganization.setStatus(Byte.valueOf("1"));
        foxOrganization.setName(CommonConstant.DEFAULT_ADMIN_DEPARTMENT_OF_ENTERPRISE_NAME);
        foxOrganization.setType(CommonConstant.DEPARTMENT_TYPE);
        foxOrganizationRepository.save(foxOrganization);
        foxOrganization= new FoxOrganization();
        foxOrganization.setTenantId(foxTenant.getId());
        foxOrganization.setCode(CommonConstant.DEFAULT_NORMAL_DEPARTMENT_OF_ENTERPRISE_CODE);
        foxOrganization.setStatus(Byte.valueOf("1"));
        foxOrganization.setName(CommonConstant.DEFAULT_NORMAL_DEPARTMENT_OF_ENTERPRISE_NAME);
        foxOrganization.setType(CommonConstant.DEPARTMENT_TYPE);
        foxOrganizationRepository.save(foxOrganization);
    }
}
