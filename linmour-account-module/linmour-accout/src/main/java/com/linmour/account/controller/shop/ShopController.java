package com.linmour.account.controller.shop;

import com.linmour.account.pojo.Dto.ShopPageDto;
import com.linmour.account.service.ShopService;
import com.linmour.common.dtos.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/account/shop")
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
