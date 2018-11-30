package com.qftx.base.auth.scontrol;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.qftx.common.BaseUtest;
import com.qftx.common.util.CodeUtils;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.IContants;
import com.qftx.common.util.boss.FwCodec;

public class OrgMemberControllerTest extends BaseUtest {

	@Test
	public void testUpdateLoginPwdToAuthPlat() {
		byte[] reData = new byte[]{101, 121, 74, 106, 98, 50, 82, 108, 73, 106, 111, 105, 88, 49, 82, 90, 85, 69, 86, 102, 82, 86, 74, 83, 84, 49, 73, 105, 76, 67, 74, 107, 90, 88, 78, 106, 73, 106, 111, 105, 53, 111, 54, 108, 53, 89, 43, 106, 53, 55, 71, 55, 53, 90, 54, 76, 54, 90, 83, 90, 54, 75, 43, 118, 73, 110, 48, 61};
		System.out.println(reData.length);
		String key = ConfigInfoUtils.getStringValue("aes_key");
		
		try {
			String responeStr = "" ;
			//responeStr = FwCodec.aesDeCbc5(key, reData);
			byte[] resData = CodeUtils.base64Decode(reData);
			System.out.println(resData.length);
			String reJsonStr = new String(CodeUtils.decrypt(key.getBytes(IContants.CHAR_ENCODING),resData),IContants.CHAR_ENCODING);
			System.out.println(reJsonStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
