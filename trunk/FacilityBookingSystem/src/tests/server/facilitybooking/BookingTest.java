package tests.server.facilitybooking;

import servr.facilitybooking.Booking;
import servr.facilitybooking.Facility;
import servr.facilitybooking.ServerException;
import communication.utils.Availability;
import communication.utils.DateTime;

import junit.framework.TestCase;

public class BookingTest extends TestCase {

	public BookingTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testIsFittingBetween() throws ServerException {
		String facilityName = "Three horns hostel";
		Facility facility = new Facility(facilityName);

		DateTime monday1200 = new DateTime("Monday 12:00");
		DateTime monday1600 = new DateTime("Monday 16:00");
		Booking mondayBooking1 = facility.addBooking( monday1200, monday1600);


		DateTime tuesday1200 = new DateTime("Tuesday 12:00");
		DateTime tuesday1600 = new DateTime("Tuesday 16:00");
		Booking tuesdayBooking1 = facility.addBooking( tuesday1200,
				tuesday1600);

		DateTime wednesday1200 = new DateTime("Wednesday 12:00");
		DateTime wednesday1600 = new DateTime("Wednesday 16:00");
		Booking wednesdayBooking = facility.addBooking(wednesday1200,
				wednesday1600);

		boolean tuesdayBookingFits = tuesdayBooking1.fitsBetween(
				mondayBooking1, wednesdayBooking);
		assertTrue(tuesdayBookingFits);

		DateTime monday1400 = new DateTime("Monday 16:01");
		DateTime monday1800 = new DateTime("Monday 18:00");
		Booking mondayBooking2 = facility.addBooking(monday1400, monday1800);

		boolean mondayBooking2Fits = mondayBooking2.fitsBetween(mondayBooking1,
				tuesdayBooking1);
		assertTrue(mondayBooking2Fits);

		mondayBooking2Fits = mondayBooking2.fitsBetween(null, wednesdayBooking);
		assertTrue(mondayBooking2Fits);

		mondayBooking2Fits = mondayBooking2.fitsBetween(null, null);
		assertTrue(mondayBooking2Fits);

		mondayBooking2Fits = mondayBooking2.fitsBetween(tuesdayBooking1,
				wednesdayBooking);
		assertFalse(mondayBooking2Fits);

		DateTime tuesday0800 = new DateTime("Tuesday 08:00");
		DateTime tuesday1400 = new DateTime("Tuesday 14:00");
		try{
		Booking tuesdayBooking2 = facility.addBooking(tuesday0800,
				tuesday1400);
		fail("Exception should been raised.");
		}catch(ServerException e){
			assertNotNull(e);
		}
		
		
	}

	public final void testGetDuration() throws ServerException {
		int expected = 100;
		DateTime from = new DateTime(8000);
		DateTime to = from.move(expected);

		Facility f = new Facility("f");
		Booking booking = f.addBooking(from, to);

		int actual = booking.getDuration();

		assertEquals(expected, actual);
	}

	public final void testShift1() {
		DateTime from = new DateTime(8000);
		DateTime to = from.move(1000);
		try {
			Facility f = new Facility("f");

			Booking booking = f.addBooking(from, to);

			booking.shift(400);
			DateTime expectedFrom = from.move(400);
			DateTime expectedTo = to.move(400);
			DateTime actualFrom = booking.getFrom();
			DateTime actualTo = booking.getTo();

			assertEquals(expectedFrom, actualFrom);
			assertEquals(expectedTo, actualTo);
		} catch (ServerException e) {
			e.printStackTrace();
		}
	}

	public final void testShift2() throws ServerException {

		Facility facility = new Facility("f");

		DateTime monday1200 = new DateTime("Monday 12:00");
		DateTime monday1600 = new DateTime("Monday 16:00");
		Booking mondayBooking1 = facility.addBooking( monday1200, monday1600);

		DateTime tuesday1200 = new DateTime("Tuesday 12:00");
		DateTime tuesday1600 = new DateTime("Tuesday 16:00");
		Booking tuesdayBooking = 	facility.addBooking(tuesday1200, tuesday1600);

		DateTime wednesday1200 = new DateTime("Wednesday 12:00");
		DateTime wednesday1600 = new DateTime("Wednesday 16:00");
		Booking wednesdayBooking = 	facility.addBooking(wednesday1200, wednesday1600);
		

		try {
			tuesdayBooking.shift(22 * 60); // 22 hours so tue and wed collide
		} catch (ServerException e) {
			assertNotNull(e); // exception was raised
			DateTime expectedFrom = tuesday1200;
			DateTime expectedTo = tuesday1600;
			DateTime actualFrom = tuesdayBooking.getFrom();
			DateTime actualTo = tuesdayBooking.getTo();

			assertEquals(expectedFrom, actualFrom);
			assertEquals(expectedTo, actualTo);
		}
	}

	public final void testShift3() throws ServerException {

		Facility facility = new Facility("f");

		DateTime monday1200 = new DateTime("Monday 12:00");
		DateTime monday1600 = new DateTime("Monday 16:00");
		Booking mondayBooking1 = facility.addBooking( monday1200, monday1600);

		DateTime tuesday1200 = new DateTime("Tuesday 12:00");
		DateTime tuesday1600 = new DateTime("Tuesday 16:00");
		Booking tuesdayBooking = 	facility.addBooking(tuesday1200, tuesday1600);

		DateTime wednesday1200 = new DateTime("Wednesday 12:00");
		DateTime wednesday1600 = new DateTime("Wednesday 16:00");
		Booking wednesdayBooking = 	facility.addBooking(wednesday1200, wednesday1600);

		try {
			int minutesToShift = 48 * 60;
			tuesdayBooking.shift(minutesToShift); // 48 hours so tue and wed do not
											// collide
			DateTime expectedFrom = tuesday1200.move(minutesToShift);
			DateTime expectedTo = tuesday1600.move(minutesToShift);
			DateTime actualFrom = tuesdayBooking.getFrom();
			DateTime actualTo = tuesdayBooking.getTo();

			assertEquals(expectedFrom, actualFrom);
			assertEquals(expectedTo, actualTo);
		} catch (ServerException e) {
			fail("Should not get here");
		}
	}
	
	public final void testShift4() throws ServerException {

		Facility facility = new Facility("f");

		DateTime monday1200 = new DateTime("Monday 12:00");
		DateTime monday1600 = new DateTime("Monday 16:00");
		Booking mondayBooking1 = facility.addBooking( monday1200, monday1600);

		DateTime tuesday1200 = new DateTime("Tuesday 12:00");
		DateTime tuesday1600 = new DateTime("Tuesday 16:00");
		Booking tuesdayBooking = 	facility.addBooking(tuesday1200, tuesday1600);

		DateTime wednesday1200 = new DateTime("Wednesday 12:00");
		DateTime wednesday1600 = new DateTime("Wednesday 16:00");
		Booking wednesdayBooking = 	facility.addBooking(wednesday1200, wednesday1600);

		try {
			int minutesToShift = 24 * 60;
			tuesdayBooking.shift(minutesToShift); // 24 hours so tue and wed do 
											// overlap
			DateTime expectedFrom = tuesday1200.move(minutesToShift);
			DateTime expectedTo = tuesday1600.move(minutesToShift);
			DateTime actualFrom = tuesdayBooking.getFrom();
			DateTime actualTo = tuesdayBooking.getTo();

			assertEquals(expectedFrom, actualFrom);
			assertEquals(expectedTo, actualTo);
		} catch (ServerException e) {
			assertNotNull(e); // exception was raised
			
			DateTime expectedFrom = tuesday1200;
			DateTime expectedTo = tuesday1600;
			DateTime actualFrom = tuesdayBooking.getFrom();
			DateTime actualTo = tuesdayBooking.getTo();

			assertEquals(expectedFrom, actualFrom);
			assertEquals(expectedTo, actualTo);
		}
	}
	
	public final void testExtend1() throws ServerException{
		Facility facility = new Facility("f");

		DateTime monday1200 = new DateTime("Monday 12:00");
		DateTime monday1600 = new DateTime("Monday 16:00");
		Booking mondayBooking1 = facility.addBooking( monday1200, monday1600);

		int minutesToExtend = 100;
		mondayBooking1.extend(minutesToExtend);
		
		DateTime expectedTo = monday1600.move(100);
		DateTime actualTo = mondayBooking1.getTo();
		assertEquals(expectedTo, actualTo);
		
		DateTime expectedFrom = monday1200;
		DateTime actualFrom = mondayBooking1.getFrom();
		assertEquals(expectedFrom, actualFrom);
	}
	
	public final void testExtend2() throws ServerException{
		Facility facility = new Facility("f");
		
		DateTime monday1200 = new DateTime("Monday 12:00");
		DateTime monday1600 = new DateTime("Monday 16:00");
		Booking mondayBooking1 = facility.addBooking( monday1200, monday1600);

		DateTime tuesday1200 = new DateTime("Tuesday 12:00");
		DateTime tuesday1600 = new DateTime("Tuesday 16:00");
		Booking tuesdayBooking = facility.addBooking(tuesday1200, tuesday1600);

		DateTime wednesday1200 = new DateTime("Wednesday 12:00");
		DateTime wednesday1600 = new DateTime("Wednesday 16:00");
		Booking wednesdayBooking = 	facility.addBooking(wednesday1200, wednesday1600);
		
		int minutesToExtend = DateTime.MinutesInDay;
		try{
		mondayBooking1.extend(minutesToExtend);
		} catch (ServerException e) {
			assertNotNull(e); // exception was raised
			DateTime expectedFrom = monday1200;
			DateTime expectedTo = monday1600;
			DateTime actualFrom = mondayBooking1.getFrom();
			DateTime actualTo = mondayBooking1.getTo();

			assertEquals(expectedFrom, actualFrom);
			assertEquals(expectedTo, actualTo);
		}
	}
	
	public final void testExtend3() throws ServerException{
		Facility facility = new Facility("f");

		DateTime monday1200 = new DateTime("Monday 12:00");
		DateTime monday1600 = new DateTime("Monday 16:00");
		Booking mondayBooking1 = facility.addBooking( monday1200, monday1600);

		int minutesToExtend = -5 * DateTime.MinutesInHour;
		try{
			mondayBooking1.extend(minutesToExtend);
		}
		catch(ServerException e){
			DateTime expectedTo = monday1600;
			DateTime actualTo = mondayBooking1.getTo();
			assertEquals(expectedTo, actualTo);
			
			DateTime expectedFrom = monday1200;
			DateTime actualFrom = mondayBooking1.getFrom();
			assertEquals(expectedFrom, actualFrom);
		}
	}

}
