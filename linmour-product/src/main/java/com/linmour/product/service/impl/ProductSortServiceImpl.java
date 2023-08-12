package com.linmour.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.common.dtos.Result;
import com.linmour.product.mapper.ProductInfoMapper;
import com.linmour.product.pojo.Do.ProductSort;
import com.linmour.product.service.ProductSortService;
import com.linmour.product.mapper.ProductSortMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author linmour
* @description 针对表【product_sort】的数据库操作Service实现
* @createDate 2023-07-29 14:29:16
*/
@Service
public class ProductSortServiceImpl extends ServiceImpl<ProductSortMapper, ProductSort>
    implements ProductSortService{
    @Resource
    private ProductSortMapper productSortMapper;

    @Override
    public Result getProductSort(Long shopId) {
        List<ProductSort> productSorts = productSortMapper.selectList(new LambdaQueryWrapper<ProductSort>().eq(ProductSort::getShopId, shopId));
        return Result.success(productSorts);
    }
}




