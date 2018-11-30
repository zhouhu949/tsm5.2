package com.qftx.tsm.contract.scontrol;

import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.*;
import com.qftx.common.util.constants.SysConstant;
import com.qftx.tsm.contract.bean.ContractBean;
import com.qftx.tsm.contract.bean.ContractOrderBean;
import com.qftx.tsm.contract.dto.ContractDto;
import com.qftx.tsm.contract.dto.ContractFileDto;
import com.qftx.tsm.contract.service.ContractService;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.TaoTagBean;
import com.qftx.tsm.cust.bean.TsmCustGuide;
import com.qftx.tsm.cust.dao.ComResourceMapper;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.cust.dto.SignDto;
import com.qftx.tsm.cust.service.ResCustEventService;
import com.qftx.tsm.cust.service.ResCustInfoDetailService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.follow.bean.CustFollowBean;
import com.qftx.tsm.follow.service.CustFollowService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.main.service.MainService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dao.OptionMapper;
import com.qftx.tsm.tao.service.TaoService;
import org.apache.log4j.Logger;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 合同管理
 */
@Controller
@RequestMapping("/contract")
public class ContractAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ContractAction.class);
	
	@Autowired
	private ContractService contractService;
	@Autowired
	private TaoService taoCustService;
	@Autowired
	private CustFollowService custFollowService;
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private OptionMapper optionMapper;
	@Autowired
	private ComResourceMapper comResourceMapper;
	@Autowired
	private ResCustInfoDetailService resCustInfoDetailService;
	@Autowired
	private ResCustEventService resCustEventService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrgGroupUserService orgGroupUserService;
	@Autowired
	private OrgGroupService orgGroupService;
	@Resource
	private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Resource
	private MainService mainService;
	@Autowired
	private LogUserOperateService logUserOperateService;
	@RequestMapping("/list")
	public String index(HttpServletRequest request, ContractDto contractDto) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("shiroUser", user);
			request.setAttribute("contractDto", contractDto);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "contract/contract_list";
	}

	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request, ContractDto contractDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			contractDto.setOrgId(user.getOrgId());
			if (user.getIssys() != null && user.getIssys() == 1) {
				contractDto.setOsType(StringUtils.isBlank(contractDto.getOsType()) ? "1" : contractDto.getOsType());
				if(contractDto.getOsType().equals("1")){
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
					contractDto.setOwnerAccs(accs);
				}else if(contractDto.getOsType().equals("2")){
					contractDto.setOwnerAcc(user.getAccount());
				}else{
					if (StringUtils.isNotBlank(contractDto.getOwnerAccsStr())) {
						contractDto.setOwnerAccs(Arrays.asList(contractDto.getOwnerAccsStr().split(",")));
					} else {
						contractDto.setOwnerAccsStr(user.getAccount());
						contractDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
			} else {
				contractDto.setOwnerAcc(user.getAccount());
			}
			
			if(StringUtils.isNotBlank(contractDto.getSignAccsStr())){
				contractDto.setSignAccs(Arrays.asList(contractDto.getSignAccsStr().split(",")));
			}
			
			if (contractDto.getsDateType() != null && contractDto.getsDateType() != 0 && contractDto.getsDateType() != 5) {
				contractDto.setStartSignDate(getStartDateStr(contractDto.getsDateType()));
				contractDto.setEndSignDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}

			if (contractDto.geteDateType() != null && contractDto.geteDateType() != 0 && contractDto.geteDateType() != 5) {
				if(contractDto.geteDateType() == 4){
					contractDto.setStartEffecDate(getStartDateStr(contractDto.geteDateType()));
					contractDto.setEndEffecDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
				}else{
					contractDto.setStartEffecDate(getStartDateStr(contractDto.geteDateType()));
					contractDto.setEndEffecDate(getEndDateStr(contractDto.geteDateType()));
				}
			}

			if (contractDto.getiDateType() != null && contractDto.getiDateType() != 0 && contractDto.getiDateType() != 5) {
				if(contractDto.getiDateType() == 4){
					contractDto.setStartInvalidDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
					contractDto.setEndInvalidDate(getEndDateStr(contractDto.getiDateType()));
				}else{
					contractDto.setStartInvalidDate(getStartDateStr(contractDto.getiDateType()));
					contractDto.setEndInvalidDate(getEndDateStr(contractDto.getiDateType()));
				}
			}
			if(getCommonOwnerOpenState(user.getOrgId()) == 1){//如果开启共有者  设置查询共有者
				contractDto.setCommonAcc(user.getAccount());
			}
			if(StringUtils.isBlank(contractDto.getNoteType())) contractDto.setNoteType("1");
			List<ContractDto> contractDtos = contractService.getUnitContractListPage(contractDto);
			if(contractDtos.size() > 0){
				Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
				Map<String, String> groupMap = cachedService.getOrgGroupNameMap(user.getOrgId());
				for(ContractDto cd : contractDtos){
					cd.setOwnerName(nameMap.get(cd.getOwnerAcc()) == null ? cd.getOwnerAcc() : nameMap.get(cd.getOwnerAcc()));
					cd.setGroupName(groupMap.get(cd.getGroupId()) == null ? "" : groupMap.get(cd.getGroupId()));
				}
			}
			
			map.put("list", contractDtos);
			map.put("item", contractDto);
			map.put("shiroUser", ShiroUtil.getShiroUser());
			int commonOnwerSign = 0;
			if(getCommonOwnerOpenState(user.getOrgId()) == 1){
				commonOnwerSign = getCommonOwnerSignAuth(user.getOrgId());
			}
			map.put("commonOnwerSign", commonOnwerSign);
		} catch (Exception e) {
			logger.error("合同管理异常，msg="+e.getMessage(),e);
		}
		return map;
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request, String custId, String jsonStr,String module) throws UnsupportedEncodingException {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			ResCustInfoBean custInfoBean = resCustInfoService.getCustInfoByOrgIdAndPk(ShiroUtil.getShiroUser().getOrgId(), custId);
			request.setAttribute("custInfoBean", custInfoBean);
			
			String baseStr = getBASE64(jsonStr);
			request.setAttribute("jsonStr", baseStr);
			request.setAttribute("module", module);
			/** 来自哪里 :1--客户跟进 */
			String areFrom = request.getParameter("areFrom");
			if (StringUtils.isBlank(areFrom) || "undefined".equals(areFrom)) { // 如果为空，默认为0
				areFrom = "0";
			}
			request.setAttribute("areFrom", areFrom);
			setCustomFiled(user, request);
			if(!CachedService.LOCK_MAP.containsKey(user.getOrgId())){
				CachedService.LOCK_MAP.put(user.getOrgId(), user.getOrgId());
			}
			String contractCode = cachedService.getContractCode(CachedService.LOCK_MAP.get(user.getOrgId()));
			request.setAttribute("contractCode", contractCode);
			putSignUserInfo(request, user.getOrgId(), custInfoBean.getOwnerAcc());
			request.setAttribute("tsmUpLoadServiceUrl", ConfigInfoUtils.getStringValue("tsm_upload_service_url")); // 上传服务器路径
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "contract/contract_add";
	}

	public void putSignUserInfo(HttpServletRequest request,String orgId,String ownerAcc){
		User user = userService.getByAccount(ownerAcc);
		if(user != null){
			OrgGroupUser usermeber = new OrgGroupUser();
			usermeber.setOrgId(user.getOrgId());
			usermeber.setUserId(user.getUserId());
			OrgGroupUser newmember = orgGroupUserService.getByCondtion(usermeber);
			if (newmember != null) {
				user.setGroupId(newmember.getGroupId());
				OrgGroup orgGroup = orgGroupService.getByGroupId(user.getOrgId(),newmember.getGroupId());
				if(orgGroup != null){
					user.setGroupName(orgGroup.getGroupName());
				}
			}
		}
		request.setAttribute("signUser", user);
	}
	
	@RequestMapping("/toCancle")
	public String toCancle(HttpServletRequest request, String caid, String custId) {
		request.setAttribute("caid", caid);
		request.setAttribute("custId", custId);
		return "contract/cancle_contract";
	}

	@RequestMapping("/del")
	@ResponseBody
	public String del(HttpServletRequest request, HttpServletResponse response, String id, String custId, String cancleRemark,String isRemoveOrders) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			contractService.deleteContractBatch(user, id, custId, cancleRemark,isRemoveOrders,true);
			return "1";
		} catch (Exception e) {
			logger.error("删除合同异常", e);
			return "-1";
		}
	}

	@RequestMapping("/sign")
	@ResponseBody
	public String sign(HttpServletRequest request,HttpServletResponse response,String custId,String module){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			contractService.sign(custId, user,module,null);
			return "1";
		} catch (Exception e) {
			logger.error("直接签约失败", e);
			return "-1";
		}
	}
	
	@RequestMapping("/unSign")
	@ResponseBody
	public String unSign(HttpServletRequest request,HttpServletResponse response,String custId){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			contractService.unSign(custId, user);
			return "1";
		} catch (Exception e) {
			logger.error("取消签约失败", e);
			return "-1";
		}
	}
	/**
	 * 保存合同
	 * 
	 * @param request
	 * @param response
	 * @param contractBean
	 * @create 2015年12月26日 下午12:48:32 lixing
	 * @history
	 */
	@RequestMapping("/saveAdd")
	@ResponseBody
	public String saveAdd(HttpServletRequest request, HttpServletResponse response, ContractBean contractBean, String jsonStr) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			contractBean.setInputtime(new Date());
			contractBean.setId(SysBaseModelUtil.getModelId());
			contractBean.setOrgId(user.getOrgId());
			contractBean.setUserId(user.getAccount());
			String fileIdsStr = request.getParameter("fileIdsStr");
			String module = request.getParameter("module");
			List<String> fileIds = new ArrayList<String>();
			if (StringUtils.isNotBlank(fileIdsStr)) {
				for (String fileId : fileIdsStr.split(",")) {
					if (StringUtils.isNotBlank(fileId)) {
						fileIds.add(fileId);
					}
				}
			}
			String areFrom = request.getParameter("areFrom"); // 来自哪里 :1--客户跟进
			Object[] obj = null;
			SignDto signDto = null;
			String saleProc = null;
			if (StringUtils.isNotBlank(areFrom) && !"undefined".equals(areFrom) && "1".equals(areFrom)) {
				// 保存客户跟进
				obj = (Object[]) ShiroUtil.getSession(SysConstant._FOLLOW_SIGN);
				if(obj != null){
					CustFollowBean follow = (CustFollowBean)obj[3];
					if(follow != null) saleProc = follow.getSaleProcessId();
				}
			}
			
			if (StringUtils.isNotBlank(jsonStr)) {
				jsonStr = getFromBASE64(jsonStr);
				signDto = (SignDto) JsonUtil.getObjectJsonString(jsonStr, SignDto.class);
				if(signDto != null){
					saleProc = signDto.getSaleProcessId();
				}
			}
			
			int rt = contractService.addContract(contractBean, fileIds,user,saleProc,module,obj,signDto);
			
			//跟进
			if (obj != null) {
				custFollowService.saveCustFollow((String) obj[0], (String) obj[1], (TsmCustGuide) obj[2], (CustFollowBean) obj[3], (Boolean) obj[4], false,user,null);
				ShiroUtil.removeSession(SysConstant._FOLLOW_SIGN);
			}
			
			if (signDto != null) {
				String reqId = SysBaseModelUtil.getModelId();
				logger.debug("reqId=" + reqId + "。jsonStr=" + jsonStr);
				saveOptLog(reqId, signDto, user.getOrgId(), user.getAccount(), user.getIsState(), user.getName());
			}

			return contractBean.getId()+"_"+rt;
		} catch (Exception e) {
			logger.error("保存合同失败", e);
			return "-1";
		}
	}

	public void saveOptLog(String reqId, SignDto signDto, String orgId, String account, Integer isState, String userName) {
		ResCustInfoBean custInfo = new ResCustInfoBean();
		CustFollowBean custFollow = new CustFollowBean();
		TsmCustGuide custGuide = new TsmCustGuide();
		String suitProcId = signDto.getSuitProcId();
		custInfo.setResCustId(signDto.getResCustId());
		custInfo.setIsConcat(1);
		TaoTagBean tagBean = new TaoTagBean();
		tagBean.setGroupId(signDto.getGroupId());
		tagBean.setIsConcat(new Integer(signDto.getIsConcat()));
		tagBean.setOrderType(signDto.getOrderType());
		tagBean.setAccount(account);
		tagBean.setOrgId(orgId);
		tagBean.setResourceId(signDto.getResCustId());
		custFollow.setCustFollowId(signDto.getCustFollowId());
		custFollow.setLabelCode(signDto.getLabelCode());
		custFollow.setLabelName(signDto.getLabelName());
		custFollow.setFeedbackComment(signDto.getFeedbackComment());
		custFollow.setEffectiveness(new Integer(signDto.getEffectiveness()));
		custFollow.setSaleProcessId(signDto.getSaleProcessId());
		custFollow.setFollowDate(signDto.getFollowDate() == "" ? null : DateUtil.parseDateToHMS(signDto.getFollowDate()));
		custFollow.setActionType("1");
		custFollow.setCustStatus(6);
		custFollow.setCustTypeId(signDto.getCustTypeId());
		custFollow.setFollowType((signDto.getFollowType() == null || "".equals(signDto.getFollowType())) ? "1" : signDto.getFollowType());
		custGuide.setCompetitor(signDto.getCompetitor());
		custGuide.setCustArgue(signDto.getCustArgue());
		custGuide.setCustId(signDto.getResCustId());
		custGuide.setCustInterest(signDto.getCustInterest());
		custGuide.setCustTypeId(signDto.getCustTypeId());
		custGuide.setExpectDate(signDto.getExpectDate() == "" ? null : DateUtil.parseDateToHMS(signDto.getExpectDate()));
		custGuide.setExpectSale((signDto.getExpectSale() == null || "".equals(signDto.getExpectSale())) ? null : new Long(signDto.getExpectSale()));
		custGuide.setRemark(signDto.getRemark());
		custGuide.setSaleProcessId(signDto.getSaleProcessId());
		custGuide.setSaleWay(signDto.getSaleWay());
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("resId", custInfo.getResCustId());
		resultMap.put("orgId", ShiroUtil.getUser().getOrgId());
		ResCustDto resCustDto = comResourceMapper.findResById(resultMap);
		custInfo.setLabelCode(signDto.getLabelCode());
		custInfo.setLabelName(signDto.getLabelName());
		custInfo.setName(resCustDto.getName());
		if (resCustDto == null) {
			resCustDto = new ResCustDto();
		}
		TaoTagBean tagbean = cachedService.getLoction(orgId, account);
		String pool = "1";
		if (tagBean != null) {
			pool = tagBean.getPool();
		}
		taoCustService.saveOptLog(reqId, orgId, account, custInfo, custFollow, custGuide, suitProcId, tagBean, "2", signDto.getConcatPhone(),
				signDto.getConcatName(), resCustDto.getBirthday(), pool,isState,userName);
		Map<String, Object> jsonMap = splitjsonMap(signDto.getResCustId(), custFollow, orgId, account, isState, userName);
		resCustEventService.create(orgId, signDto.getResCustId(), "1", JsonUtil.getJsonString(jsonMap));
	}

	public Map<String, Object> splitjsonMap(String custId, CustFollowBean custFollow, String orgId, String account, Integer isState, String userName) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(custId)) {
			Map<String, String> resMap = new HashMap<String, String>();
			resMap.put("resId", custId);
			resMap.put("orgId", orgId);
			ResCustDto resCustDto = comResourceMapper.findResById(resMap);
			OptionBean optionBean = optionMapper.getByPrimaryKeyAndOrgId(custFollow.getSaleProcessId(), orgId);
			if (optionBean == null) {
				optionBean = new OptionBean();
			}
			if (isState == 1) {
				jsonMap.put("telphone", custFollow.getCustDetailMobile());
			} else {
				jsonMap.put("telphone", StringUtils.isEmpty(resCustDto.getMobilephone()) ? resCustDto.getTelphone() : resCustDto.getMobilephone());
			}
			jsonMap.put("mainLinkman", isState == 1 ? custFollow.getCustDetailName() : resCustDto.getName());
			jsonMap.put("inputDate", new Date());
			jsonMap.put("userName", (userName == null || "".equals(userName) ? account : userName));
			jsonMap.put("type", "1");
			jsonMap.put("saleProcessName", optionBean.getOptionName());
			jsonMap.put("labels", custFollow.getLabelName());
			jsonMap.put("remark", custFollow.getFeedbackComment());
			jsonMap.put("custFollowId", custFollow.getCustFollowId());
		}
		return jsonMap;
	}

	/**
	 * 修改合同
	 * 
	 * @param request
	 * @param response
	 * @param contractBean
	 * @create 2015年12月28日 下午1:46:01 lixing
	 * @history
	 */
	@RequestMapping("/saveEdit")
	@ResponseBody
	public String saveEdit(HttpServletRequest request, HttpServletResponse response, ContractBean contractBean) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			contractBean.setOrgId(user.getOrgId());
			contractBean.setUpdatetime(new Date());
			String fileIdsStr = request.getParameter("fileIdsStr");
			List<String> fileIds = new ArrayList<String>();
			if (StringUtils.isNotBlank(fileIdsStr)) {
				for (String fileId : fileIdsStr.split(",")) {
					if (StringUtils.isNotBlank(fileId)) {
						fileIds.add(fileId);
					}
				}
			}
			String delFileIdsStr = request.getParameter("delFileIdsStr");
			List<String> delIdsList = new ArrayList<String>();
			if (StringUtils.isNotBlank(delFileIdsStr)) {
				delIdsList = Arrays.asList(delFileIdsStr.split(","));
			}
			contractService.editContract(contractBean, fileIds, delIdsList);
			return "1";
		} catch (Exception e) {
			logger.error("修改合同失败", e);
			return "-1";
		}
	}

	@RequestMapping("/toEdit")
	public String toEdit(HttpServletRequest request) {
		try {
			String contractId = request.getParameter("contractId");
			String fromPage = request.getParameter("fromPage");
			ShiroUser user = ShiroUtil.getShiroUser();
			ContractDto dto = contractService.getContractInfoByIdAndOrg(contractId, user.getOrgId());
			List<ContractFileDto> conFileDtos = contractService.getContractFiles(contractId, user.getOrgId());
			List<ContractOrderBean> orderBeans = contractService.getContractOrderBeans(user.getOrgId(),contractId);
			request.setAttribute("fromPage", fromPage);
			request.setAttribute("contractDto", dto);
			request.setAttribute("conFileDtos", conFileDtos);
			request.setAttribute("orderBeans", orderBeans);
			request.setAttribute("shiroUser", user);
			setCustomFiled(user, request);
			request.setAttribute("tsmUpLoadServiceUrl", ConfigInfoUtils.getStringValue("tsm_upload_service_url")); // 上传服务器路径
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "contract/contract_update";
	}

	@RequestMapping("/toView")
	public String toView(HttpServletRequest request) {
		try {
			String contractId = request.getParameter("contractId");
			ShiroUser user = ShiroUtil.getShiroUser();
			ContractDto dto = contractService.getContractInfoByIdAndOrg(contractId, user.getOrgId());
			List<ContractFileDto> conFileDtos = contractService.getContractFiles(contractId, user.getOrgId());
			List<ContractOrderBean> orderBeans = contractService.getContractOrderBeans(user.getOrgId(),contractId);
			request.setAttribute("contractDto", dto);
			request.setAttribute("conFileDtos", conFileDtos);
			request.setAttribute("orderBeans", orderBeans);
			setCustomFiled(user, request);
			request.setAttribute("tsmUpLoadServiceUrl", ConfigInfoUtils.getStringValue("tsm_upload_service_url")); // 上传服务器路径
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "contract/contract_view";
	}

	@RequestMapping("/check")
	@ResponseBody
	public String check(String id, String code) {
		String re = "1";
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<ContractBean> contractBeans = contractService.getContractBeansByCode(code, user.getOrgId());
			if (StringUtils.isNotBlank(id)) {
				if (contractBeans.size() > 0) {
					for (ContractBean cb : contractBeans) {
						if (!cb.getId().equals(id)) {
							re = "0";
						}
					}
				}
			} else {
				if (contractBeans.size() > 0) {
					re = "0";
				}
			}
		} catch (Exception e) {
			logger.error("合同编号校验失败", e);
		}
		return re;
	}
	
	/**
	 * 签约，跳过合同直接签约
	 * 
	 * @param request
	 * @param response
	 * @param contractBean
	 * @create 2017年7月24日  xiaoxh
	 * @history
	 */
	@RequestMapping("/toSign")
	@ResponseBody
	public String toSign(HttpServletRequest request,String jsonStr) {
		       ShiroUser user = ShiroUtil.getShiroUser();

		try {
			   String jsonStrs = URLDecoder.decode(jsonStr, IContants.CHAR_ENCODING);
			if (StringUtils.isNotBlank(jsonStrs)) {
				String reqId = SysBaseModelUtil.getModelId();
				SignDto signDto = (SignDto) JsonUtil.getObjectJsonString(jsonStrs, SignDto.class);
				logger.debug("reqId=" + reqId + "。jsonStr=" + jsonStrs);
				saveOptLog(reqId, signDto, user.getOrgId(), user.getAccount(), user.getIsState(), user.getName());
			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
        return "1";
	}

	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
