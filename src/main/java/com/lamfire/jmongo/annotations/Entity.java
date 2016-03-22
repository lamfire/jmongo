package com.lamfire.jmongo.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lamfire.jmongo.mapping.Mapper;


@Documented @Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Entity {
	String value() default Mapper.IGNORED_FIELDNAME;
	CappedAt cap() default @CappedAt(0);

	boolean noClassnameStored() default true;
	
	//set slaveOk for queries for this Entity.
	boolean queryNonPrimary() default true;
	
	
	//any WriteConcern static string. Case insensitive. STRICT/SAFE, NORMAL, etc...
	String concern() default "";

}
