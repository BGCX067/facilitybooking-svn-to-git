package communication.messages.client;

import communication.fieldwrappers.HumanReadableName;
import communication.utils.DateTime;

@HumanReadableName("Query availibility of a facility")
public class QueryAvailibilityMessage extends ClientMessage {
	@HumanReadableName("Facility name")
	public String facilityName;
	
	@HumanReadableName("Day")
	public DateTime day;
	
}
