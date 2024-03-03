package com.linmour.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.common.dtos.Result;
import com.linmour.order.pojo.Do.OrderInfo;
import com.linmour.order.pojo.Dto.CheckoutDto;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.pojo.Dto.OrderInfoDto;

import org.springframework.transaction.annotation.Transactional;

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

    Result getOrderInfo(Long tableId);

    Result changeOrder(OrderInfoDto orderInfoDto);
}
