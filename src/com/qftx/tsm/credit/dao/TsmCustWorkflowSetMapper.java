package com.qftx.tsm.credit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qftx.tsm.credit.bean.TsmCustWorkflowSetBean;


public interface TsmCustWorkflowSetMapper {
	
	public List<TsmCustWorkflowSetBean> find();

	public List<TsmCustWorkflowSetBean> findByCondtion(TsmCustWorkflowSetBean entity);
	
	public TsmCustWorkflowSetBean getByCondtion(TsmCustWorkflowSetBean entity);
	
	public void insert(TsmCustWorkflowSetBean entity);

	public void insertBatch(List<TsmCustWorkflowSetBean> entitys);

	public void update(TsmCustWorkflowSetBean entity);
	
	public void updateWorkset(TsmCustWorkflowSetBean entity);
	
	public TsmCustWorkflowSetBean getByPrimaryKey(@Param("orgId") String orgId, @Param("workflowId") String auditId);

	public TsmCustWorkflowSetBean getByOrgId(@Param("orgId") String orgId);
	
}
