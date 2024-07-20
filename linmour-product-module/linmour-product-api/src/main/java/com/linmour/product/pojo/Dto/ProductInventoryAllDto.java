package com.linmour.product.pojo.Dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.linmour.product.pojo.Do.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventoryAllDto {
    @TableId(value = "id")
    private Long id;

    /**
     *
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     *
     */
    @TableField(value = "inventory_id")
    private Long inventoryId;
    private Integer threshold;

    private List<ProductInfo> productInfo;

    /**
     * 物品数量
     */
    @TableField(value = "num")
    private Integer num;

    @TableField(value = "name")
    private String name;

    /**
     * 物品的计量单位
     */
    @TableField(value = "unit")
    private String unit;

}
