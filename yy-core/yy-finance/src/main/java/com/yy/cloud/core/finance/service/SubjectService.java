package com.yy.cloud.core.finance.service;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.assess.AssessCategoryItem;
import com.yy.cloud.common.data.assess.AssessMenuItem;
import com.yy.cloud.common.data.dto.assess.AssessCategoryReq;
import com.yy.cloud.common.data.dto.assess.AssessCategoryWithIDReq;
import com.yy.cloud.common.data.dto.finance.SubjectProfileReq;
import com.yy.cloud.common.data.otd.assess.SimpleAssessCategoryItem;
import com.yy.cloud.common.data.otd.finance.SubjectItemRes;
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
public interface SubjectService {
    GeneralContentResult<List<SubjectItemRes>> createSubjects(String _creatorId, List<SubjectProfileReq> _req);

}
