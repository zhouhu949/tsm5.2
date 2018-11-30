package com.qftx.tsm.sys.service;

import com.qftx.tsm.sys.bean.Product;
import com.qftx.tsm.sys.dao.ProductMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductService{

	Logger logger = Logger.getLogger(ProductService.class);
	
	@Autowired
	private ProductMapper productMapper;

	public List<Product> getList() {
		return productMapper.find();
	}

	public List<Product> getListByCondtion(Product entity) {
		return productMapper.findByCondtion(entity);
	}

	public Product getByCondtion(Product entity) {
		return productMapper.getByCondtion(entity);
	}
	
	public List<Product> getListPage(Product entity) {
		return productMapper.findListPage(entity);
	}
	
	public Product getByPrimaryKey(String pointsId) {
		return productMapper.getByPrimaryKey(pointsId);
	}
	 
	public void create(Product entity) {
		productMapper.insert(entity);
	}

	 
	public void createBatch(List<Product> entitys) {
		productMapper.insertBatch(entitys);
	}

	 
	public void modify(Product entity) {
		productMapper.updateTrends(entity);
	}

	 
	public void modifyTrends(Product entity) {
		productMapper.updateTrends(entity);	
	}

	public void modifyTrendsBatch(List<Product> entitys) {
		for (Product product : entitys) {
			productMapper.updateTrends(product);
		}
	}
	 
	public void remove(Map<String,Object>map) {
		productMapper.deleteBy(map);
	}

	public void removeBatch(Map<String,Object>map) {
		productMapper.deleteByBatch(map);
	}

	//判断排序是否唯一 
	public Integer getSortByExists(Product entity){
		return productMapper.findSortByExists(entity);
	}
	
	public List<String>getProductNames(Map<String,Object>map){
		return productMapper.findProductNames(map);
	}
}
