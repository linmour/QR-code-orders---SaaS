package com.linmour.order.feign.fallback;

import com.linmour.common.dtos.Result;
import com.linmour.order.feign.ProductFeign;
import org.springframework.stereotype.Component;

@Component
public class ProductFeignFallback implements ProductFeign {
    @Override
    public Result getProductDetails(Long productId) {
        return null;
    }
}
