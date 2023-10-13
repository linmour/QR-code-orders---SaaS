package com.linmour.order.pojo.Dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubmitOrderDto implements Serializable {
    private String orderNo;
    private Integer payType;
}
