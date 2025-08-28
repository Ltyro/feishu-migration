package com.lark.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYY = "yyyy";
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_HHMMSSSSS = "HHmmssSSS";
    public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_HHMMSS = "HHmmss";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_FORMAT_CST = "yyyy-MM-dd'T'HH:mm:ss'+0800'";
    public static final String DATE_FORMAT_MM = "MM";
    public static final String DATE_FORMAT_DD = "dd";
    private static final DateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private static final DateFormat todayFormat = new SimpleDateFormat("MM/dd");
    private static final DateFormat longTimeFormat = new SimpleDateFormat("HH:mm");
    private static final DateFormat fullTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat fullTimeFormatSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateFormat minuteTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final DateFormat milliSecTimeFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static final DateFormat secondTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final DateFormat dayTimeFormat = new SimpleDateFormat("yyyyMMdd");
    private static final String[] parsePatterns = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS", "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM", "yyyy", "yyyyMMdd", "yyyyMMddHHmmss", "yyyyMMddHHmmssSSS", "yyyyMMddHHmm", "yyyyMM", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss+08:00"};

    public DateUtils() {
    }

    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    public static String getTimeStamp() {
        return formatDate(new Date(), "yyyyMMddHHmmssSSS");
    }

    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }

        return formatDate;
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        } else {
            try {
                return parseDate(str.toString(), parsePatterns);
            } catch (ParseException var2) {
                return null;
            }
        }
    }

    public static Long parseTimestamp(Object str) {
        Date date = parseDate(str);
        return date != null ? date.getTime() : null;
    }

    public static long pastDays(Date date) {
        long t = (new Date()).getTime() - date.getTime();
        return t / 86400000L;
    }

    public static long pastHour(Date date) {
        long t = (new Date()).getTime() - date.getTime();
        return t / 3600000L;
    }

    public static long pastMinutes(Date date) {
        long t = (new Date()).getTime() - date.getTime();
        return t / 60000L;
    }

    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / 86400000L;
        long hour = timeMillis / 3600000L - day * 24L;
        long min = timeMillis / 60000L - day * 24L * 60L - hour * 60L;
        long s = timeMillis / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
        long sss = timeMillis - day * 24L * 60L * 60L * 1000L - hour * 60L * 60L * 1000L - min * 60L * 1000L - s * 1000L;
        return (day > 0L ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (double)((afterTime - beforeTime) / 86400000L);
    }

    public static Date parseTime(Date date, int hourOfDay, int minute, int second, int milliSecond) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        setCalendarTime(cal, hourOfDay, minute, second, milliSecond);
        return cal.getTime();
    }

    private static void setCalendarTime(Calendar cal, int hourOfDay, int minute, int second, int milliSecond) {
        cal.set(11, hourOfDay);
        cal.set(12, minute);
        cal.set(13, second);
        cal.set(14, milliSecond);
    }

    public static Date getLastest(Date date) {
        return parseTime(date, 23, 59, 59, 999);
    }

    public static Date getEarliest(Date date) {
        return parseTime(date, 0, 0, 0, 0);
    }

    public static String getFirstDayOfLastNYear(long n) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfThisYear = today.with(TemporalAdjusters.firstDayOfYear());
        if (n == 0L) {
            return firstDayOfThisYear.format(dateTimeFormatter);
        } else {
            LocalDate firstDayOfLastYear = firstDayOfThisYear.minusYears(n);
            return firstDayOfLastYear.format(dateTimeFormatter);
        }
    }

    public static String getFirstDayOfLastNMonth(long n) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfThisYear = today.with(TemporalAdjusters.firstDayOfMonth());
        if (n == 0L) {
            return firstDayOfThisYear.format(dateTimeFormatter);
        } else {
            LocalDate firstDayOfLastYear = firstDayOfThisYear.minusMonths(n);
            return firstDayOfLastYear.format(dateTimeFormatter);
        }
    }

    public static String getFirstDayOfLastNMonth(long n, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfThisYear = today.with(TemporalAdjusters.firstDayOfMonth());
        if (n == 0L) {
            return firstDayOfThisYear.format(dateTimeFormatter);
        } else {
            LocalDate firstDayOfLastYear = firstDayOfThisYear.minusMonths(n);
            return firstDayOfLastYear.format(dateTimeFormatter);
        }
    }

    public static String getLastDayOfMonth(long n, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate today = LocalDate.now();
        LocalDate lastDay = today.with(TemporalAdjusters.lastDayOfMonth());
        if (n == 0L) {
            return lastDay.format(dateTimeFormatter);
        } else {
            LocalDate lastDayOfMonth = lastDay.minusMonths(n);
            return lastDayOfMonth.format(dateTimeFormatter);
        }
    }

    public static Date getFisrtDayOfMonth(int year, int month) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(1, year);
            cal.set(2, month);
            int firstDay = cal.getActualMinimum(5);
            cal.set(5, firstDay);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String firstDayOfMonth = sdf.format(cal.getTime());
            return parseDate(firstDayOfMonth, new String[]{"yyyy-MM-dd"});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getLastDayOfMonth(int year, int month) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(1, year);
            cal.set(2, month);
            int firstDay = cal.getActualMaximum(5);
            cal.set(5, firstDay);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String firstDayOfMonth = sdf.format(cal.getTime());
            return parseDate(firstDayOfMonth, new String[]{"yyyy-MM-dd"});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean validDate(String text, String pattern) {
        boolean result = Boolean.FALSE;

        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
            LocalDate.parse(text, df);
            result = Boolean.TRUE;
        } catch (Exception e) {
            e.getMessage();
            result = Boolean.FALSE;
        }

        return result;
    }

    public static Boolean validDateTime(String dateStr, String dateFormatStr) {
        boolean result = Boolean.FALSE;
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);

        try {
            dateFormat.parse(dateStr);
            result = Boolean.TRUE;
        } catch (Exception var5) {
            result = Boolean.FALSE;
        }

        return result;
    }

    public static int getPastMonth(String text, String pattern) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        LocalDate a = LocalDate.parse(text, df);
        Period period = Period.between(a, now);
        return period.getYears() * 12 + period.getMonths();
    }

    public static List<String> getMonthList(String startDate, String endDate) {
        String pattern = "yyyyMMdd";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM");
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        List<String> result = new ArrayList();
        LocalDate s = LocalDate.parse(startDate, df);
        s = s.with(TemporalAdjusters.firstDayOfMonth());

        for(LocalDate e = LocalDate.parse(endDate, df); s.isBefore(e) || s.isEqual(e); s = s.plusMonths(1L)) {
            result.add(s.format(dateTimeFormatter));
        }

        return result;
    }

    public static int getPastMonth(String text) {
        String pattern = "yyyyMMdd";
        if (text != null && !StringUtils.isBlank(text)) {
            LocalDate now = LocalDate.now();
            DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
            text = text.replaceAll("-", "").replaceAll("\\.", "").replaceAll("/", "");
            if (text.length() == 4) {
                text = text + "01";
            }

            Date date = parseDate(text);
            if (date == null) {
                return -1;
            } else {
                LocalDate ago = LocalDate.parse(formatDate(date, pattern), df);
                Period period = Period.between(ago, now);
                return period.getYears() * 12 + period.getMonths();
            }
        } else {
            return -1;
        }
    }

    public static String getFormat(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        } else {
            int length = text.length();
            if (length == 4) {
                return text + "-01-01";
            } else {
                String split = "";
                if (!text.matches("^[0-9]*$")) {
                    split = text.substring(4, 5);
                }

                return text;
            }
        }
    }

    public static List<String> getMonthBetween(String minDate, String maxDate) {
        if (!StringUtils.isEmpty(minDate) && !StringUtils.isEmpty(maxDate)) {
            ArrayList<String> result = new ArrayList();

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Calendar min = Calendar.getInstance();
                Calendar max = Calendar.getInstance();
                min.setTime(sdf.parse(minDate));
                min.set(min.get(1), min.get(2), 1);
                max.setTime(sdf.parse(maxDate));
                max.set(max.get(1), max.get(2), 2);
                Calendar curr = min;

                while(curr.before(max)) {
                    result.add(sdf.format(curr.getTime()));
                    curr.add(2, 1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return result;
        } else {
            return null;
        }
    }

    public static List<String> getMonthBetweenMonth(String minDate, String maxDate) {
        if (!StringUtils.isEmpty(minDate) && !StringUtils.isEmpty(maxDate)) {
            ArrayList<String> result = new ArrayList();

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Calendar min = Calendar.getInstance();
                Calendar max = Calendar.getInstance();
                min.setTime(sdf.parse(minDate));
                min.set(min.get(1), min.get(2), 1);
                max.setTime(sdf.parse(maxDate));
                max.set(max.get(1), max.get(2), 2);
                Calendar curr = min;

                while(curr.before(max)) {
                    result.add(sdf.format(curr.getTime()).substring(5, 7));
                    curr.add(2, 1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return result;
        } else {
            return null;
        }
    }

    public static String getFirstDay(String yearMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar date = Calendar.getInstance();

        try {
            date.setTime(sdf.parse(yearMonth));
            date.set(date.get(1), date.get(2), 1);
            return formatDate(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFirstDayByMonth(String date, Integer m) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(parseDate(date));
        cale.add(2, m);
        cale.set(5, 1);
        return formatDate(getEarliest(cale.getTime()));
    }

    public static String getLastDay(String yearMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar date = Calendar.getInstance();

        try {
            date.setTime(sdf.parse(yearMonth));
            date.set(5, date.getActualMaximum(5));
            return formatDate(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getLastDayByMonth(String date, Integer m) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(parseDate(date));
        cale.add(2, m + 1);
        cale.set(5, 0);
        return formatDate(getLatest(cale.getTime()));
    }

    public static String getBeforDayByMonth(String date, Integer m) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(parseDate(date));
        cale.add(2, m - 1);
        cale.set(5, 0);
        return formatDate(getLatest(cale.getTime()));
    }

    public static Date getLatest(Date date) {
        return parseTime(date, 23, 59, 59, 999);
    }

    public static String getMinDay(String date1, String date2) {
        return parseDate(date1).compareTo(parseDate(date2)) > 0 ? date2 : date1;
    }

    public static String nextByDays(Date date, int d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, d);
        return formatDate(calendar.getTime());
    }

    public static long getNowTime() {
        return (new Date()).getTime();
    }

    public static String getMonthAfterM(Date date, Integer m, String pattern) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(2, m);
        return formatDate(c.getTime(), pattern);
    }

    public static String getTodayBeforeDateStr(int before) {
        Date date = new Date();
        new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, -before);
        Date beforeDate = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String beforeDateStr = df.format(beforeDate);
        return beforeDateStr;
    }

    public static Date getTodayBeforeDate(int before) {
        Date date = new Date();
        new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, -before);
        Date beforeDate = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String beforeDateStr = df.format(beforeDate);
        return parseDate(beforeDateStr);
    }

    public static List<String> getBetweenDate(String start, String end, String pattern) {
        List<String> list = new ArrayList();
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        LocalDate startDate = LocalDate.parse(start, df);
        LocalDate endDate = LocalDate.parse(end, df);
        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        if (distance < 1L) {
            return list;
        } else {
            Stream.iterate(startDate, (d) -> d.plusDays(1L)).limit(distance + 1L).forEach((f) -> list.add(f.format(df)));
            return list;
        }
    }

    public static long getBetweenDays(String sDate, String eDate) {
        return getBetweenDays(sDate, eDate, "yyyyMMdd");
    }

    public static long getBetweenDays(String sDate, String eDate, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        LocalDate s = LocalDate.parse(sDate, df);
        LocalDate e = LocalDate.parse(eDate, df);
        return ChronoUnit.DAYS.between(s, e);
    }

    public static Date getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(1);
        return getYearFirst(currentYear);
    }

    public static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(1);
        return getYearLast(currentYear);
    }

    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        calendar.roll(6, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    public static String[] threeYearsMonths() {
        Date date = getCurrYearLast();
        String[] threeYearsMonths = new String[36];
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        for(int i = 0; i < 36; ++i) {
            cal.set(2, cal.get(2) - 1);
            threeYearsMonths[35 - i] = cal.get(1) + addZeroForNum(String.valueOf(cal.get(2) + 1), 2);
        }

        return threeYearsMonths;
    }

    public static String[] months() {
        Date date = getCurrYearLast();
        String[] months = new String[12];
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        for(int i = 0; i < 12; ++i) {
            cal.set(2, cal.get(2) - 1);
            months[11 - i] = addZeroForNum(String.valueOf(cal.get(2) + 1), 2);
        }

        return months;
    }

    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while(strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);
                str = sb.toString();
                strLen = str.length();
            }
        }

        return str;
    }

    public static Long dateDiffMill(Date d1, Date d2) {
        long n1 = d1.getTime();
        long n2 = d2.getTime();
        long diff = Math.abs(n1 - n2);
        return diff;
    }

    public static Long dateDiff(Date d1, Date d2) {
        long n1 = d1.getTime();
        long n2 = d2.getTime();
        long diff = Math.abs(n1 - n2);
        diff /= 86400000L;
        return diff;
    }

    public static Date today() {
        Calendar cal = Calendar.getInstance();
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static String dateFormat(Date date) {
        return date != null ? simpleFormat.format(date) : "";
    }

    public static String fullDateFormat(Date date) {
        return date != null ? fullTimeFormat.format(date) : "";
    }

    public static String fullMinSecondFormat(Date date) {
        return date != null ? milliSecTimeFormat.format(date) : "";
    }

    public static boolean chechMilliSecTimeFormat(String str) {
        milliSecTimeFormat.setLenient(false);

        try {
            milliSecTimeFormat.parse(str);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Date generatorDate() {
        Date date = new Date();
        return date;
    }

    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar)Calendar.getInstance();
        gc.setTime(date);
        return gc.get(1);
    }

    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar)Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }

        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 0, 0, 0);
        calendar.set(14, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }

        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 23, 59, 59);
        calendar.set(14, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int dayofweek = cal.get(7);
            if (dayofweek == 1) {
                dayofweek += 7;
            }

            cal.add(5, 2 - dayofweek);
            return getDayStartTime(cal.getTime());
        }
    }

    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(7, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    public static Date getBeginDayOfLastWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int dayofweek = cal.get(7);
            if (dayofweek == 1) {
                dayofweek += 7;
            }

            cal.add(5, 2 - dayofweek - 7);
            return getDayStartTime(cal.getTime());
        }
    }

    public static Date getEndDayOfLastWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek());
        cal.add(7, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    public static String getBeginDayOfMonths(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - n, 1);
        return DateFormatUtils.format(getDayStartTime(calendar.getTime()), "yyyy-MM-dd");
    }

    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    public static Date getBeginDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        return getDayStartTime(calendar.getTime());
    }

    public static Date getEndDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 2, day);
        return getDayEndTime(calendar.getTime());
    }

    public static String formatDateUTC(Date date) {
        return formatDate(date, "yyyy-MM-dd'T'HH:mm:ss'Z'");
    }

    public static String formatDateCST(Date date) {
        return formatDate(date, "yyyy-MM-dd'T'HH:mm:ss'+0800'");
    }

    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(5);
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(5);
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(2) + 1;
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(1);
    }

    public static int getMonthDiff(String startDate, String endDate) {
        int result = 0;
        Date start = parseDate(startDate);
        Date end = parseDate(endDate);
        int startYear = getYear(start);
        int startMonth = getMonth(start);
        int startDay = getDay(start);
        int endYear = getYear(end);
        int endMonth = getMonth(end);
        int endDay = getDay(end);
        if (startDay > endDay) {
            if (endDay == getDaysOfMonth(getYear(new Date()), 2)) {
                result = (endYear - startYear) * 12 + endMonth - startMonth;
            } else {
                result = (endYear - startYear) * 12 + endMonth - startMonth - 1;
            }
        } else {
            result = (endYear - startYear) * 12 + endMonth - startMonth;
        }

        return result;
    }

    public static Boolean checkIfThroughYear(Date startDate, Date endDate) {
        Boolean result = true;
        int startYear = getYear(startDate);
        int endYear = getYear(endDate);
        if (startYear == endYear) {
            result = false;
        }

        return result;
    }

    public static String milliseconds2DateStr(Long milliseconds) {
        String dateStr = null;
        if (null != milliseconds) {
            long day = milliseconds / 86400000L;
            long hour = milliseconds % 86400000L / 3600000L;
            long minute = milliseconds % 86400000L % 3600000L / 60000L;
            long seconds = milliseconds % 86400000L % 3600000L % 60000L / 1000L;
            dateStr = day + "天" + hour + "小时" + minute + "分" + seconds + "秒";
        }

        return dateStr;
    }

    public static String milliseconds2Minute(Long milliseconds) {
        return null != milliseconds ? String.valueOf(milliseconds / 60000L) : "0";
    }

    public static String getDateByDayOfYear(Date date, Integer dayOfYear) {
        if (null != date && null != dayOfYear) {
            Calendar currCal = Calendar.getInstance();
            currCal.setTime(date);
            int currentYear = currCal.get(1);
            Date yearFirst = getYearFirst(currentYear);
            return dateFormat(addDays(yearFirst, dayOfYear - 1));
        } else {
            return null;
        }
    }
}
