/**
 * 
 */
package com.lamfire.jmongo.mapping.validation.fieldrules;

import java.util.Set;

import com.lamfire.jmongo.annotations.Embedded;
import com.lamfire.jmongo.annotations.Id;
import com.lamfire.jmongo.annotations.Property;
import com.lamfire.jmongo.annotations.Reference;
import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation.Level;

/** @author ScottHenandez */
public class IdDoesNotMix extends FieldConstraint {

	@Override
	protected void check(MappedClass mc, MappedField mf, Set<ConstraintViolation> ve) {
		// an @Id field can not be a Value, Reference, or Embedded
		if (mf.hasAnnotation(Id.class))
			if(mf.hasAnnotation(Reference.class) || mf.hasAnnotation(Embedded.class) || mf.hasAnnotation(Property.class))
				ve.add(new ConstraintViolation(Level.FATAL, mc, mf, this.getClass(), mf.getFullName() + " is annotated as @"
						+ Id.class.getSimpleName() + " and cannot be mixed with other annotations (like @Reference)"));
	}
}
