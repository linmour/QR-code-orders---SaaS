package com.linmour.order.pojo.Dto;

import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Do.OrderItem;
import com.linmour.product.pojo.Dto.ProductDetailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAllDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private  OrderInfo orderInfo;

    private List<OrderItem>  orderItems;
}
