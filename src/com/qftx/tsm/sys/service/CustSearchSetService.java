package com.qftx.tsm.sys.service;

import com.qftx.common.cached.CachedService;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.dao.CustSearchSetMapper;
import com.qftx.tsm.sys.dto.SearchSetSortDto;
import com.qftx.tsm.sys.enums.SysEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CustSearchSetService{
	
	@Autowired
	private CustSearchSetMapper custSearchSetMapper;

	@Autowired
	private CachedService cachedService; 
	
	public List<CustSearchSet> getList() {
		return custSearchSetMapper.find();
	}

	 
	public List<CustSearchSet> getListByCondtion(CustSearchSet entity) {
		return custSearchSetMapper.findByCondtion(entity);
	}

	public CustSearchSet getByCondtion(CustSearchSet entity) {
		return custSearchSetMapper.getByCondtion(entity);
	}

	public List<CustSearchSet> getListPage(CustSearchSet entity) {
		return custSearchSetMapper.findListPage(entity);
	}

	 
	public CustSearchSet getByPrimaryKey(String id) {
		return custSearchSetMapper.getByPrimaryKey(id);
	}

	 
	public void create(CustSearchSet entity) {
		custSearchSetMapper.insert(entity);
	}

	 
	public void createBatch(List<CustSearchSet> entitys) {
		custSearchSetMapper.insertBatch(entitys);
	}


	public void modifyTrends(CustSearchSet entity) {
		custSearchSetMapper.updateTrends(entity);
	}

	 
	public void modifyBatch(List<CustSearchSet> entitys) {
		for (CustSearchSet custSearchSet : entitys) {
			custSearchSetMapper.updateTrends(custSearchSet);
		}
	}

	 
	public void modifyTrendsBatch(List<CustSearchSet> entitys) {
		for (CustSearchSet custSearchSet : entitys) {
			custSearchSetMapper.updateTrends(custSearchSet);
		}
	}
	 
	public void remove(String id) {
		custSearchSetMapper.delete(id);
	}

	public void removeBatch(List<String> ids) {
		custSearchSetMapper.deleteBatch(ids);
	}
	
	public void batchUpdate(List<CustSearchSet> entitys){
		custSearchSetMapper.batchUpdate(entitys);
	}
	
	/** 新增自定义查询字段 */
	public void insertByFiled(CustFieldSet fieldSet) throws Exception{
		// 查询是否已存在，存在则不做处理
		CustSearchSet entity = new CustSearchSet();
		entity.setOrgId(fieldSet.getOrgId());
		entity.setDevelopCode(fieldSet.getFieldCode());
		List<CustSearchSet> list = custSearchSetMapper.findByCondtion(entity);
		if(!(list!=null && list.size()>0)){
			Map<String,Object>map =new HashMap<String, Object>();
			map.put("orgId", fieldSet.getOrgId());
			List<SearchSetSortDto> dtos =custSearchSetMapper.findMaxSortByModuleGroup(map);
			List<SearchSetSortDto> queryCounts =custSearchSetMapper.findQueryCountByModuleGroup(map);
			Map<String,Short>queryCountMap = new HashMap<String, Short>();
			if(queryCounts!=null && queryCounts.size() >0){
				queryCountMap.clear();
				for(SearchSetSortDto dto : queryCounts){
					queryCountMap.put(dto.getModuleCode(), dto.getQueryCount());
				}
			}
			if(dtos!=null && dtos.size() >0){
				map.clear();
				for(SearchSetSortDto dto : dtos){
					map.put(dto.getModuleCode(), dto.getSort()+1);
				}
			}
			list.clear();
			for(SysEnum s : SysEnum.getDefinedSearchModules()){
				CustSearchSet searchSet = new CustSearchSet();
				searchSet.setId(SysBaseModelUtil.getModelId());
				searchSet.setEnable(1);
				searchSet.setSearchModule(s.getState());
				searchSet.setSearchName(fieldSet.getFieldName());
				//searchSet.setIsQuery((short) (fieldSet.getIsQuery() == 0 ? 3 : queryCountMap.get(s.getState())==null || queryCountMap.get(s.getState()) <= 9 ? 1 : 0));
				searchSet.setIsQuery((short) 0);
				searchSet.setSort(Short.valueOf((map.get(s.getState())==null ? 2 : map.get(s.getState())).toString()));
				searchSet.setInputerAcc(fieldSet.getInputerAcc());
				searchSet.setInputdate(new Date());
				searchSet.setOrgId(fieldSet.getOrgId());
				if(fieldSet.getDataType() != null && fieldSet.getDataType() == 2){ // 日期类型
					searchSet.setSearchCode("start"+fieldSet.getFieldCode());
					searchSet.setListCode("show"+fieldSet.getFieldCode());
				}else{ 
					searchSet.setSearchCode(fieldSet.getFieldCode());
					searchSet.setListCode(fieldSet.getFieldCode());
				}				
				searchSet.setDevelopCode(fieldSet.getFieldCode());				
				searchSet.setIsShow((short) 0);
				
				searchSet.setIsDisabled(1);
				searchSet.setDataType(fieldSet.getDataType());
				searchSet.setState(2);
				if(fieldSet.getFieldCode().contains("defined")){
					searchSet.setIsFiledSet(1);
				}else{
					searchSet.setIsFiledSet(0);
				}
				list.add(searchSet);
			}
			if(list!=null && list.size() > 0){
				custSearchSetMapper.insertBatch(list);
			}
		}		
	}
	
	/**
	 * 修改自定义查询字段
	 * @param fieldSet
	 */
	public void updateByFieldSet(CustFieldSet fieldSet)throws Exception{
		// 查询是否已存在，存在则不做处理
		CustSearchSet entity = new CustSearchSet();
		entity.setOrgId(fieldSet.getOrgId());
		entity.setDevelopCode(fieldSet.getFieldCode());
		List<CustSearchSet> list = custSearchSetMapper.findByCondtion(entity);
		if(!(list!=null && list.size()>0)){ // 不存在
			// 判断字段是否启用以及是否是查询项
			if(fieldSet.getEnable() != null && fieldSet.getEnable() == 1){
				fieldSet.setInputerAcc(fieldSet.getModifierAcc());
				insertByFiled(fieldSet);
			}			
		}else{ // 存在
			// 判断字段是否停用以及是否取消查询项勾选
			if(fieldSet.getEnable() == null || fieldSet.getEnable() == 0){
				Map<String,Object>map =new HashMap<String, Object>();
				map.put("orgId", fieldSet.getOrgId());
				map.put("developCode", fieldSet.getFieldCode());
				List<SearchSetSortDto> dtos =custSearchSetMapper.findMaxSortByModuleGroup(map);
				if(dtos!=null && dtos.size() >0){
					map.clear();
					for(SearchSetSortDto dto : dtos){
						map.put(dto.getModuleCode(), dto.getSort());
					}
				}
				// 删除
				Map<String,Object>map1 = new HashMap<String, Object>();
				map1.put("orgId", fieldSet.getOrgId());
				map1.put("developCode", fieldSet.getFieldCode());
				custSearchSetMapper.deleteByDevelopCode(map1);
				
				// 区间排序做修改
				list.clear();
				for(SysEnum s : SysEnum.getDefinedSearchModules()){
					map1.clear();
					map1.put("orgId", fieldSet.getOrgId());
					map1.put("minSort", map.get(s.getState()));
					map1.put("searchModule", s.getState());
					List<CustSearchSet> list1 = custSearchSetMapper.findAllBySortInterval(map1);
					list.addAll(list1);
				}
				
				if(list!=null && list.size()>0){
					List<CustSearchSet>newList = new ArrayList<CustSearchSet>();
					for(CustSearchSet searchSet : list){
						CustSearchSet newSearchSet = new CustSearchSet();
						newSearchSet.setId(searchSet.getId());
						newSearchSet.setOrgId(searchSet.getOrgId());
						newSearchSet.setSort((short) (searchSet.getSort() - 1));
						newSearchSet.setSearchName(searchSet.getSearchName());
						newSearchSet.setModifydate(new Date());
						newSearchSet.setModifierAcc(fieldSet.getModifierAcc());
						newList.add(newSearchSet);
					}
					custSearchSetMapper.batchUpdate(newList);
				}
			}else{
				if(list!=null && list.size()>0){
					Map<String,Object>map =new HashMap<String, Object>();
					map.put("orgId", fieldSet.getOrgId());	
					//map.put("developCode", fieldSet.getFieldCode());
					List<SearchSetSortDto> queryCounts =custSearchSetMapper.findQueryCountByModuleGroup(map);
					Map<String,Short>queryCountMap = new HashMap<String, Short>();
					if(queryCounts!=null && queryCounts.size() >0){
						queryCountMap.clear();
						for(SearchSetSortDto dto : queryCounts){
							queryCountMap.put(dto.getModuleCode(), dto.getQueryCount());
						}
					}
					List<CustSearchSet>newList = new ArrayList<CustSearchSet>();
					for(CustSearchSet searchSet : list){
						CustSearchSet newSearchSet = new CustSearchSet();
						newSearchSet.setId(searchSet.getId());
						newSearchSet.setOrgId(searchSet.getOrgId());
//						if(fieldSet.getIsQuery() != searchSet.getIsQuery()){
//							newSearchSet.setIsQuery((short) (fieldSet.getIsQuery() == 0 ? 3 : queryCountMap.get(searchSet.getSearchModule())==null || queryCountMap.get(searchSet.getSearchModule()) <= 9 ? 1 : 0));
//						}	
						newSearchSet.setDataType(fieldSet.getDataType());
						newSearchSet.setSearchName(fieldSet.getFieldName());
						newSearchSet.setModifydate(new Date());
						newSearchSet.setModifierAcc(fieldSet.getModifierAcc());						
						newList.add(newSearchSet);
					}
					custSearchSetMapper.batchUpdate(newList);
				}
			}
			
			
		}
	}

	/**
	 * 修改共有者字段
	 * @param fieldSet
	 */
	public void updateByFileCode(String developCode,String orgId,String modifierAcc)throws Exception{
		// 查询是否启用
		CustSearchSet entity = new CustSearchSet();
		entity.setOrgId(orgId);
		entity.setDevelopCode(developCode);
		List<CustSearchSet> list = custSearchSetMapper.findByCondtion(entity);	
		List<CustSearchSet>enableList = new ArrayList<CustSearchSet>();
		List<CustSearchSet>notEnableList= new ArrayList<CustSearchSet>();
		
		if(list!=null && list.size()>0){
			Map<String,Object>map = new HashMap<String, Object>();
			for(CustSearchSet searchSet : list){	
				searchSet.setModifydate(new Date());
				searchSet.setModifierAcc(modifierAcc);
				map.put(searchSet.getSearchModule(), searchSet.getSort());
				if(searchSet.getEnable() == 1){
					enableList.add(searchSet);
				}else{
					notEnableList.add(searchSet);
				}
			}
			// 共享客户设置是开启,则需启用共有者
			if(getCommonOwnerOpenState(orgId) == 1){	
				if(notEnableList!=null && notEnableList.size()>0){
					// 1：区间排序修改
					updateIntervalSort(map,true,orgId,modifierAcc);	
					List<CustSearchSet>newList = new ArrayList<CustSearchSet>();
					// 2: 启用共有者字段
					for(CustSearchSet searchSet : notEnableList){
						CustSearchSet newSearchSet = new CustSearchSet();
						newSearchSet.setId(searchSet.getId());
						newSearchSet.setOrgId(searchSet.getOrgId());
						newSearchSet.setModifierAcc(modifierAcc);
						newSearchSet.setModifydate(new Date());
						newSearchSet.setEnable(1);
						newList.add(newSearchSet);
					}
					custSearchSetMapper.batchUpdate(newList);
				}				
			}else{ // 共享客户设置是开启,则需停用共有者
				if(enableList!=null && enableList.size()>0){
					List<CustSearchSet>newList = new ArrayList<CustSearchSet>();
					// 1 : 先停用共有者字段
					for(CustSearchSet searchSet : enableList){
						CustSearchSet newSearchSet = new CustSearchSet();
						newSearchSet.setId(searchSet.getId());
						newSearchSet.setOrgId(searchSet.getOrgId());
						newSearchSet.setModifierAcc(modifierAcc);
						newSearchSet.setModifydate(new Date());
						newSearchSet.setEnable(0);
						newList.add(newSearchSet);
					}
					custSearchSetMapper.batchUpdate(newList);
					
					// 2：区间排序修改
					updateIntervalSort(map,false,orgId,modifierAcc);
				}
			}
		} 
			
	}
	
	// 区间排序做修改
	private void updateIntervalSort(Map<String,Object>map,boolean isAdd,String orgId,String modifierAcc)throws Exception{				
		List<CustSearchSet> list = new ArrayList<CustSearchSet>();
		Map<String,Object>map1 = new HashMap<String, Object>();
		for(SysEnum s : SysEnum.getComAccModules()){
			map1.put("orgId", orgId);
			map1.put("minSort", map.get(s.getState()));
			map1.put("searchModule", s.getState());			
			List<CustSearchSet> list1 = custSearchSetMapper.findAllBySortInterval(map1);
			map1.clear();
			list.addAll(list1);
		}
		
		if(list!=null && list.size()>0){
			List<CustSearchSet>newList = new ArrayList<CustSearchSet>();
			for(CustSearchSet searchSet : list){
				CustSearchSet newSearchSet = new CustSearchSet();
				if(isAdd){
					newSearchSet.setSort((short) (searchSet.getSort() + 1));
				}else{
					newSearchSet.setSort((short) (searchSet.getSort() - 1));
				}		
				newSearchSet.setOrgId(searchSet.getOrgId());
				newSearchSet.setId(searchSet.getId());
				newSearchSet.setModifydate(new Date());
				newSearchSet.setModifierAcc(modifierAcc);
				newList.add(newSearchSet);
			}
			custSearchSetMapper.batchUpdate(newList);
		}
	}
	
	/**共有者开关  0-关闭 1-开启*/
	private int getCommonOwnerOpenState(String orgId){
		int open = 0;
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50011, orgId);
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getDictionaryValue();
			open = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "0");
		}
		return open;
	}
}
