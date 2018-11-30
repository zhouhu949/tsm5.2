package com.qftx.tsm.wx;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.common.BaseUtest;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.cust.bean.WxBindBean;
import com.qftx.tsm.cust.dao.WxBindMapper;

public class TestWx extends BaseUtest {
	@Autowired
	private WxBindMapper wxBindMapper;

	//@Test
	public void addWx() {
		WxBindBean wxBindBean = new WxBindBean();
		wxBindBean.setAccount("ay001");
		wxBindBean.setId(SysBaseModelUtil.getModelId());
		wxBindBean.setInputAcc("ay001");
		wxBindBean.setInputDate(new Date());
		wxBindBean.setLinkName("测试人");
		wxBindBean.setLinkNameId("aaaaaaaaaaa");
		wxBindBean.setOrgId("ay");
		wxBindBean.setType(0);
		wxBindBean.setWxId("微信id");
		wxBindBean.setWxLoginId("微信loginid");
		wxBindBean.setWxName("昵称");
		try {
			wxBindMapper.insert(wxBindBean);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void editWx() {
		WxBindBean wxBindBean = new WxBindBean();
		wxBindBean.setAccount("ay002");
		wxBindBean.setId("ec1d5c7af7cb4418be7fa831e5983752");
		wxBindBean.setUpdateAcc("ay002");
		wxBindBean.setUpdateDate(new Date());
		wxBindBean.setLinkName("测试人2");
		wxBindBean.setLinkNameId("aaaaaaaaaaa2");
		wxBindBean.setOrgId("ay");
		wxBindBean.setType(0);
		wxBindBean.setWxId("微信id2");
		wxBindBean.setWxLoginId("微信loginid2");
		wxBindBean.setWxName("昵称2");
		try {
			wxBindMapper.update(wxBindBean);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
