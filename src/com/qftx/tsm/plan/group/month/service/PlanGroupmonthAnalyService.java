package com.qftx.tsm.plan.group.month.service;

import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthAnalyBean;
import com.qftx.tsm.plan.group.month.dao.PlanGroupmonthAnalyMapper;
import com.qftx.tsm.plan.group.month.dto.AnalyDTO1;
import com.qftx.tsm.plan.user.day.service.PlanLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PlanGroupmonthAnalyService {
	@Autowired PlanGroupmonthAnalyMapper planGroupmonthAnalyMapper;
	@Autowired TsmTeamGroupService tsmTeamGroupService;
	
	/*public PlanGroupmonthAnalyBean getByUserIdAndDate(String orgId,String userId,Date date){
		OrgGroupUser entity = new OrgGroupUser();
		entity.setOrgId(orgId);
		entity.setUserId(userId);
		OrgGroupUser groupUser = orgGroupUserService.getByCondtion(entity);
		PlanGroupmonthAnalyBean analy = null;
		if(groupUser!=null && groupUser.getGroupId()!=null){
			analy = getByDate(orgId, groupUser.getGroupId(), date);
		}
		return analy;
	}*/
	
	public List<PlanGroupmonthAnalyBean> findErroData(){
		return  planGroupmonthAnalyMapper.findErroData();
	}
	
	public PlanGroupmonthAnalyBean getByDate(String orgId,String groupId,Date date){
		PlanGroupmonthAnalyBean entity = new PlanGroupmonthAnalyBean();
		entity.setIsDel(0);
		entity.setOrgId(orgId);
		entity.setGroupId(groupId);
		entity.setInputtime(DateUtil.monthBegin(date));
		
		entity.setFromDate(DateUtil.monthBegin(date));
		entity.setToDate(DateUtil.monthEnd(date));
		
		List<String> childrenGroups = tsmTeamGroupService.queryDeepGroupData(orgId, groupId);
		
		entity.setGroupIds(childrenGroups);
		PlanGroupmonthAnalyBean analy =null;
		synchronized (PlanLock.getInstance().getLock(orgId)) {
			analy = planGroupmonthAnalyMapper.getByCondtion(entity);
			if(analy == null){
				analy = insert(orgId, groupId, date);
			}else{
				int callTime = analy.getCallTime();
				if(callTime!=0){
					callTime = (int) Math.ceil(callTime/60.0);
					analy.setCallTime(callTime);
				}
			}
		}
		
		return analy;
	}
	
	/* 查询下属团队分析列表*/
	public List<PlanGroupmonthAnalyBean> findChildAnalyByDate(String orgId,String groupId,Date date){
		List<String> childrenGroups = tsmTeamGroupService.findSonGroupIds(orgId, groupId);
		
		PlanGroupmonthAnalyBean entity = new PlanGroupmonthAnalyBean();
		entity.setIsDel(0);
		entity.setOrgId(orgId);
		entity.setInputtime(DateUtil.monthBegin(date));
		entity.setGroupIds(childrenGroups);
		entity.setFromDate(DateUtil.monthBegin(date));
		entity.setToDate(DateUtil.monthEnd(date));
		
		
		List<PlanGroupmonthAnalyBean> analys = planGroupmonthAnalyMapper.findByCondtion(entity);
		
		if(analys != null){
			for (PlanGroupmonthAnalyBean analy : analys) {
				if(analy.getResNum()==null)analy.setResNum(0);
				if(analy.getSignMoney()==null) analy.setSignMoney(0d);
				if(analy.getLostCustNum()==null) analy.setLostCustNum(0);
				
				int callTime = analy.getCallTime();
				if(callTime!=0){
					callTime = (int) Math.ceil(callTime/60.0);
					analy.setCallTime(callTime);
				}
			}
		}
		return analys;
	}
	
	public List<PlanGroupmonthAnalyBean> findFatherByDate(String orgId,String groupId,Date date){
		List<PlanGroupmonthAnalyBean> analys= new ArrayList<PlanGroupmonthAnalyBean>();
		List<String> groupIds = tsmTeamGroupService.findFatherGroupIds(orgId, groupId);
		for (String gid : groupIds) {
			PlanGroupmonthAnalyBean entity = new PlanGroupmonthAnalyBean();
			entity.setIsDel(0);
			entity.setOrgId(orgId);
			entity.setGroupId(gid);
			entity.setInputtime(DateUtil.monthBegin(date));
			
			entity.setFromDate(DateUtil.monthBegin(date));
			entity.setToDate(DateUtil.monthEnd(date));
			
			synchronized (PlanLock.getInstance().getLock(orgId)) {
				List<PlanGroupmonthAnalyBean> dbAnalys = planGroupmonthAnalyMapper.findByCondtion1(entity);
				PlanGroupmonthAnalyBean analy = null;
				if(dbAnalys == null ||dbAnalys.size()==0){
					analy = insert(orgId, gid, date);
				}else{
					analy = dbAnalys.get(0);
					for(int i=1;i<dbAnalys.size();i++){
						planGroupmonthAnalyMapper.removeErroData(dbAnalys.get(i));
					}
				}
				analys.add(analy);
			}
		}
		return analys;
	}
	
	/* 更新联系资源*/
	public void updateRes(String orgId,String id,String custId){
		
		PlanGroupmonthAnalyBean entity = new PlanGroupmonthAnalyBean();
		entity.setOrgId(orgId);
		entity.setId(id);
		entity.setResNum(1);
		
		planGroupmonthAnalyMapper.updateFactNum(entity);
	}
	
	/* 更新转意向*/
	public void updateWill(String orgId,String id,int willNum){
		
		PlanGroupmonthAnalyBean entity = new PlanGroupmonthAnalyBean();
		entity.setOrgId(orgId);
		entity.setId(id);
		
		entity.setWillNum(willNum);
		
		planGroupmonthAnalyMapper.updateFactNum(entity);
	}
	
	/* 更新沉默客户*/
	public void updateSilenceNum(String orgId,String id ,Integer silenceCustNum){
		
		PlanGroupmonthAnalyBean entity = new PlanGroupmonthAnalyBean();
		entity.setOrgId(orgId);
		entity.setId(id);
		
		entity.setSilenceCustNum(silenceCustNum);
		
		planGroupmonthAnalyMapper.updateFactNum(entity);
	}
	
	/* 更新流失客户*/
	public void updateLostNum(String orgId,String id ,Integer lostCustNum){
		PlanGroupmonthAnalyBean entity = new PlanGroupmonthAnalyBean();
		entity.setOrgId(orgId);
		entity.setId(id);
		
		entity.setLostCustNum(lostCustNum);
		
		planGroupmonthAnalyMapper.updateFactNum(entity);
	}
	
	/* 更新签约合同数量   type 0:老客户  1:新客户*/
	public void updateContractNum(String orgId,String id,int type,Integer signNum){
		PlanGroupmonthAnalyBean entity = new PlanGroupmonthAnalyBean();
		entity.setOrgId(orgId);
		entity.setId(id);
		entity.setSignNum(signNum);
		if(type==0){
			entity.setOldCustSignNum(signNum);
		}else{
			entity.setNewCustSignNum(signNum);
		}
		entity.setUpdatetime(new Date());

		planGroupmonthAnalyMapper.updateFactNum(entity);
	}
	/* 更新签约金额   type 0:老客户  1:新客户*/
	public void updateContractMoney(String orgId,String id,int type,Double signMoney){
		PlanGroupmonthAnalyBean entity = new PlanGroupmonthAnalyBean();
		entity.setOrgId(orgId);
		entity.setId(id);
		entity.setUpdatetime(new Date());
		
		entity.setSignMoney(signMoney);
		if(type==0){
			entity.setOldCustSignMoney(signMoney);
		}else{
			entity.setNewCustSignMoney(signMoney);
		}

		planGroupmonthAnalyMapper.updateFactNum(entity);
	}
	
	/* 更新签约客户签约金额   type 0:老客户  1:新客户*/
	/*public void updateSign(String orgId,String id,int type,Integer signNum,Double signMoney){
		PlanGroupmonthAnalyBean entity = new PlanGroupmonthAnalyBean();
		entity.setOrgId(orgId);
		entity.setId(id);
		
		entity.setSignNum(signNum);
		entity.setSignMoney(signMoney);
		
		if(type==0){
			entity.setOldCustSignMoney(signMoney);
			entity.setOldCustSignNum(1);
		}else{
			entity.setNewCustSignMoney(signMoney);
			entity.setNewCustSignNum(1);
		}

		planGroupmonthAnalyMapper.updateFactNum(entity);
	}*/
	
	public AnalyDTO1 getAnalyDTO(String orgId,String groupId,int year,int month){
		Date currentMonth = DateUtil.monthBegin(DateUtil.getDate(year, month, 1));
		Date lastMonth = DateUtil.addDate(currentMonth, 0, -1, 0);
		
		PlanGroupmonthAnalyBean currentMonthAnaly = getByDate(orgId, groupId, currentMonth);
		PlanGroupmonthAnalyBean lastMonthAnaly = getByDate(orgId, groupId, lastMonth);
		
		AnalyDTO1 dto = new AnalyDTO1();
		dto.setCurrentMonthAnaly(currentMonthAnaly);
		dto.setLastMonthAnaly(lastMonthAnaly);
		return dto;
	}
	
	/* 插入*/
	public PlanGroupmonthAnalyBean insert(String orgId,String groupId,Date date){
		PlanGroupmonthAnalyBean bean = new PlanGroupmonthAnalyBean();
		String id=GuidUtil.getGuid();
		bean.setId(id);
		bean.setOrgId(orgId);
		bean.setGroupId(groupId);
		
		bean.setGroupType(0);
		bean.setResNum(0);
		bean.setWillNum(0);
		bean.setSignNum(0);
		bean.setSilenceCustNum(0);
		bean.setLostCustNum(0);
		bean.setNewCustSignNum(0);
		bean.setOldCustSignNum(0);
		bean.setNewCustSignMoney(0d);
		bean.setOldCustSignMoney(0d);
		bean.setSignMoney(0d);
		bean.setIsDel(0);
		
		bean.setInputtime(DateUtil.monthBegin(date));
		bean.setUpdatetime(date);
		
		planGroupmonthAnalyMapper.insert(bean);
		return bean;
	}
}
