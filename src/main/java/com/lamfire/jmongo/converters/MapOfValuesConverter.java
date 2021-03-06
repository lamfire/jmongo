package com.lamfire.jmongo.converters;

import java.util.HashMap;
import java.util.Map;

import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.MappingException;
import com.lamfire.jmongo.utils.IterHelper;
import com.lamfire.jmongo.utils.ReflectionUtils;
import com.lamfire.jmongo.utils.IterHelper.MapIterCallback;

@SuppressWarnings({"unchecked"})
public class MapOfValuesConverter extends TypeConverter {
	private final DefaultConverters converters;
	
	public MapOfValuesConverter(DefaultConverters converters) {
		this.converters = converters;
	}
	
	@Override
	protected boolean isSupported(Class<?> c, MappedField optionalExtraInfo) {
		if (optionalExtraInfo != null)
			return optionalExtraInfo.isMap();
		else
			return ReflectionUtils.implementsInterface(c, Map.class);
	}
	
	@Override
	public Object decode(Class targetClass, Object fromDBObject, final MappedField mf) throws MappingException {
		if (fromDBObject == null) return null;


		final Map values = mapr.getOptions().objectFactory.createMap(mf);
		new IterHelper<Object, Object>().loopMap((Object)fromDBObject, new MapIterCallback<Object, Object>() {
			@Override
			public void eval(Object key, Object val) {
					Object objKey = converters.decode(mf.getMapKeyClass(), key);
					values.put(objKey, converters.decode(mf.getSubClass(), val));
				}});
		
		return values;
	}
	
	@Override
	public Object encode(Object value, MappedField mf) {
		if (value == null)
			return null;
		
		Map<Object, Object> map = (Map<Object, Object>) value;
		if ((map != null) && (map.size() > 0)) {
			Map mapForDb = new HashMap();
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				String strKey = converters.encode(entry.getKey()).toString();
				mapForDb.put(strKey, converters.encode(entry.getValue()));
			}
			return mapForDb;
		}
		return null;
	}	
}