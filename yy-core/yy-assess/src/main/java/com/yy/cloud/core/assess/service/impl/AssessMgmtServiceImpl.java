/**
 * Project Name:yy-assess
 * File Name:AssessMgmtServiceImpl.java
 * Package Name:com.yy.cloud.core.assess.service.impl
 * Date:Sep 27, 20179:30:56 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.service.impl;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.assess.AssessItem;
import com.yy.cloud.common.data.assess.AssessMenuItem;
import com.yy.cloud.common.data.metadata.TemplateItem;
import com.yy.cloud.common.data.metadata.TemplateItemItem;
import com.yy.cloud.core.assess.data.domain.*;
import com.yy.cloud.core.assess.data.repositories.PerAssessPaperRepository;
import com.yy.cloud.core.assess.data.repositories.PerAssessRepository;
import com.yy.cloud.core.assess.data.repositories.PerTemplateRepository;
import com.yy.cloud.core.assess.service.AssessMgmtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

	@Autowired
	private PerAssessPaperRepository perAssessPaperRepository;

	@Override
	public GeneralContentResult<AssessItem> getAssessItemById(String _id) {
		PerAssess tempAssess = this.perAssessRepository.getOne(_id);
		
		AssessItem tempItem = this.convertToPAOTD(tempAssess);
		tempItem.setTemplateItemList(this.getRelatedTemplateItem(tempAssess));
		GeneralContentResult<AssessItem> tempResult = new GeneralContentResult<AssessItem>();
		tempResult.setResultContent(tempItem);
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		return tempResult;
	}

	@Override
	public GeneralContentResult<List<AssessMenuItem>> getAssessMenu(String _userId, String _orgId) {
		PerAssessPaper tempAssessPaper = this.perAssessPaperRepository.findByOrgId(_orgId);
		List<PerAssessAspMap> tempAssessAspMapList = tempAssessPaper.getPerAssessAspMaps();

		List<AssessMenuItem> tempAssessMenuItemList = new ArrayList<>();
		for(PerAssessAspMap tempAspMap : tempAssessAspMapList){
			PerAssess tempAssess = tempAspMap.getPerAssess();
			AssessMenuItem tempAssessMenuItem = new AssessMenuItem();
			tempAssessMenuItem.setAssessId(tempAssess.getId());
			tempAssessMenuItem.setAssessCode(tempAssess.getCode());
			tempAssessMenuItem.setAssessName(tempAssess.getName());
			tempAssessMenuItem.setSeqNo(tempAspMap.getSeqNo());
			tempAssessMenuItemList.add(tempAssessMenuItem);
		}
		GeneralContentResult<List<AssessMenuItem>> tempResult = new GeneralContentResult<List<AssessMenuItem>>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		tempResult.setResultContent(tempAssessMenuItemList);
		return tempResult;
	}


	/**
	 * 转换题所对应的OTD
	 *
	 * @param _assess
	 * @return
	 */
	private AssessItem convertToPAOTD(PerAssess _assess) {
		AssessItem tempItem = new AssessItem();
		
		tempItem.setId(_assess.getId());
		tempItem.setCode(_assess.getCode());
		tempItem.setName(_assess.getName());
		tempItem.setStatus(_assess.getStatus());
		tempItem.setType(_assess.getType());
		tempItem.setCreateDate(_assess.getCreateDate());
		tempItem.setUpdateDate(_assess.getUpdateDate());
		return tempItem;
	}

	/**
	 * 获取某个题所对应的元数据模板
	 *
	 * @param _assess
	 * @return
	 */
	private List<TemplateItem> getRelatedTemplateItem(PerAssess _assess){
		log.info("Going to retrieve assess [{}]'s template items...", _assess.getId());
		List<TemplateItem> tempList = new ArrayList<TemplateItem>();
		
		List<PerAssessTemplateMap> tempTempMapList = _assess.getPerAssessTemplateMaps();
		log.info("Get Assess[{}]'s template list count [{}].", _assess.getId(), tempTempMapList.size());
		for(PerAssessTemplateMap tempItem : tempTempMapList) {
			if(tempItem.getStatus() != CommonConstant.DIC_GLOBAL_STATUS_ENABLE) {
				continue;
			}
			String temId = tempItem.getTemplateId();
			log.info("This time is going to load template [{}]...", temId);
			PerTemplate tempTemplate = this.perTemplateRepository.getOne(temId);
			log.info("This time is going to load template [{}]'s Item List.", temId);
			List<PerTemplateTiMap> tempPTTM = tempTemplate.getPerTemplateTiMaps();
			log.info("Template [{}] has [{}] templateitem.", tempTemplate.getId(), tempPTTM.size());
			List<TemplateItemItem> tempTIIList = new ArrayList<TemplateItemItem>();
			for(PerTemplateTiMap pttmItem : tempPTTM) {
				PerTemplateItem tempPTI = pttmItem.getPerTemplateItem();
				TemplateItemItem tempTII = this.convertToTIIOTD(tempPTI);
				tempTII.setVisible(pttmItem.isVisible());//将模板元素在某个模板里面的特有属性给更新上来
				tempTII.setEditable(pttmItem.isEditable());
				tempTII.setMandatory(pttmItem.isMandatory());
				tempTII.setSeqNo(pttmItem.getSeqNo());
				tempTIIList.add(tempTII);
			}

			TemplateItem tempTemplateItem = this.convertToTIOTD(tempTemplate);
			tempTemplateItem.setTemplateItemItemList(tempTIIList);

			tempList.add(tempTemplateItem);
		}
		
		return tempList;
	}

	/**
	 * 转换获取元数据模板元素
	 *
	 * @param _tempPTI
	 * @return
	 */
	private TemplateItemItem convertToTIIOTD(PerTemplateItem _tempPTI){
		TemplateItemItem tempItem = new TemplateItemItem();
		tempItem.setId(_tempPTI.getId());
		tempItem.setCode(_tempPTI.getCode());
		tempItem.setName(_tempPTI.getName());
		tempItem.setLabel(_tempPTI.getLabel());
		tempItem.setOptionType(_tempPTI.getOptionType());
		tempItem.setPlaceholderTip(_tempPTI.getPlaceholderTip());
		tempItem.setRegExp(_tempPTI.getRegExp());
		tempItem.setRegExpExc(_tempPTI.getRegExpExc());
		tempItem.setStatus(_tempPTI.getStatus());
		tempItem.setTip(_tempPTI.getTip());
		tempItem.setType(_tempPTI.getType());
		tempItem.setValueSource(_tempPTI.getValueSource());
		tempItem.setCreateDate(_tempPTI.getCreateDate());
		tempItem.setUpdateDate(_tempPTI.getUpdateDate());
		return tempItem;
	}

	/**
	 * 增加元数据模板的转换方法
	 *
	 * @param _template
	 * @return
	 */
	private TemplateItem convertToTIOTD(PerTemplate _template){
		TemplateItem tempItem = new TemplateItem();
		tempItem.setId(_template.getId());
		tempItem.setCode(_template.getCode());
		tempItem.setName(_template.getName());
		tempItem.setStatus(_template.getStatus());
		tempItem.setType(_template.getType());
		tempItem.setCreateDate(_template.getCreateDate());
		tempItem.setUpdateDate(_template.getUpdateDate());
		return tempItem;
	}
}

