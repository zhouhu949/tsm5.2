package com.qftx.tsm.worklog.service;

import com.qftx.tsm.worklog.bean.WorkLogDefaultBean;
import com.qftx.tsm.worklog.dao.WorkLogDefaultMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class WorkLogDefaultShareService {
	@Autowired
	private WorkLogDefaultMapper workLogDefaultMapper;
	private Logger logger=  Logger.getLogger(WorkLogDefaultShareService.class);

	/* 查询默认分享人员*/
	public List<WorkLogDefaultBean> queryByUserId(String orgId,String userId){
		WorkLogDefaultBean entity = new WorkLogDefaultBean();
		entity.setOrgId(orgId);
		entity.setUserId(userId);
		entity.setIsDel(0);
		return workLogDefaultMapper.findByCondtion(entity);
	}
	
	/* 更新默认分享用户*/
	public void save(String orgId,String userId,String[] userAccs,String[] userIds){
		List<WorkLogDefaultBean> dbShares = queryByUserId(orgId,userId);
		
		Date shareTime = new Date();
		List<WorkLogDefaultBean> newShare = new ArrayList<WorkLogDefaultBean>();
		//去重
		for (int i=0;i<userAccs.length;i++) {
			String acc = userAccs[i];
			String id = userIds[i];
			WorkLogDefaultBean share = new WorkLogDefaultBean(orgId,userId, id,acc);
			share.setInputTime(shareTime);
			share.setUpdateTime(shareTime);
			share.setIsDel(0);
			if(newShare.contains(share)){
				logger.error("分享用户重复 userId:"+userId+"\t"+ orgId+"\t"+acc);
			}else{
				newShare.add(share);
			}
		}
		
		//删除取消的
		for (WorkLogDefaultBean share : dbShares) {
			if(!newShare.contains(share)){
				share.setIsDel(1);
				workLogDefaultMapper.updateTrends(share);
			}
		}
		
		//插入新增的
		for (WorkLogDefaultBean share : newShare) {
			if(!dbShares.contains(share)){
				workLogDefaultMapper.insert(share);
			}
		}
	}
}
