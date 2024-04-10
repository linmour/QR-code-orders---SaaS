package com.linmour.order.pojo.Dto;

import com.linmour.order.pojo.Do.OrderItem;
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

    private String orderId;

    private Long tableId;
    private BigDecimal amount;
    private List<OrderItem> orderItems;
    private String remark;
    private Long shopId;
}
