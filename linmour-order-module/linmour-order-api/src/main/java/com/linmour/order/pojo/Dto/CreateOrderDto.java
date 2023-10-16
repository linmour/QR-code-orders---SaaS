package com.linmour.order.pojo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto implements Serializable {

    private Long tableId;
    private BigDecimal amount;
    private List<ShopListDto> shopList;
    private String remark;
}
