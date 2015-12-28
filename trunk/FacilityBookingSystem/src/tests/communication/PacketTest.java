package tests.communication;

import java.net.DatagramPacket;
import java.net.InetAddress;

import tests.communication.utils.UtilsTest;

import communication.Packet;
import communication.utils.Id;
import communication.utils.Utils;

import junit.framework.TestCase;

public class PacketTest extends TestCase {

	byte[] m_testData;
	Packet m_packet;
	InetAddress m_inetAddress;
	int m_port;
	public PacketTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		m_testData = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 127, -1, -2, -3,
				1, 2, 3, 4, 5, 6, 7, 8, 9, 120, -1, -2, -128 };
		m_inetAddress = InetAddress.getByName("localhost");
		m_port = 12345;
		
		m_packet = new Packet(m_testData, m_inetAddress, m_port);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testPacketByteArray() {
		byte[] expected = m_testData;
		byte[] actual = m_packet.getData();
		UtilsTest.AssertArraysEqual(expected,actual );
	}

	public final void testSerializeAndDeserialize() {
		Packet expected = m_packet;
		byte[] serializedPacket = m_packet.serialize();
		byte[] bytesAddedByNetwork = new byte[]{0,23,3,4,123,-3,-23};
		byte[] recievedBytes = Utils.mergeArrays(serializedPacket, bytesAddedByNetwork);
		
		Packet actual = Packet.deserialize(recievedBytes, m_inetAddress, m_port);
		
		UtilsTest.AssertArraysEqual(expected.getData(), actual.getData());
		assertEquals(expected.getPacketId(), expected.getPacketId());
	}

	public final void testGetConfirmationPacketAndIsConfirmationPacket() {
		Packet confirmPacket = m_packet.getConfirmationPacket();
		
		assertTrue(confirmPacket.isConfirmationPacket());
		
		int expectedDataSize = 0;
		int actualDataSize = confirmPacket.getData().length;
		assertEquals(expectedDataSize, actualDataSize);		
		
		Id expectedId = m_packet.getPacketId();
		Id actualId = confirmPacket.getPacketId();
		assertEquals(expectedId,actualId);
		
		InetAddress expectedAddress = m_inetAddress;
		InetAddress actualAddress = confirmPacket.getAddress();
		assertEquals(expectedAddress, actualAddress);
	}
	
	public final void testIsMyConfirmationPacket(){
		Packet confirmPacket = m_packet.getConfirmationPacket();
		assertTrue(m_packet.isMyConfirmationPacket(confirmPacket));
		
		Packet strangePacket = new Packet(new byte[]{0,23,3,4,123,-3,-23}, m_inetAddress, m_port);
		Packet strangeConfirmPacket = strangePacket.getConfirmationPacket();
		
		assertNotSame(confirmPacket, strangeConfirmPacket);
		
		assertFalse(m_packet.isMyConfirmationPacket(strangeConfirmPacket));
	}

	public final void testGetDatagramPacket(){
		Packet expected = m_packet;
		DatagramPacket packetAsDatagram = expected.getDatagramPacket();
		Packet actual = Packet.deserialize(packetAsDatagram);
		
		assertEquals(expected.getPort(), actual.getPort());
		UtilsTest.AssertArraysEqual(expected.getData(), actual.getData());
		assertEquals(expected.getAddress(), actual.getAddress());
		assertEquals(expected.getPacketId(), actual.getPacketId());
	}

}
