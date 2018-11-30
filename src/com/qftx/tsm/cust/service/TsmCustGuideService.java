package com.qftx.tsm.cust.service;

import com.qftx.tsm.cust.bean.TsmCustGuide;
import com.qftx.tsm.cust.dao.TsmCustGuideMapper;
import com.qftx.tsm.cust.dto.TsmCustGuideDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 客户导航
 */
@Service
public class TsmCustGuideService {

	@Autowired
	private  TsmCustGuideMapper tsmCustGuideMapper;
	
	public void create(TsmCustGuide entity) {
		tsmCustGuideMapper.insert(entity);
	}

	public void createBatch(List<TsmCustGuide> entitys) {
		tsmCustGuideMapper.insertBatch(entitys);
	}

	public TsmCustGuide getByPrimaryKey(String id) {
		return tsmCustGuideMapper.getByPrimaryKey(id);
	}


	public List<TsmCustGuide> getList() {
		return tsmCustGuideMapper.find();
	}

	public List<TsmCustGuide> getListByCondtion(TsmCustGuide entity) {
		return tsmCustGuideMapper.findByCondtion(entity);
	}

	public List<TsmCustGuide> getListPage(TsmCustGuide entity) {
		return tsmCustGuideMapper.findListPage(entity);
	}

	public void modify(TsmCustGuide entity) {
		tsmCustGuideMapper.update(entity);
	}

	public void modifyTrends(TsmCustGuide entity) {
		tsmCustGuideMapper.updateTrends(entity);
	}

	public void remove(String id) {
		tsmCustGuideMapper.delete(id);
	}

	public void removeBatch(List<String> ids) {
		tsmCustGuideMapper.deleteBatch(ids);
	}
	
	public TsmCustGuideDto getCustGuideByCustId(TsmCustGuideDto entity){
		 List<TsmCustGuideDto> lists=tsmCustGuideMapper.findCustGuideByCustId(entity);
		 //避免出现多个销售进程或者无销售进程的
		 if(lists.size()>0){
			 return lists.get(0);
		 }
		 return new TsmCustGuideDto();
	}	
	
}