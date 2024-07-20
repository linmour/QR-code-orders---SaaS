package com.linmour.product.pojo.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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


    private BigDecimal price;


    /**
     * 分类id
     */

    private Long sortId;

    private List<ProductSortAndOption> specSortAndOption;

    private List<ProductInventoryAllDto> inventoryAllList;
    private String picture;

}
