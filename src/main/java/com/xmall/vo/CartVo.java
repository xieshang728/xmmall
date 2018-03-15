package com.xmall.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xies
 * @date 2018/2/2.
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartVo {

    private List<CartProductVo> cartProductVoList;
    private BigDecimal cartTotalPrice;
    private Boolean allChecked;
    private String imageHost;


}
