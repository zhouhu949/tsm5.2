package com.qftx.tsm.callrecord.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.callrecord.dto.CustResInfoDto;
import com.qftx.tsm.cust.bean.ResCustInfoBean;

import java.util.List;
import java.util.Map;

public interface CallMapper extends BaseDao<ResCustInfoBean> {
	
	/** 邮件发送异步查询 企业 资源列表  */
	public List<CustResInfoDto> findComCustListPage(CustResInfoDto entity);
	
	public List<Map<String,Object>>findComCustDetails(Map<String,Object>map);
	
	/** 邮件发送异步查询 个人 资源列表  */
	public List<CustResInfoDto> findPerSonCustListPage(CustResInfoDto entity);
	
	/** 短信发送异步查询 资源列表  */
	public List<CustResInfoDto> findCustByCallSmsListPage(CustResInfoDto entity);
	
}
