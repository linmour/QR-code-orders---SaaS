package com.linmour.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.common.dtos.Result;
import com.linmour.product.pojo.Do.ProductSort;
import com.linmour.product.service.ProductSortService;
import com.linmour.product.mapper.ProductSortMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author linmour
* @description 针对表【product_sort】的数据库操作Service实现
* @createDate 2023-08-12 18:34:04
*/
@Service
public class ProductSortServiceImpl extends ServiceImpl<ProductSortMapper, ProductSort>
    implements ProductSortService{

    @Resource
    private ProductSortMapper productSortMapper;
    @Override
    public Result getProductSort() {
        List<ProductSort> productSorts = productSortMapper.selectList(null);
        return Result.success(productSorts);
    }
}




