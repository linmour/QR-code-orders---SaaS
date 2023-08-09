package com.linmour.product.pojo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductDto implements Serializable {
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
     * 分类id
     */

    private Long sortId;


    private List<String> size;
    private List<BigDecimal> price;

    private List<String> taste;

    private List<String> urlList;

}