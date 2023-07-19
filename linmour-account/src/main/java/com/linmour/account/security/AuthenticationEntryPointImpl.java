package com.linmour.account.security;

import com.alibaba.fastjson.JSON;

import com.linmour.common.dtos.Result;
import com.linmour.common.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.linmour.common.exception.enums.AppHttpCodeEnum.*;


/**
 * @Classname AuthenticationEntryPointImpl
 * @Description 这个是用来捕获springsecurity认证异常中抛出的错误
 * @Date 2022/12/12 7:16
 * @Created by linmour
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        //InsufficientAuthenticationException
        //BadCredentialsException
        //对异常进行判断
        Result result = null;
        //这个是密码匹配失败
        if(e instanceof BadCredentialsException){
            result = Result.error(LOGIN_ERROR);
        }else if(e instanceof RuntimeException){
            result = Result.error(NEED_LOGIN);
        }else{
            result = Result.error(SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
