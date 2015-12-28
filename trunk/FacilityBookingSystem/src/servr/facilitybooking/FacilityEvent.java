package servr.facilitybooking;

public class FacilityEvent {
	private FacilityEventCause m_cause;
	private Booking m_booking;

	
	public FacilityEvent(Booking booking, FacilityEventCause cause) {
		setBooking(booking);
		setCause(cause);
	}
	
	public Booking getBooking() {
		return m_booking;
	}

	private void setBooking(Booking booking) {
		m_booking = booking;
	}

	public FacilityEventCause getCause() {
		return m_cause;
	}

	private void setCause(FacilityEventCause cause) {
		m_cause = cause;
	}
}
