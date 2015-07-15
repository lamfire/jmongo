/**
 * 
 */
package com.lamfire.jmongo.mapping.validation.classrules;

import java.util.Set;

import com.lamfire.jmongo.annotations.Embedded;
import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.jmongo.mapping.validation.ClassConstraint;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation.Level;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 *
 */
public class EmbeddedAndId implements ClassConstraint {

	public void check(MappedClass mc, Set<ConstraintViolation> ve) {
        if (mc.getEmbeddedAnnotation() != null && mc.getIdField() != null) {
			ve.add(new ConstraintViolation(Level.FATAL, mc, this.getClass(), "@" + Embedded.class.getSimpleName()
					+ " classes cannot specify a @Id field"));
        }
	}
	
}
