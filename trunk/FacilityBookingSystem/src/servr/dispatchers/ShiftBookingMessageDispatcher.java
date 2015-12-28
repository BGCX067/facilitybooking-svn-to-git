package servr.dispatchers;

import servr.ServerMain;
import servr.facilitybooking.Booking;
import servr.facilitybooking.FacilityManager;
import servr.facilitybooking.ServerException;
import communication.messages.client.ShiftBookingMessage;
import communication.messages.server.BookingShiftedMessage;
import communication.utils.Id;

public class ShiftBookingMessageDispatcher extends ServerDispatcher {

	@Override
	public void dispatch() {
		ServerMain.printOnConsole("ShiftBookingMessageDispatcher");
		
		ShiftBookingMessage message = (ShiftBookingMessage) getReceivedMessage();
		FacilityManager fm = FacilityManager.getFacilityManager();
		
		Id bookingId = message.confirmationId;
		int minutesToShift = message.offset;
	
		Booking booking = fm.getBookingById(bookingId);
		if(booking == null){
			String errorText = "No booking found for id " + bookingId;
			sendErrorMessage(errorText);
			return;
		}
		
		try {
			booking.shift(minutesToShift);
		} catch (ServerException e) {
			sendErrorMessage(e.getMessage());
			return;
		}
		
		BookingShiftedMessage bookingExtended = new BookingShiftedMessage();
		sendReplyMessage(bookingExtended);
		
	}

}
