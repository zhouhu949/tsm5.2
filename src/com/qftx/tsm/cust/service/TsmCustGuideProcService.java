package com.qftx.tsm.cust.service;

import com.qftx.tsm.cust.bean.TsmCustGuideProc;
import com.qftx.tsm.cust.dao.TsmCustGuideProcMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



/**
 * 客户导航 适用产品关联
 */
@Service
public class TsmCustGuideProcService {

	@Autowired
	private  TsmCustGuideProcMapper tsmCustGuideProcMapper;
	
	public void create(TsmCustGuideProc entity) {
		tsmCustGuideProcMapper.insert(entity);
	}

	public void createBatch(List<TsmCustGuideProc> entitys) {
		tsmCustGuideProcMapper.insertBatch(entitys);
	}

	public List<TsmCustGuideProc> getList() {
		return tsmCustGuideProcMapper.find();
	}

	public List<TsmCustGuideProc> getListByCondtion(TsmCustGuideProc entity) {
		return tsmCustGuideProcMapper.findByCondtion(entity);
	}

	public void remove(Map<String,String>map) {
		tsmCustGuideProcMapper.deleteBy(map);
	}
	
	public List<String> getProcIdsByCustId(String orgId, String custId){
		return tsmCustGuideProcMapper.findProcIdsByCustId(orgId,custId);
	}
}