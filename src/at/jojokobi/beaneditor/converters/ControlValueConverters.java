package at.jojokobi.beaneditor.converters;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import at.jojokobi.beaneditor.ClassUtils;
import at.jojokobi.beaneditor.SerializableClassHandler;
import at.jojokobi.beaneditor.gui.ListEditor;
import at.jojokobi.beaneditor.gui.ObjectEditorButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public final class ControlValueConverters {
	
	public static final ControlValueConverter<String, TextField> STRING_CONTROL_VALUE_CONVERTER = new ControlValueConverter<String, TextField>() {
		
		@Override
		public Class<TextField> getControlClass() {
			return TextField.class;
		}
		
		@Override
		public TextField createControl(Consumer<String> callback, List<Class<?>> genericParams, ConverterContainer container) {
			TextField field = new TextField();
			field.textProperty().addListener((o, oV, nV) -> callback.accept(nV));
			return field;
		}
		
		@Override
		public String convert(TextField c) {
			return c.getText();
		}

		public String createStandardValue() {
			return "";
		}

		@Override
		public void setValue(String t, TextField c) {
			c.setText(t);
		}

		@Override
		public Class<String> getValueClass() {
			return String.class;
		}
	};
	
	@SuppressWarnings("rawtypes")
	public static final ControlValueConverter<Integer, Spinner> INTEGER_CONTROL_VALUE_CONVERTER = new ControlValueConverter<Integer,Spinner>() {
		
		@Override
		public Class<Spinner> getControlClass() {
			return Spinner.class;
		}
		
		@Override
		public Spinner<Integer> createControl(Consumer<Integer> callback, List<Class<?>> genericParams, ConverterContainer container) {
			Spinner<Integer> spinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE, 0));
			spinner.valueProperty().addListener((o, oV, nV) -> callback.accept(nV));
			spinner.setEditable(true);
			return spinner;
		}
		
		@Override
		public Integer convert(Spinner c) {
			return (Integer) c.getValue();
		}

		public Integer createStandardValue() {
			return 0;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setValue(Integer t, Spinner c) {
			c.getValueFactory().setValue(t == null ? 0 : t);
		}

		@Override
		public Class<Integer> getValueClass() {
			return Integer.class;
		}
	};
	
	@SuppressWarnings("rawtypes")
	public static final ControlValueConverter<Double, Spinner> DOUBLE_CONTROL_VALUE_CONVERTER = new ControlValueConverter<Double,Spinner>() {
		
		@Override
		public Class<Spinner> getControlClass() {
			return Spinner.class;
		}
		
		@Override
		public Spinner<Double> createControl(Consumer<Double> callback, List<Class<?>> genericParams, ConverterContainer container) {
			Spinner<Double> spinner = new Spinner<Double>(new SpinnerValueFactory.DoubleSpinnerValueFactory(Double.MIN_VALUE, Double.MAX_VALUE, 0));
			spinner.valueProperty().addListener((o, oV, nV) -> callback.accept(nV));
			spinner.setEditable(true);
			return spinner;
		}
		
		@Override
		public Double convert(Spinner c) {
			return (Double) c.getValue();
		}
		
		public Double createStandardValue() {
			return 0.0;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setValue(Double t, Spinner c) {
			c.getValueFactory().setValue(t == null ? 0 : t);
		}

		@Override
		public Class<Double> getValueClass() {
			return Double.class;
		}
	};
	
	@SuppressWarnings("rawtypes")
	public static final ControlValueConverter<Byte, Spinner> BYTE_CONTROL_VALUE_CONVERTER = new ControlValueConverter<Byte,Spinner>() {
		
		@Override
		public Class<Spinner> getControlClass() {
			return Spinner.class;
		}
		
		@Override
		public Spinner<Integer> createControl(Consumer<Byte> callback, List<Class<?>> genericParams, ConverterContainer container) {
			Spinner<Integer> spinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(Byte.MIN_VALUE, Byte.MAX_VALUE, 0));
			spinner.valueProperty().addListener((o, oV, nV) -> callback.accept(nV.byteValue()));
			spinner.setEditable(true);
			return spinner;
		}
		
		@Override
		public Byte convert(Spinner c) {
			return ((Integer) c.getValue()).byteValue();
		}

		public Byte createStandardValue() {
			return 0;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setValue(Byte t, Spinner c) {
			c.getValueFactory().setValue(t == null ? 0 : t.intValue());
		}

		@Override
		public Class<Byte> getValueClass() {
			return Byte.class;
		}
	};
	
	@SuppressWarnings("rawtypes")
	public static final ControlValueConverter<Float, Spinner> FLOAT_CONTROL_VALUE_CONVERTER = new ControlValueConverter<Float,Spinner>() {
		
		@Override
		public Class<Spinner> getControlClass() {
			return Spinner.class;
		}
		
		@Override
		public Spinner<Double> createControl(Consumer<Float> callback, List<Class<?>> genericParams, ConverterContainer container) {
			Spinner<Double> spinner = new Spinner<>(new SpinnerValueFactory.DoubleSpinnerValueFactory (Float.MIN_VALUE, Float.MAX_VALUE, 0.0f));
			spinner.valueProperty().addListener((o, oV, nV) -> callback.accept(nV.floatValue()));
			spinner.setEditable(true);
			return spinner;
		}
		
		@Override
		public Float convert(Spinner c) {
			return ((Double) c.getValue()).floatValue();
		}

		public Float createStandardValue() {
			return 0.0f;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setValue(Float t, Spinner c) {
			c.getValueFactory().setValue(t == null ? 0 : t.doubleValue());
		}

		@Override
		public Class<Float> getValueClass() {
			return Float.class;
		}
	};
	
	@SuppressWarnings("rawtypes")
	public static final ControlValueConverter<List, ListEditor> LIST_CONTROL_VALUE_CONVERTER = new ControlValueConverter<List, ListEditor>() {
		
		@Override
		public Class<ListEditor> getControlClass() {
			return ListEditor.class;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public ListEditor createControl(Consumer<List> valueChanged, List<Class<?>> genericParams, ConverterContainer container) {
			Class<?> type = Object.class;
			if (genericParams.size() > 0) {
				type = genericParams.get(0);
			}
			return new ListEditor(type, container, valueChanged);
		}
		
		@Override
		public List<?> convert(ListEditor c) {
			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setValue(List t, ListEditor c) {
			if (t != null) {
				c.setList(new ArrayList<>(t));
			}
			else {
				c.setList(new ArrayList<>());
			}
			
		}

		@Override
		public Class<List> getValueClass() {
			return List.class;
		}
		
		public List createStandardValue() {
			return new ArrayList<>();
		};
	};
	
	public static final ControlValueConverter<Boolean, CheckBox> BOOLEAN_CONTROL_VALUE_CONVERTER = new ControlValueConverter<Boolean, CheckBox>() {
		
		@Override
		public void setValue(Boolean t, CheckBox c) {
			c.setSelected(t != null ? t : false);
		}
		
		@Override
		public Class<Boolean> getValueClass() {
			return Boolean.class;
		}
		
		@Override
		public Class<CheckBox> getControlClass() {
			return CheckBox.class;
		}
		
		@Override
		public CheckBox createControl(Consumer<Boolean> valueChanged, List<Class<?>> genericParams,
				ConverterContainer container) {
			CheckBox box = new CheckBox();
			box.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> o, Boolean oV, Boolean nV) {
					valueChanged.accept(nV);
					System.out.println("Changed");
				}
			});
			return box;
		}
		
		@Override
		public Boolean convert(CheckBox c) {
			return c.isSelected();
		}
		
		public Boolean createStandardValue() {
			return false;
		};
		
	};

	private ControlValueConverters() {
		
	}
	
	@SuppressWarnings("rawtypes")
	public static <T> ControlValueConverter<T, ObjectEditorButton> newObjectControlValueConverter (Class<T> clazz) {
		return new ControlValueConverter<T, ObjectEditorButton>() {

			@Override
			public ObjectEditorButton<T> createControl(Consumer<T> valueChanged, List<Class<?>> genericParams,
					ConverterContainer container) {
				ObjectEditorButton<T> button = new ObjectEditorButton<>(clazz, container, valueChanged);
				return button;
			}

			@SuppressWarnings("unchecked")
			@Override
			public T convert(ObjectEditorButton c) {
				return (T) c.getObject();
			}

			@Override
			public Class<ObjectEditorButton> getControlClass() {
				return ObjectEditorButton.class;
			}

			@Override
			public Class<T> getValueClass() {
				return clazz;
			}

			@SuppressWarnings("unchecked")
			@Override
			public void setValue(T t, ObjectEditorButton c) {
				c.setObject(t);
			}
			
		};
	}
	
	@SuppressWarnings("rawtypes")
	public static <T> ControlValueConverter<T, ChoiceBox> newEnumControlValueConverter (Class<T> clazz) {
		return new ControlValueConverter<T, ChoiceBox>() {

			@Override
			public ChoiceBox<T> createControl(Consumer<T> valueChanged, List<Class<?>> genericParams,
					ConverterContainer container) {
				ChoiceBox<T> box = new ChoiceBox<>();
				if (clazz.getEnumConstants() != null) {
					for (T t : clazz.getEnumConstants()) {
						box.getItems().add(t);
					}
				}
				box.getSelectionModel().selectFirst();
				box.getSelectionModel().selectedItemProperty().addListener((o, oV, nV) -> valueChanged.accept(nV));
				return box;
			}

			@Override
			public T convert(ChoiceBox c) {
				return clazz.cast(c.getValue());
			}

			@Override
			public Class<ChoiceBox> getControlClass() {
				return ChoiceBox.class;
			}

			@Override
			public Class<T> getValueClass() {
				return clazz;
			}

			@SuppressWarnings("unchecked")
			@Override
			public void setValue(T t, ChoiceBox c) {
				System.out.println("Set value " + t);
				c.setValue(t);
				System.out.println(c.getValue());
			}
			
//			@Override
//			public T createStandardValue() {
//				return clazz.getEnumConstants().length > 0 ? clazz.getEnumConstants()[0] : null;
//			}
		};
	}
	
	@SuppressWarnings("rawtypes")
	public static final ControlValueConverter<Class, ChoiceBox> CLASS_CONTROL_VALUE_CONVERTER = new ControlValueConverter<Class, ChoiceBox>() {

			@Override
			public ChoiceBox<Class<?>> createControl(Consumer<Class> valueChanged, List<Class<?>> genericParams,
					ConverterContainer container) {
				Class<?> clazz = Object.class;
				if (genericParams.size() > 0) {
					clazz = genericParams.get(0);
				}
				ChoiceBox<Class<?>> box = new ChoiceBox<>();
				List<Class<?>> classes = new ArrayList<>(SerializableClassHandler.getInstance().getPossibleClasses(clazz));
				classes.sort(ClassUtils.CLASS_COMPARATOR);
				
				for (Class<?> c : classes) {
					box.getItems().add(c);
				}
				
				box.getSelectionModel().selectFirst();
				box.getSelectionModel().selectedItemProperty().addListener((o, oV, nV) -> valueChanged.accept(nV));

				return box;
			}

			@Override
			public Class convert(ChoiceBox c) {
				return (Class<?>) c.getSelectionModel().getSelectedItem();
			}

			@Override
			public Class<ChoiceBox> getControlClass() {
				return ChoiceBox.class;
			}

			@Override
			public Class<Class> getValueClass() {
				return Class.class;
			}

			@SuppressWarnings("unchecked")
			@Override
			public void setValue(Class clazz, ChoiceBox c) {
				c.getSelectionModel().select(clazz);
			}
		};

}
