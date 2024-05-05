package com.linmour.product.convert;

import com.linmour.product.pojo.Do.ProductInfo;
import com.linmour.product.pojo.Do.ProductInventory;
import com.linmour.product.pojo.Do.ProductSort;
import com.linmour.product.pojo.Dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ProductInfoConvert {
    ProductInfoConvert IN = Mappers.getMapper(ProductInfoConvert.class);

    ProductInfo AddProductDtoToProductInfo(AddProductDto addProductDto);
    AppProductDto convert(ProductInfo productInfo);
    List<AppProductSort> convert(List<ProductSort> list);

    List<ProductDetailDto> ProductInfoToProductDetailDto(List<ProductInfo> list);
    default List<ProductInventory> inventoryDtoListToProductInventoryList(List<ProductInventoryAllDto> list, Long productId) {
        if (list == null) {
            return null;
        }
        List<ProductInventory> productInventoryList = new ArrayList<>();
        for (ProductInventoryAllDto dto : list) {
            productInventoryList.add(inventoryDtoToProductInventory(dto,productId));
        }
        return productInventoryList;
    }

     ProductInventory inventoryDtoToProductInventory(ProductInventoryAllDto dto, Long productId);

}
