package com.qftx.common.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.auth.service.OrgService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.common.util.constants.SysConstant;
import com.qftx.tsm.cust.bean.ResCustInfoDetailBean;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.cust.dto.ResourceGroupDto;
import com.qftx.tsm.cust.dto.StaffDto;
import com.qftx.tsm.cust.service.ComResourceGroupService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.log.bean.LogUserOperateBean;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.CustSearchSet;

import sun.misc.BASE64Decoder;

public class BaseAction {
	@Autowired
	private CachedService cachedService;
	@Autowired
	private ComResourceGroupService comResourceGroupService;
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private OrgGroupUserService orgGroupUserService;
	@Autowired
	private LogUserOperateService logUserOperateService;
	@Autowired
	private UserService userService;
	@Autowired transient private OrgService orgService;

	private Logger logger = Logger.getLogger(BaseAction.class);

	public String getParameter(HttpServletRequest request, String name, String value) {
		String result = value;
		try {
			result = (request.getParameter(name) != null && !"".equals(request.getParameter(name))) ? request.getParameter(name) : value;
		} catch (Exception e) {
			throw new SysRunException(e, true);
		}

		return result;
	}

	public List<String> getLabelNameList(List<String> codes, String orgId) {
		List<String> list = new ArrayList<String>();
		List<OptionBean> getSubOptionList = cachedService.getOptionList(AppConstant.SALES_TYPE_TEN, orgId);
		Map<String, String> changeMap = cachedService.changeOptionListToMap(getSubOptionList);
		for (String code : codes) {
			list.add(changeMap.get(code));
		}
		return list;
	}

	/**
	 * 设置自定义查询字段
	 * 
	 * @param request
	 * @param orgId
	 * @param state
	 * @create 2016年9月18日 上午9:17:13 lixing
	 * @history
	 */
	public void setDefinedQueryFileds(HttpServletRequest request, String orgId, Integer state) {
		List<CustFieldSet> list = getIsQueryList(orgId, state);
		request.setAttribute("queryFileds", list);
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

	/**
	 * 获取最后一天
	 *
	 * @param type
	 *            1-当天 2-本周 3-本月 4-半年
	 * @return
	 * @create 2015年12月14日 下午3:48:05 lixing
	 * @history
	 */
	public static String getEndDateStr(Integer type) {
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

	public void setCustomFiled(ShiroUser user, HttpServletRequest request) {
		List<CustFieldSet> fieldSets = null;
		if (user.getIsState() == 1) {// 企业资源
			fieldSets = cachedService.getComFiledSet(user.getOrgId());
		} else {
			fieldSets = cachedService.getPersonFiledSet(user.getOrgId());
		}
		Map<String, String> filedMap = new HashMap<String, String>();
		for (CustFieldSet filed : fieldSets) {
			filedMap.put(filed.getFieldCode(), filed.getFieldName());
		}
		request.setAttribute("filedMap", filedMap);
	}

	public List<CustFieldSet> getIsQueryList(String orgId, int isState) {
		List<CustFieldSet> fieldSets = null;
		if (isState == 1) {// 企业资源
			fieldSets = cachedService.getComFiledSets(orgId);
		} else {
			fieldSets = cachedService.getPersonFiledSets(orgId);
		}
		List<CustFieldSet> list = new ArrayList<CustFieldSet>();
		for (CustFieldSet filed : fieldSets) {
			if (filed.getIsQuery() == 1 && filed.getEnable() == 1 && filed.getDataType() == 1) {
				list.add(filed);
			}
		}
		return list;
	}
	
	/**
	 * 根据字段名称返回对象
	 * 
	 * @param orgId
	 * @param isState
	 * @param key
	 * @return
	 */
	public CustFieldSet getFieldCodeByKey(String orgId, int isState, String key) {
		List<CustFieldSet> fieldSets = null;
		if (isState == 1) {// 企业资源
			fieldSets = cachedService.getComFiledSets(orgId);
		} else {
			fieldSets = cachedService.getPersonFiledSets(orgId);
		}
		for (CustFieldSet filed : fieldSets) {
			if (filed.getEnable() == CustFieldSet.FIELD_ENABLED /*&& filed.getDataType() == CustFieldSet.DATATYPE_TEXT*/
					&& filed.getFieldName().contains(key)) {
				return filed;
			}
		}
		return null;
	}
	
	/**
	 * 根据字段编号返回对象
	 * 
	 * @param orgId
	 * @param isState
	 * @param key
	 * @return
	 */
	public CustFieldSet getFieldCodeByCode(String orgId, int isState, String fieldCode) {
		List<CustFieldSet> fieldSets = null;
		if (isState == 1) {// 企业资源
			fieldSets = cachedService.getComFiledSets(orgId);
		} else {
			fieldSets = cachedService.getPersonFiledSets(orgId);
		}
		for (CustFieldSet filed : fieldSets) {
			if (filed.getEnable() == CustFieldSet.FIELD_ENABLED /*&& filed.getDataType() == CustFieldSet.DATATYPE_TEXT*/
					&& filed.getFieldCode().equals(fieldCode)) {
				return filed;
			}
		}
		return null;
	}

	/**
	 * 个人资源数量是否超过最大资源数(返回集合{flag:0-没有超出/1-超出最大数,bSize:操作数量,resNum:当前资源数,maxNum:
	 * 最大资源数})
	 * 
	 * @param account
	 *            帐号
	 * @param orgId
	 *            单位ID
	 * @param bSize
	 *            操作数量
	 * @return Map<String,Integer>
	 * @create 2015年11月26日 上午9:51:50 lixing
	 * @history
	 */
	public Map<String, Integer> isResBeyondMax(String account, String orgId, Integer bSize) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("flag", 0);
		map.put("bSize", bSize);
		List<DataDictionaryBean> openList = cachedService.getDirList(AppConstant.DATA15, orgId);
		Integer maxNum = 0;
		if (!openList.isEmpty() && openList.get(0).getDictionaryValue() != null && openList.get(0).getDictionaryValue().equals("1")) {
			Integer resNum = resCustInfoService.getMyResSum(account, orgId);
			map.put("resNum", resNum);
			List<DataDictionaryBean> valueList = cachedService.getDirList(AppConstant.DATA28, orgId);
			if (!valueList.isEmpty() && valueList.get(0).getIsOpen() != null && valueList.get(0).getIsOpen().equals("1")) {
				maxNum = Integer.parseInt(valueList.get(0).getDictionaryValue() == null ? "0" : valueList.get(0).getDictionaryValue());
				if ((resNum + bSize) > maxNum) {
					map.put("flag", 1);
				}
			}
		}
		map.put("maxNum", maxNum);
		return map;
	}
	
	/**
	 * 个人意向数量是否超过最大意向数(返回集合{flag:0-没有超出/1-超出最大数,bSize:操作数量,resNum:当前意向数,maxNum:
	 * 最大资源数})
	 * 
	 * @param account
	 *            帐号
	 * @param orgId
	 *            单位ID
	 * @param bSize
	 *            操作数量
	 * @return Map<String,Integer>
	 * @create 2015年11月26日 上午9:51:50 lixing
	 * @history
	 */
	public Map<String, Integer> isCustBeyondMax(String account, String orgId, Integer bSize) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("flag", 0);
		map.put("bSize", bSize);
		List<DataDictionaryBean> openList = cachedService.getDirList(AppConstant.DATA15, orgId);
		Integer maxNum = 0;
		if (!openList.isEmpty() && openList.get(0).getDictionaryValue() != null && openList.get(0).getDictionaryValue().equals("1")) {
			Integer resNum = resCustInfoService.getMyCustSum(account, orgId);
			map.put("resNum", resNum);
			List<DataDictionaryBean> valueList = cachedService.getDirList(AppConstant.DATA03, orgId);
			if (!valueList.isEmpty() && valueList.get(0).getIsOpen() != null && valueList.get(0).getIsOpen().equals("1")) {
				maxNum = Integer.parseInt(valueList.get(0).getDictionaryValue() == null ? "0" : valueList.get(0).getDictionaryValue());
				if ((resNum + bSize) > maxNum) {
					map.put("flag", 1);
				}
			}
		}
		map.put("maxNum", maxNum);
		return map;
	}

	/**
	 * 格式化固话号码 说明：首位加0及去除区号后的“-”字符
	 *
	 * @param phone
	 * @param request
	 * @return
	 * @create 2015年11月17日 下午3:14:52 lixing
	 * @history
	 */
	public String formatPhone(String phone, HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		if (phone == null || "".equals(phone)) {
			return "";
		} else {
			phone = phone.trim();
		}
		String rstPhnoe = phone;
		if (StringUtils.isBlank(phone)) {
			return "";
		}
		String tel1 = "^\\d{3,4}-\\d{3,4}-\\d{3,4}$";
		Pattern p1 = Pattern.compile(tel1);
		Matcher m1 = p1.matcher(rstPhnoe);
		if (m1.matches()) {
			rstPhnoe = rstPhnoe.replaceAll("-", "");
		} else {
			if (rstPhnoe.length() >= 5 && rstPhnoe.substring(3, 5).contains("-")) {
				rstPhnoe = rstPhnoe.replaceFirst("-", "");
			}
			if (phone.length() == 7 || phone.length() == 8) {
				List<DataDictionaryBean> openList = cachedService.getDirList(AppConstant.DATA18, user.getOrgId());
				String isOpen = openList.get(0).getDictionaryValue();
				isOpen = isOpen == null ? "0" : isOpen;
				if (isOpen.equals("1")) {
					List<DataDictionaryBean> itemValList = cachedService.getDirList(AppConstant.DATA19, user.getOrgId());
					String px = itemValList.get(0).getDictionaryValue();
					px = px == null ? "0" : px;
					if (px.equals(rstPhnoe.substring(0, 4))) { //防止重复
						rstPhnoe = rstPhnoe.substring(4);
					}
					rstPhnoe = px + rstPhnoe;
				}
			}
		}
		return rstPhnoe;
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
	 * 缓存读取签约是否与合同无关 0-需添加合同 1-无需添加合同
	 * 
	 * @param request
	 * @create 2016年8月10日 下午2:59:09 lixing
	 * @history
	 */
	public void getSignSetting(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_40039, user.getOrgId());
		Integer signSetting = 0;
		if (!list.isEmpty() && list.get(0) != null) {
			signSetting = new Integer(list.get(0).getDictionaryValue());
		}
		request.setAttribute("signSetting", signSetting);
	}

	
	public Integer getSignSetting() {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_40039, user.getOrgId());
		Integer signSetting = 0;
		if (!list.isEmpty() && list.get(0) != null) {
			signSetting = new Integer(list.get(0).getDictionaryValue());
		}
		return signSetting;
	}

	/**
	 * 缓存读取签约是否与合同无关 0-需添加合同 1-无需添加合同
	 * 
	 * @param request
	 * @create 2016年8月10日 下午2:59:09 lixing
	 * @history
	 */
	public String getSignSetting(String orgId) {
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_40039, orgId);
		String signSetting = "0";
		if (!list.isEmpty() && list.get(0) != null) {
			signSetting = new Integer(list.get(0).getDictionaryValue()) + "";
		}
		return signSetting;
	}

	/**
	 * 从缓存读取管理员是否可以对下属成员的客户进行“签约”、“取消签约”操作的设置
	 * 
	 * @param request
	 * @create 2016年12月15日 下午2:11:09 lixing
	 * @history
	 */
	public void setAdminSignAuth(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_40041, user.getOrgId());
		Integer adminSignAuth = 0;
		if (!list.isEmpty() && list.get(0) != null) {
			adminSignAuth = new Integer(list.get(0).getDictionaryValue());
		}
		request.setAttribute("adminSignAuth", adminSignAuth);
	}

	public Integer getAdminSignAuth() {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_40041, user.getOrgId());
		Integer adminSignAuth = 0;
		if (!list.isEmpty() && list.get(0) != null) {
			adminSignAuth = new Integer(list.get(0).getDictionaryValue());
		}
		return adminSignAuth;
	}

	/**
	 * 设置是否需要只读
	 * 
	 * @param request
	 * @create 2015年11月25日 下午2:45:20 lixing
	 * @history
	 */
	public void setIsRead(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		boolean isRead = cachedService.judgeReadWhiteList(user.getOrgId(), user.getAccount());
		request.setAttribute("isRead", isRead);
	}

	/**
	 * 设置是否需要模糊电话号码
	 * 
	 * @param request
	 * @create 2015年11月25日 下午2:45:20 lixing
	 * @history
	 */
	public String getReplaceWord(String orgId, String account) {
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA24, orgId);
		String idReplaceWord = "0";
		if (!list.isEmpty() && list.get(0) != null && !cachedService.judgeHideWhiteList(orgId, account)) {
			idReplaceWord = new Integer(list.get(0).getDictionaryValue()) + "";
		}
		return idReplaceWord;
	}

	/**
	 * 设置是否需要模糊电话号码
	 * 
	 * @param request
	 * @create 2015年11月25日 下午2:45:20 lixing
	 * @history
	 */
	public String getIsShare() {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_40018, user.getOrgId());
		String isShare = "";
		if (list != null && list.size() > 0) {
			isShare = list.get(0).getDictionaryValue();
		}
		return isShare;
	}

	/**
	 * 返回系统客户跟进设置
	 * 
	 * @param paramMap
	 * @return
	 * @create 2015年11月17日 下午2:37:04 wuwei
	 * @history
	 */
	public Map<String, String> getFollowSet(String orgId, String code) {
		Map<String, String> map = new HashMap<String, String>();
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA15, orgId);
		if (list != null && list.size() > 0) {
			DataDictionaryBean ddBean = list.get(0);
			map.put("isOpen", ddBean.getDictionaryValue());
			list = cachedService.getDirList(code, orgId);
			if (list != null && list.size() > 0) {
				ddBean = list.get(0);
				map.put("val", ddBean.getDictionaryValue());
				map.put("subIsOpen", ddBean.getIsOpen());
			}
		}
		return map;
	}

	/** 获取客户跟进 下次默认跟进时间 设置 */
	public String getCustCacheDate(HttpServletRequest request) throws Exception {
		ShiroUser user = ShiroUtil.getShiroUser();
		String orgId = user.getOrgId();
		List<DataDictionaryBean> dlist = cachedService.getDirList(AppConstant.DATA15, orgId);
		int hour = 0;// 默认关闭 1 天24小时
		int day = 7;
		int minsHour = 60;
		int nextFollowValidation = 1;//默认开启下次联系时间、下次联系方式必填
		if (!dlist.isEmpty() && ("1".equals(dlist.get(0).getDictionaryValue()))) {
			// 判断是否开启
			List<DataDictionaryBean> dlist2 = cachedService.getDirList(AppConstant.DATA20, orgId);
			List<DataDictionaryBean> dlist3 = cachedService.getDirList(AppConstant.DATA21, orgId);
			if(!dlist2.isEmpty() && dlist2.get(0) != null){
				String isOpen = dlist2.get(0).getIsOpen();
				isOpen = StringUtils.isBlank(isOpen) ? "1" : isOpen;
				nextFollowValidation = Integer.valueOf(isOpen);
			}
			if (!dlist3.isEmpty() && "1".equals(dlist3.get(0).getDictionaryValue())) {
				if (!dlist2.isEmpty() && dlist2.get(0) != null) {
					if (!"".equals(dlist2.get(0).getDictionaryValue()) && null != dlist2.get(0).getDictionaryValue()) {
						int l = Integer.valueOf(dlist2.get(0).getDictionaryValue());
						hour = l;
						day = 0;
					}
				}
			}
			if (!dlist3.isEmpty() && "0".equals(dlist3.get(0).getDictionaryValue())) {
				if (!dlist2.isEmpty() && dlist2.get(0) != null) {
					if (!"".equals(dlist2.get(0).getDictionaryValue()) && null != dlist2.get(0).getDictionaryValue()) {
						int l = Integer.valueOf(dlist2.get(0).getDictionaryValue());
						hour = 0;
						day = l;
					}
				}
			}
		}

		request.setAttribute("day", day);
		request.setAttribute("hour", hour);
		request.setAttribute("nextFollowValidation", nextFollowValidation);
		
		return com.qftx.common.util.DateUtil.addDateMinut(minsHour * (day * 24 + hour));
	}

	/**
	 * 客户信息字段格式校验设置
	 * 
	 * @param request
	 * @create 2015年11月25日 下午2:45:20 lixing
	 * @history
	 */
	public void setCustWordCheck(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String cust_check_mobile = "0";
		String cust_check_tel = "0";
		String cust_check_tel2 = "0";
		String cust_check_fax = "0";
		String cust_check_mail = "0";
		String pValiDateType = "0";
		String uValiDateType = "0";
		String uhValiDateType = "0";
		String safeSet = "0";
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA14, user.getOrgId());
		if (list.size() > 0 && "1".equals(list.get(0).getDictionaryValue())) {
			list = cachedService.getDirList(AppConstant.DATA11, user.getOrgId());
			if (!"1".equals(list.get(0).getDictionaryValue()) && StringUtils.isNotEmpty(list.get(0).getDictionaryValue())) {
				pValiDateType = list.get(0).getDictionaryValue();
			}
			list = cachedService.getDirList(AppConstant.DATA12, user.getOrgId());
			if (!"0".equals(list.get(0).getDictionaryValue()) && StringUtils.isNotEmpty(list.get(0).getDictionaryValue())) {
				uValiDateType = list.get(0).getDictionaryValue();
			}
			list = cachedService.getDirList(AppConstant.DATA22, user.getOrgId());
			if (!"0".equals(list.get(0).getDictionaryValue()) && StringUtils.isNotEmpty(list.get(0).getDictionaryValue())) {
				uhValiDateType = list.get(0).getDictionaryValue();
			}
		}
		list = cachedService.getDirList(AppConstant.DATA34, user.getOrgId());
		if (list.size() > 0) {
			if ("1".equals(list.get(0).getDictionaryValue())) {
				list = cachedService.getDirList(AppConstant.DATA35, user.getOrgId());
				cust_check_mobile = list.get(0).getDictionaryValue();
				list = cachedService.getDirList(AppConstant.DATA36, user.getOrgId());
				cust_check_tel = list.get(0).getDictionaryValue();
				list = cachedService.getDirList(AppConstant.DATA37, user.getOrgId());
				cust_check_tel2 = list.get(0).getDictionaryValue();
				list = cachedService.getDirList(AppConstant.DATA38, user.getOrgId());
				cust_check_fax = list.get(0).getDictionaryValue();
				list = cachedService.getDirList(AppConstant.DATA39, user.getOrgId());
				cust_check_mail = list.get(0).getDictionaryValue();
			}
		}
		list = cachedService.getDirList(AppConstant.DATA_40029, user.getOrgId());
		if (list.size() > 0) {
			if ("1".equals(list.get(0).getDictionaryValue())) {
				safeSet = "1";
			}
		}
		request.setAttribute("safeSet", safeSet);
		request.setAttribute("cust_check_mobile", cust_check_mobile);
		request.setAttribute("cust_check_tel", cust_check_tel);
		request.setAttribute("cust_check_tel2", cust_check_tel2);
		request.setAttribute("cust_check_fax", cust_check_fax);
		request.setAttribute("cust_check_mail", cust_check_mail);
		request.setAttribute("pValiDateType", pValiDateType);
		request.setAttribute("uValiDateType", uValiDateType);
		request.setAttribute("uhValiDateType", uhValiDateType);
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
	 * 设置是否开启用*替换电话号码中间四位
	 */
	public void setIsReplaceWord(HttpServletRequest request, ShiroUser user) {
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA24, user.getOrgId());
		Integer idReplaceWord = 0;
		if (!list.isEmpty() && list.get(0) != null && !cachedService.judgeHideWhiteList(user.getOrgId(), user.getAccount())) {
			idReplaceWord = new Integer(list.get(0).getDictionaryValue());
		}
		request.setAttribute("idReplaceWord", idReplaceWord);
	}

	/*
	*//**
	 * 设置是否开启用*替换电话号码中间四位
	 */
	/*
	 * public String getIsReplaceWord(String orgId) { // 查询缓存
	 * List<DataDictionaryBean> dataMap =
	 * cachedService.getDirList(AppConstant.DATA24, orgId); //
	 * 对电话号码中间4位用*号模糊处理设置到session中 Set<String> dicSet = new HashSet<String>();
	 * dicSet.add(AppConstant.DATA24 + "_" + orgId); String idReplaceWord = "";
	 * if (!dataMap.isEmpty() && dataMap.get(0) != null) { idReplaceWord = new
	 * Integer(dataMap.get(0).getDictionaryValue()) + ""; } return
	 * idReplaceWord; }
	 */

	/**
	 * 返回系统客户跟进设置
	 * 
	 * @param paramMap
	 * @return
	 * @create 2015年11月17日 下午2:37:04 wuwei
	 * @history
	 */
	public Map<String, String> getDataDictionaryBean(String orgId) {

		Map<String, String> map = new HashMap<String, String>();
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA15, orgId);
		if (list != null && list.size() > 0) {
			DataDictionaryBean ddBean = list.get(0);
			map.put("followIsOpen", ddBean.getDictionaryValue());
			list = cachedService.getDirList(AppConstant.DATA28, orgId);
			if (list != null && list.size() > 0) {
				ddBean = list.get(0);
				map.put("val", ddBean.getDictionaryValue());
				map.put("limitIsOpen", ddBean.getIsOpen());
			}
		}
		return map;
	}

	/**
	 * 获取查询列表 动态字段名称
	 * 
	 * @param request
	 * @create 2016年3月11日 上午10:31:39 Administrator
	 * @history
	 */
	public void setDyField(HttpServletRequest request) {
		String dy_name = "";
		String dy_company = "";
		String dy_moblie = "";
		String dy_tel = "";
		ShiroUser user = ShiroUtil.getUser();
		String orgId = user.getOrgId();
		if (user.getIsState() == 1) {
			List<CustFieldSet> list = cachedService.getComFiledSet(orgId);
			if (list != null && list.size() > 0) {
				for (CustFieldSet set : list) {
					if (AppConstant.com_name.equals(set.getFieldCode())) {
						dy_name = set.getFieldName();
						break;
					}
				}
			}

			list = cachedService.getContactsFiledSet(orgId);
			if (list != null && list.size() > 0) {
				for (CustFieldSet set : list) {
					if (AppConstant.contacts_telphone.equals(set.getFieldCode())) {
						dy_moblie = set.getFieldName();
					}
					if (AppConstant.contacts_telphonebak.equals(set.getFieldCode())) {
						dy_tel = set.getFieldName();
					}
				}
			}

		} else {
			List<CustFieldSet> list = cachedService.getPersonFiledSet(orgId);
			if (list != null && list.size() > 0) {
				for (CustFieldSet set : list) {
					if (AppConstant.name.equals(set.getFieldCode())) {
						dy_name = set.getFieldName();
					}
					if (AppConstant.company.equals(set.getFieldCode())) {
						dy_company = set.getFieldName();
					}
					if (AppConstant.mobilephone.equals(set.getFieldCode())) {
						dy_moblie = set.getFieldName();
					}
					if (AppConstant.telphone.equals(set.getFieldCode())) {
						dy_tel = set.getFieldName();
					}
				}
			}
		}
		request.setAttribute("dy_name", dy_name);
		request.setAttribute("dy_company", dy_company);
		request.setAttribute("dy_moblie", dy_moblie);
		request.setAttribute("dy_tel", dy_tel);
	}

	/**
	 * 获取查询列表 动态字段名称
	 * 
	 * @param request
	 * @create 2016年3月11日 上午10:31:39 Administrator
	 * @history
	 */
	public Map<String, String> getDyField(String orgId, int isState) {
		Map<String, String> map = new HashMap<String, String>();
		String dy_name = "";
		String dy_moblie = "";
		String dy_company = "";
		String dy_tel = "";
		if (isState == 1) {
			List<CustFieldSet> list = cachedService.getComFiledSet(orgId);
			if (list != null && list.size() > 0) {
				for (CustFieldSet set : list) {
					if (AppConstant.com_name.equals(set.getFieldCode())) {
						dy_name = set.getFieldName();
						break;
					}
				}
			}

			list = cachedService.getContactsFiledSet(orgId);
			if (list != null && list.size() > 0) {
				for (CustFieldSet set : list) {
					if (AppConstant.contacts_telphone.equals(set.getFieldCode())) {
						dy_moblie = set.getFieldName();
					}
					if (AppConstant.contacts_telphonebak.equals(set.getFieldCode())) {
						dy_tel = set.getFieldName();
					}
				}
			}

		} else {
			List<CustFieldSet> list = cachedService.getPersonFiledSet(orgId);
			if (list != null && list.size() > 0) {
				for (CustFieldSet set : list) {
					if (AppConstant.name.equals(set.getFieldCode())) {
						dy_name = set.getFieldName();
					}
					if (AppConstant.company.equals(set.getFieldCode())) {
						dy_company = set.getFieldName();
					}
					if (AppConstant.mobilephone.equals(set.getFieldCode())) {
						dy_moblie = set.getFieldName();
					}
					if (AppConstant.telphone.equals(set.getFieldCode())) {
						dy_tel = set.getFieldName();
					}
				}
			}
		}
		map.put("dy_name", dy_name);
		map.put("dy_moblie", dy_moblie);
		map.put("dy_company", dy_company);
		map.put("dy_tel", dy_tel);
		return map;
	}

	/**
	 * 获取左侧资源菜单列表
	 * 
	 * @create 2014-2-11 下午03:20:23 wuwei
	 * @history
	 */
	public List<ResourceGroupBean> getLeftGroup(Map<String, Object> map, String orgId) {
		List<ResourceGroupBean> groupList = new ArrayList<ResourceGroupBean>();
		Map<String, ResourceGroupBean> groupMap = new HashMap<String, ResourceGroupBean>();
		groupMap = cachedService.getOrgResGroupBean(orgId);
		if (groupMap != null) {
			for (String key : groupMap.keySet()) {
				groupList.add(groupMap.get(key));
			}
		}
		return groupList;
	}

	/**
	 * 获取左侧资源菜单列表
	 * 
	 * @create 2014-2-11 下午03:20:23 wuwei
	 * @history
	 */
	public void getLeftResourceList(Map<String, Object> map, HttpServletRequest request, ShiroUser user) {
		List<ResourceGroupBean> groupList = new ArrayList<ResourceGroupBean>();
		List<String> groupIds = new ArrayList<String>();
		groupList = comResourceGroupService.queryResGroup(user.getOrgId());
		if (groupList != null && groupList.size() > 0) {
			for (ResourceGroupBean groupBean : groupList) {
				groupIds.add(groupBean.getResGroupId());
			}
		}
		int groupSum = 0;
		int mark = 0;
		map.put("groupIds", groupIds);
		List<ResourceGroupDto> resGroups = new ArrayList<ResourceGroupDto>();
		List<ResourceGroupDto> staticGroups = comResourceGroupService.getResSumList(map);
		if (groupList != null && groupList.size() > 0) {
			for (ResourceGroupBean groupBean : groupList) {
				for (ResourceGroupDto dto : staticGroups) {
					if (dto.getResGroupId().equals(groupBean.getResGroupId())) {
						dto.setGroupName(groupBean.getGroupName());
						dto.setIsSharePool(groupBean.getIsSharePool());
						dto.setType(groupBean.getType());
						groupSum = groupSum + new Integer(dto.getGroupCount());
						resGroups.add(dto);
						mark = 1;
						break;
					}
				}
				if (mark == 0) {
					ResourceGroupDto groupDto = new ResourceGroupDto();
					groupDto.setResGroupId(groupBean.getResGroupId());
					groupDto.setGroupName(groupBean.getGroupName());
					groupDto.setGroupCount("0");
					groupDto.setType(groupBean.getType());
					groupDto.setIsSharePool(groupBean.getIsSharePool());
					resGroups.add(groupDto);
				}
				mark = 0;
			}
		}
		// // 取得单位所有分组及成员数
		// List<ResourceGroupDto> resGroupDtos =
		// comResourceGroupService.getResSumList(map);
		request.setAttribute("resGroups", resGroups);
		//
		// // 取得单位成员总数
		// int groupSum = comResourceGroupService.getComResSumByOrgID(map);
		request.setAttribute("groupSum", groupSum);
	}

	public void setResList(String orgId, List<ResCustDto> resList, boolean isReplaceUserName) {
		List<OrgGroup> deptList = cachedService.getOrgGroup(orgId);
		Map<String, String> resGroupMap = cachedService.getOrgResGroupNames(orgId);
		Map<String, String> userMap = cachedService.getOrgUserNames(orgId);
		if (resList != null && resList.size() > 0) {
			for (ResCustDto custDto : resList) {
				if (resGroupMap != null) {
					custDto.setGroupName(resGroupMap.get(custDto.getResGroupId()));
				}
				if (deptList != null && deptList.size() > 0) {
					for (OrgGroup orgGroup : deptList) {
						if (orgGroup.getGroupId().equals(custDto.getImportDeptId())) {
							custDto.setDeptName(orgGroup.getGroupName());
							break;
						}
					}
				}
				if (userMap != null && isReplaceUserName) {
					custDto.setOwnerName(userMap.get(custDto.getOwnerAcc()));
				}
			}
		}
	}

	public void setStaffList(String orgId, List<StaffDto> staffList) {
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

		if (staffList != null && staffList.size() > 0) {
			for (StaffDto staffDto : staffList) {
				String groupId = memberGroupMap.get(staffDto.getStaffAcc());
				staffDto.setTeamGroupName(deptMap.get(groupId));
			}
		}
	}

	public List<String> getChildGroupIds(String groupId) {
		List<String> groupIds = new ArrayList<String>();
		List<Map<String, Object>> groupTree = getGroupTree();
		for (Map<String, Object> map : groupTree) {
			if (map.get("id").equals(groupId)) {
				readChild(map, groupId, groupIds, true);
			} else {
				readChild(map, groupId, groupIds, false);
			}
		}
		return groupIds;
	}

	public void readChild(Map<String, Object> map, String groupId, List<String> groupIds, boolean isFindChild) {
		if (isFindChild) {
			groupIds.add(map.get("id").toString());
			if (map.get("children") != null) {
				List<Map<String, Object>> childTree = (List<Map<String, Object>>) map.get("children");
				for (Map<String, Object> cmap : childTree) {
					readChild(cmap, groupId, groupIds, isFindChild);
				}
			}
		} else {
			if (map.get("children") != null) {
				List<Map<String, Object>> childTree = (List<Map<String, Object>>) map.get("children");
				for (Map<String, Object> cmap : childTree) {
					if (cmap.get("id").equals(groupId)) {
						readChild(cmap, groupId, groupIds, true);
					} else {
						readChild(cmap, groupId, groupIds, false);
					}
				}
			}
		}
	}

	public List<Map<String, Object>> getGroupTree() {
		List<Map<String, Object>> list = (List<Map<String, Object>>) ShiroUtil.getSession(SysConstant.get_group_json);
		if (list != null && list.size() > 0) {
			logger.debug("sessoin有权限组织结构树>>>" + JSON.toJSONString(list));
		} else {
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			list = orgGroupUserService.getTreeGroup(shiroUser.getId(), shiroUser.getOrgId());
			ShiroUtil.setSession(SysConstant.get_group_json, list);
		}
		return list;
	}

	// 将 s 进行 BASE64 编码
	public static String getBASE64(String s) {
		if (s == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
	}

	// 将 BASE64 编码的字符串 s 进行解码
	public static String getFromBASE64(String s) {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}

	public String getUserName(String orgId, String account) {
		String userName = "";
		Map<String, String> map = cachedService.getOrgUserNames(orgId);
		if (map != null) {
			userName = map.get(account);
		}
		return userName;
	}

	public void setZone4CustDetail(ResCustInfoDetailBean bean, HttpServletRequest request) {
		if (bean != null) {
			String telPhone = bean.getTelphone();
			String telPhoneBak = bean.getTelphonebak();
			bean.setTelphone(StringUtils.toCheckPhone(formatPhone(telPhone, request)));
			bean.setTelphonebak(StringUtils.toCheckPhone(formatPhone(telPhoneBak, request)));
			bean.setName(StringUtils.isNotBlank(bean.getName()) ? bean.getName().trim() : "");
		}
	}
	
	/**共有者开关  0-关闭 1-开启*/
	public int getCommonOwnerOpenState(String orgId){
		int open = 0;
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50011, orgId);
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getDictionaryValue();
			open = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "0");
		}
		return open;
	}
	
	/**共有者 放弃公海权限 0-关闭 1-开启*/
	public int getCommonOwnerGiveUpAuth(String orgId){
		int auth = 0;
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50012, orgId);
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getDictionaryValue();
			auth = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "0");
		}
		return auth;
	}
	
	/**共有者 编辑客户信息权限 0-关闭 1-开启*/
	public int getCommonOwnerEditAuth(String orgId){
		int auth = 0;
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50013, orgId);
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getDictionaryValue();
			auth = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "0");
		}
		return auth;
	}
	
	/**共有者 签约权限 0-关闭 1-开启*/
	public int getCommonOwnerSignAuth(String orgId){
		int auth = 0;
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50014, orgId);
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getDictionaryValue();
			auth = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "0");
		}
		return auth;
	}
	
	
	/** 
	 * 共有客户权限<br />
	 * <font color="red">commonOnwerOpen</font> 共有客户开启状态  0-关闭 1-开启<br />
	 * <font color="red">commonOnwerGiveUp</font> 签约权限  0-关闭 1-开启<br />
	 * <font color="red">commonOnwerEdit</font> 编辑权限  0-关闭 1-开启<br />
	 * <font color="red">commonOnwerSign</font> 签约权限  0-关闭 1-开启
	 * @param request 
	 * @create  2017年6月6日 下午6:09:37 lixing
	 * @history  
	 */
	public void commonOwnerAuth(HttpServletRequest request){
		ShiroUser user = ShiroUtil.getShiroUser();
		int isOpen = getCommonOwnerOpenState(user.getOrgId());
		if(isOpen == 1){
			request.setAttribute("commonOnwerGiveUp", getCommonOwnerGiveUpAuth(user.getOrgId()));
			request.setAttribute("commonOnwerEdit", getCommonOwnerEditAuth(user.getOrgId()));
			request.setAttribute("commonOnwerSign", getCommonOwnerSignAuth(user.getOrgId()));
		}
		request.setAttribute("commonOnwerOpen", isOpen);
	}
	
	/**弹幕设置：排行榜是否开启， 0-关闭 1-开启*/
	public int getphb(String orgId){
		int open = 0;
		List<DataDictionaryBean> dlist0 = cachedService.getDirList(AppConstant.DATA_50033, orgId);//弹幕总开关
		List<DataDictionaryBean> dlist1 = cachedService.getDirList(AppConstant.DATA_50037, orgId);//排行榜弹幕开关 
        if ((!dlist0.isEmpty() && "1".equals(dlist0.get(0).getDictionaryValue()) && (!dlist1.isEmpty() && "1".equals(dlist1.get(0).getDictionaryValue()) ))) {
        	open=1;
        	// 判断是否开启
        }
		return open;
	}
	
	/**弹幕设置：回款金额开关  0-关闭 1-开启*/
	public int getBarrageNewSale(String orgId){
		int open = 0;
		if(getphb(orgId)==1){
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50039, orgId);
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getIsOpen();
			open = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "0");
		}
		}
		return open;
	}
	
	/**弹幕设置：新增签约开关  0-关闭 1-开启*/
	public int getBarrageNewSign(String orgId){
		int open = 0;
		if(getphb(orgId)==1){
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50041, orgId);
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getIsOpen();
			open = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "0");
		}
		}
		return open;
	}
	
	/**弹幕设置：呼出次数开关  0-关闭 1-开启*/
	public int getBarrageCallout(String orgId){
		int open = 0;
		if(getphb(orgId)==1){
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50042, orgId);
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getIsOpen();
			open = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "0");
		}
		}
		return open;
	}
	
	/**弹幕设置：新增意向开关  0-关闭 1-开启*/
	public int getBarrageNewWill(String orgId){
		int open = 0;
		if(getphb(orgId)==1){
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50040, orgId);
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getIsOpen();
			open = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "0");
		}
		}
		return open;
	}
	
	/**弹幕设置：呼出时长开关  0-关闭 1-开启*/
	public int getBarrageCall(String orgId){
		int open = 0;
		if(getphb(orgId)==1){
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50043, orgId);
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getIsOpen();
			open = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "0");
		}
		}
		return open;
	}
	
	
	/** 
	 * 荣誉弹幕显示规则<br />
	 * <font color="red">getBarrageNewSign</font> 新增签约  0-关闭 1-开启<br />
	 * <font color="red">getBarrageCallout</font> 呼出次数  0-关闭 1-开启<br />
	 * <font color="red">getBarrageNewWill</font> 新增意向  0-关闭 1-开启
 	 * <font color="red">getBarrageCall</font> 呼出时长  0-关闭 1-开启
	 * @param request 
	 * @create  2017年6月20日 下午15:09:37 xiaoxh
	 * @history  
	 */
	public void BarrageAuth(HttpServletRequest request){
		ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("getBarrageNewSign", getBarrageNewSign(user.getOrgId()));
			request.setAttribute("getBarrageCallout", getBarrageCallout(user.getOrgId()));
			request.setAttribute("getBarrageNewWill", getBarrageNewWill(user.getOrgId()));
			request.setAttribute("getBarrageCall",    getBarrageCall(user.getOrgId()));
	}
	
	/** 获取生日弹幕每日弹出时间  设置 */
	public List<Map<String,String>> getBirthdayCacheDate() {
		List<String> orgIds=orgService.getAllOrgIds();
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		if(orgIds != null && orgIds.size()>0){
			for(String orgId_new : orgIds){
				if(orgId_new != null && !"".equals(orgId_new)){
					String orgId = orgId_new;
					Map<String,String> map=new HashMap();
					String hour = "0";// 默认关闭 1 天24小时
					String minsHour = "60";
						// 判断是否开启
						List<DataDictionaryBean> dlist2 = cachedService.getDirList(AppConstant.DATA_50017, orgId);
						List<DataDictionaryBean> dlist3 = cachedService.getDirList(AppConstant.DATA_50018, orgId);
					if((!dlist2.isEmpty() && dlist2.get(0) != null) || (!dlist3.isEmpty() && dlist3.get(0) != null)){
					if(!dlist2.isEmpty() && dlist2.get(0) != null){
							hour = dlist2.get(0).getDictionaryValue();
						}
						
						if(!dlist3.isEmpty() && dlist3.get(0) != null){
							minsHour = dlist3.get(0).getDictionaryValue();
						}
					if(!"0".equals(hour)&&!"0".equals(minsHour)){
					        map.put("hour", hour);
					        map.put("minsHour", minsHour);
					        map.put("orgId", orgId);
					        list.add(map);
						}
					}
				}
			}
		}

	
		return list;
	}
	
	
	/** 获取荣誉榜弹出时间设置  */
	public List<Map<String,Object>> getGetrankingCacheDate() throws Exception {
		List<String> orgIds=orgService.getAllOrgIds();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if(orgIds != null && orgIds.size()>0){
			for(String orgId_new : orgIds){
				if(orgId_new != null && !"".equals(orgId_new)){
					String orgId = orgId_new;
					Map<String,Object> map=new HashMap<String,Object>();

						// 判断是否开启
						List<DataDictionaryBean> dlist2 = cachedService.getDirList(AppConstant.DATA_50019, orgId);
					if(!dlist2.isEmpty() && dlist2.get(0) != null){
						    int	dateMoth =Integer.valueOf(dlist2.get(0).getDictionaryValue());		
							if(dateMoth>=1&&dateMoth<=7){
					        map.put("hour", "");
					        map.put("minsHour", "");
					        map.put("dateMoth", dateMoth);
					        map.put("orgId", orgId);
					        list.add(map);
					}
					}
				}
			}
		}

	
		return list;
	}

	
	
	/**
	 * 投诉客户名单。和企业黑名单校验
	 * BlackConstant：
	 * AppConstant.BlackCustPhone=BlackCustPhone 为投诉客户 ，限制呼出；
	 * AppConstant.BlackComPhone=BlackComPhone为企业黑名单,限制呼入
	 * returnValue 0为屏蔽号码，-1不是屏蔽号码
	 * @create 2017-6-12  xiaoxh
	 * @history
	 */
	public String getBlackPhone(String orgId,String phone,String BlackConstant) {
		String returnValue=AppConstant.Blak_RESULT_FAILURE;//默认此号码不是屏蔽号码
		List<String> list =new ArrayList<String>();
		if(BlackConstant!= "" && AppConstant.BlackCustPhone.equals(BlackConstant)){//投诉客户名单
		list=cachedService.getBlackCustPhoneCatch(orgId);
		}else if(BlackConstant!= "" && AppConstant.BlackComPhone.equals(BlackConstant)){//企业黑名单
		list=cachedService.getBlackComPhoneCatch(orgId);
		}
		if(list !=null && list.size()>0){
			for(String phone_new:list){
				if(phone_new.equals(phone)){
					returnValue=AppConstant.Blak_RESULT_SUCCESS;
					break;
				}
			}
		}
		
		return returnValue;
	}
	
	/** 
	 * 插入操作日志
	 * 	orgId        //机构id
	 *	userAccount  //用户账号
	 *	userName     //用户名
	 *	moduleId     //模块id，取值来源于：AppConstant中AppConstant.Module_id，根据自己模块选择对应的id
	 *	moduleName   //模块名称，取值来源于：AppConstant中AppConstant.Module_Name，根据自己模块选择对应的值
	 *	operateId    //功能、操作id,取值来源于：AppConstant中AppConstant.Operate_id，根据自己功能或者操作选择对应的id
	 *	operateName  //功能、操作名,取值来源于：AppConstant中AppConstant.Operate_Name，根据自己功能或者操作选择对应的值
	 *	content      //详细描述,
	 *	systemOperateId//销售管理id，销售管理保存时传入systemOperateId，其他模块为""即可。
	 *  */
	public void getUserOperateLog(String userAccount,String userName,String moduleId ,String moduleName ,String operateId ,String operateName ,String content,String systemOperateId ) throws Exception {
		ShiroUser user = ShiroUtil.getShiroUser();
		String orgId = user.getOrgId();
		LogUserOperateBean logUserOperateBean=new LogUserOperateBean();
		logUserOperateBean.setId(SysBaseModelUtil.getModelId());
		logUserOperateBean.setOrgId(orgId);          //机构id
		logUserOperateBean.setUserAccount(userAccount);//用户账号
		logUserOperateBean.setUserName(userName);      //用户名
		logUserOperateBean.setModuleId(moduleId);      //模块id，取值来源于：AppConstant中AppConstant.Module_id，根据自己模块选择对应的id
		logUserOperateBean.setModuleName(moduleName);   //模块名称，取值来源于：AppConstant中AppConstant.Module_Name，根据自己模块选择对应的值
		logUserOperateBean.setOperateId(operateId);//功能、操作id,取值来源于：AppConstant中AppConstant.Operate_id，根据自己功能或者操作选择对应的id
		logUserOperateBean.setOperateName(operateName);  //功能、操作名,取值来源于：AppConstant中AppConstant.Operate_Name，根据自己功能或者操作选择对应的值
		logUserOperateBean.setContent(content);          //详细描述,
		logUserOperateBean.setSystemOperateId(systemOperateId);//销售管理id，销售管理保存时传入systemOperateId，其他模块为""即可。
		logUserOperateService.insertlogUserOperate(logUserOperateBean);

	}

	/*************************************************************************************************************/
	/*******************************                   高级查询项设置                                                 *****************************/
	/*************************************************************************************************************/
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
	
	//获取文本查询列表
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
	
	// 页面非文本查询项排序值
	public Map<String,Integer> getUnTestSearchSort(String module,String orgId,Integer roleType){
		List<CustSearchSet> list = cachedService.getCustSearchSetList(module,orgId);
		int state = getCommonOwnerOpenState(orgId);
		Map<String,Integer>map = new HashMap<String, Integer>();
		int i = 0;
		for (CustSearchSet filed : list) {			
			if (filed.getIsQuery() == 1 && filed.getDataType() != 1 
					&& !(roleType ==0 && 
					("ownerAcc".equals(filed.getDevelopCode())
							||"resAddGroup".equals(filed.getDevelopCode())
					))){
				if(state == 0 && filed.getSearchCode().equals("commonAccsStr")){
					continue;
				}
				i++;
				map.put(filed.getSearchCode(),i); 
			}
		}	
		return map;
	}
	
	// 客户跟进页面需隐藏字段列
	public List<Integer>getHideSortListCode(String module,String orgId,String isState,List<String[]> showModult,int leftCos){
		List<CustSearchSet> list = cachedService.getCustSearchSetList(module,orgId);
		Map<String,String>map = new HashMap<String, String>();
		for (CustSearchSet filed : list) {
			if (filed.getIsShow() == 1) {
				map.put(filed.getDevelopCode(), filed.getDevelopCode());
			}
		}
		List<Integer>rList = new ArrayList<Integer>();		
		for(String[]strs : showModult){
			if(("2".equals(strs[2]) || isState.equals(strs[2])) && map.get(strs[0]) == null){
				rList.add(Integer.parseInt(strs[1]) + leftCos); // 存储需要隐藏的排序值 . 因为第一列是复选框、第二列是操作列，所以加了2
			}
		}		
		return rList;
	}
	
	
	
	/**
	 * 放款管理 - 自定义名称集合
	 * 
	 * @param orgId
	 * @return
	 */
	public Map<String, String> getQupaiIsDefinedNameList(String orgId) {
		List<CustFieldSet> fieldSets = null;
		Map<String, String> map = new HashMap<String, String>();

		fieldSets = cachedService.getQupaiFiledSet(orgId);

		for (CustFieldSet filed : fieldSets) {
			if (filed.getFieldCode().contains("defined")) {
				map.put(filed.getFieldCode(), filed.getFieldName());
			}
		}
		return map;
	}
	
	public List<CustFieldSet> getQupaiIsQueryList(String orgId) {
		List<CustFieldSet> fieldSets = cachedService.getQupaiFiledSets(orgId);
		List<CustFieldSet> list = new ArrayList<CustFieldSet>();
		for (CustFieldSet filed : fieldSets) {
			if (filed.getIsQuery() == 1 && filed.getEnable() == CustFieldSet.FIELD_ENABLED
					&& filed.getDataType() == CustFieldSet.DATATYPE_TEXT) {
				list.add(filed);
			}
		}
		return list;
	}
	
	
	
}
