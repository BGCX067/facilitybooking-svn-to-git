package servr.dispatchers;

import servr.ServerMain;
import servr.facilitybooking.Booking;
import servr.facilitybooking.FacilityManager;
import servr.facilitybooking.ServerException;
import communication.messages.client.ExtendBookingMessage;
import communication.messages.server.BookingExtendedMessage;
import communication.utils.Id;

public class ExtendBookingMessageDispatcher extends ServerDispatcher {

	@Override
	public void dispatch() {
		ServerMain.printOnConsole("ExtendBookingMessageDispatcher");
		
		ExtendBookingMessage message = (ExtendBookingMessage) getReceivedMessage();
		FacilityManager fm = FacilityManager.getFacilityManager();
		
		Id bookingId = message.confirmationId;
		int minutesToExtend = message.offset;
		Booking booking = fm.getBookingById(bookingId);
		if(booking == null){
			String errorText = "No booking found for id " + bookingId;
			sendErrorMessage(errorText);
			return;
		}
		
		try {
			booking.extend(minutesToExtend);
		} catch (ServerException e) {
			sendErrorMessage(e.getMessage());
			return;
		}
		
		BookingExtendedMessage bookingExtended = new BookingExtendedMessage();
		sendReplyMessage(bookingExtended);

	}

}
