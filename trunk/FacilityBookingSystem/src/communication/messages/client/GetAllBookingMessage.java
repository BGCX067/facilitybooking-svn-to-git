package communication.messages.client;

import communication.fieldwrappers.HumanReadableName;

@HumanReadableName("List all booking of a facility")
public class GetAllBookingMessage extends ClientMessage {

	@HumanReadableName("Facility name")
	public String facilityName;
}
