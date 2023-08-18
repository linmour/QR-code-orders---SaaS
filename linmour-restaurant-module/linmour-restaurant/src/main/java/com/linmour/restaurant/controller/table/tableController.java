package com.linmour.restaurant.controller.table;

import com.linmour.common.dtos.Result;
import com.linmour.restaurant.service.RestaurantTableService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/restaurant/table")
public class tableController {

    @Resource
    private RestaurantTableService restaurantTableService;

    @GetMapping("/getTable")
    public Result getTable(@RequestParam("shopId") Long shopId){
        return restaurantTableService.getTable(shopId);
    }
}
