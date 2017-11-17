package com.yy.cloud.core.filesys.data.repositories;

import com.yy.cloud.core.filesys.data.domain.YyFile;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/16/17 9:52 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface YyFileRepository extends MongoRepository<YyFile, String> {
}
