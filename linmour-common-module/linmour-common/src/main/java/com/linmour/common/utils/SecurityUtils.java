package com.linmour.common.utils;


public class SecurityUtils {

    public static Long getUserId(){
//        if (SecurityContextHolder.getContext() == null){
//            return 0L;
//        }
//        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Long id = user.getLoginVo().getId();
        return 1L;
    }
}
