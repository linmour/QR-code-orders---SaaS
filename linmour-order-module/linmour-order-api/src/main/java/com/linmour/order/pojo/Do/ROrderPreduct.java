package com.linmour.order.pojo.Do;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName r_order_preduct
 */
@TableName(value ="r_order_preduct")
@Data
public class ROrderPreduct implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id

     */
    @TableField(value = "order_id")
    private String orderId;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 购买数量
     */
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