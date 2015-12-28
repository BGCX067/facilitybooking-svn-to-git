package tests.communication.fieldwrappers;

import java.lang.reflect.Field;

import tests.communication.utils.UtilsTest;

import communication.fieldwrappers.FieldWrapper;
import communication.fieldwrappers.HumanReadableName;
import communication.utils.Utils;

import junit.framework.TestCase;

public class StringFieldWrapperTest extends TestCase {

	FieldWrapper m_fieldWrapper;
	DummyClass m_dummyObject;

	public StringFieldWrapperTest(String name) {
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
		String expected = "testing value, testing value, testing value, testing value, testing value, testing value, testing value, testing value, testing value, testing value, testing value, testing value, testing value, testing value";
		m_dummyObject.field = expected;

		byte[] serializedBytes = m_fieldWrapper.serialize();

		byte[] additionalBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7 };
		byte[] bytesToDesearilize = Utils.mergeArrays(serializedBytes,
				additionalBytes);

		m_dummyObject.field = "different value than expected";

		byte[] leftOvers = m_fieldWrapper.deserialize(bytesToDesearilize);

		String actual = m_dummyObject.field;

		assertEquals(expected, actual);
		UtilsTest.AssertArraysEqual(additionalBytes, leftOvers);
	}

	public final void testGetFieldName() {
		String actual = m_fieldWrapper.getFieldName();
		String expected = "field";

		assertEquals(expected, actual);
	}

	public final void testGetFieldHumanReadableName() {
		String expected = DummyClass.fieldHumanReadableName;
		String actual = m_fieldWrapper.getFieldHumanReadableName();

		assertEquals(expected, actual);
	}

	public final void testSetFieldValueGetFieldValue() throws IllegalArgumentException, IllegalAccessException {
		String expected = "testing value";

		m_fieldWrapper.setFieldValue(expected);
		String actual = (String) m_fieldWrapper.getFieldValue();

		assertEquals(expected, actual);
	}
	
	public final void testTestGetSetInString() throws IllegalArgumentException, IllegalAccessException{
		String expected = "whatever.....";
		m_dummyObject.field = expected;
		
		String fieldValueInString = m_fieldWrapper.getFieldValueAsString();
		m_fieldWrapper.setFieldValue(fieldValueInString);
		
		String actual = m_dummyObject.field;
		
		assertEquals(expected, actual);
	}

	public class DummyClass {
		@HumanReadableName(fieldHumanReadableName)
		public String field;

		Field getField() {
			return getClass().getFields()[0];
		}

		// do not move before "field", otherwise the test will fail because of
		// getFields()[0]
		public static final String fieldHumanReadableName = "George J. Field jr.";
	}

}
