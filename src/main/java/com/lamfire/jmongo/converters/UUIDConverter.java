package com.lamfire.jmongo.converters;

import java.util.UUID;

import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.MappingException;

@SuppressWarnings({"unchecked"})
public class UUIDConverter extends TypeConverter implements
		SimpleValueConverter {

	public UUIDConverter() {
		super(UUID.class);
	}

	public Object encode(Object value, MappedField optionalExtraInfo) {
		UUID uuid = (UUID) value;
		return uuid == null ? null : uuid.toString();
	}

	public Object decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo) throws MappingException {
		String uuidString = (String) fromDBObject;
		return uuidString == null ? null : UUID.fromString(uuidString);
	}
}