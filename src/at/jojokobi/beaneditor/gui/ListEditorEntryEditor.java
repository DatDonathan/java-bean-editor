package at.jojokobi.beaneditor.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.beaneditor.Property;
import at.jojokobi.beaneditor.PropertyEditor;

public class ListEditorEntryEditor implements PropertyEditor<ListEditorEntry>{

	@SuppressWarnings("rawtypes")
	@Override
	public List<Property<?>> getProperties(ListEditorEntry entry) {
		return Arrays.<Property<?>>asList (new Property () {

			@Override
			public Object getValue() {
				return entry.getValue();
			}

			@Override
			public void setValue(Object t) {
				entry.setValue(t);
			}

			@Override
			public String getName() {
				return "Value";
			}

			@Override
			public Class<?> getValueClass() {
				System.out.println(entry.getClazz());
				return entry.getClazz();
			}
			@Override
			public List<Class<?>> getGenericParams() {
				return new ArrayList<>();
			}
			
		});
	}
	
}
