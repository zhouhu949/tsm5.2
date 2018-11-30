package com.qftx.tsm.sys.dao;

import java.util.List;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sys.bean.SysDataHelpBean;


public interface SysDataHelpMapper extends BaseDao<SysDataHelpBean> {

	public String getDataUrlByCode(String dataModule);
	
	public List<SysDataHelpBean>getList();
}
