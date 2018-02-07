package com.xmall.service;

import com.xmall.common.RestResponse;

import java.util.Map;

/**
 * @author xies
 * @date 2018/2/6.
 */
public interface IOrderService {
    RestResponse pay(Long orderNo,Integer userId,String path);

    RestResponse aliCallback(Map<String,String> params);

    RestResponse queryOrderPayStatus(Integer userId,Long orderNo);
}
