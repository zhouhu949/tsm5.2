package com.qftx.tsm.report.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.tsm.main.service.ContactDayDataService;
import com.qftx.tsm.plan.user.day.service.PlanUserDayResourceService;
import com.qftx.tsm.plan.user.day.service.PlanUserDayService;
import com.qftx.tsm.plan.user.day.service.PlanUserdayWillcustService;
import com.qftx.tsm.report.bean.TsmReportPlanBean;
import com.qftx.tsm.report.dao.TsmReportPlanMapper;

@Service
public class TsmReportPlanService {
	@Autowired TsmReportPlanMapper tsmReportPlanMapper;
	@Autowired ContactDayDataService contactDayDataService;
	@Autowired PlanUserDayService planUserDayService;
	@Autowired PlanUserDayResourceService planUserDayResourceService;
	@Autowired PlanUserdayWillcustService planUserdayWillcustService;
	@Autowired CachedService cachedService; 
	@Autowired TsmTeamGroupService	tsmTeamGroupService;
	
	Logger logger = Logger.getLogger(TsmReportPlanService.class.getName());
	
	public List<TsmReportPlanBean> findByUser(String orgId,String userId,Date fromDate,Date toDate){
		TsmReportPlanBean entity = new TsmReportPlanBean();
		entity.setOrgId(orgId);
		entity.setUserId(userId);
		entity.setFromDate(DateUtil.dateBegin(fromDate));
		entity.setToDate(DateUtil.dateEnd(toDate));
		entity.setOrderKey("t.report_date desc,t.ACCOUNT asc");
		
		List<TsmReportPlanBean> list = tsmReportPlanMapper.findByCondtion(entity);
		processUserName(orgId, list);
		return list;
	}
	
	public List<TsmReportPlanBean> findListByUser(String orgId,TsmReportPlanBean entity){
		entity.setOrgId(orgId);
		entity.setOrderKey("t.report_date desc,t.ACCOUNT asc");
		
		List<TsmReportPlanBean> list = tsmReportPlanMapper.findListPage(entity);
		processUserName(orgId, list);
		return list;
	}
	
	public List<TsmReportPlanBean> findSumByUser(String orgId,String[] userIds,Date fromDate,Date toDate){
		TsmReportPlanBean entity = new TsmReportPlanBean();
		entity.setOrgId(orgId);
		entity.setFromDate(DateUtil.dateBegin(fromDate));
		entity.setToDate(DateUtil.dateEnd(toDate));
		entity.setUserIds(userIds);
		
		entity.setGroupKey("t.USER_ID,t.ACCOUNT");
		entity.setOrderKey("t.ACCOUNT");
		
		List<TsmReportPlanBean> list = tsmReportPlanMapper.findSumByUser(entity);
		processUserName(orgId, list);
		return list;
	}
	
	public List<TsmReportPlanBean> findSumByGroup(String orgId,String[] groupIds,Date fromDate,Date toDate){
		TsmReportPlanBean entity = new TsmReportPlanBean();
		entity.setOrgId(orgId);
		entity.setFromDate(DateUtil.dateBegin(fromDate));
		entity.setToDate(DateUtil.dateEnd(toDate));
		entity.setGroupIds(groupIds);
		
		entity.setGroupKey("t2.GROUP_NAME");
		entity.setOrderKey("t2.GROUP_INDEX");
		List<TsmReportPlanBean> list = tsmReportPlanMapper.findSumByGroup(entity);
		processTopGroupValue(orgId, list);
		return list;
	}
	
	public void processUserName(String orgId,List<TsmReportPlanBean> list){
		Map<String, String> map = cachedService.getOrgUserNamesByID(orgId);
		
		if(list!=null && list.size()>0 && map!=null &&!map.isEmpty()){
			for (TsmReportPlanBean tsmReportPlanBean : list) {
				tsmReportPlanBean.setUserName(map.get(tsmReportPlanBean.getUserId()));
			}
		}
	}
	/*处理父子部门数据包含*/
	public void processTopGroupValue(String orgId,List<TsmReportPlanBean> list){
		List<String> pids = tsmTeamGroupService.findAllPid(orgId);
		Map<String, List<OrgGroup>> orgGroupPidMap = cachedService.getOrgGroupPidMap(orgId);
			
		if(pids!=null &&pids.size()>0&&orgGroupPidMap!=null &&!orgGroupPidMap.isEmpty()){
			Map<String, TsmReportPlanBean> map = list2Map(list);
			
			Set<String> keys = map.keySet();
			List<String> teamGroupIds = new ArrayList<String>();
			for (String groupId : keys) {
				if(!pids.contains(groupId)) teamGroupIds.add(groupId);
			}
			List<String> fathers = tsmTeamGroupService.findPidsByGroupIds(orgId, teamGroupIds);
			if(fathers!=null && fathers.size()>0){
				process(orgId,fathers, map, orgGroupPidMap);
			}
			
			list = new ArrayList<TsmReportPlanBean>();
			for(String gid :map.keySet()){
				list.add(map.get(gid));
			}
		}
	}
	
	public Map<String, TsmReportPlanBean> list2Map(List<TsmReportPlanBean> list){
		Map<String, TsmReportPlanBean> map = new LinkedHashMap<String,TsmReportPlanBean>();
		for (TsmReportPlanBean tsmReportPlanBean : list) {
			String groupId = tsmReportPlanBean.getGroupId();
			map.put(groupId, tsmReportPlanBean);
		}
		return map;
	}
	
	public void process(String orgId,List<String> groupIds,Map<String, TsmReportPlanBean> map,Map<String, List<OrgGroup>> orgGroupPidMap){
		for (String gid : groupIds) {
			List<OrgGroup> sonGroupIds = orgGroupPidMap.get(gid);
			TsmReportPlanBean fatherReport = map.get(gid);
			if(fatherReport!=null){
				for (OrgGroup sonGroup : sonGroupIds) {
					TsmReportPlanBean sonReport = map.get(sonGroup.getGroupId());
					if(sonReport!=null) plusData(fatherReport, sonReport);
				}
			}
		}
		List<String> fathers = tsmTeamGroupService.findPidsByGroupIds(orgId, groupIds);
		
		if(fathers!=null && fathers.size()>0){
			process(orgId, fathers, map, orgGroupPidMap);
		}
	}
	/*将    source 中数据加到 target中*/
	public void plusData(TsmReportPlanBean target,TsmReportPlanBean source){
		target.setResPlanNum(target.getResPlanNum()+(source.getResPlanNum()==null?0:source.getResPlanNum()));
		target.setResNoContact(target.getResNoContact()+(source.getResNoContact()==null?0:source.getResNoContact()));
		target.setResTotalNum(target.getResTotalNum()+(source.getResTotalNum()==null?0:source.getResTotalNum()));
		target.setResAddNum(target.getResAddNum()+(source.getResAddNum()==null?0:source.getResAddNum()));
		target.setResSignNum(target.getResSignNum()+(source.getResSignNum()==null?0:source.getResSignNum()));
		target.setResCustNum(target.getResCustNum()+(source.getResCustNum()==null?0:source.getResCustNum()));
		target.setResPoolNum(target.getResPoolNum()+(source.getResPoolNum()==null?0:source.getResPoolNum()));
		target.setResNoNum(target.getResNoNum()+(source.getResNoNum()==null?0:source.getResNoNum()));
		target.setWillPlanNum(target.getWillPlanNum()+(source.getWillPlanNum()==null?0:source.getWillPlanNum()));
		target.setWillNoContact(target.getWillNoContact()+(source.getWillNoContact()==null?0:source.getWillNoContact()));
		target.setWillTotalNum(target.getWillTotalNum()+(source.getWillTotalNum()==null?0:source.getWillTotalNum()));
		target.setWillSignNum(target.getWillSignNum()+(source.getWillSignNum()==null?0:source.getWillSignNum()));
		target.setWillCustNum(target.getWillCustNum()+(source.getWillCustNum()==null?0:source.getWillCustNum()));
		target.setWillPoolNum(target.getWillPoolNum()+(source.getWillPoolNum()==null?0:source.getWillPoolNum()));
		target.setWillNoNum(target.getWillNoNum()+(source.getWillNoNum()==null?0:source.getWillNoNum()));
		target.setTradCustNum(target.getTradCustNum()+(source.getTradCustNum()==null?0:source.getTradCustNum()));
		target.setCallOutTime(target.getCallOutTime()+(source.getCallOutTime()==null?0:source.getCallOutTime()));
		target.setSignMoney(BigDecimal.valueOf(target.getSignMoney()).add(BigDecimal.valueOf(source.getSignMoney()==null?0:source.getSignMoney())).doubleValue());
	}
	
	//type 0:资源   1：意向
	public void cancelSign(String orgId,String userId,Date date,int type,int signNum,Double signMoney){
		TsmReportPlanBean entity = new TsmReportPlanBean();
		entity.setOrgId(orgId);
		entity.setUserId(userId);
		entity.setFromDate(DateUtil.dateBegin(date));
		entity.setToDate(DateUtil.dateEnd(date));
		
		logger.debug(" orgId "+orgId+" userId "+userId+" date "+DateUtil.formatDate(date)+" type "+type+" signNum "+signNum+" signMoney "+signMoney);
		
		TsmReportPlanBean report = tsmReportPlanMapper.getByCondtion(entity );
		if(report !=null){
			TsmReportPlanBean updateBean = new TsmReportPlanBean();
			updateBean.setId(report.getId());
			updateBean.setUpdateDate(new Date());
			updateBean.setOrgId(orgId);
			updateBean.setSignMoney(signMoney);
			if(type==0){
				updateBean.setResSignNum(signNum);
				updateBean.setResCustNum(-signNum);
			}else{
				updateBean.setWillSignNum(signNum);
				updateBean.setWillNoNum(-signNum);
			}
			tsmReportPlanMapper.cancelSign(updateBean);
		}
	}
	
	//type 0:资源   1：意向
	public void plusMoney(String orgId,String account,Date date,Double saleMoney){
		//忽略当天的记录
		if(DateUtil.dateBegin(date).equals(DateUtil.dateBegin(new Date()))) return ;
		if(saleMoney==null) return; 
		saleMoney = new BigDecimal(saleMoney/10000.0).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
		
		TsmReportPlanBean entity = new TsmReportPlanBean();
		entity.setOrgId(orgId);
		entity.setAccount(account);
		
		entity.setFromDate(DateUtil.dateBegin(date));
		entity.setToDate(DateUtil.dateEnd(date));
		
		TsmReportPlanBean report = tsmReportPlanMapper.getByCondtion(entity);
		if(report !=null){
			TsmReportPlanBean updateBean = new TsmReportPlanBean();
			updateBean.setId(report.getId());
			updateBean.setUpdateDate(new Date());
			updateBean.setOrgId(orgId);
			updateBean.setSaleMoney(saleMoney);
			tsmReportPlanMapper.plusSaleMoney(updateBean);
		}
	}
	public void plusSaleMoney(String orgId,String account,Date date,Double saleMoney){
		plusMoney(orgId, account, date, saleMoney);
	}
	
}
