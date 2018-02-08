package com.xmall.dao;

import com.xmall.entity.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    List<Cart> selectCartByUserId(Integer userId);

    int selectCartProductCheckedStatusByUserId(Integer userId);

    Cart getCartByUserIdProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    int deleteProductByUserIdProductIds(@Param("userId") Integer userId, @Param("productIdList") List<String> productList);

    int checkedOrUncheckedProduct(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("checked") Integer checked);

    int getCartProductCount(Integer userId);

    List<Cart> selectCheckedCartByUserId(Integer userId);
}