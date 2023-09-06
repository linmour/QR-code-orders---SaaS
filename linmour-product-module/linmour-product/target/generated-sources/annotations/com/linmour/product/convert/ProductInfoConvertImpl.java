package com.linmour.product.convert;

import com.linmour.product.pojo.Do.ProductInfo;
import com.linmour.product.pojo.Dto.AddProductDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-06T19:36:10+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_361 (Oracle Corporation)"
)
public class ProductInfoConvertImpl implements ProductInfoConvert {

    @Override
    public ProductInfo AddProductDtoToProductInfo(AddProductDto addProductDto) {
        if ( addProductDto == null ) {
            return null;
        }

        ProductInfo.ProductInfoBuilder productInfo = ProductInfo.builder();

        productInfo.id( addProductDto.getId() );
        productInfo.name( addProductDto.getName() );
        productInfo.shopId( addProductDto.getShopId() );
        productInfo.intro( addProductDto.getIntro() );
        productInfo.specId( addProductDto.getSpecId() );
        productInfo.sortId( addProductDto.getSortId() );
        productInfo.price( addProductDto.getPrice() );

        return productInfo.build();
    }
}
