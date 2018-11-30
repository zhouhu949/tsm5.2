package com.qftx.base.team;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.user.day.service.TestValue;

public class TsmTeamGroupServiceTest extends BaseUtest {
	@Autowired private TsmTeamGroupService service;
	
	@Test
	public void queryManageTeamListTest(){
		ShiroUser user = TestValue.getUser();
		
		List<TeamGroupBean> list = service.queryManageTeamList(user.getOrgId(), user.getAccount());
		System.out.println(JsonUtil.getJsonString(list));
	}
	
	@Test
	public void queryManageGroupListTest(){
		ShiroUser user = TestValue.getUser();
		
		List<TeamGroupBean> list = service.queryManageGroupList(user.getOrgId(), user.getAccount());
		System.out.println(JsonUtil.getJsonString(list));
	}
	
	@Test
	public void findFatherGroupIds(){
		List<String> list = service.findFatherGroupIds("hyx49", "42c41353bdcc497db57cedd0dee6664f");
		List<TeamGroupBean> list1 = service.findFatherGroups("hyx49", "42c41353bdcc497db57cedd0dee6664f");
	
		System.out.println(list);
	}
	
}
