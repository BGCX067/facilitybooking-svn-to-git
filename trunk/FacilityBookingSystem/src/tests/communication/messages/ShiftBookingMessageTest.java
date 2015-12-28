package tests.communication.messages;


import communication.messages.Message;
import communication.messages.client.ShiftBookingMessage;
import communication.utils.DateTime;
import communication.utils.Id;

import junit.framework.TestCase;

public class ShiftBookingMessageTest extends TestCase {

	ShiftBookingMessage m_message;
	public ShiftBookingMessageTest(String name) {
		super(name);
		m_message = new ShiftBookingMessage();
		m_message.offset = 200;
		m_message.confirmationId = Id.getUniqueId();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public final void testSerarializeDeserialize() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		byte[] serializedMessage = m_message.serialize();
		ShiftBookingMessage deserializedMessage = (ShiftBookingMessage) Message.deserialize(serializedMessage);
		assertEquals(m_message.confirmationId, deserializedMessage.confirmationId);
		assertEquals(m_message.offset, m_message.offset);
	}
}
