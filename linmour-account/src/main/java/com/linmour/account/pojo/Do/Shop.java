package com.linmour.account.pojo.Do;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName system_shop
 */
@TableName(value ="system_shop")
@Data
public class Shop implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 店铺名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 经营许可证等证书
     */
    @TableField(value = "certificate")
    private String certificate;

    /**
     * 店铺简介
     */
    @TableField(value = "intro")
    private String intro;

    /**
     * 店铺状态 0停用 1正常
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 审核状态 0未通过 1通过 2审核中
     */
    @TableField(value = "audit_status")
    private Integer auditStatus;

    /**
     * 分店 0表示主店
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 店主
     */
    @TableField(value = "merchant_id")
    private Long merchantId;

    /**
     * 店铺位置
     */
    @TableField(value = "position")
    private String position;

    /**
     *  经营状态 0休息中 1营业中
     */
    @TableField(value = "business_status")
    private Integer businessStatus;


    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time")
    private LocalDateTime  updateTime;

    /**
     * 
     */
    @TableField(value = "deleted")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}