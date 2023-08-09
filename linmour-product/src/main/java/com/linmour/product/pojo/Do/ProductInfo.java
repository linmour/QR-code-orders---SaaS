package com.linmour.product.pojo.Do;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName product_info
 */
@TableName(value ="product_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInfo implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品名字
     */
    @TableField(value = "name")
    private String name;
    /**
     * 店铺id
     */
    @TableField(value = "shop_id")
    private Long shopId;

    /**
     * 商品简介
     */
    @TableField(value = "intro")
    private String intro;


    /**
     * 是否需要选规格 0不要 1要
     */
    @TableField(value = "spec")
    private Integer spec;

    /**
     * 商品状态 0下架 1上架
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 商品图
     */
    @TableField(value = "picture")
    private String picture;

    /**
     * 分类id
     */
    @TableField(value = "sort_id")
    private Long sortId;

    /**
     * 
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 
     */
    @TableField(value = "deleted")
    private Integer deleted;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}