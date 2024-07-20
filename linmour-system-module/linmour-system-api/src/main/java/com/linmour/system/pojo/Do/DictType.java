package com.linmour.system.pojo.Do;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName system_dict_type
 */
@TableName(value ="system_dict_type")
@Data
public class DictType implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String type;

    /**
     * 
     */
    private Integer status;

    /**
     * 
     */
    private Long creator;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Long updater;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private Boolean deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}