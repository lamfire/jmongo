package com.lamfire.jmongo.converters;

import java.util.Locale;
import java.util.StringTokenizer;

import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.MappingException;

@SuppressWarnings({"unchecked"})
public class LocaleConverter extends TypeConverter implements SimpleValueConverter {

	public LocaleConverter() { super(Locale.class); }
	
	@Override
	public Object decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo) throws MappingException {
		return parseLocale(fromDBObject.toString());
	}
	
	@Override
	public Object encode(Object val, MappedField optionalExtraInfo) {
		if (val == null) return null;

		return val.toString();
	}
	
	public static Locale parseLocale(final String localeString) {
		if ((localeString != null) && (localeString.length() > 0)) {
			StringTokenizer st = new StringTokenizer(localeString, "_");
			String language = st.hasMoreElements() ? st.nextToken() : Locale.getDefault().getLanguage();
			String country = st.hasMoreElements() ? st.nextToken() : "";
			String variant = st.hasMoreElements() ? st.nextToken() : "";
			return new Locale(language, country, variant);
		}
		return null;
	}
}
