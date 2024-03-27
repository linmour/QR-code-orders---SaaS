package com.linmour.product.service;

import com.linmour.security.dtos.Result;
import com.linmour.product.pojo.Do.ProductInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.product.pojo.Dto.AddProductDto;
import com.linmour.product.pojo.Dto.AppProductSort;
import com.linmour.product.pojo.Dto.ProductDetailDto;
import com.linmour.product.pojo.Dto.ProductInfoPageDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author linmour
 * @description 针对表【product_info】的数据库操作Service
 * @createDate 2023-08-12 18:34:04
 */
@Transactional
public interface ProductInfoService extends IService<ProductInfo> {
    Result getProductPage(ProductInfoPageDto dto);

    void changeProduct(Long id, Integer status);

    /*
    获取商品的内容
     */
    List<ProductDetailDto> getProductDetails(List<Long> productIds);

    void saveOrUpdateProduct(AddProductDto product);


    List<AppProductSort> getProductList();

}
