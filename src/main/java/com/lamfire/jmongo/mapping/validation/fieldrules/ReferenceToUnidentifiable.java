/**
 * 
 */
package com.lamfire.jmongo.mapping.validation.fieldrules;

import java.util.Set;

import com.lamfire.jmongo.annotations.Id;
import com.lamfire.jmongo.annotations.Reference;
import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.MappingException;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation.Level;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * 
 */
public class ReferenceToUnidentifiable extends FieldConstraint {
	
	@SuppressWarnings("unchecked")
	@Override
	protected void check(MappedClass mc, MappedField mf, Set<ConstraintViolation> ve) {
		if (mf.hasAnnotation(Reference.class)) {
			Class realType = (mf.isSingleValue()) ? mf.getType() : mf.getSubClass();
			
			if (realType == null) throw new MappingException("Type is null for this MappedField: " + mf);
			
			if ((!realType.isInterface() && mc.getMapper().getMappedClass(realType).getIdField() == null))
				ve.add(new ConstraintViolation(Level.FATAL, mc, mf, this.getClass(), mf.getFullName() + " is annotated as a @"
						+ Reference.class.getSimpleName() + " but the " + mf.getType().getName()
						+ " class is missing the @" + Id.class.getSimpleName() + " annotation"));
		}
	}
	
}
