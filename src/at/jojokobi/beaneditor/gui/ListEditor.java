package at.jojokobi.beaneditor.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import at.jojokobi.beaneditor.converters.ConverterContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ListEditor<T> extends Control {
	
	private ListView<ListEditorEntry> view = new ListView<>();
	private Class<T> clazz;

	public ListEditor(Class<T> clazz, ConverterContainer container, Consumer<List<T>> valueChanged) {
		setSkin (new ListEditorSkin(this));
		System.out.println(clazz);
		
		this.clazz = clazz;
		
		VBox box = new VBox();
		box.getChildren().add(view);
		
		Button addButton = new Button("Add");
		Button removeButton = new Button("Remove");
		Button editButton = new Button("Edit");
		
		addButton.setOnAction(e -> {
			ListEditorEntry entry = new ListEditorEntry(clazz);
			view.getItems().add(view.getSelectionModel().getSelectedIndex() + 1, entry);
			view.getSelectionModel().select(entry);
			valueChanged.accept(getList());
			editButton.fire();
		});
		removeButton.setOnAction(e -> {
			view.getItems().remove(view.getSelectionModel().getSelectedItem());
			valueChanged.accept(getList());
		});
		editButton.setOnAction(e -> {
			ListEditorEntry selected = view.getSelectionModel().getSelectedItem();
			if (selected != null) {
				DialogFactory.showObjectEditorDialog(selected, container, getScene().getWindow());
				valueChanged.accept(getList());
				updateView();
			}
		});
		
		HBox buttons = new HBox(addButton, removeButton, editButton);
		box.getChildren().add(buttons);
		
		getChildren().add(box);
	}
	
	public List<T> getList () {
		List<T> list = new ArrayList<>();
		for (ListEditorEntry entry : view.getItems()) {
			list.add(clazz.cast(entry.getValue()));
		}
		return list;
	}
	
	public void setList (List<?> list) {
		ObservableList<ListEditorEntry> items = FXCollections.observableArrayList();
		for (Object obj : list) {
			ListEditorEntry entry = new ListEditorEntry(clazz);
			entry.setValue (clazz.cast(obj));
			items.add(entry);
		}
		view.setItems(items);
	}
	
	private void updateView () {
		view.setItems(FXCollections.observableArrayList(new ArrayList <>(view.getItems())));
	}
	
}

class ListEditorSkin extends SkinBase<ListEditor<?>> {

	protected ListEditorSkin(ListEditor<?> list) {
		super(list);
	}
}
