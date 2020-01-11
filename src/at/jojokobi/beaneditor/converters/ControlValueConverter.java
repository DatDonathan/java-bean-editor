package at.jojokobi.beaneditor.converters;

import java.util.List;
import java.util.function.Consumer;

import at.jojokobi.beaneditor.ClassUtils;
import javafx.scene.Node;

public interface ControlValueConverter<T, C extends Node> {
	
	public C createControl (Consumer<T> valueChanged, List<Class<?>> genericParams, ConverterContainer container);
	
	public T convert (C c);
	
	public Class<C> getControlClass ();
	
	public Class<T> getValueClass ();
	
	public void setValue (T t, C c);
	
	public default void setValueUnsafe (Object obj, Node control) {
		setValue(ClassUtils.castPrimitive(obj, getValueClass()), getControlClass().cast(control));
	}
	
	public default T convertUnsafe (Node control) {
		return convert(getControlClass().cast(control));
	}
	
	public default T createStandardValue() {
		return null;
	}

}
