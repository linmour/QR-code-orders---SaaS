package com.linmour.order.pojo.Dto;

import com.linmour.order.pojo.Do.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatOrderDto {
    private String id;
    private String payAmount;
    private String tableId;
    private List<OrderItem> list;

}
