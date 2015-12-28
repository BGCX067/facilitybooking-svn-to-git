package tests.communication.fieldwrappers;

import java.lang.reflect.Field;
import tests.communication.utils.UtilsTest;
import communication.fieldwrappers.FieldWrapper;
import communication.utils.Availability;
import communication.utils.DateTime;
import communication.utils.Utils;

import junit.framework.TestCase;

public class AvailabilityArrayFieldWrapperTest extends TestCase {

	FieldWrapper m_fieldWrapper;
	DummyClass m_dummyObject;
	
	public AvailabilityArrayFieldWrapperTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();

		m_dummyObject = new DummyClass();

		m_fieldWrapper = FieldWrapper.getFieldWrapperByClass(m_dummyObject
				.getField(), m_dummyObject);
	}


	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testSerializeAndDeserialize() throws IllegalArgumentException, IllegalAccessException{
		
		DateTime monday1200 = new DateTime("Monday 12:00");
		DateTime monday1600 = new DateTime("Monday 16:00");
		DateTime tuesday1200 = new DateTime("Tuesday 12:00");
		DateTime tuesday1600 = new DateTime("Tuesday 16:00");
		DateTime wednesday1200 = new DateTime("Wednesday 12:00");
		DateTime wednesday1600 = new DateTime("Wednesday 16:00");

		Availability expectedAvail1 =  new Availability(DateTime.StartOfWeek, monday1200);
		Availability expectedAvail2 =  new Availability(monday1600, tuesday1200);
		Availability expectedAvail3 =  new Availability(tuesday1600, wednesday1200);
		Availability expectedAvail4 =  new Availability(wednesday1600, DateTime.EndOfWeek);
		
		Availability[] expectedArray = new Availability[]{	expectedAvail1,
															expectedAvail2,
															expectedAvail3,
															expectedAvail4};
		
		m_dummyObject.field = expectedArray;
		
		byte[] serializedBytes = m_fieldWrapper.serialize();
		byte[] additionalBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7 };
		byte[] bytesToDesearilize = Utils.mergeArrays(serializedBytes, additionalBytes);
		
		m_dummyObject.field =  new Availability[]{	expectedAvail1};
		
		byte[] leftOvers = m_fieldWrapper.deserialize(bytesToDesearilize);

		Availability[] actualArray = m_dummyObject.field;
		
		for(int i = 0; i < actualArray.length; i++){
			Availability expected = expectedArray[i];
			Availability actual = actualArray[i];
			assertEquals(expected, actual);
			                                  
		}
		UtilsTest.AssertArraysEqual(additionalBytes, leftOvers);
		
		
	}
	
	public class DummyClass {
	
		public Availability[] field;

		Field getField() {
			return getClass().getFields()[0];
		}

	}
}
