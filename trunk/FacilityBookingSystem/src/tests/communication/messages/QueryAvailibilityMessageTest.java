package tests.communication.messages;

import communication.messages.Message;
import communication.messages.client.QueryAvailibilityMessage;
import communication.utils.DateTime;
import communication.utils.DaysOfWeek;

import junit.framework.TestCase;

public class QueryAvailibilityMessageTest extends TestCase {

	QueryAvailibilityMessage m_message;
	public QueryAvailibilityMessageTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		m_message = new QueryAvailibilityMessage();
		m_message.day = new DateTime(DaysOfWeek.Saturday);
		m_message.facilityName = "some facility";
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testSerarializeDeserialize() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		byte[] serializedMessage = m_message.serialize();
		QueryAvailibilityMessage deserializedMessage = (QueryAvailibilityMessage) Message.deserialize(serializedMessage);
		assertEquals(m_message.facilityName, deserializedMessage.facilityName);
		assertEquals(m_message.day.toString(), deserializedMessage.day.toString());
	}
}
