package com.qftx.tsm.sys.service;

import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.dao.CustFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class CustFieldSetService{
	
	@Autowired
	private CustFieldSetMapper custFieldSetMapper;

	
	public List<CustFieldSet> getList() {
		return custFieldSetMapper.find();
	}

	 
	public List<CustFieldSet> getListByCondtion(CustFieldSet entity) {
		return custFieldSetMapper.findByCondtion(entity);
	}

	public CustFieldSet getByCondtion(CustFieldSet entity) {
		return custFieldSetMapper.getByCondtion(entity);
	}

	public List<CustFieldSet> getListPage(CustFieldSet entity) {
		return custFieldSetMapper.findListPage(entity);
	}

	 
	public CustFieldSet getByPrimaryKey(String id) {
		return custFieldSetMapper.getByPrimaryKey(id);
	}

	 
	public void create(CustFieldSet entity) {
		custFieldSetMapper.insert(entity);
	}

	 
	public void createBatch(List<CustFieldSet> entitys) {
		custFieldSetMapper.insertBatch(entitys);
	}


	public void modifyTrends(CustFieldSet entity) {
		custFieldSetMapper.updateTrends(entity);
	}

	 
	public void modifyBatch(List<CustFieldSet> entitys) {
		for (CustFieldSet custFieldSet : entitys) {
			custFieldSetMapper.updateTrends(custFieldSet);
		}
	}

	 
	public void modifyTrendsBatch(List<CustFieldSet> entitys) {
		for (CustFieldSet custFieldSet : entitys) {
			custFieldSetMapper.updateTrends(custFieldSet);
		}
	}

	 
	public void remove(String id) {
		custFieldSetMapper.delete(id);
	}

	 
	public void removeBatch(List<String> ids) {
		custFieldSetMapper.deleteBatch(ids);
	}

	 
	public List<CustFieldSet> queryAllWithOrgId() {
		return custFieldSetMapper.findAllWithOrgId();
	}

	 
	public List<CustFieldSet> getAllWithOrgId(String orgId) {
		return custFieldSetMapper.getAllWithOrgId(orgId);
	}

	/** 顺序修改 */
	public void updateSort(List<CustFieldSet> cfs,String orgId){
		for(CustFieldSet cf : cfs){
			cf.setOrgId(orgId);
			custFieldSetMapper.updateTrends(cf);
		}
	}
	
	/** 根据字段code 模糊查询  获取该字段个数 */
	public Integer getCountByFieldCode(Map<String,Object>map){
		return custFieldSetMapper.findCountByFieldCode(map);
	}
	
	/** 查询 排序最大值 */
	public Integer getMaxBySort(Map<String,Object>map){
		return custFieldSetMapper.findMaxBySort(map);
	}
	
	/** 查询 字段名称是否存在 */
	public Integer getFieldNameIsExists(Map<String,Object>map){
		return custFieldSetMapper.findFieldNameIsExists(map);
	}
	
	/** 查询排序区间的数据 */
	public List<CustFieldSet> getAllBySortInterval(Map<String,Object>map){
		return custFieldSetMapper.findAllBySortInterval(map);
	}
	
	/**  根据 字段类型查询数据 */
	public  List<CustFieldSet> getAllByDataType(Map<String,Object>map){
		return custFieldSetMapper.findAllByDataType(map);
	}
	
	/** 根据字段ID，查询排序值*/
	public List<CustFieldSet> getSortsByFieldId(Map<String,Object>map){
		return custFieldSetMapper.findSortsByFieldId(map);
	}
	
	/**
	 * 批量修改
	 * @param list
	 */
	public void batchUpdate(List<CustFieldSet>list){
		custFieldSetMapper.batchUpdate(list);
	}
}
