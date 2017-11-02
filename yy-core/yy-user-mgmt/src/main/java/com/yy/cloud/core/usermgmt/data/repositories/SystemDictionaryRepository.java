package com.yy.cloud.core.usermgmt.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.yy.cloud.core.usermgmt.data.domain.YYSystemdictionary;


/**
 * ClassName: SystemDictionaryRepository <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年12月20日 <br/>
 *
 * @author twu
 * @version
 * @since JDK 1.8
 */
@RepositoryRestResource
public interface SystemDictionaryRepository extends JpaRepository<YYSystemdictionary, Long> {
	List<YYSystemdictionary> findByOwner(@Param("_owner") String _owner);
	List<YYSystemdictionary> findByOwnerAndField(@Param("_owner") String _owner, @Param("_field") String _field);
	List<YYSystemdictionary> findByOwnerAndFieldAndCode(@Param("_owner") String _owner, @Param("_field") String _field, @Param("_code") String _code);
	
}
