package com.linmour.order.convert;

import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Dto.OrderInfoDto;
import java.time.format.DateTimeFormatter;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-17T16:27:45+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_361 (Oracle Corporation)"
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
}
