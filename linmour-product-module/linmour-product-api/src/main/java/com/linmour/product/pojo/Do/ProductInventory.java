package com.linmour.product.pojo.Do;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName product_inventory
 */
@TableName(value ="product_inventory")
@Data
public class ProductInventory implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 物品名字
     */
    @TableField(value = "name")
    private String name;

    /**
     * 物品数量
     */
    @TableField(value = "num")
    private Integer num;

    /**
     * 物品的计量单位
     */
    @TableField(value = "unit")
    private String unit;

    /**
     * 所属店铺
     */
    @TableField(value = "shop_id")
    private Long shopId;

    @TableField(value = "product_id")
    private Long productId;


    /**
     * 阈值，提示库存不足
     */
    @TableField(value = "threshold")
    private Integer threshold;
    @TableField(value = "quantity")
    private Integer quantity;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     *
     */
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private Long createBy;

    /**
     *
     */
    @TableField(value = "update_by",fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    /**
     * 
     */
    @TableField(value = "deleted")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}