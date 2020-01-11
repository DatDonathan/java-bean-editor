package at.jojokobi.beaneditor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClassUtils {
	
	public static final Comparator<Class<?>> CLASS_COMPARATOR = (c1, c2) -> c1.getName().compareTo(c2.getName());

	@SuppressWarnings("unchecked")
	public static <T> T castPrimitive (Object obj, Class<T> clazz) {
		Class<T> castClazz = clazz;
		if (clazz.isPrimitive()) {
			if (clazz == boolean.class) {
				castClazz = (Class<T>) Boolean.class;
			}
			else if (clazz == byte.class) {
				castClazz = (Class<T>) Byte.class;
			}
			else if (clazz == short.class) {
				castClazz = (Class<T>) Short.class;
			}
			else if (clazz == int.class) {
				castClazz = (Class<T>) Integer.class;
			}
			else if (clazz == long.class) {
				castClazz = (Class<T>) Long.class;
			}
			else if (clazz == float.class) {
				castClazz = (Class<T>) Float.class;
			}
			else if (clazz == double.class) {
				castClazz = (Class<T>) Double.class;
			}
			else if (clazz == char.class) {
				castClazz = (Class<T>) Character.class;
			}
		}
		return castClazz.cast(obj);
	}
	
	public static List<Class<?>> parseGenericParams (Type type) {
		List<Class<?>> list = new ArrayList<>();
		if (type instanceof ParameterizedType) {
			for (Type t : ((ParameterizedType) type).getActualTypeArguments()) {
				if (t instanceof Class<?>) {
					list.add((Class<?>) t);
				}
				else if (t instanceof WildcardType && ((WildcardType) t).getUpperBounds()[0] instanceof Class<?>) {
					list.add((Class<?>) ((WildcardType) t).getUpperBounds()[0]);
				}
			}
		}
		return list;
	}
	
}
