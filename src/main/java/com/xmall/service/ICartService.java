package com.xmall.service;

import com.xmall.common.RestResponse;
import com.xmall.vo.CartVo;


/**
 * @author xies
 * @date 2018/2/2.
 */
public interface ICartService {
    RestResponse<CartVo> list(Integer userId);

    RestResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    RestResponse<CartVo> update(Integer userId, Integer productId, int count);

    RestResponse<CartVo> deleteProduct(Integer userId, String productIds);

    RestResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    RestResponse<Integer> getCartProductCount(Integer userId);

}
