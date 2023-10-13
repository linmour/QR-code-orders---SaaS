package com.linmour.system.convert;

import com.linmour.system.pojo.Do.Menu;
import com.linmour.system.pojo.Dto.MenuListDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MenuListDtoConvert {

    MenuListDtoConvert IN = Mappers.getMapper(MenuListDtoConvert.class);

    List<MenuListDto> MenuToMenuList(List<Menu> menu);
}
