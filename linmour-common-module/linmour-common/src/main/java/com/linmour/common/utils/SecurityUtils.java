package com.linmour.common.utils;


import com.linmour.common.dtos.LoginUser;
import com.linmour.common.dtos.LoginVo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Long getUserId(){
        if (SecurityContextHolder.getContext().getAuthentication() == null){
            return 0L;
        }
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = user.getLoginVo().getId();
        return id;
    }


    public static Long getShopId() {
        if (a()){
            return 0L;
        }
        if (SecurityContextHolder.getContext().getAuthentication() == null){
            return 0L;
        }
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = user.getLoginVo().getShopId();
        return id;
    }


    public static Boolean a(){
        if (SecurityContextHolder.getContext().getAuthentication() == null){
            return true;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                return principal.equals("anonymousUser");
    }

}
