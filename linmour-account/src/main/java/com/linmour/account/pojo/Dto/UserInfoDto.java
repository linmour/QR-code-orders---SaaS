package com.linmour.account.pojo.Dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoDto implements Serializable {

    private Long id;
    private String realName;
    private String phone;
    private Integer sex;
    private String avatar;

}
