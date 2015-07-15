/**
 * 
 */
package com.lamfire.jmongo.mapping.validation.classrules;

import java.util.List;
import java.util.Set;

import com.lamfire.jmongo.annotations.Id;
import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.validation.ClassConstraint;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation.Level;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 */
public class MultipleId implements ClassConstraint {
	
	public void check(MappedClass mc, Set<ConstraintViolation> ve) {
		
		List<MappedField> idFields = mc.getFieldsAnnotatedWith(Id.class);
		
		if (idFields.size() > 1) {
			ve.add(new ConstraintViolation(Level.FATAL, mc, this.getClass(), "More than one @" + Id.class.getSimpleName()
					+ " Field found (" + new FieldEnumString(idFields)
					+ ")."));
		}
	}
	
}
