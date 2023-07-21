package com.linmour.account.convert;

import com.linmour.account.pojo.Do.Shop;
import com.linmour.account.pojo.Dto.ShopDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ShopListDtoConvert {

    ShopListDtoConvert INSTANCE = Mappers.getMapper(ShopListDtoConvert.class);

    default List<ShopDto> ShopList(List<Shop> shopList){
        if (shopList == null){
            return null;
        }
        List<ShopDto> shopDtos = new ArrayList<>();
        for (Shop shop : shopList) {
            shopDtos.add(ShopDto.builder()
                    .auditStatus(shop.getAuditStatus())
                    .id(shop.getId())
                    .intro(shop.getIntro())
                    .status(shop.getStatus())
                    .certificate(shop.getCertificate())
                    .name(shop.getName())
                    .position(shop.getPosition())
                    .parentId(shop.getParentId()).build());
        }
        return shopDtos;
    }

}
