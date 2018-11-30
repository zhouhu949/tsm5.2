package com.qftx.tsm.credit.service;

import com.qftx.base.auth.service.OrgService;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.credit.dao.CustFieldSetQupaiMapper;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.service.CustFieldSetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class CustFieldSetQupaiService{
	
	@Autowired
	private CustFieldSetQupaiMapper custFieldSetQupaiMapper;
	@Autowired
	private CustSearchSetQupaiService custSearchSetQupaiService;
	@Autowired
	private TsmCustWorkflowSetService tsmCustWorkflowSetService;
	@Autowired 
	private  OrgService orgService;
	@Autowired 
	private  CustFieldSetService custFieldSetService;

	
	public List<CustFieldSet> getList() {
		return custFieldSetQupaiMapper.find();
	}

	 
	public List<CustFieldSet> getListByCondtion(CustFieldSet entity) {
		return custFieldSetQupaiMapper.findByCondtion(entity);
	}

	public CustFieldSet getByCondtion(CustFieldSet entity) {
		return custFieldSetQupaiMapper.getByCondtion(entity);
	}

	public List<CustFieldSet> getListPage(CustFieldSet entity) {
		return custFieldSetQupaiMapper.findListPage(entity);
	}

	 
	public CustFieldSet getByPrimaryKey(String id) {
		return custFieldSetQupaiMapper.getByPrimaryKey(id);
	}

	 
	public void create(CustFieldSet entity) {
		custFieldSetQupaiMapper.insert(entity);
	}

	 
	public void createBatch(List<CustFieldSet> entitys) {
		custFieldSetQupaiMapper.insertBatch(entitys);
	}


	public void modifyTrends(CustFieldSet entity) {
		custFieldSetQupaiMapper.updateTrends(entity);
	}

	 
	public void modifyBatch(List<CustFieldSet> entitys) {
		for (CustFieldSet custFieldSet : entitys) {
			custFieldSetQupaiMapper.updateTrends(custFieldSet);
		}
	}

	 
	public void modifyTrendsBatch(List<CustFieldSet> entitys) {
		for (CustFieldSet custFieldSet : entitys) {
			custFieldSetQupaiMapper.updateTrends(custFieldSet);
		}
	}

	 
	public void remove(String id) {
		custFieldSetQupaiMapper.delete(id);
	}

	 
	public void removeBatch(List<String> ids) {
		custFieldSetQupaiMapper.deleteBatch(ids);
	}

	 
	public List<CustFieldSet> queryAllWithOrgId() {
		return custFieldSetQupaiMapper.findAllWithOrgId();
	}

	 
	public List<CustFieldSet> getAllWithOrgId(String orgId) {
		return custFieldSetQupaiMapper.getAllWithOrgId(orgId);
	}

	/** 顺序修改 */
	public void updateSort(List<CustFieldSet> cfs,String orgId){
		for(CustFieldSet cf : cfs){
			cf.setOrgId(orgId);
			custFieldSetQupaiMapper.updateTrends(cf);
		}
	}
	
	/** 根据字段code 模糊查询  获取该字段个数 */
	public Integer getCountByFieldCode(Map<String,Object>map){
		return custFieldSetQupaiMapper.findCountByFieldCode(map);
	}
	
	/** 查询 排序最大值 */
	public Integer getMaxBySort(Map<String,Object>map){
		return custFieldSetQupaiMapper.findMaxBySort(map);
	}
	
	/** 查询 字段名称是否存在 */
	public Integer getFieldNameIsExists(Map<String,Object>map){
		return custFieldSetQupaiMapper.findFieldNameIsExists(map);
	}
	
	/** 查询排序区间的数据 */
	public List<CustFieldSet> getAllBySortInterval(Map<String,Object>map){
		return custFieldSetQupaiMapper.findAllBySortInterval(map);
	}
	
	/**  根据 字段类型查询数据 */
	public  List<CustFieldSet> getAllByDataType(Map<String,Object>map){
		return custFieldSetQupaiMapper.findAllByDataType(map);
	}
	
	/** 根据字段ID，查询排序值*/
	public List<CustFieldSet> getSortsByFieldId(Map<String,Object>map){
		return custFieldSetQupaiMapper.findSortsByFieldId(map);
	}
	
	/**
	 * 批量修改
	 * @param list
	 */
	public void batchUpdate(List<CustFieldSet>list){
		custFieldSetQupaiMapper.batchUpdate(list);
	}
	
	
	//数据订正
	public void toData(List<String> orgIds){
		
		//自定义字段
		List<CustFieldSet> fieldList=custFieldSetQupaiMapper.findDzsj();
		//查询项
		List<CustSearchSet> searchList=custSearchSetQupaiService.findSjdz();

			if(orgIds!=null && orgIds.size()>0){
				for(String orgId :orgIds){
					if(StringUtils.isNotBlank(orgId)){
						int state=orgService.getStateByOrgId(orgId);
						Map<String,Integer> map=insertOrgIdFieldSet(orgId,fieldList,state);	
						custSearchSetQupaiService.insertOrgIdSearchSet(orgId,searchList,map,state);
						tsmCustWorkflowSetService.insertDzsj(orgId);
					}
				}
			}
		
	}
	
	//自定义字段，数据订正
	public Map<String,Integer> insertOrgIdFieldSet(String orgId,List<CustFieldSet> list,Integer state){
		int cardIdIfshow=1;
		int bankCardIfshow=1;
		int openingBankIfshow=1;
		Map<String,Integer> map=new HashMap<String,Integer>();
		
		CustFieldSet hyxfieldSet = new CustFieldSet();
		hyxfieldSet.setOrgId(orgId);
		if(state ==1 ){//企业
			hyxfieldSet.setState(1); // 企业字段
		}else{//个人
			hyxfieldSet.setState(0); 
		}
		List<CustFieldSet> hyxentitys = custFieldSetService.getListByCondtion(hyxfieldSet);
		
		for(CustFieldSet custField :list){
			custField.setFieldId(SysBaseModelUtil.getModelId());
			custField.setOrgId(orgId);
			custField.setInputerAcc("admin");
			custField.setInputdate(new Date());
			custField.setState(state);
			
			//客户姓名
			if(custField.getFieldCode().equals("custName")){
				if(state ==1 ){//企业
					custField.setFieldName("客户名称");
					custField.setState(1);
				}else{//个人
					custField.setFieldName("客户姓名");
					custField.setState(0);
				}
			}
			
			//身份证号码
			if(custField.getFieldCode().equals("cardId")){
				if(hyxentitys!=null && hyxentitys.size()>0){
					for(CustFieldSet set :hyxentitys){
						if(set.getFieldName().indexOf("身份证")!=-1){
							//存在包含关系
							custField.setFieldValue(set.getFieldCode());
	                    	cardIdIfshow=1;
	                    	custField.setEnable((short)1);
	                    	break;
						}else{
							//不存在包含关系
	                    	//没有身份证号码：
	                    	cardIdIfshow=0;
	                    	custField.setEnable((short)0);
						}
					}
				}
			}
			
			//银行卡号
			if(custField.getFieldCode().equals("bankCard")){
				if(hyxentitys!=null && hyxentitys.size()>0){
					for(CustFieldSet set :hyxentitys){
						if(set.getFieldName().indexOf("银行卡")!=-1){
							//存在包含关系
							custField.setFieldValue(set.getFieldCode());
	                    	bankCardIfshow=1;
	                    	custField.setEnable((short)1);
	                    	break;
						}else{
							//不存在包含关系
	                    	//没有银行卡号：
	                    	bankCardIfshow=0;
	                    	custField.setEnable((short)0);
						}
					}
				}
			}
			
			//开户行
			if(custField.getFieldCode().equals("openingBank")){
				if(hyxentitys!=null && hyxentitys.size()>0){
					for(CustFieldSet set :hyxentitys){
						if(set.getFieldName().indexOf("开户行")!=-1){
							//存在包含关系
							custField.setFieldValue(set.getFieldCode());
	                    	openingBankIfshow=1;
	                    	custField.setEnable((short)1);
	                    	break;
						}else{
							//不存在包含关系
	                    	//没有开户行：
	                    	openingBankIfshow=0;
	                    	custField.setEnable((short)0);
						}
					}
				}
			}
			
		}
		
		map.put("cardIdIfshow", cardIdIfshow);
		map.put("bankCardIfshow", bankCardIfshow);
		map.put("openingBankIfshow", openingBankIfshow);
		
		createBatch(list);
		return map;
	}

}
