package servr.dispatchers;

import servr.ServerMain;
import servr.facilitybooking.Booking;
import servr.facilitybooking.Facility;
import servr.facilitybooking.FacilityManager;
import servr.facilitybooking.ServerException;
import communication.messages.client.BookMessage;
import communication.messages.server.BookedMessage;
import communication.utils.DateTime;

public class BookMessageDispatcher extends ServerDispatcher {

	@Override
	public void dispatch() {
		ServerMain.printOnConsole("BookMessageDispatcher");
		
		BookMessage message =  (BookMessage) getReceivedMessage();
		String facilityName = message.facilityName;
		DateTime from = message.startTime;
		DateTime to = message.endTime;
		
		FacilityManager fm = FacilityManager.getFacilityManager();
		Facility facilityToBook = fm.getFacilityByName(facilityName);
		
		if(facilityToBook == null){
			String errorText = "Facility " + facilityName + " does not exists." ;
			sendErrorMessage(errorText);
			return;
		}
		
		Booking booking;
		try {
			booking = facilityToBook.addBooking(from, to);
		} catch (ServerException e) {
			sendErrorMessage(e.getMessage());
			return;
		}
		
		BookedMessage bookedMessage =  new BookedMessage();
		bookedMessage.confirmationId = booking.getBookingId();
		
		sendReplyMessage(bookedMessage);
	}

}
