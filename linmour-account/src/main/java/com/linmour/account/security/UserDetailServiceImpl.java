package com.linmour.account.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.linmour.account.convert.LoginVoConvert;
import com.linmour.account.mapper.MerchantMapper;
import com.linmour.account.pojo.Do.Merchant;
import com.linmour.account.pojo.Vo.LoginVo;
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
        Merchant merchant = merchantMapper.selectOne(new LambdaQueryWrapper<Merchant>().eq(Merchant::getPhone, phone));

        if (merchant == null){
            throw new UsernameNotFoundException("找不到用户");
        }
        LoginVo merchant1 = LoginVoConvert.INSTANCE.Merchant(merchant);
        UserDetails loginUser = new LoginUser(merchant1);
        return loginUser;
    }
}
