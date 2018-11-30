package com.qftx.tsm.report.utils;

import com.qftx.base.util.DateUtil;
import com.qftx.common.util.StringUtils;

import java.util.Calendar;
import java.util.Date;

public class DateSubUtil {
	public static Date[] toWeek(){
    	return toWeek(new Date(),-1);
	}
	
	public static Date[] toWeek(int type){
    	return toWeek(new Date(),type);
	}
	
	public static Date[] toWeek(Date date){
    	return toWeek(date ,0);
	}
	
	public static Date[] toMonth(){
    	return toMonth(-1);
	}
	
	public static Date[] toYear(){
    	return toYear(-1);
	}
	
	public static Date[] toWeek(Date date,int type){
    	Date[] array = new Date[2];
    	
    	array[0] = DateUtil.getWeekBegin(date);
    	array[1] = DateUtil.getWeekEnd(date);
    	
		if(type==-1 && array[1].after(DateUtil.dateEnd(DateUtil.addDay(new Date(), type)))){
			array[1] = DateUtil.dateEnd(DateUtil.addDay(new Date(), type));
		}
    	return array;
	}
	/**
     * 获取X月的第X周
     *
     * @param date
     * @return
     */
	public static Date[] getWeekByMonth(Date date,int weekOfMonth){
		if (date == null) return null;
		date = DateUtil.getWeekEnd(date);
        Date tempDate = DateUtil.getWeekBegin(DateUtil.monthBegin(date));
        tempDate = DateUtil.addDay(tempDate, 7*(weekOfMonth-1));
        return toWeek(tempDate);
	}
	
	public static Date[] toMonth(int type){
		Date date = new Date();
		Date[] array = new Date[2];
    	
    	array[0] =DateUtil.monthBegin(date);
    	array[1]=DateUtil.monthEnd(date);
    	
    	if(array[1].after(DateUtil.dateEnd(DateUtil.addDay(new Date(), type)))){
			array[1] = DateUtil.dateEnd(DateUtil.addDay(new Date(), type));
		}
    	return array;
	}
	
	public static Date[] toYear(int type){
		Date date = new Date();
		Date[] array = new Date[2];
    	
    	array[0] =DateUtil.yearBegin(date);
    	array[1]=DateUtil.yearEnd(date);
    	
    	if(array[1].after(DateUtil.dateEnd(DateUtil.addDay(new Date(), type)))){
			array[1] = DateUtil.dateEnd(DateUtil.addDay(new Date(), type));
		}
    	return array;
	}
	
	
	public static Date[] getDateFromStr(String fromDateStr,String toDateStr,String type,int add){
    	Date[] array = new Date[2];
    	
    	if(StringUtils.isEmpty(fromDateStr)||StringUtils.isEmpty(toDateStr)){
    		Date date= new Date();
    		if("year".equals(type)){
    			array[0]= DateUtil.yearBegin(DateUtil.addDate(date, -add, 0, 0));
    			array[1]=DateUtil.yearEnd(date);
    		}else if("month".equals(type)){
    			array[0]= DateUtil.monthBegin(DateUtil.addDate(date, 0, -add, 0));
    			array[1]=DateUtil.monthEnd(date);
    		}else if("week".equals(type)){
    			array[0]= DateUtil.getWeekBegin(date);
    			array[1]=DateUtil.getWeekEnd(date);
    		}else{
    			array[0]= DateUtil.dateBegin(DateUtil.addDate(date, 0, 0, -add));
    			array[1]=DateUtil.dateEnd(date);
    		}
    	}else{
    		if("year".equals(type)){
    			array[0]= DateUtil.monthBegin(parseDateFromStr(fromDateStr));
        		array[1]= DateUtil.monthEnd(parseDateFromStr(toDateStr));
    		}else if("month".equals(type)){
    			array[0]= DateUtil.monthBegin(parseDateFromStr(fromDateStr));
        		array[1]= DateUtil.monthEnd(parseDateFromStr(toDateStr));
    		}else{
    			array[0]= DateUtil.dateBegin(parseDateFromStr(fromDateStr));
    			array[1]=DateUtil.dateEnd(parseDateFromStr(toDateStr));
    		}
    	}
    	
    	if(array[1].after(DateUtil.dateEnd(DateUtil.addDay(new Date(), -1)))){
			array[1] = DateUtil.dateEnd(DateUtil.addDay(new Date(), -1));
		}
    	return array;
    }
	
	public static Date parseDateFromStr(String str){
    	Calendar calendar = Calendar.getInstance();
    	String[] array = str.split("-");
    	if(array.length==2){
    		calendar.set(Integer.parseInt(array[0]), Integer.parseInt(array[1])-1, 1);
    	}else if(array.length==3){
    		calendar.set(Integer.parseInt(array[0]), Integer.parseInt(array[1])-1, Integer.parseInt(array[2]));
    	}
    	return calendar.getTime();
    }
	
	public static void main(String[] args) {
		
		Date date = DateUtil.parseDate("2018-01-01");
		
		Date[] a = DateSubUtil.getWeekByMonth(date, 1);
		System.out.println(DateUtil.formatDate(a[0]));
		System.out.println(DateUtil.formatDate(a[1]));
	}
}
