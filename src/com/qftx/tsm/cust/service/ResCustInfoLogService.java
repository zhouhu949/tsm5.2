package com.qftx.tsm.cust.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.SysRunException;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResCustinfoLogBean;
import com.qftx.tsm.cust.dao.ComResourceMapper;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.cust.dao.ResCustinfoLogMapper;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.main.bean.MainInfoBean;
import com.qftx.tsm.main.service.MainInfoService;
import com.qftx.tsm.plan.facade.PlanFactService;
import com.qftx.tsm.plan.facade.enu.PlanResCR;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResCustInfoLogService {
	@Autowired
	private ResCustinfoLogMapper resCustinfoLogMapper;
	@Autowired
	private MainInfoService mainInfoService;
	@Autowired
	private ComResourceMapper comResourceMapper;
	@Autowired
	private ResCustInfoMapper resCustInfoMapper;
	@Autowired
	private PlanFactService planFactService;
	@Autowired
	private ResCustEventService resCustEventService;

	/**
	 * 根据主键和ORG_ID获取备注
	 * 
	 * @param orgId
	 * @param trclId
	 * @return
	 * @create 2015年12月10日 下午6:51:03 lixing
	 * @history
	 */
	public ResCustinfoLogBean getByPrimaryKeyAndOrgId(@Param("orgId") String orgId, @Param("trclId") String trclId) {
		return resCustinfoLogMapper.getByPrimaryKeyAndOrgId(orgId, trclId);
	}

	public List<ResCustinfoLogBean> getByCondition(ResCustinfoLogBean entity) {
		return resCustinfoLogMapper.findByCondtion(entity);
	}

	/**
	 * 获取前两条记录(资源备注+ 客户跟进记录)
	 * 
	 * @param map
	 * @return
	 * @create 2015年12月25日 下午4:05:19 wuwei
	 * @history
	 */
	public Map<String, String> getTopConcat(Map<String, String> map) {
		return resCustinfoLogMapper.findTopConcat(map);
	}

	/**
	 * 
	 * @param map
	 * @return
	 * @create 2015年12月25日 下午4:05:19 wuwei
	 * @history
	 */
	public int getTotalLog(Map<String, String> map) {
		return resCustinfoLogMapper.findTotalLog(map);
	}

	public void create(ResCustinfoLogBean entity) {
		resCustinfoLogMapper.insert(entity);

	}

	public void createResourceBZ(String custId, String context, String nextConcatTime, ShiroUser user, String concatName) {
		String logId = SysBaseModelUtil.getModelId();
		Map<String, String> resMap = new HashMap<String, String>();
		Date currDate = new Date();
		ResCustInfoBean resCustInfoBean = new ResCustInfoBean();
		ResCustinfoLogBean logBean = new ResCustinfoLogBean();
		ResCustDto resCustDto = null;
		resMap.put("resId", custId);
		resMap.put("orgId", user.getOrgId());
		try {
			resCustDto = comResourceMapper.findResById(resMap);
			logBean.setTrclId(logId);
			logBean.setSesId(custId);
			logBean.setOrgId(user.getOrgId());
			logBean.setUserId(user.getId());
			logBean.setName(user.getIsState() == 1 ? resCustDto.getMainLinkman() : resCustDto.getName());
			logBean.setContext(context);
			logBean.setNextConcatTime(StringUtils.isNotEmpty(nextConcatTime) ? DateUtil.parse(nextConcatTime, DateUtil.hour24HMSPattern) : null);
			logBean.setInputName(user.getName());
			logBean.setInputtime(new Date());
			logBean.setIsDel(0);
			resCustinfoLogMapper.insert(logBean);

			MainInfoBean mb = new MainInfoBean();
			mb.setAccount(user.getAccount());
			mb.setOrgId(user.getOrgId());
			mb.setType("0");
			mainInfoService.updateMainInfo(mb, 4, 1);
			Map<String, String> map = new HashMap<String, String>();
			map.put("orgId", user.getOrgId());
			map.put("custId", custId);
			resCustInfoBean = comResourceMapper.getByPrimaryKey(map);
			if (resCustInfoBean != null) {
				resCustInfoBean.setIsConcat(1);
				resCustInfoBean.setUpdateAcc(user.getAccount());
				resCustInfoBean.setLastLogId(logId);
				resCustInfoBean.setUpdateDate(currDate);
				resCustInfoBean.setActionDate(currDate);
				resCustInfoBean.setNextFollowDate(StringUtils.isNotEmpty(nextConcatTime) ? DateUtil.parse(nextConcatTime, DateUtil.hour24HMSPattern) : null);
				resCustInfoBean.setOwnerActionDate(currDate);
				resCustInfoMapper.update(resCustInfoBean);
			}

			planFactService.updateContactResult(user.getOrgId(), user.getGroupId(), user.getId(), custId, "res", PlanResCR.NO_CHANGE.getResult());
			if (resCustDto != null && resCustDto.getResCustId() != null && !"".equals(resCustDto.getResCustId())) {
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put("inputDate", new Date());
				jsonMap.put("mainLinkman", concatName);
				jsonMap.put("userName", (user.getName() == null || "".equals(user.getName()) ? user.getAccount() : user.getName()));
				jsonMap.put("telphone", "");
				jsonMap.put("type", "1");
				jsonMap.put("saleProcessName", "");
				jsonMap.put("labels", "");
				jsonMap.put("remark", context);
				jsonMap.put("custName", resCustDto.getName());
				jsonMap.put("custFollowId", "");
				jsonMap.put("nextConcatTime", logBean.getNextConcatTime());
				if (user.getIsState() == 1) {
					jsonMap.put("concatName", concatName);
				}
				resCustEventService.create(user.getOrgId(), custId, "1", JsonUtil.getJsonString(jsonMap));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysRunException(e);
		}
	}

	public List<ResCustinfoLogBean> getCustinfoLogBeans(String orgId, String id) {
		return resCustinfoLogMapper.findRemarkList(orgId, id);
	}
}
