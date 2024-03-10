package com.linmour.security.exception;


import com.linmour.security.exception.enums.AppHttpCodeEnum;

public class CustomException extends RuntimeException {

    private AppHttpCodeEnum appHttpCodeEnum;

    public CustomException(AppHttpCodeEnum appHttpCodeEnum){
        this.appHttpCodeEnum = appHttpCodeEnum;
    }

    public AppHttpCodeEnum getExceptionEnum() {
        return appHttpCodeEnum;
    }
}
