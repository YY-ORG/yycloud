package com.yy.cloud.core.usermgmt.service.impl;

import com.sun.corba.se.spi.orbutil.fsm.Guard;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.constant.UserConstant;
import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.dto.accountcenter.OrganizationProfile;
import com.yy.cloud.common.data.dto.accountcenter.OrganizationResourceProfile;
import com.yy.cloud.common.data.dto.enterprise.ResourceQuotaUsageDetailProfile;
import com.yy.cloud.common.data.dto.platformmgmt.PlatformProfile;
import com.yy.cloud.common.data.dto.usermgmt.OrganizationResourceQuotaUsageProfile;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseQuotaDetailResp;
import com.yy.cloud.common.data.otd.organization.OrganizationItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformItem;
import com.yy.cloud.common.data.otd.user.OrganizationResourceItem;
import com.yy.cloud.common.data.otd.user.UserDetailsItem;
import com.yy.cloud.common.data.otd.user.UserItem;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationQuotaResp;
import com.yy.cloud.common.exception.NoRecordFoundException;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.common.utils.AssertHelper;
import com.yy.cloud.core.usermgmt.clients.PlatformAccessClient;
import com.yy.cloud.core.usermgmt.clients.ResourceClient;
import com.yy.cloud.core.usermgmt.constant.ResourceTypeConstant;
import com.yy.cloud.core.usermgmt.data.domain.*;
import com.yy.cloud.core.usermgmt.data.repositories.*;
import com.yy.cloud.core.usermgmt.service.OrgnizationService;
import com.yy.cloud.core.usermgmt.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Created by guof on 2016/10/27.
 */
@Service
@Slf4j
public class OrgnizationServiceImpl implements OrgnizationService {

    @Autowired
    private FoxOrganizationRepository foxOrganizationRepository;

    @Autowired
    private FoxUserRepository foxUserRepository;

    @Autowired
    private FoxUserOrganizationRepository foxUserOrganizationRepository;

    @Autowired
    private FoxOrganizationPlatformMapRepository foxOrganizationPlatformMapRepository;

    @Autowired
    private FoxOrganizationResourceQuotaRepository foxOrganizationResourceQuotaRepository;

    @Autowired
    private FoxTenantRepository foxTenantRepository;

    @Autowired
    private ModelMapper modelMapp;

    @Autowired
    private PlatformAccessClient platformAccessClient;

    @Autowired
    private ResourceClient resourceClient;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    private static Byte QUOTA_USE = new Byte("0");  // 使用资源
    private static Byte QUOTA_RELEASE = new Byte("1");  //释放资源

    @Override
    @Transactional
    public GeneralContentResult<String> createOrgnization(OrganizationProfile _organizationProfile) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "创建机构:{}", _organizationProfile);
        // 返回值
        GeneralContentResult<String> generalContentResult = new GeneralContentResult<>();
        generalContentResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        FoxOrganization foxOrganization = modelMapp.map(_organizationProfile, FoxOrganization.class);
        foxOrganization.setStatus(CommonConstant.DIC_GLOBAL_STATUS_INITIAL);

        // 创建机构的时候指定一个tenant ID, 等学进的登录用户完成后再获取登录用户的tenantId --WXDOK
        UserDetailsItem userDetailsItem = securityService.getCurrentUser();
        log.debug(CommonConstant.LOG_DEBUG_TAG + "获取当前登录用户：{}" +userDetailsItem);
        // 根据当前登录用户，判断
        // foxOrganization.setType();
        //String USER_ID = userDetailsItem.getUserId();
        //UserDetailsItem userDetailsItem = userService.loadUserByUserId(USER_ID);
        String enterpriseId = userDetailsItem.getEnterpriseId();
        //FoxTenant foxTenant = foxTenantRepository.getOne(enterpriseId);
        Byte tenantType = userDetailsItem.getEnterpriseType();

        // 如果是后台机构，则不需要设置配额
        if(CommonConstant.DIC_TENANT_TYPE_BUYER.equals(tenantType)){
            //前台企业的部门才设置tenantId,后台机构不设置
            foxOrganization.setTenantId(enterpriseId);
            // 类型设置为前台部门
            foxOrganization.setType(CommonConstant.DEPARTMENT_TYPE);
            // 修改业务逻辑：调用企业资源接口(贾薇)修改企业配额：验证通过后则创建企业配额
            // 先验证企业资源额度是否足够，成功后再创建部门，否则返回失败：
            FoxOrganizationResourceQuota db = new FoxOrganizationResourceQuota();   //为了和修改共用一个方法，这里创建一个对象，不会影响实际的quota值
            List<ResourceQuotaUsageDetailProfile> inputEnterpriseUsedQuota = buildEnterpriseQuotaInputParam(_organizationProfile.getResource(),db, QUOTA_RELEASE, enterpriseId);
            GeneralContentResult<ResourceQuotaUsageDetailProfile> tenantResourceResult = resourceClient.validateAndUpdateEnterpriseUsedQuota(inputEnterpriseUsedQuota);
            if(!ResultCode.OPERATION_SUCCESS.equals(tenantResourceResult.getResultCode())){
                log.debug(CommonConstant.LOG_DEBUG_TAG + "创建组织，调用接口校验和修改企业资源返回错误：{},组织创建失败", tenantResourceResult.getResultCode());
                generalContentResult.setResultCode(ResultCode.USERMGMT_ORGAN_CREATE_FAILED);
                return generalContentResult;
            }

            // 保存部门配额
            FoxOrganizationResourceQuota foxOrganizationResourceQuota = new FoxOrganizationResourceQuota();
            foxOrganizationResourceQuota.setOrganizationId(foxOrganization.getId());

            OrganizationResourceProfile inputResource = _organizationProfile.getResource();
            if(null != inputResource){
                BeanUtils.copyProperties(inputResource, foxOrganizationResourceQuota);
            }

            log.debug(CommonConstant.LOG_DEBUG_TAG + "创建机构，保存机构配额信息：{}", foxOrganizationResourceQuota);
            foxOrganizationResourceQuotaRepository.save(foxOrganizationResourceQuota);
            log.debug(CommonConstant.LOG_DEBUG_TAG + "创建机构，保存机构配额结束：{}", foxOrganization.getId());

            log.debug(CommonConstant.LOG_DEBUG_TAG + "创建组织，调用接口校验和修改企业配额资源成功：{},开始创建组织", tenantResourceResult.getResultCode());
        }else{
            // 后台用户，将创建的机构类型是指为0
            foxOrganization.setType(CommonConstant.ORGANIZATION_TYPE);
        }

        foxOrganizationRepository.save(foxOrganization);

        // 机构成员列表
        if (!CollectionUtils.isEmpty(_organizationProfile.getMembers())) {
            _organizationProfile.getMembers().forEach(
                    accountProfile -> {
                        FoxUser foxUser = Optional.ofNullable(foxUserRepository.findOne(accountProfile.getId()))
                                .orElseThrow(() -> new NoRecordFoundException("user " + accountProfile.getId() + " not exist."));

                        FoxUserOrganization foxUserOrganization = new FoxUserOrganization();
                        foxUserOrganization.setOrganizationId(foxOrganization.getId());
                        foxUserOrganization.setUserId(foxUser.getId());

                        foxUserOrganizationRepository.save(foxUserOrganization);
                    }
            );
        }
        log.debug(CommonConstant.LOG_DEBUG_TAG + "创建机构：保存机构成员列表成功");

        // 机构可管理平台列表
        if (!CollectionUtils.isEmpty(_organizationProfile.getPlatforms())) {
            _organizationProfile.getPlatforms().forEach(
                    platformProfile -> {
                        FoxOrganizationPlatformMap foxOrganizationPlatformMap = new FoxOrganizationPlatformMap();
                        foxOrganizationPlatformMap.setOrganizationId(foxOrganization.getId());
                        foxOrganizationPlatformMap.setPlatformId(platformProfile.getId());
                        foxOrganizationPlatformMapRepository.save(foxOrganizationPlatformMap);
                    }
            );
        }
        log.debug(CommonConstant.LOG_DEBUG_TAG + "创建机构：保存机构可管理平台列表成功");

        generalContentResult.setResultContent(foxOrganization.getId());
        return generalContentResult;
    }

    private List<ResourceQuotaUsageDetailProfile> buildEnterpriseQuotaInputParam(OrganizationResourceProfile inputResource, FoxOrganizationResourceQuota db, Byte actionType, String tenantId){
        List<ResourceQuotaUsageDetailProfile> result = new ArrayList<ResourceQuotaUsageDetailProfile>();

        // cpu
        ResourceQuotaUsageDetailProfile cpuQuota = new ResourceQuotaUsageDetailProfile();
        cpuQuota.setUsageValue(getValue(inputResource.getVcpu()) - getValue(db.getVcpu()));
        cpuQuota.setType(new Byte("0"));
        result.add(cpuQuota);

        // mem
        ResourceQuotaUsageDetailProfile memQuota = new ResourceQuotaUsageDetailProfile();
        memQuota.setUsageValue(getValue(inputResource.getMem()) - getValue(db.getMem()));
        memQuota.setType(new Byte("1"));
        result.add(memQuota);

        // disk
        ResourceQuotaUsageDetailProfile diskQuota = new ResourceQuotaUsageDetailProfile();
        diskQuota.setUsageValue(getValue(inputResource.getDisk()) - getValue(db.getDisk()));
        diskQuota.setType(new Byte("2"));
        result.add(diskQuota);

        // storage
        ResourceQuotaUsageDetailProfile storageQuota = new ResourceQuotaUsageDetailProfile();
        storageQuota.setUsageValue(getValue(inputResource.getStorage()) - getValue(db.getStorage()));
        storageQuota.setType(new Byte("3"));
        result.add(storageQuota);

        // vm
        ResourceQuotaUsageDetailProfile vmQuota = new ResourceQuotaUsageDetailProfile();
        vmQuota.setUsageValue(getValue(inputResource.getVm()) - getValue(db.getVm()));
        vmQuota.setType(new Byte("4"));
        result.add(vmQuota);

        // network
        ResourceQuotaUsageDetailProfile networkQuota = new ResourceQuotaUsageDetailProfile();
        networkQuota.setUsageValue(getValue(inputResource.getNetwork()) - getValue(db.getNetwork()));
        networkQuota.setType(new Byte("5"));
        result.add(networkQuota);

        // snapshot
        ResourceQuotaUsageDetailProfile snapshotQuota = new ResourceQuotaUsageDetailProfile();
        snapshotQuota.setUsageValue(getValue(inputResource.getSnapshot()) - getValue(db.getSnapshot()));
        snapshotQuota.setType(new Byte("6"));
        result.add(snapshotQuota);

        // image
        ResourceQuotaUsageDetailProfile imageQuota = new ResourceQuotaUsageDetailProfile();
        imageQuota.setUsageValue(getValue(inputResource.getImage()) - getValue(db.getImage()));
        imageQuota.setType(new Byte("7"));
        result.add(imageQuota);

        // physical
        ResourceQuotaUsageDetailProfile physicalQuota = new ResourceQuotaUsageDetailProfile();
        physicalQuota.setUsageValue(getValue(inputResource.getPhysical()) - getValue(db.getPhysical()));
        physicalQuota.setType(new Byte("8"));
        result.add(physicalQuota);

        // ipaddress
        ResourceQuotaUsageDetailProfile ipQuota = new ResourceQuotaUsageDetailProfile();
        ipQuota.setUsageValue(getValue(inputResource.getIpaddress()) - getValue(db.getIpaddress()));
        ipQuota.setType(new Byte("9"));
        result.add(ipQuota);

        for(ResourceQuotaUsageDetailProfile profile:result){
            profile.setTenantId(tenantId);
            profile.setActionType(actionType);
        }

        return result;
    }

    /**
     * 判断各种资源修改额度是否小于已使用额度，如果有小于的项则返回失败
     * @param inputResource
     * @param db
     * @return
     */
    private String checkInputQuota(OrganizationResourceProfile inputResource, FoxOrganizationResourceQuota db){
        if((getValue(inputResource.getVcpu()) - getValue(db.getUsedCpu())) < 0){
            return ResultCode.USERMGMT_ORGAN_CREATE_FAILED;
        }
        if((getValue(inputResource.getMem()) - getValue(db.getUsedMem())) < 0){
            return ResultCode.USERMGMT_ORGAN_CREATE_FAILED;
        }
        if((getValue(inputResource.getDisk()) - getValue(db.getUsedDisk())) < 0){
            return ResultCode.USERMGMT_ORGAN_CREATE_FAILED;
        }
        if((getValue(inputResource.getStorage()) - getValue(db.getUsedStorage())) < 0){
            return ResultCode.USERMGMT_ORGAN_CREATE_FAILED;
        }
        if((getValue(inputResource.getVm()) - getValue(db.getUsedVm())) < 0){
            return ResultCode.USERMGMT_ORGAN_CREATE_FAILED;
        }
        if((getValue(inputResource.getNetwork()) - getValue(db.getUsedNetwork())) < 0){
            return ResultCode.USERMGMT_ORGAN_CREATE_FAILED;
        }
        if((getValue(inputResource.getSnapshot()) - getValue(db.getUsedSnapshot())) < 0){
            return ResultCode.USERMGMT_ORGAN_CREATE_FAILED;
        }
        if((getValue(inputResource.getImage()) - getValue(db.getUsedImage())) < 0){
            return ResultCode.USERMGMT_ORGAN_CREATE_FAILED;
        }
        if((getValue(inputResource.getPhysical()) - getValue(db.getPhysical())) < 0){
            return ResultCode.USERMGMT_ORGAN_CREATE_FAILED;
        }
        if((getValue(inputResource.getIpaddress()) - getValue(db.getUsedIpaddress())) < 0){
            return ResultCode.USERMGMT_ORGAN_CREATE_FAILED;
        }
        return ResultCode.OPERATION_SUCCESS;
    }

    private int getValue(Integer i){
        if(null == i){
            return 0;
        }else{
            return i;
        }
    }

    @Override
    @Transactional
    public GeneralResult updateOrgnization(String _organizationId, OrganizationProfile _organizationProfile) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "修改组织，ID:{}, profile:{}", _organizationId, _organizationProfile);
        GeneralResult generalResult = new GeneralResult();
        generalResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        FoxOrganization foxOrganization = foxOrganizationRepository.findOne(_organizationId);
        log.info(CommonConstant.LOG_DEBUG_TAG + "修改机构/部门：{}", foxOrganization);

        // 前台部门才更新额度
        if(CommonConstant.DEPARTMENT_TYPE == foxOrganization.getType()){
            // 查询数据库中当前组织配额
            FoxOrganizationResourceQuota foxOrganizationResourceQuota = foxOrganizationResourceQuotaRepository.findByOrganizationId(_organizationId);
            log.info(CommonConstant.LOG_DEBUG_TAG + "前台部门开始修改部门配额：{}", foxOrganization);
            // 校验修改的配额是否小于已经使用的额度，如果小于，则返回错误
            String checkUsedandUsage = checkInputQuota(_organizationProfile.getResource(), foxOrganizationResourceQuota);
            if(!ResultCode.OPERATION_SUCCESS.equals(checkUsedandUsage)){
                generalResult.setResultCode(checkUsedandUsage);
                return generalResult;
            }

            log.info(CommonConstant.LOG_DEBUG_TAG + "前台部门修改配额，已使用额度校验通过：{}", foxOrganization);

            // 修改业务逻辑：调用企业资源接口(贾薇)修改企业配额：验证通过后则创建企业配额
            // 先验证企业资源额度是否足够，成功后再创建部门，否则返回失败：
            List<ResourceQuotaUsageDetailProfile> inputEnterpriseUsedQuota = buildEnterpriseQuotaInputParam(_organizationProfile.getResource(),foxOrganizationResourceQuota, QUOTA_RELEASE, foxOrganization.getTenantId());
            GeneralContentResult<ResourceQuotaUsageDetailProfile> tenantResourceResult = resourceClient.validateAndUpdateEnterpriseUsedQuota(inputEnterpriseUsedQuota);
            if(!ResultCode.OPERATION_SUCCESS.equals(tenantResourceResult.getResultCode())){
                log.debug(CommonConstant.LOG_DEBUG_TAG + "修改组织，调用接口校验和修改企业资源返回错误：{},修改组织失败", tenantResourceResult.getResultCode());
                generalResult.setResultCode(ResultCode.USERMGMT_ORGAN_CREATE_FAILED);
                return generalResult;
            }

            log.debug(CommonConstant.LOG_DEBUG_TAG + "修改组织，调用接口校验和修改企业配额资源成功：{},开始修改组织", tenantResourceResult.getResultCode());

            if(null != _organizationProfile.getResource()) {
                BeanUtils.copyProperties(_organizationProfile.getResource(), foxOrganizationResourceQuota);
            }
            foxOrganizationResourceQuotaRepository.save(foxOrganizationResourceQuota);
        }

        log.debug(CommonConstant.LOG_DEBUG_TAG + "修改组织，开始修改组织下的成员列表,新的成员列表：{}", _organizationProfile.getMembers());
        // 机构成员列表
        List<FoxUserOrganization> foxUserOrganizations = foxUserOrganizationRepository.findByOrganizationId(_organizationId);
        foxUserOrganizationRepository.deleteInBatch(foxUserOrganizations);

        if (!CollectionUtils.isEmpty(_organizationProfile.getMembers())) {
            _organizationProfile.getMembers().forEach(
                    accountProfile -> {
                        FoxUserOrganization foxUserOrganization = new FoxUserOrganization();
                        foxUserOrganization.setOrganizationId(_organizationId);
                        foxUserOrganization.setUserId(accountProfile.getId());
                        foxUserOrganizationRepository.save(foxUserOrganization);
                    }
            );
        }

        log.debug(CommonConstant.LOG_DEBUG_TAG + "修改组织，开始修改组织下的平台,新的平台列表：{}", _organizationProfile.getPlatforms());
        // 机构可管理平台列表
        List<FoxOrganizationPlatformMap> foxOrganizationPlatformMaps = foxOrganizationPlatformMapRepository.findByOrganizationId(_organizationId);
        foxOrganizationPlatformMapRepository.deleteInBatch(foxOrganizationPlatformMaps);

        if (!CollectionUtils.isEmpty(_organizationProfile.getPlatforms())) {
            _organizationProfile.getPlatforms().forEach(
                    platformProfile -> {
                        FoxOrganizationPlatformMap foxOrganizationPlatformMap = new FoxOrganizationPlatformMap();
                        foxOrganizationPlatformMap.setOrganizationId(_organizationId);
                        foxOrganizationPlatformMap.setPlatformId(platformProfile.getId());
                        foxOrganizationPlatformMapRepository.save(foxOrganizationPlatformMap);
                    }
            );
        }

        foxOrganization.setName(_organizationProfile.getName());
        foxOrganization.setDescription(_organizationProfile.getDescription());
        foxOrganization.setLeaderId(_organizationProfile.getLeaderId());
        foxOrganizationRepository.save(foxOrganization);
        log.debug(CommonConstant.LOG_DEBUG_TAG + "修改组织成功:{}", _organizationProfile);

        return generalResult;
    }

    /**
     * 校验组织下是否有已使用资源
     * @param quota
     * @return
     */
    private boolean checkUsedQuota(FoxOrganizationResourceQuota quota){
        Field[] quotaFileds = quota.getClass().getDeclaredFields();
        for(Field field:quotaFileds){
            field.setAccessible(true);
            try {
                if(field.getName().contains("used") && getValue((Integer)field.get(quota)) > 0){
                    // Integer value = (Integer)field.get(quota);
                    log.info(CommonConstant.LOG_DEBUG_TAG + "删除组织失败，该类型资源未全部释放：{}", field.getName());
                    return true;
                }
            } catch (IllegalAccessException e) {
                log.info(CommonConstant.LOG_DEBUG_TAG + "删除组织失败：{}", e);
                return true;
            }
        }

        return false;
    }

    @Override
    @Transactional
    public GeneralResult deleteOrgnization(String _organizationId) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "删除组织：{}", _organizationId);
        GeneralResult generalResult = new GeneralResult();
        generalResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        // 查询当前机构
        FoxOrganization foxOrganization = foxOrganizationRepository.findOne(_organizationId);

        if((CommonConstant.DEPARTMENT_TYPE == foxOrganization.getType())){
            log.info(CommonConstant.LOG_DEBUG_TAG + "这是后台机构:{}，或者后台用户，删除时不用校验额度", foxOrganization);
            // 调用企业接口，释放企业可用配额
            FoxOrganizationResourceQuota foxOrganizationResourceQuota = foxOrganizationResourceQuotaRepository.findByOrganizationId(_organizationId);

            // 校验是否有已经使用的资源，如果有则不能删除组织
            if(checkUsedQuota(foxOrganizationResourceQuota)){
                log.debug(CommonConstant.LOG_DEBUG_TAG + "删除组织，该组织有已使用的资源，不能删除，必须先释放所有资源:{}", foxOrganizationResourceQuota);
                generalResult.setResultCode(ResultCode.USERMGMT_ORGAN_CREATE_FAILED);
                return generalResult;
            }

            OrganizationResourceProfile organizationResourceProfile = new OrganizationResourceProfile();
            List<ResourceQuotaUsageDetailProfile> inputEnterpriseUsedQuota = buildEnterpriseQuotaInputParam(organizationResourceProfile,foxOrganizationResourceQuota, QUOTA_RELEASE, foxOrganization.getTenantId());
            log.debug(CommonConstant.LOG_DEBUG_TAG + "删除组织，调用企业接口，：{}", _organizationId);
            GeneralContentResult<ResourceQuotaUsageDetailProfile> tenantResourceResult = resourceClient.validateAndUpdateEnterpriseUsedQuota(inputEnterpriseUsedQuota);
            if(!ResultCode.OPERATION_SUCCESS.equals(tenantResourceResult.getResultCode())){
                log.debug(CommonConstant.LOG_DEBUG_TAG + "删除组织，调用接口校验和修改企业资源返回错误：{},删除组织失败", tenantResourceResult.getResultCode());
                generalResult.setResultCode(ResultCode.USERMGMT_ORGAN_CREATE_FAILED);
                return generalResult;
            }

            log.debug(CommonConstant.LOG_DEBUG_TAG + "删除组织，调用接口校验和修改企业资源返回成功：{}", tenantResourceResult.getResultCode());

            // 清除组织配额
            foxOrganizationResourceQuotaRepository.delete(foxOrganizationResourceQuota);
        }


        // 清除机构成员列表
        List<FoxUserOrganization> foxUserOrganizations = foxUserOrganizationRepository.findByOrganizationId(_organizationId);
        foxUserOrganizationRepository.deleteInBatch(foxUserOrganizations);

        // 清除机构可管理平台列表
        List<FoxOrganizationPlatformMap> foxOrganizationPlatformMaps
                = foxOrganizationPlatformMapRepository.findByOrganizationId(_organizationId);
        foxOrganizationPlatformMapRepository.deleteInBatch(foxOrganizationPlatformMaps);

        log.debug(CommonConstant.LOG_DEBUG_TAG + "删除组织/机构成功：{}", _organizationId);
        foxOrganizationRepository.delete(_organizationId);
        return generalResult;
    }

    @Override
    public void updateOrgnizationStatus(String _organizationId, Byte _status) {
        FoxOrganization foxOrganization = Optional.ofNullable(foxOrganizationRepository.findOne(_organizationId))
                .orElseThrow(() -> new NoRecordFoundException("organization " + _organizationId + " not exist."));
        foxOrganization.setStatus(_status);
        foxOrganizationRepository.save(foxOrganization);
    }

    @Override
    public List<OrganizationItem> listOrganizationsByPage(PageInfo _pageInfo, Byte _status) {
        PageRequest pageRequest = new PageRequest(_pageInfo.getCurrentPage(), _pageInfo.getPageSize(), Sort.Direction.ASC, "id");

        // 后台登录用户
        Page<FoxOrganization> foxOrganizations;
        if(userService.isProviderUser()){
            if (null == _status) {
                // foxOrganizations = foxOrganizationRepository.findAll(pageRequest);
                // 查询后台机构，过滤掉已删除状态的机构
                foxOrganizations = foxOrganizationRepository.findByTypeAndStatusLessThan(CommonConstant.ORGANIZATION_TYPE,CommonConstant.DIC_GLOBAL_STATUS_DELETED, pageRequest);
            } else {
                // 查询状态为_status的后台机构
                foxOrganizations = foxOrganizationRepository.findByTypeAndStatus(CommonConstant.ORGANIZATION_TYPE, _status, pageRequest);
            }
        }else{
            // 前台用户
            UserDetailsItem userDetailsItem = securityService.getCurrentUser();
            if (null == _status) {
                // foxOrganizations = foxOrganizationRepository.findAll(pageRequest);
                // 根据企业ID查询部门，过滤掉已删除状态的部门
                foxOrganizations = foxOrganizationRepository.findByTenantIdAndStatusLessThan(userDetailsItem.getEnterpriseId(),CommonConstant.DIC_GLOBAL_STATUS_DELETED, pageRequest);
            } else {
                // 根据企业ID查询部门，切状态为_status的部门
                foxOrganizations = foxOrganizationRepository.findByTenantIdAndStatus(userDetailsItem.getEnterpriseId(), _status, pageRequest);
            }
        }

        List<OrganizationItem> organizationItems = new ArrayList<>();
        foxOrganizations.forEach(
                foxOrganization -> {
                    OrganizationItem organizationItem = modelMapp.map(foxOrganization, OrganizationItem.class);
                    if (!StringUtils.isBlank(foxOrganization.getLeaderId())) {
                        Optional.ofNullable(foxUserRepository.findOne(foxOrganization.getLeaderId()))
                                .ifPresent(foxUser -> {
                                    organizationItem.setLeaderName(foxUser.getUserName());
                                });
                    }
                    organizationItem.setHeadCount(foxUserOrganizationRepository.findByOrganizationId(foxOrganization.getId()).size());
                    organizationItems.add(organizationItem);
                }
        );

        _pageInfo.setTotalPage(foxOrganizations.getTotalPages());
        _pageInfo.setTotalRecords(new Long(foxOrganizations.getTotalElements()).intValue());

        return organizationItems;
    }

    // TODO 查询部门已使用额度
    @Override
    public OrganizationItem findOrganizationItemById(String _organizationId) {
        FoxOrganization foxOrganization = Optional.ofNullable(foxOrganizationRepository.findOne(_organizationId))
                .orElseThrow(() -> new NoRecordFoundException(String.format("organization %s not found.", _organizationId)));

        OrganizationItem organizationItem = modelMapp.map(foxOrganization, OrganizationItem.class);

        List<UserItem> userItems = new ArrayList<>();

        List<FoxUserOrganization> foxUserOrganizations = foxUserOrganizationRepository.findByOrganizationId(_organizationId);
        foxUserOrganizations.forEach(
                foxUserOrganization -> {
                    String userId = foxUserOrganization.getUserId();
                    FoxUser foxUser = foxUserRepository.findOne(userId);
                    UserItem userItem = modelMapp.map(foxUser, UserItem.class);
                    userItems.add(userItem);
                }
        );

        organizationItem.setMembers(userItems);

        List<FoxOrganizationPlatformMap> foxOrganizationPlatformMaps = foxOrganizationPlatformMapRepository.findByOrganizationId(_organizationId);
        List<PlatformProfile> platformProfiles = new ArrayList<>();
        foxOrganizationPlatformMaps.forEach(
                foxOrganizationPlatformMap -> {
                    String platformId = foxOrganizationPlatformMap.getPlatformId();
                    PlatformProfile platformProfile = new PlatformProfile();
                    platformProfile.setId(platformId);
                    platformProfiles.add(platformProfile);
                }
        );

        GeneralContentResult<List<PlatformItem>> result = platformAccessClient.findPlatformByIds(platformProfiles);
        List<PlatformItem> platformItems = result.getResultContent();

        organizationItem.setPlatforms(platformItems);
        organizationItem.setHeadCount(userItems.size());

        if (!StringUtils.isBlank(foxOrganization.getLeaderId())) {
            Optional.ofNullable(foxUserRepository.findOne(foxOrganization.getLeaderId()))
                    .ifPresent(foxUser -> {
                        organizationItem.setLeaderName(foxUser.getUserName());
                    });
        }

        return organizationItem;
    }

    @Override
    public List<PlatformItem> findNonOrganizationPlatforms() {
        List<FoxOrganizationPlatformMap> foxOrganizationPlatformMaps = foxOrganizationPlatformMapRepository.findAll();
        List<String> platformIds = foxOrganizationPlatformMaps.stream()
                .map(foxOrganizationPlatformMap -> foxOrganizationPlatformMap.getPlatformId()).distinct()
                .collect(Collectors.toList());

        List<PlatformProfile> platformProfiles = new ArrayList<>();
        platformIds.stream().forEach(
                platformId -> {
                    PlatformProfile platformProfile = new PlatformProfile();
                    platformProfile.setId(platformId);
                    platformProfiles.add(platformProfile);
                }
        );

        GeneralContentResult<List<PlatformItem>> result = platformAccessClient.findPlatformByNonIds(platformProfiles);
        return result.getResultContent();
    }

    @Override
    public List<OrganizationItem> findOrganizationsByTenantId(String _tenantId) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "根据企业ID获取部门/组织：{}", _tenantId);
        List<FoxOrganization> foxOrganizations = null;
        if(userService.isProviderUser()){
            // TODO WXD后台用户不根据企业ID查，只根据类型查询
            foxOrganizations = foxOrganizationRepository.findByTypeAndStatusLessThan(CommonConstant.ORGANIZATION_TYPE,CommonConstant.DIC_GLOBAL_STATUS_DELETED);
        }else{
            // 前台用户根据企业ID查询：
            foxOrganizations = foxOrganizationRepository.findByTenantIdAndStatusLessThan(_tenantId, CommonConstant.DIC_GLOBAL_STATUS_DELETED);
        }

        List<OrganizationItem> organizationItems = foxOrganizations.stream()
                .sorted(((o1, o2) -> o1.getId().compareTo(o2.getId())))
                .map(foxOrganization -> {
                    OrganizationItem organizationItem = new OrganizationItem();
                    organizationItem.setId(foxOrganization.getId());
                    organizationItem.setName(foxOrganization.getName());
                    organizationItem.setDescription(foxOrganization.getDescription());
                    organizationItem.setStatus(foxOrganization.getStatus());
                    return organizationItem;
                })
                .collect(Collectors.toList());
        return organizationItems;
    }

    @Override
    public OrganizationResourceItem findResourceQuotaByOrganizationId(String _organizationId) {
        FoxOrganizationResourceQuota foxOrganizationResourceQuota = foxOrganizationResourceQuotaRepository.findByOrganizationId(_organizationId);
        if(null == foxOrganizationResourceQuota){
            return new OrganizationResourceItem();
        }
        OrganizationResourceItem organizationResourceItem = modelMapp.map(foxOrganizationResourceQuota, OrganizationResourceItem.class);
        return organizationResourceItem;
    }

    @Override
    public void createResourceQuota(String _organizationId, OrganizationResourceProfile _organizationResourceProfile) {
        FoxOrganizationResourceQuota foxOrganizationResourceQuota = new FoxOrganizationResourceQuota();
        foxOrganizationResourceQuota.setOrganizationId(_organizationId);
        foxOrganizationResourceQuota.setVcpu(_organizationResourceProfile.getVcpu());
        foxOrganizationResourceQuota.setDisk(_organizationResourceProfile.getDisk());
        foxOrganizationResourceQuota.setImage(_organizationResourceProfile.getImage());
        foxOrganizationResourceQuota.setIpaddress(_organizationResourceProfile.getIpaddress());
        foxOrganizationResourceQuota.setMem(_organizationResourceProfile.getMem());
        foxOrganizationResourceQuota.setPhysical(_organizationResourceProfile.getPhysical());
        foxOrganizationResourceQuota.setSnapshot(_organizationResourceProfile.getSnapshot());
        foxOrganizationResourceQuota.setStorage(_organizationResourceProfile.getStorage());

        foxOrganizationResourceQuotaRepository.save(foxOrganizationResourceQuota);
    }

    @Override
    public GeneralResult updateResourceQuota(String _organizationId, OrganizationResourceProfile _organizationResourceProfile) {
        GeneralContent generalContent = new GeneralContent();
        log.debug(CommonConstant.LOG_DEBUG_TAG + "修改组织配额，ID:{}, profile:{}", _organizationId, _organizationResourceProfile);
        GeneralResult generalResult = new GeneralResult();
        generalResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        // 查询数据库中当前组织配额
        FoxOrganizationResourceQuota foxOrganizationResourceQuota = foxOrganizationResourceQuotaRepository.findByOrganizationId(_organizationId);

        // 校验修改的配额是否小于已经使用的额度，如果小于，则返回错误
        String checkUsedandUsage = checkInputQuota(_organizationResourceProfile, foxOrganizationResourceQuota);
        if(!ResultCode.OPERATION_SUCCESS.equals(checkUsedandUsage)){
            generalResult.setResultCode(checkUsedandUsage);
            return generalResult;
        }

        FoxOrganization foxOrganization = foxOrganizationRepository.findOne(_organizationId);

        // 修改业务逻辑：调用企业资源接口(贾薇)修改企业配额：验证通过后则创建企业配额
        // 先验证企业资源额度是否足够，成功后再创建部门，否则返回失败：
        List<ResourceQuotaUsageDetailProfile> inputEnterpriseUsedQuota = buildEnterpriseQuotaInputParam(_organizationResourceProfile,foxOrganizationResourceQuota, QUOTA_RELEASE, foxOrganization.getTenantId());
        GeneralContentResult<ResourceQuotaUsageDetailProfile> tenantResourceResult = resourceClient.validateAndUpdateEnterpriseUsedQuota(inputEnterpriseUsedQuota);
        if(!ResultCode.OPERATION_SUCCESS.equals(tenantResourceResult.getResultCode())){
            log.debug(CommonConstant.LOG_DEBUG_TAG + "修改组织配额，调用接口校验和修改企业资源返回错误：{},修改组织配额失败", tenantResourceResult.getResultCode());
            generalResult.setResultCode(ResultCode.USERMGMT_ORGAN_CREATE_FAILED);
            return generalResult;
        }

        log.debug(CommonConstant.LOG_DEBUG_TAG + "修改组织配额，调用接口校验和修改企业配额资源成功：{},开始修改组织配额", tenantResourceResult.getResultCode());

        if(null != _organizationResourceProfile) {
            BeanUtils.copyProperties(_organizationResourceProfile, foxOrganizationResourceQuota);
        }
        foxOrganizationResourceQuotaRepository.save(foxOrganizationResourceQuota);

        return generalResult;
    }

    @Override
    public GeneralContentResult<OrganizationQuotaResp> updateOrganizationUsedResourceQuota(String _organizationId, List<OrganizationResourceQuotaUsageProfile> organizationResourceQuotaUsageProfileList) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "开始更新部门资源已使用额度，ID:{},入参:{}", _organizationId, organizationResourceQuotaUsageProfileList);
        GeneralContentResult<OrganizationQuotaResp> generalResult = new GeneralContentResult<OrganizationQuotaResp>();
        generalResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        FoxOrganizationResourceQuota dbQuota = foxOrganizationResourceQuotaRepository.findByOrganizationId(_organizationId);
        if(null == dbQuota){
            log.warn(CommonConstant.LOG_DEBUG_TAG + "该部门没有配额信息，无法校验，需要先修改配额：{}", _organizationId);
            generalResult.setResultCode(ResultCode.ORGANIZATION_QUOTA_VALIDATE_FAILED);
            return generalResult;
        }
        OrganizationQuotaResp organizationQuotaResp = isResourceQuotaEnough(dbQuota, organizationResourceQuotaUsageProfileList);
        generalResult.setResultContent(organizationQuotaResp);

        //校验通过才更新数据库
        if(organizationQuotaResp.isCheckResult()){
            dbQuota = updateOrganizationQuota(dbQuota, organizationResourceQuotaUsageProfileList);
            foxOrganizationResourceQuotaRepository.saveAndFlush(dbQuota);
        }
        log.debug(CommonConstant.LOG_DEBUG_TAG + "更新部门资源已使用额度成功，更新后的结果：{}", dbQuota);
        return generalResult;
    }

    @Override
    public GeneralContentResult<OrganizationQuotaResp> validateOrganizationUsedResourceQuota(String _organizationId, List<OrganizationResourceQuotaUsageProfile> organizationResourceQuotaUsageProfileList) {
        log.info(CommonConstant.LOG_DEBUG_TAG + "校验部门资源是否足够, 部门ID：{}， 请求额度:{}", _organizationId, organizationResourceQuotaUsageProfileList);
        GeneralContentResult<OrganizationQuotaResp> generalResult = new GeneralContentResult<OrganizationQuotaResp>();
        generalResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        FoxOrganizationResourceQuota dbQuota = foxOrganizationResourceQuotaRepository.findByOrganizationId(_organizationId);
        if(null == dbQuota){
            log.warn(CommonConstant.LOG_DEBUG_TAG + "该部门没有配额信息，无法校验，需要先修改配额：{}", _organizationId);
            generalResult.setResultCode(ResultCode.ORGANIZATION_QUOTA_VALIDATE_FAILED);
            return generalResult;
        }
        OrganizationQuotaResp organizationQuotaResp = isResourceQuotaEnough(dbQuota, organizationResourceQuotaUsageProfileList);
        generalResult.setResultContent(organizationQuotaResp);
        log.info(CommonConstant.LOG_DEBUG_TAG + "校验部门资源完成：{}", generalResult);
        return generalResult;
    }

    /**
     * 校验可用资源是否足够：足够返回true, 不够返回false
     * @param dbQuota
     * @param organizationResourceQuotaUsageProfileList
     * @return
     */
    private OrganizationQuotaResp isResourceQuotaEnough(FoxOrganizationResourceQuota dbQuota, List<OrganizationResourceQuotaUsageProfile> organizationResourceQuotaUsageProfileList) {
        log.info(CommonConstant.LOG_DEBUG_TAG + "开始校验部门资源是否足够：入参：{}， 数据库使用记录：{}", organizationResourceQuotaUsageProfileList, dbQuota);

        // 返回参数
        OrganizationQuotaResp resp = new OrganizationQuotaResp();
        resp.setCheckResult(true);//默认设置为true，如果下面检查到不通过再设置为false
        // 需要的额度+该机构下的已用额度必须小于可用的额度
        for(OrganizationResourceQuotaUsageProfile quota: organizationResourceQuotaUsageProfileList){

            //将类型设置为最后一个检查的type，不通过直接返回
            resp.setType(quota.getType());

            if(ResourceTypeConstant.CPU.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    if(getValue(dbQuota.getUsedCpu()) + quota.getUsageValue() >  getValue(dbQuota.getVcpu())){
                        log.warn(CommonConstant.LOG_DEBUG_TAG + "CPU使用额度超出限制,已用：{}, 可用：{}，需要：{}",dbQuota.getUsedCpu(), dbQuota.getVcpu(), quota.getUsageValue());
                        resp.setCheckResult(false);
                        return resp;
                    }
                }
            }else if(ResourceTypeConstant.MEM.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    if(getValue(dbQuota.getUsedMem()) + quota.getUsageValue() >  getValue(dbQuota.getMem())){
                        log.warn(CommonConstant.LOG_DEBUG_TAG + "CPU使用额度超出限制,已用：{}, 可用：{}，需要：{}",dbQuota.getUsedMem(), dbQuota.getMem(), quota.getUsageValue());
                        resp.setCheckResult(false);
                        return resp;
                    }
                }
            }else if(ResourceTypeConstant.DISK.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    if(getValue(dbQuota.getUsedDisk()) + quota.getUsageValue() >  getValue(dbQuota.getDisk())){
                        log.warn(CommonConstant.LOG_DEBUG_TAG + "CPU使用额度超出限制,已用：{}, 可用：{}，需要：{}",dbQuota.getUsedDisk(), dbQuota.getDisk(), quota.getUsageValue());
                        resp.setCheckResult(false);
                        return resp;
                    }
                }
            }else if(ResourceTypeConstant.STORAGE.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    if(getValue(dbQuota.getUsedStorage()) + quota.getUsageValue() >  getValue(dbQuota.getStorage())){
                        log.warn(CommonConstant.LOG_DEBUG_TAG + "CPU使用额度超出限制,已用：{}, 可用：{}，需要：{}",dbQuota.getUsedStorage(), dbQuota.getStorage(), quota.getUsageValue());
                        resp.setCheckResult(false);
                        return resp;
                    }
                }
            }else if(ResourceTypeConstant.VM.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    if(getValue(dbQuota.getUsedVm()) + quota.getUsageValue() >  getValue(dbQuota.getVm())){
                        log.warn(CommonConstant.LOG_DEBUG_TAG + "CPU使用额度超出限制,已用：{}, 可用：{}，需要：{}",dbQuota.getUsedVm(), dbQuota.getVm(), quota.getUsageValue());
                        resp.setCheckResult(false);
                        return resp;
                    }
                }
            }else if(ResourceTypeConstant.NETWORK.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    if(getValue(dbQuota.getUsedNetwork()) + quota.getUsageValue() >  getValue(dbQuota.getNetwork())){
                        log.warn(CommonConstant.LOG_DEBUG_TAG + "CPU使用额度超出限制,已用：{}, 可用：{}，需要：{}",dbQuota.getUsedNetwork(), dbQuota.getNetwork(), quota.getUsageValue());
                        resp.setCheckResult(false);
                        return resp;
                    }
                }
            }else if(ResourceTypeConstant.SNAPSHOT.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    if(getValue(dbQuota.getUsedSnapshot()) + quota.getUsageValue() >  getValue(dbQuota.getSnapshot())){
                        log.warn(CommonConstant.LOG_DEBUG_TAG + "CPU使用额度超出限制,已用：{}, 可用：{}，需要：{}",dbQuota.getUsedSnapshot(), dbQuota.getSnapshot(), quota.getUsageValue());
                        resp.setCheckResult(false);
                        return resp;
                    }
                }
            }else if(ResourceTypeConstant.IMAGE.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    if(getValue(dbQuota.getUsedImage()) + quota.getUsageValue() >  getValue(dbQuota.getImage())){
                        log.warn(CommonConstant.LOG_DEBUG_TAG + "CPU使用额度超出限制,已用：{}, 可用：{}，需要：{}",dbQuota.getUsedImage(), dbQuota.getImage(), quota.getUsageValue());
                        resp.setCheckResult(false);
                        return resp;
                    }
                }
            }else if(ResourceTypeConstant.PHYSICAL_MACHINE.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    if(getValue(dbQuota.getUsedPhysical()) + quota.getUsageValue() >  getValue(dbQuota.getPhysical())){
                        log.warn(CommonConstant.LOG_DEBUG_TAG + "CPU使用额度超出限制,已用：{}, 可用：{}，需要：{}",dbQuota.getUsedPhysical(), dbQuota.getPhysical(), quota.getUsageValue());
                        resp.setCheckResult(false);
                        return resp;
                    }
                }
            }else if(ResourceTypeConstant.DYNAMIC_IP.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    if(getValue(dbQuota.getUsedIpaddress()) + quota.getUsageValue() >  getValue(dbQuota.getIpaddress())){
                        log.warn(CommonConstant.LOG_DEBUG_TAG + "CPU使用额度超出限制,已用：{}, 可用：{}，需要：{}",dbQuota.getUsedIpaddress(), dbQuota.getIpaddress(), quota.getUsageValue());
                        resp.setCheckResult(false);
                        return resp;
                    }
                }
            }else{
                log.error(CommonConstant.LOG_ERROR_TAG + "资源类型错误：" + quota.getType());
                //generalResult.setResultCode(ResultCode.ORGANIZATION_QUOTA_VALIDATE_FAILED);
                resp.setCheckResult(false);
                return resp;
            }
        }

        return resp;
    }

    private FoxOrganizationResourceQuota updateOrganizationQuota(FoxOrganizationResourceQuota dbQuota, List<OrganizationResourceQuotaUsageProfile> organizationResourceQuotaUsageProfileList) {
        log.info(CommonConstant.LOG_DEBUG_TAG + "开始修改部门资源额度：入参：{}， 数据库使用记录：{}", organizationResourceQuotaUsageProfileList, dbQuota);
        // 调用该方法前已经做了额度判断，此处不用再判断，直接修改
        for(OrganizationResourceQuotaUsageProfile quota: organizationResourceQuotaUsageProfileList){
            if(ResourceTypeConstant.CPU.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    dbQuota.setUsedCpu(getValue(dbQuota.getUsedCpu()) + quota.getUsageValue());
                }else{
                    dbQuota.setUsedCpu(minusQuota(getValue(dbQuota.getUsedCpu()), quota.getUsageValue()));
                }
            }else if(ResourceTypeConstant.MEM.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    dbQuota.setUsedMem(getValue(dbQuota.getUsedMem()) + quota.getUsageValue());
                }else{
                    dbQuota.setUsedMem(minusQuota(getValue(dbQuota.getUsedMem()) , quota.getUsageValue()));
                }
            }else if(ResourceTypeConstant.DISK.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    dbQuota.setUsedDisk(getValue(dbQuota.getUsedDisk()) + quota.getUsageValue());
                }else{
                    dbQuota.setUsedDisk(minusQuota(getValue(dbQuota.getUsedDisk()) , quota.getUsageValue()));
                }
            }else if(ResourceTypeConstant.STORAGE.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    dbQuota.setUsedStorage(getValue(dbQuota.getUsedStorage()) + quota.getUsageValue());
                }else{
                    dbQuota.setUsedStorage(minusQuota(getValue(dbQuota.getUsedStorage()) , quota.getUsageValue()));
                }
            }else if(ResourceTypeConstant.VM.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    dbQuota.setUsedVm(getValue(dbQuota.getUsedVm()) + quota.getUsageValue());
                }else{
                    dbQuota.setUsedVm(minusQuota(getValue(dbQuota.getUsedVm()) , quota.getUsageValue()));
                }
            }else if(ResourceTypeConstant.NETWORK.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    dbQuota.setUsedNetwork(getValue(dbQuota.getUsedNetwork()) + quota.getUsageValue());
                }else{
                    dbQuota.setUsedNetwork(minusQuota(getValue(dbQuota.getUsedNetwork()) , quota.getUsageValue()));
                }
            }else if(ResourceTypeConstant.SNAPSHOT.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    dbQuota.setUsedSnapshot(getValue(dbQuota.getUsedSnapshot()) + quota.getUsageValue());
                }else{
                    dbQuota.setUsedSnapshot(minusQuota(getValue(dbQuota.getUsedSnapshot()) , quota.getUsageValue()));
                }
            }else if(ResourceTypeConstant.IMAGE.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    dbQuota.setUsedImage(getValue(dbQuota.getUsedImage()) + quota.getUsageValue());
                }else{
                    dbQuota.setUsedImage(minusQuota(getValue(dbQuota.getUsedImage()) , quota.getUsageValue()));
                }
            }else if(ResourceTypeConstant.PHYSICAL_MACHINE.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    dbQuota.setUsedPhysical(getValue(dbQuota.getUsedPhysical()) + quota.getUsageValue());
                }else{
                    dbQuota.setUsedPhysical(minusQuota(getValue(dbQuota.getUsedPhysical()) , quota.getUsageValue()));
                }
            }else if(ResourceTypeConstant.DYNAMIC_IP.equals(quota.getType())){
                if(ResourceTypeConstant.RESOURCE_USED == quota.getActionType()){
                    dbQuota.setUsedIpaddress(getValue(dbQuota.getUsedIpaddress()) + quota.getUsageValue());
                }else{
                    dbQuota.setUsedIpaddress(minusQuota(getValue(dbQuota.getUsedIpaddress()) , quota.getUsageValue()));
                }
            }else{
                log.error(CommonConstant.LOG_ERROR_TAG + "资源类型错误：" + quota.getType());
                return dbQuota;
            }
        }
        return dbQuota;
    }

    /**
     * 如果已用额度-释放的额度 > 0,则直接使用减出结果，否则返回0： 确保不出现已使用资源为负数的情况
     * @param usedQuota
     * @param minusQuota
     * @return
     */
    private int minusQuota(int usedQuota, int minusQuota){
        return ((usedQuota - minusQuota) > 0 ) ? (usedQuota - minusQuota):0;
    }
}
