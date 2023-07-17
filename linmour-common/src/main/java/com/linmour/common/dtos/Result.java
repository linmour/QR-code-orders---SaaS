package com.linmour.common.dtos;

import lombok.Data;
import lombok.ToString;

/**
 * @Classname Result
 * @Description 通用返回类型
 * @Date 2023/7/17 11:01
 * @Created by linmour
 */


@Data
@ToString
public class Result<T> {

    /**
     * 响应编码,0为正常,-1错误
     */
    private int code;

    /**
     * 响应提示信息
     */
    private String msg;

    /**
     * 响应内容
     */
    private T result;


    public Result() {
        this(0, "success");
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
    public static <T> com.linmour.common.dtos.Result<T> validfail(String msg) {
        com.linmour.common.dtos.Result<T> response = new com.linmour.common.dtos.Result<T>();
        response.setCode(-1);
        response.setMsg(msg);
        return response;
    }
    public static <T> com.linmour.common.dtos.Result<T> validfail(T result, String msg) {
        com.linmour.common.dtos.Result<T> response = new com.linmour.common.dtos.Result<T>();
        response.setCode(-1);
        response.setResult(result);
        response.setMsg(msg);
        return response;
    }



    /**
     * 添加正常响应数据（包含响应内容）
     *
     * @return RestResponse Rest服务封装相应数据
     */
    public static <T> com.linmour.common.dtos.Result<T> success(T result) {
        com.linmour.common.dtos.Result<T> response = new com.linmour.common.dtos.Result<T>();
        response.setResult(result);
        return response;
    }
    public static <T> com.linmour.common.dtos.Result<T> success(T result, String msg) {
        com.linmour.common.dtos.Result<T> response = new com.linmour.common.dtos.Result<T>();
        response.setResult(result);
        response.setMsg(msg);
        return response;
    }

    /**
     * 添加正常响应数据（不包含响应内容）
     *
     * @return RestResponse Rest服务封装相应数据
     */
    public static <T> com.linmour.common.dtos.Result<T> success() {
        return new com.linmour.common.dtos.Result<T>();
    }


    public Boolean isSuccessful() {
        return this.code == 0;
    }

}

