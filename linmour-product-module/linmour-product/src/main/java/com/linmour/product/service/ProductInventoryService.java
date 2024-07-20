package com.linmour.product.service;

import com.linmour.product.pojo.Do.ProductInventory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.product.pojo.Dto.ProductInventoryAllDto;
import com.linmour.security.dtos.PageResult;

import java.util.List;

/**
* @author linmour
* @description 针对表【product_inventory】的数据库操作Service
* @createDate 2023-08-12 18:34:04
*/
public interface ProductInventoryService extends IService<ProductInventory> {

    PageResult getProductInventory(ProductInventoryAllDto dto);

    void createInventory(ProductInventoryAllDto dto);

    void deletedInventory(Long id);

    ProductInventory getProductInventoryById(Long id);

    void updateInventory(ProductInventory productInventory);

    void reduceInventories(List<Long> productIds);
}
