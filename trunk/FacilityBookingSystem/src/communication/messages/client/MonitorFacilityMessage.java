package communication.messages.client;

import communication.fieldwrappers.HumanReadableName;
import communication.utils.DateTime;

@HumanReadableName("Monitor availibility of a facility")
public class MonitorFacilityMessage extends ClientMessage {

	@HumanReadableName("Monitoring interval")
	public Integer interval;
	
	@HumanReadableName("Facility name")
	public String facilityName;
}
