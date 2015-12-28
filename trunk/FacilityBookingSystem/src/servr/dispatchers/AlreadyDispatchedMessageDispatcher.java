package servr.dispatchers;

import servr.ServerMain;
import communication.messages.Message;

public class AlreadyDispatchedMessageDispatcher extends ServerDispatcher {

	Message m_reply;
	
	public void setReply(Message reply){
		m_reply = reply;
	}
	
	@Override
	public void dispatch() {
		ServerMain.printOnConsole("AlreadyDispatchedMessageDispatcher");
		
		if(m_reply != null){
			sendReplyMessage(m_reply);
		}
	}

}
