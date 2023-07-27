package com.linmour.account.pojo.Dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.linmour.common.dtos.PageParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

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


    private Long parentId;


    private String position;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;
}
