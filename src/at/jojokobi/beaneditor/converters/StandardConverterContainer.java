package at.jojokobi.beaneditor.converters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardConverterContainer implements ConverterContainer {
	
	private Map<Class<?>, ControlValueConverter<?, ?>> converters = new HashMap<Class<?>, ControlValueConverter<?,?>> ();
	
	public StandardConverterContainer() {
		putConverter(String.class, ControlValueConverters.STRING_CONTROL_VALUE_CONVERTER);
		putConverter(Integer.TYPE, ControlValueConverters.INTEGER_CONTROL_VALUE_CONVERTER);
		putConverter(Double.TYPE, ControlValueConverters.DOUBLE_CONTROL_VALUE_CONVERTER);
		putConverter(List.class, ControlValueConverters.LIST_CONTROL_VALUE_CONVERTER);
	}
	
	public <T> void putConverter (Class<T> clazz, ControlValueConverter<T, ?> converter) {
		converters.put(clazz, converter);
	}
	
	public <T> ControlValueConverter<?, ?> getConverter (Class<T> clazz) {
		ControlValueConverter<?, ?> converter = converters.get(clazz);
		return converter;
	}


}
