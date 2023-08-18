package com.linmour.product.pojo.Do;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
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

    /**
     * 阈值，提示库存不足
     */
    @TableField(value = "Threshold")
    private Integer threshold;

    /**
     * 
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 
     */
    @TableField(value = "create_by")
    private Long createBy;

    /**
     * 
     */
    @TableField(value = "update_by")
    private Long updateBy;

    /**
     * 
     */
    @TableField(value = "deleted")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}