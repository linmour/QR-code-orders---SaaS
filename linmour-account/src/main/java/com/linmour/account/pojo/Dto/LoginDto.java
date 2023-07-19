package com.linmour.account.pojo.Dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDto implements Serializable {

    private Long id;
    private String phone;
    private String password;
}
