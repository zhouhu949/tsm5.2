package com.qftx.tsm.cust.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.MD5Utils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.area.service.AreaService;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.cust.service.ComResourceGroupService;
import com.qftx.tsm.cust.service.ComResourceService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.dto.HighSearchDto;
import com.qftx.tsm.sys.dto.SearchListShowCodeDto;
import com.qftx.tsm.sys.enums.SysEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 已分配资源
 * 
 * @author: wuwei
 * @since: 2015年11月18日 下午3:01:53
 * @history:
 */
@Controller
@RequestMapping(value = "/res/resmanage")
public class CompAssignResAction extends BaseAction {
	private static Logger logger = Logger.getLogger(CompAssignResAction.class);
	@Autowired
	transient private ComResourceService comResourceService;
	@Autowired
	transient private ComResourceGroupService comResourceGroupService;
	@Autowired
	transient private OrgGroupService orgGroupService;
	@Autowired
	transient private OrgGroupUserService orgGroupUserService;
	@Resource
	transient private JdbcTemplate jdbcTemplate;
	@Autowired
	transient private CachedService cachedService;
	@Resource
	private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private LogUserOperateService logUserOperateService;
	@RequestMapping(value = "queryComAssginResList")
	public String queryComAssginResList(HttpServletRequest request, ResCustDto resCustDto, String deptId,String level) {
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		resCustDto.setOrgId(orgId);
		List<String> groups = new ArrayList<String>();
		Map<String, String> shareMap = new HashMap<String, String>();
		shareMap.put("account", user.getAccount());
		shareMap.put("orgId", orgId);
		List<String> shareGroupList = cachedService.getShareGroupId(shareMap);
		level = (level == null || "".equals(level)) ? "1" : level;
		String groupId = resCustDto.getGroupId() == null ? "all" : resCustDto.getGroupId();
		String shareAcc = resCustDto.getOwnerAccsStr();
		try {
			if (user.getIssys() != null && user.getIssys() == 1) {
				resCustDto.setOsType(StringUtils.isBlank(resCustDto.getOsType()) ? "1" : resCustDto.getOsType());
				if ("1".equals(resCustDto.getOsType())) {
					resCustDto.setAdminAcc(user.getAccount());
					List<String> list = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					if (!list.contains(user.getAccount())) {
						list.add(user.getAccount());
					}
					resCustDto.setOwnerAccs(list);
				} else if ("2".equals(resCustDto.getOsType())) {
					resCustDto.setOwnerAccs(null);
					resCustDto.setOwnerAcc(user.getAccount());
				} else {
					// 处理拥有者
					if (StringUtils.isNotBlank(resCustDto.getOwnerAccsStr())) {
						resCustDto.setOwnerAccs(Arrays.asList(resCustDto.getOwnerAccsStr().split(",")));
					} else {
						resCustDto.setOwnerAccsStr(user.getAccount());
						resCustDto.setOwnerAccs(Arrays.asList(user.getAccount()));
						resCustDto.setOwnerUserIdsStr(user.getId());
					}
				}
				if (deptId == null || "".equals(deptId)) {
					// 查询 所选部门成员列表
					if (StringUtils.isNotBlank(user.getGroupId())) { // 查询该部门以及子部门成员
						groups = shareGroupList;
						if (groups.size() == 0 || groups == null) {
							groups.add(user.getGroupId());
						}
					}
				} else {
					groups = Arrays.asList(deptId.split(","));
					groups = getGroupList(groups,shareGroupList);
				}
			}

			// 账号
			resCustDto.setDeptIds(groups);
			if (groups != null && groups.size() > 0) {
				deptId = "";
				for (String str : groups) {
					deptId = deptId + str + ",";
				}
			}
			resCustDto.setDeptIds(groups);
			resCustDto.setOrgId(orgId);
			resCustDto.setInputAcc(user.getAccount());
			resCustDto.setResGroupId(groupId);
			List<Map<String, Object>> resGroups = cachedService.getResGroupList1(orgId);
			request.setAttribute("resGroups", resGroups);
			if ("all".equals(groupId)) {
				resCustDto.setResGroupId("");
				resCustDto.setGroupId("");
			}
			request.setAttribute("resCustDto", resCustDto);
			request.setAttribute("groupId", groupId);
			request.setAttribute("deptId", deptId);
			request.setAttribute("shareAcc", shareAcc);
			request.setAttribute("level", level);
			// 所属行业
			List<OptionBean> companyTrades = cachedService.getOptionList("companyTrade",user.getOrgId());
			request.setAttribute("companyTrades",companyTrades);
			List<CustSearchSet> tlist = getIsTextQueryList(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_10.getState());
			request.setAttribute("tList", tlist); // 文本类型查询字段列表
			Map<String,String> map1 = getIsDefinedNameList(user.getOrgId(),user.getIsState());
			request.setAttribute("definedNameMap", map1);
			// 用于筛选项排序
			Map<String,Integer> mutilSearchCodeSortMap = getUnTestSearchSort(SysEnum.SEARCH_SET_MODULE_10.getState(),user.getOrgId(),user.getIssys());
			request.setAttribute("mutilSearchCodeSortMap",mutilSearchCodeSortMap);
			List<HighSearchDto> dtos = cachedService.getHighSearch(SysEnum.SEARCH_SET_MODULE_10.getState(), user.getOrgId(), user.getIssys());
			List<HighSearchDto> definedDtos = new ArrayList<HighSearchDto>();
			for(HighSearchDto dto : dtos){
				if(dto.getIsFiledSet() ==1 && dto.getIsQuery() == 1 ){
					definedDtos.add(dto);
				}
			}			
			request.setAttribute("definedSearchCodes",definedDtos); // 自定义非文本项查询集合
			// 需隐藏列的序号
			List<Integer> sorts = getHideSortListCode(SysEnum.SEARCH_SET_MODULE_10.getState(),user.getOrgId(),user.getIsState().toString());
			request.setAttribute("sorts", sorts);
			setIsReplaceWord(request);

		} catch (Exception e) {
			logger.error("查询已分配资源异常。logacc=" + user.getAccount() + "|shareAcc=" + shareAcc + "|groupId=" + groupId, e);
		}
		return "/tsm/resource/queryComAssginResList";
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
		for(String[]strs : SearchListShowCodeDto.modult_10_List){
			if(("2".equals(strs[2]) || isState.equals(strs[2])) && map.get(strs[0]) == null){
				rList.add(Integer.parseInt(strs[1]) + 2); // 存储需要隐藏的排序值 . 因为第一列是复选框、第二列是操作列，所以加了2
			}
		}		
		return rList;
	}

	@ResponseBody
	@RequestMapping(value = "getAssginResJson",method=RequestMethod.POST)
	public Map<String, Object> getAssginResJson(HttpServletRequest request, ResCustDto resCustDto, String deptId,String level) {
		Map<String, Object> jsonMap = new HashMap<>();
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		Map<String, String> shareMap = new HashMap<String, String>();
		shareMap.put("account", user.getAccount());
		shareMap.put("orgId", orgId);
		List<String> shareGroupList = cachedService.getShareGroupId(shareMap);
		level = (level == null || "".equals(level)) ? "1" : level;
		String groupId = (resCustDto.getGroupId() == null || "".equals(resCustDto.getGroupId())) ? "all" : resCustDto.getGroupId();
		List<String> groups = new ArrayList<String>();
		String shareAcc = resCustDto.getOwnerAccsStr();
		resCustDto.setOrgId(orgId);
		resCustDto.setState(user.getIsState());
		try {
			if (user.getIssys() != null && user.getIssys() == 1) {
				if (StringUtils.isNotBlank(resCustDto.getOwnerAccsStr())) {
					resCustDto.setOsType("3");
				}else if (StringUtils.isBlank(resCustDto.getOwnerAccsStr()) && StringUtils.isBlank(resCustDto.getOsType())){
					resCustDto.setOsType("1");
				}
				if ("1".equals(resCustDto.getOsType())) {
					resCustDto.setAdminAcc(user.getAccount());
					List<String> list = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					if (!list.contains(user.getAccount())) {
						list.add(user.getAccount());
					}
					resCustDto.setOwnerAccs(list);
				} else if ("2".equals(resCustDto.getOsType())) {
					resCustDto.setOwnerAccs(null);
					resCustDto.setOwnerAcc(user.getAccount());
				} else {
					// 处理拥有者
					if (StringUtils.isNotBlank(resCustDto.getOwnerAccsStr())) {
						resCustDto.setOwnerAccs(Arrays.asList(resCustDto.getOwnerAccsStr().split(",")));
					} else {
						resCustDto.setOwnerAccsStr(user.getAccount());
						resCustDto.setOwnerAccs(Arrays.asList(user.getAccount()));
						resCustDto.setOwnerUserIdsStr(user.getId());
					}
				}

				if (deptId == null || "".equals(deptId)) {
					// 查询 所选部门成员列表
					if (StringUtils.isNotBlank(user.getGroupId())) { // 查询该部门以及子部门成员
						groups = shareGroupList;
						if (groups.size() == 0 || groups == null) {
							groups.add(user.getGroupId());
						}
					}
				} else {
					groups = Arrays.asList(deptId.split(","));
					groups = getGroupList(groups,shareGroupList);
				}
			}

			// 账号
			resCustDto.setDeptIds(groups);
			if (groups != null && groups.size() > 0) {
				deptId = "";
				for (String str : groups) {
					deptId = deptId + str + ",";
				}
			}
			resCustDto.setDeptIds(groups);
			resCustDto.setOrgId(orgId);
			resCustDto.setInputAcc(user.getAccount());
			resCustDto.setResGroupId(groupId);
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
			if (resCustDto.getoDateType() != null && !"".equals(resCustDto.getoDateType()) && !"5".equals(resCustDto.getoDateType())) {
				resCustDto.setStartDate(getStartDateStr(new Integer(resCustDto.getoDateType())));
				resCustDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
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
			List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_10.getState());
	    	
			String key = CachedNames.CACHE_RES+CachedNames.SEPARATOR+MD5Utils.getMD5String(JSON.toJSONString(resCustDto));
	    	Map<String, Object> cacheList = (Map<String, Object>) CachedUtil.getInstance().get(key);
	    	if(cacheList!=null){
	    		return cacheList;
	    	}
			List<ResCustDto> resList = new ArrayList<ResCustDto>();
			if (groups != null && groups.size() > 0) {
				resList = comResourceService.queryAssginResListPage(resCustDto,multiSearchList);
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
					List<String> multiShowList = cachedService.getMultiSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_10.getState());
					List<String> singleShowList = cachedService.getSingleSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_10.getState());
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
			setResList(orgId, resList, true);
			request.setAttribute("reslist", resList);
			request.setAttribute("resCustDto", resCustDto);
			// 是否有数据标记
			request.setAttribute("groupId", groupId);
			request.setAttribute("deptId", deptId);
			request.setAttribute("shareAcc", shareAcc);
			setIsReplaceWord(request);
			jsonMap.put("item", resCustDto);
			jsonMap.put("list", resList);
			CachedUtil.getInstance().set(key, jsonMap, 10);
		} catch (Exception e) {
			logger.error("查询已分配资源异常。logacc=" + user.getAccount() + "|shareAcc=" + shareAcc + "|groupId=" + groupId, e);
		}
		return jsonMap;
	}

	/**
	 * 跳转合并分组页面
	 * 
	 * @return
	 * @create 2015年11月18日 下午4:29:23 WUWEI
	 * @history
	 */
	@RequestMapping(value = "toMergeGroup")
	public String toMergeGroup(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			List<Map<String, Object>> resourceGroupList = cachedService.getResGroupList1(user.getOrgId());
			request.setAttribute("resourceGroupList", resourceGroupList);
		} catch (Exception e) {
			logger.error("合并分组异常：loginAcc=" + user.getOrgId(), e);
			throw new SysRunException(e);
		}
		return "tsm/resource/mergeGroup";
	}

	/**
	 * 合并分组
	 * 
	 * @param request
	 * @param ids
	 * @param newGroup
	 * @param entity
	 * @create 2015年11月18日 下午4:34:42 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "mergeGroup")
	public String mergeGroup(HttpServletRequest request, String ids, String newGroup, ResCustDto entity) {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<ResourceGroupBean> resourceGroupList = comResourceGroupService.queryResGroup(ShiroUtil.getUser().getOrgId());
		request.setAttribute("newGroupList", resourceGroupList);
		return comResourceService.saveMergeGroup(ids, newGroup, entity, user.getOrgId(), user.getAccount(),user.getName());
	}

	/**
	 * 回收资源
	 * 
	 * @return
	 * @create 2015年11月18日 下午5:32:47 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "recyleRes")
	public String recyleRes(HttpServletRequest request, String ids,String accs,String taskId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String, String> paramMap = new HashMap<String, String>();
		String[] idsArr = ids.split(",");
		String[] accs_ = accs.split("\\,");
		Map<String, List<String>> logMap = new HashMap<>();
		for(int i = 0 ; i < accs_.length ; i++) {
			List<String> list = new ArrayList<>();
			if (logMap.keySet().contains(accs_[i])) {
				list = logMap.get(accs_[i]);
			}
			list.add(idsArr[i]);
			logMap.put(accs_[i], list);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("total", idsArr.length);
		map.put("finished", 0);
		map.put("status", true);
		cachedService.setProgress(user.getOrgId(), taskId, map);
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("recyleRes reqId= " + reqId + ",account=" + user.getAccount() + ",ids=" + ids);
		String result = "";
		try {
			String batchId =  SysBaseModelUtil.getModelId();
			result = comResourceService.updateBatchRes(reqId, user.getOrgId(), user.getAccount(),logMap,user.getName(),taskId,map,batchId,"assign");
		} catch (Exception e) {
			logger.error("recyleRes reqId=" + reqId + e.getMessage(), e);
		}
		return result;
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
	

}
