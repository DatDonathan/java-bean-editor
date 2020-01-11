package at.jojokobi.beaneditor.converters;

public interface ConverterContainer {
	
	public <T> void putConverter (Class<T> clazz, ControlValueConverter<T, ?> converter);
	
	public <T> ControlValueConverter<?, ?> getConverter (Class<T> clazz);
}
