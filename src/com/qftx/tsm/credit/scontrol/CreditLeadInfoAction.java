package com.qftx.tsm.credit.scontrol;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.DataDeleteStatus;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.Page;
import com.qftx.common.util.QupaiModule;
import com.qftx.common.util.ResultCode;
import com.qftx.common.util.ResultObject;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.SysRunException;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.credit.bean.CreditLeadInfoBean;
import com.qftx.tsm.credit.dto.CreditLeadInfoDto;
import com.qftx.tsm.credit.dto.RefFieldMapDto;
import com.qftx.tsm.credit.dto.TsmLoanReviewInfoDto;
import com.qftx.tsm.credit.service.CreditLeadInfoService;
import com.qftx.tsm.credit.service.LoanReviewService;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.log.service.LogBatchInfoService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayBean;
import com.qftx.tsm.plan.user.day.service.PlanUserDayService;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.dto.HighSearchDto;
import com.qftx.tsm.sys.dto.SearchListShowCodeDto;
import com.qftx.tsm.sys.enums.SysEnum;
import com.qftx.tsm.util.CollectionUtil;
import com.qftx.tsm.util.ExtColumnUtil;
import com.qftx.tsm.util.StringUtil;

@Controller
@RequestMapping("/credit/lead")
public class CreditLeadInfoAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(CreditLeadInfoAction.class);
	
	@Autowired
	private PlanUserDayService planUserDayService;
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private CreditLeadInfoService creditLeadInfoService;
	@Autowired
	private LoanReviewService loanReviewService;
	@Autowired
	private UserService userService;
	@Autowired
	private CachedService cachedService;
	@Resource
	private LogCustInfoService logCustInfoService;
	@Resource
	private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Autowired
	private LogBatchInfoService logBatchInfoService;
	
//	@RequestMapping("/myCustsList")
//	public String toMyCusts(HttpServletRequest request) {
//		ShiroUser user = ShiroUtil.getShiroUser();
//		List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
//		request.setAttribute("options", options);
//		commonOwnerAuth(request);
//		return "res/myCusts";
//	}
	
	/**
	 * 催放管理 - 放款管理
	 *
	 * @param request
	 * @param resCustInfoDto
	 * @return
	 * @create 2015年11月13日 上午10:36:10 lixing
	 * @history
	 */
	@RequestMapping("/myLeads")
	public String myLeads(HttpServletRequest request, CreditLeadInfoDto leadInfoDto) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("groupList", cachedService.getResGroupList(user.getOrgId()));
			request.setAttribute("shiroUser", user);
			String module = QupaiModule.LOAN_MNG.getModule();
			
			// 获取文本类型查询字段列表
			List<CustSearchSet> tlist = cachedService.getQupaiIsTextQueryList(user.getOrgId(), module);
			request.setAttribute("tList", tlist); // 
			
			// 获取自定义扩展字段列表
			Map<String,String>map = getQupaiIsDefinedNameList(user.getOrgId());
			request.setAttribute("definedNameMap", map);
			
			// 获取筛选项目列表
			Map<String,Integer> mutilSearchCodeSortMap = cachedService.getQupaiUnTestSearchSort(user.getOrgId(), user.getIssys(), module);
			request.setAttribute("mutilSearchCodeSortMap", mutilSearchCodeSortMap);
			request.setAttribute("mutilSearchCodeSortMapJson",JsonUtil.getJsonString(mutilSearchCodeSortMap));
			
			// 高级查询项目
			List<HighSearchDto> dtos = cachedService.getQupaiHighSearch(module, user.getOrgId(), user.getIssys());
			List<HighSearchDto> definedDtos = new ArrayList<HighSearchDto>();
			for(HighSearchDto dto : dtos){
				if(dto.getIsFiledSet() ==1 && dto.getIsQuery() == 1 ){
					definedDtos.add(dto);
				}
			}			
			request.setAttribute("definedSearchCodes",definedDtos); // 自定义非文本项查询集合
			request.setAttribute("definedSearchCodesJson",JsonUtil.getJsonString(definedDtos));
			// 查询需要隐藏的列序号
			List<String[]> columns = SearchListShowCodeDto.modult_17_List_1;
			if (0 == user.getIsState()) {
				columns = SearchListShowCodeDto.modult_17_List_0;
			}
			List<Integer> sorts = cachedService.getQupaiHideSortListCode(module, user.getOrgId(), user.getIsState().toString(), columns, 2);
			request.setAttribute("sorts", sorts);
			setIsReplaceWord(request);
			// List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
			// request.setAttribute("companyTrades", trades);
			request.setAttribute("item", leadInfoDto);
			
			// expireSetting(user, request);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "/qupai/lead/myLeads";
	}
	
	/**
	 * 放款信息查询
	 * @param request
	 * @param leadInfoDto
	 * @return
	 */
	@RequestMapping("/myLeadsData")
	@ResponseBody
	public Map<String, Object> myRestsData(HttpServletRequest request, CreditLeadInfoDto leadInfoDto){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			
			//leadInfoDto.setState(user.getIsState());
			List<CreditLeadInfoDto> leadInfoList = queryLeadInfoList(leadInfoDto, user);
			map.put("item", leadInfoDto);
			map.put("list", leadInfoList);
			map.put("serverDay", user.getServerDay());
			map.put("loginAcc", user.getAccount());
			map.put("fields", getQupaiIsQueryList(user.getOrgId()));
			Map<String, String> definedFieldMap = new HashMap<String, String>();
			
			List<CustFieldSet> fields = cachedService.getQupaiFieldSet(user.getOrgId());
			for (CreditLeadInfoDto leadInfo : leadInfoList) {
				for (CustFieldSet field : fields) {
					String fieldCode = field.getFieldCode();
					if (null != field && CustFieldSet.DATATYPE_MONEY == field.getDataType() && fieldCode.contains("define")) {

						try {
							PropertyDescriptor pd = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptor(leadInfo,
									fieldCode);
							
							if (null != pd) {
								Object value = org.apache.commons.beanutils.PropertyUtils.getProperty(leadInfo, fieldCode);
								if (null != value) {
									org.apache.commons.beanutils.BeanUtils.setProperty(leadInfo, fieldCode,
											moneyFormat(value.toString()));
								}
							}
						} catch (Exception e) {
							logger.error(e);
						}
					}
				}
			}
			
//			List<CustFieldSet> list = cachedService.getQupaiFiledSets(user.getOrgId());
//			for (CustFieldSet field : list) {
//				String fieldCode = field.getFieldCode();
//				if (fieldCode.contains("define")) {
//					definedFieldMap.put(fieldCode, "" + field.getDataType());
//				}
//			}
//			
//			map.put("definedFieldMap", definedFieldMap);
//			map.put("definedFieldMapJson", JsonUtil.getJsonString(definedFieldMap));
			
			map.put("signSetting", getSignSetting());
			map.put("isState", user.getIsState());
		} catch (Exception e) {
			logger.error("催放管理 - 放款信息异步加载数据异常！", e);
		}
		return map;
	}

	/**
	 * @param leadInfoDto
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private List<CreditLeadInfoDto> queryLeadInfoList(CreditLeadInfoDto leadInfoDto, ShiroUser user) throws Exception {
		initQueryCondition(leadInfoDto, user);
		
		//排序
		if(StringUtils.isNotBlank(leadInfoDto.getOrderKey())){
			String orderKey = leadInfoDto.getOrderKey()+",CREATE_DATE DESC,LEAD_ID DESC";
			leadInfoDto.setOrderKey(orderKey);
		} else {
			leadInfoDto.setOrderKey("CREATE_DATE DESC,LEAD_ID DESC");
		}
		//查出多选项查询字段
		List<String> multiSearchList = cachedService.getQupaiMultiSelectDefinedSearchFiled(user.getOrgId(),QupaiModule.LOAN_MNG.getModule());
		List<CreditLeadInfoDto> leadInfoList = creditLeadInfoService.getMyLeadListPage(leadInfoDto, multiSearchList, user.getIsState());
		//多选项显示
		if(leadInfoList.size() > 0){
			List<String> multiShowList = cachedService.getQupaiMultiSelectDefinedShowFiled(user.getOrgId(),QupaiModule.LOAN_MNG.getModule());
			List<String> singleShowList = cachedService.getQupaiSingleSelectDefinedShowFiled(user.getOrgId(),QupaiModule.LOAN_MNG.getModule());
			creditLeadInfoService.multiDefinedShowChange(leadInfoList,multiShowList,singleShowList,user.getOrgId());
		}
		Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
//			Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
		Map<String, String> deptName = cachedService.getOrgGroupNameMap(user.getOrgId());
//			Map<Integer, String> provinceMap = new HashMap<Integer, String>();
//			Map<Integer, String> cityMap = new HashMap<Integer, String>();
//			Map<Integer, String> countyMap = new HashMap<Integer, String>();
//			Map<String, String> tradeMap = new HashMap<String, String>();
//			if(user.getIsState() == 1){
//				provinceMap = cachedservice.getAreaMap(CachedNames.PROVINCE);
//				cityMap = cachedservice.getAreaMap(CachedNames.CITY);
//				countyMap = cachedservice.getAreaMap(CachedNames.COUNTY);
//				List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
//				tradeMap = cachedservice.changeOptionListToMap(trades);
//			}
		
		for (CreditLeadInfoDto leadInfo : leadInfoList) {
			leadInfo.setOwnerName(StringUtils.isNotBlank(leadInfo.getOwnerAcc()) ? nameMap.get(leadInfo.getOwnerAcc()) : "");
			leadInfo.setInchargeName(StringUtils.isNotBlank(leadInfo.getInchargeAcc()) ? nameMap.get(leadInfo.getInchargeAcc()) : "");
			// 此处处理前台展示时间字段，因使用fmt标签转换耗费时间，统一在后台转为字符串
			leadInfo.setBstartDate(leadInfo.getBorrowDate() != null ? DateUtil.formatDate(leadInfo.getBorrowDate(), DateUtil.Data_ALL) : "");
			leadInfo.setBendDate(leadInfo.getBorrowDate() != null ? DateUtil.formatDate(leadInfo.getBorrowDate(), DateUtil.DATE_DAY) : "");
			leadInfo.setRstartDate(leadInfo.getRepayDate() != null ? DateUtil.formatDate(leadInfo.getRepayDate(), DateUtil.Data_ALL) : "");
			leadInfo.setRendDate(leadInfo.getRepayDate() != null ? DateUtil.formatDate(leadInfo.getRepayDate(), DateUtil.DATE_DAY) : "");
			
			leadInfo.setShowdefined16(leadInfo.getDefined16() !=null?DateUtil.formatDate(leadInfo.getDefined16(), DateUtil.DATE_DAY): "");
			leadInfo.setShowdefined17(leadInfo.getDefined17() !=null?DateUtil.formatDate(leadInfo.getDefined17(), DateUtil.DATE_DAY): "");
			leadInfo.setShowdefined18(leadInfo.getDefined18() !=null?DateUtil.formatDate(leadInfo.getDefined18(), DateUtil.DATE_DAY): "");
			leadInfo.setImportDeptName(StringUtils.isNotBlank(leadInfo.getImportDeptId()) ? deptName.get(leadInfo.getImportDeptId()) : "");
		}
		return leadInfoList;
	}

	
	/**
	 * 千分位显示
	 * 
	 * @param value
	 * @return
	 */
	public static String moneyFormat(String value) {
		if (StringUtils.isBlank(value)) {
			return value;
		}
		try {
			DecimalFormat moneyFormat = new DecimalFormat("#,###");
			return moneyFormat.format(new BigDecimal(value));
		} catch (Exception e) {
			logger.error("格式千分位出错", e);
			return value;
		}
	}
	
	/**
	 * 初始化查询条件参数
	 * 
	 * @param leadInfoDto
	 * @param user
	 */
	private void initQueryCondition(CreditLeadInfoDto leadInfoDto, ShiroUser user) {
		leadInfoDto.setOrgId(user.getOrgId());
		
		// 创建人
		if (user.getIssys() != null && user.getIssys() == 1) {
			// 如果是管理人员
			String ownerType = leadInfoDto.getOwnerType();
			leadInfoDto.setOwnerType(StringUtils.isBlank(ownerType) ? "1" : ownerType);
			leadInfoDto.setAdminAcc(user.getAccount());
			ownerType = leadInfoDto.getOwnerType();
			if (ownerType.equals("1")  && StringUtils.isBlank(leadInfoDto.getOwnerAccsStr())) {
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())){
					accs.add(user.getAccount());
				}
				leadInfoDto.setOwnerAccs(accs);
			} else if (ownerType.equals("2") && StringUtils.isBlank(leadInfoDto.getOwnerAccsStr())) {
				leadInfoDto.setOwnerAcc(user.getAccount());
			} else {
				// 处理拥有者
				if (StringUtils.isNotBlank(leadInfoDto.getOwnerAccsStr())) {
					leadInfoDto.setOwnerAccs(Arrays.asList(leadInfoDto.getOwnerAccsStr().split(",")));
				} else {
					leadInfoDto.setOwnerAccsStr(user.getAccount());
					if (null==leadInfoDto.getOwnerAccs() || leadInfoDto.getOwnerAccs().isEmpty()) {
						leadInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
			}
		} else {
			// 如果是普通销售人员
			leadInfoDto.setOwnerAcc(user.getAccount());
		}
		
		
		// 负责人
		if (user.getIssys() != null && user.getIssys() == 1) {
			// 如果是管理人员
			String inchargeType = leadInfoDto.getInchargeType();
			leadInfoDto.setInchargeType(StringUtils.isBlank(inchargeType) ? "1" : inchargeType);
			inchargeType = leadInfoDto.getInchargeType();
			leadInfoDto.setAdminAcc(user.getAccount());
			if (inchargeType.equals("1")  && StringUtils.isBlank(leadInfoDto.getInchargeAccsStr())) {
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())){
					accs.add(user.getAccount());
				}
				leadInfoDto.setInchargeAccs(accs);
			} else if (inchargeType.equals("2") && StringUtils.isBlank(leadInfoDto.getInchargeAccsStr())) {
				leadInfoDto.setInchargeAcc(user.getAccount());
			} else {
				// 处理拥有者
				if (StringUtils.isNotBlank(leadInfoDto.getOwnerAccsStr())) {
					leadInfoDto.setInchargeAccs(Arrays.asList(leadInfoDto.getOwnerAccsStr().split(",")));
				} else {
					leadInfoDto.setInchargeAccsStr(user.getAccount());
					if (null==leadInfoDto.getInchargeAccs() || leadInfoDto.getInchargeAccs().isEmpty()) {
						leadInfoDto.setInchargeAccs(Arrays.asList(user.getAccount()));
					}
				}
			}
		} else {
			// 如果是普通销售人员
			leadInfoDto.setInchargeAcc(user.getAccount());
		}
		
		
		// 处理借款日
		Integer bDateType = leadInfoDto.getbDateType();
		if (bDateType != null && bDateType != 0 && bDateType != 5) {
			leadInfoDto.setBstartDate(getStartDateStr(bDateType));
			leadInfoDto.setBendDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 处理到期日
		Integer rDateType = leadInfoDto.getrDateType();
		if (rDateType != null && rDateType != 0 && rDateType != 5) {
			leadInfoDto.setRstartDate(getStartDateStr(rDateType));
			leadInfoDto.setRendDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
	}
	
	/**
	 * 新增放款信息
	 *
	 * @param request
	 * @param response
	 * @param
	 * @create 2015年11月17日 下午3:15:26 lixing
	 * @history
	 */
	@RequestMapping("/addLead")
	@ResponseBody
	public ResultObject addLead(HttpServletRequest request, HttpServletResponse response, CreditLeadInfoBean leadInfo) {
		ResultObject result = new ResultObject(ResultCode.SUCCESS.getStatus(), null, null);
		
		long timestamp = System.currentTimeMillis();
		Date currentDate = new Date();
		ShiroUser user = ShiroUtil.getShiroUser();
		String account = user.getAccount();
		String leadId = SysBaseModelUtil.getModelId();
		leadInfo.setLeadId(leadId);
		
		String orgId = user.getOrgId();
		int resType = ShiroUtil.getShiroUser().getIsState();
		logger.debug(String.format("addLead timestamp=%s, account=%s, resDto=%s", timestamp, account,
				JsonUtil.getJsonString(leadInfo)));

		try {
			result = validateLeadInfo(leadInfo, user);
			if (ResultCode.SUCCESS.getStatus() != result.getResultCode()) {
				return result;
			}

			leadInfo.setOrgId(orgId);
			leadInfo.setIsDel(DataDeleteStatus.NOT_DELETE);
			leadInfo.setOwnerAcc(account);
			leadInfo.setInchargeAcc(account);
			leadInfo.setCreateDate(currentDate);
			
			// 默认设置放款状态为待放款
			leadInfo.setStatus(CreditLeadInfoBean.STATUS_DOING); 
			leadInfo.setImportDeptId(user.getGroupId());
			leadInfo.setImportDeptName(user.getGroupName());
			
			int loanCount = creditLeadInfoService.queryLoanCount(orgId, leadInfo.getCardId());
			leadInfo.setBatch(loanCount + 1);
			leadInfo.setIsDel(DataDeleteStatus.NOT_DELETE);
			leadInfo.setSubmitTime(currentDate);
			creditLeadInfoService.create(leadInfo, resType);
			
			// 启动审核流程
			loanReviewService.init(user, leadInfo);

			if (resType == 1) {// 企业账号

			} else {// 个人账户

			}
			
			// 插入日志
			LogBean logBean = new LogBean(orgId, account, user.getName(),
					OperateEnum.LOG_LEAD_ADD.getCode(), OperateEnum.LOG_LEAD_ADD.getDesc(), 1,
					SysBaseModelUtil.getModelId());
			logBean.setModuleId(AppConstant.Module_id31);
			logBean.setModuleName(AppConstant.Module_Name31);
			logBean.setOperateId(AppConstant.Operate_id84);
			logBean.setOperateName(AppConstant.Operate_Name84);
			logBean.setContent(String.format("放款编号：%s, 客户名称：%s", leadInfo.getLeadCode(), leadInfo.getCustName()));

			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(leadId, "");
			logCustInfoService.addTableStoreLog(logBean, logMap);

			result.setResultCode(ResultCode.SUCCESS.getStatus());
			result.setResult(leadId);
		} catch (Exception e) {
			logger.error(String.format("addLead timestamp=%s, loginAcc=%s", timestamp, account), e);
			
			result.setResultCode(ResultCode.FAILURE.getStatus());
			result.setResultDesc("新增放款信息失败");
		}

		return result;
	}

	/**
	 * 放款信息保存验证逻辑
	 * 
	 * @param leadInfo
	 * @param user
	 * @return
	 */
	private ResultObject validateLeadInfo(CreditLeadInfoBean leadInfo, ShiroUser user) {
		ResultObject result = new ResultObject(ResultCode.SUCCESS.getStatus(), null, null);
		
		String orgId = user.getOrgId();
		String account = user.getAccount();
		String leadId = leadInfo.getLeadId();
		
		// List<CustFieldSet> fieldSet = cachedService.getQupaiFieldSet(orgId);
		// CustFieldSet cardIdField = CollectionUtil.get(fieldSet, "fieldCode", "cardId");
		
		// 根据身份证号查询其他负责人下所拥有的放款信息
		if (/*null!=cardIdField && 1==cardIdField.getIsRequired() && */!StringUtils.isEmpty(leadInfo.getCardId())) {
			CreditLeadInfoBean notCurrentAccLeadInfo = creditLeadInfoService.findNotCurrentAccLeadInfo(orgId, leadId, leadInfo.getCardId(), account);
			if (null != notCurrentAccLeadInfo) {
				String tmpAccount = notCurrentAccLeadInfo.getOwnerAcc();
				String tmpAccountName = userService.getByAccount(tmpAccount).getUserName();
				
				result.setResultCode(ResultCode.FAILURE.getStatus());
				result.setResultDesc(String.format("该身份证号码已被【%s（%s）】放款", tmpAccountName, tmpAccount));
			}
			
			// 查询是否有未处理的放款信息
			List<CreditLeadInfoBean> notProcessedLeadInfoList = creditLeadInfoService
					.findNotProcessedLeadInfoList(orgId, leadId, leadInfo.getCardId());
			if (null != notProcessedLeadInfoList && !notProcessedLeadInfoList.isEmpty()) {
				
				result.setResultCode(ResultCode.FAILURE.getStatus());
				result.setResultDesc("该身份证号存在驳回/待放款信息, 不能再添加该客户放款信息");
			}
		}
		
		// 放款编号验证, 同一机构下放款编号不能重复
		int countByLeadCode = creditLeadInfoService.countByLeadCode(orgId, leadId, leadInfo.getLeadCode());
		if (0 < countByLeadCode) {
			result.setResultCode(ResultCode.FAILURE.getStatus());
			result.setResultDesc("放款编号已存在");
		}
		
//		List<CustFieldSet> fieldSets = cachedService.getQupaiFiledSets(user.getOrgId());
//		for (CustFieldSet field : fieldSets) {
//			// 验证是否比填
//			try {
//				Object value = PropertyUtils.getProperty(leadInfo, field.getFieldCode());
//				if (1 == field.getIsRequired() && (null==value || StringUtils.isBlank(value.toString()))) {
//					result.setResult(ResultCode.FAILURE.getStatus());
//					result.setResultDesc(field.getFieldName() + "：为必填项！");
//					return result;
//				}
//			} catch (Exception e) {
//				
//			}
//		}
		
		
		// 验证字段长度是否超出
//		if (StringUtils.isNotEmpty(leadInfo.getLeadCode()) && 32<leadInfo.getLeadCode().length()) {
//			result.setResultCode(ResultCode.FAILURE.getStatus());
//			result.setResultDesc("放款编号长度超过32个字符");
//		}
//		
//		if (StringUtils.isNotEmpty(leadInfo.getCardId()) && 50<leadInfo.getCardId().length()) {
//			result.setResultCode(ResultCode.FAILURE.getStatus());
//			result.setResultDesc("身份证号长度超过50个字符");
//		}
		
		return result;
	}
	
	/**
	 * 查询贷款次数
	 *
	 * @param request
	 * @param response
	 * @param leadInfo
	 * @return
	 */
	@RequestMapping("/loanCount")
	@ResponseBody
	public ResultObject loanCount(HttpServletRequest request, HttpServletResponse response, CreditLeadInfoBean leadInfo) {
		long timestamp = System.currentTimeMillis();
		ResultObject result = new ResultObject(ResultCode.SUCCESS.getStatus(), null, null);

		// 根据身份证号查询客户贷款次数
		if (null == leadInfo || StringUtils.isEmpty(leadInfo.getCardId())) {
			result.setResultCode(ResultCode.FAILURE.getStatus());
			result.setResultDesc("请输入参数身份证号");
			return result;
		}

		ShiroUser user = ShiroUtil.getShiroUser();
		String cardId = leadInfo.getCardId();
		logger.debug(String.format("loanCount timestamp=%s, loginAcc=%s, orgId=%s, cardId=%s", timestamp,
				user.getAccount(), user.getOrgId(), cardId));


		try {
			int loanCount = creditLeadInfoService.queryLoanCount(user.getOrgId(), leadInfo.getCardId());

			result.setResultCode(ResultCode.SUCCESS.getStatus());
			result.setResult(loanCount);
		} catch (Exception e) {
			logger.error(String.format("loanCount timestamp=%s, loginAcc=%s", timestamp, user.getAccount()), e);
			result.setResultCode(ResultCode.FAILURE.getStatus());
			result.setResultDesc("接口异常");
		}

		return result;
	}
	
	/**
	 * 查询引用字段映射关系
	 * 
	 * @param request
	 * @param phone
	 * @return
	 */
	@RequestMapping("/refFieldMap")
	@ResponseBody
	public Map<String, Object> refFieldMap(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			List<RefFieldMapDto> list = queryRefFieldMap(user.getOrgId(), user.getIsState());
			
			result.put("fieldMap", list);
		} catch (Exception e) {
			logger.error(String.format("toAddLead loginAcc=%s", user.getAccount()), e);
			throw new SysRunException(e);
		}
		
		return result;
	}

	/**
	 * 查询引用字段映射关系
	 * 
	 * @param orgId
	 * @param isState
	 * @return
	 */
	private List<RefFieldMapDto> queryRefFieldMap(String orgId, Integer isState) {
		List<CustFieldSet> fieldSets = cachedService.getQupaiFiledSets(orgId);
		
		List<Object> values = new ArrayList<Object>();
		values.add(CustFieldSet.DATATYPE_REFER);
		fieldSets = CollectionUtil.equals(fieldSets, "dataType", values);
		
		
		List<RefFieldMapDto> list = new ArrayList<RefFieldMapDto>();
		for (CustFieldSet qupaiField : fieldSets) {
			RefFieldMapDto refFieldMap = new RefFieldMapDto();
			refFieldMap.setQupaiField(qupaiField);
			
			CustFieldSet resField = null;
			
			String qupaiFieldName = qupaiField.getFieldName();
			String resFieldCode = qupaiField.getFieldValue();
			if (StringUtils.isNotEmpty(resFieldCode)) {
				resField = this.getFieldCodeByCode(orgId, isState, resFieldCode);
			}
			
			if (null == resField) {
				if (qupaiFieldName.contains(CreditLeadInfoBean.FIELDNAME_KEY_CARDID)) {
					
					resField = this.getFieldCodeByKey(orgId, isState, CreditLeadInfoBean.FIELDNAME_KEY_CARDID);
				} else if (qupaiFieldName.contains(CreditLeadInfoBean.FIELDNAME_KEY_BANKCARD)) {
					resField = this.getFieldCodeByKey(orgId, isState, CreditLeadInfoBean.FIELDNAME_KEY_BANKCARD);
				} else if (qupaiFieldName.contains(CreditLeadInfoBean.FIELDNAME_KEY_OPENINGBANK)) {
					resField = this.getFieldCodeByKey(orgId, isState, CreditLeadInfoBean.FIELDNAME_KEY_OPENINGBANK);
				} else if ("phone".equals(qupaiField.getFieldCode())) {
					resField = this.getFieldCodeByCode(orgId, isState, "mobilephone");
					if (null == resField) {
						resField = new CustFieldSet();
						resField.setFieldCode("mobilephone");
						resField.setFieldName("常用电话");
						resField.setDataType(CustFieldSet.DATATYPE_TEXT);
						resField.setEnable((short)CustFieldSet.FIELD_ENABLED);
					}
				} else {
					resField = this.getFieldCodeByKey(orgId, isState, qupaiFieldName);
				}
			}
			
			refFieldMap.setResField(resField);
			list.add(refFieldMap);
		}
		return list;
	}
	
	
	/**
	 * 转发到"放款管理 - 新增"页面
	 * 
	 * @param request
	 * @param phone
	 * @return
	 */
	@RequestMapping("/toAddLead")
	public String toAddLead(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
//			String comFrom=request.getParameter("comFrom");
//			setCustWordCheck(request);
			setIsReplaceWord(request, user);
			setIsRead(request);
			List<CustFieldSet> fieldSets = null;
//			List<ResourceGroupBean> groupList = cachedService.getResGroupList(user.getOrgId());
//			request.setAttribute("groupList", groupList);
			int resType = ShiroUtil.getShiroUser().getIsState();
//			request.setAttribute("phone", formatPhone(phone, request));   
//			if(comFrom!=null&&!"".endsWith(comFrom)){
//				request.setAttribute("comFrom", comFrom);
//			}
			
//			OptionBean option=new OptionBean();
//			option.setOrgId(user.getOrgId());		
//			option.setItemCode("companyTrade");
//			option.setOrderKey("sort asc");
//			List<OptionBean> optionList = optionService.getListByCondtion(option);
//			request.setAttribute("optionList", optionList);
			
			fieldSets = cachedService.getQupaiFiledSets(user.getOrgId());
			setShowValue(fieldSets);
			
			if (resType == 1) {// 企业资源
				
			} else {// 个人资源
				
			}
			
			request.setAttribute("fieldSets", fieldSets);
			request.setAttribute("fieldSetsJson", JsonUtil.getJsonString(fieldSets));
			return "qupai/lead/add_lead";
		} catch (Exception e) {
			logger.error(String.format("toAddLead loginAcc=%s", user.getAccount()), e);
			throw new SysRunException(e);
		}
	}
	
	/**
	 * 设置单选字段、多选字段值
	 * 
	 * @param fieldSets
	 */
	public static final void setShowValue(List<CustFieldSet> fieldSets) {
		for (CustFieldSet custFieldSet : fieldSets) {
			Integer fieldType = custFieldSet.getDataType();
			if (fieldType == CustFieldSet.DATATYPE_RADIO || fieldType == CustFieldSet.DATATYPE_CHECK) {
				List<OptionBean> optionList = custFieldSet.getOptionList();
				if (null == optionList || optionList.isEmpty()) {
					continue;
				}
				
				for (OptionBean optionBean : optionList) {
					if (optionBean.getIsDefault() == OptionBean.IS_DEFAULT_YES) {
						custFieldSet.setShowValue(optionBean.getOptionlistId());
					}
				}
			}
		}
	}

	/**
	 * 设置自定义字段显示值
	 * 
	 * @param leadInfo
	 * @param fieldSets
	 * @throws Exception
	 */
	public void setShowValue(CreditLeadInfoBean leadInfo, List<CustFieldSet> fieldSets) throws Exception {
		if (leadInfo != null) {
			for (CustFieldSet custFieldSet : fieldSets) {
				String fieldCode = custFieldSet.getFieldCode();
				if (fieldCode.contains("defined")) {
					if ("defined16".equals(fieldCode) || "defined17".equals(fieldCode)
							|| "defined18".equals(fieldCode)) {
						Date date = (Date) PropertyUtils.getProperty(leadInfo, fieldCode);
						if (date != null) {
							String value = new SimpleDateFormat("yyyy-MM-dd").format(date);
							custFieldSet.setShowValue(value);
						}
					} else {
						String value = (String) PropertyUtils.getProperty(leadInfo, fieldCode);
						if (value != null) {
							custFieldSet.setShowValue(value);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 设置所有字段显示值
	 * 
	 * @param leadInfo
	 * @param fieldSets
	 * @throws Exception
	 */
	public void setAllFieldShowValue(CreditLeadInfoBean leadInfo, List<CustFieldSet> fieldSets) throws Exception {
		if (leadInfo != null) {
			for (CustFieldSet custFieldSet : fieldSets) {
				String fieldCode = custFieldSet.getFieldCode();
				
				// 没有对应的属性
				if (null == PropertyUtils.getPropertyDescriptor(leadInfo, fieldCode)) {
					continue;
				}
				
				Object srcValue = PropertyUtils.getProperty(leadInfo, fieldCode);
				if (null!=srcValue && srcValue instanceof Date) {
					String value = new SimpleDateFormat("yyyy-MM-dd").format(srcValue);
					custFieldSet.setShowValue(value);
				} else if (null!=srcValue) {
					custFieldSet.setShowValue(srcValue.toString());
				} else {
					custFieldSet.setShowValue("");
				}
			}
		}
	}
	
	/**
	 * 跳转到"放款管理 - 编辑"页面
	 *
	 * @param request
	 * @param leadId
	 * @return
	 * @history
	 */
	@RequestMapping("/toEditLead")
	public String toEditLead(HttpServletRequest request, String leadId) {
		List<CustFieldSet> fieldSets = null;
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			setCustWordCheck(request);
			setIsReplaceWord(request, user);
			setIsRead(request);
			CreditLeadInfoBean leadInfo = creditLeadInfoService.queryLeadInfoByOrgIdAndPk(user.getOrgId(), leadId);
			
//			OptionBean option=new OptionBean();
//			option.setOrgId(user.getOrgId());		
//			option.setItemCode("companyTrade");
//			option.setOrderKey("sort asc");
//			List<OptionBean> optionList = optionService.getListByCondtion(option);
//			request.setAttribute("optionList", optionList);
			
			int resType = ShiroUtil.getShiroUser().getIsState();
			if (resType == 1) {// 企业资源
				fieldSets = cachedService.getQupaiFiledSets(user.getOrgId());
			} else {// 个人资源
				fieldSets = cachedService.getQupaiFiledSets(user.getOrgId());
			}
			
			setAllFieldShowValue(leadInfo, fieldSets);
			
			// 查询审核结果
			TsmLoanReviewInfoDto reviewInfo = loanReviewService.getReviewInfo(leadId);
			if (null != reviewInfo) {
				request.setAttribute("reviewAuth", reviewInfo.getReviewAuth()); //审核已完成
				request.setAttribute("item", reviewInfo);
				request.setAttribute("list", reviewInfo.getReviewList());
			}
			
			request.setAttribute("leadInfo", leadInfo);
			request.setAttribute("issys", user.getIssys());
			request.setAttribute("fieldSets", fieldSets);
			request.setAttribute("fieldSetsJson", JsonUtil.getJsonString(fieldSets));
			
			return "qupai/lead/add_lead";
		} catch (Exception e) {
			logger.error(String.format("loginAcc=%s, leadId=%s", user.getAccount(), leadId) , e);
			throw new SysRunException(e);
		}
	}
	
	/**
	 * 修改放款信息
	 * 
	 * @param request
	 * @param response
	 * @param leadInfoBean
	 * @return
	 */
	@RequestMapping("/editLead")
	@ResponseBody
	public ResultObject editLead(HttpServletRequest request, HttpServletResponse response, CreditLeadInfoBean leadInfoBean) {
		ResultObject result = new ResultObject(ResultCode.SUCCESS.getStatus(), null, null);
		
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			result = validateLeadInfo(leadInfoBean, user);
			if (ResultCode.SUCCESS.getStatus() != result.getResultCode()) {
				return result;
			}
			
			if (user.getIsState() != 1) {
//				if (StringUtils.isNotEmpty(leadInfoBean.getMobilephone())) {
//					leadInfoBean.setMobilephone(StringUtils.toCheckPhone(formatPhone(leadInfoBean.getMobilephone(), request)));
//				}
//				if (StringUtils.isNotEmpty(leadInfoBean.getTelphone())) {
//					leadInfoBean.setTelphone(StringUtils.toCheckPhone(formatPhone(leadInfoBean.getTelphone(), request)));
//				}
			}
			
//			leadInfoBean.setUpdateAcc(user.getAccount());
//			leadInfoBean.setUpdateDate(new Date());
			leadInfoBean.setOrgId(user.getOrgId());
			// 去除空格
//			leadInfoBean.setName(StringUtils.isNotBlank(leadInfoBean.get) ? leadInfoBean.getName().trim() : "");
//			leadInfoBean.setCompany(StringUtils.isNotBlank(leadInfoBean.getCompany()) ? leadInfoBean.getCompany().trim() : "");
//			leadInfoBean.setUnithome(StringUtils.isNotBlank(leadInfoBean.getUnithome()) ? leadInfoBean.getUnithome().trim() : "");
//			leadInfoBean.setState(user.getIsState());
			creditLeadInfoService.modify2(leadInfoBean, user.getIsState());
			
			loanReviewService.updateInit(user, leadInfoBean);
			
			result.setResultCode(ResultCode.SUCCESS.getStatus());
			
		} catch (Exception e) {
			logger.error(String.format("loginAcc=%s, leadInfoBean=%s" , user.getAccount(), JsonUtil.getJsonString(leadInfoBean)), e);
			
			result.setResultCode(ResultCode.FAILURE.getStatus());
			result.setResultDesc("编辑放款信息失败");
		}
		
		return result;
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
	 * 删除驳回状态放款信息
	 * 
	 * @param ids 驳回状态的放款信息记录
	 * @return
	 */
	@RequestMapping("/delBatchLead")
	@ResponseBody
	public ResultObject delBatchLead(String ids) {

		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			if (null == user) {
				ResultObject result = new ResultObject(ResultCode.FAILURE.getStatus(), "请使用授权账号登陆系统", null);
				return result;
			}
			
			List<String> leadIds = StringUtil.toList(ids, StringUtil.DEFAULT_SPLIT);

			List<CreditLeadInfoDto> leadInfoList = creditLeadInfoService.findLeadInfoList(user.getOrgId(), leadIds);
			// 判断选中数据存在【已放款】状态
			boolean doneFlag = CollectionUtil.exists(leadInfoList, "status", CreditLeadInfoBean.STATUS_DONE);
			if (doneFlag) {
				ResultObject result = new ResultObject(ResultCode.FAILURE.getStatus(), "已放款记录不可删除", null);
				return result;
			}

			// 判断选中数据存在【待放款】状态
			boolean doingFlag = CollectionUtil.exists(leadInfoList, "status", CreditLeadInfoBean.STATUS_DOING);
			if (doingFlag) {
				ResultObject result = new ResultObject(ResultCode.FAILURE.getStatus(), "待放款记录不可删除", null);
				return result;
			}

			
			CreditLeadInfoDto leadInfoDto = new CreditLeadInfoDto();
			leadInfoDto.setLeadIds(leadIds);
			leadInfoDto.setIsDel(DataDeleteStatus.IS_DELETED);
			leadInfoDto.setOrgId(user.getOrgId());

			creditLeadInfoService.delBatchLead(leadInfoDto);

			String context = String.format("%s条放款信息", leadIds.size());

			Map<String, Object> idContextMap = new HashMap<String, Object>();
			for (String id : leadIds) {
				idContextMap.put(id, "");
			}

			logBatchInfoService.saveLog(leadInfoDto.getOrgId(), user.getAccount(), user.getName(),
					OperateEnum.LOG_DELETE, AppConstant.Module_id31, AppConstant.Module_Name31,
					AppConstant.Operate_id5, AppConstant.Operate_Name5, context, "", leadIds, "",
					SysBaseModelUtil.getModelId(), idContextMap);

			ResultObject result = new ResultObject(ResultCode.SUCCESS.getStatus(), null, null);
			return result;
		} catch (Exception e) {
			logger.error("删除驳回状态放款信息失败", e);
			ResultObject result = new ResultObject(ResultCode.FAILURE.getStatus(), "删除驳回状态放款信息失败", null);
			return result;
		}
	}
	
	
	/**
	 * 跳转"放款管理 - 导出"页面
	 * 
	 * @param request
	 * @param leadInfoDto
	 * @return
	 */
	@RequestMapping("/toExport")
	public String toExport(HttpServletRequest request, CreditLeadInfoDto leadInfoDto) {
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			request.setAttribute("leadInfoDto", JsonUtil.getJsonString(leadInfoDto));
			return "qupai/lead/export";
		} catch (Exception e) {
			logger.error(String.format("toExport loginAcc=%s", user.getAccount()), e);
			throw new SysRunException(e);
		}
	}
	
	/**
	 * [催放管理 - 放款管理 - 导出] 读取导出字段列表
	 * 
	 * @param request
	 * @param leadInfoDto
	 * @return
	 */
	@RequestMapping("/exportFields")
	@ResponseBody
	public Map<String, Object> exportFields(HttpServletRequest request, CreditLeadInfoDto leadInfoDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String orgId = user.getOrgId();

			List<CustSearchSet> fieldSets = cachedService.getQupaiShowCustSearchSet(QupaiModule.LOAN_MNG.getModule(), orgId);
			fieldSets = CollectionUtil.remove(fieldSets, "dataType", CustSearchSet.DATATYPE_PICTURE);
			
			if (0 == user.getIsState()) {	// 个人客户需要导入单位名称
				List<CustSearchSet> tmpFieldSets = new ArrayList<CustSearchSet>();
				CustSearchSet companySearchSet = new CustSearchSet();
				companySearchSet.setDevelopCode("company");
				companySearchSet.setSearchCode("company");
				companySearchSet.setSearchName("单位名称");
				companySearchSet.setDataType(1);
				companySearchSet.setEnable(CustSearchSet.ENABLE_YES);
				companySearchSet.setIsShow(CustSearchSet.IS_SHOW_YES);
				
				boolean custNameSearchSetExists = false;
				for (CustSearchSet searchSet : fieldSets) {
					if ("custName".equals(searchSet.getDevelopCode())) {
						tmpFieldSets.add(companySearchSet);
						custNameSearchSetExists = true;
					}
					
					tmpFieldSets.add(searchSet);
				}
				if (!custNameSearchSetExists) {
					tmpFieldSets.add(0, companySearchSet);
				}
				fieldSets = tmpFieldSets;
			}
			
			// 查询待导出记录数目
			this.initQueryCondition(leadInfoDto, user);
			Integer totalCount = creditLeadInfoService.findExportCount(leadInfoDto);

			map.put("success", true);
			map.put("isState", user.getIsState());
			map.put("fields", fieldSets);
			map.put("totalCount", totalCount);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "读取导出字段失败！");
			logger.error("[催放管理 - 放款管理 - 导出] 读取导出字段失败", e);
		}
		return map;
	}
	
//	/**
//	 * 查询导出数据量
//	 * 
//	 * @param leadInfoDto
//	 * @return
//	 */
//	@RequestMapping("/exportCount")
//	@ResponseBody
//	public Map<String, Object> exportCount(CreditLeadInfoDto leadInfoDto) {
//		Integer totalCount = 0;
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			ShiroUser user = ShiroUtil.getShiroUser();
//			
//			totalCount = creditLeadInfoService.findExportCount(leadInfoDto);
//
//			map.put("success", true);
//		} catch (Exception e) {
//			map.put("success", false);
//			logger.error("[催放管理 - 放款管理 - 导出] 查询导出数量失败！", e);
//		}
//		map.put("totalCount", totalCount);
//		return map;
//	}
	
	/**
	 * 放款信息 - 导出 - 确定
	 * 
	 * @param request
	 * @param response
	 * @param leadInfoDto     查询对象
	 * @param exportColumnStr 需要导出的字段, 逗号分隔
	 */
	@RequestMapping("/exportData")
	@ResponseBody
	public void exportData(HttpServletRequest request, HttpServletResponse response, CreditLeadInfoDto leadInfoDto, String exportColumnStr) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			
			List<CreditLeadInfoDto> list = new ArrayList<CreditLeadInfoDto>();
			List<String> columns = Arrays.asList(exportColumnStr.split(","));
			List<String> exportColumns = new ArrayList<String>();
			Map<String, String> fieldMap = getFiledMap(QupaiModule.LOAN_MNG.getModule(), user.getOrgId(), user.getIsState());	// getFiledMap(user.getOrgId(), user.getIsState());
			List<String> heads = new ArrayList<String>();
			for (String column : columns) {
				heads.add(fieldMap.get(column));
//				if (column.equals("comArea")) {
//					exportColumns.add("losingRemark");
//				} else {
					exportColumns.add(column);
//				}
			}

			while(true) {
				List<CreditLeadInfoDto> tmpList = this.queryLeadInfoList(leadInfoDto, user);
				if (tmpList.isEmpty()) {
					break;
				}
				list.addAll(tmpList);
				
				Page page = leadInfoDto.getPage();
				if (page.getCurrentPage() + 1 > page.getTotalPage()) {
					break;
				} else {
					page.setCurrentPage(page.getCurrentPage() + 1);
				}
				
			}
			

			if (!list.isEmpty()) {
				String downloadFileName = URLEncoder.encode("放款信息列表.xlsx","UTF-8");
				
				response.setContentType("application/vnd.ms-excel");    
				response.setHeader("Content-disposition", String.format("attachment;filename=%s", downloadFileName));
				
				creditLeadInfoService.export(response.getOutputStream(), list, exportColumns, heads, user.getOrgId(), user.getAccount());
			} else {
				logger.error("没有数据需要导出");
			}
			
		} catch (Exception e) {
			logger.error("数据导出失败！", e);
		}
	}
	
	/**
	 * 查询字段映射关系
	 * 
	 * @param orgId
	 * @param isState
	 * @return
	 */
	public Map<String, String> getFiledMap(String orgId, Integer isState){
//		List<CustFieldSet> fieldSets = new ArrayList<CustFieldSet>();
//		Map<String, String> map = new HashMap<String, String>();
//		fieldSets = cachedService.getQupaiFieldSet(orgId);
//		
//		for(CustFieldSet fieldSet : fieldSets){
//			if(fieldSet.getEnable() == 1){
//				map.put(fieldSet.getFieldCode(), fieldSet.getFieldName());
//			}
//		}
		
		List<CustSearchSet> fieldSets = new ArrayList<CustSearchSet>();
		Map<String, String> map = new HashMap<String, String>();
		fieldSets = cachedService.getQupaiCustSearchSetList(orgId);
		
		for(CustSearchSet fieldSet : fieldSets){
			if(fieldSet.getEnable() == CustSearchSet.ENABLE_YES){
				map.put(fieldSet.getDevelopCode(), fieldSet.getSearchName());
			}
		}
		
		
		return map;
	}
	
	/**
	 * 查询字段映射关系
	 * 
	 * @param orgId
	 * @param isState
	 * @return
	 */
	public Map<String, String> getFiledMap(String module, String orgId, Integer isState){
		List<CustSearchSet> fieldSets = new ArrayList<CustSearchSet>();
		Map<String, String> map = new HashMap<String, String>();
		fieldSets = cachedService.getQupaiShowCustSearchSet(module, orgId);
		
		for(CustSearchSet fieldSet : fieldSets){
			map.put(fieldSet.getDevelopCode(), fieldSet.getSearchName());
		}
		
		if (0 == isState) {// 个人客户
			map.put("company", "单位名称");
		}

		return map;
	}
	
	/**
	 * 跳转"放款管理 - 放款详情"
	 * 
	 * @param request
	 * @param leadId	放款信息主键
	 * @return
	 */
	@RequestMapping("/leadInfoDetail")
	public String leadInfo(HttpServletRequest request, String leadId){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String orgId = user.getOrgId();

			//设置是否模糊处理电话号码
			setIsReplaceWord(request);
			CreditLeadInfoBean leadInfoBean = creditLeadInfoService.queryLeadInfoByOrgIdAndPk(orgId,leadId);
			List<CustFieldSet> fieldSets = cachedService.getQupaiFieldSet(orgId);
			
			CreditLeadInfoDto leadInfoDto = new CreditLeadInfoDto();
			BeanUtils.copyProperties(leadInfoBean, leadInfoDto);
			leadInfoDto = ExtColumnUtil.getExtData(fieldSets, leadInfoDto);
			this.setAllFieldShowValue(leadInfoDto, fieldSets);
			
			// 查询审核结果
			TsmLoanReviewInfoDto reviewInfo = loanReviewService.getReviewInfo(leadId);
			if (null != reviewInfo) {
				request.setAttribute("reviewAuth", reviewInfo.getReviewAuth()); //审核已完成
				request.setAttribute("item", reviewInfo);
				request.setAttribute("list", reviewInfo.getReviewList());
			}
						
			request.setAttribute("leadInfo",leadInfoDto);
			request.setAttribute("fieldSets", fieldSets);
			request.setAttribute("shiroUser", user);
			// commonOwnerAuth(request);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "qupai/lead/lead_info_detail";
	}
	
	/**
	 * 跳转"放款管理 - 放款详情"
	 * 
	 * @param request
	 * @param leadId	放款信息主键
	 * @return
	 */
	@RequestMapping("/defaultLeadCode")
	@ResponseBody
	public Object defaultLeadCode(){
		ShiroUser user = ShiroUtil.getShiroUser();
		String orgId = user.getOrgId();

		return cachedService.getLeadCode(orgId);
	}
	
	
	public static void main(String[] args) {
//		CreditLeadInfoBean leadInfoBean = new CreditLeadInfoBean();
//		leadInfoBean.setCardId("18758862675");
//		CreditLeadInfoDto leadInfoDto = new CreditLeadInfoDto();
//		
//		BeanUtils.copyProperties(leadInfoBean, leadInfoDto);
//		
//		System.out.println(leadInfoDto.getCardId());
		
//		List<String> list = new ArrayList<String>();
//		list.add("haha1");
//		list.add("haha2");
//		list.add(0, "haha0");
//		System.out.println(list);
		
		System.out.println(moneyFormat("148736458"));
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
			List<ResCustInfoDto> custs = resCustInfoService.getQypaiSignCustListPage(resCustInfoDto,multiSearchList);
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
				provinceMap = cachedService.getAreaMap(CachedNames.PROVINCE);
				cityMap = cachedService.getAreaMap(CachedNames.CITY);
				countyMap = cachedService.getAreaMap(CachedNames.COUNTY);
				List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
				tradeMap = cachedService.changeOptionListToMap(trades);
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
	 * 计划页面调用的时候添加参数
	 * 
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
}
