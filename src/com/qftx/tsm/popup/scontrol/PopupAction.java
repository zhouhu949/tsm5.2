package com.qftx.tsm.popup.scontrol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qftx.base.auth.bean.Org;
import com.qftx.base.auth.dao.OrgMapper;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.SysRunException;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.bean.TsmCustGuide;
import com.qftx.tsm.cust.service.*;
import com.qftx.tsm.follow.dto.CustFollowDto;
import com.qftx.tsm.follow.service.CustFollowService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.service.OptionService;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.Product;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User�� bxl Date�� 2016/11/9 Time�� 11:41
 */
@Controller
@CrossOrigin
@RequestMapping("/popup")
public class PopupAction extends BaseAction{
	private Logger logger = Logger.getLogger(PopupAction.class);
	@Autowired
	private CachedService cachedService;
	@Autowired
	private ComResourceService comResourceService;
	@Autowired
	private ComResourceGroupService comResourceGroupService;
	@Autowired
	private OptionService optionService;
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private CustFollowService custFollowService;
	@Autowired
	private ResCustInfoDetailService resCustInfoDetailService;
	@Autowired
	private TsmCustGuideProcService tsmCustGuideProcService;
	@Autowired
	private TsmCustGuideService tsmCustGuideService;
    @Autowired
    private ComResourceService resCustService;
    @Autowired
    private OrgMapper orgMapper;
	@Value("#{config['tsm_service_url']}")
	private String serviceProjectUrl;

	/**
	 * �ܵ���ת�ӿ����
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping()
	public String defaultList(HttpServletRequest request) {
		return list(request);
	}

	/*
	j_username    当前登陆账号;
	j_path        跳转地址，慧沟通免登地址（/res/cust/custCardInfo.do）; //慧营销4.0 来电弹屏:/res/incall/toEditResIframe; telPhoneCall;
	j_parameter  弹屏需要的参数
	考虑到参数存在地址栏无法辨别的特殊符号，需要将此参数URL编码一次，
	例如：URLEncoder.encode(“?phone=18914191807”,"utf-8");
	URLEncoder.encode(“?phone=057145424524”,"utf-8");

	j_timestamp   时间戳（格式：yyyyMMddHHmmss）;
	type          登陆身份，慧沟通身份（tsm_hgt）;
	j_key         加密key（1ac740bdc71bsc86836de1da07adcbeb）;
	j_sign        签名（MD5加密字符串）加密规则: MD5(j_username + j_path + j_timestamp + type + j_key);
	*/
	@RequestMapping("list")
	public String list(HttpServletRequest request) {
		String j_username = request.getParameter("j_username");
		String custId = request.getParameter("j_custid");
		String callid = request.getParameter("j_callid");
		String signNum = request.getParameter("j_signNum");
		String j_path = request.getParameter("j_path");
		String type = request.getParameter("type");
		String key = request.getParameter("key");
		String j_timestamp = request.getParameter("j_timestamp");
		String j_sign = request.getParameter("j_sign");
		String phone = request.getParameter("j_parameter");
		final String telPhoneCall = "telPhoneCall"; // 来电弹屏
		final String main = "main"; // ��ת��ҳ
		final String telPhoneRes = "telPhoneRes"; // 添加资源
		final String telPhoneCust = "telPhoneCust";//添加客户
		final String tobarry = "tobarry"; // 弹出弹幕页面
		final String tocustFollowPage = "tocustFollowPage"; // 客户跟进
		final String toRobot = "toRobot"; // 调整机器人
		final String toRobotTrain = "toRobotTrain"; // 调整机器人
		logger.debug("弹出客户端的请求：custid=" + custId + ",account=" + j_username + ",j_path=" + j_path + ",type=" + type + ",phone=" + phone + ",j_timestamp="
				+ j_timestamp + ",j_sign=" + j_sign + ",serviceProjectUrl=" + serviceProjectUrl);
		ShiroUser user = null;
		try {
			SecurityUtils.getSubject().logout();
			user = ShiroUtil.getShiroUser();
			logger.debug("shiroUser1:\n" + JSON.toJSONString(user, true));
			if (user == null) {
				login(j_username, "123456");
				user = ShiroUtil.getShiroUser();
			}
		} catch (Exception e) {
		}
		int isState = user.getIsState();
		setIsReplaceWord(request);
		OptionBean option=new OptionBean();
		option.setOrgId(user.getOrgId());		
		option.setItemCode("companyTrade");
		option.setOrderKey("sort asc");
		List<OptionBean> optionList = optionService.getListByCondtion(option);
		request.setAttribute("optionList", optionList);
		
		if (telPhoneCall.equals(j_path)) {
		
			Map<String, String> map = new HashMap<String, String>();
			map.put("orgId", user.getOrgId());
			map.put("ownerAcc", user.getAccount());
			map.put("phone", phone);
			String isCommon="1";//默认不是共有客户
			if (StringUtils.isBlank(custId)) {
				custId = comResourceService.getCustInfo(map, user.getIsState());

			}
		    String	custId_new = comResourceService.getComCustInfo(map, user.getIsState());	
			if(custId ==null || custId == "" && custId_new !=null){
				custId=custId_new;
			}
			if(custId_new !=null && custId_new !=""){
				isCommon="0";
			}
			List<String> shiroIdsList = cachedService.getAuthUrlMenu(user);
			String toFollowPage = request.getParameter("toFollowPage");
			if(toFollowPage!=null&&!"".endsWith(toFollowPage)){
				request.setAttribute("toFollowPage", toFollowPage);	
			}
		    request.getSession().setAttribute("shiroIdsList", JSONObject.toJSON(shiroIdsList.toArray()));
			request.setAttribute("isCommon", isCommon);//判断是否是共有客户：0为是共有客户，1为不是共有客户
			request.setAttribute("custId", custId);
			request.setAttribute("phone", phone);
			request.setAttribute("serviceProjectUrl", serviceProjectUrl);
			return "incall/edit_cust_iframe";
		}
		setMySelf(request, isState, user, phone);
		if (telPhoneRes.equals(j_path)) {
			request.setAttribute("comFrom", 3);
			if (isState == 1) {
				return "res/add_cust";
			} else {
				return "/res/add_person_cust";
			}
		}
		if (telPhoneCust.equals(j_path)) {
			request.setAttribute("comFrom", 3);
			if (isState == 1) {// ��ҵ��Դ
				return "res/addCustByScreen";
			} else {// ������Դ
				return "res/addCustByScreen_person";
			}
		}
		if (tobarry.equals(j_path)) {//调整到弹幕页面
			return "/barrage";
		}
		
		if (tocustFollowPage.equals(j_path)) {//跳转到跟进页面
			try {
//				request.setAttribute("dayPlan", "1");
				request.setAttribute("extension", "1");
				ResCustInfoBean bean = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custId);
				Map<String, Object> followMap = new HashMap<String, Object>();
				followMap.put("orgId", user.getOrgId());
				followMap.put("custFollowId", bean.getLastCustFollowID());
				followMap.put("custId", bean.getResCustId());
				followMap.put("state", user.getIsState());
				List<CustFollowDto> followDtos = custFollowService.queryCustFollowByCustId(followMap);
				request.setAttribute("lastCustFollow", (followDtos == null || followDtos.size() <= 0) ? new CustFollowDto() : followDtos.get(0));
				String name = "";
				String custDetailId = "";
				if (user.getIsState() == 1) {
					Map<String, String> map = new HashMap<String, String>();
					map = resCustInfoDetailService.getCustDetailName(user.getOrgId(), phone, custId);
					if (map != null) {
						name = map.get("name");
					}
				} else {
					if (bean != null) {
						name = bean.getName();
					}
				}
				request.setAttribute("type", bean.getType());
				request.setAttribute("status", bean.getStatus());
				request.setAttribute("custId", custId);
				request.setAttribute("name", name);
				request.setAttribute("custDetailId", custDetailId);
				request.setAttribute("actionDate", com.qftx.common.util.DateUtil.getDateCurrentDate(com.qftx.common.util.DateUtil.hour24HMSPattern));
				request.setAttribute("followId", SysBaseModelUtil.getModelId()); // 跟进Id
				request.setAttribute("planParam", request.getParameter("planParam")); // 1：只显示计划中客户，2：不显示计划中客户
				// 取得单位下各选项列表(销售进程、客户类型、适用产品、反馈信息)
				List<OptionBean> saleProcessList = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
				List<OptionBean> custTypeList = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
				List<Product> suitProcList = cachedService.getOpionProduct(user.getOrgId());
				List<OptionBean> labelList = cachedService.getOptionList(AppConstant.SALES_TYPE_TEN, user.getOrgId());
				request.setAttribute("saleProcessList", saleProcessList); // 销售进程
				request.setAttribute("custTypeList", custTypeList); // 客户类型
				request.setAttribute("suitProcList", suitProcList); // 适用产品
				request.setAttribute("labelList", JsonUtil.getJsonString(labelList));
				String productIds = "";
				String productNames = "";
				if (bean != null) {
					List<String> procIds = tsmCustGuideProcService.getProcIdsByCustId(user.getOrgId(), bean.getResCustId());
					if (procIds != null && procIds.size() > 0) {
						List<Product> levels = cachedService.getOpionProduct(user.getOrgId());
						Map<String, String> procIdsMap = cachedService.changeOptionProductListToMap(levels);
						for (String proId : procIds) {
							if(procIdsMap.containsKey(proId)){
								productIds = productIds + proId + ",";
								productNames = productNames + procIdsMap.get(proId) + ",";
							}
						}
					}
				}
				request.setAttribute("productIds", productIds);
				request.setAttribute("productNames", productNames);
				// 获取指定客户导航
				TsmCustGuide tsmCustGuideEntity = new TsmCustGuide();
				tsmCustGuideEntity.setCustId(custId);
				tsmCustGuideEntity.setOrgId(user.getOrgId());
				List<TsmCustGuide> tsmCustGuideList = tsmCustGuideService.getListByCondtion(tsmCustGuideEntity);
				if (tsmCustGuideList != null && tsmCustGuideList.size() > 0) {
					request.setAttribute("tsmCustGuideEntity", tsmCustGuideList.get(0));
				}

				// 获取适用产品 默认值
				if (suitProcList != null && suitProcList.size() > 0 && com.qftx.common.util.StringUtils.isBlank(productIds)) {
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
						request.setAttribute("productIds", str);
					}
					if (sbf1 != null && StringUtils.isNotBlank(sbf1.toString())) {
						String str1 = sbf1.toString().substring(0, sbf1.toString().length() - 1);
						request.setAttribute("productNames", str1);
					}

				}
//				getSignSetting(request);
				// 获取默认下次跟进时间
				request.setAttribute("defDate", getCustCacheDate(request));
				request.setAttribute("phone", phone);
				if(bean.getStatus().intValue() == 4 || bean.getStatus().intValue() == 5){
					request.setAttribute("isPoolCust", 1);
				}
			} catch (Exception e) {
				throw new SysRunException(e);
			}
			return "card/custFollow";
		}
		
		if (toRobot.equals(j_path)) {
			String robotUrl=ConfigInfoUtils.getStringValue("tsm_airobot_url");;
		    return "redirect:"+robotUrl+"/call/callRecord/infoPage?j_parameter="+phone;

		}
		if (toRobotTrain.equals(j_path)) {
			String robotUrl=ConfigInfoUtils.getStringValue("tsm_airobot_url");;
			return "redirect:"+robotUrl+"/procedure/task/runPage?j_parameter="+phone;

		}
		return main;
	}

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
	 * ���絯��Ҫʵ����������
	 *
	 * @param account
	 * @param password
	 */
	public void login(String account, String password) {
		logger.debug("�����־:" + account);
		UsernamePasswordToken token = new UsernamePasswordToken(account, password.toCharArray());
		Subject user = SecurityUtils.getSubject();
		token.setRememberMe(true);
		user.login(token);
	}

	public void setMySelf(HttpServletRequest request, int isState, ShiroUser user, String phone) {
		setCustWordCheck(request);
		List<CustFieldSet> fieldSets = null;
		List<CustFieldSet> concatFieldSets = null;
		if (isState == 1) {// ��ҵ��Դ
			fieldSets = cachedService.getComFiledSets(user.getOrgId());
			concatFieldSets = cachedService.getContactsFiledSets(user.getOrgId());
		} else {// ������Դ
			fieldSets = cachedService.getPersonFiledSets(user.getOrgId());
		}
		List<ResourceGroupBean> groupList = comResourceGroupService.queryResGroup(user.getOrgId());
		request.setAttribute("groupList", groupList);
		request.setAttribute("phone", formatPhone(phone, request));
		request.setAttribute("fieldSets", fieldSets);
		request.setAttribute("concatFieldSets", concatFieldSets);
	}

	/**
	 * �ͻ���Ϣ�ֶθ�ʽУ������
	 *
	 * @param request
	 * @create 2015��11��25�� ����2:45:20 lixing
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

	/**
	 * �����Ƿ���Ҫģ��绰����
	 *
	 * @param request
	 * @create 2015��11��25�� ����2:45:20 lixing
	 * @history
	 */
	public void setIsReplaceWord(HttpServletRequest request, String orgId, String account) {
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA24, orgId);
		Integer idReplaceWord = 0;
		if (!list.isEmpty() && list.get(0) != null && !cachedService.judgeHideWhiteList(orgId, account)) {
			idReplaceWord = new Integer(list.get(0).getDictionaryValue());
		}
		request.setAttribute("idReplaceWord", idReplaceWord);
	}

	/**
	 * ��ȡ��Ŀ��ַ
	 *
	 * @param request
	 * @return
	 */
	public String getProgetUtil(HttpServletRequest request) {
		if (80 == request.getServerPort()) {
			return request.getScheme() + "://" + request.getServerName() + request.getContextPath();
		} else {
			return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		}
	}

	/**
	 * ��ʽ���̻����� ˵������λ��0��ȥ����ź�ġ�-���ַ�
	 *
	 * @param phone
	 * @param request
	 * @return
	 * @create 2015��11��17�� ����3:14:52 lixing
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
					rstPhnoe = px + rstPhnoe;
				}
			}
		}
		return rstPhnoe;
	}

	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
	
	
    /**
     * 表单页面
     * @param request
     * @return 
     * @create  2018-6-20 下午1:31:34 xiaoxh
     * @history  5.1.x
     */
    @RequestMapping("/toPersonal_center_restable")
    public String toResTable(HttpServletRequest request){
		String userAccount = request.getParameter("userAccount");
		String orgId = request.getParameter("orgId");

    	request.setAttribute("orgId", orgId);
    	request.setAttribute("userAccount", userAccount);

    	return "home/personal/right/personal_center_restable";
    }
	
    
    /**
     * 提交表单
     * @param request
     * @return 
     * @create  2018-6-20 下午1:31:34 xiaoxh
     * @history  5.1.x
     */
    @RequestMapping("/saveRes")
    public void saveRes(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> map=new HashMap<>();
    	try{
    		String name = request.getParameter("name");
    		String mobilephone = request.getParameter("mobilephone");
    		String qq = request.getParameter("qq");
    		String userAccount = request.getParameter("userAccount");
    		String orgId = request.getParameter("orgId");

    		if(userAccount == null || "".equals(userAccount)){
    			map.put("code", "-1");
    			map.put("msg", "userAccount为null");
    		}
    		
    		if(orgId == null || "".equals(orgId)){
    			map.put("code", "-1");
    			map.put("msg", "orgId为null");
    		}
    		
     		List<DataDictionaryBean> dic = cachedService.getDirList(AppConstant.DATA14, orgId);//总开关
     		if (!dic.isEmpty() && "1".equals(dic.get(0).getDictionaryValue())) {
			List<DataDictionaryBean> dlist0 = cachedService.getDirList(AppConstant.DATA11, orgId);//手机号去重
			List<DataDictionaryBean> dlist1 = cachedService.getDirList(AppConstant.DATA12, orgId);//单位名称去重
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orgId", orgId);
			Org org = orgMapper.getByPrimaryKey(orgId);
    		int isState=0;
            if (org != null) {
            	isState = org.getState();
             }
			param.put("isState", isState);
			List<ResCustInfoBean> lists=new ArrayList<ResCustInfoBean>();
            if (!dlist0.isEmpty() && !"1".equals(dlist0.get(0).getDictionaryValue())){//对所有资源进行手机号码去重
            	if(!"4".equals(dlist0.get(0).getDictionaryValue())){
            		param.put("validateType", dlist0.get(0).getDictionaryValue());
            	}
    			if(mobilephone!=null&&!"".endsWith(mobilephone)){
    				param.put("phone", mobilephone);
    				lists = resCustService.getDPPhone(param);
    			}
			}

			if(lists==null || lists.size()<1){
		        if (!dlist1.isEmpty() && !"0".equals(dlist1.get(0).getDictionaryValue())){//对所有单位名称进行去重	
		        	if(!"3".equals(dlist1.get(0).getDictionaryValue())){
	            		param.put("validateType", dlist1.get(0).getDictionaryValue());
	            	}
		        	if(name!=null&&!"".endsWith(name)){
						param.put("company", name);
						lists = resCustService.getDPUnit(param);	
					}
		        }
			}
				if (lists.size() > 0) {
	    			map.put("code", "-1");
	    			map.put("msg", "您的信息已提交，请耐心等待!");
				}else{
					ResCustInfoBean resInfo=new ResCustInfoBean();
					resInfo.setOrgId(orgId);
					resInfo.setOwnerAcc(userAccount);
					resInfo.setMobilephone(mobilephone);
					resInfo.setName(name);
					resInfo.setMainLinkman(name);
					resInfo.setDefined3(qq);
		    		resCustInfoService.addMyRes(resInfo,orgId, userAccount);
				}
    		}else{
				ResCustInfoBean resInfo=new ResCustInfoBean();
				resInfo.setOrgId(orgId);
				resInfo.setOwnerAcc(userAccount);
				resInfo.setMobilephone(mobilephone);
				resInfo.setName(name);
				resInfo.setMainLinkman(name);
				resInfo.setDefined3(qq);
	    		resCustInfoService.addMyRes(resInfo,orgId, userAccount);
    		}
    		
			map.put("code", "0");
    	}catch(Exception e){
			map.put("code", "-1");
       		map.put("msg", e.getMessage());
    	}
    	response.setContentType("text/html; charset=UTF-8");		
		response.setHeader("Access-Control-Allow-Origin","*");
		try {
			response.getWriter().print(JsonUtil.getJsonString(map));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    

}
