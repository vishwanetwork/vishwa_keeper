/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.scan.keeper.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	public static final String TIME_WITH_MINUTE_PATTERN = "HH:mm";

	public static final long DAY_MILLI = 24 * 60 * 60 * 1000; // MilliSecond

	public final static int LEFT_OPEN_RIGHT_OPEN = 1;
	public final static int LEFT_CLOSE_RIGHT_OPEN = 2;
	public final static int LEFT_OPEN_RIGHT_CLOSE = 3;
	public final static int LEFT_CLOSE_RIGHT_CLOSE = 4;
	/**
	 *  --，
	 */
	public final static int COMP_MODEL_DATE = 1;
	/**
	 *  --，
	 */
	public final static int COMP_MODEL_TIME = 2;
	/**
	 *  --，
	 */
	public final static int COMP_MODEL_DATETIME = 3;

	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

	/**
	 * DATE Format
	 */
	public static String DATE_FORMAT_DATEONLY = "yyyy-MM-dd"; // //
	public static String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss"; // //
	public static SimpleDateFormat sdfDateTime = new SimpleDateFormat(DateUtils.DATE_FORMAT_DATETIME);
	// Global SimpleDateFormat object
	public static SimpleDateFormat sdfDateOnly = new SimpleDateFormat(DateUtils.DATE_FORMAT_DATEONLY);
	public static final SimpleDateFormat SHORTDATEFORMAT = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat LONG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat HMS_FORMAT = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat FORMATTIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
     * date
     *
     * @param dateString
     * @return
     */
    public static Date StringToDate2(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DATEONLY);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return date;
    }
	
	/**
	 * 
	 * 
	 * @param str
	 *            
	 * @param parsePatterns
	 *            
	 * @return 
	 * @throws ParseException
	 */
	public static Date parseDate(String str, String parsePatterns) {
		try {
			return parseDate(str, new String[] { parsePatterns });
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * 
	 * @param date
	 *            1
	 * @param otherDate
	 *            2
	 * @param withUnit
	 *            ，Calendar field
	 * @return 0, 0 0
	 */
	public static int compareDate(Date date, Date otherDate, int withUnit) {
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);
		Calendar otherDateCal = Calendar.getInstance();
		otherDateCal.setTime(otherDate);

		switch (withUnit) {
		case Calendar.YEAR:
			dateCal.clear(Calendar.MONTH);
			otherDateCal.clear(Calendar.MONTH);
		case Calendar.MONTH:
			dateCal.set(Calendar.DATE, 1);
			otherDateCal.set(Calendar.DATE, 1);
		case Calendar.DATE:
			dateCal.set(Calendar.HOUR_OF_DAY, 0);
			otherDateCal.set(Calendar.HOUR_OF_DAY, 0);
		case Calendar.HOUR:
			dateCal.clear(Calendar.MINUTE);
			otherDateCal.clear(Calendar.MINUTE);
		case Calendar.MINUTE:
			dateCal.clear(Calendar.SECOND);
			otherDateCal.clear(Calendar.SECOND);
		case Calendar.SECOND:
			dateCal.clear(Calendar.MILLISECOND);
			otherDateCal.clear(Calendar.MILLISECOND);
		case Calendar.MILLISECOND:
			break;
		default:
			throw new IllegalArgumentException("withUnit  " + withUnit + " ！！");
		}
		return dateCal.compareTo(otherDateCal);
	}

	/**
	 * 
	 * 
	 * @param date
	 *            1
	 * @param otherDate
	 *            2
	 * @param withUnit
	 *            ，Calendar field
	 * @return 0, 0 0
	 */
	public static int compareTime(Date date, Date otherDate, int withUnit) {
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);
		Calendar otherDateCal = Calendar.getInstance();
		otherDateCal.setTime(otherDate);

		dateCal.clear(Calendar.YEAR);
		dateCal.clear(Calendar.MONTH);
		dateCal.set(Calendar.DATE, 1);
		otherDateCal.clear(Calendar.YEAR);
		otherDateCal.clear(Calendar.MONTH);
		otherDateCal.set(Calendar.DATE, 1);
		switch (withUnit) {
		case Calendar.HOUR:
			dateCal.clear(Calendar.MINUTE);
			otherDateCal.clear(Calendar.MINUTE);
		case Calendar.MINUTE:
			dateCal.clear(Calendar.SECOND);
			otherDateCal.clear(Calendar.SECOND);
		case Calendar.SECOND:
			dateCal.clear(Calendar.MILLISECOND);
			otherDateCal.clear(Calendar.MILLISECOND);
		case Calendar.MILLISECOND:
			break;
		default:
			throw new IllegalArgumentException("withUnit  " + withUnit + " ！！");
		}
		return dateCal.compareTo(otherDateCal);
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public static long nowTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public static Timestamp nowTimeStamp() {
		return new Timestamp(nowTimeMillis());
	}

	/**
	 * yyyy-MM-dd 
	 * 
	 */
	public static String getReqDate() {
		return SHORTDATEFORMAT.format(new Date());
	}

	/**
	 * yyyy-MM-dd 
	 * 
	 * @param date
	 * @return
	 */
	public static String getReqDate(Date date) {
		return SHORT_DATE_FORMAT.format(date);
	}

	/**
	 * yyyyMMdd 
	 * 
	 * @param date
	 * @return
	 */
	public static String getReqDateyyyyMMdd(Date date) {
		return SHORTDATEFORMAT.format(date);
	}

	/**
	 * yyyy-MM-dd 
	 * 
	 * @param tmp
	 * @return
	 */
	public static String TimestampToDateStr(Timestamp tmp) {
		return SHORT_DATE_FORMAT.format(tmp);
	}

	/**
	 * HH:mm:ss 
	 * 
	 * @return
	 */
	public static String getReqTime() {
		return HMS_FORMAT.format(new Date());
	}

	/**
	 * 
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeStampStr(Date date) {
		return LONG_DATE_FORMAT.format(date);
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public static String getLongDateStr() {
		return LONG_DATE_FORMAT.format(new Date());
	}

	public static String getLongDateStr(Timestamp time) {
		return LONG_DATE_FORMAT.format(time);
	}

	/**
	 * 
	 * 
	 * @param date
	 * @return
	 */
	public static String getShortDateStr(Date date) {
		return SHORT_DATE_FORMAT.format(date);
	}

	public static String getShortDateStr() {
		return SHORT_DATE_FORMAT.format(new Date());
	}

	/**
	 *  second 
	 * 
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date addSecond(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		;
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 *  minute 
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	/**
	 *  hour 
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);
		return calendar.getTime();
	}

	/**
	 * day。
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * day.
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}

	/**
	 *  day 
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	/**
	 * month.
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}

	public static Date addYear(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 365 * year);
		return calendar.getTime();
	}

	public static Timestamp strToTimestamp(String dateStr) {
		return Timestamp.valueOf(dateStr);
	}

	public static Timestamp strToTimestamp(Date date) {
		return Timestamp.valueOf(FORMATTIMESTAMP.format(date));
	}

	public static Timestamp getCurTimestamp() {
		return Timestamp.valueOf(FORMATTIMESTAMP.format(new Date()));
	}

	/**
	 * 
	 * 
	 * @return t1t2，t2  t1，，
	 */
	public static long daysBetween(Timestamp t1, Timestamp t2) {
		return (t2.getTime() - t1.getTime()) / DAY_MILLI;
	}

	/**
	 * java.sql.TimestampSYSDATE
	 *
	 * @return java.sql.TimestampSYSDATE
	 * @since 1.0
	 * @history
	 */
	public static Timestamp getSysDateTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * Date(YYYY/MM/DD)Stringjava.sql.Timestamp
	 *
	 * @param sDate
	 *            Date string
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static Timestamp toSqlTimestamp(String sDate) {
		if (sDate == null) {
			return null;
		}
		if (sDate.length() != DateUtils.DATE_FORMAT_DATEONLY.length()
				&&sDate.length() != DateUtils.DATE_FORMAT_DATETIME.length()) {
			return null;
		}
		return toSqlTimestamp(sDate,
				sDate.length() == DateUtils.DATE_FORMAT_DATEONLY.length()
				?DateUtils.DATE_FORMAT_DATEONLY
				:DateUtils.DATE_FORMAT_DATETIME);

	}

	/**
	 * Date(YYYY/MM/DD hh:mm:ss)Stringjava.sql.Timestamp
	 *
	 * @param sDate
	 *            Date string
	 * @param sFmt
	 *            Date format DATE_FORMAT_DATEONLY/DATE_FORMAT_DATETIME
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static Timestamp toSqlTimestamp(String sDate, String sFmt) {
		String temp = null;
		if (sDate == null || sFmt == null) {
			return null;
		}
		if (sDate.length() != sFmt.length()) {
			return null;
		}
		if (sFmt.equals(DateUtils.DATE_FORMAT_DATETIME)) {
			temp = sDate.replace('/', '-');
			temp = temp + ".000000000";
		} else if (sFmt.equals(DateUtils.DATE_FORMAT_DATEONLY)) {
			temp = sDate.replace('/', '-');
			temp = temp + " 00:00:00.000000000";
			// }else if( sFmt.equals (DateUtils.DATE_FORMAT_SESSION )){
			// //Format: 200009301230
			// temp =
			// sDate.substring(0,4)+"-"+sDate.substring(4,6)+"-"+sDate.substring(6,8);
			// temp += " " + sDate.substring(8,10) + ":" +
			// sDate.substring(10,12) + ":00.000000000";
		} else {
			return null;
		}
		// java.sql.Timestamp.value() yyyy-mm-dd hh:mm:ss.fffffffff
		return Timestamp.valueOf(temp);
	}

	/**
	 * YYYY/MM/DD HH24:MI:SS
	 *
	 * @return 
	 * @since 1.0
	 * @history
	 */
	public static String getSysDateTimeString() {
		return toString(new Date(System.currentTimeMillis()), DateUtils.sdfDateTime);
	}

	/**
	 * Formatjava.util.DateString
	 *
	 * @param dt
	 *            java.util.Date instance
	 * @param sFmt
	 *            Date format , DATE_FORMAT_DATEONLY or DATE_FORMAT_DATETIME
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toString(Date dt, String sFmt) {
		if (dt == null || sFmt == null || "".equals(sFmt)) {
			return "";
		}
		return toString(dt, new SimpleDateFormat(sFmt));
	}

	/**
	 * SimpleDateFormat instancejava.util.DateString
	 *
	 * @param dt
	 *            java.util.Date instance
	 * @param formatter
	 *            SimpleDateFormat Instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	private static String toString(Date dt, SimpleDateFormat formatter) {
		String sRet = null;

		try {
			sRet = formatter.format(dt).toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
			sRet = null;
		}

		return sRet;
	}

	/**
	 * java.sql.TimestampString，YYYY/MM/DD HH24:MI
	 *
	 * @param dt
	 *            java.sql.Timestamp instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toSqlTimestampString2(Timestamp dt) {
		if (dt == null) {
			return null;
		}
		String temp = toSqlTimestampString(dt, DateUtils.DATE_FORMAT_DATETIME);
		return temp.substring(0, 16);
	}

	public static String toString(Timestamp dt) {
		return dt == null ? "" : toSqlTimestampString2(dt);
	}

	/**
	 * java.sql.TimestampString
	 *
	 * @param dt
	 *            java.sql.Timestamp instance
	 * @param sFmt
	 *            Date ，DATE_FORMAT_DATEONLY/DATE_FORMAT_DATETIME/
	 *            DATE_FORMAT_SESSION
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toSqlTimestampString(Timestamp dt, String sFmt) {
		String temp = null;
		String out = null;
		if (dt == null || sFmt == null) {
			return null;
		}
		temp = dt.toString();
		if (sFmt.equals(DateUtils.DATE_FORMAT_DATETIME) || // "YYYY/MM/DD
				// HH24:MI:SS"
				sFmt.equals(DateUtils.DATE_FORMAT_DATEONLY)) { // YYYY/MM/DD
			temp = temp.substring(0, sFmt.length());
			out = temp.replace('/', '-');
			// }else if( sFmt.equals (DateUtils.DATE_FORMAT_SESSION ) ){
			// //Session
			// out =
			// temp.substring(0,4)+temp.substring(5,7)+temp.substring(8,10);
			// out += temp.substring(12,14) + temp.substring(15,17);
		}
		return out;
	}

	// 
	public static int getWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int w = cal.get(Calendar.DAY_OF_WEEK);
		return w;
	}

	/**
	 * Timestamp yyyy-MM-dd timestampToSql(Timestamp yyyy-MM-dd)
	 *
	 * @param timestamp
	 *            
	 * @return createTimeStr yyyy-MM-dd 
	 * @Exception 
	 * @since V1.0
	 */
	public static String timestampToStringYMD(Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DATE_FORMAT_DATEONLY);
		String createTimeStr = sdf.format(timestamp);
		return createTimeStr;
	}

	/**
	 * 
	 *
	 * @param now
	 *            
	 * @param start
	 *            
	 * @param end
	 *            
	 * @param model
	 *            
	 * @return 
	 */
	public static boolean isBetween(Date now, Date start, Date end, int model) {
		return isBetween(now, start, end, model, LEFT_OPEN_RIGHT_OPEN);
	}

	/**
	 * 
	 *
	 * @param date
	 *            
	 * @param start
	 *            
	 * @param end
	 *            
	 * @param interModel
	 *            
	 *
	 *            <pre>
	 * 		：
	 * 			LEFT_OPEN_RIGHT_OPEN
	 * 			LEFT_CLOSE_RIGHT_OPEN
	 * 			LEFT_OPEN_RIGHT_CLOSE
	 * 			LEFT_CLOSE_RIGHT_CLOSE
	 * </pre>
	 * @param compModel
	 *            
	 *
	 *            <pre>
	 * 		：
	 * 			COMP_MODEL_DATE		，
	 * 			COMP_MODEL_TIME		，
	 * 			COMP_MODEL_DATETIME ，
	 * </pre>
	 * @return
	 */
	public static boolean isBetween(Date date, Date start, Date end, int interModel, int compModel) {
		if (date == null || start == null || end == null) {
			throw new IllegalArgumentException("");
		}
		SimpleDateFormat format = null;
		switch (compModel) {
		case COMP_MODEL_DATE: {
			format = new SimpleDateFormat("yyyyMMdd");
			break;
		}
		case COMP_MODEL_TIME: {
			format = new SimpleDateFormat("HHmmss");
			break;
		}
		case COMP_MODEL_DATETIME: {
			format = new SimpleDateFormat("yyyyMMddHHmmss");
			break;
		}
		default: {
			throw new IllegalArgumentException(String.format("[%d]", compModel));
		}
		}
		long dateNumber = Long.parseLong(format.format(date));
		long startNumber = Long.parseLong(format.format(start));
		long endNumber = Long.parseLong(format.format(end));
		switch (interModel) {
		case LEFT_OPEN_RIGHT_OPEN: {
			if (dateNumber <= startNumber || dateNumber >= endNumber) {
				return false;
			} else {
				return true;
			}
		}
		case LEFT_CLOSE_RIGHT_OPEN: {
			if (dateNumber < startNumber || dateNumber >= endNumber) {
				return false;
			} else {
				return true;
			}
		}
		case LEFT_OPEN_RIGHT_CLOSE: {
			if (dateNumber <= startNumber || dateNumber > endNumber) {
				return false;
			} else {
				return true;
			}
		}
		case LEFT_CLOSE_RIGHT_CLOSE: {
			if (dateNumber < startNumber || dateNumber > endNumber) {
				return false;
			} else {
				return true;
			}
		}
		default: {
			throw new IllegalArgumentException(String.format("[%d]", interModel));
		}
		}
	}

	/**
	 * 
	 *
	 * @param date
	 * @return
	 */
	public static Date getWeekStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.get(Calendar.WEEK_OF_YEAR);
		int firstDay = calendar.getFirstDayOfWeek();
		calendar.set(Calendar.DAY_OF_WEEK, firstDay);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 
	 *
	 * @param date
	 * @return
	 */
	public static Date getWeekEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.get(Calendar.WEEK_OF_YEAR);
		int firstDay = calendar.getFirstDayOfWeek();
		calendar.set(Calendar.DAY_OF_WEEK, 8 - firstDay);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 
	 * <p>
	 * param date
	 * return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Sunday
		return calendar.getTime();
	}

	/**
	 * 
	 * <p>
	 * param date
	 * return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Saturday
		return calendar.getTime();
	}

	/**
	 * 
	 * <p>
	 * param date
	 * return
	 */
	public static Date getLastDayOfLastWeek(Date date) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		return getLastDayOfWeek(calendar.get(Calendar.YEAR),
//				calendar.get(Calendar.WEEK_OF_YEAR) - 1);

		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 23, 59,59);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.add(Calendar.DATE,-1);
		return calendar.getTime();
	}

	/**
	 * 
	 * <p>
	 * param date
	 * return
	 */
	public static Date getFirstDayOfLastWeek(Date date) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		return getFirstDayOfWeek(calendar.get(Calendar.YEAR),
//				calendar.get(Calendar.WEEK_OF_YEAR) - 1);


		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.add(Calendar.DATE, -7);
		return calendar.getTime();
	}


	/**
	 * 
	 * <p>
	 * param year
	 * param week
	 * return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		week = week - 1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DATE, 1);

		Calendar cal = (Calendar) calendar.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 
	 * <p>
	 * param year
	 * param week
	 * return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		week = week - 1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DATE, 1);
		Calendar cal = (Calendar) calendar.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}



	/**
	 * 
	 *
	 * @param date
	 * @return
	 */
	public static Date getMonthStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 
	 *
	 * @param date
	 * @return
	 */
	public static Date getYearStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 
	 *
	 * @param date
	 * @return
	 */
	public static Date getYearEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 
	 *
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 * 
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 * 
	 *
	 * @param date
	 * @return
	 */
	public static Date getSeasonStart(Date date) {
		return getDayStart(getFirstDateOfMonth(getSeasonDate(date)[0]));
	}

	/**
	 * 
	 *
	 * @param date
	 * @return
	 */
	public static Date getSeasonEnd(Date date) {
		return getDayEnd(getLastDateOfMonth(getSeasonDate(date)[2]));
	}

	/**
	 * 
	 *
	 * @param date
	 * @return
	 */
	public static Date[] getSeasonDate(Date date) {
		Date[] season = new Date[3];

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		int nSeason = getSeason(date);
		if (nSeason == 1) {// 
			c.set(Calendar.MONTH, Calendar.JANUARY);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.FEBRUARY);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.MARCH);
			season[2] = c.getTime();
		} else if (nSeason == 2) {// 
			c.set(Calendar.MONTH, Calendar.APRIL);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.MAY);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.JUNE);
			season[2] = c.getTime();
		} else if (nSeason == 3) {// 
			c.set(Calendar.MONTH, Calendar.JULY);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.AUGUST);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.SEPTEMBER);
			season[2] = c.getTime();
		} else if (nSeason == 4) {// 
			c.set(Calendar.MONTH, Calendar.OCTOBER);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.NOVEMBER);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.DECEMBER);
			season[2] = c.getTime();
		}
		return season;
	}

	/**
	 *
	 * 1  2  3  4 
	 *
	 * @param date
	 * @return
	 */
	public static int getSeason(Date date) {

		int season = 0;

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		switch (month) {
		case Calendar.JANUARY:
		case Calendar.FEBRUARY:
		case Calendar.MARCH:
			season = 1;
			break;
		case Calendar.APRIL:
		case Calendar.MAY:
		case Calendar.JUNE:
			season = 2;
			break;
		case Calendar.JULY:
		case Calendar.AUGUST:
		case Calendar.SEPTEMBER:
			season = 3;
			break;
		case Calendar.OCTOBER:
		case Calendar.NOVEMBER:
		case Calendar.DECEMBER:
			season = 4;
			break;
		default:
			break;
		}
		return season;
	}

	/**
	 * date
	 *
	 * @param dateString
	 * @return
	 */
	public static Date StringToDate(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return date;
	}

	/**
	 * ()
	 *
	 * @param date
	 * @return
	 */
	public static int getWeekIndex(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * ，2013/12/09 00:00:00 
	 */
	public static Date subDays(int days) {
		Date date = addDay(new Date(), -days);
		String dateStr = getReqDate(date);
		Date date1 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date1 = sdf.parse(dateStr);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return date1;
	}

	/**
	 * ， ：，90
	 *
	 * @param startDate
	 *            
	 * @param endDate
	 *            
	 * @param interval
	 *            
	 * @param dateUnit
	 *            (：，),Calendar
	 * @return
	 */
	public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval, int dateUnit) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(dateUnit, interval * (-1));
		Date curDate = getDayStart(cal.getTime());
		if (getDayStart(startDate).compareTo(curDate) < 0 || getDayStart(endDate).compareTo(curDate) < 0) {
			return true;
		}
		return false;
	}

	/**
	 * ，,  ：，90
	 *
	 * @param startDate
	 *            
	 * @param endDate
	 *            
	 * @param interval
	 *            
	 * @return
	 */
	public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, interval * (-1));
		Date curDate = getDayStart(cal.getTime());
		if (getDayStart(startDate).compareTo(curDate) < 0 || getDayStart(endDate).compareTo(curDate) < 0) {
			return true;
		}
		return false;
	}

	/**
	 * ，,  ：，90
	 *
	 * @param startDateStr
	 *            
	 * @param endDateStr
	 *            
	 * @param interval
	 *            
	 * @return
	 */
	public static boolean isOverIntervalLimit(String startDateStr, String endDateStr, int interval) {
		Date startDate = null;
		Date endDate = null;
		startDate = DateUtils.parseDate(startDateStr, DateUtils.DATE_FORMAT_DATEONLY);
		endDate = DateUtils.parseDate(endDateStr, DateUtils.DATE_FORMAT_DATEONLY);
		if (startDate == null || endDate == null){
			return true;
		}

		return isOverIntervalLimit(startDate, endDate, interval);
	}

	/**
	 * ，Date
	 *
	 * @param src
	 *            
	 * @param pattern
	 *            
	 * @return Date
	 */
	public static Date getDateFromString(String src, String pattern) {
		SimpleDateFormat f = new SimpleDateFormat(pattern);
		try {
			return f.parse(src);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getQuarter(Date date) {
		if (date.getMonth() == 0 || date.getMonth() == 1 || date.getMonth() == 2) {
			return 1;
		} else if (date.getMonth() == 3 || date.getMonth() == 4 || date.getMonth() == 5) {
			return 2;
		} else if (date.getMonth() == 6 || date.getMonth() == 7 || date.getMonth() == 8) {
			return 3;
		} else if (date.getMonth() == 9 || date.getMonth() == 10 || date.getMonth() == 11) {
			return 4;
		} else {
			return 0;

		}
	}

	/**
	 * 
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * 
	 * 
	 * @param
	 * @return String
	 */
	public static String today() {
		return formatDate(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 
	 * 
	 * @param
	 * @return String
	 */
	public static String currentTime() {
		return formatDate(new Date(), "yyyyMMddhhmmssSSS");
	}

	/**
	 * 
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * 
	 * 
	 * @return Date
	 */
	public static String getYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return formatDate(calendar.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 
	 * 
	 * @param startTime
	 * @return boolean
	 */
	public static boolean isInBetweenTimes(String startTime, String endTime) {
		Date nowTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String time = sdf.format(nowTime);
		if (time.compareTo(startTime) >= 0 && time.compareTo(endTime) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date getDateByStr(String dateStr) {
		SimpleDateFormat formatter = null;
		if (dateStr == null) {
			return null;
		} else if (dateStr.length() == 10) {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
		} else if (dateStr.length() == 16) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		} else if (dateStr.length() == 19) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if (dateStr.length() > 19) {
			dateStr = dateStr.substring(0, 19);
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			return null;
		}
		try {
			return formatter.parse(dateStr);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	/**
	 * ，days
	 * 
	 * @param days
	 * @return Date
	 */
	public static Date getDate(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	/**
	 * 
	 * 
	 * @param dt
	 * @return
	 */
	public static Date getMaxTime(Date dt) {

		Date dt1 = null;
		Calendar ca = Calendar.getInstance();
		ca.setTime(dt);
		ca.add(Calendar.DAY_OF_MONTH, 1);
		dt1 = ca.getTime();
		dt1 = DateUtils.getMinTime(dt1);
		ca.setTime(dt1);
		ca.add(Calendar.SECOND, -1);
		dt1 = ca.getTime();
		return dt1;
	}

	/**
	 * 
	 * 
	 * @param dt
	 * @return
	 */
	public static Date getMinTime(Date dt) {
		Date dt1 = null;
		dt1 = DateUtils.getDateByStr(DateUtils.formatDate(dt, "yyyy-MM-dd"));
		return dt1;
	}

	/**
	 * 
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date getLastDayOfMonth(Date date) {
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(date);
		int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date lastDate = cDay1.getTime();
		lastDate.setDate(lastDay);
		return lastDate;
	}

	/**
	 * 
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public static Date getPreviousMonthFirstDay() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 1
		lastDate.add(Calendar.MONTH, -1);// ，1
		return getMinTime(lastDate.getTime());
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public static Date getPreviousMonthLastDay() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 1
		lastDate.add(Calendar.DATE, -1);
		return getMinTime(lastDate.getTime());
	}

	/**
	 * 
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getDateDiff(String startDate, String endDate) {
		long diff = 0;
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

			diff = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1.getTime() - date2.getTime())
					/ (24 * 60 * 60 * 1000)
					: (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
		}
		return diff;
	}

	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDateDiff(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return 0L;
		}
		long diff = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1.getTime() - date2
				.getTime()) / (24 * 60 * 60 * 1000) : (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
		return diff;
	}

	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getYearDiff(Date date1, Date date2){
		if (date1 == null || date2 == null) {
			return 0;
		}

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		int year1 = calendar1.get(Calendar.YEAR);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		int year2 = calendar2.get(Calendar.YEAR);

		return Math.abs( year1 - year2);
	}

	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getTimeDiff(Date date1, Date date2){
		if (date1 == null || date1 == null) {
			return 0L;
		}
		long diff = (date1.getTime() - date2.getTime()) > 0 ? (date1.getTime() - date2
				.getTime())  : (date2.getTime() - date1.getTime()) ;
		return diff;
	}

	/*
	 * 
	 */
	public static boolean isSameWeekWithToday(Date date) {

		if (date == null) {
			return false;
		}

		// 0.DateCalendar
		Calendar todayCal = Calendar.getInstance();
		Calendar dateCal = Calendar.getInstance();

		todayCal.setTime(new Date());
		dateCal.setTime(date);
		int subYear = todayCal.get(Calendar.YEAR) - dateCal.get(Calendar.YEAR);
		// subYear==0,
		if (subYear == 0) {
			if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR)){
			    return true;
			}
		} else if (subYear == 1 && dateCal.get(Calendar.MONTH) == 11 && todayCal.get(Calendar.MONTH) == 0) {
			if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR)){
			    return true;
			}
		} else if (subYear == -1 && todayCal.get(Calendar.MONTH) == 11 && dateCal.get(Calendar.MONTH) == 0) {
			if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR)){
			    return true;
			}
		}
		return false;
	}

	/**
	 * getStrFormTime: <br/>
	 * 
	 * @param form
	 *            
	 * @param date
	 *            
	 * @return
	 */
	public static String getStrFormTime(String form, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(form);
		return sdf.format(date);
	}

	/**
	 *  return 2014-5-4、2014-5-3
	 */
	public static List<String> getLastDays(int countDay) {
		List<String> listDate = new ArrayList<String>();
		for (int i = 0; i < countDay; i++) {
			listDate.add(DateUtils.getReqDateyyyyMMdd(DateUtils.getDate(-i)));
		}
		return listDate;
	}

	/**
	 * 
	 * 
	 * @param date
	 * @return
	 */
	public static Date dateFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date value = new Date();

		try {
			value = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return value;

	}
	
	public static boolean isSameDayWithToday(Date date) {

		if (date == null) {
			return false;
		}

		Calendar todayCal = Calendar.getInstance();
		Calendar dateCal = Calendar.getInstance();

		todayCal.setTime(new Date());
		dateCal.setTime(date);
		int subYear = todayCal.get(Calendar.YEAR) - dateCal.get(Calendar.YEAR);
		int subMouth = todayCal.get(Calendar.MONTH) - dateCal.get(Calendar.MONTH);
		int subDay = todayCal.get(Calendar.DAY_OF_MONTH) - dateCal.get(Calendar.DAY_OF_MONTH);
		if (subYear == 0 && subMouth == 0 && subDay == 0) {
			return true;
		}
		return false;
	}
	 public static String  getTimeToApp(Date date) {
		 Date now = new Date();
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(now);
		 calendar.add(Calendar.DATE,-1);//，
		 calendar.set(Calendar.HOUR_OF_DAY,0);
		 calendar.set(Calendar.MINUTE,0);
		 calendar.set(Calendar.SECOND,0);
		 Date yes=calendar.getTime();
		 long len = (now.getTime() - yes.getTime())/1000;
		 long second = (now.getTime() - date.getTime()) / 1000;
		 if (second < 60) {
			 return "";
		 } else if (second <= 60 * 60) {
			 return String.valueOf(second / 60) + "";
		 } else if (second <= 60 * 60 * 24) {
			 String min = null;
			 if (second % 3600 == 0){
				 min = "";
			 }else {
				 min = String.valueOf(second/60 % 60) + "";
			 }
			 return String.valueOf(second / 3600) + "" + min;
		 } else if (second > 60 * 60 * 24 && second < len) {
			 DateFormat dateFormat = new SimpleDateFormat("HH:mm");
			 return "" + dateFormat.format(date);
		 }else {
			 DateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
			 return dateFormat.format(date);
		 }
	 }
	 
	 public static int getHour(Date date) {
	     Calendar calendar = Calendar.getInstance();
	     calendar.setTime(date);
	     return calendar.get(Calendar.HOUR_OF_DAY);
	 }

	 /**
	  * 
	  * */
	 public static long getOverTime(String timezone){
		 TimeZone time = TimeZone.getTimeZone(timezone);
		 TimeZone.setDefault(time);
		 Calendar calendar = Calendar.getInstance();
		 Date date = calendar.getTime();
		 long now = date.getTime();
		 try {
			 SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
			 long overTime = (now - (sdfOne.parse(sdfOne.format(now)).getTime()))/1000;
			 return overTime;
		 } catch (ParseException e) {
			 e.printStackTrace();
			 return 0;
		 }

//		 long now = System.currentTimeMillis();
//		 SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
//		 sdfOne.setTimeZone(TimeZone.getTimeZone("GT+9:00"));
//		 try {
//			 long overTime = (now - (sdfOne.parse(sdfOne.format(now)).getTime()))/1000;
//			 logger.info(": " + sdfOne.parse(sdfOne.format(now)));
//			 logger.info("："+overTime);
//			 return overTime;
//		 } catch (ParseException e) {
//			 e.printStackTrace();
//			 return 0;
//		 }
	 }
}
