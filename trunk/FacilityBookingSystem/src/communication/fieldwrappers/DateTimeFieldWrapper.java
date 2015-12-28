package communication.fieldwrappers;

import java.lang.reflect.Field;

import communication.utils.ByteConversions;
import communication.utils.DateTime;
import communication.utils.Utils;

public class DateTimeFieldWrapper extends FieldWrapper {

	public DateTimeFieldWrapper(Field field, Object associatedObject) {
		initFieldWrapper(field, associatedObject);
	}

	@Override
	public byte[] deserialize(byte[] serializedField)
			throws IllegalArgumentException, IllegalAccessException {
		byte[] fieldValueInBytes = Utils.cutOutSubArray(0, ByteConversions.SizeOfDateTimeInBytes, serializedField);
		DateTime fieldValue = ByteConversions.byteArrayToDateTime(fieldValueInBytes);
		
		setFieldValue(fieldValue);
		
		serializedField = Utils.trimStartOfArray(fieldValueInBytes, serializedField);
		return serializedField;
	}

	@Override
	public byte[] serialize() throws IllegalArgumentException,
			IllegalAccessException {
		DateTime fieldValue = (DateTime) getFieldValue();
		byte[] fieldValueInBytes = ByteConversions.dateTimeToByteArray(fieldValue);
		
		return fieldValueInBytes;
	}

	
	@Override
	public void setFieldValue(String value) throws IllegalArgumentException,
			IllegalAccessException {
		DateTime fieldValue = new DateTime(value);
		setFieldValue(fieldValue);
	}

	@Override
	public String getFieldValueAsString() throws IllegalArgumentException,
			IllegalAccessException {
		return getFieldValue().toString();
	}

}
