/**
 * 
 */
package com.lamfire.jmongo.converters;

import java.util.ArrayList;
import java.util.List;

import org.bson.LazyBSONList;

import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.MappingException;
import com.lamfire.jmongo.utils.ReflectionUtils;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * @author scotthernandez
 */
@SuppressWarnings({"unchecked"})
public class DoubleConverter extends TypeConverter implements SimpleValueConverter{

	public DoubleConverter() { super(double.class, Double.class); }
	
	@Override
	public
	Object decode(Class targetClass, Object val, MappedField optionalExtraInfo) throws MappingException {
		if (val == null) return null;
		
		if (val instanceof Double)
			return (Double) val;
		
		if (val instanceof Number)
			return ((Number) val).doubleValue();

		//FixMe: super-hacky
		if (val instanceof LazyBSONList || val instanceof ArrayList)
			return ReflectionUtils.convertToArray(double.class, (List<?>)val);
			
		String sVal = val.toString();
		return Double.parseDouble(sVal);
	}
}
