package com.qftx.sys.dao;

import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.sys.bean.Knowlege;
import com.qftx.tsm.sys.bean.KnowlegeGroup;
import com.qftx.tsm.sys.dao.KnowlegeGroupMapper;
import com.qftx.tsm.sys.dao.KnowlegeMapper;
import com.qftx.tsm.sys.dto.KnowlegeDto;
import com.qftx.tsm.sys.dto.KnowlegeGroupDto;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestKnowlege extends BaseUtest{
	@Autowired(required=false)
	public KnowlegeMapper knowlegeMapper;
	@Autowired(required=false)
	KnowlegeGroupMapper knowlegeGroupMapper;
	/** 
	 * 查看知识库列表
	 * @create  2015年11月12日 上午9:27:39 wuwei
	 * @history  
	 */
	
//	@Ignore
	@Test
	public void testqueryKnowlege() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgId", shiroUser.getOrgId());
		List<KnowlegeGroupDto> list = knowlegeGroupMapper.findKnowlegeGroup(map);
		System.out.println("查询知识分组列表："+JsonUtil.getJsonString(list));
		
		int unGroup = knowlegeGroupMapper.findKnowlegeUnGroup(map);
		System.out.println("查询知识未分组数量："+unGroup);
		
		int allKnowlege = knowlegeGroupMapper.findAllKnowleges(map);
		System.out.println("查询知识数量："+allKnowlege);
		
		KnowlegeDto knowlegeDto = new KnowlegeDto();
		knowlegeDto.setOrgId(shiroUser.getOrgId());
		List<Knowlege> knowlegeList = knowlegeMapper.findListPage(knowlegeDto);
		System.out.println("知识列表=" + JsonUtil.getJsonString(knowlegeList));
	}
	
	
	/** 
	 * 删除知识
	 * @create  2015年11月13日 下午8:19:36 wuwei
	 * @history  
	 */
	@Ignore
	@Test
	public void delKnowlege() {
	    System.out.println("删除知识开始---");
	    String ids= "c54e0f16572c470ea7eefcdb5e60d303";
		String[] idsArray = ids.split("\\,");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", idsArray);
		map.put("updateacc", shiroUser.getAccount());
		knowlegeMapper.deleteKnowlegeBatch(map);
		System.out.println("删除知识结束---");
	}
	@Ignore
	@Test
	public void vitifyGroupName(){
		System.out.println("验证知识库名称重名开始------");
		KnowlegeGroup knowlegeGroup = new KnowlegeGroup();
		String groupName ="ddddd";
		knowlegeGroup.setOrgId(shiroUser.getOrgId());
		knowlegeGroup.setGroupName(groupName);
		List<KnowlegeGroup> tmpList = knowlegeGroupMapper.findGrpListByGrpName(knowlegeGroup);
		System.out.println(JsonUtil.getJsonString(tmpList));
		System.out.println("验证知识库名称结束------");
	}
	@Ignore
	@Test
	public void addKnowlegeGroup() {
	    System.out.println("添加知识分组开始---");
	    KnowlegeGroup knowlegeGroup = new KnowlegeGroup();
	    String groupId = GuidUtil.getId();
	    String groupName = Math.random()+"";
	    knowlegeGroup.setGroupId(groupId);
	    knowlegeGroup.setGroupName("test"+groupName);
	    knowlegeGroup.setOrgId(shiroUser.getAccount());
	    knowlegeGroup.setIsDel(new Short("0"));
	    knowlegeGroupMapper.insert(knowlegeGroup);
		System.out.println("添加知识分组 id="+groupId+"---");
		System.out.println("添加知识分组结束---");
	}
	@Ignore
	@Test
	public void delKnowlegeGroup() {
	    System.out.println("删除知识分组开始---");
	    KnowlegeGroup knowlegeGroup = new KnowlegeGroup();
	    String groupId = "ac6b3d8405d845c59bdc55594d0cd99e";
	    String groupName = groupId;
	    knowlegeGroup.setGroupId(groupId);
	    knowlegeGroup.setGroupName(groupName);
	    knowlegeGroup.setOrgId(shiroUser.getAccount());
	    knowlegeGroup.setIsDel(new Short("1"));
		Map<String, String> map = new HashMap<String, String>();
		map.put("groupId", groupId);
		map.put("inputAcc", shiroUser.getAccount());
		knowlegeGroupMapper.updateKnowlegeGroup(map);
		System.out.println("删除知识分组结束---");
	}
    @Ignore
	@Test
	public void updateKnowlegeGroup() {
	    System.out.println("修改知识分组开始---");
	    KnowlegeGroup knowlegeGroup = new KnowlegeGroup();
	    String groupId = "cc6eb3ea19c64eb3a9001c8d1afdbca8";
	    String groupName = groupId;
	    knowlegeGroup.setGroupId(groupId);
	    knowlegeGroup.setGroupName(groupName);
	    knowlegeGroup.setOrgId(shiroUser.getAccount());
	    knowlegeGroup.setIsDel(new Short("0"));
	    knowlegeGroupMapper.updateTrends(knowlegeGroup);
		System.out.println("修改知识分组结束---");
	}
}
