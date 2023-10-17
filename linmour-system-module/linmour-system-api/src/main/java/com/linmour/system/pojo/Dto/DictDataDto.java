package com.linmour.system.pojo.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DictDataDto {
    private Long id;

    /**
     *
     */
    private String label;

    /**
     *
     */
    private String value;

    /**
     *
     */
    private String dictType;

    /**
     *
     */
    private Integer status;

}
