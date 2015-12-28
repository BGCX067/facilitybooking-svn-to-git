package servr.dispatchers;

import servr.ServerMain;
import servr.facilitybooking.Booking;
import servr.facilitybooking.Facility;
import servr.facilitybooking.FacilityManager;
import communication.messages.client.GetAllBookingMessage;
import communication.messages.server.AllBookingMessage;

public class GetAllBookingMessageDispatcher extends ServerDispatcher {

	@Override
	public void dispatch() {
		ServerMain.printOnConsole("GetAllBookingMessageDispatcher");
		GetAllBookingMessage message = (GetAllBookingMessage) getReceivedMessage();
		String facilityName = message.facilityName;
		
		FacilityManager fc = FacilityManager.getFacilityManager();
		
		Facility facility = fc.getFacilityByName(facilityName);
		
		if(facility == null){
			String errorText = "Facility " + facilityName + " does not exists." ;
			sendErrorMessage(errorText);
			return;
		}
		
		Booking[] bookings = facility.getAllBookings();
		
		String[] bookingsAsString = new String[bookings.length];
		
		for (int booking = 0; booking < bookingsAsString.length; booking++) {
			String tmpBookingAsString = bookings[booking].toString();
			bookingsAsString[booking] = tmpBookingAsString;
		}
		
		AllBookingMessage replyMessage = new AllBookingMessage();
		replyMessage.allBookings = bookingsAsString;
		
		sendReplyMessage(replyMessage);
	}

}
