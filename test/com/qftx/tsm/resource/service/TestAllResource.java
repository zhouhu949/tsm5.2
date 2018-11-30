package com.qftx.tsm.resource.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dao.ComResourceMapper;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.cust.service.ComResourceGroupService;
import com.qftx.tsm.cust.service.ComResourceService;

/**
 * 查询所有资源
 * 
 * @author: wuwei
 * @since: 2015年12月1日 下午5:00:36
 * @history:
 */
public class TestAllResource {

	@Autowired
	private ComResourceGroupService comResourceGroupService;
	@Autowired
	private ComResourceService comResourceService;
	@Autowired
	private ComResourceMapper comResourceMapper;

	/**
	 * 查询全部资源
	 * 
	 * @create 2015年12月1日 下午5:01:32 wuwei
	 * @history
	 */
	@Test
	public void queryAllRes(){
		ShiroUser user = ShiroUtil.getUser();
		ResCustDto entity = new ResCustDto();
		entity.setOrgId(user.getOrgId());
		entity.setResCustId("");
		System.out.println("查询全部资源开始----------");
//		List<ResCustInfoBean> resList =comResourceService.queryResListPage(entity);
//		System.out.println("查询全部资源列表："+JsonUtil.getJsonString(resList));
		System.out.println("查询全部资源结束----------");
	}
}
