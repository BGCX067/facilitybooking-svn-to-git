/**
 * 
 */
package communication.fieldwrappers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

import communication.utils.Availability;
import communication.utils.DateTime;
import communication.utils.Id;


/**
 * @author Honzik
 * 
 */
public abstract class FieldWrapper {
	Field m_field;
	Object m_associatedObject;

	private static Hashtable<Class<?>, Class<?>> classVsFieldWrapperTable;
	static{
		classVsFieldWrapperTable = new Hashtable<Class<?>, Class<?>>();
		
		classVsFieldWrapperTable.put(String.class, StringFieldWrapper.class);
		classVsFieldWrapperTable.put(Integer.class, IntegerFieldWrapper.class);
		classVsFieldWrapperTable.put((new String[0]).getClass(), StringArrayFieldWrapper.class);
		classVsFieldWrapperTable.put(DateTime.class, DateTimeFieldWrapper.class);
		classVsFieldWrapperTable.put(Id.class, IdFieldWrapper.class);
		classVsFieldWrapperTable.put(new Availability[0].getClass(), AvailabilityArrayFieldWrapper.class);
	}
	
	public static FieldWrapper getFieldWrapperByClass(Field fieldToWrap, Object associatedObject) throws InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException{
		Class<?> fieldWrapperClass = classVsFieldWrapperTable.get(fieldToWrap.getType()); 
		
		FieldWrapper fieldWrapper =  (FieldWrapper) fieldWrapperClass.getConstructor(Field.class, Object.class).newInstance(fieldToWrap, associatedObject);//fieldWrapperClass.newInstance();
		
		 return fieldWrapper;
	}
	
	
	void initFieldWrapper(Field field, Object associatedObject) {
		// if(field == null || associatedObject == null) do something
		m_field = field;
		m_associatedObject = associatedObject;
	}

	public String getFieldName() {
		return m_field.getName();
	}

	public String getFieldHumanReadableName() {
		HumanReadableName humanReadableNameAnnotation = m_field.getAnnotation(HumanReadableName.class);
		if (humanReadableNameAnnotation == null) {
			return getFieldName();
		} else
			return humanReadableNameAnnotation.value();
	}

	public Object getFieldValue() throws IllegalArgumentException,
			IllegalAccessException {
		Object fieldValue =  m_field.get(m_associatedObject);

		return fieldValue;
	}

	public void setFieldValue(Object fieldValue) throws IllegalArgumentException,
			IllegalAccessException {
		m_field.set(m_associatedObject, fieldValue);
	}
	
	public abstract void setFieldValue(String value) throws IllegalArgumentException, IllegalAccessException;
	
	public abstract String getFieldValueAsString() throws IllegalArgumentException, IllegalAccessException;
	

	/**
	 * 
	 * @param serializedField
	 *            value of field in bytes
	 * @return the bytes not used during the deserialization
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public abstract byte[] deserialize(byte[] serializedField)
			throws IllegalArgumentException, IllegalAccessException;

	public abstract byte[] serialize() throws IllegalArgumentException,
			IllegalAccessException;

}
