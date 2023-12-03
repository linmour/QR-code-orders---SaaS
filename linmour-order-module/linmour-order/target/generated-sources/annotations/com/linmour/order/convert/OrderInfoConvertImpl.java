package com.linmour.order.convert;

import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Dto.OrderInfoDto;
import java.time.LocalDateTime;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-03T10:31:28+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.21 (Amazon.com Inc.)"
)
public class OrderInfoConvertImpl implements OrderInfoConvert {

    @Override
    public OrderInfo OrderInfoDtoToOrderInfo(OrderInfoDto orderInfoDto) {
        if ( orderInfoDto == null ) {
            return null;
        }

        OrderInfo orderInfo = new OrderInfo();

        orderInfo.setId( orderInfoDto.getId() );
        orderInfo.setCusId( orderInfoDto.getCusId() );
        orderInfo.setTableId( orderInfoDto.getTableId() );
        orderInfo.setPayType( orderInfoDto.getPayType() );
        orderInfo.setPayStatus( orderInfoDto.getPayStatus() );
        orderInfo.setPayAmount( orderInfoDto.getPayAmount() );
        orderInfo.setOrderStatus( orderInfoDto.getOrderStatus() );
        orderInfo.setRemark( orderInfoDto.getRemark() );
        if ( orderInfoDto.getCreateTime() != null ) {
            orderInfo.setCreateTime( LocalDateTime.parse( orderInfoDto.getCreateTime() ) );
        }
        if ( orderInfoDto.getUpdateTime() != null ) {
            orderInfo.setUpdateTime( LocalDateTime.parse( orderInfoDto.getUpdateTime() ) );
        }
        orderInfo.setCreateBy( orderInfoDto.getCreateBy() );
        orderInfo.setUpdateBy( orderInfoDto.getUpdateBy() );
        orderInfo.setDeleted( orderInfoDto.getDeleted() );

        return orderInfo;
    }
}
