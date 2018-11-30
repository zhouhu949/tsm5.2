package com.qftx.tsm.report.scontrol;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.dao.ResourceGroupMapper;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.report.bean.CustomReportBean;
import com.qftx.tsm.report.bean.CustomReportShareBean;
import com.qftx.tsm.report.dto.CustomReportDoubleShowDto;
import com.qftx.tsm.report.dto.CustomReportSingleDto;
import com.qftx.tsm.report.dto.ResCustReportDto;
import com.qftx.tsm.report.dto.ShowReportDatailDto;
import com.qftx.tsm.report.service.CustomReportService;
import com.qftx.tsm.report.service.CustomReportShareService;
import com.qftx.tsm.sms.service.TsmMessageSendService;
import com.qftx.tsm.sys.bean.CustFieldSet;
@Controller
@RequestMapping(value = "/custom/report/")
public class CustomReportAction extends BaseAction{
	private static Logger logger = Logger.getLogger(CustomReportAction.class);
	@Autowired 
	private CachedService cachedService;
	@Autowired 
	private CustomReportService customReportService;
	@Autowired 
	private ResCustInfoService resCustInfoService;
	@Autowired
	private CustomReportShareService customReportShareService;
	@Autowired
	private TsmMessageSendService tsmMessageSendService;
	@Autowired
	private ResourceGroupMapper resourceGroupMapper;
	@Autowired
	private OrgGroupUserService orgGroupUserService;
	
	
	/**
	 * 添加自定义报表页面
	 */
	@RequestMapping(value = "toAddCustomReport")
	public String toAddCustomReport(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		//从缓存读取客户分类列表
		List<OptionBean> custTypeList = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO,orgId);
		request.setAttribute("custTypeList",custTypeList);
		//客户状态
		//从缓存读取销售进程列表
		List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,orgId);
		request.setAttribute("options",options);		
		//所有者 get_all_group_json
		// 资源分组
		List<Map<String, Object>> groups = cachedService.getResGroupList1(orgId);
		request.setAttribute("groupList",groups);
		// 所属行业
		List<OptionBean> companyTrades = cachedService.getOptionList("companyTrade",user.getOrgId());
		request.setAttribute("companyTrades",companyTrades);
		//所在地区 有口子
		//资源录入部门 口子
		//客户来源 
		//放弃类型
		//单选多选自定义类型
		List<CustFieldSet> defineds = new ArrayList<>();
		List<CustFieldSet> defineds1 = new ArrayList<>();
		List<CustFieldSet> defineds2 = new ArrayList<>();
		if (user.getIsState() == 1) {
			defineds = cachedService.getComFiledSets(orgId);
		}else if(user.getIsState() == 0) {
			defineds = cachedService.getPersonFiledSets(orgId);
		}
		if (defineds.size() > 0) {
			for (CustFieldSet fieldSet : defineds) {
				if (fieldSet.getDataType() == 3 && fieldSet.getOptionList().size() > 0 && fieldSet.getFieldCode().contains("defined")) {
					defineds1.add(fieldSet);
				}
			}
		}
		if (defineds.size() > 0) {
			for (CustFieldSet fieldSet : defineds) {
				if (fieldSet.getDataType() == 4 && fieldSet.getOptionList().size() > 0 && fieldSet.getFieldCode().contains("defined")) {
					defineds2.add(fieldSet);
				}
			}
		}
		request.setAttribute("singleDefineds", defineds1);
		request.setAttribute("multipleDefineds", defineds2);
		return "report/customRoport/newCustReport";
	}
	/**
	 * 获取客户分类列表
	 */
	@ResponseBody
	@RequestMapping(value = "getCustTypeList")
	public List<OptionBean> getCustTypeList(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		//从缓存读取客户分类列表
		List<OptionBean> custTypeList = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO,orgId);
		return custTypeList;
		
	}
	/**
	 * 获取销售进程
	 */
	@ResponseBody
	@RequestMapping(value = "getSaleProcessList")
	public List<OptionBean> getSaleProcessList(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		//从缓存读取销售进程列表
		List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,orgId);
		return options;
		
	}
	/**
	 * 获取资源分组
	 */
	@ResponseBody
	@RequestMapping(value = "getGroupList")
	public List<Map<String, Object>> getGroupList(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		// 资源分组
		List<Map<String, Object>> groups = cachedService.getResGroupList1(orgId);
		return groups;
	}
	/**
	 * 获取所属行业列表
	 */
	@ResponseBody
	@RequestMapping(value = "getCompanyTradeList")
	public List<OptionBean> getCompanyTradeList(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		// 所属行业
		List<OptionBean> companyTrades = cachedService.getOptionList("companyTrade",user.getOrgId());
		return companyTrades;
	}
	/**
	 * 获取多选自定义属性
	 */
	@ResponseBody
	@RequestMapping(value = "getMultipleDefinedList")
	public List<CustFieldSet> getMultipleDefinedList(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		
		List<CustFieldSet> defineds = new ArrayList<>();
		List<CustFieldSet> defineds1 = new ArrayList<>();
		if (user.getIsState() == 1) {
			defineds = cachedService.getComFiledSets(orgId);
		}else if(user.getIsState() == 0) {
			defineds = cachedService.getPersonFiledSets(orgId);
		}
		if (defineds.size() > 0) {
			for (CustFieldSet fieldSet : defineds) {
				if (fieldSet.getDataType() == 4 && fieldSet.getOptionList().size() > 0 && fieldSet.getFieldCode().contains("defined")) {
					defineds1.add(fieldSet);
				}
			}
		}
		return defineds1;
	}
	/**
	 * 获取单选自定义属性
	 */
	@ResponseBody
	@RequestMapping(value = "getSingleDefinedList")
	public List<CustFieldSet> getSingleDefinedList(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		
		List<CustFieldSet> defineds = new ArrayList<>();
		List<CustFieldSet> defineds1 = new ArrayList<>();
		if (user.getIsState() == 1) {
			defineds = cachedService.getComFiledSets(orgId);
		}else if(user.getIsState() == 0) {
			defineds = cachedService.getPersonFiledSets(orgId);
		}
		if (defineds.size() > 0) {
			for (CustFieldSet fieldSet : defineds) {
				if (fieldSet.getDataType() == 3 && fieldSet.getOptionList().size() > 0 && fieldSet.getFieldCode().contains("defined")) {
					defineds1.add(fieldSet);
				}
			}
		}
		return defineds1;
	}
	/**
	 * 添加自定义报表
	 */
	@ResponseBody
	@RequestMapping(value = "addCustomReport")
	public BaseJsonResult addCustomReport(HttpServletRequest request,ResCustReportDto dto,String customReportName) {
		try {
			ShiroUser user = ShiroUtil.getUser();
			String orgId = user.getOrgId();
			dto.setOrgId(orgId);
			
			CustomReportBean bean = new CustomReportBean();
			bean.setData(JSON.toJSON(dto).toString());
			bean.setCustomReportId(SysBaseModelUtil.getModelId());
			bean.setCustomReportName(customReportName);
			bean.setInputAcc(user.getAccount());
			bean.setInputDate(new Date());
			bean.setIsDel(0);
			if (dto.getGroupByType2() != null && !"".equals(dto.getGroupByType2())) {
				bean.setIsDouble(2);
			}else {
				bean.setIsDouble(1);
			}
			bean.setOrgId(orgId);
			customReportService.create(bean);
			return BaseJsonResult.success("新增成功");
		} catch (Exception e) {
			logger.error("双维度自定义报表异常！", e);
			e.printStackTrace();
			return BaseJsonResult.error("新增失败");
		}
	}
	
	/**
	 * 判断名称是否重复
	 */
	@ResponseBody
	@RequestMapping(value = "judgeIsHaveSameName")
	public BaseJsonResult judgeIsHaveSameName(HttpServletRequest request,String customReportName,String oldCustomReportName) {
		try {
			ShiroUser user = ShiroUtil.getUser();
			String orgId = user.getOrgId();
			if (oldCustomReportName == null || "".equals(oldCustomReportName)) {
				CustomReportBean bean = new CustomReportBean();
				bean.setOrgId(orgId);
				bean.setCustomReportName(customReportName);
				bean.setIsDel(0);
				bean.setInputAcc(user.getAccount());
				List<CustomReportBean> list = customReportService.findByCondtion(bean);
				if(list.size() == 0 || list == null){
					return BaseJsonResult.success();
				}else {
					return BaseJsonResult.error("名称重复");
				}
			}else {
				if (customReportName.equals(oldCustomReportName)) {
					return BaseJsonResult.success();
				}else {
					CustomReportBean bean = new CustomReportBean();
					bean.setOrgId(orgId);
					bean.setCustomReportName(customReportName);
					bean.setIsDel(0);
					bean.setInputAcc(user.getAccount());
					List<CustomReportBean> list = customReportService.findByCondtion(bean);
					if(list.size() == 0 || list == null){
						return BaseJsonResult.success();
					}else {
						return BaseJsonResult.error("名称重复");
					}
				}
			}
		} catch (Exception e) {
			logger.error("双维度自定义报表异常！", e);
			e.printStackTrace();
			return BaseJsonResult.error("查询异常");
		}
	}
	
	/**
	 * 修改自定义报表页面
	 */
	@RequestMapping(value = "toEditCustomReport")
	public String toEditCustomReport(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		String customReportId = request.getParameter("customReportId");
		CustomReportBean bean = new CustomReportBean();
		bean.setOrgId(orgId);
		bean.setCustomReportId(customReportId);
		CustomReportBean crb = customReportService.getByCondtion(bean);
		String data = crb.getData();
		ResCustReportDto dto = JSON.parseObject(data, ResCustReportDto.class);
		
		//从缓存读取客户分类列表
		List<OptionBean> custTypeList = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO,orgId);
		request.setAttribute("custTypeList",custTypeList);
		//客户状态
		//从缓存读取销售进程列表
		List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,orgId);
		request.setAttribute("options",options);		
		//所有者 get_all_group_json
		// 资源分组
		List<Map<String, Object>> groups = cachedService.getResGroupList1(orgId);
		request.setAttribute("groupList",groups);
		// 所属行业
		List<OptionBean> companyTrades = cachedService.getOptionList("companyTrade",user.getOrgId());
		request.setAttribute("companyTrades",companyTrades);
		//所在地区 有口子
		Map<Integer, String> provinceMap = new HashMap<Integer, String>();
		Map<Integer, String> cityMap = new HashMap<Integer, String>();
		Map<Integer, String> countyMap = new HashMap<Integer, String>();
		provinceMap = cachedService.getAreaMap(CachedNames.PROVINCE);
		cityMap = cachedService.getAreaMap(CachedNames.CITY);
		countyMap = cachedService.getAreaMap(CachedNames.COUNTY);
		if (dto.getProvinceId() != null && !"".equals(dto.getProvinceId())) {
			dto.setProvinceName(provinceMap.get(Integer.parseInt(dto.getProvinceId())));
		}
		if (dto.getCityId() != null && !"".equals(dto.getCityId())) {
			dto.setCityName(cityMap.get(Integer.parseInt(dto.getCityId())));
		}
		if (dto.getCountyId() != null && !"".equals(dto.getCountyId())) {
			dto.setCountyName(countyMap.get(Integer.parseInt(dto.getCountyId())));
		}
		//资源录入部门 口子
		//客户来源 
		//放弃类型
		//单选多选自定义类型
		List<CustFieldSet> defineds = new ArrayList<>();
		List<CustFieldSet> defineds1 = new ArrayList<>();
		List<CustFieldSet> defineds2 = new ArrayList<>();
		if (user.getIsState() == 1) {
			defineds = cachedService.getComFiledSets(orgId);
		}else if(user.getIsState() == 0) {
			defineds = cachedService.getPersonFiledSets(orgId);
		}
		if (defineds.size() > 0) {
			for (CustFieldSet fieldSet : defineds) {
				if (fieldSet.getDataType() == 3 && fieldSet.getOptionList().size() > 0 && fieldSet.getFieldCode().contains("defined")) {
					defineds1.add(fieldSet);
				}
			}
		}
		if (defineds.size() > 0) {
			for (CustFieldSet fieldSet : defineds) {
				if (fieldSet.getDataType() == 4 && fieldSet.getOptionList().size() > 0 && fieldSet.getFieldCode().contains("defined")) {
					defineds2.add(fieldSet);
				}
			}
		}
		request.setAttribute("bean", JSONObject.toJSON(dto));
		request.setAttribute("item", JSONObject.toJSON(crb));
		request.setAttribute("singleDefineds", defineds1);
		request.setAttribute("multipleDefineds", defineds2);
		return "report/customRoport/editCustReport";
	}
	
	/**
	 * 修改自定义报表
	 */
	@ResponseBody
	@RequestMapping(value = "editCustomReport")
	public BaseJsonResult editCustomReport(HttpServletRequest request,ResCustReportDto dto,String customReportName) {
		try {
			ShiroUser user = ShiroUtil.getUser();
			String orgId = user.getOrgId();
			dto.setOrgId(orgId);
			
			String customReportId = request.getParameter("customReportId");
			CustomReportBean bean = new CustomReportBean();
			bean.setData(JSON.toJSON(dto).toString());
			bean.setCustomReportId(customReportId);
			bean.setCustomReportName(customReportName);
			bean.setUpdateDate(new Date());
			if (dto.getGroupByType2() != null && !"".equals(dto.getGroupByType2())) {
				bean.setIsDouble(2);
			}else {
				bean.setIsDouble(1);
			}
			bean.setOrgId(orgId);
			customReportService.update(bean);
			return BaseJsonResult.success("编辑成功");
		} catch (Exception e) {
			logger.error("双维度自定义报表异常！", e);
			e.printStackTrace();
			return BaseJsonResult.error("编辑失败");
		}
	}
	
	/**
	 * 判断是否分享过
	 */
	@ResponseBody
	@RequestMapping(value = "judgeIsShare")
	public BaseJsonResult judgeIsShare(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getUser();
			String orgId = user.getOrgId();
			String customReportId = request.getParameter("customReportId");
			Map<String, String> map = new HashMap<String, String>();
			map.put("orgId",orgId);
			map.put("customReportId", customReportId);
			List<String> shareAcc = customReportShareService.getShareAccsByReportId(map);
			if (shareAcc.size() > 0) {
				return BaseJsonResult.error();
			}else {
				return BaseJsonResult.success();
			}
		} catch (Exception e) {
			logger.error("双维度自定义报表异常！", e);
			e.printStackTrace();
		}
		return BaseJsonResult.success();
	}
	
	/**
	 * 删除自定义报表
	 */
	@ResponseBody
	@RequestMapping(value = "delCustomReport")
	public BaseJsonResult delCustomReport(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getUser();
			String orgId = user.getOrgId();
			String customReportId = request.getParameter("customReportId");
			CustomReportBean bean = new CustomReportBean();
			bean.setCustomReportId(customReportId);
			bean.setUpdateDate(new Date());
			bean.setOrgId(orgId);
			bean.setIsDel(1);
			customReportService.delete(bean);
			CustomReportShareBean share = new CustomReportShareBean();
			share.setOrgId(orgId);
			share.setCustomReportId(customReportId);
			share.setUpdateDate(new Date());
			customReportShareService.deleteShare(share);
			return BaseJsonResult.success("删除成功");
		} catch (Exception e) {
			logger.error("双维度自定义报表异常！", e);
			e.printStackTrace();
			return BaseJsonResult.error("删除失败");
		}
	}
	
	/**
	 * 新增分享人页面
	 */
	@RequestMapping(value = "toSetShare")
	public String toSetShare(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		String customReportId = request.getParameter("customReportId");
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgId",orgId);
		map.put("customReportId", customReportId);
		List<String> shareAcc = customReportShareService.getShareAccsByReportId(map);
		String shareAccStr = "";
		if (shareAcc != null && shareAcc.size() > 0) {
			for (String string : shareAcc) {
				shareAccStr += string;
				shareAccStr += ",";
			}
		}
		request.setAttribute("shareAcc", shareAccStr);
		request.setAttribute("customReportId", customReportId);
		return "report/customRoport/shareReportWindow";
	}
	
	
	
	/**
	 * 设置分享人
	 */
	@ResponseBody
	@RequestMapping(value = "setShare")
	public BaseJsonResult setShare(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getUser();
			String orgId = user.getOrgId();
			String accs = request.getParameter("accs");
			String customReportId = request.getParameter("customReportId");
			Map<String, String> map = new HashMap<String, String>();
			map.put("orgId",orgId);
			map.put("customReportId", customReportId);
			List<String> oldShareAccs = customReportShareService.getShareAccsByReportId(map);
			List<String> willDelAccs = new ArrayList<>();
			List<String> willAddAccs = new ArrayList<>();
			List<String> shareAccs = new ArrayList<>();
			if (!("".equals(accs) || accs == null)) {
				shareAccs = Arrays.asList(accs.split(","));
				if (oldShareAccs.size() > 0) {
					for(String acc : oldShareAccs) {
						if (!shareAccs.contains(acc)) {
							willDelAccs.add(acc);
						}
					}
					for(String acc : shareAccs) {
						if (!oldShareAccs.contains(acc)) {
							willAddAccs.add(acc);
						}
					}
				}else {
					willAddAccs.addAll(shareAccs);
				}
				if (willAddAccs.size() > 0) {
					for(String acc : willAddAccs){
						CustomReportShareBean bean = new CustomReportShareBean();
						bean.setOrgId(orgId);
						bean.setCustomReportId(customReportId);
						bean.setShareId(SysBaseModelUtil.getModelId());
						bean.setInputDate(new Date());
						bean.setInputAcc(user.getAccount());
						bean.setIsDel(0);
						bean.setShareAcc(acc);
						customReportShareService.create(bean);
					}
				}
				if (willDelAccs.size() > 0) {
					Map<String,Object> map1 = new HashMap<>();
					map1.put("orgId", orgId);
					map1.put("ids", willDelAccs);
					customReportShareService.deleteById(map1);
				}
				
			}else {
				CustomReportShareBean share = new CustomReportShareBean();
				share.setOrgId(orgId);
				share.setCustomReportId(customReportId);
				customReportShareService.delete(share);
			}
			Map<String, String> names = cachedService.getOrgUserNames(orgId);
			if (willAddAccs.size() > 0) {
				for(String acc : willAddAccs) {
					tsmMessageSendService.addSysMessage(orgId, user.getAccount(),acc, "报表分享提醒", names.get(user.getAccount()) == null ? user.getAccount() : names.get(user.getAccount()) + "分享了一个自定义报表给你，请去自定义报表-分享给我的报表中查看！");
				}
			}
			return BaseJsonResult.success("分享成功");
		} catch (Exception e) {
			logger.error("分享自定义报表异常！", e);
			e.printStackTrace();
			return BaseJsonResult.error("分享异常");
		}
	}
	
	/**
	 * 报表详情页面
	 */
	@RequestMapping(value = "toReportDetail")
	public String toReportDetail(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		String customReportId = request.getParameter("customReportId");
		String isDouble = request.getParameter("isDouble");
		String customReportName = request.getParameter("customReportName");
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgId",orgId);
		map.put("customReportId", customReportId);
		List<String> shareAcc = customReportShareService.getShareAccsByReportId(map);
		Map<String,String> nameMap = cachedService.getOrgUserNames(orgId);
		List<String> shareName = new ArrayList<String>();
		for(String acc :shareAcc) {
			if (!"".equals(nameMap.get(acc)) && nameMap.get(acc) != null) {
				shareName.add(nameMap.get(acc));
			}else {	
				shareName.add(acc);
			}
			
		}
		
		CustomReportBean bean = new CustomReportBean();
		bean.setOrgId(orgId);
		bean.setCustomReportId(customReportId);
		CustomReportBean crb = customReportService.getByCondtion(bean);
		String data = crb.getData();
		ResCustReportDto dto = JSON.parseObject(data, ResCustReportDto.class);
		
		//从缓存读取客户分类列表
		List<OptionBean> custTypeList = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO,orgId);
		//客户状态
		//从缓存读取销售进程列表
		List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,orgId);
		//所有者 get_all_group_json
		// 资源分组
		List<ResourceGroupBean> resGroupList= resourceGroupMapper.findResGroup(orgId);
		// 所属行业
		List<OptionBean> companyTrades = cachedService.getOptionList("companyTrade",user.getOrgId());
		//所在地区 有口子
		Map<Integer, String> provinceMap = new HashMap<Integer, String>();
		Map<Integer, String> cityMap = new HashMap<Integer, String>();
		Map<Integer, String> countyMap = new HashMap<Integer, String>();
		provinceMap = cachedService.getAreaMap(CachedNames.PROVINCE);
		cityMap = cachedService.getAreaMap(CachedNames.CITY);
		countyMap = cachedService.getAreaMap(CachedNames.COUNTY);
		//资源录入部门 口子
		List<OrgGroup> orgGroups = orgGroupUserService.getAllDeptList(orgId);
		Map<String,String> orgGroupMap = new HashMap<>();
		for(OrgGroup group : orgGroups) {
			orgGroupMap.put(group.getGroupId(), group.getGroupName());
		}
		//客户来源 
		//放弃类型
		//单选多选自定义类型
		List<CustFieldSet> defineds = new ArrayList<>();
		List<CustFieldSet> defineds1 = new ArrayList<>();
		List<CustFieldSet> defineds2 = new ArrayList<>();
		if (user.getIsState() == 1) {
			defineds = cachedService.getComFiledSets(orgId);
		}else if(user.getIsState() == 0) {
			defineds = cachedService.getPersonFiledSets(orgId);
		}
		if (defineds.size() > 0) {
			for (CustFieldSet fieldSet : defineds) {
				if (fieldSet.getDataType() == 3 && fieldSet.getOptionList().size() > 0 && fieldSet.getFieldCode().contains("defined")) {
					defineds1.add(fieldSet);
				}
			}
		}
		if (defineds.size() > 0) {
			for (CustFieldSet fieldSet : defineds) {
				if (fieldSet.getDataType() == 4 && fieldSet.getOptionList().size() > 0 && fieldSet.getFieldCode().contains("defined")) {
					defineds2.add(fieldSet);
				}
			}
		}
		Map<String, CustFieldSet> defineds3 = new HashMap<>();
		if (defineds.size() > 0) {
			for (CustFieldSet fieldSet : defineds) {
				if ((fieldSet.getDataType() == 3 || fieldSet.getDataType() == 4) && fieldSet.getOptionList().size() > 0 && fieldSet.getFieldCode().contains("defined")) {
					defineds3.put(fieldSet.getFieldCode(),fieldSet);
				}
			}
		}
		List<ShowReportDatailDto> list = new ArrayList<>();
		if (!"".equals(dto.getCustTypeId()) && dto.getCustTypeId() != null && dto.getCustTypeMatch() != null ) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("客户类型");
			show.setMatch(dto.getCustTypeMatch() == 0 ? "等于" : "不等于");
			String value = "";
			String[] custTypeIds = dto.getCustTypeId().split(",");
			for(String custTypeId : custTypeIds) {
				for(OptionBean option : custTypeList) {
					if (custTypeId.equals(option.getOptionlistId())) {
						value += option.getOptionName();
						value += ",";
					}
				}
			}
			if (value.length() > 1) {
				show.setValue(value.substring(0, value.length() - 1));
				list.add(show);
			}
		}
		if (!"".equals(dto.getqStatus()) && dto.getqStatus() != null && dto.getqStatusMatch() != null ) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("客户状态");
			show.setMatch(dto.getqStatusMatch() == 0 ? "等于" : "不等于");
			String value = "";
			String[] qStatuss = dto.getqStatus().split(",");
			for(String qStatus : qStatuss) {
				if ("1".equals(qStatus)) {value += "待分配资源";value += ",";}
				if ("2".equals(qStatus)) {value += "已分配资源";value += ",";}
				if ("3".equals(qStatus)) {value += "意向客户";value += ",";}
				if ("4".equals(qStatus)) {value += "签约客户";value += ",";}
				if ("5".equals(qStatus)) {value += "流失客户";value += ",";}
				if ("6".equals(qStatus)) {value += "沉默客户";value += ",";}
				if ("7".equals(qStatus)) {value += "公海客户";value += ",";}
			}
			if(value.length() > 1){
				show.setValue(value.substring(0, value.length() - 1));
				list.add(show);
			}
		}
		if(!"".equals(dto.getOptionlistId()) && dto.getOptionlistId() != null && dto.getOptionlistMatch() != null ) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("销售进程");
			show.setMatch(dto.getOptionlistMatch() == 0 ? "等于" : "不等于");
			String value = "";
			String[] optionlistIds = dto.getOptionlistId().split(",");
			for(String optionlistId : optionlistIds) {
				for(OptionBean option : options) {
					if (optionlistId.equals(option.getOptionlistId())) {
						value += option.getOptionName();
						value += ",";
					}
				}
			}
			if(value.length() > 1){
				show.setValue(value.substring(0, value.length() - 1));
				list.add(show);
			}
		}
		if(!"".equals(dto.getAccs()) && dto.getAccs() != null && dto.getOwnerAccMatch() != null ) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("所有者");
			show.setMatch(dto.getOwnerAccMatch() == 0 ? "等于" : "不等于");
			String value = "";
			String[] ownerAccs = dto.getAccs().split(",");
			for(String acc : ownerAccs) {
				value += (nameMap.get(acc) == null ? orgGroupMap.get(acc) : nameMap.get(acc));
				value += ",";
			}
			if (value.length() > 1) {
				show.setValue(value.substring(0, value.length() - 1));
				list.add(show);
			}
		}
		if(!"".equals(dto.getResGroupId()) && dto.getResGroupId() != null && dto.getResGroupMatch() != null ) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("资源分组");
			show.setMatch(dto.getResGroupMatch() == 0 ? "等于" : "不等于");
			String value = "";
			String[] resGroupIds = dto.getResGroupId().split(",");
			for(String resGroupId : resGroupIds) {
				for(ResourceGroupBean group : resGroupList) {
					if (resGroupId.equals(group.getResGroupId())) {
						value += group.getGroupName();
						value += ",";
					}
				}
			}
			if (value.length() > 1) {
				show.setValue(value.substring(0, value.length() - 1));
				list.add(show);
			}
		}
		if(!"".equals(dto.getCompanyTrade()) && dto.getCompanyTrade() != null && dto.getCompanyTradeMatch() != null ) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("所属行业");
			show.setMatch(dto.getCompanyTradeMatch() == 0 ? "等于" : "不等于");
			String value = "";
			String[] trades = dto.getCompanyTrade().split(",");
			for(String trade : trades) {
				for(OptionBean option : companyTrades) {
					if (trade.equals(option.getOptionlistId())) {
						value += option.getOptionName();
						value += ",";
					}
				}
			}
			if (value.length() > 1) {
				show.setValue(value.substring(0, value.length() - 1));
				list.add(show);
			}
		}
		if(dto.getAreaMatch() != null ) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("所在地区");
			show.setMatch(dto.getAreaMatch() == 0 ? "等于" : "不等于");
			String value = "";
			if (dto.getProvinceId() != null && !"".equals(dto.getProvinceId())) {
				value += provinceMap.get(Integer.parseInt(dto.getProvinceId()));
			}
			if (dto.getCityId() != null && !"".equals(dto.getCityId())) {
				value += "-";
				value += cityMap.get(Integer.parseInt(dto.getCityId()));
			}
			if (dto.getCountyId() != null && !"".equals(dto.getCountyId())) {
				value += "-";
				value += countyMap.get(Integer.parseInt(dto.getCountyId()));
			}
			if (value.length() > 1) {
				show.setValue(value);
				list.add(show);
			}
		}
		if(!"".equals(dto.getImportDeptId()) && dto.getImportDeptId() != null && dto.getImportDeptMatch() != null ) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("资源录入部门");
			show.setMatch(dto.getImportDeptMatch() == 0 ? "等于" : "不等于");
			String value = "";
			String[] importDeptIds = dto.getImportDeptId().split(",");
			for(String deptId : importDeptIds) {
				for(OrgGroup orgGroup : orgGroups) {
					if (deptId.equals(orgGroup.getGroupId())) {
						value += orgGroup.getGroupName();
						value += ",";
					}
				}
			}
			if (value.length() > 1) {
				show.setValue(value.substring(0, value.length() - 1));
				list.add(show);
			}
		}
		if (!"".equals(dto.getSource()) && dto.getSource() != null && dto.getSourceMatch() != null ) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("客户来源");
			show.setMatch(dto.getSourceMatch() == 0 ? "等于" : "不等于");
			String value = "";
			String[] sources = dto.getSource().split(",");
			for(String source : sources) {
				if ("1".equals(source)) {value += "自有导入";value += ",";}
				if ("2".equals(source)) {value += "分配交接";value += ",";}
				if ("3".equals(source)) {value += "公海取回";value += ",";}
				if ("4".equals(source)) {value += "AI初筛";value += ",";}
				if ("5".equals(source)) {value += "在线表单";value += ",";}
			}
			if (value.length() > 1) {
				show.setValue(value.substring(0, value.length() - 1));
				list.add(show);
			}
		}
		if (!"".equals(dto.getOpreateType()) && dto.getOpreateType() != null && dto.getOpreateTypeMatch() != null ) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("放弃类型");
			show.setMatch(dto.getOpreateTypeMatch() == 0 ? "等于" : "不等于");
			String value = "";
			String[] opreateTypes = dto.getOpreateType().split(",");
			for(String type : opreateTypes) {
				if ("11".equals(type)) {value += "客户-到期未签约";value += ",";}
				if ("16".equals(type)) {value += "客户-到期未跟进";value += ",";}
				if ("12".equals(type)) {value += "客户-主动放弃";value += ",";}
				if ("21".equals(type)) {value += "签约客户-流失";value += ",";}
				if ("23".equals(type)) {value += "签约-到期未续签";value += ",";}
				if ("4".equals(type)) {value += "资源-沟通失败";value += ",";}
				if ("5".equals(type)) {value += "资源-信息错误";value += ",";}
				if ("24".equals(type)) {value += "资源-到期未联系";value += ",";}
			}
			if (value.length() > 1) {
				show.setValue(value.substring(0, value.length() - 1));
				list.add(show);
			}
		}
		if(!"".equals(dto.getDefined1()) && dto.getDefined1() != null && dto.getDefined1Match() != null ) {
			CustFieldSet field = defineds3.get("defined1");
			if (field != null ) {
				ShowReportDatailDto show = new ShowReportDatailDto();
				List<OptionBean> optionList = field.getOptionList();
				show.setName(field.getFieldName());
				show.setMatch(dto.getDefined1Match() == 0 ? "等于" : "不等于");
				String value = "";
				String[] defined1 = dto.getDefined1().split(",");
				for(String defined : defined1) {
					for(OptionBean option : optionList) {
						if (defined.equals(option.getOptionlistId())) {
							value += option.getOptionName();
							value += ",";
						}
					}
				}
				if (value.length() > 1) {
					show.setValue(value.substring(0, value.length() - 1));
					list.add(show);
				}
			}
		}
		if(!"".equals(dto.getDefined2()) && dto.getDefined2() != null && dto.getDefined2Match() != null ) {
			CustFieldSet field = defineds3.get("defined2");
			if (field != null ) {
				ShowReportDatailDto show = new ShowReportDatailDto();
				List<OptionBean> optionList = field.getOptionList();
				show.setName(field.getFieldName());
				show.setMatch(dto.getDefined2Match() == 0 ? "等于" : "不等于");
				String value = "";
				String[] defined2 = dto.getDefined2().split(",");
				for(String defined : defined2) {
					for(OptionBean option : optionList) {
						if (defined.equals(option.getOptionlistId())) {
							value += option.getOptionName();
							value += ",";
						}
					}
				}
				if (value.length() > 1) {
					show.setValue(value.substring(0, value.length() - 1));
					list.add(show);
				}
			}
		}
		if(!"".equals(dto.getDefined3()) && dto.getDefined3() != null && dto.getDefined3Match() != null ) {
			CustFieldSet field = defineds3.get("defined3");
			if (field != null ) {
				ShowReportDatailDto show = new ShowReportDatailDto();
				List<OptionBean> optionList = field.getOptionList();
				show.setName(field.getFieldName());
				show.setMatch(dto.getDefined3Match() == 0 ? "等于" : "不等于");
				String value = "";
				String[] defined3 = dto.getDefined3().split(",");
				for(String defined : defined3) {
					for(OptionBean option : optionList) {
						if (defined.equals(option.getOptionlistId())) {
							value += option.getOptionName();
							value += ",";
						}
					}
				}
				if (value.length() > 1) {
					show.setValue(value.substring(0, value.length() - 1));
					list.add(show);
				}
			}
		}
		if(!"".equals(dto.getDefined4()) && dto.getDefined4() != null && dto.getDefined4Match() != null ) {
			CustFieldSet field = defineds3.get("defined4");
			if (field != null ) {
				ShowReportDatailDto show = new ShowReportDatailDto();
				List<OptionBean> optionList = field.getOptionList();
				show.setName(field.getFieldName());
				show.setMatch(dto.getDefined4Match() == 0 ? "等于" : "不等于");
				String value = "";
				String[] defined4 = dto.getDefined4().split(",");
				for(String defined : defined4) {
					for(OptionBean option : optionList) {
						if (defined.equals(option.getOptionlistId())) {
							value += option.getOptionName();
							value += ",";
						}
					}
				}
				if (value.length() > 1) {
					show.setValue(value.substring(0, value.length() - 1));
					list.add(show);
				}
			}
		}
		if(!"".equals(dto.getDefined5()) && dto.getDefined5() != null && dto.getDefined5Match() != null ) {
			CustFieldSet field = defineds3.get("defined5");
			if (field != null ) {
				ShowReportDatailDto show = new ShowReportDatailDto();
				List<OptionBean> optionList = field.getOptionList();
				show.setName(field.getFieldName());
				show.setMatch(dto.getDefined5Match() == 0 ? "等于" : "不等于");
				String value = "";
				String[] defined5 = dto.getDefined5().split(",");
				for(String defined : defined5) {
					for(OptionBean option : optionList) {
						if (defined.equals(option.getOptionlistId())) {
							value += option.getOptionName();
							value += ",";
						}
					}
				}
				if (value.length() > 1) {
					show.setValue(value.substring(0, value.length() - 1));
					list.add(show);
				}
			}
		}
		if(!"".equals(dto.getDefined6()) && dto.getDefined6() != null && dto.getDefined6Match() != null ) {
			CustFieldSet field = defineds3.get("defined6");
			if (field != null ) {
				ShowReportDatailDto show = new ShowReportDatailDto();
				List<OptionBean> optionList = field.getOptionList();
				show.setName(field.getFieldName());
				show.setMatch(dto.getDefined6Match() == 0 ? "等于" : "不等于");
				String value = "";
				String[] defined6 = dto.getDefined6().split(",");
				for(String defined : defined6) {
					for(OptionBean option : optionList) {
						if (defined.equals(option.getOptionlistId())) {
							value += option.getOptionName();
							value += ",";
						}
					}
				}
				if (value.length() > 1) {
					show.setValue(value.substring(0, value.length() - 1));
					list.add(show);
				}
			}
		}
		if(!"".equals(dto.getDefined7()) && dto.getDefined7() != null && dto.getDefined7Match() != null ) {
			CustFieldSet field = defineds3.get("defined7");
			if (field != null ) {
				ShowReportDatailDto show = new ShowReportDatailDto();
				List<OptionBean> optionList = field.getOptionList();
				show.setName(field.getFieldName());
				show.setMatch(dto.getDefined7Match() == 0 ? "等于" : "不等于");
				String value = "";
				String[] defined7 = dto.getDefined7().split(",");
				for(String defined : defined7) {
					for(OptionBean option : optionList) {
						if (defined.equals(option.getOptionlistId())) {
							value += option.getOptionName();
							value += ",";
						}
					}
				}
				if (value.length() > 1) {
					show.setValue(value.substring(0, value.length() - 1));
					list.add(show);
				}
			}
		}
		if(!"".equals(dto.getDefined8()) && dto.getDefined8() != null && dto.getDefined8Match() != null ) {
			CustFieldSet field = defineds3.get("defined8");
			if (field != null ) {
				ShowReportDatailDto show = new ShowReportDatailDto();
				List<OptionBean> optionList = field.getOptionList();
				show.setName(field.getFieldName());
				show.setMatch(dto.getDefined8Match() == 0 ? "等于" : "不等于");
				String value = "";
				String[] defined8 = dto.getDefined8().split(",");
				for(String defined : defined8) {
					for(OptionBean option : optionList) {
						if (defined.equals(option.getOptionlistId())) {
							value += option.getOptionName();
							value += ",";
						}
					}
				}
				if (value.length() > 1) {
					show.setValue(value.substring(0, value.length() - 1));
					list.add(show);
				}
			}
		}
		if(!"".equals(dto.getDefined9()) && dto.getDefined9() != null && dto.getDefined9Match() != null ) {
			CustFieldSet field = defineds3.get("defined9");
			if (field != null ) {
				ShowReportDatailDto show = new ShowReportDatailDto();
				List<OptionBean> optionList = field.getOptionList();
				show.setName(field.getFieldName());
				show.setMatch(dto.getDefined9Match() == 0 ? "等于" : "不等于");
				String value = "";
				String[] defined9 = dto.getDefined9().split(",");
				for(String defined : defined9) {
					for(OptionBean option : optionList) {
						if (defined.equals(option.getOptionlistId())) {
							value += option.getOptionName();
							value += ",";
						}
					}
				}
				if (value.length() > 1) {
					show.setValue(value.substring(0, value.length() - 1));
					list.add(show);
				}
			}
		}
		if(!"".equals(dto.getDefined10()) && dto.getDefined10() != null && dto.getDefined10Match() != null ) {
			CustFieldSet field = defineds3.get("defined10");
			if (field != null ) {
				ShowReportDatailDto show = new ShowReportDatailDto();
				List<OptionBean> optionList = field.getOptionList();
				show.setName(field.getFieldName());
				show.setMatch(dto.getDefined10Match() == 0 ? "等于" : "不等于");
				String value = "";
				String[] defined10 = dto.getDefined10().split(",");
				for(String defined : defined10) {
					for(OptionBean option : optionList) {
						if (defined.equals(option.getOptionlistId())) {
							value += option.getOptionName();
							value += ",";
						}
					}
				}
				if (value.length() > 1) {
					show.setValue(value.substring(0, value.length() - 1));
					list.add(show);
				}
			}
		}
		if(!"".equals(dto.getDefined11()) && dto.getDefined11() != null && dto.getDefined11Match() != null ) {
			CustFieldSet field = defineds3.get("defined11");
			if (field != null ) {
				ShowReportDatailDto show = new ShowReportDatailDto();
				List<OptionBean> optionList = field.getOptionList();
				show.setName(field.getFieldName());
				show.setMatch(dto.getDefined11Match() == 0 ? "等于" : "不等于");
				String value = "";
				String[] defined11 = dto.getDefined11().split(",");
				for(String defined : defined11) {
					for(OptionBean option : optionList) {
						if (defined.equals(option.getOptionlistId())) {
							value += option.getOptionName();
							value += ",";
						}
					}
				}
				if (value.length() > 1) {
					show.setValue(value.substring(0, value.length() - 1));
					list.add(show);
				}
			}
		}
		if(!"".equals(dto.getDefined12()) && dto.getDefined12() != null && dto.getDefined12Match() != null ) {
			CustFieldSet field = defineds3.get("defined12");
			if (field != null ) {
				ShowReportDatailDto show = new ShowReportDatailDto();
				List<OptionBean> optionList = field.getOptionList();
				show.setName(field.getFieldName());
				show.setMatch(dto.getDefined12Match() == 0 ? "等于" : "不等于");
				String value = "";
				String[] defined12 = dto.getDefined12().split(",");
				for(String defined : defined12) {
					for(OptionBean option : optionList) {
						if (defined.equals(option.getOptionlistId())) {
							value += option.getOptionName();
							value += ",";
						}
					}
				}
				if (value.length() > 1) {
					show.setValue(value.substring(0, value.length() - 1));
					list.add(show);
				}
			}
		}
		if(!"".equals(dto.getDefined13()) && dto.getDefined13() != null && dto.getDefined13Match() != null ) {
			CustFieldSet field = defineds3.get("defined13");
			if (field != null ) {
				ShowReportDatailDto show = new ShowReportDatailDto();
				List<OptionBean> optionList = field.getOptionList();
				show.setName(field.getFieldName());
				show.setMatch(dto.getDefined13Match() == 0 ? "等于" : "不等于");
				String value = "";
				String[] defined13 = dto.getDefined13().split(",");
				for(String defined : defined13) {
					for(OptionBean option : optionList) {
						if (defined.equals(option.getOptionlistId())) {
							value += option.getOptionName();
							value += ",";
						}
					}
				}
				if (value.length() > 1) {
					show.setValue(value.substring(0, value.length() - 1));
					list.add(show);
				}
			}
		}
		if(!"".equals(dto.getDefined14()) && dto.getDefined14() != null && dto.getDefined14Match() != null ) {
			CustFieldSet field = defineds3.get("defined14");
			if (field != null ) {
				ShowReportDatailDto show = new ShowReportDatailDto();
				List<OptionBean> optionList = field.getOptionList();
				show.setName(field.getFieldName());
				show.setMatch(dto.getDefined14Match() == 0 ? "等于" : "不等于");
				String value = "";
				String[] defined14 = dto.getDefined14().split(",");
				for(String defined : defined14) {
					for(OptionBean option : optionList) {
						if (defined.equals(option.getOptionlistId())) {
							value += option.getOptionName();
							value += ",";
						}
					}
				}
				if (value.length() > 1) {
					show.setValue(value.substring(0, value.length() - 1));
					list.add(show);
				}
			}
		}
		if(!"".equals(dto.getDefined15()) && dto.getDefined15() != null && dto.getDefined15Match() != null ) {
			CustFieldSet field = defineds3.get("defined15");
			if (field != null ) {
				ShowReportDatailDto show = new ShowReportDatailDto();
				List<OptionBean> optionList = field.getOptionList();
				show.setName(field.getFieldName());
				show.setMatch(dto.getDefined15Match() == 0 ? "等于" : "不等于");
				String value = "";
				String[] defined15 = dto.getDefined15().split(",");
				for(String defined : defined15) {
					for(OptionBean option : optionList) {
						if (defined.equals(option.getOptionlistId())) {
							value += option.getOptionName();
							value += ",";
						}
					}
				}
				if (value.length() > 1) {
					show.setValue(value.substring(0, value.length() - 1));
					list.add(show);
				}
			}
		}
		if (dto.getActionDateStart() != null && dto.getActionDateEnd() != null) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("最近联系时间");
			show.setMatch("介于");
			String value = dto.getActionDateStart() + "~" + dto.getActionDateEnd();
			show.setValue(value);
			list.add(show);
		}
		if (dto.getNextFollowDateStart() != null && dto.getNextFollowDateEnd() != null) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("下次联系时间");
			show.setMatch("介于");
			String value = dto.getNextFollowDateStart() + "~" + dto.getNextFollowDateEnd();
			show.setValue(value);
			list.add(show);
		}
		if (dto.getAmoytocustomerDateStart() != null && dto.getAmoytocustomerDateEnd() != null) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("淘到客户时间");
			show.setMatch("介于");
			String value = dto.getAmoytocustomerDateStart() + "~" + dto.getAmoytocustomerDateEnd();
			show.setValue(value);
			list.add(show);
		}
		if (dto.getInputDateStart() != null && dto.getInputDateEnd() != null) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("创建时间");
			show.setMatch("介于");
			String value = dto.getInputDateStart() + "~" + dto.getInputDateEnd();
			show.setValue(value);
			list.add(show);
		}
		if (dto.getSignDateStart() != null && dto.getSignDateEnd() != null) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("签约时间");
			show.setMatch("介于");
			String value = dto.getSignDateStart() + "~" + dto.getSignDateEnd();
			show.setValue(value);
			list.add(show);
		}
		if (dto.getOwnerStartDateStart() != null && dto.getOwnerStartDateEnd() != null) {
			ShowReportDatailDto show = new ShowReportDatailDto();
			show.setName("分配时间");
			show.setMatch("介于");
			String value = dto.getOwnerStartDateStart() + "~" + dto.getOwnerStartDateEnd();
			show.setValue(value);
			list.add(show);
		}
		request.setAttribute("list", list);
		request.setAttribute("shareName", shareName);
		request.setAttribute("customReportId", customReportId);
		request.setAttribute("customReportName", customReportName);
		request.setAttribute("isDouble", isDouble);
		return "report/customRoport/custReportDetail";
	}
	
	
	
	/**
	 * 双维度自定义报表详情数据
	 */
	@ResponseBody
	@RequestMapping(value = "getDoutbleReportDatailJson")
	public List<CustomReportDoubleShowDto> getDoutbleReportDatailJson(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getUser();
			String orgId = user.getOrgId();
			String customReportId = request.getParameter("customReportId");
			CustomReportBean bean = new CustomReportBean();
			bean.setOrgId(orgId);
			bean.setCustomReportId(customReportId);
			CustomReportBean crb = customReportService.getByCondtion(bean);
			String data = crb.getData();
			ResCustReportDto rcrd = JSON.parseObject(data, ResCustReportDto.class);
			
			rcrd.setOrgId(orgId);
			reSetbean(rcrd);
			List<CustomReportDoubleShowDto> list = new ArrayList<>();
			list = resCustInfoService.makeCustomReport2(rcrd);
			return list;
		} catch (Exception e) {
			logger.error("双维度自定义报表异常！", e);
			e.printStackTrace();
		}
		return null;
	}
	
	public void reSetbean(ResCustReportDto rcrd) {
		//客户类型
		if (!"".equals(rcrd.getCustTypeId()) && rcrd.getCustTypeId() != null) {
			String[] custTypeIds = rcrd.getCustTypeId().split(",");
			rcrd.setCustTypeIds(Arrays.asList(custTypeIds));
		}
		//客户状态
		if (!"".equals(rcrd.getqStatus()) && rcrd.getqStatus() != null && rcrd.getqStatusMatch() != null) {
			String[] qStatuss = rcrd.getqStatus().split(",");
			Map<Integer, String> qStatusMap = new HashMap<>(); 
			for (String string : qStatuss) {
				qStatusMap.put(Integer.parseInt(string), string);
			}
			rcrd.setqStatusMap(qStatusMap);
			List<String> types = new ArrayList<>();
			List<String> statuss = new ArrayList<>();
			List<String> qStatusss = new ArrayList<>();
			if (rcrd.getqStatusMatch() == 1) {
				if(!Arrays.asList(qStatuss).contains("1")) qStatusss.add("1");
				if(!Arrays.asList(qStatuss).contains("2")) qStatusss.add("2");
				if(!Arrays.asList(qStatuss).contains("3")) qStatusss.add("3");
				if(!Arrays.asList(qStatuss).contains("4")) qStatusss.add("4");
				if(!Arrays.asList(qStatuss).contains("5")) qStatusss.add("5");
				if(!Arrays.asList(qStatuss).contains("6")) qStatusss.add("6");
				if(!Arrays.asList(qStatuss).contains("7")) qStatusss.add("7");
				qStatuss = qStatusss.toArray(new String[]{});
			}
			if (qStatuss.length > 0) {
				for(String qStatus : qStatuss) {
					if ("1".equals(qStatus)) {
						types.add("1");
						statuss.add("1");
					}else if("2".equals(qStatus)) {
						types.add("1");
						statuss.add("2");
						statuss.add("3");
					}else if("3".equals(qStatus)) {
						types.add("2");
						statuss.add("2");
						statuss.add("3");
					}else if("4".equals(qStatus)) {
						types.add("2");
						statuss.add("6");
					}else if("5".equals(qStatus)) {
						types.add("2");
						statuss.add("8");
					}else if("6".equals(qStatus)) {
						types.add("2");
						statuss.add("7");
					}else if("7".equals(qStatus)) {
						statuss.add("4");
					}
				}
			}
			
			rcrd.setTypes(types);
			rcrd.setStatuss(statuss);
		}
		//销售进程
		if (!"".equals(rcrd.getOptionlistId()) && rcrd.getOptionlistId() != null) {
			String[] optionlistIds = rcrd.getOptionlistId().split(",");
			rcrd.setOptionlistIds(Arrays.asList(optionlistIds));
		}
		//所有者
		if (!"".equals(rcrd.getAccs()) && rcrd.getAccs() != null) {
			String[] ownerAccs = rcrd.getAccs().split(",");
			rcrd.setOwnerAccs(Arrays.asList(ownerAccs));
		}
		//资源分组
		if (!"".equals(rcrd.getResGroupId()) && rcrd.getResGroupId() != null) {
			String[] resGroupIds = rcrd.getResGroupId().split(",");
			rcrd.setResGroupIds(Arrays.asList(resGroupIds));
		}
		//所属行业
		if (!"".equals(rcrd.getCompanyTrade()) && rcrd.getCompanyTrade() != null) {
			String[] companyTrades = rcrd.getCompanyTrade().split(",");
			rcrd.setCompanyTrades(Arrays.asList(companyTrades));
		}
		//资源录入部门
		if (!"".equals(rcrd.getImportDeptId()) && rcrd.getImportDeptId() != null) {
			String[] importDeptIds = rcrd.getImportDeptId().split(",");
			rcrd.setImportDeptIds(Arrays.asList(importDeptIds));
		}
		//客户来源
		if (!"".equals(rcrd.getSource()) && rcrd.getSource() != null) {
			String[] sources = rcrd.getSource().split(",");
			rcrd.setSources(Arrays.asList(sources));
		}
		//放弃类型
		if (!"".equals(rcrd.getOpreateType()) && rcrd.getOpreateType() != null) {
			String[] opreateTypes = rcrd.getOpreateType().split(",");
			rcrd.setOpreateTypes(Arrays.asList(opreateTypes));
		}
		//defined
		if (!"".equals(rcrd.getDefined1()) && rcrd.getDefined1() != null) {
			String[] defined1s = rcrd.getDefined1().split(",");
			rcrd.setDefined1s(Arrays.asList(defined1s));
		}
		if (!"".equals(rcrd.getDefined2()) && rcrd.getDefined2() != null) {
			String[] defined2s = rcrd.getDefined2().split(",");
			rcrd.setDefined2s(Arrays.asList(defined2s));
		}
		if (!"".equals(rcrd.getDefined3()) && rcrd.getDefined3() != null) {
			String[] defined3s = rcrd.getDefined3().split(",");
			rcrd.setDefined3s(Arrays.asList(defined3s));
		}
		if (!"".equals(rcrd.getDefined4()) && rcrd.getDefined4() != null) {
			String[] defined4s = rcrd.getDefined4().split(",");
			rcrd.setDefined4s(Arrays.asList(defined4s));
		}
		if (!"".equals(rcrd.getDefined5()) && rcrd.getDefined5() != null) {
			String[] defined5s = rcrd.getDefined5().split(",");
			rcrd.setDefined5s(Arrays.asList(defined5s));
		}
		if (!"".equals(rcrd.getDefined6()) && rcrd.getDefined6() != null) {
			String[] defined6s = rcrd.getDefined6().split(",");
			rcrd.setDefined6s(Arrays.asList(defined6s));
		}
		if (!"".equals(rcrd.getDefined7()) && rcrd.getDefined7() != null) {
			String[] defined7s = rcrd.getDefined7().split(",");
			rcrd.setDefined7s(Arrays.asList(defined7s));
		}
		if (!"".equals(rcrd.getDefined8()) && rcrd.getDefined8() != null) {
			String[] defined8s = rcrd.getDefined8().split(",");
			rcrd.setDefined8s(Arrays.asList(defined8s));
		}
		if (!"".equals(rcrd.getDefined9()) && rcrd.getDefined9() != null) {
			String[] defined9s = rcrd.getDefined9().split(",");
			rcrd.setDefined9s(Arrays.asList(defined9s));
		}
		if (!"".equals(rcrd.getDefined10()) && rcrd.getDefined10() != null) {
			String[] defined10s = rcrd.getDefined10().split(",");
			rcrd.setDefined10s(Arrays.asList(defined10s));
		}
		if (!"".equals(rcrd.getDefined11()) && rcrd.getDefined11() != null) {
			String[] defined11s = rcrd.getDefined11().split(",");
			rcrd.setDefined11s(Arrays.asList(defined11s));
		}
		if (!"".equals(rcrd.getDefined12()) && rcrd.getDefined12() != null) {
			String[] defined12s = rcrd.getDefined12().split(",");
			rcrd.setDefined12s(Arrays.asList(defined12s));
		}
		if (!"".equals(rcrd.getDefined13()) && rcrd.getDefined13() != null) {
			String[] defined13s = rcrd.getDefined13().split(",");
			rcrd.setDefined13s(Arrays.asList(defined13s));
		}
		if (!"".equals(rcrd.getDefined14()) && rcrd.getDefined14() != null) {
			String[] defined14s = rcrd.getDefined14().split(",");
			rcrd.setDefined14s(Arrays.asList(defined14s));
		}
		if (!"".equals(rcrd.getDefined15()) && rcrd.getDefined15() != null) {
			String[] defined15s = rcrd.getDefined15().split(",");
			rcrd.setDefined15s(Arrays.asList(defined15s));
		}
	}
	/**
	 * 单维度自定义报表详情数据
	 */
	@ResponseBody
	@RequestMapping(value = "getSingleReportDatailJson")
	public List<CustomReportSingleDto> getSingleReportDatailJson(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getUser();
			String orgId = user.getOrgId();
			String customReportId = request.getParameter("customReportId");
			CustomReportBean bean = new CustomReportBean();
			bean.setOrgId(orgId);
			bean.setCustomReportId(customReportId);
			CustomReportBean crb = customReportService.getByCondtion(bean);
			String data = crb.getData();
			ResCustReportDto rcrd = JSON.parseObject(data, ResCustReportDto.class);
			rcrd.setOrgId(orgId);
			reSetbean(rcrd);
			List<CustomReportSingleDto> list = new ArrayList<>();
			list = resCustInfoService.makeCustomReport1(rcrd);
			return list;
		} catch (Exception e) {
			logger.error("单维度自定义报表异常！", e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 我的报表页面
	 */
	@RequestMapping(value = "toMyCustomReportList")
	public String toMyCustomReportList(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		return "report/customRoport/myReport";
	}
	/**
	 * 我的报表json
	 */
	@ResponseBody
	@RequestMapping(value = "myCustomReportListJson")
	public List<CustomReportBean> myCustomReportListJson(HttpServletRequest request,CustomReportBean bean) {
		try {
			ShiroUser user = ShiroUtil.getUser();
			String orgId = user.getOrgId();
			bean.setOrgId(orgId);
			bean.setInputAcc(user.getAccount());
			bean.setIsDel(0);
			bean.setOrderKey("input_date desc");
			List<CustomReportBean> list = new ArrayList<>();
			list = customReportService.findByCondtion(bean);
			return list;
		} catch (Exception e) {
			logger.error("我的报表json异常！", e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 分享给我的报表页面
	 */
	@RequestMapping(value = "toShareToMeCustomReportList")
	public String toShareToMeCustomReportList(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		return "report/customRoport/shareReport";
	}
	
	/**
	 * 分享给我的报表json
	 */
	@ResponseBody
	@RequestMapping(value = "shareToMeCustomReportListJson")
	public List<CustomReportBean> shareToMeCustomReportListJson(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getUser();
			String orgId = user.getOrgId();
			/*CustomReportShareBean bean = new CustomReportShareBean();
			bean.setShareAcc(user.getAccount());
			bean.setIsDel(0);
			bean.setOrgId(orgId);
			bean.setOrderKey("input_date desc");
			List<String> ids = customReportShareService.findReportIdsByShareAcc(bean);
			List<CustomReportBean> list = new ArrayList<>();
			if (ids.size() > 0) {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("orgId", orgId);
				map.put("ids",ids);
				list = customReportService.findByIds(map);
			}*/
			List<CustomReportBean> list = new ArrayList<>();
			CustomReportShareBean bean = new CustomReportShareBean();
			bean.setShareAcc(user.getAccount());
			bean.setIsDel(0);
			bean.setOrgId(orgId);
			list = customReportService.findShareToMeCustomReportListPage(bean);
			return list;
		} catch (Exception e) {
			logger.error("分享给我的报表json异常！", e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
