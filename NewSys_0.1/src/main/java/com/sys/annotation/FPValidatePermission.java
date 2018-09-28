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
	public static final String TITEL_STAGE = "����׶�";
	public static final String OPEN_REPORT_STAGE = "���ⱨ��׶�";

	String stageName() default "";
}
