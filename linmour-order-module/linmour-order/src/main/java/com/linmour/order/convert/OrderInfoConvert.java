package com.linmour.order.convert;

import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Dto.OrderInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderInfoConvert {
    OrderInfoConvert IN = Mappers.getMapper(OrderInfoConvert.class);

    OrderInfo OrderInfoDtoToOrderInfo(OrderInfoDto orderInfoDto);

}
