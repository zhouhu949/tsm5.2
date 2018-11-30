package com.qftx.tsm.plan.user.month.service;

import com.qftx.base.util.GuidUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.plan.ResultDTO;
import com.qftx.tsm.plan.user.month.bean.PlanHistoryBean;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthCustBean;
import com.qftx.tsm.plan.user.month.dao.PlanUsermonthCustMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class PlanUsermonthCustService {
	@Autowired
	private PlanUsermonthCustMapper mapper;
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired 
	private PlanUserMonthService planService;
	@Autowired
	private CachedService cachedService;
	
	Logger logger =Logger.getLogger(PlanUsermonthCustService.class);
	
	public List<PlanUsermonthCustBean> queryByPlanUserMonthId(String orgId,String planId){
		PlanUsermonthCustBean entity= new PlanUsermonthCustBean();
		entity.setOrgId(orgId);
		entity.setPlanId(planId);
		entity.setIsDel(0);
		return mapper.findByCondtion(entity);
	}
	
	public List<PlanHistoryBean> findHistoryListPage(PlanHistoryBean item){
		item.setOrderKey("-plan_year ,-plan_month");
		return mapper.findHistoryListPage(item);
	}
	
	public PlanUsermonthCustBean getById(String orgId,String id){
		PlanUsermonthCustBean entity = new PlanUsermonthCustBean();
		entity.setOrgId(orgId);
		entity.setId(id);
		entity.setIsDel(0);
		return mapper.getByCondtion(entity);
	}
	
	public PlanUsermonthCustBean getByPlanIdAndCustId(String orgId,String planId,String custId){
		PlanUsermonthCustBean entity = new PlanUsermonthCustBean();
		entity.setOrgId(orgId);
		entity.setPlanId(planId);
		entity.setCustId(custId);
		entity.setIsDel(0);
		return mapper.getByCondtion(entity);
	}
	/*更新重点客户实际执行值*/
	public void updateFactNum(String orgId,int factSingcustNum,double factMoney,PlanUsermonthCustBean cust){
		//意向客户，签约后变完成
		//签约客户    签约金额
		PlanUsermonthCustBean entity = new PlanUsermonthCustBean();
		entity.setOrgId(orgId);
		entity.setId(cust.getId());
		entity.setIsDel(0);
		entity.setFactSingcustNum(factSingcustNum);
		entity.setFactMoney(factMoney);
	 	
		if(factSingcustNum>0 || factMoney>0){
			if(cust.getStatus()==0){
		 		if(cust.getSingcustNum()==1 && factSingcustNum==1){
		 			entity.setStatus(1);
		 		}else if((factMoney+cust.getFactMoney())>=cust.getPlanMoney()){
		 			entity.setStatus(1);
		 		}
		 	}
		}else{
			if(cust.getStatus()==1){
				if(cust.getSingcustNum()==1 && factSingcustNum==-1){
		 			entity.setStatus(0);
		 		}else if((factMoney+cust.getFactMoney())<cust.getPlanMoney()){
		 			entity.setStatus(0);
		 		}
			}
		}
	 	mapper.updateFactNum(entity);
	}
	
	
	/*查询个人月计划重点客户签约客户数和签约金额*/
	public PlanUsermonthCustBean queryCustSum(String orgId,String planId){
		PlanUsermonthCustBean bean = new PlanUsermonthCustBean();
		bean.setOrgId(orgId);
		bean.setPlanId(planId);
		PlanUsermonthCustBean sum = mapper.queryCustSum(bean);
		if(sum!=null){
			if(sum.getPlanMoney()!=null) sum.setPlanMoney(new BigDecimal(sum.getPlanMoney()).setScale(2, RoundingMode.HALF_UP).doubleValue());
			if(sum.getFactMoney()!=null) sum.setFactMoney(new BigDecimal(sum.getFactMoney()).setScale(2, RoundingMode.HALF_UP).doubleValue());
		}
		return sum;
	}
	
	//删除客户   is_del 更新为1
	public boolean removeCust(String orgId,String id,String planId){
		PlanUsermonthCustBean cust = getById(orgId, id);
		if(cust!=null && cust.getSingcustNum()!=null&&cust.getPlanMoney()!=null){
			//planService.addSignNumFromCust(planId, orgId, -cust.getSingcustNum(),-cust.getPlanMoney());
			PlanUsermonthCustBean bean = new PlanUsermonthCustBean();
			bean.setId(id);
			bean.setOrgId(orgId);
			bean.setIsDel(1);
			mapper.updateTrends(bean);
			return true;
		}else{
			logger.error("删除重点客户失败！！");
			return false;
		}
	}
	
	public  ResultDTO saveCust(PlanUsermonthBean plan,List<PlanUsermonthCustBean> custs){
		List<PlanUsermonthCustBean> dbCusts = queryByPlanUserMonthId(plan.getOrgId(), plan.getId());
		
		List<PlanUsermonthCustBean> removes = new ArrayList<PlanUsermonthCustBean>();
		List<PlanUsermonthCustBean> news = new ArrayList<PlanUsermonthCustBean>();
		List<PlanUsermonthCustBean> edits = new ArrayList<PlanUsermonthCustBean>();
		
		Map<String, PlanUsermonthCustBean> map = list2Map(custs);
		Map<String, PlanUsermonthCustBean> dbMap = list2Map(dbCusts);
		
		Set<String> keys = map.keySet();
		Set<String> dbKeys = dbMap.keySet();
		
		for (String dbKey : dbKeys) {
			if(map.containsKey(dbKey)){
				PlanUsermonthCustBean cust = map.get(dbKey);
				PlanUsermonthCustBean dbCust = dbMap.get(dbKey);
				
				if(dbCust!=null && cust!=null &&!dbCust.equals(cust)){
					//编辑
					cust.setId(dbCust.getId());
					cust.setOrgId(plan.getOrgId());
					cust.setPlanId(plan.getId());
					edits.add(cust);
				}
			}else{
				PlanUsermonthCustBean dbCust = dbMap.get(dbKey);
				if(dbCust!=null && dbCust.getCustId()!=null && dbCust.getId()!=null) removes.add(dbCust);
				//删除
			}
		}
		
		for (String key : keys) {
			if(!dbMap.containsKey(key)){
				//新增
				PlanUsermonthCustBean cust = map.get(key);
				
				cust.setOrgId(plan.getOrgId());
				cust.setPlanId(plan.getId());
				
				news.add(cust);
			}
		}
		
		if(news.size()>0) insertList(news);
		if(removes.size()>0) removeList(removes);
		if(edits.size()>0) updateList(edits);
		
		if(news.size()>0||removes.size()>0||edits.size()>0){
			return ResultDTO.Success();
		}else{
			return ResultDTO.erro("重点客户无变化！");
		}
	}
	
	public Map<String, PlanUsermonthCustBean> list2Map(List<PlanUsermonthCustBean> custs){
		HashMap<String, PlanUsermonthCustBean> map = new HashMap<String, PlanUsermonthCustBean>();
		
		for (PlanUsermonthCustBean cust : custs) {
			if(!map.containsKey(cust.getCustId())){
				map.put(cust.getCustId(), cust);
			}else{
				logger.error("custId 重复！");
			}
		}
		return map;
	}
	
	public void insertList(List<PlanUsermonthCustBean> custs){
		for (PlanUsermonthCustBean bean : custs) {
			insert(bean);
		}
	}
	
	public void removeList(List<PlanUsermonthCustBean> custs){
		for (PlanUsermonthCustBean bean : custs) {
			removeCust(bean.getOrgId(), bean.getId(), bean.getPlanId());
		}
	}
	
	public void updateList(List<PlanUsermonthCustBean> custs){
		for (PlanUsermonthCustBean bean : custs) {
			update(bean);
		}
	}
	
	public List<PlanUsermonthCustBean> findFromRes(String orgId,String [] custIds){
		PlanUsermonthCustBean entity = new PlanUsermonthCustBean();
		entity.setOrgId(orgId);
		entity.setCustIds(custIds);
		List<PlanUsermonthCustBean> list = mapper.findFromRes(entity);
		Map<String, String> map = cachedService.getOrgSaleProcess(orgId);
		if(map!=null && !map.isEmpty()){
			for (PlanUsermonthCustBean cust : list){
				cust.setCustStatus(map.get(cust.getCustStatus()));
			}
		}
		return list;
	}
	
	public void update (PlanUsermonthCustBean bean){
		bean.setUpdatetime(new Date());
		mapper.updateTrends(bean);
	}
	
	//插入客户
	public PlanUsermonthCustBean insert(PlanUsermonthCustBean bean){
		bean.setId(GuidUtil.getGuid());
		Date date = new Date();
		bean.setInputtime(date);
		bean.setUpdatetime(date);
		bean.setStatus(0);
		bean.setFactSingcustNum(0);
		bean.setFactMoney(0d);
		bean.setIsDel(0);
		mapper.insert(bean);
		return bean;
	}
}
