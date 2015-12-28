package tests.communication.messages;

import communication.messages.Message;
import communication.messages.server.IntervalExpiredMessage;
import junit.framework.TestCase;

public class IntervalExpiredMessageTest extends TestCase {

	IntervalExpiredMessage m_message;
	public IntervalExpiredMessageTest(String name) {
		super(name);
		m_message = new IntervalExpiredMessage();
		m_message.facilityName = "to by bylo uzasny :-)     fsdfsdfafasdfadsfdasfsdafsdafasdfasdf";
	}
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testSerarializeDeserialize() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		byte[] serializedMessage = m_message.serialize();
		String expected = m_message.facilityName; 
		IntervalExpiredMessage deserializedMessage = (IntervalExpiredMessage) Message.deserialize(serializedMessage);
		String actual = deserializedMessage.facilityName;
		assertEquals(expected, actual);
	}
}
