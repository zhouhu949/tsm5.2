package com.qftx.pay.api;

import org.apache.commons.codec.binary.Base64;


/**
 * <li> BASE64编码/解码
 * <li> DES加密/解密
 * <li> AES加密/解密
 * 
 * @author 企蜂通信 GuoBL
 */
public class Base64Util {
	/** 编码：UTF-8 */
	private static final String UTF8		= "UTF-8";

	
	public static String enBase64(String data){
		try {
			byte[] b = Base64.encodeBase64(data.getBytes(UTF8));
			return new String(b,UTF8).replaceAll("\n|\r", "");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String deBase64(String data){
		try {
			byte[] b = Base64.decodeBase64(data.getBytes(UTF8));
			return new String(b,UTF8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
