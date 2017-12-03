package org.pinae.sarabi.service.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.pinae.sarabi.service.Http;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
	String name() default "";
	
	String description() default "";
	
	String url();
	
	String[] method() default {Http.HTTP_GET};
	
	String contentType() default Http.APPLICATION_JSON;
	
	String charset() default "utf-8";
}
