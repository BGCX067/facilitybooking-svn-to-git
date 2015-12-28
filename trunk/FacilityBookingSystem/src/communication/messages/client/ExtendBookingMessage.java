package communication.messages.client;

import communication.fieldwrappers.HumanReadableName;
import communication.utils.Id;
@HumanReadableName("Extend a booking")
public class ExtendBookingMessage extends ClientMessage {

	@HumanReadableName("Unique comfirmation Id")
	public Id confirmationId;

	@HumanReadableName("Booking offset in [+/-min]")
	public Integer offset;
}
