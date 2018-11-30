package com.qftx.tsm.resource.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedService;
import com.qftx.tsm.cust.dao.StaffResourceMgMapper;
import com.qftx.tsm.cust.dto.ResourceGroupDto;
import com.qftx.tsm.cust.service.StaffResourceMgService;

public class TestStaffResourceMg extends BaseUtest {
	@Autowired
	private StaffResourceMgMapper staffResourceMgMapper;
	@Autowired
	private StaffResourceMgService staffResourceMgService;
	@Autowired
	transient private OrgGroupService orgGroupService;
	@Autowired
	private CachedService cachedService;

	/**
	 * 资源组
	 * 
	 * @create 2015年12月2日 下午1:15:11 wuwei
	 * @history
	 */
	// @Test
	public void testResGroup() {
		System.out.println("根据orgid获取资源组---------------开始");
		Map<String, String> map = new HashMap<String, String>();
		String orgId = ShiroUtil.getUser().getOrgId();
		map.put("orgId", orgId);
		List<ResourceGroupDto> resGroupList = staffResourceMgService.getResGroupList(map);
		System.out.println("根据orgid获取资源组列表。orgId" + orgId + JsonUtil.getJsonString(resGroupList));
		System.out.println("根据orgid获取资源组---------------结束");
	}

	/**
	 * 根据条件获取资源数
	 * 
	 * @create 2015年12月2日 下午1:23:52 wuwei
	 * @history
	 */
	// @Test
	public void testGetResNum() {
		System.out.println("获取资源数-----------开始");
		String resGroupId = "5bb24609b80d4c1abc28949641ad1ae6";// 总经办
		String starttime = "2015-12-01 12:11:11";
		String endtime = "2015-12-03 12:11:11";
		Map<String, Object> map = new HashMap<String, Object>();
		String orgId = ShiroUtil.getUser().getOrgId();
		map.put("orgId", orgId);
		map.put("groupId", resGroupId);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		// 1获取 可分配资源总数
		int resSum = staffResourceMgService.getResourcemaxSum(map);
		System.out.println("获取资源数=" + resSum);
		System.out.println("获取资源数-----------结束");
	}

	/**
	 * 分配资源
	 * 
	 * @create 2015年12月2日 下午1:23:52 wuwei
	 * @history
	 */
	// @Test
	public void testAssignRes() {
		System.out.println("分配资源-----------开始");
		String resGroupId = "5bb24609b80d4c1abc28949641ad1ae6";
		String starttime = "2015-12-01 12:11:11";
		String endtime = "2015-12-03 12:11:11";
		ShiroUser user = ShiroUtil.getUser();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> resultMap = new HashMap<String, String>();
		map.put("v_staffIds", "hn003,hn056,hn004,hn005,hn006,hn007,hn999");
		map.put("v_groupId", resGroupId);
		map.put("v_starttime", starttime);
		map.put("v_endtime", endtime);
		map.put("v_plan_sum", "6");
		map.put("v_every_sum", "1");
		map.put("resNum", "");
		map.put("v_org_id", user.getOrgId());
		map.put("v_input_acc", user.getAccount());
		Map<String, Object> resmap = new HashMap<String, Object>();
		String orgId = user.getOrgId();
		resmap.put("orgId", orgId);
		resmap.put("groupId", resGroupId);
		resmap.put("starttime", starttime);
		resmap.put("endtime", endtime);
		// 1获取 可分配资源总数
		int resSum = staffResourceMgService.getResourcemaxSum(resmap);
		System.out.println("获取 可分配资源总数=" + resSum);
		map.put("resNum", resSum + "");
		// 1获取 可分配资源总数
//		staffResourceMgService.saveAssginRes(map, resultMap, user.getOrgId(), user.getAccount());
		System.out.println("分配资源map=" + resultMap.get("result") + "|msg=" + resultMap.get("msg"));
		System.out.println("分配资源-----------结束");
	}

	/**
	 * 回收资源
	 * 
	 * @create 2015年12月2日 下午1:23:52 wuwei
	 * @history
	 */
	@Test
	public void testRecyleRes() {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> resultMap = new HashMap<String, String>();
		ShiroUser user = ShiroUtil.getShiroUser();
		map.put("v_staffAccs", "hn001,hn002");
		map.put("v_recyleSum", "10");
		System.out.println("回收资源-----------开始");
		// 1获取 可分配资源总数
//		staffResourceMgService.saveRecycleResource(map, resultMap,user.getOrgId(),user.getAccount());
		System.out.println("回收资源map=" + resultMap.get("result") + "|msg=" + resultMap.get("msg"));
		System.out.println("回收资源-----------结束");
	}

}
