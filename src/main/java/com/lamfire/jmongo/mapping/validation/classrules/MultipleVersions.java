/**
 * 
 */
package com.lamfire.jmongo.mapping.validation.classrules;

import java.util.List;
import java.util.Set;

import com.lamfire.jmongo.annotations.Version;
import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.validation.ClassConstraint;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation.Level;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 *
 */
public class MultipleVersions implements ClassConstraint {
	
	public void check(MappedClass mc, Set<ConstraintViolation> ve) {
		List<MappedField> versionFields = mc.getFieldsAnnotatedWith(Version.class);
		if (versionFields.size() > 1)
			ve.add(new ConstraintViolation(Level.FATAL, mc, this.getClass(), "Multiple @" + Version.class
					+ " annotations are not allowed. (" + new FieldEnumString(versionFields)));
	}
}
