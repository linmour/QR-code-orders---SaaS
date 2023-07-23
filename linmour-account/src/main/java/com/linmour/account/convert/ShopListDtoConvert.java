package com.linmour.account.convert;

import com.linmour.account.pojo.Do.Shop;
import com.linmour.account.pojo.Dto.ShopPageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ShopListDtoConvert {

    ShopListDtoConvert INSTANCE = Mappers.getMapper(ShopListDtoConvert.class);

    List<ShopPageDto> shopListToShopPageDtoList(List<Shop> shopList);


}
