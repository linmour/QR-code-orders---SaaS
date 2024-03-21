package com.linmour.system.pojo.Dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.linmour.security.dtos.PageParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopPageDto extends PageParam implements Serializable{
    private Long id;


    private String name;


    private String certificate;


    private String intro;


    private Integer status;


    private Integer auditStatus;

    private Integer businessStatus;

    private LocalTime businessHours;


    private Long parentId;


    private String position;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;
}
