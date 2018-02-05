package com.xmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.xmall.common.Const;
import com.xmall.common.ResponseCode;
import com.xmall.common.RestResponse;
import com.xmall.entity.Product;
import com.xmall.entity.User;
import com.xmall.service.IFileService;
import com.xmall.service.IProductService;
import com.xmall.service.IUserService;
import com.xmall.util.PropertiesUtil;
import com.xmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private IFileService iFileService;

    @RequestMapping(value = "save.do")
    @ResponseBody
    public RestResponse<String> productSave(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return RestResponse.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (!iUserService.checkAdminRole(user).isSuccess()) {
            return RestResponse.error("没有权限");
        }
        return iProductService.saveOrUpdateProduct(product);
    }

    @RequestMapping(value = "search.do")
    @ResponseBody
    public RestResponse<PageInfo> searchProduct(
            HttpSession session, String productName, Integer productId,
            @RequestParam(value = "pageNum", required = false, defaultValue = Const.PAGE_NUM) Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = Const.PAGE_SIZE) Integer pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return RestResponse.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (!iUserService.checkAdminRole(user).isSuccess()) {
            return RestResponse.error("无权限操作");
        }
        return iProductService.searchProduct(productName, productId, pageNum, pageSize);
    }

    @RequestMapping(value = "set_sale_status.do")
    @ResponseBody
    public RestResponse<String> setSalestatus(
            HttpSession session, Integer productId, Integer status
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return RestResponse.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (!iUserService.checkAdminRole(user).isSuccess()) {
            return RestResponse.error("无权限操作");
        }
        return iProductService.setSaleStatus(productId, status);
    }

    @RequestMapping(value = "detail.do")
    @ResponseBody
    public RestResponse<ProductListVo> getDetail(
            HttpSession session, Integer productId
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return RestResponse.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (!iUserService.checkAdminRole(user).isSuccess()) {
            return RestResponse.error("无权限操作");
        }
        return iProductService.manageProductDetail(productId);
    }


    @RequestMapping(value = "list.do")
    @ResponseBody
    public RestResponse<PageInfo> getList(
            HttpSession session,
            @RequestParam(value = "pageNum", defaultValue = Const.PAGE_NUM) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = Const.PAGE_SIZE) Integer pageSize
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return RestResponse.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (!iUserService.checkAdminRole(user).isSuccess()) {
            return RestResponse.error("无权限操作");
        }
        return iProductService.getProductList(pageNum, pageSize);
    }


    @RequestMapping(value = "/upload.do")
    @ResponseBody
    public RestResponse<Map> upload(
            HttpSession session,
            @RequestParam(value = "upload_file", required = false) MultipartFile multipartFile,
            HttpServletRequest request
    ) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return RestResponse.error(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (!iUserService.checkAdminRole(user).isSuccess()) {
            return RestResponse.error("无权限操作");
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(multipartFile, path);
        String url = PropertiesUtil.getProperty("ftp.server.prefix") + targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return RestResponse.success(fileMap);
    }


    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richTextImgUpload(
            HttpSession session,
            @RequestParam(value = "upload_file", required = false) MultipartFile multipartFile,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map resultMap = Maps.newHashMap();
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            resultMap.put("success", false);
            resultMap.put("msg", "请登录管理员账号");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(multipartFile, path);
            if (StringUtils.isBlank(targetFileName)) {
                resultMap.put("success", false);
                resultMap.put("msg", "上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.prefix") + targetFileName;
            resultMap.put("success", true);
            resultMap.put("msg", "上传成功");
            resultMap.put("file_path", url);
            response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
            return resultMap;
        } else {
            resultMap.put("success", false);
            resultMap.put("msg", "无权限操作");
            return resultMap;
        }
    }
}
