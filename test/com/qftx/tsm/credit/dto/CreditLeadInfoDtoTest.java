package com.qftx.tsm.credit.dto;

import org.junit.Test;

import com.qftx.tsm.credit.bean.CreditLeadInfoBean;

import junit.framework.Assert;

/**
 * 
 * @author chenhm
 *
 */
public class CreditLeadInfoDtoTest {

	@Test
	public void test() {
		String actual = CreditLeadInfoBean.maps.get("status").get(CreditLeadInfoBean.STATUS_DOING);
		Assert.assertEquals("待放款", actual);
	}

}
