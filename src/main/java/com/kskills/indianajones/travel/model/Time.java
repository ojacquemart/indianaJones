package com.kskills.indianajones.travel.model;

import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

public class Time {

	private static final String SEPARATOR = ":";
	
	private Integer hours;
	private Integer minutes;

	public Time(Integer hours, Integer minutes) {
		this.hours = hours;
		this.minutes = minutes;
	}
	
	public static Time empty() {
		return new Time(0, 0);
	}
	
	public Time add(Time other) {
		if (other == null) {
			return empty();
		}
		
		Long totalOther = other.getTotalSeconds();
		Long totalThis = getTotalSeconds();
		Long sumInMillis = 1000 * (totalOther + totalThis);
		
		Integer hours = (int) TimeUnit.MILLISECONDS.toHours(sumInMillis);
		Integer minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(sumInMillis) - TimeUnit.HOURS.toMinutes(hours));
		
		return new Time(hours, minutes);
	}
	
	public Time add(String duration) {
		return add(fromString(duration));
	}
	
	public boolean before(Time other) {
		if (other == null) {
			return true;
		}
		
		return getTotalSeconds().compareTo(other.getTotalSeconds()) <= 0;
	}
	
	public static Time fromString(String value) {
		String[] split = value.split(SEPARATOR);
		Integer hours = Integer.valueOf(split[0]);
		Integer minutes = Integer.valueOf(split[1]);
		
		return new Time(hours, minutes);
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minuts) {
		this.minutes = minuts;
	}
	
	public Long getTotalSeconds() {
		return TimeUnit.HOURS.toSeconds(getHours()) + TimeUnit.MINUTES.toSeconds(getMinutes());
	}
	
	@Override
	public String toString() {
		final NumberFormat nb = NumberFormat.getInstance();
		nb.setMinimumIntegerDigits(2);
		
		return nb.format(hours) + SEPARATOR + nb.format(minutes);
	}

}
