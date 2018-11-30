package com.qftx.tsm.option.service;

import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.dao.DataDictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataDictionaryService {
	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;
	
	 
	public void create(DataDictionaryBean entity) {
		dataDictionaryMapper.insert(entity);
	}

	 
	public void createBatch(List<DataDictionaryBean> entitys) {
		dataDictionaryMapper.insertBatch(entitys);
	}

	 
	public DataDictionaryBean getByPrimaryKey(String id) {
		return dataDictionaryMapper.getByPrimaryKey(id);
	}

	 
	public List<DataDictionaryBean> getList() {
		return dataDictionaryMapper.find();
	}

	 
	public List<DataDictionaryBean> getListByCondtion(DataDictionaryBean entity) {
		return dataDictionaryMapper.findByCondtion(entity);
	}

	 
	public List<DataDictionaryBean> getListPage(DataDictionaryBean entity) {
		return dataDictionaryMapper.findListPage(entity);
	}

	 
	public void modify(DataDictionaryBean entity) {
		dataDictionaryMapper.update(entity);
	}

	 
	public void modifyBatch(List<DataDictionaryBean> entitys) {
		for (DataDictionaryBean dataDictionary : entitys) {
			dataDictionaryMapper.update(dataDictionary);
		}
	}

	 
	public void modifyTrends(DataDictionaryBean entity) {
		dataDictionaryMapper.updateTrends(entity);
	}

	 
	public void modifyTrendsBatch(List<DataDictionaryBean> entitys) {
		dataDictionaryMapper.batchUpdate(entitys);
	}

	 
	public void remove(String id) {
		dataDictionaryMapper.delete(id);
	}

	 
	public void removeBatch(List<String> ids) {
		dataDictionaryMapper.deleteBatch(ids);
	}

	 
	public void removeFakeBatch(Map<String, Object> map) {
		dataDictionaryMapper.deleteFakeBatch(map);
	}

	 
	public Integer getDataValueByOrgIdAndCode(Map<String, String> param) {
		return dataDictionaryMapper.findDataValueByOrgIdAndCode(param);
	}

	public DataDictionaryBean  getDataDictionaryBean(Map<String, String> map){
		return dataDictionaryMapper.findDataDictionaryBean(map);
	}
	
	public List<DataDictionaryBean> queryAllWithOrgId() {
		return dataDictionaryMapper.findAllWithOrgId();
	}


	public List<DataDictionaryBean> getAllWithOrgId(String orgId) {
		return dataDictionaryMapper.getAllWithOrgId(orgId);
	}

	/**
	 * 获取状态为开启的消息发送参数
	 */
	public List<DataDictionaryBean> findOpenMsgArgs(Map<String, String> param){
		return dataDictionaryMapper.findOpenMsgArgs(param);
	}
	
	public void updateByCode(DataDictionaryBean bean){
		dataDictionaryMapper.updateByCode(bean);
	}
	
}
