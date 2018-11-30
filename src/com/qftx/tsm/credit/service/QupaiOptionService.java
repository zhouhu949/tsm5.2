package com.qftx.tsm.credit.service;

import com.qftx.tsm.credit.dao.QupaiOptionMapper;
import com.qftx.tsm.option.bean.OptionBean;
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
public class QupaiOptionService {

	Logger logger = Logger.getLogger(QupaiOptionService.class);
	
	@Autowired
	private QupaiOptionMapper qupaioptionMapper;
	
	public void create(OptionBean entity) {
		qupaioptionMapper.insert(entity);
	}

	 
	public void createBatch(List<OptionBean> entitys) {
		qupaioptionMapper.insertBatch(entitys);
	}
	 
	public OptionBean getByPrimaryKey(String id) {
		return qupaioptionMapper.getByPrimaryKey(id);
	}

	 
	public List<OptionBean> getList() {
		return qupaioptionMapper.find();
	}

	 
	public List<OptionBean> getListByCondtion(OptionBean entity) {
		return qupaioptionMapper.findByCondtion(entity);
	}

	public OptionBean getByCondtion(OptionBean entity) {
		return qupaioptionMapper.getByCondtion(entity);
	}
	 
	public List<OptionBean> getListPage(OptionBean entity) {
		return qupaioptionMapper.findListPage(entity);
	}

	 
	public void modify(OptionBean entity) {
		qupaioptionMapper.update(entity);
	}

	 
	public void modifyBatch(List<OptionBean> entitys) {
		for (OptionBean option : entitys) {
			qupaioptionMapper.update(option);
		}
	}

	 
	public void modifyTrends(OptionBean entity) {
		qupaioptionMapper.updateTrends(entity);
	}

	 
	public void modifyTrendsBatch(List<OptionBean> entitys) {
		for (OptionBean option : entitys) {
			qupaioptionMapper.updateTrends(option);
		}
	}

	 
	public void remove(String id) {
		qupaioptionMapper.delete(id);
	}

	public void deleteByMap(OptionBean entity){
		qupaioptionMapper.deleteByMap(entity);
	}
	 
	public void removeBatch(Map<String,Object>map) {
		qupaioptionMapper.deleteByBatch(map);
	}

	 
	public boolean IsUnitInit(String orgId) {
		Integer count = qupaioptionMapper.findIsUnitInit(orgId);
		if(count != null && count > 0){
			return true;
		}
		return false;
	}
	 
	public List<OptionBean> getOptionGiveUpListPage1(OptionBean entity) {
		return qupaioptionMapper.findOptionGiveUpListPage1(entity);
	}

	 
	public List<OptionBean> getSubOptionList(Map<String, String> param) {
		return qupaioptionMapper.findSubOptionList(param);
	}
	public List<OptionBean>getOptionGiveUp(Map<String,Object>map){
		return qupaioptionMapper.findOptionGiveUp(map);
	}
	 
	public List<OptionBean> queryAllWithOrgId() {
		return qupaioptionMapper.findAllWithOrgId();
	}

	 
	public List<String> getOrgIdList() {
		return qupaioptionMapper.getOrgIdList();
	}

	public List<OptionBean> getAllWithOrgId(String orgId) {
		return qupaioptionMapper.getAllWithOrgId(orgId);
	}
	
	public  List<OptionDto> getRecordReviewTypeByOrgId(TsmCustReviewDto reviewDto){
		return qupaioptionMapper.findRecordReviewTypeByOrgId(reviewDto);
	}
	
	//判断排序是否唯一
	public Integer getSortByExists(OptionBean entity){
		return qupaioptionMapper.findSortByExists(entity);
	}
	
	public List<String>getOptionNames(Map<String,Object>map){
			return qupaioptionMapper.findOptionNames(map);
	}
	
	public List<OptionBean> findOptionListByAccount(String orgId){
		return qupaioptionMapper.findOptionListByAccount(orgId);
}


	public List<OptionBean> getAllOption(OptionBean optionBean) {
		return qupaioptionMapper.getAllOption(optionBean);
	}
}
