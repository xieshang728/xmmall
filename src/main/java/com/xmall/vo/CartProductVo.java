package com.xmall.vo;

import lombok.*;

import java.math.BigDecimal;

/**
 * @author xies
 * @date 2018/2/2
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartProductVo {

    private Integer id;
    private Integer userId;
    private Integer productId;
    /**
     * 购物车中商品数量
     */
    private Integer quantity;
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
    private BigDecimal productTotalPrice;
    private Integer productStock;
    /**
     * 此商品是否已勾选
     */
    private Integer productChecked;
    /**
     * 限制商品数量的返回结果
     */
    private String limitQuantity;

}
