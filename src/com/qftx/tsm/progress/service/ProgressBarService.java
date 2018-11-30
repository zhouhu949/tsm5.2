package com.qftx.tsm.progress.service;

import com.qftx.common.cached.CachedService;
import com.qftx.tsm.progress.dto.ProgressBarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
@Service
public class ProgressBarService {
	@Autowired
	private CachedService cachedService;
	/***
	 * 启动任务
	 *
	 * @param orgId
	 * @param account
	 * @param type 任务类型
	 * @history
	 */
	/*public String startTask(String orgId,String account,String type){
		String id=GuidUtil.getId();
		ProgressBarDTO dto = new ProgressBarDTO();
		dto.setOrgId(orgId);
		dto.setType(type);
		dto.setAccount(account);
		dto.setId(id);
		return id;
	}*/
	
	/*更新进度*/
	public void insertProgress(String orgId,String account,String type,String id,int total){
		ProgressBarDTO dto = new ProgressBarDTO();
		dto.setAccount(account);
		dto.setId(id);
		dto.setOrgId(orgId);
		dto.setType(type);
		dto.setStartTime(new Date());
		dto.setTotal(total);
		dto.setCurrent(0);
		cachedService.setProgessBar(dto);
	}
	
	/*更新进度*/
	public void updateProgress(String orgId,String account,String id,String type,int current){
		ProgressBarDTO dto = new ProgressBarDTO();
		dto.setOrgId(orgId);
		dto.setAccount(account);
		dto.setType(type);
		dto.setId(id);
		dto.setCurrent(current);
		cachedService.setProgessBar(dto);
	}
	
	/*查询进度*/
	public Map<String, ProgressBarDTO> queryProgress(String orgId,String account,String type){
		return cachedService.getProgessBar(orgId, account, type);
	}
	
}
