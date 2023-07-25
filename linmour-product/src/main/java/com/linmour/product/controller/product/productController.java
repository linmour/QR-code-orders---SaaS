package com.linmour.product.controller.product;

import com.linmour.common.dtos.Result;
import com.linmour.product.service.ProductInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/a")
public class productController {

    @Resource
    private ProductInfoService productInfoService;
    @GetMapping("/b/{shopId}")
    public Result getProductList(@PathVariable Long shopId){
        return productInfoService.getProductList(shopId);
    }
}
