package com.xmall.controller.backend;

import com.xmall.common.Const;
import com.xmall.common.ResponseCode;
import com.xmall.common.RestResponse;
import com.xmall.entity.Category;
import com.xmall.entity.User;
import com.xmall.service.ICategoryService;
import com.xmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author xies
 * @date 2018/1/23
 */
@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping(value = "add_category.do")
    @ResponseBody
    public RestResponse<String> addCategory(HttpSession session, String categoryName,
                                            @RequestParam(value = "categoryId", defaultValue = Const.DEFAULT_PARENT_ID) int parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return RestResponse.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (!iUserService.checkAdminRole(user).isSuccess()) {
            return RestResponse.error("用户权限不够，不能添加分类");
        }
        return iCategoryService.addCategory(categoryName, parentId);
    }

    @RequestMapping(value = "set_category_name.do")
    @ResponseBody
    public RestResponse<String> setCategoryName(HttpSession session, String categoryName, int categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return RestResponse.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (!iUserService.checkAdminRole(user).isSuccess()) {
            return RestResponse.error("用户权限不够，不能修改分类");
        }
        return iCategoryService.setCategoryName(categoryName, categoryId);
    }

    @RequestMapping(value = "get_category.do")
    @ResponseBody
    public RestResponse<List<Category>> getChildrenParallelCategory(HttpSession session,
                                                                    @RequestParam(value = "categoryId", defaultValue = Const.DEFAULT_PARENT_ID) Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return RestResponse.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (!iUserService.checkAdminRole(user).isSuccess()) {
            return RestResponse.error("用户权限不够，不能修改分类");
        }
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    @RequestMapping(value = "get_deep_category.do")
    @ResponseBody
    public RestResponse<List<Integer>> selectCategoryAndChildreanById(HttpSession session,
                                                                      @RequestParam(value = "categoryId", defaultValue = Const.DEFAULT_PARENT_ID) Integer categoryId
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return RestResponse.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (!iUserService.checkAdminRole(user).isSuccess()) {
            return RestResponse.error("用户权限不够，不能修改分类");
        }
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }
}
