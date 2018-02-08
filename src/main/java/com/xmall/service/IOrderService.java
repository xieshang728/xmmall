package com.xmall.service;

import com.github.pagehelper.PageInfo;
import com.xmall.common.RestResponse;
import com.xmall.vo.OrderVo;

import java.net.InetAddress;
import java.util.Map;

/**
 * @author xies
 * @date 2018/2/6.
 */
public interface IOrderService {
    RestResponse pay(Long orderNo,Integer userId,String path);

    RestResponse aliCallback(Map<String,String> params);

    RestResponse queryOrderPayStatus(Integer userId,Long orderNo);

    RestResponse createOrder(Integer userId,Integer shippingId);

    RestResponse cancelOrder(Integer userId,Long orderNo);

    RestResponse getOrderCartProduct(Integer userId);

    RestResponse getOrderDetail(Integer userId,Long orderNo);

    RestResponse getOrderList(Integer userId,int pageNum,int pageSize);

    RestResponse<PageInfo> manageList(Integer pageNum,Integer pageSize);

    RestResponse<OrderVo> manageDetail(Long orderNo);

    RestResponse<PageInfo> manageSearch(Long orderNo,int pageNum,int pageSize);

    RestResponse<String> manageSendGoods(Long orderNo);
}
