package com.qftx.tsm.workSign.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.workSign.bean.WorkSignInfoBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WorkSignInfoMapper extends BaseDao<WorkSignInfoBean> {

	public List<WorkSignInfoBean> findByPage(WorkSignInfoBean entity);
	//更新评论数
	public void updateNum(Map<String, Object> params);
	
	public List<WorkSignInfoBean> getListByCondtion(WorkSignInfoBean entity);
	
	public List<WorkSignInfoBean> findWorkSignInfos(Map<String, Object> map);
}