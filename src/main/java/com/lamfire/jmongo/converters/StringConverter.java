/**
 * 
 */
package com.lamfire.jmongo.converters;

import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.MappingException;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * @author scotthernandez
 */
@SuppressWarnings({"unchecked"})
public class StringConverter extends TypeConverter implements SimpleValueConverter{
	public StringConverter() { super(String.class); }

	@Override
	public Object decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo) throws MappingException {
		if (fromDBObject == null) return null;
		
		if (fromDBObject instanceof String)
			return (String) fromDBObject;
		
		return fromDBObject.toString();
	}
}
