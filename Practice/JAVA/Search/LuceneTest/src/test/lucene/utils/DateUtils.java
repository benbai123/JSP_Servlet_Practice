package test.lucene.utils;

import java.text.ParseException;
import java.util.*;

import org.apache.lucene.document.*;

public class DateUtils {
	/**
	 * Convert Date to String in MILLISECOND Resolution
	 * @param date The date to convert
	 * @return String The converted String
	 */
	public static String getStringMillis (Date date) {
		return DateTools.dateToString(date,
							DateTools.Resolution.MILLISECOND);
	}
	/**
	 * Convert String to Date
	 * @param str The String to convert
	 * @return Date The converted Date
	 * @throws ParseException
	 */
	public static Date stringToDate (String str)
		throws ParseException {
		return DateTools.stringToDate(str);
	}
}