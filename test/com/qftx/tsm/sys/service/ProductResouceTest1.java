package com.qftx.tsm.sys.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.auth.bean.AuthProductResource;
import com.qftx.base.auth.bean.Role;
import com.qftx.base.auth.bean.RoleResource;
import com.qftx.base.auth.dao.RoleMapper;
import com.qftx.base.auth.service.AuthProductReousrceService;
import com.qftx.base.auth.service.OrgService;
import com.qftx.base.auth.service.RoleResourceService;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;

/** 
 * 新增的菜单与角色关联
 */
public class ProductResouceTest1 extends BaseUtest{
	@Autowired 
	private OrgService orgService;
	@Autowired 
	private RoleResourceService roleResourceService;
	@Autowired 
	private RoleMapper roleMapper;
	@Test
	public void main(){
		List<String>orgIds = new ArrayList<String>();
		orgIds = orgService.getAllOrgIdsByProductType("6001");
		if(orgIds != null && orgIds.size() >0){
			for(int i = 0; i<orgIds.size(); i++){
					// execute(orgIds.get(i));
			}
		}
		execute(null);
		
	}
	
	public void execute(String orgId){
		List<Role> oldRoleList = roleMapper.getOldRoleList();//角色数据
		List<RoleResource> roleresourcelist = new ArrayList<RoleResource>();
		for (Role role : oldRoleList) {			
    			// 初始化角色
    			if(AppConstant.ROLE_A.equals(role.getRoleCode())){
    				RoleResource rr = new RoleResource();
					rr.setId(SysBaseModelUtil.getModelId());
					rr.setResourceId("61cd1c17ec3346bdb0e6162a4c5a333");
					rr.setOrgId(orgId);
					rr.setRoleId(AppConstant.ROLE_A);
					roleresourcelist.add(rr);	
					rr = new RoleResource();
					rr.setId(SysBaseModelUtil.getModelId());
					rr.setResourceId("44473c1ca43d40149b2383523507b511");
					rr.setOrgId(orgId);
					rr.setRoleId(AppConstant.ROLE_A);
					roleresourcelist.add(rr);	
					rr = new RoleResource();
					rr.setId(SysBaseModelUtil.getModelId());
					rr.setResourceId("44473c1ca43d40149b23835235071522");
					rr.setOrgId(orgId);
					rr.setRoleId(AppConstant.ROLE_A);
					roleresourcelist.add(rr);	
    			}
    			if(AppConstant.ROLE_AC.equals(role.getRoleCode()) || AppConstant.ROLE_AD.equals(role.getRoleCode())
    					||AppConstant.ROLE_AE.equals(role.getRoleCode()) ||AppConstant.ROLE_AF.equals(role.getRoleCode())){
    				RoleResource rr = new RoleResource();
					rr.setId(SysBaseModelUtil.getModelId());
					rr.setResourceId("6803e57b795d45379c788beacf79356d");
					rr.setOrgId(orgId);
					rr.setRoleId(role.getRoleCode());
					roleresourcelist.add(rr);	
					rr = new RoleResource();
					rr.setId(SysBaseModelUtil.getModelId());
					rr.setResourceId("61cd1c17ec3346bdb0e6162a4c5b112");
					rr.setOrgId(orgId);
					rr.setRoleId(role.getRoleCode());
					roleresourcelist.add(rr);	
    			}
    			
			if(roleresourcelist !=null && roleresourcelist.size() >0){
				List<List<RoleResource>> lists = splitList(roleresourcelist, 500);
				for (List<RoleResource> ids : lists) {
					roleResourceService.createBatch(ids);
				}
				
			}
			
		}
	}
	
	public  <T> List<List<T>> splitList(List<T> data, int groupCount) {
		List<List<T>> lists = new ArrayList<List<T>>();	
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < data.size(); i++) {
			list.add(data.get(i));
			if ((i + 1) % groupCount == 0) {
				lists.add(list);
				list = new ArrayList<T>();
			}
		}
		if (list.size() != 0) {
			lists.add(list);
		}		
		return lists;
	}
}
