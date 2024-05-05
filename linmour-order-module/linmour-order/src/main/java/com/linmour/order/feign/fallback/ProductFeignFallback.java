package com.linmour.order.feign.fallback;

import com.linmour.security.dtos.Result;
import com.linmour.order.feign.ProductFeign;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductFeignFallback implements ProductFeign {
    @Override

    public Result getProductDetails(List<Long> productIds) {
        return null;
    }

    @Override
    public Result reduceInventories(List<Long> productIds) {
        return null;
    }
}
