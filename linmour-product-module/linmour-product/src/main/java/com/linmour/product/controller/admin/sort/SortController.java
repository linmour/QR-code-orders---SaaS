package com.linmour.product.controller.admin.sort;

import com.linmour.common.dtos.Result;
import com.linmour.product.service.ProductSortService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product/sort")
public class SortController {

    @Resource
    private ProductSortService productSortService;
    @GetMapping("/getProductSort")
    public Result getProductSort(){
        Result productSort = productSortService.getProductSort();
        return (productSort) ;
    }

}
