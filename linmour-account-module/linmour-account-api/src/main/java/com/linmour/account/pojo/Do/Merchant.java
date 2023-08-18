package com.linmour.account.pojo.Do;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;



/**
 * 
 * @TableName system_merchant
 */
@TableName(value ="system_merchant")
@Data
public class Merchant implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 店主真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 电话号码
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 身份证
     */
    @TableField(value = "id_card")
    private String idCard;

    /**
     * 身份证照片
     */
    @TableField(value = "id_card_url")
    private String idCardUrl;

    /**
     * 权限类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 性别  0女 1男
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 账号状态 0停用 1正常
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 最后登录ip
     */
    @TableField(value = "login_ip")
    private String loginIp;

    /**
     * 创建时间
     */
    //将时间转换为自己要的时间格式向前端发送(具体还是json数据不变)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime  createTime;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除 0没1删
     */
    @TableLogic(value = "0", delval = "1")
    @TableField(value = "deleted")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}