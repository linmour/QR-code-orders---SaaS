package com.linmour.order.feign;

import com.linmour.security.dtos.Result;
import com.linmour.order.feign.fallback.ProductFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "linmour-product",fallback = ProductFeignFallback.class)
public interface ProductFeign {
    @GetMapping("/product/product/getProductDetails")
    Result getProductDetails(@RequestParam("productIds") List<Long> productIds) ;


    @PostMapping("/product/inventory/reduceInventories")
    Result reduceInventories(@RequestBody List<Long> productIds) ;
}
