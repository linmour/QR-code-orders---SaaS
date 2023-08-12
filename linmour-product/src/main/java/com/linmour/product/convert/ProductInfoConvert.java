package com.linmour.product.convert;

import com.linmour.product.pojo.Do.ProductInfo;
import com.linmour.product.pojo.Dto.AddProductDto;
import com.linmour.product.pojo.Dto.ProductInfoPageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductInfoConvert {
    ProductInfoConvert IN = Mappers.getMapper(ProductInfoConvert.class);

    default ProductInfo ProductInfoPageDtoToProductInfo(ProductInfoPageDto dto){
        if (dto == null) {
            return null;
        }
        ProductInfo.ProductInfoBuilder productInfoBuilder = ProductInfo.builder();
        productInfoBuilder.id(dto.getId()).intro(dto.getIntro())
                .status(dto.getStatus()?1:0)
                .picture(dto.getPicture())
                .name(dto.getName())
                .specId(dto.getSpecId())
                .sortId(dto.getSortId());
        return productInfoBuilder.build();
    }

    default ProductInfo AddProductDtoToProductInfo(AddProductDto addProductDto){
        if (addProductDto == null) {
            return null;
        } else {
            ProductInfo.ProductInfoBuilder productInfo = ProductInfo.builder();
            productInfo.id(addProductDto.getId());
            productInfo.name(addProductDto.getName());
            productInfo.shopId(addProductDto.getShopId());
            productInfo.intro(addProductDto.getIntro());
            productInfo.specId(addProductDto.getSpecId());
            productInfo.sortId(addProductDto.getSortId());
            return productInfo.build();
        }
    }
}
