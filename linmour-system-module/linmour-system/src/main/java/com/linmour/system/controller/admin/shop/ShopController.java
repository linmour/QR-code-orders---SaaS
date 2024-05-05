package com.linmour.system.controller.admin.shop;

import com.linmour.security.dtos.Result;
import com.linmour.system.pojo.Do.Shop;
import com.linmour.system.pojo.Dto.ShopPage;
import com.linmour.system.service.ShopService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.swing.event.ListDataEvent;
import javax.validation.Valid;
import java.util.List;

@RestController
    @RequestMapping("/system/shop")
public class ShopController {

    @Resource
    private ShopService shopService;

    @GetMapping("/shopList")
    private Result shopList(@Valid ShopPage dto) {
        return shopService.shopList(dto);
    }

    @PostMapping("/save0rUpdateShop")
    public Result save0rUpdateShop(@RequestBody Shop shop) {
        return Result.success(shopService.save0rUpdateShop(shop));
    }

    @GetMapping("/SonShopList")
    private Result SonShopList(@Valid ShopPage dto) {
        return shopService.SonShopList(dto);
    }
    @GetMapping("/getShopByIds")
    private List<Shop> getShopByIds(@RequestParam List<Long> ids) {
        return shopService.getShopByIds(ids);
    }



}
