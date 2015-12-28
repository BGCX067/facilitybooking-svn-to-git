package communication.messages;

import communication.fieldwrappers.HumanReadableName;

@HumanReadableName("Error message!")
public class ErrorMessage extends Message {

	@HumanReadableName("Error message")
	public String errorMessage;

	public static ErrorMessage createErrorMessage(String errorMessage){
		 ErrorMessage error = new ErrorMessage();
		 error.errorMessage = errorMessage;
		 return error;
	}
}
