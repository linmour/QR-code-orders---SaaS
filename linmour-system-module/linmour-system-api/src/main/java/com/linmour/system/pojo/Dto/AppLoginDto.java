package com.linmour.system.pojo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppLoginDto {
    private String code;
    private String nickName;
    private String avatarUrl;
}
