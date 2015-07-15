package com.lamfire.jmongo.mapping.validation;

import java.util.Set;

import com.lamfire.jmongo.mapping.MappedClass;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 */
public interface ClassConstraint {
	void check(MappedClass mc, Set<ConstraintViolation> ve);
}
