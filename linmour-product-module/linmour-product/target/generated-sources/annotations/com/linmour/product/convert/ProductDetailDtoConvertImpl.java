package com.linmour.product.convert;

import com.linmour.product.pojo.Do.ProductInfo;
import com.linmour.product.pojo.Dto.ProductDetailDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-23T19:33:10+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_361 (Oracle Corporation)"
)
public class ProductDetailDtoConvertImpl implements ProductDetailDtoConvert {

    @Override
    public ProductDetailDto ProductInfoToProductDetailDto(ProductInfo dto) {
        if ( dto == null ) {
            return null;
        }

        ProductDetailDto.ProductDetailDtoBuilder productDetailDto = ProductDetailDto.builder();

        productDetailDto.id( dto.getId() );
        productDetailDto.name( dto.getName() );
        productDetailDto.shopId( dto.getShopId() );
        productDetailDto.intro( dto.getIntro() );
        productDetailDto.specId( dto.getSpecId() );
        productDetailDto.status( dto.getStatus() );
        productDetailDto.sortId( dto.getSortId() );
        productDetailDto.picture( dto.getPicture() );
        productDetailDto.price( dto.getPrice() );

        return productDetailDto.build();
    }
}
