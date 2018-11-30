package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.bean.ResourceCountBean;

import java.util.List;

public interface ResourceCountMapper extends BaseDao<ResourceCountBean> {
	
	public List<ResourceCountBean> selectResource(ResourceCountBean bean);

}
