package com.linmour.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.product.pojo.Do.ProductInventory;
import com.linmour.product.service.ProductInventoryService;
import com.linmour.product.mapper.ProductInventoryMapper;
import org.springframework.stereotype.Service;

/**
* @author linmour
* @description 针对表【product_inventory】的数据库操作Service实现
* @createDate 2023-08-12 18:34:04
*/
@Service
public class ProductInventoryServiceImpl extends ServiceImpl<ProductInventoryMapper, ProductInventory>
    implements ProductInventoryService{

}




