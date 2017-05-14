/**
 * Project Name:liz-admin
 * File Name:SysBaseServiceImpl.java
 * Package Name:com.gemii.lizcloud.api.admin.service.impl
 * Date:Sep 28, 20164:12:44 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.api.admin.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.cloud.api.admin.clients.SysBaseClient;
import com.yy.cloud.api.admin.service.SysBaseService;
import com.yy.cloud.common.data.system.SysDic;
import com.yy.cloud.common.data.system.SystemDictionary;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:SysBaseServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 28, 2016 4:12:44 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Service
@Slf4j
public class SysBaseServiceImpl implements SysBaseService {
	@Autowired
	private SysBaseClient sysBaseClient;

	@Override
	public List<SysDic> getAllSysdictionary() {
		log.debug("Going to retrieve all of the dics...");
		
		List<SysDic> tempList = new ArrayList<SysDic>();
		
		Collection<SystemDictionary> tempCol = this.sysBaseClient.findSysDicAll().getContent();
		if (tempCol == null || tempCol.isEmpty()) {
			return tempList;
		}
		for (SystemDictionary tempDic : tempCol) {
			SysDic tempItem = new SysDic();
			tempItem.setCode(tempDic.getValue());
			tempItem.setValue(tempDic.getCode());
			tempItem.setDisplayValue(tempDic.getText());
			tempItem.setField(tempDic.getField());
			tempItem.setOwner(tempDic.getOwner());
			
			tempList.add(tempItem);
		}

		return tempList;
	}

	@Override
	public List<SysDic> getSysdictionaryByOwner(String _owner) {

		List<SysDic> tempList = new ArrayList<SysDic>();
		
		Collection<SystemDictionary> tempCol = this.sysBaseClient.findByOwner(_owner).getContent();
		
		if (tempCol == null || tempCol.isEmpty()) {
			return tempList;
		}
		for (SystemDictionary tempDic : tempCol) {
			SysDic tempItem = new SysDic();
			tempItem.setCode(tempDic.getValue());
			tempItem.setValue(tempDic.getCode());
			tempItem.setDisplayValue(tempDic.getText());
			tempItem.setField(tempDic.getField());
			tempItem.setOwner(tempDic.getOwner());
			
			tempList.add(tempItem);
		}

		return tempList;
	}

	@Override
	public List<SysDic> getSysdictionaryByOwnerAndField(String _owner, String _field) {
		log.info("Going to invoke sysbase to retrieve {}-{} 's dics.", _owner, _field);
		List<SysDic> tempList = new ArrayList<SysDic>();
		
		Collection<SystemDictionary> tempCol = this.sysBaseClient.findByOwnerAndField(_owner, _field).getContent();
		
		if (tempCol == null || tempCol.isEmpty()) {
			log.debug("The result is empty!");
			return tempList;
		}
		for (SystemDictionary tempDic : tempCol) {
			SysDic tempItem = new SysDic();
			tempItem.setCode(tempDic.getValue());
			tempItem.setValue(tempDic.getCode());
			tempItem.setDisplayValue(tempDic.getText());
			tempItem.setField(tempDic.getField());
			tempItem.setOwner(tempDic.getOwner());
			
			tempList.add(tempItem);
		}

		return tempList;
	}

	@Override
	public SysDic getOneSysdictionary(String _owner, String _field, String _code) {

		SysDic tempList = new SysDic();
		
		Collection<SystemDictionary> tempCol = this.sysBaseClient.findByOwnerAndFieldAndCode(_owner, _field, _code).getContent();
		
		if (tempCol == null || tempCol.isEmpty()) {
			return tempList;
		}
		
		SysDic tempItem = new SysDic();
		for (SystemDictionary tempDic : tempCol) {
			tempItem.setCode(tempDic.getValue());
			tempItem.setValue(tempDic.getCode());
			tempItem.setDisplayValue(tempDic.getText());
			tempItem.setField(tempDic.getField());
			tempItem.setOwner(tempDic.getOwner());
		}
		
		return tempItem;
	}

}
