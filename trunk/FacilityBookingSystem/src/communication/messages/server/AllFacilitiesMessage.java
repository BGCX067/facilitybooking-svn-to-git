package communication.messages.server;

import communication.fieldwrappers.HumanReadableName;

@HumanReadableName("All facilities")
public class AllFacilitiesMessage extends ServerMessage {

//	@HumanReadableName("All facilities")
	public String[] allFacilities;
}
