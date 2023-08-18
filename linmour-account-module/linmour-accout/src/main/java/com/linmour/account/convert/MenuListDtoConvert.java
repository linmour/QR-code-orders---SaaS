package com.linmour.account.convert;

import com.linmour.account.pojo.Do.Menu;
import com.linmour.account.pojo.Dto.MenuListDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MenuListDtoConvert {

    MenuListDtoConvert IN = Mappers.getMapper(MenuListDtoConvert.class);

    List<MenuListDto> MenuToMenuList(List<Menu> menu);
}
