package com.linmour.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;


@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    //插入时
    @Override
    public void insertFill(MetaObject metaObject) {
        //这里要填充的字段和他的值
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
//        this.setFieldValByName("createBy",userId , metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
//        this.setFieldValByName("updateBy", userId, metaObject);
    }
    //修改时
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
//        this.setFieldValByName("updateBy", SecurityUtils.getUserId(), metaObject);
    }
}