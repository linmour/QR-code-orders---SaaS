package com.linmour.product.mapper;

import com.linmour.product.pojo.Do.SpecSort;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author linmour
* @description 针对表【spec_sort】的数据库操作Mapper
* @createDate 2023-08-12 18:34:04
* @Entity com.linmour.product.pojo.Do.SpecSort
*/
public interface SpecSortMapper extends BaseMapper<SpecSort> {

    void deleteValue(@Param("productId") Long productId);

    void deleteNonValue(@Param("productId") Long productId);
}




