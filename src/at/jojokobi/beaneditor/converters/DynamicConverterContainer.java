package at.jojokobi.beaneditor.converters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class DynamicConverterContainer implements ConverterContainer {
	
	private List<Function<Class<?>, ControlValueConverter<?, ?>>> converters = new ArrayList<>();
	
	public DynamicConverterContainer() {
		
	}
	
	public DynamicConverterContainer addStandardConverters () {
		addConverter(c -> ControlValueConverters.newObjectControlValueConverter(c));
		addConverter(c -> c.isEnum() ? ControlValueConverters.newEnumControlValueConverter(c) : null);
		putConverter(Class.class, ControlValueConverters.CLASS_CONTROL_VALUE_CONVERTER);
		putConverter(List.class, ControlValueConverters.LIST_CONTROL_VALUE_CONVERTER);
		putConverter(String.class, ControlValueConverters.STRING_CONTROL_VALUE_CONVERTER);
		putConverter(Double.class, ControlValueConverters.DOUBLE_CONTROL_VALUE_CONVERTER);
		putConverter(Integer.class, ControlValueConverters.INTEGER_CONTROL_VALUE_CONVERTER);
		putConverter(Boolean.class, ControlValueConverters.BOOLEAN_CONTROL_VALUE_CONVERTER);
		putConverter(Float.class, ControlValueConverters.FLOAT_CONTROL_VALUE_CONVERTER);
		putConverter(Byte.class, ControlValueConverters.BYTE_CONTROL_VALUE_CONVERTER);
		putConverter(double.class, ControlValueConverters.DOUBLE_CONTROL_VALUE_CONVERTER);
		putConverter(int.class, ControlValueConverters.INTEGER_CONTROL_VALUE_CONVERTER);
		putConverter(boolean.class, ControlValueConverters.BOOLEAN_CONTROL_VALUE_CONVERTER);
		putConverter(float.class, ControlValueConverters.FLOAT_CONTROL_VALUE_CONVERTER);
		putConverter(byte.class, ControlValueConverters.BYTE_CONTROL_VALUE_CONVERTER);
		
		return this;
	}
	
	/**
	 * 
	 * @param converter	This function should return a ControlValueConverter if possible for the given class. If not it should return null.
	 */
	public void addConverter (Function<Class<?>, ControlValueConverter<?, ?>> converter) {
		converters.add(0, converter);
	}

	@Override
	public <T> ControlValueConverter<?, ?> getConverter(Class<T> clazz) {
		ControlValueConverter<?, ?> converter = null;
		for (Iterator<Function<Class<?>, ControlValueConverter<?, ?>>> iterator = converters.iterator(); iterator.hasNext() && converter == null;) {
			Function<Class<?>, ControlValueConverter<?, ?>> function = iterator.next();
			converter = function.apply(clazz);
		}
		return converter;
	}

	@Override
	public <T> void putConverter(Class<T> clazz, ControlValueConverter<T, ?> converter) {
		addConverter(c -> c == clazz ? converter : null);
	}

}