package at.jojokobi.beaneditor.gui;

import java.util.ArrayList;
import java.util.List;

import at.jojokobi.beaneditor.Property;
import at.jojokobi.beaneditor.converters.ControlValueInstance;
import at.jojokobi.beaneditor.converters.ConverterContainer;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;

public class ObjectEditorPane extends Control{

	
	public <T> ObjectEditorPane(List<Property<?>> properties, ConverterContainer container) {
		super();
		setSkin(new ObjectEditorPaneSkin(this));
		List<ControlValueInstance> instances = new ArrayList<>();
		for (Property<?> property : properties) {
			ControlValueInstance instance = new ControlValueInstance(property, container);
			instance.getConverter().setValueUnsafe(property.getValue(), instance.getControl());
			instances.add(instance);
		}
		VBox boxes = new VBox ();
//		//Menu
//		MenuBar bar = new MenuBar();
//		
//		Menu fileMenu = new Menu("File");
//		MenuItem printItem = new MenuItem("Print to console");
//		printItem.setOnAction(e -> System.out.println(t));
//		fileMenu.getItems().add(printItem);
//		
//		bar.getMenus().addAll(fileMenu);
//		boxes.getChildren().add(bar);
		//Input Boxes
		for (ControlValueInstance instance : instances) {
			boxes.getChildren().add(new PropertyControl(instance));
		}
		getChildren().add(boxes);
	}

}