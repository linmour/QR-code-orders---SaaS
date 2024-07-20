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
 * @TableName order_summary_time_period
 */
@TableName(value ="order_summary_time_period")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSummaryTimePeriod implements Serializable {
    /**
     * 
     */
    @TableId(value = " id")
    private Integer id;

    /**
     * 时间段 
07:00 - 10:00
11:00 - 14:00
17:00 - 20:00
21:00 - 00:00
     */
    @TableField(value = "time_period")
    private String timePeriod;

    /**
     * 
     */
    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 
     */
    @TableField(value = "product_num")
    private Integer productNum;

    /**
     * 
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 
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