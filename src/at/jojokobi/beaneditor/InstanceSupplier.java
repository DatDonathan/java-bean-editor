package at.jojokobi.beaneditor;

import java.util.List;

public interface InstanceSupplier<T> {
	
	public Class<T> getSupplyingClass();
	
	public T create(Object... objects);
	
	public List<Argument<?>> getArguments ();

}
