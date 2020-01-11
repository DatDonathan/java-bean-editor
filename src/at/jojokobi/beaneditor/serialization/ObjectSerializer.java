package at.jojokobi.beaneditor.serialization;

import java.io.InputStream;
import java.io.OutputStream;

public interface ObjectSerializer<T extends TemporarySerializatizerData> {

	public String getFileExtension ();
	
	public Object deserialize (InputStream in, T data) throws SerializationException;
	
	public default Object deserializeUnsafe (InputStream in, TemporarySerializatizerData data) throws SerializationException {
		return deserialize(in, getSerializationDataClass().cast(data));
	}
	
	public void serialize (Object obj, OutputStream out, T data) throws SerializationException;
	
	public default void serializeUnsafe (Object obj, OutputStream out, TemporarySerializatizerData data) throws SerializationException {
		serialize(obj, out, getSerializationDataClass().cast(data));
	}
	
	public Class<T> getSerializationDataClass ();
	
	public String getName ();
	
}
