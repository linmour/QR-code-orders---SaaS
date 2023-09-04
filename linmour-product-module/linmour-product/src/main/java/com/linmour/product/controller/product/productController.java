package com.linmour.product.controller.product;

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
public class productController {

    @Resource
    private ProductInfoService productInfoService;

    @GetMapping("/getProductList")
    public Result getProductList(@Valid ProductInfoPageDto dto) {
        return (productInfoService.getProductList(dto));
    }

    @PostMapping("/changeProduct")
    public Result changeProduct(@RequestBody Map<String,ProductInfoPageDto> map){
        ProductInfoPageDto productInfo = map.get("product");
        return productInfoService.changeProduct(productInfo);
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

    @PostMapping("/upload_product")
//    ,@RequestParam("shopId") String shopId 接收前端的参数
    public Result b(@RequestParam("file") MultipartFile[] file){
        return productInfoService.uploadProductImg(file);
    }

}


