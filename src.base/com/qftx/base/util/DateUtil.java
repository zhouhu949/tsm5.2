package com.qftx.base.util;

import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * User: baoxl Date: 2010-5-4 Time: 14:29:12
 */
public class DateUtil {
    private static Logger logger = Logger.getLogger(DateUtil.class.getName());
    public static final String Data_ALL = "yyyy-MM-dd HH:mm:ss";
    
    public static final String DATE_MIN = "yyyy-MM-dd HH:mm";

    public static final String DATE_DAY = "yyyy-MM-dd";
    
    public static final String TIME = "HH:mm:ss";

    public static final String DATE_MONTH = "yyyy-MM";

    private static final SimpleDateFormat df = new SimpleDateFormat(Data_ALL);

    public static String getDataAll() {

        return df.format(new Date().getTime());
    }

    public static String getDataAll(Date dt) {
        return df.format(dt.getTime());
    }

    public static String getDataMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_MONTH);
        return df.format(date);
    }
    
    public static String getDateDay(Date date) {
    	if(date==null) return null;
    	
        SimpleDateFormat df = new SimpleDateFormat(DATE_DAY);
        return df.format(date);
    }

    
    public static Date getDate(Date date) throws ParseException {
        return df.parse(df.format(date));
    }
    /**
     * User: zmw
     *
     * @return
     * @throws ParseException Des: 获取全日期格式(数据库)
     */
    public static Date getDbDate() throws ParseException {
        Date date = new Date();
        return df.parse(df.format(date));
    }

    public static String getFtpDatePath() throws ParseException {
        String datePath = "yyyyMM/dd/";
        SimpleDateFormat dp = new SimpleDateFormat(datePath);
        Date date = new Date();
        return dp.format(date);
    }

    public static Timestamp getDbTimestamp() {
        // Timestamp d = new Timestamp(System.currentTimeMillis());
        Date date = new Date();
        Timestamp nousedate = new Timestamp(date.getTime());
        return nousedate;
    }

    public static Date getData(String dt) {
        try {
            return df.parse(dt);
        } catch (ParseException e) {
            logger.debug(e.getMessage(), e);
        }
        return null;
    }

    public static String formatDate(Date date) {

        return df.format(date);
    }

    public static String formatDate(Date date, String formatStr) {
    	if(date==null){
    		return null;
    	}else{
    		return new SimpleDateFormat(formatStr).format(date);
    	}
    }

    public static long formatDate(String date) throws ParseException {
        Date dt = df.parse(date);
        return dt.getTime();
    }

    public static String getDataAll(String str) {
        return df.format(new Date().getTime());
    }

    public static String getDateStart(String str, boolean istrue) {
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
        String dt = null;
        try {
            dt = dff.format(df.parse(str)) + " 00:00:00";
            if (istrue) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(df.parse(dt));
                cal.add(Calendar.DATE, 1);
                dt = df.format(cal.getTime());
            }
        } catch (ParseException e) {
            // ates.
        }
        return dt;
    }

    public static String getQueryDate(String str, boolean istrue) {
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
        String dt = null;
        try {
            dt = dff.format(df.parse(str)) + " 00:00:00";
            if (istrue) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(df.parse(dt));
                cal.add(Calendar.DATE, 1);
                dt = df.format(cal.getTime());
            }
        } catch (ParseException e) {
            // ates.
        }
        return dt;
    }

    /**
     * 注释：日期转换成字符串，不包含时分秒！
     *
     * @param date
     * @throws Exception
     */

    public static String DatetoStrWithoutHour(Date date) throws Exception {

        String result = "";

        try {

            if (date != null) {

                Calendar calendar = new GregorianCalendar();

                calendar.setTime(date);

                result = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1)

                        + "-" + (calendar.get(Calendar.DATE)) + " "

                        + (calendar.get(Calendar.HOUR_OF_DAY)) + ":"

                        + (calendar.get(Calendar.MINUTE)) + ":" + (calendar.get(Calendar.SECOND));

            }

        } catch (Exception e) {

            throw e;

        }

        return result;

    }

	/*
     *
	 * @author nxk
	 * 
	 * StrToDate() 将页面获得的违法时间转化为日期型数据
	 * 
	 * return
	 */

    public static Date StrToDate(String str) throws Exception {

        if (str.length() == 0) {

            return null;

        }

        Date date = new Date();

        try {

            String str1 = StrToWellStr(str, null);

            int start = str1.indexOf('-');

            String year = str1.substring(0, start);

            start++;

            int start1 = str1.indexOf('-', start);

            String month = str1.substring(start, start1);

            int hourStart = str1.indexOf(' ');

            String day = str1.substring(start1 + 1, hourStart);

            int hourEnd = str1.indexOf(':');

            String hour = str1.substring(hourStart + 1, hourEnd);

            int minuteEnd = str1.indexOf(':', hourEnd + 1);

            String minute = str1.substring(hourEnd + 1, minuteEnd);

            String sec = str1.substring(minuteEnd + 1, minuteEnd + 3);

            int yearint = new Integer(year).intValue();

            int monthint = new Integer(month).intValue();

            int dayint = new Integer(day).intValue();

            int hourint = new Integer(hour).intValue();

            int minuteint = new Integer(minute).intValue();

            int secint = new Integer(sec).intValue();

            if ((yearint > 9999) || (yearint < 1000)) {

                throw new Exception("年份不对！");

            }

            if ((monthint > 12) || (monthint < 1)) {

                throw new Exception("月份不对！");

            }

            if ((dayint > 31) || (dayint < 1)) {

                throw new Exception("日期不对！");

            }

            if ((hourint > 23) || (hourint < 0)) {

                throw new Exception("小时不对！");

            }

            if ((minuteint > 59) || (minuteint < 0)) {

                throw new Exception("分钟不对！");

            }

            if ((secint > 59) || (secint < 0)) {

                throw new Exception("秒钟不对！");

            }
        } catch (Exception ex) {

        }

        return date;

    }

    /**
     * 注释：请在这里加入说明！
     *
     * @param str  时间字符串
     * @param type 时间类型（end 一天的结束 , start 一天的开始）
     * @return
     * @throws
     */

    public static String StrToWellStr(String str, String type) throws Exception {

        String newStr = null;

        if (str.length() == 0) {

            return null;

        }

        int start = str.indexOf('-');

        String year = str.substring(0, start);

        start++;

        int start1 = str.indexOf('-', start);

        String month = str.substring(start, start1);

        if (month.length() < 2) {

            month = "0" + month;

        }

        int hourStart = str.indexOf(' ');

        String day = "";

        if (hourStart > 0) {

            day = str.substring(start1 + 1, hourStart);

            if (day.length() < 2) {

                day = "0" + day;

            }

        } else {

            day = str.substring(start1 + 1, 10);

        }

        int hourEnd = str.indexOf(':');

        String hour = "";

        if (hourEnd > 0) {

            hour = str.substring(hourStart + 1, hourEnd);

            if (hour.length() < 2) {

                hour = "0" + hour;

            }

            int minuteEnd = str.indexOf(':', hourEnd + 1);

            String minute = "";

            String sec = "";

            if (minuteEnd == -1) {

                minute = str.substring(hourEnd + 1, str.length());

                if (minute.length() < 2) {

                    minute = "0" + minute;

                }

                sec = "00";

            } else {

                minute = str.substring(hourEnd + 1, minuteEnd);

                if (minute.length() < 2) {

                    minute = "0" + minute;

                }

                sec = str.substring(minuteEnd + 1, minuteEnd + 3);

                if (sec.length() < 2) {

                    sec = "0" + sec;

                }

            }

            newStr =

                    year

                            + month

                            + day

                            + hour

                            + minute

                            + sec;

        } else {

            if (type != null) {

                if (type.equals("start")) {

                    hour = "000000000";

                } else {

                    hour = "235959999";

                }

            }

            newStr =

                    year

                            + month

                            + day

                            + hour;

        }

        return newStr;

    }

    /**
     * 注释：将数据库中的日期字符串（20060817125012）转化成日期格式字符串（2006-08-17 12:50:12）！
     *
     * @param number
     * @return
     * @throws ExceptionTODO
     */

    public static String StringToDateStr(String number) throws Exception {

        String result = "";

        try {

            String[] str = number.split("");

            result = str[1] + str[2] + str[3] + str[4] + "-" + str[5] + str[6] + "-" + str[7]

                    + str[8] + " " + str[9] + str[10] + ":" + str[11] + str[12] + ":" + str[13]

                    + str[14];

        } catch (Exception e) {

            throw e;

        }

        return result;

    }

    /**
     * 日期格式化对象
     */

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 日期时间格式化对象
     */

    private static DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 时间格式化对象
     */

    private static DateFormat timeFormat = new SimpleDateFormat("HH:mm");

    /**
     * 时间格式化对象"yyyy-MM-dd HH:mm:ss"
     */

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取时间格式化对象 "yyyy-MM-dd"
     *
     * @return
     */

    public static final DateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * 获取时间日期格式化对象 "yyyy-MM-dd HH:mm"
     *
     * @return
     */

    public static final DateFormat getDateTimeFormat() {

        return dateTimeFormat;

    }

    /**
     * 获取时间日期格式化对象 "yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */

    public static final SimpleDateFormat getSimpleDateFormat() {

        return sdf;

    }

    /**
     * 获取当前时间的时间对象
     *
     * @return
     */

    public static final Date nowDate() {

        return new Date();

    }

    /**
     * 系统最小时间
     *
     * @return
     */

    public static final Date minDate() {

        return dateBegin(getDate(1900, 1, 1));

    }

    /**
     * 系统最大时间
     *
     * @return
     */

    public static final Date maxDate() {

        return dateEnd(getDate(2079, 1, 1));

    }

    /**
     * 获取指定时间的年
     *
     * @param date
     * @return
     */

    public static final int year(Date date) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);

    }

    /**
     * 获取指定时间的月
     *
     * @param date
     * @return
     */

    public static final int month(Date date) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        return calendar.get(Calendar.MONTH) + 1;

    }

    /**
     * 获取指定时间的日
     *
     * @param date
     * @return
     */

    public static final int day(Date date) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        return calendar.get(Calendar.DATE);

    }

    /**
     * 获取指定时间的星期
     *
     * @param date
     * @return
     */

    public static final String dayOfWeek(Date date) {
        String[] dayOfWeeks = {"", "星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        return dayOfWeeks[calendar.get(Calendar.DAY_OF_WEEK)];

    }

    /**
     * 获取一个时间对象
     *
     * @param year  格式为：2004
     * @param month 从1开始
     * @param date  从1开始
     * @return
     */

    public static final Date getDate(int year, int month, int date) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, date);

        return calendar.getTime();

    }

    /**
     * 获取一个时间对象
     *
     * @param year   格式为：2004
     * @param month  从1开始
     * @param date   从1开始
     * @param hour
     * @param minute
     * @param second
     * @return
     */

    public static final Date getDateTime(int year, int month, int date, int hour, int minute,

                                         int second) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, date, hour, minute, second);

        return calendar.getTime();

    }

    public static final Date addYear(Date oldDate, int year) {
        return addDate(oldDate, year, 0, 0);
    }
    
    public static final Date addMonth(Date oldDate, int month) {
        return addDate(oldDate, 0, month, 0);
    }
    
    public static final Date addDay(Date oldDate, int day) {
        return addDate(oldDate, 0, 0, day);
    }
    
    /**
     * 在一个已知时间的基础上增加指定的时间
     *
     * @param oleDate
     * @param year
     * @param month
     * @param date
     * @return
     */

    public static final Date addDate(Date oldDate, int year, int month, int date) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(oldDate);

        calendar.add(Calendar.YEAR, year);

        calendar.add(Calendar.MONTH, month);

        calendar.add(Calendar.DATE, date);

        return calendar.getTime();

    }

    public static final Date addHours(Date oldDate,int hours){
    	 Calendar calendar = Calendar.getInstance();

         calendar.setTime(oldDate);
         
         calendar.add(Calendar.HOUR, hours);
         
         return calendar.getTime();
    }
    
    /**
     * 返回两个时间相差的月数
     *
     * @param from
     * @param to
     * @return
     */

    public static final int monthSub(Date from, Date to) {
        int month = 0;
        from = monthBegin(from);
        to = monthBegin(to);

        while (from.before(to)) {
            month++;
            from = addDate(from, 0, 1, 0);
        }
        return month;
    }

    /**
     * 返回两个时间相差的天数
     *
     * @param a
     * @param b
     * @return
     */

    public static final int dateSub(Date a, Date b) {

        int date = (int) (a.getTime() / (24 * 60 * 60 * 1000) - b.getTime() / (24 * 60 * 60 * 1000));

        return date <= 0 ? 0 : date;

    }

    public static final int dateSubAddOne(Date a, Date b) {

        int date = (int) (a.getTime() / (24 * 60 * 60 * 1000) - b.getTime() / (24 * 60 * 60 * 1000));

        return date <= 0 ? 1 : date + 1;

    }

    /**
     * 返回两个时间相差多少分钟
     *
     * @param a
     * @param b
     * @return
     */

    public static final int subSecond(Date a, Date b) {

        return (int) (a.getTime() / (1000) - b.getTime() / (1000));

    }

    public static final int subSecond(String str, Date b) {

        Date a = null;

        try {

            a = timeFormat.parse(str);

        } catch (ParseException e) {

            return 0;

        }

        return (int) ((a.getTime() % (24 * 60 * 60 * 1000)) / 1000 - (b.getTime() % (24 * 60 * 60 * 1000)) / 1000);

    }

    /**
     * 一天的开始时间
     *
     * @param date
     * @return
     */

    public static final Date dateBegin(Date date) {

        if (date == null)

            return null;

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        dateBegin(calendar);

        return calendar.getTime();

    }

    /**
     * 一天的结束时间
     *
     * @param date
     * @return
     */

    public static final Date dateEnd(Date date) {

        if (date == null)

            return null;

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        dateEnd(calendar);

        return calendar.getTime();

    }

    /**
     * 一天的结束时间
     *
     * @param calendar
     * @return
     */

    public static final Calendar dateEnd(Calendar calendar) {

        if (calendar == null)

            return null;

        calendar.set(Calendar.HOUR_OF_DAY, 23);

        calendar.set(Calendar.MINUTE, 59);

        calendar.set(Calendar.SECOND, 59);

        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;

    }

    /**
     * 一天的开始时间
     *
     * @param calendar
     * @return
     */

    public static final Calendar dateBegin(Calendar calendar) {

        if (calendar == null)

            return null;

        calendar.set(Calendar.HOUR_OF_DAY, 0);

        calendar.set(Calendar.MINUTE, 0);

        calendar.set(Calendar.SECOND, 0);

        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;

    }
   
    /**
     * 一月的开始时间
     *
     * @param date
     * @return
     */

    public static final Date monthBegin(Date date) {

        if (date == null)

            return null;

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        int day = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);

        calendar.set(Calendar.DATE, day);

        dateBegin(calendar);

        return calendar.getTime();

    }

    /**
     * 一月的技术时间
     *
     * @param date
     * @return
     */

    public static final Date monthEnd(Date date) {

        if (date == null)

            return null;

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.set(Calendar.DATE, day);

        dateEnd(calendar);

        return calendar.getTime();

    }

    /**
     * 一年的开始时间
     *
     * @param date
     * @return
     */

    public static final Date yearBegin(Date date) {

        if (date == null)

            return null;

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.MONTH, 0);

        calendar.set(Calendar.DATE, 1);

        dateBegin(calendar);

        return calendar.getTime();

    }

    /**
     * 一年的结束时间
     *
     * @param date
     * @return
     */

    public static final Date yearEnd(Date date) {

        if (date == null)

            return null;

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.MONTH, 11);

        calendar.set(Calendar.DATE, 31);

        dateEnd(calendar);

        return calendar.getTime();

    }

    /**
     * 从字符串转换为date
     * <p/>
     * 默认格式为 "yyyy-MM-dd"
     *
     * @param source
     * @return
     */

    public static final Date parseDate(String source) {

        if (logger.isDebugEnabled()) {

            logger.debug("parseDate(String) - start");

        }

        if (source == null || source.length() == 0)

            return null;

        try {

            Date returnDate = dateFormat.parse(source);

            if (logger.isDebugEnabled()) {

                logger.debug("parseDate(String) - end");

            }

            return returnDate;

        } catch (ParseException e) {

            logger.error("DateUtil parseDate error", e);

            if (logger.isDebugEnabled()) {

                logger.debug("parseDate(String) - end");

            }

            return null;

        }

    }

    /**
     * 从字符串转换为date
     * <p/>
     * 默认格式为 "yyyy-MM-dd HH:mm"
     *
     * @param source
     * @return
     */

    public static final Date parseDateTime(String source) {

        if (source == null || source.length() == 0)

            return null;

        try {

            return dateTimeFormat.parse(source);

        } catch (ParseException e) {

            logger.error("DateUtil parseDate error", e);

            return null;

        }

    }

    /**
     * 从字符串转换为date
     * <p/>
     * 默认格式为 "yyyy-MM-dd HH:mm:ss"
     *
     * @param source
     * @return
     */

    public static final Date parseDateToHMS(String source) {

        if (source == null || source.length() == 0)

            return null;

        try {

            return sdf.parse(source);

        } catch (ParseException e) {

            logger.error("DateUtil parseDate error", e);

            return null;

        }

    }

    /**
     * 格式化输出
     * <p/>
     * 默认格式为 "yyyy-MM-dd HH:mm"
     *
     * @param date
     * @return
     */

    public static String formatDateTime(Date date) {

        if (date == null)

            return "";

        return dateTimeFormat.format(date);

    }

    /**
     * 从Date装化为字符串
     * <p/>
     * 格式化输出
     * <p/>
     * 默认格式为 "yyyy-MM-dd HH:mm:ss"
     *
     * @param date
     * @return
     */

    public static String formatDateToHMS(Date date) {

        if (date == null)

            return "";

        return sdf.format(date);

    }

    /**
     * 注释：验证单个时间是否合法！
     *
     * @param time
     * @return
     * @throws Exception
     */

    public static boolean checkoutTime(String time) throws Exception {

        String[] timearray = time.split("\\:");// ":"为特殊符号，不能直接使用split(":")来分隔字符串
        // ，必须用split("\\:")

        // 判断输入的时间是否为2位

        if (timearray.length != 3) {

            throw new Exception("输入的时间长度有误！");

        }

        String timeHour = timearray[0];

        String timeMinute = timearray[1];

        // 判断是否为数字

        if (!isNumber(timeHour) || !isNumber(timeMinute)) {

            throw new Exception("输入的时间不是数字！");

        }

        // 判断IP地址是否规范

        try {

            ipCriterion(timeHour, timeMinute);

        } catch (Exception e) {

            throw e;

        }

        return true;

    }

    /**
     * 注释：判断时间输入是否合法！
     *
     * @param timeHour
     * @param timeMinute
     * @return
     * @throws Exception
     */

    private static boolean ipCriterion(String timeHour, String timeMinute) throws Exception {

        int timeone = Integer.parseInt(timeHour);

        int timetwo = Integer.parseInt(timeMinute);

        // 第一个时间在0-23之间，第二个时间在0-59之间；

        if (timeone < 0 || timeone > 23 || timetwo < 0 || timetwo > 59) {

            throw new Exception("输入的时间值错误！");

        }

        return true;

    }

    /**
     * 注释：判断时间是否为数字！
     *
     * @param str
     * @return
     */

    private static boolean isNumber(String str) {

        if (str == null || str.length() <= 0) {

            return false;

        }

        int num = str.length();

        for (int i = 0; i < num; i++) {

            char c = str.charAt(i);

            if (c < '0' || c > '9') {

                return false;

            }

        }

        return true;

    }

    public static int byteToInt(byte[] b) {

        int s = 0;

        for (int i = 0; i < 3; i++) {

            if (b[i] >= 0)

                s = s + b[i];

            else

                s = s + 256 + b[i];

            s = s * 256;

        }

        if (b[3] >= 0) // 最后一个之所以不乘，是因为可能会溢出

            s = s + b[3];

        else

            s = s + 256 + b[3];

        return s;

    }

    /**
     * 注释：字符串转化为日期（calendar）！
     *
     * @param date
     * @return
     * @throws Exception
     */

    public static Calendar stringToCalendar(String date) throws Exception {

        Calendar calendar = Calendar.getInstance();

        try {

            if (date != null && !date.equals("")) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Date d = sdf.parse(date);

                calendar.setTime(d);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return calendar;

    }

    /**
     * 注释：日期（calendar）转化为字符串！
     *
     * @param calendar
     * @return
     * @throws Exception
     */

    public static String calendarToString(Calendar calendar) throws Exception {

        String result = "";

        try {

            if (calendar != null) {

                Date d = calendar.getTime();

                SimpleDateFormat sdf = getSimpleDateFormat();

                result = sdf.format(d);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return result;

    }

    /**
     * 注释：根据开始时间和结束时间获取这段时间的每一天！
     *
     * @param kssj
     * @param jssj
     * @return
     * @throws Exception
     */

    public static String[] getDateSegment(String kssj, String jssj) throws Exception {

        int total = dateSub(new SimpleDateFormat("yyyy-MM-dd").parse(jssj), new SimpleDateFormat(

                "yyyy-MM-dd").parse(kssj));

        String[] dateString = new String[total + 1];

        Calendar startCalendar = stringToCalendar(kssj);

        Calendar endCalendar = stringToCalendar(jssj);

        dateString[0] = kssj;

        try {

            for (int i = 1; !startCalendar.equals(endCalendar); i++) {

                startCalendar.add(Calendar.DAY_OF_YEAR, 1);

                dateString[i] = new SimpleDateFormat("yyyy-MM-dd").format(startCalendar.getTime());

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return dateString;

    }

    /**
     * 从字符串转化为Timestamp 字符串时间格式 yyyy-MM-dd HH：mm:ss
     *
     * @param source
     * @return
     */
    public static final Timestamp parseTimestampTime(String source) {
        if (source == null || source.length() == 0) {
            return null;
        }
        try {
            return new Timestamp(sdf.parse(source).getTime());
        } catch (ParseException e) {
            logger.error("DateUtil parseDate error", e);
            return null;
        }
    }

    public static Date getAddDate(Date date, int addNum) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.add(Calendar.DATE, addNum);
        return cld.getTime();
    }

    /**
     * 获取周第一天
     */
    public static String getWeekFirstDay(Date date) {
        return formatDate(getWeekBegin(date), DATE_DAY);
    }

    /**
     * 获取周最后一天
     */
    public static String getWeekLastDay(Date date) {
        return formatDate(getWeekEnd(date), DATE_DAY);
    }

    /**
     * 获取月第一天
     */
    public static String getMonthFirstDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return formatDate(cal.getTime(), DATE_DAY);
    }

    /**
     * 获取月最后一天
     */
    public static String getMonthLastDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return formatDate(cal.getTime(), DATE_DAY);
    }

    public static int getMonthSpace(String date1, String date2) throws ParseException {

        int result = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));

        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);

        return result == 0 ? 1 : Math.abs(result);

    }

    public static Date nextMonthFirstDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    public static Date halfYearFirstDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -5);
        return calendar.getTime();
    }

    public static String getCurrMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return String
     */
    public static String getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return formatDate(currYearFirst, DATE_DAY);
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return String
     */
    public static String getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return formatDate(currYearLast, DATE_DAY);
    }

    /**
     * 获取指定月份第一天
     *
     * @param year
     * @param month
     * @return String
     */
    public static String getMonthFirst(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return formatDate(calendar.getTime(), DATE_DAY);
    }

    /**
     * 获取指定月份最后一天
     *
     * @param year
     * @param month
     * @return String
     */
    public static String getMonthLast(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return formatDate(calendar.getTime(), DATE_DAY);
    }

    public static int calculateMonthIn(Date date1, Date date2) {
        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(date1);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(date2);
        int c =
                (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12 + cal1.get(Calendar.MONTH)
                        - cal2.get(Calendar.MONTH);
        return c;
    }
    
    /**
     * 一周的开始时间
     *
     * @param date
     * @return
     */

    public static final Date getWeekBegin(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        dateBegin(cal);
        return cal.getTime();
    }
    public static final Date getWeekEnd(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        dateEnd(cal);
        return cal.getTime();
    }
    
    /**
     * 获取本月有几周（自然周，满7天，1号为第一周，包含1号的上月日期计算在下个月）
     *
     * @param date
     * @return
     */

    public static final Integer getWeekNumOfMonth(Date date) {
        if (date == null) return null;
        
        Date monthBegin = monthBegin(date);
        Date monthBegin1 = monthBegin(DateUtil.getWeekEnd(date));
        
        Date tempDate = null;
        Date monthEnd = null;
        
        if(monthBegin.equals(monthBegin1)){
        	tempDate = DateUtil.getWeekBegin(monthBegin);
        	monthEnd = DateUtil.monthEnd(date);
        }else{
        	//如周末在下个月则计算到下个月
        	tempDate = DateUtil.getWeekBegin(monthBegin1);
        	monthEnd = DateUtil.monthEnd(monthBegin1);
        }
        int i=0;
		while(tempDate.before(monthEnd)){
			i++;
			tempDate = DateUtil.addDay(tempDate, 7);
			if(DateUtil.dateEnd(DateUtil.addDay(tempDate, 6)).after(monthEnd)) break;
		}
        return i;
    }
    
    /**
     * 获取当天为本月的第几周
     *
     * @param date
     * @return
     */

    public static final Integer getWeekOfMonth(Date date) {
        if (date == null) return null;
        
        if(DateUtil.getWeekEnd(date).after(monthEnd(date))){
        	return 1;
        }
        
        Date tempDate = DateUtil.getWeekBegin(monthBegin(date));
        int i=0;
		while(tempDate.before(DateUtil.monthEnd(date))){
			i++;
			tempDate = DateUtil.addDay(tempDate, 7);
			if(DateUtil.addDay(tempDate, 7).after(DateUtil.monthEnd(date))) break;
			if(tempDate.after(date)) break;
		}
        return i;
    }
    
    public static final boolean equalsDay(Date date1,Date date2) {
        return DateUtil.dateBegin(date1).equals(DateUtil.dateBegin(date2));
    }
    
    public static int datediff(Date d1,Date d2) throws ParseException{
    	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
    	 d1=sdf.parse(sdf.format(d1));  
    	 d2=sdf.parse(sdf.format(d2));  
         Calendar cal = Calendar.getInstance();    
         cal.setTime(d1);    
         long time1 = cal.getTimeInMillis();                 
         cal.setTime(d2);    
         long time2 = cal.getTimeInMillis();         
         long between_days=(time1-time2)/(1000*3600*24);  
             
        return Integer.parseInt(String.valueOf(between_days));    
    }
	/**
     * 判断时间是不是今天
     * @param date
     * @return    是返回true，不是返回false
     */
	public static boolean isNow(Date date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);
         
        //对比的时间
        String day = sf.format(date);
         
        return day.equals(nowDay);    
    }
	
	/**
     * 判断时间是不是今天
     * @param date
     * @return    是返回true，不是返回false
     */
	public static boolean isNow(String date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        //获取今天的日期
        String nowDay = sf.format(now);
         
        //对比的时间
        String day = sf.format(date);
         
        return day.equals(nowDay);    
    }
    public static void main(String[] args) throws ParseException {
    	//System.out.println(datediff(new Date(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-11-10 00:00:00")));
    	
    	Integer num = getWeekNumOfMonth(DateUtil.parseDate("2018-03-01"));
    	System.out.println(num);
    	
//    	String jssj="2016-03-20";
//    	String kssj="2016-03-23";
//    	 int seg = dateSub(new SimpleDateFormat("yyyy-MM-dd").parse(kssj), new SimpleDateFormat(
//                 "yyyy-MM-dd").parse(jssj));
    	 //System.out.println(seg);
        // String str = "2010-10-02 01:00:00";
        // System.out.println(df.format(df.parse(str)));
        // System.out.println(getDateStart(str, false));
        // System.out.println(getDateStart(str, true));
        // System.out.println(getFtpDatePath());
        // System.out.println(getMonthLastDay(new Date()));
        // System.out.println(getDataMonth(new Date()));
//		 System.out.println(MyCalendar.getMonthSpace("1982-12-8", "2012-12-12"));
//		String startIime = "2014-01-01";
//		String endTime = "2016-06-01";
//		List<String> list = new ArrayList<String>();
//		long diff = DateUtil.calculateMonthIn(parseDate(endTime), parseDate(startIime));
//		if (diff > 0) {
//			for (int i = 0; i <= diff; i++) {
//				if (i == 0) {
//					System.out.println(startIime);
//					continue;
//				}
//				Date date = DateUtil.nextMonthFirstDate(DateUtil.parseDate(startIime + "-01"));
//				list.add(DateUtil.getCurrMonth(date));
//				startIime = DateUtil.getCurrMonth(date);
//				System.out.println(startIime);
//			}
//		}
         // System.out.println(halfYearFirstDate(new Date()));
//        System.out.println(DateUtil.formatDate(getWeekBegin(DateUtil.parseDate("2016-02-23 00:00:00"))));
//        System.out.println(DateUtil.formatDate(getWeekEnd(DateUtil.parseDate("2016-02-28 00:00:00"))));
        //  System.out.println(getCurrMonth(halfYearFirstDate(new Date())));
          
//          for(int i=0;i<7;i++){
//        	  Date date = parseDate("2016-06-"+(12+i));
//        	  System.out.print("2016-06-"+(12+i)+"\t");
//              System.out.print(getWeekBegin(date)+"\t");
//              System.out.print(getWeekEnd(date)+"\n");
//              
//          }
    }
}
