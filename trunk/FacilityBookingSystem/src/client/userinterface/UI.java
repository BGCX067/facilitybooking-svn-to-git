package client.userinterface;

import communication.fieldwrappers.FieldWrapper;
import communication.messages.Message;
import communication.messages.client.*;

public class UI {
	static Class<?>[] ms_messages = new Class<?>[]{ 
		QueryAvailibilityMessage.class,	
		BookMessage.class,
		ShiftBookingMessage.class,
		MonitorFacilityMessage.class,
		ExtendBookingMessage.class,
		GetAllFacilitiesMessage.class,
		GetAllBookingMessage.class,
		ShutDownServerMessage.class,
		EndClientMessage.class};
	
	
	public static  void printMenu(){
		printDelimiter();
		System.out.println("Choose an action:");
		
		int menuItemConter = 0;
		for (Class<?> messageClass : ms_messages) {
			String classHumanReadableName = Message.getMessageHumanReadableName(messageClass);
			System.out.println(menuItemConter++ + ") " + classHumanReadableName);
		}
	}
	
	public static ClientMessage getUsersChoice() throws IllegalArgumentException, IllegalAccessException, InstantiationException{
		printDelimiter();
		System.out.print("Choose what to do: ");
		
		String choice = System.console().readLine();
		int messageIndex = Integer.parseInt(choice);
		
		Class<?> messageClass = ms_messages[messageIndex];
		ClientMessage message = (ClientMessage) messageClass.newInstance();
		
		FieldWrapper[] fieldWrappers = message.getFieldWrappers();
		for (FieldWrapper fieldWrapper : fieldWrappers) {
			String fieldName = fieldWrapper.getFieldHumanReadableName();
			System.out.print(fieldName + ": ");
			String fieldValue = System.console().readLine();
			fieldWrapper.setFieldValue(fieldValue);
		}
		System.out.println("------------------------------------------");
		return message;
	}
	
	public static void printMessage(Message message) throws IllegalArgumentException, IllegalAccessException{
		printDelimiter();
		String classHumanReadableName = Message.getMessageHumanReadableName(message.getClass());
		System.out.println(classHumanReadableName);
		
		FieldWrapper[] fw = message.getFieldWrappers();
		for (FieldWrapper fieldWrapper : fw) {
			System.out.println(fieldWrapper.getFieldHumanReadableName()+": "+ fieldWrapper.getFieldValueAsString());
		}
		
		printDelimiter();
	}
	
	public static void printString(String stringToPrint){
		printDelimiter();
		System.out.println(stringToPrint);
		printDelimiter();
	}
	
	 public static void printDelimiter(){
		System.out.println("------------------------------------------");
	}
	
}
