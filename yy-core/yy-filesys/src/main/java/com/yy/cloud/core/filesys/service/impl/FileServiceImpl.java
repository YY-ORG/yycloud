package com.yy.cloud.core.filesys.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.yy.cloud.common.constant.ExceptionCode;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.otd.file.SimpleFileInfo;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.filesys.data.domain.YyFile;
import com.yy.cloud.core.filesys.data.repositories.YyFileRepository;
import com.yy.cloud.core.filesys.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    private YyFileRepository yyFileRepository;
    @Autowired
    private MongoDbFactory mongodbfactory;


    @Override
    public GeneralContentResult<YyFile> saveFile(MultipartFile _file) throws YYException {
        GridFS gridFS = new GridFS(mongodbfactory.getDb());

        GeneralContentResult<YyFile> tempResult = new GeneralContentResult<>();
        try {
            InputStream in = _file.getInputStream();
            String name = _file.getOriginalFilename();
            GridFSInputFile gridFSInputFile = gridFS.createFile(in);
            gridFSInputFile.setFilename(name);
            gridFSInputFile.setContentType(_file.getContentType());
            gridFSInputFile.save();

            YyFile tempFile = new YyFile(
                    name, _file.getContentType(), _file.getSize(), new byte[]{}
            );
            tempFile.setId(gridFSInputFile.getId().toString());
            tempFile.setMd5(gridFSInputFile.getMD5());

            tempResult.setResultContent(tempFile);
            tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (IOException e){
            log.error("Encountered Error while trying to upload file [{}].", _file.getOriginalFilename());
            throw new YYException(ResultCode.FILE_UPLOAD_ERROR);
        }
        return tempResult;
    }

    @Override
    public GeneralResult removeFile(String _id) {
        GridFS gridFS = new GridFS(mongodbfactory.getDb());
        gridFS.remove(new ObjectId(_id));

        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        return tempResult;
    }

    @Override
    public GeneralContentResult<GridFSDBFile> getFileById(String _id) {
        GridFS gridFS = new GridFS(mongodbfactory.getDb());
        GridFSDBFile tempDBFile = gridFS.findOne(new ObjectId(_id));

        GeneralContentResult<GridFSDBFile> tempResult = new GeneralContentResult<>();
        tempResult.setResultContent(tempDBFile);
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        return tempResult;
    }

    @Override
    public GeneralPagingResult<List<YyFile>> listFilesByPage(Pageable _page) {
        GridFS gridFS = new GridFS(mongodbfactory.getDb());
        DBCursor tempCursor = gridFS.getFileList();
        int tempIndex = 0;
        int tempPage = _page.getPageNumber();
        int tempSize = _page.getPageSize();
        List<YyFile> tempFileList = new ArrayList<>();
        int tempTotalElements = tempCursor.length();
        while (tempCursor.hasNext()) {
            if(tempIndex >= tempPage * tempSize && tempIndex < (tempPage + 1) * tempSize) {
                DBObject tempObj = tempCursor.next();
                GridFSDBFile tempGridFile = (GridFSDBFile)tempObj;
                YyFile tempFile = new YyFile(tempGridFile.getFilename(), tempGridFile.getContentType(), tempGridFile.getLength(), new byte[]{});
                tempFile.setId(tempGridFile.getId().toString());
                tempFile.setMd5(tempGridFile.getMD5());
                tempFileList.add(tempFile);
            } else if (tempIndex >= (tempPage + 1) * tempSize) {
                break;
            }
            tempIndex ++;
        }

        int tempPages = 0;
        if(_page.getPageSize() > 0 ){
            tempPages = tempTotalElements / _page.getPageSize();
            int tempReminder = tempTotalElements % _page.getPageSize();
            if(tempReminder > 0) {
                tempPages ++;
            }
        }

        GeneralPagingResult<List<YyFile>> tempResult = new GeneralPagingResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempFileList);
        PageInfo _pageInfo = new PageInfo();
        _pageInfo.setCurrentPage(_page.getPageNumber());
        _pageInfo.setPageSize(_page.getPageSize());
        _pageInfo.setTotalPage(tempPages);
        _pageInfo.setTotalRecords(new Long(tempTotalElements));
        tempResult.setPageInfo(_pageInfo);

        return tempResult;
    }

    @Override
    public List<SimpleFileInfo> getSimpleFileInfo(List<String> _idList) {
        List<SimpleFileInfo> tempResult = new ArrayList<>();
        if(_idList == null || _idList.size() == 0){
            return tempResult;
        }

        GridFS gridFS = new GridFS(mongodbfactory.getDb());
        _idList.stream().forEach(item -> {
            GridFSDBFile tempDBFile = gridFS.findOne(new ObjectId(item));
            log.info("The file is: {}-{}", tempDBFile.getId(), tempDBFile.getMD5());
            SimpleFileInfo tempInfo = new SimpleFileInfo();
            tempInfo.setId(tempDBFile.getId().toString());
            tempInfo.setName(tempDBFile.getFilename());
            tempInfo.setContentType(tempDBFile.getContentType());
            tempInfo.setSize(tempDBFile.getLength());
            tempInfo.setUploadDate(tempDBFile.getUploadDate());
            tempInfo.setMd5(tempDBFile.getMD5());
            tempResult.add(tempInfo);
        });

        return tempResult;
    }
}
