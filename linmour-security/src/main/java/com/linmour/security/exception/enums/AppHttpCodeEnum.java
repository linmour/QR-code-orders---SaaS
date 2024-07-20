package com.linmour.security.exception.enums;

public enum AppHttpCodeEnum {
    SUCCESS(200,"操作成功"),

    ERROR(204,"操作失败"),
    NEED_LOGIN(401,"需要登录后操作"),
    TOKEN_ERROR(402,"token解析异常"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    REQUEST_ERROR(499,"请求方法错误"),


    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    CONTENT_NOT_NULL(506,"内容不能为空"),
    FILE_TYPE_ERROR(507,"文件类型错误"),
    USERNAME_NOT_NULL(508, "用户名不能为空"),
    NICKNAME_NOT_NULL(509, "昵称不能为空"),
    PASSWORD_NOT_NULL(510, "密码不能为空"),
    EMAIL_NOT_NULL(511, "邮箱不能为空"),
    NICKNAME_EXIST(512, "昵称已存在"),
    ACCOUNT_DISABLE(513,"账号已停用"),
    ARAUMENT_ERROR(514,"参数异常"),
    OCR_ERRER(515,"识别异常" ),
    LOGOUT_ERROR(603,"退出失败"),

    USERINFO_ERROR(1000,"获取用户信息失败"),
    SHOP_ERRPR(1001, "获取店面列表失败"),
    PRODUCT_ERROR(1002,"商品获取失败"),
    ORDER_ITEM_NOT_NULL(80401,"订单条目不能为空");


    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

