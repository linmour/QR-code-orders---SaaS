package com.linmour.product.controller.app.product;

import com.linmour.common.dtos.Result;
import com.linmour.product.pojo.Dto.ProductInfoPageDto;
import com.linmour.product.service.ProductInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/app/product/product")
public class AppProductController {

    @Resource
    private ProductInfoService productInfoService;

    @GetMapping("/getProductList")
    public Result getProductList() {
        return productInfoService.getProductList();
    }

}


