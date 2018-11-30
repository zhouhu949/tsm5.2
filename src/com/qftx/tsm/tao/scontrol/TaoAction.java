package com.qftx.tsm.tao.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.Org;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.OrgService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.*;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.area.bean.ChinaCityBean;
import com.qftx.tsm.area.bean.ChinaCountyBean;
import com.qftx.tsm.area.bean.ChinaProvinceBean;
import com.qftx.tsm.area.service.AreaService;
import com.qftx.tsm.callrecord.dto.FollowCallQueryDto;
import com.qftx.tsm.callrecord.dto.TsmRecordCallBean;
import com.qftx.tsm.callrecord.util.CallRecordGetUtil;
import com.qftx.tsm.cust.bean.*;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.cust.dto.CallListDto;
import com.qftx.tsm.cust.service.ComResourceService;
import com.qftx.tsm.cust.service.ResCustInfoDetailService;
import com.qftx.tsm.cust.service.ResCustInfoLogService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.follow.bean.CustFollowBean;
import com.qftx.tsm.follow.dto.CustFollowCallDto;
import com.qftx.tsm.follow.dto.CustFollowDto;
import com.qftx.tsm.follow.service.CustFollowService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.bean.OptionGroupBean;
import com.qftx.tsm.plan.facade.PlanFactService;
import com.qftx.tsm.plan.facade.enu.PlanResCR;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.tao.dto.BzDto;
import com.qftx.tsm.tao.dto.OptionDto;
import com.qftx.tsm.tao.service.CustGuideService;
import com.qftx.tsm.tao.service.CustInfoService;
import com.qftx.tsm.tao.service.TaoService;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 淘客户
 * 
 * @author: wuwei
 * @since: 2015年12月4日 下午1:55:43
 * @history:
 */
@Controller
@RequestMapping(value = "/res/tao/")
public class TaoAction extends BaseAction {
	private static Logger logger = Logger.getLogger(TaoAction.class);
	@Autowired
	private TaoService taoService;
	@Autowired
	private ComResourceService comResourceService;
	@Autowired
	transient private CachedService cachedService;
	@Autowired
	transient private UserService userService;
	@Resource
	private ResCustInfoDetailService resCustInfoDetailService;

	@Resource
	private ResCustInfoLogService resCustInfoLogService;
	@Resource
	private CustFollowService custFollowService;
	@Resource
	private PlanFactService planFactService;
	@Resource
	private AreaService areaService;
	@Resource
	private ResCustInfoMapper resCustInfoMapper;
	@Resource
	private CustInfoService custInfoService;
	@Resource
	private CustGuideService custGuideService;

	@Resource
	private OrgService orgService;
	@Value("#{config['call_stat_url']}")
	private String callStatUrl;
	@Autowired
	private ResCustInfoService resCustInfoService;
	/**
	 * 淘客户
	 * 
	 * @param request
	 * @return
	 * @create 2015年12月31日 上午11:04:43 wuwei
	 * @history
	 */
	@RequestMapping(value = "/taoMyRes")
	public String taoMyRes(HttpServletRequest request) {
		String resId = request.getParameter("resId") == null ? "" : request.getParameter("resId").trim(); // 我的客户跳转过来，延后消息跳转(都以当前id，往下取。)
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
		String isSet = "0";
//		Org org = orgService.getByPrimaryKey(user.getOrgId());
		Org org =cachedService.getAuthOrgCatch(user.getOrgId());
		if (org != null) {
			if (StringUtils.isNotBlank(org.getIsSet() + "")) {
				isSet = org.getIsSet() + "";
			}
		}
		List<DataDictionaryBean> contractOns = cachedService.getDirList(AppConstant.DATA_40039, user.getOrgId());
		boolean addContract = true;
		if(contractOns!=null && contractOns.size() > 0){
			if ("1".equals(contractOns.get(0).getDictionaryValue())) {
				addContract = false;
			}
		}
		request.setAttribute("addContract", addContract);
		// 获取默认下次跟进时间
		request.setAttribute("defDate", getCustCacheDate(request));
		request.setAttribute("isSet", isSet);
		request.setAttribute("callStatUrl", callStatUrl);
		request.setAttribute("custId", resId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user.getIsState() == 1) {
			return "/tao/taoCust";
		} else {
			return "/tao/taoCust_p";
		}
		
	}

	// 淘客户ajax请求
	@ResponseBody
	@RequestMapping(value = "/getTaoCustList")
	public String getTaoCust(HttpServletRequest request) {
		String resourceGroupId = request.getParameter("resourceGroupId") == null ? "today" : request.getParameter("resourceGroupId").trim();
		String orderType = StringUtils.isNotBlank(request.getParameter("orderType")) ? request.getParameter("orderType").trim(): "owner_start_date desc" ;
		int isConcat = request.getParameter("isConcat") == null ? 2 : new Integer(request.getParameter("isConcat").trim());
		String resId = request.getParameter("resId") == null ? "" : request.getParameter("resId").trim(); // 我的客户跳转过来，延后消息跳转(都以当前id，往下取。)
		ShiroUser user = ShiroUtil.getShiroUser();
		String pool = getParameter(request, "pool", "1");
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("taoMyRes reqId=" + reqId + " account= " + user.getAccount() + ",resId=" + resId + ",resourceGroupId=" + resourceGroupId + ",orderType="
				+ orderType + ",isConcat=" + isConcat);
		long startTime = System.currentTimeMillis();
		CallListDto listDto = new CallListDto();
		try {
			listDto = taoService.saveTaoCust(pool, reqId, request, resId, resourceGroupId, orderType, isConcat, user.getOrgId(), user.getAccount());
			if (listDto == null) {
				listDto = new CallListDto();
			}
			listDto.setPool(pool);
			listDto.setIsFromOtherPage("0");
			setSystemValOfTao(listDto, request, user.getOrgId(), user.getAccount(), user.getIsState());
			logger.debug("taoMyRes reqId=" + reqId + "耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
		} catch (Exception e) {
			logger.error("taoMyRes reqId=" + reqId + "。loginName=" + user.getAccount() + "|orgId=" + user.getOrgId() + "|resId=" + resId, e);
		}
		return JsonUtil.getJsonString(listDto);
	}

	public void setSystemValOfTao(CallListDto listDao, HttpServletRequest request, String orgId, String account, int isState) {
		listDao.setProjectPath(getProgetUtil(request));
		listDao.setIsReplaceWord(getReplaceWord(orgId, account));
		Map<String, String> dyMap = getDyField(orgId, isState);
		if (dyMap != null) {
			listDao.setDyMoblie(dyMap.get("dy_moblie"));
			listDao.setDyName(dyMap.get("dy_name"));
			listDao.setDyTel(dyMap.get("dy_tel"));
		}
		listDao.setFollowId(SysBaseModelUtil.getModelId());
		listDao.setAutoCall(getAutoCall(request));
		listDao.setSignSetting(getSignSetting(orgId));
	}

	/**
	 * 初始化
	 * 
	 * @param request
	 * @return
	 * @create 2016年1月9日 下午3:02:17 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "/initTaoRes")
	public String initTaoRes(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		long startTime = System.currentTimeMillis();
		String reqId = SysBaseModelUtil.getModelId();
		String result = "";
		String pool = getParameter(request, "pool", "1");
		try {
			result = taoService.initTaoRes(user, pool);
		} catch (Exception e) {
			logger.error("初始化页面待呼资源异常！loginName=" + user.getAccount() + "|orgId=" + user.getOrgId(), e);
		}
		logger.debug("initTaoRes reqId=" + reqId + "耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
		return result;
	}

	/**
	 * 修改筛选条件
	 * 
	 * @return
	 * @create 2015-3-4 下午04:39:43 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping("updateTaoTag")
	public String updateTaoTag(HttpServletRequest request) {
		CallListDto listDto = new CallListDto();
		ShiroUser user = ShiroUtil.getShiroUser();
		String resourceGroupId = getParameter(request, "resourceGroupId", "");
		String isConcat = getParameter(request, "isConcat", "2");
		String orderType = request.getParameter("orderType") == null ? "owner_start_date desc" : request.getParameter("orderType").trim();
		String pool = getParameter(request, "pool", "1");
		TaoTagBean tagBean = new TaoTagBean();
		tagBean.setAccount(user.getAccount());
		tagBean.setOrgId(user.getOrgId());
		tagBean.setGroupId(resourceGroupId);
		tagBean.setOrderType(orderType);
		tagBean.setIsConcat(new Integer(isConcat));
		tagBean.setPool(pool);
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("updateTaoTag reqId=" + reqId + ",account=" + user.getAccount() + "。tagBean=" + JsonUtil.getJsonString(tagBean));
		long startTime = System.currentTimeMillis();
		try {
			listDto = taoService.updateTaoTag(pool, reqId, request, tagBean, user.getOrgId(), user.getAccount());

			if (listDto == null) {
				listDto = new CallListDto();
			}
			listDto.setPool(pool);
			listDto.setIsFromOtherPage("0");
			setSystemValOfTao(listDto, request, user.getOrgId(), user.getAccount(), user.getIsState());
			request.setAttribute("pool", pool);
			setSystemValOfTao(listDto, request, user.getOrgId(), user.getAccount(), user.getIsState());
			logger.debug("updateTaoTag reqId=" + reqId + "耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
		} catch (Exception e) {
			logger.error("updateTaoTag reqId=" + reqId + "修改筛选条件异常！loginName=" + ShiroUtil.getUser().getAccount() + "|orgId=" + ShiroUtil.getUser().getOrgId()
					+ "|参数 tagBean = " + JsonUtil.getJsonString(tagBean), e);
			throw new SysRunException(e);
		}
		return JsonUtil.getJsonString(listDto);
	}

	/**
	 * 在选择优先级时，获取该组下资源数
	 * 
	 * @create 2015年12月29日 下午6:58:23 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping("getMyResByGroupId")
	public String getMyResByGroupId(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String, Object> map = new HashMap<String, Object>();
		String groupId = getParameter(request, "resourceGroupId", "");
		String orderType = getParameter(request, "orderType", "owner_start_date desc");
		String isConcat = getParameter(request, "isConcat", "2");
		map.put("resourceGroupId", groupId);
		map.put("orderType", orderType);
		map.put("isConcat", isConcat);
		map.put("account", user.getAccount());
		map.put("orgId", user.getOrgId());
		int resNum = 0;
		try {
			resNum = comResourceService.getResListCount(map);
			return resNum + "";
		} catch (Exception e) {
			logger.error(
					"选择优先级异常 ！loginName=" + ShiroUtil.getUser().getAccount() + "|orgId=" + ShiroUtil.getUser().getOrgId() + "|参数map="
							+ JsonUtil.getJsonString(map), e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}

	/**
	 * 延后呼叫
	 * 
	 * @return
	 * @create 2013-11-1 上午08:40:31 haoqj
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "delayCall")
	public String updateDelayCallReason(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String resId = request.getParameter("resId") == null ? "" : request.getParameter("resId").trim();
		String resourceGroupId = request.getParameter("resourceGroupId") == null ? "today" : request.getParameter("resourceGroupId").trim();
		String orderType = request.getParameter("orderType") == null ? "owner_start_date desc" : request.getParameter("orderType").trim();
		int isConcat = request.getParameter("isConcat") == null ? 2 : new Integer(request.getParameter("isConcat").trim()); // 用于判断是否来自点击了淘客户页面其他资源
		String waitDate = request.getParameter("waitDate") == "" ? null : request.getParameter("waitDate").trim();
		String delayReason = request.getParameter("delayReason") == null ? "" : request.getParameter("delayReason").trim();
		String pool = getParameter(request, "pool", "1");
		String nextResId = "";
		Map<String, String> map = new HashMap<String, String>();
		map.put("waitDate", waitDate);
		map.put("resCustId", resId);
		map.put("delayReason", delayReason);
		map.put("orgId", user.getOrgId());
		map.put("account", user.getAccount());
		TaoTagBean tagBean = new TaoTagBean();
		tagBean.setGroupId(resourceGroupId);
		tagBean.setOrderType(orderType);
		tagBean.setIsConcat(isConcat);
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("updateDelayCallReason reqId=" + reqId + ",account=" + user.getAccount() + "resourceGroupId=" + resourceGroupId + ",isConcat=" + isConcat
				+ ",orderType=" + orderType);
		try {
			nextResId = taoService.updateDelayCallReason(tagBean, map, resId, user.getOrgId(), user.getAccount(), pool);
		} catch (Exception e) {
			logger.error("updateDelayCallReason reqId=" + reqId + "loginName=" + ShiroUtil.getUser().getAccount() + "|orgId=" + ShiroUtil.getUser().getOrgId()
					+ "|resId=" + resId, e);
		}
		return nextResId;
	}

	@ResponseBody
	@RequestMapping(value = "updatePlanResult")
	public String updatePlanResult(HttpServletRequest request, String resId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			planFactService.updateContactResult(user.getOrgId(), user.getGroupId(), user.getId(), resId, "res", PlanResCR.NO_CHANGE.getResult());
			ResCustInfoBean resCustInfoBean = new ResCustInfoBean();
			resCustInfoBean.setResCustId(resId);
			resCustInfoBean.setOrgId(user.getOrgId());
			resCustInfoBean = resCustInfoMapper.getByCondtion(resCustInfoBean);
			if (resCustInfoBean != null) {
				resCustInfoBean.setIsConcat(1);
				resCustInfoBean.setUpdateAcc(user.getAccount());
				resCustInfoBean.setUpdateDate(new Date());
				resCustInfoMapper.update(resCustInfoBean);
			}

		} catch (Exception e) {
			return AppConstant.RESULT_EXCEPTION;
		}
		return AppConstant.RESULT_SUCCESS;
	}

	@ResponseBody
	@RequestMapping(value = "giveUpRes")
	public String giveUpRes(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String custInfo_name=request.getParameter("custInfo_name");
		String dealTypeStr = request.getParameter("dealType");
		String resId = request.getParameter("resId") == null ? "" : request.getParameter("resId").trim();
		String resourceGroupId = request.getParameter("resourceGroupId") == null ? "today" : request.getParameter("resourceGroupId").trim();
		String orderType = request.getParameter("orderType") == null ? "owner_start_date desc" : request.getParameter("orderType").trim();
		String pool = getParameter(request, "pool", "1");
		int isConcat = request.getParameter("isConcat") == null ? 2 : new Integer(request.getParameter("isConcat").trim()); // 用于判断是否来自点击了淘客户页面其他资源
		String result = "";
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("giveUpRes 。reqId=" + reqId + "。account=" + user.getAccount() + "resId=" + resId);
		long startTime = System.currentTimeMillis();
		try {
			result = taoService.modifyRes2GH(resId, dealTypeStr, resourceGroupId, orderType, isConcat, user.getOrgId(), user.getAccount(), pool, user.getId(),
					user.getGroupId(), custInfo_name);
			logger.debug("giveUpRes reqId=" + reqId + "。耗时=" + (System.currentTimeMillis() - startTime) + "(毫秒)");
		} catch (Exception e) {
			logger.error("reqId=" + reqId + "。放弃客户异常！loginName=" + user.getAccount() + "|orgId=" + user.getOrgId() + "|resId=" + resId, e);
		}
		return result;
	}

	/**
	 * 获取一条资源
	 * 
	 * @param request
	 * @param resId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getMyRes")
	public String getMyRes(HttpServletRequest request, String resId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		int upOrDown = request.getParameter("upOrDown") == null ? 2 : new Integer(request.getParameter("upOrDown").trim());
		String resourceGroupId = request.getParameter("resourceGroupId") == null ? "today" : request.getParameter("resourceGroupId").trim();
		String orderType = request.getParameter("orderType") == null ? "owner_start_date desc" : request.getParameter("orderType").trim();
		int isConcat = request.getParameter("isConcat") == null ? 2 : new Integer(request.getParameter("isConcat").trim());
		String pool = getParameter(request, "pool", "1");
		TaoTagBean tagBean = new TaoTagBean();
		tagBean.setGroupId(resourceGroupId);
		tagBean.setIsConcat(isConcat);
		tagBean.setOrderType(orderType);
		tagBean.setAccount(user.getAccount());
		tagBean.setOrgId(user.getOrgId());
		String resultStr = "";
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("getMyRes reqId=" + reqId + "。account=" + user.getAccount() + ",resId= " + resId);
		long start = System.currentTimeMillis();
		try {
			resultStr = taoService.getMyRes(resId, user.getOrgId(), user.getAccount(), upOrDown, tagBean, pool);
			logger.debug("getMyRes:reqId=" + reqId + "耗时：" + (System.currentTimeMillis() - start) + "毫秒");
		} catch (Exception e) {
			logger.error("getMyRes reqId=" + reqId + "loginName=" + user.getAccount() + "|orgId=" + user.getOrgId() + "|resId=" + resId, e);
		}
		return resultStr;
	}

	@ResponseBody
	@RequestMapping(value = "getFresh")
	public String getFresh(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String resourceGroupId = request.getParameter("resourceGroupId") == null ? "today" : request.getParameter("resourceGroupId").trim();
		String orderType = request.getParameter("orderType") == null ? "owner_start_date desc" : request.getParameter("orderType").trim();
		int isConcat = request.getParameter("isConcat") == null ? 2 : new Integer(request.getParameter("isConcat").trim());
		int upOrDown = request.getParameter("upOrDown") == null ? 2 : new Integer(request.getParameter("upOrDown").trim());
		TaoTagBean tagBean = new TaoTagBean();
		String pool = getParameter(request, "pool", "1");
		tagBean.setIsConcat(isConcat);
		tagBean.setGroupId(resourceGroupId);
		tagBean.setOrderType(orderType);
		tagBean.setAccount(user.getAccount());
		tagBean.setOrgId(user.getOrgId());
		String reqId = SysBaseModelUtil.getModelId();
		CallListDto listDto = new CallListDto();
		logger.debug("getFresh reqId=" + reqId + "。account=" + user.getAccount() + ",resourceGroupId= " + resourceGroupId + ",orderType=" + orderType
				+ ",upOrDown=" + upOrDown);
		long start = System.currentTimeMillis();
		try {
			listDto = taoService.getFresh(request, tagBean, upOrDown, user.getOrgId(), user.getAccount(), user.getIsState(), pool);
			if (listDto == null) {
				listDto = new CallListDto();
			}
			listDto.setPool(pool);
			listDto.setIsFromOtherPage("1");
			setSystemValOfTao(listDto, request, user.getOrgId(), user.getAccount(), user.getIsState());
			request.setAttribute("pool", pool);
			logger.debug("getFresh:reqId=" + reqId + "耗时：" + (System.currentTimeMillis() - start) + "毫秒");
		} catch (Exception e) {
			logger.error("getFresh:reqId=" + reqId + "loginName=" + user.getAccount() + "|orgId=" + user.getOrgId(), e);
			throw new SysRunException(e);
		}
		return JsonUtil.getJsonString(listDto);
	}

	/**
	 * 延后呼叫列表
	 * 
	 * @param request
	 * @return
	 * @create 2016年1月5日 上午9:40:36 wuwei
	 * @history
	 */
	@RequestMapping(value = "delayCallList")
	public String delayCallList(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String resId = request.getParameter("resId") == null ? "" : request.getParameter("resId").trim();
		ResCustInfoBean bean = new ResCustInfoBean();
		try {
			bean.setResCustId(resId);
			bean.setUpdateAcc(user.getAccount());
			bean.setOrgId(user.getOrgId());
			bean.setUpdateDate(new Date());
			bean.setFilterType(1);
			comResourceService.modify(bean);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "redirect:/res/tao/taoMyRes?resId=" + resId;
	}

	/**
	 * 把延后参数，传入到对应页面
	 * 
	 * @return
	 * @create 2014-3-20 下午03:40:36 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping("toUpdateDelayCallReason")
	public String toUpdateDelayCallReason(HttpServletRequest request) {
		try {
			// 默认待呼时间往后延长半小时
			String waitDate = DateUtil.getNextHalfHourTime();
			return waitDate;
		} catch (Exception e) {
			logger.error("*******获取延后时间失败！********" + e.getMessage(), e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}

	/**
	 * 淘到客户处理
	 * 
	 * @return
	 * @create 2013-11-1 上午08:42:08 haoqj
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "addMyCust")
	public String addMyCust(HttpServletRequest request, String resCustId, String followDate, String custFollowId, String effectiveness, String labelCode,
			String labelName, String feedbackComment, String feedbackImg, String followType, TsmCustGuide custGuide, String concatPhone, String concatName,String isAddDate) {
		String custInfo_name=request.getParameter("custInfoName_tao");	
		ShiroUser user = ShiroUtil.getUser();
		String reqId = SysBaseModelUtil.getModelId();
		if (logger.isDebugEnabled())
			logger.debug("addMyCust reqId=" + reqId + "account=" + user.getAccount() + "|resCustId=" + resCustId + "|custFollowId=" + custFollowId
					+ "concatPhone=" + concatPhone + "|concatName=" + concatName);
		String suitProcId = request.getParameter("suitProcId") == null ? "" : request.getParameter("suitProcId").trim();
		String resourceGroupId = request.getParameter("resourceGroupId_tao") == null ? "all" : request.getParameter("resourceGroupId_tao").trim();
		String orderType = request.getParameter("orderType_tao") == null ? "owner_start_date desc" : request.getParameter("orderType_tao").trim();
		int isConcat = request.getParameter("isConcat_tao") == null ? 2 : new Integer(request.getParameter("isConcat_tao").trim());
		String opterType = getParameter(request, "opterType", "1");
		CustFollowBean custFollow = new CustFollowBean();
		String pool = getParameter(request, "pool_tao", "1");
		TaoTagBean tagBean = new TaoTagBean();
		tagBean.setGroupId(resourceGroupId);
		tagBean.setIsConcat(isConcat);
		tagBean.setOrderType(orderType);
		tagBean.setAccount(user.getAccount());
		tagBean.setOrgId(user.getOrgId());
		tagBean.setResourceId(resCustId);
		long startTime = System.currentTimeMillis();
		try {
			custFollow.setCustFollowId(custFollowId);
			custFollow.setLabelCode(labelCode);
			custFollow.setLabelName(labelName);
			custFollow.setFeedbackComment(feedbackComment);
			custFollow.setFeedbackImg(feedbackImg);
			custFollow.setEffectiveness(new Integer(effectiveness));
			custFollow.setFollowType(followType);
			custFollow.setFollowDate(DateUtil.parse(followDate, DateUtil.hour24HMSPattern));
			
			if (isAddDate != null && !"".equals(isAddDate)) {
				if (isAddDate.equals("2")) {
					custFollow.setFollowDate(null);
				}
			}
			ResCustInfoBean custInfo = new ResCustInfoBean();
			custInfo.setResCustId(resCustId);
			custInfo.setLabelCode(labelCode);
			custInfo.setLabelName(labelName);
			resCustId = taoService.addMyCust(reqId, user.getOrgId(), user.getAccount(), user.getGroupId(), user.getId(), user.getName(), user.getIsState(),
					custInfo, custFollow, custGuide, suitProcId, tagBean, concatPhone, concatName, pool, opterType,0);
//			logUserOperateService.setUserOperateLog(AppConstant.Module_id9 ,AppConstant.Module_Name9 ,AppConstant.Operate_id63 ,AppConstant.Operate_Name63 ,custInfo_name,"" );
			logger.debug("addMyCust reqId=" + reqId + "耗时= " + (System.currentTimeMillis() - startTime) + "（毫秒）");
		} catch (Exception e) {
			logger.error("addMyCust reqId=" + reqId + "。淘到客户异常!loginName=" + user.getAccount() + "|orgId=" + user.getOrgId() + "|custId=" + resCustId, e);
		}
		return resCustId;
	}

	/**
	 * 修改资料时局部刷新
	 * 
	 * @return
	 * @create 2015年12月24日 下午4:24:17 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "taoMainPlatform")
	public String taoMainPlatform(HttpServletRequest request) {
		String reqId = SysBaseModelUtil.getModelId();
		ShiroUser user = ShiroUtil.getShiroUser();
		int opType = user.getIsState();
		String id = "";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		String custId = getParameter(request, "custId", "");
		String groupName = "";
		List<CustFieldSet> fieldSets = null;
		List<ResCustInfoDetailBean> totalList = null;
		String pname = "";
		String cname = "";
		String oname = "";
		String concatDay = "";
		String mainPhone = "";
		String custName = "";
		String concatId = "";
		String concatName = "";
		logger.debug("taoMainPlatform reqId=" + reqId + "account=" + user.getAccount() + ",custId=" + custId);
		long startTime = System.currentTimeMillis();
		try {
			ResCustInfoBean custInfo = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custId);
			if (custInfo != null) {
				concatName = custInfo.getName();
				custName = custInfo.getName();
				mainPhone = StringUtils.isEmpty(custInfo.getMobilephone()) ? custInfo.getTelphone() : custInfo.getMobilephone();
				// Map<String, String> dateMap =
				// custFollowService.getLastConcatDay(map);
				// if (dateMap != null) {
				// int seg = com.qftx.base.util.DateUtil.dateSub(new
				// SimpleDateFormat("yyyy-MM-dd").parse(dateMap.get("endTime")),
				// new SimpleDateFormat(
				// "yyyy-MM-dd").parse(dateMap.get("startTime")));
				// concatDay = (seg + "");
				// }
				if (custInfo.getResGroupId() != null) {
					Map<String, String> resultMap = cachedService.getOrgResGroupNames(user.getOrgId());
					groupName = resultMap.get(custInfo.getResGroupId());
				}
				id = custInfo.getResCustId();
				Integer pid = custInfo.getProvinceId();
				// 省市区
				if (pid != null) {
					ChinaProvinceBean cpb = areaService.getChinaProvinceByPid(pid);
					pname = cpb.getPname();
					Integer cid = custInfo.getCityId();
					if (cid != null) {
						ChinaCityBean ccb = areaService.getChinaCityByCid(cid);
						cname = ccb.getCname();
						Integer oid = custInfo.getCountyId();
						if (oid != null) {
							ChinaCountyBean cob = areaService.getChinaCountyByOid(oid);
							oname = cob.getOname();
						}
					}
				}
			}
			if (opType == 1) {
				if (custInfo != null) {
					totalList = resCustInfoDetailService.getCustsInfoDetails(user.getOrgId(), custInfo.getResCustId());
					if (totalList != null && totalList.size() > 0) {
						concatName = totalList.get(0).getName();
						mainPhone = StringUtils.isNotEmpty(totalList.get(0).getTelphone()) ? totalList.get(0).getTelphone() : totalList.get(0).getTelphonebak();
						concatId = totalList.get(0).getTscidId();
					}
				}
				// 查询缓存 企业字段
				fieldSets = cachedService.getComFiledSets(user.getOrgId());
			} else {
				// 查询缓存 企业字段
				fieldSets = cachedService.getPersonFiledSets(user.getOrgId());
			}
			Map<String, String> bzMap = resCustInfoLogService.getTopConcat(map);
			String context = "";
			String nextConcatTime = "";
			if (bzMap != null) {
				context = bzMap.get("context");
				nextConcatTime = bzMap.get("nextConcatTime");
			}
			int total = resCustInfoLogService.getTotalLog(map);
			dataMap.put("custInfo", custInfoService.getCustDto(custInfo, opType, totalList));
			dataMap.put("id", id);
			dataMap.put("context", context);
			dataMap.put("nextConcatTime", nextConcatTime);
			dataMap.put("total", total + "");
			dataMap.put("groupName", groupName);
			dataMap.put("state", opType);
			dataMap.put("concatDay", concatDay);
			dataMap.put("custName", custName);
			dataMap.put("concatName", concatName);
			dataMap.put("concatId", concatId);
			dataMap.put("serverDay", user.getServerDay());
			dataMap.put("mainPhone", mainPhone);
			dataMap.put("company", custInfo == null ? "" : custInfo.getCompany());
			dataMap.put("custData", custInfoService.getCustData(fieldSets, opType, pname, cname, oname, custInfo));
			dataMap.put("followId", GuidUtil.getId());// 提前生成followId
			logger.debug("taoMainPlatform reqId=" + reqId + " 耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
		} catch (Exception e) {
			logger.error("获取客户通话信息 异常 !reqId=" + reqId + "。loginName=" + user.getAccount() + "|orgId=" + user.getOrgId(), e);
			throw new SysRunException(e);
		}
		/*
		 * if (opType == 1) { return "tsm/resource/taoMain"; } else { return
		 * "tsm/resource/taoMain_persion"; }
		 */
		return JsonUtil.getJsonString(dataMap);
	}

	@ResponseBody
	@RequestMapping(value = "queryCallNum")
	public String queryCallNum(HttpServletRequest request, String detailId, String resId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String, String> map = new HashMap<String, String>();
		try {
			ResCustInfoDetailBean rbean = resCustInfoDetailService.getByPrimaryKeyAndOrgId(user.getOrgId(), detailId,resId);
			map.put("callNum", rbean.getCallNum() == null ? "0" : rbean.getCallNum() + "");
			map.put("code", "0");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			map.put("code", "1");
		}
		return JsonUtil.getJsonString(map);
	}

	@ResponseBody
	@RequestMapping(value = "getDetailId")
	public String getDetailId(HttpServletRequest request, String detailId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String jsonStr = "";
		try {
			ResCustInfoDetailBean rbean = resCustInfoDetailService.getByPrimaryKeyAndOrgId(user.getOrgId(), detailId,"");
			jsonStr = JsonUtil.getJsonString(rbean);
		} catch (Exception e) {
			jsonStr = AppConstant.RESULT_EXCEPTION;
		}
		System.out.println("返回结果==" + jsonStr + "|" + detailId);
		return jsonStr;
	}

	/**
	 * 销售导航局部刷新
	 * 
	 * @param request
	 * @return
	 * @create 2016年1月7日 下午2:47:39 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "taoCustGuide")
	public String taoCustGuide(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String, Object> guideMap = new HashMap<String, Object>();
		String custId = getParameter(request, "custId", "");
		ResCustInfoBean custInfo = new ResCustInfoBean();
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("taoCustGuide reqId=" + reqId + ",account= " + user.getAccount() + ",custId=" + custId);
		long startTime = System.currentTimeMillis();
		try {
			custInfo.setResCustId(custId);
			guideMap = custGuideService.getOptionMap(request, user.getOrgId());
			guideMap.put("defDate", getCustCacheDate(request));
			guideMap.put("custId", custId);
			guideMap.put("serverDay", user.getServerDay());
			logger.debug("taoCustGuide reqId=" + reqId + "耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
		} catch (Exception e) {
			logger.error("taoCustGuide reqId=" + reqId + "loginName=" + user.getAccount() + "|orgId=" + user.getOrgId() + "|custId=" + custId, e);
		}
		// return "tsm/resource/taoCust_guide";
		return JsonUtil.getJsonString(guideMap);
	}

	/**
	 * 延后呼叫资源局部刷新
	 * 
	 * @param request
	 * @return
	 * @create 2016年1月7日 下午2:47:39 wuwei
	 * @history
	 */
	@RequestMapping(value = "taoCustDelay")
	public String taoCustDelay(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("taoCustDelay reqId=" + reqId + ",account= " + user.getAccount());
		long startTime = System.currentTimeMillis();
		try {
			taoService.taoCustDelay(request, user.getOrgId(), user.getAccount());
			setDyField(request);
			logger.debug("taoCustDelay reqId=" + reqId + "耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
		} catch (Exception e) {
			logger.error("taoCustDelay reqId=" + reqId + "loginName=" + ShiroUtil.getUser().getAccount() + "|orgId=" + ShiroUtil.getUser().getOrgId(), e);
			throw new SysRunException(e);
		}
		return "tao/taoCust_delay";
	}

	/**
	 * 延后呼叫资源局部刷新
	 * 
	 * @param request
	 * @return
	 * @create 2016年1月7日 下午2:47:39 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "getRemarkList")
	public String getRemarkList(HttpServletRequest request) {
		String custId = request.getParameter("custId");
		ShiroUser user = ShiroUtil.getShiroUser();
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("taoCustDelay reqId=" + reqId + ",account= " + user.getAccount());
		long startTime = System.currentTimeMillis();
		List<ResCustinfoLogBean> zybzList = new ArrayList<ResCustinfoLogBean>();
		List<BzDto> bzDtos = new ArrayList<BzDto>();
		Map<String, Object> remarkMap = new HashMap<String, Object>();
		try {
			zybzList = resCustInfoLogService.getCustinfoLogBeans(user.getOrgId(), custId);

			if (zybzList != null && zybzList.size() > 0) {

				for (ResCustinfoLogBean bean : zybzList) {
					BzDto bzDto = new BzDto();
					bzDto.setContext(bean.getContext());
					bzDto.setInputTime(DateUtil.format(bean.getInputtime(), DateUtil.hour24HMSPattern));
					bzDto.setNextConcatTime(DateUtil.format(bean.getNextConcatTime(), DateUtil.hour24HMSPattern));
					bzDtos.add(bzDto);
				}
			}
			remarkMap.put("bzDtos", bzDtos);
			logger.debug("getRemarkList reqId=" + reqId + "耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
		} catch (Exception e) {
			logger.error("taoCustDelay reqId=" + reqId + "loginName=" + ShiroUtil.getUser().getAccount() + "|orgId=" + ShiroUtil.getUser().getOrgId(), e);
			throw new SysRunException(e);
		}
		return JsonUtil.getJsonString(remarkMap);
	}

	public List<CustFollowDto> getFollowList(String custId, List<ResCustinfoLogBean> zybzList) {
		ShiroUser user = ShiroUtil.getShiroUser();
		// 根据客户id 查询所有 跟进信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", user.getOrgId());
		map.put("custId", custId);
		map.put("state", user.getIsState());
		List<CustFollowDto> custFollows = custFollowService.queryCustFollowByCustId(map);
		Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
		// 组装跟进ID
		List<String> followIdSbf = new ArrayList<>();
		Map<String, String> optionMap = cachedService.getOrgSaleProcess(user.getOrgId());
		// 将列表中的标签CODE值根据tsm_$_optionMap转换为标签NAME
		for (CustFollowDto followDto : custFollows) {
			followDto.setOwnerName(nameMap.get(followDto.getFollowAcc()) == null ? followDto.getFollowAcc() : nameMap.get(followDto.getFollowAcc()));
			followIdSbf.add(followDto.getCustFollowId());
			followDto.setShowLastActionDate(followDto.getLastActionDate() != null ? com.qftx.base.util.DateUtil.formatDate(followDto.getLastActionDate(),
					com.qftx.base.util.DateUtil.Data_ALL) : "");
			followDto.setShowNextActionDate(followDto.getNextActionDate() != null ? com.qftx.base.util.DateUtil.formatDate(followDto.getNextActionDate(),
					com.qftx.base.util.DateUtil.Data_ALL) : "");
			if (optionMap != null) {
				followDto.setOptionName(optionMap.get(followDto.getOptionlistId()));
			}
			String labelName = followDto.getLabelName();
			if (StringUtils.isNotBlank(labelName)) {
				labelName = labelName.replaceAll("#", "，");
				labelName = labelName.substring(0, labelName.length() - 1);
				followDto.setLabelName(labelName);
			}
		}

		// 获取相关跟进ID 的录音记录
		if (followIdSbf != null && followIdSbf.size() > 0) {
			String followIds = followIdSbf.toString().substring(0, followIdSbf.toString().length() - 1);
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
				for (CustFollowDto followDto : custFollows) { // 重新组装 跟进记录
					if (map1.get(followDto.getCustFollowId()) != null) {
						followDto.setUrlList(map1.get(followDto.getCustFollowId()));
					}
				}
			}
			if (custFollows == null) {
				custFollows = new ArrayList<CustFollowDto>();
			}
			if (zybzList == null) {
				zybzList = new ArrayList<ResCustinfoLogBean>();
			}
			for (ResCustinfoLogBean bean : zybzList) {
				CustFollowDto custFollowDto = new CustFollowDto();
				custFollowDto.setShowLastActionDate(DateUtil.format(bean.getInputtime(), DateUtil.hour24HMSPattern));
				custFollowDto.setShowNextActionDate(DateUtil.format(bean.getNextConcatTime(), DateUtil.hour24HMSPattern));
				custFollowDto.setFeedbackComment(bean.getContext());
				custFollows.add(custFollowDto);
			}
		}
		sortList(custFollows);
		return custFollows;
	}

	@RequestMapping(value = "updateConcat4Res")
	public void updateConcat4Res(HttpServletRequest request, String custId, String lianXiId, String orgId, String isConcat, String timeLength) {
		ShiroUser user = ShiroUtil.getShiroUser();
		logger.debug("======updateConcat4Res 请求 custId=" + custId + ",lianXiId=" + lianXiId + ",orgId=" + orgId + ",isConcat=" + isConcat + ",timeLength="
				+ timeLength + "=====");
		taoService.updateConcat4Res(custId, lianXiId, orgId, user.getAccount(), isConcat, timeLength,user);
	}

	@RequestMapping(value = "updateClueConcat")
	public void updateClueConcat(HttpServletRequest request, String custId, String lianXiId, String orgId, String isConcat, String timeLength) {
        ShiroUser user = ShiroUtil.getShiroUser();
		logger.debug("======updateClueConcat 请求 custId=" + custId + ",lianXiId=" + lianXiId + ",orgId=" + orgId + ",isConcat=" + isConcat + ",timeLength="
				+ timeLength + "=====");
      try {
		Map<String,Object> map=new HashMap<String,Object>();
		  map.put("clueId",custId);
		  map.put("orgId",orgId);
          map.put("userAccount",user.getAccount());
//		  map.put("isConcat",isConcat);
//		  map.put("timeLength",timeLength);
		String sendpar = JSON.toJSONString(map, true);
		String url = ConfigInfoUtils.getStringValue("tsm_clue_url");
		url=url+"/api/updateLastContactTime";
		HttpUtil.doPostJSON(url, sendpar);
      }catch (Exception e){
      	e.printStackTrace();
	  }
	}

	@ResponseBody
	@RequestMapping(value = "getCallNum")
	public String getCallNum(HttpServletRequest request, String id) throws Exception {
		ShiroUser user = ShiroUtil.getShiroUser();
		int state = 0;
		Org org = cachedService.getAuthOrgCatch(user.getOrgId());
		if (org != null) {
			state = org.getState();
		}

		Map<String, String> map = taoService.getCallNum(state, id, user.getOrgId());
		return JsonUtil.getJsonString(map);
	}

	// 根据联系人判断是否已经关联过微信
	@ResponseBody
	@RequestMapping(value = "getWxBindInfo")
	public String getWxBindInfo(String concatId, String name) {
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String, String> map = new HashMap<String, String>();
		try {
			WxBindBean bean = new WxBindBean();
			bean.setOrgId(user.getOrgId());
			bean.setLinkNameId(concatId);
			bean.setAccount(user.getAccount());
			bean = taoService.getWxBindInfo(bean);
			map.put("custId", concatId);
			map.put("custName", name);
			if (bean != null) {
				map.put("wxLoginId", bean.getWxLoginId());
				map.put("wxId", bean.getWxId());
				map.put("wxName", bean.getWxName());
			}
		} catch (Exception e) {
			logger.error("getWxBindInfo loginAcc=" + user.getAccount() + "concatId=" + concatId + ",error msg= " + e.getMessage(), e);
		}
		return JSON.toJSONString(map);
	}

	// 根据wxid判断是否已经绑定
	@ResponseBody
	@RequestMapping(value = "IsBindedByWx")
	public String IsBindedByWx(String custId, String custName, String wxLoginId, String wxId, String wxName) {
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String, String> map = new HashMap<String, String>();
		try {
			WxBindBean bean = new WxBindBean();
			bean.setOrgId(user.getOrgId());
			bean.setLinkNameId(custId);
			bean.setAccount(user.getAccount());
			bean.setWxId(wxId);
			bean.setWxLoginId(wxLoginId);
			bean.setWxName(wxName);
			bean.setLinkName(custName);
			map = taoService.getBindedByWx(bean);
		} catch (Exception e) {
			logger.error("IsBindedByWx loginAcc=" + user.getAccount() + "concatId=" + custId + ",error msg= " + e.getMessage(), e);
		}
		return JSON.toJSONString(map);
	}

	// 关联或解绑微信
	@ResponseBody
	@RequestMapping(value = "bindOrUnBind")
	public String bindOrUnBind(String custId, String custName, String wxLoginId, String wxId, String wxName, String type) {
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String, String> map = new HashMap<String, String>();
		String result = "";
		logger.debug("custId=" + custId + ",custName=" + custName + ",wxLoginId=" + wxLoginId + ",wxId=" + wxId + ",wxName=" + wxName + ",type=" + type);
		try {
			WxBindBean bean = new WxBindBean();
			bean.setOrgId(user.getOrgId());
			bean.setLinkNameId(custId);
			bean.setLinkName(custName);
			bean.setWxLoginId(wxLoginId);
			bean.setWxId(wxId);
			bean.setWxName(wxName);
			bean.setAccount(user.getAccount());
			bean.setType(user.getIsState());
			taoService.addOrUpdateWx(bean, user.getAccount(), user.getOrgId(), type, user.getIsState());
			result = "0";
		} catch (Exception e) {
			result = "-1";
			logger.error("getWxBindInfo loginAcc=" + user.getAccount() + ",custId=" + custId + ",custName=" + custName + ",wxLoginId=" + wxLoginId + ",wxId="
					+ wxId + ",wxName=" + wxName + ",type=" + type + ",error msg= " + e.getMessage(), e);
		}
		return result;
	}

	public void sortList(List<CustFollowDto> list) {
		Collections.sort(list, new Comparator<CustFollowDto>() {
			public int compare(CustFollowDto pFirst, CustFollowDto pSecond) {
				long diff = 0;
				long aFirst = com.qftx.common.util.StringUtils.isEmpty(pFirst.getShowLastActionDate()) ? -1 : DateUtil.parse(pFirst.getShowLastActionDate(),
						DateUtil.hour24HMSPattern).getTime();
				long aSecond = com.qftx.common.util.StringUtils.isEmpty(pSecond.getShowLastActionDate()) ? -1 : DateUtil.parse(pSecond.getShowLastActionDate(),
						DateUtil.hour24HMSPattern).getTime();
				diff = aFirst - aSecond;
				if (diff > 0) {
					return -1;
				} else if (diff < 0) {
					return 1;
				} else {
					return 0;
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping(value = "setDelay")
	public String setDelay(HttpServletRequest request, String resId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			taoService.upateCustDelay(resId, user.getOrgId(), user.getAccount());
		} catch (Exception e) {
			return AppConstant.RESULT_EXCEPTION;
		}
		return AppConstant.RESULT_SUCCESS;
	}

	/**
	 * 设置重点关注
	 * 
	 * @param request
	 * @return
	 * @create 2015年12月25日 下午5:21:37 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "setMajor")
	public String setMajor(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String, String> map = new HashMap<String, String>();
		String custId = getParameter(request, "custId", "");
		String isMajar = getParameter(request, "isMajar", "");
		if ("".equals(isMajar) || isMajar == null) {
			isMajar = "0";
		}
		map.put("orgId", user.getOrgId());
		map.put("custId", custId);
		map.put("isMajor", isMajar);
		try {
			comResourceService.modifyMajorId(map);
			return AppConstant.RESULT_SUCCESS;
		} catch (Exception e) {
			logger.error("loginName = " + user.getName() + "参数=" + JsonUtil.getJsonString(map), e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}

	/**
	 * 改变自动呼叫状态
	 * 
	 * @return
	 * @create 2014-1-13 下午07:33:49 haoqj
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "changeAutoCall")
	public String changeAutoCall(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String reqId = SysBaseModelUtil.getModelId();
		try {
			String isAutoCall = request.getParameter("isAutoCall") == null ? "0" : request.getParameter("isAutoCall"); // 是否自动呼叫:
			logger.debug("changeAutoCall reqId=" + reqId + ",account=" + user.getAccount() + ",isAutoCall=" + isAutoCall);
			if ("1".equals(isAutoCall)) { // 设置自动呼叫
				request.getSession().setAttribute("isAutoCall", isAutoCall);
			} else { // 　取消自动呼叫
				request.getSession().removeAttribute("isAutoCall");
			}
			return AppConstant.RESULT_SUCCESS;
		} catch (Exception e) {
			logger.error("changeAutoCall reqId=" + reqId + "account=" + user.getAccount() + "开启自动联播异常", e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}
	
	
	/**
	 * 获取销售管理中自动连拨设置
	 * 
	 * @return
	 * @create 2014-1-13 下午07:33:49 haoqj
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "getSallAutoCall")
	public String getSallAutoCall(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String open="0";
		try {
			List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50003, user.getOrgId());
			if(!list.isEmpty() && list.get(0) != null){
				String dicValue = list.get(0).getDictionaryValue();
				open = StringUtils.isNotBlank(dicValue) ? dicValue : "0";
			}
			return open;
		} catch (Exception e) {
			logger.error("getSallAutoCall"+ "orgId=" + user.getOrgId() + "销售管理开启自动联播异常", e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}

	/**
	 * 屏幕取值生成客户
	 * 
	 * @return
	 * @create 2014-7-18 下午02:15:16 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping("addCustByScreen")
	public String addCustByScreen(HttpServletRequest request, ResCustInfoBean custInfo, CustFollowBean custFollow, TsmCustGuide custGuide) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("addCustByScreen reqId = " + reqId + ",account=" + user.getAccount() + ",custInfo=" + JsonUtil.getJsonString(custInfo));
		logger.debug("addCustByScreen reqId = " + reqId + ",account=" + user.getAccount() + ",custFollow=" + JsonUtil.getJsonString(custFollow));
		logger.debug("addCustByScreen reqId = " + reqId + ",account=" + user.getAccount() + ",custGuide=" + JsonUtil.getJsonString(custGuide));
		try {
			// taoService.saveCustByScreen(custInfo, custFollow, custGuide);
			return "/tsm/resource/addCustByScreen";
		} catch (Exception e) {
			return AppConstant.RESULT_EXCEPTION;
		}
	}

	// 获取项目地址
	public String getProgetUtil(HttpServletRequest request) {
		if (80 == request.getServerPort()) {
			return request.getScheme() + "://" + request.getServerName() + request.getContextPath();
		} else {
			return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		}
	}

	/**
	 * 设置资源去重验证类型
	 * 
	 * @作者 徐承恩
	 * @创建时间 2014年4月11日 上午10:48:51
	 */
	private void setValiDateType(HttpServletRequest request, String orgId) {
		List<DataDictionaryBean> dic = cachedService.getDirList(AppConstant.DATA14 + "_" + orgId, orgId);
		if (!dic.isEmpty() && "1".equals(dic.get(0).getDictionaryValue())) {
			dic = cachedService.getDirList(AppConstant.DATA11 + "_" + orgId, orgId);
			request.setAttribute("phoneValiDate", !dic.isEmpty() ? dic.get(0).getDictionaryValue() : 1);
			dic = cachedService.getDirList(AppConstant.DATA12 + "_" + orgId, orgId);
			request.setAttribute("unitValiDate", !dic.isEmpty() ? dic.get(0).getDictionaryValue() : 0);
			dic = cachedService.getDirList(AppConstant.DATA22 + "_" + orgId, orgId);
			request.setAttribute("unitHomeValiDate", !dic.isEmpty() ? dic.get(0).getDictionaryValue() : 0);
		}
	}

	/**
	 * 向VIEW响应当前用户剩余通话时长（套餐包+通信包）
	 * 
	 * @create 2013-11-28 下午05:11:09 徐承恩
	 * @history
	 */
	private void getUserTimeLength(HttpServletRequest request) {
		String account = ShiroUtil.getUser().getAccount();
		String isBilling = "";
		String result = ShiroUtil.getUser().getIsBilling();
		if (StringUtils.isBlank(result)) {
			isBilling = "1";
		} else {
			isBilling = result;
		}
		// 获得剩余总通话时长
		Integer timeLength = userService.getTimeLengthByAccount(account);
		request.setAttribute("isBilling", isBilling);
		request.setAttribute("timeLength", timeLength);

		// 获取短信剩余条数
		User u = userService.getByAccount(account);
		request.setAttribute("user", u);
	}

	/**
	 * 是否超出最大资源数
	 * 
	 * @return
	 * @create 2015年4月25日 下午5:29:24 wuwei
	 * @history
	 */
	private int isBeyondMaxRes(String account, String orgId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgId", orgId);
		map.put("ownerAcc", account);
		int myCustSum = comResourceService.getMyCustSum(map);
		int flg = 0;
		String code = AppConstant.DATA03 + "_" + orgId;
		List<DataDictionaryBean> dataList = cachedService.getDirList(code, orgId);
		if (dataList.size() > 0) {
			if (!"".equals(dataList.get(0).getDictionaryValue()) && null != dataList.get(0).getDictionaryValue()) {
				// //获取当前用户客户上限
				Integer dataVal = new Integer(dataList.get(0).getDictionaryValue());
				String isOpen = dataList.get(0).getIsOpen();
				code = AppConstant.DATA15 + "_" + orgId;
				dataList = cachedService.getDirList(code, orgId);
				Integer data15 = 0;
				if (dataList.size() > 0) {
					data15 = new Integer(dataList.get(0).getDictionaryValue());
					// 比较客户数是否超过当前客户上限
					if (myCustSum >= dataVal && data15 == 1 && "1".equals(isOpen)) {
						flg = 1;
					}
				}
			}
		}
		return flg;
	}

	public String getTimeElngth(String orgId) {
		String isEffect = cachedService.getDirList(AppConstant.DATA16, orgId).get(0).getDictionaryValue();
		String timeElngth = "0";
		if ("1".equals(isEffect)) {
			timeElngth = cachedService.getDirList(AppConstant.DATA04, orgId).get(0).getDictionaryValue();
		}
		return timeElngth;
	}

	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}

	public void autoCall(HttpServletRequest request) {
		String autoCall = "0";
		autoCall = (String) request.getSession().getAttribute("isAutoCall");
		if (StringUtils.isEmpty(autoCall)) {
			autoCall = "0";
		}
		request.setAttribute("autoCall", autoCall);
	}

	public String getAutoCall(HttpServletRequest request) {
		String autoCall = "0";
		autoCall = (String) request.getSession().getAttribute("isAutoCall");
		if (StringUtils.isEmpty(autoCall)) {
			autoCall = "0";
		}
		return autoCall;
	}
	
	
	
	/**
	 * 获取行动标签分层显示
	 * 
	 * @return
	 * @create 2017-10-26 下午07:33:49 xiaoxh
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "/getLableList")
	public List<Map<String,Object>> getLableList(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			List<OptionGroupBean> getOptionGroupList=cachedService.getOptionGroupList(AppConstant.SALES_TYPE_TEN,user.getOrgId());
			List<OptionBean> labelList = cachedService.getOptionList(AppConstant.SALES_TYPE_TEN, user.getOrgId());
			if(getOptionGroupList!=null&&getOptionGroupList.size()>0){
				
				for(OptionGroupBean group:getOptionGroupList){
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("groupId", group.getGroupId());
					map.put("groupName", group.getGroupName());
					map.put("groupName", group.getGroupName());
					if(labelList!=null&&labelList.size()>0){
						List<OptionBean> returnList=new ArrayList<OptionBean>();
						for(OptionBean optionBean:labelList){
							if(optionBean.getGroupId()==group.getGroupId()||optionBean.getGroupId().endsWith(group.getGroupId())){
								returnList.add(optionBean);
							}
						}
						map.put("optionList", returnList);
					}
					list.add(map);
				}
				
			}
		
		} catch (Exception e) {
			logger.error("getLableList"+ "orgId=" + user.getOrgId() + "获取行动标签分层显示", e);
		}
		return list;
	}
	
	/**
	 * 行动标签分层显示保存
	 * 
	 * @return
	 * @create 2017-10-26 下午07:33:49 xiaoxh
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "/setLableList")
	public boolean setLableList(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<OptionDto> opList = new ArrayList<OptionDto>();
		String optionlistIds = request.getParameter("optionlistIds");
		List<String> idList=new ArrayList<String>();
		try {
			   if (StringUtils.isNotEmpty(optionlistIds)) {
	      		    String[] blackIds_list = optionlistIds.split(",");
	      		    idList = Arrays.asList(blackIds_list);
	       	}
			List<OptionBean> labelList = cachedService.getOptionList(AppConstant.SALES_TYPE_TEN, user.getOrgId());
			if(idList!=null&&idList.size()>0){
				for(String ids:idList){
					if(labelList!=null&&labelList.size()>0){
						for(OptionBean optionBean:labelList){
                           if(ids==optionBean.getOptionlistId()||ids.endsWith(optionBean.getOptionlistId())){
               				OptionDto optionDto = new OptionDto();
            				optionDto.setIsDefault(optionBean.getIsDefault() + "");
            				optionDto.setOptionId(optionBean.getOptionlistId());
            				optionDto.setOptionName(optionBean.getOptionName());
            				optionDto.setSort(optionBean.getSort());
            				opList.add(optionDto);  
                           }
						}
					
					}	
				}
			}	
	        ComparatorChain chain = new ComparatorChain();
	        chain.addComparator(new BeanComparator("sort"),false);//true,fase正序反序
			Collections.sort(opList,chain);
			 cachedService.setLableList(user.getOrgId(), opList);
		} catch (Exception e) {
			logger.error("getLableList"+ "orgId=" + user.getOrgId() + "设置行动标签分层显示失败", e);
			return true;
		}
		return true;
	}
	
	/**
	 * 跳转行动标签页面
	 * 
	 * @return
	 * @create 2017-10-26 下午07:33:49 xiaoxh
	 * @history
	 */
	@RequestMapping(value = "/toActionTagChoose")
	public String toActionTagChoose(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String optionlistIds = request.getParameter("optionlistIds");
//		List<String> idList=new ArrayList<String>();
		try {
//			   if (StringUtils.isNotEmpty(optionlistIds)) {
//	      		    String[] blackIds_list = optionlistIds.split("#");
//	      		    idList = Arrays.asList(blackIds_list);
//	       	}
			   
		} catch (Exception e) {
			logger.error("getLableList"+ "orgId=" + user.getOrgId() + "跳转行动标签页面失败", e);

		}
	request.setAttribute("optionList", optionlistIds);
	return "/tao/action-tag-choose";
	}
}
