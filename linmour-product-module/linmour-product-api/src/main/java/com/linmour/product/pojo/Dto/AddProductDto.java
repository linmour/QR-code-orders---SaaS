package com.linmour.product.pojo.Dto;

import com.baomidou.mybatisplus.annotation.TableField;
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


    private Byte valueSpec;


    private Byte nonValueSpec;

    private BigDecimal price;


    /**
     * 分类id
     */

    private Long sortId;

    private List<NonValueDto> nonValueList;
    private List<ValueDto> valueList;
    private List<InventoryDto> inventoryList;
    private List<String> urlList;

}
