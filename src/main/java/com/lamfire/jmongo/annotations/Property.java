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
@Target(ElementType.FIELD)
public @interface Property {

    /** The name of the key to store the field in; Defaults to the field name. */
    String value() default Mapper.IGNORED_FIELDNAME;
 
    /** Specify the concrete class to instantiate. */
    Class<?> concreteClass() default Object.class;
}
