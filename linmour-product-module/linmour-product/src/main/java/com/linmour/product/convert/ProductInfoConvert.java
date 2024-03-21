package com.linmour.product.convert;

import com.linmour.product.pojo.Do.ProductInfo;
import com.linmour.product.pojo.Do.ProductInventory;
import com.linmour.product.pojo.Do.ProductSort;
import com.linmour.product.pojo.Dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static com.linmour.security.utils.SecurityUtils.getShopId;

@Mapper
public interface ProductInfoConvert {
    ProductInfoConvert IN = Mappers.getMapper(ProductInfoConvert.class);

    ProductInfo ProductInfoPageDtoToProductInfo(ProductInfoPageDto dto);

    ProductInfo AddProductDtoToProductInfo(AddProductDto addProductDto);
    AppProductDto convert(ProductInfo productInfo);
    List<AppProductDto> convertProductInfo(List<ProductInfo> productInfo);
    List<AppProductSort> convert(List<ProductSort> list);
    default InventoryDto InventoryDtoToProductInventory(ProductInventory dto){
        if (dto == null){
            return null;
        }
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setName(dto.getName());

        String num = dto.getNum().toString();
        String unit = dto.getUnit();
        String numAndUnit = num + "/" + unit;
        inventoryDto.setNumAndUnit(numAndUnit);
        return inventoryDto;
    }
    List<ProductDetailDto> ProductInfoToProductDetailDto(List<ProductInfo> list);
    List<ProductInfoPageDto> ProductInfoToProductInfoPageDto(List<ProductInfo> productInfos);
    default List<ProductInventory> inventoryDtoListToProductInventoryList(List<InventoryDto> list, Long productId) {
        if (list == null) {
            return null;
        }
        List<ProductInventory> productInventoryList = new ArrayList<>();
        for (InventoryDto dto : list) {
            productInventoryList.add(inventoryDtoToProductInventory(dto,productId));
        }
        return productInventoryList;
    }

    default ProductInventory inventoryDtoToProductInventory(InventoryDto dto, Long productId) {
        if (dto == null){
            return null;
        }
        ProductInventory productInventory = new ProductInventory();
        String[] split = dto.getNumAndUnit().split("/");
        productInventory.setNum(Integer.valueOf(split[0]));
        productInventory.setUnit(split[1]);
        productInventory.setName(dto.getName());
        productInventory.setShopId(getShopId());
        productInventory.setProductId(productId);
        return productInventory;
    }


}
