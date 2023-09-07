package com.linmour.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.product.pojo.Do.RProductValueSpec;
import com.linmour.product.service.RProductValueSpecService;
import com.linmour.product.mapper.RProductValueSpecMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author linmour
* @description 针对表【r_product_value_spec】的数据库操作Service实现
* @createDate 2023-08-12 18:34:04
*/
@Service
public class RProductValueSpecServiceImpl extends ServiceImpl<RProductValueSpecMapper, RProductValueSpec>
    implements RProductValueSpecService{

    @Override
    public List<Long> getValueId(Long specId) {
        return this.listObjs(
                new LambdaQueryWrapper<RProductValueSpec>()
                        .select(RProductValueSpec::getValueSpecId)
                        .eq(RProductValueSpec::getProductSpecId,specId), o -> Long.valueOf(o.toString()));

    }
}




