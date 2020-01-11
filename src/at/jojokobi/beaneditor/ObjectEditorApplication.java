package at.jojokobi.beaneditor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import at.jojokobi.beaneditor.gui.ObjectEditorPaneMenu;
import at.jojokobi.beaneditor.plugins.ObjectEditorPlugin;
import at.jojokobi.beaneditor.serialization.EmptySerializerData;
import at.jojokobi.beaneditor.serialization.JavaSerializer;
import at.jojokobi.beaneditor.serialization.ObjectSerializer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ObjectEditorApplication extends Application {

	public ObjectEditorApplication() {
		
	}
	
	public static void main (String... args) {
		launch(args);
	}

	@Override
	public void start(Stage stage)  {
		stage.setTitle("Cool Bean Editor");
		
		List<Class<?>> classes = new ArrayList<>();
		classes.add(EmptySerializerData.class);
		List<ObjectSerializer<?>> serializers = new ArrayList<>();
		serializers.add(new JavaSerializer());
		//Load jars
		File pluginsFolder = new File("./addons");
		pluginsFolder.mkdirs();
		List<String> plugins = new ArrayList<>();
		for (File file : pluginsFolder.listFiles()) {
			if (file.isFile() && file.getName().endsWith(".jar")) {
				CustomAgent.addToClasspath(file);
				URL url;
				try {
					url = new URL("jar", "", file.toURI().toURL() + "!/plugin.txt");
					try (Scanner scan = new Scanner(url.openStream())) {
						if (scan.hasNextLine()) {
							plugins.add(scan.nextLine ());
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
		//Load plugins
		for (String name: plugins) {
			try {
				Class<?> main = Class.forName(name);
				if (ObjectEditorPlugin.class.isAssignableFrom(main)) {
					ObjectEditorPlugin plugin = (ObjectEditorPlugin) main.getConstructor().newInstance();
					plugin.init ();
					classes.addAll(plugin.getSerializableClasses());
					serializers.addAll(plugin.getObjectSerializers());
					plugin.enable ();
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				System.err.println("Couldn't load plugin " + name + ":");
				e.printStackTrace();
			}
		}
		
		for (Class<?> clazz : classes) {
			for (InstanceSupplier<?> supplier : ReflectiveInstanceSupplier.createInstaceSuppliers(clazz)) {
				SerializableClassHandler.getInstance().addClass(supplier);
			}
		}
	
		stage.setScene(new Scene(new ObjectEditorPaneMenu(serializers.toArray(ObjectSerializer<?>[]::new))));
		stage.show();
	}

}
