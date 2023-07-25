package com.linmour.product.mapper;

import com.linmour.product.pojo.Do.ProductInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author linmour
* @description 针对表【product_info】的数据库操作Mapper
* @createDate 2023-07-25 18:45:50
* @Entity com.linmour.product.pojo.Do.ProductInfo
*/
@Mapper
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {

}




