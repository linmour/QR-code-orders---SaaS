package com.linmour.order.controller.admin.order;

import com.linmour.common.dtos.Result;
import com.linmour.order.pojo.Dto.OrderInfoDto;
import com.linmour.order.service.OrderInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/order/order")
public class OrderController {
    @Resource
    private OrderInfoService orderInfoService;
    @GetMapping("/getOrderInfo/{tableId}")
    public Result getOrderInfo(@PathVariable Long tableId){
        return orderInfoService.getOrderInfo(tableId);
    }



    @PostMapping("/changeOrder")
    public Result changeOrderStatus(@RequestBody Map<String,OrderInfoDto> map){
        OrderInfoDto orderInfoDto = map.get("orderInfoDto");
        return orderInfoService.changeOrder(orderInfoDto);
    }

}
