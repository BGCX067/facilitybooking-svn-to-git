package servr.dispatchers;

import servr.ServerMain;
import servr.facilitybooking.Facility;
import servr.facilitybooking.FacilityManager;

import communication.messages.client.QueryAvailibilityMessage;
import communication.messages.server.AvailabilitiesMessage;
import communication.utils.Availability;
import communication.utils.DateTime;

public class QueryAvailibilityMessageDispatcher extends ServerDispatcher {

	@Override
	public void dispatch() {
		ServerMain.printOnConsole("QueryAvailibilityMessageDispatcher");
		
		QueryAvailibilityMessage message =  (QueryAvailibilityMessage) getReceivedMessage();
		String facilityName = message.facilityName;
		DateTime day = message.day;
		
		
		FacilityManager fm = FacilityManager.getFacilityManager();
		Facility facility = fm.getFacilityByName(facilityName);
		
		if(facility == null){
			String errorText = "Facility " + facilityName + " does not exists." ;
			sendErrorMessage(errorText);
			return;
		}
		
		Availability[] availabilities = facility.getAvailabilityForDay(day);
		
		AvailabilitiesMessage availabilitiesMsg = new AvailabilitiesMessage();
		availabilitiesMsg.availabilities = availabilities;

		sendReplyMessage(availabilitiesMsg);
	}

}
