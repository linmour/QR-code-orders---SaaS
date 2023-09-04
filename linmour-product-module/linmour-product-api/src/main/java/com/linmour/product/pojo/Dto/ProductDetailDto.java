package com.linmour.product.pojo.Dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductDetailDto  implements  Serializable  {

    private Long id;

    /**
     * 商品名字
     */
     
    private String name;

    /**
     *
     */
     
    private Long shopId;

    /**
     * 商品简介
     */
     
    private String intro;

    /**
     * 0没有规格 有值表示规格id
     */
     
    private Long specId;

    /**
     * 商品状态 0下架 1上架
     */
     
    private Integer status;

    /**
     * 分类id
     */
     
    private Long sortId;

    /**
     * 商品图
     */
     
    private String picture;

    /**
     * 如果商品有规格就显示价格最低的配置
     */
     
    private BigDecimal price;
    private String sort;
    private List<NonValueDto> nonValueList;
    private List<ValueDto> ValueList;
    private List<InventoryDto> inventoryList;


}
