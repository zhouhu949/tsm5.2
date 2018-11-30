package com.qftx.tsm.call;

import com.qftx.base.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestCallTodyStartTime {
    public static void main(String[] args) {
        String str= DateUtil.formatDate(DateUtil.dateBegin(new Date()), "YYYY-MM-dd HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("当天Y="+str);
        System.out.println("当天y="+df.format(new Date()));
        System.out.println("本周="+getStartDateStr(2));
        System.out.println("本月="+getStartDateStr(3));
        System.out.println("本周一" + df.format(getThisWeekMonday(new Date())));
    }
    public static String getStartDateStr(Integer type) {
        String str = "";
        if (type == 1) {
            str = DateUtil.formatDate(DateUtil.dateBegin(new Date()), "yyyy-MM-dd HH:mm:ss");
        } else if (type == 2) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            str = DateUtil.formatDate(cal.getTime(), DateUtil.Data_ALL);
        } else if (type == 3) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.DAY_OF_MONTH, 1);
            str = DateUtil.formatDate(cal.getTime(), DateUtil.Data_ALL);
        } else if (type == 4) {
            str = DateUtil.formatDate(DateUtil.getAddDate(new Date(), -180), DateUtil.Data_ALL);
        }
        return str;
    }
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }
}
