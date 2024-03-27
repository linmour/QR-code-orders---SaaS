package com.linmour.product.controller.admin.product;

import com.linmour.security.dtos.Result;
import com.linmour.product.pojo.Dto.AddProductDto;
import com.linmour.product.pojo.Dto.ProductInfoPageDto;
import com.linmour.product.service.ProductInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product/product")
public class ProductController {

    @Resource
    private ProductInfoService productInfoService;

    @GetMapping("/getProductPage")
    public Result getProductPage(@Valid ProductInfoPageDto dto) {
        return (productInfoService.getProductPage(dto));
    }

    @PostMapping("/changeProduct")
    public Result changeProduct(@RequestBody Map map){
        Integer intValue = (Integer) map.get("id");
        productInfoService.changeProduct(Long.valueOf(intValue.intValue()),(Integer) (map.get("status")));
        return Result.success();
    }

    @GetMapping("/getProductDetails")
    public Result getProductDetails(@RequestParam("productIds") List<Long> productIds) {
        productIds.removeIf(id -> id == 0);
        return Result.success(productInfoService.getProductDetails(productIds));
    }

    @PostMapping("/saveOrUpdateProduct")
    public Result saveOrUpdateProduct(@RequestBody Map<String, AddProductDto> map){
        AddProductDto product = map.get("product");
        productInfoService.saveOrUpdateProduct(product);
        return Result.success();
    }

}


