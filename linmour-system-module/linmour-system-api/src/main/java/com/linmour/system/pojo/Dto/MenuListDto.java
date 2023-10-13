package com.linmour.system.pojo.Dto;

import com.linmour.system.pojo.Do.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MenuListDto extends Menu {
    private List<MenuListDto> children;
}
