package at.jojokobi.beaneditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerializableClassHandler {
	
	private static SerializableClassHandler instance = null;
	
	private Map<Class<?>, List<InstanceSupplier<?>>> classes = new HashMap<>();
	
	public static SerializableClassHandler getInstance () {
		if (instance == null) {
			instance = new SerializableClassHandler();
		}
		return instance;
	}
	
	private SerializableClassHandler () {
		
	}
	
	public void addClass (InstanceSupplier<?> supplier) {
		if (classes.get(supplier.getSupplyingClass()) == null) {
			classes.put(supplier.getSupplyingClass(), new ArrayList<>());
		}
		classes.get(supplier.getSupplyingClass()).add(supplier);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<Class<? extends T>> getPossibleClasses (Class<T> superclass) {
		List<Class<? extends T>> classes = new ArrayList<>();
		for (Class<?> clazz : this.classes.keySet()) {
			if (superclass.isAssignableFrom(clazz)) {
				classes.add((Class<? extends T>) clazz);
			}
		}
		return classes;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<InstanceSupplier<T>> getSuppliers(Class<T> clazz) {
		List<InstanceSupplier<T>> suppliers = new ArrayList<>();
		if (classes.get(clazz) != null) {
			for (InstanceSupplier<?> supplier : classes.get(clazz)) {
				suppliers.add ((InstanceSupplier<T>) supplier);
			}
		}
		return suppliers;
	}

}
