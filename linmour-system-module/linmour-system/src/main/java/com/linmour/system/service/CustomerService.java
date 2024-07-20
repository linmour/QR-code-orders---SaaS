package com.linmour.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.system.pojo.Do.Customer;
import com.linmour.system.pojo.Dto.CustomerDto;

public interface CustomerService extends IService<Customer> {
    Customer login(CustomerDto customer);
}
