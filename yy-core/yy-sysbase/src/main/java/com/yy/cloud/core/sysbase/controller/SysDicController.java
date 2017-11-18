package com.yy.cloud.core.sysbase.controller;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.otd.sysbase.SysDic;
import com.yy.cloud.common.data.sysbase.SystemDictionary;
import com.yy.cloud.core.sysbase.data.domain.YYSystemdictionary;
import com.yy.cloud.core.sysbase.data.repositories.SystemDictionaryRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/18/17 2:52 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Slf4j
@RestController
public class SysDicController {
    @Autowired
    private SystemDictionaryRepository systemDictionaryRepository;

    @RequestMapping(value = "/authsec/sysdic/owner", method = RequestMethod.GET)
    @ApiOperation(value = "获取数据字典的Owner列表")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<List<String>> getDicOwnerList() {
        List<String> tempList = this.systemDictionaryRepository.getOwnerList();
        GeneralContentResult<List<String>> result = new GeneralContentResult<>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        result.setResultContent(tempList);
        return result;
    }

    @RequestMapping(value = "/authsec/sysdic/owner/{_owner}/field", method = RequestMethod.GET)
    @ApiOperation(value = "获取数据字典某个Owner的Field列表")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<List<String>> getDicFieldListByOwner(@ApiParam(value = "类目") @PathVariable(value = "_owner") String _owner){
        List<String> tempList = this.systemDictionaryRepository.getFieldListByOwner(_owner);
        GeneralContentResult<List<String>> result = new GeneralContentResult<>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        result.setResultContent(tempList);
        return result;
    }

    @RequestMapping(value = "/authsec/sysdic", method = RequestMethod.POST)
    @ApiOperation(value = "创建数据字典")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult saveDic(@ApiParam(value = "类目") @RequestBody List<SysDic> _dicist){
        List<YYSystemdictionary> tempList = _dicist.stream().map(this::convertToDTO).collect(Collectors.toList());
        this.systemDictionaryRepository.save(tempList);
        GeneralResult result = new GeneralResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        return result;
    }

    @RequestMapping(value = "/authsec/sysdic/sysdiclist", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取数据字典")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralPagingResult<List<SystemDictionary>> getDicByPage(Pageable _page){
        GeneralPagingResult<List<SystemDictionary>> result = new GeneralPagingResult<>();
        Page<YYSystemdictionary> dicPage = this.systemDictionaryRepository.findAll(_page);
        List<SystemDictionary> tempList = dicPage.getContent().stream().map(this::convertToOTD).collect(Collectors.toList());
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        result.setResultContent(tempList);

        PageInfo _pageInfo = new PageInfo();
        _pageInfo.setCurrentPage(dicPage.getNumber());
        _pageInfo.setPageSize(dicPage.getSize());
        _pageInfo.setTotalPage(dicPage.getTotalPages());
        _pageInfo.setTotalRecords(dicPage.getTotalElements());

        result.setPageInfo(_pageInfo);

        return result;
    }

    @RequestMapping(value = "/authsec/sysdic/{_id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除某个数据字典")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult deleteDic(@ApiParam(value = "字典的ID") @PathVariable Long _id){
        this.systemDictionaryRepository.delete(_id);
        GeneralResult result = new GeneralResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        return result;
    }

    private YYSystemdictionary convertToDTO(SysDic _dic){
        YYSystemdictionary tempDic = new YYSystemdictionary();
        tempDic.setOwner(_dic.getOwner());
        tempDic.setField(_dic.getField());
        tempDic.setCode(_dic.getCode());
        tempDic.setValue(_dic.getValue());
        tempDic.setText(_dic.getDisplayValue());

        return tempDic;
    }

    private SystemDictionary convertToOTD(YYSystemdictionary _dic){
        SystemDictionary tempDic = new SystemDictionary();
        tempDic.setId(_dic.getId());
        tempDic.setOwner(_dic.getOwner());
        tempDic.setField(_dic.getField());
        tempDic.setCode(_dic.getCode());
        tempDic.setValue(_dic.getValue());
        tempDic.setText(_dic.getText());
        return tempDic;
    }
}
