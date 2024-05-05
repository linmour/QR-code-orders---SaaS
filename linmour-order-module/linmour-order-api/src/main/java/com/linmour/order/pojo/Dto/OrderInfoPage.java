package com.linmour.order.pojo.Dto;

import com.linmour.security.dtos.PageParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoPage extends PageParam {
    private Integer payType;

    private Long shopId;
}
