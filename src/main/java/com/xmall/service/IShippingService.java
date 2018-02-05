package com.xmall.service;

import com.github.pagehelper.PageInfo;
import com.xmall.common.RestResponse;
import com.xmall.entity.Shipping;

import java.util.Map;

/**
 * @author xies
 * @date 2018/1/31
 */
public interface IShippingService {

    RestResponse<Map<String, Object>> add(int id, Shipping shipping);

    RestResponse<String> del(Integer userId, Integer shippingId);

    RestResponse<String> update(Integer userId, Shipping shipping);

    RestResponse<Shipping> select(Integer userId, Integer shippingId);

    RestResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize);
}
