package com.qftx.tsm.sys.service;

import com.qftx.tsm.sys.bean.Knowlege;
import com.qftx.tsm.sys.dao.KnowlegeMapper;
import com.qftx.tsm.sys.dto.KnowlegeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KnowlegeService {
     @Autowired
	 private KnowlegeMapper knowlegeMapper;
     
 	public void create(Knowlege entity) {
 		knowlegeMapper.insert(entity);
 	}

 	
 	public void createBatch(List<Knowlege> entitys) {
 		knowlegeMapper.insertBatch(entitys);
 	}

 	
 	public Knowlege getByPrimaryKey(String id) {
 		return knowlegeMapper.getByPrimaryKey(id);
 	}

 	
 	public List<Knowlege> getList() {
 		return knowlegeMapper.find();
 	}

 	
 	public List<Knowlege> getListByCondtion(Knowlege entity) {
 		return knowlegeMapper.findByCondtion(entity);
 	}

 	
 	public List<Knowlege> getListPage(Knowlege entity) {
 		return knowlegeMapper.findListPage(entity);
 	}
 	
 	public List<Knowlege> getListPage_new(Knowlege entity) {
 		return knowlegeMapper.findListPage_new(entity);
 	}
 	
 	public List<KnowlegeDto> getListPageDto(KnowlegeDto entity) {
 		return knowlegeMapper.getListPageDto(entity);
 	}

 	
 	public void modify(Knowlege entity) {
 		knowlegeMapper.update(entity);
 	}

 	
 	public void modifyBatch(List<Knowlege> entitys) {
 		for (Knowlege knowlege : entitys) {
 			knowlegeMapper.update(knowlege);
 		}
 	}
 	
 	public void modifyBatch_new(List<Knowlege> entitys) {
 		for (Knowlege knowlege : entitys) {
 			knowlegeMapper.update_new(knowlege);
 		}
 	}

 	
 	public void modifyTrends(Knowlege entity) {
 		knowlegeMapper.updateTrends(entity);
 	}

 	
 	public void modifyTrendsBatch(List<Knowlege> entitys) {
 		for (Knowlege knowlege : entitys) {
 			knowlegeMapper.updateTrends(knowlege);
 		}
 	}

 	
 	public void remove(String id) {
 		knowlegeMapper.delete(id);
 	}

 	
 	public void removeBatch(List<String> ids) {
 		knowlegeMapper.deleteBatch(ids);
 	}

 	
 	public void removeKnowlegeBatch(Map<String, Object> map) {
 		knowlegeMapper.deleteKnowlegeBatch(map);
 	}

 	
 	public Knowlege getKnowlegeById(String orgId,String knowlegeId) {
 		return knowlegeMapper.findKnowlegeById(orgId,knowlegeId);
 	}

}
