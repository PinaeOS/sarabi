package org.pinae.sarabi.service.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
	boolean singleton() default false;
	
	String url() default "";
}
