package com.qftx.tsm.resource.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.dao.ComResourceMapper;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.cust.dto.ResourceGroupDto;
import com.qftx.tsm.cust.service.ComResourceGroupService;
import com.qftx.tsm.cust.service.ComResourceService;

/**
 * 未分配资源
 * 
 * @author: wuwei
 * @since: 2015年11月16日 下午2:07:19
 * @history:
 */
public class TestUnAssignResource extends BaseUtest {

	@Autowired
	private ComResourceGroupService comResourceGroupService;
	@Autowired
	private ComResourceService comResourceService;
	@Autowired
	private ComResourceMapper comResourceMapper;

	/**
	 * 左侧资源分组
	 * 
	 * @create 2015年11月16日 下午2:15:19 wuwei
	 * @history
	 */
//	@Ignore
//	@Test
	public void getLeftResourceList() {
		System.out.println("未分配资源左侧分组开始---------");
		ShiroUser user = ShiroUtil.getUser();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", user.getOrgId());
		map.put("resType", "1");
		// 取得单位所有分组及成员数
		List<ResourceGroupDto> resGroupDtos = comResourceGroupService
				.getResSumList(map);
		System.out.println("资源分组=" + JsonUtil.getJsonString(resGroupDtos));


		int groupSum = comResourceGroupService.getComResSumByOrgID(map);
		System.out.println("单位成员总数=" + groupSum);
		System.out.println("未分配资源左侧分组结束---------");
	}

	/**
	 * 未分配资源列表
	 * 
	 * @create 2015年11月16日 下午2:16:10 wuwei
	 * @history
	 */
//	@Test
	public void queryUnGroupRes() {
		System.out.println("未分配资源列表开始---------");
		ShiroUser user = ShiroUtil.getUser();
		ResCustDto entity = new ResCustDto();
		entity.setOrgId(user.getOrgId());
//		List<ResCustInfoBean> resList = comResourceService
//				.queryUnAssginResListPage(entity);
//		System.out.println("未分配资源列表=" + JsonUtil.getJsonString(resList));
		System.out.println("未分配资源列表结束---------");
	}

	/**
	 * 清空资源列表查询
	 * 
	 * @create 2015年11月16日 下午2:16:10 wuwei
	 * @history
	 */
//	@Test
	public void queryUnAssginResIds() {
		System.out.println("清空资源列表查询开始---------");
		ShiroUser user = ShiroUtil.getUser();
		ResCustDto entity = new ResCustDto();
		entity.setOrgId(user.getOrgId());
		entity.setGroupName("电商");
//		 var groupName =$("#groupName").val();
//		 var groupId = $("#groupId").val();
//		 var keyWord= $("#keyWord").val();
//		 var mobileOrtel =$("#mobileOrtel").val();
//		 var startDate = $("#startDate").val();
//		 var endDate = $("#endDate").val();
//		 var cusName =$("#cusName").val();
//		 var company =$("#company").val();
		//List<ResCustInfoBean> resList = comResourceService.queryUnAssginResIdsListPage(entity);
	//	System.out.println("清空资源列表=" + JsonUtil.getJsonString(resList));
		System.out.println("清空资源列表查询结束---------");
	}	
	
	/**
	 * 清空资源
	 * 
	 * @create 2015年11月16日 下午2:16:10 wuwei
	 * @history
	 */
	@Test
	public void clearRes() {
		System.out.println("清空资源查询开始---------");
		ShiroUser user = ShiroUtil.getUser();
		user.setAccount("hzhh4001");
		user.setOrgId("hzhh4");
		ResCustDto entity = new ResCustDto();
		entity.setOrgId("hzhh4");
//		entity.setGroupName("电商");
//		entity.setName("dd");
//		entity.setKeyWord("ww");
//		entity.setGroupName("33");
		entity.setInputAcc("hzhh4001");
		entity.setResGroupId("fdbcf30d1a844676a8c93ecead187e15");
//		entity.setMobilephone("");
//		entity.setStartDate(DateUtil.formatDate(new Date()));
//		entity.setEndDate(DateUtil.formatDate(new Date()));
//		entity.setCompany("www");
//		 var groupName =$("#groupName").val();
//		 var groupId = $("#groupId").val();
//		 var keyWord= $("#keyWord").val();
//		 var mobileOrtel =$("#mobileOrtel").val();
//		 var startDate = $("#startDate").val();
//		 var endDate = $("#endDate").val();
//		 var cusName =$("#cusName").val();
//		 var company =$("#company").val();
		//System.out.println(comResourceService.clearMyRes(entity,user.getOrgId(),user.getAccount(),user.getName()));
		System.out.println("清空资源结束---------");
	}	
	/**
	 * 添加资源
	 * 
	 * @create 2015年11月16日 下午3:58:06 wuwei
	 * @history
	 */
//	@Test
	public void insertResource() {
		System.out.println("添加资源开始---------");
		ShiroUser user = ShiroUtil.getUser();
		String groupId = "5bb24609b80d4c1abc28949641ad1ae6";
		String resName = "电商";
		int i=0;
		while (i==0) {
			i++;
			ResCustInfoBean resCustInfoBean = new ResCustInfoBean();
			resCustInfoBean.setResCustId(GuidUtil.getId());
//			resCustInfoBean.setOwnerAcc("hn001");
			resCustInfoBean.setOrgId(user.getOrgId());
			String importId = user.getGroupId();
			System.out.println("importId="+importId);
			user.setGroupId("14");
			System.out.println("importId="+user.getGroupId()+"|"+user.getGroupName());
			resCustInfoBean.setStatus(1);
			resCustInfoBean.setIsDel(0);
			resCustInfoBean.setFilterCount(new Long(30501+i));
			resCustInfoBean.setType(1);
			resCustInfoBean.setFilterType(0);
			resCustInfoBean.setIsPrecedence(0);
			resCustInfoBean.setState(0);
			resCustInfoBean.setImportDeptId(importId);
			resCustInfoBean.setName(resName);
			resCustInfoBean.setResGroupId(groupId);
			resCustInfoBean.setInputDate(new Date());
			comResourceService.create(resCustInfoBean);
			System.out.println("添加资源id=" + resCustInfoBean.getResCustId());
			i++;
		}
		System.out.println("添加资源结束---------");
	}

	/**
	 * 查询
	 * 
	 * @create 2015年11月16日 下午4:04:56 wuwei
	 * @history
	 */
//	@Ignore
//	@Test
	public void getResource() {
		System.out.println("查询资源开始---------");
		ShiroUser user = ShiroUtil.getUser();
		String id = "e0fd4720442f4d208449816d913e1da9";
//		ResCustInfoBean resCustInfoBean = comResourceService
//				.getByPrimaryKey(id);
//		System.out.println("资源=" + JsonUtil.getJsonString(resCustInfoBean));
		System.out.println("查询资源结束---------");
	}

	/**
	 * 修改
	 * 
	 * @create 2015年11月16日 下午4:06:24 wuwei
	 * @history
	 */
//	@Ignore
//	@Test
	public void updateResource() {
		System.out.println("修改资源开始---------");
		ShiroUser user = ShiroUtil.getUser();
		ResCustInfoBean resCustInfoBean = new ResCustInfoBean();
		String id = "e0fd4720442f4d208449816d913e1da9";
		String name = "test";
		resCustInfoBean.setResCustId(id);
		resCustInfoBean.setOwnerAcc(user.getAccount());
		resCustInfoBean.setOrgId(user.getOrgId());
		resCustInfoBean.setStatus(1);
		resCustInfoBean.setIsDel(0);
		resCustInfoBean.setType(1);
		resCustInfoBean.setState(0);
		resCustInfoBean.setName(name);
		comResourceService.modify(resCustInfoBean);
		System.out.println("修改资源id=" + resCustInfoBean.getResCustId());
		System.out.println("修改资源结束---------");
	}

	/**
	 * 根据ids查询资源
	 * 
	 * @create 2015年11月17日 下午4:51:47 wuwei
	 * @history
	 */
//	@Ignore
//	@Test
	public void findResList() {
		System.out.println("查询资源开始---------");
		List<String> idsList = new ArrayList<String>();
		idsList.add("e0fd4720442f4d208449816d913e1da9");
		List<ResCustInfoBean> list = comResourceMapper.findResList(idsList,"");
		System.out.println("查询资源ids=" + JsonUtil.getJsonString(list));
		System.out.println("查询资源结束---------");
	}

	
	/** 
	 * 资源分配
	 * @create  2015年11月17日 下午6:12:20 wuwei
	 * @history  
	 */
//	@Ignore
//	@Test
	public void assginRes() {
		ShiroUser user = ShiroUtil.getShiroUser();
		System.out.println("资源分配开始---------");
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> resultMap = new HashMap<String, String>();
		String ids = "e0fd4720442f4d208449816d913e1da9";
		map.put("ids", ids);
		map.put("isCreate", "0");
		map.put("ownerAcc", "happy001");
		map.put("ownerName", "happy001");
		map.put("orgId", ShiroUtil.getUser().getOrgId());
		map.put("inputAcc", ShiroUtil.getUser().getAccount());
		map.put("result", "");
		map.put("msg", "");
//		comResourceService.saveAssginRes(map,resultMap,user.getOrgId(),user.getAccount());
		System.out.println("查询资源ids=" + JsonUtil.getJsonString(map));
		System.out.println("资源分配结束---------");
	}

	
	/** 
	 * 设置共享公海池
	 * @create  2015年11月17日 下午6:12:20 wuwei
	 * @history  
	 */
//	@Ignore
//	@Test
	public void setShare() {
		System.out.println("设置共享公海池开始---------");
        ResourceGroupBean  rgbBean = null;
        String groupId = "5bb24609b80d4c1abc28949641ad1ae6";
        rgbBean = comResourceGroupService.getByPrimaryKey(groupId);
        rgbBean.setResGroupId(groupId);
        rgbBean.setIsSharePool(new Short("1"));
        rgbBean.setModifierAcc(ShiroUtil.getUser().getAccount());
        rgbBean.setModifydate(new Date());
		comResourceGroupService.modify(rgbBean);
		System.out.println("设置共享公海池rgbBean=" + JsonUtil.getJsonString(rgbBean));
		System.out.println("设置共享公海池结束---------");
	}
	@Test
	public void testUUID() {
		System.out.println(UUID.randomUUID());
	}
}
