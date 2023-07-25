package com.linmour.product.pojo.Do;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName product_info
 */
@TableName(value ="product_info")
@Data
public class ProductInfo implements Serializable {
    /**
     * /*、
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
     * 商品价格  基本价格+规格价钱
     */
    @TableField(value = "price")
    private BigDecimal price;

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
    @TableField(value = "deleted")
    private Integer deleted;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}