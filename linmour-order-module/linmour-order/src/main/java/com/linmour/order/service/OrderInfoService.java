package com.linmour.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.order.pojo.Dto.*;
import com.linmour.security.dtos.Result;
import com.linmour.order.pojo.Do.OrderInfo;

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

    Result changeOrder(OrderInfoDto orderInfoDto);

    List<OrderAllDto> GetHistoryOrderList(Long tableId);

    OrderAllDto GetCurrentOrderInfo(Long tableId);

    OrderAllDto GetOrderInfoDetail(Long orderId);

    void updateOrderItemStatus(String tableId,Long index);

    List<OrderAllDto> GetOrderByShopId();

    List<OrderInfoDto> getOrderPayAmount(OrderInfoPage orderInfoPage);
}
