package com.qftx.tsm.option.service;

import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dao.OptionMapper;
import com.qftx.tsm.option.dto.OptionDto;
import com.qftx.tsm.sys.dto.TsmCustReviewDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

 /** 
 * 数据选项业务逻辑实现
 * @history:
 */
@Service
public class OptionService {

	Logger logger = Logger.getLogger(OptionService.class);
	
	@Autowired
	private OptionMapper optionMapper;
	
	public void create(OptionBean entity) {
		optionMapper.insert(entity);
	}

	 
	public void createBatch(List<OptionBean> entitys) {
		optionMapper.insertBatch(entitys);
	}
	 
	public OptionBean getByPrimaryKey(String id) {
		return optionMapper.getByPrimaryKey(id);
	}

	 
	public List<OptionBean> getList() {
		return optionMapper.find();
	}

	 
	public List<OptionBean> getListByCondtion(OptionBean entity) {
		return optionMapper.findByCondtion(entity);
	}

	public OptionBean getByCondtion(OptionBean entity) {
		return optionMapper.getByCondtion(entity);
	}
	 
	public List<OptionBean> getListPage(OptionBean entity) {
		return optionMapper.findListPage(entity);
	}

	 
	public void modify(OptionBean entity) {
		optionMapper.update(entity);
	}

	 
	public void modifyBatch(List<OptionBean> entitys) {
		for (OptionBean option : entitys) {
			optionMapper.update(option);
		}
	}

	 
	public void modifyTrends(OptionBean entity) {
		optionMapper.updateTrends(entity);
	}

	 
	public void modifyTrendsBatch(List<OptionBean> entitys) {
		for (OptionBean option : entitys) {
			optionMapper.updateTrends(option);
		}
	}

	 
	public void remove(String id) {
		optionMapper.delete(id);
	}

	 
	public void removeBatch(Map<String,Object>map) {
		optionMapper.deleteByBatch(map);
	}

	 
	public boolean IsUnitInit(String orgId) {
		Integer count = optionMapper.findIsUnitInit(orgId);
		if(count != null && count > 0){
			return true;
		}
		return false;
	}
	 
	public List<OptionBean> getOptionGiveUpListPage1(OptionBean entity) {
		return optionMapper.findOptionGiveUpListPage1(entity);
	}

	 
	public List<OptionBean> getSubOptionList(Map<String, String> param) {
		return optionMapper.findSubOptionList(param);
	}
	public List<OptionBean>getOptionGiveUp(Map<String,Object>map){
		return optionMapper.findOptionGiveUp(map);
	}
	 
	public List<OptionBean> queryAllWithOrgId() {
		return optionMapper.findAllWithOrgId();
	}

	 
	public List<String> getOrgIdList() {
		return optionMapper.getOrgIdList();
	}

	public List<OptionBean> getAllWithOrgId(String orgId) {
		return optionMapper.getAllWithOrgId(orgId);
	}
	
	public  List<OptionDto> getRecordReviewTypeByOrgId(TsmCustReviewDto reviewDto){
		return optionMapper.findRecordReviewTypeByOrgId(reviewDto);
	}
	
	//判断排序是否唯一
	public Integer getSortByExists(OptionBean entity){
		return optionMapper.findSortByExists(entity);
	}
	
	public List<String>getOptionNames(Map<String,Object>map){
			return optionMapper.findOptionNames(map);
	}
	
	public List<OptionBean> findOptionListByAccount(String orgId){
		return optionMapper.findOptionListByAccount(orgId);
}


	public List<OptionBean> getAllOption(OptionBean optionBean) {
		return optionMapper.getAllOption(optionBean);
	}
}
