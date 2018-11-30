package com.qftx.tsm.worklog.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class MyTest {
	static String name = null;
	public static void main(String[] args) { 
		//System.out.println(upper("abc"));;
	a();
	}
	
	public static void run1(Object object){
		Class clazz=object.getClass();
		Field[] fileds = clazz.getDeclaredFields();
		StringBuilder sb= new StringBuilder();
		for (Field field : fileds) {
			String type = field.getType().getSimpleName();
			String name = upper(field.getName());
			if("Integer".equals(type)){
				System.out.println("total.set"+name+"(total.get"+name+"()+(bean.get"+name+"()==null?0:bean.get"+name+"()));");
			}else if("Double".equals(type)){
				System.out.println("total.set"+name+"(BigDecimal.valueOf(total.get"+name+"()).add(BigDecimal.valueOf(bean.get"+name+"()==null?0:bean.get"+name+"())).doubleValue());");
			}
			
			//System.out.println("<result column=\""+field.getName()+"\" property=\""+field.getName()+"\"/>");
		}
	}
	
	public static String  upper(String str){
		
		return str.replace(str.subSequence(0, 1),str.subSequence(0, 1).toString().toUpperCase());
	}
	
	public static void a() {
		Double money = 123446.23d;
		BigDecimal bigDecimal =new BigDecimal(money/10000);  
		money = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
		System.out.println(money);
	}
	
	public static  void run(Object object){
		Class clazz=object.getClass();
		Field[] fileds = clazz.getDeclaredFields();
		StringBuilder sb= new StringBuilder();
		for (Field field : fileds) {
			if("serialVersionUID".equals(field.getName())){
				continue;
			}
			System.out.println("<result column=\""+field.getName()+"\" property=\""+field.getName()+"\"/>");
			sb.append(name+"."+field.getName()+",");
		}
		System.out.println(sb);
	}
	
	
	
	
	
	
	
}
