package client.dispatchers;

import client.ClientMain;
import communication.Packet;
import communication.Socket;
import communication.messages.ErrorMessage;
import communication.messages.Message;


public class GeneralClientDispatcher extends ClientDispatcher {

	@Override
	public Message dispatch() {
		Socket socket = null;
		try {
			 socket =  new Socket(ClientMain.clientPort);
			 
			 Packet outgoingPacket = new Packet(m_message.serialize(),
					 							ClientMain.serverAddress, 
					 							ClientMain.serverPort);
			 boolean sent = socket.send(outgoingPacket);
			 
			 if(!sent){
				return ErrorMessage.createErrorMessage("Sending failed.");
			 }
			 
			 Packet incomingPacket = socket.receive();
			 
			 boolean received = (incomingPacket != null );
			 if(!received){
				 return ErrorMessage.createErrorMessage("Receiving failed.");
			 }
			 
			 byte[] serializedMessage = incomingPacket.getData();
			 Message deliveredMessage = Message.deserialize(serializedMessage);
			 
			 boolean messageIdsMatch = (deliveredMessage.getMessageId().equals(m_message.getMessageId()));
			 if(!messageIdsMatch){
				 return ErrorMessage.createErrorMessage("Receiving wrong answer.");
			 }
			 
			 //everything is all right;
			 return deliveredMessage;
			 
		} catch (Exception e) {
			e.printStackTrace();
			ErrorMessage error = new ErrorMessage();
			error.errorMessage = e.getMessage();
			return error;
		}
		finally{
			if(socket != null){
				socket.close();
			}
		}
	}


}
