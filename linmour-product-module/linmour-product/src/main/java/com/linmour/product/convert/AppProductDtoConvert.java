package com.linmour.product.convert;

import com.linmour.product.pojo.Do.ProductInfo;
import com.linmour.product.pojo.Dto.AppProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AppProductDtoConvert {
    AppProductDtoConvert IN = Mappers.getMapper(AppProductDtoConvert.class);

    AppProductDto convert(ProductInfo productInfo);
    List<AppProductDto> convert(List<ProductInfo> productInfo);
}
