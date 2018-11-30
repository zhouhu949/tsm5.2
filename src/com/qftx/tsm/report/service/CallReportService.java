package com.qftx.tsm.report.service;

import com.alibaba.fastjson.JSON;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.report.bean.CallReportBean;
import com.qftx.tsm.report.dao.CallReportMapper;
import com.qftx.tsm.report.dto.CallReportDto;
import com.qftx.tsm.report.dto.CallReportQueryDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CallReportService {
	private Log log = LogFactory.getLog(CallReportService.class);
	
	@Autowired
	private CallReportMapper callReportMapper;
	
	public CallReportBean getUserTodayCallReport(String orgId,String account){
		CallReportBean crb = new CallReportBean();
		try {
			CallReportQueryDto queryDto = new CallReportQueryDto();
			queryDto.setOrgId(orgId);
			queryDto.setAccounts(Arrays.asList(account));
			String re_msg = HttpUtil.doPostJSON(ConfigInfoUtils.getStringValue("today_call_report_data_url"), JsonUtil.getJsonString(queryDto));
			CallReportQueryDto re_dto = JSON.parseObject(re_msg,CallReportQueryDto.class);
			if(re_dto.getBeans().size() > 0){
				crb = re_dto.getBeans().get(0);
			}
		} catch (Exception e) {
			log.error("个人今日通话数据查询失败！",e);
		}
		return crb;
	}
	
	
	/** 
	 * 上门拜访量修改
	 * @param orgId 单位ID
	 * @param account 帐号
	 * @param addNum 增量
	 * @create  2016年5月31日 上午11:22:50 lixing
	 * @history  
	 */
	public void addVisit(String orgId,String account,Integer addNum){
		CallReportBean crb = new CallReportBean();
		List<CallReportBean> beans = callReportMapper.findUserDayCallReport(orgId,account,DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		if(beans.size() > 0){
			crb = beans.get(0);
			crb.setVisitNum((crb.getVisitNum() == null ? 0 : crb.getVisitNum()) +addNum);
			crb.setUpdateTime(new Date());
			callReportMapper.update(crb);
		}else{
			crb.setId(SysBaseModelUtil.getModelId());
			crb.setReportDate(new Date());
			crb.setAccount(account);
			crb.setOrgId(orgId);
			crb.setInsertTime(new Date());
			crb.setVisitNum(addNum);
			callReportMapper.insert(crb);
		}
	}
	
	public List<CallReportDto> getTeamDayCallReport(String orgId,String account,List<String> groupIds) throws Exception{
		List<CallReportDto> list = callReportMapper.findTeamDayCallReport(orgId,account,groupIds);
		if(list.size() > 0){
			List<String> accounts = new ArrayList<String>();
			Map<String, CallReportDto> map = new HashMap<String, CallReportDto>();
			for(CallReportDto dto : list){
				accounts.add(dto.getAccount());
				map.put(dto.getAccount(), dto);
			}
			CallReportQueryDto queryDto = new CallReportQueryDto();
			queryDto.setOrgId(orgId);
			queryDto.setAccounts(accounts);
			String re_msg = HttpUtil.doPostJSON(ConfigInfoUtils.getStringValue("today_call_report_data_url"), JsonUtil.getJsonString(queryDto));
			CallReportQueryDto re_dto = JSON.parseObject(re_msg,CallReportQueryDto.class);
			if(re_dto.getBeans() != null && re_dto.getBeans().size() > 0){
				list.clear();
				for(CallReportBean bean : re_dto.getBeans()){
					CallReportDto crd = map.get(bean.getAccount());
					BeanUtils.copyProperties(bean, crd);
					crd.setCallinTime((int)Math.ceil((double)crd.getCallinTime()/(double)60));
					crd.setCalloutTime((int)Math.ceil((double)crd.getCalloutTime()/(double)60));
					list.add(crd);
					map.remove(bean.getAccount());
				}
				for(String key : map.keySet()){
					list.add(map.get(key));
				}
			}
		}
		return list;
	}
	
	public Map<String,CallReportDto> getDayReportByAccountsMap(String orgId,List<String> accounts) throws Exception{
		Map<String,CallReportDto> map = new HashMap<String, CallReportDto>();
		for(String acc : accounts){
			CallReportDto crd = new CallReportDto();
			crd.setAccount(acc);
			map.put(acc, crd);
		}
		CallReportQueryDto queryDto = new CallReportQueryDto();
		queryDto.setOrgId(orgId);
		queryDto.setAccounts(accounts);
		String re_msg = HttpUtil.doPostJSON(ConfigInfoUtils.getStringValue("today_call_report_data_url"), JsonUtil.getJsonString(queryDto));
		CallReportQueryDto re_dto = JSON.parseObject(re_msg,CallReportQueryDto.class);
		if(re_dto.getBeans() != null && re_dto.getBeans().size() > 0){
			for(CallReportBean bean : re_dto.getBeans()){
				CallReportDto crd = map.get(bean.getAccount());
				BeanUtils.copyProperties(bean, crd);
				map.put(bean.getAccount(),crd);
			}
		}
		return map;
	}
	
	public List<CallReportDto> getTeamDayCallReportListPage(String orgId,String account,List<String> groupIds) throws Exception{
		List<CallReportDto> list = callReportMapper.findTeamDayCallReportListPage(orgId,account,groupIds);
		if(list.size() > 0){
			List<String> accounts = new ArrayList<String>();
			Map<String, CallReportDto> map = new HashMap<String, CallReportDto>();
			for(CallReportDto dto : list){
				accounts.add(dto.getAccount());
				map.put(dto.getAccount(), dto);
			}
			CallReportQueryDto queryDto = new CallReportQueryDto();
			queryDto.setOrgId(orgId);
			queryDto.setAccounts(accounts);
			String re_msg = HttpUtil.doPostJSON(ConfigInfoUtils.getStringValue("today_call_report_data_url"), JsonUtil.getJsonString(queryDto));
			CallReportQueryDto re_dto = JSON.parseObject(re_msg,CallReportQueryDto.class);
			if(re_dto.getBeans() != null && re_dto.getBeans().size() > 0){
				list.clear();
				for(CallReportBean bean : re_dto.getBeans()){
					CallReportDto crd = map.get(bean.getAccount());
					BeanUtils.copyProperties(bean, crd);
					crd.setCallinTime((int)Math.ceil((double)crd.getCallinTime()/(double)60));
					crd.setCalloutTime((int)Math.ceil((double)crd.getCalloutTime()/(double)60));
					list.add(crd);
					map.remove(bean.getAccount());
				}
				for(String key : map.keySet()){
					list.add(map.get(key));
				}
			}
		}
		return list;
	}
	
}
