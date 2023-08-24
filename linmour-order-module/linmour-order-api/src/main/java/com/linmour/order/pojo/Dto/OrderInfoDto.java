package com.linmour.order.pojo.Dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.linmour.product.pojo.Dto.ProductDetailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 这个是一个rocketmq序列化日期类型有问题，这个类没日期类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDto implements Serializable {

    private String id;

    /**
     * 用户id
     */
    
    private Long cusId;

    
    private Long tableId;

    /**
     * 支付方式 1支付宝 2微信 3积分..
     */
    
    private Integer payType;

    /**
     * 支付状态 1已支付 2未支付 3已取消
     */
    
    private Integer payStatus;

    /**
     * 支付金额
     */
    
    private BigDecimal payAmount;

    /**
     * 订单状态 1已完成 2进行中 3待处理
     */
    
    private Integer orderStatus;

    /**
     * 备注
     */
    
    private String remark;


    
    private String createTime;


    
    private String updateTime;

    /**
     *
     */
    
    private Long createBy;

    /**
     *
     */
    
    private Long updateBy;

    /**
     *
     */
    
    private Integer deleted;




}