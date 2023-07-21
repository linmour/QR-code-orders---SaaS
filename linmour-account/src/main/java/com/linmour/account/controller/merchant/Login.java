package com.linmour.account.controller.merchant;

import com.linmour.account.pojo.Dto.LoginDto;
import com.linmour.account.service.MerchantService;
import com.linmour.common.dtos.Result;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/account/merchant")
public class Login {
    @Resource
    private MerchantService merchantService;



    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto){
        return (merchantService.login(loginDto));
    }

    @PostMapping("/logout/{id}")
    public Result logout(@PathVariable Long id){
        return merchantService.logout(id);
    }

}
