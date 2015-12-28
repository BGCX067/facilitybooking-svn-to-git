package client.dispatchers;

import communication.messages.Message;
import communication.messages.client.ClientMessage;
import communication.messages.client.MonitorFacilityMessage;

public abstract class ClientDispatcher {

	protected Message m_message;

	public void setMessage(Message messageToDispatch) {
		m_message = messageToDispatch;
	}

	public abstract Message dispatch();

	public static ClientDispatcher getDispatcherForMessage(Message messageToDispatch) {
		
		boolean dispatchByMonitorFacilityMessageDispatcher = (messageToDispatch.getClass() == MonitorFacilityMessage.class);
		if(dispatchByMonitorFacilityMessageDispatcher){
			//System.out.println("MonitorFacilityMessageDispatcher");
			MonitorFacilityMessageDispatcher dispatcher = new MonitorFacilityMessageDispatcher();
			dispatcher.setMessage(messageToDispatch);
			
			return dispatcher;
		}
		
		boolean dispatchByGeneralDispatcher = (messageToDispatch.getClass().getSuperclass() == ClientMessage.class);
		if(dispatchByGeneralDispatcher){
			//System.out.println("GeneralClientDispatcher");
			ClientDispatcher dispatcher = new GeneralClientDispatcher();
			dispatcher.setMessage(messageToDispatch);
	
			return dispatcher;
		}
			
		return null;
	}
}
