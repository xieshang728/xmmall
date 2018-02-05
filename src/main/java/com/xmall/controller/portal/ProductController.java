package com.xmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.xmall.common.Const;
import com.xmall.common.RestResponse;
import com.xmall.entity.Product;
import com.xmall.service.IProductService;
import com.xmall.vo.ProductListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author xies
 * @date 2018/1/24
 */
@Controller
@RequestMapping(value = "/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @RequestMapping(value = "detail.do")
    @ResponseBody
    public RestResponse<ProductListVo> detail(Integer productId) {
        return iProductService.getProductDetail(productId);
    }


    @RequestMapping(value = "list.do")
    @ResponseBody
    public RestResponse<PageInfo> list(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "orderBy", defaultValue = Const.EMPTY) String orderBy,
            @RequestParam(value = "pageNum", defaultValue = Const.PAGE_NUM) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = Const.PAGE_SIZE) Integer pageSize
    ) {
        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }
}
