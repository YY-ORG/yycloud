package com.yy.cloud.core.usermgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationProfile;
import com.yy.cloud.core.usermgmt.service.OrgnizationService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OrgnizationController {

	@Autowired
	private OrgnizationService orgnizationService;

	@RequestMapping(value = "/authsec/organization", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "用户中心-组织管理，创建")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralContentResult<String> createOrganization(@RequestBody OrganizationProfile _organizationProfile) {
		log.debug("用户中心-组织管理，创建组织：{}", _organizationProfile);
		GeneralContentResult<String> result = orgnizationService.createOrgnization(_organizationProfile);
		return result;
	}

	@RequestMapping(value = "/authsec/organization/{organization_id}", method = RequestMethod.PUT)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	@ApiOperation(value = "用户中心-组织管理，修改")
	public GeneralResult modifyOrganization(@PathVariable("organization_id") String _organizationId,
			@RequestBody OrganizationProfile _organizationProfile) {
		GeneralResult result = orgnizationService.updateOrgnization(_organizationId, _organizationProfile);
		return result;
	}

	@RequestMapping(value = "/authsec/organization/{organization_id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "用户中心-组织管理，删除")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralResult deleteOrganization(@PathVariable("organization_id") String _organizationId) {
		GeneralResult result = orgnizationService.deleteOrgnization(_organizationId);
		return result;
	}

	@RequestMapping(value = "/authsec/organization/{organization_id}/status", method = RequestMethod.PUT)
	@ApiOperation(value = "用户中心-组织管理，状态更新")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralResult updateOrganizationStatus(@PathVariable("organization_id") String _organizationId,
			@RequestParam(value = "status") Byte _status) {
		GeneralResult result = new GeneralResult();
		result.setResultCode(ResultCode.OPERATION_SUCCESS);

		orgnizationService.updateOrgnizationStatus(_organizationId, _status);
		return result;
	}

	@RequestMapping(value = "/authsec/organizations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "用户中心-组织管理，获取所有机构")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralPagingResult<List<OrganizationItem>> findOrganizations(
			@RequestParam(value = "status", required = false) Byte _status, @RequestParam(value = "page") Integer _page,
			@RequestParam(value = "size") Integer _size, @RequestParam(value = "name", required = false) String _name) {
		GeneralPagingResult<List<OrganizationItem>> result = new GeneralPagingResult<List<OrganizationItem>>();
		result.setResultCode(ResultCode.OPERATION_SUCCESS);

		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(_page);
		pageInfo.setPageSize(_size);

		List<OrganizationItem> organizationItems = orgnizationService.listOrganizationsByPage(pageInfo, _status, _name);

		result.setResultContent(organizationItems);
		result.setPageInfo(pageInfo);
		return result;
	}

	@RequestMapping(value = "/authsec/organization/{organization_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "用户中心-组织管理，获取单个机构")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralContentResult<OrganizationItem> findOrganizationById(
			@PathVariable("organization_id") String _organizationId) {
		GeneralContentResult<OrganizationItem> result = new GeneralContentResult<OrganizationItem>();
		result.setResultCode(ResultCode.OPERATION_SUCCESS);

		OrganizationItem organizationItem = orgnizationService.findOrganizationItemById(_organizationId);

		result.setResultContent(organizationItem);
		return result;
	}
}
