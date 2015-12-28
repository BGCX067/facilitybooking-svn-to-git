package servr.facilitybooking;

import java.util.ArrayList;

import communication.utils.DateTime;
import communication.utils.Id;

public class FacilityManager {
	
	private Facility[] m_facilities;

	public Facility[] getAllFacilities(){
		return m_facilities;
	}
	
	public FacilityManager() {
		ArrayList<Facility> facilities = new ArrayList<Facility>(5);
		Facility facility1 = new Facility("F1");
		Facility facility2 = new Facility("F2");
		Facility facility3 = new Facility("F3");
		Facility facility4 = new Facility("F4");
		Facility facility5 = new Facility("F5");
		
		facilities.add(facility1);
		facilities.add(facility2);
		facilities.add(facility3);
		facilities.add(facility4);
		facilities.add(facility5);
		
		try {
			facility1.addBooking(new DateTime("Monday 12:00"), new DateTime("Monday 18:00"));
			facility1.addBooking(new DateTime("Thursday 8:00"), new DateTime("Sunday 10:00"));
			facility2.addBooking(new DateTime("Wednesday 8:00"), new DateTime("Friday 18:00"));
		} catch (ServerException e) {
			//God damn java's exceptions ;-) 
			e.printStackTrace();
		}
		m_facilities = facilities.toArray(new Facility[0]);
	}
	
	private static FacilityManager ms_facilityManager = new FacilityManager();
	
	public static FacilityManager getFacilityManager(){
		return ms_facilityManager;
	}
	
	public String[] getAllFacilitiesNames(){
		String[] facilitiesNames = new String[m_facilities.length];
		for (int i = 0; i < m_facilities.length; i++) {
			Facility facility = m_facilities[i];
			facilitiesNames[i] = facility.getName();
		}
		
		return facilitiesNames;
	}
	
	public Facility getFacilityByName(String facilityName){
		for (int i = 0; i < m_facilities.length; i++) {
			Facility facility = m_facilities[i];
			boolean namesMatches = (facility.getName().equals( facilityName));
			if(namesMatches){
				return facility;
			}
		}
		
		return null;
	}
	
	public Booking getBookingById(Id bookingId){
		for (int i = 0; i < m_facilities.length; i++) {
			Facility facility = m_facilities[i];
			Booking booking = facility.getBookingById(bookingId);
			boolean bookingFound = (booking != null);
			if(bookingFound){
				return booking;
			}
		}
		
		return null;
	}
	
}
