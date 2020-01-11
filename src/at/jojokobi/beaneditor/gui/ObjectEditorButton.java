package at.jojokobi.beaneditor.gui;

import java.util.function.Consumer;

import at.jojokobi.beaneditor.converters.ConverterContainer;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.HBox;

public class ObjectEditorButton<T> extends Control {
	
	private T object;
	private Label label;

	public ObjectEditorButton(Class<T> clazz, ConverterContainer container, Consumer<T> valueChanged) {
		setSkin(new ObjectEditorButtonSkin(this));
		label = new Label();
		label.setPadding(new Insets(5));
		label.setText("" + object);
		Button newButton = new Button("New");
		Button editButton = new Button("Edit");
		
		newButton.setOnAction(e -> {
			T t = DialogFactory.showCreateObjectDialog(clazz, container, getScene().getWindow());
			if (t != null) {
				object = t;
				valueChanged.accept(object);
				label.setText("" + object);
				editButton.fire();
			}
		});
		editButton.setOnAction(e -> {
			if (object != null) {
				DialogFactory.showObjectEditorDialog(object, container, getScene().getWindow());
				valueChanged.accept(object);
				label.setText("" + object);
			}
		});
		
		HBox box = new HBox(newButton, editButton, label);
		getChildren().add(box);
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
		label.setText("" + object);
	}
	
}


class ObjectEditorButtonSkin extends SkinBase<ObjectEditorButton<?>> {

	protected ObjectEditorButtonSkin(ObjectEditorButton<?> arg0) {
		super(arg0);
	}
	
}