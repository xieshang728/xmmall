package com.xmall.vo;

import lombok.*;

/**
 * @author xies
 * @date 2018/2/8.
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShippingVo {

    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;

}
