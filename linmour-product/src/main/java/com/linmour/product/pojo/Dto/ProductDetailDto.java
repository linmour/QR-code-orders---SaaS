package com.linmour.product.pojo.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailDto implements Serializable {


    private Long id;

    /**
     * 商品名字
     */
    
    private String name;
    /**
     * 店铺id
     */
    
    private Long shopId;

    /**
     * 商品简介
     */
    
    private String intro;


    /**
     * 是否需要选规格 0不要 1要
     */
    
    private Integer spec;

    /**
     * 商品状态 0下架 1上架
     */
    
    private Integer status;

    /**
     * 商品图
     */
    
    private String picture;

    /**
     * 分类名
     */

    private String sort;

    
    private List<ProductSizeAndPrice> productSizeAndPriceList;

    private List<TasteAndId> tasteAndId;

}
