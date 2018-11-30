package com.qftx.tsm.worklog.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.tsm.worklog.bean.WorkLogBbsBean;
import com.qftx.tsm.worklog.bean.WorkLogBbsUpBean;
import com.qftx.tsm.worklog.bean.WorkLogInfoBean;
import com.qftx.tsm.worklog.dao.WorkLogBbsMapper;
import com.qftx.tsm.worklog.dao.WorkLogBbsUpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WorkLogBbsUpService {
	@Autowired
	WorkLogInfoService workLogInfoService;
	@Autowired
	WorkLogBbsMapper workLogBbsMapper;
	@Autowired 
	WorkLogBbsUpMapper workLogBbsUpMapper;
	@Autowired
	WorkLogBbsService workLogBbsService;
	@Autowired
	CachedService cachedService;
	
	
	public void removeUp(WorkLogBbsUpBean entity){
		WorkLogBbsUpBean remove = new WorkLogBbsUpBean();
		remove.setOrgId(entity.getOrgId());
		remove.setId(entity.getId());
		remove.setUpdatedate(new Date());
		remove.setIsDel(1);
		workLogBbsUpMapper.updateTrends(remove);
	}
	
	public WorkLogBbsUpBean getByCondtion(WorkLogBbsUpBean entity){
		entity.setIsDel(0);
		return workLogBbsUpMapper.getByCondtion(entity);
	}
	
	public List<WorkLogBbsUpBean> findByCondtion(WorkLogBbsUpBean entity){
		entity.setIsDel(0);
		return workLogBbsUpMapper.findByCondtion(entity);
	}
	
	public List<WorkLogBbsUpBean> findListPage(WorkLogBbsUpBean entity){
		entity.setIsDel(0);
		return workLogBbsUpMapper.findListPage(entity);
	}
	
	public List<WorkLogBbsUpBean> findFavourList(WorkLogBbsUpBean entity){
		entity.setIsDel(0);
		List<WorkLogBbsUpBean> list = findListPage(entity);
		return list;
	}
	
	
	/**
	 * 处理是否显示点赞逻辑*/
	public void processFavour1(List<WorkLogBbsBean> bbss,ShiroUser user){
		List<String> wlbIds= new ArrayList<String>();
		for (WorkLogBbsBean  workLogBbsBean : bbss) {
			wlbIds.add(workLogBbsBean.getWlbId());
		}
		
		WorkLogBbsUpBean entity = new WorkLogBbsUpBean();
		entity.setUserId(user.getId());
		entity.setWlbIds(wlbIds);
		
		List<WorkLogBbsUpBean> list = findByCondtion(entity);
		
		wlbIds = new ArrayList<String>();
		for (WorkLogBbsUpBean workLogBbsUpBean : list) {
			wlbIds.add(workLogBbsUpBean.getWlbId());
		}
		
		if(wlbIds == null || wlbIds.size() == 0){
			return ;
		}else{
			for (WorkLogBbsBean  workLogBbsBean : bbss) {
				if(wlbIds.contains(workLogBbsBean.getWlbId()))
				workLogBbsBean.setFavour(true);
			}
		}
	}
	
	/**
	 * 处理是否显示点赞逻辑*/
	public void processFavour(List<WorkLogInfoBean> logs,ShiroUser user){
		List<String> wlbIds= new ArrayList<String>();
		for (WorkLogInfoBean workLogInfoBean : logs) {
			wlbIds.add(workLogInfoBean.getWliId());
		}
		
		WorkLogBbsUpBean entity = new WorkLogBbsUpBean();
		entity.setUserId(user.getId());
		entity.setWlbIds(wlbIds);
		
		List<WorkLogBbsUpBean> list = findByCondtion(entity);
		
		wlbIds = new ArrayList<String>();
		for (WorkLogBbsUpBean workLogBbsUpBean : list) {
			wlbIds.add(workLogBbsUpBean.getWlbId());
		}
		
		if(wlbIds == null || wlbIds.size() == 0){
			return ;
		}else{
			for (WorkLogInfoBean workLogInfoBean : logs) {
				if(wlbIds.contains(workLogInfoBean.getWliId()))
				workLogInfoBean.setFavour(true);
			}
		}
	}
	
	/* 插入*/
	public WorkLogBbsUpBean insert(WorkLogBbsUpBean bean){
		String id=GuidUtil.getGuid();
		bean.setId(id);
		bean.setInputdate(new Date());
		bean.setIsDel(0);
		workLogBbsUpMapper.insert(bean);
		return bean;
	}
	
	/* 评论顶数+1*/
	public BaseJsonResult updateUpNum(WorkLogBbsUpBean bbsUp){
		WorkLogBbsUpBean db = getByCondtion(bbsUp);
		int upNum =0;
		if(db==null){
			upNum =1;
			insert(bbsUp);
		}else{
			upNum=-1;
			removeUp(db);
		}
		
		if(bbsUp.getType()==null || bbsUp.getType().intValue()==0){
			//评论点赞
			WorkLogBbsBean bbs = new WorkLogBbsBean();
			bbs.setOrgId(bbsUp.getOrgId());
			bbs.setWlbId(bbsUp.getWlbId());
			bbs.setUpNum(upNum);
			workLogBbsMapper.updateUpNum(bbs);
		}else{
			//日志点赞
			workLogInfoService.updateNum(bbsUp.getOrgId(), bbsUp.getWlbId(), "favour_num",upNum);
		}
		return BaseJsonResult.success("upNum",upNum);
	}
}
