package servr.dispatchers;

import communication.messages.client.MonitorFacilityMessage;
import communication.messages.server.FacilityChangedMessage;

import servr.ServerMain;
import servr.facilitybooking.Facility;
import servr.facilitybooking.FacilityEvent;
import servr.facilitybooking.FacilityListener;
import servr.facilitybooking.FacilityManager;

public class MonitorFacilityMessageDispatcher extends ServerDispatcher implements FacilityListener {

	@Override
	public void dispatch() {
		ServerMain.printOnConsole("MonitorFacilityMessageDispatcher");
		MonitorFacilityMessage receivedMessage = (MonitorFacilityMessage) getReceivedMessage();
		
		String facilityName = receivedMessage.facilityName;
		int monitoringInterval = receivedMessage.interval;
		
		FacilityManager fm = FacilityManager.getFacilityManager();
		Facility facilityToMonitor = fm.getFacilityByName(facilityName);
		
		if(facilityToMonitor == null){
			String errorText = "Facility " + facilityName + " does not exists." ;
			sendErrorMessage(errorText);
			return;
		}
		
		facilityToMonitor.addFacilityListener(this, monitoringInterval);
	}

	@Override
	public void FacilityChanged(FacilityEvent event) {
		FacilityChangedMessage replyMessage = new FacilityChangedMessage();
		replyMessage.facilityName = event.getBooking().getFacility().getName();
		sendReplyMessage(replyMessage);
	}

}
