package communication.utils;
import org.omg.CORBA.IntHolder;

public class ByteConversions {
	public static final int SizeOfIntegerInBytes = 4;
	public static final int SizeOfDaysOfWeekInBytes = SizeOfIntegerInBytes;
	public static final int SizeOfDateTimeInBytes = DateTime.getSizeOfDayTimeInBytes(); 

	public static final byte[] intToByteArray(int value) {
		byte[] bytes = new byte[] { 
				(byte) (value >>> 24), 
				(byte) (value >>> 16),
				(byte) (value >>> 8), 
				(byte) value }; 
		return  bytes;
	}
	
	static final int UnsignedInt = 0x000000FF;
	public static int byteArrayToInt(byte[] byteArray){
		//if(byteArray.length != 4) throw new ArgumentException("Byte array must be 4 elements long");
		int intValue = 0;
		intValue = intValue | (((int)byteArray[0] & UnsignedInt ) << 24);
		intValue = intValue | (((int)byteArray[1] & UnsignedInt ) << 16);
		intValue = intValue | (((int)byteArray[2] & UnsignedInt ) << 8);
		intValue = intValue | (((int)byteArray[3] & UnsignedInt ));
		return intValue;
	}

	//tested in StringFieldWrapperTest
	public static byte[] stringToByteArray(String value){
		byte[] valueInBytes = value.getBytes();

		int sizeInBytes = valueInBytes.length;
		byte[] sizeInBytesBytes = ByteConversions.intToByteArray(sizeInBytes);

		byte[] bytesSerialized = Utils.mergeArrays(sizeInBytesBytes, valueInBytes);
		
		return bytesSerialized;
	}
	
	//tested in StringFieldWrapperTest
	public static String byteArrayToString(byte[] byteArray, IntHolder OUT_byteUsedCount){
		byte[] bytesCountInBytes = Utils.cutOutSubArray(0, ByteConversions.SizeOfIntegerInBytes, byteArray); 
		int bytesCount = ByteConversions.byteArrayToInt(bytesCountInBytes);
		
		byte[] valueInBytes = Utils.cutOutSubArray(ByteConversions.SizeOfIntegerInBytes, bytesCount, byteArray);
		String value = new String(valueInBytes);
		
		OUT_byteUsedCount.value = bytesCountInBytes.length + valueInBytes.length;
		return value;
	}
	
	public static byte[] daysOfWeekToByteArray(DaysOfWeek dayOfWeek){
		int dayOfWeekOrdinal = dayOfWeek.ordinal();
		byte[] dayOfWeekOrdinalInBytes = intToByteArray(dayOfWeekOrdinal);
		return dayOfWeekOrdinalInBytes;
	}
	
	public static DaysOfWeek byteArrayToDaysOfWeek(byte[] byteArray){
		int dayOfWeekOrdinal = byteArrayToInt(byteArray);
		DaysOfWeek dayOfWeek = DaysOfWeek.values()[dayOfWeekOrdinal];
		
		return dayOfWeek;
	}
	
	public static byte[] dateTimeToByteArray(DateTime dateTime){
		byte[] dateTimeInBytes = new byte[0];
		
		byte[] dayOfWeekInBytes = daysOfWeekToByteArray(dateTime.dayOfWeek);
		byte[] hoursInBytes = intToByteArray(dateTime.hours);
		byte[] minutesInBytes = intToByteArray(dateTime.minutes);
		
		dateTimeInBytes = Utils.mergeArrays(dateTimeInBytes, dayOfWeekInBytes);
		dateTimeInBytes = Utils.mergeArrays(dateTimeInBytes, hoursInBytes);
		dateTimeInBytes = Utils.mergeArrays(dateTimeInBytes, minutesInBytes);
		
		return dateTimeInBytes;
	}
	
	public static DateTime byteArrayToDateTime(byte[] byteArray){
		byte[] dayOfWeekInBytes = Utils.cutOutSubArray(0, SizeOfDaysOfWeekInBytes, byteArray);
		byte[] hoursInBytes = Utils.cutOutSubArray(SizeOfDaysOfWeekInBytes, SizeOfIntegerInBytes, byteArray);
		byte[] minutesInBytes = Utils.cutOutSubArray(SizeOfIntegerInBytes+SizeOfDaysOfWeekInBytes, SizeOfIntegerInBytes, byteArray);
		
		DateTime dateTime = new DateTime();
		dateTime.dayOfWeek = byteArrayToDaysOfWeek(dayOfWeekInBytes);
		dateTime.hours = byteArrayToInt(hoursInBytes);
		dateTime.minutes = byteArrayToInt(minutesInBytes);
		
		return dateTime;
	}
}
