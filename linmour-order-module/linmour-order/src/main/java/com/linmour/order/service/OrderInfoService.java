package com.linmour.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.order.pojo.Do.OrderItem;
import com.linmour.order.pojo.Dto.OrderAllDto;
import com.linmour.security.dtos.Result;
import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Dto.CheckoutDto;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.pojo.Dto.OrderInfoDto;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author linmour
* @description 针对表【order_info】的数据库操作Service
* @createDate 2023-08-16 15:25:38
*/
@Transactional(rollbackFor = Exception.class)
//@ShardingTransactionType(TransactionType.BASE)
public interface OrderInfoService extends IService<OrderInfo> {

    Result createOrder(CreateOrderDto createOrderDto);

    Result checkout(CheckoutDto checkoutDto);

//    Map<String, Object> getOrderInfo(Long tableId);

    Result changeOrder(OrderInfoDto orderInfoDto);

    List<OrderAllDto> GetHistoryOrderList(Long tableId);

    OrderAllDto GetCurrentOrderInfo(Long tableId);

    OrderAllDto GetOrderInfoDetail(Long orderId);

    void updateOrderItemStatus(String tableId,Long index);
}
