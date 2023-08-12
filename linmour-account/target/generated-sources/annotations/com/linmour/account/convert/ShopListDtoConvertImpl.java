package com.linmour.account.convert;

import com.linmour.account.pojo.Do.Shop;
import com.linmour.account.pojo.Dto.ShopPageDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-12T15:58:26+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_361 (Oracle Corporation)"
)
public class ShopListDtoConvertImpl implements ShopListDtoConvert {

    @Override
    public List<ShopPageDto> shopListToShopPageDtoList(List<Shop> shopList) {
        if ( shopList == null ) {
            return null;
        }

        List<ShopPageDto> list = new ArrayList<ShopPageDto>( shopList.size() );
        for ( Shop shop : shopList ) {
            list.add( shopToShopPageDto( shop ) );
        }

        return list;
    }

    protected ShopPageDto shopToShopPageDto(Shop shop) {
        if ( shop == null ) {
            return null;
        }

        ShopPageDto.ShopPageDtoBuilder shopPageDto = ShopPageDto.builder();

        shopPageDto.id( shop.getId() );
        shopPageDto.name( shop.getName() );
        shopPageDto.certificate( shop.getCertificate() );
        shopPageDto.intro( shop.getIntro() );
        shopPageDto.status( shop.getStatus() );
        shopPageDto.auditStatus( shop.getAuditStatus() );
        shopPageDto.businessStatus( shop.getBusinessStatus() );
        shopPageDto.parentId( shop.getParentId() );
        shopPageDto.position( shop.getPosition() );
        shopPageDto.createTime( shop.getCreateTime() );

        return shopPageDto.build();
    }
}
