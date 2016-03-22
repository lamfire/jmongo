package com.lamfire.jmongo.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CappedAt {
	/** size to cap at (defaults to 1MB) */
	long value() default 1024*1024;
	/** count of items to cap at (defaults to unlimited) */
	long count() default 0;
}
