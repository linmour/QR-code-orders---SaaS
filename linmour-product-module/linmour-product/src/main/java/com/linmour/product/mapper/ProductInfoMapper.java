package com.linmour.product.mapper;

import com.linmour.product.pojo.Do.ProductInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author linmour
* @description 针对表【product_info】的数据库操作Mapper
* @createDate 2023-08-12 18:34:04
* @Entity com.linmour.product.pojo.Do.ProductInfo
*/
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {

    String getSort(@Param("productId") Long productId);
}




