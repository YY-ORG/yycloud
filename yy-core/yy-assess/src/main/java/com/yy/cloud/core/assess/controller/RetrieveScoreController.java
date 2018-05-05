package com.yy.cloud.core.assess.controller;

import com.yy.cloud.common.constant.ExceptionCode;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.otd.assess.SimplePersonalScoreDetail;
import com.yy.cloud.common.data.otd.assess.SimpleRankingItem;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.assess.service.RetrieveScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     3/26/18 8:21 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Slf4j
@RestController
@Api("分数查询相关API")
public class RetrieveScoreController {

    @Autowired
    private RetrieveScoreService retrieveScoreService;

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/answerscorelist", method = RequestMethod.GET)
    @ApiOperation(value = "按照分组获取某个用户某个试卷的评分详情")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<List<SimplePersonalScoreDetail>> getPersoanlAnswerScoreList(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                                                      @ApiParam(value = "做题人的ID, 为空则表示当前用户") @RequestParam(value = "_userId", required = false) String _userId){
        GeneralContentResult<List<SimplePersonalScoreDetail>> result  = new GeneralContentResult<>();

        try {
            result = this.retrieveScoreService.getPersoanlAnswerScoreList(_userId, _assessPaperId);

        } catch (YYException ye) {
            log.error("YYException occured: {}", ye.getCode());
            result.setDetailDescription(ExceptionCode.EXCEPTION_MSG.get(ye.getCode()));
            result.setResultCode(ye.getCode());
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }
    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/ranking", method = RequestMethod.GET)
    @ApiOperation(value = "依据条件检索某个试卷的成绩排名列表")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralPagingResult<List<SimpleRankingItem>> getPersoanlAnswerScoreRankingByPage(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                                                            @ApiParam(value = "所选择的部门") @RequestParam(value = "_orgId", required = false) String _orgId,
                                                                                            @ApiParam(value = "所选择的职称") @RequestParam(value = "_title", required = false) Byte _title,
                                                                                            @PageableDefault(sort = { "totalRanking" }, direction = Sort.Direction.ASC) Pageable _page){
        GeneralPagingResult<List<SimpleRankingItem>> result  = new GeneralPagingResult<>();

        try {
            result = this.retrieveScoreService.getTotalRankListByCon(_assessPaperId, _orgId, _title, _page);

        } catch (YYException ye) {
            log.error("YYException occured: {}", ye.getCode());
            result.setDetailDescription(ExceptionCode.EXCEPTION_MSG.get(ye.getCode()));
            result.setResultCode(ye.getCode());
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }
}
