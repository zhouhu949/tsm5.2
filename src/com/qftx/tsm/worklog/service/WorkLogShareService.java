package com.qftx.tsm.worklog.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.tsm.plan.ResultDTO;
import com.qftx.tsm.worklog.bean.WorkLogInfoBean;
import com.qftx.tsm.worklog.bean.WorkLogShareBean;
import com.qftx.tsm.worklog.dao.WorkLogInfoMapper;
import com.qftx.tsm.worklog.dao.WorkLogShareMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class WorkLogShareService {
	@Autowired
	private WorkLogShareMapper shareMapper;
	private Logger logger=  Logger.getLogger(WorkLogShareService.class);
	@Autowired
	private WorkLogInfoMapper workLogInfoMapper;
	@Autowired
	private WorkLogInfoService workLogInfoService;
	@Autowired
	private TsmTeamGroupService tsmTeamGroupService;
	//分享日志 手工分享+默认分享
	public List<WorkLogInfoBean> findShareLogByPage(ShiroUser user,WorkLogInfoBean item,String type){
		item.setOrgId(user.getOrgId());
		item.setUserId(user.getId());
		if("first".equals(type)){
			item.setSymbol(">");
			item.setOrderKey("log_date asc");
		}else{
			item.setSymbol("<");
			if(type==null && item.getLogDate()!=null)  item.setLogDate(DateUtil.dateEnd(item.getLogDate()));
			item.setOrderKey("log_date desc");
		}
		
		return  workLogInfoMapper.findShareLogByPage(item);
	}
	
	/* 查询分享人员*/
	public List<WorkLogShareBean> findByCondtion(String orgId,String wliId){ 
		WorkLogShareBean entity = new WorkLogShareBean();
		entity.setWliId(wliId);
		entity.setOrgId(orgId);
		entity.setIsDel(0);
		
		return shareMapper.findByCondtion(entity);
	}
	
	/* 更新分享用户*/
	public ResultDTO saveShareUser(String orgId,String userId,String wliId,String[] userAccs,String[] userIds){
		List<WorkLogShareBean> dbShares = findByCondtion(orgId, wliId);
		
		Date shareTime = new Date();
		List<WorkLogShareBean> newShare = new ArrayList<WorkLogShareBean>();
		//更新标记
		boolean flag=false;
		
		//去重
		for (int i=0;i< userAccs.length;i++) {
			String shareUserId= userIds[i];
			String shareUserAcc = userAccs[i];
			
			
			WorkLogShareBean share = new WorkLogShareBean(wliId, userId, orgId, shareUserAcc,shareUserId);
			share.setInputTime(shareTime);
			share.setUpdateTime(shareTime);
			share.setType(2);
			share.setIsDel(0);
			if(newShare.contains(share)){
				logger.error("分享日志重复 wliId:"+wliId+"\t"+ orgId+"\t"+shareUserId);
			}else{
				newShare.add(share);
			}
		}
		
		//删除取消的
		for (WorkLogShareBean share : dbShares) {
			if(!newShare.contains(share)){
				flag=true;
				share.setIsDel(1);
				share.setUpdateTime(new Date());
				
				shareMapper.updateTrends(share);
			}
		}
		
		//插入新增的
		for (WorkLogShareBean share : newShare) {
			if(!dbShares.contains(share)){
				flag=true;
				shareMapper.insert(share);
			}
		}
		//有更新菜去更新分享数
		if(flag){
			//更新分享数量
		/*	Map<String, Object> params=new HashMap<String, Object>(3);
			params.put("orgId", orgId);
			params.put("wliId", wliId);
			params.put("size", newShare.size());
			workLogInfoMapper.updateShareNum(params);*/
			
			
			WorkLogInfoBean update = new WorkLogInfoBean();
			update.setOrgId(orgId);
			update.setWliId(wliId);
			update.setShareNum(newShare.size());
			workLogInfoMapper.updateTrends(update);
		} 
		
		WorkLogInfoBean query = new WorkLogInfoBean();
		query.setOrgId(orgId);
		query.setWliId(wliId);
		WorkLogInfoBean worklog = workLogInfoService.getByCondtion(query );
		int row =worklog.getShareNum();
		return new ResultDTO("success", String.valueOf(row));
	}
}
