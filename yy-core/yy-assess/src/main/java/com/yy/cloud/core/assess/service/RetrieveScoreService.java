package com.yy.cloud.core.assess.service;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.otd.assess.SimplePersonalScoreDetail;
import com.yy.cloud.common.data.otd.assess.SimpleRankingItem;
import com.yy.cloud.common.utils.YYException;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     3/28/18 9:14 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface RetrieveScoreService {
    GeneralContentResult<List<SimplePersonalScoreDetail>> getPersoanlAnswerScoreList(String _userId, String _assessPaperId) throws YYException;
    GeneralPagingResult<List<SimpleRankingItem>> getTotalRankList(String _assessPaperId, Pageable _page) throws YYException;
    GeneralPagingResult<List<SimpleRankingItem>> getTotalRankListByCon(String _assessPaperId, String _orgId, Byte _title, Pageable _page) throws YYException;
}
