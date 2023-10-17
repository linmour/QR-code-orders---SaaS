package com.linmour.product.mapper;

import com.linmour.product.pojo.Do.RProductNonValueSpec;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linmour.product.pojo.Do.RProductValueSpec;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author linmour
* @description 针对表【r_product_non_value_spec】的数据库操作Mapper
* @createDate 2023-08-12 18:34:04
* @Entity com.linmour.product.pojo.Do.RProductNonValueSpec
*/
public interface RProductNonValueSpecMapper extends BaseMapper<RProductNonValueSpec> {

    void deleteNonValue(@Param("productId") Long productId);

    void insertBatchSomeColumn(@Param("list") List<RProductNonValueSpec> list);
}




