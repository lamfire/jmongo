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
public class CharArrayConverter extends TypeConverter  implements SimpleValueConverter{
	public CharArrayConverter() { super(char[].class); }
	
	@Override
	public Object decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo) throws MappingException {
		if (fromDBObject == null) return null;
		
		return fromDBObject.toString().toCharArray();
	}
	
	@Override
	public Object encode(Object value, MappedField optionalExtraInfo) {
		return new String((char[]) value);
	}
}
