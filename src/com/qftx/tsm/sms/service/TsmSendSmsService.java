package com.qftx.tsm.sms.service;

import com.qftx.base.enums.SubmitStatus;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.sms.bean.TsmSendSms;
import com.qftx.tsm.sms.dao.TsmSendSmsMapper;
import com.qftx.tsm.sms.dto.TsmSendSmsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TsmSendSmsService {
	@Autowired
	private TsmSendSmsMapper tsmSendSmsMapper;
	
	 
	public List<TsmSendSms> getList() {
		return tsmSendSmsMapper.find();
	}
	 
	public List<TsmSendSms> getListByCondtion(TsmSendSms entity) {
		return tsmSendSmsMapper.findByCondtion(entity);
	}
	 
	public List<TsmSendSms> getListPage(TsmSendSms entity) {
		return tsmSendSmsMapper.findListPage(entity);
	}
	 
	public TsmSendSms getByPrimaryKey(String id) {
		return tsmSendSmsMapper.getByPrimaryKey(id);
	}
	 
	public void create(TsmSendSms entity) {
		tsmSendSmsMapper.insert(entity);

	}
	 
	public void createBatch(List<TsmSendSms> entitys) {
		tsmSendSmsMapper.insertBatch(entitys);

	}
	 
	public void modify(TsmSendSms entity) {
		tsmSendSmsMapper.update(entity);

	}
 
	public void modifyTrends(TsmSendSms entity) {
		tsmSendSmsMapper.updateTrends(entity);

	}

	public void modifyBatch(List<TsmSendSms> entitys) {
		for (TsmSendSms dataDictionary : entitys) {
			tsmSendSmsMapper.update(dataDictionary);
		}

	}
	 
	public void modifyTrendsBatch(List<TsmSendSms> entitys) {
		for (TsmSendSms tsmSendSms : entitys) {
			tsmSendSmsMapper.updateTrends(tsmSendSms);
		}

	}
	 
	public void remove(String id) {
		tsmSendSmsMapper.delete(id);

	}
	 
	public void removeBatch(List<String> ids,String orgId) {
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("list", ids);
		tsmSendSmsMapper.deleteBatchBy(map);
		//  TODO 删除短信发送明细
	}
	 
	public void updateBatchSms(Map<String, Object> map) {
		tsmSendSmsMapper.updateBatchSms(map);
	}
	 
	public List<TsmSendSmsDto> getSendSmsListPage(TsmSendSmsDto entity) {
		return tsmSendSmsMapper.getSendSmsListPage(entity);
	}

	public List<TsmSendSms> getByIds(List<String> ids) {
		return tsmSendSmsMapper.findByIds(ids);
	}

	public List<TsmSendSmsDto> getTeamSendHookSmsListPage(TsmSendSmsDto entity){
		return tsmSendSmsMapper.getTeamSendHookSmsListPage(entity);
	}
	
	/**
	 * 保存短信记录
	 * @param name_mobile
	 * @param smslabel
	 * @param smsContent
	 * @param smsTotal
	 * @history
	 */
	public String saveSms(String id,String account, String smslabel, String smsContent,
			int smsTotal, String orgId, Date date,int type) {
		String tempId = "";
		TsmSendSms entity = new TsmSendSms();
		entity.setSmsId(id);
		entity.setIsDel(new Short("0"));
		entity.setOrgId(orgId);
		entity.setAccount(account);
		entity.setSmsComment(smsContent); //   + smslabel 
		entity.setSendDate(date);
		entity.setSubmitStatus(SubmitStatus.NO_SUBMIT.getStatus());
		entity.setSmsNumber(smsTotal);
		entity.setSignature(smslabel);
		entity.setType(type);
		tempId = id;
		// 批量提交
		tsmSendSmsMapper.insert(entity);
		return tempId;
	}
	
	/** 查询 在条件内是否有短信记录 */
	public int findExistsCount(TsmSendSms entity){
		return tsmSendSmsMapper.getExistsCount(entity);
	}
}
