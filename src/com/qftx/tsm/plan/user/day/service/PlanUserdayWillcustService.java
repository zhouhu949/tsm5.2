package com.qftx.tsm.plan.user.day.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.plan.user.day.bean.DataLableDTO;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayBean;
import com.qftx.tsm.plan.user.day.bean.PlanUserdayWillcustBean;
import com.qftx.tsm.plan.user.day.dao.PlanUserDayMapper;
import com.qftx.tsm.plan.user.day.dao.PlanUserdayWillcustMapper;
import com.qftx.tsm.plan.user.day.dto.ContactResult;
import com.qftx.tsm.plan.user.day.dto.WillCustIndex;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlanUserdayWillcustService extends PlanUserDayBaseService{
	@Autowired
	private PlanUserdayWillcustMapper planUserdayWillcustMapper;
	@Autowired
	private PlanUserDayService planUserDayService;
	@Autowired
	private ResCustInfoService resCustService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private PlanUserDayMapper planUserDayMapper;
	
	private Logger logger=Logger.getLogger(PlanUserDayResourceService.class);
	
	/* 根据orgId 和sudId 查询资源 分页查询*/
	public List<PlanUserdayWillcustBean> queryPageBySudId(PlanUserdayWillcustBean bean){
		List<PlanUserdayWillcustBean> list = planUserdayWillcustMapper.findListPage(bean);
		processList(list, bean);
		process(bean.getOrgId(),list);
		return processWill(list,bean.getUserAcc());
	}
	
	
	public void processList(List<PlanUserdayWillcustBean> list,PlanUserdayWillcustBean bean){
		if(list!=null && list.size()>0){
			List<String> custIds = new ArrayList<>();
			for (PlanUserdayWillcustBean planUserdayWillcustBean : list) {
				custIds.add(planUserdayWillcustBean.getCustId());
			}
			bean.setCustIds(custIds.toArray(new String[]{}));
			
			
			List<PlanUserdayWillcustBean> list1 = planUserdayWillcustMapper.processList(bean);
			Map<String, PlanUserdayWillcustBean> map = new HashMap<>();
			for (PlanUserdayWillcustBean planUserdayWillcustBean : list1) {
				map.put(planUserdayWillcustBean.getCustId(), planUserdayWillcustBean);
			}
			
			
			for (PlanUserdayWillcustBean planUserdayWillcustBean : list) {
				PlanUserdayWillcustBean cust = map.get(planUserdayWillcustBean.getCustId());
				
				if(cust!=null){
					//,t1.is_major,t1.TYPE as cust_type,t1.STATUS as cust_cust_status,t1.RES_CUST_ID as cust_custId,t1.OWNER_ACC as cust_ownerAcc,t1.last_cust_follow_id as cust_last_cust_follow_id
					planUserdayWillcustBean.setIsMajor(cust.getIsMajor());
					planUserdayWillcustBean.setCust_type(cust.getCust_type());
					planUserdayWillcustBean.setCust_status(cust.getCust_status());
					
					planUserdayWillcustBean.setCust_custId(cust.getCust_custId());
					planUserdayWillcustBean.setCust_ownerAcc(cust.getCust_ownerAcc());
					planUserdayWillcustBean.setCust_last_cust_follow_id(cust.getCust_last_cust_follow_id());
					
				} 
			}
		}
	}
	
	
	/* 获取数据标签*/
	public DataLableDTO queryDataLable(String orgId,String[] userIds,Date date,String authState){
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("orgId", orgId);
        params.put("userIds", userIds==null?null:Arrays.asList(userIds));
        params.put("fromDate", DateUtil.dateBegin(date));
        params.put("toDate", DateUtil.dateEnd(date));
        params.put("authState", authState);
        List<String> planIds = planUserDayMapper.findUsersPlanId(params);
		
		if(planIds!=null && planIds.size()>0){
			params = new HashMap<String, Object>();
			params.put("orgId", orgId);
			params.put("planIds", planIds);
			return  planUserdayWillcustMapper.queryDataLable(params);
		}else{
			return new DataLableDTO();
		}
	}
	
	/* 管理者获取当天联系结果*/
	public ContactResult queryContactResult(String orgId,String[] userIds,Date date,String authState){
		DataLableDTO data = queryDataLable(orgId, userIds, date,authState);
		ContactResult contactResult = new ContactResult();
		if(data!=null){
			contactResult.setTotalCount(data.getPlanCount());
			contactResult.setContactCount(data.getAlreadyContact());
			contactResult.setNoContactCount(data.getNoContact());
		}
		return contactResult;
	}
	
	/*更新资源联系结果 */
	public void updateContactResult(String orgId,String sudId,String custId,String contactResult,String newCustStatusId,String newCustStatus){
		PlanUserdayWillcustBean entity= new PlanUserdayWillcustBean();
		entity.setOrgId(orgId);
		entity.setSudId(sudId);
		entity.setCustId(custId);
		entity.setStatus("1");
		entity.setContactResult(contactResult);
		entity.setNewCustStatusId(newCustStatusId);
		entity.setNewCustStatus(newCustStatus);
		planUserdayWillcustMapper.updateTrends(entity);
	}
	
	public List<WillCustIndex> findIndexFromRes(PlanUserDayBean query){
		return planUserdayWillcustMapper.findIndexFromRes(query);
	}
	
	public List<WillCustIndex> findIndexFromPlan(PlanUserDayBean query){
		return planUserdayWillcustMapper.findIndexFromPlan(query);
	}
	
	public List<PlanUserdayWillcustBean> findFromRes(PlanUserdayWillcustBean entity){
		List<PlanUserdayWillcustBean> list =  planUserdayWillcustMapper.findFromRes(entity);
		process(entity.getOrgId(),list);
		return list;
	}
	
	public List<PlanUserdayWillcustBean> findFromResListPage(PlanUserdayWillcustBean entity){
		List<PlanUserdayWillcustBean> list =  planUserdayWillcustMapper.findFromResListPage(entity);
		process(entity.getOrgId(),list);
		return list;
	}
	 
	private void process(String orgId,List<PlanUserdayWillcustBean> list){
		Map<String, String> map = cachedService.getOrgSaleProcess(orgId);
		//Map<String, String> map1 = cachedService.getOrgCustTypes(orgId);
		Map<String, String> groupMap = cachedService.getOrgResGroupNames(orgId);
		
		for (PlanUserdayWillcustBean planUserdayWillcustBean : list) {
			if(groupMap!=null && !groupMap.isEmpty() && StringUtils.isBlank(planUserdayWillcustBean.getGroupName())) planUserdayWillcustBean.setGroupName(groupMap.get(planUserdayWillcustBean.getGroupId()));			
			if(map!=null && map.size()>0 && StringUtils.isBlank(planUserdayWillcustBean.getCustStatus())) planUserdayWillcustBean.setCustStatus(map.get(planUserdayWillcustBean.getCustStatusId()));
			//if(map1!=null && map1.size()>0 && StringUtils.isBlank(planUserdayWillcustBean.getCustTypeName())) planUserdayWillcustBean.setCustTypeName(map1.get(planUserdayWillcustBean.getCustTypeId()));;
		}
	}
	
	public String insertWillcusts(ShiroUser user,String[] custIds,Date planDate){
		Date time = new Date();
		PlanUserDayBean plan = planUserDayService.insertIfNotExist(user, planDate);
		List<String> existsIds = planUserDayService.findCustIdByPlanId(user.getOrgId(), plan.getId(),"will");
		
		
		PlanUserdayWillcustBean query = new PlanUserdayWillcustBean();
		query.setUserAcc(user.getAccount());
		query.setOrgId(user.getOrgId());
		query.setCustIds(custIds);
		if(existsIds!=null && existsIds.size()>0)
			query.setRejectCustIds(existsIds.toArray(new String[existsIds.size()]));
		
		List<PlanUserdayWillcustBean> custs = findFromRes(query);
		for (PlanUserdayWillcustBean bean : custs) {
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
		planUserdayWillcustMapper.insertBatch(custs);
		
		return plan.getId();
	}
}
