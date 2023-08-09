package com.linmour.product.pojo.Dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeAndPrice {
    private String size;
    private BigDecimal price;
    private Integer status;
    private Long id;
}
