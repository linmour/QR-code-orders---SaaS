package com.linmour.product.service;

import com.linmour.common.dtos.Result;
import com.linmour.product.pojo.Do.ProductInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.product.pojo.Dto.AddProductDto;
import com.linmour.product.pojo.Dto.ProductInfoPageDto;
import org.springframework.web.multipart.MultipartFile;

/**
* @author linmour
* @description 针对表【product_info】的数据库操作Service
* @createDate 2023-07-25 18:45:50
*/
public interface ProductInfoService extends IService<ProductInfo> {

    Result getProductList(ProductInfoPageDto dto);


    Result changeProduct(ProductInfoPageDto productInfo);

    Result getProductDetails(Long productId);

    Result addProduct(AddProductDto product);

    Result uploadProductImg(MultipartFile[] file,String shopID);

    Result updateProduct(AddProductDto product);
}
