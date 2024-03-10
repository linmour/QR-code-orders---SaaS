package com.linmour.product.convert;

import com.linmour.product.pojo.Do.ProductInfo;
import com.linmour.product.pojo.Do.ProductSort;
import com.linmour.product.pojo.Dto.AppProductDto;
import com.linmour.product.pojo.Dto.AppProductSort;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AppProductSortConvert {

    AppProductSortConvert IN = Mappers.getMapper(AppProductSortConvert.class);

    List<AppProductSort> convert(List<ProductSort> list);
}
