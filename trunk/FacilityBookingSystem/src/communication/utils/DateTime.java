package communication.utils;

public class DateTime implements Comparable<DateTime> {
	public static final int MinutesInHour = 60;
	public static final int MinutesInDay = MinutesInHour * 24;
	public static final int MinutesInWeek = MinutesInDay * 7; 
	public static final DateTime StartOfWeek = new DateTime(0);
	public static final DateTime EndOfWeek = new DateTime(MinutesInWeek - 1);

	public DaysOfWeek dayOfWeek = DaysOfWeek.Monday; // default value
	public int hours;
	public int minutes;

	public DateTime() {
	}

	public DateTime(String dateTimeInString) {
		set(dateTimeInString);
	}

	public DateTime(DaysOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public DateTime(int minutesFromStartOfWeek) {
		setMinutesFromStartOfWeek(minutesFromStartOfWeek);
	}

	public static int getSizeOfDayTimeInBytes() {
		return ByteConversions.SizeOfDaysOfWeekInBytes + 2
				* ByteConversions.SizeOfIntegerInBytes;
	}

	public int getMinuteFromStartOfWeek() {
		int daysInMinutes = dayOfWeek.ordinal() * MinutesInDay;
		int hoursInMinutes = hours * MinutesInHour;
		int dateTimeInMinutesFromStartOfWeek = daysInMinutes + hoursInMinutes
				+ minutes;

		return dateTimeInMinutesFromStartOfWeek;
	}

	public void setMinutesFromStartOfWeek(int minutes) {
		minutes = (minutes >= 0) ? minutes : 0;
		minutes = (minutes < MinutesInWeek) ? minutes : MinutesInWeek-1;

		int dayOrdinal = minutes / (MinutesInDay);
		dayOfWeek = DaysOfWeek.values()[dayOrdinal];
		this.hours = (minutes - dayOrdinal * MinutesInDay) / MinutesInHour;
		this.minutes = minutes - dayOrdinal * MinutesInDay - hours
				* MinutesInHour;
	}

	public void set(String dateTimeInString) {
		int indexOfWhiteSpace = dateTimeInString.indexOf(" ");
		String dayOfWeekInString;

		boolean timeContained = (indexOfWhiteSpace > -1);
		if (timeContained) {
			dayOfWeekInString = dateTimeInString
					.substring(0, indexOfWhiteSpace);

			int indexOfColon = dateTimeInString.indexOf(":");
			String hoursInString = dateTimeInString.substring(
					indexOfWhiteSpace + 1, indexOfColon);

			String minutesInString = dateTimeInString
					.substring(indexOfColon + 1);

			dayOfWeek = DaysOfWeek.valueOf(dayOfWeekInString);
			hours = Integer.parseInt(hoursInString);
			minutes = Integer.parseInt(minutesInString);
		} else {
			dayOfWeek = DaysOfWeek.valueOf(dateTimeInString);
			hours = 0;
			minutes = 0;
		}
	}

	public DateTime(DaysOfWeek dayOfWeek, int hours, int minutes) {
		this.dayOfWeek = dayOfWeek;
		this.hours = hours;
		this.minutes = minutes;
	}
	
	public DateTime move(int minutesToMove){
		int minutesFromStartOfWeekMoved = getMinuteFromStartOfWeek() + minutesToMove;
		
		DateTime extendedDateTime = new DateTime(minutesFromStartOfWeekMoved);
		return extendedDateTime;
	}

	@Override
	public String toString() {
		String dateTimeInString = "";
		dateTimeInString += dayOfWeek.name() + " ";
		dateTimeInString += hours + ":" + minutes;

		return dateTimeInString;
	}

	@Override
	public boolean equals(Object o) {
		DateTime dateTime = (DateTime) o;
		boolean daysEqual = dayOfWeek.ordinal() == dateTime.dayOfWeek.ordinal();
		boolean hoursEqual = hours == dateTime.hours;
		boolean minutesEqual = minutes == dateTime.minutes;

		return daysEqual && hoursEqual && minutesEqual;
	}

	@Override
	public int compareTo(DateTime o) {
		Integer thisAsInt = getMinuteFromStartOfWeek();
		Integer oAsInt = o.getMinuteFromStartOfWeek();
		
		return thisAsInt.compareTo(oAsInt);
	}
}
