package com.linmour.product.pojo.Dto;

import com.linmour.product.pojo.Do.ProductSort;
import com.linmour.product.pojo.Do.ProductSpecOption;
import com.linmour.product.pojo.Do.ProductSpecSort;
import lombok.Data;

import java.util.List;

@Data
public class ProductSortAndOption {
    private ProductSpecSort productSpecSort;
    private List<ProductSpecOption> productSpecOptions;
}
