package com.linmour.product.convert;

import com.linmour.product.pojo.Do.ProductInventory;
import com.linmour.product.pojo.Dto.InventoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InventoryDtoConvert {
    InventoryDtoConvert IN = Mappers.getMapper(InventoryDtoConvert.class);

    default  InventoryDto InventoryDtoToProductInventory(ProductInventory dto){
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
}
