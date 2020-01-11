package at.jojokobi.beaneditor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CustomPropertyEditor {

	Class<? extends PropertyEditor<?>> editor ();
	
}
