package com.lamfire.jmongo.converters;

import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.MappingException;

@SuppressWarnings({"unchecked"})
public class LongConverter extends TypeConverter implements SimpleValueConverter {
	
	public LongConverter() { super(long.class, Long.class); }
	
	@Override
	public Object decode(Class targetClass, Object val, MappedField optionalExtraInfo) throws MappingException {
		if (val == null) return null;

		if (val instanceof Number)
			return ((Number) val).longValue();
		else
			return Long.parseLong(val.toString());
	}

}
