package com.linmour.order.pojo.Dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ShopListDto implements Serializable {
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

    /**
     *
     */

    private Long specId;

    /**
     * 商品状态 0下架 1上架
     */

    private Boolean status;

    /**
     * 商品图
     */

    private String picture;

    private Long sortId;


    private Integer valueSpec;
    private Integer nonValueSpec;

    private Integer number;
}
