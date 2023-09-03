package com.linmour.restaurant.controller.table;

import com.linmour.common.dtos.Result;
import com.linmour.restaurant.pojo.Dto.RestaurantTableDto;
import com.linmour.restaurant.service.RestaurantTableService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/restaurant/table")
public class tableController {

    @Resource
    private RestaurantTableService restaurantTableService;

    @GetMapping("/getTable")
    public Result getTable(){
        return restaurantTableService.getTable();
    }

    @PostMapping("/createTable")
    public Result createTable(@RequestBody Map<String,RestaurantTableDto> map)  {
        RestaurantTableDto dto = map.get("dto");
        return restaurantTableService.createTable(dto);
    }


}
