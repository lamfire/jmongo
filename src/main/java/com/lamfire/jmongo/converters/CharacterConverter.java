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
public class CharacterConverter extends TypeConverter implements SimpleValueConverter {
	public CharacterConverter() { super(Character.class, char.class); }
	
	@Override
	public Object decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo) throws MappingException {
		if (fromDBObject == null) return null;

		// TODO: Check length. Maybe "" should be null?
		return fromDBObject.toString().charAt(0);
	}
	
	@Override
	public Object encode(Object value, MappedField optionalExtraInfo) {
		return String.valueOf(value);
	}
}
