package com.linmour.product.convert;

import com.linmour.product.pojo.Do.ProductInfo;
import com.linmour.product.pojo.Dto.ProductDetailDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-03T10:09:22+0800",
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
        productDetailDto.spec( dto.getSpec() );
        productDetailDto.status( dto.getStatus() );
        productDetailDto.picture( dto.getPicture() );

        return productDetailDto.build();
    }
}
