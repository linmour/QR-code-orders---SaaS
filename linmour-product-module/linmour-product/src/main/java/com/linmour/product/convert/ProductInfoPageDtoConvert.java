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

    List<ProductInfoPageDto> ProductInfoToProductInfoPageDto(List<ProductInfo> productInfos);

//     ProductInfoPageDto productInfoToProductInfoPageDto(ProductInfo productInfo) ;
}
