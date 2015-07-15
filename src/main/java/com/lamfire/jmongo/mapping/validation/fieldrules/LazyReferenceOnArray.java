/**
 * 
 */
package com.lamfire.jmongo.mapping.validation.fieldrules;

import java.util.Set;

import com.lamfire.jmongo.annotations.Reference;
import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation.Level;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * 
 */
@SuppressWarnings("unchecked")
public class LazyReferenceOnArray extends FieldConstraint {
	
	@Override
	protected void check(MappedClass mc, MappedField mf, Set<ConstraintViolation> ve) {
		Reference ref = mf.getAnnotation(Reference.class);
		if (ref != null && ref.lazy()) {
			Class type = mf.getType();
			if (type.isArray())
				ve.add(new ConstraintViolation(Level.FATAL, mc, mf, this.getClass(),
						"The lazy attribute cannot be used for an Array. If you need a lazy array please use ArrayList instead."));
		}
	}
	
}
