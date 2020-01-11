package at.jojokobi.beaneditor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

public class ReflectiveProperty<T> implements Property<T>{
	
	private String name;
	private Object holder;
	private Class<T> clazz;
	private Method getter;
	private Method setter;

	public ReflectiveProperty(String name, Object holder, Class<T> clazz, Method getter, Method setter) {
		super();
		this.name = name;
		this.holder = holder;
		this.clazz = clazz;
		this.getter = getter;
		this.setter = setter;
		
		if (!setter.canAccess(holder) || !getter.canAccess(holder) || getter.getParameterTypes().length != 0 || setter.getParameterTypes().length != 1 || !setter.getParameterTypes()[0].equals(clazz) || !clazz.equals(getter.getReturnType())) {
			throw new IllegalArgumentException("Getter and/or setter are invalid!");
		}
	}

	@Override
	public T getValue() {
		T value = null;
		try {
			value = ClassUtils.castPrimitive(getter.invoke(holder), clazz);
//			if (clazz.isEnum()) {
				System.out.println("Got enum value from " + name + ": " + value);
//			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return value;
	}

	@Override
	public void setValue(T t) {
		try {
			setter.invoke(holder, t);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<T> getValueClass() {
		return clazz;
	}

	@Override
	public List<Class<?>> getGenericParams() {
		Type type = getter.getGenericReturnType();
		return ClassUtils.parseGenericParams(type);
	}
}
