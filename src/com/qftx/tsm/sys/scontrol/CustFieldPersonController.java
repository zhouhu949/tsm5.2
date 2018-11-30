package com.qftx.tsm.sys.scontrol;

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
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.service.CustFieldSetService;
import com.qftx.tsm.sys.service.CustSearchSetService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

/**
 * 个人系统字段
 */
@Controller
@RequestMapping("/custField/person")
public class CustFieldPersonController{
	Logger logger=Logger.getLogger(CustFieldPersonController.class);
	@Autowired private CustFieldSetService custFieldSetService;
	@Autowired private CustSearchSetService custSearchSetService;
	@Autowired
	private LogCustInfoService logCustInfoService;
	@Autowired private UserService userService;
	
	/** 
	 * 自定义设置查询 
	 */
	@RequestMapping("/custFieldPersonPage")
	public String custFieldPersonPage(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			CustFieldSet fieldSet = new CustFieldSet();
			fieldSet.setOrgId(user.getOrgId());
			fieldSet.setState(0); // 个人资源
			fieldSet.setOrderKey("sort asc");
			List<CustFieldSet> entitys = custFieldSetService.getListByCondtion(fieldSet);
//			Map<String,Object>map = new HashMap<String, Object>();
//			map.put("total", 1);
//			List<Map<String,Object>>rows = new ArrayList<Map<String,Object>>();
//			for(CustFieldSet cfs : entitys){
//				if(!AppConstant.isMajor.equals(cfs.getFieldCode())){ // 重点关注不需显示
//					Map<String,Object>rowMap = new HashMap<String, Object>();
//					rowMap.put("id", cfs.getFieldId());
//					rowMap.put("sort", cfs.getSort());
//					rowMap.put("operation", "<a href='###' class='icon-edit demoBtn_b' id='"+cfs.getFieldId()+"' title='编辑'></a><i class='fa fa-arrow-circle-o-up icon-20px icon-blue' data-id='"+cfs.getFieldId()+"' data-sort='"+cfs.getSort()+"'></i><i class='fa fa-arrow-circle-o-down icon-20px icon-blue' data-id='"+cfs.getFieldId()+"' data-sort='"+cfs.getSort()+"'></i>");
//					rowMap.put("name", cfs.getFieldName());
//					rowMap.put("type",SysEnum.getEnum1(cfs.getDataType().toString(),SysEnum.getFieldDataType()).getDescr());
//					rowMap.put("required",cfs.getIsRequired()==1?"&radic;":"&chi;"); // 是否必填
//					rowMap.put("onlyread",cfs.getIsRead()==1?"&radic;":"&chi;"); // 是否只读
//					rowMap.put("enable",cfs.getEnable()==1?"&radic;":"&chi;"); // 是否启用
//					rowMap.put("query",cfs.getIsQuery()==1?"&radic;":"--"); // 是否是查询条件
//					rows.add(rowMap);
//				}				
//			}
//			map.put("rows", rows);
			request.setAttribute("entitys", entitys);	
			request.setAttribute("entitysJson", JsonUtil.getJsonString(entitys));			
		}catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/tsm/sys/custField/custFieldPerson";
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
				custFieldSetService.batchUpdate(custFieldSets);			
				// 新增用户操作日志
				LogBean logBean = new LogBean();
				logBean.setOrgId(user.getOrgId());
				logBean.setUserAcc(user.getAccount());
				logBean.setUserName(user.getName());
				logBean.setModuleId(AppConstant.Module_id1020);
				logBean.setModuleName(AppConstant.Module_Name1020);
				logBean.setOperateId( AppConstant.Operate_id67);
				logBean.setOperateName( AppConstant.Operate_Name67);
				logBean.setContent("批量保存");
				logBean.setData(JsonUtil.getJsonString(custFieldSets));
				logCustInfoService.addTableStoreLog(logBean, null);
			}
		
			// 删除缓存
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSET);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSETS);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.MULTI_FILEDSET);  
			final String userOrgId = user.getOrgId();
			final List<CustFieldSet> custFieldSets1 =custFieldSets;
			ExecutorUtils.THREAD_POOL.submit(new Runnable() {				
				public void run() {
					// 循环修改查询项相关自定义字段，时间长度会比较长
					for(CustFieldSet custFieldSet : custFieldSets1){
						/** 以下字段修改，需要同步查询项配置管理 */
						if(custFieldSet.getFieldCode().contains("defined")){
							try {
								custSearchSetService.updateByFieldSet(custFieldSet);
							} catch (Exception e) {
								logger.error("循环修改查询项相关自定义字段 异常！", e);
							}
						}
					}
					// 删除高级查询缓存
					for(int i = 1;i<16;i++){
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +"0"+CachedNames.HIGH_SEARCH_+i);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +"1"+CachedNames.HIGH_SEARCH_+i);
					}
					List<String> accounts = userService.getUserAccounts(userOrgId);
					for(String account : accounts){
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+1);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+2);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+3);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+4);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+5);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+6);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+7);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+8);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+9);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+10);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+11);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+12);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+13);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+14);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+15);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+16);
					}
					CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR + CachedNames.SEARCH_SET);
				}
			});
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e){
			logger.error("个人字段保存异常！", e);
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
			Integer count = new Integer(0);
			// 查询自定义字段 是否是查询条件 个数
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", user.getOrgId());
			map.put("fieldCode", "defined");
			map.put("state", 0); // 个人资源字段
			map.put("isQuery", 1);
			count = custFieldSetService.getCountByFieldCode(map);
			if(StringUtils.isNotBlank(id)){			
				fieldSet.setOrgId(user.getOrgId());
				fieldSet.setFieldId(id);
				fieldSet = custFieldSetService.getByCondtion(fieldSet);	
				if(!fieldSet.getFieldCode().contains("defined")){ 
					request.setAttribute("isDefined", 1); // 不能设为查询字段
				}else{
					if(fieldSet.getIsQuery() == 1){
						count = count - 1;
					}				
				}
			}
			request.setAttribute("count", count); // 自定义 个数
			request.setAttribute("item", fieldSet);
			return "/tsm/sys/custField/idialog/person_custFiled_opera";
		}catch(Exception e){
			logger.error("跳转至 操作弹窗页面 异常！", e);
			return null;
		}		
	}
	
	/** 弹窗页面 操作 */
	@RequestMapping("/opera")
	@	ResponseBody
	public String opera(HttpServletRequest request,CustFieldSet fieldSet){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			fieldSet.setOrgId(user.getOrgId());
			String operateId;
			String operateName;
			if(StringUtils.isNotBlank(fieldSet.getFieldId())){
				// 其他类型不能修改为日期类型
				if(fieldSet.getFieldCode().contains("defined") && fieldSet.getDataType()!=null  && fieldSet.getDataType() == 2){
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
					map1.put("state", 0);
					Integer maxSort = custFieldSetService.getMaxBySort(map1)+1;
					fieldSet.setSort(Short.valueOf(maxSort.toString()));
				}else {
					if(fieldSet.getSort() != fieldSet.getOldSort()){ //修改区间排序
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
				custFieldSetService.modifyTrends(fieldSet);
				/** 以下字段修改，需要同步查询项配置管理 */
				if(fieldSet.getFieldCode().contains("defined")){
					custSearchSetService.updateByFieldSet(fieldSet);
				}
				operateId = AppConstant.Operate_id67;
				operateName = AppConstant.Operate_Name67;
			}else{
				fieldSet.setFieldId(SysBaseModelUtil.getModelId());
				// 获取自定义字段个数,设置自定义字段CODE
				Map<String,Object>map = new HashMap<String, Object>();
				map.put("orgId", user.getOrgId());
				map.put("fieldCode", "defined");
				map.put("state", 0);
				map.put("dataType",fieldSet.getDataType());
				Integer count = custFieldSetService.getCountByFieldCode(map)+1;				
				if(fieldSet.getDataType() != null && fieldSet.getDataType() == 2){ // 日期类型默认是16,17,18					
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
					map1.put("state", 0);
					Integer maxSort = custFieldSetService.getMaxBySort(map1)+1;
					fieldSet.setSort(Short.valueOf(maxSort.toString()));
				}else{ // 修改区间排序
					modfiyIntervalSort(fieldSet.getSort(), null,user.getOrgId(),true);
				}
				fieldSet.setState(0); // 个人
				fieldSet.setOrgId(user.getOrgId());
				fieldSet.setInputerAcc(user.getAccount());
				fieldSet.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
				//  自定义字段 默认查询条件为勾选
				fieldSet.setIsQuery((short)1);		
				custFieldSetService.create(fieldSet);
				
				/** 如果新增字段需要同步查询项配置管理 */				
				custSearchSetService.insertByFiled(fieldSet);
				operateId = AppConstant.Operate_id2;
				operateName = AppConstant.Operate_Name2;
			}
			
			//修改缓存
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSET);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSETS);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.MULTI_FILEDSET);  
			final String userOrgId = user.getOrgId();
			ExecutorUtils.THREAD_POOL.submit(new Runnable() {
				public void run() {
					// 删除高级查询缓存
					for(int i = 1;i<16;i++){
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +"0"+CachedNames.HIGH_SEARCH_+i);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +"1"+CachedNames.HIGH_SEARCH_+i);
					}
					List<String> accounts = userService.getUserAccounts(userOrgId);
					for(String account : accounts){
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+1);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+2);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+3);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+4);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+5);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+6);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+7);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+8);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+9);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+10);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+11);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+12);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+13);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+14);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+15);
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+16);
					}
					CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR + CachedNames.SEARCH_SET);
				}
			});
			
			// 新增用户操作日志
			LogBean logBean = new LogBean();
			logBean.setOrgId(user.getOrgId());
			logBean.setUserAcc(user.getAccount());
			logBean.setUserName(user.getName());
			logBean.setModuleId(AppConstant.Module_id1020);
			logBean.setModuleName(AppConstant.Module_Name1020);
			logBean.setOperateId(operateId);
			logBean.setOperateName(operateName);
			logBean.setContent(fieldSet.getFieldName());
			logCustInfoService.addTableStoreLog(logBean, null);		
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e){
			logger.error("弹窗页面 操作 异常！", e);
			return null;
		}
	}
	
	/** 查询自定义字段 个数 */
	@RequestMapping("/getIdFinedCount")
	@	ResponseBody
	public Integer getIdFinedCount(HttpServletRequest request) throws Exception{
		// 判断新增自定义字段 是否超过10个
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", user.getOrgId());
		map.put("state", 0);
		map.put("fieldCode", "defined");
		Integer count = custFieldSetService.getCountByFieldCode(map);
		return count;		
	}
	
	/** 查询 字段名称是否存在 */
	@RequestMapping("/fieldNameIsExists")
	@	ResponseBody
	public Integer fieldNameIsExists(HttpServletRequest request) throws Exception{
		ShiroUser user = ShiroUtil.getShiroUser();
		String fieldName = request.getParameter("fieldName");
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", user.getOrgId());
		map.put("fieldName", fieldName);
		map.put("state", 0);
		Integer count = custFieldSetService.getFieldNameIsExists(map);
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
			List<CustFieldSet> lists = custFieldSetService.getSortsByFieldId(map);
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
			custFieldSetService.updateSort(custFieldSets,user.getOrgId());
			//修改缓存
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSET);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSETS);
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e){
			logger.error("custFieldContactsControl modfiySort 异常！", e);
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
		map.put("state", "0");
		map.put("minSort", minSort);
		map.put("maxSort",maxSort);
		List<CustFieldSet> list = custFieldSetService.getAllBySortInterval(map);
		if(list!=null && list.size()>0){
			for(CustFieldSet custFieldSet : list){
				if(isAdd){
					custFieldSet.setSort((short) (custFieldSet.getSort()+1));
				}else{
					custFieldSet.setSort((short) (custFieldSet.getSort()-1));
				}				
				custFieldSetService.modifyTrends(custFieldSet);
			}
		}
	}
	
}
