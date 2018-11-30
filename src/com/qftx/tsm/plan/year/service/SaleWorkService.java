package com.qftx.tsm.plan.year.service;

import com.qftx.tsm.plan.year.bean.SaleWorkBean;
import com.qftx.tsm.plan.year.dao.SaleWorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleWorkService {
	@Autowired
	private SaleWorkMapper salWorkMapper;
	
	/* 查询全年计划*/
	public List<SaleWorkBean>  querySaleWorks(String orgId){
		Map<String, String> params =  new HashMap<String, String>(2);
		params.put("orgId", orgId);
		
		return salWorkMapper.querySaleWorks(params);
	}
	
}
