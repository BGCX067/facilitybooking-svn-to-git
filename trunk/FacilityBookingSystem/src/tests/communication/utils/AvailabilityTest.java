package tests.communication.utils;

import communication.utils.Availability;
import communication.utils.DateTime;

import junit.framework.TestCase;

public class AvailabilityTest extends TestCase {

	public AvailabilityTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testSerializeAndDeserialize() {
		Availability expected = new Availability(DateTime.StartOfWeek, DateTime.EndOfWeek);
		byte[] serialized = expected.serialize();
		
		Availability actual = Availability.deserialize(serialized);
		
		assertEquals(expected, actual);
	}

}
