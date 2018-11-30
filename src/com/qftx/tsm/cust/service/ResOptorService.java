package com.qftx.tsm.cust.service;

import com.qftx.tsm.cust.bean.ResOptorBean;
import com.qftx.tsm.cust.dao.ResOptorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



 /** 
 * 资源操作日志
 * @author: wuwei
 * @since: 2015年11月17日  下午4:12:54
 * @history:
 */
@Service
public class ResOptorService{

	@Autowired
	private  ResOptorMapper resOptorMapper;
	
	
	public void create(ResOptorBean entity) {
		resOptorMapper.insert(entity);
	}

	
	public void createBatch(List<ResOptorBean> entitys) {
		resOptorMapper.insertBatch(entitys);
	}

	
	public ResOptorBean getByPrimaryKey(String id) {
		return resOptorMapper.getByPrimaryKey(id);
	}

	
	public List<ResOptorBean> getList() {
		return resOptorMapper.find();
	}

	
	public List<ResOptorBean> getListByCondtion(ResOptorBean entity) {
		return resOptorMapper.findByCondtion(entity);
	}

	
	public List<ResOptorBean> getListPage(ResOptorBean entity) {
		return resOptorMapper.findListPage(entity);
	}

	
	public void modify(ResOptorBean entity) {
		resOptorMapper.update(entity);
	}

	
	public void modifyBatch(List<ResOptorBean> entitys) {
    
	}

	
	public void modifyTrends(ResOptorBean entity) {
		resOptorMapper.updateTrends(entity);
	}

	
	public void modifyTrendsBatch(List<ResOptorBean> entitys) {
	}

	
	public void remove(String id) {
		resOptorMapper.delete(id);
	}

	
	public void removeBatch(List<String> ids) {
		resOptorMapper.deleteBatch(ids);
	}

	
}