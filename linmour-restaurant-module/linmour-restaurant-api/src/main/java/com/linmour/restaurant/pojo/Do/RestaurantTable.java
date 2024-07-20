package com.linmour.restaurant.pojo.Do;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName restaurant_table
 */
@TableName(value = "restaurant_table")
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
     *
     */
    @TableField(value = "shop_id")
    private Long shopId;
    @TableField(value = "serving")
    private Integer serving;
    @TableField(value = "dishes")
    private Integer dishes;

    @TableField(value = "qr_code_url")
    private String qrCodeUrl;

    /**
     *
     */
    @TableField(value = "deleted")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}