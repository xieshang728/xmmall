package com.xmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.xmall.common.RestResponse;
import com.xmall.dao.ShippingMapper;
import com.xmall.entity.Shipping;
import com.xmall.service.IShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author xies
 * @date 2018/1/31
 */
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public RestResponse<Map<String, Object>> add(int id, Shipping shipping) {
        try {
            shipping.setUserId(id);
            int result = shippingMapper.insert(shipping);
            if (result > 0) {
                Map map = Maps.newHashMap();
                map.put("shippingId", shipping.getId());
                return RestResponse.success("新建地址成功", map);
            }
        } catch (Exception e) {
            logger.error("add exception: " + e.toString());
        }
        return RestResponse.error("新建地址失败");
    }

    @Override
    public RestResponse<String> del(Integer userId, Integer shippingId) {
        int result = 0;
        try {
            result = shippingMapper.deleteByShippingIdUserId(userId, shippingId);
            if (result > 0) {
                return RestResponse.success("删除地址成功");
            }
        } catch (Exception e) {
            logger.error("del exception: " + e.toString());
        }
        return RestResponse.error("删除地址失败");
    }

    @Override
    public RestResponse<String> update(Integer userId, Shipping shipping) {
        int result = 0;
        try {
            shipping.setUserId(userId);
            result = shippingMapper.updateByShipping(shipping);
            if (result > 0) {
                return RestResponse.success("更新地址成功");
            }
        } catch (Exception e) {
            logger.error("update exception: " + e.toString());
        }
        return RestResponse.error("地址更新失败");
    }

    @Override
    public RestResponse<Shipping> select(Integer userId, Integer shippingId) {
        Shipping shipping = null;
        try {
            shipping = shippingMapper.selectByShippingIdUserId(userId, shippingId);
            if (shipping != null) {
                return RestResponse.success("查询的地址为", shipping);
            }
        } catch (Exception e) {
            logger.error("select exception: " + e.toString());
        }
        return RestResponse.error("查询不该地址");
    }

    @Override
    public RestResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
            PageInfo pageInfo = new PageInfo(shippingList);
            return RestResponse.success(pageInfo);
        } catch (Exception e) {
            logger.error("list exception: " + e.toString());
        }
        return RestResponse.error("获取收货地址列表失败");
    }
}
