package communication.messages.server;

import communication.fieldwrappers.HumanReadableName;
@HumanReadableName("Facility availability has changed")
public class FacilityChangedMessage extends ServerMessage {
	
	@HumanReadableName("Facility name")
	public String facilityName;
}
