package com.yy.cloud.api.admin.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.PageParameter;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseQuotaProfile;
import com.yy.cloud.common.data.dto.enterprise.ResourceQuotaUsageDetailProfile;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseQuotaDetailResp;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseQuotaItem;
import com.yy.cloud.common.data.otd.enterprise.UsedQuotaItem;

import java.util.List;

/**
 * Created by chenluo on 9/27/2016.
 */
@FeignClient("resourcemgmt")
public interface ResourceMgmtClient {

	/**
	 * createResourceQuotaByBatch: 创建企业资源配额. <br/>
	 *
	 * @param _list
	 * @return
	 * 对于返回Entity EnterpriseQuotaItem， 下面几个参数只有最新生成的id是必须返回的
	 * <li>id
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprise/resource/quotas",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	GeneralPagingResult<List<EnterpriseQuotaItem>> createResourceQuotaByBatch(
			@RequestBody List<EnterpriseQuotaProfile> _list);

	/**
	 * updateResourceQuota: 批量更新企业资源配额. <br/>
	 *
	 * @param _list
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/authsec/enterprise/resource/quotas",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	GeneralPagingResult<GeneralContent> updateResourceQuota(@RequestBody List<EnterpriseQuotaProfile> _list);

	/**
	 * getResourceQuotaList: 获取所有企业的资源配额. <br/>
	 *
	 * @param _request
	 * @return
	 * 对于返回Entity EnterpriseQuotaItem， 下面几个参数是必须返回的
	 * <li>enterpriseId
	 * <li>platformId || regionId 二选一，或者都返回 均可
	 * <li>vmQuota
	 * <li>storageQuota
	 * <li>networkQuota
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/noauth/enterprise/resource/quotas/all",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	GeneralPagingResult<List<EnterpriseQuotaItem>> getResourceQuotaList(@RequestBody PageParameter _request);

	/**
	 * getResourceQuotaByEnt:依据企业ID来获取企业下的资源配额. <br/>
	 *
	 * @param enterpriseId
	 * @param request
	 * @return
	 * 对于返回Entity EnterpriseQuotaItem， 下面几个参数是必须返回的
	 * <li>enterpriseId
	 * <li>platformId || regionId 二选一，或者都返回 均可
	 * <li>vmQuota
	 * <li>storageQuota
	 * <li>networkQuota
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/noauth/enterprise/{enterpriseId}/resource/quotas",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	GeneralPagingResult<List<EnterpriseQuotaItem>> getResourceQuotaByEnt(
			@PathVariable("enterpriseId") String enterpriseId, @RequestBody PageParameter request);

	/**
	 * getResourceQuotaByEnts:依据企业ID找到企业下面资源配额. <br/>
	 * 返回的数据集，请按照入参企业id的顺序返回. <br/>
	 *
	 * @param enterpriseIds
	 * @return
	 * 对于返回Entity EnterpriseQuotaItem， 下面几个参数是必须返回的
	 * <li>enterpriseId
	 * <li>platformId || regionId 二选一，或者都返回 均可
	 * <li>vmQuota
	 * <li>storageQuota
	 * <li>networkQuota
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/noauth/enterprise/resource/quotas/ids",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	GeneralPagingResult<List<EnterpriseQuotaItem>> getResourceQuotaByEnts(@RequestBody List<String> enterpriseIds);


	@RequestMapping(value = "/noauth/enterprise/resource/quota/usages", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	GeneralContentResult<List<UsedQuotaItem>> getEnterpriseUsedQuota(@RequestBody List<String> tenantIds);


	/**
	 * 更新企业配额的使用量
	 * @param resourceQuotaUsageDetailProfiles
	 * @return
	 */
	@RequestMapping(value = "/noauth/enterprise/resource/quota/caculate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	GeneralContentResult<List<String>> enterpriseUsageQuotaCaculate(@RequestBody List<ResourceQuotaUsageDetailProfile> resourceQuotaUsageDetailProfiles);

	/**
	 * 获取企业配额详情
	 * @param enterpriseId
	 * @return
	 */
	@RequestMapping(value = "/noauth/enterprise/{enterpriseId}/resource/quota/detail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	GeneralContentResult<List<EnterpriseQuotaDetailResp>> getEnterpriseQuotaDetail(@PathVariable("enterpriseId") String enterpriseId);

	/**
	 * 获取所有企业配额详情
	 * @return
	 */
	@RequestMapping(value = "/noauth/enterprise/resource/quota/detail/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	GeneralContentResult<List<EnterpriseQuotaDetailResp>> getAllEnterpriseQuotaDetail();

	/**
	 * 校验并更新企业配额的使用量
	 * @param resourceQuotaUsageDetailProfiles
	 * @return
	 */
	@RequestMapping(value = "/noauth/enterprise/resouce/quota/validationupdate", method = RequestMethod.PUT)
	public GeneralContentResult<ResourceQuotaUsageDetailProfile> validateAndUpdateEnterpriseUsedQuota(@RequestBody List<ResourceQuotaUsageDetailProfile> resourceQuotaUsageDetailProfiles);

}
