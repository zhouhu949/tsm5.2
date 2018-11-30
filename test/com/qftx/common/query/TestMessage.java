package com.qftx.common.query;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Locale;
/**
 * Created by bxl on 2015/10/26.
 */
public class TestMessage {
    public static void main(String[] args) {
       ApplicationContext context = new ClassPathXmlApplicationContext("mvc/applicationContext-mvc-share.xml");
        System.out.println(context.getDisplayName());
       // String name = context.getMessage("customer.name", new Object[] { 28,"http://www.eeee.com" }, Locale.US);
     //  System.out.println("English : " + name);
        String namechinese = context.getMessage("success", new Object[] {28, "http://www.qftx.com" },new Locale("zh", "CN"));
        System.out.println("Chinese : " + namechinese);

        namechinese = context.getMessage("success", new Object[] {28, "http://www.qftx.com" }, new Locale("en", "US"));
        System.out.println("English : " + namechinese);
    }
}
