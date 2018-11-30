package com.qftx.base.team;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.team.bean.TsmTeamGroupMemberBean;
import com.qftx.base.team.service.TsmTeamGroupMemberService;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.user.day.service.TestValue;

public class TsmTeamGroupMemberServiceTest extends BaseUtest {
	
	@Autowired private TsmTeamGroupMemberService service;
	
	@Test
	public void findByGroupIdTest(){
		List<TsmTeamGroupMemberBean> list = service.findByGroupId(TestValue.orgId, "12");
	
		System.out.println(JsonUtil.getJsonString(list));
	}
	
}
