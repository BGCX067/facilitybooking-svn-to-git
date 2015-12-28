package communication.messages.server;

import communication.fieldwrappers.HumanReadableName;
import communication.utils.Availability;

public class AvailabilitiesMessage extends ServerMessage{

	@HumanReadableName("Availabilities on the facility")
	public Availability[] availabilities;
}
