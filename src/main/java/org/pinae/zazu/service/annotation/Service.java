package org.pinae.zazu.service.annotation;

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
	String url();
	
	String[] method() default {Http.HTTP_GET};
	
	String contentType() default Http.APPLICATION_JSON;
	
	String charset() default "utf-8";
}
