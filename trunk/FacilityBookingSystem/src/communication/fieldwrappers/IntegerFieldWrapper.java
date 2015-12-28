package communication.fieldwrappers;

import java.lang.reflect.Field;

import communication.utils.ByteConversions;
import communication.utils.Utils;

public class IntegerFieldWrapper extends FieldWrapper {

	public IntegerFieldWrapper(Field field, Object associatedObject) {
		initFieldWrapper(field, associatedObject);
	}
	
	@Override
	public byte[] deserialize(byte[] serializedField)
			throws IllegalArgumentException, IllegalAccessException {
		byte[] fieldValueBytes = Utils.cutOutSubArray(0, ByteConversions.SizeOfIntegerInBytes, serializedField);
		
		Integer fieldValue = ByteConversions.byteArrayToInt(fieldValueBytes);
		setFieldValue(fieldValue);
		
		byte[] bytesToReturn = Utils.trimStartOfArray(fieldValueBytes, serializedField);
		
		return bytesToReturn;
	}

	@Override
	public byte[] serialize() throws IllegalArgumentException,
			IllegalAccessException {
		Integer fieldValue = (Integer) getFieldValue();
		byte[] bytesSerialized = ByteConversions.intToByteArray(fieldValue);
		
		return bytesSerialized;
	}

	@Override
	public void setFieldValue(String value) throws IllegalArgumentException, IllegalAccessException {
		int intValue = Integer.parseInt(value);
		setFieldValue((Object)intValue);
		
	}

	@Override
	public String getFieldValueAsString() throws IllegalArgumentException,
			IllegalAccessException {
		return getFieldValue().toString();
	}

	

}
