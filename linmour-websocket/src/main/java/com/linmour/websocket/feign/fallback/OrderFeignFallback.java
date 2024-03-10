package com.linmour.websocket.feign.fallback;

import com.linmour.order.pojo.Dto.CreateOrderDto;
import com.linmour.security.dtos.Result;
import com.linmour.websocket.feign.OrderFeign;

public class OrderFeignFallback implements OrderFeign  {

    @Override
    public Result getOrderInfo(Long tableId) {
        return null;
    }

    @Override
    public Result createOrder(CreateOrderDto createOrderDto) {
        return null;
    }


}
