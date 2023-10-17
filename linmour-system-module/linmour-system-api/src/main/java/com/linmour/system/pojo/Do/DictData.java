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
 * @TableName system_dict_data
 */
@TableName(value ="system_dict_data")
@Data
public class DictData implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典键值
     */
    private String value;

    /**
     *  字典类型
     */
    private String dictType;

    /**
     *  状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 
     */
    private Long creator;

    /**
     * 
     */
    private Date creatorTime;

    /**
     * 
     */
    private Long updater;

    /**
     * 
     */
    private Date updaterTime;

    /**
     * 
     */
    private Boolean deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}