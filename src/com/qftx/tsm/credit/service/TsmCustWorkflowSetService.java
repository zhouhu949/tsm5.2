package com.qftx.tsm.credit.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.credit.dao.TsmCustWorkflowSetMapper;
import com.qftx.tsm.credit.bean.TsmCustWorkflowSetBean;

@Service
public class TsmCustWorkflowSetService{
	
	@Resource
	TsmCustWorkflowSetMapper tsmCustWorkflowSetMapper;
	
	public List<TsmCustWorkflowSetBean> find(){
		return tsmCustWorkflowSetMapper.find();
	}

	public List<TsmCustWorkflowSetBean> findByCondtion(TsmCustWorkflowSetBean entity){
		return tsmCustWorkflowSetMapper.findByCondtion(entity);
	}
	

	
	public TsmCustWorkflowSetBean getByCondtion(TsmCustWorkflowSetBean entity){
		return tsmCustWorkflowSetMapper.getByCondtion(entity);
	}

	public void insert(TsmCustWorkflowSetBean entity,ShiroUser user){
		entity.setWorkflowId(SysBaseModelUtil.getModelId());
		entity.setOrgId(user.getOrgId());
		entity.setInputerAcc(user.getAccount());
		entity.setInputtime(new Date());
		tsmCustWorkflowSetMapper.insert(entity);
	}

	public void insertBatch(List<TsmCustWorkflowSetBean> entitys,ShiroUser user){
		for (TsmCustWorkflowSetBean entity : entitys) {
			entity.setWorkflowId(SysBaseModelUtil.getModelId());
			entity.setOrgId(user.getOrgId());
			entity.setInputerAcc(user.getAccount());
			entity.setInputtime(new Date());
		}
		tsmCustWorkflowSetMapper.insertBatch(entitys);
	}

	public void update(TsmCustWorkflowSetBean entity,ShiroUser user){
		entity.setOrgId(user.getOrgId());
		tsmCustWorkflowSetMapper.update(entity);
	}
	
	
	public void updateWorkset(TsmCustWorkflowSetBean entity,ShiroUser user){
		entity.setOrgId(user.getOrgId());
		tsmCustWorkflowSetMapper.updateWorkset(entity);
	}
	
	public void insertDzsj(String orgId){
		TsmCustWorkflowSetBean entity=new TsmCustWorkflowSetBean();
		entity.setWorkflowId(SysBaseModelUtil.getModelId());
		entity.setWorkflowName("放款确认");
		entity.setOrgId(orgId);
		entity.setInputerAcc("admin");
		entity.setInputtime(new Date());
		entity.setType(0);
		entity.setAuditNum(1);
		tsmCustWorkflowSetMapper.insert(entity);
	}
	
	
	public static void main(String[] args) {
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());
		System.out.println(SysBaseModelUtil.getModelId());

	}
}
