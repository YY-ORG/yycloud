package com.yy.cloud.core.assess.service.impl;

import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.otd.file.SimpleSourceJournalItem;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.assess.service.DirectoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     5/17/18 10:59 AM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Service
@Slf4j
public class DirectoryServiceImpl implements DirectoryService {
    @Override
    public GeneralResult createAssessDirectoryByBatch(List<SimpleSourceJournalItem> _items) throws YYException {
        return null;
    }
}
