package tests.communication.fieldwrappers;

import java.lang.reflect.Field;

import tests.communication.utils.UtilsTest;


import communication.fieldwrappers.FieldWrapper;
import communication.utils.Utils;


import junit.framework.TestCase;

public class StringArrayFieldWrapperTest extends TestCase {

	FieldWrapper m_fieldWrapper;
	DummyClass m_dummyObject;
	
	public StringArrayFieldWrapperTest(String name) {
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

	public final void testSerializeDeserialize() throws IllegalArgumentException, IllegalAccessException {
		String[] expected = new String[]{"one","two","three","","this","is","test"};
		m_dummyObject.field = expected;
		
		byte[] serializedBytes = m_fieldWrapper.serialize();
		
		byte[] additionalBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7 };
		byte[] bytesToDesearilize = Utils.mergeArrays(serializedBytes,
				additionalBytes);
		
		m_dummyObject.field = new String[]{"","new value"};
		
		byte[] leftOvers = m_fieldWrapper.deserialize(bytesToDesearilize);

		String[] actual = m_dummyObject.field;
		
		UtilsTest.AssertArraysEqual(expected, actual);
		UtilsTest.AssertArraysEqual(additionalBytes, leftOvers);
	}
	
	public final void testTestGetSetInString() throws IllegalArgumentException, IllegalAccessException{
		String[] expected = new String[]{"one","two","three","","this","is","test"};
		m_dummyObject.field = expected;
		
		String fieldValueInString = m_fieldWrapper.getFieldValueAsString();
		m_fieldWrapper.setFieldValue(fieldValueInString);
		
		String[] actual = m_dummyObject.field;
		
		UtilsTest.AssertArraysEqual(expected, actual);
	}

	public class DummyClass {

		
		public String[] field;

		Field getField() {
			return getClass().getFields()[0];
		}

	}

}
