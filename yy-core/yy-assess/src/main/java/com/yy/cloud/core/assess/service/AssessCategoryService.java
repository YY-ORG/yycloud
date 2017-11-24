package com.yy.cloud.core.assess.service;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.assess.AssessCategoryItem;
import com.yy.cloud.common.data.assess.AssessMenuItem;
import com.yy.cloud.common.data.dto.assess.AssessCategoryReq;
import com.yy.cloud.common.data.dto.assess.AssessCategoryWithIDReq;
import com.yy.cloud.common.data.otd.assess.SimpleAssessCategoryItem;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/11/17 4:50 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface AssessCategoryService {
    GeneralContentResult<AssessCategoryItem> createAssessCategory(String _creatorId, AssessCategoryReq _req);

    GeneralContentResult<AssessCategoryItem> updateAssessCategory(String _creatorId, AssessCategoryWithIDReq _req);

    GeneralResult deleteAssessCategory(String _assessCategoryId);

    GeneralPagingResult<List<SimpleAssessCategoryItem>> getAssessCategoryListByPage(Pageable _page);

    GeneralResult addCategoryToAssessPaper(String _creatorId, String _assessPaper, List<String> _categoryList);

    GeneralContentResult<List<SimpleAssessCategoryItem>> getCategoryListByAssessPaper(String _assessPaper);

    GeneralContentResult<List<AssessMenuItem>> getAssessMenuByAssessPaperMap(String _groupMapId);
}
