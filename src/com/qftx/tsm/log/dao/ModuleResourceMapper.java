package com.qftx.tsm.log.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.log.bean.ModuleResourceBean;
import com.qftx.tsm.log.dto.DtoPModuleResourceBean;

import java.util.List;
import java.util.Map;

public interface  ModuleResourceMapper extends BaseDao<DtoPModuleResourceBean>{
	
	public List<ModuleResourceBean> findModuleReslist ();
	public List<ModuleResourceBean> findModuleReslistByPid (String pid);
	public List<ModuleResourceBean> findModuleRes (Map<String,String> map);

}
