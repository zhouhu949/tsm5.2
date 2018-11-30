package com.qftx.tsm.callrecord.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.*;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.common.util.constants.SysConstant;
import com.qftx.tsm.callrecord.bean.MissCallRecordBean;
import com.qftx.tsm.callrecord.dto.*;
import com.qftx.tsm.callrecord.service.CallRecordInfoService;
import com.qftx.tsm.callrecord.service.MissCallRecordService;
import com.qftx.tsm.callrecord.util.CallRecordGetUtil;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.cust.service.ComResourceService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.TsmCustReview;
import com.qftx.tsm.sys.dto.HighSearchDto;
import com.qftx.tsm.sys.scontrol.RecordToExampleApi;
import com.qftx.tsm.sys.service.TsmCustReviewService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * User： bxl Date： 2016/1/12 Time： 19:33
 */
@Controller
@RequestMapping("/callrecord")
public class CallRecordAction {
	private Logger logger = Logger.getLogger(CallRecordAction.class);

	@Autowired
	private ResCustInfoMapper resCustInfoMapper;
	@Autowired
	private CallRecordInfoService callRecordInfoService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private MissCallRecordService missCallRecordService;
	@Autowired
	private TsmCustReviewService tsmCustReviewService;
	@Autowired
	private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Autowired
	private ComResourceService comResourceService;
	@Autowired
	private UserService userService;
	@Autowired private OrgGroupService orgGroupService;
 	@Autowired private OrgGroupUserService orgGroupUserService;

	@RequestMapping()
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取当前登录用户
		return list(request);
	}

	@RequestMapping("/list")
	public String list(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		// 服务地址，为了提供给客户端，弹出播放列表
		request.setAttribute("project_path", getProgetUtil(request));
		// 录音地址
		request.setAttribute("playUrl", ConfigInfoUtils.getStringValue("call_play_url"));
		// 从缓存读取销售进程列表
		List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
		request.setAttribute("options", options);
		request.setAttribute("shiroUser", user);
		request.setAttribute("dDateType", "1"); // 默认为当天
		request.setAttribute("tsmUpLoadServiceUrl", ConfigInfoUtils.getStringValue("tsm_upload_service_url")); // 上传服务器路径
		setIsReplaceWord(request, user); // 设置是否开启用*替换电话号码中间四位
		if (user.getIsState() == 0) { // 个人版
			return "/call/callRecordList_person";
		}
		return "/call/callRecordList";
	}
	
	@RequestMapping("/historyList")
	public String historyList(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		// 服务地址，为了提供给客户端，弹出播放列表
		request.setAttribute("project_path", getProgetUtil(request));
		// 录音地址
		request.setAttribute("playUrl", ConfigInfoUtils.getStringValue("call_play_url"));
		// 从缓存读取销售进程列表
		List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
		request.setAttribute("options", options);
		request.setAttribute("shiroUser", user);		
		setIsReplaceWord(request, user); // 设置是否开启用*替换电话号码中间四位
		if (user.getIsState() == 0) { // 个人版
			return "/call/callRecordHistoryList_person";
		}
		return "/call/callRecordHistoryList";
	}

	/** 通话记录 查询列表 */
	@RequestMapping("/listJson")
	@ResponseBody
	public Map<String, Object> listJson(HttpServletRequest request, HttpServletResponse response, ConditionDto bean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ShiroUser user = ShiroUtil.getShiroUser();
		String accs = request.getParameter("accs"); // 选择的所有联系人
		String dDateType = request.getParameter("dDateType");
		String callState = request.getParameter("callState_");
		String queryType = request.getParameter("queryType");
		String queryText = request.getParameter("queryText");
		String osType = request.getParameter("osType");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		// 管理者查询
		if (user.getIssys() != null && user.getIssys() == 1) {
			// 所有者查询方式 1-全部 2-只看自己 3-选中查询
			osType = StringUtils.isBlank(osType) ? "2" : osType;
			if (StringUtils.isNotEmpty(accs) && "3".equals(osType)) {
				String[] ownerAccs = accs.split(",");
				bean.setInputAccs(Arrays.asList(ownerAccs));
			} else if ("1".equals(osType)) {
				List<String> list = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if (list != null && list.size() > 0) {
					StringBuffer sb = new StringBuffer();
					for (String str : list) {
						sb.append(str);
						sb.append(",");
					}
					sb.append(user.getAccount());
					sb.append(",");
					if (sb.length() > 0) {
						sb = sb.deleteCharAt(sb.length() - 1);
					}
					bean.setInputAccs(Arrays.asList(sb.toString().split(",")));
				}
			} else {
				List<String>list = new ArrayList<String>();
				list.add(user.getAccount());
				bean.setInputAccs(list);
			}
		} else {
			List<String>list = new ArrayList<String>();
			list.add(user.getAccount());
			bean.setInputAccs(list);
		}

		// 搜索框查询
		if (StringUtils.isNotBlank(queryText)) {
			if ("1".equals(queryType)) { // 客户姓名
				bean.setCustName(queryText);
			} else if ("2".equals(queryType)) { // 联系号码
				bean.setQueryKey(queryText.trim());
			} else if ("3".equals(queryType)) { // 客户单位
				bean.setDefine3(queryText);
			}
		}

		// 呼叫类型
		if (StringUtils.isNotBlank(callState)) {
			if ("1".equals(callState)) { // 已接来电
				if (bean.getTimeLengthBegin() == null || bean.getTimeLengthBegin() == 0) {
					bean.setTimeLengthBegin(1);
				}
				bean.setCallState("1");
			} else if ("2".equals(callState)) { // 已拨电话
				if (bean.getTimeLengthBegin() == null || bean.getTimeLengthBegin() == 0) {
					bean.setTimeLengthBegin(1);
				}
				bean.setCallState("2");
			} else if ("3".equals(callState)) { // 未接来电
				bean.setCallState("1");
				bean.setTimeLengthBegin(0);
				bean.setTimeLengthEnd(0);
			} else if ("4".equals(callState)) { // 未接去电
				bean.setCallState("2");
				bean.setTimeLengthBegin(0);
				bean.setTimeLengthEnd(0);
			}
		}

			// 通话日期
			if (StringUtils.isBlank(dDateType)) {			
				dDateType = "1";
				bean.setStartTimeBegin(getStartDateStr(Integer.parseInt(dDateType))+ " 00:00:00");
				bean.setStartTimeEnd(DateUtil.formatDate(DateUtil.dateEnd(new Date()),DateUtil.DATE_DAY)+" 23:59:59");
			} else {
				if (StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)) {
					bean.setStartTimeBegin(getStartDateStr(Integer.parseInt(dDateType))+ " 00:00:00");
					bean.setStartTimeEnd(DateUtil.formatDate(DateUtil.dateEnd(new Date()),DateUtil.DATE_DAY)+" 23:59:59");
				} else if ("5".equals(dDateType)) {
					if (StringUtils.isNotBlank(startDate)) {
						bean.setStartTimeBegin(startDate + " 00:00:00");
					}
					if (StringUtils.isNotBlank(endDate)) {
						bean.setStartTimeEnd(endDate + " 23:59:59");
					}
				}
			}
		
		// 客户类型(0: 线索, 1: 资源, 2: 意向)，不过滤线索类型数据
		bean.setCustType("1,2");
		
		// 客户状态
		if (StringUtils.isNotBlank(bean.getCustState()) && "9".equals(bean.getCustState())) { // 资源
			bean.setCustState("");
			bean.setCustType("1");
		} else if (StringUtils.isNotBlank(bean.getCustState()) && "10".equals(bean.getCustState())) { // 访客
			bean.setCustState("");
			bean.setDefine10("1"); // 定义define10 查询访客
		}

		bean.setOrgId(user.getOrgId());
		String key = MD5Utils.getMD5String(JSON.toJSONString(bean));
		List<TsmRecordCallBean> list = null;
		ConditionDto item = null;
		CallRecordListDto queryCall = null;
		try {
			// 增加查询缓存
			queryCall = (CallRecordListDto) CachedUtil.getInstance().get(key);
			if (!(queryCall != null)) {
				queryCall = callRecordInfoService.queryCallRecord(bean);
				CachedUtil.getInstance().set(key, queryCall, 10);
			}
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> optionMap = cachedService.getOrgSaleProcess(user.getOrgId());
			list = queryCall.getBeans();
			for (TsmRecordCallBean frcb : list) {
				frcb.setInputName(nameMap.get(frcb.getInputAcc()) == null ? frcb.getInputAcc() : nameMap.get(frcb.getInputAcc()));
				frcb.setDefine16(MathUtil.secondFormat1(frcb.getTimeLength()));
				frcb.setDefine15(frcb.getStartTime() != null ? DateUtil.formatDate(frcb.getStartTime(), "yyyy-MM-dd HH:mm:ss") : "");
				if (optionMap != null) {
					frcb.setSaleProcessName(optionMap.get(frcb.getSaleProcessId()));
				}
			}
			item = queryCall.getCondition();
			logger.debug("返回参数=" + JSON.toJSONString(queryCall, true));
			logger.debug("返回参数list=" + list.size());
		} catch (Exception e) {
			logger.error("query call error:" + e.getMessage(),e);
			map.put("status", "error");
			CachedUtil.getInstance().delete(key); // 报异常后 删除缓存!
			return map;
		}
		if (list == null) {
			list = new ArrayList<TsmRecordCallBean>();
		}
		if (item == null) {
			item = new ConditionDto();
		}

		map.put("item", item);
		map.put("list", list);
		map.put("status", "success");
		return map;
	}

	  /** 历史通话记录 查询列表 */
    @RequestMapping("/historyListJson")
    @ResponseBody
    public Map<String, Object> historyListJson(HttpServletRequest request, HttpServletResponse response, HistoryCallQueryDto bean) throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
		ShiroUser user = ShiroUtil.getShiroUser();
		String callState = request.getParameter("callState_");
		String queryType = request.getParameter("queryType");
		String queryText = request.getParameter("queryText");

		// 搜索框查询
		if (StringUtils.isNotBlank(queryText)) {
			if ("1".equals(queryType)) { // 通信主叫
				bean.setCallerNum(queryText.trim()); 
			} else if ("2".equals(queryType)) { // 通信被叫
				bean.setCalledNum(queryText.trim());
			}
		}
		
		// 呼叫类型
		if (StringUtils.isNotBlank(callState)) {
			if ("1".equals(callState)) { // 已接来电
				if (bean.getTimeLengthBegin() == null || bean.getTimeLengthBegin() == 0) {
					bean.setTimeLengthBegin(1);
				}
				bean.setCallState("1");
			} else if ("2".equals(callState)) { // 已拨电话
				if (bean.getTimeLengthBegin() == null || bean.getTimeLengthBegin() == 0) {
					bean.setTimeLengthBegin(1);
				}
				bean.setCallState("2");
			} else if ("3".equals(callState)) { // 未接来电
				bean.setCallState("1");
				bean.setTimeLengthBegin(0);
				bean.setTimeLengthEnd(0);
			} else if ("4".equals(callState)) { // 未接去电
				bean.setCallState("2");
				bean.setTimeLengthBegin(0);
				bean.setTimeLengthEnd(0);
			}
		}

				
		// 历史记录查询年份，供接口调用	
		if (StringUtils.isNotBlank(bean.getStartTimeBegin())) {
			bean.setStartTimeBegin(bean.getStartTimeBegin() + " 00:00:00");
		}
		if (StringUtils.isNotBlank(bean.getStartTimeEnd())) {
			bean.setStartTimeEnd(bean.getStartTimeEnd() + " 23:59:59");	
		}else{
			bean.setStartTimeEnd(com.qftx.common.util.DateUtil.format(com.qftx.common.util.DateUtil.parse(com.qftx.common.util.DateUtil.getOneDay(-90)),
					com.qftx.common.util.DateUtil.defaultPattern) + " 23:59:59");
		}
		if (user.getIssys() != null && user.getIssys() == 0) {
			bean.setInputAcc(user.getAccount());
		}		
		bean.setOrgId(user.getOrgId());
		String key = MD5Utils.getMD5String(JSON.toJSONString(bean));
		List<TsmRecordCallBean> list = null;
		Object scroll = null;
		TsGetRangeScrollResp queryCall = null;
		try {
			// 增加查询缓存
			queryCall = (TsGetRangeScrollResp) CachedUtil.getInstance().get(key);
			if (!(queryCall != null)) {
				queryCall = CallRecordGetUtil.getHisRecordeList(bean);
				CachedUtil.getInstance().set(key, queryCall, 10);
			}
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> optionMap = cachedService.getOrgSaleProcess(user.getOrgId());
			list = queryCall.getBeans();
			for (TsmRecordCallBean frcb : list) {
				frcb.setInputName(nameMap.get(frcb.getInputAcc()) == null ? frcb.getInputAcc() : nameMap.get(frcb.getInputAcc()));
				frcb.setDefine16(MathUtil.secondFormat1(frcb.getTimeLength()));
				frcb.setDefine15(frcb.getStartTime() != null ? DateUtil.formatDate(frcb.getStartTime(), "yyyy-MM-dd HH:mm:ss") : "");
				if (optionMap != null) {
					frcb.setSaleProcessName(optionMap.get(frcb.getSaleProcessId()));
				}
			}
			scroll = queryCall.getScroll();
			logger.debug("返回参数=" + JSON.toJSONString(queryCall, true));
			logger.debug("返回参数list=" + list.size());
		} catch (Exception e) {
			logger.error(" query history call error:" + e.getMessage(), e);
			map.put("status", "error");
			CachedUtil.getInstance().delete(key); // 报异常后 删除缓存!
			return map;
		}
		if (list == null) {
			list = new ArrayList<TsmRecordCallBean>();
		}
		if (scroll == null) {
			scroll = new Object();
		}
		map.put("scroll", scroll);
		map.put("list", list);
		map.put("status", "success");
		return map;
    }
    
    /** 历史通话记录 查询列表 */
    @RequestMapping("/historyScrollListJson")
    @ResponseBody
    public Map<String, Object> historyScrollListJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
		ShiroUser user = ShiroUtil.getShiroUser();
		String scroll = request.getParameter("scroll");
		
		List<TsmRecordCallBean> list = null;
		Object scroll1 = null;
		TsGetRangeScrollResp queryCall1 = null;
		try {
			queryCall1 =  CallRecordGetUtil.getHisRecordeScrollList(scroll);
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> optionMap = cachedService.getOrgSaleProcess(user.getOrgId());
			list = queryCall1.getBeans();
			for (TsmRecordCallBean frcb : list) {
				frcb.setInputName(nameMap.get(frcb.getInputAcc()) == null ? frcb.getInputAcc() : nameMap.get(frcb.getInputAcc()));
				frcb.setDefine16(MathUtil.secondFormat1(frcb.getTimeLength()));
				frcb.setDefine15(frcb.getStartTime() != null ? DateUtil.formatDate(frcb.getStartTime(), "yyyy-MM-dd HH:mm") : "");
				if (optionMap != null) {
					frcb.setSaleProcessName(optionMap.get(frcb.getSaleProcessId()));
				}
			}
			scroll1 = queryCall1.getScroll();
			logger.debug("返回参数=" + JSON.toJSONString(queryCall1, true));
			logger.debug("返回参数list=" + list.size());
		} catch (Exception e) {
			logger.error(" query history scroll call error:" + e.getMessage(), e);
			map.put("status", "error");
			return map;
		}
		if (list == null) {
			list = new ArrayList<TsmRecordCallBean>();
		}
		if (scroll1 == null) {
			scroll1 = new Object();
		}
		map.put("scroll", scroll1);
		map.put("list", list);
		map.put("status", "success");
		return map;
    }
	
	
	/** 未接记录 查询列表 */
	@RequestMapping("/noCall/list")
	public String noCallList(HttpServletRequest request, HttpServletResponse response, MissCallRecordDto bean) throws Exception {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String accs = request.getParameter("accs"); // 选择的所有联系人
			String dDateType = request.getParameter("dDateType");
			bean.setRoleType(user.getIssys()); // 角色类别：0--销售，1--管理者
			if (StringUtils.isNotBlank(bean.getAccs())) {
				String[] accss = bean.getAccs().split(",");
				bean.setOwnerAccs(Arrays.asList(accss));
			}
			String loginAcc = user.getAccount();
			bean.setOwnerAcc(loginAcc);
			// 最近来电时间
			if (StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)) {
				bean.setStartDate(getStartDateStr(Integer.parseInt(dDateType))+ " 00:00:00");
				bean.setEndDate(DateUtil.formatDate(DateUtil.dateEnd(new Date()),DateUtil.DATE_DAY)+" 23:59:59");
			}
			bean.setOrgId(user.getOrgId());
			bean.setOrderKey(" trm.input_date desc");
			List<MissCallRecordBean> list = missCallRecordService.getMissCallListPage(bean);
			request.setAttribute("list", list);
			request.setAttribute("item", bean);
			request.setAttribute("dDateType", dDateType);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/call/noCallRecordList";
	}

	@ResponseBody
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String tel = request.getParameter("tel");
		String saleProcessId = request.getParameter("saleProcessId");
		String saleProcessName = request.getParameter("saleProcessName");
		String followId = request.getParameter("followId");
		ShiroUser user = ShiroUtil.getShiroUser();
		ResCustInfoBean item = resCustInfoMapper.getByPrimaryKey(user.getOrgId(), id);
		if (tel != null && !"".equals(tel)) {
			item.setTelphone(tel);
		}
		return callRecordInfoService.saveCallRecordInfo(item, user, saleProcessId, saleProcessName, followId);
	}

	/** 处理未接记录 处理状态 */
	@RequestMapping("/updateMissCallStatus")
	@ResponseBody
	public String updateMissCallStatus(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String phone = request.getParameter("phone"); // 来电号码
			if (StringUtils.isNotBlank(phone)) {
				MissCallRecordBean entity = new MissCallRecordBean();
				entity.setOrgId(user.getOrgId());
				entity.setInputAcc(user.getAccount());
				entity.setPhone(phone);
				entity.setStatus(1);
				missCallRecordService.modfiyByStatus(entity);
			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return AppConstant.RESULT_SUCCESS;
	}

	/** 查看资源是否被删除|是否是自己的 */
	@RequestMapping("/findResIsDel")
	@ResponseBody
	public String findResIsDel(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String custId = request.getParameter("custId");
			Map<String, String> map = new HashMap<String, String>();
			map.put("orgId", user.getOrgId());
			map.put("custId", custId);
			ResCustInfoBean custInfo = comResourceService.getByPrimaryKey(map);			
			if (custInfo != null && custInfo.getIsDel() == 0) {
				if(custInfo.getStatus() == 4){ // 公海客户
					return "success";
				}
				Integer groupType = (Integer) ShiroUtil.getSession(SysConstant.USER_GROUP_TYPE);
				if(groupType == null){
					 OrgGroupUser usermeber = new OrgGroupUser();
			         usermeber.setOrgId(user.getOrgId());
			         usermeber.setUserId(user.getId());
			         OrgGroupUser newmember = orgGroupUserService.getByCondtion(usermeber);
			         if(newmember!=null){
			        	 OrgGroup orgGroup = orgGroupService.getByGroupId(user.getOrgId(),newmember.getGroupId());
				         if(orgGroup!=null){
				        	 // 设置用户类型
					         ShiroUtil.setSession(SysConstant.USER_GROUP_TYPE, orgGroup.getGroupType());
					         groupType = (Integer) ShiroUtil.getSession(SysConstant.USER_GROUP_TYPE);
				         }		        	
			         }	
				 }
				if(groupType!=null && groupType==2){ // 客服直接看
					return "success";
				}
				// 拥有者为空，或者拥有者是自己，或者共有者是自己
				if(StringUtils.isBlank(custInfo.getOwnerAcc()) || user.getAccount().equals(custInfo.getOwnerAcc()) || user.getAccount().equals(custInfo.getCommonAcc())){
					return "success";
				}									
				// 管理员查看是否是自己权限下人员的。
				if (user.getIssys() != null && user.getIssys() == 1) {
					List<String> list = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					if (list != null && list.size() > 0) {
						boolean isTrue = false;
						for(String acc: list){
							if(acc.equals(custInfo.getOwnerAcc())){
								isTrue = true;
								break;
							}
						}
						if(isTrue){
							return "success";
						}
					}					
				}
				// 不是自己的资源
				Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
				return nameMap.get(custInfo.getOwnerAcc()) == null ?custInfo.getOwnerAcc() : nameMap.get(custInfo.getOwnerAcc());
			} else {
				return "isDel"; // 已删除
			}
		} catch (Exception e) {
			logger.error(" 查看资源是否被删除 异常！", e);
			return "error";
		}
	}

	/** 根据资源ID查询客户名称，单位名称，联系人，状态，类型 */
	@RequestMapping("/findResCallByCustId")
	@ResponseBody
	public Map<String, Object> findResCallByCustId(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String resCustId = request.getParameter("resCustId");
			ResCustDto dto = comResourceService.findResCallByCustId(user.getOrgId(), resCustId);
			Map<String, String> optionMap = cachedService.getOrgSaleProcess(user.getOrgId());
			if (dto != null) {
				map.put("status", "success");
				map.put("name", dto.getName());
				map.put("state", dto.getStatus());
				map.put("custType", dto.getType());
				map.put("detailName", dto.getMainLinkman());
				map.put("company", dto.getCompany());
				map.put("saleProcessId", dto.getLastOptionId());
				map.put("isConcat", dto.getIsConcat());
				if (optionMap != null) {
					map.put("saleProcessName", optionMap.get(dto.getLastOptionId()));
				}
			} else {
				map.put("status", "error");
			}
		} catch (Exception e) {
			map.put("status", "error");
		}
		return map;
	}

	/***
	 * 通话记录生成录音范例 页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @create 2016-6-27 上午11:30:37 zwj
	 * @history 4.x
	 */
	@RequestMapping("/review")
	public String reviewCallInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 查询录音范例分类
			ShiroUser user = ShiroUtil.getShiroUser();
			String retaId = request.getParameter("callId"); // 录音ID
			String recordUrl = request.getParameter("url"); // 录音地址
			String timeLength = request.getParameter("timeLength"); // 录音时长
			String inputName = request.getParameter("inputName"); // 录音联系人
			String inputDate = request.getParameter("inputDate"); // 录音通话时间
			String recordCode = request.getParameter("recordCode"); // 录音地址中code参数
			String attachParam = request.getParameter("attachParam"); // 录音地址中d参数
			String callCode = request.getParameter("callCode"); // 录音地址中id参数
			String callId = request.getParameter("id"); // 录音地址中id参数
			TsmCustReview review = tsmCustReviewService.getReview(user.getOrgId(), retaId);
			request.setAttribute("review", review);
			request.setAttribute("retaId", retaId);
			request.setAttribute("recordUrl", recordUrl);
			request.setAttribute("timeLength", timeLength);
			request.setAttribute("inputDate", inputDate);
			request.setAttribute("inputName", inputName);
			request.setAttribute("recordCode", recordCode);
			request.setAttribute("attachParam", attachParam);
			request.setAttribute("callCode", callCode);
			request.setAttribute("callId", callId);
			// 取得录音分类下拉框
			List<OptionBean> oplist = cachedService.getOptionList(AppConstant.RECORD_CODE, user.getOrgId());
			request.setAttribute("oplist", oplist);
		} catch (Exception e) {
			logger.error("【通话记录生成录音范例 页面 异常】！", e);
		}
		return "/call/idialog/AddOrEditReview";
	}

	/***
	 * 通话记录生成录音范例 操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @create 2016-6-27 上午11:34:39 zwj
	 * @history 4.x
	 */
	@RequestMapping("/review/callOpera")
	@ResponseBody
	public String review_opera(HttpServletRequest request, HttpServletResponse response, TsmCustReview review) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String recordCode = com.qftx.common.util.StringUtils.isBlank(review.getCallCode()) ? "" : review.getCallCode();
			review.setOrgId(user.getOrgId());
			if (StringUtils.isNotBlank(review.getReviewId())) { // 修改
				tsmCustReviewService.modifyTrends(review);
			} else { // 增加
				String inputDate = request.getParameter("inputDate");
				String attachParam = request.getParameter("attachParam");
				// 调用接口
				String postResult = RecordToExampleApi.convertCallRecord(user.getOrgId(), recordCode, review.getCallD(),
						URLDecoder.decode(attachParam, IContants.CHAR_ENCODING));
				if ("0".equals(postResult)) {
					if (StringUtils.isNotBlank(inputDate)) {
						review.setRetaTime(DateUtil.parseDateTime(inputDate));
					}
					review.setOwnerAcc(URLDecoder.decode(review.getOwnerAcc(), IContants.CHAR_ENCODING));
					review.setReviewDate(new Date());
					review.setReviewAcc(user.getAccount());
					review.setReviewName(user.getName());
					review.setReviewId(SysBaseModelUtil.getModelId());
					review.setType(2);
					if (!recordCode.contains("aliyun")) {
						review.setRecordUrl(URLDecoder.decode(review.getRecordUrl(), IContants.CHAR_ENCODING));
					} else {
						String http = ConfigInfoUtils.getStringValue("record_to_example_url");
						String url = http + "/down_demo?code=" + recordCode + "&comp_id=" + user.getOrgId() + "&call_id=" + review.getCallD();
						review.setRecordUrl(URLDecoder.decode(url, IContants.CHAR_ENCODING));

					}
					tsmCustReviewService.create(review);
				} else {
					return AppConstant.RESULT_FAILURE;
				}
			}
		} catch (Exception e) {
			logger.error("【通话记录生成录音范例 操作 异常】！", e);
			return AppConstant.RESULT_FAILURE;
		}
		return AppConstant.RESULT_SUCCESS;
	}

	/**
	 * 通话录音列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/soundList")
	public String soundList(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		// 服务地址，为了提供给客户端，弹出播放列表
		request.setAttribute("project_path", getProgetUtil(request));
		// 录音地址
		request.setAttribute("playUrl", ConfigInfoUtils.getStringValue("call_play_url"));
		// 从缓存读取销售进程列表
		List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
		request.setAttribute("options", options);
		Integer groupType = (Integer) ShiroUtil.getSession(SysConstant.USER_GROUP_TYPE);
		if(groupType == null){
			 OrgGroupUser usermeber = new OrgGroupUser();
	         usermeber.setOrgId(user.getOrgId());
	         usermeber.setUserId(user.getId());
	         OrgGroupUser newmember = orgGroupUserService.getByCondtion(usermeber);
	         OrgGroup orgGroup = orgGroupService.getByGroupId(user.getOrgId(),newmember.getGroupId());
	         // 设置用户类型
	         ShiroUtil.setSession(SysConstant.USER_GROUP_TYPE, orgGroup.getGroupType());
	         groupType = (Integer) ShiroUtil.getSession(SysConstant.USER_GROUP_TYPE);
		 }
		if (!(groupType != null && groupType == 2)) {
			groupType = 1;
		}

		request.setAttribute("shiroUser", user);
		request.setAttribute("groupType", groupType);
		request.setAttribute("osType", "2");
		request.setAttribute("dDateType", "1"); // 默认为当天
		setIsReplaceWord(request, user); // 设置是否开启用*替换电话号码中间四位
		return "/call/callSoundList";
	}

	/** 通话录音 查询列表 */
	@RequestMapping("/soundListJson")
	@ResponseBody
	public Map<String, Object> soundListJson(HttpServletRequest request, HttpServletResponse response, ConditionDto bean) throws Exception {
		Map<String, Object> map = new HashMap<>();
		ShiroUser user = ShiroUtil.getShiroUser();
		String accs = request.getParameter("accs"); // 选择的所有联系人
		String dDateType = request.getParameter("dDateType");
		String callState = request.getParameter("callState_");
		String groupType = request.getParameter("groupType"); // 1:销售，2:客服
		String queryType = request.getParameter("queryType");
		String queryText = request.getParameter("queryText");
		String osType = request.getParameter("osType");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		// 搜索框查询
		if (StringUtils.isNotBlank(queryText)) {
			if ("1".equals(queryType)) { // 通信主叫
				bean.setCallerNum(queryText.trim()); 
			} else if ("2".equals(queryType)) { // 通信被叫
				bean.setCalledNum(queryText.trim());
			}
		}
		
		// 联系人查询方式 1-全部 2-只看自己 3-选中查询
		osType = StringUtils.isBlank(osType) ? "2" : osType;
		if (StringUtils.isNotEmpty(accs) && "3".equals(osType)) {
			String[] ownerAccs = accs.split(",");
			bean.setInputAccs(Arrays.asList(ownerAccs));
		} else if ("1".equals(osType)) {
			List<String> list = userService.getManageUserAccByType(user.getOrgId(), groupType);
			if (list != null && list.size() > 0) {
				StringBuffer sb = new StringBuffer();
				list.add(user.getAccount());
				for (String str : list) {
					sb.append(str);
					sb.append(",");
				}
				if (sb.length() > 0) {
					sb = sb.deleteCharAt(sb.length() - 1);
				}
				bean.setInputAccs(Arrays.asList(sb.toString().split(",")));
			}
		} else {
			bean.setInputAccs(Arrays.asList((new String[] { user.getAccount()})));
		}

		// 呼叫类型
		if (StringUtils.isNotBlank(callState)) {
			if ("1".equals(callState)) { // 已接来电
				if (bean.getTimeLengthBegin() == null || bean.getTimeLengthBegin() == 0) {
					bean.setTimeLengthBegin(1);
				}
				bean.setCallState("1");
			} else if ("2".equals(callState)) { // 已拨电话
				if (bean.getTimeLengthBegin() == null || bean.getTimeLengthBegin() == 0) {
					bean.setTimeLengthBegin(1);
				}
				bean.setCallState("2");
			}
		}
		if (bean.getTimeLengthBegin() == null || bean.getTimeLengthBegin() == 0) {
			bean.setTimeLengthBegin(1);
		}
	
		// 通话日期
		if (StringUtils.isBlank(dDateType)) {			
			dDateType = "2";
			bean.setStartTimeBegin(getStartDateStr(Integer.parseInt(dDateType))+ " 00:00:00");
			bean.setStartTimeEnd(DateUtil.formatDate(DateUtil.dateEnd(new Date()),DateUtil.DATE_DAY)+" 23:59:59");
		} else {
			if (StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)) {
				bean.setStartTimeBegin(getStartDateStr(Integer.parseInt(dDateType))+ " 00:00:00");
				bean.setStartTimeEnd(DateUtil.formatDate(DateUtil.dateEnd(new Date()),DateUtil.DATE_DAY)+" 23:59:59");
			} else if ("5".equals(dDateType)) {
				if (StringUtils.isNotBlank(startDate)) {
					bean.setStartTimeBegin(startDate + " 00:00:00");
				}
				if (StringUtils.isNotBlank(endDate)) {
					bean.setStartTimeEnd(endDate + " 23:59:59");
				}
			}
		}
		
		// 客户状态
		if (StringUtils.isNotBlank(bean.getCustState()) && "9".equals(bean.getCustState())) { // 资源
			bean.setCustState("");
			bean.setCustType("1");
		} else if (StringUtils.isNotBlank(bean.getCustState()) && "10".equals(bean.getCustState())) { // 访客
			bean.setCustState("");
			bean.setDefine10("1"); // 定义define10 查询访客
		}
		bean.setOrgId(user.getOrgId());
		String key = MD5Utils.getMD5String(JSON.toJSONString(bean));
		List<TsmRecordCallBean> list = null;
		ConditionDto item = null;
		CallRecordListDto queryCall = null;
		try {
			// 增加查询缓存
			queryCall = (CallRecordListDto) CachedUtil.getInstance().get(key);
			if (!(queryCall != null)) {
				queryCall = callRecordInfoService.queryCallRecord(bean);
				CachedUtil.getInstance().set(key, queryCall, 10);
			}
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> optionMap = cachedService.getOrgSaleProcess(user.getOrgId());
			list = queryCall.getBeans();
			for (TsmRecordCallBean frcb : list) {
				frcb.setInputName(nameMap.get(frcb.getInputAcc()) == null ? frcb.getInputAcc() : nameMap.get(frcb.getInputAcc()));
				frcb.setDefine16(MathUtil.secondFormat1(frcb.getTimeLength()));
				frcb.setDefine15(frcb.getStartTime() != null ? DateUtil.formatDate(frcb.getStartTime(), "yyyy-MM-dd HH:mm") : "");
				if (optionMap != null) {
					frcb.setSaleProcessName(optionMap.get(frcb.getSaleProcessId()));
				}
			}
			item = queryCall.getCondition();
			logger.debug("返回参数=" + JSON.toJSONString(queryCall, true));
			logger.debug("返回参数list=" + list.size());
		} catch (Exception e) {
			logger.error("query call error:" + e.getMessage(), e);
			map.put("status", "error");
			CachedUtil.getInstance().delete(key); // 报异常后 删除缓存!
			return map;
		}
		if (list == null) {
			list = new ArrayList<TsmRecordCallBean>();
		}
		if (item == null) {
			item = new ConditionDto();
		}
		map.put("item", item);
		map.put("list", list);
		map.put("status", "success");
		return map;
	}

	/**
	 * 获取查询时间
	 * 
	 * @param type
	 *            1-当天 2-本周 3-本月 4-半年
	 * @return
	 * @create 2015年12月14日 下午3:48:05 lixing
	 * @history
	 */
	private String getStartDateStr(Integer type) {
		String str = "";
		if (type == 1) {
			str = DateUtil.formatDate(DateUtil.dateBegin(new Date()), "yyyy-MM-dd");
		} else if (type == 2) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			str = DateUtil.formatDate(cal.getTime(), DateUtil.DATE_DAY);
		} else if (type == 3) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.DAY_OF_MONTH, 1);
			str = DateUtil.formatDate(cal.getTime(), DateUtil.DATE_DAY);
		} else if (type == 4) {
			str = DateUtil.formatDate(DateUtil.getAddDate(new Date(), -180), DateUtil.DATE_DAY);
		}
		return str;
	}

	/**
	 * 设置是否开启用*替换电话号码中间四位
	 */
	private void setIsReplaceWord(HttpServletRequest request, ShiroUser user) {
		// 查询缓存
		List<DataDictionaryBean> dataMap = cachedService.getDirList(AppConstant.DATA24, user.getOrgId());
		// 对电话号码中间4位用*号模糊处理设置到session中
		Set<String> dicSet = new HashSet<String>();
		dicSet.add(AppConstant.DATA24 + "_" + user.getOrgId());
		Integer idReplaceWord = 0;
		if (!dataMap.isEmpty() && dataMap.get(0) != null && !cachedService.judgeHideWhiteList(user.getOrgId(), user.getAccount())) {
			idReplaceWord = new Integer(dataMap.get(0).getDictionaryValue());
		}
		request.setAttribute("idReplaceWord", idReplaceWord);
	}

	

	 
	  
	
	// 获取项目地址
	private String getProgetUtil(HttpServletRequest request) {
		if (80 == request.getServerPort()) {
			return request.getScheme() + "://" + request.getServerName() + request.getContextPath();
		} else {
			return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		}
	}

}
