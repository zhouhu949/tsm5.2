package com.qftx.tsm.cust.service;

import com.qftx.tsm.cust.bean.TaoTagBean;
import com.qftx.tsm.cust.dao.TaoTagMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaoTagService {

	Logger logger = Logger.getLogger(TaoTagService.class);

	@Autowired
	private TaoTagMapper taoTagMapper;

	public List<TaoTagBean> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaoTagBean> getListByCondtion(TaoTagBean entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaoTagBean> getListPage(TaoTagBean entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public TaoTagBean getByPrimaryKey(String account) {
		return taoTagMapper.getByPrimaryKey(account);
	}

	public void create(TaoTagBean entity) {
		taoTagMapper.insert(entity);

	}

	public void createBatch(List<TaoTagBean> entitys) {
		// TODO Auto-generated method stub

	}

	public void modify(TaoTagBean entity) {
		taoTagMapper.update(entity);

	}

	public void modifyTrends(TaoTagBean entity) {
		// TODO Auto-generated method stub

	}

	public void modifyBatch(List<TaoTagBean> entitys) {
		// TODO Auto-generated method stub

	}

	public void modifyTrendsBatch(List<TaoTagBean> entitys) {
		// TODO Auto-generated method stub

	}

	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	public void removeBatch(List<String> ids) {
		// TODO Auto-generated method stub

	}

	public void modifyResIdByAcc(Map<String, String> map) {
		taoTagMapper.updateResIdByAcc(map);
	}

	public TaoTagBean getByPrimaryKey(String account, String orgId) {
		return taoTagMapper.getByPrimaryKey(account, orgId);
	}
}
