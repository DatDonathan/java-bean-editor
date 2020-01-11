package at.jojokobi.beaneditor;

import java.util.List;

public interface Property<T> {
	public T getValue();

	public void setValue(T t);

	public String getName();

	public default void setValueUnsafe(Object o) {
		setValue(ClassUtils.castPrimitive(o, getValueClass()));
	}

	public Class<T> getValueClass();

	public List<Class<?>> getGenericParams();

}
