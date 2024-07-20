package com.linmour.security.exception;

import com.linmour.security.dtos.Result;
import com.linmour.security.exception.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//控制器增强类
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理不可控异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e){
        e.printStackTrace();

        return Result.error(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    //密码错误
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public Result exception(BadCredentialsException e){
        e.printStackTrace();
        log.error("catch exception:{}",e.getMessage());

        return Result.error(AppHttpCodeEnum.LOGIN_ERROR);
    }
    //请求方式不对
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result exception(HttpRequestMethodNotSupportedException e){
        e.printStackTrace();

        return Result.error(AppHttpCodeEnum.REQUEST_ERROR);
    }
    //请求参数不对
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result exception(BindException e){
        e.printStackTrace();


        return Result.error(AppHttpCodeEnum.ARAUMENT_ERROR);
    }

    /**
     * 处理可控异常  自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result exception(CustomException e){

        return Result.error(e.getExceptionEnum());
    }
}
