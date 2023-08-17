package com.linmour.common.utils;



import com.alibaba.nacos.api.config.filter.IFilterConfig;
import com.linmour.common.dtos.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

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
