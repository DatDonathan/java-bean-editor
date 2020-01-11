package at.jojokobi.beaneditor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FunctionalInstanceSupplier<T> implements InstanceSupplier<T>{
	
	private Class<T> clazz;
	private Supplier<T> supplier;

	public FunctionalInstanceSupplier(Class<T> clazz, Supplier<T> supplier) {
		super();
		this.clazz = clazz;
		this.supplier = supplier;
	}

	@Override
	public Class<T> getSupplyingClass() {
		return clazz;
	}

	@Override
	public T create(Object... objects) {
		return supplier.get();
	}
	
	@Override
	public String toString() {
		return clazz.getName();
	}

	@Override
	public List<Argument<?>> getArguments() {
		return new ArrayList<>();
	}
	
}
