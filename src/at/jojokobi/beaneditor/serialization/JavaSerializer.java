package at.jojokobi.beaneditor.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class JavaSerializer implements ObjectSerializer<EmptySerializerData>{

	@Override
	public String getFileExtension() {
		return "dat";
	}

	@Override
	public Object deserialize(InputStream in, EmptySerializerData data) throws SerializationException {
		Object obj = null;
		try (ObjectInputStream oin = new ObjectInputStream(in)) {
			obj = oin.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new SerializationException(e);
		}
		return obj;
	}

	@Override
	public void serialize(Object obj, OutputStream out, EmptySerializerData data) throws SerializationException{
		try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
			oos.writeObject(obj);
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public Class<EmptySerializerData> getSerializationDataClass() {
		return EmptySerializerData.class;
	}

	@Override
	public String getName() {
		return "Java Serializable File";
	}
	
}
