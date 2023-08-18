package com.linmour.order.pojo.Do;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName order_sale
 */
@TableName(value ="order_sale")
@Data
public class OrderSale implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 顾客id
     */
    @TableField(value = "cus_id")
    private Long cusId;

    /**
     * 销售日期
     */
    @TableField(value = "sale_date")
    private Date saleDate;

    /**
     * 销售数量
     */
    @TableField(value = "sale_quantity")
    private Integer saleQuantity;

    /**
     * 所属店铺
     */
    @TableField(value = "shop_id")
    private Long shopId;

    /**
     * 销售金额
     */
    @TableField(value = "sale_amount")
    private BigDecimal saleAmount;

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