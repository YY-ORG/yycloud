package com.yy.cloud.core.assess.service.impl;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.otd.usermgmt.UserItem;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.assess.clients.UserMgmtClient;
import com.yy.cloud.core.assess.data.domain.PerAssessOrgMap;
import com.yy.cloud.core.assess.data.domain.PerAssessPaperExamineeMap;
import com.yy.cloud.core.assess.data.domain.PerAssessPeriod;
import com.yy.cloud.core.assess.data.repositories.PerAssessOrgMapRepository;
import com.yy.cloud.core.assess.data.repositories.PerAssessPaperExamineeMapRepository;
import com.yy.cloud.core.assess.data.repositories.PerAssessPeriodRepository;
import com.yy.cloud.core.assess.service.DoingAssessService;
import com.yy.cloud.core.assess.service.MarkedScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/7/18 5:19 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Component
@Slf4j
public class ScheduledTask {
    @Autowired
    private UserMgmtClient userMgmtClient;
    @Autowired
    private PerAssessOrgMapRepository perAssessOrgMapRepository;
    @Autowired
    private PerAssessPaperExamineeMapRepository perAssessPaperExamineeMapRepository;
    @Autowired
    private DoingAssessService doingAssessService;
    @Autowired
    private MarkedScoreService markedScoreService;
    @Autowired
    private PerAssessPeriodRepository perAssessPeriodRepository;

    @Scheduled(cron = "0 0 1 * * *")
    public void autoCommitUnCompletePaper() throws InterruptedException {
        log.info("Going to auto-commit the uncompleted assess paper.");
        GeneralPagingResult<List<UserItem>> tempUserPageResult = this.userMgmtClient.findUsers(CommonConstant.DIC_GLOBAL_STATUS_ENABLE, 0, 2000);
        tempUserPageResult.getResultContent().stream().forEach(tempUser -> {
            List<PerAssessOrgMap> tempAssessPaperOrgMapList = this.perAssessOrgMapRepository.findByOrgIdAndTitleTypeAndStatus(tempUser.getDeptId(), tempUser.getProfessionalTitle(), CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
            tempAssessPaperOrgMapList.stream().forEach(tempOrgMapItem -> {
                String tempAssessPaperId = tempOrgMapItem.getPerAssessPaper().getId();
                try {
                    PerAssessPeriod tempPeriod = this.perAssessPeriodRepository.getPerAssessPeriodByAssessPaperId(tempAssessPaperId);
                    if (tempPeriod != null) {
                        LocalDate currentDate = LocalDate.now();
                        Timestamp tempCurrentTimestamp = new Timestamp(System.currentTimeMillis());
                        if (tempPeriod.getDoingEnd() != null && tempCurrentTimestamp.after(tempPeriod.getDoingEnd())) {
                            LocalDate startDate = tempPeriod.getDoingEnd().toLocalDateTime().toLocalDate();
                            LocalDate endDate = startDate.plusDays(5);

                            if (currentDate.isAfter(startDate) && startDate.isBefore(endDate)) {
                                this.doingAssessService.submitAssessPaperAnswer(tempUser.getId(), tempUser.getDeptId(), tempUser.getProfessionalTitle(), tempAssessPaperId);
                            } else {
                                log.info("The current data is [{}], so the examinee still can submit the answer.");
                            }
                        }
                    } else {
                        log.info("There is no time period for assesspaper [{}], so do nothing.", tempAssessPaperId);
                    }
                } catch (YYException ex) {
                    log.info("The User [{}] has submitted [{}] assess paper already.", tempUser.getId(), tempAssessPaperId);
                } catch (Exception e) {
                    log.error("Encounter unknown exception while try to submit User [{}]'s [{}] assess paper.", tempUser.getId(), tempOrgMapItem.getPerAssessPaper().getId(), e);
                }
            });
        });
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void autoCommitUnScoringPaper() throws InterruptedException {
        log.info("Going to auto-submit the scoring.");
        List<Byte> tempStatusList = new ArrayList<>();
        tempStatusList.add(CommonConstant.DIC_ASSESSPAPER_STATUS_SUBMITTED);
        Page<PerAssessPaperExamineeMap> tempPaPeMapPage = this.perAssessPaperExamineeMapRepository.findByStatusIn(tempStatusList, new PageRequest(0, 2000));
        tempPaPeMapPage.getContent().stream().forEach(item -> {
                    PerAssessPeriod tempPeriod = this.perAssessPeriodRepository.getPerAssessPeriodByAssessPaperId(item.getAssessPaperId());
                    if (tempPeriod != null) {
                        LocalDate currentDate = LocalDate.now();
                        Timestamp tempCurrentTimestamp = new Timestamp(System.currentTimeMillis());
                        if (tempPeriod.getScEnd() != null && tempCurrentTimestamp.after(tempPeriod.getDoingEnd())) {
                            LocalDate startDate = tempPeriod.getScEnd().toLocalDateTime().toLocalDate();
                            LocalDate endDate = startDate.plusDays(5);

                            if (currentDate.isAfter(startDate) && startDate.isBefore(endDate)) {
                                try {
                                    markedScoreService.submitAssessPaperScoring(item.getCreatorId(), item.getAssessPaperId(), "sysauto");
                                } catch (YYException ex) {
                                    log.info("The User [{}]'s [{}] assess paper has been scoringed already.", item.getCreatorId(), item.getAssessPaperId());
                                } catch (Exception e) {
                                    log.error("Encounter unknown exception while try to scoring User [{}]'s [{}] assess paper.", item.getCreatorId(), item.getAssessPaperId(), e);
                                }
                            } else {
                                log.info("The current data is [{}], so the examinee still can submit the answer.");
                            }
                        }
                    } else {
                        log.info("There is no time period for assesspaper [{}], so do nothing.", item.getAssessPaperId());
                    }
                }
        );
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void autoCommitUnAuditPaper() throws InterruptedException {
        log.info("Going to auto-Audit the scoring.");
        List<Byte> tempStatusList = new ArrayList<>();
        tempStatusList.add(CommonConstant.DIC_ASSESSPAPER_STATUS_MARKED);
        Page<PerAssessPaperExamineeMap> tempPaPeMapPage = this.perAssessPaperExamineeMapRepository.findByStatusIn(tempStatusList, new PageRequest(0, 2000));
        tempPaPeMapPage.getContent().stream().forEach(item -> {
                    PerAssessPeriod tempPeriod = this.perAssessPeriodRepository.getPerAssessPeriodByAssessPaperId(item.getAssessPaperId());
                    if (tempPeriod != null) {
                        LocalDate currentDate = LocalDate.now();
                        Timestamp tempCurrentTimestamp = new Timestamp(System.currentTimeMillis());
                        if (tempPeriod.getAuEnd() != null && tempCurrentTimestamp.after(tempPeriod.getDoingEnd())) {
                            LocalDate startDate = tempPeriod.getAuEnd().toLocalDateTime().toLocalDate();
                            LocalDate endDate = startDate.plusDays(5);

                            if (currentDate.isAfter(startDate) && startDate.isBefore(endDate)) {
                                try {
                                    markedScoreService.submitAssessPaperAuditScore(item.getCreatorId(), item.getAssessPaperId(), "sysauto", CommonConstant.DIC_SCORING_LEVEL_FAILED);
                                } catch (YYException ex) {
                                    log.info("The User [{}]'s [{}] assess paper has been scoringed already.", item.getCreatorId(), item.getAssessPaperId());
                                } catch (Exception e) {
                                    log.error("Encounter unknown exception while try to scoring User [{}]'s [{}] assess paper.", item.getCreatorId(), item.getAssessPaperId(), e);
                                }
                            } else {
                                log.info("The current data is [{}], so the marker still can scoring the answer.");
                            }
                        }
                    } else {
                        log.info("There is no time period for assesspaper [{}], so do nothing.", item.getAssessPaperId());
                    }
                }
        );
    }
}
