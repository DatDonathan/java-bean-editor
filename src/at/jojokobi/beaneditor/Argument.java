package at.jojokobi.beaneditor;

import java.util.List;

public interface Argument<T> {
	
	public String getName ();
	
	public Class<T> getValueClass ();
	
	public List<Class<?>> getGenericParams ();

}
