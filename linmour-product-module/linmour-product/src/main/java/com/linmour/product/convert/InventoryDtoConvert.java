package com.linmour.product.convert;

import com.linmour.product.pojo.Do.ProductInventory;
import com.linmour.product.pojo.Dto.InventoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InventoryDtoConvert {
    InventoryDtoConvert IN  = Mappers.getMapper(InventoryDtoConvert.class);

    default InventoryDto ProductInventoryToInventoryDto(ProductInventory productInventory) {
        if ( productInventory == null ) {
            return null;
        }

        InventoryDto inventoryDto = new InventoryDto();

        inventoryDto.setName( productInventory.getName() );
        String numAndUnit = "";
        Integer num = productInventory.getNum();
        String unit = productInventory.getUnit();
        numAndUnit = numAndUnit + num.toString() +"/"+ unit;
        inventoryDto.setNumAndUnit(numAndUnit);

        return inventoryDto;
    }
}
