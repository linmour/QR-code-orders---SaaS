package com.linmour.product.pojo.Dto;


import com.linmour.common.dtos.PageParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class ProductInfoPageDto extends PageParam implements Serializable {

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

    private BigDecimal price;

    private Byte valueSpec;


    private Byte nonValueSpec;

    /**
     * 商品状态 0下架 1上架
     */
   
    private Integer status;

    /**
     * 商品图
     */
   
    private String picture;

    private Long sortId;



}
