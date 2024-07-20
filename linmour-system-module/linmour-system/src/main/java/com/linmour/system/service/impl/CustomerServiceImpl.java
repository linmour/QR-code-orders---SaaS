package com.linmour.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.system.mapper.CustomerMapper;
import com.linmour.system.pojo.Do.Customer;
import com.linmour.system.pojo.Dto.CustomerDto;
import com.linmour.system.service.CustomerService;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomerServiceImpl  extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.secret}")
    private String secret;


    @Override
    public Customer login(CustomerDto customer) {
        


        JsonNode json = Unirest.get("https://api.weixin.qq.com/sns/jscode2session?appid=" +appid +"&secret=" + secret + "&js_code=" + customer.getCode() + "&grant_type=authorization_code").asJson().getBody();

        if (json.toString().contains("errcode")) {
            return null;
        }
        String openid = json.getObject().getString("openid");

        Customer user = customerMapper.selectOne(new LambdaQueryWrapper<Customer>().eq(Customer::getOpenid, openid));
        //如果注册过了就直接返回openid
        if (!ObjectUtil.isEmpty(user))
            return user;

        Customer Customer = new Customer();
//        Customer.setAvatar("https://www.linmour.asia:48080/admin-api/infra/file/18/get/avatar-" + openid + ".png");
        Customer.setOpenid(openid);
        Customer.setNickName("用户"+openid.substring(openid.length()-5));
        customerMapper.insert(Customer);
        return  Customer;
    }
}
