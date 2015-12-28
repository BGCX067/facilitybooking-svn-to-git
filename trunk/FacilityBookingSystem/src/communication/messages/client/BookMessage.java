package communication.messages.client;

import communication.fieldwrappers.HumanReadableName;
import communication.utils.DateTime;

@HumanReadableName("Book facility")
public class BookMessage extends ClientMessage {
	@HumanReadableName("Facility name")
	public String facilityName;
	
	@HumanReadableName("Start time")
	public DateTime startTime;
	
	@HumanReadableName("End time")
	public DateTime endTime;
}
