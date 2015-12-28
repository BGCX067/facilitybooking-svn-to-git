package servr.facilitybooking;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.swing.Timer;

import communication.utils.Availability;
import communication.utils.DateTime;
import communication.utils.DaysOfWeek;
import communication.utils.Id;

public class Facility{

	protected String m_facilityName;
	protected TreeSet<Booking> m_bookings = new TreeSet<Booking>();
	protected List<FacilityListener> m_facilityListeners;
	
	public Facility(String facilityName) {
		m_facilityName = facilityName;

		setFacilityListeners(new ArrayList<FacilityListener>());
	}

	public List<FacilityListener> getFacilityListeners() {
		return m_facilityListeners;
	}

	private void setFacilityListeners(List<FacilityListener> facilityListeners) {
		m_facilityListeners = facilityListeners;
	}

	public void addFacilityListener(FacilityListener listener, int listeningInterval) {
		getFacilityListeners().add(listener);
	
		TimerPerformer timerPerformer = new TimerPerformer(listener, this);
		Timer timer = new Timer(listeningInterval, timerPerformer) ;
		timerPerformer.setTimer(timer);
		timer.start();
		
	}
	
	public void removeFacilityListner(FacilityListener listener){
		getFacilityListeners().remove(listener);
	}

	protected void fireFacilityListners(Booking booking, FacilityEventCause cause) {
		FacilityEvent event = new FacilityEvent(booking, cause);

		for (FacilityListener facilityListener : getFacilityListeners()) {
			facilityListener.FacilityChanged(event);
		}
	}

	protected TreeSet<Booking> getBookings() {
		return m_bookings;
	}

	protected void addBooking_NO_CHECK(Booking newBooking) {
		getBookings().add(newBooking);
	}

	protected void addBooking(Booking bookingToAdd) throws ServerException {
		Booking floorBooking = getBookings().floor(bookingToAdd); // closest
																	// earlier
																	// booking
		Booking ceilBooking = getBookings().ceiling(bookingToAdd); // closest
																	// later
																	// booking

		boolean fitsBetween = bookingToAdd.fitsBetween(floorBooking,
				ceilBooking);
		if (fitsBetween) {
			addBooking_NO_CHECK(bookingToAdd);
			fireFacilityListners(bookingToAdd, FacilityEventCause.BookingAdded);
		} else {
			// booking cannot be inserted
			throw new ServerException(
					"Booking failed: Facility is not available at the required time span.");
		}
	}

	public boolean removeBooking(Booking booking) {
		//fireFacilityListners(booking, FacilityEventCause.BookingRemoved);
		return getBookings().remove(booking);
	}

	public Booking addBooking(DateTime from, DateTime to)
			throws ServerException {
		Booking newBooking = new Booking(this, from, to);
		addBooking(newBooking);
		
		return newBooking;
	}

	public String getName() {
		return m_facilityName;
	}

	public Booking getBookingById(Id bookingId) {
		getBookings().iterator();
		for (Iterator<Booking> iterator = getBookings().iterator(); iterator
				.hasNext();) {
			Booking booking = iterator.next();
			boolean idsMatch = bookingId.equals(booking.getBookingId());
			if (idsMatch) {
				return booking;
			}
		}

		return null;
	}

	public Availability[] getAvailabilityForDay(DateTime day) {

		Availability[] availabilities = getAvailability();
		ArrayList<Availability> availabilitiesForDay = new ArrayList<Availability>();

		for (Availability avail : availabilities) {
			DaysOfWeek dayFrom = avail.getFrom().dayOfWeek;
			DaysOfWeek dayTo = avail.getTo().dayOfWeek;
			DaysOfWeek dayAvail = day.dayOfWeek;

			boolean daysMatch = ((dayFrom == dayAvail) || (dayTo == dayAvail));
			if (daysMatch) {
				availabilitiesForDay.add(avail);
			}
		}

		return availabilitiesForDay.toArray(new Availability[0]);
	}

	public Availability[] getAvailability() {

		int numberOfBookings = getBookings().size();
		boolean isFullyAvailable = (numberOfBookings == 0);
		if (isFullyAvailable) {
			Availability entireWeek = new Availability(DateTime.StartOfWeek,
					DateTime.EndOfWeek);
			return new Availability[] { entireWeek };
		}

		ArrayList<Availability> availabilities = new ArrayList<Availability>();
		DateTime availableFrom = DateTime.StartOfWeek;
		for (Booking booking : getBookings()) {
			DateTime availableTo = booking.getFrom();
			insertToAvailabilities(availableFrom, availableTo, availabilities);
			availableFrom = booking.getTo();
		}

		// check if available in the end of the week
		insertToAvailabilities(availableFrom, DateTime.EndOfWeek,
				availabilities);
		Availability[] toReturn = new Availability[0];
		toReturn = availabilities.toArray(toReturn);

		return toReturn;
	}

	private void insertToAvailabilities(DateTime availableFrom,
			DateTime availableTo, ArrayList<Availability> availabilities) {
		Availability potencialAvail = new Availability(availableFrom,
				availableTo);

		boolean hasDuration = (potencialAvail.getDuration() > 0);
		if (hasDuration) {
			availabilities.add(potencialAvail);
		}
	}

	public Booking[] getAllBookings() {
		return getBookings().toArray(new Booking[0]);
	}
	
	private class TimerPerformer implements ActionListener{
		private FacilityListener m_listener;
		private Facility m_facility;
		private Timer m_timer;
		
		public TimerPerformer( FacilityListener listener, Facility facility) {
			m_listener = listener;
			m_facility = facility;
		
		}
		
		public void setTimer(Timer timer){
			m_timer = timer;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Listener removed from a facility " + getName());
			m_timer.stop();
			m_facility.removeFacilityListner(m_listener);
		}
		
	}

}
