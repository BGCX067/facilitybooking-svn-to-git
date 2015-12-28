package tests.communication.fieldwrappers;

import java.lang.reflect.Field;

import tests.communication.utils.UtilsTest;

import communication.fieldwrappers.FieldWrapper;
import communication.utils.Utils;

import junit.framework.TestCase;

public class IntegerFieldWrapperTest extends TestCase {

	FieldWrapper m_fieldWrapper;
	DummyClass m_dummyObject;

	public IntegerFieldWrapperTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();

		m_dummyObject = new DummyClass();
		m_fieldWrapper = FieldWrapper.getFieldWrapperByClass(m_dummyObject
				.getField(), m_dummyObject);
	}

	public final void testSerializeAndDeserialize()
			throws IllegalArgumentException, IllegalAccessException {
		int[] valuesToTest = new int[] { Integer.MIN_VALUE, Integer.MAX_VALUE,
				-12343, 0, 999341 };
		for (int value : valuesToTest) {
			doTestSerializeAndDeserialize(value);
		}
	}

	private final void doTestSerializeAndDeserialize(Integer valueToTest)
			throws IllegalArgumentException, IllegalAccessException {

		Integer expected = valueToTest;
		m_dummyObject.field = expected;

		byte[] serializedBytes = m_fieldWrapper.serialize();

		byte[] additionalBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7 };
		byte[] bytesToDesearilize = Utils.mergeArrays(serializedBytes,
				additionalBytes);

		m_dummyObject.field = -12; // just random number

		byte[] leftOvers = m_fieldWrapper.deserialize(bytesToDesearilize);

		Integer actual = m_dummyObject.field;

		assertEquals(expected, actual);
		UtilsTest.AssertArraysEqual(additionalBytes, leftOvers);
	}
	
	

	public final void testTestGetSetInString() throws IllegalArgumentException, IllegalAccessException{
		Integer expected = 213213;
		m_dummyObject.field = expected;
		
		String fieldValueInString = m_fieldWrapper.getFieldValueAsString();
		m_fieldWrapper.setFieldValue(fieldValueInString);
		
		Integer actual = m_dummyObject.field;
		
		assertEquals(expected, actual);
		
	}
	
	public class DummyClass {

		public Integer field;

		Field getField() {
			return getClass().getFields()[0];
		}

	}

}
