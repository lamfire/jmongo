package com.lamfire.jmongo.converters;

import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.MappingException;

@SuppressWarnings({"unchecked"})
public class PassthroughConverter extends TypeConverter {
	
	public PassthroughConverter() {}

	public PassthroughConverter(Class...types) { super(types); }
	
	@Override
	protected boolean isSupported(Class c, MappedField optionalExtraInfo) {
		return true;
	}
	
	@Override
	public Object decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo) throws MappingException {
		return fromDBObject;
	}
}
