package com.qftx.tsm.resource.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.cust.dao.ComResourceMapper;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.cust.service.ComResourceGroupService;
import com.qftx.tsm.cust.service.ComResourceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 已分配资源測試
 * 
 * @author: wuwei
 * @since: 2015年11月18日 下午3:31:10
 * @history:
 */
public class TestAssignResource extends BaseUtest {

	@Autowired
	private ComResourceGroupService comResourceGroupService;
	@Autowired
	private ComResourceService comResourceService;
	@Autowired
	private ComResourceMapper comResourceMapper;

	/**
	 * 查询已分配资源
	 * 
	 * @create 2015年11月18日 下午3:32:07 WUWEI
	 * @history
	 */
//	@Test
	public void queryAssginRes() {
		System.out.println("查询分配资源开始----------");
		ShiroUser user = ShiroUtil.getUser();
		String ownerAccs = "happy001;happy002";
		ResCustDto entity = new ResCustDto();
		entity.setOrgId(user.getOrgId());
//		entity.setName("dd");
//		entity.setKeyWord("ww");
//		entity.setGroupId("333");
//		entity.setMobilephone("");
//		entity.setOwnerAccs(Arrays.asList(ownerAccs.split(";")));
//		entity.setStartDate(DateUtil.formatDate(new Date()));
//		entity.setEndDate(DateUtil.formatDate(new Date()));
//		entity.setCompany("www");
//		List<ResCustInfoBean> list = comResourceService
//				.queryAssginResListPage(entity);
//		System.out.println("查询分配资源列表：" + JsonUtil.getJsonString(list));
		System.out.println("查询分配资源结束----------");
	}
	
	
	/** 
	 * 合并资源分组
	 * @create  2015年11月18日 下午4:55:57 WUWEI
	 * @history  
	 */
//	@Test
	public void saveMergeGroup(){
		System.out.println("合并资源分组开始----------");
		ShiroUser user = ShiroUtil.getUser();
		ResCustDto entity = new ResCustDto();
		entity.setOrgId(user.getOrgId());
		String ids = "5bb24609b80d4c1abc28949641ad1ae6,3078921b00834516a652fe506a6b501a";
		String newGroup="41995a4baf914e299ec758dc52954d2d";
		String result = comResourceService.saveMergeGroup(ids, newGroup, entity,user.getOrgId(),user.getAccount(),user.getName());
		System.out.println("合并资源分组返回内容：" + JsonUtil.getJsonString(result));
		System.out.println("合并资源分组结束----------");
	}
	
	
	/** 
	 * 资源维护-已分配资源-回收资源
	 * @create  2015年11月18日 下午5:57:19 wuwei
	 * @history  
	 */
//	@Test
	public void reclyeRes(){
		System.out.println("回收资源开始----------");
		ShiroUser user = ShiroUtil.getUser();
		ResCustDto entity = new ResCustDto();
		entity.setOrgId(user.getOrgId());
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("orgId", ShiroUtil.getUser().getOrgId());
		paramMap.put("ids", "e0fd4720442f4d208449816d913e1da9,86d9e462c0704c6b999ad9e2606eaea1");
		paramMap.put("inputAcc", ShiroUtil.getUser().getAccount());
//		String result = comResourceService.updateBatchRes(paramMap,user.getOrgId(),user.getAccount());
//		System.out.println("回收资源内容：" + JsonUtil.getJsonString(result));
		System.out.println("回收资源结束----------");
	}
	
//	@Test
	public void testParam(){
		System.out.println("测试获取不到参数开始----------");
		ResCustDto resCustDto = new ResCustDto();
		ShiroUser user = ShiroUtil.getUser();
		resCustDto.setResourceGroupId("all");
		resCustDto.setOrderType("");
		resCustDto.setOwnerAcc(ShiroUtil.getUser().getAccount());
		resCustDto.setOrgId(ShiroUtil.getUser().getOrgId());
		resCustDto.setIsConcat(new Integer(2));
		resCustDto.setOwnerAcc(user.getAccount());
		System.out.println("测试获取不到参数结束----------");
	}
	@Test
	public  void testIndexOff(){
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		for(String string :list){
			if("22".equals(string)){
				System.out.println(list.indexOf(string));
			}
		}
	}
}
