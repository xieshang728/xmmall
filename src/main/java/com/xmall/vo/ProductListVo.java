package com.xmall.vo;

import lombok.*;

import java.math.BigDecimal;

/**
 * @author xies
 * @date 2018/1/24
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductListVo {

    private Integer id;
    private Integer categoryId;

    private String name;
    private String subtitle;
    private String mainImage;
    private BigDecimal price;

    private Integer status;

    private String imageHost;

}
