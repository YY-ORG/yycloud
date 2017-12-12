package com.yy.cloud.core.filesys.service.impl;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.otd.file.SimpleFileInfo;
import com.yy.cloud.core.filesys.data.domain.YyFile;
import com.yy.cloud.core.filesys.data.repositories.YyFileRepository;
import com.yy.cloud.core.filesys.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/16/17 9:58 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private YyFileRepository yyFileRepository;

    @Override
    public GeneralContentResult<YyFile> saveFile(YyFile _file) {
        YyFile tempFile = this.yyFileRepository.save(_file);
        GeneralContentResult<YyFile> tempResult = new GeneralContentResult<>();
        tempResult.setResultContent(tempFile);
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        return tempResult;
    }

    @Override
    public GeneralResult removeFile(String _id) {
        this.yyFileRepository.delete(_id);
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        return tempResult;
    }

    @Override
    public GeneralContentResult<YyFile> getFileById(String _id) {
        YyFile tempFile = this.yyFileRepository.findOne(_id);
        GeneralContentResult<YyFile> tempResult = new GeneralContentResult<>();
        tempResult.setResultContent(tempFile);
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        return tempResult;
    }

    @Override
    public GeneralPagingResult<List<YyFile>> listFilesByPage(Pageable _page) {
        Page<YyFile> filePage = this.yyFileRepository.findAll(_page);

        GeneralPagingResult<List<YyFile>> tempResult = new GeneralPagingResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(filePage.getContent());
        PageInfo _pageInfo = new PageInfo();
        _pageInfo.setCurrentPage(filePage.getNumber());
        _pageInfo.setPageSize(filePage.getSize());
        _pageInfo.setTotalPage(filePage.getTotalPages());
        _pageInfo.setTotalRecords(filePage.getTotalElements());
        tempResult.setPageInfo(_pageInfo);

        return tempResult;
    }

    @Override
    public List<SimpleFileInfo> getSimpleFileInfo(List<String> _idList) {
        Iterable<YyFile> yyFileList = this.yyFileRepository.findAll(_idList);
        List<SimpleFileInfo> tempResult = new ArrayList<>();
        yyFileList.forEach(tempItem -> {
            SimpleFileInfo tempInfo = new SimpleFileInfo();
            tempInfo.setId(tempItem.getId());
            tempInfo.setName(tempItem.getName());
            tempInfo.setContentType(tempItem.getContentType());
            tempInfo.setSize(tempItem.getSize());
            tempInfo.setUploadDate(tempItem.getUploadDate());
            tempInfo.setMd5(tempItem.getMd5());
            tempResult.add(tempInfo);
        });

        return tempResult;
    }
}
