package com.qftx.tsm.follow.service;

import com.qftx.tsm.cust.dao.ResCustInfoDetailMapper;
import com.qftx.tsm.follow.bean.TsmCustExtension;
import com.qftx.tsm.follow.dao.TsmCustExtensionMapper;
import com.qftx.tsm.follow.dto.TsmCustExtensionDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户跟进延期审核
 */
@Service
public class TsmCustExtensionService{

	@Autowired
	private  TsmCustExtensionMapper tsmCustExtensionMapper;
	@Autowired
	private ResCustInfoDetailMapper resCustInfoDetailMapper;
	
	public void create(TsmCustExtension entity) {
		tsmCustExtensionMapper.insert(entity);
	}

	public void createBatch(List<TsmCustExtension> entitys) {
		tsmCustExtensionMapper.insertBatch(entitys);
	}

	public TsmCustExtension getByPrimaryKey(String id) {
		return tsmCustExtensionMapper.getByPrimaryKey(id);
	}

	public List<TsmCustExtension> getList() {
		return tsmCustExtensionMapper.find();
	}

	public List<TsmCustExtension> getListByCondtion(TsmCustExtension entity) {
		return tsmCustExtensionMapper.findByCondtion(entity);
	}

	public List<TsmCustExtension> getListPage(TsmCustExtension entity) {
		return tsmCustExtensionMapper.findListPage(entity);
	}

	 
	public void modify(TsmCustExtension entity) {
		tsmCustExtensionMapper.update(entity);
	}

	 
	public void modifyTrends(TsmCustExtension entity) {
		tsmCustExtensionMapper.updateTrends(entity);
	}

	public void remove(String id) {
		tsmCustExtensionMapper.delete(id);
	}

	 
	public void removeBatch(List<String> ids) {
		tsmCustExtensionMapper.deleteBatch(ids);
	}

	 
	public List<TsmCustExtensionDto> getDeferredAuditListPage(TsmCustExtensionDto entity) {
		List<TsmCustExtensionDto> custs = new ArrayList<TsmCustExtensionDto>();
    	List<String> cids = new ArrayList<String>();
    	if(entity.getState() != null && entity.getState() == 1 && StringUtils.isNotBlank(entity.getQueryText()) && (entity.getQueryType().equals("3") || entity.getQueryType().equals("2"))){
    		if(entity.getQueryType().equals("3")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(entity.getOrgId(), entity.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(entity.getOrgId(), entity.getQueryText());
    		}
    		if(cids.size() == 0) return custs;
    		entity.setCustIds(cids);
    		entity.setQueryText(null);
    	}
		return tsmCustExtensionMapper.findDeferredAuditListPage(entity);
	}
	
	 
    public void modifyBatchStatus(String orgId,String statusExtended,String updateAcc,String updateName,List<String>lists){
		tsmCustExtensionMapper.updateBatchStatus(orgId, statusExtended, updateAcc, updateName, lists);
    }
	
	
}