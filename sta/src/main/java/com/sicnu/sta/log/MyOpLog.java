package com.sicnu.sta.log;

import java.lang.annotation.*;

/**
 * 自定义注解类
 */
// 注解放置的目标位置,METHOD是可注解在方法级别上
@Target(ElementType.METHOD)
// 注解在哪个阶段执行
@Retention(RetentionPolicy.RUNTIME)
// 生成文档
@Documented
public @interface MyOpLog {
    String value() default "";
}
