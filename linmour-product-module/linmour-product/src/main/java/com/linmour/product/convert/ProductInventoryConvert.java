package com.linmour.product.convert;

import com.linmour.product.pojo.Do.ProductInventory;
import com.linmour.product.pojo.Dto.InventoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static com.linmour.common.utils.SecurityUtils.getShopId;

@Mapper
public interface ProductInventoryConvert {
    ProductInventoryConvert IN = Mappers.getMapper(ProductInventoryConvert.class);

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
