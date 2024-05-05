package com.linmour.system.pojo.Dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.linmour.security.dtos.PageParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantPage extends PageParam {
    private String realName;

    private String phone;

    private String idCard;

    private Integer status;
}
