package com.qftx.tsm.credit.scontrol;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.ExecutorUtils;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.credit.service.CustFieldSetQupaiService;
import com.qftx.tsm.credit.service.CustSearchSetQupaiService;
import com.qftx.tsm.credit.service.QupaiOptionService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.service.CustFieldSetService;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

/**
 * qupai统字段
 */
@Controller
@RequestMapping("/custField/quPai")
public class CustFieldQupaiController{
	Logger logger=Logger.getLogger(CustFieldQupaiController.class);
	@Autowired private CustFieldSetQupaiService custFieldSetQupaiService;
	@Autowired private CustFieldSetService custFieldSetService;
	@Autowired private CustSearchSetQupaiService custSearchSetQupaiService;
	@Autowired private UserService userService;
	@Autowired private LogCustInfoService logCustInfoService;
	@Autowired private QupaiOptionService qupaiOptionService;
	/** 
	 * 自定义设置查询 
	 */
	@RequestMapping("/custFieldQupaiPage")
	public String custFieldComPage(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			CustFieldSet fieldSet = new CustFieldSet();
			fieldSet.setOrgId(user.getOrgId());
//			fieldSet.setState(1); // 企业字段
			fieldSet.setOrderKey("sort asc");
			List<CustFieldSet> entitys = custFieldSetQupaiService.getListByCondtion(fieldSet);
			request.setAttribute("entitys", entitys);	
			request.setAttribute("entitysJson", JsonUtil.getJsonString(entitys));	
		}catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/qupai/sys/custField/custFieldCom";
	}
	
	/** 修改 */
	@RequestMapping("/modfiy") 
    @ResponseBody  
    public String modfiy(HttpServletRequest request) { 
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String jsonDate = request.getParameter("jsonDate");
			List<CustFieldSet> custFieldSets = JsonUtil.getListJson(jsonDate, CustFieldSet.class);
			if(custFieldSets != null && custFieldSets.size() > 0){
				for(CustFieldSet custFieldSet : custFieldSets){
					custFieldSet.setModifierAcc(user.getAccount());
					custFieldSet.setModifydate(new Date());
				}
				custFieldSetQupaiService.batchUpdate(custFieldSets);
//				// 新增用户操作日志
//				LogBean logBean = new LogBean();
//				logBean.setOrgId(user.getOrgId());
//				logBean.setUserAcc(user.getAccount());
//				logBean.setUserName(user.getName());
//				logBean.setModuleId(AppConstant.Module_id1018);
//				logBean.setModuleName(AppConstant.Module_Name1018);
//				logBean.setOperateId( AppConstant.Operate_id67);
//				logBean.setOperateName( AppConstant.Operate_Name67);
//				logBean.setContent("批量保存");
//				logBean.setData(JsonUtil.getJsonString(custFieldSets));
//				logCustInfoService.addTableStoreLog(logBean, null);
			}
			// 删除缓存
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_FILEDSET);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_FILEDSETS);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_MULTI_FILEDSET); 
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_OPTION);
			final String userOrgId = user.getOrgId();
			final List<CustFieldSet> custFieldSets1 =custFieldSets;
			ExecutorUtils.THREAD_POOL.submit(new Runnable() {
				public void run() {	
					// 循环修改查询项相关自定义字段，时间长度会比较长
					for(CustFieldSet custFieldSet : custFieldSets1){
						if(custFieldSet.getFieldCode().contains("defined")||"companyTrade".equals(custFieldSet.getFieldCode())){
							try {
								custSearchSetQupaiService.updateByFieldSet(custFieldSet);
							} catch (Exception e) {
							    logger.error("循环修改查询项相关自定义字段异常！", e);
							}
						}
					}
//					// 删除高级查询缓存
					for(int i = 1;i<3;i++){
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +"0"+CachedNames.QUPAi_HIGH_SEARCH_+i);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +"1"+CachedNames.QUPAi_HIGH_SEARCH_+i);
					}
					List<String> accounts = userService.getUserAccounts(userOrgId);
					for(String account : accounts){
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.QUPAi_HIGH_SEARCH_+1);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.QUPAi_HIGH_SEARCH_+2);
					}
					CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR + CachedNames.QUPAI_SEARCH_SET);
				}
			});
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e){
			logger.error("企业字段保存异常！", e);
			return AppConstant.RESULT_EXCEPTION;
		}
    }
	
	/**  跳转至 操作弹窗页面 */
	@RequestMapping("/reditPage")
	public String reditPage(HttpServletRequest request){
		try{
			String id = request.getParameter("id");
			ShiroUser user = ShiroUtil.getShiroUser();
			CustFieldSet fieldSet = new CustFieldSet();
			CustFieldSet hyxfieldSet = new CustFieldSet();
			Map<String,Object> retMap=new HashMap<String,Object>();
			List<OptionBean> optionList=new ArrayList<OptionBean>();
			Integer count = new Integer(0);
			// 查询自定义字段 是否是查询条件 个数
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", user.getOrgId());
			map.put("fieldCode", "defined");
//			map.put("state", 1); // 企业资源字段
			map.put("isQuery", 1);
			count = custFieldSetQupaiService.getCountByFieldCode(map);
			if(StringUtils.isNotBlank(id)){			
				fieldSet.setOrgId(user.getOrgId());
				fieldSet.setFieldId(id);
				fieldSet = custFieldSetQupaiService.getByCondtion(fieldSet);	
				request.setAttribute("isEditPage", 1); 
				if(!fieldSet.getFieldCode().contains("defined")){ 
					request.setAttribute("isDefined", 1); // 不能设为查询字段
				}else{
					if(fieldSet.getIsQuery() == 1){
						count = count - 1;
					}	
				}	
				
				if(fieldSet != null && fieldSet.getDataType()!=null){
				    //单选或者多选
					if(fieldSet.getDataType() ==3 || fieldSet.getDataType() == 4){
						OptionBean entity=new OptionBean();
						entity.setOrgId(user.getOrgId());
						entity.setItemCode(fieldSet.getFieldCode());
						optionList = qupaiOptionService.getListByCondtion(entity);
						
					}
				}
			}
			
			
			
			//引用
				hyxfieldSet.setOrgId(user.getOrgId());
				int resType = ShiroUtil.getShiroUser().getIsState();
				if(resType ==1 ){//企业
					hyxfieldSet.setState(1); // 企业字段
				}else{//个人
					hyxfieldSet.setState(0); // 企业字段
				}
				hyxfieldSet.setOrderKey("sort asc");
				hyxfieldSet.setDataType(1);
				List<CustFieldSet> entitys = custFieldSetService.getListByCondtion(hyxfieldSet);
				
				if(resType ==1){
					CustFieldSet newSet=new CustFieldSet();
					newSet.setFieldName("常用电话");
					newSet.setFieldCode("mobilephone");
					entitys.add(newSet);
				}
				//children
				retMap.put("fileName", "客户");
				retMap.put("children", entitys);
			
				request.setAttribute("optionList", optionList);//单选多选
				request.setAttribute("hyxItem", retMap);//引用数据
				request.setAttribute("count", count); // 自定义 个数
				request.setAttribute("item", fieldSet);
				return "/qupai/sys/custField/idialog/com_custFiled_opera";
		}catch(Exception e){
			logger.error("跳转至 操作弹窗页面 异常！", e);
			return null;
		}		
	}
	
	/** 弹窗页面 操作 */
	@RequestMapping("/opera")
	@ResponseBody
	public String opera(HttpServletRequest request,CustFieldSet fieldSet){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			fieldSet.setOrgId(user.getOrgId());
			String operateId;
			String operateName;
			if(StringUtils.isNotBlank(fieldSet.getFieldId())){
				// 其他类型不能修改为日期类型
				if(fieldSet.getFieldCode().contains("defined") && fieldSet.getDataType()!=null && fieldSet.getDataType() == 2){
					Integer count = Integer.parseInt(fieldSet.getFieldCode().substring(7,fieldSet.getFieldCode().length()));
					if(count <16){
						return "3"; // 保存失败！无法将字段类型变更为日期类型
					}
				}
				
				fieldSet.setModifierAcc(user.getAccount());
				fieldSet.setModifydate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
				if(fieldSet.getSort() != null && fieldSet.getSort()==1){// 如输入值为1自当将该值返回变为2(因为排序为1的字段默认为名称)
					fieldSet.setSort((short) 2);
				}
				if(fieldSet.getSort()==null ){					
					boolean isAdd = false;
					Short minSort = fieldSet.getOldSort();
					modfiyIntervalSort(minSort,null,user.getOrgId(),isAdd);
					// 获取最大排序,设置排序+1
					Map<String,Object>map1 = new HashMap<String, Object>();
					map1.put("orgId", user.getOrgId());
//					map1.put("state", 1);
					Integer maxSort = custFieldSetQupaiService.getMaxBySort(map1)+1;
					fieldSet.setSort(Short.valueOf(maxSort.toString()));
				}else {
					if(!fieldSet.getSort().equals(fieldSet.getOldSort())){ //修改区间排序
						boolean isAdd = true;
						Short minSort = fieldSet.getSort();
						Short maxSort = fieldSet.getOldSort();
						if(fieldSet.getSort() > fieldSet.getOldSort()){
							minSort = fieldSet.getOldSort();
							maxSort = fieldSet.getSort();
							isAdd = false;
						}
						modfiyIntervalSort(minSort,maxSort,user.getOrgId(),isAdd);
					}
				}	
				//  自定义字段 默认查询条件为勾选
				if(fieldSet.getFieldCode().contains("defined")){
					fieldSet.setIsQuery((short)1);
				}	
				custFieldSetQupaiService.modifyTrends(fieldSet);
				/** 以下字段修改，需要同步查询项配置管理 */
				if(fieldSet.getFieldCode().contains("defined")||"companyTrade".equals(fieldSet.getFieldCode())){
					custSearchSetQupaiService.updateByFieldSet(fieldSet);
				}
				
				operateId = AppConstant.Operate_id67;
				operateName = AppConstant.Operate_Name67;
			}else{				
				fieldSet.setFieldId(SysBaseModelUtil.getModelId());
				// 获取自定义字段个数,设置自定义字段CODE
				Map<String,Object>map = new HashMap<String, Object>();
				map.put("orgId", user.getOrgId());
				map.put("fieldCode", "defined");
//				map.put("state", 1); // 企业资源字段			
				map.put("dataType",fieldSet.getDataType());
				Integer count = custFieldSetQupaiService.getCountByFieldCode(map)+1;				
				
				if(fieldSet.getDataType() == 2){ // 日期类型默认是16,17,18					
					//  新增自定义日期类型最多3个
					if(count>3){
						return "4"; // 保存失败！日期类型的自定义字段最多为3个
					}
					count = count+15;
				}
				if(fieldSet.getDataType() != null && fieldSet.getDataType() != 2){
					if(count>15){
						return "5"; // 保存失败！除日期类型的自定义字段最多为15个
					}					
				}
				fieldSet.setFieldCode("defined"+count);
				if(fieldSet.getSort()==null){
					// 获取最大排序,设置排序+1
					Map<String,Object>map1 = new HashMap<String, Object>();
					map1.put("orgId", user.getOrgId());
//					map1.put("state", 1);
					Integer maxSort = custFieldSetQupaiService.getMaxBySort(map1)+1;
					fieldSet.setSort(Short.valueOf(maxSort.toString()));
				}else{ // 修改区间排序
					modfiyIntervalSort(fieldSet.getSort(), null,user.getOrgId(),true);
				}
//				fieldSet.setState(1); // 企业资源字段
				fieldSet.setOrgId(user.getOrgId());
				fieldSet.setInputerAcc(user.getAccount());
				fieldSet.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
				//  自定义字段 默认查询条件为勾选
				fieldSet.setIsQuery((short)1);			
				custFieldSetQupaiService.create(fieldSet);
				
				/** 如果新增字段查询项勾选，需要同步查询项配置管理 */
				custSearchSetQupaiService.insertByFiled(fieldSet);
				operateId = AppConstant.Operate_id2;
				operateName = AppConstant.Operate_Name2;
			}
			
			
			if(fieldSet.getDataType()!=null&&(fieldSet.getDataType()==3 || fieldSet.getDataType()==4)){
					OptionBean entity=new OptionBean();
					entity.setOrgId(user.getOrgId());
					entity.setItemCode(fieldSet.getFieldCode());
					List<OptionBean> select_options = qupaiOptionService.getListByCondtion(entity);
				
					List<String> ids=new ArrayList<String>();
					List<Map> optList =fieldSet.getDataJson();
					if(optList!=null && optList.size()>0){
						for(Map map : optList){
							OptionBean optionBean=new OptionBean();
							optionBean.setOptionlistId((String)(map.get("optionlistId") == null ? "" : map.get("optionlistId")));
							optionBean.setOptionName((String)(map.get("optionName") ==null ?"" :map.get("optionName")));
							optionBean.setIsDefault(Short.parseShort(((String)map.get("isDefaultValue") ==null ? "0": (String)map.get("isDefaultValue"))));
							if(StringUtils.isNotBlank(optionBean.getOptionlistId())){
								ids.add(optionBean.getOptionlistId());
								//update
								optionBean.setOrgId(user.getOrgId());
								qupaiOptionService.modifyTrends(optionBean);

								
							}else{//insert
								optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
								optionBean.setInputerAcc(user.getAccount());
								optionBean.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
								optionBean.setOrgId(user.getOrgId());
								optionBean.setItemCode(fieldSet.getFieldCode());
								qupaiOptionService.create(optionBean);
							}
						}
					}
					

					
					List<String> removeIds=new ArrayList<String>();
			        if(ids!=null && ids.size()>0){
					if(select_options!=null&& select_options.size()>0 ){
						Iterator<OptionBean>it = select_options.iterator();
						while(it.hasNext()){
							OptionBean option = it.next();
							for(String id :ids){
								if(option.getOptionlistId().equals(id)){
									it.remove();
								}
							}
						}
					 	

			        } 
			        }
			        if(select_options!=null && select_options.size()>0){
			        	for(OptionBean option:select_options){
			        		qupaiOptionService.remove(option.getOptionlistId());	
			        	}
			        }
			}
			
	
//			// 新增用户操作日志
//			LogBean logBean = new LogBean();
//			logBean.setOrgId(user.getOrgId());
//			logBean.setUserAcc(user.getAccount());
//			logBean.setUserName(user.getName());
//			logBean.setModuleId(AppConstant.Module_id1018);
//			logBean.setModuleName(AppConstant.Module_Name1018);
//			logBean.setOperateId(operateId);
//			logBean.setOperateName(operateName);
//			logBean.setContent(fieldSet.getFieldName());
//			logCustInfoService.addTableStoreLog(logBean, null);	
			
			//修改缓存
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_FILEDSET);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_FILEDSETS);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_MULTI_FILEDSET); 
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_OPTION);
			final String userOrgId = user.getOrgId();
			ExecutorUtils.THREAD_POOL.submit(new Runnable() {
				public void run() {	
//					// 删除高级查询缓存
					for(int i = 1;i<3;i++){
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +"0"+CachedNames.QUPAi_HIGH_SEARCH_+i);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +"1"+CachedNames.QUPAi_HIGH_SEARCH_+i);
					}
					List<String> accounts = userService.getUserAccounts(userOrgId);
					for(String account : accounts){
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.QUPAi_HIGH_SEARCH_+1);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.QUPAi_HIGH_SEARCH_+2);
					}
					CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR + CachedNames.QUPAI_SEARCH_SET);
				}
			});
			
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e){
			logger.error("弹窗页面 操作 异常！", e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}
	
	
	
	
	/** 查询自定义字段 个数 */
	@RequestMapping("/getIdFinedCount")
	@ResponseBody
	public Integer getIdFinedCount(HttpServletRequest request) throws Exception{
		// 判断新增自定义字段 是否超过10个
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", user.getOrgId());
//		map.put("state", 1);
		map.put("fieldCode", "defined");
		Integer count = custFieldSetQupaiService.getCountByFieldCode(map);
		return count;		
	}
	
	/** 查询 字段名称是否存在 */
	@RequestMapping("/fieldNameIsExists")
	@ResponseBody
	public Integer fieldNameIsExists(HttpServletRequest request) throws Exception{
		ShiroUser user = ShiroUtil.getShiroUser();
		String fieldName = request.getParameter("fieldName");
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", user.getOrgId());
		map.put("fieldName", fieldName);
//		map.put("state", 1);
		Integer count = custFieldSetQupaiService.getFieldNameIsExists(map);
		return count;
	}
	
	/** 修改排序  */
	@RequestMapping("/modfiySort") 
    @ResponseBody  
    public String saveUser(HttpServletRequest request) { 
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String currRowId = request.getParameter("currRowId");
			String changedRowId = request.getParameter("changedRowId");
			String changedRowSort = "";
			String currRowSort = "";
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", user.getOrgId());
			List<String>list = new ArrayList<String>();
			list.add(currRowId);
			list.add(changedRowId);
			map.put("list", list);
			List<CustFieldSet> lists = custFieldSetQupaiService.getSortsByFieldId(map);
			map.clear();
			for(CustFieldSet custFieldSet : lists){
				if(custFieldSet.getFieldId().equals(currRowId)){
					changedRowSort = custFieldSet.getSort().toString();
				}else if(custFieldSet.getFieldId().equals(changedRowId)){
					currRowSort = custFieldSet.getSort().toString();
				}
			}
			List<CustFieldSet> custFieldSets = new ArrayList<CustFieldSet>();
			CustFieldSet custFieldSet = new CustFieldSet();
			custFieldSet.setFieldId(currRowId);
			custFieldSet.setSort(Short.parseShort(currRowSort));
			custFieldSets.add(custFieldSet);
			custFieldSet = new CustFieldSet();
			custFieldSet.setFieldId(changedRowId);
			custFieldSet.setSort(Short.parseShort(changedRowSort));
			custFieldSets.add(custFieldSet);
			custFieldSetQupaiService.updateSort(custFieldSets,user.getOrgId()); //40788ee65ca24c7295a0404670d27692
			//修改缓存
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_FILEDSET);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_FILEDSETS);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_OPTION);
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e){
			logger.error("custFieldComControl modfiySort 异常！", e);
			 return AppConstant.RESULT_FAILURE;
		}
    } 
	
	/**
	 *  修改序号区间排序
	 * @param minSort   区间开始值
	 * @param maxSort  区间结束值
	 * @param orgId		
	 * @param isAdd		true:区间值+1，false:区间值-1	
	 * @return
	 */
	public void modfiyIntervalSort(Short minSort,Short maxSort,String orgId,boolean isAdd)throws Exception{
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", orgId);
//		map.put("state", "1");
		map.put("minSort", minSort);
		map.put("maxSort",maxSort);
		List<CustFieldSet> list = custFieldSetQupaiService.getAllBySortInterval(map);
		if(list!=null && list.size()>0){
			for(CustFieldSet custFieldSet : list){
				if(isAdd){
					custFieldSet.setSort((short) (custFieldSet.getSort()+1));
				}else{
					custFieldSet.setSort((short) (custFieldSet.getSort()-1));
				}				
				custFieldSetQupaiService.modifyTrends(custFieldSet);
			}
		}
	}
	
	
	/** 禁用 */
	@RequestMapping("/unable")
	@ResponseBody
	public String unable(HttpServletRequest request,CustFieldSet fieldSet){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			fieldSet.setOrgId(user.getOrgId());
			if(StringUtils.isNotBlank(fieldSet.getFieldId())){
				
				fieldSet.setModifierAcc(user.getAccount());
				fieldSet.setModifydate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
				fieldSet.setEnable((short)0);
					
				//  自定义字段 默认查询条件为勾选
				if(fieldSet.getFieldCode().contains("defined")){
					fieldSet.setIsQuery((short)1);
				}	
				custFieldSetQupaiService.modifyTrends(fieldSet);
				/** 以下字段修改，需要同步查询项配置管理 */
				if(fieldSet.getFieldCode().contains("defined")||"companyTrade".equals(fieldSet.getFieldCode())){
					custSearchSetQupaiService.updateByFieldSet(fieldSet);
				}

			}
	
//			// 新增用户操作日志
//			LogBean logBean = new LogBean();
//			logBean.setOrgId(user.getOrgId());
//			logBean.setUserAcc(user.getAccount());
//			logBean.setUserName(user.getName());
//			logBean.setModuleId(AppConstant.Module_id1018);
//			logBean.setModuleName(AppConstant.Module_Name1018);
//			logBean.setOperateId(AppConstant.Operate_id67);
//			logBean.setOperateName(AppConstant.Operate_Name67);
//			logBean.setContent(fieldSet.getFieldName());
//			logCustInfoService.addTableStoreLog(logBean, null);	
			
			//修改缓存
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_FILEDSET);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_FILEDSETS);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_MULTI_FILEDSET); 
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_OPTION);
			final String userOrgId = user.getOrgId();
			ExecutorUtils.THREAD_POOL.submit(new Runnable() {
				public void run() {	
					// 删除高级查询缓存
					for(int i = 1;i<3;i++){
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +"0"+CachedNames.QUPAi_HIGH_SEARCH_+i);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +"1"+CachedNames.QUPAi_HIGH_SEARCH_+i);
					}
					List<String> accounts = userService.getUserAccounts(userOrgId);
					for(String account : accounts){
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.QUPAi_HIGH_SEARCH_+1);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.QUPAi_HIGH_SEARCH_+2);
					}
					CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR + CachedNames.QUPAI_SEARCH_SET);
				}
			});
			
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e){
			logger.error("禁用 操作 异常！", e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}

	public static void main(String[] args) {
		String str = "definde1";
		String count = str.substring(7,str.length());
		System.out.println(count);
	}
	
}
