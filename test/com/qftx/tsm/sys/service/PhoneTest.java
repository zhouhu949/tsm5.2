package com.qftx.tsm.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.ValidationUtils;

import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedService;

public class PhoneTest extends BaseUtest{
	@Autowired 
	private CachedService cachedService;

//	public static void main(String[] args) {
//		try {
//			
//
//		String phone1="010765588682";
////		String phone1="07147655886";
//		Pattern pattern = Pattern.compile("^([0-9]{3}-?[0-9]{8})|([0-9]{4}-?[0-9]{7})$");
//		Matcher matcher = pattern.matcher(phone1);
//		boolean b = matcher.matches();
//		
//		if(b==true){
//			System.out.println("座机");
//		}else if(b==false){
//			System.out.println("不是座机");
//	    if(phone1.length()==12){
//		
//		if(!phone1.substring(0, 3).endsWith("010")){
//			if(phone1.startsWith("01")){
//				phone1=phone1.substring(1);
//			}
//		}
//		}
//		}
//
//
//			
//
//		System.out.println(phone1);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
	@Test
	public void test(){
		try {
			//企业黑名单
			List<String> list=cachedService.getBlackComList("fhtx");
			System.out.println("list:"+list);
			if(list!=null){
				if(list.contains("15172043578")==false){
					// 发送短信
					System.out.println("发送1");
				}else{System.out.println("-------");}
			}else{
				// 发送短信
				System.out.println("发送2");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) {
////		List<String> list =new ArrayList<String>();
////		list.add("a");
////		list.add("ab");
////		list.add("abc");
////		list.add("abc1234");
////		if(list.contains("abc1111")==false){
////			System.out.println("key");
////		}
//
//	}
}
