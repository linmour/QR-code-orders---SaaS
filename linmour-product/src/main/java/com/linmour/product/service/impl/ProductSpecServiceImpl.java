package com.linmour.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.product.pojo.Do.ProductSpec;
import com.linmour.product.service.ProductSpecService;
import com.linmour.product.mapper.ProductSpecMapper;
import org.springframework.stereotype.Service;

/**
* @author linmour
* @description 针对表【product_spec】的数据库操作Service实现
* @createDate 2023-08-12 18:34:04
*/
@Service
public class ProductSpecServiceImpl extends ServiceImpl<ProductSpecMapper, ProductSpec>
    implements ProductSpecService{

}




