package tests.communication.fieldwrappers;

import java.lang.reflect.Field;

import tests.communication.utils.UtilsTest;

import communication.fieldwrappers.FieldWrapper;
import communication.utils.DateTime;
import communication.utils.Utils;


import junit.framework.TestCase;

public class DateTimeFieldWrapperTest extends TestCase {

	FieldWrapper m_fieldWrapper;
	DummyClass m_dummyObject;

	public DateTimeFieldWrapperTest(String name) {
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

	public final void testSerializeAndDeserialize()
			throws IllegalArgumentException, IllegalAccessException {
		DateTime expected = new DateTime("Wednesday 23:12");
		
		m_dummyObject.field = expected;

		byte[] serializedBytes = m_fieldWrapper.serialize();

		byte[] additionalBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7 };
		byte[] bytesToDesearilize = Utils.mergeArrays(serializedBytes,
				additionalBytes);

		m_dummyObject.field = new DateTime("Friday 23:12"); // just random DateTime
		
		byte[] leftOvers = m_fieldWrapper.deserialize(bytesToDesearilize);

		DateTime actual = m_dummyObject.field;
		
		assertEquals(expected.toString(), actual.toString());
		UtilsTest.AssertArraysEqual(additionalBytes, leftOvers);
			
	}

	
	public final void testTestGetSetInString() throws IllegalArgumentException,
			IllegalAccessException {
		DateTime expected = new DateTime("Wednesday 23:12");
		m_dummyObject.field = expected;

		String fieldValueInString = m_fieldWrapper.getFieldValueAsString();
		m_fieldWrapper.setFieldValue(fieldValueInString);

		DateTime actual = m_dummyObject.field;

		assertEquals(expected.toString(), actual.toString());
	}

	public class DummyClass {

		public DateTime field;

		Field getField() {
			return getClass().getFields()[0];
		}

	}

}
