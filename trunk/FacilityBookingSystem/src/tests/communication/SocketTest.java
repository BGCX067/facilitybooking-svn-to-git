package tests.communication;

import java.io.IOException;
import java.net.InetAddress;

import tests.communication.utils.UtilsTest;
import communication.Packet;
import communication.Socket;
import communication.messages.Message;
import communication.messages.client.MonitorFacilityMessage;
import communication.utils.DateTime;

import junit.framework.TestCase;

public class SocketTest extends TestCase{

	Socket m_socket;
	SocketServerTestThread m_server;
	public SocketTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		Socket.NetworkFailureProbability = 0;
		m_socket = new Socket(23456, "client");
		m_server = new SocketServerTestThread();
		m_server.start();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		m_socket.close();
	}
	
	public final void testSocket() throws IOException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException{
		MonitorFacilityMessage expectedMsg = new MonitorFacilityMessage();
		expectedMsg.facilityName = "my test facility";
		expectedMsg.interval = 2000;
		
		byte[] expectedData  = expectedMsg.serialize();
		InetAddress address = InetAddress.getByName("localhost");
		int port = SocketServerTestThread.port;
		Packet expectedPacket = new Packet(expectedData,address, port);
		
		boolean sent = m_socket.send(expectedPacket);
		//System.out.println("client: sent is " + sent);
		Packet actualPacket = m_socket.receive();
		
	//	assertNotNull(actualPacket);
		
		if(actualPacket != null){
			//System.out.println("client: received");
			byte[] actualData = actualPacket.getData();
			UtilsTest.AssertArraysEqual(expectedData, actualData);
			assertEquals(expectedPacket.getPacketId(), actualPacket.getPacketId());
			
			MonitorFacilityMessage actualMsg = (MonitorFacilityMessage)Message.deserialize(actualData);
			assertEquals(expectedMsg.facilityName, actualMsg.facilityName);
			assertEquals(expectedMsg.interval, actualMsg.interval);
			
		}else{
			//System.out.println("client: not received");
			fail("client: Received packet was null");
		}
	}

	class SocketServerTestThread extends Thread{
		public static final int port = 12345;
		
		public SocketServerTestThread(){
			super("SocketServerTestThread");
		}
		
		@Override
		public void run(){
			try {
				Socket server = new Socket(port, "SERVER");
				Packet recievedPacket = server.receive();
				if(recievedPacket != null){
					MonitorFacilityMessage actualMsg = (MonitorFacilityMessage)Message.deserialize(recievedPacket.getData());
					//System.out.println("SERVER: received: " + actualMsg.facilityName + ", " + actualMsg.interval);
					server.send(recievedPacket);
				}else{
					//System.out.println("SERVER: not received");
				}
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
