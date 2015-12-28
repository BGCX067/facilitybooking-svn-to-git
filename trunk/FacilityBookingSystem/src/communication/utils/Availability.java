package communication.utils;

public class Availability {
	protected DateTime m_from;
	protected DateTime m_to;
	
	public Availability(DateTime from, DateTime to){
		setFrom(from);
		setTo(to);
	}
	
	public DateTime getFrom() {
		return m_from;
	}
	
	protected void setFrom(DateTime from) {
		this.m_from = from;
	}
	
	public DateTime getTo() {
		return m_to;
	}
	
	protected void setTo(DateTime to) {
		this.m_to = to;
	}
	

	
	public int getDuration(){
		int toInMins = getTo().getMinuteFromStartOfWeek();
		int fromInMins = getFrom().getMinuteFromStartOfWeek();
		int duration = toInMins - fromInMins;
		
		return duration;
	}
	
	@Override
	public String toString(){
		return getFrom().toString() + " - " + getTo().toString();
	}
	
	public boolean equals(Object o){
		Availability toCompare = (Availability) o;
		boolean fromsEqual = getFrom().equals(toCompare.getFrom());
		boolean tosEqual = getTo().equals(toCompare.getTo());
		
		return fromsEqual && tosEqual;
	}
	
	public static int getSizeOfAvailabilityInBytes(){
		return 2 * ByteConversions.SizeOfDateTimeInBytes;
	}
	
	public byte[] serialize(){
		byte[] fromAsBytes = ByteConversions.dateTimeToByteArray(getFrom());
		byte[] toAsBytes = ByteConversions.dateTimeToByteArray(getTo());
		
		byte[] serializedAvail = Utils.mergeArrays(fromAsBytes, toAsBytes);
		return serializedAvail;
	}
	
	public static Availability deserialize(byte[] serializedAvail){
		byte[] fromAsBytes = Utils.cutOutSubArray(0, ByteConversions.SizeOfDateTimeInBytes, serializedAvail);
		byte[] toAsBytes = Utils.cutOutSubArray(ByteConversions.SizeOfDateTimeInBytes, ByteConversions.SizeOfDateTimeInBytes, serializedAvail);
		DateTime from = ByteConversions.byteArrayToDateTime(fromAsBytes);
		DateTime to = ByteConversions.byteArrayToDateTime(toAsBytes);
		
		return new Availability(from, to);
	}
}
