package com.yy.cloud.api.admin.controller;

import com.yy.cloud.api.admin.service.EnterpriseMgmtService;
import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseAdminProfile;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseAdAdminItem;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseAdminItem;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by chenluo on 10/31/2016.
 */
@RestController
@Slf4j
public class EnterpriseAdminController {

    @Autowired
    private EnterpriseMgmtService enterpriseMgmtService;

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprise/{_enterpriseId}/admin", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "创建企业管理员 (已完成）", notes = "Create an enterpise admin")
    public GeneralContentResult<String> createEnterpriseAdmin(
            @ApiParam(name="_enterpriseId", required=true) @PathVariable String _enterpriseId,
            @ApiParam(name = "_profile", value = "Platform's Profile", required = true) @RequestBody EnterpriseAdminProfile _profile) {
        log.debug("Going to create the EnterpriseAdmin {}.", _profile);

        GeneralContentResult<String> tempResult = this.enterpriseMgmtService
                .createEnterpriseAdmin(_enterpriseId, _profile);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprise/admin/{_adminId}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取某个企业管理员详细信息(已完成）", notes = "Get an enterpise admin")
    public GeneralPagingResult<EnterpriseAdminItem> getEnterpriseAdmin(
            @ApiParam(name = "_adminId", value = "Admin's ID", required = true) @PathVariable String _adminId) {
        log.debug("Going to retrieve the Enterprise {}.", _adminId);

        GeneralPagingResult<EnterpriseAdminItem> tempResult = this.enterpriseMgmtService.getEnterpriseAdmin(_adminId);
        return tempResult;
    }
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprises/admins/page/{_page}/size/{_size}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分页获取所有企业所有管理员列表(已完成）",
            notes = "Retrieve the admin list for Enterprise.")
    public GeneralPagingResult<List<EnterpriseAdminItem>> getAllAdminList(
            @ApiParam(name = "_page", value = "_page", required = true) @PathVariable Integer _page,
            @ApiParam(name = "_size", value = "_size", required = true) @PathVariable Integer _size) {
        log.debug("Going to retrieve all of Enterprises' admin.");
        GeneralPagingResult<List<EnterpriseAdminItem>> tempResult = this.enterpriseMgmtService
                .getEnterpriseAdminList(_page, _size);
        return tempResult;
    }
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprise/{_enterpriseId}/admins/page/{_page}/size/{_size}",
            method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分页获取某个企业下的所有管理员(已完成）", notes = "Retrieve the admin list for Enterprise.")
    public GeneralPagingResult<List<EnterpriseAdminItem>> getAdminListByEnterprise(
            @ApiParam(name = "_enterpriseId", value = "Enterprise's ID",
                    required = true) @PathVariable String _enterpriseId,
            @ApiParam(name = "_page", value = "_page", required = true) @PathVariable Integer _page,
            @ApiParam(name = "_size", value = "_size", required = true) @PathVariable Integer _size) {
        log.debug("Going to retrieve the Enterprise {} 's admin list.", _enterpriseId);

        GeneralPagingResult<List<EnterpriseAdminItem>> tempResult = this.enterpriseMgmtService
                .getEnterpriseAdminListByEnt(_enterpriseId, _page, _size);

        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprise/{_enterpriseId}/adadmins/page/{_page}/size/{_size}",
            method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分页获取某个企业下的所有Ad管理员(未完成)", notes = "Retrieve the admin list for Enterprise.")
    public GeneralPagingResult<List<EnterpriseAdAdminItem>> getAdAdminListByEnterprise(
            @ApiParam(name = "_enterpriseId", value = "Enterprise's ID",
                    required = true) @PathVariable String _enterpriseId,
            @ApiParam(name = "_page", value = "_page", required = true) @PathVariable Integer _page,
            @ApiParam(name = "_size", value = "_size", required = true) @PathVariable Integer _size) {
        log.debug("Going to retrieve the Enterprise {} 's admin list.", _enterpriseId);

//		GeneralPagingResult<List<EnterpriseAdAdminItem>> tempResult = this.enterpriseMgmtService
//				.getEnterpriseAdAdminListByEnt(_enterpriseId, _page, _size);
        //todo
        GeneralPagingResult<List<EnterpriseAdAdminItem>> tempResult = new GeneralPagingResult<>();
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprise/admin/{_adminId}", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "更新某个企业管理员(已完成）", notes = "Update the admin list for Enterprise.")
    public GeneralResult updateAdmin(
            @ApiParam(name = "_adminId", value = "Enterprise Admin Id", required = true) @PathVariable("_adminId") String _adminId,
            @ApiParam(name = "_profile", value = "Admin's new profile", required = true) @RequestBody EnterpriseAdminProfile _profile) {
        log.debug("Going to update the admin {}.", _adminId);
        _profile.setId(_adminId);
        GeneralResult tempResult = this.enterpriseMgmtService.updateEnterpiseAdmin(_profile);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprise/admin/{_adminId}/credential", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "重置企业管理员的密码（待确认）", notes = "Update the admin's credential.")
    public GeneralResult updateAdminCredential(
            @ApiParam(name = "_adminId", value = "Enterprise's ID", required = true) @PathVariable("_adminId") String _adminId) {
        log.debug("Going to update the admin {}.", _adminId);
        GeneralResult tempResult = this.enterpriseMgmtService.updateEnterpiseAdminCredential(_adminId);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprise/admin/{_id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除某个企业管理员（已完成）", notes = "Delete the admin.")
    public GeneralPagingResult<GeneralContent> deleteAdmin(
            @ApiParam(name = "_id", value = "Enterprise Admin's ID", required = true) @PathVariable String _id) {
        log.debug("Going to delete the admin {}.", _id);

        GeneralPagingResult<GeneralContent> tempResult = this.enterpriseMgmtService.deleteEnterpriseAdmin(_id);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprises/admins", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiOperation(value = "批量删除企业管理员，暂不支持（未完成）", notes = "Delete the Administrators.")
    public GeneralPagingResult<GeneralContent> deleteAdminByBatch(@ApiParam(name = "_id",
            value = "Enterprise Admin's ID", required = true) @RequestBody List<EnterpriseAdminProfile> _list) {
        log.debug("Going to delete the admin {}.", _list);

//		GeneralPagingResult<GeneralContent> tempResult = this.enterpriseMgmtService.deleteEnterpriseAdminByBatch(_list);
        //todo
        GeneralPagingResult<GeneralContent> tempResult= new GeneralPagingResult<>();
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprise/admin/{_id}/status/{_status}", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "更新企业管理员状态（已完成）", notes = "Update the admin Status.")
    public GeneralResult UpdateAdminStatus(
            @ApiParam(name = "_id", value = "Admin's ID", required = true) @PathVariable String _id,
            @ApiParam(name = "_status", value = "0: 未启用; 1: 已启用; 2: 已挂起; 3: 已禁用; 4: 已删除", required = true) @PathVariable Byte _status) {
        log.debug("Going to update the admin {}' status to {}.", _id, _status);

        GeneralResult tempResult = this.enterpriseMgmtService.updateEnterpriseAdminStatus(_id,
                _status);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprises/admins/status/{_status}", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "批量更新企业管理员状态（已完成）",
            notes = "Update the admin Status by batch.")
    public GeneralPagingResult<GeneralContent> UpdateAdminStatusByBatch(
            @ApiParam(name = "_status", value = "0: 未启用; 1: 已启用; 2: 已挂起; 3: 已禁用; 4: 已删除", required = true) @PathVariable Byte _status,
            @RequestBody List<String> _list) {
        log.debug("Going to update the admins {}' status to {}.", _list, _status);
        GeneralPagingResult<GeneralContent> tempResult = this.enterpriseMgmtService
                .updateEnterpriseAdminStatusByBatch(_list, _status);
        return tempResult;
    }
}
