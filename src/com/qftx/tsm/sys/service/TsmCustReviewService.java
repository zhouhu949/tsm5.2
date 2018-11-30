package com.qftx.tsm.sys.service;


import com.qftx.base.util.DateUtil;
import com.qftx.tsm.cust.service.ResCustEventService;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.sys.bean.TsmCustReview;
import com.qftx.tsm.sys.dao.TsmCustReviewMapper;
import com.qftx.tsm.sys.dto.TsmCustReviewDto;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TsmCustReviewService{
	@Autowired
	private  TsmCustReviewMapper tsmCustReviewMapper;
	@Autowired
	private ResCustEventService resCustEventService;
	@Autowired
	private TsmMessageService tsmMessageService;
	
	public void create(TsmCustReview entity) {
		tsmCustReviewMapper.insert(entity);
	}

	public void createWithEvent(TsmCustReview tcr){
		tsmCustReviewMapper.insert(tcr);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("reviewId", tcr.getReviewId());
		jsonMap.put("reviewAcc", tcr.getReviewAcc());
		jsonMap.put("reviewName", tcr.getReviewName());
		jsonMap.put("revComment", tcr.getRevComment());
		jsonMap.put("reviewDate", DateUtil.formatDate(tcr.getReviewDate()));
		resCustEventService.create(tcr.getOrgId(), tcr.getCustId(), "4", JSONObject.fromObject(jsonMap).toString());
		/**发送消息 */
		tsmMessageService.createReviewMessage(tcr);
	}
	
	public void createBatch(List<TsmCustReview> entitys) {
		tsmCustReviewMapper.insertBatch(entitys);
	}

	
	public TsmCustReview getByPrimaryKey(String id) {
		return tsmCustReviewMapper.getByPrimaryKey(id);
	}

	
	public List<TsmCustReview> getList() {
		return tsmCustReviewMapper.find();
	}

	
	public List<TsmCustReview> getListByCondtion(TsmCustReview entity) {
		return tsmCustReviewMapper.findByCondtion(entity);
	}

	
	public List<TsmCustReview> getListPage(TsmCustReview entity) {
		return tsmCustReviewMapper.findListPage(entity);
	}

	
	public void modify(TsmCustReview entity) {
		tsmCustReviewMapper.update(entity);
	}

	
	public void modifyBatch(List<TsmCustReview> entitys) {
    
	}

	
	public void modifyTrends(TsmCustReview entity) {
		tsmCustReviewMapper.updateTrends(entity);
	}

	
	public void modifyTrendsBatch(List<TsmCustReview> entitys) {
		// TODO Auto-generated method stub
		
	}

	
	public void remove(String id) {
		tsmCustReviewMapper.delete(id);
	}

	
	public void removeBatch(List<String> ids,String orgId) {
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("list", ids);
		map.put("orgId", orgId);
		tsmCustReviewMapper.deleteBatchBy(map);
	}

	
	public List<TsmCustReviewDto> getCustCardListPage(TsmCustReviewDto entity) {
		return tsmCustReviewMapper.findCustCardListPage(entity);
	}

	
	public TsmCustReview getReview(String orgId,String retaId) {
		return tsmCustReviewMapper.findReview(orgId,retaId);
	}

	
	public List<TsmCustReviewDto> getRecordReviewListPage(
			TsmCustReviewDto tsmCustReviewDto) {
		return tsmCustReviewMapper.findRecordReviewListPage(tsmCustReviewDto);
	}

	
	public List<TsmCustReview> getCustReview(TsmCustReview tsmCustReview) {
		return tsmCustReviewMapper.getCustReview(tsmCustReview);
	}

	public List<TsmCustReview> getCustReviewListPage(TsmCustReview tsmCustReview) {
		return tsmCustReviewMapper.findCustReviewListPage(tsmCustReview);
	}
}