package com.linmour.dataAnaly.pojo.Do;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName product_summary_day
 */
@TableName(value ="product_summary_day")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSummaryDay implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 商品个数
     */
    @TableField(value = "num")
    private Integer num;

    /**
     * 日期
     */
    @TableField(value = "date")
    private Date date;

    /**
     * 
     */
    @TableField(value = "shop_id")
    private Long shopId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}