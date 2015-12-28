package tests.communication.messages;

import communication.messages.Message;
import communication.messages.server.BookingShiftedMessage;
import communication.utils.Id;
import junit.framework.TestCase;

public class BookingShiftedMessageTest extends TestCase {
	
	BookingShiftedMessage m_message;
	public BookingShiftedMessageTest(String name) {
		super(name);
		m_message = new BookingShiftedMessage();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public final void testSerarializeDeserialize() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		byte[] serializedMessage = m_message.serialize();
		BookingShiftedMessage deserializedMessage = (BookingShiftedMessage) Message.deserialize(serializedMessage);
		assertNotNull(deserializedMessage);
		
		Id expectedId = m_message.getMessageId();
		Id actualId = deserializedMessage.getMessageId();
		assertEquals(expectedId, actualId);
	}
	
	public final void testIds(){
		BookingShiftedMessage otherMessage = new BookingShiftedMessage();
		Id firstId = m_message.getMessageId();
		Id secondId = otherMessage.getMessageId();
		assertNotSame(firstId, secondId);
		
		otherMessage.setMessageId(firstId);
		secondId = otherMessage.getMessageId();
		assertEquals(firstId, secondId);
		
	}

}
