package com.linmour.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.system.convert.MenuListDtoConvert;
import com.linmour.system.mapper.MenuMapper;
import com.linmour.system.pojo.Do.Menu;
import com.linmour.system.pojo.Dto.MenuListDto;

import com.linmour.system.service.MenuService;
import com.linmour.common.dtos.LoginUser;
import com.linmour.common.dtos.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author linmour
* @description 针对表【system_menu】的数据库操作Service实现
* @createDate 2023-07-24 15:40:34
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService {

    @Resource
    private MenuMapper menuMapper;
    @Override
    public Result getMenus() {
        Long id = ((LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLoginVo().getId();
        List<MenuListDto> menus = MenuListDtoConvert.IN.MenuToMenuList(menuMapper.getMenus(id));
        menus.stream().filter(m->m.getParentId().equals(0))
                .map(m->m.setChildren(getChildrens(m,menus)))
                .collect(Collectors.toList());

        return Result.success(menus);
    }

    private List<MenuListDto> getChildrens(MenuListDto menuVo, List<MenuListDto> menuVos) {
        List<MenuListDto> children = menuVos.stream()
                //如果id和父目录一样就说明是子目录
                .filter(m -> m.getParentId().equals(menuVo.getId()))
                //这个是找多级目录
                .map(m -> m.setChildren(getChildrens(m,menuVos)))
                .collect(Collectors.toList());
        return children;
    }
}




