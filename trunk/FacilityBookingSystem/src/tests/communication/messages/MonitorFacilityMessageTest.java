package tests.communication.messages;

import communication.messages.Message;
import communication.messages.client.MonitorFacilityMessage;
import communication.utils.DateTime;

import junit.framework.TestCase;

public class MonitorFacilityMessageTest extends TestCase {

	MonitorFacilityMessage m_message;
	public MonitorFacilityMessageTest(String name) {
		super(name);
		m_message = new MonitorFacilityMessage();
		m_message.facilityName = "'What up yo' hostel";
		m_message.interval = 2000;
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testSerarializeDeserialize() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		byte[] serializedMessage = m_message.serialize();
		MonitorFacilityMessage deserializedMessage = (MonitorFacilityMessage) Message.deserialize(serializedMessage);
		assertEquals(m_message.facilityName, deserializedMessage.facilityName);
		assertEquals(m_message.interval, deserializedMessage.interval);
	}
}
