package com.yy.cloud.core.filesys.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.gridfs.GridFSDBFile;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.otd.file.SimpleFileInfo;
import com.yy.cloud.common.utils.MD5Utils;
import com.yy.cloud.core.filesys.data.domain.YyFile;
import com.yy.cloud.core.filesys.service.FileService;
import io.swagger.annotations.Api;
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

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
@Api("文件上传下载相关API")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 分页查询文件
     *
     * @param _page
     * @return
     */
    @RequestMapping(value = "/authsec/files", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询文件")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralPagingResult<List<YyFile>> listFilesByPage(Pageable _page) {
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
     *
     * @param _id
     * @return
     */
    @RequestMapping(value = "/authsec/file/{_id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取文件片信息")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public void serveFile(@ApiParam(value = "文件ID") @PathVariable(value = "_id") String _id, HttpServletResponse _response) {

        GeneralContentResult<GridFSDBFile> tempResult = fileService.getFileById(_id);
        GridFSDBFile tempFile = tempResult.getResultContent();

        if (tempFile == null) {
            responseFail("404 not found", _response);
            return;
        }
        OutputStream tempOs = null;
        try {
            tempOs = _response.getOutputStream();
            _response.addHeader("Content-Disposition", "attachment;filename=" + tempFile.getFilename());
            _response.addHeader("Content-Length", "" + tempFile.getLength());
            _response.setContentType("application/octet-stream");
            tempFile.writeTo(tempOs);
            tempOs.flush();
            tempOs.close();

        } catch (Exception e) {
            try {
                if (tempOs != null) {
                    tempOs.close();
                }
            } catch (Exception e2) {
            }
            log.error("Encountered Error while trying to download file [{}]. ", tempFile.getFilename(), e);
        }
    }


    /**
     * 在线显示/下载文件
     *
     * @param _id
     * @return
     */
    @RequestMapping(value = "/authsec/file/{_id}/view", method = RequestMethod.GET)
    @ApiOperation(value = "在线显示文件")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    @ResponseBody
    public void serveFileOnline(@ApiParam(value = "文件ID") @PathVariable(value = "_id") String _id, HttpServletResponse _response) {

        GeneralContentResult<GridFSDBFile> tempResult = fileService.getFileById(_id);
        GridFSDBFile tempFile = tempResult.getResultContent();

        if (tempFile == null) {
            responseFail("404 not found", _response);
            return;
        }
        OutputStream tempOs = null;
        try {
            tempOs = _response.getOutputStream();
            _response.addHeader("Content-Disposition", "attachment;filename=" + tempFile.getFilename());
            _response.addHeader("Content-Length", "" + tempFile.getLength());
            _response.setContentType(tempFile.getContentType());
            tempFile.writeTo(tempOs);
            tempOs.flush();
            tempOs.close();
        } catch (Exception e) {
            try {
                if (tempOs != null) {
                    tempOs.close();
                }
            } catch (Exception e2) {
            }
            log.error("Encountered Error while trying to view file [{}]. ", tempFile.getFilename(), e);
        }
    }

    /**
     * 上传接口
     *
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
            log.debug("Going to upload file 文件[{}] this time.", _file.getOriginalFilename());
            GeneralContentResult<YyFile> tempResult = fileService.saveFile(_file);
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
     *
     * @param _id
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

    /**
     * 获取文件的摘要信息
     *
     * @param _idList
     * @return
     */
    @RequestMapping(value = "/authsec/file/simpleinfo", method = RequestMethod.POST)
    @ApiOperation(value = "批量获取文件的信息")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    @ResponseBody
    public GeneralContentResult<List<SimpleFileInfo>> getSimpleFileInfo(@ApiParam(value = "文件ID列表") @RequestBody List<String> _idList) {
        GeneralContentResult<List<SimpleFileInfo>> result = new GeneralContentResult<>();
        try {
            List<SimpleFileInfo> tempList = fileService.getSimpleFileInfo(_idList);
            result.setResultContent(tempList);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;

    }

    private void responseFail(String _msg, HttpServletResponse _response) {
        _response.setCharacterEncoding("UTF-8");
        _response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            String res = mapper.writeValueAsString(_msg);
            out = _response.getWriter();
            out.append(res);
        } catch (Exception e) {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e2) {
            }
        }
    }
}
