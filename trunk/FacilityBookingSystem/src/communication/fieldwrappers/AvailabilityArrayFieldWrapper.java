package communication.fieldwrappers;

import java.lang.reflect.Field;


import communication.utils.Availability;
import communication.utils.ByteConversions;
import communication.utils.Utils;

public class AvailabilityArrayFieldWrapper extends FieldWrapper {
	
	public AvailabilityArrayFieldWrapper(Field field, Object associatedObject) {
		initFieldWrapper(field, associatedObject);
	}

	@Override
	public byte[] deserialize(byte[] serializedField)
			throws IllegalArgumentException, IllegalAccessException {

		byte[] elementsCountInBytes = Utils.cutOutSubArray(0,ByteConversions.SizeOfIntegerInBytes, serializedField);
		int elementsCount = ByteConversions.byteArrayToInt(elementsCountInBytes);
		Availability[] fieldValue = new Availability[elementsCount];
		serializedField = Utils.trimStartOfArray(elementsCountInBytes, serializedField);

		for (int i = 0; i < fieldValue.length; i++) {
			byte[] elementAsBytes = Utils.cutOutSubArray(0, Availability.getSizeOfAvailabilityInBytes(), serializedField); 
			Availability element = Availability.deserialize(elementAsBytes);
			fieldValue[i] = element;
			serializedField = Utils.trimStartOfArray(elementAsBytes,serializedField);
		}

		setFieldValue(fieldValue);
		return serializedField;
	}

	@Override
	public String getFieldValueAsString() throws IllegalArgumentException,
			IllegalAccessException {
		String fieldValueInString = "";
		Availability[] fieldValue = (Availability[]) getFieldValue();
		if (fieldValue != null) {
			for (Availability availibility : fieldValue) {
				fieldValueInString += availibility.toString() + StringArrayFieldWrapper.StringElementDelimiter;
			}
			int lastIndexOfDelimiter = fieldValueInString.lastIndexOf(StringArrayFieldWrapper.StringElementDelimiter);
			fieldValueInString = fieldValueInString.substring(0, lastIndexOfDelimiter);
		} else {
			fieldValueInString = "";
		}
		return fieldValueInString;
	}

	@Override
	public byte[] serialize() throws IllegalArgumentException,
			IllegalAccessException {
		Availability[] fieldValue = (Availability[]) getFieldValue();

		byte[] serializedField = new byte[0];
		byte[] elementsCountInBytes = ByteConversions
				.intToByteArray(fieldValue.length);
		serializedField = Utils.mergeArrays(serializedField,
				elementsCountInBytes);

		for (Availability element : fieldValue) {
			byte[] elementInBytes = element.serialize();
			serializedField = Utils
					.mergeArrays(serializedField, elementInBytes);
		}

		return serializedField;
	}

	@Override
	public void setFieldValue(String value) throws IllegalArgumentException,
			IllegalAccessException {
		;
	}

}
