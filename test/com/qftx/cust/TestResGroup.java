package com.qftx.cust;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.auth.service.OrgService;
import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedUtil;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.dao.ResourceGroupMapper;
import com.qftx.tsm.cust.service.ComResourceGroupService;

public class TestResGroup extends BaseUtest {
	@Autowired
	private OrgService orgService;
	
	@Autowired
	private ResourceGroupMapper resourceGroupMapper;

	@Autowired
	private ComResourceGroupService comResourceGroupService;
	
	
	//标准版
	@Test
	public void initResGroup6000() {
		List<String>orgIds = new ArrayList<String>();
		orgIds = orgService.getAllOrgIdsByProductType("6000");
		if(orgIds != null && orgIds.size() >0){
			for(int i = 0; i<orgIds.size(); i++){
				main(orgIds.get(i));
				/*CachedUtil.getInstance().delete(orgIds.get(i) + CachedNames.SEPARATOR + CachedNames.RES_GROUP_LIST);
				CachedUtil.getInstance().delete(orgIds.get(i) + CachedNames.SEPARATOR + CachedNames.RES_GROUP_LIST1);
				CachedUtil.getInstance().delete(orgIds.get(i) + CachedNames.SEPARATOR + CachedNames.RES_GROUP_BEAN);
				CachedUtil.getInstance().delete(orgIds.get(i) + CachedNames.SEPARATOR + CachedNames.RES_GROUP_NAME);
				CachedUtil.getInstance().delete(orgIds.get(i) + CachedNames.SEPARATOR + CachedNames.RES_CLASS_LIST);*/
			}
		}
	}
	
	//快话
	@Test
	public void initResGroup5000() {
		List<String>orgIds = new ArrayList<String>();
		orgIds = orgService.getAllOrgIdsByProductType("5000");
		if(orgIds != null && orgIds.size() >0){
			for(int i = 0; i<orgIds.size(); i++){
				main(orgIds.get(i));
			}
		}
	}
	
	//专业版
	@Test
	public void initResGroup6001() {
		List<String>orgIds = new ArrayList<String>();
		orgIds = orgService.getAllOrgIdsByProductType("6001");
		if(orgIds != null && orgIds.size() >0){
			for(int i = 0; i<orgIds.size(); i++){
				main(orgIds.get(i));
			}
		}
	}
	
	public void main(String orgId) {
		comResourceGroupService.initResGroup(orgId);
	}
	//校验是否有遗漏单位
	@Test
	public void test1() {
		List<String>orgIds = new ArrayList<String>();
		orgIds = orgService.getAllOrgIdsByProductType("6001");
		int count = 0;
		if(orgIds != null && orgIds.size() >0){
			for(int i = 0; i<orgIds.size(); i++){
				ResourceGroupBean bean2 = new ResourceGroupBean();
				//bean2.setResGroupId(groupId);
				bean2.setGroupName("未分类");
				bean2.setOrgId(orgIds.get(i));
				bean2.setLevel(0);
				ResourceGroupBean bean3 = resourceGroupMapper.findUnClassById(bean2);
				if (bean3 == null) {
					count += 1;
				}
			}
		}
		System.out.println(count);
	}
}
