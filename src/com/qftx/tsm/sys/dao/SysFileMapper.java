package com.qftx.tsm.sys.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sys.bean.SysFileBean;

import java.util.Map;

public interface SysFileMapper extends BaseDao<SysFileBean> {

	public SysFileBean getByOrgIdPrimaryKey(Map<String,Object>map);
}
