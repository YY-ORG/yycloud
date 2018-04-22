package com.yy.cloud.core.assess.service.impl;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.otd.assess.SimplePersonalScoreDetail;
import com.yy.cloud.common.data.otd.assess.SimpleRankingItem;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.assess.service.RetrieveScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     3/28/18 9:20 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Service
@Slf4j
public class RetrieveScoreServiceImpl implements RetrieveScoreService {
    @Override
    public GeneralContentResult<SimplePersonalScoreDetail> getPersoanlAnswerScoreList(String _userId, String _assessPaperId) throws YYException {
        return null;
    }

    @Override
    public GeneralPagingResult<List<SimpleRankingItem>> getTotalRankList(String _assessPaperId) {
        return null;
    }

    @Override
    public GeneralPagingResult<List<SimpleRankingItem>> getTotalRankListByCon(String _assessPaperId, String _orgId, Byte _title) {
        return null;
    }
}
