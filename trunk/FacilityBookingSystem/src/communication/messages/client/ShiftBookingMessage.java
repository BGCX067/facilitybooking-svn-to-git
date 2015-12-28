package communication.messages.client;

import communication.fieldwrappers.HumanReadableName;
import communication.utils.DateTime;
import communication.utils.Id;

@HumanReadableName("Shift booking of a facility")
public class ShiftBookingMessage extends ClientMessage {
		
	@HumanReadableName("Unique comfirmation Id")
	public Id confirmationId;
	
	@HumanReadableName("Booking offset in [+/-min]")
	public Integer offset;
}
