package com.example.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

	public static Date toDate(String date, String inputFormat, String outputFormat) throws ParseException {
		SimpleDateFormat srcDate = new SimpleDateFormat(inputFormat);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		return formatter.parse(formatter.format(srcDate.parse(date)));
	}
}
