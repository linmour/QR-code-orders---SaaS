package com.linmour.system.controller.admin.shop;

import com.linmour.security.dtos.Result;
import com.linmour.system.pojo.Dto.ShopPageDto;
import com.linmour.system.service.ShopService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/system/shop")
public class ShopController {

    @Resource
    private ShopService shopService;

    @GetMapping("/shopList")
    private Result shopList(@Valid ShopPageDto dto){
        return shopService.shopList(dto);
    }



    @GetMapping("/SonShopList")
    private Result SonShopList(@Valid ShopPageDto dto){
        return shopService.SonShopList(dto);
    }

}
