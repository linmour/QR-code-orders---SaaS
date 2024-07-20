package com.linmour.common.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/********************************************************************************
 ** @author ： ZYJ
 ** @date ：2023/03/09
 ** @description ：厂长加班写代码-批量插入SQL注入器
 *********************************************************************************/
public class InsertBatchSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new InsertBatchSomeColumn()); //添加InsertBatchSomeColumn方法
        return methodList;
    }
}