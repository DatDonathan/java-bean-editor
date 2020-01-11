package at.jojokobi.beaneditor.gui;

import at.jojokobi.beaneditor.converters.ControlValueInstance;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PropertyControl extends Control{
	
	public PropertyControl(ControlValueInstance instance) {
		setSkin(new PropertyControlSkin(this));
		
		HBox box = new HBox();
		//Assign standard value
		if (instance.getProperty().getValue() == null) {
			System.out.println("Property null: " + instance.getProperty().getName());
			instance.getProperty().setValueUnsafe(instance.getConverter().createStandardValue());
		}
		//Label
		Label label = new Label(instance.getProperty().getName() + ":");
		label.setMinWidth(200);
		Font font = Font.font("None", FontWeight.BOLD, 12);
		label.setFont(font);
		box.getChildren().add(label);
		//Control
		box.getChildren().add(instance.getControl());
		//Null Button
		if (!instance.getProperty().getValueClass().isPrimitive()) {
			Button button = new Button("Make Null");
			button.setOnAction(a -> {
				if (instance.getProperty().getValue() == null) {
					instance.updateProperty();
					instance.getControl().setVisible(true);
					if (instance.getProperty().getValue() != null) {
						button.setText("Make Null");
					}
				}
				else {
					instance.getProperty().setValue(null);
					instance.getControl().setVisible(false);
					button.setText ("Assign Value");
				}
			});
			box.getChildren().add(button);
			if (instance.getProperty().getValue() == null) {
				button.fire();
			}
		}
		
		box.setPadding(new Insets(10));
		getChildren().add(box);
	}
	
}

class PropertyControlSkin extends SkinBase<PropertyControl> {
	
	protected PropertyControlSkin(PropertyControl control) {
		super(control);
	}
	
}
