package com.xmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xmall.common.Const;
import com.xmall.common.ResponseCode;
import com.xmall.common.RestResponse;
import com.xmall.dao.CategoryMapper;
import com.xmall.dao.ProductMapper;
import com.xmall.entity.Category;
import com.xmall.entity.Product;
import com.xmall.service.ICategoryService;
import com.xmall.service.IProductService;
import com.xmall.util.PropertiesUtil;
import com.xmall.vo.ProductListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xies
 * @date 2018/1/24
 */
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

    @Autowired
    private ProductMapper productMapper;


    private ProductListVo assembleProductListVo(Product product) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server", "www.xmall.com"));
        productListVo.setName(product.getName());
        productListVo.setPrice(product.getPrice());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setStatus(product.getStatus());
        productListVo.setSubtitle(product.getSubtitle());
        return productListVo;
    }

    @Override
    public RestResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, Integer pageNum, Integer pageSize, String orderBy) {
        if (StringUtils.isBlank(keyword) || categoryId == null) {
            return RestResponse.error(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList = new ArrayList<>();
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isBlank(keyword)) {
                PageHelper.startPage(pageNum, pageSize);
                List<ProductListVo> productListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVoList);
                return RestResponse.success(pageInfo);
            }
            categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();
        }
        if (StringUtils.isNotBlank(keyword)) {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }

        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotBlank(keyword)) {
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArray = orderBy.split("-");
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
            }
        }

        List<Product> list = productMapper.selectByNameAndCategoryIds(
                StringUtils.isBlank(keyword) ? null : keyword,
                categoryIdList.size() == 0 ? null : categoryIdList);

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product product : list) {
            System.out.println(product);
            productListVoList.add(assembleProductListVo(product));
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(productListVoList);
        return RestResponse.success(pageInfo);
    }

    @Override
    public RestResponse<ProductListVo> getProductDetail(Integer productId) {
        if (productId == null) {
            return RestResponse.error(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return RestResponse.error("产品下架或者已删除");
        }
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
            return RestResponse.error("产品下架或者已删除");
        }
        ProductListVo productListVo = assembleProductListVo(product);
        return RestResponse.success(productListVo);
    }

    @Override
    public RestResponse<String> saveOrUpdateProduct(Product product) {
        if (product != null) {
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }
        }
        if (product.getId() != null) {
            int rowCount = productMapper.updateByPrimaryKeySelective(product);
            if (rowCount > 0) {
                return RestResponse.success("产品更新成功");
            }
            return RestResponse.error("产品更新失败");
        } else {
            int rowCount = productMapper.insertSelective(product);
            if (rowCount > 0) {
                return RestResponse.success("产品插入成功");
            }
            return RestResponse.error("产品插入失败");
        }
    }

    @Override
    public RestResponse<PageInfo> searchProduct(String productName, Integer productId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotBlank(productName)) {
            productName = new StringBuffer().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName, productId);
        PageInfo pageInfo = new PageInfo();
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product product : productList) {
            productListVoList.add(assembleProductListVo(product));
        }
        pageInfo.setList(productListVoList);
        return RestResponse.success(pageInfo);
    }

    @Override
    public RestResponse<String> setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return RestResponse.error(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int result = productMapper.updateByPrimaryKeySelective(product);
        if (result > 0) {
            return RestResponse.success("修改产品状态成功");
        }
        return RestResponse.error("修改产品状态失败");
    }

    @Override
    public RestResponse<ProductListVo> manageProductDetail(Integer productId) {
        if (productId == null) {
            return RestResponse.error(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return RestResponse.error("产品已下架，或者删除");
        }
        ProductListVo productListVo = assembleProductListVo(product);
        return RestResponse.success(productListVo);
    }

    @Override
    public RestResponse<PageInfo> getProductList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductListVo> productListVos = Lists.newArrayList();
        List<Product> products = productMapper.selectList();
        for (Product product : products) {
            ProductListVo productListVo = assembleProductListVo(product);
            productListVos.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(products);
        pageInfo.setList(productListVos);
        return RestResponse.success(pageInfo);
    }
}
