package com.qftx.tsm.cust.service;

import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.cust.bean.ResCustEventBean;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dao.ResCustEventMapper;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.cust.dto.ResCustActionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class ResCustEventService {
	@Autowired
	private ResCustEventMapper resCustEventMapper;
	@Autowired
	private ResCustInfoMapper resCustInfoMapper;
	
	public List<ResCustEventBean> getByCondtion(ResCustEventBean bean){
		return resCustEventMapper.findByCondtion(bean);
	}
	
	public List<ResCustEventBean> getListPage(ResCustEventBean bean){
		return resCustEventMapper.findListPage(bean);
	}
	
	public List<ResCustEventBean> getHistoryListPage(ResCustEventBean bean){
		return resCustEventMapper.findHistoryListPage(bean);
	}
	
	public void create(ResCustEventBean bean){
		resCustEventMapper.insert(bean);
	}
	
	public List<Map<String, Object>> getEventsNums(ResCustEventBean bean){
		return resCustEventMapper.findEventsNums(bean);
	}
	
	/** 
	 * 增加事件数据
	 * @param orgId 组织ID
	 * @param custId 客户ID
	 * @param type 操作类型(1行动记录、2服务记录、3通话记录、4评论记录、5合同记录、6订单记录)
	 * @param jsonData json数据
	 * @create  2016年1月14日 下午6:53:55 lixing
	 * @history  
	 */
	public void create(String orgId,String custId,String type,String jsonData){
		ResCustEventBean rceb = new ResCustEventBean();
		rceb.setId(SysBaseModelUtil.getModelId());
		rceb.setOrgId(orgId);
		rceb.setCustId(custId);
		ResCustInfoBean custInfo = resCustInfoMapper.getByPrimaryKey(orgId, custId);
		rceb.setUserId(custInfo.getOwnerAcc());
		rceb.setData(jsonData);
		rceb.setType(type);
		rceb.setLifeCode(custInfo.getLifeCode());
		
		//资源客户状态  资源客户状态：1-未分配，2-未跟进，3-跟进中，4-公海客户，5-资源回收站，6-已签约，7-沉默客户，8-流失客户
		int custStatus = custInfo.getStatus();
		//类型:1-资源，2客户,3-再淘资源
		int custType = custInfo.getType();
		//我的资源
		if((custStatus == 2 || custStatus == 3) && (custType == 1 || custType == 3)){
			rceb.setState("1");
		}else if((custStatus == 2 || custStatus == 3) && custType == 2){//意向客户
			rceb.setState("2");
		}else if(custStatus == 6 && custType == 2){//签约客户
			rceb.setState("3");
		}else if(custStatus == 7 && custType == 2){//沉默客户
			rceb.setState("4");
		}else if(custStatus == 8 && custType == 2){//流失客户
			rceb.setState("5");
		}else{
			if(type.equals("1")){
				rceb.setState("2");
			}
		}
		
		Calendar cal = Calendar.getInstance();
		rceb.setInputtime(cal.getTime());
		rceb.setIsDel(0);
		resCustEventMapper.insert(rceb);
	}
	
	public void cleanActionRecord(String orgId,String type,List<String> custIds){
		resCustEventMapper.cleanActionRecords(orgId, type, custIds);
	}
	
	public List<ResCustActionDto> getResCustActionsListPage(ResCustActionDto dto){
		return resCustEventMapper.findResCustActionsListPage(dto);
	}
	
	public List<ResCustActionDto> getResCustActionsList(String orgId,String custId){
		return resCustEventMapper.findResCustActionsList(orgId, custId);
	}
}
