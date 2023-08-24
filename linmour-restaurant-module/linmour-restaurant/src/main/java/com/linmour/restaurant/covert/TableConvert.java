package com.linmour.restaurant.covert;

import com.linmour.restaurant.pojo.Do.RestaurantTable;
import com.linmour.restaurant.pojo.Dto.RestaurantTableDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TableConvert {
    TableConvert IN  = Mappers.getMapper(TableConvert.class);

    RestaurantTable TableDtoToTable(RestaurantTableDto restaurantTableDto);

}
