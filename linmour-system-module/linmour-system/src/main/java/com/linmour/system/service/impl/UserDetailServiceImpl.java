package com.linmour.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.linmour.security.dtos.LoginUser;
import com.linmour.security.dtos.LoginVo;
import com.linmour.system.convert.LoginVoConvert;

import com.linmour.system.mapper.MerchantMapper;
import com.linmour.system.pojo.Do.Merchant;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 这个是实现登录验证密码的
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private MerchantMapper merchantMapper;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        //根据电话查找用户
        Merchant merchant = merchantMapper.selectOne(new LambdaQueryWrapper<Merchant>().eq(Merchant::getPhone, phone));
        if (ObjectUtil.isNull(merchant)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        LoginVo merchant1 = LoginVoConvert.INSTANCE.MerchantToLoginVo(merchant);
        UserDetails loginUser = new LoginUser(merchant1);
        return loginUser;
    }
}
