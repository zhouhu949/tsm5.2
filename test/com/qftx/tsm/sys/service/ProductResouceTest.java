package com.qftx.tsm.sys.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.auth.bean.AuthProductResource;
import com.qftx.base.auth.bean.RoleResource;
import com.qftx.base.auth.service.AuthProductReousrceService;
import com.qftx.base.auth.service.RoleResourceService;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.SysBaseModelUtil;

public class ProductResouceTest extends BaseUtest{
	@Autowired 
	private AuthProductReousrceService authProductReousrceService;
	@Autowired 
	private RoleResourceService roleResourceService;
	
	@Test
	public void main(){
		Map<String,Object>map = new HashMap<String,Object>();
		map.put("proCode", "hyxzy");
		List<AuthProductResource> resources = authProductReousrceService.getButtons(map);
		if(resources != null && resources.size() >0){
			map.clear();
			List<String>childIds = null;
			for(AuthProductResource apr : resources){				
				if(map.get(apr.getParentId())!=null){
					childIds = (List<String>) map.get(apr.getParentId());
					childIds.add(apr.getResourceId());
					map.put(apr.getParentId(), childIds);
				}else{
					childIds = new ArrayList<String>();
					childIds.add(apr.getResourceId());
					map.put(apr.getParentId(), childIds);
				}				
			}
		}
		
		execute(null,map);
		
	}
	
	public void execute(String orgId,Map<String,Object>map){
		RoleResource entity = new RoleResource();
		entity.setOrgId(orgId);
		List<RoleResource> resoures  = new ArrayList<RoleResource>();
		if(orgId == null){
			resoures =roleResourceService.getOldRoleResourceList();
		}else{
			resoures = roleResourceService.getListByCondtion(entity);
		}
		
		if(resoures!=null && resoures.size() >0){
			List<RoleResource>list = new ArrayList<RoleResource>();
			
			Map<String,String>roleMap = new HashMap<String, String>();
			for(RoleResource roleResource : resoures){
				roleMap.put(roleResource.getRoleId(), roleResource.getRoleId());
				if(map.get(roleResource.getResourceId()) != null){
					for(String resourceId : (List<String>) map.get(roleResource.getResourceId())){
						RoleResource rr = new RoleResource();
						rr.setId(SysBaseModelUtil.getModelId());
						rr.setResourceId(resourceId);
						rr.setOrgId(orgId);
						rr.setRoleId(roleResource.getRoleId());
						list.add(rr);
					}				
				}		
			}
			
			if(roleMap !=null && roleMap.size() >0){
				for (Map.Entry<String, String> entry : roleMap.entrySet()) {  
					RoleResource rr = new RoleResource();
					rr.setId(SysBaseModelUtil.getModelId());
					rr.setResourceId("base_followCust");
					rr.setOrgId(orgId);
					rr.setRoleId(entry.getKey());
					list.add(rr);
					rr = new RoleResource();
					rr.setId(SysBaseModelUtil.getModelId());
					rr.setResourceId("base_putIntoHighSeas");
					rr.setOrgId(orgId);
					rr.setRoleId(entry.getKey());
					list.add(rr);
					rr = new RoleResource();
					rr.setId(SysBaseModelUtil.getModelId());
					rr.setResourceId("base_signCust");
					rr.setOrgId(orgId);
					rr.setRoleId(entry.getKey());
					list.add(rr);
					rr = new RoleResource();
					rr.setId(SysBaseModelUtil.getModelId());
					rr.setResourceId("base_custEdit");
					rr.setOrgId(orgId);
					rr.setRoleId(entry.getKey());
					list.add(rr);
				} 
			}
			
			if(list !=null && list.size() >0){
				List<List<RoleResource>> lists = splitList(list, 500);
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
