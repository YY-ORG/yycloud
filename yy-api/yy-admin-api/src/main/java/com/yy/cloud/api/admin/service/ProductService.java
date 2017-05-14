package com.yy.cloud.api.admin.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.PageParameter;
import com.yy.cloud.common.data.dto.enterprise.ProductNewProfile;
import com.yy.cloud.common.data.dto.enterprise.ProductQueryConditionReq;
import com.yy.cloud.common.data.dto.enterprise.ProductStatusReq;
import com.yy.cloud.common.data.otd.enterprise.CommonKeyValue;
import com.yy.cloud.common.data.otd.enterprise.ProductItem;

import java.util.List;

/**
 * Created by chenluo on 10/20/2016.
 */
public interface ProductService {

    GeneralPagingResult<List<ProductItem>> getAllEnterpriseProduct(Integer _page, Integer _size, ProductQueryConditionReq _conn);

    GeneralContentResult<String> createProduct(ProductNewProfile productProfile);

    GeneralContentResult<ProductNewProfile> getProduct( String productProfile);

    GeneralContentResult<ProductNewProfile> updateProduct(ProductNewProfile productProfile);

    GeneralContentResult<String> updateProductStatus(ProductStatusReq productProfile);

    GeneralPagingResult<List<ProductItem>> getAllUnselectedEnterpriseProduct( String enterpriseId,  PageParameter pageParameter);


    GeneralContentResult<List<String>> bindEnterpriseProduct(String enterpriseId,List<String> productIds);

    GeneralContentResult<List<String>> bindProductEnterprise(String productId, List<String> enterpriseIds);
}
