package com.qftx.tsm.report.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.tsm.report.bean.CustomReportBean;
import com.qftx.tsm.report.bean.CustomReportShareBean;
import com.qftx.tsm.report.dao.CustomReportMapper;

/** 
* 自定义报表
* @author: zjh
* @history:
*/
@Service
public class CustomReportService {
	private static Logger logger = Logger.getLogger(CustomReportService.class);
	@Autowired
	private CustomReportMapper customReportMapper;
	
	public void create(CustomReportBean bean) {
		customReportMapper.insert(bean);
	}

	public CustomReportBean getByCondtion(CustomReportBean bean) {
		return customReportMapper.getByCondtion(bean);
	}

	public void update(CustomReportBean bean) {
		customReportMapper.update(bean);
	}

	public void delete(CustomReportBean bean) {
		customReportMapper.update(bean);
	}

	public List<CustomReportBean> findByCondtion(CustomReportBean bean) {
		return customReportMapper.findByCondtion(bean);
	}

	/*public List<CustomReportBean> findByIds(Map<String, Object> map) {
		return customReportMapper.findByIds(map);
	}*/

	public List<CustomReportBean> findShareToMeCustomReportListPage(CustomReportShareBean bean) {
		return customReportMapper.findShareToMeCustomReportListPage(bean);
	}
	
	
}
