package com.linmour.order.pojo.Do;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 
 * @TableName order_info
 */
@TableName(value ="order_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private String id;

    /**
     * 用户id
     */
    @TableField(value = "cus_id")
    private Long cusId;

    @TableField(value = "table_id")
    private Long tableId;

    /**
     * 支付方式 1支付宝 2微信 3积分..
     */
    @TableField(value = "pay_type")
    private Integer payType;

    /**
     * 支付状态 1已支付 2未支付 3已取消
     */
    @TableField(value = "pay_status")
    private Integer payStatus;

    /**
     * 支付金额
     */
    @TableField(value = "pay_amount")
    private BigDecimal payAmount;

    /**
     * 订单状态 1已完成 2进行中 3待处理
     */
    @TableField(value = "order_status")
    private Integer orderStatus;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(value = "shop_id")
    private Long shopId;


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