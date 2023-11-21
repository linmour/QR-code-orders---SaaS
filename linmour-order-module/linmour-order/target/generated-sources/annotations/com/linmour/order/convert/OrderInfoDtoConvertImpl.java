package com.linmour.order.convert;

import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Dto.OrderInfoDto;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-22T15:59:36+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.21 (Amazon.com Inc.)"
)
public class OrderInfoDtoConvertImpl implements OrderInfoDtoConvert {

    @Override
    public OrderInfoDto OrderInfoToOrderInfoDto(OrderInfo orderInfo) {
        if ( orderInfo == null ) {
            return null;
        }

        OrderInfoDto orderInfoDto = new OrderInfoDto();

        orderInfoDto.setId( orderInfo.getId() );
        orderInfoDto.setCusId( orderInfo.getCusId() );
        orderInfoDto.setTableId( orderInfo.getTableId() );
        orderInfoDto.setPayType( orderInfo.getPayType() );
        orderInfoDto.setPayStatus( orderInfo.getPayStatus() );
        orderInfoDto.setPayAmount( orderInfo.getPayAmount() );
        orderInfoDto.setOrderStatus( orderInfo.getOrderStatus() );
        orderInfoDto.setRemark( orderInfo.getRemark() );
        if ( orderInfo.getCreateTime() != null ) {
            orderInfoDto.setCreateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( orderInfo.getCreateTime() ) );
        }
        if ( orderInfo.getUpdateTime() != null ) {
            orderInfoDto.setUpdateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( orderInfo.getUpdateTime() ) );
        }
        orderInfoDto.setCreateBy( orderInfo.getCreateBy() );
        orderInfoDto.setUpdateBy( orderInfo.getUpdateBy() );
        orderInfoDto.setDeleted( orderInfo.getDeleted() );

        return orderInfoDto;
    }

    @Override
    public List<OrderInfoDto> OrderInfoListToOrderInfoDtoList(List<OrderInfo> orderInfo) {
        if ( orderInfo == null ) {
            return null;
        }

        List<OrderInfoDto> list = new ArrayList<OrderInfoDto>( orderInfo.size() );
        for ( OrderInfo orderInfo1 : orderInfo ) {
            list.add( OrderInfoToOrderInfoDto( orderInfo1 ) );
        }

        return list;
    }
}
