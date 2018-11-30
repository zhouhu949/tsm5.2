package com.qftx.tsm.resource.service;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.service.ComResourceGroupService;


 /** 
 * 资源组测试
 * @author: WUWEI
 * @since: 2015年11月16日  下午4:10:53
 * @history:
 */
public class TestResGroup extends BaseUtest{

	@Autowired
	private ComResourceGroupService comResourceGroupService;
	
	/** 
	 * 创建资源分组
	 * @create  2015年11月16日 下午4:12:44 wuwei
	 * @history  
	 */
	@Ignore
	@Test
	public void  create(){
		System.out.println("创建资源分组开始-------");
		ShiroUser user = ShiroUtil.getUser();
		ResourceGroupBean resourceGroupBean = new ResourceGroupBean();
		resourceGroupBean.setResGroupId(GuidUtil.getId());
		resourceGroupBean.setOrgId(user.getOrgId());
		resourceGroupBean.setIsDel(new Short("0"));
		resourceGroupBean.setGroupName("电商团队");
		resourceGroupBean.setSysType("5");
		comResourceGroupService.create(resourceGroupBean);
		System.out.println("创建资源分组id="+resourceGroupBean.getResGroupId()+"-------");
		System.out.println("创建资源分组结束-------");
	}
	
	
	/** 
	 * 修改资源组
	 * @create  2015年11月16日 下午4:20:05 wuwei
	 * @history  
	 */
	@Ignore
	@Test
	public void  modify(){
		System.out.println("修改资源分组开始-------");
		ShiroUser user = ShiroUtil.getUser();
		ResourceGroupBean resourceGroupBean = new ResourceGroupBean();
		resourceGroupBean.setResGroupId("3078921b00834516a652fe506a6b501a");
		resourceGroupBean.setOrgId(user.getOrgId());
		resourceGroupBean.setIsDel(new Short("0"));
		resourceGroupBean.setGroupName("测试2");
		resourceGroupBean.setSysType("5");
		comResourceGroupService.create(resourceGroupBean);
		System.out.println("修改资源分组id="+resourceGroupBean.getResGroupId()+"-------");
		System.out.println("修改资源分组结束-------");
	}
	
	/** 
	 * 查询资源组
	 * @create  2015年11月16日 下午4:20:05 wuwei
	 * @history  
	 */
	@Ignore
	@Test
	public void  getResGroup(){
		System.out.println("查询资源分组开始-------");
		ResourceGroupBean resourceGroupBean = new ResourceGroupBean();
		String id="5c5ce5930507465ba2e8fc0b6f081d9c";
		resourceGroupBean = comResourceGroupService.getByPrimaryKey(id);
		System.out.println("查询资源分组="+JsonUtil.getJsonString(resourceGroupBean)+"-------");
		System.out.println("查询资源分组结束-------");
	}
	
	
	/** 
	 * 查询资源分组列表
	 * @create  2015年11月16日 下午4:29:56 wuwei
	 * @history  
	 */
	@Ignore
	@Test
	public void  queryResGroup(){
		ShiroUser user = ShiroUtil.getUser();
		System.out.println("查询资源分组列表开始-------");
		List<ResourceGroupBean> list = comResourceGroupService.queryResGroup(user.getOrgId());
		System.out.println("查询资源分组列表="+JsonUtil.getJsonString(list)+"-------");
		System.out.println("查询资源分组列表结束-------");
	}	
	/** 
	 * 删除资源组
	 * @create  2015年11月16日 下午4:20:05 wuwei
	 * @history  
	 */
	@Ignore
	@Test
	public void  delResGroup(){
		System.out.println("删除资源分组开始-------");
		String id="5c5ce5930507465ba2e8fc0b6f081d9c";
//		comResourceGroupService.deleteResourceGroupByGroupId(id);;
		System.out.println("删除资源分组="+id+"-------");
		System.out.println("删除资源分组结束-------");
	}
	
	/** 
	 * 根据分组判断是否重复
	 * @create  2015年11月16日 下午4:20:05 wuwei
	 * @history  
	 */
	@Ignore
	@Test
	public void  vitifyByGName(){
		System.out.println("资源分组验证重复开始-------");
		String name= "电商团队";
		ResourceGroupBean rGroupBean = new ResourceGroupBean();
		rGroupBean.setOrgId(shiroUser.getOrgId());
		rGroupBean.setGroupName(name);
		List<ResourceGroupBean> list = comResourceGroupService.getGrpListByGrpName(rGroupBean);
		System.out.println("资源分组验证重复  list="+JsonUtil.getJsonString(list)+"-------");
		System.out.println("资源分组验证重复结束-------");
	}
}
