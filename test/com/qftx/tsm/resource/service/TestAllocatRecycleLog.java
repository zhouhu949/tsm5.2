package com.qftx.tsm.resource.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.cust.bean.AllocatRecycleLogBean;
import com.qftx.tsm.cust.dao.AllocatRecycleLogBeanMapper;
import com.qftx.tsm.cust.scontrol.CompUnAssignResAction;

/**
 * 资源分配回收日志
 * 
 * @author: wuwei
 * @since: 2015年11月17日 下午4:01:22
 * @history:
 */
public class TestAllocatRecycleLog extends BaseUtest {
	@Autowired
	private CompUnAssignResAction compUnAssignResAction;
	@Autowired
	private AllocatRecycleLogBeanMapper allocatRecycleLogBeanMapper;

	@Test
	public  void testQuery() {
		 compUnAssignResAction.getIsShare();
		 System.out.println("ok");
	}
	// @Test
	// public void insert() {
	// System.out.println("插入资源分配回收日志开始------");
	// AllocatRecycleLogBean bean = new AllocatRecycleLogBean();
	// bean.setResOptorId(GuidUtil.getId());
	// bean.setOptoerAcc(ShiroUtil.getUser().getAccount());
	// bean.setOrgId(ShiroUtil.getUser().getOrgId());
	// bean.setOptorQuantity(11);
	// bean.setOwnerAcc("happy001");
	// bean.setType("01");
	// allocatRecycleLogBeanMapper.insert(bean);
	// System.out.println("插入资源分配回收日志.id=" + bean.getResOptorId());
	// System.out.println("插入资源分配回收日志结束------");
	//
	// }
}
