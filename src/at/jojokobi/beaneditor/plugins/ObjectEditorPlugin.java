package at.jojokobi.beaneditor.plugins;

import java.util.List;

import at.jojokobi.beaneditor.serialization.ObjectSerializer;

public interface ObjectEditorPlugin {
	
	public void init ();

	public List<Class<?>> getSerializableClasses ();
	
	public List<ObjectSerializer<?>> getObjectSerializers ();

	public default void enable() {
		
	}
	
}
