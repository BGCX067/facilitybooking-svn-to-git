package tests.server.facilitybooking;

import com.sun.org.apache.bcel.internal.generic.FMUL;

import servr.facilitybooking.Booking;
import servr.facilitybooking.Facility;
import servr.facilitybooking.FacilityEvent;
import servr.facilitybooking.FacilityListener;
import servr.facilitybooking.FacilityManager;
import servr.facilitybooking.ServerException;

import communication.utils.Availability;
import communication.utils.DateTime;
import communication.utils.Id;

import junit.framework.TestCase;

public class FacilityTest extends TestCase {

	public FacilityTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testGetBookingById() throws ServerException {
		Facility facility = new Facility("f");

		DateTime monday1200 = new DateTime("Monday 12:00");
		DateTime monday1600 = new DateTime("Monday 16:00");
		Booking mondayBooking1 = facility.addBooking(monday1200, monday1600);

		DateTime tuesday1200 = new DateTime("Tuesday 12:00");
		DateTime tuesday1600 = new DateTime("Tuesday 16:00");
		Booking tuesdayBooking = facility.addBooking(tuesday1200, tuesday1600);

		DateTime wednesday1200 = new DateTime("Wednesday 12:00");
		DateTime wednesday1600 = new DateTime("Wednesday 16:00");
		Booking wednesdayBooking = facility.addBooking(wednesday1200,
				wednesday1600);

		Id expectedBookingId = tuesdayBooking.getBookingId();
		Booking actualBooking = facility.getBookingById(expectedBookingId);
		Id actualBookingId = actualBooking.getBookingId();
		assertEquals(expectedBookingId, actualBookingId);

		Id randomId = Id.getUniqueId();
		Booking nullBooking = facility.getBookingById(randomId);
		assertNull(nullBooking);
	}

	public final void testAddBooking() throws ServerException {
		Facility facility = new Facility("f");

		DateTime monday1200 = new DateTime("Monday 12:00");
		DateTime monday1600 = new DateTime("Monday 16:00");
		Booking mondayBooking1 = facility.addBooking(monday1200, monday1600);

		DateTime tuesday1200 = new DateTime("Tuesday 12:00");
		DateTime tuesday1600 = new DateTime("Tuesday 16:00");
		Booking tuesdayBooking = facility.addBooking(tuesday1200, tuesday1600);

		DateTime wednesday1600 = new DateTime("Wednesday 16:00");
		Booking wednesdayBooking = facility.addBooking(tuesday1600,
				wednesday1600);
	}

	public final void testGetAvailabilities1() throws ServerException {
		Facility facility = new Facility("f");

		DateTime monday1200 = new DateTime("Monday 12:00");
		DateTime monday1600 = new DateTime("Monday 16:00");
		Booking mondayBooking1 = facility.addBooking(monday1200, monday1600);

		DateTime tuesday1200 = new DateTime("Tuesday 12:00");
		DateTime tuesday1600 = new DateTime("Tuesday 16:00");
		Booking tuesdayBooking = facility.addBooking(tuesday1200, tuesday1600);

		DateTime wednesday1200 = new DateTime("Wednesday 12:00");
		DateTime wednesday1600 = new DateTime("Wednesday 16:00");
		Booking wednesdayBooking = facility.addBooking(wednesday1200,
				wednesday1600);

		Availability expectedAvail1 = new Availability(DateTime.StartOfWeek,
				monday1200);
		Availability expectedAvail2 = new Availability(monday1600, tuesday1200);
		Availability expectedAvail3 = new Availability(tuesday1600,
				wednesday1200);
		Availability expectedAvail4 = new Availability(wednesday1600,
				DateTime.EndOfWeek);

		Availability[] availabilities = facility.getAvailability();
		int expectedLength = 4;
		int actualLength = availabilities.length;
		assertEquals(expectedLength, actualLength);

		Availability actualAvail1 = availabilities[0];
		Availability actualAvail2 = availabilities[1];
		Availability actualAvail3 = availabilities[2];
		Availability actualAvail4 = availabilities[3];

		assertEquals(expectedAvail1, actualAvail1);
		assertEquals(expectedAvail2, actualAvail2);
		assertEquals(expectedAvail3, actualAvail3);
		assertEquals(expectedAvail4, actualAvail4);
	}

	public final void testGetAvailabilities2() throws ServerException {
		Facility facility = new Facility("f");

		DateTime monday1600 = new DateTime("Monday 16:00");
		Booking mondayBooking1 = facility.addBooking(DateTime.StartOfWeek,
				monday1600);

		DateTime tuesday1600 = new DateTime("Tuesday 16:00");
		Booking tuesdayBooking = facility.addBooking(monday1600, tuesday1600);

		DateTime wednesday1200 = new DateTime("Wednesday 12:00");
		Booking wednesdayBooking = facility.addBooking(wednesday1200,
				DateTime.EndOfWeek);

		Availability expectedAvail1 = new Availability(tuesday1600,
				wednesday1200);

		Availability[] availabilities = facility.getAvailability();
		int expectedLength = 1;
		int actualLength = availabilities.length;
		assertEquals(expectedLength, actualLength);

		Availability actualAvail1 = availabilities[0];

		assertEquals(expectedAvail1, actualAvail1);

	}

	public final void testGetAvailabilities3() throws ServerException {
		Facility facility = new Facility("f");

		Availability expectedAvail1 = new Availability(DateTime.StartOfWeek,
				DateTime.EndOfWeek);

		Availability[] availabilities = facility.getAvailability();
		int expectedLength = 1;
		int actualLength = availabilities.length;
		assertEquals(expectedLength, actualLength);

		Availability actualAvail1 = availabilities[0];

		assertEquals(expectedAvail1, actualAvail1);
	}

	public final void testGetAvailabilityForDay() throws ServerException {
		Facility facility = new Facility("f");

		DateTime monday1200 = new DateTime("Monday 12:00");
		DateTime monday1600 = new DateTime("Monday 16:00");
		facility.addBooking(monday1200, monday1600);

		DateTime monday2000 = new DateTime("Monday 20:00");
		DateTime monday2300 = new DateTime("Monday 23:00");
		facility.addBooking(monday2000, monday2300);

		DateTime tuesday0800 = new DateTime("Tuesday 08:00");
		DateTime tuesday1200 = new DateTime("Tuesday 12:00");
		facility.addBooking(tuesday0800, tuesday1200);

		DateTime wednesday1200 = new DateTime("Wednesday 12:00");
		DateTime wednesday1600 = new DateTime("Wednesday 16:00");
		facility.addBooking(wednesday1200, wednesday1600);
		
		

		Availability expectedAvail1 = new Availability(DateTime.StartOfWeek,monday1200);
		Availability expectedAvail2 = new Availability(monday1600, monday2000);
		Availability expectedAvail3 = new Availability(monday2300,	tuesday0800);

		DateTime monday = new DateTime("Monday");
		Availability[] availabilities = facility.getAvailabilityForDay(monday);
		int expectedLength = 3;
		int actualLength = availabilities.length;
		assertEquals(expectedLength, actualLength);

		Availability actualAvail1 = availabilities[0];
		Availability actualAvail2 = availabilities[1];
		Availability actualAvail3 = availabilities[2];
	

		assertEquals(expectedAvail1, actualAvail1);
		assertEquals(expectedAvail2, actualAvail2);
		assertEquals(expectedAvail3, actualAvail3);

	}
	
//	public final void testFacilityListener() throws ServerException, InterruptedException{
//		FacilityManager fm = new FacilityManager();
//		Facility f1 = fm.getAllFacilities()[0];
//		f1.addFacilityListener(new TestFacilityListener(), 2000);
//		f1.getAllBookings()[0].shift(12);
//		Thread.sleep(3000);
//		f1.getAllBookings()[0].shift(12);
//		
//	}
//	
//	private class TestFacilityListener implements FacilityListener{
//
//		@Override
//		public void FacilityChanged(FacilityEvent event) {
//			System.out.println("facilityChanged called with: " + event.getCause().name() + ", " + event.getBooking().getFacility().getName());
//		}
//		
//	}

}
