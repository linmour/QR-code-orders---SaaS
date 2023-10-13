package com.linmour.product.controller.admin.product;

import com.linmour.common.dtos.Result;
import com.linmour.product.pojo.Dto.AddProductDto;
import com.linmour.product.pojo.Dto.ProductInfoPageDto;
import com.linmour.product.service.ProductInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return productInfoService.changeProduct(Long.valueOf(intValue.intValue()),(Integer) (map.get("status")));
    }

    @GetMapping("/getProductDetails")
    public Result getProductDetails(@RequestParam("productIds") List<Long> productIds) {
        productIds.removeIf(id -> id == 0);
        return (productInfoService.getProductDetails(productIds));
    }

    @PostMapping("/addProduct")
    public Result addProduct(@RequestBody Map<String, AddProductDto> map){
        AddProductDto product = map.get("product");
        return productInfoService.addProduct(product);
    }

    @PostMapping("/updateProduct")
    public Result updateProduct(@RequestBody Map<String, AddProductDto> map){
        AddProductDto product = map.get("product");
        return productInfoService.updateProduct(product);
    }


}


