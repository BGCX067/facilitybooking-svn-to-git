package communication.messages.server;

import communication.fieldwrappers.HumanReadableName;

@HumanReadableName("Booking for a facility")
public class AllBookingMessage extends ServerMessage {
		
	public String[] allBookings;
}
