package com.linmour.product.pojo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValueDto implements Serializable {
    private String sort;
    private List<String> spec;
    private  List<BigDecimal> price;
}
