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
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.assess.AssessItem;
import com.yy.cloud.common.data.assess.AssessMenuItem;
import com.yy.cloud.common.data.assess.AssessPaperItem;
import com.yy.cloud.common.data.dto.assess.*;
import com.yy.cloud.common.data.dto.metadata.*;
import com.yy.cloud.common.data.metadata.TemplateItem;
import com.yy.cloud.common.data.metadata.TemplateItemItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessPaperItem;
import com.yy.cloud.common.data.otd.metadata.ComplexTemplateItem;
import com.yy.cloud.common.data.otd.metadata.SimpleTemplate;
import com.yy.cloud.common.data.otd.metadata.SimpleTemplateItem;
import com.yy.cloud.core.assess.data.domain.*;
import com.yy.cloud.core.assess.data.repositories.*;
import com.yy.cloud.core.assess.service.AssessMgmtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private PerTemplateTiMapRepository perTemplateTiMapRepository;
	@Autowired
	private PerAssessPaperRepository perAssessPaperRepository;
	@Autowired
	private PerAssessAspMapRepository perAssessAspMapRepository;
	@Autowired
	private PerTemplateItemRepository perTemplateItemRepository;
	@Autowired
    private	PerAssessTemplateMapRepository perAssessTemplateMapRepository;

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
		tempAssessMenuItemList = tempAssessAspMapList.stream().map(this::convertToAssessMenuOTD).collect(Collectors.toList());

		GeneralContentResult<List<AssessMenuItem>> tempResult = new GeneralContentResult<List<AssessMenuItem>>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		tempResult.setResultContent(tempAssessMenuItemList);
		return tempResult;
	}

	@Override
	public GeneralContentResult<List<AssessMenuItem>> getAssessMenuByAssessPaperId(String _assessPaperId) {
		List<PerAssessAspMap> tempAssessAspMapList = this.perAssessAspMapRepository.findByAssessPaperIdAndStatus(_assessPaperId, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
		List<AssessMenuItem> tempAssessMenuItemList = tempAssessAspMapList.stream().map(this::convertToAssessMenuOTD).collect(Collectors.toList());

		GeneralContentResult<List<AssessMenuItem>> tempResult = new GeneralContentResult<List<AssessMenuItem>>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		tempResult.setResultContent(tempAssessMenuItemList);
		return tempResult;
	}

	@Override
	public GeneralContentResult<List<AssessMenuItem>> getAssessMenuByAssessPaperIdAndGroup(String _assessPaperId, String _groupId) {
		return null;
	}

	/**
	 * 转换考卷分组
	 *
	 * @param _tempMap
	 * @return
	 */
	private AssessMenuItem convertToAssessMenuOTD(PerAssessAspMap _tempMap){
		PerAssess tempAssess = _tempMap.getPerAssess();
		AssessMenuItem tempAssessMenuItem = new AssessMenuItem();
		tempAssessMenuItem.setAssessId(tempAssess.getId());
		tempAssessMenuItem.setAssessCode(tempAssess.getCode());
		tempAssessMenuItem.setAssessName(tempAssess.getName());
		tempAssessMenuItem.setSeqNo(_tempMap.getSeqNo());
		return tempAssessMenuItem;
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
     * 转换题所对应的简单的Obj
     *
     * @param _assess
     * @return
     */
    private SimpleAssessItem convertToSPAOTD(PerAssess _assess) {
        SimpleAssessItem tempItem = new SimpleAssessItem();

        tempItem.setId(_assess.getId());
        tempItem.setCode(_assess.getCode());
        tempItem.setName(_assess.getName());
        tempItem.setStatus(_assess.getStatus());
        tempItem.setType(_assess.getType());
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
		tempItem.setValueOwner(_tempPTI.getValueOwner());
		tempItem.setValueField(_tempPTI.getValueField());
		tempItem.setCreateDate(_tempPTI.getCreateDate());
		tempItem.setUpdateDate(_tempPTI.getUpdateDate());
		return tempItem;
	}

    /**
     * 转换简单元数据模板元素的方法
     *
     * @param _tempPTI
     * @return
     */
	private SimpleTemplateItem convertToSTIIOTD(PerTemplateItem _tempPTI){
        SimpleTemplateItem tempItem = new SimpleTemplateItem();
        tempItem.setId(_tempPTI.getId());
        tempItem.setCode(_tempPTI.getCode());
        tempItem.setName(_tempPTI.getName());
        tempItem.setLabel(_tempPTI.getLabel());
        tempItem.setPlaceholderTip(_tempPTI.getPlaceholderTip());
        tempItem.setStatus(_tempPTI.getStatus());
        tempItem.setTip(_tempPTI.getTip());
        tempItem.setType(_tempPTI.getType());
        tempItem.setValueField(_tempPTI.getValueField());
        tempItem.setValueOwner(_tempPTI.getValueOwner());
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

    /**
     * 增加转换简单元数据模板的方法
     *
     * @param _template
     * @return
     */
	private SimpleTemplate convertToSTIOTD(PerTemplate _template){
        SimpleTemplate tempItem = new SimpleTemplate();
        tempItem.setId(_template.getId());
        tempItem.setCode(_template.getCode());
        tempItem.setName(_template.getName());
        tempItem.setStatus(_template.getStatus());
        tempItem.setType(_template.getType());
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
        tempAssess.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);

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
        tempAssess.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
        this.perAssessTemplateMapRepository.deleteByPerAssess(tempAssess);
	//	List<PerAssessTemplateMap> tempMapList = tempAssess.getPerAssessTemplateMaps();
		List<PerAssessTemplateMap> newMapList = new ArrayList<>();

		if(_profile.getTemplateId() != null && _profile.getTemplateId().size() > 0){
			for(String tempTemplateId : _profile.getTemplateId()){
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
        tempTemplate.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
		List<PerTemplateTiMap> tempMapList = new ArrayList<>();
		if(_req.getItemList() != null && _req.getItemList().size() > 0){
			for(TemplateItemMapReq tempTiReq : _req.getItemList()){
				PerTemplateTiMap tempTiMap = new PerTemplateTiMap();
				tempTiMap.setSeqNo(tempTiReq.getSeqNo());
				tempTiMap.setEditable(tempTiReq.getEditable());
				tempTiMap.setVisible(tempTiReq.getVisible());
				tempTiMap.setMandatory(tempTiReq.getMandatory());
				tempTiMap.setTemplateItemId(tempTiReq.getTemplateItemId());
                tempTiMap.setPerTemplate(tempTemplate);
                tempTiMap.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
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
    public GeneralContentResult<SimpleTemplate> updateAssessTemplate(TemplateWithIDProfileReq _req) {
        GeneralContentResult<SimpleTemplate> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        if(_req == null)
            return tempResult;

        PerTemplate tempTemplate = this.perTemplateRepository.findOne(_req.getId());
        tempTemplate.setCode(_req.getCode());
        tempTemplate.setName(_req.getName());
        tempTemplate.setType(_req.getType());
        tempTemplate.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
      //  List<PerTemplateTiMap> existsMapList = tempTemplate.getPerTemplateTiMaps();
        this.perTemplateTiMapRepository.deleteByTemplateId(_req.getId());

        List<PerTemplateTiMap> tempMapList = new ArrayList<>();

        if(_req.getItemList() != null && _req.getItemList().size() > 0){
            for(TemplateItemMapReq tempTiReq : _req.getItemList()){
                PerTemplateTiMap tempTiMap = new PerTemplateTiMap();
                tempTiMap.setSeqNo(tempTiReq.getSeqNo());
                tempTiMap.setEditable(tempTiReq.getEditable());
                tempTiMap.setVisible(tempTiReq.getVisible());
                tempTiMap.setMandatory(tempTiReq.getMandatory());
                tempTiMap.setTemplateItemId(tempTiReq.getTemplateItemId());
                tempTiMap.setPerTemplate(tempTemplate);
                tempTiMap.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
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
    public GeneralPagingResult<List<SimpleTemplate>> getAssessTemplateList(Pageable _page) {
	    Page<PerTemplate> templatePage = this.perTemplateRepository.findAll(_page);

        List<SimpleTemplate> tempTemplateList = templatePage.getContent().stream().map(this::convertToSTIOTD).collect(Collectors.toList());
        GeneralPagingResult<List<SimpleTemplate>> tempResult = new GeneralPagingResult<>();
        tempResult.setResultContent(tempTemplateList);

        PageInfo _pageInfo = new PageInfo();
        _pageInfo.setCurrentPage(templatePage.getNumber());
        _pageInfo.setPageSize(templatePage.getSize());
        _pageInfo.setTotalPage(templatePage.getTotalPages());
        _pageInfo.setTotalRecords(templatePage.getTotalElements());
        tempResult.setPageInfo(_pageInfo);
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        return tempResult;
    }

    @Override
    public GeneralPagingResult<List<SimpleTemplate>> getAssessTemplateList(Byte _type, Pageable _page) {
        Page<PerTemplate> templatePage = this.perTemplateRepository.findByTypeAndStatusOrderByNameAsc(_type, CommonConstant.DIC_GLOBAL_STATUS_ENABLE, _page);

        List<SimpleTemplate> tempTemplateList = templatePage.getContent().stream().map(this::convertToSTIOTD).collect(Collectors.toList());
        GeneralPagingResult<List<SimpleTemplate>> tempResult = new GeneralPagingResult<>();
        tempResult.setResultContent(tempTemplateList);

        PageInfo _pageInfo = new PageInfo();
        _pageInfo.setCurrentPage(templatePage.getNumber());
        _pageInfo.setPageSize(templatePage.getSize());
        _pageInfo.setTotalPage(templatePage.getTotalPages());
        _pageInfo.setTotalRecords(templatePage.getTotalElements());
        tempResult.setPageInfo(_pageInfo);
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        return tempResult;
    }

    @Override
    public GeneralContentResult<List<SimpleTemplate>> getAssessTemplateByAssess(String _assessId) {
	    List<PerTemplate> templateList = this.perTemplateRepository.getListByAssess(_assessId);

        List<SimpleTemplate> tempSTempList = templateList.stream().map(this::convertToSTIOTD).collect(Collectors.toList());
        GeneralContentResult<List<SimpleTemplate>> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempSTempList);

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
        tempTemplateItem.setValueOwner(_req.getValueOwner());
        tempTemplateItem.setValueField(_req.getValueField());
        tempTemplateItem.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);

        PerTemplateItem templateItem = this.perTemplateItemRepository.save(tempTemplateItem);
        SimpleTemplateItem resultTemplateItem = new SimpleTemplateItem();
        resultTemplateItem.setId(templateItem.getId());
        resultTemplateItem.setCode(templateItem.getCode());
        resultTemplateItem.setLabel(templateItem.getLabel());
        resultTemplateItem.setName(templateItem.getName());
        resultTemplateItem.setDefaultValue(templateItem.getDefaultValue());
        resultTemplateItem.setValueOwner(templateItem.getValueOwner());
        resultTemplateItem.setValueField(templateItem.getValueField());
        resultTemplateItem.setPlaceholderTip(templateItem.getPlaceholderTip());
        resultTemplateItem.setTip(templateItem.getTip());
        resultTemplateItem.setStatus(templateItem.getStatus());

        GeneralContentResult<SimpleTemplateItem> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(resultTemplateItem);
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
        tempTemplateItem.setValueOwner(_req.getValueOwner());
        tempTemplateItem.setValueField(_req.getValueField());
        tempTemplateItem.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);

        PerTemplateItem templateItem = this.perTemplateItemRepository.save(tempTemplateItem);
        SimpleTemplateItem resultTemplateItem = new SimpleTemplateItem();
        resultTemplateItem.setId(templateItem.getId());
        resultTemplateItem.setCode(templateItem.getCode());
        resultTemplateItem.setLabel(templateItem.getLabel());
        resultTemplateItem.setName(templateItem.getName());
        resultTemplateItem.setDefaultValue(templateItem.getDefaultValue());
        resultTemplateItem.setValueOwner(templateItem.getValueOwner());
        resultTemplateItem.setValueField(templateItem.getValueField());
        resultTemplateItem.setPlaceholderTip(templateItem.getPlaceholderTip());
        resultTemplateItem.setTip(templateItem.getTip());
        resultTemplateItem.setStatus(templateItem.getStatus());

        GeneralContentResult<SimpleTemplateItem> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(resultTemplateItem);
        return tempResult;
    }

    @Override
    public GeneralPagingResult<List<SimpleTemplateItem>> getAssessTemplateItemList(Pageable _page) {
        Page<PerTemplateItem> templatePage = this.perTemplateItemRepository.findAll(_page);

        List<SimpleTemplateItem> tempTemplateItemList = templatePage.getContent().stream().map(this::convertToSTIIOTD).collect(Collectors.toList());
        GeneralPagingResult<List<SimpleTemplateItem>> tempResult = new GeneralPagingResult<>();
        tempResult.setResultContent(tempTemplateItemList);

        PageInfo _pageInfo = new PageInfo();
        _pageInfo.setCurrentPage(templatePage.getNumber());
        _pageInfo.setPageSize(templatePage.getSize());
        _pageInfo.setTotalPage(templatePage.getTotalPages());
        _pageInfo.setTotalRecords(templatePage.getTotalElements());
        tempResult.setPageInfo(_pageInfo);
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        return tempResult;
    }

    @Override
    public GeneralContentResult<List<ComplexTemplateItem>> getAssessTemplateItemByTemplate(String _templateId) {
	    List<PerTemplateTiMap> templateTiMapList = this.perTemplateTiMapRepository.findByTemplateId(_templateId);

        List<ComplexTemplateItem> tempSTempList = templateTiMapList.stream().map(this::convertToCTIOTD).collect(Collectors.toList());

//        List<PerTemplateItem> templateList = this.perTemplateItemRepository.getTemplateItemByTemplate(_templateId);
//        log.info("Total records for [{}] is [{}].", _templateId, templateList.size());
//        List<SimpleTemplateItem> tempSTempList = templateList.stream().map(this::convertToSTIIOTD).collect(Collectors.toList());
        GeneralContentResult<List<ComplexTemplateItem>> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempSTempList);

        return tempResult;
    }

    private ComplexTemplateItem convertToCTIOTD(PerTemplateTiMap _tiMap){
        ComplexTemplateItem tempItem = new ComplexTemplateItem();
        tempItem.setEditable(_tiMap.isEditable());
        tempItem.setMandatory(_tiMap.isMandatory());
        tempItem.setVisible(_tiMap.isVisible());
        tempItem.setSeqNo(_tiMap.getSeqNo());

        if(StringUtils.isNotBlank(_tiMap.getReliedId())){//获取真实的Relied的Item ID
            PerTemplateTiMap tempTiMap = this.perTemplateTiMapRepository.findOne(_tiMap.getReliedId());
            tempItem.setReliedId(tempTiMap.getTemplateItemId());
        }

        PerTemplateItem tempTemplateItem = _tiMap.getPerTemplateItem();
        tempItem.setId(tempTemplateItem.getId());
        tempItem.setCode(tempTemplateItem.getCode());
        tempItem.setName(tempTemplateItem.getName());
        tempItem.setLabel(tempTemplateItem.getLabel());
        tempItem.setPlaceholderTip(tempTemplateItem.getPlaceholderTip());
        tempItem.setStatus(tempTemplateItem.getStatus());
        tempItem.setTip(tempTemplateItem.getTip());
        tempItem.setType(tempTemplateItem.getType());
        tempItem.setValueField(tempTemplateItem.getValueField());
        tempItem.setValueOwner(tempTemplateItem.getValueOwner());
        return tempItem;
    }
    @Transactional
	@Override
	public GeneralResult deleteAssess(String _assessId) {
		PerAssess tempAssess = this.perAssessRepository.getOne(_assessId);
//		List<PerAssessTemplateMap> tempItempMap = tempAssess.getPerAssessTemplateMaps();
//		this.perAssessTemplateMapRepository.deleteInBatch(tempItempMap);
		this.perAssessTemplateMapRepository.deleteByPerAssess(tempAssess);
		this.perAssessRepository.delete(tempAssess);
		GeneralResult tempResult = new GeneralResult();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

		return tempResult;
	}
    @Transactional
	@Override
	public GeneralResult deleteAssessTemplate(String _assessTemplateId) {
		this.perAssessTemplateMapRepository.deleteByTemplateId(_assessTemplateId);
		this.perTemplateRepository.delete(_assessTemplateId);
		GeneralResult tempResult = new GeneralResult();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		return tempResult;
	}
    @Transactional
	@Override
	public GeneralResult deleteAssessTemplateItem(String _assessTemplateItemId) {
        this.perTemplateTiMapRepository.deleteByTemplateItemId(_assessTemplateItemId);
        this.perTemplateItemRepository.delete(_assessTemplateItemId);
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		return tempResult;
	}

	@Override
	public GeneralPagingResult<List<SimpleAssessItem>> getAssessList(Pageable _page) {
        Page<PerAssess> assessPage = this.perAssessRepository.findAll(_page);

        List<SimpleAssessItem> tempAssessList = assessPage.getContent().stream().map(this::convertToSPAOTD).collect(Collectors.toList());
        GeneralPagingResult<List<SimpleAssessItem>> tempResult = new GeneralPagingResult<>();
        tempResult.setResultContent(tempAssessList);

        PageInfo _pageInfo = new PageInfo();
        _pageInfo.setCurrentPage(assessPage.getNumber());
        _pageInfo.setPageSize(assessPage.getSize());
        _pageInfo.setTotalPage(assessPage.getTotalPages());
        _pageInfo.setTotalRecords(assessPage.getTotalElements());
        tempResult.setPageInfo(_pageInfo);
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

		return tempResult;
	}

	@Override
	public GeneralPagingResult<List<SimpleAssessItem>> getAssessListByAssessPaper(String _assessPaperId, Pageable _page) {
        Page<PerAssess> assessPage = this.perAssessRepository.getAssessByAsp(_assessPaperId, _page);
        List<SimpleAssessItem> tempAssessList = assessPage.getContent().stream().map(this::convertToSPAOTD).collect(Collectors.toList());
        GeneralPagingResult<List<SimpleAssessItem>> tempResult = new GeneralPagingResult<>();
        tempResult.setResultContent(tempAssessList);

        PageInfo _pageInfo = new PageInfo();
        _pageInfo.setCurrentPage(assessPage.getNumber());
        _pageInfo.setPageSize(assessPage.getSize());
        _pageInfo.setTotalPage(assessPage.getTotalPages());
        _pageInfo.setTotalRecords(assessPage.getTotalElements());
        tempResult.setPageInfo(_pageInfo);
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
	    return null;
	}

	@Override
	public GeneralContentResult<SimpleAssessPaperItem> createAssessPaper(AssessPaperProfileReq _req) {
        GeneralContentResult<SimpleAssessPaperItem> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        if(_req == null)
            return tempResult;

        PerAssessPaper tempPAP = new PerAssessPaper();
        tempPAP.setCode(_req.getCode());
        tempPAP.setName(_req.getName());
        tempPAP.setOrgId(_req.getOrgId());
        tempPAP.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
        tempPAP.setTitleType(_req.getTitle());
        List<PerAssessAspMap> tempPAAMapList = _req.getAssessList().stream().map(tempItem -> this.convertToAssessAspDTO(tempItem, tempPAP)).collect(Collectors.toList());
        tempPAP.setPerAssessAspMaps(tempPAAMapList);

        PerAssessPaper resultPAP = this.perAssessPaperRepository.save(tempPAP);
        SimpleAssessPaperItem tempASPI = new SimpleAssessPaperItem();
        tempASPI.setId(resultPAP.getId());
        tempASPI.setCode(resultPAP.getCode());
        tempASPI.setName(resultPAP.getName());
        tempASPI.setOrgId(resultPAP.getOrgId());
        tempASPI.setStatus(resultPAP.getStatus());
        tempASPI.setTitle(resultPAP.getTitleType());
        tempResult.setResultContent(tempASPI);

	    return tempResult;
	}

	@Override
	public GeneralContentResult<SimpleAssessPaperItem> updateAssessPaper(AssessPaperWithIDProfileReq _req) {
        GeneralContentResult<SimpleAssessPaperItem> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        if(_req == null)
            return tempResult;


		this.perAssessAspMapRepository.deletePerAssessAspMapsByAssessPaperId(_req.getId());
        for(String tempOrg : _req.getOrgId()){
        	for(Byte tempTitle : _req.getTitle()){
				PerAssessPaper tempPAP = this.perAssessPaperRepository.findOne(_req.getId());
				tempPAP.setCode(_req.getCode());
				tempPAP.setName(_req.getName());
				tempPAP.setOrgId(tempOrg);
				tempPAP.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
				tempPAP.setTitleType(tempTitle);
				List<PerAssessAspMap> tempPAAMapList = _req.getAssessList().stream().map(tempItem -> this.convertToAssessAspDTO(tempItem, tempPAP)).collect(Collectors.toList());
				tempPAP.setPerAssessAspMaps(tempPAAMapList);
			}
		}

        PerAssessPaper resultPAP = this.perAssessPaperRepository.save(tempPAP);
        SimpleAssessPaperItem tempASPI = new SimpleAssessPaperItem();
        tempASPI.setId(resultPAP.getId());
        tempASPI.setCode(resultPAP.getCode());
        tempASPI.setName(resultPAP.getName());
        tempASPI.setOrgId(_req.getOrgId());
        tempASPI.setStatus(resultPAP.getStatus());
        tempASPI.setTitle(_req.getTitle());
        tempResult.setResultContent(tempASPI);

        return tempResult;
	}

	/**
	 * 转换题和卷子及卷子分组的关系
	 *
	 * @param _assessReq
	 * @return
	 */
	private PerAssessAspMap convertToAssessAspDTO(SimpleAssessReq _assessReq, PerAssessPaper _pap){
		PerAssessAspMap tempPAAM = new PerAssessAspMap();
		tempPAAM.setAssessId(_assessReq.getAssessId());
		tempPAAM.setAssessCategoryId(_assessReq.getAssessCategoryId());
		tempPAAM.setSeqNo(_assessReq.getSeqNo());
		tempPAAM.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
		tempPAAM.setPerAssessPaper(_pap);
		return tempPAAM;
	}

    @Transactional
	@Override
	public GeneralResult deleteAssessPaper(String _assessPaperId) {
        this.perAssessAspMapRepository.deletePerAssessAspMapsByAssessPaperId(_assessPaperId);
        this.perAssessPaperRepository.delete(_assessPaperId);

        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        return tempResult;
	}

	@Override
	public GeneralPagingResult<List<SimpleAssessPaperItem>> getAssessPaperList(Pageable _page) {
	    Page<PerAssessPaper> tempPAPPage = this.perAssessPaperRepository.findAll(_page);
        List<SimpleAssessPaperItem> tempSAPI = tempPAPPage.getContent().stream().map(this::convertToAPIOTD).collect(Collectors.toList());
        GeneralPagingResult<List<SimpleAssessPaperItem>> tempResult = new GeneralPagingResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempSAPI);

        PageInfo _pageInfo = new PageInfo();
        _pageInfo.setCurrentPage(tempPAPPage.getNumber());
        _pageInfo.setPageSize(tempPAPPage.getSize());
        _pageInfo.setTotalPage(tempPAPPage.getTotalPages());
        _pageInfo.setTotalRecords(tempPAPPage.getTotalElements());
        tempResult.setPageInfo(_pageInfo);

		return tempResult;
	}

    @Override
    public GeneralPagingResult<List<SimpleAssessPaperItem>> getAssessPaperListByOrg(String _orgId, Pageable _page) {
        Page<PerAssessPaper> tempPAPPage = this.perAssessPaperRepository.findByOrgIdAndAndStatus(_orgId, CommonConstant.DIC_GLOBAL_STATUS_ENABLE, _page);
        List<SimpleAssessPaperItem> tempSAPI = tempPAPPage.getContent().stream().map(this::convertToAPIOTD).collect(Collectors.toList());
        GeneralPagingResult<List<SimpleAssessPaperItem>> tempResult = new GeneralPagingResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempSAPI);

        PageInfo _pageInfo = new PageInfo();
        _pageInfo.setCurrentPage(tempPAPPage.getNumber());
        _pageInfo.setPageSize(tempPAPPage.getSize());
        _pageInfo.setTotalPage(tempPAPPage.getTotalPages());
        _pageInfo.setTotalRecords(tempPAPPage.getTotalElements());
        tempResult.setPageInfo(_pageInfo);

        return tempResult;
    }

    /**
     * Convert the PerAssessPaper to SimpleAssessPaper.
     *
     * @param _pap
     * @return
     */
	private SimpleAssessPaperItem convertToAPIOTD(PerAssessPaper _pap) {
        SimpleAssessPaperItem tempItem = new SimpleAssessPaperItem();
        tempItem.setId(_pap.getId());
        tempItem.setCode(_pap.getCode());
        tempItem.setName(_pap.getName());
        tempItem.setStatus(_pap.getStatus());
        tempItem.setOrgId(_pap.getOrgId());
        tempItem.setTitle(_pap.getTitleType());
        return tempItem;
    }

	@Override
	public GeneralContentResult<List<SimpleTemplate>> getTemplateListByType(Byte _type) {
		List<PerTemplate> templateList = this.perTemplateRepository.findByTypeAndStatusOrderByNameAsc(_type, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
		List<SimpleTemplate> tempTemplateList = templateList.stream().map(this::convertToSTIOTD).collect(Collectors.toList());
		GeneralContentResult<List<SimpleTemplate>> tempResult = new GeneralContentResult<>();
		tempResult.setResultContent(tempTemplateList);
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		return tempResult;
	}
}

