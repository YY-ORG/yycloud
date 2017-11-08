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
import com.yy.cloud.common.data.assess.AssessPaperItem;
import com.yy.cloud.common.data.dto.assess.AssessProfileReq;
import com.yy.cloud.common.data.dto.assess.AssessWithIDProfileReq;
import com.yy.cloud.common.data.dto.metadata.*;
import com.yy.cloud.common.data.metadata.TemplateItem;
import com.yy.cloud.common.data.metadata.TemplateItemItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessItem;
import com.yy.cloud.common.data.otd.metadata.SimpleTemplate;
import com.yy.cloud.common.data.otd.metadata.SimpleTemplateItem;
import com.yy.cloud.core.assess.data.domain.*;
import com.yy.cloud.core.assess.data.repositories.*;
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
	@Autowired
	private PerAssessAspMapRepository perAssessAspMapRepository;
	@Autowired
	private PerTemplateItemRepository perTemplateItemRepository;

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
		List<PerAssessPaper> tempAssessPaperList = this.perAssessPaperRepository.findByOrgIdAndStatus(_orgId, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);

		List<AssessMenuItem> tempAssessMenuItemList = new ArrayList<>();
		if(tempAssessPaperList == null || tempAssessPaperList.size() == 0){
			GeneralContentResult<List<AssessMenuItem>> tempResult = new GeneralContentResult<List<AssessMenuItem>>();
			tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
			tempResult.setResultContent(tempAssessMenuItemList);
			return tempResult;
		}

		PerAssessPaper tempAssessPaper = tempAssessPaperList.get(0);//默认获取第一个
		List<PerAssessAspMap> tempAssessAspMapList = tempAssessPaper.getPerAssessAspMaps();

		for(PerAssessAspMap tempAssessAspMapItem : tempAssessAspMapList){
			PerAssess tempAssess = tempAssessAspMapItem.getPerAssess();
			AssessMenuItem tempAssessMenuItem = new AssessMenuItem();
			tempAssessMenuItem.setAssessId(tempAssess.getId());
			tempAssessMenuItem.setAssessCode(tempAssess.getCode());
			tempAssessMenuItem.setAssessName(tempAssess.getName());
			tempAssessMenuItem.setSeqNo(tempAssessAspMapItem.getSeqNo());
			tempAssessMenuItemList.add(tempAssessMenuItem);
		}
		GeneralContentResult<List<AssessMenuItem>> tempResult = new GeneralContentResult<List<AssessMenuItem>>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		tempResult.setResultContent(tempAssessMenuItemList);
		return tempResult;
	}

	@Override
	public GeneralContentResult<List<AssessMenuItem>> getAssessMenuByAssessPaperId(String _assessPaperId) {
		List<PerAssessAspMap> tempAssessAspMapList = this.perAssessAspMapRepository.findByAssessPaperIdAndStatus(_assessPaperId, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);

		List<AssessMenuItem> tempAssessMenuItemList = new ArrayList<>();
		for(PerAssessAspMap tempAssessAspMapItem : tempAssessAspMapList){
			PerAssess tempAssess = tempAssessAspMapItem.getPerAssess();
			AssessMenuItem tempAssessMenuItem = new AssessMenuItem();
			tempAssessMenuItem.setAssessId(tempAssess.getId());
			tempAssessMenuItem.setAssessCode(tempAssess.getCode());
			tempAssessMenuItem.setAssessName(tempAssess.getName());
			tempAssessMenuItem.setSeqNo(tempAssessAspMapItem.getSeqNo());
			tempAssessMenuItemList.add(tempAssessMenuItem);
		}
		GeneralContentResult<List<AssessMenuItem>> tempResult = new GeneralContentResult<List<AssessMenuItem>>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		tempResult.setResultContent(tempAssessMenuItemList);
		return tempResult;
	}

	@Override
	public GeneralContentResult<List<AssessPaperItem>> getAssessPaperList(String _userId, String _orgId) {
		List<PerAssessPaper> tempAssessPaper = this.perAssessPaperRepository.findByOrgIdAndStatus(_orgId, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
		List<AssessPaperItem> tempAssessPaperItemList = new ArrayList<>();
		for(PerAssessPaper tempItem : tempAssessPaper){
			AssessPaperItem tempAssessPaperItem = new AssessPaperItem();
			tempAssessPaperItem.setId(tempItem.getId());
			tempAssessPaperItem.setCode(tempItem.getCode());
			tempAssessPaperItem.setName(tempItem.getName());
			tempAssessPaperItem.setOrgId(tempItem.getOrgId());
			tempAssessPaperItem.setStatus(tempItem.getStatus());
			tempAssessPaperItemList.add(tempAssessPaperItem);
		}

		GeneralContentResult<List<AssessPaperItem>> tempResult = new GeneralContentResult<List<AssessPaperItem>>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		tempResult.setResultContent(tempAssessPaperItemList);
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

	@Override
	public GeneralContentResult<SimpleAssessItem> createAssess(AssessProfileReq _profile) {
		GeneralContentResult<SimpleAssessItem> tempResult = new GeneralContentResult<>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		if(_profile == null)
			return tempResult;

		PerAssess tempAssess = new PerAssess();
		tempAssess.setCode(_profile.getCode());
		tempAssess.setName(_profile.getName());
		tempAssess.setType(_profile.getType());

		List<PerAssessTemplateMap> tempMapList = new ArrayList<>();
		if(_profile.getTemplateId() != null && _profile.getTemplateId().size() > 0){
			for(String tempTemplateId : _profile.getTemplateId()){
				PerAssessTemplateMap tempMapItem = new PerAssessTemplateMap();
				tempMapItem.setTemplateId(tempTemplateId);
				tempMapItem.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
				tempMapItem.setPerAssess(tempAssess);
				tempMapList.add(tempMapItem);
			}
			tempAssess.setPerAssessTemplateMaps(tempMapList);
		}
		PerAssess newAssess = this.perAssessRepository.save(tempAssess);

		SimpleAssessItem tempResultAssessItem = new SimpleAssessItem();
		tempResultAssessItem.setCode(newAssess.getCode());
		tempResultAssessItem.setName(newAssess.getName());
		tempResultAssessItem.setType(newAssess.getType());
		tempResultAssessItem.setType(newAssess.getType());
		tempResultAssessItem.setStatus(newAssess.getStatus());
		tempResultAssessItem.setId(newAssess.getId());

		tempResult.setResultContent(tempResultAssessItem);
		return tempResult;
	}

	@Override
	public GeneralContentResult<SimpleAssessItem> updateAssess(AssessWithIDProfileReq _profile) {
		GeneralContentResult<SimpleAssessItem> tempResult = new GeneralContentResult<>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		if(_profile == null)
			return tempResult;

		PerAssess tempAssess = this.perAssessRepository.findOne(_profile.getId());
		tempAssess.setCode(_profile.getCode());
		tempAssess.setName(_profile.getName());
		tempAssess.setType(_profile.getType());

		List<PerAssessTemplateMap> tempMapList = tempAssess.getPerAssessTemplateMaps();
		List<PerAssessTemplateMap> newMapList = new ArrayList<>();

		if(_profile.getTemplateId() != null && _profile.getTemplateId().size() > 0){
			for(String tempTemplateId : _profile.getTemplateId()){
				Boolean findFlag = false;
				for(PerAssessTemplateMap tempMap : tempMapList){
					if(tempTemplateId.equals(tempMap.getTemplateId())){
						findFlag = true;
						newMapList.add(tempMap);
						break;
					}
				}
				if(findFlag)
					continue;

				PerAssessTemplateMap tempMapItem = new PerAssessTemplateMap();
				tempMapItem.setTemplateId(tempTemplateId);
				tempMapItem.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
				tempMapItem.setPerAssess(tempAssess);
				newMapList.add(tempMapItem);
			}
		}
		tempAssess.setPerAssessTemplateMaps(newMapList);
		PerAssess newAssess = this.perAssessRepository.save(tempAssess);

		SimpleAssessItem tempResultAssessItem = new SimpleAssessItem();
		tempResultAssessItem.setCode(newAssess.getCode());
		tempResultAssessItem.setName(newAssess.getName());
		tempResultAssessItem.setType(newAssess.getType());
		tempResultAssessItem.setType(newAssess.getType());
		tempResultAssessItem.setStatus(newAssess.getStatus());
		tempResultAssessItem.setId(newAssess.getId());

		tempResult.setResultContent(tempResultAssessItem);
		return tempResult;
	}

	@Override
	public GeneralContentResult<SimpleTemplate> createAssessTemplate(TemplateProfileReq _req) {
		GeneralContentResult<SimpleTemplate> tempResult = new GeneralContentResult<>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		if(_req == null)
			return tempResult;

		PerTemplate tempTemplate = new PerTemplate();
		tempTemplate.setCode(_req.getCode());
		tempTemplate.setName(_req.getName());
		tempTemplate.setType(_req.getType());
		List<PerTemplateTiMap> tempMapList = new ArrayList<>();
		if(_req.getItemList() != null && _req.getItemList().size() > 0){
			for(TemplateItemMapReq tempTiReq : _req.getItemList()){
				PerTemplateTiMap tempTiMap = new PerTemplateTiMap();
				tempTiMap.setSeqNo(tempTiReq.getSeqNo());
				tempTiMap.setEditable(tempTiReq.getEditable());
				tempTiMap.setVisible(tempTiReq.getVisible());
				tempTiMap.setMandatory(tempTiReq.getMandatory());
				tempTiMap.setTemplateItemId(tempTiReq.getTemplateItemId());
				tempMapList.add(tempTiMap);
			}
			tempTemplate.setPerTemplateTiMaps(tempMapList);
		}

		PerTemplate newTemplate = this.perTemplateRepository.save(tempTemplate);
		SimpleTemplate tempResultTemplate = new SimpleTemplate();
		tempResultTemplate.setCode(newTemplate.getCode());
		tempResultTemplate.setName(newTemplate.getName());
		tempResultTemplate.setType(newTemplate.getType());
		tempResultTemplate.setId(newTemplate.getId());

		tempResult.setResultContent(tempResultTemplate);
		return tempResult;
	}

	@Override
	public GeneralContentResult<SimpleTemplateItem> createAssessTemplateItem(TemplateItemProfileReq _req) {
		PerTemplateItem tempTemplateItem = new PerTemplateItem();

		tempTemplateItem.setCode(_req.getCode());
		tempTemplateItem.setLabel(_req.getLabel());
		tempTemplateItem.setName(_req.getName());
		tempTemplateItem.setType(_req.getType());
		tempTemplateItem.setPlaceholderTip(_req.getPlaceHolder());
		tempTemplateItem.setTip(_req.getTip());
		tempTemplateItem.setDefaultValue(_req.getDefaultValue());
		tempTemplateItem.setValueSource(_req.getValueSource());
		tempTemplateItem.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);

		PerTemplateItem templateItem = this.perTemplateItemRepository.save(tempTemplateItem);
		SimpleTemplateItem resultTemplateItem = new SimpleTemplateItem();
		resultTemplateItem.setId(templateItem.getId());
		resultTemplateItem.setCode(templateItem.getCode());
		resultTemplateItem.setLabel(templateItem.getLabel());
		resultTemplateItem.setName(templateItem.getName());
		resultTemplateItem.setDefaultValue(templateItem.getDefaultValue());
		resultTemplateItem.setValueSource(templateItem.getValueSource());
		resultTemplateItem.setPlaceHolder(templateItem.getPlaceholderTip());
		resultTemplateItem.setTip(templateItem.getTip());
		resultTemplateItem.setStatus(templateItem.getStatus());

		GeneralContentResult<SimpleTemplateItem> tempResult = new GeneralContentResult<>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		tempResult.setResultContent(resultTemplateItem);
		return tempResult;
	}

    @Override
    public GeneralContentResult<SimpleTemplate> updateAssessTemplate(TemplateWithIDProfileReq _req) {
        GeneralContentResult<SimpleTemplate> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        if(_req == null)
            return tempResult;

        PerTemplate tempTemplate = this.perTemplateRepository.findOne(_req.getId());
        tempTemplate.setCode(_req.getCode());
        tempTemplate.setName(_req.getName());
        tempTemplate.setType(_req.getType());
        List<PerTemplateTiMap> existsMapList = tempTemplate.getPerTemplateTiMaps();

        List<PerTemplateTiMap> tempMapList = new ArrayList<>();

        if(_req.getItemList() != null && _req.getItemList().size() > 0){
            for(TemplateItemMapReq tempTiReq : _req.getItemList()){
                PerTemplateTiMap tempTiMap = new PerTemplateTiMap();
                tempTiMap.setSeqNo(tempTiReq.getSeqNo());
                tempTiMap.setEditable(tempTiReq.getEditable());
                tempTiMap.setVisible(tempTiReq.getVisible());
                tempTiMap.setMandatory(tempTiReq.getMandatory());
                tempTiMap.setTemplateItemId(tempTiReq.getTemplateItemId());
                tempMapList.add(tempTiMap);
            }
            tempTemplate.setPerTemplateTiMaps(tempMapList);
        }

        PerTemplate newTemplate = this.perTemplateRepository.save(tempTemplate);
        SimpleTemplate tempResultTemplate = new SimpleTemplate();
        tempResultTemplate.setCode(newTemplate.getCode());
        tempResultTemplate.setName(newTemplate.getName());
        tempResultTemplate.setType(newTemplate.getType());
        tempResultTemplate.setId(newTemplate.getId());

        tempResult.setResultContent(tempResultTemplate);
        return tempResult;
    }

    @Override
    public GeneralContentResult<SimpleTemplateItem> updateAssessTemplateItem(TemplateItemWithIDProfileReq _req) {
        PerTemplateItem tempTemplateItem = this.perTemplateItemRepository.findOne(_req.getId());

        tempTemplateItem.setCode(_req.getCode());
        tempTemplateItem.setLabel(_req.getLabel());
        tempTemplateItem.setName(_req.getName());
        tempTemplateItem.setType(_req.getType());
        tempTemplateItem.setPlaceholderTip(_req.getPlaceHolder());
        tempTemplateItem.setTip(_req.getTip());
        tempTemplateItem.setDefaultValue(_req.getDefaultValue());
        tempTemplateItem.setValueSource(_req.getValueSource());
        tempTemplateItem.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);

        PerTemplateItem templateItem = this.perTemplateItemRepository.save(tempTemplateItem);
        SimpleTemplateItem resultTemplateItem = new SimpleTemplateItem();
        resultTemplateItem.setId(templateItem.getId());
        resultTemplateItem.setCode(templateItem.getCode());
        resultTemplateItem.setLabel(templateItem.getLabel());
        resultTemplateItem.setName(templateItem.getName());
        resultTemplateItem.setDefaultValue(templateItem.getDefaultValue());
        resultTemplateItem.setValueSource(templateItem.getValueSource());
        resultTemplateItem.setPlaceHolder(templateItem.getPlaceholderTip());
        resultTemplateItem.setTip(templateItem.getTip());
        resultTemplateItem.setStatus(templateItem.getStatus());

        GeneralContentResult<SimpleTemplateItem> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(resultTemplateItem);
        return tempResult;
    }
}

