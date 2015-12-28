package tests.communication.messages;

import communication.messages.Message;
import communication.messages.client.BookMessage;
import communication.utils.DateTime;

import junit.framework.TestCase;

public class BookMessageTest extends TestCase {

	BookMessage m_message;
	public BookMessageTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		m_message = new BookMessage();
		m_message.facilityName = "'What up yo' hostel";
		m_message.startTime = new DateTime("Thursday 1:0");
		m_message.endTime = new DateTime("Thursday 1:1");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testSerarializeDeserialize() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		byte[] serializedMessage = m_message.serialize();
		BookMessage deserializedMessage = (BookMessage) Message.deserialize(serializedMessage);
		assertEquals(m_message.facilityName, deserializedMessage.facilityName);
		assertEquals(m_message.startTime.toString(), deserializedMessage.startTime.toString());
		assertEquals(m_message.endTime.toString(), deserializedMessage.endTime.toString());
	}

}
