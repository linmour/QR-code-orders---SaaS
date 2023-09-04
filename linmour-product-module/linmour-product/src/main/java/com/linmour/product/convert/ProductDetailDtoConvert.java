package com.linmour.product.convert;

import com.linmour.product.pojo.Do.ProductInfo;
import com.linmour.product.pojo.Dto.ProductDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductDetailDtoConvert {

    ProductDetailDtoConvert IN = Mappers.getMapper(ProductDetailDtoConvert.class);

    List<ProductDetailDto> ProductInfoToProductDetailDto(List<ProductInfo> list);
}
