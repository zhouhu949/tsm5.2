package com.qftx.tsm.workAll.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.workAll.bean.WorkAllIndexBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkAllIndexMapper extends BaseDao<WorkAllIndexBean>{

	public List<WorkAllIndexBean> getAllWorkByPage(WorkAllIndexBean entity);
	
}