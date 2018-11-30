package com.qftx.tsm.cust.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.MD5Utils;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.area.service.AreaService;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.cust.service.ComResourceGroupService;
import com.qftx.tsm.cust.service.ComResourceService;
import com.qftx.tsm.cust.service.ResCustInfoDetailService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.log.service.LogBatchInfoService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.dto.HighSearchDto;
import com.qftx.tsm.sys.dto.SearchListShowCodeDto;
import com.qftx.tsm.sys.enums.SysEnum;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

@Controller
@RequestMapping(value = "/res/resmanage/")
public class CompUnAssignResAction extends BaseAction {
	private static Logger logger = Logger.getLogger(CompUnAssignResAction.class);
	@Autowired
	transient private ComResourceService comResourceService;
	@Autowired
	transient private ComResourceGroupService comResourceGroupService;
	@Autowired
	transient private OrgGroupUserService orgGroupUserService;
	@Autowired
	PlatformTransactionManager transactionManager;
	@Autowired
	transient private CachedService cachedService;
	@Autowired
	transient private ResCustInfoService resCustInfoService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private LogBatchInfoService logBatchInfoService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private LogUserOperateService logUserOperateService;
	@Autowired
	private ResCustInfoDetailService resCustInfoDetailService;
	@Autowired
	private LogCustInfoService logCustInfoService;

	/**
	 * 查询待分配资源
	 * 
	 * @param request
	 * @param
	 * @return
	 * @create 2015年11月16日 下午1:35:07 wuwei
	 * @history
	 */
	@RequestMapping(value = "queryComUnAssginResList")
	public String queryComUnAssginResList(HttpServletRequest request, ResCustDto resCustDto, String deptId, String isSharePool,String level) {
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		setDyField(request);
		level = (level == null || "".equals(level)) ? "1" : level;
		String groupId = (resCustDto.getGroupId() == null || "".equals(resCustDto.getGroupId())) ? "all" : resCustDto.getGroupId();
		String isShare = cachedService.getDirList(AppConstant.DATA_40018, orgId).get(0).getDictionaryValue();
		List<String> groups = new ArrayList<String>();
		String clearTaskId = GuidUtil.getUUID();
		request.setAttribute("clearTaskId", clearTaskId);
		String assignTaskId = GuidUtil.getUUID();
		request.setAttribute("assignTaskId", assignTaskId);
		Map<String, String> shareMap = new HashMap<String, String>();
		shareMap.put("account", user.getAccount());
		shareMap.put("orgId", orgId);
		List<String> shareGroupList = cachedService.getShareGroupId(shareMap);
		
		try {
			if (user.getIssys() != null && user.getIssys() == 1) {
				if (deptId == null || "".equals(deptId)) {
					// 查询 所选部门成员列表
					if (StringUtils.isNotBlank(user.getGroupId())) { // 查询该部门以及子部门成员
						groups = shareGroupList;
					}
				} else {
					groups = Arrays.asList(deptId.split(","));
					groups = getGroupList(groups, shareGroupList);
				}
			}

			resCustDto.setDeptIds(groups);
			resCustDto.setInputAcc(user.getAccount());
			resCustDto.setResGroupId(resCustDto.getGroupId());
			resCustDto.setOrgId(orgId);
			if ("all".equals(groupId)) {
				resCustDto.setResGroupId("");
				resCustDto.setGroupId("");
			}
			if (groups != null && groups.size() > 0) {
				deptId = "";
				for (String str : groups) {
					deptId = deptId + str + ",";
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			map.put("resType", "1");
			map.put("deptIds", groups);
			map.put("ownerAccs", null);
			map.put("inputAcc", user.getAccount());
			List<Map<String, Object>> resGroups = cachedService.getResGroupList1(orgId);
			request.setAttribute("resGroups", resGroups);			
			request.setAttribute("deptId", deptId);
			request.setAttribute("groupId", groupId);
			request.setAttribute("resCustDto", resCustDto);
			request.setAttribute("isShare", isShare);
			request.setAttribute("isSharePool", isSharePool);
			request.setAttribute("level", level);
			
			// 所属行业
			List<OptionBean> companyTrades = cachedService.getOptionList("companyTrade",user.getOrgId());
			request.setAttribute("companyTrades",companyTrades);
			List<CustSearchSet> tlist = getIsTextQueryList(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_9.getState());
			request.setAttribute("tList", tlist); // 文本类型查询字段列表
			Map<String,String> map1 = getIsDefinedNameList(user.getOrgId(),user.getIsState());
			request.setAttribute("definedNameMap", map1);
			// 用于筛选项排序
			Map<String,Integer> mutilSearchCodeSortMap = getUnTestSearchSort(SysEnum.SEARCH_SET_MODULE_9.getState(),user.getOrgId(),user.getIssys());
			request.setAttribute("mutilSearchCodeSortMap",mutilSearchCodeSortMap);
			List<HighSearchDto> dtos = cachedService.getHighSearch(SysEnum.SEARCH_SET_MODULE_9.getState(), user.getOrgId(), user.getIssys());
			List<HighSearchDto> definedDtos = new ArrayList<HighSearchDto>();
			for(HighSearchDto dto : dtos){
				if(dto.getIsFiledSet() ==1 && dto.getIsQuery() == 1 ){
					definedDtos.add(dto);
				}
			}			
			request.setAttribute("definedSearchCodes",definedDtos); // 自定义非文本项查询集合
			// 需隐藏列的序号
			List<Integer> sorts = getHideSortListCode(SysEnum.SEARCH_SET_MODULE_9.getState(),user.getOrgId(),user.getIsState().toString());
			request.setAttribute("sorts", sorts);
			setIsReplaceWord(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "/tsm/resource/queryComUnAssginResList";
	}
	public List<CustSearchSet> getIsTextQueryList(String orgId,String module) {
		List<CustSearchSet> list = cachedService.getCustSearchSetList(module,orgId);
		List<CustSearchSet> rlist = new ArrayList<CustSearchSet>();
		for (CustSearchSet filed : list) {
			if (filed.getIsQuery() == 1 && filed.getDataType() == 1) {
				rlist.add(filed);
			}
		}
		return rlist;
	}
	// 自定义名称集合
	public Map<String,String> getIsDefinedNameList(String orgId, int isState) {
		List<CustFieldSet> fieldSets = null;
		Map<String,String>map = new HashMap<String, String>();
		if (isState == 1) {
			fieldSets = cachedService.getComFiledSet(orgId);
		} else {
			fieldSets = cachedService.getPersonFiledSet(orgId);
		}
		for (CustFieldSet filed : fieldSets) {
			if (filed.getFieldCode().contains("defined")) {
				map.put(filed.getFieldCode(), filed.getFieldName());
			}
		}
		return map;
	}
	// 页面非文本查询项排序值
	public Map<String,Integer>getUnTestSearchSort(String module,String orgId,Integer roleType){
		List<CustSearchSet> list = cachedService.getCustSearchSetList(module,orgId);
		Map<String,Integer>map = new HashMap<String, Integer>();
		int i = 0;
		for (CustSearchSet filed : list) {			
			if (filed.getIsQuery() == 1 && filed.getDataType() != 1 
					&& !(roleType ==0 && 
					("ownerAcc".equals(filed.getDevelopCode())
							||"resAddGroup".equals(filed.getDevelopCode())
					))){
				i++;
				map.put(filed.getSearchCode(),i); 
			}
		}	
		return map;
	}
	// 客户跟进页面需隐藏字段列
		public List<Integer>getHideSortListCode(String module,String orgId,String isState){
		List<CustSearchSet> list = cachedService.getCustSearchSetList(module,orgId);
		Map<String,String>map = new HashMap<String, String>();
		for (CustSearchSet filed : list) {
			if (filed.getIsShow() == 1) {
				map.put(filed.getDevelopCode(), filed.getDevelopCode());
			}
		}
		List<Integer>rList = new ArrayList<Integer>();		
		for(String[]strs : SearchListShowCodeDto.modult_9_List){
			if(("2".equals(strs[2]) || isState.equals(strs[2])) && map.get(strs[0]) == null){
				rList.add(Integer.parseInt(strs[1]) + 2); // 存储需要隐藏的排序值 . 因为第一列是复选框、第二列是操作列，所以加了2
			}
		}		
		return rList;
	}

	@ResponseBody
	@RequestMapping(value = "getUnAssginResJson",method=RequestMethod.POST)
	public Map<String, Object> getUnAssginResJson(HttpServletRequest request, ResCustDto resCustDto, String deptId, String isSharePool,String level) {
		Map<String, Object> jsonMap = new HashMap<>();
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		resCustDto.setOrgId(orgId);
		resCustDto.setState(user.getIsState());
		Map<String, String> shareMap = new HashMap<String, String>();
		shareMap.put("account", user.getAccount());
		shareMap.put("orgId", orgId);
		List<String> shareGroupList = cachedService.getShareGroupId(shareMap);
		level = (level == null || "".equals(level)) ? "1" : level;
		String groupId = (resCustDto.getGroupId() == null || "".equals(resCustDto.getGroupId())) ? "all" : resCustDto.getGroupId();
		List<String> groups = new ArrayList<String>();
		try {
			if (user.getIssys() != null && user.getIssys() == 1) {
				if (deptId == null || "".equals(deptId)) {
					// 查询 所选部门成员列表
					if (StringUtils.isNotBlank(user.getGroupId())) { // 查询该部门以及子部门成员
						groups = shareGroupList;
					}
				} else {
					groups = Arrays.asList(deptId.split(","));
					groups = getGroupList(groups,shareGroupList);
				}
			}
			if (resCustDto.getoDateType() != null && !"".equals(resCustDto.getoDateType()) && !"5".equals(resCustDto.getoDateType())) {
				resCustDto.setStartDate(getStartDateStr(new Integer(resCustDto.getoDateType())));
				resCustDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			resCustDto.setDeptIds(groups);
			resCustDto.setOrgId(orgId);
			resCustDto.setInputAcc(user.getAccount());
			resCustDto.setResGroupId(resCustDto.getGroupId());
			if ("all".equals(groupId)) {
				resCustDto.setResGroupIds(null);
				resCustDto.setResGroupId("");
				resCustDto.setGroupId("");
			}else {
				if ("1".equals(level)) {
					List<String> resGroupIds = new ArrayList<>();
					resGroupIds.add(resCustDto.getResGroupId());
					resCustDto.setResGroupIds(resGroupIds);
				}else {
					List<String> resGroupIds = new ArrayList<>();
					List<Map<String,Object>> resGroupList = cachedService.getResGroupList1(orgId);
					for (Map<String, Object> map : resGroupList) {
						if (map.get("id").equals(resCustDto.getResGroupId())) {
							List<Map<String, Object>> childrenList = (List<Map<String, Object>>) map.get("children");
							for (Map<String, Object> map2 : childrenList) {
								resGroupIds.add((String) map2.get("id"));
							}
						}
					}
					resCustDto.setResGroupIds(resGroupIds);
				}
			}
			

			// 模糊查询处理
			if (StringUtils.isNotBlank(resCustDto.getqKeyWord())) {
				String queryText = resCustDto.getqKeyWord().trim();
				resCustDto.setqKeyWord(queryText);
				if (queryText.matches("[0-9]+")) {
					resCustDto.setQueryPhone(true);
				} else {
					resCustDto.setQueryPhone(false);
				}
			}
			//查出多选项查询字段
			List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_9.getState());
			
	    	String key = CachedNames.CACHE_RES+CachedNames.SEPARATOR+MD5Utils.getMD5String(JSON.toJSONString(resCustDto));
	    	Map<String, Object> cacheList = (Map<String, Object>) CachedUtil.getInstance().get(key);
	    	if(cacheList!=null){
	    		return cacheList;
	    	}
	    	
			if(resCustDto.getInputStatusStr()!=null && !"".equals(resCustDto.getInputStatusStr())){
		     	 String[] inputStatus_list = resCustDto.getInputStatusStr().split(",");
		     	List<String> inputStatuList = Arrays.asList(inputStatus_list);
		     	resCustDto.setInputStatusList(inputStatuList);
			}else{
				String[] inputStatus_list=request.getParameterValues("inputStatus");
				if(inputStatus_list!=null && inputStatus_list.length>0){
					List<String> inputStatuList = Arrays.asList(inputStatus_list);
		     		resCustDto.setInputStatusList(inputStatuList);
				}
			}
	    	
			List<ResCustDto> resList = new ArrayList<ResCustDto>();
			if (groups != null && groups.size() > 0) {
				resList = comResourceService.queryUnAssginResListPage(resCustDto,multiSearchList);
			}
			Map<Integer, String> provinceMap = new HashMap<Integer, String>();
			Map<Integer, String> cityMap = new HashMap<Integer, String>();
			Map<Integer, String> countyMap = new HashMap<Integer, String>();
			Map<String, String> tradeMap = new HashMap<String, String>();
			if(user.getIsState() == 1){
				provinceMap = cachedService.getAreaMap(CachedNames.PROVINCE);
				cityMap = cachedService.getAreaMap(CachedNames.CITY);
				countyMap = cachedService.getAreaMap(CachedNames.COUNTY);
				List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
				tradeMap = cachedService.changeOptionListToMap(trades);
			}
			//多选项显示
			if (resList.size() != 0 && resList != null) {
				if(resList.size() > 0){
					List<String> multiShowList = cachedService.getMultiSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_9.getState());
					List<String> singleShowList = cachedService.getSingleSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_9.getState());
					comResourceService.multiDefinedShowChange(resList,multiShowList,singleShowList,user.getOrgId());
				}
				for (ResCustDto resDto : resList) {
					resDto.setShowdefined16(resDto.getDefined16() !=null?DateUtil.formatDate(resDto.getDefined16(), DateUtil.DATE_DAY): "");
					resDto.setShowdefined17(resDto.getDefined17() !=null?DateUtil.formatDate(resDto.getDefined17(), DateUtil.DATE_DAY): "");
					resDto.setShowdefined18(resDto.getDefined18() !=null?DateUtil.formatDate(resDto.getDefined18(), DateUtil.DATE_DAY): "");
					if(user.getIsState() == 1){
						if (resDto.getProvinceId() != null) {
							String area = provinceMap.get(resDto.getProvinceId());
							if(resDto.getCityId() != null) area+="-"+cityMap.get(resDto.getCityId());
							if(resDto.getCountyId() != null) area+="-"+countyMap.get(resDto.getCountyId());
							resDto.setLocationArea(area);
						}
						resDto.setCompanyTrade(resDto.getCompanyTrade() != null ? tradeMap.get(resDto.getCompanyTrade()) : "");
					}
				}
			}
			setResList(orgId, resList, false);
			jsonMap.put("item", resCustDto);
			jsonMap.put("list", resList);
			CachedUtil.getInstance().set(key, jsonMap, 10);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonMap;
	}

	/**
	 * 变更分组
	 * 
	 * @return
	 * @create 2015年11月17日 上午10:07:03 wuwei
	 * @history
	 */
	@RequestMapping(value = "toChangeGroup")
	public String toChangeGroup(HttpServletRequest request, String ids) {
		ShiroUser user = ShiroUtil.getUser();
		String module = request.getParameter("module");
		try {
			List<Map<String, Object>> resourceGroupList = cachedService.getResGroupList1(user.getOrgId());
			request.setAttribute("resourceGroupList", resourceGroupList);
			request.setAttribute("ids", ids);
			request.setAttribute("module", module);
		} catch (Exception e) {

			throw new SysRunException(e);
		}
		return "tsm/resource/change_group";
	}

	/**
	 * 变更资源组
	 * 
	 * @create 2015年11月17日 上午10:10:24 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "changeResGroup")
	public String changeResGroup(HttpServletRequest request, String ids, String groupId, String groupType,String module,String groupName) {
		try {
			ShiroUser user = ShiroUtil.getUser();
			// 获取查询条件
			String[] idsArr = ids.split("\\,");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids",idsArr );
			map.put("account", user.getAccount());
			map.put("groupId", groupId);
			map.put("orgId", user.getOrgId());
			comResourceService.updateBatchResToGroup(map, user.getOrgId(), user.getAccount(),user.getName(),module,groupName);
			return AppConstant.RESULT_SUCCESS;
		} catch (Exception e) {
			logger.error("变更分组。ids=" + ids + "groupId=" + groupId, e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}

	/**
	 * 删除资源
	 * 
	 * @create 2015年11月17日 上午10:35:09 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping("deleteComRes")
	public String deleteComRes(HttpServletRequest request, String ids, String groupId,String module,String accs) {
		try {
			ShiroUser user = ShiroUtil.getUser();
			if (ids != null) {
				Map<String, List<String>> logMap = new HashMap<>();
				String[] ids_ = ids.split("\\,");
				if (accs != null) {
					String[] accs_ = accs.split("\\,");
					for(int i = 0 ; i < accs_.length ; i++) {
						List<String> list = new ArrayList<>();
						if (logMap.keySet().contains(accs_[i])) {
							list = logMap.get(accs_[i]);
						}
						list.add(ids_[i]);
						logMap.put(accs_[i], list);
					}
				}
				Map<String, Object> delmap = new HashMap<String, Object>();
				delmap.put("updateacc", user.getAccount());// 修改者帐号
				delmap.put("ids", ids_);// IDS_列表
				delmap.put("orgId", user.getOrgId());
				comResourceService.removeComResBatch(delmap, Arrays.asList(ids_), user.getOrgId(), user.getAccount());// 调用服务层批量伪删除方法
				resCustInfoDetailService.removeComResLinkBatch(delmap);
				String batchId =  SysBaseModelUtil.getModelId();
				if (logMap.keySet().size() == 0 || logMap.keySet() == null) {
					if ("unAssgin".equals(module)) {
						logBatchInfoService.saveLog(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_RES_DEL, AppConstant.Module_id106,AppConstant.Module_Name106, AppConstant.Operate_id35, AppConstant.Operate_Name35, "删除资源"+Arrays.asList(ids_).size()+"条", "", Arrays.asList(ids_), "", batchId, null);
					} else if ("all".equals(module)) {
						logBatchInfoService.saveLog(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_RES_DEL, AppConstant.Module_id118,AppConstant.Module_Name118, AppConstant.Operate_id35, AppConstant.Operate_Name35, "删除资源"+Arrays.asList(ids_).size()+"条", "", Arrays.asList(ids_), "", batchId, null);
					}
				}else {
					for(String ownerAcc : logMap.keySet()) {
						if ("unAssgin".equals(module)) {
							logBatchInfoService.saveLog(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_RES_DEL, AppConstant.Module_id106,AppConstant.Module_Name106, AppConstant.Operate_id35, AppConstant.Operate_Name35, "删除资源"+logMap.get(ownerAcc).size()+"条", ownerAcc, logMap.get(ownerAcc), "", batchId, null);
						} else if ("all".equals(module)) {
							logBatchInfoService.saveLog(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_RES_DEL, AppConstant.Module_id118,AppConstant.Module_Name118, AppConstant.Operate_id35, AppConstant.Operate_Name35, "删除资源"+logMap.get(ownerAcc).size()+"条", ownerAcc, logMap.get(ownerAcc), "", batchId, null);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("删除资源.ids=" + ids + "groupId=" + groupId);
			return AppConstant.RESULT_EXCEPTION;
		}
		return AppConstant.RESULT_SUCCESS;
	}

	/**
	 * 资源分配
	 * 
	 * @return
	 * @create 2015年11月17日 上午10:51:56 wuwei
	 * @history
	 */
	@RequestMapping("toassginResource")
	public String toassginResource(HttpServletRequest request, String ids) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<Map<String, Object>> groupAndMembers = orgGroupUserService.getTreeGroupUser(user.getId(), user.getOrgId());
			request.setAttribute("groupAndMembers", groupAndMembers);
			request.setAttribute("ids", ids);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/tsm/resource/add_againassginresource";
	}

	/**
	 * 资源分配
	 * 
	 * @create 2015年11月17日 上午11:12:25 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping("getAssginRes")
	public String getAssginRes(HttpServletRequest request, String ids, String ownerAcc, String ownerName,String taskId) {
		// 获取查询条件
		ShiroUser user = ShiroUtil.getShiroUser();
		String[] idsArr = ids.split(","); 
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> resultMap = new HashMap<String, String>();
		map.put("ids", ids);
		map.put("isCreate", "0");
		map.put("ownerAcc", ownerAcc);
		map.put("ownerName", ownerName);
		map.put("orgId", user.getOrgId());
		map.put("inputAcc", user.getAccount());
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("getAssginRes reqId=" + reqId + ",account=" + user.getAccount() + ",ownerAcc=" + ownerAcc + ",ids=" + ids);
		try {
			
			comResourceService.saveAssginRes(reqId, map, resultMap, user.getOrgId(), user.getAccount(),user.getName(),taskId);
			if (!"0".equals(resultMap.get("result"))) {
				logger.debug(resultMap.get("msg"));
			}
			//logUserOperateService.setUserOperateLog( AppConstant.Module_id106,AppConstant.Module_Name106, AppConstant.Operate_id34, AppConstant.Operate_Name34, "分配资源"+idsArr.length+"条给'"+ownerAcc+"'","");
			return resultMap.get("result");
		} catch (Exception e) {
			logger.error("分配资源异常。ids=" + ids + "ownerAcc=" + ownerAcc + "参数=" + map.toString(), e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}

	@ResponseBody
	@RequestMapping("clearMyRes")
	public String clearMyRes(HttpServletRequest request, ResCustDto entity, String deptId,String level,String taskId) {
		String orgId = "";
		ShiroUser user = ShiroUtil.getUser();
		orgId = user.getOrgId();
		entity.setOrgId(orgId);
		Map<String, String> shareMap = new HashMap<String, String>();
		shareMap.put("account", user.getAccount());
		shareMap.put("orgId", orgId);
		List<String> shareGroupList = cachedService.getShareGroupId(shareMap);	
		level = (level == null || "".equals(level)) ? "1" : level;
		String groupId = (entity.getGroupId() == null || "".equals(entity.getGroupId())) ? "all" : entity.getGroupId();
		List<String> groups = new ArrayList<String>();
		if (user.getIssys() != null && user.getIssys() == 1) {
			if (deptId == null || "".equals(deptId)) {
				// 查询 所选部门成员列表
				if (StringUtils.isNotBlank(user.getGroupId())) { // 查询该部门以及子部门成员
					groups = shareGroupList;
				}
			} else {
				groups = Arrays.asList(deptId.split(","));
				groups = getGroupList(groups,shareGroupList);
			}
		}
		if (entity.getoDateType() != null && !"".equals(entity.getoDateType()) && !"5".equals(entity.getoDateType())) {
			entity.setStartDate(getStartDateStr(new Integer(entity.getoDateType())));
			entity.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		entity.setDeptIds(groups);
		entity.setOrgId(orgId);
		entity.setInputAcc(user.getAccount());
		entity.setResGroupId(entity.getGroupId());
		if ("all".equals(groupId)) {
			entity.setResGroupIds(null);
			entity.setResGroupId("");
			entity.setGroupId("");
		}else {
			if ("1".equals(level)) {
				List<String> resGroupIds = new ArrayList<>();
				resGroupIds.add(entity.getResGroupId());
				entity.setResGroupIds(resGroupIds);
			}else {
				List<String> resGroupIds = new ArrayList<>();
				List<Map<String,Object>> resGroupList = cachedService.getResGroupList1(orgId);
				for (Map<String, Object> map : resGroupList) {
					if (map.get("id").equals(entity.getResGroupId())) {
						List<Map<String, Object>> childrenList = (List<Map<String, Object>>) map.get("children");
						for (Map<String, Object> map2 : childrenList) {
							resGroupIds.add((String) map2.get("id"));
						}
					}
				}
				entity.setResGroupIds(resGroupIds);
			}
		}
		

		// 模糊查询处理
		if (StringUtils.isNotBlank(entity.getqKeyWord())) {
			String queryText = entity.getqKeyWord().trim();
			entity.setqKeyWord(queryText);
			if (queryText.matches("[0-9]+")) {
				entity.setQueryPhone(true);
			} else {
				entity.setQueryPhone(false);
			}
		}
		//entity = getQCondition(entity, deptId, user);

        if(entity.getInputStatusStr()!=null && !"".equals(entity.getInputStatusStr())){
            String[] inputStatus_list = entity.getInputStatusStr().split(",");
            List<String> inputStatuList = Arrays.asList(inputStatus_list);
            entity.setInputStatusList(inputStatuList);
        }else{
            String[] inputStatus_list=request.getParameterValues("inputStatus");
            if(inputStatus_list!=null && inputStatus_list.length>0){
                List<String> inputStatuList = Arrays.asList(inputStatus_list);
                entity.setInputStatusList(inputStatuList);
            }
        }

		return comResourceService.clearMyRes(entity, orgId, user.getAccount(),user.getName(),taskId);
	}

	public ResCustDto getQCondition(ResCustDto resCustDto, String deptId, ShiroUser user) {
		List<String> groups = null;
		if (user.getIssys() != null && user.getIssys() == 1) {
			if (deptId == null || "".equals(deptId)) {
				// 查询 所选部门成员列表
				if (StringUtils.isNotBlank(user.getGroupId())) { // 查询该部门以及子部门成员
					groups = orgGroupUserService.getTreeGroupIds(user.getOrgId(), user.getGroupId());
				}
			} else {
				groups = Arrays.asList(deptId.split(","));
				groups = getGroupList2(groups, user.getAccount(), user.getOrgId());
			}
		}
		if (resCustDto.getoDateType() != null && !"".equals(resCustDto.getoDateType())) {
			resCustDto.setStartDate(getStartDateStr(new Integer(resCustDto.getoDateType())));
			resCustDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		resCustDto.setDeptIds(groups);
		resCustDto.setOrgId(user.getOrgId());
		resCustDto.setInputAcc(user.getAccount());
		return resCustDto;
	}

	/**
	 * 设置共享池
	 * 
	 * @param request
	 * @param groupId
	 * @return
	 * @create 2015年12月1日 下午2:03:17 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "setShare")
	public String setResGroupShare(HttpServletRequest request, String groupId, String isSharePool) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			Map<String, String> groupMap = new HashMap<String, String>();
			groupMap.put("groupId", groupId);
			groupMap.put("orgId", ShiroUtil.getShiroUser().getOrgId());
			ResourceGroupBean rgb = comResourceGroupService.getByPrimaryKey(groupMap);
			rgb.setIsSharePool(new Short(isSharePool));
			rgb.setModifierAcc(ShiroUtil.getUser().getAccount());
			rgb.setModifydate(new Date());
			comResourceGroupService.modify(rgb);
			Map<String, ResourceGroupBean> map = new HashMap<String, ResourceGroupBean>();
			map.put(rgb.getResGroupId(), rgb);
			cachedService.setOrgResGroupBean(user.getOrgId(), map);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_GROUP_LIST);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_GROUP_LIST1);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_CLASS_LIST);
			//查询groupname
			Map<String, Object> map2 = new HashMap<>();
			map2.put("groupIds", groupId.split(","));
			map2.put("orgId", user.getOrgId());
			List<ResourceGroupBean> groups = comResourceGroupService.findByGroupIds(map2);
			StringBuffer groupName= new StringBuffer();
			if (groups != null && groups.size() == 1) {
				groupName.append(groups.get(0).getGroupName());
			}
			if ("1".equals(isSharePool)) {
				logUserOperateService.setUserOperateLog( AppConstant.Module_id106,AppConstant.Module_Name106, AppConstant.Operate_id38, AppConstant.Operate_Name38,groupName.toString() ,"");
			} else if (("0".equals(isSharePool))) {
				logUserOperateService.setUserOperateLog( AppConstant.Module_id106,AppConstant.Module_Name106, AppConstant.Operate_id36, AppConstant.Operate_Name36,groupName.toString() ,"");
			}
		} catch (Exception e) {
			logger.error("设置共享公海池异常groupId=" + groupId, e);
			return "-1";
		}
		return "0";
	}

	public List<String> getGroupList(List<String> list,List<String> shareGroupList ) {
		List<String> resutlList = new ArrayList<String>();
		if (list != null && list.size() > 0 && shareGroupList != null) {
			Map<String, String> map = new HashMap<String, String>();
			for (String shareGroupId : shareGroupList) {
				map.put(shareGroupId, shareGroupId);
			}
			for (String groupId : list) {
				if (map.get(groupId) != null) {
					resutlList.add(groupId);
				}
			}
		}
		if (resutlList == null || resutlList.size() <= 0) {
			resutlList = list;
		}
		return resutlList;
	}
	
	public List<String> getGroupList2(List<String> list, String account, String orgId) {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("account", account);
		map1.put("orgId", orgId);
		List<String> shareGroupList =cachedService.getShareGroupId(map1);

		List<String> resutlList = new ArrayList<String>();
		if (list != null && list.size() > 0 && shareGroupList != null) {
			Map<String, String> map = new HashMap<String, String>();
			for (String shareGroupId : shareGroupList) {
				map.put(shareGroupId, shareGroupId);
			}
			for (String groupId : list) {
				if (map.get(groupId) != null) {
					resutlList.add(groupId);
				}
			}
		}
		if (resutlList == null || resutlList.size() <= 0) {
			resutlList = list;
		}
		return resutlList;
	}

	/**
	 * 放弃客户
	 *
	 * @param request
	 * @create 2015年11月13日 下午7:44:26 lixing
	 * @history
	 */
	@RequestMapping("/removeCust")
	@ResponseBody
	public String custRemoveBatch(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			Short operateType = AppConstant.OPREATE_TYPE12;
			if (StringUtils.isNotBlank(ids)) {
				List<String> custIds = getCustIds(ids);
				resCustInfoService.givePublicUp(user, custIds, operateType, "");
				comResourceService.freshTaoCache(user.getOrgId(), user.getAccount());
			}
			return "1";
		} catch (Exception e) {
			logger.error("放弃客户失败", e);
			return "-1";
		}
	}

	public List<String> getCustIds(String custIds) {
		List<String> ids = new ArrayList<String>();
		for (String custId : custIds.split(",")) {
			if (StringUtils.isNotBlank(custId)) {
				ids.add(custId);
			}
		}
		return ids;
	}
	
	/**
	 * @Description:待分配资源二级分配
	 * @param @param request
	 * @param @param ids
	 * @param @param deptId
	 * @param @return
	 * @return BaseJsonResult
	 * @create 2018-11-27 上午9:08:42 lubo 
	 */
	@ResponseBody
	@RequestMapping("secondDistribution")
	public BaseJsonResult secondDistribution(HttpServletRequest request, String ids, String deptId){
		List<String> custIds = getCustIds(ids);
		ShiroUser user = ShiroUtil.getShiroUser();
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("getAssginRes ids=" + ids + "deptId=" +deptId);
		try {
			BaseJsonResult baseJsonResult = comResourceService.secondDistribution(reqId, custIds,deptId,user.getOrgId(),user.getAccount());
			return baseJsonResult;
		} catch (Exception e) {
			logger.error("资源二级分配异常。ids=" + ids + "deptId=" + deptId ,e);
			return BaseJsonResult.error(e.getMessage());
		}
	}
	
}