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

    default List<ShopPageDto> ShopList(List<Shop> shopList){
        if (shopList == null){
            return null;
        }
        List<ShopPageDto> shopPageDtos = new ArrayList<>();
        for (Shop shop : shopList) {
            shopPageDtos.add(ShopPageDto.builder()
                    .auditStatus(shop.getAuditStatus())
                    .id(shop.getId())
                    .intro(shop.getIntro())
                    .status(shop.getStatus())
                    .certificate(shop.getCertificate())
                    .name(shop.getName())
                    .position(shop.getPosition())
                    .parentId(shop.getParentId())
                    .createTime(shop.getCreateTime())
                    .businessStatus(shop.getBusinessStatus()).build());
        }
        return shopPageDtos;
    }

}
