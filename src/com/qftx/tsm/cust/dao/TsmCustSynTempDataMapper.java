package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.TsmCustSynTempDataBean;

import java.util.Map;

public interface TsmCustSynTempDataMapper extends BaseDao<TsmCustSynTempDataBean> {

	// 删除
	public void deleteByIds(Map<String,Object>map);
}
