package com.qftx.tsm.report.service;

import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.report.bean.TsmCallReportLogBean;
import com.qftx.tsm.report.dao.TsmCallReportLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TsmCallReportLogService {
	@Autowired TsmCallReportLogMapper mapper;
	/* 查询*/
	public List<TsmCallReportLogBean> query(String orgId){
		Map<String , String> params = new HashMap<String, String>();
		params.put("orgId", orgId);
		return mapper.query(params);
	}
	/* 插入*/
	public TsmCallReportLogBean insert(String orgId){
		TsmCallReportLogBean bean = new TsmCallReportLogBean();
		String id=GuidUtil.getGuid();
		bean.setId(id);
		bean.setOrgId(orgId);
		
		
		mapper.insert(bean);
		return bean;
	}
}
