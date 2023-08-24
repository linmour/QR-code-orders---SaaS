package com.linmour.restaurant.pojo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTableDto implements Serializable {
    private String name;

    private String size;
}
