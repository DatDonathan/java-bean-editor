package at.jojokobi.beaneditor.converters;

import at.jojokobi.beaneditor.Property;
import javafx.scene.Node;

public class ControlValueInstance {
	
	private Property<?> property;
	private ControlValueConverter<?, ?> converter;
	private Node control;
	
	public ControlValueInstance(Property<?> property, ConverterContainer container) {
		super();
		this.property = property;
		this.converter = container.getConverter(property.getValueClass());
		this.control = converter.createControl(v -> property.setValueUnsafe(v), property.getGenericParams(), container);
	}

	public void updateProperty () {
		property.setValueUnsafe(converter.convertUnsafe(control));
	}
	
	public Node getControl () {
		return control;
	}

	public Property<?> getProperty() {
		return property;
	}

	public ControlValueConverter<?, ?> getConverter() {
		return converter;
	}

}
