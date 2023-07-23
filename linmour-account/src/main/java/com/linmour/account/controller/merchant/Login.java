package com.linmour.account.controller.merchant;

import com.linmour.account.pojo.Vo.LoginVo;
import com.linmour.account.service.MerchantService;
import com.linmour.common.dtos.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/account/merchant")
public class Login {
    @Resource
    private MerchantService merchantService;



    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        return (merchantService.login(loginVo));
    }

    @PostMapping("/logout/{id}")
    public Result logout(@PathVariable Long id){
        return merchantService.logout(id);
    }

}
