package com.linmour.dataAnaly.pojo.Do;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName order_summary_day
 */
@TableName(value ="order_summary_day")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSummaryDay implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 日期
     */
    @TableField(value = "date")
    private Date date;

    /**
     * 订单数
     */
    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 商品数量
     */
    @TableField(value = "product_num")
    private Integer productNum;

    /**
     * 总金额
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 每单平均金额
     */
    @TableField(value = "avg_price")
    private BigDecimal avgPrice;

    /**
     * 
     */
    @TableField(value = "shop_id")
    private Long shopId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}