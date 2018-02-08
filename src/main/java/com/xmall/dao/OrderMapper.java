package com.xmall.dao;

import com.xmall.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectOrderByUserIdOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);

    Order selectByOrderNo(Long orderNo);

    List<Order> getOrdersByUserId(Integer userId);

    List<Order> getAllOrder();
}