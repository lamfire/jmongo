/**
 * 
 */
package com.lamfire.jmongo.converters;

import java.sql.Timestamp;
import java.util.Date;

import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.MappingException;

/**
 * @author scotthernandez
 */
@SuppressWarnings({"unchecked"})
public class TimestampConverter extends DateConverter implements SimpleValueConverter{
	
	public TimestampConverter() { super(Timestamp.class); };
	
	@Override
	public
	Object decode(Class targetClass, Object val, MappedField optionalExtraInfo) throws MappingException {
		Date d = (Date) super.decode(targetClass, val, optionalExtraInfo);
		return new Timestamp(d.getTime());
	}

	@Override
	public Object encode(Object val, MappedField optionalExtraInfo) {
		if (val == null) 
			return null;
		return new Date(((Timestamp)val).getTime());
	}
	
}
