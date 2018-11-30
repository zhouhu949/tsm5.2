package com.qftx.cust;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.common.BaseUtest;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.tao.service.TaoService;

public class TestCust extends BaseUtest {

	@Autowired
	private TaoService TaoService;

	@Test
	public void testConcat4Res() {
		try {
			System.out.println("===============开始================");
			String orgId = "khedl6";
			String timeLength = "1";
			String isConcat = "0";
			String custId = "91c4e4facc234c7daf94f950e37d2b90";
			String account = "";
			String lianXiId = "9d1fb88addcc4c5c953acaafbae71197";
//			TaoService.updateConcat4Res(custId, lianXiId, orgId, account, isConcat, timeLength);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("===============结束================");
	}
}
