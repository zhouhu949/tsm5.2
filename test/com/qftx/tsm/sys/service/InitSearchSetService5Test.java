package com.qftx.tsm.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.auth.service.OrgService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.sys.dao.CustSearchSetMapper1;
import com.qftx.tsm.plan.user.day.service.TestValue;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.bean.SysFileBean;
import com.qftx.tsm.sys.dto.HighSearchChildDto;
import com.qftx.tsm.sys.dto.HighSearchDto;
import com.qftx.tsm.sys.enums.SysEnum;

/**
 * 客户跟进，我的客户（全部）新增 客户来源字段。
 * @author Administrator
 *
 */
public class InitSearchSetService5Test extends BaseUtest{
	@Autowired 
	private OrgService orgService;
	@Autowired 
	private CustSearchSetMapper1 custSearchSetMapper1;
	@Autowired 
	private UserService userService;
	@Test
	public void main(){
		List<String>orgIds = new ArrayList<String>();
		orgIds = orgService.getAllOrgIdsByProductType("6001");
		if(orgIds != null && orgIds.size() >0){
			for(int i = 0; i<orgIds.size(); i++){
				 try {
					execute(orgIds.get(i),"7"); // 1 ,4,5,6,7
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
	}
	
	
	public void execute(String orgId,String searchModule) throws Exception{
		// 查询是否启用
		CustSearchSet entity = new CustSearchSet();
		entity.setOrgId(orgId);
		entity.setDevelopCode("custSource");
		entity.setSearchModule(searchModule);
		List<CustSearchSet> list = custSearchSetMapper1.findByCondtion(entity);
		if(!(list!=null && list.size()>0)){
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			map.put("searchModule", searchModule);
			map.put("developCode", "ownerAcc");
			Integer sort = custSearchSetMapper1.findSortByDevelopCode(map);
			updateIntervalSort(sort,orgId,searchModule);
			insertSource(sort,orgId,searchModule);
			// 删除高级查询缓存			
//			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"0"+CachedNames.HIGH_SEARCH_+searchModule);
//			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"1"+CachedNames.HIGH_SEARCH_+searchModule);		
//			List<String> accounts = userService.getUserAccounts(orgId);
//			for(String account : accounts){
//				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+searchModule);
//			}
//			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.SEARCH_SET);
		}
			
	}
	
	// 区间排序做修改
	private void updateIntervalSort(Integer sort,String orgId,String searchModule)throws Exception{				
			
			Map<String,Object>map1 = new HashMap<String, Object>();
			map1.put("orgId", orgId);
			map1.put("minSort", sort);
			map1.put("searchModule", searchModule);			
			List<CustSearchSet> list1 = custSearchSetMapper1.findAllBySortInterval(map1);
			
			if(list1!=null && list1.size()>0){
				List<CustSearchSet>newList = new ArrayList<CustSearchSet>();
				for(CustSearchSet searchSet : list1){
					CustSearchSet newSearchSet = new CustSearchSet();
					newSearchSet.setSort((short) (searchSet.getSort() + 1));	
					newSearchSet.setOrgId(searchSet.getOrgId());
					newSearchSet.setId(searchSet.getId());
					newSearchSet.setModifydate(new Date());
					newSearchSet.setModifierAcc("system");
					newList.add(newSearchSet);
				}
				custSearchSetMapper1.batchUpdate(newList);
			}
		}

	private void insertSource(Integer sort,String orgId,String searchModule){
		CustSearchSet newSearchSet = new CustSearchSet();
		newSearchSet.setSort(Short.valueOf(sort.toString()));	
		newSearchSet.setOrgId(orgId);
		newSearchSet.setId(SysBaseModelUtil.getModelId());
		newSearchSet.setModifydate(new Date());
		newSearchSet.setModifierAcc("system");
		newSearchSet.setInputdate(new Date());
		newSearchSet.setInputerAcc("system");
		newSearchSet.setEnable(1);
		newSearchSet.setSearchModule(searchModule);
		newSearchSet.setSearchName("客户来源");
		newSearchSet.setIsQuery((short)0);
		newSearchSet.setSearchCode("source");
		newSearchSet.setDevelopCode("custSource");
		newSearchSet.setIsShow((short)1);
		newSearchSet.setIsDisabled(1);
		newSearchSet.setState(2);
		newSearchSet.setDataType(3);
		newSearchSet.setIsFiledSet(0);
		custSearchSetMapper1.insert(newSearchSet);
	}
}
