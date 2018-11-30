package com.qftx.tsm.cust.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.CustDefinedDataBean;
import com.qftx.tsm.cust.bean.LinkmanDefinedDataBean;

public interface LinkmanDefinedDataMapper extends BaseDao<LinkmanDefinedDataBean>{
	
	public void deleteBylinkmanId(@Param("orgId")String orgId,@Param("custId")String custId,@Param("linkmanId")String linkmanId);

	public List<LinkmanDefinedDataBean> findLinkmanDefinedShowDatas(Map<String, Object> map);
}