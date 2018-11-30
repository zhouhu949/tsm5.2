package com.qftx.tsm.plan.facade;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Date;

//计划实际执行
@Service
public class PlanFactService {
	@Autowired 
	ThreadPoolTaskExecutor poolTaskExecutor;
	private Logger logger = Logger.getLogger(PlanFactService.class);
	
	/*签约*/
	public void toSign(String orgId,String groupId,String userId,String custId,int signNum,double factMoney){
		ToSignThread thread = new ToSignThread(orgId, groupId, userId, custId, signNum, factMoney,new Date());
		poolTaskExecutor.execute(thread);
	}
	
	/*签约*/
	//type 0:资源   1：意向
	//custType 0:老客户  1:新客户
	public void cancelSign(String orgId,String groupId,String userId,String custId,int signNum,double factMoney,Date date,int type){
		ToSignThread thread = new ToSignThread(orgId, groupId, userId, custId, signNum, factMoney,date);
		thread.setType(type);
		poolTaskExecutor.execute(thread);
		
		if(factMoney != 0){
			cancelContractMoney(orgId, groupId, 0, factMoney, date);
		}
		
		if(signNum!=0){
			cancelContractNum(orgId, groupId, 0, signNum, date);
		}
		
	}
	
	/*转意向*/
	public void toWill(String orgId,String groupId,String userId){
		ToWillThread thread = new ToWillThread(orgId, groupId, userId);
		poolTaskExecutor.execute(thread);
	}
	
	
	
	/*联系后设置下次联系时间为当天 
	 * custType当前状态      changeCustType更改后状态(
	 * res:资源 
	 * will:意向  
	 * sign:签约
	 * )
	 * nextFollowToday 传true 区分下面的方法实际没屌用
	 * */
	public void updateContactResult(String orgId,String groupId,String userId,String custId,String custType,boolean nextFollowToday,String changeCustType){
		UpdateContactResultThread thread = new UpdateContactResultThread(orgId, groupId, userId, custId, custType);
		thread.setNextFollowToday(true);
		thread.setChangeCustType(changeCustType);
		poolTaskExecutor.execute(thread);
	}
	
	/*更新资源联系结果  
	 * custType(
	 * res:资源   contactResult枚举值：  PlanResCR
	 * will:意向  contactResult枚举值：PlanWillCR
	 * sign:签约 contactResult枚举值：  PlanSignCR
	 * )*/
	public void updateContactResult(String orgId,String groupId,String userId,String custId,String custType,String contactResult){
		updateContactResult(orgId, groupId, userId, custId, custType, contactResult, null);
	}
	
	/*更新资源联系结果  
	 * custType(
	 * res:资源   contactResult枚举值：  PlanResCR
	 * will:意向  contactResult枚举值：PlanWillCR
	 * sign:签约 contactResult枚举值：  PlanSignCR
	 * 
	 * lastOptopnId:销售进程ID
	 * )*/
	public void updateContactResult(String orgId,String groupId,String userId,String custId,String custType,String contactResult,String lastOptopnId){
		UpdateContactResultThread thread = new UpdateContactResultThread(orgId, groupId, userId, custId, custType, contactResult,lastOptopnId);
		poolTaskExecutor.execute(thread);
	}
	
	/*更新签约合同客户数*/
	//type 0:老客户  1:新客户
	public void updateContractNum(String orgId,String groupId,int type,int contractNum){
		 UpdateContractNumThread thread = new UpdateContractNumThread(orgId, groupId, type, contractNum,new Date());
		 poolTaskExecutor.execute(thread);
	}
	
	//type 0:老客户  1:新客户
	private void cancelContractNum(String orgId,String groupId,int type,int contractNum,Date date){
		 UpdateContractNumThread thread = new UpdateContractNumThread(orgId, groupId, type, contractNum,date);
		 poolTaskExecutor.execute(thread);
	}
	
	/*更新签约金额数*/
	public void updateContractMoney(String orgId,String groupId,int type,Double contractMoney){ 
		UpdateContractMoneyThread thread = new UpdateContractMoneyThread(orgId, groupId, type, contractMoney,new Date());
		poolTaskExecutor.execute(thread);
	}
	
	private void cancelContractMoney(String orgId,String groupId,int type,Double contractMoney,Date date){
		 UpdateContractMoneyThread thread = new UpdateContractMoneyThread(orgId, groupId, type, contractMoney,date);
		 poolTaskExecutor.execute(thread);
	}
	
	/*更新流失客户数*/
	public void updateLostNum(String orgId,String groupId,int lostNum){
		UpdateLostNumThread thread = new UpdateLostNumThread(orgId, groupId, lostNum);
		poolTaskExecutor.execute(thread);
	}
	
	/*更新沉默客户数*/
	public void updateSilenceNum(String orgId,String groupId,int silenceNum){
		UpdateSilenceNumThread thread = new UpdateSilenceNumThread(orgId, groupId, silenceNum); 
		poolTaskExecutor.execute(thread);
	}
}
