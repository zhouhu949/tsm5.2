package com.qftx.tsm.imp.service;

import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.imp.bean.ImportResResultDetailsBean;
import com.qftx.tsm.imp.dao.ImportResResultDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImportResResultDetailsService {
	@Autowired ImportResResultDetailsMapper mapper;
	/* 查询*/
	public List<ImportResResultDetailsBean> query(String orgId){
		Map<String , String> params = new HashMap<String, String>();
		params.put("orgId", orgId);
		return mapper.query(params);
	}
	/* 插入*/
	public ImportResResultDetailsBean insert(String orgId){
		ImportResResultDetailsBean bean = new ImportResResultDetailsBean();
		String id=GuidUtil.getGuid();
		bean.setId(id);
		bean.setOrgId(orgId);
		
		mapper.insert(bean);
		return bean;
	}
	
	// 批量插入
	public void insertBatch(List<ImportResResultDetailsBean>list){
		mapper.insertBatch(list);
	}
	
	// 批量删除
	public void deleteByBatch(String orgId,List<String> list){
		mapper.deleteByBatch(orgId, list);
	}
	
}
