package communication.messages;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.omg.CORBA.IntHolder;

import communication.fieldwrappers.FieldWrapper;
import communication.fieldwrappers.HumanReadableName;
import communication.utils.ByteConversions;
import communication.utils.Id;
import communication.utils.Utils;

/**
 * @author jan.vratislav@gmail.com This class serves as a general container for
 *         a message send from client and server. It utilizes reflection to
 *         generate messages.
 * 
 */
public abstract class Message {
	private FieldWrapper[] m_fieldWrappers;
	private Id m_messageId;
	
	public Message() {
		try {
			initMessage();
		} catch (Exception e) {
			//wrapping up all exception into Runtime exceptions because it is unchecked
			//checked exceptions cause problem when trying to invoke the constructor by reflection
			throw new RuntimeException(e);
		}
	}
	
	public static String getMessageHumanReadableName(Class<?> messageClass){
		HumanReadableName humanReadableName = messageClass.getAnnotation(HumanReadableName.class);
		if(humanReadableName != null){
			return humanReadableName.value();
		}else{
			return "";
		}
	}

	protected void initMessage() throws IllegalArgumentException, SecurityException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Field[] declaredFields = getClass().getDeclaredFields();
		
		m_messageId = Id.getUniqueId();
		m_fieldWrappers = new FieldWrapper[declaredFields.length];
		
		for (int fieldIndex = 0; fieldIndex < declaredFields.length; fieldIndex++) {
			Field declaredField = declaredFields[fieldIndex];
			m_fieldWrappers[fieldIndex] = FieldWrapper.getFieldWrapperByClass(
					declaredField, this);
		}
	}
	
	public Id getMessageId(){
		return m_messageId;
	}
	
	public void setMessageId(Id messageId){
		m_messageId = messageId;
	}

	public static Message deserialize(byte[] serializedMessage) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		//message class
		IntHolder elementsToTrim = new IntHolder();
		String messageClassName =  ByteConversions.byteArrayToString(serializedMessage, elementsToTrim);;
				
		serializedMessage = Utils.trimStartOfArray(elementsToTrim.value, serializedMessage);
		
		Message message = (Message) Class.forName(messageClassName).newInstance();
		
		//message id
		byte[] messageIdInBytes = Utils.cutOutSubArray(0, Id.getSizeOfIdInBytes(), serializedMessage);
		Id messageId = Id.deserialize(messageIdInBytes);
		message.m_messageId = messageId;
		serializedMessage = Utils.trimStartOfArray(messageIdInBytes, serializedMessage);
		
		//message fields
		for (FieldWrapper fieldWrapper : message.getFieldWrappers()) {
			serializedMessage= fieldWrapper.deserialize(serializedMessage);
		}		
		
		return message;
	}

	public  byte[] serialize() throws IllegalArgumentException, IllegalAccessException{
		byte[] serializedMessage =new  byte[0];
		
		//message class
		String messageClassName = getClass().getName();
		byte[] messageClassNameInBytes = ByteConversions.stringToByteArray(messageClassName);
		serializedMessage = Utils.mergeArrays(serializedMessage, messageClassNameInBytes);
		
		//message id
		byte[] messageIdInBytes = m_messageId.serialize();
		serializedMessage = Utils.mergeArrays(serializedMessage, messageIdInBytes);
		
		//message fields
		for (FieldWrapper fieldWrapper : m_fieldWrappers) {
			byte[] serializedField = fieldWrapper.serialize();
			serializedMessage = Utils.mergeArrays(serializedMessage, serializedField);			
		}
		
		return serializedMessage;
	}

	public FieldWrapper[] getFieldWrappers() {
		return m_fieldWrappers;
	}

}
