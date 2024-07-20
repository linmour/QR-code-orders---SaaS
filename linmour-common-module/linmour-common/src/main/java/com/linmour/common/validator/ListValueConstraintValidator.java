package com.linmour.common.validator;

import com.linmour.common.annotation.ListValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {
    HashSet<Integer> set = new HashSet<>();
    //初始化方法，这里的值就是注解上设置的值
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();
        //把值装入集合中
        for (int val : vals) {
            if(!String.valueOf(val).equals("")){
                set.add(val);
            }
        }
    }


    //判断是否校验成功
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        //判断用户传的值是否在集合中
        return set.contains(integer);
    }
}