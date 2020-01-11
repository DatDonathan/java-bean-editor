package at.jojokobi.beaneditor.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import at.jojokobi.beaneditor.PropertyEditor;
import at.jojokobi.beaneditor.converters.ConverterContainer;
import at.jojokobi.beaneditor.converters.DynamicConverterContainer;
import at.jojokobi.beaneditor.serialization.ObjectSerializer;
import at.jojokobi.beaneditor.serialization.SerializationException;
import at.jojokobi.beaneditor.serialization.TemporarySerializationData;
import at.jojokobi.beaneditor.serialization.TemporarySerializatizerData;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ObjectEditorPaneMenu extends Control {

	private Object object;
	private Map<String, ObjectSerializer<?>> serializers = new HashMap<>();;
	private TemporarySerializationData data;

	public ObjectEditorPaneMenu(ObjectSerializer<?>... serializers) {
		for (ObjectSerializer<?> serializer : serializers) {
			this.serializers.put(serializer.getName(), serializer);
		}
		setSkin(new ObjectEditorPaneMenuSkin(this));
		
		ConverterContainer container = new DynamicConverterContainer().addStandardConverters();
		BorderPane pane = new BorderPane ();
		
		MenuItem newItem = new MenuItem("New");
		MenuItem openItem = new MenuItem("Open");
		MenuItem saveItem = new MenuItem("Save");
		MenuItem saveAsItem = new MenuItem("Save as ...");
		MenuItem printItem = new MenuItem("Print to console");
		Menu fileMenu = new Menu("File");
		fileMenu.getItems().addAll(newItem, openItem, saveItem, saveAsItem, printItem);
		
		MenuBar menuBar = new MenuBar(fileMenu);
		pane.setTop(menuBar);
		
		//New entry
		newItem.setOnAction(e -> {
			Object t = DialogFactory.showCreateObjectDialog(Object.class, container, getScene().getWindow());
			if (t != null) {
				object = t;
				data = null;
				pane.setCenter(new ScrollPane(new ObjectEditorPane(PropertyEditor.getPropertiesStatic(object), container)));
			}
		});
		//Open entry
		openItem.setOnAction(e -> {
			boolean cancelled = false;
			if (object != null) {
				Alert dialog = new Alert(AlertType.CONFIRMATION, "Do you want to save your unsaved progress?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
				ButtonType type = dialog.showAndWait().orElseGet(() -> null);
				if (type == ButtonType.YES) {
					saveItem.fire();
				}
				else if (type == ButtonType.CANCEL) {
					cancelled = true;
				}
			}
			if (!cancelled) {
				FileChooser fileChooser = new FileChooser();
				ExtensionFilter selected = null;
				//File extensions
				for (String name : this.serializers.keySet ()) {
					ExtensionFilter filter = new ExtensionFilter(name, "*." + this.serializers.get(name).getFileExtension());
					fileChooser.getExtensionFilters().add(filter);
					if (data != null && data.getSerializer().getName().equals(name)) {
						selected = filter;
					}
				}
				if (selected != null) {
					fileChooser.setSelectedExtensionFilter(selected);
				}
				if (data != null) {
					fileChooser.setInitialDirectory(data.getFile().getParentFile());
				}
				//Select file
				File file = fileChooser.showOpenDialog(getScene().getWindow());
				if (file != null) {
					//Create serializer data
					ObjectSerializer<? extends TemporarySerializatizerData> serializer = this.serializers.get(fileChooser.getSelectedExtensionFilter().getDescription());
					TemporarySerializatizerData serializerData = DialogFactory.showCreateObjectDialog(serializer.getSerializationDataClass(), container, getScene().getWindow());
					DialogFactory.showObjectEditorDialog(serializerData, container, getScene().getWindow());
					//Deserialize
					try (InputStream in = new FileInputStream(file)) {
						object = serializer.deserializeUnsafe(in, serializerData);
						data = new TemporarySerializationData(serializer, file, serializerData);
						System.out.println(object);
						pane.setCenter(new ScrollPane(new ObjectEditorPane(PropertyEditor.getPropertiesStatic(object), container)));
						System.out.println(object);
						
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error while opening file");
						alert.setHeaderText("Couldn't open file " + file.getName() + "!");
						alert.setContentText(e1.toString());
						alert.showAndWait();
					} catch (IOException e1) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error while closing");
						alert.setHeaderText("Couldn't close file " + file.getName() + "!");
						alert.setContentText(e1.toString());
						alert.showAndWait();
						e1.printStackTrace();
					} catch (SerializationException e1) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error while deserializing");
						alert.setHeaderText("Couldn't deserialize file " + file.getName() + "!");
						alert.setContentText(e1.toString());
						alert.showAndWait();
						e1.printStackTrace();
					}
					
				}
			}
		});
		//Save entry
		saveItem.setOnAction(e -> {
			if (data == null) {
				saveAsItem.fire();
			}
			else {
				//Serialize
				try (OutputStream out = new FileOutputStream(data.getFile())) {
					data.getSerializer().serializeUnsafe(object, out, data.getData());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error while creating file");
					alert.setHeaderText("Couldn't create file " + data.getFile().getName() + "!");
					alert.setContentText(e1.toString());
					alert.showAndWait();
				} catch (IOException e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error while closing file");
					alert.setHeaderText("Couldn't close file " + data.getFile().getName() + "!");
					alert.setContentText(e1.toString());
					alert.showAndWait();
					e1.printStackTrace();
				} catch (SerializationException e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error while serializing");
					alert.setHeaderText("Couldn't serialize file " + data.getFile().getName() + "!");
					alert.setContentText(e1.toString());
					alert.showAndWait();
					e1.printStackTrace();
				}
			}
		});
		//Save as entry
		saveAsItem.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			ExtensionFilter selected = null;
			//File extensions
			for (String name : this.serializers.keySet ()) {
				ExtensionFilter filter = new ExtensionFilter(name, "*." + this.serializers.get(name).getFileExtension());
				fileChooser.getExtensionFilters().add(filter);
				if (data != null && data.getSerializer().getName().equals(name)) {
					selected = filter;
				}
			}
			if (selected != null) {
				fileChooser.setSelectedExtensionFilter(selected);
			}
			if (data != null) {
				fileChooser.setInitialDirectory(data.getFile().getParentFile());
			}
			//Select file
			File file = fileChooser.showSaveDialog(getScene().getWindow());
			if (file != null) {
				//Create serializer data
				ObjectSerializer<? extends TemporarySerializatizerData> serializer = this.serializers.get(fileChooser.getSelectedExtensionFilter().getDescription());
				TemporarySerializatizerData serializerData = DialogFactory.showCreateObjectDialog(serializer.getSerializationDataClass(), container, getScene().getWindow());
				DialogFactory.showObjectEditorDialog(serializerData, container, getScene().getWindow());
				//Serialize
				try (OutputStream out = new FileOutputStream(file)) {
					serializer.serializeUnsafe(object, out, serializerData);
					data = new TemporarySerializationData(serializer, file, serializerData);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error while creating file");
					alert.setHeaderText("Couldn't create file " + file.getName() + "!");
					alert.setContentText(e1.toString());
					alert.showAndWait();
				} catch (IOException e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error while closing file");
					alert.setHeaderText("Couldn't close file " + file.getName() + "!");
					alert.setContentText(e1.toString());
					alert.showAndWait();
					e1.printStackTrace();
				} catch (SerializationException e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error while serializing");
					alert.setHeaderText("Couldn't serialize file " + file.getName() + "!");
					alert.setContentText(e1.toString());
					alert.showAndWait();
					e1.printStackTrace();
				}
				
			}
		});
		
		//Print to console entry
		printItem.setOnAction(e -> System.out.println(object));
		
		getChildren().add(pane);
		setMinWidth(480);
		setMinHeight(640);
	}

}

class ObjectEditorPaneMenuSkin extends SkinBase<ObjectEditorPaneMenu> {

	protected ObjectEditorPaneMenuSkin(ObjectEditorPaneMenu menu) {
		super(menu);
	}

}