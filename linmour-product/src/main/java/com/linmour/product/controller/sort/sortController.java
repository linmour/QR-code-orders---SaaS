package com.linmour.product.controller.sort;

import com.linmour.common.dtos.Result;
import com.linmour.product.service.ProductSortService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product/sort")
public class sortController {

    @Resource
    private ProductSortService productSortService;
    @GetMapping("/getProductSort/{shopId}")
    public Result getProductSort(@PathVariable("shopId") Long shopId){
        return (productSortService.getProductSort(shopId)) ;
    }

}
