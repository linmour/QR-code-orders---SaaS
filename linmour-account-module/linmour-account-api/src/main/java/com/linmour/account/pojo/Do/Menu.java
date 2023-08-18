package com.linmour.account.pojo.Do;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName system_menu
 */
@TableName(value ="system_menu")
@Data
public class Menu implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父菜单的id
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 菜单名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 路由跳转路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 权限分为3种 1管理员 2商户 3顾客，用来获取不同的菜单
     */
    @TableField(value = "permissions")
    private String permissions;

    @TableField(value = "sort")
    private String sort;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}