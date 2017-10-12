/**
 * Project Name:yy-assess
 * File Name:AssessMgmtService.java
 * Package Name:com.yy.cloud.core.assess.service
 * Date:Sep 27, 20179:28:50 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.service;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.assess.AssessItem;
import com.yy.cloud.common.data.assess.AssessMenuItem;
import com.yy.cloud.common.data.assess.AssessPaperItem;

import java.util.List;

/**
 * ClassName:AssessMgmtService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 27, 2017 9:28:50 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public interface AssessMgmtService {

	public GeneralContentResult<AssessItem> getAssessItemById(String _id);

	public GeneralContentResult<List<AssessMenuItem>> getAssessMenu(String _userId, String _orgId);

	public GeneralContentResult<List<AssessMenuItem>> getAssessMenuByAssessPaperId(String _assessPaperId);

	public GeneralContentResult<List<AssessPaperItem>> getAssessPaperList(String _userId, String _orgId);

}

