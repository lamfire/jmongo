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
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Embedded {

    /**
     * The name of the Mongo value to store the field.
     * Defaults to the name of the field being annotated.
     *
     * @return the name of the Mongo value storing the field value (use on fields only, not applicable for Type level)
     */
    String value() default Mapper.IGNORED_FIELDNAME;

    /** Specify the concrete class to instantiate. */
    Class<?> concreteClass() default Object.class;
}
