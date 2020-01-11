package at.jojokobi.beaneditor;

import java.lang.reflect.Method;

public final class BeanUtils {

	private BeanUtils() {
		
	}
	
	public static String toVariableName (String methodName) {
		if (methodName.startsWith("is")) {
			methodName = methodName.substring(2);
		}
		else if (methodName.startsWith("get") || methodName.startsWith("set")) {
			methodName = methodName.substring(3);
		}
		else {
			throw new IllegalArgumentException("The Method Name is not a valid getter or setter!");
		}
		StringBuilder name = new StringBuilder(methodName);
		name.setCharAt(0, Character.toLowerCase(name.charAt(0)));
		return name.toString();
	}
	
//	public static String toSetterName (String methodName) {
//		StringBuilder name = new StringBuilder("set" + methodName);
//		name.setCharAt(3, Character.toLowerCase(name.charAt(3)));
//		return name.toString();
//	}
//	
//	public static String toGetterName (String methodName) {
//		StringBuilder name = new StringBuilder("get" + methodName);
//		name.setCharAt(3, Character.toLowerCase(name.charAt(3)));
//		return name.toString();
//	}
	
	public static boolean isGetter (Method getter) {
		return !Void.TYPE.equals(getter.getReturnType()) && hasGetterSignature(getter, getter.getReturnType()) && getter.getName().startsWith(Boolean.TYPE.equals(getter.getReturnType()) ? "is" : "get");
	}
	
	public static boolean isGetterFor (Method getter, String variable) {
		return isGetter(getter) && variable.equals(toVariableName(getter.getName()));
	}
	
	public static boolean isSetter (Method setter) {
		return setter.getParameterTypes().length == 1 && hasSetterSignature(setter, setter.getParameterTypes()[0]) && setter.getName().startsWith("set");
	}
	
	public static boolean isSetterFor (Method setter, String variable) {
		return isSetter(setter) && variable.equals(toVariableName(setter.getName()));
	}
	
	public static boolean hasGetterSignature (Method getter, Class<?> type) {
		return getter.getParameterTypes().length == 0 && type.equals(getter.getReturnType());
	}
	
	public static boolean hasSetterSignature (Method setter, Class<?> type) {
		return setter.getParameterTypes().length == 1 && type.equals(setter.getParameterTypes()[0]);
	}
	

	public static String toPropertyName (String name) {
		StringBuilder string = new StringBuilder();
		
		for (int i = 0; i < name.length(); i++) {
			if (i == 0) {
				string.append(Character.toUpperCase(name.charAt(i)));
			}
			else {
				if (Character.isUpperCase(name.charAt(i))) {
					string.append (" ");
				}
				string.append(name.charAt(i));
			}
		}
		return string.toString();
	}

}
