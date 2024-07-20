package com.linmour.common.annotation;

import com.linmour.common.validator.ListValueConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
@Documented
@Constraint(validatedBy = {ListValueConstraintValidator.class})
@Target({ FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface ListValue {
    //这个com.lsc.common.valid.ListValue.message要在properties文件里写上要返回的信息
    String message() default "{com.lsc.common.valid.ListValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    //上面上个是必须的，这个是我们需要的值
    int[] vals() default { };
}