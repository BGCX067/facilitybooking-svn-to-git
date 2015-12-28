package tests.communication.utils;

import communication.utils.Utils;

import junit.framework.TestCase;

public class UtilsTest extends TestCase {

	public UtilsTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testTrimStartOfArray() {
		byte[] trimmedArray = new byte[] { 1, 2, 3, 4, 5 };
		byte[] trimmingArray = new byte[] { 1, 2 };
		byte[] expected = new byte[] { 3, 4, 5 };

		byte[] actual = Utils.trimStartOfArray(trimmingArray, trimmedArray);

		AssertArraysEqual(expected, actual);
	}

	public final void testMergeArrays() {
		byte[] firstArray = new byte[] { 1, 2, 3 };
		byte[] secondArray = new byte[] { 4, 5 };
		byte[] expected = new byte[] { 1, 2, 3, 4, 5 };

		byte[] actual = Utils.mergeArrays(firstArray, secondArray);
		
		AssertArraysEqual(expected, actual);

	}
	
	public final void testCutOutSubArray(){
		byte[] sourceArray = new byte[] { 1, 2, 3, 4, 5 };
		byte[] expected = new byte[] { 3, 4 };
		
		byte[] actualArray = Utils.cutOutSubArray(2, 2, sourceArray);
		
		AssertArraysEqual(expected, actualArray);
		
	}

	public static void AssertArraysEqual(byte[] expectedArray, byte[] actualArray) {
		assertEquals(expectedArray.length, actualArray.length);

		for (int element = 0; element < actualArray.length; element++) {
			byte expectedElement = expectedArray[element];
			byte actualElement = actualArray[element];
			assertEquals(expectedElement, actualElement);
		}
	}
	
	public static void AssertArraysEqual(String[] expectedArray, String[] actualArray) {
		assertEquals(expectedArray.length, actualArray.length);

		for (int element = 0; element < actualArray.length; element++) {
			String expectedElement = expectedArray[element];
			String actualElement = actualArray[element];
			assertEquals(expectedElement, actualElement);
		}
	}
	

//	public static <T,S> void AssertArraysEqual(T[] expectedArray, S[] actualArray) {
//		assertEquals(expectedArray.length, actualArray.length);
//
//		for (int element = 0; element < actualArray.length; element++) {
//			T expectedElement = expectedArray[element];
//			S actualElement = actualArray[element];
//			assertEquals(expectedElement, actualElement);
//		}
//	}

}
