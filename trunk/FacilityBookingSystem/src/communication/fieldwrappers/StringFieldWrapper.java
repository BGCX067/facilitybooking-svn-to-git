package communication.fieldwrappers;

import java.lang.reflect.Field;

import org.omg.CORBA.IntHolder;

import communication.utils.ByteConversions;
import communication.utils.Utils;

public class StringFieldWrapper extends FieldWrapper {

	public StringFieldWrapper(Field field, Object associatedObject) {
		initFieldWrapper(field, associatedObject);
	}

	
	@Override
	public byte[] deserialize(byte[] serializedField) throws IllegalArgumentException, IllegalAccessException {
		IntHolder elementsToTrim = new IntHolder();
		String fieldValue =	ByteConversions.byteArrayToString(serializedField, elementsToTrim);
		setFieldValue(fieldValue);
		
		byte[] bytesToReturn = Utils.trimStartOfArray(elementsToTrim.value, serializedField);
		
		return bytesToReturn;
	}

	@Override
	public byte[] serialize() throws IllegalArgumentException,
			IllegalAccessException {
		String fieldValue = (String) getFieldValue();
		
		return ByteConversions.stringToByteArray(fieldValue);
	}
	
	@Override
	public void setFieldValue(String value) throws IllegalArgumentException, IllegalAccessException {
			setFieldValue((Object)value);	
	}


	@Override
	public String getFieldValueAsString() throws IllegalArgumentException,
			IllegalAccessException {
		return (String) getFieldValue();
	}
	

}
