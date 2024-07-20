package com.linmour.order.pojo.Dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CheckoutDto implements Serializable {
    private String tableId;
    private Integer payType;
    private String openid;
}
