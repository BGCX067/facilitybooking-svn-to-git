package tests.communication.messages;

import communication.messages.Message;
import communication.messages.client.ExtendBookingMessage;
import communication.utils.DateTime;
import communication.utils.Id;

import junit.framework.TestCase;

public class ExtendBookingMessageTest extends TestCase {

	ExtendBookingMessage m_message;
	public ExtendBookingMessageTest(String name) {
		super(name);
		m_message = new ExtendBookingMessage();
		m_message.offset = -200;
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
		ExtendBookingMessage deserializedMessage = (ExtendBookingMessage) Message.deserialize(serializedMessage);
		assertEquals(m_message.confirmationId, deserializedMessage.confirmationId);
		assertEquals(m_message.offset, m_message.offset);
	}
}
