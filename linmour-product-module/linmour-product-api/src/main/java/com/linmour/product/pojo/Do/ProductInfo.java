package com.linmour.product.pojo.Do;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * @TableName product_info
 */
@TableName(value ="product_info")
@Data
@Builder
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
     * 
     */
    @TableField(value = "shop_id")
    private Long shopId;

    /**
     * 商品简介
     */
    @TableField(value = "intro")
    private String intro;


    /**
     * 商品状态 0下架 1上架
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 分类id
     */
    @TableField(value = "sort_id")
    private Long sortId;

    /**
     * 商品图
     */
    @TableField(value = "picture")
    private String picture;

    /**
     * 如果商品有规格就显示价格最低的配置
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 创建时间
     */
    //将时间转换为自己要的时间格式向前端发送(具体还是json数据不变)
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