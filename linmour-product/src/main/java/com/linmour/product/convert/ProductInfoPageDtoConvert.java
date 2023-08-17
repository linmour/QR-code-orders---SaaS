package com.linmour.product.convert;


import com.linmour.product.pojo.Do.ProductInfo;
import com.linmour.product.pojo.Dto.ProductInfoPageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mapper
public interface ProductInfoPageDtoConvert {
    ProductInfoPageDtoConvert INSTANCE = Mappers.getMapper(ProductInfoPageDtoConvert.class);

    default List<ProductInfoPageDto> ProductInfoToProductInfoPageDto(List<ProductInfo> productInfos){
        if (productInfos == null) {
            return null;
        } else {
            List<ProductInfoPageDto> list = new ArrayList(productInfos.size());
            Iterator var3 = productInfos.iterator();

            while(var3.hasNext()) {
                ProductInfo productInfo = (ProductInfo)var3.next();
                list.add(this.productInfoToProductInfoPageDto(productInfo));
            }

            return list;
        }
    }

     default ProductInfoPageDto productInfoToProductInfoPageDto(ProductInfo productInfo) {
        if (productInfo == null) {
            return null;
        } else {
            ProductInfoPageDto.ProductInfoPageDtoBuilder productInfoPageDto = ProductInfoPageDto.builder();
            productInfoPageDto.id(productInfo.getId());
            productInfoPageDto.name(productInfo.getName());
            productInfoPageDto.shopId(productInfo.getShopId());
            productInfoPageDto.intro(productInfo.getIntro());
            productInfoPageDto.specId(productInfo.getSpecId());
            productInfoPageDto.status(productInfo.getStatus() == 1 ? true : false);
            productInfoPageDto.picture(productInfo.getPicture());
            productInfoPageDto.sortId(productInfo.getSortId());
            productInfoPageDto.price(productInfo.getPrice());
            productInfoPageDto.selectNum(0);
            return productInfoPageDto.build();
        }
    }
}
