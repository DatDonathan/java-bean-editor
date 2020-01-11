package at.jojokobi.beaneditor;

import java.util.List;

public class SimpleProperty<T> implements Property<T>{
	
	private String name;
	private Class<T> clazz;
	private T t;
	private List<Class<?>> genericParams;
	
	public SimpleProperty(String name, Class<T> clazz, T t, List<Class<?>> genericParams) {
		super();
		this.name = name;
		this.clazz = clazz;
		this.t = t;
		this.genericParams = genericParams;
	}
	@Override
	public T getValue() {
		return t;
	}
	@Override
	public void setValue(T t) {
		this.t = t;
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
		return genericParams;
	}

}
