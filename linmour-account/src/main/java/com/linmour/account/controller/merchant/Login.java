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

    @PostMapping("logout")
    public Result logout(){
        return merchantService.logout();
    }

    @RequestMapping("/a")
    public Result a(){
        String password = "123";
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        System.out.println("加密后的值：" + hashedPassword);
        return Result.success(hashedPassword);
    }
}
