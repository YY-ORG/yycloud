package com.yy.cloud.core.finance.service.impl;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.assess.AssessCategoryItem;
import com.yy.cloud.common.data.dto.assess.AssessCategoryReq;
import com.yy.cloud.common.data.dto.assess.AssessCategoryWithIDReq;
import com.yy.cloud.common.data.otd.assess.SimpleAssessCategoryItem;
import com.yy.cloud.core.finance.data.domain.PerApAcMap;
import com.yy.cloud.core.finance.data.domain.PerAssessCategory;
import com.yy.cloud.core.finance.data.repositories.PerApAcMapRepository;
import com.yy.cloud.core.finance.data.repositories.PerAssessCategoryRepository;
import com.yy.cloud.core.finance.service.AssessCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/11/17 5:15 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Slf4j
@Service
public class AssessCategoryServiceImpl implements AssessCategoryService {

    @Autowired
    private PerAssessCategoryRepository perAssessCategoryRepository;
    @Autowired
    private PerApAcMapRepository perApAcMapRepository;

    @Override
    public GeneralContentResult<AssessCategoryItem> createAssessCategory(String _creatorId, AssessCategoryReq _req) {
        PerAssessCategory tempCategory = new PerAssessCategory();
        tempCategory.setCode(_req.getCode());
        tempCategory.setName(_req.getName());
        tempCategory.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
        tempCategory.setCreatorId(_creatorId);

        PerAssessCategory resultCategory = this.perAssessCategoryRepository.save(tempCategory);
        AssessCategoryItem tempItem = new AssessCategoryItem();
        tempItem.setId(resultCategory.getId());
        tempItem.setCode(resultCategory.getCode());
        tempItem.setName(resultCategory.getName());
        tempItem.setStatus(resultCategory.getStatus());
        tempItem.setCreatorId(resultCategory.getCreatorId());

        GeneralContentResult<AssessCategoryItem> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempItem);

        return tempResult;
    }

    @Override
    public GeneralContentResult<AssessCategoryItem> updateAssessCategory(String _creatorId, AssessCategoryWithIDReq _req) {
        PerAssessCategory tempCategory = this.perAssessCategoryRepository.findOne(_req.getId());
        tempCategory.setCode(_req.getCode());
        tempCategory.setName(_req.getName());
        tempCategory.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
        tempCategory.setCreatorId(_creatorId);

        PerAssessCategory resultCategory = this.perAssessCategoryRepository.save(tempCategory);
        AssessCategoryItem tempItem = new AssessCategoryItem();
        tempItem.setId(resultCategory.getId());
        tempItem.setCode(resultCategory.getCode());
        tempItem.setName(resultCategory.getName());
        tempItem.setStatus(resultCategory.getStatus());
        tempItem.setCreatorId(resultCategory.getCreatorId());

        GeneralContentResult<AssessCategoryItem> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempItem);

        return tempResult;
    }

    @Override
    public GeneralResult deleteAssessCategory(String _assessCategoryId) {
        this.perAssessCategoryRepository.delete(_assessCategoryId);
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        return tempResult;
    }

    @Override
    public GeneralPagingResult<List<SimpleAssessCategoryItem>> getAssessCategoryListByPage(Pageable _page) {
        Page<PerAssessCategory> assessCategoryPage = this.perAssessCategoryRepository.findAllByOrderByCreateDateAsc(_page);// this.perAssessCategoryRepository.findAll(_page);
        List<SimpleAssessCategoryItem> assessCategoryItemList = assessCategoryPage.getContent().stream().map(this::convertToASCIOTD).collect(Collectors.toList());
        GeneralPagingResult<List<SimpleAssessCategoryItem>> tempResult = new GeneralPagingResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(assessCategoryItemList);
        PageInfo _pageInfo = new PageInfo();
        _pageInfo.setCurrentPage(assessCategoryPage.getNumber());
        _pageInfo.setPageSize(assessCategoryPage.getSize());
        _pageInfo.setTotalPage(assessCategoryPage.getTotalPages());
        _pageInfo.setTotalRecords(assessCategoryPage.getTotalElements());
        tempResult.setPageInfo(_pageInfo);
        return tempResult;
    }

    /**
     * Convert Category Item to Simple Item.
     *
     * @param _category
     * @return
     */
    private SimpleAssessCategoryItem convertToASCIOTD(PerAssessCategory _category){
        SimpleAssessCategoryItem tempItem = new SimpleAssessCategoryItem();
        tempItem.setId(_category.getId());
        tempItem.setCode(_category.getCode());
        tempItem.setName(_category.getName());
        return tempItem;
    }

    @Override
    public GeneralResult addCategoryToAssessPaper(String _creatorId, String _assessPaper, List<String> _categoryList) {
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        this.perApAcMapRepository.deleteByAssessPaperId(_assessPaper);
        List<PerApAcMap> tempApAcMapList = new ArrayList<>();
        if(_categoryList != null && _categoryList.size() > 0){
            for(String tempCategoryId : _categoryList) {
                PerApAcMap tempItem = new PerApAcMap();
                tempItem.setAssessPaperId(_assessPaper);
                tempItem.setAssessCategoryId(tempCategoryId);
                tempItem.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
                tempItem.setCreatorId(_creatorId);
                tempApAcMapList.add(tempItem);
            }
        }
        this.perApAcMapRepository.save(tempApAcMapList);
        return tempResult;
    }

    @Override
    public GeneralContentResult<List<SimpleAssessCategoryItem>> getCategoryListByAssessPaper(String _assessPaper) {
        List<PerAssessCategory> tempList = this.perAssessCategoryRepository.findByAssessPaper(_assessPaper);
        List<SimpleAssessCategoryItem> assessCategoryItemList = tempList.stream().map(this::convertToASCIOTD).collect(Collectors.toList());
        GeneralContentResult<List<SimpleAssessCategoryItem>> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(assessCategoryItemList);

        return tempResult;
    }
}
