package at.jojokobi.beaneditor.serialization;

import java.io.File;

public class TemporarySerializationData {

	private ObjectSerializer<?> serializer;
	private File file;
	private TemporarySerializatizerData data;
	
	public TemporarySerializationData(ObjectSerializer<?> serializer, File file, TemporarySerializatizerData data) {
		super();
		this.serializer = serializer;
		this.file = file;
		this.data = data;
	}

	public ObjectSerializer<?> getSerializer() {
		return serializer;
	}

	public File getFile() {
		return file;
	}

	public TemporarySerializatizerData getData() {
		return data;
	}
	
}
