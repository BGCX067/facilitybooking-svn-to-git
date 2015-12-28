package communication;

import java.net.DatagramPacket;
import java.net.InetAddress;

import communication.utils.ByteConversions;
import communication.utils.Id;
import communication.utils.Utils;

public class Packet {
	byte[] m_data;
	Id m_packetId;
	InetAddress m_address;
	int m_port;

	/**
	 * Construct a packet for sending data
	 * 
	 * @param data
	 */
	public Packet(byte[] data, InetAddress address, int port) {
		m_data = data;
		m_packetId = Id.getUniqueId();
		m_address = address;
		m_port = port;
	}

	Packet(Id packetId, InetAddress address, int port) {
		m_data = new byte[0];
		m_packetId = packetId;
		m_address = address;
		m_port = port;
	}

	Packet(Id packetId, byte[] data, InetAddress address, int port) {
		m_data = data;
		m_packetId = packetId;
		m_address = address;
		m_port = port;
	}

	public byte[] getData() {
		return m_data;
	}

	public Id getPacketId() {
		return m_packetId;
	}

	public InetAddress getAddress() {
		return m_address;
	}

	public int getPort() {
		return m_port;
	}

	public byte[] serialize() {
		byte[] packetIdInBytes = m_packetId.serialize();
		byte[] packetDataSizeInBytes = ByteConversions.intToByteArray(m_data.length);

		byte[] serializedPacket = new byte[0];
		serializedPacket = Utils.mergeArrays(serializedPacket, packetIdInBytes);
		serializedPacket = Utils.mergeArrays(serializedPacket,
				packetDataSizeInBytes);
		serializedPacket = Utils.mergeArrays(serializedPacket, m_data);

		return serializedPacket;
	}

	/**
	 * @return DatagramPacket containing data, ip address and port
	 */
	public DatagramPacket getDatagramPacket() {
		byte[] serializedPacket = serialize();
		DatagramPacket datagram = new DatagramPacket(serializedPacket,
				serializedPacket.length, m_address, m_port);

		return datagram;
	}

	public Packet getConfirmationPacket() {
		return new Packet(m_packetId, m_address, m_port);
	}

	public boolean isConfirmationPacket() {
		return m_data.length == 0;
	}

	public boolean isMyConfirmationPacket(Packet packet) {
		boolean idsMatch = m_packetId.equals(packet.getPacketId());
		return packet.isConfirmationPacket() && idsMatch;
	}

	public static Packet deserialize(byte[] serializedPacket,
			InetAddress address, int port) {
		byte[] packetIdInBytes = Utils.cutOutSubArray(0,Id
				.getSizeOfIdInBytes(), serializedPacket);
		byte[] packetDataSizeInBytes = Utils.cutOutSubArray(Id
				.getSizeOfIdInBytes(),
				ByteConversions.SizeOfIntegerInBytes, serializedPacket);

		Id packetId = Id.deserialize(packetIdInBytes);
		int packetDataSize = ByteConversions
				.byteArrayToInt(packetDataSizeInBytes);

		int startOfDataIndex = Id.getSizeOfIdInBytes()
				+ ByteConversions.SizeOfIntegerInBytes;
		byte[] data = Utils.cutOutSubArray(startOfDataIndex, packetDataSize,
				serializedPacket);

		return new Packet(packetId, data, address, port);
	}

	public static Packet deserialize(DatagramPacket datagram) {
		return deserialize(datagram.getData(), datagram.getAddress(), datagram
				.getPort());
	}

}
