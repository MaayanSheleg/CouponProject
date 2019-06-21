package com.mbms.main;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * 
 */
/**
 * @author Maytal & mayaan *
*/
public class Utils {

	public static Date getCurrentDate() {
		LocalDate localDate = LocalDate.now();
		Date date = java.sql.Date.valueOf(localDate);
		return date;
	}

	public static Date getExpiredDate() {
		LocalDate localDate = LocalDate.now();
		localDate = localDate.plusMonths(1);
		Date date = java.sql.Date.valueOf(localDate);
		return date;
	}

	public static Date getExpiredDateNextDay() {

		LocalDate localDate = LocalDate.now();
		localDate = localDate.plusDays(1);
		Date date = java.sql.Date.valueOf(localDate);

		return date;
	}

	public static LocalDate convertToLocalDateViaMilisecond(java.util.Date date) {
		return Instant.ofEpochMilli(date.getTime())
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}

	public static Date getExpiredDatelastmonth() {

		LocalDate localDate = LocalDate.now();
		localDate = localDate.minusDays(30);
		Date date = java.sql.Date.valueOf(localDate);

		return date;
	}
}
