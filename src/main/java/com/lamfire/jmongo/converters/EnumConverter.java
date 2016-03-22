package com.lamfire.jmongo.converters;

import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.MappingException;

@SuppressWarnings({"unchecked"})
public class EnumConverter extends TypeConverter implements SimpleValueConverter{
	
	@Override
	protected
	boolean isSupported(Class c, MappedField optionalExtraInfo) {
		return c.isEnum();
	}
	
	@Override
	public
	Object decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo) throws MappingException {
		if (fromDBObject == null) return null;
		return Enum.valueOf(targetClass, fromDBObject.toString());
	}
	
	@Override
	public
	Object encode(Object value, MappedField optionalExtraInfo) {
		if (value == null)
			return null;
		
		return getName((Enum) value);
	}
	
	private <T extends Enum> String getName(T value) {
		return value.name();
	}
}
