/**
 * 
 */
package com.lamfire.jmongo.mapping.validation.fieldrules;

import java.util.Set;

import com.lamfire.jmongo.annotations.Reference;
import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.lazy.LazyFeatureDependencies;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation.Level;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 *
 */
public class LazyReferenceMissingDependencies extends FieldConstraint {
	
	@Override
	protected void check(MappedClass mc, MappedField mf, Set<ConstraintViolation> ve) {
		Reference ref = mf.getAnnotation(Reference.class);
		if (ref != null) {
			if (ref.lazy()) {
				if (!LazyFeatureDependencies.testDependencyFullFilled())
					ve.add(new ConstraintViolation(Level.SEVERE, mc, mf, this.getClass(),
							"Lazy references need CGLib and Proxytoys in the classpath."));
			}
		}
	}
	
}
