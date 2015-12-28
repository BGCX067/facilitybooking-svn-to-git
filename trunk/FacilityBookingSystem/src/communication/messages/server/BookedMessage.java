package communication.messages.server;

import communication.fieldwrappers.HumanReadableName;
import communication.utils.Id;

@HumanReadableName("All facilities")
public class BookedMessage extends ServerMessage {

	@HumanReadableName("Unique comfirmation Id")
	public Id confirmationId;
}
