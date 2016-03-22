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
public @interface Index {
	/** List of fields (prepended with "-" for desc; defaults to asc) */
	String value();
	/** The name of the index to create; default is to let the jmongo create a name (in the form of key1_1/-1_key2_1/-1...*/
	String name() default "";
	/** Creates the index as a unique value index; inserting duplicates values in this field will cause errors */
	boolean unique() default false;
	/** Tells the unique index to drop duplicates silently when creating; only the first will be kept*/
	boolean dropDups() default false;
	/** Create the index in the background*/
	boolean background() default false;
	/** Create the index with the sparse option*/
	boolean sparse() default false;
	/** disables validation for the field name*/
	boolean disableValidation() default false;

}
