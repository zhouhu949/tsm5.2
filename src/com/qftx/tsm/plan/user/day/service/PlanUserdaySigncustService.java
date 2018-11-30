package com.qftx.tsm.plan.user.day.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.plan.user.day.bean.DataLableDTO;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayBean;
import com.qftx.tsm.plan.user.day.bean.PlanUserdaySigncustBean;
import com.qftx.tsm.plan.user.day.dao.PlanUserdaySigncustMapper;
import com.qftx.tsm.plan.user.day.dto.ContactResult;

@Service
public class PlanUserdaySigncustService extends PlanUserDayBaseService{
	@Autowired
	private PlanUserdaySigncustMapper planUserdaySigncustMapper;
	@Autowired
	private PlanUserDayService planUserDayService;
	@Autowired
	private ResCustInfoService resCustService;
	@Autowired
	private CachedService cachedService;
	private Logger logger=Logger.getLogger(PlanUserDayResourceService.class);
	
	/* 根据orgId 和sudId 查询资源 分页查询*/
	public List<PlanUserdaySigncustBean> queryPageBySudId(PlanUserdaySigncustBean bean){
		List<PlanUserdaySigncustBean> list = planUserdaySigncustMapper.findListPage(bean);
		processList(list, bean);
		
		process(bean.getOrgId(), list);
		processSign(list,bean.getUserAcc());
		return list;
	}
	
	public void processList(List<PlanUserdaySigncustBean> list,PlanUserdaySigncustBean bean){
		if(list!=null && list.size()>0){
			List<String> custIds = new ArrayList<>();
			for (PlanUserdaySigncustBean planUserdaySigncustBean : list) {
				custIds.add(planUserdaySigncustBean.getCustId());
			}
			bean.setCustIds(custIds.toArray(new String[]{}));
			
			
			List<PlanUserdaySigncustBean> list1 = planUserdaySigncustMapper.processList(bean);
			Map<String, PlanUserdaySigncustBean> map = new HashMap<>();
			for (PlanUserdaySigncustBean planUserdaySigncustBean : list1) {
				map.put(planUserdaySigncustBean.getCustId(), planUserdaySigncustBean);
			}
			
			
			for (PlanUserdaySigncustBean planUserdaySigncustBean : list) {
				PlanUserdaySigncustBean cust = map.get(planUserdaySigncustBean.getCustId());
				
				if(cust!=null){
					//,t1.TYPE as cust_type,t1.STATUS as cust_status,t1.RES_CUST_ID as cust_custId,t1.OWNER_ACC as cust_ownerAcc,t1.last_cust_follow_id as cust_last_cust_follow_id
					planUserdaySigncustBean.setCust_type(cust.getCust_type());
					planUserdaySigncustBean.setCust_status(cust.getCust_status());
					
					planUserdaySigncustBean.setCust_custId(cust.getCust_custId());
					planUserdaySigncustBean.setCust_ownerAcc(cust.getCust_ownerAcc());
					planUserdaySigncustBean.setCust_last_cust_follow_id(cust.getCust_last_cust_follow_id());
					
				} 
			}
		}
	}
	
	/* 处理状态变化的资源计划*/
	public List<PlanUserdaySigncustBean> processChange(String orgId,List<PlanUserdaySigncustBean> list){
		if(list!=null && list.size()>0){
			List<String> resIds = new ArrayList<String>();
			for (PlanUserdaySigncustBean planUserdaySigncustBean : list) {
				resIds.add(planUserdaySigncustBean.getCustId());
			}
			PlanUserdaySigncustBean bean =new PlanUserdaySigncustBean();
			bean.setOrgId(orgId);
			bean.setCustIds(resIds.toArray(new String[]{}));
			
			resIds = planUserdaySigncustMapper.findResId(bean);
			
			for (PlanUserdaySigncustBean planUserdaySigncustBean : list) {
				if(!resIds.contains(planUserdaySigncustBean.getCustId())){
					planUserdaySigncustBean.setTypeChange(true);
				}
			}
		}
		return list;
	}
	
	public List<PlanUserdaySigncustBean> findFromRes(PlanUserdaySigncustBean entity){
		List<PlanUserdaySigncustBean> list = planUserdaySigncustMapper.findFromRes(entity);
		process(entity.getOrgId(), list);
		return list;
	}
	
	public List<PlanUserdaySigncustBean> findFromResListPage(PlanUserdaySigncustBean entity){
		List<PlanUserdaySigncustBean> list = planUserdaySigncustMapper.findFromResListPage(entity);
		process(entity.getOrgId(), list);
		return list;
	}
	
	public void process(String orgId,List<PlanUserdaySigncustBean> list){
		//Map<String, String> map1 = cachedService.getOrgCustTypes(orgId);
		Map<String, String> groupMap = cachedService.getOrgResGroupNames(orgId);
		for (PlanUserdaySigncustBean signcustBean : list) {
			if(groupMap!=null && !groupMap.isEmpty() && StringUtils.isBlank(signcustBean.getGroupName())) signcustBean.setGroupName(groupMap.get(signcustBean.getGroupId()));
			//if(map1!=null && !map1.isEmpty()&& StringUtils.isBlank(signcustBean.getCustTypeName())) signcustBean.setCustTypeName(map1.get(signcustBean.getCustTypeId()));
		}
	}
	
	/* 获取数据标签*/
	public DataLableDTO queryDataLable(String orgId,String[] userIds,Date date){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("userIds", userIds);
		params.put("fromDate", DateUtil.dateBegin(date));
		params.put("toDate", DateUtil.dateEnd(date));
		return  planUserdaySigncustMapper.queryDataLable(params);
	}
	
	/* 管理者获取当天联系结果*/
	public ContactResult queryContactResult(String orgId,String[] userIds,Date date){
		DataLableDTO data = queryDataLable(orgId, userIds, date);
		ContactResult contactResult = new ContactResult();
		if(data!=null){
			contactResult.setTotalCount(data.getPlanCount());
			contactResult.setContactCount(data.getAlreadyContact());
			contactResult.setNoContactCount(data.getNoContact());
		}
		return contactResult;
	}
	
	/*更新资源联系结果 */
	public void updateContactResult(String orgId,String sudId,String custId,String contactResult){
		PlanUserdaySigncustBean entity= new PlanUserdaySigncustBean();
		entity.setOrgId(orgId);
		entity.setSudId(sudId);
		entity.setCustId(custId);
		entity.setStatus("1");
		entity.setContactResult(contactResult);
		planUserdaySigncustMapper.updateTrends(entity);
	}
	
	public String insertSigncusts(ShiroUser user,String[] custIds,Date planDate){
		Date time = new Date();
		PlanUserDayBean plan = planUserDayService.insertIfNotExist(user, planDate);
		List<String> existsIds = planUserDayService.findCustIdByPlanId(user.getOrgId(), plan.getId(),"sign");
		
		
		PlanUserdaySigncustBean query = new PlanUserdaySigncustBean();
		query.setUserAcc(user.getAccount());
		query.setOrgId(user.getOrgId());
		query.setCustIds(custIds);
		
		if(existsIds!=null && existsIds.size()>0)
			query.setRejectCustIds(existsIds.toArray(new String[existsIds.size()]));
		
		List<PlanUserdaySigncustBean> custs = findFromRes(query);
		for (PlanUserdaySigncustBean bean : custs) {
			bean.setId(GuidUtil.getUUID());
			bean.setOrgId(user.getOrgId());
			bean.setSudId(plan.getId());
			bean.setStatus("0");
			bean.setState(0);
			bean.setInputtime(time);
			bean.setUpdatetime(time);
			bean.setContactResult("未联系");
			bean.setIsDel(0);
			
			if(user.getIsState()==0){
				bean.setCustUser(bean.getCustName());
			}
		}
		if(custs!=null && custs.size()>0)
		planUserdaySigncustMapper.insertBatch(custs);
		return plan.getId();
	}
}
