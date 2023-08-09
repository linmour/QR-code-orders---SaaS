package com.linmour.product.controller.product;

import com.linmour.common.dtos.Result;
import com.linmour.product.convert.ProductInfoPageDtoConvert;
import com.linmour.product.pojo.Do.ProductInfo;
import com.linmour.product.pojo.Dto.AddProductDto;
import com.linmour.product.pojo.Dto.ProductInfoPageDto;
import com.linmour.product.service.ProductInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
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

    @GetMapping("/getProductDetails/{productId}")
    public Result getProductDetails(@PathVariable Long productId) {
        return (productInfoService.getProductDetails(productId));
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
    public Result b(@RequestParam("file") MultipartFile[] file,@RequestParam("shopId") String shopId){
        return productInfoService.uploadProductImg(file,shopId);
    }

}


