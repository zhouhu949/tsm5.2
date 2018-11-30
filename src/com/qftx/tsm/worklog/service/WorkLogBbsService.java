package com.qftx.tsm.worklog.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.worklog.bean.WorkLogBbsBean;
import com.qftx.tsm.worklog.dao.WorkLogBbsMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class WorkLogBbsService {
	@Autowired
	WorkLogInfoService workLogInfoService;
	@Autowired
	private WorkLogBbsMapper workLogBbsMapper;
	@Autowired
	WorkLogBbsUpService workLogBbsUpService;
	
	private Logger logger=  Logger.getLogger(WorkLogBbsService.class);
	
	/*根据用户ID查询日志评论信息*/
	public List<WorkLogBbsBean> findListPage(ShiroUser user , WorkLogBbsBean item){
		item.setIsDel(0);
		item.setOrgId(user.getOrgId());
		List<WorkLogBbsBean> list = workLogBbsMapper.findListPage(item);
		
		for (WorkLogBbsBean workLogBbsBean : list) {
			if(user.getId().equals(workLogBbsBean.getUserId())) workLogBbsBean.setDelable(true);
		}
		return list;
	}
	
	public WorkLogBbsBean getByCondtion(WorkLogBbsBean entity){
		entity.setIsDel(0);
		return workLogBbsMapper.getByCondtion(entity);
	}
	
	public List<WorkLogBbsBean> findByCondition(WorkLogBbsBean entity){
		entity.setIsDel(0);
		return workLogBbsMapper.findByCondtion(entity);
	}
	
	public BaseJsonResult delBbs(ShiroUser user,WorkLogBbsBean del){
		if(StringUtils.isBlank(del.getWlbId())){
			return BaseJsonResult.error("回复ID为空！");
		}
		
		WorkLogBbsBean dbData = getByCondtion(del);
		if(dbData==null){
			return BaseJsonResult.error("回复不存在！");
		}else if(!user.getId().equals(dbData.getUserId())){
			return BaseJsonResult.error("只能删除自己的回复！");
		}else{
			del.setIsDel(1);
			workLogBbsMapper.updateTrends(del);
			workLogInfoService.updateNum(del.getOrgId(), dbData.getWliId(), "comment_num",-1);
			return BaseJsonResult.success();
		}
	}
	
	/* 插入评论*/
	public void insertBbs(ShiroUser user,WorkLogBbsBean workLogBbs){
		//1、插入评论次数
		if(!StringUtils.isBlank(workLogBbs.getReplyWlbId())){
			WorkLogBbsBean query = new WorkLogBbsBean();
			query.setOrgId(user.getOrgId());
			query.setWlbId(workLogBbs.getReplyWlbId());
			query = getByCondtion(query);
			if(query!=null && query.getUserId()!=null){
				workLogBbs.setReplyUserId(query.getUserId());
			}
		}
		
		workLogBbs.setWlbId(GuidUtil.getUUID());
		workLogBbs.setUserId(user.getId());
		workLogBbs.setOrgId(user.getOrgId());
		workLogBbs.setInputdate(new Date());
		workLogBbs.setIsDel(0);
		workLogBbs.setUpNum(0);
		workLogBbs.setType(2);
		workLogBbsMapper.insert(workLogBbs);
		
		//2、评论数+1
		workLogInfoService.updateNum(user.getOrgId(), workLogBbs.getWliId(), "comment_num",1);
	}
}
