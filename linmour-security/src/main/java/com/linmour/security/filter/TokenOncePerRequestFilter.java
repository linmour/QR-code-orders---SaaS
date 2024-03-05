package com.linmour.security.filter;


import com.alibaba.fastjson.JSON;

import com.linmour.common.dtos.LoginUser;
import com.linmour.common.dtos.LoginVo;
import com.linmour.common.dtos.Result;
import com.linmour.common.utils.JwtUtil;
import com.linmour.common.utils.RedisCache;
import com.linmour.common.utils.WebUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.linmour.common.exception.enums.AppHttpCodeEnum.NEED_LOGIN;
import static com.linmour.security.constants.constant.USER_LOGIN_KEY;

@Component
public class TokenOncePerRequestFilter extends OncePerRequestFilter {

    @Resource
    private RedisCache a;

    //注入为空
    public static RedisCache redisCache;
    @PostConstruct
    public void b(){
        redisCache = this.a;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = "";

//        //这个websocket的判断，请求头只能放在这里
//        if (request.getRequestURI().contains("websocket")){
//            token = request.getHeader("Sec-Websocket-Protocol");
//        }else {
            //从请求头中获取token
            token= request.getHeader("Token");
            //这个为空说明用户未注册，就直接放行，让他就像往下面的过滤链走
            if((StringUtils.isBlank(token) || request.getRequestURI().contains("login"))){
                filterChain.doFilter(request,response);
                //这个ruturn是不让他继续执行下面的代码
                return;
            }
//        }


        //TODO 为了方便测试
        if (token.equals("1")){
            //存入SecurityHolder，方便后续过滤连进行状态判断,
            // 三个参数的重载表示用户已认证，第一个就是用户名，第二个是密码，第三个是权限，
            // 两个参数的重载，那么默认这个用户是未认证状态
            LoginUser loginUser = new LoginUser();
            loginUser.setLoginVo(new LoginVo(1L));
            String shopId = request.getHeader("Shopid");
            //todo  java.lang.NumberFormatException: For input string: ""
            if (shopId != null){
                loginUser.getLoginVo().setShopId(Long.valueOf(shopId));
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request,response);
        }else {

            String id;
            //从token解析出id
            try {
                id = JwtUtil.parseJWT(token).getSubject();
            } catch (Exception e) {
                //这有两种情况，1.token时间过了，2.token不合法，这里不能直接抛异常，因为异常处理针对的是controller,这里是过滤器
                Result result = Result.error(NEED_LOGIN);
                WebUtils.renderString(response, JSON.toJSONString(result));
                return;
            }
            LoginUser user = JSON.toJavaObject( redisCache.getCacheObject(USER_LOGIN_KEY+id), LoginUser.class);
            String shopId = request.getHeader("Shopid");
            if (shopId != null && !Objects.isNull(user)){
                user.getLoginVo().setShopId(Long.valueOf(shopId));

            }
            if (Objects.isNull(user)){
                Result result = Result.error(NEED_LOGIN);
                WebUtils.renderString(response, JSON.toJSONString(result));
                return;
            }


            //存入SecurityHolder，方便后续过滤连进行状态判断,
            // 三个参数的重载表示用户已认证，第一个就是用户名，第二个是密码，第三个是权限，
            // 两个参数的重载，那么默认这个用户是未认证状态
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request,response);
        }

    }
}
