package com.linmour.order.service;

import com.linmour.common.dtos.Result;
import com.linmour.order.pojo.Do.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.order.pojo.Dto.SubmitOrderDto;
import org.springframework.transaction.annotation.Transactional;

/**
* @author linmour
* @description 针对表【order_info】的数据库操作Service
* @createDate 2023-08-16 15:25:38
*/
@Transactional
public interface OrderInfoService extends IService<OrderInfo> {

    Result createOrder(CreateOrderDto createOrderDto);

    Result submitOrder(SubmitOrderDto submitOrderDto);
}
