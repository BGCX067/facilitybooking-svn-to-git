package servr.dispatchers;

import java.net.InetAddress;
import java.util.Hashtable;

import servr.InvocationSemantics;
import servr.ServerMain;

import communication.Packet;
import communication.Socket;
import communication.messages.ErrorMessage;
import communication.messages.Message;
import communication.messages.client.BookMessage;
import communication.messages.client.ExtendBookingMessage;
import communication.messages.client.GetAllBookingMessage;
import communication.messages.client.GetAllFacilitiesMessage;
import communication.messages.client.MonitorFacilityMessage;
import communication.messages.client.QueryAvailibilityMessage;
import communication.messages.client.ShiftBookingMessage;
import communication.messages.client.ShutDownServerMessage;
import communication.utils.Id;

public abstract class ServerDispatcher {
		private Message m_receivedMessage;
		private InetAddress m_address;
		private int	m_port;
		
		private static Hashtable<Id, Message> m_replyMessageHistoryTable;
		
		static Hashtable<Class<?>, Class<?>> messageVsServerDispatcher;
		static{
			m_replyMessageHistoryTable =  new  Hashtable<Id, Message>();
			messageVsServerDispatcher = new Hashtable<Class<?>, Class<?>>();
			
			messageVsServerDispatcher.put(BookMessage.class, BookMessageDispatcher.class);
			messageVsServerDispatcher.put(ExtendBookingMessage.class, ExtendBookingMessageDispatcher.class);
			messageVsServerDispatcher.put(GetAllFacilitiesMessage.class, GetAllFacilitiesMessageDispatcher.class);
			messageVsServerDispatcher.put(MonitorFacilityMessage.class, MonitorFacilityMessageDispatcher.class);
			messageVsServerDispatcher.put(QueryAvailibilityMessage.class, QueryAvailibilityMessageDispatcher.class);
			messageVsServerDispatcher.put(ShiftBookingMessage.class, ShiftBookingMessageDispatcher.class);
			messageVsServerDispatcher.put(ShutDownServerMessage.class, ShutDownServerMessageDispatcher.class);
			messageVsServerDispatcher.put(GetAllBookingMessage.class, GetAllBookingMessageDispatcher.class);
		}
		
		public abstract void dispatch();
		
		void setReceivedMessage(Message message){
			m_receivedMessage = message;
		}
		
		public Message getReceivedMessage(){
			return m_receivedMessage;
		}
		
		void setPort(int port){
			m_port = port;
		}
		
		public int getPort(){
			return m_port;
		}
		
		void setInetAddress(InetAddress inetAddress){
			m_address = inetAddress;
		}
		
		public InetAddress getInetAddress(){
			return m_address;
		}
		
		
		
		public static ServerDispatcher getServerDispatcherForPacket(Packet packet) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
			byte[] serializedMessage = packet.getData();
			Message message = Message.deserialize(serializedMessage);
			
			ServerMain.printOnConsole("Received message " + message.getClass().getName() + ", id = " + message.getMessageId());
			
			if(ServerMain.invocationSemantics  == InvocationSemantics.AtMostOnce){
				Message previousReply =  getPreviosReply(message);
				boolean alreadyDisptached = (previousReply != null);
				if(alreadyDisptached){
					AlreadyDispatchedMessageDispatcher dispatcher = new AlreadyDispatchedMessageDispatcher();
					dispatcher.setReply(previousReply);
					
					dispatcher.setReceivedMessage(message);
					dispatcher.setInetAddress(packet.getAddress());
					dispatcher.setPort(packet.getPort());
					
					return dispatcher;
				}
			}
			
			Class<?> dispatcherClass = messageVsServerDispatcher.get(message.getClass());;
			ServerDispatcher dispatcher = (ServerDispatcher) dispatcherClass.newInstance();
			
			dispatcher.setReceivedMessage(message);
			dispatcher.setInetAddress(packet.getAddress());
			dispatcher.setPort(packet.getPort());
			
			return dispatcher;
		}
		
		
		private static Message getPreviosReply(Message msg){
			Id msgId = msg.getMessageId();
			Message replyMessage = m_replyMessageHistoryTable.get(msgId);
			return replyMessage;
		}
		
		public void sendReplyMessage(Message message){
			Id recievedId = m_receivedMessage.getMessageId();
			message.setMessageId(recievedId);
			
			if(ServerMain.invocationSemantics  == InvocationSemantics.AtMostOnce){
				Id messageId = message.getMessageId();
				m_replyMessageHistoryTable.put(messageId, message);
			}
			
			byte[] data;
			try {
				data = message.serialize();
				Packet packet = new Packet(data, m_address, m_port);
				
				Socket socket = ServerMain.getFreeSocket();
				socket.send(packet);
			} catch (Exception e) {
				e.printStackTrace();
				ServerMain.shutDown();
			}
		}
		
		public void sendErrorMessage(String errorMessageText){
			ErrorMessage errorMessage =  ErrorMessage.createErrorMessage(errorMessageText);
			sendReplyMessage(errorMessage);
		}
		
		
}
