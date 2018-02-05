package com.xmall.service;

import com.github.pagehelper.PageInfo;
import com.xmall.common.RestResponse;
import com.xmall.entity.Product;
import com.xmall.vo.ProductListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author xies
 * @date 2018/1/24
 */
public interface IProductService {

    RestResponse<ProductListVo> getProductDetail(Integer productId);

    RestResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, Integer pageNum, Integer pageSize, String orderBy);

    RestResponse<String> saveOrUpdateProduct(Product product);

    /**
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    RestResponse<PageInfo> searchProduct(String productName, Integer productId, Integer pageNum, Integer pageSize);

    RestResponse<String> setSaleStatus(Integer productId, Integer status);

    RestResponse<ProductListVo> manageProductDetail(Integer productId);

    RestResponse<PageInfo> getProductList(Integer pageNum, Integer pageSize);
}
