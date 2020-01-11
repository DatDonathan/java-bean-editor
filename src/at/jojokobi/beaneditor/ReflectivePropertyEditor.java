package at.jojokobi.beaneditor;

import static at.jojokobi.beaneditor.BeanUtils.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectivePropertyEditor<T> implements PropertyEditor<T> {

	private Class<T> clazz;

	public ReflectivePropertyEditor(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	@Override
	public List<Property<?>> getProperties(T t) {
		List<Method> methods = Arrays.asList(clazz.getMethods());
		List<Property<?>> properties = new ArrayList<>();
		for (Method method : new ArrayList<>(methods)) {
			Method setter = null;
			if (isGetter(method)
					&& (setter = methods.stream().filter(m -> isSetterFor(m, toVariableName(method.getName())))
							.findFirst().orElseGet(() -> null)) != null
					&& setter.canAccess(t) && method.canAccess(t)) {
				properties.add(new ReflectiveProperty<>(toPropertyName(toVariableName(method.getName())), t, method.getReturnType(), method, setter));
			}
		}
		
		return properties;
	}
	
}
