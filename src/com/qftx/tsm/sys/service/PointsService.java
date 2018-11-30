package com.qftx.tsm.sys.service;

import com.qftx.tsm.sys.bean.Points;
import com.qftx.tsm.sys.dao.PointsMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointsService{

	Logger logger = Logger.getLogger(PointsService.class);
	
	@Autowired
	private PointsMapper pointsMapper;

	public List<Points> getList() {
		return pointsMapper.find();
	}

	public List<Points> getListByCondtion(Points entity) {
		return pointsMapper.findByCondtion(entity);
	}

	 
	public Points getByPrimaryKey(String pointsId) {
		return pointsMapper.getByPrimaryKey(pointsId);
	}
	 
	public void create(Points entity) {
		pointsMapper.insert(entity);
	}

	 
	public void createBatch(List<Points> entitys) {
		pointsMapper.insertBatch(entitys);
	}

	 
	public void modify(Points entity) {
		pointsMapper.updateTrends(entity);
	}

	 
	public void modifyTrends(Points entity) {
		pointsMapper.updateTrends(entity);	
	}

	 
	public void modifyBatch(List<Points> entitys) {
		
	}

	public void modifyTrendsBatch(List<Points> entitys) {
		for(Points point:entitys){
			pointsMapper.updateTrends(point);	
		}
	}

	/** 定义 初始化数据 */
	public List<Points>getOldPointsList(){
		return pointsMapper.getOldPointsList();
	}
}
