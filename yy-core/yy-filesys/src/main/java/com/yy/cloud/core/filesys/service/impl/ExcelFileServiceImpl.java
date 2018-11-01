package com.yy.cloud.core.filesys.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ExceptionCode;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.filesys.data.domain.SourceJournalFile;
import com.yy.cloud.core.filesys.service.ExcelFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     5/14/18 8:24 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Service
@Slf4j
public class ExcelFileServiceImpl implements ExcelFileService {

    @Override
    public GeneralResult importSourceJounalExcelFile(MultipartFile _file) throws YYException {
        if (_file == null){
            throw new YYException(ResultCode.FILE_EXCEL_NOT_EXISTS);
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(CommonConstant.FILE_EXCEL_GENERAL_TITILE_ROWS);
        params.setHeadRows(CommonConstant.FILE_EXCEL_GENERAL_HEADER_ROWS);
        List<T> list = null;

        try {
            list = ExcelImportUtil.importExcel(_file.getInputStream(), SourceJournalFile.class, params);

        }catch (NoSuchElementException e){
            throw new YYException(ResultCode.FILE_EXCEL_IS_EMPTY);
        } catch (Exception e) {
            throw new YYException(ResultCode.FILE_EXCEL_UNKNOWN_ERROR);
        }

        return null;
    }

    @Override
    public GeneralResult importKeyJounalExcelFile(MultipartFile _file) throws YYException{
        return null;
    }
}
