/**
 * Project Name:liz-admin
 * File Name:SysBaseController.java
 * Package Name:com.gemii.lizcloud.api.admin.controller
 * Date:Sep 28, 20164:07:04 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.api.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.otd.sysbase.SysDic;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:SysBaseController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 28, 2016 4:07:04 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@RestController
@Slf4j
public class SysBaseController {
	@Autowired
	private SysBaseService sysBaseService;

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/sysdic", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取所有字典信息（已完成）", notes = "Get All of the dictionaries.")
	public GeneralPagingResult<List<SysDic>> getAllDics() {
		log.debug("Going to retrieve all of the sysdics.");
		List<SysDic> tempResponse = this.sysBaseService.getAllSysdictionary();
		GeneralPagingResult<List<SysDic>> tempResult = new GeneralPagingResult<List<SysDic>>();
		tempResult.setResultContent(tempResponse);
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		return tempResult;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/sysdic/{_owner}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据owner 获取字典信息（已完成）", notes = "Get some Owner's dictionaries.")
	public GeneralPagingResult<List<SysDic>> getDicsByOwner(
			@ApiParam(required = true, name = "_owner", value = "Dics' Owner") @PathVariable String _owner) {
		log.debug("Going to retrieve all of the {}'s sysdics.", _owner);
		List<SysDic> tempResponse = this.sysBaseService.getSysdictionaryByOwner(_owner);
		GeneralPagingResult<List<SysDic>> tempResult = new GeneralPagingResult<List<SysDic>>();
		tempResult.setResultContent(tempResponse);
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		return tempResult;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/sysdic/{_owner}/{_field}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据owner和field 获取字典信息（已完成）", notes = "Get the dictionaries of some owner's some field.")
	public GeneralPagingResult<List<SysDic>> getDicsByOwnerAndField(
			@ApiParam(required = true, name = "_owner", value = "Dics' Owner") @PathVariable String _owner,
			@ApiParam(required = true, name = "_field",
					value = "Some field of this Owner") @PathVariable String _field) {
		log.debug("Going to retrieve all of the sysdics.");
		List<SysDic> tempResponse = this.sysBaseService.getSysdictionaryByOwnerAndField(_owner, _field);
		GeneralPagingResult<List<SysDic>> tempResult = new GeneralPagingResult<List<SysDic>>();
		tempResult.setResultContent(tempResponse);
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		return tempResult;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/sysdic/{_owner}/{_field}/{_code}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据owner和field 和code 获取字典信息（已完成）", notes = "Get the dictionaries of some owner's some field.")
	public GeneralPagingResult<SysDic> getSomeDics(
			@ApiParam(required = true, name = "_owner", value = "Dics' Owner") @PathVariable String _owner,
			@ApiParam(required = true, name = "_field", value = "Some field of this Owner") @PathVariable String _field,
			@ApiParam(required = true, name = "_code", value = "spefied code") @PathVariable String _code) {
		log.debug("Going to retrieve all of the sysdics.");
		SysDic tempResponse = this.sysBaseService.getOneSysdictionary(_owner, _field, _code);
		GeneralPagingResult<SysDic> tempResult = new GeneralPagingResult<SysDic>();
		tempResult.setResultContent(tempResponse);
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		return tempResult;
	}

}
