package com.xmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.xmall.common.Const;
import com.xmall.common.ResponseCode;
import com.xmall.common.RestResponse;
import com.xmall.entity.Product;
import com.xmall.entity.User;
import com.xmall.service.IProductService;
import com.xmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author xies
 * @date 2018/1/25
 */
@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @RequestMapping(value="save.do")
    @ResponseBody
    public RestResponse<String> productSave(HttpSession session,Product product){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return RestResponse.error(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return RestResponse.error("没有权限");
        }
        return iProductService.saveOrUpdateProduct(product);
    }

    @RequestMapping(value="search.do")
    @ResponseBody
    public RestResponse<PageInfo> searchProduct(
           HttpSession session,String productName, Integer productId,
           @RequestParam(value="pageNum",required = false,defaultValue = Const.PAGE_NUM) Integer pageNum,
           @RequestParam(value="pageSize",required = false,defaultValue = Const.PAGE_SIZE) Integer pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return RestResponse.error(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return RestResponse.error("无权限操作");
        }
        return iProductService.searchProduct(productName,productId,pageNum,pageSize);
    }
}
