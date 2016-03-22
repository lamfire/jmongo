package com.lamfire.jmongo.converters;

import java.net.URI;

import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.MappingException;

@SuppressWarnings({"unchecked"})
public class URIConverter extends TypeConverter implements SimpleValueConverter{
	
	public URIConverter() { this(URI.class); };
	protected URIConverter(Class clazz) { super(clazz); }
	@Override
	public String encode(Object uri, MappedField optionalExtraInfo) {
		if (uri == null) return null;
		
		return ((URI)uri).toString().replace(".", "%46");
	}
	
	@Override
	public
	Object decode(Class targetClass, Object val, MappedField optionalExtraInfo) throws MappingException {
		if (val == null) return null;

		return URI.create(val.toString().replace("%46", "."));
	}
}
