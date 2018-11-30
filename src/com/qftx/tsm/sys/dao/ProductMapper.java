package com.qftx.tsm.sys.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sys.bean.Product;

import java.util.List;
import java.util.Map;


public interface ProductMapper extends BaseDao<Product> {
	
	public void deleteByBatch(Map<String,Object>map);
	
	public void deleteBy(Map<String,Object>map);
	
	//判断排序是否唯一 
	public Integer findSortByExists(Product entity);
	
	public List<String>findProductNames(Map<String,Object>map);
}
