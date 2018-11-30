package com.qftx.tsm.sms.service;


import com.qftx.tsm.sms.bean.TsmCallSendSms;
import com.qftx.tsm.sms.dao.TsmCallSendSmsMapper;
import com.qftx.tsm.sms.dto.TsmCallSendSmsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TsmCallSendSmsService {
	@Autowired
	private TsmCallSendSmsMapper tsmCallSendSmsMapper;
	
	 
	public List<TsmCallSendSms> getList() {
		return tsmCallSendSmsMapper.find();
	}
	 
	public List<TsmCallSendSms> getListByCondtion(TsmCallSendSms entity) {
		return tsmCallSendSmsMapper.findByCondtion(entity);
	}
	 
	public List<TsmCallSendSms> getListPage(TsmCallSendSms entity) {
		return tsmCallSendSmsMapper.findListPage(entity);
	}
	 
	public TsmCallSendSms getByPrimaryKey(String id) {
		return tsmCallSendSmsMapper.getByPrimaryKey(id);
	}
	 
	public void create(TsmCallSendSms entity) {
		tsmCallSendSmsMapper.insert(entity);

	}
	 
	public void createBatch(List<TsmCallSendSms> entitys) {
		tsmCallSendSmsMapper.insertBatch(entitys);

	}
	 
	public void modify(TsmCallSendSms entity) {
		tsmCallSendSmsMapper.update(entity);

	}
 
	public void modifyTrends(TsmCallSendSms entity) {
		tsmCallSendSmsMapper.updateTrends(entity);

	}

	public void modifyBatch(List<TsmCallSendSms> entitys) {
		for (TsmCallSendSms dataDictionary : entitys) {
			tsmCallSendSmsMapper.update(dataDictionary);
		}

	}
	 
	public void modifyTrendsBatch(List<TsmCallSendSms> entitys) {
		for (TsmCallSendSms tsmSendSms : entitys) {
			tsmCallSendSmsMapper.updateTrends(tsmSendSms);
		}

	}
	 
	public void remove(String id) {
		tsmCallSendSmsMapper.delete(id);

	}
	 
	public void removeBatch(List<String> ids,String orgId) {
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("list", ids);
		tsmCallSendSmsMapper.deleteBatchBy(map);
		//  TODO 删除短信发送明细
	}
	 
	public void updateBatchSms(Map<String, Object> map) {
		tsmCallSendSmsMapper.updateBatchSms(map);
	}
	 
	public List<TsmCallSendSmsDto> getSendSmsListPage(TsmCallSendSmsDto entity) {
		return tsmCallSendSmsMapper.getSendSmsListPage(entity);
	}

	public List<TsmCallSendSms> getByIds(List<String> ids) {
		return tsmCallSendSmsMapper.findByIds(ids);
	}

}
