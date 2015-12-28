package tests.communication.utils;

import communication.utils.DateTime;
import communication.utils.DaysOfWeek;

import junit.framework.TestCase;

public class DateTimeTest extends TestCase {

	public DateTimeTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testSetAndToString() {
		String expected = "Sunday 12:23";
		DateTime dateTime = new DateTime(expected);
		
		String actual = dateTime.toString();
		assertEquals(expected, actual);
	}
	
	public final void testSetAndToString2ndFormat() {
		String day = "Sunday";
		DateTime actual = new DateTime(day);
		
		DateTime expected = new DateTime();
		expected.dayOfWeek = DaysOfWeek.Sunday;
		
		assertEquals(expected, actual);
	}
	
	public final void testGetSetInMinutesFromStartOfWeek(){
		int tuesday1430 = 2310;
		
		DateTime dt1 = new DateTime(DaysOfWeek.Tuesday, 14, 30);
		
		int acutalMinutes  =dt1.getMinuteFromStartOfWeek();
		assertEquals(tuesday1430, acutalMinutes);
		
		DateTime dt2 = new DateTime();
		dt2.setMinutesFromStartOfWeek(tuesday1430);
		
		assertEquals(dt1, dt2);
	}
	
	public final void testMove(){
		int expectedDifference = 200;
		
		DateTime dt1 = new DateTime(2000);
		DateTime dt2 = dt1.move(expectedDifference);
		
		int actualDifference = dt2.getMinuteFromStartOfWeek() - dt1.getMinuteFromStartOfWeek();
		
		assertEquals(expectedDifference, actualDifference);
		
		DateTime dt3 = dt1.move(DateTime.MinutesInWeek);
		int expectedMins1 = DateTime.MinutesInWeek-1;
		int actualMins1 = dt3.getMinuteFromStartOfWeek();
		assertEquals(expectedMins1, actualMins1);
		
		DateTime dt4 = dt1.move(-DateTime.MinutesInWeek);
		int expectedMins2 = 0;
		int actualMins2 = dt4.getMinuteFromStartOfWeek();
		assertEquals(expectedMins2, actualMins2);
		
		
	}

}
