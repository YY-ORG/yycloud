package com.yy.cloud.api.admin.clients;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationProfile;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@FeignClient("usermgmt")
public interface OrgnizationClient {

	@RequestMapping(value = "/authsec/organization", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "用户中心-组织管理，创建")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralContentResult<String> createOrganization(@RequestBody OrganizationProfile _organizationProfile);

	@RequestMapping(value = "/authsec/organization/{organization_id}", method = RequestMethod.PUT)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	@ApiOperation(value = "用户中心-组织管理，修改")
	public GeneralResult modifyOrganization(@PathVariable("organization_id") String _organizationId,
			@RequestBody OrganizationProfile _organizationProfile);

	@RequestMapping(value = "/authsec/organization/{organization_id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "用户中心-组织管理，删除")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralResult deleteOrganization(@PathVariable("organization_id") String _organizationId);

	@RequestMapping(value = "/authsec/organization/{organization_id}/status", method = RequestMethod.PUT)
	@ApiOperation(value = "用户中心-组织管理，状态更新")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralResult updateOrganizationStatus(@PathVariable("organization_id") String _organizationId,
			@RequestParam(value = "status") Byte _status);

	@RequestMapping(value = "/authsec/organizations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "用户中心-组织管理，获取所有机构")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralPagingResult<List<OrganizationItem>> findOrganizations(
			@RequestParam(value = "status", required = false) Byte _status, @RequestParam(value = "page") Integer _page,
			@RequestParam(value = "size") Integer _size);

	@RequestMapping(value = "/authsec/organization/{organization_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "用户中心-组织管理，获取单个机构")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralContentResult<OrganizationItem> findOrganizationById(
			@PathVariable("organization_id") String _organizationId);

}
