package com.linmour.order.pojo.Dto;

import com.linmour.product.pojo.Dto.ProductDetailDto;
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
    private List<ProductDetailDto> list;

}
