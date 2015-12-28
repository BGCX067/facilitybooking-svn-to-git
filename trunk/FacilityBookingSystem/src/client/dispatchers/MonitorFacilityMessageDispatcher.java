package client.dispatchers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import client.ClientMain;
import client.userinterface.UI;
import communication.Packet;
import communication.Socket;
import communication.messages.ErrorMessage;
import communication.messages.Message;
import communication.messages.client.MonitorFacilityMessage;
import communication.messages.client.MonitoringIntervalExpiredMessage;

public class MonitorFacilityMessageDispatcher extends ClientDispatcher implements ActionListener {
	private boolean keepReceiving;
	private Timer monitoringTimer;

	@Override
	public Message dispatch() {
		UI.printString("Monitoring has started.");
		Socket socket = null;
		try {
			socket = new Socket(ClientMain.clientPort);
			MonitorFacilityMessage requestMessage = (MonitorFacilityMessage) m_message;

			Packet outgoingPacket = new Packet(requestMessage.serialize(),
					ClientMain.serverAddress, ClientMain.serverPort);

		
			boolean sent = socket.send(outgoingPacket);
			if (!sent) {
				return ErrorMessage.createErrorMessage("Sending failed.");
			}
			
			int monitoringInterval = requestMessage.interval;
			monitoringTimer = new Timer(monitoringInterval,this);
			monitoringTimer.start();
			
			keepReceiving = true;
			while (keepReceiving) {
				Packet incomingPacket = socket.receive();

				boolean received = (incomingPacket != null);
				if (!received) {
					continue;
				}

				byte[] serializedMessage = incomingPacket.getData();
				Message deliveredMessage = Message
						.deserialize(serializedMessage);

				boolean messageIdsMatch = (deliveredMessage.getMessageId()
						.equals(m_message.getMessageId()));
				if (!messageIdsMatch) {
					continue;
				}
				
				UI.printMessage(deliveredMessage);
			}
				

			return new MonitoringIntervalExpiredMessage();

		} catch (Exception e) {
			e.printStackTrace();
			ErrorMessage error = new ErrorMessage();
			error.errorMessage = e.getMessage();
			return error;
		} finally {
			if (socket != null) {
				socket.close();
			}
			monitoringTimer.stop();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		keepReceiving = false;
	}

}
