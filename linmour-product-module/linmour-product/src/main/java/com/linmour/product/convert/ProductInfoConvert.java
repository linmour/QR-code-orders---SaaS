package com.linmour.product.convert;

import com.linmour.product.pojo.Do.ProductInfo;
import com.linmour.product.pojo.Dto.AddProductDto;
import com.linmour.product.pojo.Dto.ProductInfoPageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductInfoConvert {
    ProductInfoConvert IN = Mappers.getMapper(ProductInfoConvert.class);

    ProductInfo ProductInfoPageDtoToProductInfo(ProductInfoPageDto dto);

    ProductInfo AddProductDtoToProductInfo(AddProductDto addProductDto);
}
