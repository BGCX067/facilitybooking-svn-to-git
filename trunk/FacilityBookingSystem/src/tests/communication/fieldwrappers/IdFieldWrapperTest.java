package tests.communication.fieldwrappers;

import java.lang.reflect.Field;

import tests.communication.utils.UtilsTest;

import communication.fieldwrappers.FieldWrapper;
import communication.utils.Id;
import communication.utils.Utils;
import junit.framework.TestCase;

public class IdFieldWrapperTest extends TestCase {

	FieldWrapper m_fieldWrapper;
	DummyClass m_dummyObject;
	
	public IdFieldWrapperTest(String name) {
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
		Id expected = Id.getUniqueId();
		m_dummyObject.field = expected;

		byte[] serializedBytes = m_fieldWrapper.serialize();

		byte[] additionalBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7 };
		byte[] bytesToDesearilize = Utils.mergeArrays(serializedBytes,
				additionalBytes);
		
		m_dummyObject.field = Id.getUniqueId(); // just random Id
		byte[] leftOvers = m_fieldWrapper.deserialize(bytesToDesearilize);
		
		Id actual = m_dummyObject.field;

		assertEquals(expected, actual);
		UtilsTest.AssertArraysEqual(additionalBytes, leftOvers);

	}
	
	public final void testGetAndSetAsString() throws IllegalArgumentException, IllegalAccessException{
		Id expected = Id.getUniqueId();
		m_dummyObject.field = expected;
		String fieldValue = m_fieldWrapper.getFieldValueAsString();
		
		m_dummyObject.field = Id.getUniqueId(); //rewrite original value
		
		m_fieldWrapper.setFieldValue(fieldValue);
		
		Id actual = m_dummyObject.field;
		
		assertEquals(expected, actual);
	}
	
	public class DummyClass {

		public Id field;

		Field getField() {
			return getClass().getFields()[0];
		}
	}

}
