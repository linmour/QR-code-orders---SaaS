package com.linmour.product.mapper;

import com.linmour.product.pojo.Do.RProductValueSpec;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author linmour
* @description 针对表【r_product_value_spec】的数据库操作Mapper
* @createDate 2023-08-12 18:34:04
* @Entity com.linmour.product.pojo.Do.RProductValueSpec
*/
public interface RProductValueSpecMapper extends BaseMapper<RProductValueSpec> {

    void deleteValue(@Param("productId") Long productId);
}




