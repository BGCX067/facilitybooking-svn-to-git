package communication.fieldwrappers;

import java.lang.reflect.Field;

import communication.utils.Id;
import communication.utils.Utils;

public class IdFieldWrapper extends FieldWrapper {

	public IdFieldWrapper(Field field, Object associatedObject) {
		initFieldWrapper(field, associatedObject);
	}
	
	@Override
	public byte[] deserialize(byte[] serializedField)
			throws IllegalArgumentException, IllegalAccessException {
		int sizeOfInBytes = Id.getSizeOfIdInBytes();
		byte[] idInBytes = Utils.cutOutSubArray(0, sizeOfInBytes, serializedField);
		
		Id deserializedId = Id.deserialize(idInBytes);
		setFieldValue(deserializedId);
		
		serializedField = Utils.trimStartOfArray(idInBytes, serializedField);
		return serializedField;
	}

	@Override
	public String getFieldValueAsString() throws IllegalArgumentException,
			IllegalAccessException {
		Id fieldValue = (Id) getFieldValue();
		
		return fieldValue.toString();
	}

	@Override
	public byte[] serialize() throws IllegalArgumentException,
			IllegalAccessException {
		Id idToSerialize = (Id) getFieldValue();
		return idToSerialize.serialize();
	}

	@Override
	public void setFieldValue(String value) throws IllegalArgumentException,
			IllegalAccessException {
		Id fieldValue = new Id(value);
		setFieldValue(fieldValue);
	}

}
