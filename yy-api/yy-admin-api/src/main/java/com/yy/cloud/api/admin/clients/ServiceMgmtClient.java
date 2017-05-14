package com.yy.cloud.api.admin.clients;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.enterprise.ServicePageQueryCondition;
import com.yy.cloud.common.data.dto.enterprise.VMServiceQueryCondition;
import com.yy.cloud.common.data.dto.platformmgmt.*;
import com.yy.cloud.common.data.otd.enterprise.VMServiceDetailInfoItem;
import com.yy.cloud.common.data.otd.platformmgmt.DiskPlatformDetailInfoResp;
import com.yy.cloud.common.data.otd.platformmgmt.ServiceItem;
import com.yy.cloud.common.data.otd.platformmgmt.ServiceSimpleItem;
import com.yy.cloud.common.data.otd.platformmgmt.VmPlatformDetailInfoResp;
import com.yy.cloud.common.data.platformmgmt.ServiceSku;

import java.util.List;

@FeignClient("servicemgmt")
//@FeignClient(name = "testservicemgmt", url = "http://localhost:9105")
public interface ServiceMgmtClient {
	@RequestMapping(value = "/authsec/services/vm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public GeneralResult createService(@RequestBody VMServiceProfile serviceReq);

	@RequestMapping(value = "/authsec/services/vm/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public GeneralResult updateService(@PathVariable("id") String id, @RequestBody VMServiceProfile data);

	@RequestMapping(value = "/authsec/services/vm/{id}", method = RequestMethod.GET)
	@ResponseBody
	public GeneralContentResult<VMServiceDetailInfoItem> getVMServiceItem(@PathVariable("id") String id);

	@RequestMapping(value = "/authsec/platform/{platformid}/services", method = RequestMethod.GET)
	@ResponseBody
	public GeneralPagingResult<List<AdsServiceReq>> getServicesOfPlatform(@PathVariable("platformid") String platformid,
																		  @RequestParam("status") Byte status);

	@RequestMapping(value = "/authsec/service/{id}/status/{status}", method = RequestMethod.PUT)
	@ResponseBody
	public GeneralResult updateServiceStatus(@PathVariable("id") String id,
                                                             @PathVariable("status") Byte status);

	@RequestMapping(value = "/authsec/service/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public GeneralContentResult<Integer> deleteService(@PathVariable("id") String id);

	@RequestMapping(value = "/authsec/servicetemplates", method = RequestMethod.GET)
	@ResponseBody
	public GeneralContentResult<List<AdsServiceTemplateReq>> getTemplates(@RequestParam("status") Byte status);

	@RequestMapping(value = "/authsec/region/{regionid}/services", method = RequestMethod.GET)
	@ResponseBody
	public GeneralPagingResult<List<ServiceItem>> getServicesOfRegion(@PathVariable("regionid") String regionid,
                                                                      @RequestParam("status") Byte status, @RequestParam(value = "page", required = false) Integer page,
                                                                      @RequestParam(value = "size", required = false) Integer size);

	@RequestMapping(value = "/authsec/services", method = RequestMethod.GET)
	@ResponseBody
	public GeneralPagingResult<List<ServiceItem>> getServices(@RequestParam("status") Byte status,
                                                              @RequestParam(value = "page", required = false) Integer page,
                                                              @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(method = RequestMethod.GET, value = "/authsec/services/simple")
    @ResponseBody
	public GeneralContentResult<List<ServiceSimpleItem>> getServiceSimpleItemList();

	@RequestMapping(value = "/authsec/service/{serviceid}/sku", method = RequestMethod.POST)
	@ResponseBody
	public GeneralContentResult<List<ServiceSku>> createSkus(@PathVariable("serviceid") String serviceid);

	@RequestMapping(value = "/authsec/service/{serviceid}/skus", method = RequestMethod.GET)
	@ResponseBody
	public GeneralPagingResult<List<ServiceSku>> getSkus(@PathVariable("serviceid") String serviceid,
                                                         @RequestParam(value = "page", required = false) Integer page,
                                                         @RequestParam(value = "size", required = false) Integer size);

	@RequestMapping(value = "/authsec/services/search/paging", method = RequestMethod.POST)
	@ResponseBody
	public GeneralPagingResult<List<ServiceItem>> getServiceByConPaging(@RequestBody ServicePageQueryCondition _con);


	/**
	 * 获取平台Disk服务（目录）详情，平台，可用区，储存（未完成）
	 * @return
	 */
	@RequestMapping(value = "/authsec/services/platforms/disk/detailinfo", method = RequestMethod.GET)
	@ResponseBody
	public GeneralContentResult<List<DiskPlatformDetailInfoResp>> getDiskServicePlatformInfo();


	/**
	 * 获取产品目录详情包括平台信息，可用区，储存（未完成）
	 * @param serviceId
	 * @return
	 */
	@RequestMapping(value = "/authsec/services/{serviceId}/disk/", method = RequestMethod.GET)
	@ResponseBody
	public GeneralContentResult<DiskServiceProfileRep> getProductPlatformInfoForCreateDisk(@PathVariable("serviceId") String serviceId) ;


	/**
	 * 创建Disk产品目录（开发中）
	 * @param _profile
	 * @return
	 */
	@RequestMapping(value = "/authsec/services/type/disk", method = RequestMethod.POST)
	@ResponseBody
	public GeneralResult createDiskService(@RequestBody DiskServiceProfile _profile) ;



	@RequestMapping(path = "/authsec/services/groupinfo/vm", method = RequestMethod.POST)
	@ResponseBody
	public GeneralContentResult<List<VmPlatformDetailInfoResp>> getVmServicePlatformDetailInfo(@RequestBody VMServiceQueryCondition _conn);






}
