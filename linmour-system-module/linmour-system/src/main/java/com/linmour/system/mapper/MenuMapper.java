package com.linmour.system.mapper;

import com.linmour.system.pojo.Do.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
* @author linmour
* @description 针对表【system_menu】的数据库操作Mapper
* @createDate 2023-07-24 15:40:33
* @Entity com.linmour.Do.pojo.system.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {


    List<Menu> getMenus(@Param("id") Long id);

}




