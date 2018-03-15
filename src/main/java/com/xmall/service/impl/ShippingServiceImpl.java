package com.xmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.xmall.common.RestResponse;
import com.xmall.dao.ShippingMapper;
import com.xmall.entity.Shipping;
import com.xmall.service.IShippingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author xies
 * @date 2018/1/31
 */
@Service
@Slf4j
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;


    @Override
    public RestResponse<Map<String, Object>> add(int id, Shipping shipping) {
        shipping.setUserId(id);
        int result = shippingMapper.insert(shipping);
        if (result > 0) {
            Map map = Maps.newHashMap();
            map.put("shippingId", shipping.getId());
            return RestResponse.success("新建地址成功", map);
        }
        return RestResponse.error("新建地址失败");
    }

    @Override
    public RestResponse<String> del(Integer userId, Integer shippingId) {
        int result = 0;
        result = shippingMapper.deleteByShippingIdUserId(userId, shippingId);
        if (result > 0) {
            return RestResponse.success("删除地址成功");
        }
        return RestResponse.error("删除地址失败");
    }

    @Override
    public RestResponse<String> update(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int result = shippingMapper.updateByShipping(shipping);
        if (result > 0) {
            return RestResponse.success("更新地址成功");
        }
        return RestResponse.error("地址更新失败");
    }

    @Override
    public RestResponse<Shipping> select(Integer userId, Integer shippingId) {
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId, shippingId);
        if (shipping != null) {
            return RestResponse.success("查询的地址为", shipping);
        }
        return RestResponse.error("查询不该地址");
    }

    @Override
    public RestResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return RestResponse.success(pageInfo);
    }
}
