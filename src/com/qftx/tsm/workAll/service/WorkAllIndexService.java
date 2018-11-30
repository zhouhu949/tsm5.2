package com.qftx.tsm.workAll.service;

import com.qftx.base.auth.bean.User;
import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.workAll.bean.WorkAllIndexBean;
import com.qftx.tsm.workAll.dao.WorkAllIndexMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class WorkAllIndexService {
	@Autowired
	private WorkAllIndexMapper workAllIndexMapper;
	private Logger logger=  Logger.getLogger(WorkAllIndexService.class);
	
	/*查询全部*/
	public List<WorkAllIndexBean> findAllWorkByPage(WorkAllIndexBean entity,String type){
		entity.setIsDel(0);
		entity.setIsDel(0);
		if("first".equals(type)){
			entity.setSymbol(">");
			entity.setOrderKey("input_date asc");
		}else{
			entity.setSymbol("<");
			entity.setOrderKey("input_date desc");
		}
		return workAllIndexMapper.getAllWorkByPage(entity);
	}
	
	/*更新数据*/
	public void updateTrends(WorkAllIndexBean entity){
		workAllIndexMapper.updateTrends(entity);
	}
	
	/*插入数据*/
	public void insert(String workId,Date inputDate,User user,Integer type){
		WorkAllIndexBean bean = new WorkAllIndexBean();
		bean.setId(GuidUtil.getUUID());
		bean.setWorkId(workId);
		bean.setOrgId(user.getOrgId());
		bean.setUserId(user.getUserId());
		bean.setUserAcc(user.getUserAccount());
		bean.setIsDel(0);
		bean.setInputDate(inputDate);
		bean.setType(type);
		workAllIndexMapper.insert(bean);
	}
	
}
