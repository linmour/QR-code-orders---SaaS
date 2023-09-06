package com.linmour.order.feign;

import com.linmour.common.dtos.Result;
import com.linmour.order.feign.fallback.ProductFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "linmour-product",fallback = ProductFeignFallback.class)
public interface ProductFeign {
    @GetMapping("/product/product/getProductDetails/{productId}")
    Result getProductDetails(@PathVariable Long productId) ;
}
