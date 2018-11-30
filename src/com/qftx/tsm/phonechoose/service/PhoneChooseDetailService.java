
package com.qftx.tsm.phonechoose.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dao.ResourceGroupMapper;
import com.qftx.tsm.phonechoose.bean.PhoneChooseDetailBean;
import com.qftx.tsm.phonechoose.bean.PhoneChooseTaskBean;
import com.qftx.tsm.phonechoose.dao.PhoneChooseDetailBeanMapper;

@Service
public class PhoneChooseDetailService {
	private static final Logger logger = Logger.getLogger(PhoneChooseDetailService.class);

	@Autowired
	PhoneChooseDetailBeanMapper mapper;
	@Autowired
	private CachedService cachedService;
	@Autowired
	ResourceGroupMapper resourceGroupMapper;

	/**
	 * 创建号码筛查数据
	 * 
	 * @param taskId	号码筛查任务ID
	 * @param user
	 * @param custInfo
	 * @return
	 */
	public PhoneChooseDetailBean insert(String taskId, ShiroUser user, ResCustInfoBean custInfo) {
		PhoneChooseDetailBean bean = new PhoneChooseDetailBean();
		bean.setId(GuidUtil.getGuid());
		bean.setTaskId(taskId);
		bean.setInputAcc(user.getAccount());
		bean.setInputDate(new Date());
		bean.setMobilephone(custInfo.getMobilephone());
		bean.setRciId(custInfo.getResCustId());
		bean.setOrgId(user.getOrgId());
		// bean.setStatus(PhoneChooseDetailBean.STATUS_DEAD);
		mapper.insert(bean);
		return bean;
	}
	
	/**
	 * 更新号码状态信息
	 * 
	 * @param account
	 * @param taskId
	 * @param processedCount
	 * @param status
	 * @return
	 */
	public String updateDetail(String account, String detailId, Byte status) {
		PhoneChooseDetailBean bean = new PhoneChooseDetailBean();
		bean.setId(detailId);
		bean.setStatus(status);
		bean.setUpdateAcc(account);
		bean.setUpdateDate(new Date());
		mapper.updateByPrimaryKeySelective(bean);
		return bean.getId();
	}
	
	/**
	 * 号码筛查 - 判断客户是否已经筛查
	 * 
	 * @param orgId
	 * @param rciId
	 * @return
	 */
	public Integer findCountByResCustId(String orgId, String rciId) {
		return mapper.findCountByResCustId(orgId, rciId);
	}

}
