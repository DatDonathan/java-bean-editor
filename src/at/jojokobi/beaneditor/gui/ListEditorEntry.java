package at.jojokobi.beaneditor.gui;

import at.jojokobi.beaneditor.CustomPropertyEditor;

@CustomPropertyEditor  (editor = ListEditorEntryEditor.class)
public class ListEditorEntry {

	private Class<?> clazz;
	private Object value;
	
	public ListEditorEntry(Class<?> clazz) {
		super();
		this.clazz = clazz;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value + "";
	}
	
}
