package com.tianya.springboot.jwt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description
 *	不需要授权的 接口
 * @author TianwYam
 * @date 2021年5月20日上午9:15:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface NotAuth {

}
