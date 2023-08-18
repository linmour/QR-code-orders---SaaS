package com.linmour.product.service;

import com.linmour.product.pojo.Do.RProductNonValueSpec;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author linmour
* @description 针对表【r_product_non_value_spec】的数据库操作Service
* @createDate 2023-08-12 18:34:04
*/
public interface RProductNonValueSpecService extends IService<RProductNonValueSpec> {

    public  List<Long> getNonValueId(Long specId) ;
}
