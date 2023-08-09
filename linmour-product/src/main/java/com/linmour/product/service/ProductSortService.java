package com.linmour.product.service;

import com.linmour.common.dtos.Result;
import com.linmour.product.pojo.Do.ProductSort;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author linmour
* @description 针对表【product_sort】的数据库操作Service
* @createDate 2023-07-29 14:29:16
*/
public interface ProductSortService extends IService<ProductSort> {

    Result getProductSort(Long shopId);
}
