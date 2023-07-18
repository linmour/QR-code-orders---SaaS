package com.linmour.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.linmour.account.RedisCache;
import com.linmour.account.constants.constants;
import com.linmour.account.mapper.MerchantMapper;
import com.linmour.account.pojo.Do.Merchant;
import com.linmour.account.pojo.Vo.LoginVo;
import com.linmour.account.security.LoginUser;
import com.linmour.account.service.MerchantService;
import com.linmour.common.dtos.Result;
import com.linmour.common.utils.JwtUtil;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;


/**
* @author linmour
* @description 针对表【system_merchant】的数据库操作Service实现
* @createDate 2023-07-17 20:05:36
*/
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant>
    implements MerchantService{

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;


    @Override
    public Result login(LoginVo loginVo) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getPhone(),loginVo.getPassword());
        //这个方法就会调用我们写的UserDetailsService类进行数据库查询，如果返回null说明用户名错误
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(authenticate == null){
            throw new RuntimeException("密码错误");
        }
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        String UserId = loginUser.getLoginVo().getId().toString();
        //用id生成jwt
        String jwt = JwtUtil.createJWT(UserId);
        //把用户信息存入redis
        redisCache.setCacheObject(constants.USER_LOGIN_KEY +UserId,loginUser);
        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);

//        //把用户信息封装成vo
//        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
//        //再把token和用户信息封装成一个vo
//        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt,userInfoVo);
        return Result.success(map);
    }
}




