/**
 * 
 */
package com.lamfire.jmongo.mapping.validation.classrules;

import java.util.Set;

import com.lamfire.jmongo.annotations.Embedded;
import com.lamfire.jmongo.annotations.Entity;
import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.jmongo.mapping.validation.ClassConstraint;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation.Level;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * 
 */
public class EntityAndEmbed implements ClassConstraint {

	public void check(MappedClass mc, Set<ConstraintViolation> ve) {
		
		if (mc.getEntityAnnotation() != null && mc.getEmbeddedAnnotation() != null) {
			new ConstraintViolation(Level.FATAL, mc, this.getClass(), "Cannot have both @" + Entity.class.getSimpleName() + " and @"
					+ Embedded.class.getSimpleName() + " annotation at class level.");
		}
		
	}
}
