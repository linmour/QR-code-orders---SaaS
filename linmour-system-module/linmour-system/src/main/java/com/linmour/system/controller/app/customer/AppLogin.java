package com.linmour.system.controller.app.customer;

import com.linmour.common.dtos.Result;
import com.linmour.system.pojo.Dto.AppLoginDto;
import com.linmour.system.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/system/customer")
public class AppLogin {
    @Resource
    private CustomerService customerService;

    @PostMapping("/login")
    public Result login(@RequestBody AppLoginDto dto) {
        return customerService.login(dto);

    }
}
