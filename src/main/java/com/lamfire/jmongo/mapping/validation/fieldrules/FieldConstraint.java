/**
 * 
 */
package com.lamfire.jmongo.mapping.validation.fieldrules;

import java.util.Set;

import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.Mapper;
import com.lamfire.jmongo.mapping.validation.ClassConstraint;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 *
 */
public abstract class FieldConstraint implements ClassConstraint {
	Mapper mapr;
	
	public final void check(MappedClass mc, Set<ConstraintViolation> ve) {
		for (MappedField mf : mc.getPersistenceFields()) {
			check(mc, mf, ve);
		}
	}
	
	protected abstract void check(MappedClass mc, MappedField mf, Set<ConstraintViolation> ve);
	
}
