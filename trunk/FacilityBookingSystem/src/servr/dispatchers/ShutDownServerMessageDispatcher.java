package servr.dispatchers;

import communication.messages.server.ShuttingDownServerMessage;

import servr.ServerMain;

public class ShutDownServerMessageDispatcher extends ServerDispatcher {

	@Override
	public void dispatch() {
		ServerMain.printOnConsole("ShutDownServerMessageDispatcher");
		ShuttingDownServerMessage message = new ShuttingDownServerMessage();
		sendReplyMessage(message);
		
		ServerMain.shutDown();
	}

}
