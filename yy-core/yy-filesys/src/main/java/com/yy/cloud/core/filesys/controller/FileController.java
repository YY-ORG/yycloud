package com.yy.cloud.core.filesys.controller;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.utils.MD5Utils;
import com.yy.cloud.core.filesys.data.domain.YyFile;
import com.yy.cloud.core.filesys.service.FileService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/16/17 10:08 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RestController
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 分页查询文件
     * @param _page
     * @return
     */
    @RequestMapping(value = "/authsec/files", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询文件")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralPagingResult<List<YyFile>> listFilesByPage(Pageable _page){
        GeneralPagingResult<List<YyFile>> result = new GeneralPagingResult<>();
        try {
            log.info("Going to load file by page [{}].", _page);
            result = this.fileService.listFilesByPage(_page);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    /**
     * 获取文件片信息/下载文件
     * @param _id
     * @return
     */
    @RequestMapping(value = "/authsec/file/{_id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取文件片信息")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    @ResponseBody
    public ResponseEntity<Object> serveFile(@ApiParam(value = "文件ID") @PathVariable(value = "_id") String _id) {

        GeneralContentResult<YyFile> tempResult = fileService.getFileById(_id);

        YyFile tempFile = tempResult.getResultContent();
        if (tempFile != null) {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + tempFile.getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream" )
                    .header(HttpHeaders.CONTENT_LENGTH, tempFile.getSize()+"")
                    .header("Connection",  "close")
                    .body(tempFile.getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }

    }

    /**
     * 在线显示/下载文件
     * @param id
     * @return
     */
    @RequestMapping(value = "/authsec/file/{_id}/view", method = RequestMethod.GET)
    @ApiOperation(value = "在线显示文件")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    @ResponseBody
    public ResponseEntity<Object> serveFileOnline(@ApiParam(value = "文件ID") @PathVariable(value = "_id") String _id) {
        GeneralContentResult<YyFile> tempResult = fileService.getFileById(_id);
        YyFile tempFile = tempResult.getResultContent();

        if (tempFile != null) {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + tempFile.getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, tempFile.getContentType() )
                    .header(HttpHeaders.CONTENT_LENGTH, tempFile.getSize()+"")
                    .header("Connection",  "close")
                    .body( tempFile.getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }

    }

    /**
     * 上传接口
     * @param _file
     * @return
     */
    @RequestMapping(value = "/authsec/file", method = RequestMethod.POST)
    @ApiOperation(value = "上传文件")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    @ResponseBody
    public GeneralContentResult<String> handleFileUpload(@ApiParam(value = "待上传文件") @RequestParam("file") MultipartFile _file) {
        GeneralContentResult<String> result = new GeneralContentResult<>();
        try {
            YyFile f = new YyFile(_file.getOriginalFilename(),  _file.getContentType(), _file.getSize(),_file.getBytes());
            f.setMd5(MD5Utils.getMD5(_file.getInputStream()));
            GeneralContentResult<YyFile> tempResult = fileService.saveFile(f);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
            result.setResultContent(tempResult.getResultContent().getId());
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    /**
     * 删除文件
     * @param id
     * @return
     */
    @RequestMapping(value = "/authsec/file/{_id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除文件")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    @ResponseBody
    public GeneralResult deleteFile(@ApiParam(value = "文件ID") @PathVariable(value = "_id") String _id) {
        GeneralResult result = new GeneralResult();
        try {
            fileService.removeFile(_id);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }
}
