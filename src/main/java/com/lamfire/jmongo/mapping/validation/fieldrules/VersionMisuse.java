/**
 * 
 */
package com.lamfire.jmongo.mapping.validation.fieldrules;

import java.util.Set;

import com.lamfire.jmongo.annotations.Version;
import com.lamfire.jmongo.mapping.DefaultCreator;
import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation.Level;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 *
 */
public class VersionMisuse extends FieldConstraint {
	
	@Override
	protected void check(MappedClass mc, MappedField mf, Set<ConstraintViolation> ve) {
		if (mf.hasAnnotation(Version.class)) {
			Class<?> type = mf.getType();
			if (Long.class.equals(type) || long.class.equals(type)) {
				
				//TODO: Replace this will a read ObjectFactory call -- requires Mapper instance.
				Object testInstance = DefaultCreator.createInst(mc.getClazz());
				
				// check initial value
				if (Long.class.equals(type)) {
					if (mf.getFieldValue(testInstance) != null)
						ve.add(new ConstraintViolation(Level.FATAL, mc, mf, this.getClass(), "When using @" + Version.class.getSimpleName()
								+ " on a Long field, it must be initialized to null."));
				} else if (long.class.equals(type)) {
					if ((Long) mf.getFieldValue(testInstance) != 0L)
						ve.add(new ConstraintViolation(Level.FATAL, mc, mf, this.getClass(), "When using @" + Version.class.getSimpleName()
								+ " on a long field, it must be initialized to 0."));
				}
			} else
				ve.add(new ConstraintViolation(Level.FATAL, mc, mf, this.getClass(), "@" + Version.class.getSimpleName() + " can only be used on a Long/long field."));
		}
	}
	
}
