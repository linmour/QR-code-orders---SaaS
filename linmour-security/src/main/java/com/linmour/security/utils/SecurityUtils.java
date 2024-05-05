package com.linmour.security.utils;


import com.linmour.security.dtos.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    private static Long shopId;

    public static Long getUserId(){
        if (SecurityContextHolder.getContext().getAuthentication() == null || "anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())){
            return 0L;
        }
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = user.getLoginVo().getId();
        return id;
    }


    public static Long getShopId() {
        if (a()){
            return shopId;
        }

        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        shopId = user.getLoginVo().getShopId();
        return shopId;
    }

    public static void setShopId(Long i) {
        shopId = i;
    }


    public static Boolean a(){
        if (SecurityContextHolder.getContext().getAuthentication() == null){
            return true;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                return principal.equals("anonymousUser");
    }

}
