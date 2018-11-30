package com.qftx.tsm.report;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.report.dao.SilenceCustMapper;
import com.qftx.tsm.report.dto.SilenceCustDto;
import com.qftx.tsm.report.service.SilenceCustService;

public class SilenceCustServiceTest extends BaseUtest {

	@Autowired
	private SilenceCustService silenceCustService;
	@Autowired
	SilenceCustMapper silenceCustMapper;

//	@Test
	public void saveTest() {
		Date currDate = new Date();
		SilenceCustDto sBean = new SilenceCustDto();
		ShiroUser user = ShiroUtil.getShiroUser();
		sBean.setAccount(user.getAccount());
		sBean.setOrgId(user.getOrgId());
		sBean.setCurrDate(DateUtil.getDataMonth(currDate));
		try {
			/*silenceCustService.updateSilentLossReport(currDate,2, 1);
			silenceCustService.updateSilentLossReport(currDate,2, 1);
			silenceCustService.updateSilentLossReport(currDate,3, 1);
			silenceCustService.updateSilentLossReport(currDate,4, 1);
			silenceCustService.updateSilentLossReport(currDate,5, 1);*/
			SilenceCustDto tempBean = silenceCustMapper.getByCondtion(sBean);
			System.out.println("签约数=" +tempBean.getSignTotal());
			System.out.println("沉默数=" +tempBean.getSilentTotal());
			System.out.println("唤醒数=" +tempBean.getWakeTotal());
			System.out.println("到期数=" +tempBean.getExpireCustTotal());
			System.out.println("流失数=" +tempBean.getLossTotal());
			System.out.println("新增沉默数=" +tempBean.getAddWakeTotal());
			System.out.println("新增唤醒数=" +tempBean.getAddWakeTotal());
			System.out.println("新增流失数=" +tempBean.getAddLossTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getSilentCustDetail(){
		Map<String, String> map = new HashMap<String, String>();
		ShiroUser user = ShiroUtil.getShiroUser();
		map.put("orgId", user.getOrgId());
		map.put("account", "hn001");
		map.put("currDate", "2016-01");
		List<SilenceCustDto> list = silenceCustService.getSilentDetailList(map);
		System.out.println(JsonUtil.getJsonString(list));
	}
}
