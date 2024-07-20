package com.linmour.product.pojo.Dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class AppProductSort {
    private Long id;

    /**
     * 商品分类
     */
    private String sort;

    /**
     *
     */
    private Long shopId;

    private List<ProductDetailDto> list;

}
