package com.linmour.common.exception;

import com.linmour.common.exception.enums.ExceptionEnum;

public class CustomException extends RuntimeException {

    private ExceptionEnum exceptionEnum;

    public CustomException(ExceptionEnum exceptionEnum){
        this.exceptionEnum = exceptionEnum;
    }

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }
}
