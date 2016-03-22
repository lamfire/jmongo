package com.lamfire.jmongo.converters;

import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.MappingException;

@SuppressWarnings({"unchecked"})
public class ShortConverter extends TypeConverter implements SimpleValueConverter{
	public ShortConverter() { super(short.class, Short.class); }
	
	@Override
	public Object decode(Class targetClass, Object val, MappedField optionalExtraInfo) throws MappingException {
		if (val == null) return null;
			Object dbValue = val;
		
		if (dbValue instanceof Number)
			return ((Number) dbValue).shortValue();
		
		String sVal = val.toString();
		return Short.parseShort(sVal);
	}
}
