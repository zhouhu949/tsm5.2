package com.qftx.tsm.resource.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.tsm.cust.bean.TaoTagBean;
import com.qftx.tsm.cust.dao.TaoTagMapper;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.cust.service.ComResourceGroupService;
import com.qftx.tsm.cust.service.ComResourceService;
import com.qftx.tsm.tao.service.TaoService;

/**
 * 淘客户
 * 
 * @author: wuwei
 * @since: 2015年12月7日 下午4:27:38
 * @history:
 */
public class TestTaoCustService extends BaseUtest {
	@Autowired
	private ComResourceGroupService comResourceGroupService;
	@Autowired
	private ComResourceService comResourceService;
	@Autowired
	private TaoService taoCustService;
	@Autowired
	private TaoTagMapper taoTagMapper;

	/**
	 * 淘客户
	 * 
	 * @create 2015年12月7日 下午6:33:34 wuwei
	 * @history
	 */
	@Test
	public void testTaoCust() {
		/**
		 * 1、 首次登陆，
		 */
		System.out.println("淘客户开始。。。。。。。。");
		ResCustDto resInfo = new ResCustDto();
		ShiroUtil.getUser().setAccount("hn006");
		resInfo.setOrgId(ShiroUtil.getUser().getOrgId());
		resInfo.setOwnerAcc("hn006");
		resInfo.setActionDate(new Date());
		String yx = "is_precedence desc";
		String resIdFromOtherRes = "";
		String isFromOtherPage = "";
		String updownOptType = "0";
		String lastDelay = "";
		// String groupId = "5bb24609b80d4c1abc28949641ad1ae6";
		String groupId = "all";
		String isFromOtherRes = "";
		String crruDelay = "0";
		String lastFilterCount = "0";
		String filterCount = "0";
		resInfo.setOrderType(yx);
		resInfo.setGroupId(groupId);
		resInfo.setOwnerAcc("hn006");
		resInfo.setOrgId(ShiroUtil.getUser().getOrgId());
		resInfo.setIsFromOtherRes(isFromOtherRes);
		/**
		 * 2、上次为延后的 lastDelay
		 */
//		ShiroUtil.getUser().setAccount("hn006");
//		resInfo.setOrgId(ShiroUtil.getUser().getOrgId());
//		resInfo.setOwnerAcc("hn006");
//		resInfo.setActionDate(new Date());
//		String yx = "owner_start_date desc";
//		String resIdFromOtherRes = "";
//		String isFromOtherPage = "0";
//		String updownOptType = "0";
//		String lastDelay = "lastDelay";
//		// String groupId = "5bb24609b80d4c1abc28949641ad1ae6";
//		String groupId = "all";
//		String isFromOtherRes = "0";
//		String crruDelay = "0";
//		String lastFilterCount = "0";
//		String filterCount = "0";
//		resInfo.setOrderType(yx);
//		resInfo.setGroupId(groupId);
//		resInfo.setOwnerAcc("hn006");
//		resInfo.setOrgId(ShiroUtil.getUser().getOrgId());
//		resInfo.setIsFromOtherRes(isFromOtherRes);
		/**
		 * 3、 其他资源列表进入，
		 */
//		ResCustDto resInfo = new ResCustDto();
//		ShiroUtil.getUser().setAccount("hn006");
//		resInfo.setOrgId(ShiroUtil.getUser().getOrgId());
//		resInfo.setOwnerAcc("hn006");
//		resInfo.setActionDate(new Date());
//		String yx = "is_precedence desc";
//		String resIdFromOtherRes = "831f0a19907f4eb1a8be5722aa535dcf";
//		String isFromOtherPage = "0";
//		String updownOptType = "2";
//		String lastDelay = "";
//		 String groupId = "5bb24609b80d4c1abc28949641ad1ae6";
////		String groupId = "all";
//		String isFromOtherRes = "1";
//		String crruDelay = "0";
//		String lastFilterCount = "0";
//		String filterCount = "20495";
//		resInfo.setOrderType(yx);
//		resInfo.setGroupId(groupId);
//		resInfo.setOwnerAcc("hn006");
//		resInfo.setOrgId(ShiroUtil.getUser().getOrgId());
//		resInfo.setIsFromOtherRes(isFromOtherRes);
		Map<String, Object> resultMap =null; //taoCustService.saveTaoCust(resIdFromOtherRes, isFromOtherRes, isFromOtherPage, filterCount, updownOptType,
				//groupId, lastDelay, crruDelay, lastFilterCount,"",1);
		System.out.println("返回内容：" + resultMap.toString());
		System.out.println("淘客户结束。。。。。。。。");
	}
	
	
	
	/** 
	 * 修改筛选分组
	 * @create  2015年12月8日 下午3:45:20 WUWEI
	 * @history  
	 */
//	@Test
	public void testUpdateFilterGroup(){
		System.out.println("修改筛选分组开始。。。。。。。。");
		ResCustDto resCustDto = new ResCustDto();
		String defaultOrder = ConfigInfoUtils.getStringValue("tao_order");
		long base_number = 10;
		String resourceGroupId ="today";
//		String resourceGroupId ="5bb24609b80d4c1abc28949641ad1ae6";
		String orderType = "owner_start_date desc";
		resCustDto.setResourceGroupId(resourceGroupId);
		resCustDto.setOrderType(orderType);
		resCustDto.setOwnerAcc(ShiroUtil.getUser().getAccount());
		resCustDto.setOrgId(ShiroUtil.getUser().getOrgId());
		resCustDto.setOwnerAcc("hn006");
//		taoCustService.updateTaoTag(resCustDto, defaultOrder, base_number);
		System.out.println("修改筛选分组结束。。。。。。。。");
	}
	/** 
	 * 定位标签
	 * @create  2015年12月7日 下午6:34:50 wuwei
	 * @history  
	 */
//	@Test
	public void testInsertTag(){
		 System.out.println("新增定位上次淘客户时的资源。。。。。。开始");
		 TaoTagBean tagBean = new TaoTagBean();
		 tagBean.setAccount("hn006");
		 tagBean.setCreateTime(new Date());
		 tagBean.setGroupId("today");
		 tagBean.setOrderType("owner_start_date desc");
		 tagBean.setResourceId("d15b32f5d55e4d7a92266ecd23ffd333");
		 taoTagMapper.insert(tagBean);
		 System.out.println("新增定位上次淘客户时的资源。。。。。。结束");
	}
	
	/** 
	 * 定位标签
	 * @create  2015年12月7日 下午6:34:50 wuwei
	 * @history  
	 */
//	@Test
	public void updateInsertTag(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", ShiroUtil.getUser().getAccount());
		map.put("orgId", ShiroUtil.getUser().getOrgId());
		 System.out.println("修改定位上次淘客户时的资源。。。。。。开始");
		  taoTagMapper.updateResIdByAcc(map);
		 System.out.println("修改定位上次淘客户时的资源。。。。。。结束");
	}
	
	/** 
	 * 删除定位标签
	 * @create  2015年12月7日 下午6:34:50 wuwei
	 * @history  
	 */
//	@Test
	public void delTag(){
		 System.out.println("删除定位上次淘客户时的资源。。。。。。开始");
		  taoTagMapper.delete("831f0a19907f4eb1a8be5722aa535dcf");
		 System.out.println("删除定位上次淘客户时的资源。。。。。。结束");
	}
}
