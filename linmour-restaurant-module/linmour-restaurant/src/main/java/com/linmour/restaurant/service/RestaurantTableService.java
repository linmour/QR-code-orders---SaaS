package com.linmour.restaurant.service;

import com.linmour.security.dtos.Result;
import com.linmour.restaurant.pojo.Do.RestaurantTable;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.restaurant.pojo.Dto.RestaurantTableDto;

import java.io.IOException;

/**
* @author linmour
* @description 针对表【restaurant_table】的数据库操作Service
* @createDate 2023-08-05 20:01:23
*/
public interface RestaurantTableService extends IService<RestaurantTable> {

    Result getTable();

    Result createTable( RestaurantTableDto dto) throws IOException;
}
