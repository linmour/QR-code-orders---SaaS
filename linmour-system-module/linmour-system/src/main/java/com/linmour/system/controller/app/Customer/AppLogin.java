package com.linmour.system.controller.app.Customer;


import com.linmour.security.dtos.Result;
import com.linmour.system.pojo.Do.Customer;
import com.linmour.system.pojo.Dto.CustomerDto;
import com.linmour.system.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.linmour.security.dtos.Result.success;


@RestController
@RequestMapping("/app/system/customer")
public class AppLogin {
    @Resource
    CustomerService customerService;
    @PostMapping("/login")
    public Result<Customer> login(@RequestBody CustomerDto customer) {
        return success( customerService.login(customer));

    }

}
