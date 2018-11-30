package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.CustLabelCodeDataBean;

import java.util.List;
import java.util.Map;

public interface CustLabelCodeDataMapper extends BaseDao<CustLabelCodeDataBean> {
	
	public List<String> findActionIdsByLabelCodeData(Map<String, Object> map);
	
	public void deleteByFieldCode(Map<String,Object>map);
	
	public void deleteByFieldCodes(Map<String,Object>map);
}
