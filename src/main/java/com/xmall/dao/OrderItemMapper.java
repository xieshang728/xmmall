package com.xmall.dao;

import com.xmall.entity.OrderItem;
import com.xmall.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> getByUserIdOrderNo(@Param("userId") Integer userId,@Param("orderNo") Long orderNo);

    int batchInsert(@Param("orderItems") List<OrderItem> orderItems);

    List<OrderItem> getByOrderNo(Long orderNo);

    List<OrderItem> getByOrderNoUserId(@Param("orderNo") Long orderNo,@Param("userId") int userId);
}