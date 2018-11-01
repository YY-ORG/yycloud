package com.yy.cloud.core.assess.service;

import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.otd.file.SimpleSourceJournalItem;
import com.yy.cloud.common.utils.YYException;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     5/14/18 9:11 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface DirectoryService {
    GeneralResult createAssessDirectoryByBatch(List<SimpleSourceJournalItem> _items) throws YYException;
}
