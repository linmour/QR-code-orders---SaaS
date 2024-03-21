package com.linmour.order.pojo.Dto;

import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Do.OrderItem;
import com.linmour.product.pojo.Dto.ProductDetailDto;
import lombok.Data;

import java.util.List;

@Data
public class OrderAllDto {
    private  OrderInfo orderInfo;

    private List<ProductDetailDto>  ProductDetailDtos;
}
