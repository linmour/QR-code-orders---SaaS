package com.linmour.product.convert;

import com.linmour.product.pojo.Do.ProductInfo;
import com.linmour.product.pojo.Dto.ProductDetailDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-07T18:48:52+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_351 (Oracle Corporation)"
)
public class ProductDetailDtoConvertImpl implements ProductDetailDtoConvert {

    @Override
    public List<ProductDetailDto> ProductInfoToProductDetailDto(List<ProductInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductDetailDto> list1 = new ArrayList<ProductDetailDto>( list.size() );
        for ( ProductInfo productInfo : list ) {
            list1.add( productInfoToProductDetailDto( productInfo ) );
        }

        return list1;
    }

    protected ProductDetailDto productInfoToProductDetailDto(ProductInfo productInfo) {
        if ( productInfo == null ) {
            return null;
        }

        ProductDetailDto.ProductDetailDtoBuilder productDetailDto = ProductDetailDto.builder();

        productDetailDto.id( productInfo.getId() );
        productDetailDto.name( productInfo.getName() );
        productDetailDto.shopId( productInfo.getShopId() );
        productDetailDto.intro( productInfo.getIntro() );
        productDetailDto.specId( productInfo.getSpecId() );
        productDetailDto.status( productInfo.getStatus() );
        productDetailDto.sortId( productInfo.getSortId() );
        productDetailDto.picture( productInfo.getPicture() );
        productDetailDto.price( productInfo.getPrice() );

        return productDetailDto.build();
    }
}
