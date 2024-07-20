package com.linmour.system.controller.admin.merchant;


import com.linmour.security.dtos.LoginVo;
import com.linmour.security.dtos.Result;
import com.linmour.system.service.MerchantService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/merchant")
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
