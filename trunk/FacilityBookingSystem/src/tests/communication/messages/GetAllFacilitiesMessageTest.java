package tests.communication.messages;

import communication.messages.Message;
import communication.messages.client.GetAllFacilitiesMessage;

import junit.framework.TestCase;

public class GetAllFacilitiesMessageTest extends TestCase {

	GetAllFacilitiesMessage m_message;
	
	public GetAllFacilitiesMessageTest(String name) {
		super(name);
		m_message = new GetAllFacilitiesMessage();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	public final void testSerarializeDeserialize() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		byte[] serializedMessage = m_message.serialize();
		GetAllFacilitiesMessage deserializedMessage = (GetAllFacilitiesMessage) Message.deserialize(serializedMessage);
		assertNotNull(deserializedMessage);
		
	}

}
