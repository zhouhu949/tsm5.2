package com.qftx.tsm.credit.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.common.BaseUtest;
import com.qftx.tsm.credit.bean.CreditLeadInfoBean;

public class CreditLeadInfoServiceTest extends BaseUtest {

	@Autowired
	private CreditLeadInfoService  creditLeadInfoService;
	
	@Test
	public void testFindNotProcessedLeadInfoList() {
		List<CreditLeadInfoBean> list = creditLeadInfoService.findNotProcessedLeadInfoList("qp3", "2141254325235", "");
		System.out.println(list.size());
	}

}
