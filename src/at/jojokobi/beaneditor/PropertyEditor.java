package at.jojokobi.beaneditor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface PropertyEditor<T> {
	
	public List<Property<?>> getProperties (T t);
	
	@SuppressWarnings("unchecked")
	public static <T> PropertyEditor<T> getPropertyEditor (Class<T> clazz) {
		PropertyEditor<T> editor = null;
		//Check for Annotation
		System.out.println(clazz);
		System.out.println(clazz.getAnnotations());
		if (clazz.isAnnotationPresent(CustomPropertyEditor.class)) {
			System.out.println("Annotation");
			CustomPropertyEditor annotation = clazz.getAnnotation(CustomPropertyEditor.class);
			try {
				Constructor<?> constr = annotation.editor().getConstructor();
				editor = (PropertyEditor<T>) constr.newInstance();
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		if (editor == null) {
			editor = new ReflectivePropertyEditor<T>(clazz);
		}
		return editor;
	}
	
	public static <T> List<Property<?>> getPropertiesStatic (T t) {
		@SuppressWarnings("unchecked")
		PropertyEditor<? super T> editor = (PropertyEditor<? super T>) getPropertyEditor(t.getClass());
		return editor.getProperties(t);
	}

}
