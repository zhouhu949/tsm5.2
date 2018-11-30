package com.qftx.tsm.report.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.tsm.report.bean.CustomReportShareBean;
import com.qftx.tsm.report.dao.CustomReportShareMapper;
@Service
public class CustomReportShareService {
	private static Logger logger = Logger.getLogger(CustomReportShareService.class);
	@Autowired
	private CustomReportShareMapper customReportShareMapper;

	public List<String> findReportIdsByShareAcc(CustomReportShareBean bean) {
		List<String> list = customReportShareMapper.findReportIdsByShareAcc(bean);
		return list;
	}

	public void create(CustomReportShareBean bean) {
		customReportShareMapper.insert(bean);
	}

	public List<String> getShareAccsByReportId(Map<String, String> map) {
		return customReportShareMapper.getShareAccsByReportId(map);
	}

	public void deleteShare(CustomReportShareBean share) {
		customReportShareMapper.deleteShare(share);
	}

	public void delete(CustomReportShareBean share) {
		customReportShareMapper.deleteByReportId(share);
	}

	public void deleteById(Map<String, Object> map) {
		customReportShareMapper.deleteById(map);
	}
	
}
