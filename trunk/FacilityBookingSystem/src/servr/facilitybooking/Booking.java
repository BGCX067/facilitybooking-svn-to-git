package servr.facilitybooking;


import communication.utils.DateTime;
import communication.utils.Id;

public class Booking implements Comparable<Booking>{
	Id m_bookingId;
	Facility m_facility;
	DateTime m_from;
	DateTime m_to;
	
	Booking(Facility facility, DateTime from, DateTime to){
		m_bookingId = Id.getUniqueId();
		m_facility = facility;
		m_from = from;
		m_to = to;
	}
	
	public void shift(int minutesToShift) throws ServerException{
		DateTime fromShifted = getFrom().move(minutesToShift);
		DateTime toShifted = getTo().move(minutesToShift);
		Booking shiftedBooking = new Booking(getFacility(), fromShifted, toShifted);
			
		boolean canBeReplacedByShiftedBooking = canBeReplaceByBooking(shiftedBooking);
		
		if(canBeReplacedByShiftedBooking){
			setFrom(fromShifted);
			setTo(toShifted);
			getFacility().fireFacilityListners(shiftedBooking, FacilityEventCause.BookingChanged);
		}
		else{
			throw new ServerException("Shifting failed: Facility is not available at the required time span.");
		}
	}
	
	public void extend(int minutesToExtend) throws ServerException{
		DateTime toExtended = getTo().move(minutesToExtend);
		Booking extendedBooking = new Booking(getFacility(), getFrom(), toExtended);
		
		boolean canBeReplacedByExtendedBooking = canBeReplaceByBooking(extendedBooking);
		
		if(canBeReplacedByExtendedBooking){
			boolean hasLength = extendedBooking.getDuration() > 0;
			if(!hasLength){
				throw new ServerException("Shifting failed: duration of booking is 0!");
			}else{
				setTo(toExtended);
				getFacility().fireFacilityListners(extendedBooking, FacilityEventCause.BookingChanged);
			}
		}else{
			throw new ServerException("Shifting failed: Facility is not available at the required time span.");
		}
	}
	
	/**
	 * 
	 * @return duration in minutes from beginning of the week
	 */
	public int getDuration(){
		int toInMins = getTo().getMinuteFromStartOfWeek();
		int fromInMins = getFrom().getMinuteFromStartOfWeek();
		int duration = toInMins - fromInMins;
		
		return duration;
	}
	
	/**
	 * @return true if original booking can be replaced by this booking
	 */
	boolean canBeReplaceByBooking(Booking replacingBooking){
		//remove original booking
		getFacility().removeBooking(this);
		
		Booking floorBooking = getFacility().getBookings().floor(replacingBooking); //closest earlier booking
		Booking ceilBooking = getFacility().getBookings().ceiling(replacingBooking); //closest later booking
		
		boolean replacedBookingFits = replacingBooking.fitsBetween(floorBooking, ceilBooking);
		boolean canBeReplaced = replacedBookingFits;
		
		//add original back
		getFacility().addBooking_NO_CHECK(this);
		
		return canBeReplaced;
	}
	
	public boolean fitsBetween(Booking floorBooking, Booking ceilBooking){
		//find out if the shifted booking would collide with booking from the bottom
		boolean freeFromBottom;
		if(floorBooking != null ){
			DateTime floorBookingTo = floorBooking.getTo();
			freeFromBottom = (floorBookingTo.compareTo(getFrom()) != 1); //floorBookingTo <= getFrom()
		}else{
			freeFromBottom = true;
		}
		
		//find out if the shifted booking would collide with booking from the top 
		boolean freeFromTop;
		if(ceilBooking != null){
			DateTime ceilBookingFrom = ceilBooking.getFrom();
			freeFromTop = (getTo().compareTo(ceilBookingFrom) != 1); // getTo() < ceilBookingFrom
		}else{
			freeFromTop = true;
		}
		
		boolean fitsBetween = (freeFromBottom && freeFromTop);
		
		return fitsBetween;
	}

	public Id getBookingId() {
		return m_bookingId;
	}

	public Facility getFacility() {
		return m_facility;
	}

	public DateTime getFrom() {
		return m_from;
	}

	public DateTime getTo() {
		return m_to;
	}
	
	void setTo(DateTime to) {
		this.m_to = to;
	}
	
	void setBookingId(Id id) {
		m_bookingId = id;
	}

	void setFacility(Facility facility) {
		this.m_facility = facility;
	}

	void setFrom(DateTime from) {
		this.m_from = from;
	}

	@Override
	public int compareTo(Booking o) {
		return m_from.compareTo(o.m_from);
	}
	
	@Override
	public String toString(){
		return getFrom().toString() + " - " + getTo().toString()+ " [id: " + getBookingId() + "]";
	}
	
}
