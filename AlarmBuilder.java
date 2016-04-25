package alarmclock;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public final class AlarmBuilder {

	private static final String ALARM_DATE_INVALID_MSG = "Alarm date is not valid.";

	public AlarmBuilder() {
	}
	
	public WeekScope every (){
		return new Weakscope(new GregorianCalendar(), new WeekAndDayMemorie());
	}
	
	public MinuteScope in (){
		return new MinuteScope();
	}
	
	public TimeWrapperScope on (Day... days){
		WeekAndDayMemorie wadm = new WeekAndDayMemorie();
		GregorianCalendar c = new GregorianCalendar();
		for (Day day : days) {wadm.addDay(day);}
		int oldVal = c.get(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_WEEK, (days[0].ordinal() + 2 > 7) ? 1 : days[0].ordinal() + 2);
		if (oldVal > c.get(Calendar.DAY_OF_MONTH)) {c.add(Calendar.WEEK_OF_YEAR, 1);}
		return new TimeWrapperScope(c, wadm);
	}
	
	public YearScope on (){
		return new YearScope(new GregorianCalendar());
	}
	
	public TimeWrapperScope today (){
		return new TimeWrapperScope(new GregorianCalendar(), null);
	}
	
	
	public final class MinuteScope {
	
			
		private MinuteScope (){
		}
		
		public AlarmProperties minutes (int minutes){
			GregorianCalendar c = new GregorianCalendar();
			c.add(Calendar.MINUTE, minutes);
			return new AlarmProperties(c, null);
		}
	
	}


	public final class YearScope {
	
		private GregorianCalendar c;	
			
		private YearScope (GregorianCalendar c){
			this.c = c; 
		}
		
		public MonthScope year (int year){
			c.set(Calendar.YEAR, year);
			return new MonthScope(c);
		}
	
		public MonthScope thisYear (){
			return new MonthScope(c);
		}
	
	}


	public final class MonthScope {
	
		private GregorianCalendar c;	
			
		private MonthScope (GregorianCalendar c){
			this.c = c; 
		}
		
		public DayScope month (int month){
			if (month < 1 || month > 12) {throw new IllegalArgumentException(ALARM_DATE_INVALID_MSG);}
			c.set(Calendar.MONTH, month - 1);
			return new DayScope(c);
		}
	
		public DayScope thisMonth (){
			return new DayScope(c);
		}
	
	}


	public final class DayScope {
	
		private GregorianCalendar c;	
			
		private DayScope (GregorianCalendar c){
			this.c = c; 
		}
		
		public TimeWrapperScope day (int day){
			int currMonth = c.get(Calendar.MONTH);
			int highestDay;
			if (currMonth == 1) {ighestDay = 28;}
			else if ((currMonth % 2) == 0) {highestDay = 31;}
			else {highestDay = 30;}
			if (day < 1 || day > highestDay) {throw new IllegalArgumentException(ALARM_DATE_INVALID_MSG);}
			c.set(Calendar.DAY_OF_MONTH, day);
			return new TimeWrapperScope(c, null);
		}
	
	}


	public final class TimeWrapperScope {
	
		private GregorianCalendar c;	
		private WeekAndDayMemorie wadm;	
			
		private TimeWrapperScope (GregorianCalendar c ,WeekAndDayMemorie wadm){
			this.c = c; 
			this.wadm = wadm; 
		}
		
		public HourScope at (){
			return new HourScope(c, wadm);
		}
	
	}


	public final class HourScope {
	
		private GregorianCalendar c;	
		private WeekAndDayMemorie wadm;	
			
		private HourScope (GregorianCalendar c ,WeekAndDayMemorie wadm){
			this.c = c; 
			this.wadm = wadm; 
		}
		
		public TimeMinuteScope hour (int hour){
			if (hour < 0 || hour > 23) {throw new IllegalArgumentException(ALARM_DATE_INVALID_MSG);}
			c.set(Calendar.HOUR_OF_DAY, hour);
			return new TimeMinuteScope(c, wadm);
		}
	
	}


	public final class TimeMinuteScope {
	
		private GregorianCalendar c;	
		private WeekAndDayMemorie wadm;	
			
		private TimeMinuteScope (GregorianCalendar c ,WeekAndDayMemorie wadm){
			this.c = c; 
			this.wadm = wadm; 
		}
		
		public AlarmProperties minute (int minute){
			if (minute < 0 || minute > 59) {throw new IllegalArgumentException(ALARM_DATE_INVALID_MSG);}
			c.set(Calendar.MINUTE, minute);
			return new TimeMinuteScope(c, wadm);
		}
	
	}


	public final class WeekScope {
	
		private GregorianCalendar c;	
		private WeekAndDayMemorie wadm;	
			
		private WeekScope (GregorianCalendar c ,WeekAndDayMemorie wadm){
			this.c = c; 
			this.wadm = wadm; 
		}
		
		public DayWrapperScope weeks (int weeks){
			if (weeks < 1) {throw new IllegalArgumentException(ALARM_DATE_INVALID_MSG);}
			wadm.setWeeks(weeks);
			return new DayWrapperScope(c, wadm);
		}
	
		public DayWrapperScope week (){
			wadm.setWeeks(1);
			return new DayWrapperScope(c, wadm);
		}
	
	}


	public final class DayWrapperScope {
	
		private GregorianCalendar c;	
		private WeekAndDayMemorie wadm;	
			
		private DayWrapperScope (GregorianCalendar c ,WeekAndDayMemorie wadm){
			this.c = c; 
			this.wadm = wadm; 
		}
		
		public TimeWrapperScope on (Day... days){
			for (Day day : days) {wadm.addDay(day);}
			int oldVal = c.get(Calendar.DAY_OF_MONTH);
			c.set(Calendar.DAY_OF_WEEK, (days[0].ordinal() + 2 > 7) ? 1 : days[0].ordinal() + 2);
			if (oldVal > c.get(Calendar.DAY_OF_MONTH)) {c.add(Calendar.WEEK_OF_YEAR, 1);}
			return new TimeWrapperScope(c, wadm);
		}
	
	}


	public final class WeekAndDayMemorie {
	
		private int weeks;	
		private LinkedList<Day> days;	
			
		private WeekAndDayMemorie (){
			this.weeks = 0; 
			this.days = new LinkedList<Day>(); 
		}
		
		public void setWeeks (int weeks){
			if (weeks < 1) {throw new IllegalArgumentException(ALARM_DATE_INVALID_MSG);}
			this.weeks = weeks;
		}
	
		public void addDay (Day day){
			if (!this.days.contains(day)) {days.add(day);}
		}
	
		public int getWeeks() (){
			return weeks;
		}
	
		public LinkedList<Day> getDays (){
			return days
		}
	
	}


}