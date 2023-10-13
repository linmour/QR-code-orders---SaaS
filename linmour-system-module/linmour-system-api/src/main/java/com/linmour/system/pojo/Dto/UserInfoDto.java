package com.linmour.system.pojo.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto implements Serializable {

    private Long id;
    private String realName;
    private String phone;
    private Integer sex;
    private String avatar;
    private String type;

    private String idCard;

    private String idCardUrl;
}
