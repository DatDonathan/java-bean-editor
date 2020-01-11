package at.jojokobi.beaneditor.gui;

import java.util.ArrayList;
import java.util.List;

import at.jojokobi.beaneditor.Argument;
import at.jojokobi.beaneditor.ClassUtils;
import at.jojokobi.beaneditor.InstanceSupplier;
import at.jojokobi.beaneditor.Property;
import at.jojokobi.beaneditor.PropertyEditor;
import at.jojokobi.beaneditor.SerializableClassHandler;
import at.jojokobi.beaneditor.SimpleProperty;
import at.jojokobi.beaneditor.converters.ConverterContainer;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DialogFactory {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T showCreateObjectDialog (Class<T> clazz, ConverterContainer container, Window window) {
		List<Class<? extends T>> options = SerializableClassHandler.getInstance().getPossibleClasses(clazz);
		options.sort(ClassUtils.CLASS_COMPARATOR);
		Class<? extends T> selected = null;
		if (options.size() == 1) {
			selected = options.get(0);
		}
		else {
			selected = new ChoiceDialog<>(options.size() > 0 ? options.get(0) : null, options).showAndWait().orElseGet(() -> null);
		}
		InstanceSupplier<? extends T> supplier = null;
		if (selected != null) {
			List<InstanceSupplier<? extends T>> suppliers = new ArrayList<> (SerializableClassHandler.getInstance().getSuppliers(selected));
			if (suppliers.size () == 1) {
				supplier = suppliers.get(0);
			}
			else {
				supplier = new ChoiceDialog<>(suppliers.size() > 0 ? suppliers.get(0) : null, suppliers).showAndWait().orElseGet(() -> null);
			}
		}
		T t = null;
		if (supplier != null) {
			Object[] objects = new Object[0];
			if (supplier.getArguments().size() > 0) {
				//Constructor Editor
				List<Property<?>> props = new ArrayList<>();
				for (Argument<?> arg : supplier.getArguments()) {
					props.add(new SimpleProperty(arg.getName(), arg.getValueClass(), null, arg.getGenericParams()));
				}
				
				Stage stage = new Stage();
				stage.initOwner(window);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setTitle ("Insert Constructor Values");
				stage.setScene(new Scene(new ObjectEditorPane(props, container)));
				stage.showAndWait ();
				
				objects = new Object[props.size()];
				int i = 0;
				for (Property<?> prop : props) {
					objects[i++] = prop.getValue();
				}
			}
			t = supplier.create(objects);
		}
		return t;
	}
	
	public static void showObjectEditorDialog (Object object, ConverterContainer container, Window window) {
		List<Property<?>> properties = PropertyEditor.getPropertiesStatic(object);
		if (!properties.isEmpty ()) {
			Stage stage = new Stage();
			stage.initOwner(window);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Edit " + object.getClass().getSimpleName());
			stage.setScene(new Scene(new ObjectEditorPane (PropertyEditor.getPropertiesStatic(object), container)));
			
			stage.showAndWait();
		}
	}

}
