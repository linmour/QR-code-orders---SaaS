package com.linmour.product.service;

import com.linmour.product.pojo.Do.ProductSort;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author linmour
* @description 针对表【product_sort】的数据库操作Service
* @createDate 2023-08-12 18:34:04
*/
public interface ProductSortService extends IService<ProductSort> {
    List<ProductSort> getProductSort();

    void createProductSort(ProductSort productSort);
}
