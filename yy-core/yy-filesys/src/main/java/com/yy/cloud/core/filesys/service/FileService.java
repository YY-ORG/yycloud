package com.yy.cloud.core.filesys.service;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.otd.file.SimpleFileInfo;
import com.yy.cloud.core.filesys.data.domain.YyFile;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/16/17 9:55 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface FileService {
    /**
     * 保存文件
     * @param _file
     * @return
     */
    GeneralContentResult<YyFile> saveFile(YyFile _file);

    /**
     * 删除文件
     * @param _id
     * @return
     */
    GeneralResult removeFile(String _id);

    /**
     * 根据id获取文件
     * @param _id
     * @return
     */
    GeneralContentResult<YyFile> getFileById(String _id);

    /**
     * 分页查询，按上传时间降序
     * @param _page
     * @return
     */
    GeneralPagingResult<List<YyFile>> listFilesByPage(Pageable _page);

    /**
     * 依据文件ID，来获取文件的简要信息
     *
     * @param _idList
     * @return
     */
    List<SimpleFileInfo> getSimpleFileInfo(List<String> _idList);

}
