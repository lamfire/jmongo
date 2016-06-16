package com.lamfire.jmongo.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented @Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})


public @interface Indexes {
	String name() default "";
	String value();
	boolean dropDups() default false;
	boolean background() default false;
	boolean sparse() default false;
	boolean disableValidation() default false;
	boolean unique() default false;
}
