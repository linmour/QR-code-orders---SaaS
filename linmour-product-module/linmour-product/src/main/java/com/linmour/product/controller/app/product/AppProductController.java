package com.linmour.product.controller.app.product;

import com.linmour.security.dtos.Result;
import com.linmour.product.service.ProductInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/product/product")
public class AppProductController {

    @Resource
    private ProductInfoService productInfoService;

    @GetMapping("/getProductList")
    public Result getProductList() {
        return Result.success(productInfoService.getProductList());
    }

}


