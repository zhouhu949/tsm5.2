package com.qftx.tsm.follow.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.enums.FollowCustEnum;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.*;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.callrecord.dto.CustCallQueryDto;
import com.qftx.tsm.callrecord.dto.FollowCallQueryDto;
import com.qftx.tsm.callrecord.dto.TsmRecordCallBean;
import com.qftx.tsm.callrecord.util.CallRecordGetUtil;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResCustInfoDetailBean;
import com.qftx.tsm.cust.dto.ResCustActionDto;
import com.qftx.tsm.cust.service.ResCustEventService;
import com.qftx.tsm.cust.service.ResCustInfoDetailService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.follow.dto.CustFollowCallDto;
import com.qftx.tsm.follow.dto.CustFollowDto;
import com.qftx.tsm.follow.dto.RecordCallDto;
import com.qftx.tsm.follow.service.CustFollowService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.bean.OptionGroupBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.bean.TsmCustReview;
import com.qftx.tsm.sys.dto.HighSearchDto;
import com.qftx.tsm.sys.dto.SearchListShowCodeDto;
import com.qftx.tsm.sys.enums.SysEnum;
import com.qftx.tsm.sys.service.TsmCustReviewService;
import com.qftx.tsm.tao.dto.OptionDto;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.beans.PropertyEditor;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 * 客户跟进 相关模板 列表
 * @author: zwj
 * @since: 2015-12-4  下午2:14:09
 * @history: 4.x
 */
@Controller  
@RequestMapping(value = "/cust/custFollow/List")
public class CustFollowListControl {
	private static Logger logger = Logger.getLogger(CustFollowListControl.class);
	@Autowired
	private CustFollowService custFollowService;

	@Autowired private CachedService cachedService;
	@Autowired private ResCustInfoService resCustInfoService;
	@Autowired private ResCustInfoDetailService resCustInfoDetailService;
	@Autowired private TsmCustReviewService tsmCustReviewService;
	@Autowired private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Autowired
	private ResCustEventService resCustEventService;
	
	
	/**  
	 * 客户销售进程变化
	 */
	@RequestMapping("/custSaleProcess")
	public String custSaleProcess(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("shiroUser", user);
			CustFollowDto custFollowDto = new CustFollowDto();
			custFollowDto.setRoleType(user.getIssys()); // 角色类别：0--销售，1--管理者
			// 管理者查询
			if(custFollowDto.getRoleType() != null && custFollowDto.getRoleType()==1){
				// 所有者查询方式 1-全部 2-只看自己 3-选中查询
				custFollowDto.setOsType(StringUtils.isBlank(custFollowDto.getOsType()) ? "1" : custFollowDto.getOsType());			
			}			  
			//从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,user.getOrgId());
			request.setAttribute("options",options);			
			request.setAttribute("item", custFollowDto);
			if(user.getIsState() == 0){ // 个人版
				return "/follow/saleProcess_person";
			}	
		}catch (Exception e) {
			logger.error(" 客户销售进程变化异常！",e);
		}
		return "/follow/saleProcess";	
	}
	
	/**  
	 * 客户销售进程变化JSON
	 */
	@RequestMapping("/custSaleProcessJson")
	@ResponseBody
	public Map<String,Object> custSaleProcessJson(HttpServletRequest request,CustFollowDto custFollowDto){
		Map<String,Object>map = new HashMap<String, Object>();
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String dDateType = request.getParameter("dDateType");
			String orgId=user.getOrgId();
			custFollowDto.setRoleType(user.getIssys()); // 角色类别：0--销售，1--管理者
			// 管理者查询
			if(custFollowDto.getRoleType() != null && custFollowDto.getRoleType()==1){
				// 所有者查询方式 1-全部 2-只看自己 3-选中查询
				custFollowDto.setOsType(StringUtils.isBlank(custFollowDto.getOsType()) ? "1" : custFollowDto.getOsType());
				if (StringUtils.isNotEmpty(custFollowDto.getAccs()) && "3".equals(custFollowDto.getOsType())) {
					String[] ownerAccs = custFollowDto.getAccs().split(",");
					List<String> owaList = Arrays.asList(ownerAccs);
					custFollowDto.setOwnerAccs(owaList);
				}else if("1".equals(custFollowDto.getOsType())){
						List<String>list = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(),user.getAccount());
						if(list!=null && list.size()>0){
							StringBuffer sb = new StringBuffer();
							for(String str: list){
								sb.append(str);
								sb.append(",");
							}
							if (!list.contains(user.getAccount())) {
								list.add(user.getAccount());
							}
							if(sb.length()>0){
								sb = sb.deleteCharAt(sb.length() - 1);
							}
							custFollowDto.setAccs(sb.toString());
							custFollowDto.setOwnerAccs(list);
						}else {
							list.add(user.getAccount());
							custFollowDto.setOwnerAccs(list);
						}					
					}	
			}
			String loginAcc=user.getAccount();
			custFollowDto.setOwnerAcc(loginAcc);
		
			// 最近联系时间
			if(StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)){
				custFollowDto.setLastStartActionDate(getStartDateStr(Integer.parseInt(dDateType)));
				custFollowDto.setLastEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			
		    custFollowDto.setOrgId(orgId);
		    custFollowDto.setState(user.getIsState());
		    custFollowDto.setOrderKey("LAST_ACTION_DATE desc ");
		    Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			List<CustFollowDto> tsmCustFollowDtoList = custFollowService.getTeamSaleAndFollowListPage(custFollowDto);
			/** 拼装组成查询结果 */
			if(tsmCustFollowDtoList != null && tsmCustFollowDtoList.size() >0){
				List<String>recustIds = new ArrayList<String>();
				// 用户分组 缓存
				List<OrgGroup> deptList = cachedService.getOrgGroup(orgId);
				List<OrgGroupUser> memberGroupList = cachedService.getOrgGroupMember(orgId);
				Map<String, String> deptMap = new HashMap<String, String>();
				Map<String, String> memberGroupMap = new HashMap<String, String>();
				if (deptList != null && deptList.size() > 0) {
					for (OrgGroup dept : deptList) {
						deptMap.put(dept.getGroupId(), dept.getGroupName());
					}
				}
				if (memberGroupList != null && memberGroupList.size() > 0) {
					for (OrgGroupUser member : memberGroupList) {
						memberGroupMap.put(member.getMemberAcc(), member.getGroupId());
					}
				}
				for(CustFollowDto fdto : tsmCustFollowDtoList){
					fdto.setGroupName(deptMap.get(memberGroupMap.get(fdto.getFollowAcc())));
					recustIds.add(fdto.getCustId());					
				}
				if(recustIds != null && recustIds.size() >0){
					custFollowDto.setResCustIds(recustIds);
					List<CustFollowDto> tsmCusts = custFollowService.getResCustsByCustIds(custFollowDto);
					Map<String,CustFollowDto> tsmCustMap = new HashMap<String, CustFollowDto>();
				
					if(tsmCusts != null && tsmCusts.size()>0){
						Map<String, String> optionMap = cachedService.getOrgSaleProcess(user.getOrgId());	
						for(CustFollowDto cfd:tsmCusts){
							tsmCustMap.put(cfd.getResCustId(), cfd);
						}
						for(CustFollowDto fdto : tsmCustFollowDtoList){
							fdto.setShowLastActionDate(fdto.getLastActionDate() !=null?DateUtil.formatDate(fdto.getLastActionDate(), DateUtil.Data_ALL): "");
							if (optionMap != null) {
								fdto.setOptionName(optionMap.get(fdto.getSaleProcessId()));
								fdto.setLastSaleProcess(optionMap.get(fdto.getLastSaleProcessId()));
							}
							if(tsmCustMap.get(fdto.getCustId()) != null){
								CustFollowDto cfd1 = tsmCustMap.get(fdto.getCustId());
								fdto.setResCustId(fdto.getCustId());
								fdto.setCreateTime(cfd1.getCreateTime());
								fdto.setOwnerName(nameMap.get(cfd1.getOwnerName()) == null ? cfd1.getOwnerName() : nameMap.get(cfd1.getOwnerName()));
								fdto.setCustName(cfd1.getCustName());
								fdto.setCustMobilephone(cfd1.getCustMobilephone());
								fdto.setIsMajor(cfd1.getIsMajor());
								fdto.setCompany(cfd1.getCompany());
							}		
						}
					}
				}
				request.setAttribute("tsmCustFollowDtoList", tsmCustFollowDtoList);
			}
			map.put("list", tsmCustFollowDtoList);
			map.put("item", custFollowDto);
			map.put("status", "success");				
		}catch (Exception e) {
			map.put("status", "error");	
			logger.error("客户销售进程变化JSON异常！", e);
		}
		return map;	
	}
	
	/***
	 * 跟进警报列表
	 */
	@RequestMapping("/followUpTheAlertList")
	public String followUpTheAlertList(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("shiroUser", user);
			//从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,user.getOrgId());
			request.setAttribute("options",options);			
			List<DataDictionaryBean> contractOns = cachedService.getDirList(AppConstant.DATA_40039, user.getOrgId());
			boolean addContract = true;
			if(contractOns!=null && contractOns.size() > 0){
				if ("1".equals(contractOns.get(0).getDictionaryValue())) {
					addContract = false;
				}
			}
			request.setAttribute("addContract", addContract);
			//从缓存读取客户分类列表
			List<OptionBean> custTypeList = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO,user.getOrgId());
			request.setAttribute("custTypeList",custTypeList);
			setIsReplaceWord(request,user); // 设置是否开启用*替换电话号码中间四位	
			getSignSetting(request);
			if(user.getIsState() == 0){ // 个人版
				return "/follow/followAlarm_person";
			}	
		} catch (Exception e) {
			logger.error("跟进警报列表 异常！", e);
		}
		return "/follow/followAlarm";
	}
	
	/***
	 * 跟进警报 JSON
	 */
	@RequestMapping("/followUpTheAlertListJson")
	@ResponseBody
	public Map<String,Object> followUpTheAlertListJson(HttpServletRequest request,CustFollowDto custFollowDto){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String dDateType = request.getParameter("dDateType");
			String nDateType = request.getParameter("nDateType");
			custFollowDto.setOwnerAcc(user.getAccount());
			custFollowDto.setOrgId(user.getOrgId());
			custFollowDto.setState(user.getIsState());  // 0：个人客户,1:企业客户
			
			String allLabels = request.getParameter("allLabels");
			// 组装选中标签			
			if(StringUtils.isNotBlank(allLabels)){
				custFollowDto.setLabels(allLabels.split(","));
				request.setAttribute("labelNamesList", getLabelNameList(Arrays.asList(allLabels.split(",")), user.getOrgId()));
				request.setAttribute("allLabels", allLabels);
			}
			//  最近联系时间
			if(StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)){
				custFollowDto.setLastStartActionDate(getStartDateStr(Integer.parseInt(dDateType)));
				custFollowDto.setLastEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			//  下次联系时间
			if(StringUtils.isNotBlank(nDateType) && !"0".equals(nDateType) && !"5".equals(nDateType)){
				custFollowDto.setNextStartActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
				custFollowDto.setNextEndActionDate(getEndDateStr(Integer.parseInt(nDateType)));
			}
			custFollowDto.setDataDictionVal(Integer.parseInt(getFollowExpire()));
			custFollowDto.setDataDictionVal1(Integer.parseInt(getFollowExpire1()));
			/**
			 * 【跟进警报】，按“剩余天数”排，天数越小排在最前；其次接下来的排序优先级根据客户姓名。
			 */
			custFollowDto.setOrderKey(" DAYS_AFTER_DATE_COUNT ASC,CONVERT(TRCI.NAME USING GBK)");	
			List<String> multiSearchList = new ArrayList<String>();
			multiSearchList.add(AppConstant.SEARCH_LABEL);
			List<CustFollowDto> tsmCustFollowDtoList = custFollowService.getTsmCustFollowAlarmPageList(custFollowDto,multiSearchList);
			if(tsmCustFollowDtoList != null && tsmCustFollowDtoList.size() >0){
				Map<String, String> optionMap = cachedService.getOrgSaleProcess(user.getOrgId());			
				Map<String, String> custTypeMap = cachedService.getOrgCustTypes(user.getOrgId());
				// 转换页面显示值
				for(CustFollowDto cfd : tsmCustFollowDtoList){
					cfd.setShowLastActionDate(cfd.getLastActionDate() !=null?DateUtil.formatDate(cfd.getLastActionDate(), DateUtil.Data_ALL): "");
					cfd.setShowNextActionDate(cfd.getNextActionDate() !=null?DateUtil.formatDate(cfd.getNextActionDate(), DateUtil.Data_ALL): "");
					if (optionMap != null) {
						cfd.setOptionName(optionMap.get(cfd.getOptionlistId()));
					}
					if (custTypeMap != null) {
						cfd.setCustTypeName(custTypeMap.get(cfd.getCustTypeId()));
					}
				}
			}
			
			map.put("list", tsmCustFollowDtoList);
			map.put("item", custFollowDto);
			map.put("currentPage", custFollowDto.getPage().getCurrentPage());
			map.put("showCount", custFollowDto.getPage().getShowCount());
			map.put("status", "success");					
		} catch (Exception e) {
			logger.error("跟进警报 JSON 异常！", e);
			map.put("status", "error");	
		}
		return  map;
	}
	
	/** 异步获取跟进警报个数 */
	@RequestMapping("/getFollowAlarmCount")
	@ResponseBody
	public Integer getFollowAlarmCount(HttpServletRequest request){
		Integer followCount = 0;
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			//计算跟进数
			CustFollowDto tsmCustFollowDto_ = new CustFollowDto();
			tsmCustFollowDto_.setOwnerAcc(user.getAccount());
			tsmCustFollowDto_.setOrgId(user.getOrgId());
			tsmCustFollowDto_.setState(user.getIsState());
			followCount = custFollowService.getTsmCustFollowAlarmCount(tsmCustFollowDto_);
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return followCount;
	}
	
	/**
	 * 客户跟进列表
	 */
	@RequestMapping("/custFollowListContent")
	public String custFollowList(HttpServletRequest request) {
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("shiroUser", user);
			CustFollowDto custFollowDto = new CustFollowDto();
			custFollowDto.setRoleType(user.getIssys()); // 角色类别：0--销售，1--管理者		
			// 默认选中全部
			custFollowDto.setFollowCustCation(FollowCustEnum.CUST_ALL.getState());
			// 管理者查询
			if(custFollowDto.getRoleType() != null && custFollowDto.getRoleType()==1){
				// 所有者查询方式 1-全部 2-只看自己 3-选中查询
				custFollowDto.setOsType(StringUtils.isBlank(custFollowDto.getOsType()) ? "1" : custFollowDto.getOsType());
				custFollowDto.setComosType(StringUtils.isBlank(custFollowDto.getComosType()) ? "1" : custFollowDto.getComosType());
				request.setAttribute("item", custFollowDto);
			}	
			List<DataDictionaryBean> contractOns = cachedService.getDirList(AppConstant.DATA_40039, user.getOrgId());
			boolean addContract = true;
			if(contractOns!=null && contractOns.size() > 0){
				if ("1".equals(contractOns.get(0).getDictionaryValue())) {
					addContract = false;
				}
			}
			request.setAttribute("addContract", addContract);
			//从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,user.getOrgId());
			request.setAttribute("options",options);			
			//从缓存读取客户分类列表
			List<OptionBean> custTypeList = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO,user.getOrgId());
			request.setAttribute("custTypeList",custTypeList);
			// 资源分组
			List<Map<String, Object>> groups = cachedService.getResGroupList1(user.getOrgId());
			request.setAttribute("groupList",groups);
			// 所属行业
			List<OptionBean> companyTrades = cachedService.getOptionList("companyTrade",user.getOrgId());
			request.setAttribute("companyTrades",companyTrades);
			List<CustSearchSet> tlist = getIsTextQueryList(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_1.getState());
			request.setAttribute("tList", tlist); // 文本类型查询字段列表
			Map<String,String>map = getIsDefinedNameList(user.getOrgId(),user.getIsState());
			request.setAttribute("definedNameMap", map);
			// 用于筛选项排序
			Map<String,Integer> mutilSearchCodeSortMap = getUnTestSearchSort(SysEnum.SEARCH_SET_MODULE_1.getState(),user.getOrgId(),user.getIssys());
			request.setAttribute("mutilSearchCodeSortMap",mutilSearchCodeSortMap);
			List<HighSearchDto> dtos = cachedService.getHighSearch(SysEnum.SEARCH_SET_MODULE_1.getState(), user.getOrgId(), user.getIssys());
			List<HighSearchDto> definedDtos = new ArrayList<HighSearchDto>();
			for(HighSearchDto dto : dtos){
				if(dto.getIsFiledSet() ==1 && dto.getIsQuery() == 1 ){
					definedDtos.add(dto);
				}
			}			
			request.setAttribute("definedSearchCodes",definedDtos); // 自定义非文本项查询集合
			// 需隐藏列的序号
			List<Integer> sorts = getHideSortListCode(SysEnum.SEARCH_SET_MODULE_1.getState(),user.getOrgId(),user.getIsState().toString());
			request.setAttribute("sorts", sorts);
			getSignSetting(request);
			setAdminSignAuth(request);
			setIsReplaceWord(request);
//			if(user.getIsState() == 0){ // 个人版
//				return "/follow/followList_person";
//			}			
			Map<String,Object> maps=getLableList(user.getOrgId());
			Integer isSelect=(Integer) maps.get("isSelect");
			request.setAttribute("isSelect", isSelect);
			String optionlistId= request.getParameter("saleProcessId");
			request.setAttribute("optionlistId", optionlistId);
			String status = request.getParameter("status");
			request.setAttribute("status", status);
		} catch (Exception e) {
			logger.error("客户跟进列表异常", e);
		}
		return "/follow/followList";
	}
	
	@RequestMapping("/custFollowList")
	public String custFollowListFrame(HttpServletRequest request) {
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("shiroUser", user);
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,user.getOrgId());
			request.setAttribute("options",options);		
		} catch (Exception e) {
			logger.error("客户跟进列表异常", e);
		}
		return "/follow/followListFrame";
	}
	/**
	 * 设置是否需要模糊电话号码
	 * 
	 * @param request
	 * @create 2015年11月25日 下午2:45:20 lixing
	 * @history
	 */
	public void setIsReplaceWord(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA24, user.getOrgId());
		Integer idReplaceWord = 0;
		if (!list.isEmpty() && list.get(0) != null && !cachedService.judgeHideWhiteList(user.getOrgId(), user.getAccount())) {
			idReplaceWord = new Integer(list.get(0).getDictionaryValue());
		}
		request.setAttribute("idReplaceWord", idReplaceWord);
	}
	
	/**
	 * 客户跟进列表JSON
	 */
	@RequestMapping("/custFollowListJson")
	@ResponseBody
	public Map<String, Object> custFollowListJson(HttpServletRequest request,CustFollowDto custFollowDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		String key = null;
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String dDateType = request.getParameter("dDateType");
			String nDateType = request.getParameter("nDateType");
			String mDateType = request.getParameter("mDateType");
			List<CustFollowDto> tsmCustFollowDtoList = new ArrayList<CustFollowDto>();
			 //模糊查询处理
            if(StringUtils.isNotBlank(custFollowDto.getQueryText())){
            	String queryText = custFollowDto.getQueryText().trim();
            	custFollowDto.setQueryText(queryText);
            }
			//  联系时间
			if(StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)){
				custFollowDto.setLastStartActionDate(getStartDateStr(Integer.parseInt(dDateType)));
				custFollowDto.setLastEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			//  下次联系时间
			if(StringUtils.isNotBlank(nDateType) && !"0".equals(nDateType) && !"5".equals(nDateType)){
				if("4".equals(nDateType)){
					custFollowDto.setNextStartActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
					custFollowDto.setNextEndActionDate(getEndDateStr(Integer.parseInt(nDateType)));
				}else {
					custFollowDto.setNextStartActionDate(getStartDateStr(Integer.parseInt(nDateType)));
					custFollowDto.setNextEndActionDate(getEndDateStr(Integer.parseInt(nDateType)));
				}
			}
			//  开始联系时间
			if(StringUtils.isNotBlank(mDateType) && !"0".equals(mDateType) && !"5".equals(mDateType)){
				custFollowDto.setInitStartFollowDate(getStartDateStr(Integer.parseInt(mDateType)));
				custFollowDto.setInitEndFollowDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			if(custFollowDto.getFollowCustCation()==null){
				// 默认选中全部
				custFollowDto.setFollowCustCation(FollowCustEnum.CUST_ALL.getState());
			}
			String allLabels = request.getParameter("allLabels");
			custFollowDto.setState(user.getIsState());  // 0：个人客户,1:企业客户
			custFollowDto.setRoleType(user.getIssys()); // 角色类别：0--销售，1--管理者
			// 管理者查询
			if(custFollowDto.getRoleType() != null && custFollowDto.getRoleType()==1){
				if(StringUtils.isNotEmpty(custFollowDto.getAccs())){
					String[] ownerAccs = custFollowDto.getAccs().split(",");
					List<String> owaList = Arrays.asList(ownerAccs);
					custFollowDto.setOwnerAccs(owaList);
				}else{
					// 所有者查询方式 1-全部 2-只看自己 3-选中查询
					if (StringUtils.isNotEmpty(custFollowDto.getAccs())) {
						custFollowDto.setOsType("3");
					}else if (StringUtils.isEmpty(custFollowDto.getAccs()) && StringUtils.isBlank(custFollowDto.getOsType())){
						custFollowDto.setOsType("1");
					}
					//custFollowDto.setOsType(StringUtils.isBlank(custFollowDto.getOsType()) ? "1" : custFollowDto.getOsType());
					if("1".equals(custFollowDto.getOsType())){
						List<String>list = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(),user.getAccount());
						if(list!=null && list.size()>0){
							StringBuffer sb = new StringBuffer();
							for(String str: list){
								sb.append(str);
								sb.append(",");
							}
							if (!list.contains(user.getAccount())) {
								list.add(user.getAccount());
							}
							if(sb.length()>0){
								sb = sb.deleteCharAt(sb.length() - 1);
							}
							custFollowDto.setAccs(sb.toString());
							custFollowDto.setOwnerAccs(list);
						}else {
							list.add(user.getAccount());
							custFollowDto.setOwnerAccs(list);
						}					
					}	
					 
				}
				
			}	
			// 组装选中标签
			if(StringUtils.isNotBlank(allLabels)){
				custFollowDto.setLabels(allLabels.split(","));
				request.setAttribute("labelNamesList", getLabelNameList(Arrays.asList(allLabels.split(",")), user.getOrgId()));
				request.setAttribute("allLabels", allLabels);
			}
			
			String orgId = user.getOrgId();
			custFollowDto.setOrgId(orgId);
			custFollowDto.setOwnerAcc(user.getAccount());
			CustFollowDto tsmCustFollowDto_cation = (CustFollowDto) custFollowDto.clone();
			followCustCations(tsmCustFollowDto_cation);
			//查出多选项查询字段
			List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_1.getState());
			multiSearchList.add(AppConstant.SEARCH_LABEL);
			tsmCustFollowDtoList = custFollowService.getTsmCustFollowListPage(tsmCustFollowDto_cation,multiSearchList);
			// 缓存 数据列表
			/*key = MD5Utils.getMD5String(JSON.toJSONString(tsmCustFollowDto_cation));			
			tsmCustFollowDtoList = (List<CustFollowDto>) CachedUtil.getInstance().get(key);
			if(!(tsmCustFollowDtoList!=null && tsmCustFollowDtoList.size() >0)){
				tsmCustFollowDtoList = custFollowService.getTsmCustFollowListPage(tsmCustFollowDto_cation,multiSearchList);	
			}*/
			//多选项显示
			if(tsmCustFollowDtoList.size() > 0){
				List<String> multiShowList = cachedService.getMultiSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_1.getState());
				List<String> singleShowList = cachedService.getSingleSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_1.getState());
				custFollowService.multiDefinedShowChange(tsmCustFollowDtoList,multiShowList,singleShowList,user.getOrgId());
			}
			// 所属行业
			Map<String, String> custTypeMap = cachedService.getOrgCustTypes(user.getOrgId());
			Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> optionMap = cachedService.getOrgSaleProcess(user.getOrgId());			
			Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
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
			//Map<String,String> definedMap = cachedService.getDefinedSearchField(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_1.getState());
			// 转换页面显示值
			for(CustFollowDto cdto : tsmCustFollowDtoList){
				if (custTypeMap != null) {
					cdto.setCustTypeName(custTypeMap.get(cdto.getCustTypeId()));
				}
				if(user.getIsState() == 1){
					if (cdto.getProvinceId() != null) {
						String area = provinceMap.get(cdto.getProvinceId());
						if(cdto.getCityId() != null) area+="-"+cityMap.get(cdto.getCityId());
						if(cdto.getCountyId() != null) area+="-"+countyMap.get(cdto.getCountyId());
						cdto.setArea(area);
					}
					cdto.setCompanyTrade(cdto.getCompanyTrade() != null ? tradeMap.get(cdto.getCompanyTrade()) : "");
				}
				cdto.setOwnerAcc(cdto.getOwnerName());
				cdto.setOwnerName(nameMap.get(cdto.getOwnerName()) == null ? cdto.getOwnerName() : nameMap.get(cdto.getOwnerName()));
				cdto.setShowLastActionDate(cdto.getLastActionDate() !=null?DateUtil.formatDate(cdto.getLastActionDate(), DateUtil.Data_ALL): "");
				cdto.setShowMinActionDate(cdto.getMinActionDate() !=null?DateUtil.formatDate(cdto.getMinActionDate(), DateUtil.Data_ALL): "");
				cdto.setShowNextActionDate(cdto.getNextActionDate() !=null?DateUtil.formatDate(cdto.getNextActionDate(), DateUtil.Data_ALL): "");
				cdto.setShowdefined16(cdto.getDefined16() !=null?DateUtil.formatDate(cdto.getDefined16(), DateUtil.DATE_DAY): "");
				cdto.setShowdefined17(cdto.getDefined17() !=null?DateUtil.formatDate(cdto.getDefined17(), DateUtil.DATE_DAY): "");
				cdto.setShowdefined18(cdto.getDefined18() !=null?DateUtil.formatDate(cdto.getDefined18(), DateUtil.DATE_DAY): "");
				cdto.setGroupName(StringUtils.isNotBlank(cdto.getGroupId()) ? groupMap.get(cdto.getGroupId()) : "");//资源分组
				if (optionMap != null) {
					cdto.setOptionName(StringUtils.isNotBlank(cdto.getOptionlistId()) ? optionMap.get(cdto.getOptionlistId()) : "");
				}
				
				/*if(definedMap != null && definedMap.size() > 0){
					// 自定义字段名称显示
					custFollowService.setCustFollowDefined(cdto,definedMap,user.getOrgId());
				}	*/			
			}
			/*CachedUtil.getInstance().set(key,tsmCustFollowDtoList,10);*/
			map.put("list", tsmCustFollowDtoList);
			map.put("item", custFollowDto);
			map.put("status", "success");				
		} catch (Exception e) {			
			CachedUtil.getInstance().delete(key); // 报异常后 删除缓存!
			logger.error("客户跟进列表JSON异常！", e);
			map.put("status", "error");						
		}
		return map;
	}
	
	/**
	 * 跟进记录
	 */
	@RequestMapping("/followRecordList")
	public String followRecordList(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("shiroUser", user);
			//从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,user.getOrgId());
			request.setAttribute("options",options);			
			//从缓存读取客户分类列表
			List<OptionBean> custTypeList = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO,user.getOrgId());
			request.setAttribute("custTypeList",custTypeList);
			request.setAttribute("dDateType", "1"); //最近联系时间 默认为当天
			// 所属行业
			List<OptionBean> companyTrades = cachedService.getOptionList("companyTrade",user.getOrgId());
			request.setAttribute("companyTrades",companyTrades);
			// 管理者查询 所有者查询方式 默认 1-全部
			if(user.getIssys() != null && user.getIssys()==1){ 
				CustFollowDto custFollowDto = new CustFollowDto();
				custFollowDto.setOsType(StringUtils.isBlank(custFollowDto.getOsType()) ? "1" : custFollowDto.getOsType());	
				custFollowDto.setComosType(StringUtils.isBlank(custFollowDto.getComosType()) ? "1" : custFollowDto.getComosType());
				request.setAttribute("item", custFollowDto);
			}
			// 资源分组
			List<Map<String, Object>> groups = cachedService.getResGroupList1(user.getOrgId());
			request.setAttribute("groupList",groups);
			List<CustSearchSet> tlist = getIsTextQueryList(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_2.getState());
			request.setAttribute("tList", tlist); // 文本类型查询字段列表
			// 用于筛选项排序
			Map<String,Integer> mutilSearchCodeSortMap = getUnTestSearchSort(SysEnum.SEARCH_SET_MODULE_2.getState(),user.getOrgId(),user.getIssys());
			request.setAttribute("mutilSearchCodeSortMap",mutilSearchCodeSortMap);
			// 需隐藏列的序号
			List<Integer> sorts = getHideSort2ListCode(SysEnum.SEARCH_SET_MODULE_2.getState(),user.getOrgId(),user.getIsState().toString());
			request.setAttribute("sorts", sorts);
			setIsReplaceWord(request);
		}catch (Exception e) {
			logger.error("跟进记录列表 异常！", e);
		}
		return "/follow/followRecordList";
	}
	
	/**
	 * 跟进记录JSON 数据
	 */
	@ResponseBody
	@RequestMapping("/followRecordListJson")
	public  Map<String, Object> followRecordListJson(HttpServletRequest request,CustFollowDto custFollowDto){
		Map<String, Object> map = new HashMap<String, Object>();
		String key = null;
		try{			
			ShiroUser user = ShiroUtil.getShiroUser();
			String dDateType = request.getParameter("dDateType");
			String nDateType = request.getParameter("nDateType");
			List<CustFollowDto> tsmCustFollowDtoList = new ArrayList<CustFollowDto>();
			//TODO 判断是否是管理者 
			String orgId=user.getOrgId();
		    String loginAcc=user.getAccount();
		    String  allLabels=request.getParameter("allLabels");
		    custFollowDto.setRoleType(user.getIssys()); // 角色类别：0--销售，1--管理者

		    //模糊查询处理
            if(StringUtils.isNotBlank(custFollowDto.getQueryText())){
            	String queryText = custFollowDto.getQueryText().trim();
            	custFollowDto.setQueryText(queryText);
            }
	         // 管理者查询
			if(custFollowDto.getRoleType() != null && custFollowDto.getRoleType()==1){
				// 所有者查询方式 1-全部 2-只看自己 3-选中查询
				if (StringUtils.isNotEmpty(custFollowDto.getAccs())) {
					custFollowDto.setOsType("3");
				}else if (StringUtils.isEmpty(custFollowDto.getAccs()) && StringUtils.isBlank(custFollowDto.getOsType())){
					custFollowDto.setOsType("1");
				}
				//custFollowDto.setOsType(StringUtils.isBlank(custFollowDto.getOsType()) ? "1" : custFollowDto.getOsType());
				if (StringUtils.isNotEmpty(custFollowDto.getAccs()) && "3".equals(custFollowDto.getOsType())) {
					String[] ownerAccs = custFollowDto.getAccs().split(",");
					List<String> owaList = Arrays.asList(ownerAccs);
					custFollowDto.setOwnerAccs(owaList);
				}else if("1".equals(custFollowDto.getOsType())){
						List<String>list = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(),user.getAccount());
						if(list!=null && list.size()>0){
							StringBuffer sb = new StringBuffer();
							for(String str: list){
								sb.append(str);
								sb.append(",");
							}
							if (!list.contains(user.getAccount())) {
								list.add(user.getAccount());
							}
							if(sb.length()>0){
								sb = sb.deleteCharAt(sb.length() - 1);
							}
							custFollowDto.setAccs(sb.toString());
							custFollowDto.setOwnerAccs(list);
						}else {
							list.add(user.getAccount());
							custFollowDto.setOwnerAccs(list);
						}	
						
					}
				
				// 跟进者查询方式
				if (StringUtils.isNotEmpty(custFollowDto.getFaccs())) {
					String[] ownerAccs = custFollowDto.getFaccs().split(",");
					List<String> owaList = Arrays.asList(ownerAccs);
					custFollowDto.setFollowAccs(owaList);
				}
			}
			//  最近联系时间
			if(StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)){
				custFollowDto.setLastStartActionDate(getStartDateStr(Integer.parseInt(dDateType)));
				custFollowDto.setLastEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			//  下次联系时间
			if(StringUtils.isNotBlank(nDateType) && !"0".equals(nDateType) && !"5".equals(nDateType)){
				if("4".equals(nDateType)){
					custFollowDto.setNextStartActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
					custFollowDto.setNextEndActionDate(getEndDateStr(Integer.parseInt(nDateType)));
				}else {
					custFollowDto.setNextStartActionDate(getStartDateStr(Integer.parseInt(nDateType)));
					custFollowDto.setNextEndActionDate(getEndDateStr(Integer.parseInt(nDateType)));
				}
			}
			
			custFollowDto.setOwnerAcc(loginAcc);
			
			custFollowDto.setState(user.getIsState());
			// 组装选中标签
			if(StringUtils.isNotBlank(allLabels)){
				custFollowDto.setLabels(allLabels.split(","));
			}				
			custFollowDto.setOrgId(orgId);
			custFollowDto.setOrderKey(" LAST_ACTION_DATE desc");
			tsmCustFollowDtoList = custFollowService.queryCustFollowsListPage(custFollowDto);
			// 缓存 数据列表
			/*key = MD5Utils.getMD5String(JSON.toJSONString(custFollowDto));			
			tsmCustFollowDtoList = (List<CustFollowDto>) CachedUtil.getInstance().get(key);
			if(!(tsmCustFollowDtoList!=null && tsmCustFollowDtoList.size() >0)){
				tsmCustFollowDtoList = custFollowService.queryCustFollowsListPage(custFollowDto);	
				CachedUtil.getInstance().set(key,tsmCustFollowDtoList,10);
			}*/

			Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> custTypeMap = cachedService.getOrgCustTypes(user.getOrgId());
			Map<String, String> resGroupNames = cachedService.getOrgResGroupNames(orgId);
			
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
			/** 拼装跟进记录查询结果  */
			// 根据查询出的资源ID集合，查询资源信息
			if(tsmCustFollowDtoList != null && tsmCustFollowDtoList.size() >0){
				List<String>recustIds = new ArrayList<String>();				
				Map<String, String> optionMap = cachedService.getOrgSaleProcess(user.getOrgId());			
				// 转换页面显示值
				for(CustFollowDto cfd : tsmCustFollowDtoList){
					if (custTypeMap != null) {
						cfd.setCustTypeName(custTypeMap.get(cfd.getCustTypeId()));
					}
					/*if(user.getIsState() == 1){
						if (cfd.getProvinceId() != null) {
							String area = provinceMap.get(cfd.getProvinceId());
							if(cfd.getCityId() != null) area+="-"+cityMap.get(cfd.getCityId());
							if(cfd.getCountyId() != null) area+="-"+countyMap.get(cfd.getCountyId());
							cfd.setArea(area);
						}
						cfd.setCompanyTrade(cfd.getCompanyTrade() != null ? tradeMap.get(cfd.getCompanyTrade()) : "");
					}*/
					cfd.setShowLastActionDate(cfd.getLastActionDate() !=null?DateUtil.formatDate(cfd.getLastActionDate(), DateUtil.Data_ALL): "");
					cfd.setShowNextActionDate(cfd.getNextActionDate() !=null?DateUtil.formatDate(cfd.getNextActionDate(), DateUtil.Data_ALL): "");
					if (optionMap != null) {
						cfd.setOptionName(optionMap.get(cfd.getOptionlistId()));
					}
					if(nameMap !=null){
						cfd.setFollowAcc(nameMap.get(cfd.getFollowAcc()) == null ? "" : nameMap.get(cfd.getFollowAcc()));
					}
					
					recustIds.add(cfd.getCustId());
				}
				if(recustIds != null && recustIds.size() >0){
					Map<String,CustFollowDto>cfdMap = new HashMap<String, CustFollowDto>();
					custFollowDto.setResCustIds(recustIds);
					List<CustFollowDto> tsmCustList = custFollowService.getResCustsByCustIds(custFollowDto);
					for(CustFollowDto fcd : tsmCustList){
						cfdMap.put(fcd.getResCustId(), fcd);
					}	
					for(CustFollowDto cfd : tsmCustFollowDtoList){
						if(cfdMap.get(cfd.getCustId()) != null){
							CustFollowDto cfd1 = cfdMap.get(cfd.getCustId());
							cfd.setResCustId(cfd.getCustId());
							cfd.setCreateTime(cfd1.getCreateTime());
							cfd.setOwnerName(nameMap.get(cfd1.getOwnerName()) == null ? cfd1.getOwnerName() : nameMap.get(cfd1.getOwnerName()));
							cfd.setCustMobilephone(cfd1.getCustMobilephone());
							cfd.setIsMajor(cfd1.getIsMajor());
							cfd.setCompany(cfd1.getCompany());
							cfd.setCustName(cfd1.getCustName());
							cfd.setCustMobilephone(cfd1.getCustMobilephone());
							cfd.setLinkName(cfd1.getLinkName());
							cfd.setGroupName(cfd1.getGroupId() != null ? resGroupNames.get(cfd1.getGroupId()) : "");
							if(user.getIsState() == 1){
								if (cfd1.getProvinceId() != null) {
									String area = provinceMap.get(cfd1.getProvinceId());
									if(cfd1.getCityId() != null) area+="-"+cityMap.get(cfd1.getCityId());
									if(cfd1.getCountyId() != null) area+="-"+countyMap.get(cfd1.getCountyId());
									cfd.setArea(area);
								}
								cfd.setCompanyTrade(cfd1.getCompanyTrade() != null ? tradeMap.get(cfd1.getCompanyTrade()) : "");
							}
						}
					}
				}			
			}
			
			map.put("list", tsmCustFollowDtoList);
			map.put("item", custFollowDto);
			map.put("status", "success");
	     	return map;
		}catch (Exception e) {
			CachedUtil.getInstance().delete(key); // 报异常后 删除缓存!
			logger.error("跟进记录JSON 数据异常！", e);
			 map.put("status", "error");
			 return map;
		}
		
	}
	
	// 跟进记录 右侧信息 
	@RequestMapping("/followRecordRight")
	public String followRecordRight(HttpServletRequest request){
		try{
			rightInfo(request);
		}catch(Exception e){
			logger.error("跟进记录 右侧信息 异常！",e);
		}
		return "/follow/list_right/followRecordRight";
	}
	
	//客户跟进 右侧信息 
	@RequestMapping("/followListRight")
	public String followListRight(HttpServletRequest request){
		try{
			rightInfo(request);
		}catch(Exception e){
			logger.error("客户跟进 右侧信息  异常！",e);
		}
		return "/follow/list_right/followListRight";
	}
	
	//客户跟进 右侧信息 
	@RequestMapping("/saleChanceRight")
	public String saleChanceRight(HttpServletRequest request){
		try{
			rightInfo1(request);
		}catch(Exception e){
			logger.error("客户跟进 右侧信息  异常！",e);
		}
		return "/follow/list_right/followListRight";
	}
	
	// 跟进列表右侧 公共方法
	private void rightInfo1(HttpServletRequest request) throws Exception{
		String resCustId = request.getParameter("reCustId"); // resCustId 资源ID
		request.setAttribute("resCustId", resCustId);
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String,Object> maps=getLableList(user.getOrgId());
		Integer isSelect=(Integer) maps.get("isSelect");
		request.setAttribute("isSelect", isSelect);
		// 查询客户
		ResCustInfoBean custInfo = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(),resCustId);
		request.setAttribute("custInfo", custInfo);		
		
		// 查询联系人
		List<ResCustInfoDetailBean> details = resCustInfoDetailService.getCustsInfoDetails(user.getOrgId(), resCustId);
		request.setAttribute("details",details);
	
		//从缓存读取销售进程列表
		List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,user.getOrgId());
		request.setAttribute("options",options);		
		
		//根据客户id 查询所有 跟进信息
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", user.getOrgId());
		map.put("custId", resCustId);
		map.put("state", user.getIsState());
		
		List<CustFollowDto> custFollows= custFollowService.queryCustFollowByCustId(map);
		Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
		// 组装跟进ID
		List<String> followIdSbf = new ArrayList<String>();
		Map<String, String> optionMap = cachedService.getOrgSaleProcess(user.getOrgId());	
		 // 将列表中的标签CODE值根据tsm_$_optionMap转换为标签NAME
		for(CustFollowDto followDto : custFollows){
			followDto.setOwnerName(nameMap.get(followDto.getFollowAcc()) == null ? followDto.getFollowAcc() : nameMap.get(followDto.getFollowAcc()));
			followIdSbf.add(followDto.getCustFollowId());							
			followDto.setShowLastActionDate(followDto.getLastActionDate() !=null?DateUtil.formatDate(followDto.getLastActionDate(), DateUtil.Data_ALL): "");			
			followDto.setShowNextActionDate(followDto.getNextActionDate() !=null?DateUtil.formatDate(followDto.getNextActionDate(), DateUtil.Data_ALL): "");
				if (optionMap != null) {
					followDto.setOptionName(optionMap.get(followDto.getOptionlistId()));
				}
			String labelName = followDto.getLabelName();
			if(StringUtils.isNotBlank(labelName)){
				labelName = labelName.replaceAll("#", "，");
				//labelName = labelName.substring(0,labelName.length()-1);
				followDto.setLabelName(labelName);
			}
		}
		
		//  获取相关跟进ID 的录音记录
		if(followIdSbf != null && followIdSbf.size() > 0){
			/** 参数 */ 
			FollowCallQueryDto fcqd = new FollowCallQueryDto();
			fcqd.setOrgId(user.getOrgId());
			fcqd.setFollowIds(followIdSbf);
			List<TsmRecordCallBean> callLists  = null;
			String key = MD5Utils.getMD5String(JSON.toJSONString(fcqd));    
			callLists =(List<TsmRecordCallBean>) CachedUtil.getInstance().get(key);
			if(!(callLists != null)){
				callLists = CallRecordGetUtil.getRecordeCallFollowList(fcqd);
				if(callLists!=null && callLists.size() >0){
					CachedUtil.getInstance().set(key,callLists,10);
				}				
			}
			
			if(callLists != null && callLists.size() >0){
				Map<String,List<CustFollowCallDto>>map1 = new HashMap<String, List<CustFollowCallDto>>(); 
				for(TsmRecordCallBean trcb : callLists){
					if(trcb.getRecordState() !=null && trcb.getRecordState()==1){ // 有录音
						CustFollowCallDto cfcd = new CustFollowCallDto(); //  组装跟进与录音相关联的DTO
						cfcd.setOrgId(trcb.getOrgId());
						cfcd.setCallId(trcb.getId());
						cfcd.setRecordUrl(trcb.getRecordUrl());
						cfcd.setTimeShow(MathUtil.secondFormat(trcb.getTimeLength() == null ? 0 : trcb.getTimeLength())); // 时长
						cfcd.setId(trcb.getId());
						cfcd.setCalledNum(trcb.getCalledNum());
						cfcd.setCallerNum(trcb.getCallerNum());
						cfcd.setCallState(trcb.getCallState());
						cfcd.setCode(trcb.getCode());
						cfcd.setCustName(trcb.getCustName());
						cfcd.setRecordKey(trcb.getRecordKey());
						cfcd.setRecordState(trcb.getRecordState());
						cfcd.setTimeLength(new Integer(trcb.getTimeLength() == null ? 0 : trcb.getTimeLength()));
						if(map1.get(trcb.getFollowId()) != null){ // 如果跟进有多个录音，装入同一个LIST
							List<CustFollowCallDto> callDtos = map1.get(trcb.getFollowId());
							callDtos.add(cfcd);
							map1.put(trcb.getFollowId(), callDtos);
						}else{
							List<CustFollowCallDto> callDtos1 = new ArrayList<CustFollowCallDto>();
							callDtos1.add(cfcd);
							map1.put(trcb.getFollowId(), callDtos1);
						}
					}
				}
				for(CustFollowDto followDto : custFollows){ // 重新组装 跟进记录
					if(map1.get(followDto.getCustFollowId()) != null){
						followDto.setUrlList(map1.get(followDto.getCustFollowId()));
					}
				}
			}
			  // 录音地址
	        request.setAttribute("playUrl", ConfigInfoUtils.getStringValue("call_play_url"));
	        // 服务地址，为了提供给客户端，弹出播放列表 	
	        request.setAttribute("project_path", getProgetUtil(request));
		}
		
		request.setAttribute("custFollows",custFollows);
		request.setAttribute("state", user.getIsState());  // 0:个人资源，1：企业资源
		setIsReplaceWord(request,user); // 设置是否开启用*替换电话号码中间四位	
	}
	
	//我的计划 右侧信息 
	@RequestMapping("/dayPlanRight")
	public String dayPlanRight(HttpServletRequest request){
		try{
			rightInfo2(request);
		}catch(Exception e){
			logger.error("我的计划 右侧信息  异常！",e);
		}
		return "/plan/dayplan/right/dayPlan_Right";
	}
	
	// 跟进列表右侧 公共方法
		private void rightInfo2(HttpServletRequest request) throws Exception{
			try {
				ShiroUser user = ShiroUtil.getShiroUser();
				String resCustId = request.getParameter("reCustId"); // resCustId 资源ID
				String followId = request.getParameter("followId"); // 跟进ID
				String showCount = request.getParameter("showCount");//是否只显示一条跟进记录   1.只显示一条 0.不限制显示
				request.setAttribute("resCustId", resCustId);
				// 查询客户
				ResCustInfoBean custInfo = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(),resCustId);
				
				//if(StringUtils.isNotBlank(resCustId) && StringUtils.isNotBlank(followId)){
					request.setAttribute("followId", followId);
					
					
					Map<String,Object> maps=getLableList(user.getOrgId());
					Integer isSelect=(Integer) maps.get("isSelect");
					request.setAttribute("isSelect", isSelect);
					
					// 查询联系人
					List<ResCustInfoDetailBean> details = resCustInfoDetailService.getCustsInfoDetails(user.getOrgId(), resCustId);
					request.setAttribute("details",details);
				
					//从缓存读取销售进程列表
					List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,user.getOrgId());
					request.setAttribute("options",options);		
					Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
					Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
					custInfo.setLastOptionName(custInfo.getLastOptionId() == null ? null : saleProcMap.get(custInfo.getLastOptionId()));
					request.setAttribute("custInfo", custInfo);		
				List<ResCustActionDto> actionDtos = resCustEventService.getResCustActionsList(user.getOrgId(), resCustId);
				// 组装跟进ID
				List<String> followIdSbf = new ArrayList<String>();
				for (ResCustActionDto actoionDto : actionDtos) {
//					if(StringUtils.isNotBlank(actoionDto.getInputAcc())){
						actoionDto.setInputName(nameMap.get(actoionDto.getInputAcc()));
//					}
					if(StringUtils.isNotBlank(actoionDto.getSaleProcessId())){
						actoionDto.setSaleProcessName(saleProcMap.get(actoionDto.getSaleProcessId()));
					}
					if(StringUtils.isNotBlank(actoionDto.getCustFollowId())){
						followIdSbf.add(actoionDto.getCustFollowId());
					}
					String labelName = actoionDto.getLabels();
					if (StringUtils.isNotBlank(labelName)) {
						labelName = labelName.replaceAll("#", "，");
						//labelName = labelName.substring(0, labelName.length() - 1);
						actoionDto.setLabels(labelName);
					}
				}
				// 获取相关跟进ID 的录音记录
				if (followIdSbf != null && followIdSbf.size() > 0) {
					/** 参数 */
					FollowCallQueryDto fcqd = new FollowCallQueryDto();
					fcqd.setOrgId(user.getOrgId());
					fcqd.setFollowIds(followIdSbf);
					List<TsmRecordCallBean> callLists = CallRecordGetUtil.getRecordeCallFollowList(fcqd);
					if (callLists != null && callLists.size() >0) {
						Map<String, List<CustFollowCallDto>> map1 = new HashMap<String, List<CustFollowCallDto>>();
						for (TsmRecordCallBean trcb : callLists) {
							if (trcb.getRecordState() != null && trcb.getRecordState() == 1) { // 有录音
								CustFollowCallDto cfcd = new CustFollowCallDto(); // 组装跟进与录音相关联的DTO
								cfcd.setOrgId(trcb.getOrgId());
								cfcd.setCallId(trcb.getId());
								cfcd.setRecordUrl(trcb.getRecordUrl());
								cfcd.setTimeShow(MathUtil.secondFormat(trcb.getTimeLength() == null ? 0 : trcb.getTimeLength())); // 时长
								cfcd.setId(trcb.getId());
								cfcd.setCalledNum(trcb.getCalledNum());
								cfcd.setCallerNum(trcb.getCallerNum());
								cfcd.setCallState(trcb.getCallState());
								cfcd.setCode(trcb.getCode());
								cfcd.setCustName(trcb.getCustName());
								cfcd.setRecordKey(trcb.getRecordKey());
								cfcd.setRecordState(trcb.getRecordState());
								cfcd.setTimeLength(new Integer(trcb.getTimeLength() == null ? 0 : trcb.getTimeLength()));
								if (map1.get(trcb.getFollowId()) != null) { // 如果跟进有多个录音，装入同一个LIST
									List<CustFollowCallDto> callDtos = map1.get(trcb.getFollowId());
									callDtos.add(cfcd);
									map1.put(trcb.getFollowId(), callDtos);
								} else {
									List<CustFollowCallDto> callDtos1 = new ArrayList<CustFollowCallDto>();
									callDtos1.add(cfcd);
									map1.put(trcb.getFollowId(), callDtos1);
								}
							}
						}
						for (ResCustActionDto actoionDto : actionDtos) { // 重新组装
							if(StringUtils.isNotBlank(actoionDto.getCustFollowId())){
								if (map1.get(actoionDto.getCustFollowId()) != null) {
									actoionDto.setUrlList(map1.get(actoionDto.getCustFollowId()));
								}
							}
						}
					}
				}
				request.setAttribute("actionDtos", actionDtos);
				// 录音地址
				request.setAttribute("playUrl", ConfigInfoUtils.getStringValue("call_play_url"));
				// 服务地址，为了提供给客户端，弹出播放列表
				request.setAttribute("project_path", getProgetUtil(request));

				request.setAttribute("state", user.getIsState()); // 0:个人资源，1：企业资源
				request.setAttribute("custInfo", custInfo);

				request.setAttribute("showCount", showCount);  // 0:不显示，1：显示
				setIsReplaceWord(request,user); // 设置是否开启用*替换电话号码中间四位	
			} catch (Exception e) {
				throw new SysRunException(e);
			}
		}
	
	// 跟进列表右侧 公共方法
	private void rightInfo(HttpServletRequest request) throws Exception{
		ShiroUser user = ShiroUtil.getShiroUser();
		String resCustId = request.getParameter("reCustId"); // resCustId 资源ID
		String followId = request.getParameter("followId"); // 跟进ID
		String showCount = request.getParameter("showCount");//是否只显示一条跟进记录   1.只显示一条 0.不限制显示
		String saleChanceId = request.getParameter("saleChanceId");
		String isShow = request.getParameter("isShow");
		request.setAttribute("resCustId", resCustId);
		request.setAttribute("isShow", isShow);
		// 查询客户
		ResCustInfoBean custInfo = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(),resCustId);
		//if(StringUtils.isNotBlank(resCustId) && StringUtils.isNotBlank(followId)){
			request.setAttribute("followId", followId);
			
			
			Map<String,Object> maps=getLableList(user.getOrgId());
			Integer isSelect=(Integer) maps.get("isSelect");
			request.setAttribute("isSelect", isSelect);
			
			// 查询联系人
			List<ResCustInfoDetailBean> details = resCustInfoDetailService.getCustsInfoDetails(user.getOrgId(), resCustId);
			request.setAttribute("details",details);
		
			//从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,user.getOrgId());
			request.setAttribute("options",options);		
			
			//根据客户id 查询所有 跟进信息
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", user.getOrgId());
			map.put("custId", resCustId);
			map.put("showCount", showCount);
			map.put("state", user.getIsState());
			map.put("custFollowId", followId);
			map.put("saleChanceId", saleChanceId);
			
			List<CustFollowDto> custFollows= custFollowService.queryCustFollowByCustId(map);
			Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			// 组装跟进ID
			List<String> followIdSbf = new ArrayList<String>();
			Map<String, String> optionMap = cachedService.getOrgSaleProcess(user.getOrgId());
			custInfo.setLastOptionName(custInfo.getLastOptionId() == null ? null : optionMap.get(custInfo.getLastOptionId()));
			request.setAttribute("custInfo", custInfo);
			String optionId = "";
			String optionName = "";
			 // 将列表中的标签CODE值根据tsm_$_optionMap转换为标签NAME
			//if (custFollows != null && custFollows.size() > 0) {
				for(CustFollowDto followDto : custFollows){
					/*if(followDto.getCustFollowId().equals(followId)){	// 根据客户跟进ID 查询销售进程ID以及销售进程名称
						optionId = followDto.getOptionlistId();
						if (optionMap != null) {
							optionName = optionMap.get(followDto.getOptionlistId());
						}
					}*/
					followDto.setOwnerName(nameMap.get(followDto.getFollowAcc()) == null ? followDto.getFollowAcc() : nameMap.get(followDto.getFollowAcc()));
					followIdSbf.add(followDto.getCustFollowId());							
					followDto.setShowLastActionDate(followDto.getLastActionDate() !=null?DateUtil.formatDate(followDto.getLastActionDate(), DateUtil.Data_ALL): "");			
					followDto.setShowNextActionDate(followDto.getNextActionDate() !=null?DateUtil.formatDate(followDto.getNextActionDate(), DateUtil.Data_ALL): "");
					if (optionMap != null) {
						followDto.setOptionName(optionMap.get(followDto.getOptionlistId()));
					}
					String labelName = followDto.getLabelName();
					if(StringUtils.isNotBlank(labelName)){
						labelName = labelName.replaceAll("#", "，");
						//labelName = labelName.substring(0,labelName.length()-1);
						followDto.setLabelName(labelName);
					}
				}
			//}else {
				optionId = custInfo.getLastOptionId();
				optionName = custInfo.getLastOptionName();
			//}
			
			request.setAttribute("optionId", optionId);
			request.setAttribute("optionName", optionName);
			
			//  获取相关跟进ID 的录音记录
			if(followIdSbf != null && followIdSbf.size() > 0){
				/** 参数 */ 
				FollowCallQueryDto fcqd = new FollowCallQueryDto();
				fcqd.setOrgId(user.getOrgId());
				fcqd.setFollowIds(followIdSbf);
				List<TsmRecordCallBean> callLists  = null;
				String key = MD5Utils.getMD5String(JSON.toJSONString(fcqd));    
				callLists =(List<TsmRecordCallBean>) CachedUtil.getInstance().get(key);
				if(!(callLists != null)){
					callLists = CallRecordGetUtil.getRecordeCallFollowList(fcqd);
					if(callLists!=null && callLists.size() >0){
						CachedUtil.getInstance().set(key,callLists,10);
					}				
				}
				
				if(callLists != null && callLists.size() >0){
					Map<String,List<CustFollowCallDto>>map1 = new HashMap<String, List<CustFollowCallDto>>(); 
					for(TsmRecordCallBean trcb : callLists){
						if(trcb.getRecordState() !=null && trcb.getRecordState()==1){ // 有录音
							CustFollowCallDto cfcd = new CustFollowCallDto(); //  组装跟进与录音相关联的DTO
							cfcd.setOrgId(trcb.getOrgId());
							cfcd.setCallId(trcb.getId());
							cfcd.setRecordUrl(trcb.getRecordUrl());
							cfcd.setTimeShow(MathUtil.secondFormat(trcb.getTimeLength() == null ? 0 : trcb.getTimeLength())); // 时长
							cfcd.setId(trcb.getId());
							cfcd.setCalledNum(trcb.getCalledNum());
							cfcd.setCallerNum(trcb.getCallerNum());
							cfcd.setCallState(trcb.getCallState());
							cfcd.setCode(trcb.getCode());
							cfcd.setCustName(trcb.getCustName());
							cfcd.setRecordKey(trcb.getRecordKey());
							cfcd.setRecordState(trcb.getRecordState());
							cfcd.setTimeLength(new Integer(trcb.getTimeLength() == null ? 0 : trcb.getTimeLength()));
							if(map1.get(trcb.getFollowId()) != null){ // 如果跟进有多个录音，装入同一个LIST
								List<CustFollowCallDto> callDtos = map1.get(trcb.getFollowId());
								callDtos.add(cfcd);
								map1.put(trcb.getFollowId(), callDtos);
							}else{
								List<CustFollowCallDto> callDtos1 = new ArrayList<CustFollowCallDto>();
								callDtos1.add(cfcd);
								map1.put(trcb.getFollowId(), callDtos1);
							}
						}
					}
					for(CustFollowDto followDto : custFollows){ // 重新组装 跟进记录
						if(map1.get(followDto.getCustFollowId()) != null){
							followDto.setUrlList(map1.get(followDto.getCustFollowId()));
						}
					}
				}
				  // 录音地址
		        request.setAttribute("playUrl", ConfigInfoUtils.getStringValue("call_play_url"));
		        // 服务地址，为了提供给客户端，弹出播放列表 	
		        request.setAttribute("project_path", getProgetUtil(request));
			}
			
			request.setAttribute("custFollows",custFollows);
			request.setAttribute("state", user.getIsState());  // 0:个人资源，1：企业资源
			request.setAttribute("showCount", showCount);  // 0:不显示，1：显示
			setIsReplaceWord(request,user); // 设置是否开启用*替换电话号码中间四位	
		//}
	}
	
	public Map<String,Object> getLableList(String orgId){
		Map<String,Object> map=new HashMap<String,Object>();
		Integer isSelect=0;//是否需要选择层级，0为不需要，按照默认的来，1为需要
		List<OptionDto> opList = new ArrayList<OptionDto>();
		try {

		List<OptionGroupBean> getOptionGroupList=cachedService.getOptionGroupList(AppConstant.SALES_TYPE_TEN,orgId);
		List<OptionBean> labelList = cachedService.getOptionList(AppConstant.SALES_TYPE_TEN, orgId);
		if(getOptionGroupList!=null&&getOptionGroupList.size()>0){
			if(getOptionGroupList.size()==1){
				 isSelect=0;
			}else if(getOptionGroupList.size()>1){
				 int sum=0;
				for(OptionGroupBean optionGroupBean :getOptionGroupList){
					if(optionGroupBean.getIsDefault()==1){
						if (labelList != null && labelList.size() > 0) {
							for (OptionBean optionBean : labelList) {
                                if(optionGroupBean.getGroupId()==optionBean.getGroupId()||optionGroupBean.getGroupId().endsWith(optionBean.getGroupId())){
                                	sum=sum+1;
                                	if(sum>1||sum==1){
                                		isSelect=1;
                                		break;
                                	}
                                }
								
							}
						}
					}
				}
			}
		}
		
        if(isSelect==0){
    		if (labelList != null && labelList.size() > 0) {
    			for (OptionBean optionBean : labelList) {
    				OptionDto optionDto = new OptionDto();
    				optionDto.setIsDefault(optionBean.getIsDefault() + "");
    				optionDto.setOptionId(optionBean.getOptionlistId());
    				optionDto.setOptionName(optionBean.getOptionName());
    				opList.add(optionDto);
    			}
    		}	
        }else{
        	opList = cachedService.getLableList(orgId);
        }
		
	   } catch (Exception e) {
		e.printStackTrace();
	   }
        map.put("isSelect", isSelect);
        map.put("opList", opList);
        return map;
	}
	
	/** 客户跟进右侧 录音记录 */
	@RequestMapping("/queryCallLog")
	public String queryCallLog(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String custId=request.getParameter("resCustId");	
			if(StringUtils.isNotBlank(custId)){
				/** 参数 */ 
				CustCallQueryDto queryDto = new CustCallQueryDto();
				queryDto.setOrgId(user.getOrgId());
				queryDto.setCustId(custId);
				List<TsmRecordCallBean> callList = CallRecordGetUtil.getRecordeCallList(queryDto);
				if(callList != null && callList.size() > 0){
					List<RecordCallDto> list = new ArrayList<RecordCallDto>();
					RecordCallDto dto = null;
					if(callList != null && callList.size() >0){
						Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
						for(TsmRecordCallBean callBean : callList){
							dto = new RecordCallDto();
							BeanUtils.copyProperties(callBean, dto); // copy 
							dto.setTimeShow(MathUtil.secondFormat(callBean.getTimeLength()==null ? 0 : callBean.getTimeLength())); // 时长
							dto.setInputName(StringUtils.isBlank(dto.getInputAcc()) ? "" : nameMap.get(dto.getInputAcc()));
							list.add(dto);
						}
					}					
					request.setAttribute("callList", list);
					  // 录音地址
			        request.setAttribute("playUrl", ConfigInfoUtils.getStringValue("call_play_url"));
			        // 服务地址，为了提供给客户端，弹出播放列表 	
			        request.setAttribute("project_path", getProgetUtil(request));
			        setIsReplaceWord(request,user); // 设置是否开启用*替换电话号码中间四位	
				}
			}			
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return "/follow/list_right/followListRight_callLog";
	}
	
	/** 客户跟进右侧 点评信息 */
	@RequestMapping("/queryReview")
	public String queryReview(HttpServletRequest request){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String custId = request.getParameter("resCustId"); // 资源ID
			String retaId = request.getParameter("retaId"); // 跟进Id
			if(StringUtils.isNotBlank(custId) && StringUtils.isNotBlank(retaId)){
				TsmCustReview tsmCustReview = new TsmCustReview();
				tsmCustReview.setCustId(custId);
				tsmCustReview.setOrgId(user.getOrgId());
				tsmCustReview.setRetaId(retaId);
				List<TsmCustReview> tsmCustReviews= tsmCustReviewService.getCustReview(tsmCustReview);
				if(tsmCustReviews!=null && tsmCustReviews.size()>0){
					Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
					for(TsmCustReview revie : tsmCustReviews){
						revie.setReviewAcc(nameMap.get(revie.getReviewAcc()) == null ? revie.getReviewAcc() : nameMap.get(revie.getReviewAcc()));
					}
				}
				request.setAttribute("tsmCustReviews", tsmCustReviews);
			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/follow/list_right/followListRight_review";
	}
	
	/** 我的客户右侧 点评信息 */
	@RequestMapping("/queryCustReview")
	public String queryCustReview(HttpServletRequest request){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String custId = request.getParameter("resCustId"); // 资源ID
			if(StringUtils.isNotBlank(custId)){
				TsmCustReview tsmCustReview = new TsmCustReview();
				tsmCustReview.setCustId(custId);
				tsmCustReview.setOrgId(user.getOrgId());
				List<TsmCustReview> tsmCustReviews= tsmCustReviewService.getCustReview(tsmCustReview);
				if(tsmCustReviews!=null && tsmCustReviews.size()>0){
					Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
					for(TsmCustReview revie : tsmCustReviews){
						revie.setReviewAcc(nameMap.get(revie.getReviewAcc()) == null ? revie.getReviewAcc() : nameMap.get(revie.getReviewAcc()));
					}
				}
				request.setAttribute("tsmCustReviews", tsmCustReviews);
				request.setAttribute("resCustId", custId);
			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/res/custRight_review";
	}
	
	/** 添加点评  */
	@RequestMapping("/addReview")
	@ResponseBody
	public String addReview(HttpServletRequest request,TsmCustReview tsmCustReview) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			tsmCustReview.setReviewId(SysBaseModelUtil.getModelId());
			tsmCustReview.setReviewAcc(user.getAccount());
			tsmCustReview.setReviewName(user.getName());
			tsmCustReview.setReviewDate( com.qftx.common.util.DateUtil.getDateCurrentDate(com.qftx.common.util.DateUtil.hour24HMSPattern));
			tsmCustReview.setOrgId(user.getOrgId());
			tsmCustReview.setIsRead(0);
			tsmCustReview.setIsDel(0);
			tsmCustReviewService.createWithEvent(tsmCustReview);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return AppConstant.RESULT_SUCCESS;
	}
	
	/** 获取全部标签 */
	@RequestMapping("/allLabels")
	public String allLabels(HttpServletRequest request){		
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String label = request.getParameter("labels");
			if(StringUtils.isNotBlank(label)){ // 选中的标签			
				label = URLDecoder.decode(label,IContants.CHAR_ENCODING);			
			}
			List<OptionBean> getSubOptionList = cachedService.getOptionList(AppConstant.SALES_TYPE_TEN,user.getOrgId());
			request.setAttribute("entitys", getSubOptionList);
			request.setAttribute("size",getSubOptionList.size());
			request.setAttribute("label", label);
		} catch (Exception e) {
			logger.error("编码错误",e);
		}
		return "/follow/allLabel";
	}
	
	/** 获取全部标签 */
	@RequestMapping("/newAllLabels")
	public String newAllLabels(HttpServletRequest request){		
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String label = request.getParameter("labels");
			if(StringUtils.isNotBlank(label)){ // 选中的标签			
				label = URLDecoder.decode(label,IContants.CHAR_ENCODING);			
			}
			List<OptionBean> getSubOptionList = cachedService.getOptionList(AppConstant.SALES_TYPE_TEN,user.getOrgId());
			request.setAttribute("entitys", getSubOptionList);
			request.setAttribute("size",getSubOptionList.size());
			request.setAttribute("label", label);
		} catch (Exception e) {
			logger.error("编码错误",e);
		}
		return "/follow/new_allLabel";
	}
	
	@RequestMapping("/setFollowNextActionDate")
	@ResponseBody
	public String setFollowNextActionDate(HttpServletRequest request){
		try{
			String custIds = request.getParameter("resCustIds");
			String followIds = request.getParameter("custFollowIds");
			String nextActionDate = request.getParameter("nextActionDate");
			custFollowService.modfiyNextActionDate(DateUtil.parseDateToHMS(nextActionDate),followIds,custIds);			
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return AppConstant.RESULT_SUCCESS;
	}
	
	 /** 
		 * 客户跟进数据分类 列表查询不同处 集合 
		 * @param tsmCustFollowDto_ 获取跟进数的查询条件
		 * @param tsmCustFollowDto  获取跟进列表的查询条件
		 */
		private void followCustCations(CustFollowDto custFollowDto){
			 try{
				 ShiroUser user = ShiroUtil.getShiroUser();
				 // 上次跟进时间范围
				String lastStartActionDate_ = custFollowDto.getLastStartActionDate();
				String lastEndActionDate_ = custFollowDto.getLastEndActionDate();
				//上次所有者跟进时间范围
				String lastStartOwnerActionDate_ = custFollowDto.getLastStartOwnerActionDate();
				String lastEndOwnerActionDate_ = custFollowDto.getLastEndOwnerActionDate();
				// 下次跟进时间范围
				String nextStartActionDate_ = custFollowDto.getNextStartActionDate();
				String nextEndActionDate_ = custFollowDto.getNextEndActionDate();
				String today=com.qftx.common.util.DateUtil.format(new Date(), "yyyy-MM-dd");
				if (FollowCustEnum.CUST_TODAY_CONTACT_X.getState().equals(
						custFollowDto.getFollowCustCation())) {
						// 【今日待联系】
						//排序
						if(StringUtils.isNotBlank(custFollowDto.getOrderKey())){
							String orderKey = custFollowDto.getOrderKey()+",TRCI.IS_MAJOR DESC,TRCI.INPUT_DATE ASC,CONVERT(TRCI.NAME USING GBK)";
							custFollowDto.setOrderKey(orderKey);
						}else {
							custFollowDto.setOrderKey("TRCI.IS_MAJOR DESC,TRCI.NEXTFOLLOW_DATE ASC,TRCI.ACTION_DATE ASC,TRCI.INPUT_DATE ASC,CONVERT(TRCI.NAME USING GBK)");	 
						}
						if ((StringUtils.isBlank(nextStartActionDate_))
								&& (StringUtils.isBlank(nextEndActionDate_))) {
							nextStartActionDate_ = today;
							nextEndActionDate_ =  today;
						}		
						custFollowDto.setNextStartActionDate(nextStartActionDate_);
						custFollowDto.setNextEndActionDate(nextEndActionDate_);		
						custFollowDto.setContactStatus(1); 
						// 设置今日计划资源ID集合
						/*List<String>resCustIds = null;
						// 查询今日计划ID
						PlanUserDayBean planUserDayBean = planUserDayService.getPlanUserDay(user.getOrgId(),user.getId(),new Date());
						if(planUserDayBean != null){
							Map<String,Object>param = new HashMap<String, Object>();
							param.put("orgId", user.getOrgId());
							param.put("sudId", planUserDayBean.getId());
							List<String>strs = custFollowService.getCustIdBySignPlanIds(param);
							if(strs!= null && strs.size() >0){
								resCustIds = new ArrayList<String>();
								resCustIds.addAll(strs);
							}
							List<String>strs1 = custFollowService.getCustIdByWillPlanIds(param);
							if(strs1 != null && strs1.size() >0){
								if(resCustIds == null){
									resCustIds = new ArrayList<String>();
								}
								resCustIds.addAll(strs1);
							}
							custFollowDto.setResCustIds(resCustIds);
						}			*/			
				}else if(FollowCustEnum.CUST_TODAY_CONTACT_Y.getState().equals(
						custFollowDto.getFollowCustCation())){
						// 【今日已联系】上次次跟进时间=今天		
						if ((StringUtils.isBlank(lastStartOwnerActionDate_))
								&& (StringUtils.isBlank(lastEndOwnerActionDate_))) {
							lastStartOwnerActionDate_ = today;
							lastEndOwnerActionDate_ =  today;
						}		
						custFollowDto.setLastStartOwnerActionDate(lastStartOwnerActionDate_);
						custFollowDto.setLastEndOwnerActionDate(lastEndOwnerActionDate_);
						//排序
						if(StringUtils.isNotBlank(custFollowDto.getOrderKey())){
							String orderKey = custFollowDto.getOrderKey()+",TRCI.IS_MAJOR DESC,TRCI.INPUT_DATE ASC,CONVERT(TRCI.NAME USING GBK)";
							custFollowDto.setOrderKey(orderKey);
						}else {
							custFollowDto.setOrderKey("TRCI.IS_MAJOR DESC,TRCI.NEXTFOLLOW_DATE ASC,TRCI.ACTION_DATE ASC,TRCI.INPUT_DATE ASC,CONVERT(TRCI.NAME USING GBK)");	 
						}
				}else if(FollowCustEnum.CUST_WEEK_CONTACT_Y.getState().equals(
						custFollowDto.getFollowCustCation())){
						// 【本周已联系】周一=>上次跟进时间=今天			
					if ((StringUtils.isBlank(lastStartOwnerActionDate_))
							&& (StringUtils.isBlank(lastEndOwnerActionDate_))) {					
						lastStartOwnerActionDate_ = DateUtil.getWeekFirstDay(new Date());
						lastEndOwnerActionDate_ =  today;
					}
						custFollowDto.setLastStartOwnerActionDate(lastStartOwnerActionDate_);
						custFollowDto.setLastEndOwnerActionDate(lastEndOwnerActionDate_);	
						//排序
						if(StringUtils.isNotBlank(custFollowDto.getOrderKey())){
							String orderKey = custFollowDto.getOrderKey()+",TRCI.IS_MAJOR DESC,TRCI.INPUT_DATE ASC,CONVERT(TRCI.NAME USING GBK)";
							custFollowDto.setOrderKey(orderKey);
						}else {
							custFollowDto.setOrderKey("TRCI.IS_MAJOR DESC,TRCI.NEXTFOLLOW_DATE ASC,TRCI.ACTION_DATE ASC,TRCI.INPUT_DATE ASC,CONVERT(TRCI.NAME USING GBK)");	 
						}
				}else if(FollowCustEnum.CUST_7_TODAY_CONTACT_W.getState().equals(
						custFollowDto.getFollowCustCation())){
						// 【7天未联系】上次跟进时间<=7天前的时间			
						if ((StringUtils.isBlank(lastStartOwnerActionDate_))
								&& (StringUtils.isBlank(lastEndOwnerActionDate_))) {
							lastStartOwnerActionDate_ = null;
							lastEndOwnerActionDate_ =  com.qftx.common.util.DateUtil.format(com.qftx.common.util.DateUtil.parse(com.qftx.common.util.DateUtil.getOneDay(-7)),com.qftx.common.util.DateUtil.defaultPattern);
						}
						custFollowDto.setLastStartOwnerActionDate(lastStartOwnerActionDate_);
						custFollowDto.setLastEndOwnerActionDate(lastEndOwnerActionDate_);
						//排序
						if(StringUtils.isNotBlank(custFollowDto.getOrderKey())){
							String orderKey = custFollowDto.getOrderKey()+",TRCI.IS_MAJOR DESC,TRCI.INPUT_DATE ASC,CONVERT(TRCI.NAME USING GBK)";
							custFollowDto.setOrderKey(orderKey);
						}else {
							custFollowDto.setOrderKey("TRCI.IS_MAJOR DESC,TRCI.NEXTFOLLOW_DATE ASC,TRCI.ACTION_DATE ASC,TRCI.INPUT_DATE ASC,CONVERT(TRCI.NAME USING GBK)");	 
						}
				}else if(FollowCustEnum.CUST_30_TODAY_CONTACT_W.getState().equals(
						custFollowDto.getFollowCustCation())){
						// 【30天未联系】上次跟进时间<=30天前的时间
					if ((StringUtils.isBlank(lastStartOwnerActionDate_))
							&& (StringUtils.isBlank(lastEndOwnerActionDate_))) {
						lastStartOwnerActionDate_ = null;
						lastEndOwnerActionDate_ =  com.qftx.common.util.DateUtil.format(com.qftx.common.util.DateUtil.parse(com.qftx.common.util.DateUtil.getOneDay(-30)),com.qftx.common.util.DateUtil.defaultPattern);
					}
					custFollowDto.setLastStartOwnerActionDate(lastStartOwnerActionDate_);
					custFollowDto.setLastEndOwnerActionDate(lastEndOwnerActionDate_);		
					//排序
					if(StringUtils.isNotBlank(custFollowDto.getOrderKey())){
						String orderKey = custFollowDto.getOrderKey()+",TRCI.IS_MAJOR DESC,TRCI.INPUT_DATE ASC,CONVERT(TRCI.NAME USING GBK)";
						custFollowDto.setOrderKey(orderKey);
					}else {
						custFollowDto.setOrderKey("TRCI.IS_MAJOR DESC,TRCI.NEXTFOLLOW_DATE ASC,TRCI.ACTION_DATE ASC,TRCI.INPUT_DATE ASC,CONVERT(TRCI.NAME USING GBK)");	 
					}
				}else if(FollowCustEnum.CUST_CONTACT_IMPORT.getState().equals(
						custFollowDto.getFollowCustCation())){ // 重点客户			
					custFollowDto.setIsMajor("1");	
					//排序
					if(StringUtils.isNotBlank(custFollowDto.getOrderKey())){
						String orderKey = custFollowDto.getOrderKey()+",TRCI.IS_MAJOR DESC,TRCI.INPUT_DATE ASC,CONVERT(TRCI.NAME USING GBK)";
						custFollowDto.setOrderKey(orderKey);
					}else {
						custFollowDto.setOrderKey("TRCI.IS_MAJOR DESC,TRCI.NEXTFOLLOW_DATE ASC,TRCI.ACTION_DATE ASC,TRCI.INPUT_DATE ASC,CONVERT(TRCI.NAME USING GBK)");	 
					}
				}else{		
					//排序
					if(StringUtils.isNotBlank(custFollowDto.getOrderKey())){
						String orderKey = custFollowDto.getOrderKey()+",TRCI.IS_MAJOR DESC,TRCI.INPUT_DATE ASC,CONVERT(TRCI.NAME USING GBK)";
						custFollowDto.setOrderKey(orderKey);
					}else {
						custFollowDto.setOrderKey("TRCI.IS_MAJOR DESC,TRCI.NEXTFOLLOW_DATE ASC,TRCI.ACTION_DATE ASC,TRCI.INPUT_DATE ASC,CONVERT(TRCI.NAME USING GBK)");	 
					}
				}		
			} catch (Exception e) {
				throw new SysRunException(e);
			}
			}
	
		/**
		 * 获取第一天
		 * 
		 * @param type
		 *            1-当天 2-本周 3-本月 4-半年
		 * @return
		 * @create 2015年12月14日 下午3:48:05 lixing
		 * @history
		 */
		public String getStartDateStr(Integer type) {
			String str = "";
			if (type == 1) {
				str = DateUtil.formatDate(new Date(), DateUtil.DATE_DAY);
			} else if (type == 2) {
				str = DateUtil.getWeekFirstDay(new Date());
			} else if (type == 3) {
				str = DateUtil.getMonthFirstDay(new Date());
			} else if (type == 4) {
				str = DateUtil.formatDate(DateUtil.getAddDate(new Date(), -180), DateUtil.DATE_DAY);
			}
			return str;
		}

		public List<String> getLabelNameList(List<String> codes,String orgId){
			List<String> list = new ArrayList<String>();
			List<OptionBean> getSubOptionList = cachedService.getOptionList(AppConstant.SALES_TYPE_TEN,orgId);
			Map<String, String> changeMap = cachedService.changeOptionListToMap(getSubOptionList);
			for(String code : codes){
				list.add(changeMap.get(code));
			}
			return list;
		}
		/**
		 * 获取最后一天
		 * 
		 * @param type
		 *            1-当天 2-本周 3-本月 4-半年
		 * @return
		 * @create 2015年12月14日 下午3:48:05 lixing
		 * @history
		 */
		public String getEndDateStr(Integer type) {
			String str = "";
			if (type == 1) {
				str = DateUtil.formatDate(new Date(), DateUtil.DATE_DAY);
			} else if (type == 2) {
				str = DateUtil.getWeekLastDay(new Date());
			} else if (type == 3) {
				str = DateUtil.getMonthLastDay(new Date());
			} else if (type == 4) {
				str = DateUtil.formatDate(DateUtil.getAddDate(new Date(), 180), DateUtil.DATE_DAY);
			}
			return str;
		}
	
	/**
	 * 设置是否开启用*替换电话号码中间四位
	 */
	private void setIsReplaceWord(HttpServletRequest request,ShiroUser user) {
		// 查询缓存
		List<DataDictionaryBean> dataMap=cachedService.getDirList(AppConstant.DATA24, user.getOrgId());
		Integer idReplaceWord = 0;
		if (!dataMap.isEmpty() && dataMap.get(0) != null && !cachedService.judgeHideWhiteList(user.getOrgId(), user.getAccount())) {
			idReplaceWord = new Integer(dataMap.get(0).getDictionaryValue());
		}
		request.setAttribute("idReplaceWord", idReplaceWord);
	}
	
	/** 
	 * 缓存读取签约是否与合同无关 0-需添加合同 1-无需添加合同
	 * @param request 
	 * @create  2016年8月10日 下午2:59:09 lixing
	 * @history  
	 */
	public void getSignSetting(HttpServletRequest request){
		ShiroUser user = ShiroUtil.getShiroUser();
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_40039, user.getOrgId());
		Integer signSetting = 0;
		if(!list.isEmpty() && list.get(0) != null){
			signSetting = new Integer(list.get(0).getDictionaryValue());
		}
		request.setAttribute("signSetting", signSetting);
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
		for(String[]strs : SearchListShowCodeDto.modult_1_List){
			if(("2".equals(strs[2]) || isState.equals(strs[2])) && map.get(strs[0]) == null){
				rList.add(Integer.parseInt(strs[1]) + 2); // 存储需要隐藏的排序值 . 因为第一列是复选框、第二列是操作列，所以加了2
			}
		}		
		return rList;
	}
	
	//  跟进记录页面需隐藏字段列
	public List<Integer>getHideSort2ListCode(String module,String orgId,String isState){
		List<CustSearchSet> list = cachedService.getCustSearchSetList(module,orgId);
		Map<String,String>map = new HashMap<String, String>();
		for (CustSearchSet filed : list) {
			if (filed.getIsShow() == 1) {
				map.put(filed.getDevelopCode(), filed.getDevelopCode());
			}
		}
		List<Integer>rList = new ArrayList<Integer>();		
		for(String[]strs : SearchListShowCodeDto.modult_2_List){
			if(("2".equals(strs[2]) || isState.equals(strs[2])) && map.get(strs[0]) == null){
				rList.add(Integer.parseInt(strs[1]) + 1); // 存储需要隐藏的排序值 // 因为第一列是操作列，所以加了1
			}
		}		
		return rList;
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
	
	/***
	 * 获取是否显示 跟进警报
	 */
	@RequestMapping("/getFlagInfo")
	@ResponseBody
	public String getFlagInfo(HttpServletRequest request){
		return getFollowExpire();
	}
	@InitBinder
    public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, (PropertyEditor) new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }
	// 获取项目地址
	private String getProgetUtil(HttpServletRequest request) {
		if (80 == request.getServerPort()) {
			return request.getScheme() + "://" + request.getServerName() + request.getContextPath();
		} else {
			return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		}
	}
	
	// 返回跟进警报相关设置值
	private String getFollowExpire(){
		String flg = "0";
		try{				
			ShiroUser user = ShiroUtil.getShiroUser();
			// 查询 跟进警报提醒是否开启 
			List<DataDictionaryBean> data1=cachedService.getDirList(AppConstant.DATA15, user.getOrgId());	
			List<DataDictionaryBean> data2=cachedService.getDirList(AppConstant.DATA_FOLLOW_EXPIRE, user.getOrgId());	
			if(!data1.isEmpty() && "1".equals(data1.get(0).getDictionaryValue()) && "1".equals(data2.get(0).getIsOpen())){
				flg = data2.get(0).getDictionaryValue();
			}		
		}catch(Exception e){
			throw new SysRunException(e);				
		}
		return flg;
	}
	
		// 返回跟进警报消息提前时间设置值
		private String getFollowExpire1(){
			String flg = "0";
			try{				
				ShiroUser user = ShiroUtil.getShiroUser();		
				List<DataDictionaryBean> data2=cachedService.getDirList(AppConstant.DATA_20013, user.getOrgId());	
				if(StringUtils.isNotBlank(data2.get(0).getDictionaryValue())){
					flg = data2.get(0).getDictionaryValue();
				}		
			}catch(Exception e){
				throw new SysRunException(e);				
			}
			return flg;
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
		
		/** 
		 * 从缓存读取管理员是否可以对下属成员的客户进行“签约”、“取消签约”操作的设置
		 * @param request 
		 * @create  2016年12月15日 下午2:11:09 lixing
		 * @history  
		 */
		public void setAdminSignAuth(HttpServletRequest request){
			ShiroUser user = ShiroUtil.getShiroUser();
			List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_40041, user.getOrgId());
			Integer adminSignAuth = 0;
			if (!list.isEmpty() && list.get(0) != null){
				adminSignAuth = new Integer(list.get(0).getDictionaryValue());
			}
			request.setAttribute("adminSignAuth", adminSignAuth);
		}
		public static void main(String[] args) throws Exception {
			String encode = "eyJvcmdJZCI6Imh6aGg1IiwiaW5wdXROYW1lIjoi5p2O5YekIiwiaW5wdXRBY2MiOiJoemhoNTAwMiIsIm9yZ05hbWUiOiIiLCJjdXN0SWQiOiI2YmMzZGQyMzQ0ZGM0ZDNjOWRlYTBlNWQ3MWU0YTlkYSIsImN1c3ROYW1lIjoi6auY6ZizIiwiY3VzdFN0YXRlIjoiMyIsImN1c3RUeXBlIjoiMiIsInNhbGVQcm9jZXNzSWQiOiJiOWNjYzAyZGVhMGY0ODhmYThiNWViNjQ2OGY1YmM4NSIsInNhbGVQcm9jZXNzTmFtZSI6IiJ9";
			System.out.println(new String(FwCodeUtils.base64Decode(encode),"utf-8"));
		}
}
