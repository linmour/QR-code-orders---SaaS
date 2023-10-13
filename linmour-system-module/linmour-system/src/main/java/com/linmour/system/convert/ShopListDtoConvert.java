package com.linmour.system.convert;

import com.linmour.system.pojo.Do.Shop;
import com.linmour.system.pojo.Dto.ShopPageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ShopListDtoConvert {

    ShopListDtoConvert INSTANCE = Mappers.getMapper(ShopListDtoConvert.class);

    List<ShopPageDto> shopListToShopPageDtoList(List<Shop> shopList);


}
