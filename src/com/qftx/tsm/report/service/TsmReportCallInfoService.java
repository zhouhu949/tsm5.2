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
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.NumUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.report.bean.PercentConfig;
import com.qftx.tsm.report.bean.TsmReportCallInfoBean;
import com.qftx.tsm.report.dao.TsmReportCallInfoMapper;
import com.qftx.tsm.report.dto.TsmReportCallInfoDTO;

@Service
public class TsmReportCallInfoService {
	@Autowired private TsmReportCallInfoMapper tsmReportCallInfoMapper;
	@Autowired private CachedService cachedService;
	@Autowired private TsmTeamGroupService	tsmTeamGroupService;
	@Autowired private TsmReportPlanSaleChanceService tsmReportPlanSaleChanceService;
	
	Logger logger = Logger.getLogger(TsmReportCallInfoService.class);
	
	//sortType : asc desc
	public List<TsmReportCallInfoBean> findByAccAndDate(String orgId,String account,Date fromDate,Date toDate,String sortType){
		TsmReportCallInfoDTO entity = new TsmReportCallInfoDTO();
		entity.setOrgId(orgId);
		entity.setFromDate(DateUtil.dateBegin(fromDate));
		entity.setToDate(DateUtil.dateEnd(toDate));
		entity.setAccount(account);
		entity.setOrderKey("t1.report_date "+sortType);
		return tsmReportCallInfoMapper.findByCondtion(entity);
	}
	
	public List<TsmReportCallInfoBean> findByGroupAndDate(String orgId,String[] groupIds,Date fromDate,Date toDate,String orderKey){
		TsmReportCallInfoDTO entity = new TsmReportCallInfoDTO();
		entity.setOrgId(orgId);
		entity.setFromDate(DateUtil.dateBegin(fromDate));
		entity.setToDate(DateUtil.dateEnd(toDate));
		entity.setGroupIds(groupIds);
		//entity.setOrderKey("t1.ACCOUNT");
		entity.setOrderKey(orderKey);
		entity.setGroupKey("t1.ACCOUNT");
		entity.setDateFmtStr("%Y-%m-%d");
		List<TsmReportCallInfoBean> list = tsmReportCallInfoMapper.findSumByCondtion(entity);
		formatCallTime(list);
		processUserName(list, orgId);;
		return list;
	}
	/**
	 * 查询时间段内用户的数据
	 * 以成员聚合
	 * */
	public List<TsmReportCallInfoBean> findSumByAccAndDate(String orgId,String account,Date fromDate,Date toDate,String sortType){
		TsmReportCallInfoDTO entity = new TsmReportCallInfoDTO();
		entity.setOrgId(orgId);
		entity.setFromDate(DateUtil.dateBegin(fromDate));
		entity.setToDate(DateUtil.dateEnd(toDate));
		entity.setAccount(account);
		
		entity.setDateFmtStr("%Y-%m");
		entity.setGroupKey("datefmt");
		entity.setOrderKey("t1.report_date "+sortType);
		
		List<TsmReportCallInfoBean> list = tsmReportCallInfoMapper.findSumByCondtion(entity);
		tsmReportPlanSaleChanceService.processByDate(list, entity);
		return list;
	}
	/**
	 * 查询时间段内部门内成员的数据
	 * 以成员聚合
	 * */
	public List<TsmReportCallInfoBean> findSumByGroupAndDate(String orgId,String[] userIds,Date fromDate,Date toDate,String orderKey){
		TsmReportCallInfoDTO entity = new TsmReportCallInfoDTO();
		entity.setOrgId(orgId);
		entity.setFromDate(DateUtil.dateBegin(fromDate));
		entity.setToDate(DateUtil.dateEnd(toDate));
		//entity.setGroupIds(groupIds);
		entity.setUserIds(userIds);
		
		entity.setDateFmtStr("%Y-%m");
		entity.setGroupKey("t1.ACCOUNT,t1.user_id,datefmt");
		//entity.setOrderKey("t1.ACCOUNT");
		entity.setOrderKey(orderKey);
		List<TsmReportCallInfoBean> list = tsmReportCallInfoMapper.findSumByCondtion(entity);
		tsmReportPlanSaleChanceService.processByAccount(list, entity);
		formatCallTime(list);
		processUserName(list, orgId);
		return list;
	}
	/**
	 * 查询时间段内部门的统计数据
	 * 以部门聚合
	 * */
	public List<TsmReportCallInfoBean> findSumByGroup(String orgId,String[] groupIds,Date fromDate,Date toDate,String sortType){
		TsmReportCallInfoDTO entity = new TsmReportCallInfoDTO();
		entity.setOrgId(orgId);
		entity.setFromDate(DateUtil.dateBegin(fromDate));
		entity.setToDate(DateUtil.dateEnd(toDate));
		entity.setGroupIds(groupIds);
		
		entity.setGroupKey("t2.group_id");
		//entity.setOrderKey("t1.report_date "+sortType);
		List<TsmReportCallInfoBean> list = tsmReportCallInfoMapper.findSumByGroup(entity);
		
		formatCallTime(list);
		tsmReportPlanSaleChanceService.processByGroup(list, entity);
		processGroupName(list, orgId);
		processTopGroupValue(orgId, list);
		return list;
	}
	
	public List<TsmReportCallInfoBean> findSumByDate(String orgId,String[] userIds,Date fromDate,Date toDate,String dateFmtStr,String sortType){
		TsmReportCallInfoDTO entity = new TsmReportCallInfoDTO();
		entity.setOrgId(orgId);
		entity.setFromDate(DateUtil.dateBegin(fromDate));
		entity.setToDate(DateUtil.dateEnd(toDate));
		entity.setUserIds(userIds);
		//entity.setGroupIds(groupIds);
		//entity.setDateFmtStr("%Y-%m-%d");
		entity.setDateFmtStr(dateFmtStr);
		entity.setGroupKey("datefmt");
		entity.setOrderKey("t1.report_date "+sortType);
		
		List<TsmReportCallInfoBean> list = tsmReportCallInfoMapper.findSumByDate(entity);
		formatCallTime(list);
		if("%Y-%m".equals(dateFmtStr)){
			tsmReportPlanSaleChanceService.processByDate(list, entity);
		}
		return list;
	}
	
	public TsmReportCallInfoBean getByCondition(TsmReportCallInfoBean entity){
		return tsmReportCallInfoMapper.getByCondtion(entity);
	}
	
	/* 插入*/
	public TsmReportCallInfoBean insert(String orgId,String account,Date date){
		TsmReportCallInfoBean bean = new TsmReportCallInfoBean();
		String id=GuidUtil.getGuid();
		bean.setId(id);
		bean.setOrgId(orgId);
		bean.setAccount(account);
		bean.setCallInNum(0);
		bean.setCallOutNum(0);
		bean.setCallRes(0);
		bean.setValidCallOut(0);
		bean.setChargeTime(0);
		bean.setCallTime(0);
		
		bean.setInputtime(date);
		bean.setUpdateDate(date);
		tsmReportCallInfoMapper.insert(bean);
		return bean;
	}
	
	public List<TsmReportCallInfoBean> processPercent(String orgId,List<TsmReportCallInfoBean> list){
		PercentConfig config = getPercentConfig(orgId);
		
		for (TsmReportCallInfoBean tsmReportCallInfoBean : list) {
			//{(每日呼出已接次数/每日呼出已接次数达标值)*接通次数指标系数+（每日通话时长/每日通话时长达标值）*通话时长指标系数}乘以100%；
			int callTime = 0;
			if(config.isCallInTime()) callTime+=tsmReportCallInfoBean.getInCallTime();
			if(config.isCallOutTime()) callTime+=tsmReportCallInfoBean.getCallTime();
			
			double percent =(tsmReportCallInfoBean.getCallInNum()*1.0d/config.getBasicNum()*config.getNumXishu()+
					callTime*1.0d/config.getBasicTime()*config.getTimeXishu());
			tsmReportCallInfoBean.setPercent(percent);
		}
    	return list;
    }
	
	public List<TsmReportCallInfoBean> formatCallTime(List<TsmReportCallInfoBean> list){
		for (TsmReportCallInfoBean tsmReportCallInfoBean : list) {
			if(tsmReportCallInfoBean.getCallTime()!=null)
			tsmReportCallInfoBean.setCallTime((int) Math.ceil(tsmReportCallInfoBean.getCallTime()/60.0));
			
			if(tsmReportCallInfoBean.getInCallTime()!=null)
				tsmReportCallInfoBean.setInCallTime((int) Math.ceil(tsmReportCallInfoBean.getInCallTime()/60.0));
		}
    	return list;
    }
	     
	public void processUserName(List<TsmReportCallInfoBean> list,String orgId){
		if(list!=null&&list.size()>0){
			Map<String, String> userNameMap = cachedService.getOrgUserNames(orgId);
			
			if(userNameMap!=null && !userNameMap.isEmpty()){
				for (TsmReportCallInfoBean tsmReportCallInfoBean : list) {
					String account = tsmReportCallInfoBean.getAccount();
					if(account!=null){
						tsmReportCallInfoBean.setUserName(userNameMap.get(account));
					}
				}
			}
		}
    }
	
	public void processGroupName(List<TsmReportCallInfoBean> list,String orgId){
		if(list!=null&&list.size()>0){
			Map<String, OrgGroup> groupMap = cachedService.getOrgGroupMap(orgId);
			
			if(groupMap!=null && !groupMap.isEmpty()){
				for (TsmReportCallInfoBean tsmReportCallInfoBean : list) {
					String groupId = tsmReportCallInfoBean.getGroupId();
					if(groupId!=null){
						OrgGroup group = groupMap.get(groupId);
						if(group!=null) tsmReportCallInfoBean.setGroupName(group.getGroupName());
					}
				}
			}
		}
    }
	
	/*处理父子部门数据包含*/
	public void processTopGroupValue(String orgId,List<TsmReportCallInfoBean> list){
		List<String> pids = tsmTeamGroupService.findAllPid(orgId);
		Map<String, List<OrgGroup>> orgGroupPidMap = cachedService.getOrgGroupPidMap(orgId);
			
		if(pids!=null &&pids.size()>0&&orgGroupPidMap!=null &&!orgGroupPidMap.isEmpty()){
			Map<String, TsmReportCallInfoBean> map = list2Map(list);
			
			Set<String> keys = map.keySet();
			List<String> teamGroupIds = new ArrayList<String>();
			for (String groupId : keys) {
				if(!pids.contains(groupId)) teamGroupIds.add(groupId);
			}
			List<String> fathers = tsmTeamGroupService.findPidsByGroupIds(orgId, teamGroupIds);
			if(fathers!=null && fathers.size()>0){
				process(orgId,fathers, map, orgGroupPidMap);
			}
			
			list = new ArrayList<TsmReportCallInfoBean>();
			for(String gid :map.keySet()){
				list.add(map.get(gid));
			}
		}
	}
	
	public Map<String, TsmReportCallInfoBean> list2Map(List<TsmReportCallInfoBean> list){
		Map<String, TsmReportCallInfoBean> map = new LinkedHashMap<String,TsmReportCallInfoBean>();
		for (TsmReportCallInfoBean tsmReportCallInfoBean : list) {
			String groupId = tsmReportCallInfoBean.getGroupId();
			map.put(groupId, tsmReportCallInfoBean);
		}
		return map;
	}
	
	public void process(String orgId,List<String> groupIds,Map<String, TsmReportCallInfoBean> map,Map<String, List<OrgGroup>> orgGroupPidMap){
		for (String gid : groupIds) {
			List<OrgGroup> sonGroupIds = orgGroupPidMap.get(gid);
			TsmReportCallInfoBean fatherReport = map.get(gid);
			if(fatherReport!=null){
				for (OrgGroup sonGroup : sonGroupIds) {
					TsmReportCallInfoBean sonReport = map.get(sonGroup.getGroupId());
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
	public void plusData(TsmReportCallInfoBean target,TsmReportCallInfoBean source){
		target.setCallOutNum(target.getCallOutNum()+(source.getCallOutNum()==null?0:source.getCallOutNum()));
		target.setCallInNum(target.getCallInNum()+(source.getCallInNum()==null?0:source.getCallInNum()));
		target.setCallRes(target.getCallRes()+(source.getCallRes()==null?0:source.getCallRes()));
		target.setValidCallOut(target.getValidCallOut()+(source.getValidCallOut()==null?0:source.getValidCallOut()));
		target.setChargeTime(target.getChargeTime()+(source.getChargeTime()==null?0:source.getChargeTime()));
		target.setCallTime(target.getCallTime()+(source.getCallTime()==null?0:source.getCallTime()));
		target.setWillNum(target.getWillNum()+(source.getWillNum()==null?0:source.getWillNum()));
		target.setSignNum(target.getSignNum()+(source.getSignNum()==null?0:source.getSignNum()));
		target.setSignMoney(BigDecimal.valueOf(target.getSignMoney()==null?0:target.getSignMoney()).add(BigDecimal.valueOf(source.getSignMoney()==null?0:source.getSignMoney())).doubleValue());
		target.setSaleMoney(BigDecimal.valueOf(target.getSaleMoney()==null?0:target.getSaleMoney()).add(BigDecimal.valueOf(source.getSaleMoney()==null?0:source.getSaleMoney())).doubleValue());
		target.setSaleChanceMoney(BigDecimal.valueOf(target.getSaleChanceMoney()==null?0:target.getSaleChanceMoney()).add(BigDecimal.valueOf(source.getSaleChanceMoney()==null?0:source.getSaleChanceMoney())).doubleValue());
	}
	
	public int getTimeElngth(String orgId) {
		String isEffect = cachedService.getDirList(AppConstant.DATA16, orgId).get(0).getDictionaryValue();
		int timeElngth = 0;
		if ("1".equals(isEffect)) {
			timeElngth = new Integer(cachedService.getDirList(AppConstant.DATA04, orgId).get(0).getDictionaryValue());
		}
		return timeElngth;
	}
	
	public PercentConfig getPercentConfig(String orgId) {
		PercentConfig config = new PercentConfig();
		try {
			String open = cachedService.getDirList(AppConstant.DATA_50004, orgId).get(0).getDictionaryValue();
			String basicTime = cachedService.getDirList(AppConstant.DATA_50005, orgId).get(0).getDictionaryValue();
			String callInTime = cachedService.getDirList(AppConstant.DATA_50006, orgId).get(0).getDictionaryValue();
			String callOutTime = cachedService.getDirList(AppConstant.DATA_50007, orgId).get(0).getDictionaryValue();
			String basicNum = cachedService.getDirList(AppConstant.DATA_50008, orgId).get(0).getDictionaryValue();
			String timeXishu = cachedService.getDirList(AppConstant.DATA_50009, orgId).get(0).getDictionaryValue();
			String numXishu = cachedService.getDirList(AppConstant.DATA_50010, orgId).get(0).getDictionaryValue();
			
			
			config.setOpen("1".equals(open));
			
			config.setCallInTime("1".equals(callInTime));
			config.setCallOutTime("1".equals(callOutTime));
			config.setBasicNum(NumUtil.parseInt(basicNum));
			config.setBasicTime(NumUtil.parseInt(basicTime));
			config.setTimeXishu(NumUtil.parseDouble(timeXishu));
			config.setNumXishu(NumUtil.parseDouble(numXishu));
		} catch (Exception e) {
			config = new PercentConfig();
			logger.error("获取缓存失败！orgId["+orgId+"]",e);
		}
		return config;
	}
}
