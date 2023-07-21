package com.linmour.account.controller.merchant;

import com.linmour.account.service.MerchantService;
import com.linmour.common.dtos.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RestController
@RequestMapping("/account/merchant")
public class merchantController {

    @Resource
    private MerchantService merchantService;
    @PostMapping("/userInfo")
    public Result userInfo(Long userId){
        return Result.success(merchantService.userInfo(userId));
    }
}
