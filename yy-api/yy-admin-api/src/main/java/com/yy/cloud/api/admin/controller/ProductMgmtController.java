package com.yy.cloud.api.admin.controller;

import com.yy.cloud.api.admin.service.PlatformMgmtService;
import com.yy.cloud.api.admin.service.ProductService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageParameter;
import com.yy.cloud.common.data.dto.enterprise.*;
import com.yy.cloud.common.data.dto.platformmgmt.DiskServiceProfile;
import com.yy.cloud.common.data.dto.platformmgmt.VMServiceProfile;
import com.yy.cloud.common.data.otd.enterprise.CommonKeyValue;
import com.yy.cloud.common.data.otd.enterprise.ProductItem;
import com.yy.cloud.common.data.otd.enterprise.VMServiceDetailInfoItem;
import com.yy.cloud.common.data.otd.platformmgmt.DiskPlatformDetailInfoResp;
import com.yy.cloud.common.data.otd.platformmgmt.ServiceItem;
import com.yy.cloud.common.data.otd.platformmgmt.ServiceSimpleItem;
import com.yy.cloud.common.data.otd.platformmgmt.VmPlatformDetailInfoResp;
import com.yy.cloud.common.data.platformmgmt.ServiceTemplateItem;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by chenluo on 10/20/2016.
 */
@RestController
@Slf4j
public class ProductMgmtController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PlatformMgmtService platformService;

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprises/products/search/page/{_page}/size/{_size}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "分页检索产品列表（已完成）", notes = "Get all enterprises' product.")
    public GeneralPagingResult<List<ProductItem>> getAllEnterpriseProduct(
            @ApiParam(name = "_page", value = "_page", required = true) @PathVariable Integer _page,
            @ApiParam(name = "_size", value = "_size", required = true) @PathVariable Integer _size,
            @ApiParam(name = "_conn", value = "UI检索条件", required = true) @RequestBody ProductQueryConditionReq _conn) {
        log.debug("Going to get all of the enterprises' product with conn {}.", _conn);

        GeneralPagingResult<List<ProductItem>> tempResponse = this.productService.getAllEnterpriseProduct(_page-1, _size, _conn);
        return tempResponse;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprises/product", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新建产品（已完成）")
    public GeneralContentResult<String> createProduct(@RequestBody ProductNewProfile productProfile){
        log.debug("开始创建产品.{}", productProfile);
        productService.createProduct(productProfile);
        return null;
    }

    /**
     * 获取产品信息
     * @param _productId
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(method = RequestMethod.GET, value = "/authsec/enterprises/product/{_productId}")
    @ResponseBody
    @ApiOperation(value = "获取产品详情（已完成）")
    GeneralContentResult<ProductNewProfile> getProduct(@PathVariable String _productId){
        return productService.getProduct(_productId);
    }


    /**
     * 更新产品状态
     * @param productProfile
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(method = RequestMethod.PUT, value = "/authsec/enterprises/products/status")
    @ResponseBody
    @ApiOperation(value = "更新产品状态（已完成）")
    GeneralContentResult<String> updateProductStatus(@RequestBody ProductStatusReq productProfile){
        log.debug(CommonConstant.LOG_DEBUG_TAG+"更新产品 {} 的状态",productProfile);
        return productService.updateProductStatus(productProfile);
    }

    /**
     *
     * @param enterpriseId 企业id
     * @param pageParameter 分页信息
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprises/{enterpriseId}/products/search/paging")
    @ResponseBody
    @ApiOperation(value ="获取企业未选中的产品（已完成）")
    public GeneralPagingResult<List<ProductItem>> getAllUnselectedEnterpriseProduct(@PathVariable("enterpriseId") String enterpriseId, @RequestBody PageParameter pageParameter){
        if(pageParameter.getCurrentPage()>0){
            pageParameter.setCurrentPage(pageParameter.getCurrentPage()-1);
        }
        return productService.getAllUnselectedEnterpriseProduct(enterpriseId,pageParameter);
    }


    /**
     *
     * @param enterpriseId 企业ID
     * @param productIds 产品ID 数组
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprises/{enterpriseId}/products/bind")
    @ResponseBody
    @ApiOperation(value = "绑定企业与产品关系（已完成）")
    GeneralContentResult<List<String>> bindEnterpriseProduct(@PathVariable("enterpriseId") String enterpriseId,
                                                             @RequestBody List<String> productIds){
       return  productService.bindProductEnterprise(enterpriseId,productIds);

    }

    /**
     *
     * @param productId 产品ID
     * @param enterpriseIds 企业ID 数组
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(method = RequestMethod.POST, value = "/authsec/products/{productId}/enterprises/bind")
    @ResponseBody
    @ApiOperation(value = "绑定产品与企业关系（已完成）")
    GeneralContentResult<List<String>> bindProductEnterprise(@PathVariable("productId") String productId,
                                                             @RequestBody List<String> enterpriseIds){
       return  productService.bindProductEnterprise(productId,enterpriseIds);
    }











}
