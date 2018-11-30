package com.qftx.tsm.cust.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.dto.BaseSend;
import com.qftx.base.auth.dto.MessageObj;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.bean.TsmTeamGroupMemberBean;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.team.service.TsmTeamGroupMemberService;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.*;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.callrecord.dto.FollowCallQueryDto;
import com.qftx.tsm.callrecord.dto.TsmRecordCallBean;
import com.qftx.tsm.callrecord.util.CallRecordGetUtil;
import com.qftx.tsm.cust.bean.*;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.cust.dto.*;
import com.qftx.tsm.cust.service.*;
import com.qftx.tsm.follow.bean.CustFollowBean;
import com.qftx.tsm.follow.dto.CustFollowCallDto;
import com.qftx.tsm.follow.dto.PageFormDto;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.log.service.LogContactDayDataService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.log.util.ContactUtil;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dto.DictionProcessJsonDto;
import com.qftx.tsm.option.service.OptionService;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayBean;
import com.qftx.tsm.plan.user.day.service.PlanUserDayService;
import com.qftx.tsm.report.service.RankingReportService;
import com.qftx.tsm.sms.bean.TsmMessageSend;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.bean.Product;
import com.qftx.tsm.sys.dto.HighSearchDto;
import com.qftx.tsm.sys.dto.SearchListShowCodeDto;
import com.qftx.tsm.sys.enums.SysEnum;
import com.qftx.tsm.tao.service.CustGuideService;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.beans.PropertyEditor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/res/cust")
public class ResCustInfoAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ResCustInfoAction.class);
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private ComResourceGroupService comReesourceGroupService;
	@Autowired
	private CustOptorService custOptorService;
	@Autowired
	private ResCustInfoDetailService resCustInfoDetailService;
	@Autowired
	private ResCustInfoLogService resCustInfoLogService;
	@Autowired
	private ComResourceService comResourceService;
	@Resource
	private LogCustInfoService logCustInfoService;
	@Resource
	private TsmTeamGroupService tsmTeamGroupService;
	@Resource
	TsmTeamGroupMemberService tsmTeamGroupMemberService;
	@Resource
	private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Autowired
	private ResCustEventService resCustEventService;
	@Autowired
	private CachedService cachedservice;
	@Autowired
	private OptionService optionService;
	@Autowired
	private LogUserOperateService logUserOperateService;
	@Autowired
	private TsmMessageService tsmMessageService;
	@Autowired
	private LogContactDayDataService logContactDayDataService;
	@Autowired
	private PlanUserDayService planUserDayService;
	@Resource
	private CustGuideService custGuideService;
	@Resource
	private RankingReportService rankingReportService;
	@Resource
	private ResCustInfoMapper resCustInfoMapper;
	
	@RequestMapping("/myCustsList")
	public String toMyCusts(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
		request.setAttribute("options", options);
		commonOwnerAuth(request);
		return "res/myCusts";
	}

	@RequestMapping("/myCommonCusts")
	public String myCommonCusts(HttpServletRequest request){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			// 分组
			List<ResourceGroupBean> groupList = cachedService.getResGroupList(user.getOrgId());
			// 从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			//从缓存读取客户类型列表
			List<OptionBean> typeOptions = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			// 设置自定义字段查询
			setDefinedQueryFileds(request, user.getOrgId(), user.getIsState());
			request.setAttribute("groupList", groupList);
			request.setAttribute("options", options);
			request.setAttribute("typeOptions", typeOptions);
			request.setAttribute("shiroUser", user);
			setCustomFiled(user, request);
			setIsReplaceWord(request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "res/myCommonCusts";
	}
	
	@RequestMapping("/myCommonCustsData")
	@ResponseBody
	public Map<String, Object> myCommonCustsData(HttpServletRequest request, ResCustInfoDto resCustInfoDto){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			resCustInfoDto.setState(user.getIsState());
			resCustInfoDto.setOrgId(user.getOrgId());
			// 设置共有者是自己
			resCustInfoDto.setCommonAcc(user.getAccount());
			// 默认选中全部标签
			if (StringUtils.isBlank(resCustInfoDto.getNoteType())) resCustInfoDto.setNoteType("1");
			// 处理最近联系时间
			if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5
					&& resCustInfoDto.getlDateType() != 6) {
				resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
				resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 下次联系时间
			if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5
					&& resCustInfoDto.getoDateType() != 6) {
				if(resCustInfoDto.getoDateType() == 4){
					resCustInfoDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
					resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
				}else{
					resCustInfoDto.setPstartDate(getStartDateStr(resCustInfoDto.getoDateType()));
					resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
				}
			}
			// 签约时间
			if (resCustInfoDto.getnDateType() != null && resCustInfoDto.getnDateType() != 0 && resCustInfoDto.getnDateType() != 5) {
				resCustInfoDto.setStartDate(getStartDateStr(resCustInfoDto.getnDateType()));
				resCustInfoDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理所有者查询
			if(StringUtils.isNotBlank(resCustInfoDto.getCommonAccsStr())){
				resCustInfoDto.setCommonAccs(Arrays.asList(resCustInfoDto.getCommonAccsStr().split(",")));
			}
			// 排序
			if(StringUtils.isNotBlank(resCustInfoDto.getOrderKey())){
				String orderKey = resCustInfoDto.getOrderKey()+",RES_CUST_ID DESC";
				resCustInfoDto.setOrderKey(orderKey);
			}else {
				resCustInfoDto.setOrderKey("NEXTFOLLOW_DATE DESC,RES_CUST_ID DESC");
			}
			// 处理标签
			if (StringUtils.isNotBlank(resCustInfoDto.getAllLabels())) {
				resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
			}
			// 查询数据
			List<ResCustInfoDto> list = resCustInfoService.getMyCommonCustListPage(resCustInfoDto);
			// 组装数据
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
			// 从缓存读取客户类型列表
			List<OptionBean> typeOptions = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			Map<String, String> typeNameMap = cachedService.changeOptionListToMap(typeOptions);
			
			for(ResCustInfoDto rcid : list){
				rcid.setCustTypeName(StringUtils.isNotBlank(rcid.getCustTypeId()) ? typeNameMap.get(rcid.getCustTypeId()) : "");//客户类型
				rcid.setSaleProcessName(StringUtils.isNotBlank(rcid.getLastOptionId()) ? saleProcMap.get(rcid.getLastOptionId()) : "");//销售进程
				rcid.setOwnerName(StringUtils.isNotBlank(rcid.getOwnerAcc()) ? nameMap.get(rcid.getOwnerAcc()) : "");//所有者
				rcid.setGroupName(StringUtils.isNotBlank(rcid.getResGroupId()) ? groupMap.get(rcid.getResGroupId()) : "");//资源分组
				rcid.setStartActionDate(rcid.getActionDate() != null ? DateUtil.formatDate(rcid.getActionDate(), DateUtil.Data_ALL) : "");//最近联系时间 yyyy-MM-dd HH:mm:ss
				rcid.setEndActionDate(rcid.getActionDate() != null ? DateUtil.formatDate(rcid.getActionDate(), DateUtil.DATE_DAY) : "");//最近联系时间 yyyy-MM-dd
				rcid.setPstartDate(rcid.getNextFollowDate() != null ? DateUtil.formatDate(rcid.getNextFollowDate(), DateUtil.Data_ALL) : "");//下次联系时间 yyyy-MM-dd HH:mm:ss
				rcid.setPendDate(rcid.getNextFollowDate() != null ? DateUtil.formatDate(rcid.getNextFollowDate(), DateUtil.DATE_DAY) : "");//下次联系时间 yyyy-MM-dd
				rcid.setStartDate(rcid.getSignDate() != null ? DateUtil.formatDate(rcid.getSignDate(), DateUtil.DATE_DAY) : "");//签约时间 yyyy-MM-dd
				if(rcid.getStatus().intValue() == 6){
					rcid.setCustType("3");
				}else{
					rcid.setCustType("2");
				}
			}
			
			map.put("item", resCustInfoDto);
			map.put("list", list);
			map.put("serverDay", user.getServerDay());
			map.put("loginAcc", user.getAccount());
			map.put("fields", getIsQueryList(user.getOrgId(), user.getIsState()));
			map.put("signSetting", getSignSetting());
			map.put("isState", user.getIsState());
			map.put("commonOnwerSign", getCommonOwnerSignAuth(user.getOrgId()));
			map.put("commonOnwerGiveUp", getCommonOwnerGiveUpAuth(user.getOrgId()));
		} catch (Exception e) {
			logger.error("共有客户加载数据失败！",e);
		}
		return map;
	}
	
	/**
	 * 我的客户 - 资源
	 *
	 * @param request
	 * @param resCustInfoDto
	 * @return
	 * @create 2015年11月13日 上午10:36:10 lixing
	 * @history
	 */
	@RequestMapping("/myRests")
	public String myRests(HttpServletRequest request, ResCustInfoDto resCustInfoDto) {
		try {
			boolean planFlag = processPlanPage(request);
			
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("groupList", cachedService.getResGroupList(user.getOrgId()));
			request.setAttribute("shiroUser", user);
			List<CustSearchSet> tlist = getIsTextQueryList(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_4.getState());
			request.setAttribute("tList", tlist); // 文本类型查询字段列表
			Map<String,String>map = getIsDefinedNameList(user.getOrgId(),user.getIsState());
			request.setAttribute("definedNameMap", map);
			// 用于筛选项排序
			Map<String,Integer> mutilSearchCodeSortMap = getUnTestSearchSort(SysEnum.SEARCH_SET_MODULE_4.getState(),user.getOrgId(),user.getIssys());
			request.setAttribute("mutilSearchCodeSortMap",mutilSearchCodeSortMap);
			List<HighSearchDto> dtos = cachedService.getHighSearch(SysEnum.SEARCH_SET_MODULE_4.getState(), user.getOrgId(), user.getIssys());
			List<HighSearchDto> definedDtos = new ArrayList<HighSearchDto>();
			for(HighSearchDto dto : dtos){
				if(dto.getIsFiledSet() ==1 && dto.getIsQuery() == 1 ){
					definedDtos.add(dto);
				}
			}			
			request.setAttribute("definedSearchCodes",definedDtos); // 自定义非文本项查询集合
			// 需隐藏列的序号
			List<Integer> sorts = getHideSortListCode(SysEnum.SEARCH_SET_MODULE_4.getState(),user.getOrgId(),user.getIsState().toString(),SearchListShowCodeDto.modult_4_List,2);
			request.setAttribute("sorts", sorts);
			setIsReplaceWord(request);
			List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
			request.setAttribute("companyTrades", trades);
			
			expireSetting(user, request);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "res/myRests";
	}

	public Map<String, Object> expireData(String orgId){
		Map<String, Object> expireMap = new HashMap<String, Object>();
		List<DataDictionaryBean> setOns = cachedService.getDirList(AppConstant.DATA13, orgId);
		List<DataDictionaryBean> checkOns = cachedService.getDirList(AppConstant.DATA_50024, orgId);
		List<DataDictionaryBean> date1Ons = cachedService.getDirList(AppConstant.DATA_RECYLE_EXPIRE, orgId);
		List<DataDictionaryBean> date2Ons = cachedService.getDirList(AppConstant.DATA_50025, orgId);
		List<DataDictionaryBean> placeOns = cachedService.getDirList(AppConstant.DATA_50026, orgId);
		Integer day = 0;
		if(setOns!=null && setOns.size() > 0 && checkOns!=null && checkOns.size() > 0){
			if ("1".equals(setOns.get(0).getDictionaryValue()) && "1".equals(checkOns.get(0).getDictionaryValue()) && date1Ons != null && date1Ons.size() != 0) {
				expireMap.put("expireType", "1");
				if (StringUtils.isNotBlank(date1Ons.get(0).getDictionaryValue())) {
					day = new Integer(date1Ons.get(0).getDictionaryValue());
				}
			}else if ("1".equals(setOns.get(0).getDictionaryValue()) && "2".equals(checkOns.get(0).getDictionaryValue()) && date2Ons != null && date2Ons.size() != 0 && placeOns != null && placeOns.size() != 0) {
				expireMap.put("expireType", "2");
				if (StringUtils.isNotBlank(date2Ons.get(0).getDictionaryValue()) &&StringUtils.isNotBlank(placeOns.get(0).getDictionaryValue())) {
					day = new Integer(date2Ons.get(0).getDictionaryValue());
				}
			}
		}
		expireMap.put("expireDay", day);
		return expireMap;
	}
	
	/**到期回收设置**/
	public void expireSetting(ShiroUser user,HttpServletRequest request){
		Integer recyle_setting = 0,will_recyle=0;
		List<DataDictionaryBean> setOns = cachedService.getDirList(AppConstant.DATA13, user.getOrgId());
		List<DataDictionaryBean> checkOns = cachedService.getDirList(AppConstant.DATA_50024, user.getOrgId());
		List<DataDictionaryBean> date1Ons = cachedService.getDirList(AppConstant.DATA_RECYLE_EXPIRE, user.getOrgId());
		List<DataDictionaryBean> date2Ons = cachedService.getDirList(AppConstant.DATA_50025, user.getOrgId());
		List<DataDictionaryBean> placeOns = cachedService.getDirList(AppConstant.DATA_50026, user.getOrgId());
		Map<String, Object> expireMap = new HashMap<String, Object>();
		expireMap.put("orgId", user.getOrgId());
		Integer day = 0;
		if(setOns!=null && setOns.size() > 0 && checkOns!=null && checkOns.size() > 0){
			if ("1".equals(setOns.get(0).getDictionaryValue()) && "1".equals(checkOns.get(0).getDictionaryValue()) && date1Ons != null && date1Ons.size() != 0) {
				recyle_setting = 1;
				expireMap.put("expireType", "1");
				if (user.getIssys() != null && user.getIssys() == 1) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					if(!accs.contains(user.getAccount())){
						accs.add(user.getAccount());
					}
					expireMap.put("ownerAccs", accs);
				}else{
					expireMap.put("ownerAcc", user.getAccount());
				}
				if (StringUtils.isNotBlank(date1Ons.get(0).getDictionaryValue())) {
					day = new Integer(date1Ons.get(0).getDictionaryValue());
					expireMap.put("recyleDay", day-3);
					will_recyle = resCustInfoService.getResExpireCount(expireMap);
				}
			}else if ("1".equals(setOns.get(0).getDictionaryValue()) && "2".equals(checkOns.get(0).getDictionaryValue()) && date2Ons != null && date2Ons.size() != 0 && placeOns != null && placeOns.size() != 0) {
				recyle_setting = 1;
				expireMap.put("expireType", "2");
				if (user.getIssys() != null && user.getIssys() == 1) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					if(!accs.contains(user.getAccount())){
						accs.add(user.getAccount());
					}
					expireMap.put("ownerAccs", accs);
				}else{
					expireMap.put("ownerAcc", user.getAccount());
				}
				if (StringUtils.isNotBlank(date2Ons.get(0).getDictionaryValue()) &&StringUtils.isNotBlank(placeOns.get(0).getDictionaryValue())) {
					day = new Integer(date2Ons.get(0).getDictionaryValue());
					expireMap.put("recyleDay", day-3);
					will_recyle = resCustInfoService.getResExpireCount(expireMap);
				}
			}
		}
		request.setAttribute("recyle_setting", recyle_setting);
		request.setAttribute("will_recyle", will_recyle);
	}
	
	
	
	@RequestMapping("/myRestsData")
	@ResponseBody
	public Map<String, Object> myRestsData(HttpServletRequest request, ResCustInfoDto resCustInfoDto){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			boolean planFlag = processPlanData(request,resCustInfoDto,user);
			
			resCustInfoDto.setState(user.getIsState());
			resCustInfoDto.setOrgId(user.getOrgId());
			if (!planFlag && user.getIssys() != null && user.getIssys() == 1) {
				resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
				resCustInfoDto.setAdminAcc(user.getAccount());
				if (resCustInfoDto.getOsType().equals("1")  && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					if(!accs.contains(user.getAccount())){
						accs.add(user.getAccount());
					}
					resCustInfoDto.setOwnerAccs(accs);
				} else if (resCustInfoDto.getOsType().equals("2") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
					resCustInfoDto.setOwnerAcc(user.getAccount());
				} else {
					// 处理拥有者
					if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
						resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
					} else {
						resCustInfoDto.setOwnerAccsStr(user.getAccount());
						resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
				//从缓存读取管理员是否可以对下属成员的客户进行“签约”、“取消签约”操作的设置
				map.put("adminSignAuth", getAdminSignAuth());
			} else {
				resCustInfoDto.setOwnerAcc(user.getAccount());
			}
			// 默认选中全部标签
			if (StringUtils.isBlank(resCustInfoDto.getNoteType())) {
				resCustInfoDto.setNoteType("1");
			}
			// 处理添加/分配时间
			if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
				resCustInfoDto.setPstartDate(getStartDateStr(resCustInfoDto.getoDateType()));
				resCustInfoDto.setPendDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理最近联系时间
			if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5) {
				resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
				resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 下次联系时间
			if (resCustInfoDto.getnDateType() != null && resCustInfoDto.getnDateType() != 0 && resCustInfoDto.getnDateType() != 5) {
				if(resCustInfoDto.getnDateType() == 4){
					resCustInfoDto.setStartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
					resCustInfoDto.setEndDate(getEndDateStr(resCustInfoDto.getnDateType()));
				}else{
					resCustInfoDto.setStartDate(getStartDateStr(resCustInfoDto.getnDateType()));
					resCustInfoDto.setEndDate(getEndDateStr(resCustInfoDto.getnDateType()));
				}
			}
			//导入部门
			if(StringUtils.isNotBlank(resCustInfoDto.getImportDeptIdsStr())) resCustInfoDto.setImportDeptIds(Arrays.asList(resCustInfoDto.getImportDeptIdsStr().split(",")));
			
			//处理三天内回收
			Map<String, Object> expireMap = expireData(user.getOrgId());
			
			if(StringUtils.isNotBlank(resCustInfoDto.getNoteType()) && "8".equals(resCustInfoDto.getNoteType())){
				if(expireMap.containsKey("expireType")){
					resCustInfoDto.setExpireType(expireMap.get("expireType").toString());
					resCustInfoDto.setRecyleDay(Integer.valueOf(expireMap.get("expireDay").toString())-3);
				}
			}
			
			//排序
			if(StringUtils.isNotBlank(resCustInfoDto.getOrderKey())){
				String orderKey = resCustInfoDto.getOrderKey()+",IS_MAJOR DESC,RES_CUST_ID DESC";
				resCustInfoDto.setOrderKey(orderKey);
			}else {
				resCustInfoDto.setOrderKey("OWNER_START_DATE DESC,IS_MAJOR DESC,RES_CUST_ID DESC");
			}
			
			if(resCustInfoDto.getInputStatusStr()!=null && !"".equals(resCustInfoDto.getInputStatusStr())){
		     	 String[] inputStatus_list = resCustInfoDto.getInputStatusStr().split(",");
		     	List<String> inputStatuList = Arrays.asList(inputStatus_list);
		     	resCustInfoDto.setInputStatusList(inputStatuList);
			}else{
				String[] inputStatus_list=request.getParameterValues("inputStatus");
				if(inputStatus_list!=null && inputStatus_list.length>0){
			     	List<String> inputStatuList = Arrays.asList(inputStatus_list);
			     	resCustInfoDto.setInputStatusList(inputStatuList);
				}
			}
			
			//查出多选项查询字段
			List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_4.getState());
			List<ResCustInfoDto> rests = resCustInfoService.getMyResCustListPage(resCustInfoDto,multiSearchList);
			//多选项显示
			if(rests.size() > 0){
				List<String> multiShowList = cachedService.getMultiSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_4.getState());
				List<String> singleShowList = cachedService.getSingleSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_4.getState());
				resCustInfoService.multiDefinedShowChange(rests,multiShowList,singleShowList,user.getOrgId());
			}
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
			Map<String, String> deptName = cachedService.getOrgGroupNameMap(user.getOrgId());
			Map<Integer, String> provinceMap = new HashMap<Integer, String>();
			Map<Integer, String> cityMap = new HashMap<Integer, String>();
			Map<Integer, String> countyMap = new HashMap<Integer, String>();
			Map<String, String> tradeMap = new HashMap<String, String>();
			if(user.getIsState() == 1){
				provinceMap = cachedservice.getAreaMap(CachedNames.PROVINCE);
				cityMap = cachedservice.getAreaMap(CachedNames.CITY);
				countyMap = cachedservice.getAreaMap(CachedNames.COUNTY);
				List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
				tradeMap = cachedservice.changeOptionListToMap(trades);
			}
			Date now_time = new Date();
			for (ResCustInfoDto rcid : rests) {
				if (rcid.getPlanDate() != null) {
					Date planDate = DateUtil.parseDate(DateUtil.formatDate(rcid.getPlanDate(), DateUtil.DATE_DAY));
					Date curDate = DateUtil.parseDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
					if (planDate.compareTo(curDate) >= 0) {
						// rcid.setPlanDate(null);
						rcid.setPstartDate(DateUtil.formatDate(rcid.getPlanDate(), DateUtil.DATE_DAY));
					}
				}
				rcid.setOwnerName(StringUtils.isNotBlank(rcid.getOwnerAcc()) ? nameMap.get(rcid.getOwnerAcc()) : "");
				rcid.setGroupName(StringUtils.isNotBlank(rcid.getResGroupId()) ? groupMap.get(rcid.getResGroupId()) : "");
				// 此处处理前台展示时间字段，因使用fmt标签转换耗费时间，统一在后台转为字符串
				rcid.setStartActionDate(rcid.getActionDate() != null ? DateUtil.formatDate(rcid.getActionDate(), DateUtil.Data_ALL) : "");
				rcid.setEndActionDate(rcid.getActionDate() != null ? DateUtil.formatDate(rcid.getActionDate(), DateUtil.DATE_DAY) : "");
				rcid.setStartDate(rcid.getOwnerStartDate() != null ? DateUtil.formatDate(rcid.getOwnerStartDate(), DateUtil.Data_ALL) : "");
				rcid.setEndDate(rcid.getOwnerStartDate() != null ? DateUtil.formatDate(rcid.getOwnerStartDate(), DateUtil.DATE_DAY) : "");
				rcid.setStartNextContactDate(rcid.getNextFollowDate() != null ? DateUtil.formatDate(rcid.getNextFollowDate(), DateUtil.Data_ALL) : "");
				rcid.setEndNextContactDate(rcid.getNextFollowDate() != null ? DateUtil.formatDate(rcid.getNextFollowDate(), DateUtil.DATE_DAY) : "");
				rcid.setShowdefined16(rcid.getDefined16() !=null?DateUtil.formatDate(rcid.getDefined16(), DateUtil.DATE_DAY): "");
				rcid.setShowdefined17(rcid.getDefined17() !=null?DateUtil.formatDate(rcid.getDefined17(), DateUtil.DATE_DAY): "");
				rcid.setShowdefined18(rcid.getDefined18() !=null?DateUtil.formatDate(rcid.getDefined18(), DateUtil.DATE_DAY): "");
				rcid.setImportDeptName(StringUtils.isNotBlank(rcid.getImportDeptId()) ? deptName.get(rcid.getImportDeptId()) : "");
				if(rcid.getProvinceId() != null && user.getIsState() == 1){
					String area = provinceMap.get(rcid.getProvinceId());
					if(rcid.getCityId() != null) area+="-"+cityMap.get(rcid.getCityId());
					if(rcid.getCountyId() != null) area+="-"+countyMap.get(rcid.getCountyId());
					rcid.setArea(area);
				}
				rcid.setCompanyTrade(rcid.getCompanyTrade() != null ? tradeMap.get(rcid.getCompanyTrade()) : "");
				
				if(expireMap.containsKey("expireType")){
					int day = Integer.valueOf(expireMap.get("expireDay").toString());
					if(expireMap.get("expireType").toString().equals("1")){
						int day_diff = DateUtil.datediff(now_time, rcid.getOwnerStartDate());
						int ep = day - day_diff ;
						if(ep <= 3 && ep > 0){
							rcid.setDays(ep);
						}else if(ep <=0){
							rcid.setDays(1);
						}
					}else{
						Date d;
						if(rcid.getActionDate() != null){
							d = rcid.getActionDate();
						}else{
							d = rcid.getOwnerStartDate();
						}
						int day_diff = DateUtil.datediff(now_time, d);
						int ep = day - day_diff;
						if(ep <= 3 && ep > 0){
							rcid.setDays(ep);
						}else if(ep <=0){
							rcid.setDays(1);
						}
					}
				}
				
			}
			if(expireMap.containsKey("expireType")){
				map.put("showExpireDay",1);
			}
			map.put("item", resCustInfoDto);
			map.put("list", rests);
			map.put("serverDay", user.getServerDay());
			map.put("loginAcc", user.getAccount());
			map.put("fields", getIsQueryList(user.getOrgId(), user.getIsState()));
			map.put("signSetting", getSignSetting());
			map.put("isState", user.getIsState());
		} catch (Exception e) {
			logger.error("我的客户-资源异步加载数据异常！",e);
		}
		return map;
	}
	
	@RequestMapping("/getMyCustNums")
	@ResponseBody
	public String getMyCustNums(HttpServletResponse response, String groupId, String type) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			ResCustInfoDto resCustInfoDto = new ResCustInfoDto();
			resCustInfoDto.setOrgId(user.getOrgId());
			if (user.getIssys() != null && user.getIssys() == 1 && StringUtils.isNotBlank(type) && type.equals("1")) {
				resCustInfoDto.setAdminAcc(user.getAccount());
			} else {
				resCustInfoDto.setOwnerAcc(user.getAccount());
			}
			if (StringUtils.isNotBlank(groupId)) {
				resCustInfoDto.setGroupId(groupId);
			}
			Map<String, Integer> numMap = resCustInfoService.getCustNums(resCustInfoDto);
			return JSONObject.fromObject(numMap).toString();
		} catch (Exception e) {
			logger.error("获取客户数量失败");
			return "";
		}
	}

	/**
	 * 我的客户-意向客户
	 *
	 * @param request
	 * @return
	 * @create 2015年11月13日 下午6:43:00 lixing
	 * @history
	 */
	@RequestMapping("/myIntCusts")
	public String myCusts(HttpServletRequest request) {
		try {
			boolean planFlag = processPlanPage(request);
			
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("shiroUser", user);
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			request.setAttribute("options", options);
			List<OptionBean> typeOptions = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			request.setAttribute("typeOptions", typeOptions);

			request.setAttribute("groupList", cachedService.getResGroupList(user.getOrgId()));
			List<CustSearchSet> tlist = getIsTextQueryList(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_5.getState());
			request.setAttribute("tList", tlist); // 文本类型查询字段列表
			Map<String,String>map = getIsDefinedNameList(user.getOrgId(),user.getIsState());
			request.setAttribute("definedNameMap", map);
			// 用于筛选项排序
			Map<String,Integer> mutilSearchCodeSortMap = getUnTestSearchSort(SysEnum.SEARCH_SET_MODULE_5.getState(),user.getOrgId(),user.getIssys());
			request.setAttribute("mutilSearchCodeSortMap",mutilSearchCodeSortMap);
			List<HighSearchDto> dtos = cachedService.getHighSearch(SysEnum.SEARCH_SET_MODULE_5.getState(), user.getOrgId(), user.getIssys());
			List<HighSearchDto> definedDtos = new ArrayList<HighSearchDto>();
			for(HighSearchDto dto : dtos){
				if(dto.getIsFiledSet() ==1 && dto.getIsQuery() == 1 ){
					definedDtos.add(dto);
				}
			}			
			request.setAttribute("definedSearchCodes",definedDtos); // 自定义非文本项查询集合
			String saleProcessId= request.getParameter("saleProcessId");
			request.setAttribute("saleProcessId", saleProcessId);
			// 需隐藏列的序号
			List<Integer> sorts = getHideSortListCode(SysEnum.SEARCH_SET_MODULE_5.getState(),user.getOrgId(),user.getIsState().toString(),SearchListShowCodeDto.modult_5_List,2);
			if(getCommonOwnerOpenState(user.getOrgId()) == 0){
				if(!sorts.contains(13)) sorts.add(13);
			}
			request.setAttribute("sorts", sorts);
			
			setIsReplaceWord(request);
			getSignSetting(request);
			commonOwnerAuth(request);
			
			List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
			request.setAttribute("companyTrades", trades);
			
			followExpireSetting(user, request);
			
		} catch (Exception e) {
			logger.error(e);
		}
			return "res/myIntCusts";
	}
	
	public void followExpireSetting(ShiroUser user,HttpServletRequest request){
		Integer recyle_setting = 0,will_recyle=0;
		List<DataDictionaryBean> followOns = cachedService.getDirList(AppConstant.DATA15, user.getOrgId());
		List<DataDictionaryBean> dictionOns = cachedService.getDirList(AppConstant.DATA23, user.getOrgId());
		List<DataDictionaryBean> dictionTwo = cachedService.getDirList(AppConstant.DATA_FOLLOW_EXPIRE, user.getOrgId());
		Map<String, Object> expireMap = new HashMap<String, Object>();
		expireMap.put("orgId", user.getOrgId());
		if(followOns != null && followOns.size() >0 && "1".equals(followOns.get(0).getDictionaryValue())){
			int followExpire = 0,signExpire=0;
			if(dictionOns != null && dictionOns.size() >0  &&  "1".equals(dictionOns.get(0).getIsOpen())){
				// 针对不同的销售进程设置时间，做判断，是否需要发送提醒	
				List<DictionProcessJsonDto> processJsonDtos = cachedService.getDicProcessList(user.getOrgId(),dictionOns.get(0).getDictionaryValue());				
				List<DictionProcessJsonDto> options = new ArrayList<DictionProcessJsonDto>();
				String epDay = dictionOns.get(0).getDictionaryValue();
				if(processJsonDtos != null && processJsonDtos.size() > 0){
					for(DictionProcessJsonDto pjd : processJsonDtos){
						if(StringUtils.isNotBlank(pjd.getOptionValue())){
							Integer intVal = Integer.valueOf(pjd.getOptionValue()) - 3;
							pjd.setOptionValue(intVal.toString());
							options.add(pjd);
						}else if(StringUtils.isNotBlank(epDay)){
							Integer intVal = Integer.valueOf(epDay) - 3;
							pjd.setOptionValue(intVal.toString());
							options.add(pjd);
						}
					}
					if(options.size() > 0){
						followExpire = 1;
//						expireMap.put("options", processJsonDtos);
//						if (user.getIssys() != null && user.getIssys() == 1) {
//							List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
//							if(!accs.contains(user.getAccount())){
//								accs.add(user.getAccount());
//							}
//							expireMap.put("ownerAccs", accs);
//						}else{
//							expireMap.put("ownerAcc", user.getAccount());
//						}
//						will_recyle+= resCustInfoService.getCustExpireCount(expireMap);
					}
				}
			}
			
			if(dictionTwo != null && dictionTwo.size() >0  &&  "1".equals(dictionTwo.get(0).getIsOpen()) && StringUtils.isNotBlank(dictionTwo.get(0).getDictionaryValue())){
				signExpire = 1;
//				Integer intVal = Integer.valueOf(dictionTwo.get(0).getDictionaryValue()) - 3;
//				expireMap.remove("options");
//				expireMap.put("epDay", intVal);
//				if (user.getIssys() != null && user.getIssys() == 1) {
//					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
//					if(!accs.contains(user.getAccount())){
//						accs.add(user.getAccount());
//					}
//					expireMap.put("ownerAccs", accs);
//				}else{
//					expireMap.put("ownerAcc", user.getAccount());
//				}
//				will_recyle+= resCustInfoService.getCustExpireCount(expireMap);
			}
			
			if(followExpire == 1 && signExpire == 0){
				recyle_setting = 1;
			}else if(followExpire == 0 && signExpire == 1){
				recyle_setting = 2;
			}else if(followExpire == 1 && signExpire == 1){
				recyle_setting = 3;
			}
			
		}
		request.setAttribute("recyle_setting", recyle_setting);
		request.setAttribute("will_recyle", will_recyle);
	}
	
	public Map<String, Object> followExpireData(String orgId){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<DataDictionaryBean> followOns = cachedService.getDirList(AppConstant.DATA15, orgId);
		List<DataDictionaryBean> dictionOns = cachedService.getDirList(AppConstant.DATA23, orgId);
		List<DataDictionaryBean> dictionTwo = cachedService.getDirList(AppConstant.DATA_FOLLOW_EXPIRE, orgId);
		if(followOns != null && followOns.size() >0 && "1".equals(followOns.get(0).getDictionaryValue())){
			int followExpire = 0,signExpire=0;
			if(dictionOns != null && dictionOns.size() >0  &&  "1".equals(dictionOns.get(0).getIsOpen())){
				String epDay = dictionOns.get(0).getDictionaryValue();
				// 针对不同的销售进程设置时间，做判断，是否需要发送提醒	
				List<DictionProcessJsonDto> processJsonDtos = cachedService.getDicProcessList(orgId,dictionOns.get(0).getDictionaryValue());				
				List<DictionProcessJsonDto> options = new ArrayList<DictionProcessJsonDto>();
				for(DictionProcessJsonDto pjd : processJsonDtos){
					if(StringUtils.isNotBlank(pjd.getOptionValue())){
						options.add(pjd);
					}else if(StringUtils.isNotBlank(epDay)){
						pjd.setOptionValue(epDay);
						options.add(pjd);
					}
				}
				if(options != null && options.size() > 0){
					followExpire = 1;
					dataMap.put("options", options);
				}
			}
			
			if(dictionTwo != null && dictionTwo.size() >0  &&  "1".equals(dictionTwo.get(0).getIsOpen()) && StringUtils.isNotBlank(dictionTwo.get(0).getDictionaryValue())){
				signExpire = 1;
				dataMap.put("epDay", Integer.valueOf(dictionTwo.get(0).getDictionaryValue()));
			}
			
			if(followExpire == 1 && signExpire == 0){
				dataMap.put("expireType", 1);
			}else if(followExpire == 0 && signExpire == 1){
				dataMap.put("expireType", 2);
			}else if(followExpire == 1 && signExpire == 1){
				dataMap.put("expireType", 3);
			}
			
		}
		return dataMap;
	}
	
	@RequestMapping("/myIntCustsData")
	@ResponseBody
	public Map<String, Object> myIntCustsData(HttpServletRequest request,ResCustInfoDto resCustInfoDto){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			boolean planFlag = processPlanData(request,resCustInfoDto,user);
			
			resCustInfoDto.setState(user.getIsState());
			resCustInfoDto.setOrgId(user.getOrgId());
			if (!planFlag && user.getIssys() != null && user.getIssys() == 1) {
				resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
				resCustInfoDto.setAdminAcc(user.getAccount());
				if (resCustInfoDto.getOsType().equals("1") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					if(!accs.contains(user.getAccount())){
						accs.add(user.getAccount());
					}
					resCustInfoDto.setOwnerAccs(accs);
				} else if (resCustInfoDto.getOsType().equals("2") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
					resCustInfoDto.setOwnerAcc(user.getAccount());
				} else {
					// 处理拥有者
					if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
						resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
					} else {
						resCustInfoDto.setOwnerAccsStr(user.getAccount());
						resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
				//从缓存读取管理员是否可以对下属成员的客户进行“签约”、“取消签约”操作的设置
				map.put("adminSignAuth", getAdminSignAuth());
			} else {
				resCustInfoDto.setOwnerAcc(user.getAccount());
			}
			if (StringUtils.isBlank(resCustInfoDto.getNoteType())) {
				resCustInfoDto.setNoteType("1");
			}
			// 淘到客户时间
			if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
				resCustInfoDto.setPstartDate(getStartDateStr(resCustInfoDto.getoDateType()));
				resCustInfoDto.setPendDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理最近联系时间
			if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5
					&& resCustInfoDto.getlDateType() != 6) {
				resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
				resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			if (resCustInfoDto.getnDateType() != null && resCustInfoDto.getnDateType() != 0 && resCustInfoDto.getnDateType() != 5
					&& resCustInfoDto.getnDateType() != 6) {
				if(resCustInfoDto.getnDateType() == 4){
					resCustInfoDto.setStartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
					resCustInfoDto.setEndDate(getEndDateStr(resCustInfoDto.getnDateType()));
				}else{
					resCustInfoDto.setStartDate(getStartDateStr(resCustInfoDto.getnDateType()));
					resCustInfoDto.setEndDate(getEndDateStr(resCustInfoDto.getnDateType()));
				}
			}

			// 处理标签
			if (StringUtils.isNotBlank(resCustInfoDto.getAllLabels())) {
				resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
			}

			if(getCommonOwnerOpenState(user.getOrgId()) == 1){//如果开启共有者  设置查询共有者
				resCustInfoDto.setCommonAcc(user.getAccount());
				if(StringUtils.isNotBlank(resCustInfoDto.getCommonAccsStr())){
					resCustInfoDto.setCommonAccs(Arrays.asList(resCustInfoDto.getCommonAccsStr().split(",")));
				}
			}
			
			Map<String, Object> expireMap = followExpireData(user.getOrgId());
			Map<String, Integer> expireProMap = new HashMap<String, Integer>();
			if(expireMap.containsKey("expireType")){
				map.put("showExpireDay",1);
				if(expireMap.containsKey("options")){
					List<DictionProcessJsonDto> expireOptions = (List<DictionProcessJsonDto>)expireMap.get("options");
					for (DictionProcessJsonDto dpj : expireOptions) {
						expireProMap.put(dpj.getOptionId(), Integer.valueOf(dpj.getOptionValue()));
					}
				}
			}
			if(StringUtils.isNotBlank(resCustInfoDto.getNoteType()) && "10".equals(resCustInfoDto.getNoteType())){
				List<String> custIds = new ArrayList<String>();
				Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("orgId", user.getOrgId());
				searchMap.put("ownerAccs", resCustInfoDto.getOwnerAccs());
				searchMap.put("ownerAcc", resCustInfoDto.getOwnerAcc());
				if(expireMap.containsKey("options")){
					List<DictionProcessJsonDto> expireOptions = (List<DictionProcessJsonDto>)expireMap.get("options");
					List<DictionProcessJsonDto> options = new ArrayList<DictionProcessJsonDto>();
					for(DictionProcessJsonDto pjd : expireOptions){
						DictionProcessJsonDto jd = new DictionProcessJsonDto();
						BeanUtils.copyProperties(pjd, jd);
						Integer intVal = Integer.parseInt(jd.getOptionValue()) - 3;
						jd.setOptionValue(intVal.toString());
						options.add(jd);
					}
					searchMap.put("options", options);
					List<String> expireIds = resCustInfoService.getCustExpireIds(searchMap);
					if(expireIds.size() > 0){
						custIds.addAll(expireIds);
					}
				}
				
				if(expireMap.containsKey("epDay")){
					searchMap.remove("options");
					int epDay = (int)expireMap.get("epDay");
					searchMap.put("epDay",epDay -3 );
					List<String> expireIds = resCustInfoService.getCustExpireIds(searchMap);
					if(expireIds.size() > 0){
						custIds.addAll(expireIds);
					}
				}
				
				if(custIds.size() > 0) resCustInfoDto.setIds(custIds);
			}
			
			//排序
			if(StringUtils.isNotBlank(resCustInfoDto.getOrderKey())){
				String orderKey = resCustInfoDto.getOrderKey()+",RES_CUST_ID DESC";
				resCustInfoDto.setOrderKey(orderKey);
			}else {
				resCustInfoDto.setOrderKey("AMOYTOCUSTOMER_DATE DESC,RES_CUST_ID DESC");
			}
			List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_5.getState());
			multiSearchList.add(AppConstant.SEARCH_LABEL);
			List<ResCustInfoDto> custs = resCustInfoService.getMyCustListPage(resCustInfoDto,multiSearchList);
			//多选项显示
			if(custs.size() > 0){
				List<String> multiShowList = cachedService.getMultiSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_5.getState());
				List<String> singleShowList = cachedService.getSingleSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_5.getState());
				resCustInfoService.multiDefinedShowChange(custs,multiShowList,singleShowList,user.getOrgId());
			}
			// 从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
			
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
			// 从缓存读取客户类型列表
			List<OptionBean> typeOptions = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			Map<String, String> typeNameMap = cachedService.changeOptionListToMap(typeOptions);
			
			Map<Integer, String> provinceMap = new HashMap<Integer, String>();
			Map<Integer, String> cityMap = new HashMap<Integer, String>();
			Map<Integer, String> countyMap = new HashMap<Integer, String>();
			Map<String, String> tradeMap = new HashMap<String, String>();
			if(user.getIsState() == 1){
				provinceMap = cachedservice.getAreaMap(CachedNames.PROVINCE);
				cityMap = cachedservice.getAreaMap(CachedNames.CITY);
				countyMap = cachedservice.getAreaMap(CachedNames.COUNTY);
				List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
				tradeMap = cachedservice.changeOptionListToMap(trades);
			}
			Date now_time = new Date();
			int day;
			for (ResCustInfoDto custInfoDto : custs) {
				custInfoDto.setCustTypeName(StringUtils.isNotBlank(custInfoDto.getCustTypeId()) ? typeNameMap.get(custInfoDto.getCustTypeId()) : "");
				custInfoDto.setOwnerName(StringUtils.isNotBlank(custInfoDto.getOwnerAcc()) ? nameMap.get(custInfoDto.getOwnerAcc()) : "");
				custInfoDto.setCommonOwnerName(StringUtils.isNotBlank(custInfoDto.getCommonAcc()) ? nameMap.get(custInfoDto.getCommonAcc()) : "");
				custInfoDto.setSaleProcessName(StringUtils.isNotBlank(custInfoDto.getLastOptionId()) ? saleProcMap.get(custInfoDto.getLastOptionId()) : "");
				// 此处处理前台展示时间字段，因使用fmt标签转换耗费时间，统一在后台转为字符串
				custInfoDto.setStartActionDate(custInfoDto.getActionDate() != null ? DateUtil.formatDate(custInfoDto.getActionDate(), DateUtil.Data_ALL) : "");
				custInfoDto.setEndActionDate(custInfoDto.getActionDate() != null ? DateUtil.formatDate(custInfoDto.getActionDate(), DateUtil.DATE_DAY) : "");
				custInfoDto.setPstartDate(custInfoDto.getNextFollowDate() != null ? DateUtil.formatDate(custInfoDto.getNextFollowDate(), DateUtil.Data_ALL)
						: "");
				custInfoDto.setPendDate(custInfoDto.getNextFollowDate() != null ? DateUtil.formatDate(custInfoDto.getNextFollowDate(), DateUtil.DATE_DAY) : "");
				custInfoDto.setStartDate(custInfoDto.getAmoytocustomerDate() != null ? DateUtil.formatDate(custInfoDto.getAmoytocustomerDate(),
						DateUtil.Data_ALL) : "");
				custInfoDto
						.setEndDate(custInfoDto.getAmoytocustomerDate() != null ? DateUtil.formatDate(custInfoDto.getAmoytocustomerDate(), DateUtil.DATE_DAY)
								: "");
				custInfoDto.setGroupName(StringUtils.isNotBlank(custInfoDto.getResGroupId()) ? groupMap.get(custInfoDto.getResGroupId()) : "");
				custInfoDto.setShowdefined16(custInfoDto.getDefined16() !=null?DateUtil.formatDate(custInfoDto.getDefined16(), DateUtil.DATE_DAY): "");
				custInfoDto.setShowdefined17(custInfoDto.getDefined17() !=null?DateUtil.formatDate(custInfoDto.getDefined17(), DateUtil.DATE_DAY): "");
				custInfoDto.setShowdefined18(custInfoDto.getDefined18() !=null?DateUtil.formatDate(custInfoDto.getDefined18(), DateUtil.DATE_DAY): "");
			
				if(custInfoDto.getProvinceId() != null && user.getIsState() == 1){
					String area = provinceMap.get(custInfoDto.getProvinceId());
					if(custInfoDto.getCityId() != null) area+="-"+cityMap.get(custInfoDto.getCityId());
					if(custInfoDto.getCountyId() != null) area+="-"+countyMap.get(custInfoDto.getCountyId());
					custInfoDto.setArea(area);
				}
				custInfoDto.setCompanyTrade(custInfoDto.getCompanyTrade() != null ? tradeMap.get(custInfoDto.getCompanyTrade()) : "");
			
				if(expireMap.containsKey("expireType")){
					if(expireMap.containsKey("options") && StringUtils.isNotBlank(custInfoDto.getLastOptionId())){
						if(expireProMap.containsKey(custInfoDto.getLastOptionId())){
							day = expireProMap.get(custInfoDto.getLastOptionId());
							Date dd = custInfoDto.getActionDate() == null ? custInfoDto.getAmoytocustomerDate() : custInfoDto.getActionDate();
							int day_diff = DateUtil.datediff(now_time, dd);
							int ep = day - day_diff ;
							if(ep <= 3 && ep > 0){
								custInfoDto.setDays(ep);
							}else if(ep <=0){
								custInfoDto.setDays(1);
							}
						}
					}
					if(expireMap.containsKey("epDay") && custInfoDto.getOwnerStartDate() != null){
						day = (int)expireMap.get("epDay");
						int day_diff = DateUtil.datediff(now_time, custInfoDto.getOwnerStartDate());
						int ep = day - day_diff ;
						Integer days = custInfoDto.getDays();
						if(ep <= 3 && ep > 0){
							custInfoDto.setDays(ep);
						}else if(ep <=0){
							custInfoDto.setDays(1);
						}
						if(days != null && custInfoDto.getDays().compareTo(days) == 1){
							custInfoDto.setDays(days);
						}
					}
				}
				
			}
			map.put("item", resCustInfoDto);
			map.put("list", custs);
			map.put("serverDay", user.getServerDay());
			map.put("loginAcc", user.getAccount());
			map.put("fields", getIsQueryList(user.getOrgId(), user.getIsState()));
			map.put("signSetting", getSignSetting());
			map.put("isState", user.getIsState());
			/**共有客户设置*/
			int isOpen = getCommonOwnerOpenState(user.getOrgId());
			map.put("commonOnwerOpen", isOpen);
			if(isOpen == 1){
				map.put("commonOnwerSign", getCommonOwnerSignAuth(user.getOrgId()));
			}
		} catch (Exception e) {
			logger.error("我的客户-意向客户异步加载数据异常！",e);
		}
		return map;
	}

	/**
	 * 我的客户-签约
	 *
	 * @param request
	 * @return
	 * @create 2015年11月16日 上午11:44:16 lixing
	 * @history
	 */
	@RequestMapping("/mySignCusts")
	public String signCusts(HttpServletRequest request) {
		String page = request.getParameter("pageView");
		try {
			boolean planFlag = processPlanPage(request);
			
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("shiroUser", user);
			
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			request.setAttribute("options", options);
			List<OptionBean> typeOptions = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			request.setAttribute("typeOptions", typeOptions);

			request.setAttribute("groupList", cachedService.getResGroupList(user.getOrgId()));
			List<CustSearchSet> tlist = getIsTextQueryList(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_6.getState());
			request.setAttribute("tList", tlist); // 文本类型查询字段列表
			Map<String,String>map = getIsDefinedNameList(user.getOrgId(),user.getIsState());
			request.setAttribute("definedNameMap", map);
			// 用于筛选项排序
			Map<String,Integer> mutilSearchCodeSortMap = getUnTestSearchSort(SysEnum.SEARCH_SET_MODULE_6.getState(),user.getOrgId(),user.getIssys());
			request.setAttribute("mutilSearchCodeSortMap",mutilSearchCodeSortMap);
			List<HighSearchDto> dtos = cachedService.getHighSearch(SysEnum.SEARCH_SET_MODULE_6.getState(), user.getOrgId(), user.getIssys());
			List<HighSearchDto> definedDtos = new ArrayList<HighSearchDto>();
			for(HighSearchDto dto : dtos){
				if(dto.getIsFiledSet() ==1 && dto.getIsQuery() == 1 ){
					definedDtos.add(dto);
				}
			}			
			request.setAttribute("definedSearchCodes",definedDtos); // 自定义非文本项查询集合
			// 需隐藏列的序号
			List<Integer> sorts = getHideSortListCode(SysEnum.SEARCH_SET_MODULE_6.getState(),user.getOrgId(),user.getIsState().toString(),SearchListShowCodeDto.modult_6_List,2);
			if(getCommonOwnerOpenState(user.getOrgId()) == 0){
				if(!sorts.contains(12)) sorts.add(12);
			}
			request.setAttribute("sorts", sorts);
			
			setIsReplaceWord(request);
			commonOwnerAuth(request);
			
			List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
			request.setAttribute("companyTrades", trades);
			
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		if (StringUtils.isNotBlank(page) && page.equals("manage")) {
			return "res/sign_custs_manage";
		} else {
			return "res/mySignCusts";
		}
	}
	
	@RequestMapping("/mySignCustsData")
	@ResponseBody
	public Map<String, Object> mySignCustsData(HttpServletRequest request,ResCustInfoDto resCustInfoDto){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			boolean planFlag = processPlanData(request,resCustInfoDto,user);
			
			resCustInfoDto.setState(user.getIsState());
			resCustInfoDto.setOrgId(user.getOrgId());
			if (!planFlag && user.getIssys() != null && user.getIssys() == 1) {
				resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
				resCustInfoDto.setAdminAcc(user.getAccount());
				if (resCustInfoDto.getOsType().equals("1") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
					resCustInfoDto.setOwnerAccs(accs);
				} else {
					// 处理拥有者
					if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
						resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
					} else {
						resCustInfoDto.setOwnerAccsStr(user.getAccount());
						resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
				//从缓存读取管理员是否可以对下属成员的客户进行“签约”、“取消签约”操作的设置
				map.put("adminSignAuth", getAdminSignAuth());
			} else {
				resCustInfoDto.setOwnerAcc(user.getAccount());
			}
			// 处理最近联系时间
			if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5
					&& resCustInfoDto.getlDateType() != 6) {
				resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
				resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 下次联系时间
			if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5
					&& resCustInfoDto.getoDateType() != 6) {
				if(resCustInfoDto.getoDateType() == 4){
					resCustInfoDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
					resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
				}else{
					resCustInfoDto.setPstartDate(getStartDateStr(resCustInfoDto.getoDateType()));
					resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
				}
			}
			// 最近签约时间
			if (resCustInfoDto.getnDateType() != null && resCustInfoDto.getnDateType() != 0 && resCustInfoDto.getnDateType() != 5) {
				resCustInfoDto.setStartDate(getStartDateStr(resCustInfoDto.getnDateType()));
				resCustInfoDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 默认选中全部标签
			if (StringUtils.isBlank(resCustInfoDto.getNoteType())) {
				resCustInfoDto.setNoteType("1");
			}
			// 处理标签
			if (StringUtils.isNotBlank(resCustInfoDto.getAllLabels())) {
				resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
			}
			if(getCommonOwnerOpenState(user.getOrgId()) == 1){//如果开启共有者  设置查询共有者
				resCustInfoDto.setCommonAcc(user.getAccount());
				if(StringUtils.isNotBlank(resCustInfoDto.getCommonAccsStr())){
					resCustInfoDto.setCommonAccs(Arrays.asList(resCustInfoDto.getCommonAccsStr().split(",")));
				}
			}
			if(StringUtils.isNotBlank(resCustInfoDto.getServiceAccStr())){
				resCustInfoDto.setServiceAccs(Arrays.asList(resCustInfoDto.getServiceAccStr().split(",")));
			}
			//排序
			if(StringUtils.isNotBlank(resCustInfoDto.getOrderKey())){
				String orderKey = resCustInfoDto.getOrderKey()+",RES_CUST_ID DESC";
				resCustInfoDto.setOrderKey(orderKey);
			}else {
				resCustInfoDto.setOrderKey("SIGN_DATE DESC,RES_CUST_ID DESC");
			}
			
			List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_6.getState());
			multiSearchList.add(AppConstant.SEARCH_LABEL);
			List<ResCustInfoDto> custs = resCustInfoService.getSignCustListPage(resCustInfoDto,multiSearchList);
			//多选项显示
			if(custs.size() > 0){
				List<String> multiShowList = cachedService.getMultiSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_6.getState());
				List<String> singleShowList = cachedService.getSingleSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_6.getState());
				resCustInfoService.multiDefinedShowChange(custs,multiShowList,singleShowList,user.getOrgId());
			}

			
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
			// 从缓存读取客户类型列表
			List<OptionBean> typeOptions = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			Map<String, String> typeNameMap = cachedService.changeOptionListToMap(typeOptions);
//			Map<String,String> definedMap = cachedService.getDefinedSearchField(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_6.getState());
			
			Map<Integer, String> provinceMap = new HashMap<Integer, String>();
			Map<Integer, String> cityMap = new HashMap<Integer, String>();
			Map<Integer, String> countyMap = new HashMap<Integer, String>();
			Map<String, String> tradeMap = new HashMap<String, String>();
			if(user.getIsState() == 1){
				provinceMap = cachedservice.getAreaMap(CachedNames.PROVINCE);
				cityMap = cachedservice.getAreaMap(CachedNames.CITY);
				countyMap = cachedservice.getAreaMap(CachedNames.COUNTY);
				List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
				tradeMap = cachedservice.changeOptionListToMap(trades);
			}
			
			for (ResCustInfoDto rcid : custs) {
				rcid.setOwnerName(StringUtils.isNotBlank(rcid.getOwnerAcc()) ? nameMap.get(rcid.getOwnerAcc()) : "");
				rcid.setServiceName(StringUtils.isNotBlank(rcid.getServiceAcc()) ? nameMap.get(rcid.getServiceAcc()) : "");
				// 此处处理前台展示时间字段，因使用fmt标签转换耗费时间，统一在后台转为字符串
				rcid.setCommonOwnerName(StringUtils.isNotBlank(rcid.getCommonAcc()) ? nameMap.get(rcid.getCommonAcc()) : "");
				rcid.setStartActionDate(rcid.getActionDate() != null ? DateUtil.formatDate(rcid.getActionDate(), DateUtil.Data_ALL) : "");
				rcid.setEndActionDate(rcid.getActionDate() != null ? DateUtil.formatDate(rcid.getActionDate(), DateUtil.DATE_DAY) : "");
				rcid.setPstartDate(rcid.getNextFollowDate() != null ? DateUtil.formatDate(rcid.getNextFollowDate(), DateUtil.Data_ALL) : "");
				rcid.setPendDate(rcid.getNextFollowDate() != null ? DateUtil.formatDate(rcid.getNextFollowDate(), DateUtil.DATE_DAY) : "");
				rcid.setStartDate(rcid.getSignDate() != null ? DateUtil.formatDate(rcid.getSignDate(), DateUtil.DATE_DAY) : "");
				rcid.setGroupName(StringUtils.isNotBlank(rcid.getResGroupId()) ? groupMap.get(rcid.getResGroupId()) : "");
				rcid.setCustTypeName(StringUtils.isNotBlank(rcid.getCustTypeId()) ? typeNameMap.get(rcid.getCustTypeId()) : "");
				rcid.setShowdefined16(rcid.getDefined16() !=null?DateUtil.formatDate(rcid.getDefined16(), DateUtil.DATE_DAY): "");
				rcid.setShowdefined17(rcid.getDefined17() !=null?DateUtil.formatDate(rcid.getDefined17(), DateUtil.DATE_DAY): "");
				rcid.setShowdefined18(rcid.getDefined18() !=null?DateUtil.formatDate(rcid.getDefined18(), DateUtil.DATE_DAY): "");
//				if(definedMap != null && definedMap.size() > 0) resCustInfoService.setCustHighSearchDefined(rcid, definedMap, user.getOrgId());
				if(rcid.getProvinceId() != null && user.getIsState() == 1){
					String area = provinceMap.get(rcid.getProvinceId());
					if(rcid.getCityId() != null) area+="-"+cityMap.get(rcid.getCityId());
					if(rcid.getCountyId() != null) area+="-"+countyMap.get(rcid.getCountyId());
					rcid.setArea(area);
				}
				rcid.setCompanyTrade(rcid.getCompanyTrade() != null ? tradeMap.get(rcid.getCompanyTrade()) : "");
			}
			map.put("item", resCustInfoDto);
			map.put("list", custs);
			map.put("serverDay", user.getServerDay());
			map.put("loginAcc", user.getAccount());
			map.put("fields", getIsQueryList(user.getOrgId(), user.getIsState()));
			map.put("signSetting", getSignSetting());
			map.put("isState", user.getIsState());
			/**共有客户设置*/
			int isOpen = getCommonOwnerOpenState(user.getOrgId());
			map.put("commonOnwerOpen", isOpen);
			if(isOpen == 1){
				map.put("commonOnwerSign", getCommonOwnerSignAuth(user.getOrgId()));
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("我的客户-签约客户异步加载数据异常！",e);
		}
		return map;
	}
	
	/**
	 * 沉默客户筛选
	 *
	 * @param request
	 * @param resCustInfoDto
	 * @return
	 * @create 2015年12月16日 下午6:10:57 lixing
	 * @history
	 */
	@RequestMapping("/silentCustSelect")
	public String silentCustsSelect(HttpServletRequest request, ResCustInfoDto resCustInfoDto) {
		Integer isState = 0;
		try {
			String selectType = request.getParameter("selectType");
			ShiroUser user = ShiroUtil.getShiroUser();
			isState = user.getIsState();
			resCustInfoDto.setOrgId(user.getOrgId());
			if (user.getIssys() != null && user.getIssys() == 1) {
				resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
				resCustInfoDto.setAdminAcc(user.getAccount());
				if (resCustInfoDto.getOsType().equals("1")) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					resCustInfoDto.setOwnerAccs(accs);
				} else {
					// 处理拥有者
					if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
						resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
					} else {
						resCustInfoDto.setOwnerAccsStr(user.getAccount());
						resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
			} else {
				resCustInfoDto.setOwnerAcc(user.getAccount());
			}
			if ((resCustInfoDto.getNoteType() == null || resCustInfoDto.getNoteType().equals("")) && resCustInfoDto.getDays() == null) {
				resCustInfoDto.setNoteType("1");
			}
			if (resCustInfoDto.getDays() != null) {
				resCustInfoDto.setNoteType("");
			}
			if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
				resCustInfoDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
				resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
			}
			List<ResCustInfoDto> custs = resCustInfoService.silentCustsSxListPage(resCustInfoDto);
			request.setAttribute("custs", custs);
			request.setAttribute("resCustInfoDto", resCustInfoDto);
			request.setAttribute("selectType", selectType);
			request.setAttribute("shiroUser", user);
			setCustomFiled(user, request);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		if (isState == 1) {
			return "res/silent_select";
		} else {
			return "res/silent_select_person";
		}
	}

	/**
	 * 沉默客户
	 *
	 * @param request
	 * @param resCustInfoDto
	 * @return
	 * @create 2015年12月4日 下午5:32:43 lixing
	 * @history
	 */
	@RequestMapping("/mySilentCusts")
	public String mySilentCusts(HttpServletRequest request, ResCustInfoDto resCustInfoDto) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			// 设置自定义字段查询
			setDefinedQueryFileds(request, user.getOrgId(), user.getIsState());
			request.setAttribute("shiroUser", user);
			setCustomFiled(user, request);
			setIsReplaceWord(request);
			request.setAttribute("groupList", cachedService.getResGroupList(user.getOrgId()));
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "res/mySilentCusts";
	}

	@RequestMapping("/mySilentCustsData")
	@ResponseBody
	public Map<String, Object> mySilentCustsData(HttpServletRequest request, ResCustInfoDto resCustInfoDto){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			resCustInfoDto.setState(user.getIsState());
			resCustInfoDto.setOrgId(user.getOrgId());
			if (user.getIssys() != null && user.getIssys() == 1) {
				resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
				resCustInfoDto.setAdminAcc(user.getAccount());
				if (resCustInfoDto.getOsType().equals("1")) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
					resCustInfoDto.setOwnerAccs(accs);
				} else {
					// 处理拥有者
					if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
						resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
					} else {
						resCustInfoDto.setOwnerAccsStr(user.getAccount());
						resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
			} else {
				resCustInfoDto.setOwnerAcc(user.getAccount());
			}
			if ((resCustInfoDto.getNoteType() == null || resCustInfoDto.getNoteType().equals("")) && resCustInfoDto.getDays() == null) {
				resCustInfoDto.setNoteType("1");
			}
			if (resCustInfoDto.getDays() != null) {
				resCustInfoDto.setNoteType("");
			}
			// 处理 合同截止时间
			if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
				if(resCustInfoDto.getoDateType() == 4){
					resCustInfoDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
					resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
				}else{
					resCustInfoDto.setPstartDate(getStartDateStr(resCustInfoDto.getoDateType()));
					resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
				}
			}
			// 处理 最近联系时间
			if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5) {
				resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
				resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理标签
			if (StringUtils.isNotBlank(resCustInfoDto.getAllLabels())) {
				resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
			}
			//排序
			if(StringUtils.isNotBlank(resCustInfoDto.getOrderKey())){
				String orderKey = resCustInfoDto.getOrderKey()+",RES_CUST_ID DESC";
				resCustInfoDto.setOrderKey(orderKey);
			}else {
				resCustInfoDto.setOrderKey("DAYS DESC,RES_CUST_ID DESC ");
			}
			List<ResCustInfoDto> custs = resCustInfoService.getSilentCustListPage(resCustInfoDto);
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
			for (ResCustInfoDto rcid : custs) {
				rcid.setGroupName(StringUtils.isNotBlank(rcid.getResGroupId()) ? groupMap.get(rcid.getResGroupId()) : "");
				rcid.setOwnerName(StringUtils.isNotBlank(rcid.getOwnerAcc()) ? nameMap.get(rcid.getOwnerAcc()) : "");
				// 此处处理前台展示时间字段，因使用fmt标签转换耗费时间，统一在后台转为字符串
				rcid.setStartActionDate(rcid.getActionDate() != null ? DateUtil.formatDate(rcid.getActionDate(), DateUtil.Data_ALL) : "");
				rcid.setEndActionDate(rcid.getActionDate() != null ? DateUtil.formatDate(rcid.getActionDate(), DateUtil.DATE_DAY) : "");
				rcid.setStartDate(rcid.getContractEndDate() != null ? DateUtil.formatDate(rcid.getContractEndDate(), DateUtil.DATE_DAY) : "");
			}
			map.put("item", resCustInfoDto);
			map.put("list", custs);
			map.put("serverDay", user.getServerDay());
			map.put("loginAcc", user.getAccount());
			map.put("fields", getIsQueryList(user.getOrgId(), user.getIsState()));
			map.put("signSetting", getSignSetting());
			map.put("isState", user.getIsState());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("我的客户-沉默客户异步加载数据异常！",e);
		}
		return map;
	}
	
	/**
	 * 流失客户
	 *
	 * @param request
	 * @return
	 * @create 2015年12月17日 下午6:54:42 lixing
	 * @history
	 */
	@RequestMapping("/myLosingCusts")
	public String myLosingCusts(HttpServletRequest request) {
		String page = request.getParameter("pageView");
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_WAST, user.getOrgId());
			// 设置自定义字段查询
			setDefinedQueryFileds(request, user.getOrgId(), user.getIsState());
			request.setAttribute("options", options);
			request.setAttribute("shiroUser", user);
			setCustomFiled(user, request);
			setIsReplaceWord(request);
			request.setAttribute("groupList", cachedService.getResGroupList(user.getOrgId()));
			if (StringUtils.isNotBlank(page) && page.equals("manage")) {
				List<OptionBean> typeOptions = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
				request.setAttribute("typeOptions", typeOptions);
				List<CustSearchSet> tlist = getIsTextQueryList(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_12.getState());
				request.setAttribute("tList", tlist); // 文本类型查询字段列表
				Map<String,String>map = getIsDefinedNameList(user.getOrgId(),user.getIsState());
				request.setAttribute("definedNameMap", map);
				// 用于筛选项排序
				Map<String,Integer> mutilSearchCodeSortMap = getUnTestSearchSort(SysEnum.SEARCH_SET_MODULE_12.getState(),user.getOrgId(),user.getIssys());
				request.setAttribute("mutilSearchCodeSortMap",mutilSearchCodeSortMap);
				List<HighSearchDto> dtos = cachedService.getHighSearch(SysEnum.SEARCH_SET_MODULE_12.getState(), user.getOrgId(), user.getIssys());
				List<HighSearchDto> definedDtos = new ArrayList<HighSearchDto>();
				for(HighSearchDto dto : dtos){
					if(dto.getIsFiledSet() ==1 && dto.getIsQuery() == 1 ){
						definedDtos.add(dto);
					}
				}			
				request.setAttribute("definedSearchCodes",definedDtos); // 自定义非文本项查询集合
				// 需隐藏列的序号
				List<Integer> sorts = getHideSortListCode(SysEnum.SEARCH_SET_MODULE_12.getState(),user.getOrgId(),user.getIsState().toString(),SearchListShowCodeDto.modult_12_List,2);
				request.setAttribute("sorts", sorts);
				List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
				request.setAttribute("companyTrades", trades);
				return "res/losing_custs_manage";
			} else {
				return "res/myLosingCusts";
			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
	}

	@RequestMapping("/myLosingCustsData")
	@ResponseBody
	public Map<String, Object> myLosingCustsData(HttpServletRequest request, ResCustInfoDto resCustInfoDto){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			resCustInfoDto.setState(user.getIsState());
			resCustInfoDto.setOrgId(user.getOrgId());
			if (user.getIssys() != null && user.getIssys() == 1) {
				// resCustInfoDto.setAdminAcc(user.getAccount());
				resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
				resCustInfoDto.setAdminAcc(user.getAccount());
				if (resCustInfoDto.getOsType().equals("1")) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
					resCustInfoDto.setOwnerAccs(accs);
				} else {
					// 处理拥有者
					if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
						resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
					} else {
						resCustInfoDto.setOwnerAccsStr(user.getAccount());
						resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
			} else {
				resCustInfoDto.setOwnerAcc(user.getAccount());
			}

			if ((resCustInfoDto.getNoteType() == null || resCustInfoDto.getNoteType().equals("")) && resCustInfoDto.getDays() == null) {
				resCustInfoDto.setNoteType("1");
			}
			if (resCustInfoDto.getDays() != null) {
				resCustInfoDto.setNoteType("");
			}
			// 处理 合同截止时间
			if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
				if(resCustInfoDto.getoDateType() == 4){
					resCustInfoDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
					resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
				}else{
					resCustInfoDto.setPstartDate(getStartDateStr(resCustInfoDto.getoDateType()));
					resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
				}
			}
			// 处理 最近联系时间
			if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5) {
				resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
				resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理标签
			if (StringUtils.isNotBlank(resCustInfoDto.getAllLabels())) {
				resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
			}
			//排序
			if(StringUtils.isNotBlank(resCustInfoDto.getOrderKey())){
				String orderKey = resCustInfoDto.getOrderKey()+",RES_CUST_ID DESC";
				resCustInfoDto.setOrderKey(orderKey);
			}else {
				resCustInfoDto.setOrderKey("DAYS DESC,RES_CUST_ID DESC ");
			}
			List<ResCustInfoDto> custs = resCustInfoService.getLosingCustListPage(resCustInfoDto);
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_WAST, user.getOrgId());
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> losingMap = cachedService.changeOptionListToMap(options);
			Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
			for (ResCustInfoDto rcid : custs) {
				rcid.setGroupName(StringUtils.isNotBlank(rcid.getResGroupId()) ? groupMap.get(rcid.getResGroupId()) : "");
				rcid.setOwnerName(StringUtils.isNotBlank(rcid.getOwnerAcc()) ? nameMap.get(rcid.getOwnerAcc()) : "");
				rcid.setLosingReason(StringUtils.isNotBlank(rcid.getLosingId()) ? losingMap.get(rcid.getLosingId()) : "");
				rcid.setStartActionDate(rcid.getActionDate() != null ? DateUtil.formatDate(rcid.getActionDate(), DateUtil.Data_ALL) : "");
				rcid.setEndActionDate(rcid.getActionDate() != null ? DateUtil.formatDate(rcid.getActionDate(), DateUtil.DATE_DAY) : "");
				rcid.setStartDate(rcid.getContractEndDate() != null ? DateUtil.formatDate(rcid.getContractEndDate(), DateUtil.DATE_DAY) : "");
			}
			map.put("item", resCustInfoDto);
			map.put("list", custs);
			map.put("serverDay", user.getServerDay());
			map.put("loginAcc", user.getAccount());
			map.put("fields", getIsQueryList(user.getOrgId(), user.getIsState()));
			map.put("signSetting", getSignSetting());
			map.put("isState", user.getIsState());
			map.put("isSys", user.getIssys());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("我的客户-流失客户异步加载数据异常！",e);
		}
		return map;
	}
	
	@RequestMapping("/losingCustsData")
	@ResponseBody
	public Map<String, Object> losingCustsData(HttpServletRequest request, ResCustInfoDto resCustInfoDto){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			resCustInfoDto.setState(user.getIsState());
			resCustInfoDto.setOrgId(user.getOrgId());
			if (user.getIssys() != null && user.getIssys() == 1) {
				// resCustInfoDto.setAdminAcc(user.getAccount());
				resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
				resCustInfoDto.setAdminAcc(user.getAccount());
				if (resCustInfoDto.getOsType().equals("1") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
					resCustInfoDto.setOwnerAccs(accs);
				} else {
					// 处理拥有者
					if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
						resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
					} else {
						resCustInfoDto.setOwnerAccsStr(user.getAccount());
						resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
			} else {
				resCustInfoDto.setOwnerAcc(user.getAccount());
			}

			if ((resCustInfoDto.getNoteType() == null || resCustInfoDto.getNoteType().equals("")) && resCustInfoDto.getDays() == null) {
				resCustInfoDto.setNoteType("1");
			}
			if (resCustInfoDto.getDays() != null) {
				resCustInfoDto.setNoteType("");
			}
			// 处理 合同截止时间
			if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
				if(resCustInfoDto.getoDateType() == 4){
					resCustInfoDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
					resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
				}else{
					resCustInfoDto.setPstartDate(getStartDateStr(resCustInfoDto.getoDateType()));
					resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
				}
			}
			// 处理 最近联系时间
			if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5) {
				resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
				resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理标签
			if (StringUtils.isNotBlank(resCustInfoDto.getAllLabels())) {
				resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
			}
			//排序
			if(StringUtils.isNotBlank(resCustInfoDto.getOrderKey())){
				String orderKey = resCustInfoDto.getOrderKey()+",RES_CUST_ID DESC";
				resCustInfoDto.setOrderKey(orderKey);
			}else {
				resCustInfoDto.setOrderKey("DAYS DESC,RES_CUST_ID DESC ");
			}
			List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_12.getState());
			multiSearchList.add(AppConstant.SEARCH_LABEL);
			List<ResCustInfoDto> custs = resCustInfoService.getManageLosingCustListPage(resCustInfoDto,multiSearchList);
			//多选项显示
			if(custs.size() > 0){
				List<String> multiShowList = cachedService.getMultiSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_12.getState());
				List<String> singleShowList = cachedService.getSingleSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_12.getState());
				resCustInfoService.multiDefinedShowChange(custs,multiShowList,singleShowList,user.getOrgId());
			}
			
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_WAST, user.getOrgId());
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> losingMap = cachedService.changeOptionListToMap(options);
			Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
			// 从缓存读取客户类型列表
			List<OptionBean> typeOptions = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			Map<String, String> typeNameMap = cachedService.changeOptionListToMap(typeOptions);
//			Map<String,String> definedMap = cachedService.getDefinedSearchField(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_12.getState());
			
			Map<Integer, String> provinceMap = new HashMap<Integer, String>();
			Map<Integer, String> cityMap = new HashMap<Integer, String>();
			Map<Integer, String> countyMap = new HashMap<Integer, String>();
			Map<String, String> tradeMap = new HashMap<String, String>();
			if(user.getIsState() == 1){
				provinceMap = cachedservice.getAreaMap(CachedNames.PROVINCE);
				cityMap = cachedservice.getAreaMap(CachedNames.CITY);
				countyMap = cachedservice.getAreaMap(CachedNames.COUNTY);
				List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
				tradeMap = cachedservice.changeOptionListToMap(trades);
			}
			
			for (ResCustInfoDto rcid : custs) {
				rcid.setCustTypeName(StringUtils.isNotBlank(rcid.getCustTypeId()) ? typeNameMap.get(rcid.getCustTypeId()) : "");
				rcid.setGroupName(StringUtils.isNotBlank(rcid.getResGroupId()) ? groupMap.get(rcid.getResGroupId()) : "");
				rcid.setOwnerName(StringUtils.isNotBlank(rcid.getOwnerAcc()) ? nameMap.get(rcid.getOwnerAcc()) : "");
				rcid.setLosingReason(StringUtils.isNotBlank(rcid.getLosingId()) ? losingMap.get(rcid.getLosingId()) : "");
				rcid.setStartActionDate(rcid.getActionDate() != null ? DateUtil.formatDate(rcid.getActionDate(), DateUtil.Data_ALL) : "");
				rcid.setEndActionDate(rcid.getActionDate() != null ? DateUtil.formatDate(rcid.getActionDate(), DateUtil.DATE_DAY) : "");
				rcid.setStartDate(rcid.getContractEndDate() != null ? DateUtil.formatDate(rcid.getContractEndDate(), DateUtil.DATE_DAY) : "");
//				if(definedMap != null) resCustInfoService.setCustHighSearchDefined(rcid, definedMap, user.getOrgId());
				if(rcid.getProvinceId() != null && user.getIsState() == 1){
					String area = provinceMap.get(rcid.getProvinceId());
					if(rcid.getCityId() != null) area+="-"+cityMap.get(rcid.getCityId());
					if(rcid.getCountyId() != null) area+="-"+countyMap.get(rcid.getCountyId());
					rcid.setArea(area);
				}
				rcid.setCompanyTrade(rcid.getCompanyTrade() != null ? tradeMap.get(rcid.getCompanyTrade()) : "");
			}
			map.put("item", resCustInfoDto);
			map.put("list", custs);
			map.put("serverDay", user.getServerDay());
			map.put("loginAcc", user.getAccount());
			map.put("fields", getIsQueryList(user.getOrgId(), user.getIsState()));
			map.put("signSetting", getSignSetting());
			map.put("isState", user.getIsState());
			map.put("isSys", user.getIssys());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("流失客户异步加载数据异常！",e);
		}
		return map;
	}
	
	@RequestMapping("/toRecyleLosingCust")
	public String toRecyleLosingCust(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<ResourceGroupBean> groupList = cachedService.getResGroupList(user.getOrgId());
			request.setAttribute("groupList", groupList);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "res/recyle_losing_custs";
	}

	/**
	 * 全部客户
	 *
	 * @param request
	 * @return
	 * @create 2015年12月22日 下午2:12:34 lixing
	 * @history
	 */
	@RequestMapping("/myAllTypeCusts")
	public String myAllTypeCusts(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			// 分组
			List<ResourceGroupBean> groupList = cachedService.getResGroupList(user.getOrgId());
			// 从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			request.setAttribute("groupList", groupList);
			request.setAttribute("options", options);
			request.setAttribute("shiroUser", user);
			
			List<CustSearchSet> tlist = getIsTextQueryList(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_7.getState());
			request.setAttribute("tList", tlist); // 文本类型查询字段列表
			Map<String,String>map = getIsDefinedNameList(user.getOrgId(),user.getIsState());
			request.setAttribute("definedNameMap", map);
			// 用于筛选项排序
			Map<String,Integer> mutilSearchCodeSortMap = getUnTestSearchSort(SysEnum.SEARCH_SET_MODULE_7.getState(),user.getOrgId(),user.getIssys());
			request.setAttribute("mutilSearchCodeSortMap",mutilSearchCodeSortMap);
			List<HighSearchDto> dtos = cachedService.getHighSearch(SysEnum.SEARCH_SET_MODULE_7.getState(), user.getOrgId(), user.getIssys());
			List<HighSearchDto> definedDtos = new ArrayList<HighSearchDto>();
			for(HighSearchDto dto : dtos){
				if(dto.getIsFiledSet() ==1 && dto.getIsQuery() == 1 ){
					definedDtos.add(dto);
				}
			}			
			request.setAttribute("definedSearchCodes",definedDtos); // 自定义非文本项查询集合
			// 需隐藏列的序号
			List<Integer> sorts = getHideSortListCode(SysEnum.SEARCH_SET_MODULE_7.getState(),user.getOrgId(),user.getIsState().toString(),SearchListShowCodeDto.modult_7_List,2);
			request.setAttribute("sorts", sorts);
			
			setIsReplaceWord(request);
			
			List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
			request.setAttribute("companyTrades", trades);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "res/myAllTypeCusts";
	}

	
	@RequestMapping("/myAllTypeCustsData")
	@ResponseBody
	public Map<String, Object> myAllTypeCustsData(HttpServletRequest request, ResCustInfoDto resCustInfoDto){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			resCustInfoDto.setState(user.getIsState());
			resCustInfoDto.setOrgId(user.getOrgId());
			if (user.getIssys() != null && user.getIssys() == 1) {
				resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
				resCustInfoDto.setAdminAcc(user.getAccount());
				if (resCustInfoDto.getOsType().equals("1") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
					resCustInfoDto.setOwnerAccs(accs);
				} else {
					// 处理拥有者
					if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
						resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
					} else {
						resCustInfoDto.setOwnerAccsStr(user.getAccount());
						resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
			} else {
				resCustInfoDto.setOwnerAcc(user.getAccount());
			}

			// 默认选中全部标签
			if (StringUtils.isBlank(resCustInfoDto.getNoteType())) {
				resCustInfoDto.setNoteType("1");
			}
			// 默认资源
			if (StringUtils.isBlank(resCustInfoDto.getCustType())) {
				resCustInfoDto.setCustType("0");
			}
			// 处理 添加/分配时间
			if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
				resCustInfoDto.setPstartDate(getStartDateStr(resCustInfoDto.getoDateType()));
				resCustInfoDto.setPendDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理 最近联系时间
			if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5) {
				resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
				resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理 下次联系时间
			if (resCustInfoDto.getnDateType() != null && resCustInfoDto.getnDateType() != 0 && resCustInfoDto.getnDateType() != 5
					&& resCustInfoDto.getnDateType() != 6) {
				if(resCustInfoDto.getnDateType() == 4){
					resCustInfoDto.setStartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
					resCustInfoDto.setEndDate(getEndDateStr(resCustInfoDto.getnDateType()));
				}else{
					resCustInfoDto.setStartDate(getStartDateStr(resCustInfoDto.getnDateType()));
					resCustInfoDto.setEndDate(getEndDateStr(resCustInfoDto.getnDateType()));
				}
			}
			// 处理标签
			if (StringUtils.isNotBlank(resCustInfoDto.getAllLabels())) {
				resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
			}
			//排序
			if(StringUtils.isNotBlank(resCustInfoDto.getOrderKey())){
				String orderKey = resCustInfoDto.getOrderKey()+",RES_CUST_ID DESC";
				resCustInfoDto.setOrderKey(orderKey);
			}else {
				resCustInfoDto.setOrderKey("OWNER_START_DATE DESC,RES_CUST_ID DESC");
			}
			List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_7.getState());
			multiSearchList.add(AppConstant.SEARCH_LABEL);
			List<ResCustInfoDto> custs = resCustInfoService.getAllTypeCustListPage(resCustInfoDto,multiSearchList);

			//多选项显示
			if(custs.size() > 0){
				List<String> multiShowList = cachedService.getMultiSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_7.getState());
				List<String> singleShowList = cachedService.getSingleSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_7.getState());
				resCustInfoService.multiDefinedShowChange(custs,multiShowList,singleShowList,user.getOrgId());
			}
			
			// 从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
//			Map<String,String> definedMap = cachedService.getDefinedSearchField(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_7.getState());
			Map<Integer, String> provinceMap = new HashMap<Integer, String>();
			Map<Integer, String> cityMap = new HashMap<Integer, String>();
			Map<Integer, String> countyMap = new HashMap<Integer, String>();
			Map<String, String> tradeMap = new HashMap<String, String>();
			if(user.getIsState() == 1){
				provinceMap = cachedservice.getAreaMap(CachedNames.PROVINCE);
				cityMap = cachedservice.getAreaMap(CachedNames.CITY);
				countyMap = cachedservice.getAreaMap(CachedNames.COUNTY);
				List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
				tradeMap = cachedservice.changeOptionListToMap(trades);
			}
			for (ResCustInfoDto dto : custs) {
				dto.setSaleProcessName(StringUtils.isNotBlank(dto.getLastOptionId()) ? saleProcMap.get(dto.getLastOptionId()) : "");
				dto.setOwnerName(StringUtils.isNotBlank(dto.getOwnerAcc()) ? nameMap.get(dto.getOwnerAcc()) : "");
				dto.setGroupName(StringUtils.isNotBlank(dto.getResGroupId()) ? groupMap.get(dto.getResGroupId()) : "");
				dto.setStartDate(dto.getOwnerStartDate() != null ? DateUtil.formatDate(dto.getOwnerStartDate(), DateUtil.Data_ALL) : "");
				dto.setEndDate(dto.getOwnerStartDate() != null ? DateUtil.formatDate(dto.getOwnerStartDate(), DateUtil.DATE_DAY) : "");
				dto.setStartActionDate(dto.getActionDate() != null ? DateUtil.formatDate(dto.getActionDate(), DateUtil.Data_ALL) : "");
				dto.setEndActionDate(dto.getActionDate() != null ? DateUtil.formatDate(dto.getActionDate(), DateUtil.DATE_DAY) : "");
				dto.setPstartDate(dto.getNextFollowDate() != null ? DateUtil.formatDate(dto.getNextFollowDate(), DateUtil.Data_ALL) : "");
				dto.setPendDate(dto.getNextFollowDate() != null ? DateUtil.formatDate(dto.getNextFollowDate(), DateUtil.DATE_DAY) : "");
				dto.setShowdefined16(dto.getDefined16() !=null?DateUtil.formatDate(dto.getDefined16(), DateUtil.DATE_DAY): "");
				dto.setShowdefined17(dto.getDefined17() !=null?DateUtil.formatDate(dto.getDefined17(), DateUtil.DATE_DAY): "");
				dto.setShowdefined18(dto.getDefined18() !=null?DateUtil.formatDate(dto.getDefined18(), DateUtil.DATE_DAY): "");
//				if(definedMap != null && definedMap.size() > 0) resCustInfoService.setCustHighSearchDefined(dto, definedMap, user.getOrgId());
				if ((dto.getStatus() == 2 || dto.getStatus() == 3) && (dto.getType() == 1 || dto.getType() == 3))
					dto.setCustType("1");
				if ((dto.getStatus() == 2 || dto.getStatus() == 3) && dto.getType() == 2)
					dto.setCustType("2");
				if (dto.getStatus() == 6 && dto.getType() == 2)
					dto.setCustType("3");
				if (dto.getStatus() == 7 && dto.getType() == 2)
					dto.setCustType("4");
				if (dto.getStatus() == 8 && dto.getType() == 2)
					dto.setCustType("5");
				


				if(dto.getProvinceId() != null && user.getIsState() == 1){
					String area = provinceMap.get(dto.getProvinceId());
					if(dto.getCityId() != null) area+="-"+cityMap.get(dto.getCityId());
					if(dto.getCountyId() != null) area+="-"+countyMap.get(dto.getCountyId());
					dto.setArea(area);
				}
				dto.setCompanyTrade(dto.getCompanyTrade() != null ? tradeMap.get(dto.getCompanyTrade()) : "");
			}

			map.put("item", resCustInfoDto);
			map.put("list", custs);
			map.put("serverDay", user.getServerDay());
			map.put("loginAcc", user.getAccount());
			map.put("fields", getIsQueryList(user.getOrgId(), user.getIsState()));
			map.put("signSetting", getSignSetting());
			map.put("isState", user.getIsState());
			map.put("isSys", user.getIssys());
		} catch (Exception e) {
			logger.error("我的客户-全部客户异步加载数据异常！",e);
		}
		return map;
	}
	
	
	
	/***
	 * 客户交接
	 *
	 * @param request
	 * @return
	 * @create 2015年12月23日 上午9:41:30 lixing
	 * @history
	 */
	@RequestMapping("/custTransferManage")
	public String custTransferManage(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("shiroUser", ShiroUtil.getShiroUser());
			// 从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			request.setAttribute("options", options);
			// 从缓存读取客户类型列表
			List<OptionBean> typeOptions = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			request.setAttribute("typeOptions", typeOptions);
			// 设置自定义字段查询
			request.setAttribute("groupList", cachedService.getResGroupList(user.getOrgId()));
			List<CustSearchSet> tlist = getIsTextQueryList(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_3.getState());
			request.setAttribute("tList", tlist); // 文本类型查询字段列表
			Map<String,String>map = getIsDefinedNameList(user.getOrgId(),user.getIsState());
			request.setAttribute("definedNameMap", map);
			// 用于筛选项排序
			Map<String,Integer> mutilSearchCodeSortMap = getUnTestSearchSort(SysEnum.SEARCH_SET_MODULE_3.getState(),user.getOrgId(),user.getIssys());
			request.setAttribute("mutilSearchCodeSortMap",mutilSearchCodeSortMap);
			List<HighSearchDto> dtos = cachedService.getHighSearch(SysEnum.SEARCH_SET_MODULE_3.getState(), user.getOrgId(), user.getIssys());
			List<HighSearchDto> definedDtos = new ArrayList<HighSearchDto>();
			for(HighSearchDto dto : dtos){
				if(dto.getIsFiledSet() ==1 && dto.getIsQuery() == 1 ){
					definedDtos.add(dto);
				}
			}			
			request.setAttribute("definedSearchCodes",definedDtos); // 自定义非文本项查询集合
			// 需隐藏列的序号
			List<Integer> sorts = getHideSortListCode(SysEnum.SEARCH_SET_MODULE_3.getState(),user.getOrgId(),user.getIsState().toString(),SearchListShowCodeDto.modult_3_List,2);
			request.setAttribute("sorts", sorts);
			
			setIsReplaceWord(request);
			
			List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
			request.setAttribute("companyTrades", trades);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "res/cust_transfer_manage";
	}

	@RequestMapping("/custTransferManageData")
	@ResponseBody
	public Map<String, Object> custTransferManageData(HttpServletRequest request, ResCustInfoDto resCustInfoDto){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			resCustInfoDto.setState(user.getIsState());
			resCustInfoDto.setOrgId(user.getOrgId());
			if (user.getIssys() != null && user.getIssys() == 1) {
				resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
				resCustInfoDto.setAdminAcc(user.getAccount());
				if (resCustInfoDto.getOsType().equals("1") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
					resCustInfoDto.setOwnerAccs(accs);
				} else{
					// 处理拥有者
					if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
						resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
					} else {
						resCustInfoDto.setOwnerAccsStr(user.getAccount());
						resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
			} else {
				resCustInfoDto.setOwnerAcc(user.getAccount());
			}

			if (StringUtils.isBlank(resCustInfoDto.getCustType())) {
				resCustInfoDto.setCustType("0");
			}

			// 处理 添加/分配时间
			if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
				resCustInfoDto.setPstartDate(getStartDateStr(resCustInfoDto.getoDateType()));
				resCustInfoDto.setPendDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理 最近联系时间
			if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5) {
				resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
				resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			
			// 处理 下次联系时间
			if (resCustInfoDto.getnDateType() != null && resCustInfoDto.getnDateType() != 0 && resCustInfoDto.getnDateType() != 5
					&& resCustInfoDto.getnDateType() != 6) {
				if(resCustInfoDto.getnDateType() == 4){
					resCustInfoDto.setStartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
					resCustInfoDto.setEndDate(getEndDateStr(resCustInfoDto.getnDateType()));
				}else{
					resCustInfoDto.setStartDate(getStartDateStr(resCustInfoDto.getnDateType()));
					resCustInfoDto.setEndDate(getEndDateStr(resCustInfoDto.getnDateType()));
				}
			}
			
			//排序
			if(StringUtils.isNotBlank(resCustInfoDto.getOrderKey())){
				String orderKey = resCustInfoDto.getOrderKey()+",RES_CUST_ID DESC";
				resCustInfoDto.setOrderKey(orderKey);
			}else {
				resCustInfoDto.setOrderKey("OWNER_START_DATE DESC,RES_CUST_ID DESC");
			}
			List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_3.getState());
			List<ResCustInfoDto> custs = resCustInfoService.getAllTypeCustListPage(resCustInfoDto,multiSearchList);

			//多选项显示
			if(custs.size() > 0){
				List<String> multiShowList = cachedService.getMultiSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_3.getState());
				List<String> singleShowList = cachedService.getSingleSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_3.getState());
				resCustInfoService.multiDefinedShowChange(custs,multiShowList,singleShowList,user.getOrgId());
			}
			// 从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			// 从缓存读取客户类型列表
			List<OptionBean> typeOptions = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
			Map<String, String> typeNameMap = cachedService.changeOptionListToMap(typeOptions);
//			Map<String,String> definedMap = cachedService.getDefinedSearchField(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_3.getState());
			Map<Integer, String> provinceMap = new HashMap<Integer, String>();
			Map<Integer, String> cityMap = new HashMap<Integer, String>();
			Map<Integer, String> countyMap = new HashMap<Integer, String>();
			Map<String, String> tradeMap = new HashMap<String, String>();
			if(user.getIsState() == 1){
				provinceMap = cachedservice.getAreaMap(CachedNames.PROVINCE);
				cityMap = cachedservice.getAreaMap(CachedNames.CITY);
				countyMap = cachedservice.getAreaMap(CachedNames.COUNTY);
				List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
				tradeMap = cachedservice.changeOptionListToMap(trades);
			}

			for (ResCustInfoDto custInfoDto : custs) {
				// 设置客户类型
				custInfoDto.setCustTypeName(StringUtils.isNotBlank(custInfoDto.getCustTypeId()) ? typeNameMap.get(custInfoDto.getCustTypeId()) : "");
				custInfoDto.setOwnerName(StringUtils.isNotBlank(custInfoDto.getOwnerAcc()) ? nameMap.get(custInfoDto.getOwnerAcc()) : "");
				custInfoDto.setSaleProcessName(StringUtils.isNotBlank(custInfoDto.getLastOptionId()) ? saleProcMap.get(custInfoDto.getLastOptionId()) : "");
				custInfoDto.setGroupName(StringUtils.isNotBlank(custInfoDto.getResGroupId()) ? groupMap.get(custInfoDto.getResGroupId()) : "");
				custInfoDto.setStartDate(custInfoDto.getOwnerStartDate() != null ? DateUtil.formatDate(custInfoDto.getOwnerStartDate(), DateUtil.Data_ALL) : "");
				custInfoDto.setEndDate(custInfoDto.getOwnerStartDate() != null ? DateUtil.formatDate(custInfoDto.getOwnerStartDate(), DateUtil.DATE_DAY) : "");
				custInfoDto.setStartActionDate(custInfoDto.getActionDate() != null ? DateUtil.formatDate(custInfoDto.getActionDate(), DateUtil.Data_ALL) : "");
				custInfoDto.setEndActionDate(custInfoDto.getActionDate() != null ? DateUtil.formatDate(custInfoDto.getActionDate(), DateUtil.DATE_DAY) : "");
				custInfoDto.setPstartDate(custInfoDto.getNextFollowDate() != null ? DateUtil.formatDate(custInfoDto.getNextFollowDate(), DateUtil.Data_ALL) : "");
				custInfoDto.setPendDate(custInfoDto.getNextFollowDate() != null ? DateUtil.formatDate(custInfoDto.getNextFollowDate(), DateUtil.DATE_DAY) : "");
				custInfoDto.setShowdefined16(custInfoDto.getDefined16() !=null?DateUtil.formatDate(custInfoDto.getDefined16(), DateUtil.DATE_DAY): "");
				custInfoDto.setShowdefined17(custInfoDto.getDefined17() !=null?DateUtil.formatDate(custInfoDto.getDefined17(), DateUtil.DATE_DAY): "");
				custInfoDto.setShowdefined18(custInfoDto.getDefined18() !=null?DateUtil.formatDate(custInfoDto.getDefined18(), DateUtil.DATE_DAY): "");
//				if(definedMap != null && definedMap.size() > 0) resCustInfoService.setCustHighSearchDefined(custInfoDto, definedMap, user.getOrgId());
				// 判断状态
				if ((custInfoDto.getStatus() == 2 || custInfoDto.getStatus() == 3) && (custInfoDto.getType() == 1 || custInfoDto.getType() == 3))
					custInfoDto.setCustType("1");
				if ((custInfoDto.getStatus() == 2 || custInfoDto.getStatus() == 3) && custInfoDto.getType() == 2)
					custInfoDto.setCustType("2");
				if (custInfoDto.getStatus() == 6 && custInfoDto.getType() == 2)
					custInfoDto.setCustType("3");
				if (custInfoDto.getStatus() == 7 && custInfoDto.getType() == 2)
					custInfoDto.setCustType("4");
				if (custInfoDto.getStatus() == 8 && custInfoDto.getType() == 2)
					custInfoDto.setCustType("5");

				if(custInfoDto.getProvinceId() != null && user.getIsState() == 1){
					String area = provinceMap.get(custInfoDto.getProvinceId());
					if(custInfoDto.getCityId() != null) area+="-"+cityMap.get(custInfoDto.getCityId());
					if(custInfoDto.getCountyId() != null) area+="-"+countyMap.get(custInfoDto.getCountyId());
					custInfoDto.setArea(area);
				}
				custInfoDto.setCompanyTrade(custInfoDto.getCompanyTrade() != null ? tradeMap.get(custInfoDto.getCompanyTrade()) : "");
				
			}
			
			map.put("item", resCustInfoDto);
			map.put("list", custs);
			map.put("serverDay", user.getServerDay());
			map.put("loginAcc", user.getAccount());
			map.put("fields", getIsQueryList(user.getOrgId(), user.getIsState()));
			map.put("signSetting", getSignSetting());
			map.put("isState", user.getIsState());
			map.put("isSys", user.getIssys());
		} catch (Exception e) {
			logger.error("我的客户-客户交接异步加载数据异常！",e);
		}
		return map;
	}
	
	/**
	 * 跳转客户交接页面
	 *
	 * @param request
	 * @param ids
	 * @return
	 * @create 2015年12月23日 上午11:42:13 lixing
	 * @history
	 */
	@RequestMapping("/toTransfer")
	public String toTransfer(HttpServletRequest request, String ids) {
		try {
			request.setAttribute("ids", ids);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "res/to_transfer";
	}

	/**
	 * 交接页面获取管理团队成员
	 *
	 * @param searchText
	 * @return
	 * @create 2015年12月23日 上午11:42:13 lixing
	 * @history
	 */
	@RequestMapping("/toTransfer/getUsers")
	@ResponseBody
	public List<CustTransferDTO> getUsers(String searchText,String queryScale) {
		ShiroUser user = ShiroUtil.getUser();
		List<TeamGroupBean> groups = new ArrayList<TeamGroupBean>();
		if(StringUtils.isNotBlank(queryScale) && queryScale.equals("1")){
			TeamGroupBean tgb = new TeamGroupBean();
			tgb.setOrgId(user.getOrgId());
			tgb.setGroupType("1");
			groups = tsmTeamGroupService.findByCondtion(tgb);
		}else{
			groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
		}
		List<CustTransferDTO> dtos = new ArrayList<CustTransferDTO>();
		for (TeamGroupBean teamGroupBean : groups) {
			CustTransferDTO dto = new CustTransferDTO();
			dto.setGroupId(teamGroupBean.getGroupId());
			dto.setGroupName(teamGroupBean.getGroupName());
			List<TsmTeamGroupMemberBean> members = tsmTeamGroupMemberService.findByGroupId(user.getOrgId(), teamGroupBean.getGroupId());
			if (StringUtils.isBlank(searchText)) {
				dto.setMembers(members);
				dtos.add(dto);
			} else {
				List<TsmTeamGroupMemberBean> temp = new ArrayList<TsmTeamGroupMemberBean>();
				for (TsmTeamGroupMemberBean member : members) {
					if (member.getUserName().toUpperCase().contains(searchText.toUpperCase())
							|| member.getMemberAcc().toUpperCase().contains(searchText.toUpperCase())) {
						temp.add(member);
					}
				}

				if (temp.size() != 0) {
					dto.setMembers(temp);
					dtos.add(dto);
				}
			}

		}
		return dtos;
	}

	/**
	 * 客户交接
	 *
	 * @param request
	 * @param response
	 * @create 2015年12月23日 上午11:29:28 lixing
	 * @history
	 */
	@RequestMapping("/custTransfer")
	@ResponseBody
	public String custTransfer(HttpServletRequest request, HttpServletResponse response) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			String ownerAcc = request.getParameter("ownerAcc");
			String ownerName = request.getParameter("ownerName");
			String name = user.getName();
			String reason = request.getParameter("reason");
			if (StringUtils.isNotBlank(ids)) {
				List<String> custIds = getCustIds(ids);
				resCustInfoService.modifyBatchCust(ownerName, ownerAcc, custIds, reason, user, null);
				comResourceService.freshTaoCache(user.getOrgId(), user.getAccount());
			}
			return "1";
		} catch (Exception e) {
			logger.error("客户交接失败", e);
			return "-1";
		}
	}

	public List<String> getTransferIds(String ids) {
		List<String> list = new ArrayList<String>();
		String[] id_strs = ids.split(",");
		for (String id_str : id_strs) {
			list.add(id_str.split("_")[0]);
		}
		return list;
	}

	/**
	 * 跳转销售交接页面
	 *
	 * @param request
	 * @param ids
	 * @return
	 * @create 2015年12月23日 上午11:42:13 lixing
	 * @history
	 */
	@RequestMapping("/toSaleTransfer")
	public String toSaleTransfer(HttpServletRequest request, String ids) {
		return "res/to_sale_transfer";
	}

	@RequestMapping("/toCommonOwnerSet")
	public String toCommonOwnerSet(HttpServletRequest request, String ids) {
		request.setAttribute("ids", ids);
		return "res/to_set_common_owner";
	}
	
	@RequestMapping("/setCommonOwner")
	@ResponseBody
	public String setCommonOnwer(String ids,String commonAcc,String commonName){
		try {
			List<String> custIds = new ArrayList<String>();
			ShiroUser user = ShiroUtil.getShiroUser();
			String[] idParam;
			for(String idStr : Arrays.asList(ids.split(","))){
				idParam = idStr.split("_");
				if(!idParam[1].equals(commonAcc)){
					custIds.add(idParam[0]);
				}
			}
			if(custIds.size() > 0){
				StringBuffer context = new StringBuffer("");
				if(custIds.size() > 1){
					context.append(custIds.size()).append("条");
					if(StringUtils.isNotBlank(commonName)) context.append("，共有人：").append(commonName);
				}else{
					ResCustInfoBean bean = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custIds.get(0));
					if(bean.getState() == 1 || StringUtils.isEmpty(bean.getCompany())){
						context.append(StringUtils.isEmpty(bean.getName()) ? "" : bean.getName());
					}else{
						context.append(StringUtils.isEmpty(bean.getCompany()) ? "" : bean.getCompany());
					}
//					if(StringUtils.isNotBlank(bean.getMobilephone())) context.append("(").append(bean.getMobilephone()).append(")");
					if(StringUtils.isNotBlank(commonName)) context.append("，").append("共有人：").append(commonName);
				}
				resCustInfoService.updateCustsCommonAcc(user.getOrgId(),user.getAccount(),user.getName(),custIds , commonAcc,context.toString());
			}
			return AppConstant.RESULT_SUCCESS;
		} catch (Exception e) {
			logger.error("设置共有者失败，ids="+ids+";commonAcc="+commonAcc);
			return AppConstant.RESULT_EXCEPTION;
		}
	}
	
	/**
	 * 客户交接记录
	 *
	 * @param request
	 * @return
	 * @create 2015年11月30日 下午5:06:42 lixing
	 * @history
	 */
	@RequestMapping("/custMoveList")
	public String custMoveList(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			// 销售进程
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			// 从缓存读取客户类型列表
			List<OptionBean> typeOptions = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			
			request.setAttribute("options", options);
			request.setAttribute("typeOptions", typeOptions);
			request.setAttribute("shiroUser", user);
			setIsReplaceWord(request);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "res/custMoveList";
	}

	@RequestMapping("/custMoveListData")
	@ResponseBody
	public Map<String, Object> custMoveListData(HttpServletRequest request,CustOptorDto custOptorDto){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			custOptorDto.setOrgId(user.getOrgId());
			if (user.getIssys() != null && user.getIssys() == 1) {
				if (custOptorDto.getOsType().equals("1")) {
					custOptorDto.setShareAcc(user.getAccount());
				} else if (custOptorDto.getOsType().equals("2")) {
					custOptorDto.setTransferAcc(user.getAccount());
					custOptorDto.setOwnerAcc(user.getAccount());
				} else {
					if (StringUtils.isNotBlank(custOptorDto.getOwnerAccsStr())) {
						custOptorDto.setOwnerAccs(Arrays.asList(custOptorDto.getOwnerAccsStr().split(",")));
					} else {
						custOptorDto.setOwnerAccsStr(user.getAccount());
						custOptorDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
			} else {
				custOptorDto.setTransferAcc(user.getAccount());
				custOptorDto.setOwnerAcc(user.getAccount());
			}
			// 转移时间
			if (custOptorDto.getnDateType() != null && custOptorDto.getnDateType() != 0 && custOptorDto.getnDateType() != 5) {
				custOptorDto.setStartDate(getStartDateStr(custOptorDto.getnDateType()));
				custOptorDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			List<CustOptorDto> optorDtos = custOptorService.findCustInOutListPage(custOptorDto,user.getIsState());
			// 销售进程
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			// 从缓存读取客户类型列表
			List<OptionBean> typeOptions = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
			Map<String, String> typeMap = cachedService.changeOptionListToMap(typeOptions);
			for (CustOptorDto cod : optorDtos) {
				cod.setOptionName(StringUtils.isNotBlank(cod.getSaleProcessId()) ? saleProcMap.get(cod.getSaleProcessId()) : "");
				cod.setUserName(StringUtils.isNotBlank(cod.getTransferAcc()) ? nameMap.get(cod.getTransferAcc()) : "");
				cod.setOptoerName(StringUtils.isNotBlank(cod.getOptoerAcc()) ? nameMap.get(cod.getOptoerAcc()) : "");
				cod.setCustTypeName(StringUtils.isNotBlank(cod.getCustTypeId()) ? typeMap.get(cod.getCustTypeId()) : "");
				cod.setStartDate(cod.getOptorResDate() != null ? DateUtil.formatDate(cod.getOptorResDate(), DateUtil.Data_ALL) : "");
				cod.setEndDate(cod.getOptorResDate() != null ? DateUtil.formatDate(cod.getOptorResDate(), DateUtil.DATE_DAY) : "");
			}
			map.put("item", custOptorDto);
			map.put("list", optorDtos);
			map.put("account", user.getAccount());
		} catch (Exception e) {
			logger.error("客户交接记录读取数据失败！",e);
		}
		return map;
	}
	
	/**
	 * 销售交接 将一销售的所有的资源、客户、全部交接给另外一销售
	 *
	 * @param request
	 * @param response
	 * @create 2015年11月24日 上午9:27:57 lixing
	 * @history
	 */
	@RequestMapping("/custDeliveryAll")
	@ResponseBody
	public String custDeliveryAll(HttpServletRequest request, HttpServletResponse response, ResCustInfoDto custInfoDto) {
		try {
			return resCustInfoService.saleCustMove(custInfoDto, request, ShiroUtil.getShiroUser());
		} catch (Exception e) {
			logger.error("****【销售交接】  发生异常，exception:" + e.getMessage(), e);
			return "-1";
		}
	}

	/**
	 * 销售交接 获取客户数量
	 *
	 * @param request
	 * @param response
	 * @create 2015年11月24日 上午9:27:57 lixing
	 * @history
	 */
	@RequestMapping("/custDeliveryAllNum")
	@ResponseBody
	public Integer custDeliveryAllNum(HttpServletRequest request, HttpServletResponse response, ResCustInfoDto custInfoDto) {
		try {
			return resCustInfoService.saleCustMoveNum(custInfoDto, request, ShiroUtil.getShiroUser());
		} catch (Exception e) {
			logger.error("****【销售交接获取客户数量】  发生异常，exception:" + e.getMessage(), e);
			return 0;
		}
	}

	/**
	 * 设置/取消 优先级
	 *
	 * @param request
	 * @param response
	 * @create 2015年11月13日 下午7:40:29 lixing
	 * @history
	 */
	@RequestMapping("/setImp")
	@ResponseBody
	public String setCustPrecedence(HttpServletRequest request, HttpServletResponse response) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			Integer isPrecedence = Integer.parseInt(request.getParameter("isPrecedence"));
			if (StringUtils.isNotBlank(ids)) {
				resCustInfoService.setPrecedenceBatch(user.getAccount(), isPrecedence, getCustIds(ids), user);
			}
			return "1";
		} catch (Exception e) {
			logger.error("设置/取消 优先级失败", e);
			return "-1";
		}
	}

	/**
	 * 放弃客户
	 *
	 * @param request
	 * @param response
	 * @create 2015年11月13日 下午7:44:26 lixing
	 * @history
	 */
	@RequestMapping("/removeCust")
	@ResponseBody
	public String custRemoveBatch(HttpServletRequest request, HttpServletResponse response) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			Short operateType = AppConstant.OPREATE_TYPE12;
			String operateTypeStr = request.getParameter("operateType");
			String module = request.getParameter("module");
			if (StringUtils.isNotBlank(operateTypeStr)) {
				operateType = Short.valueOf(operateTypeStr);
			}
			if (StringUtils.isNotBlank(ids)) {
				List<String> custIds = getCustIds(ids);
				resCustInfoService.giveUp(user, custIds, operateType, "",module);
				comResourceService.freshTaoCache(user.getOrgId(), user.getAccount());
			}
			return "1";
		} catch (Exception e) {
			logger.error("放弃客户失败", e);
			return "-1";
		}
	}
	
	/**
	 * 跳转流失客户转换页面
	 *
	 * @param request
	 * @param ids
	 * @return
	 * @create 2015年12月17日 下午1:15:18 lixing
	 * @history
	 */
	@RequestMapping("/toLosing")
	public String toLosing(HttpServletRequest request, String ids,String module) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_WAST, user.getOrgId());
			request.setAttribute("ids", ids);
			request.setAttribute("module", module);
			request.setAttribute("options", options);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "res/change_to_losing";
	}

	/**
	 * 转为流失客户
	 *
	 * @param request
	 * @param response
	 * @create 2015年11月18日 上午10:32:48 lixing
	 * @history
	 */
	@RequestMapping("/changeToLosing")
	@ResponseBody
	public String changeToLosing(HttpServletRequest request, HttpServletResponse response) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			String losingId = request.getParameter("losingId");
			String losingRemark = request.getParameter("losingRemark");
			String module = request.getParameter("module");
			ResCustInfoDto custInfoDto = new ResCustInfoDto();
			List<String> idList = getCustIds(ids);
			HashSet<String> set = new HashSet<String>(idList);
			Iterator<String> it = set.iterator();
			idList.clear();
			while (it.hasNext()) {
				idList.add(it.next());
			}
			custInfoDto.setIds(idList);
			custInfoDto.setLosingId(losingId);
			custInfoDto.setLosingRemark(losingRemark);
			custInfoDto.setStatus(8);
			custInfoDto.setUpdateAcc(user.getAccount());
			custInfoDto.setOpreateType(19);
			custInfoDto.setUpdateDate(new Date());
			custInfoDto.setOrgId(user.getOrgId());
			custInfoDto.setLastLossDate(new Date());
			resCustInfoService.changeToLosing(custInfoDto, user,module);
			return "1";
		} catch (Exception e) {
			logger.error("转为流失客户失败", e);
			return "-1";
		}
	}

	/**
	 * 设置为沉默客户
	 *
	 * @param request
	 * @param response
	 * @create 2015年11月17日 下午2:26:19 lixing
	 * @history
	 */
	@RequestMapping("/changeToSilent")
	@ResponseBody
	public String changeToSilent(HttpServletRequest request, HttpServletResponse response) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			ResCustInfoDto custInfoDto = new ResCustInfoDto();
			List<String> idList = getCustIds(ids);
			HashSet<String> set = new HashSet<String>(idList);
			Iterator<String> it = set.iterator();
			idList.clear();
			while (it.hasNext()) {
				idList.add(it.next());
			}
			custInfoDto.setOrgId(user.getOrgId());
			custInfoDto.setIds(idList);
			custInfoDto.setStatus(7);
			custInfoDto.setOpreateType(17);
			custInfoDto.setUpdateAcc(user.getAccount());
			custInfoDto.setUpdateDate(new Date());
			resCustInfoService.changeSilentStatus(custInfoDto, user);
			return "1";
		} catch (Exception e) {
			logger.error("设置为流失客户失败", e);
			return "-1";
		}
	}

	@ResponseBody
	@RequestMapping("/qiHui")
	public String qiHui(HttpServletRequest request) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		String custId = "";
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String val = request.getParameter("val");
			String resId = request.getParameter("resId");
			String type = request.getParameter("type");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orgId", user.getOrgId());
			param.put("phone", val);
			param.put("company", val);
			param.put("unitHome", val);
			param.put("resCustId", resId);
			param.put("isState", user.getIsState());
			List<ResCustInfoBean> lists = new ArrayList<ResCustInfoBean>();
			if ("6".equals(type)) {
				lists = comResourceService.getDPPhone(param);
			} else if ("7".equals(type)) { // 单位名称去重
				lists = comResourceService.getDPUnit(param);
			} else if ("8".equals(type)) { // 单位主页去重
				lists = comResourceService.getDPUnitHome(param);
			}
			if (lists != null && lists.size() > 0) {
				ResCustInfoBean bean = lists.get(0);
				Date date = new Date();
				int restype=bean.getType();
				int resStatus=bean.getStatus();
				bean.setOwnerAcc(user.getAccount());
				bean.setIsConcat(0);
				bean.setLifeCode(SysBaseModelUtil.getModelId());
				bean.setActionDate(null);
				bean.setUpdateAcc(user.getAccount());
				bean.setUpdateDate(date);
				bean.setOpreateType(AppConstant.OPREATE_TYPE2.intValue());
				bean.setStatus(AppConstant.STATUS_2.intValue());
				bean.setType(AppConstant.CUST_TYPE1.intValue());
				bean.setInputStatus(0);
				bean.setFilterType(AppConstant.FILTER_TYPE0.intValue());
				bean.setOwnerAcc(user.getAccount());
				bean.setOwnerStartDate(date);
				bean.setOrgId(user.getOrgId());
				bean.setState(user.getIsState());
				bean.setSource(3);
				custId = bean.getResCustId();
				resCustInfoService.modify(bean);
//				if (restype == 1 && resStatus == 1) {}//带分配资源取资源
//				if (resStatus == 4 || resStatus == 5) {//公海取资源
					logContactDayDataService.addLogContactDayData(ContactUtil.SEA_TO_RES,user.getOrgId(), custId, user.getAccount(), null, null);
//				}
				//logCustInfoService.addLog(user.getOrgId(), user.getAccount(), custId, "", OperateEnum.LOG_SEA_QH);
				Map<String, Object> logMap = new HashMap<String, Object>();
				logMap.put(custId, "");
				logCustInfoService.addTableStoreLog(new LogBean(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_SEA_QH.getCode(), OperateEnum.LOG_SEA_QH.getDesc(), 1,SysBaseModelUtil.getModelId()), logMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rtnMap.put("code", "1");
			return JsonUtil.getJsonString(rtnMap);
		}
		rtnMap.put("code", "0");
		rtnMap.put("custId", custId);
		rtnMap.put("result", "1");
		return JsonUtil.getJsonString(rtnMap);
	}
	
	//取回意向
	@ResponseBody
	@RequestMapping("/qiHuiYiXiang")
	public String qiHuiYiXiang(HttpServletRequest request) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		String custId = "";
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String val = request.getParameter("val");
			String resId = request.getParameter("resId");
			String type = request.getParameter("type");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orgId", user.getOrgId());
			param.put("phone", val);
			param.put("company", val);
			param.put("unitHome", val);
			param.put("resCustId", resId);
			param.put("isState", user.getIsState());
			List<ResCustInfoBean> lists = new ArrayList<ResCustInfoBean>();
			if ("6".equals(type)) {
				lists = comResourceService.getDPPhone(param);
			} else if ("7".equals(type)) { // 单位名称去重
				lists = comResourceService.getDPUnit(param);
			} else if ("8".equals(type)) { // 单位主页去重
				lists = comResourceService.getDPUnitHome(param);
			}
			if (lists != null && lists.size() > 0) {
				ResCustInfoBean bean = lists.get(0);
				Date date = new Date();
				int restype=bean.getType();
				int resStatus=bean.getStatus();
				bean.setOwnerAcc(user.getAccount());
//				bean.setIsConcat(0);
//				bean.setLifeCode(SysBaseModelUtil.getModelId());
//				bean.setActionDate(null);
				bean.setNextFollowDate(null);
				bean.setBirthday(bean.getBirthday());
				bean.setAmoytocustomerDate(date);
				bean.setUpdateAcc(user.getAccount());
				bean.setUpdateDate(date);
				bean.setOpreateType(AppConstant.OPREATE_TYPE10.intValue());
				bean.setStatus(AppConstant.STATUS_3.intValue());
				bean.setType(AppConstant.CUST_TYPE2.intValue());
				bean.setInputStatus(0);
//				bean.setFilterType(AppConstant.FILTER_TYPE0.intValue());
				bean.setLastOptionId(null);
				bean.setOwnerAcc(user.getAccount());
				bean.setOwnerStartDate(date);
				bean.setOrgId(user.getOrgId());
				bean.setState(user.getIsState());
				bean.setSource(3);
	        	String option="";
	    		List<OptionBean> saleProcessList = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
	    		if (saleProcessList != null && saleProcessList.size() > 0) {
	    			for (OptionBean optionBean : saleProcessList) {
	    				if(optionBean.getIsDefault()==1){
	    					option=optionBean.getOptionlistId();	
	    				};
	    				
	        			if("".endsWith(option)){
	        				option=saleProcessList.get(0).getOptionlistId();	
	        			}
	        		}
	    			}

	    		bean.setLastOptionId(option);
	    		
	        	String custType="";
	    		List<OptionBean> custTypeList = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
	    		if (custTypeList != null && custTypeList.size() > 0) {
	    			for (OptionBean optionBean : custTypeList) {
	    				if(optionBean.getIsDefault()==1){
	    					custType=optionBean.getOptionlistId();	
	    				};
	    				
	        			if("".endsWith(custType)){
	        				custType=custTypeList.get(0).getOptionlistId();	
	        			}
	        		}
	    			}

	    		bean.setLastCustTypeId(custType);
	    		List<DataDictionaryBean> data1 = cachedService.getDirList(AppConstant.DATA_40032, user.getOrgId());
	    		List<DataDictionaryBean> data2 = cachedService.getDirList(AppConstant.DATA_50055, user.getOrgId());
	    		
	            if ((!data1.isEmpty() && "1".equals(data1.get(0).getDictionaryValue()))&&(!data2.isEmpty() && "1".equals(data2.get(0).getIsOpen()))) {
	            	int dates= Integer.valueOf(data2.get(0).getDictionaryValue());
		            Calendar calen = Calendar.getInstance();
		            calen.add(Calendar.DAY_OF_YEAR,dates);
		            Date nextFollowDate=calen.getTime();
		            bean.setNextFollowDate(nextFollowDate);   
	            }
				custId = bean.getResCustId();
				resCustInfoService.modify(bean);
//				logCustInfoService.addLog(user.getOrgId(), user.getAccount(), custId, "", OperateEnum.LOG_SEA_QH);
//				if (restype == 1 && resStatus == 1) {
//
//				}//带分配资源取意向
//				if (resStatus == 4 || resStatus == 5) {//公海取意向
//			    }
				logContactDayDataService.addLogContactDayData(ContactUtil.SEA_TO_WILL,user.getOrgId(), custId, user.getAccount(), option, option);
				rankingReportService.updateRankingData(user.getOrgId(), user.getAccount(), new BigDecimal(0), 0, 1);//排行榜
				ResCustInfoBean beans = resCustInfoMapper.getByPrimaryKey(user.getOrgId(), custId);
				LogBean  logBean =new LogBean(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_QUHUIYX.getCode(), OperateEnum.LOG_QUHUIYX.getDesc(), 1,SysBaseModelUtil.getModelId()); 
				logBean.setModuleId(AppConstant.Module_id16);
				logBean.setModuleName(AppConstant.Module_Name16);
				logBean.setOperateId(AppConstant.Operate_id80);
				logBean.setOperateName(AppConstant.Operate_Name80);
				logBean.setContent((beans.getCompany() == null || "".equals(beans.getCompany()))? beans.getName() : beans.getCompany());				
				Map<String, Object> logMap = new HashMap<String, Object>();
				logMap.put(custId, "");
				logCustInfoService.addTableStoreLog(logBean, logMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rtnMap.put("code", "1");
			return JsonUtil.getJsonString(rtnMap);
		}
		rtnMap.put("code", "0");
		rtnMap.put("custId", custId);
		rtnMap.put("result", "1");
		return JsonUtil.getJsonString(rtnMap);
	}

	/**
	 * 新增资源
	 *
	 * @param request
	 * @param response
	 * @param
	 * @create 2015年11月17日 下午3:15:26 lixing
	 * @history
	 */
	@RequestMapping("/addComRes")
	@ResponseBody
	public String addMyRes(HttpServletRequest request, HttpServletResponse response, CompanyResDto resDto, String isSaveDetail, String context,String comFrom) {
		Map<String, String> map = new HashMap<String, String>();
		ShiroUser user = ShiroUtil.getShiroUser();
		ResCustInfoBean resInfo = resDto.getCustInfo();
		resInfo.setLifeCode(SysBaseModelUtil.getModelId());
		String resCustId = SysBaseModelUtil.getModelId();
		Date optDate = new Date();
		int resType = ShiroUtil.getShiroUser().getIsState();
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("addMyRes reqId=" + reqId + ",account=" + user.getAccount() + ",resDto=" + JsonUtil.getJsonString(resDto) + ",isSaveDetail="
				+ isSaveDetail + ",context=" + context);
		try {
			if ("".equals(resInfo.getResGroupId()) || resInfo.getResGroupId() == null) {
				String groupId = comReesourceGroupService.saveUnGroup(user.getOrgId());
				resInfo.setResGroupId(groupId);
			}
			if (resType == 1) {// 企业资源
				ResCustInfoDetailBean resDetailInfo = resDto.getCustInfoDetail();
				setZone4CustDetail(resDetailInfo,request);
				String account = user.getAccount();
				resInfo.setResCustId(resCustId);
				resInfo.setState(1);// 企业资源
				String fax = resInfo.getFax() == null ? "" : resInfo.getFax();
				// 去除空格
				resInfo.setName(StringUtils.isNotBlank(resInfo.getName()) ? resInfo.getName().trim() : "");
				resInfo.setCompany(StringUtils.isNotBlank(resInfo.getCompany()) ? resInfo.getCompany().trim() : "");
				resInfo.setUnithome(StringUtils.isNotBlank(resInfo.getUnithome()) ? resInfo.getUnithome().trim() : "");
				resInfo.setFax(fax.replace(",", ""));
				resInfo.setOrgId(user.getOrgId());
				resInfo.setOpreateType(AppConstant.OPREATE_TYPE2.intValue());
				resInfo.setStatus(AppConstant.STATUS_2.intValue());
				resInfo.setType(AppConstant.CUST_TYPE1.intValue());
				resInfo.setIsDel(0);
				resInfo.setInputStatus(0);
				resInfo.setFilterType(AppConstant.FILTER_TYPE0.intValue());
				resInfo.setInputAcc(account);
				resInfo.setOwnerAcc(account);
				resInfo.setOwnerStartDate(optDate);
				resInfo.setInputDate(optDate);
				resInfo.setSource(1);
				resInfo.setOwnerActionDate(optDate);
				resInfo.setImportDeptId(user.getGroupId());
				// 常用电话
				resInfo.setMobilephone(StringUtils.toCheckPhone(formatPhone(resDetailInfo.getTelphone(), request))); // 判断是否是固话，是，再判断是否系统设置要添加区号。
				// 备用电话
				resInfo.setTelphone(StringUtils.toCheckPhone(formatPhone(resDetailInfo.getTelphonebak(), request))); //
				resInfo.setMainLinkman(resDetailInfo.getName());// 主要联系人
				resInfo.setLifeCode(GuidUtil.getId());
				resDetailInfo.setOrgId(user.getOrgId());
				resDetailInfo.setInputtime(optDate);
				resDetailInfo.setRciId(resCustId);
				resDetailInfo.setTscidId(SysBaseModelUtil.getModelId());
				resDetailInfo.setIsDefault(1);
				resDetailInfo.setIsDel(0);
				resDetailInfo.setTelphone(StringUtils.toCheckPhone(resDetailInfo.getTelphone()));
				resDetailInfo.setTelphonebak(StringUtils.toCheckPhone(resDetailInfo.getTelphonebak()));
				resCustInfoService.createComRes(resInfo, resDetailInfo, isSaveDetail);
//				tsmNewWillCountService.updateTsmNewWillCount(user.getOrgId(), account, user.getName(), 1);
			} else {// 个人资源
				String account = user.getAccount();
				resInfo.setResCustId(resCustId);
				resInfo.setState(0);// 企业资源
				String fax = resInfo.getFax() == null ? "" : resInfo.getFax();
				resInfo.setName(StringUtils.isNotBlank(resInfo.getName()) ? resInfo.getName().trim() : "");
				resInfo.setCompany(StringUtils.isNotBlank(resInfo.getCompany()) ? resInfo.getCompany().trim() : "");
				resInfo.setUnithome(StringUtils.isNotBlank(resInfo.getUnithome()) ? resInfo.getUnithome().trim() : "");
				resInfo.setFax(fax.replace(",", ""));
				resInfo.setOrgId(user.getOrgId());
				resInfo.setOpreateType(AppConstant.OPREATE_TYPE2.intValue());
				resInfo.setStatus(AppConstant.STATUS_2.intValue());
				resInfo.setType(AppConstant.CUST_TYPE1.intValue());
				resInfo.setIsDel(0);
				resInfo.setInputStatus(0);
				resInfo.setFilterType(AppConstant.FILTER_TYPE0.intValue());
				resInfo.setInputAcc(account);
				resInfo.setOwnerAcc(account);
				resInfo.setOwnerStartDate(optDate);
				resInfo.setInputDate(optDate);
				resInfo.setSource(1);
				resInfo.setOwnerActionDate(optDate);
				resInfo.setImportDeptId(user.getGroupId());
				resInfo.setTelphone(StringUtils.toCheckPhone(formatPhone(resInfo.getTelphone(), request))); // 判断是否是固话，是，再判断是否系统设置要添加区号。
				resInfo.setMobilephone(StringUtils.toCheckPhone(formatPhone(resInfo.getMobilephone(), request))); //
				resCustInfoService.create(resInfo);
//				tsmNewWillCountService.updateTsmNewWillCount(user.getOrgId(), account, user.getName(), 1);
			}
			if (context != null && StringUtils.isNotEmpty(context)) {
				resCustInfoLogService.createResourceBZ(resInfo.getResCustId(), context, null, user,
						user.getIsState() == 1 ? resInfo.getMainLinkman() : resInfo.getName());
			}
//			logUserOperateService.setUserOperateLog(AppConstant.Module_id9 ,AppConstant.Module_Name9 ,AppConstant.Operate_id12 ,AppConstant.Operate_Name12 ,StringUtils.isNotBlank(resInfo.getCompany()) ? resInfo.getCompany().trim() : (StringUtils.isNotBlank(resInfo.getName()) ? resInfo.getName().trim() : ""),"" );
			//插入日志
			//logCustInfoService.addLog(user.getOrgId(), user.getAccount(), resCustId, null, OperateEnum.LOG_ADD);
			LogBean logBean =new LogBean(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_ADD.getCode(), OperateEnum.LOG_ADD.getDesc(), 1,SysBaseModelUtil.getModelId()); 
			if("1".equals(comFrom)){//来自淘客户
				logBean.setModuleId(AppConstant.Module_id9);
				logBean.setModuleName(AppConstant.Module_Name9);
				logBean.setOperateId(AppConstant.Operate_id12);
				logBean.setOperateName(AppConstant.Operate_Name12);
				logBean.setContent((resInfo.getCompany() == null || "".equals(resInfo.getCompany()))? resInfo.getName() : resInfo.getCompany());				
			}else if("2".equals(comFrom)){
				logBean.setModuleId(AppConstant.Module_id1001);
				logBean.setModuleName(AppConstant.Module_Name1001);
				logBean.setOperateId(AppConstant.Operate_id12);
				logBean.setOperateName(AppConstant.Operate_Name12);
				logBean.setContent((resInfo.getCompany() == null || "".equals(resInfo.getCompany()))? resInfo.getName() : resInfo.getCompany());				
			}else if("3".equals(comFrom)){
				logBean.setModuleId(AppConstant.Module_id16);
				logBean.setModuleName(AppConstant.Module_Name16);
				logBean.setOperateId(AppConstant.Operate_id12);
				logBean.setOperateName(AppConstant.Operate_Name12);
				logBean.setContent((resInfo.getCompany() == null || "".equals(resInfo.getCompany()))? resInfo.getName() : resInfo.getCompany());				
			}
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(resCustId, "");
			logCustInfoService.addTableStoreLog(logBean, logMap);
			
			logContactDayDataService.addLogContactDayData(ContactUtil.ADD_RES,user.getOrgId(), resInfo.getResCustId(), resInfo.getOwnerAcc(), null, resInfo.getLastOptionId());
			map.put("result", "1");
			map.put("resId", resCustId);
			return JsonUtil.getJsonString(map);
		} catch (Exception e) {
			logger.error("addComRes reqId=" + reqId + ",loginAcc=" + user.getAccount(), e);
			map.put("result", "-1");
			map.put("resId", "");
			return JsonUtil.getJsonString(map);
		}

	}

	@RequestMapping("/toAddResByScreen")
	public String toAddResByScreen(HttpServletRequest request, String phone) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("toAddResByScreen reqId= " + reqId + ",account =" + user.getAccount() + ",phone=" + phone);
		try {
			setCustWordCheck(request);
			setIsReplaceWord(request, user);
			setIsRead(request);
			List<CustFieldSet> fieldSets = null;
			List<CustFieldSet> concatFieldSets = null;
			List<ResourceGroupBean> groupList = cachedService.getResGroupList(user.getOrgId());
			request.setAttribute("groupList", groupList);
			int resType = ShiroUtil.getShiroUser().getIsState();
			request.getSession().setAttribute("phone", formatPhone(phone, request));
			if (resType == 1) {// 企业资源
				fieldSets = cachedService.getComFiledSets(user.getOrgId());
				setShowValue(fieldSets);
				concatFieldSets = cachedService.getContactsFiledSets(user.getOrgId());
				setShowValue(concatFieldSets);
				request.setAttribute("fieldSets", fieldSets);
				request.setAttribute("concatFieldSets", concatFieldSets);
				return "res/addCustByScreen";
			} else {// 个人资源
				fieldSets = cachedService.getPersonFiledSets(user.getOrgId());
				setShowValue(fieldSets);
				request.setAttribute("fieldSets", fieldSets);
				return "res/addCustByScreen_person";
			}
		} catch (Exception e) {
			logger.error("toAddResByScreen reqId= " + reqId + ",account =" + user.getAccount() + ",phone=" + phone, e);
			throw new SysRunException(e);
		}
	}

	/**
	 * 跳转屏幕取值添加客户页面
	 *
	 * @param request
	 * @param companyResDto
	 * @return
	 * @create 2016年3月1日 上午11:30:06 wuwei
	 * @history
	 */
	@RequestMapping("/toFollowByScreen")
	public String toFollowByScreen(HttpServletRequest request, CompanyResDto companyResDto, String isSaveDetail, String isSave, String phone) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String custId = null;
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("toFollowByScreen reqId= " + reqId + ",account =" + user.getAccount() + ",phone=" + phone);
		try {
			setIsReplaceWord(request, user);
			setIsRead(request);
			if (companyResDto != null && companyResDto.getCustInfo() != null) {
				custId = GuidUtil.getId();
				companyResDto.getCustInfo().setResCustId(custId);
				request.getSession().setAttribute("companyResDto", companyResDto);
			}
			request.getSession().setAttribute("actionDate", com.qftx.common.util.DateUtil.getDateCurrentDate(com.qftx.common.util.DateUtil.hour24HMSPattern));
			request.setAttribute("followId", SysBaseModelUtil.getModelId()); // 跟进Id
			request.getSession().setAttribute("isSaveDetail", isSaveDetail);
			request.getSession().setAttribute("isSave", isSave);
			request.getSession().setAttribute("phone", formatPhone(phone, request));
			logger.debug("toFollowByScreen  reqId=" + reqId + "isSaveDetail=" + isSaveDetail + ",isSave=" + isSave);
			// 取得单位下各选项列表(销售进程、客户类型、适用产品、反馈信息)
			List<OptionBean> saleProcessList = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			List<OptionBean> custTypeList = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			List<Product> suitProcList = cachedService.getOpionProduct(user.getOrgId());
			List<OptionBean> labelList = cachedService.getOptionList(AppConstant.SALES_TYPE_TEN, user.getOrgId());
			Map<String,Object> maps=custGuideService.getLableList(user.getOrgId());
			Integer isSelect=(Integer) maps.get("isSelect");
			ResCustInfoBean custInfo=companyResDto.getCustInfo();
			String labelId="";
			String labelName="";
			if(custInfo!=null){
			labelId=custInfo.getLabelCode();
			labelName=custInfo.getLabelName();
			}
			if(labelId!=null&&labelName!=null){
			if(!"".endsWith(labelId)&&!"".endsWith(labelName)){
				labelName=labelName.replace("#", ",");
				labelName=labelName.substring(0, labelName.length()-1);
	
			}
			}
			List<OptionBean> label6List = new ArrayList<OptionBean>(); // 默认显示6个
			List<OptionBean> otberLabelList = new ArrayList<OptionBean>(); // 其余隐藏
			if (labelList != null && labelList.size() > 0) {
				for (int i = 0; i < labelList.size(); i++) {
					if (i < 6) {
						label6List.add(labelList.get(i));
					} else {
						otberLabelList.add(labelList.get(i));
					}
				}
			}
			request.setAttribute("isSelect", isSelect); // 
			request.setAttribute("labelName", labelName);
			request.setAttribute("labelIds", labelId);
			request.setAttribute("saleProcessList", saleProcessList); // 销售进程
			request.setAttribute("custTypeList", custTypeList); // 客户类型
			request.setAttribute("suitProcList", suitProcList); // 适用产品
			request.setAttribute("label6List", label6List);
			request.setAttribute("otberLabelList", otberLabelList);

			// 获取指定客户导航
			// TsmCustGuide tsmCustGuideEntity = new TsmCustGuide();
			// tsmCustGuideEntity.setCustId(custId);
			// tsmCustGuideEntity.setOrgId(user.getOrgId());
			// List<TsmCustGuide> tsmCustGuideList =
			// tsmCustGuideService.getListByCondtion(tsmCustGuideEntity);
			// if (tsmCustGuideList != null && tsmCustGuideList.size() > 0) {
			// request.setAttribute("tsmCustGuideEntity",
			// tsmCustGuideList.get(0));
			// }

			// 获取适用产品 默认值
			if (suitProcList != null && suitProcList.size() > 0) {
				StringBuffer sbf = new StringBuffer();
				StringBuffer sbf1 = new StringBuffer();
				for (Product pd : suitProcList) {
					if (pd.getIsDefault() == 1) {
						sbf.append(pd.getId());
						sbf.append(",");
						sbf1.append(pd.getName());
						sbf1.append(",");
					}
				}
				if (sbf != null && StringUtils.isNotBlank(sbf.toString())) {
					String str = sbf.toString().substring(0, sbf.toString().length() - 1);
					request.setAttribute("procIds", str);
				}
				if (sbf1 != null && StringUtils.isNotBlank(sbf1.toString())) {
					String str1 = sbf1.toString().substring(0, sbf1.toString().length() - 1);
					request.setAttribute("procNames", str1);
				}
			}

			// 获取默认下次跟进时间
			request.setAttribute("defDate", getCustCacheDate(request));
			getCustCacheDate(request);
		} catch (Exception e) {
			logger.error("toFollowByScreen reqId= " + reqId + ",account =" + user.getAccount() + ",phone=" + phone, e);
			throw new SysRunException(e);
		}
		return "/res/followByScreen";
	}

	/**
	 * 客户跟进 保存
	 */
	@RequestMapping("/addMyCustByScreen")
	@ResponseBody
	public String addMyCustByScreen(HttpServletRequest request, PageFormDto pageFormDto) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("addMyCustByScreen reqId=" + reqId + ",account=" + user.getAccount() + ",pageFormDto=" + JsonUtil.getJsonString(pageFormDto));
		try {
			CustFollowBean custFollow = pageFormDto.getCustFollow();
			TsmCustGuide custGuide = pageFormDto.getCustGuide();
			String isSaveDetail = (String) request.getSession().getAttribute("isSaveDetail");
			String suitProcId = request.getParameter("suitProcId"); // 适用产品Id s
			if (StringUtils.isNotBlank(suitProcId) && custGuide != null && custFollow != null && StringUtils.isNotBlank(custFollow.getCustFollowId())) {
				resCustInfoService.saveCust(request, user.getOrgId(), user.getAccount(), user.getName(), user.getGroupId(), user.getId(), user.getIsState(),
						custGuide, custFollow, suitProcId, isSaveDetail, user);
				logContactDayDataService.addLogContactDayData(ContactUtil.CUST_ADD, user.getOrgId(), custFollow.getCustId(), user.getAccount(), custGuide.getSaleProcessId(), custGuide.getSaleProcessId());
			}
//			tsmReportWillService.addTsmReportWillandOption(user.getOrgId(), user.getAccount(), user.getName(), 1, custFollow.getSaleProcessId(), "", 1);
			request.getSession().removeAttribute("companyResDto");
			request.getSession().removeAttribute("phone");
			request.getSession().removeAttribute("isSaveDetail");
			request.getSession().removeAttribute("isSave");
			return AppConstant.RESULT_SUCCESS;
		} catch (Exception e) {
			logger.error("addMyCustByScreen reqId=" + reqId + ",loginAcc=" + user.getAccount() + ",pageFormDto=" + JsonUtil.getJsonString(pageFormDto), e);
			throw new SysRunException(e);
		}
	}

	/**
	 * 唤醒沉默客户
	 *
	 * @param request
	 * @param response
	 * @create 2015年11月17日 下午2:26:01 lixing
	 * @history
	 */
	@RequestMapping("/weekUp")
	@ResponseBody
	public String weekUp(HttpServletRequest request, HttpServletResponse response) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			List<String> custIds = getCustIds(ids);
			ResCustInfoDto custInfoDto = new ResCustInfoDto();
			custInfoDto.setOrgId(user.getOrgId());
			custInfoDto.setIds(custIds);
			custInfoDto.setStatus(6);
			custInfoDto.setOpreateType(18);
			custInfoDto.setUpdateAcc(user.getAccount());
			resCustInfoService.changeSilentStatus(custInfoDto, user);
			return "1";
		} catch (Exception e) {
			logger.error("唤醒沉默客户失败", e);
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
	 * 我的客户 列表右侧信息
	 *
	 * @param request
	 * @return
	 * @create 2015年11月27日 下午1:58:54 lixing
	 * @history
	 */
	@RequestMapping("/custRight")
	public String custRight(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String custId = request.getParameter("resCustId");
			ResCustInfoBean custInfo = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custId);
			if (custInfo.getState() != null && custInfo.getState() == 1) {// 企业资源
				List<ResCustInfoDetailBean> details = resCustInfoDetailService.getCustsInfoDetails(user.getOrgId(), custInfo.getResCustId());
				request.setAttribute("details", details);
			}
			// 从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			if (StringUtils.isNotBlank(custInfo.getLastOptionId())) {
				request.setAttribute("lastOptionName", saleProcMap.get(custInfo.getLastOptionId()));
			}
			
			List<ResCustActionDto> actionDtos = resCustEventService.getResCustActionsList(user.getOrgId(), custId);
			// 组装跟进ID
			List<String> followIdSbf = new ArrayList<String>();
			for (ResCustActionDto actoionDto : actionDtos) {
//				if(StringUtils.isNotBlank(actoionDto.getInputAcc())){
					actoionDto.setInputName(nameMap.get(actoionDto.getInputAcc()));
//				}
				if(StringUtils.isNotBlank(actoionDto.getSaleProcessId())){
					actoionDto.setSaleProcessName(saleProcMap.get(actoionDto.getSaleProcessId()));
				}
				if(StringUtils.isNotBlank(actoionDto.getCustFollowId())){
					followIdSbf.add(actoionDto.getCustFollowId());
				}
				String labelName = actoionDto.getLabels();
				if (StringUtils.isNotBlank(labelName)) {
					labelName = labelName.replaceAll("#", "，");
					if(labelName.endsWith("，")){
						labelName = labelName.substring(0, labelName.length() - 1);
					}
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
			commonOwnerAuth(request);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "res/custRight";
	}
	@RequestMapping("/toAddRes")
	public String toAddRes(HttpServletRequest request, String phone) {
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			String comFrom=request.getParameter("comFrom");
			setCustWordCheck(request);
			setIsReplaceWord(request, user);
			setIsRead(request);
			List<CustFieldSet> fieldSets = null;
			List<CustFieldSet> concatFieldSets = null;
			List<ResourceGroupBean> groupList = cachedService.getResGroupList(user.getOrgId());
			request.setAttribute("groupList", groupList);
			int resType = ShiroUtil.getShiroUser().getIsState();
			request.setAttribute("phone", formatPhone(phone, request));   
			if(comFrom!=null&&!"".endsWith(comFrom)){
			request.setAttribute("comFrom", comFrom);
			}
			
			OptionBean option=new OptionBean();
			option.setOrgId(user.getOrgId());		
			option.setItemCode("companyTrade");
			option.setOrderKey("sort asc");
			List<OptionBean> optionList = optionService.getListByCondtion(option);
			request.setAttribute("optionList", optionList);
			if (resType == 1) {// 企业资源
				fieldSets = cachedService.getComFiledSets(user.getOrgId());
				setShowValue(fieldSets);
				concatFieldSets = cachedService.getContactsFiledSets(user.getOrgId());
				setShowValue(concatFieldSets);
				request.setAttribute("fieldSets", fieldSets);
				request.setAttribute("concatFieldSets", concatFieldSets);
				return "res/add_cust";
				// return "res/addCustByScreen";
			} else {// 个人资源
				fieldSets = cachedService.getPersonFiledSets(user.getOrgId());
				setShowValue(fieldSets);
				request.setAttribute("fieldSets", fieldSets);
				return "res/add_person_cust";
				// return "res/addCustByScreen_person.jsp";
			}
		} catch (Exception e) {
			logger.error(e.getMessage() + "loginAcc=" + user.getAccount() + ",phone=" + phone, e);
			throw new SysRunException(e);
		}
	}
	public void setShowValue(List<CustFieldSet> fieldSets) {
		for (CustFieldSet custFieldSet : fieldSets) {
			Integer fieldType = custFieldSet.getDataType();
			if (fieldType == 3 || fieldType == 4) {
				List<OptionBean> optionList = custFieldSet.getOptionList();
				if (optionList.size() != 0 && optionList != null) {
					for (OptionBean optionBean : optionList) {
						if (optionBean.getIsDefault() == 1) {
							custFieldSet.setShowValue(optionBean.getOptionlistId());
						}
					}
				}
			}
		}
	}

	public void setShowValue(ResCustInfoBean rcib,List<CustFieldSet> fieldSets) throws Exception {
		if (rcib != null) {
			Class<?> classType = rcib.getClass();
			for (CustFieldSet custFieldSet : fieldSets) {
				String fieldCode = custFieldSet.getFieldCode();
				Integer fieldType = custFieldSet.getDataType();
				if (fieldCode.contains("defined")) {
					// 得到属性名称的第一个字母并转成大写
					String firstLetter = fieldCode.substring(0, 1).toUpperCase();
					// 获得和属性对应的getXXX()方法的名字：get+属性名称的第一个字母并转成大写+属性名去掉第一个字母，
					// 如属性名称为name，则：get+N+ame
					String getMethodName = "get" + firstLetter + fieldCode.substring(1);
					// 获得和属性对应的getXXX()方法
					Method getMethod = classType.getMethod(getMethodName,new Class[] {});
					// 调用原对象的getXXX()方法
					if ("defined16".equals(fieldCode) || "defined17".equals(fieldCode) || "defined18".equals(fieldCode)) {
						Date date = (Date)getMethod.invoke(rcib, new Object[] {});
						if (date != null) {
							String value = new SimpleDateFormat("yyyy-MM-dd").format(date);
							custFieldSet.setShowValue(value);
						}
					}else {
						String value = (String)getMethod.invoke(rcib, new Object[] {});
						if (value != null) {
							custFieldSet.setShowValue(value);
						}
					}
				}
			}
		}
	}
	
	public void setShowValue(ResCustInfoDetailBean detail,List<CustFieldSet> concatFieldSets) throws Exception {
		if (detail != null) {
			Class<?> classType = detail.getClass();
			for (CustFieldSet custFieldSet : concatFieldSets) {
				String fieldCode = custFieldSet.getFieldCode();
				Integer fieldType = custFieldSet.getDataType();
				if (fieldCode.contains("conDefined")) {
					// 得到属性名称的第一个字母并转成大写
					String firstLetter = fieldCode.substring(0, 1).toUpperCase();
					// 获得和属性对应的getXXX()方法的名字：get+属性名称的第一个字母并转成大写+属性名去掉第一个字母，
					// 如属性名称为name，则：get+N+ame
					String getMethodName = "get" + firstLetter + fieldCode.substring(1);
					// 获得和属性对应的getXXX()方法
					Method getMethod = classType.getMethod(getMethodName,new Class[] {});
					// 调用原对象的getXXX()方法
					if ("conDefined5".equals(fieldCode)) {
						Date date = (Date)getMethod.invoke(detail, new Object[] {});
						if (date != null) {
							String value = new SimpleDateFormat("yyyy-MM-dd").format(date);
							custFieldSet.setShowValue(value);
						}
					}else {
						String value = (String)getMethod.invoke(detail, new Object[] {});
						if (value != null) {
							custFieldSet.setShowValue(value);
						}
					}
				}
			}
		}
	}
	/**
	 * 跳转到修改资源页面
	 *
	 * @param request
	 * @param custId
	 * @return
	 * @create 2015年12月15日 下午8:52:21 lixing
	 * @history
	 */
	@RequestMapping("/toEditRes")
	public String toEditRes(HttpServletRequest request, String custId) {
		List<CustFieldSet> fieldSets = null;
		ShiroUser user = ShiroUtil.getShiroUser();
		String dayPlanflag=request.getParameter("dayPlanflag");
		request.setAttribute("dayPlanflag", dayPlanflag);
		try {
			setCustWordCheck(request);
			setIsReplaceWord(request, user);
			setIsRead(request);
			ResCustInfoBean rcib = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custId);
			request.setAttribute("custInfoBean", rcib);
			request.setAttribute("issys", user.getIssys());
			List<ResourceGroupBean> groupList = cachedService.getResGroupList(user.getOrgId());
			request.setAttribute("groupList", groupList);
			OptionBean option=new OptionBean();
			option.setOrgId(user.getOrgId());		
			option.setItemCode("companyTrade");
			option.setOrderKey("sort asc");
			List<OptionBean> optionList = optionService.getListByCondtion(option);
			request.setAttribute("optionList", optionList);
			
			int resType = ShiroUtil.getShiroUser().getIsState();
			if (resType == 1) {// 企业资源
				fieldSets = cachedService.getComFiledSets(user.getOrgId());
				setShowValue(rcib,fieldSets);
				List<ResCustInfoDetailBean> details = resCustInfoDetailService.getCustsInfoDetails(user.getOrgId(), rcib.getResCustId());
				request.setAttribute("fieldSets", fieldSets);
				request.setAttribute("details", details);
				return "res/edit_cust";
			} else {// 个人资源
				fieldSets = cachedService.getPersonFiledSets(user.getOrgId());
				setShowValue(rcib,fieldSets);
				request.setAttribute("fieldSets", fieldSets);
				return "res/edit_person_cust";
			}
		} catch (Exception e) {
			logger.error(e.getMessage() + "loginAcc=" + user.getAccount() + ",custId=" + custId, e);
			throw new SysRunException(e);
		}
	}

	/**
	 * 修改资源、客户
	 *
	 * @param response
	 * @param custInfoBean
	 * @create 2015年12月18日 下午4:20:59 lixing
	 * @history
	 */
	@RequestMapping("/editRes")
	@ResponseBody
	public String editRes(HttpServletResponse response, ResCustInfoBean custInfoBean, HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			if (user.getIsState() != 1) {
				if (StringUtils.isNotEmpty(custInfoBean.getMobilephone())) {
					custInfoBean.setMobilephone(StringUtils.toCheckPhone(formatPhone(custInfoBean.getMobilephone(), request)));
				}
				if (StringUtils.isNotEmpty(custInfoBean.getTelphone())) {
					custInfoBean.setTelphone(StringUtils.toCheckPhone(formatPhone(custInfoBean.getTelphone(), request)));
				}
			}
			
			custInfoBean.setUpdateAcc(user.getAccount());
			custInfoBean.setUpdateDate(new Date());
			custInfoBean.setOrgId(user.getOrgId());
			// 去除空格
			custInfoBean.setName(StringUtils.isNotBlank(custInfoBean.getName()) ? custInfoBean.getName().trim() : "");
			custInfoBean.setCompany(StringUtils.isNotBlank(custInfoBean.getCompany()) ? custInfoBean.getCompany().trim() : "");
			custInfoBean.setUnithome(StringUtils.isNotBlank(custInfoBean.getUnithome()) ? custInfoBean.getUnithome().trim() : "");
			custInfoBean.setState(user.getIsState());
			resCustInfoService.modify2(custInfoBean);
			return "1";
		} catch (Exception e) {
			logger.error(e.getMessage() + "loginAcc=" + user.getAccount() + ",custInfoBean=" + JsonUtil.getJsonString(custInfoBean), e);
			return "-1";
		}
	}

	/**
	 * 跳转编辑 新增 联系人
	 *
	 * @param request
	 * @param tscidId
	 * @param rciId
	 * @return
	 * @create 2015年12月15日 下午4:48:00 lixing
	 * @history
	 */
	@RequestMapping("/addDetail")
	public String addDetail(HttpServletRequest request, String tscidId, String rciId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			String dayPlanflag=request.getParameter("dayPlanflag");
			request.setAttribute("dayPlanflag", dayPlanflag);
			setCustWordCheck(request);
			List<ResCustInfoDetailBean> detailList = null;
			if (rciId != null) {
				detailList = resCustInfoDetailService.getCustsInfoDetails(user.getOrgId(), rciId);
			}
			List<CustFieldSet> concatFieldSets = cachedService.getContactsFiledSets(ShiroUtil.getShiroUser().getOrgId());
			setShowValue(concatFieldSets);
			request.setAttribute("concatFieldSets", concatFieldSets);
			request.setAttribute("rciId", rciId);
			request.setAttribute("detailList", detailList);
			setIsReplaceWord(request, user);
			setIsRead(request);
		} catch (Exception e) {
			logger.error(e.getMessage() + "loginAcc=" + user.getAccount() + ",tscidId=" + tscidId + ",rciId=" + rciId, e);
			throw new SysRunException(e);
		}
		return "res/add_detail";
	}

	/**
	 * 跳转编辑 新增 联系人
	 *
	 * @param request
	 * @param tscidId
	 * @param rciId
	 * @return
	 * @create 2015年12月15日 下午4:48:00 lixing
	 * @history
	 */
	@RequestMapping("/editDetail")
	public String editDetail(HttpServletRequest request, String tscidId, String rciId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			String dayPlanflag=request.getParameter("dayPlanflag");
			request.setAttribute("dayPlanflag", dayPlanflag);
			setCustWordCheck(request);
			setIsReplaceWord(request, user);
			setIsRead(request);
			ResCustInfoDetailBean detail = null;
			if (tscidId != null) {
				detail = resCustInfoDetailService.getByPrimaryKeyAndOrgId(user.getOrgId(), tscidId,rciId);
				request.setAttribute("detail", detail);
			}
			List<CustFieldSet> concatFieldSets = cachedService.getContactsFiledSets(ShiroUtil.getShiroUser().getOrgId());
			if (detail != null) {
				setShowValue(detail,concatFieldSets);
			}
			request.setAttribute("concatFieldSets", concatFieldSets);
			request.setAttribute("rciId", rciId);
			request.setAttribute("tscidId", tscidId);
			request.setAttribute("issys", ShiroUtil.getShiroUser().getIssys());
		} catch (Exception e) {
			logger.error(e.getMessage() + "loginAcc=" + user.getAccount() + ",tscidId=" + tscidId + ",rciId=" + rciId, e);
			throw new SysRunException(e);
		}
		return "res/edit_detail";
	}

	/**
	 * 新增或保存联系人
	 *
	 * @param response
	 * @param detailBean
	 * @create 2015年12月15日 下午8:06:56 lixing
	 * @history
	 */
	@RequestMapping("/saveDetail")
	@ResponseBody
	public String saveDetail(HttpServletResponse response, HttpServletRequest request,ResCustInfoDetailBean detailBean) {
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			if (detailBean.getIsDefault() == null) {
				detailBean.setIsDefault(1);
			}
			setZone4CustDetail(detailBean,request);
			resCustInfoDetailService.createOrEditDetail(detailBean, user);
			return "1";
		} catch (Exception e) {
			logger.error(e.getMessage() + "loginAcc=" + user.getAccount() + ",detailBean=" + JsonUtil.getJsonString(detailBean), e);
			return "-1";
		}
	}

	/**
	 * 删除联系人
	 *
	 * @param response
	 * @param tscidId
	 * @create 2015年12月15日 下午8:22:03 lixing
	 * @history
	 */
	@RequestMapping("delDetail")
	@ResponseBody
	public String delDetail(HttpServletResponse response, String tscidId) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			ResCustInfoDetailBean detailBean = new ResCustInfoDetailBean();
			detailBean.setOrgId(user.getOrgId());
			detailBean.setTscidId(tscidId);
			detailBean.setIsDel(1);
			resCustInfoDetailService.modify(detailBean);
			return "1";
		} catch (Exception e) {
			logger.error("删除联系人失败", e);
			return "-1";
		}
	}

	/**
	 * @param request
	 * @param custInfoDto
	 * @return
	 * @create 2016年1月25日 下午3:17:29 Administrator
	 * @history
	 */
	@RequestMapping("/hitSigle")
	public String hitSigle(HttpServletRequest request, ResCustInfoDto custInfoDto) {
		Integer isState = 0;
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			isState = user.getIsState();
			custInfoDto.setState(isState);
			if (StringUtils.isNotBlank(custInfoDto.getQueryText())) {
				custInfoDto.setOrgId(user.getOrgId());
				// 模糊查询处理
				String queryText = custInfoDto.getQueryText().trim();
				custInfoDto.setQueryText(queryText);
				List<ResCustInfoDto> custInfoDtos = resCustInfoService.hitSigleListPage(custInfoDto);
				// 从缓存读取销售进程列表
				List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
				Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
				Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
				Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
				for (ResCustInfoDto custInfo : custInfoDtos) {
					// 销售进程 拥有者
					String ownerAcc =  org.apache.commons.lang3.StringUtils.substringBeforeLast(custInfo.getOwnerAcc(), "_F");
					custInfo.setSaleProcessName(StringUtils.isNotBlank(custInfo.getLastOptionId()) ? saleProcMap.get(custInfo.getLastOptionId()) : "");
					custInfo.setOwnerName((nameMap.get(ownerAcc)!=null)?nameMap.get(ownerAcc) :ownerAcc);
					custInfo.setGroupName(StringUtils.isNotBlank(custInfo.getResGroupId()) ? groupMap.get(custInfo.getResGroupId()) : "未分组");
					// 资源客户状态：1-未分配，2-未跟进，3-跟进中，4-公海客户，5-资源回收站，6-已签约，7-沉默客户，8-流失客户
					int custStatus = custInfo.getStatus().intValue();
					// 类型:1-资源，2客户,3-再淘资源
					int custType = custInfo.getType().intValue();
					// 我的资源
					if (custType == 1 && custStatus == 1) {
						custInfo.setCustType("0");
					} else if ((custStatus == 2 || custStatus == 3) && (custType == 1 || custType == 3)) {
						custInfo.setCustType("1");
					} else if ((custStatus == 2 || custStatus == 3) && custType == 2) {// 意向客户
						custInfo.setCustType("2");
					} else if (custStatus == 6 && custType == 2) {// 签约客户
						custInfo.setCustType("3");
					} else if (custStatus == 7 && custType == 2) {// 沉默客户
						custInfo.setCustType("4");
					} else if (custStatus == 8 && custType == 2) {// 流失客户
						custInfo.setCustType("5");
					} else {// 公海
						custInfo.setCustType("6");
					}
				}
				request.setAttribute("custInfoDtos", custInfoDtos);
			}
			request.setAttribute("shiroUser", user);
			request.setAttribute("custInfoDto", custInfoDto);
			setCustomFiled(user, request);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		if (isState == 1) {
			return "res/hit_sigle";
		} else {
			return "res/hit_sigle_person";
		}
	}

	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		String uri = request.getRequestURI();
		if (uri.contains("addMyCustByScreen")) {
			binder.registerCustomEditor(Date.class, (PropertyEditor) new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		} else {
			binder.registerCustomEditor(Date.class, (PropertyEditor) new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		}
	}
	
	/**
	 * 跳转到投诉客户名单，type=0
	 *
	 * 
	 * @param request
	 * @param BlackListBean
	 * @return
	 * @create 2017年5月27日 下午2:17:29 xxh
	 * @history
	 */
	@RequestMapping("/selectBlackCustList")
	public String selectBlackCustList(HttpServletRequest request) {
		try {   
		    ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("orgId", user.getOrgId());
			request.setAttribute("type", "0");
			request.setAttribute("userId", user.getId());
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "res/complaint_list_cust";
	}
	
	/**
	 * 跳转到企业黑名单，type=1
	 *
	 * 
	 * @param request
	 * @param BlackListBean
	 * @return
	 * @create 2017年5月27日 下午2:17:29 xxh
	 * @history
	 */
	@RequestMapping("/selectBlackComList")
	public String selectBlackComList(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("orgId", user.getOrgId());
			request.setAttribute("type", "1");
			request.setAttribute("userId", user.getId());
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "res/complaint_list_company";
	}
	
	/**
	 * 跳转到投诉客户名单新增弹框页面，type=0
	 *
	 * 
	 * @param request
	 * @param BlackListBean
	 * @return
	 * @create 2017年5月31日 下午2:17:29 xxh
	 * @history
	 */
	@RequestMapping("/addBlackCustList")
	public String addBlackCustList(HttpServletRequest request) {
		try {   
		    ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("orgId", user.getOrgId());
			request.setAttribute("type", "0");
			request.setAttribute("userId", user.getId());
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "res/complaint_list_idialog";
	}
	
	/**
	 * 跳转到企业黑名单新增弹框页面，type=1
	 *
	 * 
	 * @param request
	 * @param BlackListBean
	 * @return
	 * @create 2017年5月31日 下午2:17:29 xxh
	 * @history
	 */
	@RequestMapping("/addBlackComList")
	public String addBlackComList(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("orgId", user.getOrgId());
			request.setAttribute("type", "1");
			request.setAttribute("userId", user.getId());
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "res/complaint_list_idialog";
	}
	
	/**
	 * 查询投诉客户、企业黑名单
	 * 
	 * @param request
	 * @param BlackListBean
	 * @return
	 * @create 2017年5月26日 下午2:17:29 xxh
	 * @history
	 */
	@RequestMapping("/selectBlackListJson")
	@ResponseBody
	public Map<String, Object> selectBlackListJson(HttpServletRequest request,BlackListBean black) {
		    List<BlackListBean> list=new ArrayList();
			Map<String, Object> remap = new HashMap<String, Object>();
			ShiroUser user = ShiroUtil.getShiroUser();
		try {
			String  dDateType = request.getParameter("dDateType");	
			String  phone=request.getParameter("phone");
			String  type=request.getParameter("selectType");
        //  发送时间
        	if(StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)){
        		black.setStartDate(getStartDateStr(Integer.parseInt(dDateType)));
        		black.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
        	}	
        	if (StringUtils.isNotEmpty(black.getUserAccounts())) {
       		    String[] usernames = black.getUserAccounts().split(",");
				List<String> owaList = Arrays.asList(usernames);
				black.setUserList(owaList);
        	}
				black.setPhone(phone);
				if(type!=null&&!"".endsWith(type)){
					black.setType(type);	
				}else{
					black.setType(null);
				}

				black.setOrgId(user.getOrgId());
			    list=resCustInfoService.findBlackListPage(black);
			    Map<String, String> usermap=  cachedservice.getOrgUserNames(user.getOrgId());
			    if((dDateType==null||"".equals(dDateType))&&(phone==null||"".equals(phone))&&(black.getUserAccounts()==null||"".equals(black.getUserAccounts()))){
			    	if(list!=null&&list.size()>0){
			    		for(BlackListBean bean_list:list){
			    			bean_list.setUserName(usermap.get(bean_list.getUserAccount()));
			    		}
			    	}
			    	//限制呼出
                    cachedservice.removeBlackCustList(user.getOrgId());

			    	//限制呼入
                    cachedservice.removeBlackComList(user.getOrgId());
			    	
			    }

			    
			    remap.put("list", list);
			    remap.put("item", black);
			    remap.put("status", "succes");
		} catch (Exception e) {
			remap.put("status", false);
			remap.put("errorMsg", e.getMessage());
			return remap;
		}
		    return remap;
	}
	
	
	/**
	 * @param request
	 * @param BlackListBean
	 * @return
	 * @create 2017年5月26日 下午2:17:29 xxh
	 * @history
	 */
	@RequestMapping("/insertBlackList")
	@ResponseBody
	public  Map<String, Object> insertBlackList(HttpServletRequest request) {
		    BlackListBean black=new BlackListBean();
		    ShiroUser user = ShiroUtil.getShiroUser();
		    List<BlackListBean> list=new ArrayList<BlackListBean>();
		    Map<String, Object> map = new HashMap<String, Object>();
		try {
			String  phone=request.getParameter("phone");
			String  type=request.getParameter("type");
			String  compaint_type1=request.getParameter("compaint_type1");
			String  compaint_type2=request.getParameter("compaint_type2");
			
			if("on".equals(compaint_type1)&&compaint_type2==null){//限制呼出
				type="0";
			}else if("on".equals(compaint_type2)&&compaint_type1==null){//限制呼入
				type="1";
			}else if("on".equals(compaint_type1)&&"on".equals(compaint_type2)){//限制呼出。限制呼入
				type="2";
			}
            String  userId=user.getId();
            String  orgId=user.getOrgId();
            String  userName=user.getName();
            String  userAccount=user.getAccount();
            String  blackId=SysBaseModelUtil.getModelId();
            black.setUserId(userId);
            black.setType(type);
            black.setPhone(phone);
            black.setOrgId(orgId);
            list=resCustInfoService.findBlackListPage(black);
            if(list.size()>0){
    			map.put("status", false);
    			map.put("message", "号码重复，请重新添加！");
            }else{
    			 black.setBlackId(blackId);
                 black.setUserName(userName);
                 black.setUserAccount(userAccount);
                 black.setInputDate(new Date());
                 resCustInfoService.insertBlackList(black);
                 Map<String,String> map2=new HashMap<String,String>();
                 Map<String,String> map3=new HashMap<String,String>();
                 map2.put("phone", phone);
                 map3.put("phone", phone);
                 TsmMessageSend tsmmessagesend =new TsmMessageSend();
                 tsmmessagesend.setOrgId(user.getOrgId());
                 tsmmessagesend.setTitle("新增黑名单或投诉客户");
                 if("0".equals(type)){//限制呼出
                     //新增号码给客户端，blackType:1:新增投诉客户名单，2：新增企业黑名单
                     map2.put("blackType", "1");
                     map2.put("blackcustphone", "新增投诉客户");
                     JSONObject jsonObject = JSONObject.fromObject(map2);
                     tsmMessageService.sendBlack(jsonObject.toString(),orgId,userAccount,userName);
                 }else if("1".equals(type)){//限制呼入
                     map2.put("blackType", "2");
                     map2.put("blackcomphone", "新增企业黑名单");
                     JSONObject jsonObject = JSONObject.fromObject(map2);
                     tsmMessageService.sendBlack(jsonObject.toString(),orgId,userAccount,userName);
                 }else if("2".equals(type)){//限制呼入同时是限制呼出
                     map2.put("blackType", "1");
                     map2.put("blackcustphone", "新增投诉客户");
                     JSONObject jsonObject = JSONObject.fromObject(map2);
                     tsmMessageService.sendBlack(jsonObject.toString(),orgId,userAccount,userName);
                     map3.put("blackType", "2");
                     map3.put("blackcomphone", "新增企业黑名单");
                     JSONObject jsonObject2 = JSONObject.fromObject(map3);
                     tsmMessageService.sendBlack(jsonObject2.toString(),orgId,userAccount,userName);
                 }

                 Map<String,String>params = new HashMap<String,String>();
        		 params.put("orgId", orgId);  
                 HttpUtil.doPost(ConfigInfoUtils.getStringValue("robot_Blacklist_url"), params);
                 //删除缓存
				cachedservice.removeBlackCustList(orgId);
				cachedservice.removeBlackComList(orgId);

                 map.put("status", true);
         		 map.put("message", "添加号码成功！");
            }
		} catch (Exception e) {
//			throw new SysRunException(e);
			logger.error(e.getMessage() + "loginAcc=" + user.getAccount() + ",blackBean=" + JsonUtil.getJsonString(black), e);
			map.put("status", false);
			map.put("message", e.getMessage());
		}
		return map;
	}
	
	/**
	 * @param request
	 * @param blackId
	 * @return
	 * @create 2017年5月26日 下午3:19:20 xxh
	 * @history
	 */
	@RequestMapping("/deleteBlackList")
	@ResponseBody
	public boolean deleteBlackList(HttpServletRequest request) {
		  ShiroUser user = ShiroUtil.getShiroUser();
		  BlackListBean black=new BlackListBean();
		  List<String> phonelist=new ArrayList<String>();
		try {
		  String blackIds=request.getParameter("blackIds");
		  String phone =request.getParameter("phone");
		  String type =request.getParameter("type");
		   if (StringUtils.isNotEmpty(blackIds)) {
      		    String[] blackIds_list = blackIds.split(",");
				List<String> owaList = Arrays.asList(blackIds_list);
				black.setBlackIdList(owaList);
       	}
		   
		   if (StringUtils.isNotEmpty(phone)) {
     		    String[] phone_list = phone.split(",");
     		    phonelist = Arrays.asList(phone_list);
      	}

		   String orgId=user.getOrgId();
		   black.setOrgId(orgId);
		   List<BlackListBean> list_new=resCustInfoService.findBlackListPage(black);
           resCustInfoService.deleteBlackList(black);

           //发送消息
           if(list_new!=null&&list_new.size()>0){
        	   for(BlackListBean blackbean:list_new){
                   
                   TsmMessageSend tsmmessagesend =new TsmMessageSend();
                   tsmmessagesend.setOrgId(user.getOrgId());
                   tsmmessagesend.setTitle("删除黑名单或投诉客户");
                   if("0".equals(blackbean.getType())){//限制呼出
                       //新增号码给客户端，type:3:删除投诉客户名单，4：删除企业黑名单
                	   Map<String,String> map2=new HashMap<String,String>();
                	   map2.put("phone", blackbean.getPhone());
                       map2.put("blackType", "3");
                       map2.put("blackcustphone", "删除投诉客户");
                       JSONObject jsonObject = JSONObject.fromObject(map2);
                       tsmMessageService.sendBlack(jsonObject.toString(),orgId,user.getAccount(),user.getName());
                   }else if("1".equals(blackbean.getType())){//限制呼入
                	   Map<String,String> map2=new HashMap<String,String>();
                	   map2.put("phone", blackbean.getPhone());
                       map2.put("blackType", "4");
                       map2.put("blackcomphone", "删除企业黑名单");
                       JSONObject jsonObject = JSONObject.fromObject(map2);
                       tsmMessageService.sendBlack(jsonObject.toString(),orgId,user.getAccount(),user.getName());
                   }else if("2".equals(blackbean.getType())){//企业黑名单
                	   Map<String,String> map2=new HashMap<String,String>();
                	   map2.put("phone", blackbean.getPhone());
                       map2.put("blackType", "3");
                       map2.put("blackcustphone", "删除投诉客户");
                       JSONObject jsonObject = JSONObject.fromObject(map2);
                       tsmMessageService.sendBlack(jsonObject.toString(),orgId,user.getAccount(),user.getName());
                	   
                	   
                	   Map<String,String> map3=new HashMap<String,String>();
                	   map3.put("phone", blackbean.getPhone());
                       map3.put("blackType", "4");
                       map3.put("blackcustphone", "删除企业黑名单");
                       JSONObject jsonObject2 = JSONObject.fromObject(map3);
                       tsmMessageService.sendBlack(jsonObject2.toString(),orgId,user.getAccount(),user.getName());
                   }	   
                Map<String,String>params = new HashMap<String,String>();
       			params.put("orgId", orgId);  
                HttpUtil.doPost(ConfigInfoUtils.getStringValue("robot_Blacklist_url"), params);	
               //删除缓存
               cachedservice.removeBlackCustList(orgId);
               cachedservice.removeBlackComList(orgId);
           }
           
    	   }

		} catch (Exception e) {
//			throw new SysRunException(e);
			logger.error(e.getMessage() + "userAcc=" + user.getAccount() + ",blackBean=" + JsonUtil.getJsonString(black), e);
			return false;
		}
		return true;
	}
	

	
	 /**测试数据
     * @return
     * @create xiaoxh
     * @history
     */
    @ResponseBody
    @RequestMapping(value = "/newblacklist")
    public Map<String,List<String>> findBlackList() {
		ShiroUser user = ShiroUtil.getUser();
		Map<String,List<String>> map=new HashMap<String,List<String>>();
		//投诉客户名单
		List<String> list1=cachedService.getBlackCustPhoneCatch(user.getOrgId());
		//企业黑名单
		List<String> list2=cachedService.getBlackComPhoneCatch(user.getOrgId());
		map.put("blackcustphone", list1);//投诉客户名单，限制呼出列表内电话
		map.put("blackcomphone", list2);//企业黑名单,限制列表内电话呼入
		
		return map;
    }
    
    /**计划页面调用的时候添加参数
     * @return
     * @create lizh
     * @history
     */
	public boolean processPlanPage(HttpServletRequest request) {
		String planPage = request.getParameter("planPage");
		String dateStr = request.getParameter("dateStr");
		String saleProcessId = request.getParameter("saleProcessId");
		String planid = request.getParameter("planid");
		String planType = request.getParameter("planType");
		
		
		if(!StringUtils.isBlank(planPage)){
			request.setAttribute("planType", planType);
			request.setAttribute("planid", planid);
			request.setAttribute("planPage", planPage);
			request.setAttribute("dateStr", dateStr);
			request.setAttribute("saleProcessId", saleProcessId);
			return true;
		}
		return false;
	}
	
	/**计划页面调用的时候添加参数
     * @return
     * @create lizh
     * @history
     */
	public boolean processPlanData(HttpServletRequest request,ResCustInfoDto resCustInfoDto,ShiroUser user) {
		
		String planPage = request.getParameter("planPage");
		String dateStr = request.getParameter("dateStr");
		String saleProcessId = request.getParameter("saleProcessId");
		String planid = request.getParameter("planid");
		String planType = request.getParameter("planType");
		String todayFlag = request.getParameter("todayFlag");
		
		if(!StringUtils.isBlank(planPage)){
			
			if("1".equals(todayFlag)){
				Date planDate = DateUtil.parseDate(dateStr);
				PlanUserDayBean plan= new PlanUserDayBean();
				plan.setPlanDate(planDate);
				plan.setId(planid);
				plan.setType(planType);
				plan.setSaleProcessId(saleProcessId);
				plan.setOrgId(user.getOrgId());
				plan.setUserAcc(user.getAccount());
				plan.setUserId(user.getId());
				
				List<String> custIds = planUserDayService.findCustIdFromPlan(plan);
				resCustInfoDto.setRejectIds(custIds);
			}
			return true;
		}
		return false;
	}
    
    public static void main(String[] args) {
		String msgs="{\"msgMain\":\"eyJtZXNzYWdlaW5mbyI6eyJtc2ciOiJleUppYkdGamExUjVjR1VpT2lJeElpd2ljR2h2Ym1VaU9pSXhOVEUzTWpFd016WTROeUlzSW1Kc1lXTnJZM1Z6ZEhCb2IyNWxJam9pNXBhdzVhS2U1b3FWNksrSjVhNmk1b2kzSW4wPSIsInR5cGUiOiIxMSIsIndhdiI6IjEiLCJ0aXRsZSI6IjVwYXc1YUtlNmJ1UjVaQ041WTJWNW9pVzVvcVY2SytKNWE2aTVvaTMifX0=\",\"msgType\":\"tsm_BlackList\",\"timestamp\":\"2017-07-04 13:44:56\"}";
		System.out.println("msgs:"+msgs);
		   BaseSend base = JSON.parseObject(msgs, BaseSend.class);
		try {
			String msgMain = new String(CodeUtils.base64Decode(base.getMsgMain()), IContants.CHAR_ENCODING);
			System.out.println("msgMain:"+msgMain);
			BaseSend bs = JSON.parseObject(msgMain, BaseSend.class);
	   	    JSONObject jsonObject = JSONObject.fromObject(bs);
	   	    String count=jsonObject.toString();
			System.out.println("bs:"+count);
			MessageObj mob=bs.getMessageinfo();
			String msg=new String(CodeUtils.base64Decode(mob.getMsg()), IContants.CHAR_ENCODING);
			System.out.println("msg:"+msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }
}
