package com.linmour.order.pojo.Do;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.linmour.product.pojo.Dto.ProductSortAndOption;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @TableName order_item
 */
@TableName(value = "order_item")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItem implements Serializable {
    /**
     *
     */

    private Long id;

    /**
     * 商品名字
     */

    private String name;

    /**
     *
     */

    private Long shopId;
    private Integer status;

    private Long productId;

    /**
     * 分类id
     */

    private Long sortId;

    /**
     * 商品图
     */

    private String picture;

    /**
     * 如果商品有规格就显示价格最低的配置
     */

    private BigDecimal price;
    private String sort;

    private String propsText;

    private Integer quantity;
    private String orderId;


    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     *
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private Long createBy;

    /**
     *
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    /**
     *
     */
    @TableField(value = "deleted")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}