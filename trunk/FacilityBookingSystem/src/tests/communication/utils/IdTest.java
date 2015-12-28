package tests.communication.utils;

import communication.utils.Id;

import junit.framework.TestCase;

public class IdTest extends TestCase {

	public IdTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testGetUniquePacketId() {
		Id firstId = Id.getUniqueId();
		Id secondId = Id.getUniqueId();

		assertNotSame(firstId, secondId);
	}
	
	public final void testSerializeAndDeserialize(){
		Id expected = Id.getUniqueId();
		
		byte[] expectedInBytes = expected.serialize();
		assertEquals(expectedInBytes.length, Id.getSizeOfIdInBytes());
		
		Id actual = Id.deserialize(expectedInBytes);
		
		assertEquals(expected, actual);
	}
	


}
