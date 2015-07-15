/**
 * 
 */
package com.lamfire.jmongo.converters;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.MappingException;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * @author scotthernandez
 */
@SuppressWarnings({"unchecked"})
public class EnumSetConverter extends TypeConverter implements SimpleValueConverter{	

	private EnumConverter ec = new EnumConverter();

	public EnumSetConverter() { super(EnumSet.class); }
	
	@Override
	public Object decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo) throws MappingException {
		if (fromDBObject == null)
			return null;
		
		Class enumType = optionalExtraInfo.getSubClass();
		
		List l = (List) fromDBObject;
		if (l.isEmpty())
			return EnumSet.noneOf(enumType);
		
		ArrayList enums = new ArrayList();
		for (Object object : l) {
			enums.add(ec.decode(enumType, object));
		}
		EnumSet copyOf = EnumSet.copyOf(enums);
		return copyOf;
	}
	
	@Override
	public Object encode(Object value, MappedField optionalExtraInfo) {
		if (value == null)
			return null;
		
		ArrayList values = new ArrayList();
		
		EnumSet s = (EnumSet) value;
		Object[] array = s.toArray();
		for (int i = 0; i < array.length; i++) {
			values.add(ec.encode(array[i]));
		}
		
		return values;
	}
}
