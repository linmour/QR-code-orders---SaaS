package com.linmour.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo implements Serializable {

    private Long id;
    private String phone;
    private String password;
    private Integer status;
    private Long shopId;


    public LoginVo(Long id) {
        this.id = id;
    }
}
