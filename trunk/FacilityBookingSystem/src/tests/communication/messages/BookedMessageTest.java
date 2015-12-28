package tests.communication.messages;

import communication.messages.Message;
import communication.messages.server.BookedMessage;
import communication.utils.Id;


import junit.framework.TestCase;

public class BookedMessageTest extends TestCase {

	BookedMessage m_message;
	public BookedMessageTest(String name) {
		super(name);
		m_message = new BookedMessage();
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
		BookedMessage deserializedMessage = (BookedMessage) Message.deserialize(serializedMessage);
		assertEquals(m_message.confirmationId, deserializedMessage.confirmationId);
	}
}
