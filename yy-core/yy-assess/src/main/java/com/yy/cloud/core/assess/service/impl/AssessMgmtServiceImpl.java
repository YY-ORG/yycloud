/**
 * Project Name:yy-assess
 * File Name:AssessMgmtServiceImpl.java
 * Package Name:com.yy.cloud.core.assess.service.impl
 * Date:Sep 27, 20179:30:56 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.assess.AssessItem;
import com.yy.cloud.common.data.metadata.TemplateItem;
import com.yy.cloud.core.assess.data.domain.PerAssess;
import com.yy.cloud.core.assess.data.domain.PerAssessTemplateMap;
import com.yy.cloud.core.assess.data.domain.PerTemplate;
import com.yy.cloud.core.assess.data.domain.PerTemplateItem;
import com.yy.cloud.core.assess.data.domain.PerTemplateTiMap;
import com.yy.cloud.core.assess.data.repositories.PerAssessRepository;
import com.yy.cloud.core.assess.data.repositories.PerTemplateRepository;
import com.yy.cloud.core.assess.service.AssessMgmtService;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:AssessMgmtServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 27, 2017 9:30:56 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Slf4j
@Service
public class AssessMgmtServiceImpl implements AssessMgmtService {
	@Autowired
	private PerAssessRepository perAssessRepository;
	@Autowired
	private PerTemplateRepository perTemplateRepository;
	@Override
	public GeneralContentResult<AssessItem> getAssessItemById(String _id) {
		PerAssess tempAssess = this.perAssessRepository.getOne(_id);
		
		AssessItem tempItem = this.convertToOTD(tempAssess);
		
		GeneralContentResult<AssessItem> tempResult = new GeneralContentResult<AssessItem>();
		tempResult.setResultContent(tempItem);
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		return tempResult;
	}
	
	private AssessItem convertToOTD(PerAssess _assess) {
		AssessItem tempItem = new AssessItem();
		
		tempItem.setId(_assess.getId());
		tempItem.setCode(_assess.getCode());
		tempItem.setName(_assess.getName());
		tempItem.setStatus(_assess.getStatus());
		tempItem.setType(_assess.getType());
		
		return tempItem;
	}
	
	private List<TemplateItem> getRelatedTemplateItem(PerAssess _assess){
		log.info("Going to retrieve assess [{}]'s template items...", _assess.getId());
		List<TemplateItem> tempList = new ArrayList<TemplateItem>();
		
		List<PerAssessTemplateMap> tempTempMapList = _assess.getPerAssessTemplateMaps();
		
		for(PerAssessTemplateMap tempItem : tempTempMapList) {
			if(tempItem.getStatus() != CommonConstant.DIC_GLOBAL_STATUS_ENABLE) {
				continue;
			}
			String temId = tempItem.getTemplateId();
			PerTemplate tempTemplate = this.perTemplateRepository.getOne(temId);
			List<PerTemplateTiMap> tempPTTM = tempTemplate.getPerTemplateTiMaps();
			
			for(PerTemplateTiMap pttmItem : tempPTTM) {
				PerTemplateItem tempPTI = pttmItem.getPerTemplateItem();
			}
			
		}
		
		return tempList;
	}
}

