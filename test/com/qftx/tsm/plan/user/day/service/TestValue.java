package com.qftx.tsm.plan.user.day.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;


public class TestValue {
	public static String sudId ="07cdf8010314445cac736c06343318bb" ;
	public static String orgId ="8decbe1278b646b5a462bbd4bc80bd58";
	public static String userId ="d73d4d42a3d343e799bbe3963045d323";
	public static String groupId ="14";
	public static String userAccount = "hn001";
	
	public static String [] custIds ={"f74751c0d72e45c4bfa3246d5eb93ff4","8af6607b278d47e2a4ac7c22add85659","8621585261a349b5aa5bf5bfa1d2eb74","a0a1863e19a84e9fb6a2e4c634f97ec9","69f5e2f440f541af96cc8eb3b0facf74","201bb1eecfe643e992e0c1c96560d593","9b9b52962ec74c019952810f335d6d65","8af6aa30b9dd47f78b02e4eda453a1ee","eb89227d893b4e0da861195e49bcd54c","fb262246ad4147fdb6d41c44d9aa3097","f687baa17adf402bbc3201b931701329","7753d11380aa494eac47f0f8c548bb75","35bbedc42c3c4e0297171648364597dd","7dc05152b8734db1a03e20536f3c5531","d15b32f5d55e4d7a92266ecd23ffd333","6c04268103424715bd40485b10308bc4","1b365543cbcb44d996ccbc3f54584fb1"};

	public static ShiroUser getUser(){
		return ShiroUtil.getShiroUser();
	}
	
	public static void sleep(int second){
		try {
			Thread.sleep(second*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
