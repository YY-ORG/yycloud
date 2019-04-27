/**
 * Project Name:fox-admin
 * File Name:SysBaseServiceImpl.java
 * Package Name:com.hpe.foxcloud.api.admin.service.impl
 * Date:Sep 28, 20164:12:44 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.api.admin.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.cloud.api.admin.clients.SysBaseClient;
import com.yy.cloud.api.admin.service.SysBaseService;
import com.yy.cloud.common.data.otd.sysbase.SysDic;
import com.yy.cloud.common.data.sysbase.SystemDictionary;

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
		
		Collection<SystemDictionary> tempCol = this.sysBaseClient.findSysDicAll().getContent();
		return this.convertToSysDic(tempCol);
	}

	@Override
	public List<SysDic> getSysdictionaryByOwner(String _owner) {
		Collection<SystemDictionary> tempCol = this.sysBaseClient.findByOwner(_owner).getContent();
		return this.convertToSysDic(tempCol);
	}

	@Override
	public List<SysDic> getSysdictionaryByOwnerAndField(String _owner, String _field) {
		log.info("Going to invoke sysbase to retrieve {}-{} 's dics.", _owner, _field);

		Collection<SystemDictionary> tempCol = this.sysBaseClient.findByOwnerAndField(_owner, _field).getContent();
		return this.convertToSysDic(tempCol);
	}

	private List<SysDic> convertToSysDic(Collection<SystemDictionary> _col) {
		List<SysDic> tempList = new ArrayList<SysDic>();
		if (_col == null || _col.isEmpty()) {
			log.debug("The result is empty!");
			return tempList;
		}
		for (SystemDictionary tempDic : _col) {
			SysDic tempItem = new SysDic();
			tempItem.setCode(tempDic.getValue());
			tempItem.setValue(tempDic.getCode());
			tempItem.setDisplayValue(tempDic.getText());
			tempItem.setField(tempDic.getField());
			tempItem.setOwner(tempDic.getOwner());

			tempList.add(tempItem);
		}
		Collections.sort(tempList, new Comparator<SysDic>() {
			@Override
			public int compare(SysDic o1, SysDic o2) {
				int i = Integer.parseInt(o1.getValue());
				int j = Integer.parseInt(o2.getValue());
				if (i > j) return 1;
				if (i < j) return -1;
				return 0;
			}
		});
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
