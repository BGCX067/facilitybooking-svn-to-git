package tests.communication.messages;

import communication.messages.Message;
import communication.messages.server.FacilityChangedMessage;
import junit.framework.TestCase;

public class FacilityChangedMessageTest extends TestCase {
	

	FacilityChangedMessage m_message;
	public FacilityChangedMessageTest(String name) {
		super(name);
		m_message = new FacilityChangedMessage();
		m_message.facilityName = "'What up yo' hostel";
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public final void testSerarializeDeserialize() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		byte[] serializedMessage = m_message.serialize();
		FacilityChangedMessage deserializedMessage = (FacilityChangedMessage) Message.deserialize(serializedMessage);
		assertEquals(m_message.facilityName, deserializedMessage.facilityName);
	}

}
