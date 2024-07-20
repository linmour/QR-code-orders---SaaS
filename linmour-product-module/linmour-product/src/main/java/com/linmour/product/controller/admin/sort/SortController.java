package com.linmour.product.controller.admin.sort;

import com.linmour.security.dtos.Result;
import com.linmour.product.pojo.Do.ProductSort;
import com.linmour.product.service.ProductSortService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product/sort")
public class SortController {

    @Resource
    private ProductSortService productSortService;

    @GetMapping("/getProductSort")
    public Result getProductSort() {
        return Result.success(productSortService.getProductSort());
    }

    @PostMapping("/createProductSort")
    public Result createProductSort(@RequestBody ProductSort productSort) {
        productSortService.createProductSort(productSort);
        return Result.success();
    }

}
