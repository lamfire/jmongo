/**
 * 
 */
package com.lamfire.jmongo.converters;

import org.bson.types.ObjectId;

import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.MappingException;

/**
 * Convert to an ObjectId from string
 * @author scotthernandez
 */
@SuppressWarnings({"unchecked"})
public class ObjectIdConverter extends TypeConverter implements SimpleValueConverter{
	
	public ObjectIdConverter() { super(ObjectId.class); };
	
	@Override
	public
	Object decode(Class targetClass, Object val, MappedField optionalExtraInfo) throws MappingException {
		if (val == null) return null;

		if (val instanceof ObjectId)
			return val;
			
		return new ObjectId(val.toString()); // good luck
	}
}
