package com.qftx.base.auth.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * 部门树结构 zwj
 */
public class TestAuthOrgGroupMember extends BaseUtest {

	// @Test
	public void tes1() {
		OrgGroupMapper orgGroupMapper = (OrgGroupMapper) context.getBean("orgGroupMapper");
		OrgGroupUserMapper orgGroupUserMapper = (OrgGroupUserMapper) context.getBean("orgGroupUserMapper");
		List<Map<String, Object>> reList = getTreeData(null, orgGroupMapper, orgGroupUserMapper);
		System.out.println(JsonUtil.getJsonString(reList.get(0)));
	}

	public void test2() {
		OrgGroupMapper orgGroupMapper = (OrgGroupMapper) context.getBean("orgGroupMapper");
		OrgGroupUserMapper orgGroupUserMapper = (OrgGroupUserMapper) context.getBean("orgGroupUserMapper");
		List<Map<String, Object>> reList = getTreeData_2(null, orgGroupMapper, orgGroupUserMapper);
		System.out.println(JsonUtil.getJsonString(reList));
	}

	public void test3() {
		OrgGroupUserService orgGroupUserService = (OrgGroupUserService) context.getBean("orgGroupUserService");
		List<String> list = orgGroupUserService.getTreeGroupIds("8decbe1278b646b5a462bbd4bc80bd58", "14");
		System.out.println(list.size() + "!!!!!!!!!!!!!!!!!");
		for (String str : list) {
			System.out.println(str);
		}
	}

	public void test4() {
		OrgGroupUserService orgGroupUserService = (OrgGroupUserService) context.getBean("orgGroupUserService");
		List<Map<String, Object>> jsons = orgGroupUserService.getTreeUnRoleGroupUser("d73d4d42a3d343e799bbe3963045d323", "8decbe1278b646b5a462bbd4bc80bd58",
				null);
		System.out.println(JsonUtil.getJsonString(jsons));
	}

	public void test5() {
		OrgGroupUserService orgGroupUserService = (OrgGroupUserService) context.getBean("orgGroupUserService");
		List<Map<String, Object>> jsons = orgGroupUserService.getALLTreeGroupUser("d73d4d42a3d343e799bbe3963045d323", "8decbe1278b646b5a462bbd4bc80bd58");
		System.out.println(JsonUtil.getJsonString(jsons));
	}

	public void test6() {
		OrgGroupUserService orgGroupUserService = (OrgGroupUserService) context.getBean("orgGroupUserService");
		List<Map<String, Object>> jsons = orgGroupUserService.getTreeGroupUser("d73d4d42a3d343e799bbe3963045d323", "8decbe1278b646b5a462bbd4bc80bd58");
		System.out.println(JsonUtil.getJsonString(jsons));
	}

	// @Test
	public void test7() {
		OrgGroupUserService orgGroupUserService = (OrgGroupUserService) context.getBean("orgGroupUserService");
		List<Map<String, Object>> jsons = orgGroupUserService.getTreeGroup("d73d4d42a3d343e799bbe3963045d323", "8decbe1278b646b5a462bbd4bc80bd58");
		System.out.println(JsonUtil.getJsonString(jsons));
	}

	public List<Map<String, Object>> getTreeData(String pid, OrgGroupMapper orgGroupMapper, OrgGroupUserMapper orgGroupUserMapper) {
		OrgGroup orgGroup = new OrgGroup();
		orgGroup.setOrgId("8decbe1278b646b5a462bbd4bc80bd58");
		if (StringUtils.isNotBlank(pid)) {
			orgGroup.setpId(pid);
		} else {
			orgGroup.setLevel(0);
		}
		List<OrgGroup> orgGroups = (List<OrgGroup>) orgGroupMapper.findByCondtion(orgGroup);
		List<Map<String, Object>> reList = new ArrayList<Map<String, Object>>();
		for (OrgGroup group : orgGroups) {
			Map<String, Object> reMap = new HashMap<String, Object>();
			reMap.put("id", group.getGroupId() + "G");
			reMap.put("text", group.getGroupName());
			OrgGroupUser groupUser = new OrgGroupUser();
			groupUser.setOrgId("8decbe1278b646b5a462bbd4bc80bd58");
			groupUser.setGroupId(group.getGroupId());
			List<OrgGroupUser> orgGroupUsers = (List<OrgGroupUser>) orgGroupUserMapper.findByCondtion(groupUser);
			List<Map<String, Object>> reList2 = new ArrayList<Map<String, Object>>();
			for (OrgGroupUser groupUser2 : orgGroupUsers) {
				Map<String, Object> reMap1 = new HashMap<String, Object>();
				reMap1.put("id", groupUser2.getMemberAcc() + "M");
				reMap1.put("text", groupUser2.getMemberName());
				reList2.add(reMap1);
			}
			List<Map<String, Object>> childList = getTreeData(group.getGroupId(), orgGroupMapper, orgGroupUserMapper);
			childList.addAll(reList2);
			if (childList != null && childList.size() > 0) {
				reMap.put("state", "closed");
				reMap.put("children", childList);
			}
			reList.add(reMap);
		}
		return reList;
	}

	public List<Map<String, Object>> getTreeData_2(String pid, OrgGroupMapper orgGroupMapper, OrgGroupUserMapper orgGroupUserMapper) {
		OrgGroup orgGroup = new OrgGroup();
		orgGroup.setOrgId("cd578d5cb90548a29c1d9484dc54c819");
		if (StringUtils.isNotBlank(pid)) {
			orgGroup.setpId(pid);
		} else {
			orgGroup.setLevel(0);
		}
		List<OrgGroup> orgGroups = (List<OrgGroup>) orgGroupMapper.findByCondtion(orgGroup);
		List<Map<String, Object>> reList = new ArrayList<Map<String, Object>>();
		for (OrgGroup group : orgGroups) {
			Map<String, Object> reMap = new HashMap<String, Object>();
			reMap.put("id", group.getGroupId() + "G");
			reMap.put("text", group.getGroupName());
			List<Map<String, Object>> childList = getTreeData_2(group.getGroupId(), orgGroupMapper, orgGroupUserMapper);
			if (childList != null && childList.size() > 0) {
				reMap.put("state", "closed");
				reMap.put("children", childList);
			}
			reList.add(reMap);
		}
		return reList;
	}

	@Test
	public void getLeafGroup() {
		String userId = "d73d4d42a3d343e799bbe3963045d323";
		String orgId = "8decbe1278b646b5a462bbd4bc80bd58";
		OrgGroupUserService orgGroupUserService = (OrgGroupUserService) context.getBean("orgGroupUserService");
		List<Map<String, Object>> jsons = orgGroupUserService.getTreeGroup(userId, orgId);
		System.out.println("管理权限json=" + JsonUtil.getJsonString(jsons));
		List<OrgGroup> groupList = new ArrayList<OrgGroup>();
		if (jsons != null && jsons.size() > 0) {
			for (Map<String, Object> map : jsons) {
				List<Map<String, Object>> subjsons = (List<Map<String, Object>>) map.get("children");
				if (subjsons == null) {
					OrgGroup group = new OrgGroup();
					group.setGroupId((String) map.get("id"));
					group.setGroupName((String) map.get("text"));
					groupList.add(group);
				}
			}
		}
		System.out.println("叶子部门json=" + JsonUtil.getJsonString(groupList));
	}

//	public void dg(List<OrgGroup> groupList, List<Map<String, Object>> jsons) {
//		List<Map<String, Object>> subjsons = (List<Map<String, Object>>) map.get("children");
//		if (subjsons == null) {
//			OrgGroup group = new OrgGroup();
//			group.setGroupId((String) map.get("id"));
//			group.setGroupName((String) map.get("text"));
//			groupList.add(group);
//		} else {
//			List<Map<String, Object>> subjsons = (List<Map<String, Object>>) map.get("children");
//		}
//	}
}
