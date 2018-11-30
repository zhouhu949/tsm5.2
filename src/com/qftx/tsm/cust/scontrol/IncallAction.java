package com.qftx.tsm.cust.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.MathUtil;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.SysRunException;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.area.bean.ChinaCityBean;
import com.qftx.tsm.area.bean.ChinaCountyBean;
import com.qftx.tsm.area.bean.ChinaProvinceBean;
import com.qftx.tsm.area.service.AreaService;
import com.qftx.tsm.callrecord.dto.CallRecordListDto;
import com.qftx.tsm.callrecord.dto.ConditionDto;
import com.qftx.tsm.callrecord.dto.DtoCallRecordInfoBean;
import com.qftx.tsm.callrecord.dto.FollowCallQueryDto;
import com.qftx.tsm.callrecord.dto.TsmRecordCallBean;
import com.qftx.tsm.callrecord.dto.TsmRecordCallDto;
import com.qftx.tsm.callrecord.service.CallRecordInfoService;
import com.qftx.tsm.callrecord.util.CallRecordGetUtil;
import com.qftx.tsm.contract.dto.ContractDto;
import com.qftx.tsm.contract.dto.ContractOrderDetailDto;
import com.qftx.tsm.contract.service.ContractService;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResCustInfoDetailBean;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.bean.TsmCustGuide;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.cust.service.*;
import com.qftx.tsm.follow.dto.CustFollowCallDto;
import com.qftx.tsm.follow.dto.CustFollowDto;
import com.qftx.tsm.follow.service.CustFollowService;
import com.qftx.tsm.log.dao.LogCustInfoMapper;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.service.OptionService;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.Product;
import com.qftx.tsm.sys.bean.ServiceVisit;
import com.qftx.tsm.sys.bean.TsmCustReview;
import com.qftx.tsm.sys.dto.ServiceVisitDto;
import com.qftx.tsm.sys.service.ServiceVisitService;
import com.qftx.tsm.sys.service.TsmCustReviewService;
import com.qftx.tsm.tao.service.CustGuideService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 来电弹屏
 * 
 * @author: wuwei
 * @since: 2016年2月20日 下午1:44:54  
 * @history:
 */
@Controller
@RequestMapping("/res/incall")
public class IncallAction extends BaseAction {
	private Logger logger = Logger.getLogger(IncallAction.class);
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private StaffResourceMgService staffResourceMgService;
	@Autowired
	private CustFollowService custFollowService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private UserService userService;
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
	transient private JdbcTemplate jdbcTemplate;
	@Resource
	PlatformTransactionManager platformTransactionManager;
	@Resource
	LogCustInfoMapper logCustInfoMapper;
	@Resource
	private TsmCustGuideService tsmCustGuideService;
	@Resource
	private CallRecordInfoService callRecordInfoService;
	@Resource
	private TsmCustReviewService tsmCustReviewService;
	@Autowired
	private ContractService contractService;
	@Resource
	private AreaService areaService;
	@Resource
	private ServiceVisitService serviceVisitService;
	@Resource
	private TsmCustGuideProcService tsmCustGuideProcService;
	@Value("#{config['tsm_service_url']}")
	private String serviceProjectUrl;
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	@Resource
	private ResCustInfoMapper resCustInfoMapper;
	@Resource
	private OptionService optionService;
	@Resource
	private CustGuideService custGuideService;

	@RequestMapping("/toEditResIframe")
	public String toEditResIframe(HttpServletRequest request, String custId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String isCommon="1";//默认不是共有客户
		ResCustInfoBean bean=resCustInfoMapper.getByPrimaryKey(user.getOrgId(), custId);
		if(bean!=null){
			if(bean.getCommonAcc()==user.getAccount()||user.getAccount().equals(bean.getCommonAcc())){
				isCommon="0";//0为共有客户
			}
		}
		if(isCommon =="0" || "0".equals(isCommon)){
			int isOpen = getCommonOwnerOpenState(user.getOrgId());
			if(isOpen == 1){
				request.setAttribute("commonOnwerGiveUp", getCommonOwnerGiveUpAuth(user.getOrgId()));/**共有者 放入公海权限 0-关闭 1-开启*/
				request.setAttribute("CommonOwnerEditAuth", getCommonOwnerEditAuth(user.getOrgId()));/**共有者 编辑客户信息权限 0-关闭 1-开启*/
			}
		}else {
			request.setAttribute("CommonOwnerGiveUpAuth", 1);
			request.setAttribute("CommonOwnerEditAuth", 1);
		}
		request.setAttribute("custId", custId);
		request.setAttribute("serviceProjectUrl", serviceProjectUrl);
		return "incall/edit_cust_iframe";
	}
	public void setShowValue(ResCustInfoBean rcib,List<CustFieldSet> fieldSets) throws Exception {
		if (rcib != null) {
			Class<?> classType = rcib.getClass();
			for (CustFieldSet custFieldSet : fieldSets) {
				String fieldCode = custFieldSet.getFieldCode();
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
						Object value = getMethod.invoke(rcib, new Object[] {});
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
		String reqId = SysBaseModelUtil.getModelId();
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			logger.info("toEditRes reqId=" + reqId + "。custId=" + custId + "。account=" + user.getAccount());
			setCustWordCheck(request);
			setIsRead(request);
			setIsReplaceWord(request, user);
			ResCustInfoBean rcib = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custId);
			request.setAttribute("custInfoBean", rcib);
			request.setAttribute("issys", ShiroUtil.getShiroUser().getIssys());
			List<ResourceGroupBean> groupList = comReesourceGroupService.queryResGroup(user.getOrgId());
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
				return "incall/tp_cust_edit";
			} else {// 个人资源
				fieldSets = cachedService.getPersonFiledSets(user.getOrgId());
				setShowValue(rcib,fieldSets);
				request.setAttribute("fieldSets", fieldSets);
				return "incall/tp_persion_cust_edit";
			}
		} catch (Exception e) {
			logger.error("reqId=" + reqId + "loginAcc=" + user.getAccount() + ",loginName=" + user.getName() + ",custId=" + custId, e);
			throw new SysRunException(e);
		}
	}

	/**
	 * 跳转到查询资源页面
	 * 
	 * @param request
	 * @param custId
	 * @return
	 * @create 2015年12月15日 下午8:52:21 lixing
	 * @history
	 */
	@RequestMapping("/toSearchRes")
	public String toSearchRes(HttpServletRequest request, String custId ,String isCommon) {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<CustFieldSet> fieldSets = null;
		String pname = "";
		String cname = "";
		String oname = "";
		int opType = user.getIsState();
		String reqId = SysBaseModelUtil.getModelId();
		try {
			logger.info("toSearchRes reqId=" + reqId + "。custId=" + custId + "。account=" + user.getAccount());
			setIsReplaceWord(request, user);
			ResCustInfoBean rcib = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custId);
			List<ResourceGroupBean> groupList = comReesourceGroupService.queryResGroup(user.getOrgId());
			request.setAttribute("groupList", groupList);
			OptionBean option=new OptionBean();
			option.setOrgId(user.getOrgId());		
			option.setItemCode("companyTrade");
			option.setOrderKey("sort asc");
			List<OptionBean> optionList = optionService.getListByCondtion(option);
			request.setAttribute("optionList", optionList);
			int resType = ShiroUtil.getShiroUser().getIsState();
			Integer pid = rcib.getProvinceId();
			// 省市区
			if (pid != null) {
				ChinaProvinceBean cpb = areaService.getChinaProvinceByPid(pid);
				pname = cpb.getPname();
				Integer cid = rcib.getCityId();
				if (cid != null) {
					ChinaCityBean ccb = areaService.getChinaCityByCid(cid);
					cname = ccb.getCname();
					Integer oid = rcib.getCountyId();
					if (oid != null) {
						ChinaCountyBean cob = areaService.getChinaCountyByOid(oid);
						oname = cob.getOname();
					}
				}
			}

			if(isCommon =="0" || "0".equals(isCommon)){
				int isOpen = getCommonOwnerOpenState(user.getOrgId());
				if(isOpen == 1){
				request.setAttribute("commonOnwerGiveUp", getCommonOwnerGiveUpAuth(user.getOrgId()));/**共有者 放入公海权限 0-关闭 1-开启*/
				request.setAttribute("CommonOwnerEditAuth", getCommonOwnerEditAuth(user.getOrgId()));/**共有者 编辑客户信息权限 0-关闭 1-开启*/
				}
				}else {
				request.setAttribute("CommonOwnerGiveUpAuth", 1);
				request.setAttribute("CommonOwnerEditAuth", 1);
				}
			
			request.setAttribute("pname", pname);
			request.setAttribute("cname", cname);
			request.setAttribute("oname", oname);
			if (resType == 1) {// 企业资源
				fieldSets = cachedService.getComFiledSets(user.getOrgId());
				List<ResCustInfoDetailBean> details = resCustInfoDetailService.getCustsInfoDetails(user.getOrgId(), rcib.getResCustId());
				rcib = resCustInfoService.getCustData(fieldSets, pname, cname, oname, rcib);
				request.setAttribute("custInfoBean", rcib);
				request.setAttribute("fieldSets", fieldSets);
				request.setAttribute("details", details);
				return "incall/cust_info";
			} else {// 个人资源
				fieldSets = cachedService.getPersonFiledSets(user.getOrgId());
				rcib = resCustInfoService.getCustData(fieldSets, pname, cname, oname, rcib);
				request.setAttribute("custInfoBean", rcib);
				request.setAttribute("fieldSets", fieldSets);
				return "incall/cust_person_info";
			}
		} catch (Exception e) {
			logger.error("reqId=" + reqId + "loginAcc=" + user.getAccount() + ",loginName=" + user.getName() + ",custId=" + custId, e);
			throw new SysRunException(e);
		}
	}

	/** 客户跟进详细页面 */
	@RequestMapping("/custFollowPage")
	public String custFollowPage(HttpServletRequest request,String isCommon) {
		String reqId = SysBaseModelUtil.getModelId();
		ShiroUser user = ShiroUtil.getShiroUser();
		String custId = request.getParameter("custId"); // 资源Id
		try {
			ResCustInfoBean bean = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custId);
			Map<String, Object> followMap = new HashMap<String, Object>();
			followMap.put("orgId", user.getOrgId());
			followMap.put("custFollowId", bean.getLastCustFollowID());
			followMap.put("custId", bean.getResCustId());
			followMap.put("state", user.getIsState());
			List<CustFollowDto> followDtos = custFollowService.queryCustFollowByCustId(followMap);
			request.setAttribute("lastCustFollow", (followDtos == null || followDtos.size() <= 0) ? new CustFollowDto() : followDtos.get(0));
			String phone = request.getParameter("phone");
			String name = "";
			logger.info("custFollowPage reqId=" + reqId + "。custId=" + custId + "。account=" + user.getAccount() + "|phone" + phone);
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
			
			if(isCommon =="0" || "0".equals(isCommon)){
			int isOpen = getCommonOwnerOpenState(user.getOrgId());
			if(isOpen == 1){	
			int CommonOwnerGiveUpAuth=getCommonOwnerGiveUpAuth(user.getOrgId());	/**共有者 放弃公海权限 0-关闭 1-开启*/
			int CommonOwnerEditAuth=getCommonOwnerEditAuth(user.getOrgId());	/**共有者 编辑客户信息权限 0-关闭 1-开启*/
			request.setAttribute("CommonOwnerGiveUpAuth", CommonOwnerGiveUpAuth);
			request.setAttribute("CommonOwnerEditAuth", CommonOwnerEditAuth);
			}
			}else {
			request.setAttribute("CommonOwnerGiveUpAuth", 1);
			request.setAttribute("CommonOwnerEditAuth", 1);
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
			Map<String,Object> maps=custGuideService.getLableList(user.getOrgId());
			Integer isSelect=(Integer) maps.get("isSelect");
//			List<OptionDto> opList=(List<OptionDto>) maps.get("opList");
//			List<OptionBean> labelList=new ArrayList<OptionBean>();
			List<OptionBean> labelList = cachedService.getOptionList(AppConstant.SALES_TYPE_TEN, user.getOrgId());
//			if(opList!=null&&opList.size()>0){
//				for(OptionDto dto :opList){
//					OptionBean beans=new OptionBean();
//					beans.setOptionlistId(dto.getOptionId());
//					beans.setOptionName(dto.getOptionName());
//					beans.setIsDefault(Short.valueOf(dto.getIsDefault()));
//					labelList.add(beans);
//				}
//			}
			
			String labelId=bean.getLabelCode();
			String labelName=bean.getLabelName();
			if(labelId!=null&&labelName!=null){
			if(!"".endsWith(labelId)&&!"".endsWith(labelName)){
				labelId=labelId.replace("#", ",");
//				labelId=labelId.substring(0, labelId.length()-1);
				labelName=labelName.replace("#", ",");
//				labelName=labelName.substring(0, labelName.length()-1);
	
			}
			}
			List<OptionBean> label6List = new ArrayList<OptionBean>(); // 默认显示6个
			List<OptionBean> otberLabelList = new ArrayList<OptionBean>(); // 其余隐藏
			String ids="";
			if (labelList != null && labelList.size() > 0) {
				for (int i = 0; i < labelList.size(); i++) {
					if (i < 6) {
						label6List.add(labelList.get(i));
					} else {
						otberLabelList.add(labelList.get(i));
					}
					if(i<labelList.size()-1&&i!=labelList.size()-1){
					ids=ids+labelList.get(i).getOptionlistId()+",";
					}else if(i==labelList.size()-1){
					ids=ids+labelList.get(i).getOptionlistId();	
					}
				}
			}
			request.setAttribute("labelName", labelName);
			request.setAttribute("labelIds", labelId);
			request.setAttribute("labelList", labelList);
			request.setAttribute("saleProcessList", saleProcessList); // 销售进程
			request.setAttribute("custTypeList", custTypeList); // 客户类型
			request.setAttribute("suitProcList", suitProcList); // 适用产品
			request.setAttribute("isSelect", isSelect); // 
			request.setAttribute("label6List", label6List);
			request.setAttribute("otberLabelList", otberLabelList);
			String productIds = "";
			String productNames = "";
			if (bean != null) {
				List<String> procIds = tsmCustGuideProcService.getProcIdsByCustId(user.getOrgId(), bean.getResCustId());
				if (procIds != null && procIds.size() > 0) {
					List<Product> levels = cachedService.getOpionProduct(user.getOrgId());
					Map<String, String> procIdsMap = cachedService.changeOptionProductListToMap(levels);
					for (String proId : procIds) {
						productIds = productIds + proId + ",";
						if(procIdsMap.get(proId)!=null&&!"".equals(procIdsMap.get(proId))){
							productNames = productNames + procIdsMap.get(proId) + ",";
						}
						
					}
				}
			}
			if(productNames==null||"".equals(productNames)){
				productNames = "--请选择--";
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

			// 获取默认下次跟进时间
			request.setAttribute("defDate", getCustCacheDate(request));
			request.setAttribute("phone", phone);
		} catch (Exception e) {
			logger.error("reqId=" + reqId + "loginAcc=" + user.getAccount() + ",loginName=" + user.getName() + ",custId=" + custId, e);
			throw new SysRunException(e);
		}
		return "/incall/custFollow";
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
		String reqId = SysBaseModelUtil.getModelId();
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			setCustWordCheck(request);
			setIsReplaceWord(request, user);
			if (tscidId != null) {
				logger.info("addDetail reqId=" + reqId + "。tscidId=" + tscidId + "|rciId=" + rciId + "|account=" + user.getAccount());
				ResCustInfoDetailBean detail = resCustInfoDetailService.getByPrimaryKeyAndOrgId(user.getOrgId(), tscidId,rciId);
				request.setAttribute("detail", detail);
			}
			List<CustFieldSet> concatFieldSets = cachedService.getContactsFiledSets(ShiroUtil.getShiroUser().getOrgId());
			request.setAttribute("concatFieldSets", concatFieldSets);
			request.setAttribute("rciId", rciId);
		} catch (Exception e) {
			logger.error("reqId=" + reqId + "loginAcc=" + user.getAccount() + ",loginName=" + user.getName() + ",tscidId=" + tscidId + ",rciId=" + rciId, e);
			throw new SysRunException(e);
		}
		return "incall/tp_add_detail";
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
		return "incall/tp_edit_detail";
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
	 * 新增或保存联系人
	 * 
	 * @param response
	 * @param detailBean
	 * @create 2015年12月15日 下午8:06:56 lixing
	 * @history
	 */
	@RequestMapping("/saveDetail")
	@ResponseBody
	public String saveDetail(HttpServletResponse response, ResCustInfoDetailBean detailBean) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String reqId = SysBaseModelUtil.getModelId();
		try {
			logger.info("saveDetail reqId=" + reqId + "。detailBean=" + JsonUtil.getJsonString(detailBean) + "|account=" + user.getAccount());
			if (detailBean.getIsDefault() == null) {
				detailBean.setIsDefault(1);
			}
			resCustInfoDetailService.createOrEditDetail(detailBean, user);
			return "1";
		} catch (Exception e) {
			logger.error(
					"保存或新增联系人失败 。reqId=" + reqId + "loginAcc=" + user.getAccount() + ",loginName=" + user.getName() + ",detailBean="
							+ JsonUtil.getJsonString(detailBean), e);
			return "-1";
		}
	}

	@RequestMapping(value = "findFollowList")
	public String findFollowList(HttpServletRequest request, CustFollowDto custFollowDto) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String custId = request.getParameter("custId") == null ? "" : request.getParameter("custId");
		List<CustFollowDto> custFollows = null;
		String reqId = SysBaseModelUtil.getModelId();
		try {
			logger.info("findFollowList reqId=" + reqId + "。custId=" + custId + "。custFollowDto=" + JsonUtil.getJsonString(custFollowDto) + "|account="
					+ user.getAccount());
			if (custFollowDto == null) {
				custFollowDto = new CustFollowDto();
			}
			custFollowDto.setOrgId(user.getOrgId());
			custFollowDto.setFollowAcc(user.getAccount());
			custFollowDto.setCustId(custId);
			custFollowDto.setState(user.getIsState());
			custFollows = custFollowService.queryCustFollows4TPListPage(custFollowDto);
			// 组装跟进ID
			List<String> followIdSbf = new ArrayList<String>();
			// 将列表中的标签CODE值根据tsm_$_optionMap转换为标签NAME
			for (CustFollowDto followDto : custFollows) {
				followIdSbf.add(followDto.getCustFollowId());
				String labelName = followDto.getLabelName();
				if (StringUtils.isNotBlank(labelName)) {
					labelName = labelName.replaceAll("#", "，");
					followDto.setLabelName(labelName);
				}
			}

			// 获取相关跟进ID 的录音记录
			if (followIdSbf != null && followIdSbf.size() > 0) {
				/** 参数 */
				FollowCallQueryDto fcqd = new FollowCallQueryDto();
				fcqd.setOrgId(user.getOrgId());
				fcqd.setFollowIds(followIdSbf);
				List<TsmRecordCallBean> callLists =  CallRecordGetUtil.getRecordeCallFollowList(fcqd);
				if (callLists != null && callLists.size() > 0) {
					Map<String, List<CustFollowCallDto>> map1 = new HashMap<String, List<CustFollowCallDto>>();
					for (TsmRecordCallBean trcb : callLists) {
						if(trcb.getRecordState()!=null){
						if (trcb.getRecordState() == 1) { // 有录音
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
					}
					for (CustFollowDto followDto : custFollows) { // 重新组装 跟进记录
						if (map1.get(followDto.getCustFollowId()) != null) {
							followDto.setUrlList(map1.get(followDto.getCustFollowId()));
						}
					}
				}

			}
		} catch (Exception e) {
			logger.error("reqId=" + reqId + "loginAcc=" + user.getAccount() + ",loginName=" + user.getName() + ",custId=" + custId, e);
			throw new SysRunException(e);
		}
		request.setAttribute("custFollowList", custFollows);
		request.setAttribute("project_path", getProgetUtil(request));
		request.setAttribute("custFollowDto", custFollowDto);
		request.setAttribute("playUrl", ConfigInfoUtils.getStringValue("call_play_url"));
		request.setAttribute("custId", custId);
		return "incall/custFollowList";
	}

	/** 通话记录 查询列表 */
	@RequestMapping("/callList")
	public String list(HttpServletRequest request, HttpServletResponse response, DtoCallRecordInfoBean bean) throws Exception {
		ShiroUser user = ShiroUtil.getShiroUser();
		String custId = request.getParameter("custId") == null ? "" : request.getParameter("custId");
		String accs = request.getParameter("accs"); // 选择的所有联系人
		List<OptionBean> options = null;
		List<TsmRecordCallBean> list = null;
		ConditionDto item = null;
		TsmRecordCallBean beans=new TsmRecordCallBean();
		String reqId = SysBaseModelUtil.getModelId();
		try {
			logger.info("callList reqId=" + reqId + "。custId=" + custId + "。DtoCallRecordInfoBean=" + JsonUtil.getJsonString(bean) + "|account="
					+ user.getAccount());
			setDyField(request);
			setIsReplaceWord(request, user);
			// 如果数据分类选择了“全部” 将所有查询条件清空,包括行动标签
		    ConditionDto dtoBean = new ConditionDto();
		    dtoBean.setCustIds(Arrays.asList(custId.split(",")));
		    dtoBean.setOrgId(user.getOrgId());
		    dtoBean.getPage().setCurrentPage(bean.getPage().getCurrentPage());
			dtoBean.getPage().setTotalPage(bean.getPage().getTotalPage());
			dtoBean.getPage().setTotalResult(bean.getPage().getTotalResult());
			dtoBean.getPage().setShowCount(bean.getPage().getShowCount());
			try {
				System.out.println("请求=======" + JsonUtil.getJsonString(dtoBean));
				CallRecordListDto queryCall = callRecordInfoService.queryCallRecord(dtoBean);
				list = queryCall.getBeans();
				item = queryCall.getCondition();
				logger.debug("返回参数=" + JSON.toJSONString(queryCall, true));
				logger.debug("返回参数list=" + list.size());
			} catch (Exception e) {
				logger.error("query call error:" + e.getMessage(), e);
			}
			if (list == null) {
				list = new ArrayList<TsmRecordCallBean>();
			}
			if (list.size() > 0) {
				for (TsmRecordCallBean callBean : list) {
					callBean.setInputAcc(com.qftx.common.util.StringUtils.isEmpty(callBean.getInputAcc()) ? callBean.getInputAcc() : getUserName(
							user.getOrgId(), callBean.getInputAcc()));
				}
			}
			if (item == null) {
				item = new ConditionDto();
			}

			beans.getPage().setCurrentPage(item.getPage().getCurrentPage());
			beans.getPage().setTotalResult(item.getPage().getTotalResult());
			beans.getPage().setTotalPage(item.getPage().getTotalPage());
			beans.getPage().setShowCount(item.getPage().getShowCount());
			beans.getPage().setAjaxPageStr("");
			beans.getPage().setPageStr("");
			beans.getPage().setPageSubStr("");

			// 从缓存读取销售进程列表
			options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
		} catch (Exception e) {
			logger.error("reqId=" + reqId + "loginAcc=" + user.getAccount() + ",loginName=" + user.getName(), e);
			throw new SysRunException(e);
		}
		request.setAttribute("options", options);
		request.setAttribute("list", list);
		request.setAttribute("item", beans);
		request.setAttribute("accs", accs);
		request.setAttribute("custId", custId);
		// 服务地址，为了提供给客户端，弹出播放列表
		request.setAttribute("project_path", getProgetUtil(request));
		setIsReplaceWord(request, user); // 设置是否开启用*替换电话号码中间四位
		return "incall/callList";
	}

	@RequestMapping(value = "queryReviewList")
	public String queryReviewList(HttpServletRequest request, TsmCustReview tsmCustReview) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String custId = request.getParameter("custId") == null ? "" : request.getParameter("custId");
		List<TsmCustReview> list = null;
		String reqId = SysBaseModelUtil.getModelId();
		try {
			logger.info("queryReviewList reqId=" + reqId + "。custId=" + custId + "。tsmCustReview=" + JsonUtil.getJsonString(tsmCustReview) + "|account="
					+ user.getAccount());
			tsmCustReview.setOrgId(user.getOrgId());
			// tsmCustReview.setOwnerAcc(user.getAccount());
			tsmCustReview.setCustId(custId);
			list = tsmCustReviewService.getCustReviewListPage(tsmCustReview);
			if (list != null && list.size() > 0) {
				Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
				for (TsmCustReview revie : list) {
					revie.setReviewAcc(nameMap.get(revie.getReviewAcc()) == null ? revie.getReviewAcc() : nameMap.get(revie.getReviewAcc()));
				}
			}
		} catch (Exception e) {
			logger.error("reqId=" + reqId, e);
			throw new SysRunException(e);
		}
		request.setAttribute("list", list);
		request.setAttribute("custId", custId);
		request.setAttribute("tsmCustReview", tsmCustReview);
		return "incall/reviewList";
	}

	@RequestMapping("queryContractList")
	public String queryContractList(HttpServletRequest request, ContractDto contractDto) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String custId = request.getParameter("custId") == null ? "" : request.getParameter("custId");
		List<ContractDto> contractDtos = null;
		String reqId = SysBaseModelUtil.getModelId();
		try {
			logger.info("queryContractList reqId=" + reqId + "。custId=" + custId + "。contractDto=" + JsonUtil.getJsonString(contractDto) + "|account="
					+ user.getAccount());
			contractDto.setOrgId(user.getOrgId());
			contractDto.setCustId(custId);
			// contractDto.setOwnerAcc(user.getAccount());
			contractDtos = contractService.getUnitContractListPage(contractDto);
		} catch (Exception e) {
			logger.error("reqId=" + reqId, e);
			throw new SysRunException(e);
		}
		request.setAttribute("list", contractDtos);
		request.setAttribute("contractDto", contractDto);
		request.setAttribute("shiroUser", ShiroUtil.getShiroUser());
		request.setAttribute("custId", custId);
		return "incall/contractList";
	}

	@RequestMapping(value = "queryOrderList")
	public String queryOrderList(HttpServletRequest request, ContractOrderDetailDto orderDetailDto) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String custId = request.getParameter("custId") == null ? "" : request.getParameter("custId");
		List<ContractOrderDetailDto> orderDetailDtos = null;
		String reqId = SysBaseModelUtil.getModelId();
		try {
			orderDetailDto.setOrgId(user.getOrgId());
			orderDetailDto.setCustId(custId);
			orderDetailDto.setIsTP("1");
			logger.info("queryOrderList reqId=" + reqId + "。custId=" + custId + "。orderDetailDto=" + JsonUtil.getJsonString(orderDetailDto) + "|account="
					+ user.getAccount());
			orderDetailDtos = contractService.getContractOrderDetailListPage(orderDetailDto);
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			for (ContractOrderDetailDto cod : orderDetailDtos) {
				cod.setUserName(StringUtils.isNotBlank(cod.getUserId()) ? nameMap.get(cod.getUserId()) : "");
			}
		} catch (Exception e) {
			logger.error("reqId=" + reqId, e);
			throw new SysRunException(e);
		}
		request.setAttribute("list", orderDetailDtos);
		request.setAttribute("contractOrderDto", orderDetailDto);
		return "incall/order";
	}

	@RequestMapping("queryServiceList")
	public String queryServiceList(HttpServletRequest request, ServiceVisitDto serviceVisitDto) {
		List<ServiceVisit> serviceVisitList = null;
		ShiroUser user = ShiroUtil.getShiroUser();
		String custId = request.getParameter("custId") == null ? "" : request.getParameter("custId");
		try {
			serviceVisitDto.setCustId(custId);
			serviceVisitDto.setOrgId(user.getOrgId());
			serviceVisitList = serviceVisitService.queryCustVisitListPage(serviceVisitDto);
			request.setAttribute("list", serviceVisitList);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		request.setAttribute("serviceVisitDto", serviceVisitDto);
		return "incall/serviceList";
	}

	@ResponseBody
	@RequestMapping("giveUp")
	public String giveUp(HttpServletRequest request, String custId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			resCustInfoService.giveUp(user, Arrays.asList(custId + "_2"), new Short("12"), "","");//
		} catch (Exception e) {
			logger.error("giveup 异常 ，loginAcc= " + user.getAccount() + "id=" + custId, e);
			throw new SysRunException(e);
		}
		return "1";
	}

}
