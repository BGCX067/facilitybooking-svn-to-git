package communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Socket {
	public static int TimeOut = 1000;
	public static int RetryCount = 3;
	public static double NetworkFailureProbability = 0;
	
	int m_timeOut;
	int m_retryCount;
	
	DatagramSocket m_socket;
	Random m_randomGenerator;
	Queue<Packet> m_PacketQueue = new ConcurrentLinkedQueue<Packet>();
	
	public String m_name;
	public Socket(int socketPort, String name) throws SocketException {
		m_socket = new DatagramSocket(socketPort);
		m_socket.setSoTimeout(m_timeOut);
		m_randomGenerator = new Random();
		m_name = name;
		setTimeOut(TimeOut);
		m_retryCount = RetryCount;
	}
	
	public Socket(int socketPort) throws SocketException {
		this(socketPort, "a socket");
	}

	public int getTimeOut() {
		return m_timeOut;
	}

	public int getPort(){
		return m_socket.getPort();
	}
	
	public void setTimeOut(int out) throws SocketException {
		m_timeOut = out;
		m_socket.setSoTimeout(m_timeOut);
	}

	public int getRetryCount() {
		return m_retryCount;
	}

	public void setRetryCount(int count) {
		m_retryCount = count;
	}

	public void close(){
		m_socket.close();
	}

	/**
	 * Send data in outgoingPacket to a server, then wait for a confirmation from it.
	 * If confirmation does not arrive in timeOut then retries for number of retries
	 * If other than confirmation packet is received throw it away and try 
	 * @return true if data delivery was confirmed
	 * @throws IOException
	 */
	public boolean send(Packet outgoingPacket) throws IOException {
		int retriedCount = 0;
		while (retriedCount++ < m_retryCount) {
			//System.out.println(m_name + ": trying to send for the " + retriedCount + ". time.");
			sendSimulatedNetworkFailure(outgoingPacket);
		
			Packet confirmationPacket = receiveFromNetwork();
		
			boolean receivingTimedOut = (confirmationPacket == null);
			if(receivingTimedOut){
				continue;
			}
						
			boolean receivingConfirmed = outgoingPacket.isMyConfirmationPacket(confirmationPacket);
			if(receivingConfirmed){
				return true;
			}else{
				Packet normalPacket = confirmationPacket;
				m_PacketQueue.add(normalPacket);
			}
		}
		
		return false;
	}
	
	void sendSimulatedNetworkFailure(Packet packetToSend)
			throws IOException {
		double randomDouble = m_randomGenerator.nextDouble();
		boolean shouldSend = randomDouble > NetworkFailureProbability;
		System.out.print("[");
		if (shouldSend) {
			System.out.print("]");
			m_socket.send(packetToSend.getDatagramPacket());
		}
	}
	
	Packet receiveFromNetwork() throws IOException{
		try{
			byte[] incomingBuffer = new byte[m_socket.getReceiveBufferSize()];
			DatagramPacket incomingDatagramPacket = new DatagramPacket(incomingBuffer, incomingBuffer.length);
	
			m_socket.receive(incomingDatagramPacket);
	
			Packet receivedPacket  = Packet.deserialize(incomingDatagramPacket);
			
			return receivedPacket;
		}catch (SocketTimeoutException e) {
			
			return null;
		}
	}
	
	/**
	 * Waits for timeout for an incoming data. If timeouts, retries for number of retries.
	 * If received data is confirmation packet it throws it away and retries for number of retries.
	 * Upon receiving a valid packet (except a confirmation packet) the delivery confirmation is send.
	 * @return received packet or null when nothing received;
	 * @throws IOException
	 */
	public Packet receive() throws IOException{
				
		int retriedCount = 0;
		while(retriedCount++ < m_retryCount){
			//System.out.println(m_name + ": trying to receive for the " + retriedCount + ". time.");
			boolean serveQueue = !m_PacketQueue.isEmpty();
			if(serveQueue){
				return m_PacketQueue.poll();
			}
			
			Packet incomingPacket = receiveFromNetwork();
			boolean receivingTimedOut = (incomingPacket == null);
			if(receivingTimedOut){
				continue;
			}
			
			if(!incomingPacket.isConfirmationPacket()){
				sendConfirmation(incomingPacket);
				return incomingPacket;
			}
		}
		
		return null;
	}
	
	void sendConfirmation(Packet packetToConfirm) throws IOException{
		sendSimulatedNetworkFailure(packetToConfirm.getConfirmationPacket());
	}
	


}
