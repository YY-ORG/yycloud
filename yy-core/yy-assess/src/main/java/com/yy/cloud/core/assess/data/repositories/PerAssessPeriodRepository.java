package com.yy.cloud.core.assess.data.repositories;

import com.yy.cloud.core.assess.data.domain.IPerAssespaperPeriod;
import com.yy.cloud.core.assess.data.domain.PerAssessPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/14/18 10:15 AM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RepositoryRestResource(collectionResourceRel = "perAssessPeriod", path = "perAssessPeriod")
public interface PerAssessPeriodRepository extends JpaRepository<PerAssessPeriod, String> {
//    @Query(value="select p.id as assessPaperId, p.name as assessPaperName, pp.id as periodId, pp.doingStart as exStartTime, pp.doingEnd as exEndTime, " +
//            "pp.scStart as scStartTime, pp.scEnd as scEndTime, pp.auStart as auStartTime, pp.auEnd as auEndTime from PerAssessPaper p left join PerAssessPeriod pp on p.id = pp.assessPaperId where p.status=1",
//            countQuery = "select count(p) from PerAssessPaper p where p.status = 1")
//    Page<IPerAssespaperPeriod> getAssessPaperPeriodByPage(Pageable _page);

    PerAssessPeriod getPerAssessPeriodByAssessPaperId(String _assessPapaerId);
}
