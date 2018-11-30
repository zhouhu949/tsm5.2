package com.qftx.tsm.imp.service;

import com.qftx.common.util.StringUtils;
import com.qftx.tsm.imp.bean.ImportResResultBean;
import com.qftx.tsm.imp.dao.ImportResResultMapper;
import com.qftx.tsm.imp.dto.ImportResResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImportResResultService {
	@Autowired ImportResResultMapper mapper;
	/* 查询*/
	public List<ImportResResultDto> query(String orgId,ImportResResultDto dto){
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
	public ImportResResultBean insert(String orgId,String fileId,int totalNum,int successNum,int failNum,Date startTime,Date endTime,ImportResResultBean bean){
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
	public ImportResResultBean update(String orgId,String fileId,int totalNum,int successNum,int failNum,Date startTime,Date endTime,ImportResResultBean bean){
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
	
	public void modfiyTends(ImportResResultBean entity){
		mapper.updateTrends(entity);
	}
	
	// 根据导入批次ID 修改
	public void modfiyByFileId(ImportResResultBean entity){
		mapper.updateByFileId(entity);
	}
	
	public void saveTends(ImportResResultBean bean){
		mapper.insert(bean);
	}
	
	public ImportResResultBean findByCondtion(ImportResResultBean entity){
		List<ImportResResultBean> list = mapper.findByCondtion(entity);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
}
