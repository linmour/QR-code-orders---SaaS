package com.linmour.account.convert;

import com.linmour.account.pojo.Do.Menu;
import com.linmour.account.pojo.Dto.MenuListDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-23T15:40:39+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_361 (Oracle Corporation)"
)
public class MenuListDtoConvertImpl implements MenuListDtoConvert {

    @Override
    public List<MenuListDto> MenuToMenuList(List<Menu> menu) {
        if ( menu == null ) {
            return null;
        }

        List<MenuListDto> list = new ArrayList<MenuListDto>( menu.size() );
        for ( Menu menu1 : menu ) {
            list.add( menuToMenuListDto( menu1 ) );
        }

        return list;
    }

    protected MenuListDto menuToMenuListDto(Menu menu) {
        if ( menu == null ) {
            return null;
        }

        MenuListDto menuListDto = new MenuListDto();

        menuListDto.setId( menu.getId() );
        menuListDto.setParentId( menu.getParentId() );
        menuListDto.setName( menu.getName() );
        menuListDto.setPath( menu.getPath() );
        menuListDto.setIcon( menu.getIcon() );
        menuListDto.setPermissions( menu.getPermissions() );
        menuListDto.setSort( menu.getSort() );

        return menuListDto;
    }
}
