/**
 * 
 */
package com.lamfire.jmongo.mapping.validation.fieldrules;

import java.util.Set;

import com.lamfire.jmongo.annotations.Property;
import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation.Level;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * 
 */
public class MisplacedProperty extends FieldConstraint {

	@Override
	protected void check(MappedClass mc, MappedField mf, Set<ConstraintViolation> ve) {
		// a field can be a Value, Reference, or Embedded
		if (mf.hasAnnotation(Property.class)) {
			// make sure that the property type is supported
			if (mf.isSingleValue() && !mf.isTypeMongoCompatible() && !mc.getMapper().getConverters().hasSimpleValueConverter(mf)) {
				ve.add(new ConstraintViolation(Level.WARNING, mc, mf, this.getClass(), mf.getFullName() + " is annotated as @"
						+ Property.class.getSimpleName() + " but is a type that cannot be mapped simply (type is "
						+ mf.getType().getName() + ")."));
			}
		}
	}
	
}
