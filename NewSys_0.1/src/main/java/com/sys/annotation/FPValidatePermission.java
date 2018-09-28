package com.sys.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface FPValidatePermission {
	public static final String TITEL_STAGE = "命题阶段";
	public static final String OPEN_REPORT_STAGE = "开题报告阶段";

	String stageName() default "";
}
