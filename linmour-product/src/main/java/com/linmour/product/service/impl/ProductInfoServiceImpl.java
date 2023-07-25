package com.linmour.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.common.dtos.Result;
import com.linmour.product.pojo.Do.ProductInfo;
import com.linmour.product.service.ProductInfoService;
import com.linmour.product.mapper.ProductInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author linmour
* @description 针对表【product_info】的数据库操作Service实现
* @createDate 2023-07-25 18:45:50
*/
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo>
    implements ProductInfoService{

    @Resource
    private ProductInfoMapper productInfoMapper;
    @Override
    public Result getProductList(Long shopId) {
        return Result.success(productInfoMapper.selectList(new LambdaQueryWrapper<ProductInfo>().eq(ProductInfo::getShopId,shopId)));

    }
}




