package com.linmour.product.pojo.Do;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName r_product_inventoty
 */
@TableName(value ="r_product_inventoty")
@Data
public class RProductInventoty implements Serializable {
    /**
     * 
     */
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


    /**
     * 物品数量
     */
    @TableField(value = "num")
    private Integer num;




    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}