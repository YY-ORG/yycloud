/**
 * Project Name:yy-assess
 * File Name:PerAssessPaperRepository.java
 * Package Name:com.yy.cloud.core.assess.data.repositories
 * Date:Sep 26, 20178:01:42 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.data.repositories;

import com.yy.cloud.core.assess.data.domain.IPerAssespaperPeriod;
import com.yy.cloud.core.assess.data.domain.IPerAssessPaperAnnual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.yy.cloud.core.assess.data.domain.PerAssessPaper;

import java.util.List;
import java.util.Optional;

/**
 * ClassName:PerAssessPaperRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 26, 2017 8:01:42 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RepositoryRestResource(collectionResourceRel = "perAssessPaper", path = "perAssessPaper")
public interface PerAssessPaperRepository extends JpaRepository<PerAssessPaper, String> {

    @Query(value="select p.id as assessPaperId, p.annual as annual, p.name as assessPaperName, pp.id as periodId, pp.doingStart as exStartTime, pp.doingEnd as exEndTime, " +
            "pp.scStart as scStartTime, pp.scEnd as scEndTime, pp.auStart as auStartTime, pp.auEnd as auEndTime from PerAssessPaper p left join p.perAssessPeriods pp where p.status=1",
            countQuery = "select count(p) from PerAssessPaper p where p.status = 1")
    Page<IPerAssespaperPeriod> getAssessPaperPeriodByPage(Pageable _page);

    List<PerAssessPaper> findByStatus(Byte _status);
    Page<PerAssessPaper> findByStatus(Byte _status, Pageable _page);

    /**
     * Find By status not deleted
     * @param _status
     * @param _page
     * @return
     */
    Page<PerAssessPaper> findByStatusIsNot(Byte _status, Pageable _page);

    /**
     * Try to find the assess paper with code and annual.
     *
     * @param _code
     * @param _annual
     * @param _status
     * @return
     */
    Optional<PerAssessPaper> findByCodeAndAnnualAndStatus(String _code, Integer _annual, Byte _status);

    /**
     * Duplicate the assess paper.
     *
     * @param _annual
     * @param _sourceId
     * @param _creatorId
     */
    @Procedure(procedureName = "P_DUPLICATE_ASSESS_PAPER")
    void pDuplicateAssessPaper(Integer _annual, String _sourceId, String _creatorId);

    @Query(value = "select distinct p.annual as annual from PerAssessPaper p where p.status = 1")
    List<IPerAssessPaperAnnual> findDistinctByAnnual();
}

