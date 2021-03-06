/**
 * 
 */
package com.lamfire.jmongo.mapping.validation.fieldrules;

import java.io.Serializable;
import java.util.Set;

import com.lamfire.jmongo.annotations.Serialized;
import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation.Level;
import com.lamfire.jmongo.utils.ReflectionUtils;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * 
 */
public class MapNotSerializable extends FieldConstraint {
	
	@Override
	protected void check(MappedClass mc, MappedField mf, Set<ConstraintViolation> ve) {
		if (mf.isMap()) {
			if (mf.hasAnnotation(Serialized.class)) {
				Class<?> keyClass = ReflectionUtils.getParameterizedClass(mf.getField(), 0);
				Class<?> valueClass = ReflectionUtils.getParameterizedClass(mf.getField(), 1);
				if (keyClass!=null)
				{
					if (!Serializable.class.isAssignableFrom(keyClass))
						ve.add(new ConstraintViolation(Level.FATAL, mc, mf, this.getClass(), "Key class (" + keyClass.getName()
								+ ") is not Serializable"));
				}
				if (valueClass != null) {
					if (!Serializable.class.isAssignableFrom(keyClass))
						ve.add(new ConstraintViolation(Level.FATAL, mc, mf, this.getClass(), "Value class (" + valueClass.getName()
								+ ") is not Serializable"));
				}
			}
		}
	}
}
