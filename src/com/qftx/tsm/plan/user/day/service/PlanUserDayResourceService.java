package com.qftx.tsm.plan.user.day.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResCustinfoLogBean;
import com.qftx.tsm.cust.service.ResCustInfoLogService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.plan.user.day.bean.DataLableDTO;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayBean;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayResourceBean;
import com.qftx.tsm.plan.user.day.dao.PlanUserDayMapper;
import com.qftx.tsm.plan.user.day.dao.PlanUserDayResourceMapper;
import com.qftx.tsm.plan.user.day.dto.ContactResult;
@Service
public class PlanUserDayResourceService extends PlanUserDayBaseService{
	@Autowired
	private PlanUserDayResourceMapper planUserDayResourceMapper;
	@Autowired
	private PlanUserDayService planUserDayService;
	@Autowired
	private ResCustInfoService resCustService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private ResCustInfoLogService resCustInfoLogService;
	@Autowired
	private PlanUserDayMapper planUserDayMapper;
	
	private Logger logger=Logger.getLogger(PlanUserDayResourceService.class);
	
	/* 根据orgId 和sudId 查询资源 分页查询*/
	public List<PlanUserDayResourceBean> queryPageBySudId(PlanUserDayResourceBean bean){
		List<PlanUserDayResourceBean> list = planUserDayResourceMapper.findListPage(bean);
		
		processList(list, bean);
		processRes(list,bean.getUserAcc());
		return list;
	}
	
	public void processList(List<PlanUserDayResourceBean> list,PlanUserDayResourceBean bean){
		if(list!=null && list.size()>0){
			List<String> custIds = new ArrayList<>();
			for (PlanUserDayResourceBean planUserDayResourceBean : list) {
				custIds.add(planUserDayResourceBean.getCustId());
			}
			bean.setCustIds(custIds.toArray(new String[]{}));
			
			
			List<PlanUserDayResourceBean> list1 = planUserDayResourceMapper.processList(bean);
			Map<String, PlanUserDayResourceBean> map = new HashMap<>();
			for (PlanUserDayResourceBean planUserDayResourceBean : list1) {
				map.put(planUserDayResourceBean.getCustId(), planUserDayResourceBean);
			}
			
			
			for (PlanUserDayResourceBean planUserDayResourceBean : list) {
				PlanUserDayResourceBean cust = map.get(planUserDayResourceBean.getCustId());
				
				if(cust!=null){
					//,t1.IS_PRECEDENCE as cust_level ,t1.TYPE as cust_type,t1.STATUS as cust_status,t1.RES_CUST_ID as cust_custId,t1.OWNER_ACC as cust_ownerAcc
					
					planUserDayResourceBean.setCustLevel(cust.getCustLevel());
					planUserDayResourceBean.setCust_type(cust.getCust_type());
					planUserDayResourceBean.setCust_status(cust.getCust_status());
					
					planUserDayResourceBean.setCust_custId(cust.getCust_custId());
					planUserDayResourceBean.setCust_ownerAcc(cust.getCust_ownerAcc());
					
				} 
			}
		}
	}
	
	/* 更新下次跟进时间*/
	public void updatePlanDate(PlanUserDayResourceBean bean){
		 planUserDayResourceMapper.updatePlanDate(bean);;
	}
	
	/* 更新下次跟进时间*/
	public void updateMoveDate(String orgId,String sudId,String table,String[] custIds){
		Map<String, Object> params= new HashMap<>();
		params.put("orgId", orgId);
		params.put("sudId", sudId);
		params.put("table", table);
		params.put("custIds", custIds);
		
		planUserDayResourceMapper.updateMoveDate(params);;
	}
	 
	/* 获取数据标签*/
	public DataLableDTO queryDataLable(String orgId,String sudId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("sudId", sudId);
		return  planUserDayResourceMapper.queryDataLable(params);
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
			return  planUserDayResourceMapper.queryDataLable(params);
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
		return  contactResult;
	}
	
	/*放入公海*/
	public void giveUpResource(ShiroUser user,String[] custIds){
		//将资源标记为删除
		//用户资源池中剔除 ,资源加入公海池
		for(int i=0;i<custIds.length;i++){
			custIds[i] =custIds[i]+"_1";
		}
		resCustService.modifyBatchCust("", "", Arrays.asList(custIds),null,user,AppConstant.OPREATE_TYPE12);
	}
	
	/*加入资源 state为0||临时计划state为1*/
	public String insertResources(ShiroUser user,String[] custIds,Date planDate){
		Date time = new Date();
		PlanUserDayBean plan = planUserDayService.insertIfNotExist(user, planDate);
		List<String> existsIds = planUserDayService.findCustIdByPlanId(user.getOrgId(), plan.getId(),"res");
		
		PlanUserDayResourceBean query = new PlanUserDayResourceBean();
		query.setUserAcc(user.getAccount());
		query.setOrgId(user.getOrgId());
		query.setCustIds(custIds);
		
		if(existsIds!=null && existsIds.size()>0)
		query.setRejectCustIds(existsIds.toArray(new String[existsIds.size()]));
		
		List<PlanUserDayResourceBean> custs = findFromRes(query);
		for (PlanUserDayResourceBean bean : custs) {
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
		planUserDayResourceMapper.insertBatch(custs);
		return plan.getId();
	}
	
	/*更新资源优先级*/
	public void updateCustLevel(String orgId,String userAcc,String[] custIds,String level,ShiroUser user){
		resCustService.setPrecedenceBatch(userAcc, Integer.parseInt(level), Arrays.asList(custIds),user);
	}
	
	/*更新资源联系结果 */
	public void updateContactResult(String orgId,String sudId,String custId,String contactResult){
		PlanUserDayResourceBean entity= new PlanUserDayResourceBean();
		entity.setOrgId(orgId);
		entity.setSudId(sudId);
		entity.setCustId(custId);
		entity.setStatus("1");
		entity.setContactResult(contactResult);
		planUserDayResourceMapper.updateTrends(entity);
	}
	
	public List<PlanUserDayResourceBean> findFromRes(PlanUserDayResourceBean entity){
		List<PlanUserDayResourceBean> list = planUserDayResourceMapper.findFromRes(entity);
		process(entity.getOrgId(), list);
		return list;
	}
	
	public List<PlanUserDayResourceBean> findFromResListPage(PlanUserDayResourceBean entity){
		List<PlanUserDayResourceBean> list = planUserDayResourceMapper.findFromResListPage(entity);
		process(entity.getOrgId(), list);
		return list;
	}
	
	public void process(String orgId,List<PlanUserDayResourceBean> list){
		Map<String, String> map = cachedService.getOrgResGroupNames(orgId);
		for (PlanUserDayResourceBean resourceBean : list) {
			if(map!=null && !map.isEmpty()) resourceBean.setGroupName(map.get(resourceBean.getGroupId()));
			if(resourceBean.getLastLogId()!=null){
				ResCustinfoLogBean lastLog = resCustInfoLogService.getByPrimaryKeyAndOrgId(orgId, resourceBean.getLastLogId());
				if(lastLog!=null &&lastLog.getContext()!=null) resourceBean.setCustContext(lastLog.getContext());
			}
		}
	}
	
	public static void main(String[] args) {
	    String[] stringArray = new String[]{};
	    List<String> stringB = stringArray==null?null:Arrays.asList(stringArray);
	    System.out.println(stringB);
	  }
}
