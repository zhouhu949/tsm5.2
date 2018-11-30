package com.qftx.tsm.option.service;

import com.qftx.common.util.DateUtil;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.option.bean.OptionGroupBean;
import com.qftx.tsm.option.dao.OptionGroupMapper;
import com.qftx.tsm.option.dao.OptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class OptionGroupService {

	
	@Autowired
	private OptionGroupMapper optionGroupMapper;
	@Autowired
	private OptionMapper optionMapper;
	
	public void create(OptionGroupBean entity) {
		optionGroupMapper.insert(entity);
	}

	 
	public void createBatch(List<OptionGroupBean> entitys) {
		optionGroupMapper.insertBatch(entitys);
	}
	 
	public List<OptionGroupBean> getListByCondtion(OptionGroupBean entity) {
		return optionGroupMapper.findByCondtion(entity);
	}

	public OptionGroupBean getByCondtion(OptionGroupBean entity) {
		return optionGroupMapper.getByCondtion(entity);
	}
 
	public void modify(OptionGroupBean entity) {
		optionGroupMapper.update(entity);
	}
	 
	public void modifyTrends(OptionGroupBean entity) {
		optionGroupMapper.updateTrends(entity);
	}

	 
	public void modifyTrendsBatch(List<OptionGroupBean> entitys) {
		for (OptionGroupBean option : entitys) {
			optionGroupMapper.updateTrends(option);
		}
	}
	
	//判断排序是否唯一
	public Integer getSortByExists(OptionGroupBean entity){
		return optionGroupMapper.findSortByExists(entity);
	}
	
	public List<String>getOptionGroupNames(Map<String,Object>map){
			return optionGroupMapper.findOptionGroupNames(map);
	}	
	
	public void deleteByGroupId(String orgId,String itemCode,String groupId,String account){
		OptionGroupBean entity = new OptionGroupBean();
		entity.setOrgId(orgId);
		entity.setItemCode(itemCode);
		entity.setIsDefault(0);
		// 查询默认分组，如果为空需要新增
		OptionGroupBean bean = optionGroupMapper.getByCondtion(entity);
		if(bean == null){
			bean = new OptionGroupBean();
			bean.setGroupId(SysBaseModelUtil.getModelId());
			bean.setInputerAcc(account);
			bean.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
			bean.setIsDefault(0);
			bean.setSort(1);
			bean.setOrgId(orgId);
			bean.setItemCode(itemCode);
			optionGroupMapper.insert(bean);
		}
		// 修改数据项分组为默认分组。
		Map<String,Object>map1 = new HashMap<String, Object>();
		map1.put("orgId", orgId);
		map1.put("itemCode", itemCode);
		map1.put("oldGroupId", groupId);
		map1.put("newGroupId", bean.getGroupId());
		optionMapper.updateByGroupId(map1);
		
		// 删除分组
		Map<String,String>map = new HashMap<String, String>();
		map.put("orgId", orgId);
		map.put("groupId", groupId);
		optionGroupMapper.deleteByGroupId(map);
			
	}
	
	public void updateByIds(Map<String,Object>map){
		optionMapper.updateByIds(map);
	}
	
	public List<Map<String,Object>> getByExists(OptionGroupBean entity){
		return optionGroupMapper.findByExists(entity);
	}
}
