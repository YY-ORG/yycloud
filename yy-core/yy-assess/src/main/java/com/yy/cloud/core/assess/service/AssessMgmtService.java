/**
 * Project Name:yy-assess
 * File Name:AssessMgmtService.java
 * Package Name:com.yy.cloud.core.assess.service
 * Date:Sep 27, 20179:28:50 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.service;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.assess.AssessGroupItem;
import com.yy.cloud.common.data.assess.AssessItem;
import com.yy.cloud.common.data.assess.AssessMenuItem;
import com.yy.cloud.common.data.assess.AssessPaperItem;
import com.yy.cloud.common.data.dto.assess.*;
import com.yy.cloud.common.data.dto.metadata.TemplateItemProfileReq;
import com.yy.cloud.common.data.dto.metadata.TemplateItemWithIDProfileReq;
import com.yy.cloud.common.data.dto.metadata.TemplateProfileReq;
import com.yy.cloud.common.data.dto.metadata.TemplateWithIDProfileReq;
import com.yy.cloud.common.data.otd.assess.AssessPeriodItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessPaperItem;
import com.yy.cloud.common.data.otd.metadata.ComplexTemplateItem;
import com.yy.cloud.common.data.otd.metadata.SimpleTemplate;
import com.yy.cloud.common.data.otd.metadata.SimpleTemplateItem;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.assess.data.domain.IPerAssessPaperAnnual;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * ClassName:AssessMgmtService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 27, 2017 9:28:50 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public interface AssessMgmtService {

	public GeneralContentResult<AssessItem> getAssessItemById(String _id);

	public GeneralContentResult<List<AssessMenuItem>> getAssessMenu(String _userId, String _orgId);

	public GeneralContentResult<List<AssessGroupItem>> getAssessMenuByAssessPaperId(String _userId, String _assessPaperId);

	public GeneralContentResult<List<AssessGroupItem>> getAssessMenuByAssessPaperIdAndGroup(String _userId, String _assessPaperId, String _groupId);

	public GeneralContentResult<List<AssessPaperItem>> getAssessPaperList(String _userId, String _orgId, Byte _title);

	public GeneralContentResult<SimpleAssessItem> createAssess(AssessProfileReq _profile);

	public GeneralContentResult<SimpleAssessItem> updateAssess(AssessWithIDProfileReq _profile);

	public GeneralResult deleteAssess(String _assessId);

	public GeneralContentResult<SimpleTemplate> createAssessTemplate(TemplateProfileReq _req);

	public GeneralContentResult<SimpleTemplate> updateAssessTemplate(TemplateWithIDProfileReq _req);

    public GeneralPagingResult<List<SimpleTemplate>> getAssessTemplateList(Pageable _page);

    public GeneralPagingResult<List<SimpleTemplate>> getAssessTemplateList(Byte _type, Pageable _page);

    public GeneralContentResult<List<SimpleTemplate>> getAssessTemplateByAssess(String _assessId);

	public GeneralResult deleteAssessTemplate(String _assessTemplateId);

	public GeneralContentResult<SimpleTemplateItem> createAssessTemplateItem(TemplateItemProfileReq _req);

	public GeneralContentResult<SimpleTemplateItem> updateAssessTemplateItem(TemplateItemWithIDProfileReq _req);

	public GeneralPagingResult<List<SimpleTemplateItem>> getAssessTemplateItemList(Pageable _page);

	public GeneralContentResult<List<ComplexTemplateItem>> getAssessTemplateItemByTemplate(String _templateId);

	public GeneralResult deleteAssessTemplateItem(String _assessTemplateItemId);

	public GeneralPagingResult<List<SimpleAssessItem>> getAssessList(Pageable _page);

	public GeneralPagingResult<List<SimpleAssessItem>> getAssessListByAssessPaper(String _assessPaperId, Pageable _page);

	public GeneralPagingResult<List<SimpleAssessItem>> getAssessListByAssessPaper(String _assessPaperId, String _groupId, Pageable _page);

	public GeneralContentResult<SimpleAssessPaperItem> createAssessPaper(AssessPaperProfileReq _req) throws YYException;

	/**
	 * Copy source assess paper.
	 *
	 * @param _sourceId
	 * @param _destAnnual
	 * @param _creatorId
	 * @return
	 * @throws YYException
	 */
	public GeneralResult copyAssessPaper(String _sourceId, Integer _destAnnual, String _creatorId) throws YYException;

	public GeneralContentResult<SimpleAssessPaperItem> updateAssessPaper(AssessPaperWithIDProfileReq _req) throws YYException;

	public GeneralResult deleteAssessPaper(String _assessPaperId);

	public GeneralPagingResult<List<SimpleAssessPaperItem>> getAssessPaperList(Pageable _page);

	public GeneralContentResult<List<SimpleAssessPaperItem>> getAllAssessPaperList();

	public GeneralPagingResult<List<SimpleAssessPaperItem>> getAssessPaperListByOrg(String _orgId, Pageable _page);

    public GeneralPagingResult<List<SimpleAssessPaperItem>> getAssessPaperListByOrg(String _orgId, Byte _title, Pageable _page);

	public GeneralContentResult<List<SimpleTemplate>> getTemplateListByType(Byte _type);

	public GeneralPagingResult<List<AssessPeriodItem>> getAssessPeriodPageList(Pageable _page);

	public GeneralResult updateAssessPeriod(AssessPeriodReq _req, String _userId);
	public GeneralResult updateAssessPeriodList(List<AssessPeriodReq> _reqList, String _userId);

	public GeneralContentResult<List<IPerAssessPaperAnnual>> getAssessPaperAnnualList();
}

