package com.qftx.tsm.credit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.credit.bean.ImportLeadResultDetailsBean;
import com.qftx.tsm.credit.dao.ImportLeadResultDetailsMapper;

@Service
public class ImportLeadResultDetailsService {
	@Autowired ImportLeadResultDetailsMapper mapper;
	/* 查询*/
	public List<ImportLeadResultDetailsBean> query(String orgId){
		Map<String , String> params = new HashMap<String, String>();
		params.put("orgId", orgId);
		return mapper.query(params);
	}
	/* 插入*/
	public ImportLeadResultDetailsBean insert(String orgId){
		ImportLeadResultDetailsBean bean = new ImportLeadResultDetailsBean();
		String id=GuidUtil.getGuid();
		bean.setId(id);
		bean.setOrgId(orgId);
		
		mapper.insert(bean);
		return bean;
	}
	
	// 批量插入
	public void insertBatch(List<ImportLeadResultDetailsBean>list){
		mapper.insertBatch(list);
	}
	
	// 批量删除
	public void deleteByBatch(String orgId,List<String> list){
		mapper.deleteByBatch(orgId, list);
	}
	
}
