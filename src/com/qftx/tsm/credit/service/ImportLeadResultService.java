package com.qftx.tsm.credit.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.common.util.StringUtils;
import com.qftx.tsm.credit.bean.ImportLeadResultBean;
import com.qftx.tsm.credit.dao.ImportLeadResultMapper;
import com.qftx.tsm.credit.dto.ImportLeadResultDto;

@Service
public class ImportLeadResultService {
	@Autowired ImportLeadResultMapper mapper;
	/* 查询*/
	public List<ImportLeadResultDto> query(String orgId,ImportLeadResultDto dto){
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		if("first".equals(dto.getType())){
			params.put("symbol", ">");
			params.put("orderKey", " t.inputtime asc ");
		}else{
			params.put("symbol", "< ");
			params.put("orderKey", " t.inputtime desc ");
		}
		if(StringUtils.isNotBlank(dto.getImpTime())){
			params.put("inputtime", dto.getImpTime());
		}
		if(StringUtils.isNotBlank(dto.getInputAcc())){
			params.put("inputAcc", dto.getInputAcc());
		}
		return mapper.query(params);
	}
	/* 插入*/
	public ImportLeadResultBean insert(String orgId,String fileId,int totalNum,int successNum,int failNum,Date startTime,Date endTime,ImportLeadResultBean bean){
		Date now = new Date();
		bean.setOrgId(orgId);
		bean.setFileId(fileId);
		bean.setTotalNum(totalNum);
		bean.setSuccessNum(successNum);
		bean.setFailNum(failNum);
		bean.setInputtime(now);
		bean.setUpdatetime(now);
		bean.setStartTime(startTime);
		bean.setEndTime(endTime);
		bean.setIsDel(0);
		bean.setStatus("0");		
		mapper.insert(bean);
		return bean;
	}
	
	/* 插入*/
	public ImportLeadResultBean update(String orgId,String fileId,int totalNum,int successNum,int failNum,Date startTime,Date endTime,ImportLeadResultBean bean){
		Date now = new Date();
		bean.setOrgId(orgId);
		bean.setFileId(fileId);
		bean.setTotalNum(totalNum);
		bean.setSuccessNum(successNum);
		bean.setFailNum(failNum);
		bean.setUpdatetime(now);
		bean.setStartTime(startTime);
		bean.setEndTime(endTime);
		bean.setIsDel(0);
		bean.setStatus("1");		
		mapper.updateTrends(bean);
		return bean;
	}
	
	public void modfiyTends(ImportLeadResultBean entity){
		mapper.updateTrends(entity);
	}
	
	// 根据导入批次ID 修改
	public void modfiyByFileId(ImportLeadResultBean entity){
		mapper.updateByFileId(entity);
	}
	
	public void saveTends(ImportLeadResultBean bean){
		mapper.insert(bean);
	}
	
	public ImportLeadResultBean findByCondtion(ImportLeadResultBean entity){
		List<ImportLeadResultBean> list = mapper.findByCondtion(entity);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
}
