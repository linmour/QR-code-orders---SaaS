package com.linmour.product.mapper;

import com.linmour.product.pojo.Do.ProductInventory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author linmour
* @description 针对表【product_inventory】的数据库操作Mapper
* @createDate 2023-08-12 18:34:04
* @Entity com.linmour.product.pojo.Do.ProductInventory
*/
public interface ProductInventoryMapper extends BaseMapper<ProductInventory> {
    List<ProductInventory> getInventory(@Param("shopId") Long shopId);
}




