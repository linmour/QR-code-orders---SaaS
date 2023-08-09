package com.linmour.restaurant.pojo.Do;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName restaurant_table
 */
@TableName(value ="restaurant_table")
@Data
public class RestaurantTable implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 餐桌名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 0没人 1有人 2预定
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 0菜没上完 1菜上完
     */
    @TableField(value = "serving")
    private Integer serving;

    /**
     * 
     */
    @TableField(value = "shop_id")
    private Long shopId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}