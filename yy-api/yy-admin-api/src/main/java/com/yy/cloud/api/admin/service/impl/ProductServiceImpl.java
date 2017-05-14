package com.yy.cloud.api.admin.service.impl;

import com.yy.cloud.api.admin.clients.ProductMgmtClient;
import com.yy.cloud.api.admin.service.ProductService;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.PageParameter;
import com.yy.cloud.common.data.dto.enterprise.ProductNewProfile;
import com.yy.cloud.common.data.dto.enterprise.ProductPageQueryConditionReq;
import com.yy.cloud.common.data.dto.enterprise.ProductQueryConditionReq;
import com.yy.cloud.common.data.dto.enterprise.ProductStatusReq;
import com.yy.cloud.common.data.otd.enterprise.CommonKeyValue;
import com.yy.cloud.common.data.otd.enterprise.ProductItem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenluo on 10/20/2016.
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMgmtClient productMgmtClient;

    @Override
    public GeneralPagingResult<List<ProductItem>> getAllEnterpriseProduct(Integer _page, Integer _size, ProductQueryConditionReq _conn) {
        log.debug("查询产品",_conn);
        ProductPageQueryConditionReq productPageQueryConditionReq = new ProductPageQueryConditionReq();
        productPageQueryConditionReq.setEnterpriseId(_conn.getEnterpriseId());
        productPageQueryConditionReq.setPlatformId(_conn.getPlatformId());
        productPageQueryConditionReq.setServiceId(_conn.getServiceId());
        productPageQueryConditionReq.setPage(_page);
        productPageQueryConditionReq.setSize(_size);
        return productMgmtClient.getAllEnterpriseProduct(productPageQueryConditionReq);
    }

    @Override
    public GeneralContentResult<String> createProduct(ProductNewProfile productProfile) {
        return productMgmtClient.createProduct(productProfile);
    }

    @Override
    public GeneralContentResult<ProductNewProfile> getProduct(String productProfile) {
        return productMgmtClient.getProduct(productProfile);
    }

    @Override
    public GeneralContentResult<ProductNewProfile> updateProduct(ProductNewProfile productProfile) {
        return null;//productMgmtClient.updateProduct(productProfile);
    }

    @Override
    public GeneralContentResult<String> updateProductStatus(ProductStatusReq productProfile) {
        GeneralPagingResult<GeneralContent> res =productMgmtClient.updateProductStatusByBatch(productProfile.getPids(),productProfile.getStatus());
        GeneralContentResult<String> result = new GeneralContentResult<>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        return result;
    }

    @Override
    public GeneralPagingResult<List<ProductItem>> getAllUnselectedEnterpriseProduct(String enterpriseId, PageParameter pageParameter) {
        return productMgmtClient.getAllUnselectedEnterpriseProduct(enterpriseId,pageParameter);
    }

    @Override
    public GeneralContentResult<List<String>> bindEnterpriseProduct(String enterpriseId, List<String> productIds) {
        return productMgmtClient.bindEnterpriseProduct(enterpriseId,productIds);
    }

    @Override
    public GeneralContentResult<List<String>> bindProductEnterprise(String productId, List<String> enterpriseIds) {
        return productMgmtClient.bindEnterpriseProduct(productId,enterpriseIds);
    }
}
