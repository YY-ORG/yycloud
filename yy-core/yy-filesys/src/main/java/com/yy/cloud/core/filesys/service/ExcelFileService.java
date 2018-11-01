package com.yy.cloud.core.filesys.service;

import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.utils.YYException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     5/14/18 7:59 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface ExcelFileService {
    GeneralResult importSourceJounalExcelFile(MultipartFile _file) throws YYException;
    GeneralResult importKeyJounalExcelFile(MultipartFile _file) throws YYException;
}
