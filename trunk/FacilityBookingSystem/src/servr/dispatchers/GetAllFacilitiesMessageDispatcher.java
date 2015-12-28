package servr.dispatchers;

import servr.ServerMain;
import servr.facilitybooking.FacilityManager;
import communication.messages.client.GetAllFacilitiesMessage;
import communication.messages.server.AllFacilitiesMessage;

public class GetAllFacilitiesMessageDispatcher extends ServerDispatcher {

	@Override
	public void dispatch() {
		ServerMain.printOnConsole("GetAllFacilitiesMessageDispatcher");
		GetAllFacilitiesMessage receivedMessage = (GetAllFacilitiesMessage) getReceivedMessage();
		
		String[] facilities = FacilityManager.getFacilityManager().getAllFacilitiesNames();
		
		AllFacilitiesMessage allFacilitiesMessage = new AllFacilitiesMessage();
		allFacilitiesMessage.allFacilities = facilities;
		
		sendReplyMessage(allFacilitiesMessage);
	}

}
