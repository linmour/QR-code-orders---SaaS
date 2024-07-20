package com.linmour.order.convert;

import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Do.OrderItem;
import com.linmour.order.pojo.Dto.OrderInfoDto;
import com.linmour.order.pojo.Dto.ShopListDto;
import com.linmour.product.pojo.Dto.ProductDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderInfoConvert {
    OrderInfoConvert IN = Mappers.getMapper(OrderInfoConvert.class);

    OrderInfo OrderInfoDtoToOrderInfo(OrderInfoDto orderInfoDto);

//    List<ProductDetailDto> cover(List<OrderItem> list);

}
