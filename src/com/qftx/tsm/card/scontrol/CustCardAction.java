package com.qftx.tsm.card.scontrol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.SysRunException;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.common.util.constants.SysConstant;
import com.qftx.log.query.bean.LogCustInfoQueryDto;
import com.qftx.log.tablestore.bean.TsGetRangePage;
import com.qftx.log.tablestore.bean.TsGetRangePageResp;
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
import com.qftx.tsm.card.dto.LogReturnDataDto;
import com.qftx.tsm.contract.bean.ContractBean;
import com.qftx.tsm.contract.dto.ContractOrderDto;
import com.qftx.tsm.contract.service.ContractService;
import com.qftx.tsm.cust.bean.ResCustEventBean;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResCustInfoDetailBean;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.bean.TsmCustGuide;
import com.qftx.tsm.cust.dto.ResCustActionDto;
import com.qftx.tsm.cust.service.ComResourceGroupService;
import com.qftx.tsm.cust.service.ResCustEventService;
import com.qftx.tsm.cust.service.ResCustInfoDetailService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.cust.service.TsmCustGuideProcService;
import com.qftx.tsm.cust.service.TsmCustGuideService;
import com.qftx.tsm.cust.service.WxBindService;
import com.qftx.tsm.follow.bean.CustSaleChanceBean;
import com.qftx.tsm.follow.dto.CustFollowDto;
import com.qftx.tsm.follow.service.CustFollowService;
import com.qftx.tsm.follow.service.CustSaleChanceService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.Product;
import com.qftx.tsm.sys.bean.TsmCustReview;
import com.qftx.tsm.sys.service.TsmCustReviewService;

@Controller
@RequestMapping("/cust/card")
public class CustCardAction extends BaseAction {
	private Logger logger = Logger.getLogger(CustCardAction.class);
	@Autowired
	private CachedService cachedService;
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private ResCustEventService resCustEventService;
	@Autowired
	private ResCustInfoDetailService resCustInfoDetailService;
	@Autowired
	private ComResourceGroupService comReesourceGroupService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private CustFollowService custFollowService;
	@Autowired
	private TsmCustReviewService custReviewService;
	@Autowired
	private CallRecordInfoService callRecordInfoService;
	@Autowired
	private LogCustInfoService logCustInfoService;
	@Autowired
	private ContractService contractService;
	@Autowired 
	private OrgGroupUserService orgGroupUserService;
	@Autowired 
	private OrgGroupService orgGroupService;
	@Autowired
	private CustSaleChanceService custSaleChanceService;
	@Resource
	private TsmCustGuideService tsmCustGuideService;
	@Resource
	private TsmCustGuideProcService tsmCustGuideProcService;
	@Resource
	private WxBindService wxBindService;
//	private String write_log_to_db = ConfigInfoUtils.getStringValue("write_log_to_db");
//	private String read_log_source = ConfigInfoUtils.getStringValue("read_log_source");
	/** 
	 *
	 * @param request
	 * @return 
	 * @create  2015年11月27日 下午2:07:56 lixing
	 * @history  
	 */
	@RequestMapping("/custInfoCard")
	public String custInfoCard(HttpServletRequest request){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String custId = request.getParameter("custId");
			String type = request.getParameter("type");
			type = StringUtils.isNotBlank(type) ? type : "1";
			String orgId = user.getOrgId();
			int opType = user.getIsState();
			String pname = "";
			String cname = "";
			String oname = "";
			//设置是否模糊处理电话号码
			setIsReplaceWord(request);
			ResCustInfoBean custInfoBean = resCustInfoService.getCustInfoByOrgIdAndPk(orgId,custId);
			//资源客户状态  资源客户状态：1-未分配，2-未跟进，3-跟进中，4-公海客户，5-资源回收站，6-已签约，7-沉默客户，8-流失客户
			int custStatus = custInfoBean.getStatus().intValue();
			//类型:1-资源，2客户,3-再淘资源
			int custType = custInfoBean.getType().intValue();
			//我的资源
			Date ownerStartDate = custInfoBean.getOwnerStartDate();
			if((custStatus == 2 || custStatus == 3) && (custType == 1 || custType == 3)){
				request.setAttribute("custType", "1");
				int cycleTime = DateUtil.dateSub(new Date(),ownerStartDate);
				request.setAttribute("cycleTime", cycleTime);
			}else if((custStatus == 2 || custStatus == 3) && custType == 2){//意向客户
				request.setAttribute("custType", "2");
				int cycleTime = DateUtil.dateSub(new Date(),ownerStartDate);
				request.setAttribute("cycleTime", cycleTime);
			}else if(custStatus == 6 && custType == 2){//签约客户
				request.setAttribute("custType", "3");
				int cycleTime = DateUtil.dateSub(custInfoBean.getSignDate() == null ? new Date() : custInfoBean.getSignDate(),ownerStartDate );
				request.setAttribute("cycleTime", cycleTime);
			}else if(custStatus == 7 && custType == 2){//沉默客户
				request.setAttribute("custType", "4");
			}else if(custStatus == 8 && custType == 2){//流失客户
				request.setAttribute("custType", "5");
			}else if(custStatus == 4 || custStatus == 5){//公海
				request.setAttribute("custType", "6");
			}
			List<CustFieldSet> fieldSets = null;
			if(custInfoBean.getState() != null && custInfoBean.getState() == 1){//企业资源
				List<ResCustInfoDetailBean> details = resCustInfoDetailService.getCustsInfoDetails(user.getOrgId(), custInfoBean.getResCustId());
				fieldSets = cachedService.getComFiledSets(user.getOrgId());
				request.setAttribute("details", details);
			}else{
				fieldSets = cachedService.getPersonFiledSets(user.getOrgId());
			}
			
			// 从缓存读取销售进程列表
            List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
            Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
            if(StringUtils.isNotBlank(custInfoBean.getLastOptionId())){
            	request.setAttribute("lastOptionName", saleProcMap.get(custInfoBean.getLastOptionId()));
            }
			//分组
			List<ResourceGroupBean> groupList = comReesourceGroupService.queryResGroup(user.getOrgId());
			Integer pid = custInfoBean.getProvinceId();
			//省市区
			if(pid != null){
				ChinaProvinceBean cpb = areaService.getChinaProvinceByPid(pid);
				pname = cpb.getPname();
				request.setAttribute("pname", pname);
				Integer cid = custInfoBean.getCityId();
				if(cid != null){
					ChinaCityBean ccb = areaService.getChinaCityByCid(cid);
					cname = ccb.getCname();
					request.setAttribute("cname", cname);
					Integer oid = custInfoBean.getCountyId();
					if(oid != null){
						ChinaCountyBean cob = areaService.getChinaCountyByOid(oid);
						oname = cob.getOname();
						request.setAttribute("oname", oname);
					}
				}
			}
			
			if(custInfoBean.getState() == 1){
				List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, orgId);
				request.setAttribute("trades", trades);
			}
			
			custInfoBean = resCustInfoService.getCustData(fieldSets, pname, cname, oname, custInfoBean);
			request.setAttribute("groupList", groupList);
			request.setAttribute("custInfo",custInfoBean);
			request.setAttribute("fieldSets", fieldSets);
			request.setAttribute("type", type);
			request.setAttribute("shiroUser", user);
			commonOwnerAuth(request);
			setGroupType(user, request);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "card/cust_info_card";
	}
	
	public void setGroupType(ShiroUser user,HttpServletRequest request){
		if(ShiroUtil.getSession(SysConstant.USER_GROUP_TYPE) == null){
			OrgGroupUser usermeber = new OrgGroupUser();
            usermeber.setOrgId(user.getOrgId());
            usermeber.setUserId(user.getId());
            OrgGroupUser newmember = orgGroupUserService.getByCondtion(usermeber);
            logger.debug("是否有组织结构=" + JSON.toJSONString(newmember));
            OrgGroup orgGroup = orgGroupService.getByGroupId(user.getOrgId(),newmember.getGroupId());
            // 设置用户类型
            ShiroUtil.setSession(SysConstant.USER_GROUP_TYPE, orgGroup.getGroupType());
            request.setAttribute("userGroupType", ShiroUtil.getSession(SysConstant.USER_GROUP_TYPE));
		}else{
			request.setAttribute("userGroupType", ShiroUtil.getSession(SysConstant.USER_GROUP_TYPE));
		}
	}
	
	@RequestMapping("/custFollowPage")
	public String custFollowPage(HttpServletRequest request){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String custId = request.getParameter("custId"); // 资源Id
			String dayPlan = request.getParameter("dayPlan");
			request.setAttribute("dayPlan", dayPlan);
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
//			List<OptionBean> label6List = new ArrayList<OptionBean>(); // 默认显示6个
//			List<OptionBean> otberLabelList = new ArrayList<OptionBean>(); // 其余隐藏
//			if (labelList != null && labelList.size() > 0) {
//				for (int i = 0; i < labelList.size(); i++) {
//					if (i < 6) {
//						label6List.add(labelList.get(i));
//					} else {
//						otberLabelList.add(labelList.get(i));
//					}
//				}
//			}
			request.setAttribute("saleProcessList", saleProcessList); // 销售进程
			request.setAttribute("custTypeList", custTypeList); // 客户类型
			request.setAttribute("suitProcList", suitProcList); // 适用产品
//			request.setAttribute("label6List", label6List);
//			request.setAttribute("otberLabelList", otberLabelList);
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
			getSignSetting(request);
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
	
	@RequestMapping("/getEventsNum")
	@ResponseBody
	public Map<String, Integer> getEventsNum(HttpServletRequest request,HttpServletResponse response,String custId){
		Map<String, Integer> numMap = new HashMap<String, Integer>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			ResCustEventBean rceBean = new ResCustEventBean();
			rceBean.setCustId(custId);
			rceBean.setOrgId(user.getOrgId());
			List<Map<String, Object>> numMaps = resCustEventService.getEventsNums(rceBean);
			for(Map<String, Object> map : numMaps){
				numMap.put(map.get("type").toString(), Integer.parseInt(map.get("num").toString()));
			}
			ConditionDto callBean = new ConditionDto();
			callBean.setCustIds(Arrays.asList(new String[]{custId}));
			callBean.setOrgId(user.getOrgId());
			CallRecordListDto callDto = callRecordInfoService.queryCallRecord(callBean);
			numMap.put("3", callDto.getCondition().getPage().getTotalResult());
			Integer contractNum = contractService.getCustContractNum(user.getOrgId(), custId);
			numMap.put("5", contractNum);
			Integer contractOrderNum = contractService.getCustContractOrderNum(user.getOrgId(), custId);
			numMap.put("6", contractOrderNum);
			
			LogCustInfoQueryDto queryDto = new LogCustInfoQueryDto();
			queryDto.setCustId(custId);
			queryDto.setOrgId(user.getOrgId());
			queryDto.setPageSize(1);
			queryDto.setPageNo(1);
			TsGetRangePageResp resp = LogUserOperateService.getLogTableStoreApi().searchLogCustInfoByPage(queryDto);
			TsGetRangePage tsGetRangePage = (TsGetRangePage)resp.getPage();
			numMap.put("7", tsGetRangePage.getTotalResult());
			
			CustSaleChanceBean custSaleChanceBean = new CustSaleChanceBean();
			custSaleChanceBean.setCustId(custId);
			custSaleChanceBean.setOrgId(user.getOrgId());
			custSaleChanceBean.setIsDel(0);
			custSaleChanceService.findListPage(custSaleChanceBean);
			numMap.put("8", custSaleChanceBean.getPage().getTotalResult());
			
		} catch (Exception e) {
			logger.error("客户卡片获取事件数量异常",e);
		}
		return numMap;
	}
	
	public void getSaleProcess(HttpServletRequest request,String custId,String lifeCode,ShiroUser user){
		//销售进程
		ResCustEventBean rceb = new ResCustEventBean();
		rceb.setCustId(custId);
		rceb.setOrgId(user.getOrgId());
		rceb.setIsDel(0);
		rceb.setType("1");
		rceb.setState("2");
		rceb.setLifeCode(lifeCode);
		rceb.setOrderKey("inputtime asc");
		List<ResCustEventBean> eventBeans = resCustEventService.getByCondtion(rceb);
		String saleProcs="";
		Map<String, Object> map = new HashMap<String, Object>();
		for(ResCustEventBean bean : eventBeans){
			Map<String, Object> dataMap = com.alibaba.fastjson.JSONObject.parseObject(bean.getData(),Map.class);
			if(dataMap.get("saleProcessName") != null){
				String saleProcessName = dataMap.get("saleProcessName").toString();
				if(map.get(saleProcessName) == null){
					saleProcs+=saleProcessName+"#";
					map.put(saleProcessName, saleProcessName);
				}
			}
		}
		if(StringUtils.isNotBlank(saleProcs)){saleProcs=saleProcs.substring(0,saleProcs.length()-1);};
		request.setAttribute("saleProcs", saleProcs);
	}
	
	/** 
	 * 客户卡片  右侧时间轴
	 * @param request
	 * @param custId 客户ID
	 * @param type 操作类型(1行动记录、2服务记录、3通话记录、4评论记录、5合同记录、6订单记录)
	 * @return 
	 * @create  2015年11月30日 上午10:30:53 lixing
	 * @history  
	 */
	@RequestMapping("/events")
	public String events(HttpServletRequest request,String custId,String type,String state,String userId,String lifeCode,String custType,String custStatus,String custName,String ownerAcc,String cycleTime,String commonAcc){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("custId", custId);
			request.setAttribute("type", type);
			request.setAttribute("state", state);
			request.setAttribute("userId", userId);
			request.setAttribute("lifeCode", lifeCode);
			Map<String, String> useAbelMap = new HashMap<String, String>();
			request.setAttribute("useAbelMap", useAbelMap);
			request.setAttribute("shiroUser", user);
			request.setAttribute("project_path", getProgetUtil(request));
			request.setAttribute("custType", custType);
			request.setAttribute("custStatus", custStatus);
			request.setAttribute("custName", custName);
			request.setAttribute("ownerAcc", ownerAcc);
			request.setAttribute("cycleTime", cycleTime);
			request.setAttribute("commonAcc", commonAcc);
			setGroupType(user, request);
			if(StringUtils.isNotBlank(commonAcc)){
				int isOpen = getCommonOwnerOpenState(user.getOrgId());
				if(isOpen == 1){
					request.setAttribute("commonOnwerGiveUp", getCommonOwnerGiveUpAuth(user.getOrgId()));
					request.setAttribute("commonOnwerSign", getCommonOwnerSignAuth(user.getOrgId()));
				}
			}
			if(type.equals("1")){
				ResCustInfoBean custInfoBean = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(),custId);
				// 从缓存读取销售进程列表
	            List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
	            Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
	            if(StringUtils.isNotBlank(custInfoBean.getLastOptionId())){
	            	request.setAttribute("lastOptionName", saleProcMap.get(custInfoBean.getLastOptionId()));
	            	request.setAttribute("lastOptionId", custInfoBean.getLastOptionId());
	            }
			}
			getSaleProcess(request, custId, lifeCode, user);
			setAdminSignAuth(request);
			// 录音地址
	        request.setAttribute("playUrl", ConfigInfoUtils.getStringValue("call_play_url"));
			setIsReplaceWord(request);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "card/events";
	}
	
	
	@RequestMapping("/data")
	@ResponseBody
	public String data(HttpServletRequest request,HttpServletResponse response,@RequestBody ResCustEventBean rceb){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			rceb.setOrgId(user.getOrgId());
			rceb.setIsDel(0);
			rceb.setOrderKey("inputtime desc");
			List<ResCustEventBean> eventBeans = resCustEventService.getListPage(rceb);
			Map<String,Object> reMap = new HashMap<String, Object>();
			List<Map<String, List<Object>>> alist = new ArrayList<Map<String,List<Object>>>();
			for(int i=0;i<eventBeans.size();i++){
				ResCustEventBean rb = eventBeans.get(i);
				Map<String, List<Object>> maps;
				List<Object> obj;
				String dateKey = DateUtil.format(rb.getInputtime(), DateUtil.defaultPattern);
				if(i == 0){
					maps = new HashMap<String, List<Object>>();
					obj = new ArrayList<Object>();
					Map dataMap = (Map) JSONObject.toBean(JSONObject.fromObject(rb.getData()), Map.class);
					if(rceb.getType().equals("6")){
						String inputtime = dataMap.get("inputtime").toString();
						dataMap.put("inputtime", DateUtil.format(DateUtil.parse(inputtime)));
					}
					obj.add(dataMap);
					maps.put(dateKey, obj);
					alist.add(maps);
				}else{
					if(alist.get(alist.size()-1).containsKey(dateKey)){
						maps = alist.get(alist.size()-1);
						obj = maps.get(DateUtil.format(rb.getInputtime(), dateKey));
						Map dataMap = (Map) JSONObject.toBean(JSONObject.fromObject(rb.getData()), Map.class);
						if(rceb.getType().equals("6")){
							String inputtime = dataMap.get("inputtime").toString();
							dataMap.put("inputtime", DateUtil.format(DateUtil.parse(inputtime)));
						}
						obj.add(dataMap);
						maps.put(dateKey, obj);
					}else{
						maps = new HashMap<String, List<Object>>();
						obj = new ArrayList<Object>();
						Map dataMap = (Map) JSONObject.toBean(JSONObject.fromObject(rb.getData()), Map.class);
						if(rceb.getType().equals("6")){
							String inputtime = dataMap.get("inputtime").toString();
							dataMap.put("inputtime", DateUtil.format(DateUtil.parse(inputtime)));
						}
						obj.add(dataMap);
						maps.put(dateKey, obj);
						alist.add(maps);
					}
				}
			}
			
			reMap.put("datas", alist);
			reMap.put("page", rceb.getPage());
			reMap.put("history",0);
			return JSONObject.fromObject(reMap).toString();
		} catch (Exception e) {
			logger.error("【客户卡片】获取右侧时间轴数据失败!参数:"+JSONObject.fromObject(rceb).toString());
			return "-1";
		}
	}
	
	@RequestMapping("/actionData")
	@ResponseBody
	public String actionData(HttpServletRequest request,HttpServletResponse response,@RequestBody ResCustActionDto dto){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			dto.setOrgId(user.getOrgId());
			List<ResCustActionDto> actionDtos = resCustEventService.getResCustActionsListPage(dto);
			Map<String,Object> reMap = new HashMap<String, Object>();
			List<Map<String, List<Object>>> alist = new ArrayList<Map<String,List<Object>>>();
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
            Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
			int i = 0;
			List<String> followIds = new ArrayList<String>();
			for(ResCustActionDto actionDto : actionDtos){
				String dateKey = DateUtil.format(actionDto.getInputDate(), DateUtil.defaultPattern);
				Map<String, List<Object>> maps;
				List<Object> obj;
//				if(StringUtils.isNotBlank(actionDto.getInputAcc())){
					actionDto.setInputName(nameMap.get(actionDto.getInputAcc()));
//				}
				if(StringUtils.isNotBlank(actionDto.getSaleProcessId())){
					actionDto.setSaleProcessName(saleProcMap.get(actionDto.getSaleProcessId()));
				}
				if(StringUtils.isNotBlank(actionDto.getCustFollowId())){
					followIds.add(actionDto.getCustFollowId());
				}
				if(i==0){
					maps = new HashMap<String, List<Object>>();
					obj = new ArrayList<Object>();
					obj.add(actionDto);
					maps.put(dateKey, obj);
					alist.add(maps);
				}else{
					if(alist.get(alist.size()-1).containsKey(dateKey)){
						maps = alist.get(alist.size()-1);
						obj = maps.get(dateKey);
						obj.add(actionDto);
						maps.put(dateKey, obj);
					}else{
						maps = new HashMap<String, List<Object>>();
						obj = new ArrayList<Object>();
						obj.add(actionDto);
						maps.put(dateKey, obj);
						alist.add(maps);
					}
				}
				i++;
			}
			if(followIds.size() > 0){
				/** 参数 */
				FollowCallQueryDto fcqd = new FollowCallQueryDto();
				fcqd.setOrgId(user.getOrgId());
				fcqd.setFollowIds(followIds);
				List<TsmRecordCallBean> beans = CallRecordGetUtil.getRecordeCallFollowList(fcqd);
				recordAssemble(beans,alist);
			}
			reMap.put("datas", alist);
			reMap.put("page", dto.getPage());
			reMap.put("history",0);
			return JsonUtil.getJsonString(reMap);
		} catch (Exception e) {
			logger.error("【客户卡片】获取行动记录数据失败!参数:"+JSONObject.fromObject(dto).toString());
			return "-1";
		}
	}
	
	public void recordAssemble(List<TsmRecordCallBean> beans,List<Map<String, List<Object>>> alist){
		Map<String, List<TsmRecordCallBean>> map = new HashMap<String, List<TsmRecordCallBean>>();
		if(beans != null && beans.size() > 0){
			for(TsmRecordCallBean bean : beans){
				List<TsmRecordCallBean> list;
				if(map.containsKey(bean.getFollowId())){
					list=map.get(bean.getFollowId());
					list.add(bean);
					map.put(bean.getFollowId(), list);
				}else{
					list = new ArrayList<TsmRecordCallBean>();
					list.add(bean);
					map.put(bean.getFollowId(), list);
				}
			}
			
			for(Map<String, List<Object>> lMap : alist){
				for(String key : lMap.keySet()){
					List<Object> objs = lMap.get(key);
					for(Object obj : objs){
						ResCustActionDto objMap = (ResCustActionDto)obj;
						if(StringUtils.isNotBlank(objMap.getCustFollowId())){
							String followId = objMap.getCustFollowId();
							if(map.get(followId) != null){
								objMap.setRecords(map.get(followId));
							}
						}
					}
				}
			}
		}
	}
	
	
	
	@RequestMapping("/callRecord")
	@ResponseBody
	public Map<String,Object> callRecord(HttpServletRequest request,HttpServletResponse response,@RequestBody TsmRecordCallBean bean){
		Map<String,Object> reMap = new HashMap<String, Object>();
		try {
			ConditionDto dtoBean = new ConditionDto();
			dtoBean.setCustIds(Arrays.asList(bean.getCustId().split(",")));
			dtoBean.getPage().setCurrentPage(bean.getPage().getCurrentPage());
			dtoBean.getPage().setTotalPage(bean.getPage().getTotalPage());
			dtoBean.getPage().setTotalResult(bean.getPage().getTotalResult());
			dtoBean.getPage().setShowCount(bean.getPage().getShowCount());
			ShiroUser user = ShiroUtil.getShiroUser();
			dtoBean.setOrgId(user.getOrgId());    
			CallRecordListDto callDto = callRecordInfoService.queryCallRecord(dtoBean);
	        Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
	        List<Map<String, List<Object>>> alist = new ArrayList<Map<String,List<Object>>>();
	        int i = 0;
	        for(TsmRecordCallBean cb : callDto.getBeans()){
	        	String dateKey = DateUtil.format(cb.getStartTime(), DateUtil.defaultPattern);
	        	Map<String, List<Object>> maps;
				List<Object> obj;
				cb.setInputName(StringUtils.isNotBlank(cb.getInputAcc()) ? nameMap.get(cb.getInputAcc()) : "");
				if(i==0){
	        		maps = new HashMap<String, List<Object>>();
					obj = new ArrayList<Object>();
					obj.add(cb);
					maps.put(dateKey, obj);
					alist.add(maps);
	        	}else{
	        		if(alist.get(alist.size()-1).containsKey(dateKey)){
	        			maps = alist.get(alist.size()-1);
						obj = maps.get(dateKey);
						obj.add(cb);
						maps.put(dateKey, obj);
	        		}else{
	        			maps = new HashMap<String, List<Object>>();
						obj = new ArrayList<Object>();
						obj.add(cb);
						maps.put(dateKey, obj);
						alist.add(maps);
	        		}
	        	}
	        	i++;
	        }
	        reMap.put("datas", alist);
	        reMap.put("page", callDto.getCondition().getPage());
		} catch (Exception e) {
			logger.error("【客户卡片】获取右侧时间轴通话记录失败!参数:"+JSONObject.fromObject(bean).toString());
		}
		return reMap;
	}
	
	@RequestMapping("/logData")
	@ResponseBody
	public Map<String, Object> logData(HttpServletRequest request,HttpServletResponse response,@RequestBody TsGetRangePage tsGetRangePage){
		Map<String,Object> reMap = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<LogReturnDataDto> custInfoBeans = new ArrayList<LogReturnDataDto>();
			if(tsGetRangePage.getShowCount() == null){
				LogCustInfoQueryDto queryDto = new LogCustInfoQueryDto();
				String custId = request.getParameter("custId"); 
				queryDto.setPageSize(10);
				queryDto.setPageNo(1);
				queryDto.setCustId(custId);
				queryDto.setOrgId(user.getOrgId());
				System.out.println(JsonUtil.getJsonString(queryDto));
				TsGetRangePageResp resp = LogUserOperateService.getLogTableStoreApi().searchLogCustInfoByPage(queryDto);
				tsGetRangePage = (TsGetRangePage)resp.getPage();
				List<Map<String, Object>> list = resp.getBeans();
				if(list.size() > 0){
					String json_str = JsonUtil.getJsonString(list);
					custInfoBeans = JSONArray.parseArray(json_str, LogReturnDataDto.class);
				}
			}else{
				TsGetRangePageResp resp = LogUserOperateService.getLogTableStoreApi().logChangePage(tsGetRangePage);
				tsGetRangePage = (TsGetRangePage)resp.getPage();
				List<Map<String, Object>> list = resp.getBeans();
				if(list.size() > 0){
					String json_str = JsonUtil.getJsonString(list);
					custInfoBeans = JsonUtil.getListJson(json_str, LogReturnDataDto.class);
				}
			}
			
			
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
			List<Map<String, List<Object>>> alist = new ArrayList<Map<String,List<Object>>>();
			int i = 0;
			Calendar cal = Calendar.getInstance();
			for(LogReturnDataDto bean : custInfoBeans){
				cal.setTimeInMillis(bean.getInputTime());
				bean.setInputDate(cal.getTime());
				String dateKey = DateUtil.format(bean.getInputDate(), DateUtil.defaultPattern);
				Map<String, List<Object>> maps;
				List<Object> obj;
//				bean.setUserName(StringUtils.isBlank(nameMap.get(bean.getUserAcc())) ? bean.getUserAcc() : nameMap.get(bean.getUserAcc()));
				if(StringUtils.isBlank(nameMap.get(bean.getUserAcc()))){
					if("system".equals(bean.getUserAcc())){
						bean.setUserName("系统");
					}else{
						bean.setUserName(bean.getUserAcc());
					}
				}else{
					bean.setUserName(nameMap.get(bean.getUserAcc()));
				}
				readContext(bean, nameMap, groupMap);
				if(i==0){
					maps = new HashMap<String, List<Object>>();
					obj = new ArrayList<Object>();
					obj.add(bean);
					maps.put(dateKey, obj);
					alist.add(maps);
				}else{
	        		if(alist.get(alist.size()-1).containsKey(dateKey)){
	        			maps = alist.get(alist.size()-1);
						obj = maps.get(dateKey);
						obj.add(bean);
						maps.put(dateKey, obj);
	        		}else{
	        			maps = new HashMap<String, List<Object>>();
						obj = new ArrayList<Object>();
						obj.add(bean);
						maps.put(dateKey, obj);
						alist.add(maps);
	        		}
	        	}
	        	i++;
			}
			reMap.put("datas", alist);
			reMap.put("page", tsGetRangePage);
		} catch (Exception e) {
			logger.error("【客户卡片】获取操作日志记录失败!");
		}
		return reMap;
	}
	
	public void readContext(LogReturnDataDto bean,Map<String, String> nameMap,Map<String, String> groupMap){
		if(StringUtils.isNotBlank(bean.getContext()) && StringUtils.isNotBlank(bean.getResOperateId())){
			if(bean.getResOperateId().equals(OperateEnum.LOG_DIST.getCode()) || bean.getResOperateId().equals(OperateEnum.LOG_RES_ASSIGN.getCode())){
				bean.setContext("分配后所有者："+nameMap.get(bean.getContext()));
			}else if(bean.getResOperateId().equals(OperateEnum.LOG_TRANSFER.getCode()) || bean.getResOperateId().equals(OperateEnum.LOG_SALETRANSFER.getCode())){
				Map<String, String> map = (Map<String, String>)JSONObject.toBean(JSONObject.fromObject(bean.getContext()),Map.class);
				bean.setContext("移交人："+nameMap.get(map.get("transferedAcc"))+"；接收人："+nameMap.get(map.get("transferAcc")));
			}else if(bean.getResOperateId().equals(OperateEnum.LOG_SET_COMMON_ACC.getCode())){
				bean.setContext("共有人："+nameMap.get(bean.getContext()));
			}
		}
	}
	
	/** 
	 * 客户卡片 合同记录
	 * @param request
	 * @param response
	 * @param contractBean
	 * @return 
	 * @create  2016年8月15日 下午4:47:40 lixing
	 * @history  
	 */
	@RequestMapping("/contractData")
	@ResponseBody
	public Map<String, Object> contractData(HttpServletRequest request,HttpServletResponse response,@RequestBody ContractBean contractBean){
		Map<String,Object> reMap = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			contractBean.setOrgId(user.getOrgId());
			contractBean.setIsDel(0);
			contractBean.setOrderKey("sign_date desc");
			List<ContractBean> contractBeans = contractService.getContractListPage(contractBean);
			List<Map<String, List<Object>>> alist = new ArrayList<Map<String,List<Object>>>();
			int i = 0;
			for(ContractBean bean : contractBeans){
				String dateKey = DateUtil.format(bean.getSignDate(), DateUtil.defaultPattern);
				Map<String, List<Object>> maps;
				List<Object> obj;
				if(i==0){
					maps = new HashMap<String, List<Object>>();
					obj = new ArrayList<Object>();
					obj.add(bean);
					maps.put(dateKey, obj);
					alist.add(maps);
				}else{
	        		if(alist.get(alist.size()-1).containsKey(dateKey)){
	        			maps = alist.get(alist.size()-1);
						obj = maps.get(dateKey);
						obj.add(bean);
						maps.put(dateKey, obj);
	        		}else{
	        			maps = new HashMap<String, List<Object>>();
						obj = new ArrayList<Object>();
						obj.add(bean);
						maps.put(dateKey, obj);
						alist.add(maps);
	        		}
	        	}
	        	i++;
			}
			reMap.put("datas", alist);
			reMap.put("page", contractBean.getPage());
		} catch (Exception e) {
			logger.error("【客户卡片】合同信息获取失败!参数:"+JSONObject.fromObject(contractBean).toString());
		}
		return reMap;
	}
	
	@RequestMapping("/orderData")
	@ResponseBody
	public String orderData(HttpServletRequest request,HttpServletResponse response,@RequestBody ContractOrderDto contractOrderBean){
		Map<String,Object> reMap = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			contractOrderBean.setOrgId(user.getOrgId());
			contractOrderBean.setAuthState(2);
			contractOrderBean.setIsDel(0);
			contractOrderBean.setOrderKey("inputtime desc");
			List<ContractOrderDto> contractOrderBeans = contractService.getContractOrderListPage(contractOrderBean);
			List<Map<String, List<Object>>> alist = new ArrayList<Map<String,List<Object>>>();
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			int i = 0;
			for(ContractOrderDto bean : contractOrderBeans){
				String dateKey = DateUtil.format(bean.getInputtime(), DateUtil.defaultPattern);
				bean.setUserId(nameMap.get(bean.getUserId()));
				Map<String, List<Object>> maps;
				List<Object> obj;
				if(i==0){
					maps = new HashMap<String, List<Object>>();
					obj = new ArrayList<Object>();
					obj.add(bean);
					maps.put(dateKey, obj);
					alist.add(maps);
				}else{
	        		if(alist.get(alist.size()-1).containsKey(dateKey)){
	        			maps = alist.get(alist.size()-1);
						obj = maps.get(dateKey);
						obj.add(bean);
						maps.put(dateKey, obj);
	        		}else{
	        			maps = new HashMap<String, List<Object>>();
						obj = new ArrayList<Object>();
						obj.add(bean);
						maps.put(dateKey, obj);
						alist.add(maps);
	        		}
	        	}
	        	i++;
			}
			reMap.put("datas", alist);
			reMap.put("page", contractOrderBean.getPage());
		} catch (Exception e) {
			logger.error("【客户卡片】订单信息获取失败!参数:"+JSONObject.fromObject(contractOrderBean).toString());
		}
		return JsonUtil.getJsonString(reMap,com.qftx.base.util.DateUtil.DATE_DAY);
	}
	
	
	/**销售机会**/
	@RequestMapping("/saleChanceData")
	@ResponseBody
	public String saleChanceData(@RequestBody CustSaleChanceBean custSaleChanceBean){
		Map<String,Object> reMap = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			custSaleChanceBean.setOrgId(user.getOrgId());
			custSaleChanceBean.setIsDel(0);
			custSaleChanceBean.setOrderKey("input_date desc");
			List<CustSaleChanceBean> list = custSaleChanceService.findListPage(custSaleChanceBean);
			List<Map<String, List<Object>>> alist = new ArrayList<Map<String,List<Object>>>();
			int i = 0;
			for(CustSaleChanceBean bean : list){
				String dateKey = DateUtil.format(bean.getInputDate(), DateUtil.defaultPattern);
				Map<String, List<Object>> maps;
				List<Object> obj;
				if(i==0){
					maps = new HashMap<String, List<Object>>();
					obj = new ArrayList<Object>();
					obj.add(bean);
					maps.put(dateKey, obj);
					alist.add(maps);
				}else{
					if(alist.get(alist.size()-1).containsKey(dateKey)){
	        			maps = alist.get(alist.size()-1);
						obj = maps.get(dateKey);
						obj.add(bean);
						maps.put(dateKey, obj);
	        		}else{
	        			maps = new HashMap<String, List<Object>>();
						obj = new ArrayList<Object>();
						obj.add(bean);
						maps.put(dateKey, obj);
						alist.add(maps);
	        		}
				}
				i++;
			}
			reMap.put("datas", alist);
			reMap.put("page", custSaleChanceBean.getPage());
		} catch (Exception e) {
			logger.error("【客户卡片】销售机会获取失败!参数:"+JSONObject.fromObject(custSaleChanceBean).toString());
		}
		return JsonUtil.getJsonString(reMap,com.qftx.base.util.DateUtil.DATE_DAY);
	}
	
	@RequestMapping("/toReview")
	public String toReview(HttpServletRequest request,String custId){
		request.setAttribute("custId", custId);
		return "card/review";
	}
	
	@RequestMapping("/saveReview")
	@ResponseBody
	public String saveReview(HttpServletRequest request,HttpServletResponse response,TsmCustReview custReview){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			ResCustInfoBean custInfoBean = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(),custReview.getCustId());
			custReview.setReviewId(SysBaseModelUtil.getModelId());
			custReview.setOwnerAcc(custInfoBean.getOwnerAcc());
			custReview.setOrgId(user.getOrgId());
			custReview.setCompany(custInfoBean.getCompany());
			custReview.setName(custInfoBean.getName());
			custReview.setReviewAcc(user.getAccount());
			custReview.setReviewName(user.getName());
			custReview.setReviewDate(new Date());
			custReview.setIsRead(0);
			//String optionName = custFollowService.getSalesProcessByCustId(user.getOrgId(), custReview.getCustId());
			//custReview.setSalesProcess(optionName);
			custReviewService.createWithEvent(custReview);
			return "1";
		} catch (Exception e) {
			logger.error("发表评论失败!");
			return "-1";
		}
	}
	/*微信聊天记录
	 * */
	@RequestMapping("/wxRecordJson")
	@ResponseBody
	public String saveReview(String custId,String _id){
		ShiroUser user = ShiroUtil.getShiroUser();
		//String url="http://192.168.1.75/chat/findWxRecordJson";
		List<String> ids = wxBindService.findBindId(user, custId);
		
		Map<String, String> result = new HashMap<>();
		if(ids.size() < 2){
			result.put("status", "ERROR");
			result.put("errorMsg", "请先绑定微信号码！");
		}else{
			try {
				Map<String, Object> param =new HashMap<String, Object>();
				param.put("_id", _id);
				param.put("orgId", user.getOrgId());
				param.put("account", user.getAccount());
				param.put("loginId", ids.get(0));
				param.put("custWxIds", ids.subList(1, ids.size()));
				return HttpUtil.doPostJSON(ConfigInfoUtils.getStringValue("wx_record_url"), JsonUtil.getJsonString(param));
			} catch (Exception e) {
				logger.error("wx_record_request_error!!!", e);
				result.put("status", "ERROR");
				result.put("errorMsg", "系统错误请联系管理员！");
			}
		}
		return JsonUtil.getJsonString(result);
	}
	
	public void login(String account, String password) {
		logger.debug("�����־:" + account);
		UsernamePasswordToken token = new UsernamePasswordToken(account, password.toCharArray());
		Subject user = SecurityUtils.getSubject();
		token.setRememberMe(true);
		user.login(token);
	}

	@RequestMapping("/custFollowPage2")
	public String custFollowPage2(HttpServletRequest request){
		String custId = request.getParameter("custId"); // 资源Id
		String j_username = request.getParameter("j_username"); // 资源Id
		ShiroUser user = null;
		try {
			SecurityUtils.getSubject().logout();
			user = ShiroUtil.getShiroUser();
			logger.info("�������ͻ�����" + user);
			logger.debug("shiroUser1:\n" + JSON.toJSONString(user, true));
			if (user == null) {
				login(j_username, "123456");
				user = ShiroUtil.getShiroUser();
			}
		} catch (Exception e) {
		}
		try {
//			ShiroUser user = ShiroUtil.getShiroUser();

			String dayPlan = request.getParameter("dayPlan");
			request.setAttribute("dayPlan", dayPlan);
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
//			List<OptionBean> label6List = new ArrayList<OptionBean>(); // 默认显示6个
//			List<OptionBean> otberLabelList = new ArrayList<OptionBean>(); // 其余隐藏
//			if (labelList != null && labelList.size() > 0) {
//				for (int i = 0; i < labelList.size(); i++) {
//					if (i < 6) {
//						label6List.add(labelList.get(i));
//					} else {
//						otberLabelList.add(labelList.get(i));
//					}
//				}
//			}
			request.setAttribute("saleProcessList", saleProcessList); // 销售进程
			request.setAttribute("custTypeList", custTypeList); // 客户类型
			request.setAttribute("suitProcList", suitProcList); // 适用产品
//			request.setAttribute("label6List", label6List);
//			request.setAttribute("otberLabelList", otberLabelList);
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
			getSignSetting(request);
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
	/** 
	 * 设置是否需要模糊电话号码
	 * @param request 
	 * @create  2015年11月25日 下午2:45:20 lixing
	 * @history  
	 */
//	public void setIsReplaceWord(HttpServletRequest request){
//		ShiroUser user = ShiroUtil.getShiroUser();
//		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA24,user.getOrgId());
//		Integer idReplaceWord = 0;
//		if(list.size() > 0){
//			idReplaceWord = new Integer(list.get(0).getDictionaryValue());
//		}
//		request.setAttribute("idReplaceWord", idReplaceWord);
//	}
	
}
