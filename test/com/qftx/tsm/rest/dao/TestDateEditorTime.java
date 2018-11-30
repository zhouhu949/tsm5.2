package com.qftx.tsm.rest.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User£º bxl
 * Date£º 2015/11/16
 * Time£º 8:55
 */
public class TestDateEditorTime {
    public static void main(String[] args) {
        Date value=null;
        String text="2015-11-10 33a";
        SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            value = df.parse(text);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(value);
    }
}
