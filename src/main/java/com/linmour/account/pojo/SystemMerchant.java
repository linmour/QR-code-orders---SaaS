package com.linmour.account.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName system_merchant
 */
@TableName(value ="system_merchant")
@Data
public class SystemMerchant implements Serializable {
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
     * 身份证
     */
    @TableField(value = "id_card")
    private String idCard;

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
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除 0没1删
     */
    @TableField(value = "deleted")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}