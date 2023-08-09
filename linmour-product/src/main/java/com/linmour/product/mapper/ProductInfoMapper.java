package com.linmour.product.mapper;

import com.linmour.product.pojo.Do.ProductInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linmour.product.pojo.Do.ProductSize;
import com.linmour.product.pojo.Do.ProductSpec;
import com.linmour.product.pojo.Do.ProductTaste;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author linmour
* @description 针对表【product_info】的数据库操作Mapper
* @createDate 2023-07-25 18:45:50
* @Entity com.linmour.product.pojo.Do.ProductInfo
*/
@Mapper
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {



    String getSort(@Param("id") Long id);

    ProductSpec getspecId(@Param("id") Long id);

    List<ProductSize> getsize(@Param("id") Long id);

    List<ProductTaste> gettaste(@Param("id") Long id);
}




