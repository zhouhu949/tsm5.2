package com.qftx.tsm.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TestDate {
	
	public static List<String> getAlldate(String strDate,String endDate) throws ParseException{
		List<String> list=new ArrayList<String>();
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDates = df.parse(strDate);
        startCalendar.setTime(startDates);
        Date endDates = df.parse(endDate);
        endCalendar.setTime(endDates);
        list.add(strDate);
        	

        while(true){
            startCalendar.add(Calendar.DAY_OF_MONTH, 1);
            if(startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()){
            list.add(df.format(startCalendar.getTime()));
        }else{
            break;
        }
        }
        if(!strDate.equals(endDate)){
        list.add(endDate); 	
        }
        Collections.reverse(list);
		return list;
	}
public static void main(String[] args) throws ParseException {
	 List<String> list=getAlldate("2017-06-10","2017-06-27");
	 System.out.println(list);
}
	
//	public static void main(String[] args) throws ParseException  {
//        Calendar startCalendar = Calendar.getInstance();
//        Calendar endCalendar = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy/M/d");
//        Date startDate = df.parse("2012/2/28");
//        startCalendar.setTime(startDate);
//        Date endDate = df.parse("2012/3/2");
//        endCalendar.setTime(endDate);
//        while(true){
//            startCalendar.add(Calendar.DAY_OF_MONTH, 1);
//            if(startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()){//TODO 转数组或是集合，楼主看着写吧
//            System.out.println(df.format(startCalendar.getTime()));
//        }else{
//            break;
//        }
//        }
//}
}
