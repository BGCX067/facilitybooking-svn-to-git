package tests.communication.utils;

import communication.utils.ByteConversions;
import communication.utils.DateTime;
import communication.utils.DaysOfWeek;

import junit.framework.TestCase;
import java.util.Random;


public class ByteConversionsTest extends TestCase {

	public ByteConversionsTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testIntToByteArrayAndByteArrayToInt() {
		testIntToByteArrayAndByteArrayToInt(Integer.MAX_VALUE);
		testIntToByteArrayAndByteArrayToInt(Integer.MIN_VALUE);
		testIntToByteArrayAndByteArrayToInt(getRandomInt());
		testIntToByteArrayAndByteArrayToInt(-12345);
		testIntToByteArrayAndByteArrayToInt(12345);
		testIntToByteArrayAndByteArrayToInt(0);
	}

	public final void testDaysOfWeekToByteArrayAndByteArrayToDaysOfWeek() {
		DaysOfWeek[] allValues = DaysOfWeek.values();
		for (DaysOfWeek daysOfWeek : allValues) {
			testDaysOfWeekToByteArrayAndByteArrayToDaysOfWeek(daysOfWeek);
		}
	}

	public final void testDateTimeToByteArrayByteArrayToDateTime() {
		DateTime dateTime1 = new DateTime(DaysOfWeek.Friday, 12, 12);
		DateTime dateTime2 = new DateTime(DaysOfWeek.Monday, 1, 13);
		DateTime dateTime3 = new DateTime(DaysOfWeek.Thursday, 22, 56);

		testDateTimeToByteArrayByteArrayToDateTime(dateTime1);
		testDateTimeToByteArrayByteArrayToDateTime(dateTime2);
		testDateTimeToByteArrayByteArrayToDateTime(dateTime3);
	}

	private int getRandomInt() {
		Random generator = new Random();
		return generator.nextInt();

	}

	private void testIntToByteArrayAndByteArrayToInt(int expected) {
		byte[] bytes = ByteConversions.intToByteArray(expected);
		assertEquals(ByteConversions.SizeOfIntegerInBytes, bytes.length);

		int actual = ByteConversions.byteArrayToInt(bytes);
		assertEquals(expected, actual);
	}

	private void testDaysOfWeekToByteArrayAndByteArrayToDaysOfWeek(
			DaysOfWeek expected) {
		byte[] bytes = ByteConversions.daysOfWeekToByteArray(expected);
		assertEquals(ByteConversions.SizeOfDaysOfWeekInBytes, bytes.length);

		DaysOfWeek actual = ByteConversions.byteArrayToDaysOfWeek(bytes);

		assertEquals(expected, actual);
	}

	private void testDateTimeToByteArrayByteArrayToDateTime(DateTime expected) {
		byte[] bytes = ByteConversions.dateTimeToByteArray(expected);
		assertEquals(ByteConversions.SizeOfDateTimeInBytes, bytes.length);

		DateTime actual = ByteConversions.byteArrayToDateTime(bytes);

		assertEquals(expected.dayOfWeek, actual.dayOfWeek);
		assertEquals(expected.hours, actual.hours);
		assertEquals(expected.minutes, actual.minutes);
	}
}
