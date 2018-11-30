package com.qftx.tsm.option.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.dto.HttpJsonReturn;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.ExecutorUtils;
import com.qftx.base.util.HttpUtil;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.bean.OptionGroupBean;
import com.qftx.tsm.option.dto.OptionModel;
import com.qftx.tsm.option.service.DataDictionaryService;
import com.qftx.tsm.option.service.OptionGroupService;
import com.qftx.tsm.option.service.OptionService;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.Product;
import com.qftx.tsm.sys.service.ProductService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

/***
 * 系统属性设置
 * @author: zwj
 * @since: 2015-12-4  上午9:59:25
 * @history: 4.x
 */
@Controller
@RequestMapping("/opt/set")
public class OptionSetControl {
	@Autowired
	private OptionService optionService;
	@Autowired
	private OptionGroupService optionGroupService;
	@Autowired
	private ProductService productService;
	@Autowired
	private DataDictionaryService dataDictionaryService;
	@Autowired
	private LogCustInfoService logCustInfoService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private UserService userService;
	Logger logger=Logger.getLogger(OptionSetControl.class);
	
	/***************************【慧营销4.0 开始】******************************************/
	
	/** 系统属性设置页面 */
	@RequestMapping("/propertyset")
	public String showOptionList(HttpServletRequest request){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			List<CustFieldSet> list = cachedService.getMultiFiledSet(shiroUser.getOrgId(),shiroUser.getIsState());
			
			request.setAttribute("multiList", list);
		}catch(Exception e){
			logger.error("propertyset 异常！", e);
		}		
		return "/tsm/option/property_set";
	}
	
	/** 系统属性 销售产品维护页面 */
	@RequestMapping("/propertyset_pro")
	public String showOptionProList(HttpServletRequest request,Product entity ) throws Exception{
		ShiroUser shiroUser = ShiroUtil.getShiroUser();
		entity.setOrgId(shiroUser.getOrgId());
		entity.setIsDel(0);
		entity.setOrderKey("sort asc");
		List<Product> pros = productService.getListPage(entity);
		request.setAttribute("pros", pros);
		request.setAttribute("item", entity);
		return "/tsm/option/iframe/property_set_product";
	}
	
	/** 系统属性 销售产品编辑页面 */
	@RequestMapping("/propertyset_pro_modfiy")
	public String showOptionProModfiy(HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			Product entity = new Product();
			entity.setOrgId(shiroUser.getOrgId());
			entity.setId(id);
			entity = productService.getByCondtion(entity);
			request.setAttribute("item", entity);
		}		
		return "/tsm/option/idialog/property_set_modfiy_pro";
	}
	
	/** 销售产品维护 设置  */
	@RequestMapping("/proSet")
	@ResponseBody
	public String proSet(HttpServletRequest request,Product product){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			String  operateId;
			String operateName;
			if(StringUtils.isNotBlank(product.getId())){
				operateId = AppConstant.Operate_id67;
				operateName =  AppConstant.Operate_Name67;
				// 修改 产品
				product.setOrgId(shiroUser.getOrgId());
				product.setModifierAcc(shiroUser.getAccount());
				product.setModifydate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
				productService.modifyTrends(product);
			}else{
				operateId = AppConstant.Operate_id2;
				operateName =  AppConstant.Operate_Name2;
				// 新增 产品
				product.setId(SysBaseModelUtil.getModelId());
				product.setOrgId(shiroUser.getOrgId());
				product.setInputAcc(shiroUser.getAccount());
				product.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
				product.setIsDefault(0);
				productService.create(product);
			}	
		
			// 新增用户操作日志
			LogBean logBean = new LogBean();
			logBean.setOrgId(shiroUser.getOrgId());
			logBean.setUserAcc(shiroUser.getAccount());
			logBean.setUserName(shiroUser.getName());
			logBean.setModuleId(AppConstant.Module_id1008);
			logBean.setModuleName(AppConstant.Module_Name1008);
			logBean.setOperateId(operateId);
			logBean.setOperateName(operateName);
			logBean.setContent(product.getName());
			logCustInfoService.addTableStoreLog(logBean, null);
			
			//修改缓存
			CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.OPTIONPRODUCT);
			return AppConstant.RESULT_SUCCESS;
		}catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	
	/** 销售产品维护 删除  */
	@RequestMapping("/proDel")
	@ResponseBody
	public String proDel(HttpServletRequest request){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			if(StringUtils.isNotBlank(ids)){
				String[] ids_= ids.split("\\,");
				List<String> list = Arrays.asList(ids_);
				Map<String,Object>map = new HashMap<String, Object>();
				map.put("orgId",shiroUser.getOrgId());
				map.put("list",list);
				map.put("modifierAcc", shiroUser.getAccount());
				productService.removeBatch(map);
				List<String>names = productService.getProductNames(map);
				StringBuffer sbf = new StringBuffer();
				if(names != null && names.size() >0){
					for(String name : names){
						sbf.append(name);
						sbf.append(";");
					}
				}
				// 新增用户操作日志
				LogBean logBean = new LogBean();
				logBean.setOrgId(shiroUser.getOrgId());
				logBean.setUserAcc(shiroUser.getAccount());
				logBean.setUserName(shiroUser.getName());
				logBean.setModuleId(AppConstant.Module_id1008);
				logBean.setModuleName(AppConstant.Module_Name1008);
				logBean.setOperateId(AppConstant.Operate_id5);
				logBean.setOperateName(AppConstant.Operate_Name5);
				logBean.setContent(sbf.toString());
				logCustInfoService.addTableStoreLog(logBean, null);
			}
			//修改缓存
			CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.OPTIONPRODUCT);
			return AppConstant.RESULT_SUCCESS;
		}catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	
	/** 销售产品维护 设置默认  */
	@RequestMapping("/proSetDefault")
	@ResponseBody
	public String proSetDefault(HttpServletRequest request){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id)){
				Product entity = new Product();
				entity.setOrgId(shiroUser.getOrgId());
				entity.setIsDel(0);
				List<Product> optList  = productService.getListByCondtion(entity);
				for (Product product : optList) {
					product.setModifierAcc(shiroUser.getAccount());
					product.setModifydate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));					
					if(id.equals(product.getId())){
						if(product.getIsDefault() != null && product.getIsDefault() == 1){ // 设置默认与取消默认 同一方法
							product.setIsDefault(0);
						}else{
							product.setIsDefault(1);
						}						
					}else{
						product.setIsDefault(0);
					}
				}
				// 批量修改
				productService.modifyTrendsBatch(optList);												
				//修改缓存
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.OPTIONPRODUCT);
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.COM_FILEDSETS);
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSETS);
			}

			return AppConstant.RESULT_SUCCESS;
		}catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	
	/** 查询产品 排序是否唯一 */
	@RequestMapping("/judgeOnlyProSort")
	@ResponseBody
	public String judgeOnlyProSort(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String sort = request.getParameter("sort");
			String id = request.getParameter("id");
			Product entity = new Product();
			entity.setOrgId(user.getOrgId());
			entity.setSort(Integer.parseInt(sort));			
			entity.setIsDel(0);
			if(StringUtils.isNotBlank(id)){ // 修改
				entity.setId(id);
			}
			Integer count = productService.getSortByExists(entity);			
			if(count != null && count>0){
				return AppConstant.RESULT_FAILURE;
			}	
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e){
			throw new SysRunException(e);
		}
	}
	
	/** 【系统属性设置页面】 销售进程，目标客户分类，流失客户原因，录音范例分类，行动标签维护 */
	@RequestMapping("/propertyset_option")
	public String showOptionsList(HttpServletRequest request,OptionBean optionBean) throws Exception{
		ShiroUser shiroUser = ShiroUtil.getShiroUser();
		String result = "";
		String itemCode = request.getParameter("itemCode");	
		String groupId = request.getParameter("groupId_c");	
		String defaultC = request.getParameter("default_c"); // 是否可编辑：0-否；1-是
		if(StringUtils.isNotBlank(itemCode)){
			if(AppConstant.SALES_TYPE_ONE.equals(itemCode)){ // 销售进程
				List<OptionBean> process = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, shiroUser.getOrgId());
				request.setAttribute("process", process);
				result = "/tsm/option/iframe/property_set_process";
			}else if(AppConstant.SALES_TYPE_TWO.equals(itemCode)){ // 目标客户分类
				result="/tsm/option/iframe/property_set_target";
			}else if(AppConstant.SALES_TYPE_WAST.equals(itemCode)){ // 流失客户原因
				result="/tsm/option/iframe/property_set_loss";
			}else if(AppConstant.SALES_CLUE_TYPE.equals(itemCode)){ // 线索类型
				result="/tsm/option/iframe/property_set_clue";
			}else if(AppConstant.RECORD_CODE.equals(itemCode)){ // 录音范例分类
				result="/tsm/option/iframe/property_set_record";
			}else if(AppConstant.SALES_TYPE_TEN.equals(itemCode)){ // 行动标签维护
				if(StringUtils.isNotBlank(groupId)){
					optionBean.setGroupId(groupId);
				}
				result="/tsm/option/iframe/property_set_tags";
			}else if(AppConstant.SALES_TYPE_12.equals(itemCode)){ // 服务标签维护
				if(StringUtils.isNotBlank(groupId)){
					optionBean.setGroupId(groupId);
				}
				result="/tsm/option/iframe/property_set_service_tags";
			}else if(AppConstant.SALES_TYPE_11.equals(itemCode)){ // 服务评级维护
				result="/tsm/option/iframe/property_set_service_grade";
			}else{
				result="/tsm/option/iframe/property_set_multi"; // 单选、多选类型字段
			}
		}
		
		optionBean.setOrgId(shiroUser.getOrgId());		
		optionBean.setItemCode(itemCode);
		optionBean.setOrderKey("sort asc");
		List<OptionBean>optionList = optionService.getListPage(optionBean);
		
		// 行动标签、服务标签 需做分组名称显示处理
		if(AppConstant.SALES_TYPE_TEN.equals(itemCode) || AppConstant.SALES_TYPE_12.equals(itemCode)){
			List<OptionGroupBean> list =  cachedService.getOptionGroupList(itemCode, shiroUser.getOrgId());
			request.setAttribute("items", list);
			Map<String,String>gMap = new HashMap<String, String>();
			for(OptionGroupBean gb : list){
				gMap.put(gb.getGroupId(), gb.getGroupName());
			}
			for(OptionBean opb : optionList){
				opb.setGroupName(gMap.get(opb.getGroupId()) != null ?gMap.get(opb.getGroupId()) : "" );
			}
			request.setAttribute("defaultC", defaultC);
		}
		
		request.setAttribute("optionList", optionList);
		request.setAttribute("item", optionBean);
		
		return result;
	}
	
	/** 【系统属性设置 编辑页面】 单选、多选类型字段 */
	@RequestMapping("/propertyset_modfiy_multi")
	public String showOptionModfiyMulti(HttpServletRequest request){
		try{
			String id = request.getParameter("id");
			String code = request.getParameter("code");
			OptionBean entity = new OptionBean();
			entity.setItemCode(code);
			if(StringUtils.isNotBlank(id)){
				ShiroUser shiroUser = ShiroUtil.getShiroUser();
			
				entity.setOrgId(shiroUser.getOrgId());
				entity.setOptionlistId(id);
				entity = optionService.getByCondtion(entity);	
			}		
			request.setAttribute("item", entity);
		}catch(Exception e){
			logger.error("propertyset_modfiy_multi 异常！ ", e);
		}
		
		return "/tsm/option/idialog/property_set_modfiy_multi";
	}
	
	/** 【系统属性设置 编辑页面】 标签 */
	@RequestMapping("/propertyset_modfiy_tag")
	public String propertysetModfiyTag(HttpServletRequest request){
		try{
			String id = request.getParameter("id");
			String code = request.getParameter("code");
			String groupId = request.getParameter("groupId");
			OptionBean entity = new OptionBean();
			entity.setGroupId(groupId);
			entity.setItemCode(code);
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			if(StringUtils.isNotBlank(id)){
				entity.setOrgId(shiroUser.getOrgId());
				entity.setOptionlistId(id);
				entity = optionService.getByCondtion(entity);	
			}		
			List<OptionGroupBean> list =  cachedService.getOptionGroupList(code, shiroUser.getOrgId());
			request.setAttribute("groups", list);
			request.setAttribute("item", entity);
			
		}catch(Exception e){
			logger.error("propertyset_modfiy_tag 异常！ ", e);
		}
		
		return "/tsm/option/idialog/property_set_modfiy_tag";
	}
	
	/** 【系统属性设置 编辑页面】 目标客户分类，流失客户原因，录音范例分类，行动标签维护 */
	@RequestMapping("/propertyset_modfiy")
	public String showOptionModfiy(HttpServletRequest request){
		try{
			String id = request.getParameter("id");
			String code = request.getParameter("code");
			OptionBean entity = new OptionBean();
			entity.setItemCode(code);
			if(StringUtils.isNotBlank(id)){
				ShiroUser shiroUser = ShiroUtil.getShiroUser();
			
				entity.setOrgId(shiroUser.getOrgId());
				entity.setOptionlistId(id);
				entity = optionService.getByCondtion(entity);	
			}		
			request.setAttribute("item", entity);
		}catch(Exception e){
			logger.error("propertyset_modfiy 异常！ ", e);
		}
		return "/tsm/option/idialog/property_set_modfiy";
	}
	
	/** 【系统属性设置 编辑页面】 销售进程 */
	@RequestMapping("/propertyset_modfiy_process")
	public String showOptionModfiyProcess(HttpServletRequest request){
		try{
			String id = request.getParameter("id");
			String code = request.getParameter("code");
			OptionBean entity = new OptionBean();
			entity.setItemCode(code);
			if(StringUtils.isNotBlank(id)){
				ShiroUser shiroUser = ShiroUtil.getShiroUser();
			
				entity.setOrgId(shiroUser.getOrgId());
				entity.setOptionlistId(id);
				entity = optionService.getByCondtion(entity);	
			}		
			request.setAttribute("item", entity);
		}catch(Exception e){
			logger.error("propertyset_modfiy_process 异常！ ", e);
		}
		return "/tsm/option/idialog/property_set_modfiy_process";
	}
	
	/** 【系统属性设置操作】 销售进程，目标客户分类，流失客户原因，录音范例分类，行动标签维护 */
	@RequestMapping("/addOrEditOption")
	@ResponseBody
	public String addOrEditOption(HttpServletRequest request,OptionBean optionBean){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			String moduleId;
			String moduleName;
			String operateId;
			String operateName;
			if(StringUtils.isNotBlank(optionBean.getOptionlistId())){
				// 修改数据项
				optionBean.setModifierAcc(shiroUser.getAccount());
				optionBean.setModifydate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));	
				optionBean.setOrgId(shiroUser.getOrgId());
				optionService.modifyTrends(optionBean);
				operateId = AppConstant.Operate_id67;
				operateName = AppConstant.Operate_Name67;
			}else{
				// 新增数据项
				optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
				optionBean.setInputerAcc(shiroUser.getAccount());
				optionBean.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
				optionBean.setIsDefault((short)0);
				optionBean.setOrgId(shiroUser.getOrgId());
				// 行动标签、服务标签 需要增加分组关联
				if(AppConstant.SALES_TYPE_TEN.equals(optionBean.getItemCode()) 
						|| AppConstant.SALES_TYPE_12.equals(optionBean.getItemCode())){
					if(StringUtils.isBlank(optionBean.getGroupId())){
						OptionGroupBean entity = new OptionGroupBean();
						entity.setOrgId(shiroUser.getOrgId());
						entity.setItemCode(optionBean.getItemCode());
						entity.setIsDefault(0);
						// 查询默认分组，如果为空需要新增
						OptionGroupBean bean = optionGroupService.getByCondtion(entity);
						if(bean == null){
							bean = new OptionGroupBean();
							bean.setGroupId(SysBaseModelUtil.getModelId());
							bean.setInputerAcc(shiroUser.getAccount());
							bean.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
							bean.setIsDefault(0);
							bean.setOrgId(shiroUser.getOrgId());
							bean.setItemCode(optionBean.getItemCode());
							optionGroupService.create(bean);
						}
						optionBean.setGroupId(bean.getGroupId());
					}
					
				}
				optionService.create(optionBean);
				operateId = AppConstant.Operate_id2;
				operateName = AppConstant.Operate_Name2;
			}
			if(AppConstant.SALES_TYPE_ONE.equals(optionBean.getItemCode())){ // 销售进程
				moduleId = AppConstant.Module_id1009;
				moduleName = AppConstant.Module_Name1009;
			}else if(AppConstant.SALES_TYPE_TWO.equals(optionBean.getItemCode())){ // 目标客户分类
				moduleId = AppConstant.Module_id1010;
				moduleName = AppConstant.Module_Name1010;
			}else if(AppConstant.SALES_TYPE_WAST.equals(optionBean.getItemCode())){ // 流失客户原因
				moduleId = AppConstant.Module_id1012;
				moduleName = AppConstant.Module_Name1012;
			}else if(AppConstant.RECORD_CODE.equals(optionBean.getItemCode())){ // 录音范例分类
				moduleId = AppConstant.Module_id1013;
				moduleName = AppConstant.Module_Name1013;
			}else if(AppConstant.SALES_TYPE_TEN.equals(optionBean.getItemCode())){ // 行动标签维护
				moduleId = AppConstant.Module_id1014;
				moduleName = AppConstant.Module_Name1014;
			}else if(AppConstant.SALES_TYPE_12.equals(optionBean.getItemCode())){ // 服务标签维护
				moduleId = AppConstant.Module_id1016;
				moduleName = AppConstant.Module_Name1016;
			}else if(AppConstant.SALES_TYPE_11.equals(optionBean.getItemCode())){ // 服务评级维护
				moduleId = AppConstant.Module_id1015;
				moduleName = AppConstant.Module_Name1015;
			}else{ // 单选、多选类型字段
				moduleId = AppConstant.Module_id1017;
				moduleName = AppConstant.Module_Name1017; 
			}
		
			// 新增用户操作日志
			LogBean logBean = new LogBean();
			logBean.setOrgId(shiroUser.getOrgId());
			logBean.setUserAcc(shiroUser.getAccount());
			logBean.setUserName(shiroUser.getName());
			logBean.setModuleId(moduleId);
			logBean.setModuleName(moduleName);
			logBean.setOperateId(operateId);
			logBean.setOperateName(operateName);
			logBean.setContent(optionBean.getOptionName());
			logCustInfoService.addTableStoreLog(logBean, null);		
						
			//修改缓存
			CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.OPTION);
			final String userOrgId = ShiroUtil.getShiroUser().getOrgId();
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
					CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR + CachedNames.COM_FILEDSETS);
					CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSETS);
					CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR + CachedNames.CONTACTS_FILEDSETS);
				}
			});
			
			// 快话接口 更新销售进程
		
			/**
			 * 异步更新销售进程 调用接口
			 */
			if(AppConstant.SALES_TYPE_ONE.equals(optionBean.getItemCode())){
			
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.ORG_SALE_PROCESS);
				//  更新销售管理设置中，跟进设置销售进程列表				
				DataDictionaryBean bean = new DataDictionaryBean();
				bean.setOrgId(shiroUser.getOrgId());
				bean.setDictionaryCode(AppConstant.DATA_50001);
				bean.setDictionaryValue(cachedService.setDicProcessList(shiroUser.getOrgId()));
				dataDictionaryService.updateByCode(bean);
				//修改销售管理缓存
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.DATADICTIONARY);
				ExecutorUtils.THREAD_POOL.submit(new Runnable() {
					public void run() {	
						try{
							logger.debug(userOrgId+"修改销售进程开始！");
							Map<String,String>params = new HashMap<String, String>();
							params.put("orgId", userOrgId);
							String url = ConfigInfoUtils.getStringValue("set_sale_process");
							String jsonStr = HttpUtil.doPost(url, params);
							HttpJsonReturn returnBean = JSON.parseObject(jsonStr,HttpJsonReturn.class);
							if(!"1".equals(returnBean.getCode())){ // 如果返回失败 则再发送一次
								HttpUtil.doPost(url, params);
							}
						}catch(Exception e){
							logger.error(userOrgId+"修改销售进程异常！", e);
						}					
					}
				});
			}else if(AppConstant.SALES_TYPE_TWO.equals(optionBean.getItemCode())){
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.ORG_CUST_TYPE);
			}
			return AppConstant.RESULT_SUCCESS;
		}catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	
	/**【系统属性设置 删除操作】 销售进程，目标客户分类，流失客户原因，录音范例分类，行动标签维护   */
	@RequestMapping("/optionDel")
	@ResponseBody
	public String optionDel(HttpServletRequest request){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			String itemCode = request.getParameter("itemCode");
			if(StringUtils.isNotBlank(ids)){
				String moduleId;
				String moduleName;
				String[] ids_= ids.split("\\,");
				List<String> list = Arrays.asList(ids_);
				Map<String,Object>map = new HashMap<String, Object>();
				map.put("orgId",shiroUser.getOrgId());
				map.put("list",list);
				List<String>names = optionService.getOptionNames(map);
				StringBuffer sbf = new StringBuffer();
				if(names != null && names.size() >0){
					int i = 0;
					for(String name : names){
						i++;
						sbf.append(name);
						if(i!=names.size()){
							sbf.append(",");
						}
						
					}
				}
				optionService.removeBatch(map);
				if(AppConstant.SALES_TYPE_ONE.equals(itemCode)){ // 销售进程
					moduleId = AppConstant.Module_id1009;
					moduleName = AppConstant.Module_Name1009;
				}else if(AppConstant.SALES_TYPE_TWO.equals(itemCode)){ // 目标客户分类
					moduleId = AppConstant.Module_id1010;
					moduleName = AppConstant.Module_Name1010;
				}else if(AppConstant.SALES_TYPE_WAST.equals(itemCode)){ // 流失客户原因
					moduleId = AppConstant.Module_id1012;
					moduleName = AppConstant.Module_Name1012;
				}else if(AppConstant.RECORD_CODE.equals(itemCode)){ // 录音范例分类
					moduleId = AppConstant.Module_id1013;
					moduleName = AppConstant.Module_Name1013;
				}else if(AppConstant.SALES_TYPE_TEN.equals(itemCode)){ // 行动标签维护
					moduleId = AppConstant.Module_id1014;
					moduleName = AppConstant.Module_Name1014;
				}else if(AppConstant.SALES_TYPE_12.equals(itemCode)){ // 服务标签维护
					moduleId = AppConstant.Module_id1016;
					moduleName = AppConstant.Module_Name1016;
				}else if(AppConstant.SALES_TYPE_11.equals(itemCode)){ // 服务评级维护
					moduleId = AppConstant.Module_id1015;
					moduleName = AppConstant.Module_Name1015;
				}else{ // 单选、多选类型字段
					moduleId = AppConstant.Module_id1017;
					moduleName = AppConstant.Module_Name1017; 
				}
				
				// 新增用户操作日志
				LogBean logBean = new LogBean();
				logBean.setOrgId(shiroUser.getOrgId());
				logBean.setUserAcc(shiroUser.getAccount());
				logBean.setUserName(shiroUser.getName());
				logBean.setModuleId(moduleId);
				logBean.setModuleName(moduleName);
				logBean.setOperateId(AppConstant.Operate_id5);
				logBean.setOperateName(AppConstant.Operate_Name5);
				logBean.setContent(sbf.toString());
				logCustInfoService.addTableStoreLog(logBean, null);
				
			}
			//修改缓存
			CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.OPTION);

			final String userOrgId = ShiroUtil.getShiroUser().getOrgId();
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
					CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR + CachedNames.COM_FILEDSETS);
					CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSETS);
					CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR + CachedNames.CONTACTS_FILEDSETS);
				}
			});
			
			/**
			 * 异步更新销售进程 快话接口
			 */
			if(AppConstant.SALES_TYPE_ONE.equals(itemCode)){
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.ORG_SALE_PROCESS);
				//  更新销售管理设置中，跟进设置销售进程列表				
				DataDictionaryBean bean = new DataDictionaryBean();
				bean.setOrgId(shiroUser.getOrgId());
				bean.setDictionaryCode(AppConstant.DATA_50001);
				bean.setDictionaryValue(cachedService.setDicProcessList(shiroUser.getOrgId()));
				dataDictionaryService.updateByCode(bean);
				//修改销售管理缓存
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.DATADICTIONARY);
				ExecutorUtils.THREAD_POOL.submit(new Runnable() {
					public void run() {						
						try{
							logger.debug(userOrgId+"修改销售进程开始！");
							Map<String,String>params = new HashMap<String, String>();
							params.put("orgId", userOrgId);
							String url = ConfigInfoUtils.getStringValue("set_sale_process");
							String jsonStr = HttpUtil.doPost(url, params);
							HttpJsonReturn returnBean = JSON.parseObject(jsonStr,HttpJsonReturn.class);
							if(!"1".equals(returnBean.getCode())){ // 如果失败 则再发送一次
								HttpUtil.doPost(url, params);
							}
						}catch(Exception e){
							logger.error(userOrgId+"修改销售进程异常！", e);
						}						
					}
				});
			}else if(AppConstant.SALES_TYPE_TWO.equals(itemCode)){
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.ORG_CUST_TYPE);
			}
			return AppConstant.RESULT_SUCCESS;
		}catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	
	/** 【系统属性设置 默认】 销售进程，目标客户分类*/
	@RequestMapping("/optionSetDefault")
	@ResponseBody
	public String optionSetDefault(HttpServletRequest request){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			String id = request.getParameter("id");
			String itemCode= request.getParameter("itemCode");
			if(StringUtils.isNotBlank(id)){
				OptionBean entity = new OptionBean();
				entity.setOrgId(shiroUser.getOrgId());
				entity.setItemCode(itemCode);
				List<OptionBean> optList  = optionService.getListByCondtion(entity);
				for (OptionBean option : optList) {					
					option.setModifierAcc(shiroUser.getAccount());
					option.setModifydate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));					
					if(id.equals(option.getOptionlistId())){
						if(option.getIsDefault() != null && option.getIsDefault() == 1){ // 设置默认与取消默认 同一方法
							option.setIsDefault((short)0);
						}else{
							option.setIsDefault((short)1);
						}						
					}else{
						option.setIsDefault((short)0);
					}
				}
				// 批量修改
				optionService.modifyTrendsBatch(optList);				
				//修改缓存
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.OPTION);
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.COM_FILEDSETS);
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSETS);
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.CONTACTS_FILEDSETS);
			}
			return AppConstant.RESULT_SUCCESS;
		}catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	
	/** 查询系统属性 排序是否唯一 */
	@RequestMapping("/judgeOnlySort")
	@ResponseBody
	public String judgeOnlySort(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String itemCode = request.getParameter("itemCode");
			String sort = request.getParameter("sort");
			String optionId = request.getParameter("optionId");
			OptionBean entity = new OptionBean();
			entity.setOrgId(user.getOrgId());
			entity.setItemCode(itemCode);
			entity.setSort(Integer.parseInt(sort));	
			if(StringUtils.isNotBlank(optionId)){ // 修改
				entity.setOptionlistId(optionId);
			}
			Integer count = optionService.getSortByExists(entity);
			if(count != null && count>0){
				return AppConstant.RESULT_FAILURE;
			}
			
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e){
			throw new SysRunException(e);
		}
	}
	
	/** 【系统属性设置 客户放弃页面】*/
	@RequestMapping("/propertyset_giveUp")
	public String showOptionGiveUpList(HttpServletRequest request,OptionBean optionBean){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			optionBean.setOrgId(shiroUser.getOrgId());
			optionBean.setItemCode(AppConstant.SALES_TYPE_SIX);
			optionBean.setOrderKey("sort asc");
			List<OptionBean>optionList = optionService.getListPage(optionBean);
			if(optionList!=null && optionList.size()>0){
				Map<String,Object>map = new HashMap<String, Object>();
				map.put("orgId", shiroUser.getOrgId());
				map.put("code",AppConstant.SALES_TYPE_SIX);
				List<OptionBean> subOptions = optionService.getOptionGiveUp(map);
				if(subOptions !=null && subOptions.size() >0){
					map.clear();
					for(OptionBean ob1 : subOptions){
						map.put(ob1.getPid(),ob1.getSubNameList());
					}
				}
				for(OptionBean ob : optionList){
					if(map.get(ob.getOptionlistId())!=null){
						ob.setSubNameList((String) map.get(ob.getOptionlistId()));
					}
				}
			}
			
			DataDictionaryBean datadic = new DataDictionaryBean();
			datadic.setOrgId(shiroUser.getOrgId());
			datadic.setDictionaryCode(AppConstant.DATA17);
			List<DataDictionaryBean> datadics = dataDictionaryService.getListByCondtion(datadic);
			if(datadics != null && !datadics.isEmpty()){
				request.setAttribute("dic", datadics.get(0));
			}
			request.setAttribute("optionList", optionList);
			request.setAttribute("item", optionBean);
		}catch(Exception e){
			logger.error("propertyset_giveUp 异常！", e);
		}
		
		return "/tsm/option/iframe/property_set_giveUp";
	}
	
	/**
	 * 修改应用子项功能开关值
	 */
	@RequestMapping("/updateSubOpt")
	@ResponseBody
	public String updateSubOpt(HttpServletRequest request){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			String val = request.getParameter("val");
			String id = request.getParameter("id");
			DataDictionaryBean datadic = new DataDictionaryBean();
			datadic.setDictionaryId(id);
			datadic.setDictionaryValue(val);
			datadic.setOrgId(shiroUser.getOrgId());
			dataDictionaryService.modifyTrends(datadic);
			List<DataDictionaryBean> dicList = cachedService.getDictionary(shiroUser.getOrgId());
			for(DataDictionaryBean dic:dicList){
				if(dic.getDictionaryId().equals(id)){
					dic.setDictionaryValue(val);
					break;
				}
			}
			cachedService.setDictionary(shiroUser.getOrgId(), dicList);
			// 删除高级查询缓存	公海客户			
			CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR +"0"+CachedNames.HIGH_SEARCH_+8);
			CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR +"1"+CachedNames.HIGH_SEARCH_+8);		
			List<String> accounts = userService.getUserAccounts(shiroUser.getOrgId());
			for(String account : accounts){
				// 公海客户
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+8);
			}
			// 新增用户操作日志
			LogBean logBean = new LogBean();
			logBean.setOrgId(shiroUser.getOrgId());
			logBean.setUserAcc(shiroUser.getAccount());
			logBean.setUserName(shiroUser.getName());
			logBean.setModuleId(AppConstant.Module_id1011);
			logBean.setModuleName(AppConstant.Module_Name1011);
			logBean.setOperateId(AppConstant.Operate_id68);
			logBean.setOperateName(AppConstant.Operate_Name68);
			logBean.setContent("1".equals(val) ? "开启" : "关闭");
			logCustInfoService.addTableStoreLog(logBean, null);
			return AppConstant.RESULT_SUCCESS;
		}catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	
	/** 系统属性 客户放弃原因编辑页面 */
	@RequestMapping("/propertyset_giveUp_modfiy")
	public String showOptiongiveUpModfiy(HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			OptionBean entity = new OptionBean();
			entity.setOrgId(shiroUser.getOrgId());
			entity.setOptionlistId(id);
			entity = optionService.getByCondtion(entity);
			request.setAttribute("item", entity);
			String code = "";
			if(AppConstant.GIVEUP_ONE.equals(entity.getOptionName())){
				code = AppConstant.ITEMCODES[0];
			}else if(AppConstant.GIVEUP_TWO.equals(entity.getOptionName())){
				code = AppConstant.ITEMCODES[1];
			}else if(AppConstant.GIVEUP_THREE.equals(entity.getOptionName())){
				code = AppConstant.ITEMCODES[2];
			}
			Map<String, String> param = new HashMap<String, String>();
			param.put("orgId", shiroUser.getOrgId());
			param.put("code", code);
			request.setAttribute("entitys", optionService.getSubOptionList(param));
			request.setAttribute("code", code);
			request.setAttribute("pid", id);
		}		
		return "/tsm/option/idialog/property_set_modfiy_giveUp";
	}
	
	/**
	 * 添加子项
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping("/addSubOption")
	@ResponseBody
	public String addSubOption(HttpServletRequest request,OptionModel optionModel){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			String code = request.getParameter("code");
			if(StringUtils.isNotBlank(code)){
				OptionBean opt = new OptionBean();
				opt.setOrgId(shiroUser.getOrgId());
				opt.setItemCode(code);
				List<OptionBean> opts = optionService.getListByCondtion(opt);
				List<String> ids = new ArrayList<String>();
				for (OptionBean option : opts) {
					ids.add(option.getOptionlistId());
				}
				if(!ids.isEmpty()){
					Map<String,Object>map = new HashMap<String, Object>();
					map.put("orgId", shiroUser.getOrgId());
					map.put("list", ids);
					optionService.removeBatch(map);
				}
				List<OptionBean>options = optionModel.getOptions();
				StringBuffer sbf = new StringBuffer();
				if(null != options && !options.isEmpty()){
					
					for (OptionBean obj : options) {
						obj.setOptionlistId(SysBaseModelUtil.getModelId());
						obj.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
						obj.setInputerAcc(shiroUser.getAccount());
						obj.setOrgId(shiroUser.getOrgId());
						sbf.append(obj.getOptionName());
						sbf.append(",");
					}
					optionService.createBatch(options);		
				}
				// 新增用户操作日志
				LogBean logBean = new LogBean();
				logBean.setOrgId(shiroUser.getOrgId());
				logBean.setUserAcc(shiroUser.getAccount());
				logBean.setUserName(shiroUser.getName());
				logBean.setModuleId(AppConstant.Module_id1011);
				logBean.setModuleName(AppConstant.Module_Name1011);
				logBean.setOperateId(AppConstant.Operate_id70);
				logBean.setOperateName(AppConstant.Operate_Name70);
				logBean.setContent(sbf.toString());
				logCustInfoService.addTableStoreLog(logBean, null);
				//修改缓存
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.OPTION);
				// 删除高级查询缓存	公海客户			
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR +"0"+CachedNames.HIGH_SEARCH_+8);
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR +"1"+CachedNames.HIGH_SEARCH_+8);		
				List<String> accounts = userService.getUserAccounts(shiroUser.getOrgId());
				for(String account : accounts){
					// 公海客户
					CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+8);
				}
			}			
			return AppConstant.RESULT_SUCCESS;
		}catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	
	
	/**所属行业 */
	@RequestMapping("/companyTrade")
	@ResponseBody
	public List<OptionBean> companyTrade(HttpServletRequest request) throws Exception{
		ShiroUser shiroUser = ShiroUtil.getShiroUser();
		OptionBean option=new OptionBean();
		option.setOrgId(shiroUser.getOrgId());		
		option.setItemCode("companyTrade");
		option.setOrderKey("sort asc");
		List<OptionBean> optionList = optionService.getListPage(option);
//		request.setAttribute("optionList", optionList);
//		request.setAttribute("item", option);
		return optionList;
	}

}
