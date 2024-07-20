package com.linmour.security.dtos;

import com.linmour.security.exception.enums.AppHttpCodeEnum;
import lombok.*;

import java.io.Serializable;


/**
 * @Classname Result
 * @Description 通用返回类型
 * @Date 2023/7/17 11:01
 * @Created by linmour
 */


@Data
@ToString
@Builder
@AllArgsConstructor
public class Result<T> implements Serializable {

    /**
     * 响应编码,200为正常,201错误
     */
    private int code;

    /**
     * 响应提示信息
     */
    private String msg;

    /**
     * 响应内容
     */
    private T data;


    public Result() {
        this(200, "success");
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 错误信息的封装
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(String msg) {
        Result<T> response = new Result<T>();
        response.setCode(201);
        response.setMsg(msg);
        return response;
    }
    public static <T> Result<T> error(T data, String msg) {
        Result<T> response = new Result<T>();
        response.setCode(201);
        response.setData(data);
        response.setMsg(msg);
        return response;
    }

    public static Result error(AppHttpCodeEnum enums){
        return setExceptionEnum(enums);
    }


    public static Result setExceptionEnum(AppHttpCodeEnum enums){
        return success(enums.getCode(),enums.getMsg());
    }


    


    /**
     * 添加正常响应数据（包含响应内容）
     *
     * @return RestResponse Rest服务封装相应数据
     */
    public static <T> Result<T> success(T data) {
        Result<T> response = new Result<T>();
        response.setData(data);
        return response;
    }
    public static <T> Result<T> success(T data, String msg) {
        Result<T> response = new Result<T>();
        response.setData(data);
        response.setMsg(msg);
        return response;
    }

    public static <T> Result<T> success(int code, String msg) {
        Result<T> response = new Result<T>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    /**
     * 添加正常响应数据（不包含响应内容）
     *
     * @return RestResponse Rest服务封装相应数据
     */
    public static <T> Result<T> success() {
        return new Result<T>();
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

