package com.yy.cloud.core.finance.data.repositories;

import com.yy.cloud.core.finance.data.domain.FSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/9/18 10:00 AM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RepositoryRestResource(collectionResourceRel = "fSubject", path = "fSubject")
public interface FSubjectRepository extends JpaRepository<FSubject, String> {
}
