package at.jojokobi.beaneditor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ReflectiveInstanceSupplier<T> implements InstanceSupplier<T>{

	private Class<T> clazz;
	private Constructor<?> constructor;
	
	private ReflectiveInstanceSupplier(Class<T> clazz, Constructor<?> constructor) {
		super();
		this.clazz = clazz;
		this.constructor = constructor;
	}

	@Override
	public Class<T> getSupplyingClass() {
		return clazz;
	}

	@Override
	public T create(Object... objects) {
		try {
			return clazz.cast(constructor.newInstance(objects));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Argument<?>> getArguments() {
		List<Argument<?>> args = new ArrayList<>();
		for (Parameter param : constructor.getParameters()) {
			args.add(new SimpleArgument<>(param.getType(), ClassUtils.parseGenericParams(param.getParameterizedType()), BeanUtils.toPropertyName(param.getName())));
		}
		return args;
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder(clazz.getSimpleName());
		string.append ("(");
		boolean first = true;
		for (Argument<?> arg : getArguments()) {
			if (!first) {
				string.append (", ");
			}
			string.append (arg.getValueClass().getSimpleName());
			string.append (" ");
			string.append (arg.getName());
			first = false;
		}
		string.append (")");
		return string.toString();
	}
	
	public static <T> List<ReflectiveInstanceSupplier<T>> createInstaceSuppliers (Class<T> clazz) {
		List<ReflectiveInstanceSupplier<T>> suppliers = new ArrayList<>();
		for (Iterator<Constructor<?>> iterator = Arrays.asList(clazz.getConstructors()).iterator(); iterator.hasNext();) {
			Constructor<?> c = iterator.next();
			if (c.canAccess(null)) {
				suppliers.add (new ReflectiveInstanceSupplier<>(clazz, c));
			}
		}
		return suppliers;
	}
	
}
