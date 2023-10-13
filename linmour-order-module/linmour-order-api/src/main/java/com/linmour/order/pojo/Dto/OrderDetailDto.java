package com.linmour.order.pojo.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OrderDetailDto {
    private String  orderId;

    public OrderDetailDto(String orderId) {
        this.orderId = orderId;
    }

    @JsonIgnore
    private List<Long> productIds;
    private List<Map<String,Object>> products;


}
