package com.linmour.system.controller.admin.merchant;

import com.linmour.common.dtos.Result;
import com.linmour.system.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Resource
    private MenuService menuService;
    @GetMapping("/getMenus")
    public Result getMenus(){
        return menuService.getMenus();
    }
}
