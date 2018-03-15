package com.xmall.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author  xies
 * @date 2018/2/8.
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductVo {

    private List<OrderItemVo> orderItemVoList;

    private BigDecimal productTotalPrice;

    private String imageHost;

}
