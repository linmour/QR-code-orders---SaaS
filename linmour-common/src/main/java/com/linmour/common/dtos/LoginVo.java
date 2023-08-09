package com.linmour.common.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVo implements Serializable {

    private Long id;
    private String phone;
    private String password;
    private Integer status;


}
