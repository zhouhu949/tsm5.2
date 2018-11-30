package com.qftx.tsm.cust.service;

import com.qftx.tsm.cust.bean.ResDetailCallBean;
import com.qftx.tsm.cust.dao.ResDetailCallMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResDetailCallService {
	private static final Logger logger = Logger.getLogger(ResDetailCallService.class);
	@Autowired
	private ResDetailCallMapper resDetailCallMapper;

	public ResDetailCallBean getByPrimaryKeyAndOrgId( String orgId, String tscidId) {
		return resDetailCallMapper.getByPrimaryKeyAndOrgId(orgId, tscidId);
	}

	public ResDetailCallBean getByDetailIdAndOrgId( String orgId, String tscidId) {
		return resDetailCallMapper.getByDetailIdAndOrgId(orgId, tscidId);
	}
	
	public void modify(ResDetailCallBean detailBean) {
		resDetailCallMapper.update(detailBean);
	}

	public void save(ResDetailCallBean detailBean) {
		resDetailCallMapper.insert(detailBean);
	}

	public List<ResDetailCallBean> getByCondtion(ResDetailCallBean entity) {
		return resDetailCallMapper.findByCondtion(entity);
	}
	
	public void remove(String orgId,String id) {
		resDetailCallMapper.deleteByOrgId(orgId,id);
	}
}
