package com.linmour.account.controller.merchant;

import com.linmour.account.service.MenuService;
import com.linmour.common.dtos.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/account/menu")
public class MenuController {

    @Resource
    private MenuService menuService;
    @GetMapping("/getMenus")
    public Result getMenus(){
        return menuService.getMenus();
    }
}
