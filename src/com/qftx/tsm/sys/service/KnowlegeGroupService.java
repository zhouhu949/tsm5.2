package com.qftx.tsm.sys.service;

import com.qftx.tsm.sys.bean.KnowlegeGroup;
import com.qftx.tsm.sys.dao.KnowlegeGroupMapper;
import com.qftx.tsm.sys.dto.KnowlegeGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KnowlegeGroupService {
     @Autowired
	private KnowlegeGroupMapper knowlegeGroupMapper;
     
 	public void create(KnowlegeGroup entity) {
 		knowlegeGroupMapper.insert(entity);
 	}

 	public void createBatch(List<KnowlegeGroup> entitys) {
 		knowlegeGroupMapper.insertBatch(entitys);
 	}

 	
 	public KnowlegeGroup getByPrimaryKey(String id) {
 		return knowlegeGroupMapper.getByPrimaryKey(id);
 	}

 	
 	public List<KnowlegeGroup> getList() {
 		return knowlegeGroupMapper.find();
 	}

 	
 	public List<KnowlegeGroup> getListByCondtion(KnowlegeGroup entity) {
 		return knowlegeGroupMapper.findByCondtion(entity);
 	}

 	
 	public List<KnowlegeGroup> getListPage(KnowlegeGroup entity) {
 		return knowlegeGroupMapper.findListPage(entity);
 	}

 	
 	public void modify(KnowlegeGroup entity) {
 		knowlegeGroupMapper.update(entity);
 	}

 	
 	public void modifyBatch(List<KnowlegeGroup> entitys) {
 		for (KnowlegeGroup knowlegeGroup : entitys) {
 			knowlegeGroupMapper.update(knowlegeGroup);
 		}
 	}
 	
 	public void modifyBatch_new(List<KnowlegeGroup> entitys) {
 		for (KnowlegeGroup knowlegeGroup : entitys) {
 			knowlegeGroupMapper.update_new(knowlegeGroup);
 		}
 	}

 	
 	public void modifyTrends(KnowlegeGroup entity) {
 		knowlegeGroupMapper.updateTrends(entity);
 	}

 	
 	public void modifyTrendsBatch(List<KnowlegeGroup> entitys) {
 		for (KnowlegeGroup knowlegeGroup : entitys) {
 			knowlegeGroupMapper.updateTrends(knowlegeGroup);
 		}
 	}

 	
 	public void remove(String id) {
 		knowlegeGroupMapper.delete(id);
 	}

 	
 	public void removeBatch(List<String> ids) {
 		knowlegeGroupMapper.deleteBatch(ids);
 	}

 	
 	public void removeFakeBatch(Map<String, Object> map) {
 		knowlegeGroupMapper.deleteFakeBatch(map);
 	}

 	
 	public List<KnowlegeGroupDto> queryKnowlegeGroup(Map<String, String> map) {
 		return knowlegeGroupMapper.findKnowlegeGroup(map);
 	}
 	
 	public List<KnowlegeGroupDto> findKnowlegeGroup_new(Map<String, String> map) {
 		return knowlegeGroupMapper.findKnowlegeGroup_new(map);
 	}
 	
 	public int getAllAKnowleges(Map<String, String> map) {
 		return knowlegeGroupMapper.findAllKnowleges(map);
 	}

 	
 	public int getKnowlegeUnGroup(Map<String, String> map) {
 		return knowlegeGroupMapper.findKnowlegeUnGroup(map);
 	}

 	
 	public KnowlegeGroup getKnowlegeByGroupId(Map<String, String> map) {
 		return knowlegeGroupMapper.findKnowlegeByGroupId(map);
 	}

 	
 	public void updateKnowlegeGroup(Map<String, String> map) {
 		knowlegeGroupMapper.updateKnowlegeGroup(map);
 		knowlegeGroupMapper.updateKnowleges(map);
 	}
 	
 	
 	public List<KnowlegeGroup> getGrpListByGrpName(KnowlegeGroup knowlegeGroup) {
 		return knowlegeGroupMapper.findGrpListByGrpName(knowlegeGroup);
 	}

}
