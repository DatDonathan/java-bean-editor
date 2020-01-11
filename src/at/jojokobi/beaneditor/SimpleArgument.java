package at.jojokobi.beaneditor;

import java.util.List;

public class SimpleArgument<T> implements Argument<T>{
	
	private Class<T> clazz;
	private List<Class<?>> genericParams;
	private String name;

	public SimpleArgument(Class<T> clazz, List<Class<?>> genericParams, String name) {
		super();
		this.clazz = clazz;
		this.genericParams = genericParams;
		this.name = name;
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
