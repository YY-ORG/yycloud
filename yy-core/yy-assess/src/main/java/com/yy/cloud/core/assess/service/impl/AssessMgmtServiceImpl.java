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
import com.yy.cloud.common.data.assess.*;
import com.yy.cloud.common.data.dto.assess.*;
import com.yy.cloud.common.data.dto.metadata.*;
import com.yy.cloud.common.data.metadata.TemplateItem;
import com.yy.cloud.common.data.metadata.TemplateItemItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessPaperItem;
import com.yy.cloud.common.data.otd.metadata.ComplexTemplateItem;
import com.yy.cloud.common.data.otd.metadata.SimpleTemplate;
import com.yy.cloud.common.data.otd.metadata.SimpleTemplateItem;
import com.yy.cloud.common.utils.YYException;
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

import java.util.*;
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
	@Autowired
	private PerAssessOrgMapRepository perAssessOrgMapRepository;
	@Autowired
	private PerApAcMapRepository perApAcMapRepository;
	@Autowired
	private PerAssessAnswerRepository perAssessAnswerRepository;
	@Autowired
	private PerAspProcessOverviewRepository perAspProcessOverviewRepository;

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
		List<PerAssessPaper> tempAssessPaperList = this.perAssessPaperRepository.findByStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);

		List<AssessMenuItem> tempAssessMenuItemList = new ArrayList<>();
		if(tempAssessPaperList == null || tempAssessPaperList.size() == 0){
			GeneralContentResult<List<AssessMenuItem>> tempResult = new GeneralContentResult<List<AssessMenuItem>>();
			tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
			tempResult.setResultContent(tempAssessMenuItemList);
			return tempResult;
		}

		PerAssessPaper tempAssessPaper = tempAssessPaperList.get(0);//默认获取第一个
		List<PerAssessAspMap> tempAssessAspMapList = tempAssessPaper.getPerAssessAspMaps();
		tempAssessMenuItemList = tempAssessAspMapList.stream().map(tempItem -> this.convertToAssessMenuOTD(_userId, tempItem)).collect(Collectors.toList());

		GeneralContentResult<List<AssessMenuItem>> tempResult = new GeneralContentResult<>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		tempResult.setResultContent(tempAssessMenuItemList);
		return tempResult;
	}

	@Override
	public GeneralContentResult<List<AssessGroupItem>> getAssessMenuByAssessPaperId(String _userId, String _assessPaperId) {
		List<PerAssessAspMap> tempAssessAspMapList = this.perAssessAspMapRepository.findByAssessPaperIdAndStatusOrderByCreateDateAsc(_assessPaperId, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);

		Map<AssessCategoryItem, List<AssessMenuItem>> tempMenuMap = tempAssessAspMapList.stream().collect(Collectors.groupingBy(this::convertToACIOTD,
				Collectors.mapping(tempItem -> this.convertToAssessMenuOTD(_userId, tempItem), Collectors.toList())));
		List<AssessGroupItem> tempAssessMenuItemList = tempMenuMap.entrySet().stream().map(tempItem -> this.packGroupOTD(_userId, _assessPaperId, tempItem.getKey(), tempItem.getValue())).collect(Collectors.toList());
		GeneralContentResult<List<AssessGroupItem>> tempResult = new GeneralContentResult<>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		tempResult.setResultContent(tempAssessMenuItemList.stream().sorted(Comparator.comparing(AssessGroupItem::getCreateDate)).collect(Collectors.toList()));
		return tempResult;
	}

	@Override
	public GeneralContentResult<List<AssessGroupItem>> getAssessMenuByAssessPaperIdAndGroup(String _userId, String _assessPaperId, String _groupId) {
		List<PerAssessAspMap> tempAssessAspMapList = this.perAssessAspMapRepository.findByAssessPaperIdAndAssessCategoryIdAndStatusOrderByCreateDateAsc(_assessPaperId, _groupId, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
		Map<AssessCategoryItem, List<AssessMenuItem>> tempMenuMap = tempAssessAspMapList.stream().collect(Collectors.groupingBy(this::convertToACIOTD,
				Collectors.mapping(tempItem -> this.convertToAssessMenuOTD(_userId, tempItem), Collectors.toList())));
		List<AssessGroupItem> tempAssessMenuItemList = tempMenuMap.entrySet().stream().map(tempItem -> this.packGroupOTD(_userId, _assessPaperId, tempItem.getKey(), tempItem.getValue())).collect(Collectors.toList());
		GeneralContentResult<List<AssessGroupItem>> tempResult = new GeneralContentResult<>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		tempResult.setResultContent(tempAssessMenuItemList.stream().sorted(Comparator.comparing(AssessGroupItem::getCreateDate)).collect(Collectors.toList()));
		return tempResult;
	}

	/**
	 * 封装题的组
	 *
	 * @param _aciItem
	 * @param _tempList
	 * @return
	 */
	private AssessGroupItem packGroupOTD(String _userId, String _assessPaperId, AssessCategoryItem _aciItem, List<AssessMenuItem> _tempList){
		AssessGroupItem tempItem = new AssessGroupItem();
		tempItem.setId(_aciItem.getId());
		tempItem.setCode(_aciItem.getCode());
		tempItem.setName(_aciItem.getName());
		tempItem.setAssessItemList(_tempList);
		tempItem.setCreateDate(_aciItem.getCreateDate());
//		Optional<PerAspProcessOverview> tempOverviewOpt = this.perAspProcessOverviewRepository.findByAssessPaperIdAndCategoryIdAndCreatorId(
//				_assessPaperId, _aciItem.getId(), _userId);
//		PerAspProcessOverview tempOverview;
//		if(tempOverviewOpt.isPresent()){
//			tempOverview = tempOverviewOpt.get();
//			tempOverview
//		} else {
//			tempOverview = new PerAspProcessOverview();
//			tempOverview.setAssessPaperId(_assessPaperId);
//			tempOverview.setCategoryId(_groupId);
//			tempOverview.setCreatorId(_userId);
//			tempOverview.setAssessCount(this.perAssessAspMapRepository.countByAssessPaperIdAndAssessCategoryId(
//					_assessPaperId, _groupId));
//		}
//		this.perAspProcessOverviewRepository.save(tempOverview);
		return tempItem;
	}

	/**
	 * 转换题的组
	 *
	 * @param _paam
	 * @return
	 */
	private AssessCategoryItem convertToACIOTD(PerAssessAspMap _paam){
		PerAssessCategory tempGroup = _paam.getPerAssessCategory();
		AssessCategoryItem tempItem = new AssessCategoryItem();
		if(tempGroup == null)
			return tempItem;
		tempItem.setId(tempGroup.getId());
		tempItem.setCode(tempGroup.getCode());
		tempItem.setName(tempGroup.getName());
		tempItem.setStatus(tempGroup.getStatus());
		tempItem.setCreateDate(tempGroup.getCreateDate());
		return tempItem;
	}

	/**
	 * 转换考卷分组
	 *
	 * @param _tempMap
	 * @return
	 */
	private AssessMenuItem convertToAssessMenuOTD(String _userId, PerAssessAspMap _tempMap){
		PerAssess tempAssess = _tempMap.getPerAssess();
		AssessMenuItem tempAssessMenuItem = new AssessMenuItem();
		tempAssessMenuItem.setAssessId(tempAssess.getId());
		tempAssessMenuItem.setAssessCode(tempAssess.getCode());
		tempAssessMenuItem.setAssessName(tempAssess.getName());
		tempAssessMenuItem.setSeqNo(_tempMap.getSeqNo());
		tempAssessMenuItem.setStatus(tempAssess.getStatus());

		PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.findByAssessPaperIdAndAssessIdAndCreatorId(
				_tempMap.getAssessPaperId(), _tempMap.getAssessId(), _userId);
		if(tempAnswer == null)
			tempAssessMenuItem.setAnswerStatus(CommonConstant.DIC_ASSESS_ANSWER_STATUS_NOT_STARTED);
		else
			tempAssessMenuItem.setAnswerStatus(tempAnswer.getStatus());
		return tempAssessMenuItem;
	}

	@Override
	public GeneralContentResult<List<AssessPaperItem>> getAssessPaperList(String _userId, String _orgId, Byte _title) {
		List<PerAssessOrgMap> tempAssessOrgMapList = this.perAssessOrgMapRepository.findByOrgIdAndTitleTypeAndStatus(_orgId, _title, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
		List<AssessPaperItem> tempAssessPaperItemList = new ArrayList<>();
		for(PerAssessOrgMap tempItem : tempAssessOrgMapList){
			PerAssessPaper tempPaper = tempItem.getPerAssessPaper();
			AssessPaperItem tempAssessPaperItem = new AssessPaperItem();
			tempAssessPaperItem.setId(tempPaper.getId());
			tempAssessPaperItem.setCode(tempPaper.getCode());
			tempAssessPaperItem.setName(tempPaper.getName());
			tempAssessPaperItem.setStatus(tempPaper.getStatus());
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
	@Transactional
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

		List<TemplateItemMapReq> tempReqList = _req.getItemList().stream().collect(
				Collectors.collectingAndThen(
						Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(TemplateItemMapReq::getTemplateItemId))), ArrayList::new));

		List<PerTemplateTiMap> tempMapList = tempReqList.stream().map(
				tempReq -> this.convertTOPTTMDTO(tempReq, tempTemplate)
		).collect(Collectors.toList());
		tempTemplate.setPerTemplateTiMaps(tempMapList);

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
	@Transactional
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

        List<TemplateItemMapReq> tempReqList = _req.getItemList().stream().collect(
        		Collectors.collectingAndThen(
						Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(TemplateItemMapReq::getTemplateItemId))), ArrayList::new));

        List<PerTemplateTiMap> tempMapList = tempReqList.stream().map(
        		tempReq -> this.convertTOPTTMDTO(tempReq, tempTemplate)
		).collect(Collectors.toList());
		tempTemplate.setPerTemplateTiMaps(tempMapList);

        PerTemplate newTemplate = this.perTemplateRepository.save(tempTemplate);
        SimpleTemplate tempResultTemplate = new SimpleTemplate();
        tempResultTemplate.setCode(newTemplate.getCode());
        tempResultTemplate.setName(newTemplate.getName());
        tempResultTemplate.setType(newTemplate.getType());
        tempResultTemplate.setId(newTemplate.getId());

        tempResult.setResultContent(tempResultTemplate);
        return tempResult;
    }

	/**
	 * 转化模板元素
	 *
	 * @param _req
	 * @param _template
	 * @return
	 */
    private PerTemplateTiMap convertTOPTTMDTO(TemplateItemMapReq _req, PerTemplate _template){
		PerTemplateTiMap tempTiMap = new PerTemplateTiMap();
		tempTiMap.setSeqNo(_req.getSeqNo());
		tempTiMap.setEditable(_req.getEditable());
		tempTiMap.setVisible(_req.getVisible());
		tempTiMap.setMandatory(_req.getMandatory());
		tempTiMap.setTemplateItemId(_req.getTemplateItemId());
		tempTiMap.setPerTemplate(_template);
		tempTiMap.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
		return tempTiMap;
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
	public GeneralPagingResult<List<SimpleAssessItem>> getAssessListByAssessPaper(String _assessPaperId, String _groupId, Pageable _page) {
		Page<PerAssess> assessPage = this.perAssessRepository.getAssessByAspAndGroup(_assessPaperId, _groupId, _page);
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
	public GeneralContentResult<SimpleAssessPaperItem> createAssessPaper(AssessPaperProfileReq _req) throws YYException {
        GeneralContentResult<SimpleAssessPaperItem> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        if(_req == null)
            return tempResult;

        PerAssessPaper tempPAP = new PerAssessPaper();
        tempPAP.setCode(_req.getCode());
        tempPAP.setName(_req.getName());
        tempPAP.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
		tempPAP.setPerAssessOrgMaps(this.packAssessPaperOrgMap(_req, tempPAP));

        List<PerAssessAspMap> tempPAAMapList = _req.getAssessList().stream().map(tempItem -> this.convertToAssessAspDTO(tempItem, tempPAP)).collect(Collectors.toList());
        tempPAP.setPerAssessAspMaps(tempPAAMapList);

        try {
			PerAssessPaper resultPAP = this.perAssessPaperRepository.save(tempPAP);
			SimpleAssessPaperItem tempASPI = new SimpleAssessPaperItem();
			tempASPI.setId(resultPAP.getId());
			tempASPI.setCode(resultPAP.getCode());
			tempASPI.setName(resultPAP.getName());
			tempASPI.setOrgIdList(_req.getOrgIdList());
			tempASPI.setStatus(resultPAP.getStatus());
			tempASPI.setTitleList(_req.getTitleList());
			tempResult.setResultContent(tempASPI);
		} catch (Exception ex) {
        	log.error("Save Assess Paper error: ", ex);
        	throw new YYException(ResultCode.ASSESSPAPER_CREATE_FAILED);
		}
	    return tempResult;
	}

	@Override
	@Transactional
	public GeneralContentResult<SimpleAssessPaperItem> updateAssessPaper(AssessPaperWithIDProfileReq _req) throws YYException {
        GeneralContentResult<SimpleAssessPaperItem> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        if(_req == null)
            return tempResult;
		log.info("Going to delete assess paper by id {}", _req.getId());
		this.perAssessAspMapRepository.deleteByAssessPaperId(_req.getId());
		this.perAssessAspMapRepository.flush();
		this.perAssessOrgMapRepository.deleteByAssessPaperId(_req.getId());
		this.perAssessOrgMapRepository.flush();
		log.info("Delete assess paper by id {} completed.", _req.getId());
		PerAssessPaper tempPAP = this.perAssessPaperRepository.findOne(_req.getId());
		tempPAP.setCode(_req.getCode());
		tempPAP.setName(_req.getName());
		tempPAP.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
		tempPAP.setPerAssessOrgMaps(this.packAssessPaperOrgMap(_req, tempPAP));

		List<PerAssessAspMap> tempPAAMapList = _req.getAssessList().stream().map(tempItem -> this.convertToAssessAspDTO(tempItem, tempPAP)).collect(Collectors.toList());
		tempPAP.setPerAssessAspMaps(tempPAAMapList);

		try {
			PerAssessPaper resultPAP = this.perAssessPaperRepository.save(tempPAP);
			SimpleAssessPaperItem tempASPI = new SimpleAssessPaperItem();
			tempASPI.setId(resultPAP.getId());
			tempASPI.setCode(resultPAP.getCode());
			tempASPI.setName(resultPAP.getName());
			tempASPI.setOrgIdList(_req.getOrgIdList());
			tempASPI.setStatus(resultPAP.getStatus());
			tempASPI.setTitleList(_req.getTitleList());
			tempResult.setResultContent(tempASPI);
		} catch (Exception ex) {
			log.error("Update Assess Paper error: ", ex);
			throw new YYException(ResultCode.ASSESSPAPER_UPDATE_FAILED);
		}
        return tempResult;
	}

	/**
	 * 封装试卷和部门和职称的关系
	 *
	 * @param _req
	 * @return
	 */
	private List<PerAssessOrgMap> packAssessPaperOrgMap(AssessPaperProfileReq _req, PerAssessPaper _pap) {
		List<PerAssessOrgMap> tempPAOMList = new ArrayList<>();
		for(String tempOrg : _req.getOrgIdList()){//
			for(Byte tempTitle : _req.getTitleList()){
				PerAssessOrgMap tempPAOM = new PerAssessOrgMap();
				tempPAOM.setOrgId(tempOrg);
				tempPAOM.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
				tempPAOM.setTitleType(tempTitle);
				tempPAOM.setPerAssessPaper(_pap);
				tempPAOMList.add(tempPAOM);
			}
		}
		return tempPAOMList;
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
        this.perAssessAspMapRepository.deleteByAssessPaperId(_assessPaperId);
        this.perApAcMapRepository.deleteByAssessPaperId(_assessPaperId);
        this.perAssessOrgMapRepository.deleteByAssessPaperId(_assessPaperId);
        this.perAssessPaperRepository.delete(_assessPaperId);

        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        return tempResult;
	}

	@Override
	public GeneralPagingResult<List<SimpleAssessPaperItem>> getAssessPaperList(Pageable _page) {
	    Page<PerAssessPaper> tempPAPPage = this.perAssessPaperRepository.findAll(_page);
	    log.info("The Paper page is: [{}].", tempPAPPage);
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
	public GeneralContentResult<List<SimpleAssessPaperItem>> getAllAssessPaperList() {
		List<PerAssessPaper> tempPAPList = this.perAssessPaperRepository.findByStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
		log.info("The total item is: [{}].", tempPAPList.size());
		List<SimpleAssessPaperItem> tempSAPI = tempPAPList.stream().map(this::convertToAPIOTD).collect(Collectors.toList());
		GeneralContentResult<List<SimpleAssessPaperItem>> tempResult = new GeneralContentResult<>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		tempResult.setResultContent(tempSAPI);

		return tempResult;
	}

    @Override
    public GeneralPagingResult<List<SimpleAssessPaperItem>> getAssessPaperListByOrg(String _orgId, Pageable _page) {
        Page<PerAssessPaper> tempPAPPage = this.perAssessPaperRepository.findByStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE, _page);
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
	public GeneralPagingResult<List<SimpleAssessPaperItem>> getAssessPaperListByOrg(String _orgId, Byte _title, Pageable _page) {
		Page<PerAssessOrgMap> tempOrgMap = this.perAssessOrgMapRepository.findByOrgIdAndTitleTypeAndStatus(_orgId, _title, CommonConstant.DIC_GLOBAL_STATUS_ENABLE, _page);
		List<PerAssessPaper> tempPAPPage = tempOrgMap.getContent().stream().map(tempItem -> tempItem.getPerAssessPaper()).collect(Collectors.toList());

	//	Page<PerAssessPaper> tempPAPPage = this.perAssessPaperRepository.findByOrgIdAndAndStatus(_orgId, CommonConstant.DIC_GLOBAL_STATUS_ENABLE, _page);

		List<SimpleAssessPaperItem> tempSAPI = tempPAPPage.stream().map(this::convertToAPIOTD).collect(Collectors.toList());
		GeneralPagingResult<List<SimpleAssessPaperItem>> tempResult = new GeneralPagingResult<>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		tempResult.setResultContent(tempSAPI);

		PageInfo _pageInfo = new PageInfo();
		_pageInfo.setCurrentPage(tempOrgMap.getNumber());
		_pageInfo.setPageSize(tempOrgMap.getSize());
		_pageInfo.setTotalPage(tempOrgMap.getTotalPages());
		_pageInfo.setTotalRecords(tempOrgMap.getTotalElements());
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

        Set<String> orgSet = new TreeSet<>();
		Set<Byte> titleSet = new TreeSet<>();
		log.info("Going to load [{}]'s Org info.", tempItem);
        for(PerAssessOrgMap tempMap : _pap.getPerAssessOrgMaps()){
			orgSet.add(tempMap.getOrgId());
			titleSet.add(tempMap.getTitleType());
		}

        tempItem.setOrgIdList(orgSet);
        tempItem.setTitleList(titleSet);
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

