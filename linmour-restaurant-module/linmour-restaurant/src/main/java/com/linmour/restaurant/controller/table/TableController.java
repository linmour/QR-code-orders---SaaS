package com.linmour.restaurant.controller.table;

import com.linmour.security.dtos.Result;
import com.linmour.restaurant.pojo.Do.RestaurantTable;
import com.linmour.restaurant.pojo.Dto.RestaurantTableDto;
import com.linmour.restaurant.service.RestaurantTableService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/restaurant/table")
public class TableController {

    @Resource
    private RestaurantTableService restaurantTableService;

    @GetMapping("/getTable")
    public Result getTable(){
        return restaurantTableService.getTable();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Map<String,RestaurantTable> map){
        restaurantTableService.updateById(map.get("dto"));
        return Result.success();
    }

    @PostMapping("/createTable")
    public Result createTable(@RequestBody Map<String,RestaurantTableDto> map) throws IOException {
        RestaurantTableDto dto = map.get("dto");
        return restaurantTableService.createTable(dto);
    }


}
