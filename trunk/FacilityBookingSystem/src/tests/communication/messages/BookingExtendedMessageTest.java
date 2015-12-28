package tests.communication.messages;


import communication.messages.Message;
import communication.messages.server.BookingExtendedMessage;

import junit.framework.TestCase;

public class BookingExtendedMessageTest extends TestCase {

	BookingExtendedMessage m_message;
	public BookingExtendedMessageTest(String name) {
		super(name);
		m_message = new BookingExtendedMessage();
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public final void testSerarializeDeserialize() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		byte[] serializedMessage = m_message.serialize();
		BookingExtendedMessage deserializedMessage = (BookingExtendedMessage) Message.deserialize(serializedMessage);
		assertNotNull(deserializedMessage);
	}

}
