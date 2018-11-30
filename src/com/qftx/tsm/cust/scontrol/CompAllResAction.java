package com.qftx.tsm.cust.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.MD5Utils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.area.service.AreaService;
import com.qftx.tsm.cust.dao.ComResourceMapper;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.cust.service.ComResourceGroupService;
import com.qftx.tsm.cust.service.ComResourceService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dao.DataDictionaryMapper;
import com.qftx.tsm.option.service.DataDictionaryService;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.dto.HighSearchDto;
import com.qftx.tsm.sys.dto.SearchListShowCodeDto;
import com.qftx.tsm.sys.enums.SysEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 所有资源查询控制
 * 
 * @author: wuwei
 * @since: 2015年12月1日 下午2:52:56
 * @history:
 */
@Controller
@RequestMapping(value = "/res/resmanage/")
public class CompAllResAction extends BaseAction {
	private static Logger logger = Logger.getLogger(CompAllResAction.class);
	@Autowired
	transient private ComResourceService comResourceService;
	@Autowired
	transient private ComResourceGroupService comResourceGroupService;
	@Autowired
	transient private OrgGroupService orgGroupService;
	@Autowired
	transient private OrgGroupUserService orgGroupUserService;
	@Autowired
	transient private CachedService cachedService;
	@Resource
	private DataDictionaryService dataDictionaryService;
	@Resource
	private ComResourceMapper comResourceMapper;
	@Resource
	private DataDictionaryMapper dataDictionaryMapper;
	@Autowired
	private AreaService areaService;

	/**
	 * 查詢公司所有資源客户信息
	 * 
	 * @param request
	 * @param resDto
	 * @return
	 * @create 2017年7月13日 下午3:06:56 zjh
	 * @history
	 */
	@RequestMapping(value = "queryComResList")
	public String queryComResList(HttpServletRequest request, String deptId, ResCustDto resCustDto,String level) {
		setDyField(request);
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		List<String> groups = new ArrayList<String>();
		Map<String, String> shareMap = new HashMap<String, String>();
		shareMap.put("account", user.getAccount());
		shareMap.put("orgId", orgId);
		List<String> shareGroupList = cachedService.getShareGroupId(shareMap);
		level = (level == null || "".equals(level)) ? "1" : level;
		String groupId = (resCustDto.getGroupId() == null || "".equals(resCustDto.getGroupId())) ? "all" : resCustDto.getGroupId();
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
			// 账号
			resCustDto.setDeptIds(groups);
			if (groups != null && groups.size() > 0) {
				deptId = "";
				for (String str : groups) {
					deptId = deptId + str + ",";
				}
			}
			if (resCustDto.getoDateType() != null && !"".equals(resCustDto.getoDateType()) && !"5".equals(resCustDto.getoDateType())) {
				resCustDto.setStartDate(getStartDateStr(new Integer(resCustDto.getoDateType())));
				resCustDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			resCustDto.setDeptIds(groups);
			resCustDto.setOrgId(orgId);
			resCustDto.setResGroupId(groupId);
			resCustDto.setInputAcc(user.getAccount());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			map.put("resType", "");
			map.put("deptIds", groups);
			map.put("ownerAccs", null);
			map.put("inputAcc", user.getAccount());
			List<Map<String, Object>> resGroups = cachedService.getResGroupList1(orgId);
			request.setAttribute("resGroups", resGroups);
			if ("all".equals(groupId)) {
				resCustDto.setResGroupId("");
				resCustDto.setGroupId("");
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

			// 是否有数据标记
			request.setAttribute("groupId", groupId);
			request.setAttribute("deptId", deptId);
			request.setAttribute("entity", resCustDto);
			request.setAttribute("isShare", getIsShare());
			request.setAttribute("level", level);
			// 所属行业
			List<OptionBean> companyTrades = cachedService.getOptionList("companyTrade",user.getOrgId());
			request.setAttribute("companyTrades",companyTrades);
			List<CustSearchSet> tlist = getIsTextQueryList(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_11.getState());
			request.setAttribute("tList", tlist); // 文本类型查询字段列表
			Map<String,String> map1 = getIsDefinedNameList(user.getOrgId(),user.getIsState());
			request.setAttribute("definedNameMap", map1);
			// 用于筛选项排序
			Map<String,Integer> mutilSearchCodeSortMap = getUnTestSearchSort(SysEnum.SEARCH_SET_MODULE_11.getState(),user.getOrgId(),user.getIssys());
			request.setAttribute("mutilSearchCodeSortMap",mutilSearchCodeSortMap);
			List<HighSearchDto> dtos = cachedService.getHighSearch(SysEnum.SEARCH_SET_MODULE_11.getState(), user.getOrgId(), user.getIssys());
			List<HighSearchDto> definedDtos = new ArrayList<HighSearchDto>();
			for(HighSearchDto dto : dtos){
				if(dto.getIsFiledSet() ==1 && dto.getIsQuery() == 1 ){
					definedDtos.add(dto);
				}
			}			
			request.setAttribute("definedSearchCodes",definedDtos); // 自定义非文本项查询集合
			// 需隐藏列的序号
			List<Integer> sorts = getHideSortListCode(SysEnum.SEARCH_SET_MODULE_11.getState(),user.getOrgId(),user.getIsState().toString());
			request.setAttribute("sorts", sorts);
			setIsReplaceWord(request);

		} catch (Exception e) {
			logger.error("查詢公司所有資源客户信息异常。logacc=" + user.getAccount() + "|loginName=" + user.getAccount() + "|groupId=" + user.getOrgId()+ e.getMessage(), e);
		}
		return "/tsm/resource/queryAllResList";
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
		for(String[]strs : SearchListShowCodeDto.modult_11_List){
			if(("2".equals(strs[2]) || isState.equals(strs[2])) && map.get(strs[0]) == null){
				rList.add(Integer.parseInt(strs[1]) + 2); // 存储需要隐藏的排序值 . 因为第一列是复选框、第二列是操作列，所以加了2
			}
		}		
		return rList;
	}
	
	@ResponseBody
	@RequestMapping(value = "getAllResJson",method=RequestMethod.POST)
	public Map<String, Object> getAllResJson(HttpServletRequest request, String deptId, ResCustDto resCustDto,String level) {
		Map<String, Object> jsonMap = new HashMap<>();
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		Map<String, String> shareMap = new HashMap<String, String>();
		shareMap.put("account", user.getAccount());
		shareMap.put("orgId", orgId);
		List<String> shareGroupList = cachedService.getShareGroupId(shareMap);
		List<String> groups = new ArrayList<String>();
		String shareAcc = resCustDto.getOwnerAccsStr();
		resCustDto.setState(user.getIsState());
		level = (level == null || "".equals(level)) ? "1" : level;
		String groupId = (resCustDto.getGroupId() == null || "".equals(resCustDto.getGroupId())) ? "all" : resCustDto.getGroupId();
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
			resCustDto.setResGroupId(groupId);
			resCustDto.setInputAcc(user.getAccount());
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
			List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_11.getState());
	    	String key = CachedNames.CACHE_RES+CachedNames.SEPARATOR+MD5Utils.getMD5String(JSON.toJSONString(resCustDto));
	    	Map<String, Object> cacheList = (Map<String, Object>) CachedUtil.getInstance().get(key);
	    	if(cacheList!=null){
	    		return cacheList;
	    	}
			List<ResCustDto> resList = new ArrayList<ResCustDto>();
			if (groups != null && groups.size() > 0) {
				resList = comResourceService.queryResListPage(resCustDto,multiSearchList);
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
					List<String> multiShowList = cachedService.getMultiSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_11.getState());
					List<String> singleShowList = cachedService.getSingleSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_11.getState());
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
			// 是否有数据标记
			jsonMap.put("item", resCustDto);
			jsonMap.put("list", resList);
			CachedUtil.getInstance().set(key, jsonMap, 10);
		} catch (Exception e) {
			logger.error("查询所有资源异常。logacc=" + user.getAccount() + "|groupId=" + groupId, e);
		}
		return jsonMap;
	}
	
	@ResponseBody
	@RequestMapping("getResLimitNum")
	public String getResLimitNum(HttpServletRequest request, String code) {
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = getFollowSet(user.getOrgId(), code);
			if ("1".equals(map.get("isOpen")) && "1".equals(map.get("subIsOpen"))) {
				map.put("ownerAcc", user.getAccount());
				map.put("orgId", user.getOrgId());
				int myResNum = comResourceMapper.findresourceNum(map);// 个人拥有资源数量
				if (myResNum >= new Integer(map.get("val"))) {
					return "1";
				} else {
					return "0";
				}
			} else {
				return "0";
			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
	}

	@ResponseBody
	@RequestMapping("getCustLimitNum")
	public String getCustLimitNum(HttpServletRequest request, String code) {
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = getFollowSet(user.getOrgId(), code);
			if ("1".equals(map.get("isOpen")) && "1".equals(map.get("subIsOpen"))) {
				map.put("ownerAcc", user.getAccount());
				map.put("orgId", user.getOrgId());
				int myResNum = comResourceMapper.findCustNum(map);// 个人拥有资源数量
				if (myResNum >= new Integer(map.get("val"))) {
					return "1";
				} else {
					return "0";
				}
			} else {
				return "0";
			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
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
