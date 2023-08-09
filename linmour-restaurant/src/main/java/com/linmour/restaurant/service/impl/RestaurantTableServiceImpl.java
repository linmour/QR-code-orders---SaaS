package com.linmour.restaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.common.dtos.Result;
import com.linmour.restaurant.pojo.Do.RestaurantTable;
import com.linmour.restaurant.service.RestaurantTableService;
import com.linmour.restaurant.mapper.RestaurantTableMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author linmour
* @description 针对表【restaurant_table】的数据库操作Service实现
* @createDate 2023-08-05 20:01:23
*/
@Service
public class RestaurantTableServiceImpl extends ServiceImpl<RestaurantTableMapper, RestaurantTable>
    implements RestaurantTableService{

    @Resource
    private RestaurantTableMapper restaurantTableMapper;
    @Override
    public Result getTable(Long shopId) {
        List<RestaurantTable> restaurantTables = restaurantTableMapper.selectList(new LambdaQueryWrapper<RestaurantTable>().eq(RestaurantTable::getShopId, shopId));
        return Result.success(restaurantTables);

    }
}




