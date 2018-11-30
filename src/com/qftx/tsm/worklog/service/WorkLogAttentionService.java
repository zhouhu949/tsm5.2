package com.qftx.tsm.worklog.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.plan.ResultDTO;
import com.qftx.tsm.worklog.bean.WorkLogAttentionBean;
import com.qftx.tsm.worklog.bean.WorkLogInfoBean;
import com.qftx.tsm.worklog.dao.WorkLogAttentionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WorkLogAttentionService {
	@Autowired WorkLogAttentionMapper workLogAttentionMapper;
	public List<WorkLogAttentionBean> findList(ShiroUser user){
		WorkLogAttentionBean entity = new WorkLogAttentionBean();
		entity.setUserId(user.getId());
		entity.setOrgId(user.getOrgId());
		entity.setIsDel(0);
		return workLogAttentionMapper.findByCondtion(entity);
	}
	/* 关注   取消关注*/
	public ResultDTO attention(ShiroUser user,String attentionUserId,String type){
		WorkLogAttentionBean entity = new WorkLogAttentionBean();
		entity.setUserId(user.getId());
		entity.setOrgId(user.getOrgId());
		entity.setIsDel(0);
		entity.setAttentionUserId(attentionUserId);
		WorkLogAttentionBean dbEntity = workLogAttentionMapper.getByCondtion(entity);
		if("add".equals(type)){
			//关注
			if(dbEntity == null){
				insert(user, attentionUserId);
			}else{
				return ResultDTO.erro("已经关注无法再次关注！");
			}
		}else{
			//取消关注     type =calcel
			if(dbEntity == null){
				return ResultDTO.erro("尚未关注无法取消关注！");
			}else{
				WorkLogAttentionBean updateEntity = new WorkLogAttentionBean();
				updateEntity.setOrgId(user.getOrgId());
				updateEntity.setId(dbEntity.getId());
				updateEntity.setUpdateTime(new Date());
				updateEntity.setIsDel(1);
				workLogAttentionMapper.updateTrends(updateEntity);
			}
		}
		return ResultDTO.Success();
	}
	
	public void processAttention(ShiroUser user,List<WorkLogInfoBean> list){
		if(list == null|| list.size()==0) return ;
		
		List<WorkLogAttentionBean> atts = findList(user);
		
		if(atts == null|| atts.size()==0) return ;
		
		List<String> userIds = new ArrayList<String>();
		for (WorkLogAttentionBean att : atts) {
			if(!userIds.contains(att.getAttentionUserId())){
				userIds.add(att.getAttentionUserId());
			}
		}
		
		if(userIds == null|| userIds.size()==0) return ;
		
		for (WorkLogInfoBean workLogInfoBean : list) {
			if(userIds.contains(workLogInfoBean.getUserId())){
				workLogInfoBean.setAttention(true);
			}
		}
	}
	
	
	/* 分页查询*/
	public List<WorkLogAttentionBean> findListPage(String orgId,WorkLogAttentionBean entity){
		entity.setOrgId(orgId);
		
		return workLogAttentionMapper.findListPage(entity);
	}
	/* 插入*/
	public WorkLogAttentionBean insert(ShiroUser user,String attentionUserId){
		WorkLogAttentionBean bean = new WorkLogAttentionBean();
		String id=GuidUtil.getGuid();
		bean.setId(id);
		bean.setOrgId(user.getOrgId());
		bean.setUserId(user.getId());
		bean.setAttentionUserId(attentionUserId);
		bean.setInputTime(new Date());
		bean.setIsDel(0);
		workLogAttentionMapper.insert(bean);
		return bean;
	}
}
