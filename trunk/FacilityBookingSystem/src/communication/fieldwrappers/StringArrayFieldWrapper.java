package communication.fieldwrappers;

import java.lang.reflect.Field;

import org.omg.CORBA.IntHolder;

import communication.utils.ByteConversions;
import communication.utils.Utils;

public class StringArrayFieldWrapper extends FieldWrapper {

	public StringArrayFieldWrapper(Field field, Object associatedObject) {
		initFieldWrapper(field, associatedObject);
	}

	@Override
	public byte[] deserialize(byte[] serializedField)
			throws IllegalArgumentException, IllegalAccessException {

		byte[] elementsCountInBytes = Utils.cutOutSubArray(0,
				ByteConversions.SizeOfIntegerInBytes, serializedField);
		int elementsCount = ByteConversions
				.byteArrayToInt(elementsCountInBytes);
		String[] fieldValue = new String[elementsCount];
		serializedField = Utils.trimStartOfArray(elementsCountInBytes,
				serializedField);

		for (int i = 0; i < fieldValue.length; i++) {
			IntHolder elementsToTrim = new IntHolder();
			String string = ByteConversions.byteArrayToString(serializedField,
					elementsToTrim);
			fieldValue[i] = string;
			serializedField = Utils.trimStartOfArray(elementsToTrim.value,
					serializedField);
		}

		setFieldValue(fieldValue);
		return serializedField;
	}

	@Override
	public byte[] serialize() throws IllegalArgumentException,
			IllegalAccessException {
		String[] fieldValue = (String[]) getFieldValue();

		byte[] serializedField = new byte[0];
		byte[] elementsCountInBytes = ByteConversions
				.intToByteArray(fieldValue.length);
		serializedField = Utils.mergeArrays(serializedField,
				elementsCountInBytes);

		for (String string : fieldValue) {
			byte[] stringInBytes = ByteConversions.stringToByteArray(string);
			serializedField = Utils.mergeArrays(serializedField, stringInBytes);
		}
		return serializedField;
	}

	public static final String StringElementDelimiter = System.getProperty("line.separator");
	@Override
	public void setFieldValue(String value) throws IllegalArgumentException,
			IllegalAccessException {
	
		String[] fieldValue = value.split(StringElementDelimiter);

		setFieldValue(fieldValue);
	}

	@Override
	public String getFieldValueAsString() throws IllegalArgumentException,
			IllegalAccessException {
		String fieldValueInString = "";
		String[] fieldValue = (String[]) getFieldValue();
		if (fieldValue != null) {
			for (String string : fieldValue) {
				fieldValueInString += string + StringElementDelimiter;
			}
			int lastIndexOfDelimiter = fieldValueInString.lastIndexOf(StringElementDelimiter);
			boolean trimTrailingDelimiter = (lastIndexOfDelimiter > 0);
			if(trimTrailingDelimiter){
				fieldValueInString = fieldValueInString.substring(0, lastIndexOfDelimiter);
			}
		} else {
			fieldValueInString = "";
		}
		return fieldValueInString;
	}

}
