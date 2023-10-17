package com.linmour.system.service;

import com.linmour.system.pojo.Do.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.common.dtos.Result;

/**
* @author linmour
* @description 针对表【system_menu】的数据库操作Service
* @createDate 2023-07-24 15:40:34
*/
public interface MenuService extends IService<Menu> {

    Result getMenus();

}
