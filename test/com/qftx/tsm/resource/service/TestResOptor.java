package com.qftx.tsm.resource.service;

import java.util.Date;

import org.junit.Test;

import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.cust.bean.ResOptorBean;


 /** 
 * 测试资源操作类
 * @author: wuwei
 * @since: 2015年11月17日  下午4:21:41
 * @history:
 */
public class TestResOptor extends BaseUtest{

	
	/** 
	 * 
	 * @create  2015年11月17日 下午4:22:58 Administrator
	 * @history  
	 */
	@Test
	public void insert(){
		System.out.println("插入资源操作日志开始---------");
		ResOptorBean bean = new ResOptorBean();
		bean.setResCustId(GuidUtil.getId());
		bean.setResOptorId(GuidUtil.getId());
		bean.setOptoerAcc(ShiroUtil.getUser().getAccount());
		bean.setOrgId(ShiroUtil.getUser().getOrgId());
		bean.setOwnerAcc("happy001");
		bean.setResCustId("");
		bean.setType(new Short("3"));
		bean.setCreateTime(new Date());
		System.out.println("插入资源操作日志id="+bean.getResOptorId());
		System.out.println("插入资源操作日志结束---------");
	}
}
