package com.qftx.tsm.follow.scontrol;

import com.qftx.base.auth.bean.Org;
import com.qftx.base.auth.service.OrgService;
import com.qftx.base.enums.FollowCustEnum;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.spring.RequestBean;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.MathUtil;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.common.util.constants.SysConstant;
import com.qftx.tsm.area.bean.ChinaCityBean;
import com.qftx.tsm.area.bean.ChinaCountyBean;
import com.qftx.tsm.area.bean.ChinaProvinceBean;
import com.qftx.tsm.area.service.AreaService;
import com.qftx.tsm.callrecord.dto.FollowCallQueryDto;
import com.qftx.tsm.callrecord.dto.TsmRecordCallBean;
import com.qftx.tsm.callrecord.util.CallRecordGetUtil;
import com.qftx.tsm.contract.service.ContractService;
import com.qftx.tsm.cust.bean.*;
import com.qftx.tsm.cust.dao.ComResourceMapper;
import com.qftx.tsm.cust.service.ResCustInfoDetailService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.cust.service.TsmCustGuideProcService;
import com.qftx.tsm.cust.service.TsmCustGuideService;
import com.qftx.tsm.follow.bean.CustFollowBean;
import com.qftx.tsm.follow.dto.*;
import com.qftx.tsm.follow.service.CustFollowService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.bean.OptionGroupBean;
import com.qftx.tsm.plan.user.day.service.PlanUserDayService;
import com.qftx.tsm.plan.user.day.service.PlanUserdayWillcustService;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.Product;
import com.qftx.tsm.sys.enums.SysEnum;
import com.qftx.tsm.tao.dto.OptionDto;
import com.qftx.tsm.tao.service.CustInfoService;
import com.qftx.tsm.tao.service.TaoService;

import org.apache.commons.lang3.StringUtils;
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

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.*;


/***
 * 客户跟进
 * 
 * @author: zwj
 * @since: 2015-12-4 下午2:14:09
 * @history: 4.x
 */
@Controller
@RequestMapping(value = "/cust/custFollow")
public class CustFollowControl {
	private static Logger logger = Logger.getLogger(CustFollowControl.class);
	@Autowired
	private CustFollowService custFollowService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private ResCustInfoDetailService resCustInfoDetailService;
	@Autowired
	private TsmCustGuideService tsmCustGuideService;
	@Autowired
	private TsmCustGuideProcService tsmCustGuideProcService;
	@Resource
	private CustInfoService custInfoService;
	@Resource
	private AreaService areaService;
	@Resource
	private TaoService taoCustService;
	@Resource
	private ContractService contractService;
	@Resource
	private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Resource
	private PlanUserDayService planUserDayService;
	@Resource
	private OrgService orgService;
	@Autowired
	private ComResourceMapper comResourceMapper;
	@Autowired
	private PlanUserdayWillcustService planUserdayWillcustService;
	
	/** 客户跟进详细页面 */
	@RequestMapping("/custFollowPage")
	public String custFollowPage(HttpServletRequest request,RestDto resDto) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			// 将resDto(列表中的查询条件)放入缓存，为跟进详情右侧显示内容做条件判断
			CachedUtil.getInstance().delete(user.getAccount()+"_CUSTFOLLOW_RIGHT");
			CachedUtil.getInstance().set(user.getAccount()+"_CUSTFOLLOW_RIGHT",resDto);
			
			String custId = request.getParameter("custId"); // 资源Id
			// 客户类型 跟进
			String custListType = request.getParameter("custListType"); // 1:全部，2:意向客户，3:签约客户，4:流失客户，5:沉默客户，6:客户跟进,7:共有客户
			List<DataDictionaryBean> contractOns = cachedService.getDirList(AppConstant.DATA_40039, user.getOrgId());
			boolean addContract = true;
			if(contractOns!=null && contractOns.size() > 0){
				if ("1".equals(contractOns.get(0).getDictionaryValue())) {
					addContract = false;
				}
			}
			request.setAttribute("addContract", addContract);
			boolean comPutIntoSeas = true;
			boolean comSign = true;
			if ("7".equals(custListType)) {
				List<DataDictionaryBean> date1Ons = cachedService.getDirList(AppConstant.DATA_50011, user.getOrgId());
				List<DataDictionaryBean> date2Ons = cachedService.getDirList(AppConstant.DATA_50012, user.getOrgId());
				List<DataDictionaryBean> date3Ons = cachedService.getDirList(AppConstant.DATA_50014, user.getOrgId());
				if(date1Ons!=null && date1Ons.size() > 0){
					if (date2Ons!=null && date2Ons.size() > 0) {
						if (!"1".equals(date1Ons.get(0).getDictionaryValue()) || !"1".equals(date2Ons.get(0).getDictionaryValue())) {
							comPutIntoSeas = false;
						}
					}if (date3Ons!=null && date3Ons.size() > 0) {
						if (!"1".equals(date1Ons.get(0).getDictionaryValue()) || !"1".equals(date3Ons.get(0).getDictionaryValue())) {
							comSign = false;
						}
					}
				}
			}
			request.setAttribute("comPutIntoSeas", comPutIntoSeas);
			request.setAttribute("comSign", comSign);
			String custCation = request.getParameter("custCation"); // 跟进数据分类
			String isAlarm = request.getParameter("isAlarm"); // 如果有值 表示从跟进警报 触发
																// 事件
			String startPage = request.getParameter("startPage"); // 起始行号，用于跟进警报排序
			String isSet = "0";
			Org org = orgService.getByPrimaryKey(user.getOrgId());
			if (org != null) {
				if (StringUtils.isNotBlank(org.getIsSet() + "")) {
					isSet = org.getIsSet() + "";
				}
			}
			request.setAttribute("isSet", isSet);
			request.setAttribute("startPage", startPage);
			request.setAttribute("state", user.getIsState()); // 0:个人客户，1：企业客户
			request.setAttribute("isAlarm", isAlarm);
			request.setAttribute("custListType", custListType);
			request.setAttribute("custCation", custCation);
			request.setAttribute("custId", custId);
			request.setAttribute("actionDate", com.qftx.common.util.DateUtil.getDateCurrentDate(DateUtil.Data_ALL));
			request.setAttribute("followId", SysBaseModelUtil.getModelId()); // 跟进Id
			request.setAttribute("planParam", request.getParameter("planParam")); // 1：只显示计划中客户，2：不显示计划中客户
			request.setAttribute("idReplaceWord", setIsReplaceWord(request, user));
			Map<String,Object> maps=getLableList(user.getOrgId());
			Integer isSelect=(Integer) maps.get("isSelect");
			request.setAttribute("isSelect", isSelect);
			getSignSetting(request);
			  // 录音地址
	        request.setAttribute("playUrl", ConfigInfoUtils.getStringValue("call_play_url"));
	        // 服务地址，为了提供给客户端，弹出播放列表 	
	        request.setAttribute("project_path", getProgetUtil(request));
	        getCustCacheDate(request);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/follow/custFollow";
	}

	
	/** 【日计划】客户跟进弹框详细页面 */
	@RequestMapping("/planCustFollowPage")
	public String planCustFollowPage(HttpServletRequest request) {
		try {
			String custId = request.getParameter("custId"); // 资源Id
			request.setAttribute("custId", custId);
			request.setAttribute("actionDate", com.qftx.common.util.DateUtil.getDateCurrentDate(DateUtil.Data_ALL));
			request.setAttribute("followId", SysBaseModelUtil.getModelId()); // 跟进Id			
	        getCustCacheDate(request);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/plan/dayplan/right/dayPlan_RightCust";
	}
	
	@ResponseBody
	@RequestMapping("/mainCustFollowPage")
	public String mainCustFollowPage(HttpServletRequest request) {
		CustFollowShowDto showDto = new CustFollowShowDto(); // 页面返回JSON数据存储
		try {
			ShiroUser user = ShiroUtil.getShiroUser();					
			// 取得单位下各选项列表(销售进程、客户类型、适用产品、反馈信息)
			List<OptionBean> saleProcessList = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			List<OptionBean> custTypeList = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			//List<OptionBean> labelList = cachedService.getOptionList(AppConstant.SALES_TYPE_TEN, user.getOrgId());
			List<ResourceGroupBean> resGroupList = cachedService.getResGroupList(user.getOrgId());
			Map<String,Object> maps=getLableList(user.getOrgId());
//			Integer isSelect=(Integer) maps.get("isSelect");
			List<OptionDto> opList = new ArrayList<OptionDto>();
			opList=(List<OptionDto>) maps.get("opList");
	/*		Map<String, Object> map = new HashMap<String, Object>();
			if(isSelect==1){//有多个分组
			map.put("labelList", "");	
			}else if(isSelect==0){//只有未分组
			map.put("labelList", opList);			
			}
			map.put("isSelect", isSelect);*/
			//request.setAttribute("resGroups", resGroups);			
			showDto.setSaleProcessList(saleProcessList); // 销售进程
			showDto.setCustTypeList(custTypeList); // 客户类型
			//showDto.setLabelMap(map);
			showDto.setLabelList(opList);
			showDto.setResGroupList(resGroupList);
		} catch (Exception e) {
			logger.error("客户跟进异常！！", e);
		}
		return JsonUtil.getJsonString(showDto);
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
	
	/***
	 * 页面异步刷新
	 * @param request
	 * @return 
	 * @create  2016-8-1 上午9:52:21 zwj
	 * @history  4.x
	 */
	@ResponseBody
	@RequestMapping("/RefreshCustFollowPage")
	public String refreshCustFollowPage(HttpServletRequest request) {
		CustFollowShowGuideDto showDto = new CustFollowShowGuideDto(); // 页面返回JSON数据存储
		try {
			ShiroUser user = ShiroUtil.getShiroUser();			
			String custId = request.getParameter("custId"); // 资源Id
			String custType = request.getParameter("custType");
			String orgId = user.getOrgId();
			showDto.setCustId(custId);
			showDto.setActionDate(com.qftx.common.util.DateUtil.getDateCurrentDate(DateUtil.Data_ALL));
			showDto.setFollowId(SysBaseModelUtil.getModelId()); // 跟进Id
			showDto.setPlanParam(request.getParameter("planParam")); // 1：只显示计划中客户，2：不显示计划中客户		
						
			// 查询客户信息
			ResCustInfoBean custEntity = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custId);
			Integer sign = setAdminSignAuth(request); 
			if(user.getAccount().equals(custEntity.getOwnerAcc())){
				showDto.setIsMark(1);
			}else{
				showDto.setIsMark(2);		
			}
			if ("7".equals(custType) && user.getAccount().equals(custEntity.getCommonAcc())) {
				showDto.setIsMark(1);
			}
			if(sign == 1){
				showDto.setIsSign(1);
			}
			showDto.setStatus(custEntity.getStatus());
			if(custEntity != null && StringUtils.isNotBlank(custEntity.getLastOptionId())){
				showDto.setLastOptionListId(custEntity.getLastOptionId());
			}
			
			// 查询上次跟进信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", user.getOrgId());
			map.put("custId", custId);
			map.put("custFollowId", custEntity.getLastCustFollowID());
			
			CustFollowDto lastCustFollow = custFollowService.queryLastCustFollowByCustId(map);		
			if(lastCustFollow != null){
				Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
				lastCustFollow.setOwnerName(nameMap.get(lastCustFollow.getFollowAcc()) == null ? lastCustFollow.getFollowAcc() : nameMap.get(lastCustFollow.getFollowAcc()));
				if(user.getIsState() == 0){
					lastCustFollow.setCustName(custEntity.getName());
					lastCustFollow.setCustMobilephone(custEntity.getMobilephone());
					lastCustFollow.setOptionlistId(custEntity.getLastOptionId());
				}
				showDto.setLastCustFollow(lastCustFollow);
			}
			// 取得单位下各选项列表(销售进程、客户类型、适用产品、反馈信息)
			List<Product> suitProcList = cachedService.getOpionProduct(user.getOrgId());
			

			// 获取指定客户导航
			TsmCustGuide tsmCustGuideEntity = new TsmCustGuide();
			tsmCustGuideEntity.setCustId(custId);
			tsmCustGuideEntity.setOrgId(user.getOrgId());
			List<TsmCustGuide> tsmCustGuideList = tsmCustGuideService.getListByCondtion(tsmCustGuideEntity);
			List<Product> pds = new ArrayList<Product>();
			if (tsmCustGuideList != null && tsmCustGuideList.size() > 0) {
				showDto.setTsmCustGuideEntity(tsmCustGuideList.get(0));
				// 查询客户导航关联 获取产品ID
				TsmCustGuideProc tcgp = new TsmCustGuideProc();
				tcgp.setOrgId(user.getOrgId());
				tcgp.setGuideId(tsmCustGuideList.get(0).getCustGuideId());
				List<TsmCustGuideProc> tcgps = tsmCustGuideProcService.getListByCondtion(tcgp);
				if (tcgps != null && tcgps.size() > 0) {
					// 从缓存中获取 产品列表,放入map ，匹配上次跟进时 选择的产品
					Map<String,Product> map1 = new HashMap<String, Product>();
					for(Product product1 : suitProcList){
						map1.put(product1.getId(), product1);
					}
					for (TsmCustGuideProc tcg : tcgps) {					
						if(StringUtils.isNotBlank(tcg.getProcId())){
							if(map1.get(tcg.getProcId())!=null){
								pds.add(map1.get(tcg.getProcId()));
							}							
						}						
					}
				}
			}

			StringBuffer sbf = new StringBuffer();
			StringBuffer sbf1 = new StringBuffer();
			// 获取适用产品 默认值
			if (pds != null && pds.size() > 0) { // 上次跟进适用产品
				Map<String, String> map2 = new HashMap<String, String>(); // 用于修改适用产品默认勾选页面效果
				for (Product pd : pds) {
					sbf.append(pd.getId());
					sbf.append(",");
					sbf1.append(pd.getName());
					sbf1.append(",");
					map2.put(pd.getId(), pd.getId());
				}
				for (Product pd : suitProcList) {
					if (map2.get(pd.getId()) != null) {
						pd.setIsDefault(1);
					} else {
						pd.setIsDefault(0);
					}
				}			
				Collections.sort(suitProcList, new Comparator<Product>() {
		            public int compare(Product arg0, Product arg1) {
		                return arg0.getSort().compareTo(arg1.getSort());
		            }
		        });
				showDto.setSuitProcList(suitProcList); // 适用产品			
			} else {
				if (suitProcList != null && suitProcList.size() > 0) { // 默认适用产品
					for (Product pd : suitProcList) {
						if (pd.getIsDefault() == 1) {
							sbf.append(pd.getId());
							sbf.append(",");
							sbf1.append(pd.getName());
							sbf1.append(",");
						}
					}
					Collections.sort(suitProcList, new Comparator<Product>() {
			            public int compare(Product arg0, Product arg1) {
			                return arg0.getSort().compareTo(arg1.getSort());
			            }
			        });
					showDto.setSuitProcList(suitProcList); // 适用产品
				}
			}
			if (sbf != null && StringUtils.isNotBlank(sbf.toString()) && sbf.length() > 0) {
				String str = sbf.toString().substring(0, sbf.toString().length() - 1);
				showDto.setProcIds(str);
			}
			if (sbf1 != null && StringUtils.isNotBlank(sbf1.toString()) && sbf1.length() > 0) {
				String str1 = sbf1.toString().substring(0, sbf1.toString().length() - 1);
				showDto.setProcNames(str1);
			}
			List<DataDictionaryBean> dlist2 = cachedService.getDirList(AppConstant.DATA20, orgId);
			
			int nextContactValidation = 1;
			if(!dlist2.isEmpty() && dlist2.get(0) != null){
				String isOpen = dlist2.get(0).getIsOpen();
				isOpen = StringUtils.isBlank(isOpen) ? "1" : isOpen;
				nextContactValidation = Integer.valueOf(isOpen);
			}
			if (nextContactValidation == 1) {
				// 获取默认下次跟进时间
				showDto.setDefDate(getCustCacheDate(request));
			}
		} catch (Exception e) {
			logger.error("客户跟进异常！！", e);
		}
		return JsonUtil.getJsonString(showDto);
	}

	/** 客户跟进详细页面 右侧待跟进页面数据【跟进，意向客户，签约客户，流失客户，沉默客户】 */
	
	@ResponseBody
	@RequestMapping("/custFollowRightPage")
	public String custFollowRightPage(HttpServletRequest request,RestDto dto) {
		CustFollowShowWaitDto showDto = new CustFollowShowWaitDto();// 页面返回JSON数据存储
		try {
			String isContact = request.getParameter("isContact");
			ShiroUser user = ShiroUtil.getShiroUser();
			String planParam = request.getParameter("planParam"); // 1：只显示计划中客户，2：全部 , 0：请选择
			String custId = request.getParameter("custId"); // 资源ID
			// 客户类型 跟进
			String custListType = request.getParameter("custListType"); // 1:全部，2:意向客户，3:签约客户，4:流失客户，5:沉默客户, 6：客户跟进, 7:共有客户
	
			String lastCustType = request.getParameter("last_cust_type"); // 最近跟进客户类型ID
			String lastOptionId = request.getParameter("last_option_id"); // 最近跟进销售进程ID
			
			Integer tDateType = Integer.valueOf(request.getParameter("tDateType") == null ? "0" :request.getParameter("tDateType"));
			String taoStartDate = request.getParameter("taoStartDate");
			String taoEndDate = request.getParameter("taoEndDate");
			String resGroupId = request.getParameter("resGroupId");
			String orderType = request.getParameter("orderType");
			if(tDateType != null && tDateType != 0 && tDateType != 5){
				taoStartDate = getStartDateStr(tDateType);
				taoEndDate = DateUtil.formatDate(new Date(), DateUtil.DATE_DAY);
			}
			List<RestDto> restDtos = new ArrayList<RestDto>();
			if ("1".equals(planParam)) { // 计划待跟进
				// 计划待跟进 查询待跟进时间为当日数据
				RestDto restDto1 = new RestDto();
				restDto1.setOrgId(user.getOrgId());
				restDto1.setResCustId(custId);
				restDto1.setOwnerAcc(user.getAccount());
				restDto1.setLastCustType(lastCustType);
				restDto1.setLastOptionId(lastOptionId);
				/*restDto1.setStartPage(0);
				restDto1.setEndPage(6);*/
				restDto1.setState(user.getIsState());
				restDto1.settDateType(tDateType);
				restDto1.setTaoStartDate(taoStartDate);
				restDto1.setTaoEndDate(taoEndDate);
				restDto1.setResGroupId(resGroupId);
				restDto1.setOrderType(orderType);
				
				//restDto1.setDateIsNull(1);
				if ("1".equals(isContact)) {
					restDto1.setIsContact(isContact);
					List<RestDto> dtos = custFollowService.getDayFollowRightViewListPage(restDto1);
					List<String> ids = new ArrayList<>();
					if (dtos.size() > 0) {
						for (RestDto dto1 : dtos) {
							ids.add(dto1.getResCustId());
						}
					}
					restDto1.setIds(ids);
					restDto1.setIsContact("0");
				}
				restDto1.setPage(dto.getPage());
				restDtos.addAll(custFollowService.getDayFollowRightViewListPage(restDto1));
				/*if (restDtos.size()< dto.getPage().getShowCount()) {
					restDto1.setDateIsNull(0);
					restDtos.addAll(custFollowService.getDayFollowRightViewListPage(restDto1));
				}*/
				showDto.setDto(restDto1);
			}
			if (restDtos == null) {
				restDtos = new ArrayList<RestDto>();
			}
			if (!"1".equals(planParam)) { // 全部
				if (restDtos.size() < dto.getPage().getShowCount()) { // 取要求显示条数据					
					// 待跟进客户右侧没有做查询选择
					if(StringUtils.isBlank(planParam) && StringUtils.isBlank(lastCustType) && StringUtils.isBlank(lastOptionId) && tDateType == 0 && StringUtils.isBlank(resGroupId)){
						// 获取 resDto(列表中的查询条件)缓存，为跟进详情右侧显示内容做条件判断
						RestDto restDto = (RestDto) CachedUtil.getInstance().get(user.getAccount()+"_CUSTFOLLOW_RIGHT");					
						if(restDto == null){
							restDto = new RestDto();
							restDto.setOrgId(user.getOrgId());
							restDto.setResCustId(custId);
							restDto.setOwnerAcc(user.getAccount());
							restDto.setLastCustType(lastCustType);
							restDto.setLastOptionId(lastOptionId);
							restDto.setState(user.getIsState());
							restDto.settDateType(tDateType);
							restDto.setTaoStartDate(taoStartDate);
							restDto.setTaoEndDate(taoEndDate);
							restDto.setResGroupId(resGroupId);
							restDto.setOrderType(orderType);
							
							if ("1".equals(isContact)) {
								restDto.setIsContact(isContact);
								List<RestDto> dtos = custFollowService.getFollowRightListPage(restDto);
								List<String> ids = new ArrayList<>();
								if (dtos.size() > 0) {
									for (RestDto dto1 : dtos) {
										ids.add(dto1.getResCustId());
									}
								}
								restDto.setIds(ids);
								restDto.setIsContact("0");
							}
							restDto.setPage(dto.getPage());
							restDtos.addAll(custFollowService.getFollowRightListPage(restDto));
							/*if (restDtos.size()<dto.getPage().getShowCount()) {
								restDto.setDateIsNull(0);
								restDtos.addAll(custFollowService.getFollowRightListPage(restDto));
							}*/
							showDto.setDto(restDto);
						}else{
							restDto.setOrderType(orderType);
							showDto.setDto(restDto);
							if(restDto.getCustListType() == 6){ // 客户跟进列表查询条件
								if ("1".equals(isContact)) {
									restDto.setIsContact(isContact);
									List<RestDto> dtos = getCustFollowRight_follow(request,user,restDto);
									List<String> ids = new ArrayList<>();
									if (dtos.size() > 0) {
										for (RestDto dto1 : dtos) {
											ids.add(dto1.getResCustId());
										}
									}
									restDto.setIds(ids);
									restDto.setIsContact("0");
								}
								restDto.setPage(dto.getPage());
								restDtos.addAll(getCustFollowRight_follow(request,user,restDto));
								/*if (restDtos.size()<dto.getPage().getShowCount()) {
									restDto.setDateIsNull(0);
									restDtos.addAll(getCustFollowRight_follow(request,user,restDto));
								}*/
							}else if(restDto.getCustListType() == 2){ // 我的客户--意向客户 列表查询条件
								if ("1".equals(isContact)) {
									restDto.setIsContact(isContact);
									List<RestDto> dtos = getCustFollowRight_mycust(request,user,restDto);
									List<String> ids = new ArrayList<>();
									if (dtos.size() > 0) {
										for (RestDto dto1 : dtos) {
											ids.add(dto1.getResCustId());
										}
									}
									restDto.setIds(ids);
									restDto.setIsContact("0");
								}
								restDto.setPage(dto.getPage());
								restDtos.addAll(getCustFollowRight_mycust(request,user,restDto));
								/*if (restDtos.size()<dto.getPage().getShowCount()) {
									restDto.setDateIsNull(0);
									restDtos.addAll(getCustFollowRight_mycust(request,user,restDto));
								}*/
							}else if(restDto.getCustListType() == 3){ // 我的客户--签约客户 列表查询条件
								if ("1".equals(isContact)) {
									restDto.setIsContact(isContact);
									List<RestDto> dtos = getCustFollowRight_mysign(request,user,restDto);
									List<String> ids = new ArrayList<>();
									if (dtos.size() > 0) {
										for (RestDto dto1 : dtos) {
											ids.add(dto1.getResCustId());
										}
									}
									restDto.setIds(ids);
									restDto.setIsContact("0");
								}
								restDto.setPage(dto.getPage());
								restDtos.addAll(getCustFollowRight_mysign(request,user,restDto));
								/*if (restDtos.size()<dto.getPage().getShowCount()) {
									restDto.setDateIsNull(0);
									restDtos.addAll(getCustFollowRight_mysign(request,user,restDto));
								}*/
							}else if(restDto.getCustListType() == 4){ // 我的客户-- 沉默客户 列表查询条件
								if ("1".equals(isContact)) {
									restDto.setIsContact(isContact);
									List<RestDto> dtos = getCustFollowRight_mySilent(request,user,restDto);
									List<String> ids = new ArrayList<>();
									if (dtos.size() > 0) {
										for (RestDto dto1 : dtos) {
											ids.add(dto1.getResCustId());
										}
									}
									restDto.setIds(ids);
									restDto.setIsContact("0");
								}
								restDto.setPage(dto.getPage());
								restDtos.addAll(getCustFollowRight_mySilent(request,user,restDto));
								/*if (restDtos.size()<dto.getPage().getShowCount()) {
									restDto.setDateIsNull(0);
									restDtos.addAll(getCustFollowRight_mySilent(request,user,restDto));
								}*/
							}else if(restDto.getCustListType() == 5){ // 我的客户-- 流失客户 列表查询条件
								if ("1".equals(isContact)) {
									restDto.setIsContact(isContact);
									List<RestDto> dtos = getCustFollowRight_myLos(request,user,restDto);
									List<String> ids = new ArrayList<>();
									if (dtos.size() > 0) {
										for (RestDto dto1 : dtos) {
											ids.add(dto1.getResCustId());
										}
									}
									restDto.setIds(ids);
									restDto.setIsContact("0");
								}
								restDto.setPage(dto.getPage());
								restDtos.addAll(getCustFollowRight_myLos(request,user,restDto));
								/*if (restDtos.size()<dto.getPage().getShowCount()) {
									restDto.setDateIsNull(0);
									restDtos.addAll(getCustFollowRight_myLos(request,user,restDto));
								}*/
							}else if(restDto.getCustListType() == 1){ // 我的客户-- 全部客户 列表查询条件
								if ("1".equals(isContact)) {
									restDto.setIsContact(isContact);
									List<RestDto> dtos = getCustFollowRight_myall(request,user,restDto);
									List<String> ids = new ArrayList<>();
									if (dtos.size() > 0) {
										for (RestDto dto1 : dtos) {
											ids.add(dto1.getResCustId());
										}
									}
									restDto.setIds(ids);
									restDto.setIsContact("0");
								}
								restDto.setPage(dto.getPage());
								restDtos.addAll(getCustFollowRight_myall(request,user,restDto));
								/*if (restDtos.size()<dto.getPage().getShowCount()) {
									restDto.setDateIsNull(0);
									restDtos.addAll(getCustFollowRight_myall(request,user,restDto));
								}*/
							}else if(restDto.getCustListType() == 7){ // 我的客户-- 共有客户 列表查询条件
								if ("1".equals(isContact)) {
									restDto.setIsContact(isContact);
									List<RestDto> dtos = getCustFollowRight_myCom(request,user,restDto);
									List<String> ids = new ArrayList<>();
									if (dtos.size() > 0) {
										for (RestDto dto1 : dtos) {
											ids.add(dto1.getResCustId());
										}
									}
									restDto.setIds(ids);
									restDto.setIsContact("0");
								}
								restDto.setPage(dto.getPage());
								restDtos.addAll(getCustFollowRight_myCom(request,user,restDto));
								/*if (restDtos.size()<dto.getPage().getShowCount()) {
									restDto.setDateIsNull(0);
									restDtos.addAll(getCustFollowRight_myCom(request,user,restDto));
								}*/
							}
						}
					}else{
						RestDto restDto = new RestDto();
						restDto.setOrgId(user.getOrgId());
						restDto.setResCustId(custId);
						restDto.setOwnerAcc(user.getAccount());
						restDto.setLastCustType(lastCustType);
						restDto.setLastOptionId(lastOptionId);
						restDto.setState(user.getIsState());
						restDto.settDateType(tDateType);
						restDto.setTaoStartDate(taoStartDate);
						restDto.setTaoEndDate(taoEndDate);
						restDto.setResGroupId(resGroupId);
						restDto.setOrderType(orderType);
						showDto.setDto(restDto);
						if ("1".equals(isContact)) {
							restDto.setIsContact(isContact);
							List<RestDto> dtos = custFollowService.getFollowRightListPage(restDto);
							List<String> ids = new ArrayList<>();
							if (dtos.size() > 0) {
								for (RestDto dto1 : dtos) {
									ids.add(dto1.getResCustId());
								}
							}
							restDto.setIds(ids);
							restDto.setIsContact("0");
						}
						restDto.setPage(dto.getPage());
						restDtos.addAll(custFollowService.getFollowRightListPage(restDto));
						/*if (restDtos.size()<dto.getPage().getShowCount()) {
							restDto.setDateIsNull(0);
							List<RestDto> dtos2 = custFollowService.getFollowRightListPage(restDto);
							restDtos.addAll(dtos2);
						}*/	
					}
									
				}		
			}
			
			if (restDtos != null && restDtos.size() > dto.getPage().getShowCount()) {
				restDtos = restDtos.subList(0, dto.getPage().getShowCount());
			}
			if (restDtos != null && restDtos.size() > 1) {
				restDtos = dealDate(restDtos,orderType);
			}
			showDto.setRestDtos(restDtos);
			showDto.setPlanParam(planParam);
			
		} catch (Exception e) {
			logger.error("客户跟进详细页面 右侧待跟进页面数据 异常！",e);
		}
		return JsonUtil.getJsonString(showDto);
	}
	
	public List<RestDto> dealDate(List<RestDto> resDtos,String orderType) {
		List<RestDto> list = new ArrayList<>();
		List<Integer> index = new ArrayList<>();
		List<RestDto> newList = new ArrayList<>();
		if ("1".equals(orderType)) {//下次联系时间正序
			for (int i = 0;i < resDtos.size();i++) {
				if (resDtos.get(i).getNextActionDate() == null) {
					list.add(resDtos.get(i));
				}else {
					index.add(i);
				}
			}
			if (index.size() != 0 && index != null) {
				for (int i = 0; i < index.size(); i++) {
					newList.add(resDtos.get(index.get(i)));
				}
			}
			newList.addAll(list);
		} else if ("2".equals(orderType)) {//下次联系时间倒序
			for (int i = 0;i < resDtos.size();i++) {
				if (resDtos.get(i).getNextActionDate() == null) {
					list.add(resDtos.get(i));
				}else {
					index.add(i);
				}
			}
			newList.addAll(list);
			if (index.size() != 0 && index != null) {
				for (int i = 0; i < index.size(); i++) {
					newList.add(resDtos.get(index.get(i)));
				}
			}
		} else if ("3".equals(orderType)) {//最近联系时间正序
			for (int i = 0;i < resDtos.size();i++) {
				if (resDtos.get(i).getActionDate() == null) {
					list.add(resDtos.get(i));
				}else {
					index.add(i);
				}
			}
			if (index.size() != 0 && index != null) {
				for (int i = 0; i < index.size(); i++) {
					newList.add(resDtos.get(index.get(i)));
				}
			}
			newList.addAll(list);
		} else if ("4".equals(orderType)) {//最近联系时间倒序
			for (int i = 0;i < resDtos.size();i++) {
				if (resDtos.get(i).getActionDate() == null) {
					list.add(resDtos.get(i));
				}else {
					index.add(i);
				}
			}
			newList.addAll(list);
			if (index.size() != 0 && index != null) {
				for (int i = 0; i < index.size(); i++) {
					newList.add(resDtos.get(index.get(i)));
				}
			}
		} else if ("5".equals(orderType)) {//淘到客户时间正序
			for (int i = 0;i < resDtos.size();i++) {
				if (resDtos.get(i).getAmoyToCustomrDate() == null) {
					list.add(resDtos.get(i));
				}else {
					index.add(i);
				}
			}
			if (index.size() != 0 && index != null) {
				for (int i = 0; i < index.size(); i++) {
					newList.add(resDtos.get(index.get(i)));
				}
			}
			newList.addAll(list);
		} else if ("6".equals(orderType)) {//淘到客户时间倒序
			for (int i = 0;i < resDtos.size();i++) {
				if (resDtos.get(i).getAmoyToCustomrDate() == null) {
					list.add(resDtos.get(i));
				}else {
					index.add(i);
				}
			}
			newList.addAll(list);
			if (index.size() != 0 && index != null) {
				for (int i = 0; i < index.size(); i++) {
					newList.add(resDtos.get(index.get(i)));
				}
			}
		}
		return newList;
	}
	
	/** 客户跟进详细页面 右侧页面数据【跟进警报】 */
	@ResponseBody
	@RequestMapping("/custFollowAralmRightPage")
	public String custFollowAralmRightPage(HttpServletRequest request) {
		CustFollowShowWaitDto showDto = new CustFollowShowWaitDto();// 页面返回JSON数据存储
		try {
			String startPage = request.getParameter("startPage");
			String custId = request.getParameter("custId");
			if (StringUtils.isBlank(startPage) || "undefined".equals(startPage)) { // 如果为空，默认从第一条开始
				startPage = "1";
			}
			List<RestDto> restDtos = new ArrayList<RestDto>();
			ShiroUser user = ShiroUtil.getShiroUser();
			RestDto restDto = new RestDto();
			restDto.setOrgId(user.getOrgId());
			restDto.setOwnerAcc(user.getAccount());
			restDto.setResCustId(custId);
			restDto.setState(user.getIsState());
			restDto.setStartPage(Integer.parseInt(startPage) - 1);
			restDto.setEndPage(6);
			restDto.setDataDictionVal(Integer.parseInt(getFollowExpire()));
			restDto.setDataDictionVal1(Integer.parseInt(getFollowExpire1()));
			restDtos = custFollowService.getFollowRightAralmView(restDto);
			if (restDtos != null && restDtos.size() > 0) {
				int i = 1;
				for (RestDto restDto1 : restDtos) { // 设值 第几条
					restDto1.setStartPage(Integer.parseInt(startPage) + i);
					i++;
				}
			}
			// 从0开始，查询   TODO 此处算法还存在问题，当数据少于6条时，中间条数选择会有错误
			if (Integer.parseInt(startPage) - 1 != 0) { // 组合6条数据
				if (restDtos == null || restDtos.size() < 6) {
					restDto.setStartPage(0);
					restDto.setResCustId(custId);
					restDto.setEndPage(6 - (restDtos == null ? 0 : restDtos.size()));
					restDto.setDataDictionVal(Integer.parseInt(getFollowExpire()));
					restDto.setDataDictionVal1(Integer.parseInt(getFollowExpire1()));
					List<RestDto> restDtos2 = custFollowService.getFollowRightAralmView(restDto);
					if (restDtos2 != null && restDtos2.size() > 0) {
						int i = 1;
						for (RestDto restDto1 : restDtos2) { // 设值 第几条
							restDto1.setStartPage(Integer.parseInt(startPage) + i);
							i++;
						}
					}
					restDtos.addAll(restDtos2);
				}
			}
			if (restDtos != null && restDtos.size() > 0) {
				showDto.setRestDtos(restDtos);
			}
			showDto.setStartPage(startPage);
			showDto.setIdReplaceWord(setIsReplaceWord(request, user));
		} catch (Exception e) {
			logger.error("客户跟进详细页面 右侧页面数据【跟进警报】异常！", e);
		}
		return JsonUtil.getJsonString(showDto);
	}

	/** 客户跟进 个人客户信息 */
	@RequestMapping("/custFollowPersonInfo1")
	public String custFollowPersonInfo1(HttpServletRequest request) {
		try {
			String custId = request.getParameter("custId");
			ShiroUser user = ShiroUtil.getShiroUser();
			// 查询缓存 企业字段
			List<CustFieldSet> fieldSets = cachedService.getPersonFiledSets(user.getOrgId());
			request.setAttribute("fieldSetList", fieldSets);

			// 查询客户信息
			/*ResCustInfoBean resCustEntity = new ResCustInfoBean();
			resCustEntity.setResCustId(custId);
			resCustEntity.setOrgId(user.getOrgId());
			ResCustInfoBean custEntity = resCustInfoService.getByCondtion(resCustEntity);*/
			ResCustInfoBean custEntity = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custId);
			request.setAttribute("custEntity", custEntity);
			String pname = "";
			String cname = "";
			String oname = "";
			if (custEntity != null) {
				Integer pid = custEntity.getProvinceId();
				// 省市区
				if (pid != null) {
					ChinaProvinceBean cpb = areaService.getChinaProvinceByPid(pid);
					pname = cpb.getPname();
					Integer cid = custEntity.getCityId();
					if (cid != null) {
						ChinaCityBean ccb = areaService.getChinaCityByCid(cid);
						cname = ccb.getCname();
						Integer oid = custEntity.getCountyId();
						if (oid != null) {
							ChinaCountyBean cob = areaService.getChinaCountyByOid(oid);
							oname = cob.getOname();
						}
					}
				}
			}
			request.setAttribute("pname", pname);
			request.setAttribute("cname", cname);
			request.setAttribute("oname", oname);
			setIsReplaceWord(request, user);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/follow/info_submodual/follow_person_custInfo";
	}
	
	/** 客户跟进 个人客户信息 */
	@ResponseBody
	@RequestMapping("/custFollowPersonInfo")
	public String custFollowPersonInfo(HttpServletRequest request) {
		CustFollowShowCustInfoDto showDto = new CustFollowShowCustInfoDto(); // 存储页面返回JSON数据
		try {
			String custId = request.getParameter("custId");
			ShiroUser user = ShiroUtil.getShiroUser();
			// 查询缓存 企业字段
			List<CustFieldSet> fieldSets = cachedService.getPersonFiledSets(user.getOrgId());
			showDto.setFieldSets(fieldSets);

			// 查询客户信息
			ResCustInfoBean custEntity = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custId);
			showDto.setCustEntity(custEntity);
			String pname = "";
			String cname = "";
			String oname = "";
			if (custEntity != null) {
				Integer pid = custEntity.getProvinceId();
				// 省市区
				if (pid != null) {
					ChinaProvinceBean cpb = areaService.getChinaProvinceByPid(pid);
					pname = cpb.getPname();
					Integer cid = custEntity.getCityId();
					if (cid != null) {
						ChinaCityBean ccb = areaService.getChinaCityByCid(cid);
						cname = ccb.getCname();
						Integer oid = custEntity.getCountyId();
						if (oid != null) {
							ChinaCountyBean cob = areaService.getChinaCountyByOid(oid);
							oname = cob.getOname();
						}
					}
				}
			}
			request.setAttribute("pname", pname);
			request.setAttribute("cname", cname);
			request.setAttribute("oname", oname);
			showDto.setPname(pname);
			showDto.setCname(cname);
			showDto.setOname(oname);
			showDto.setIdReplaceWord(setIsReplaceWord(request, user));
			showDto.setCustData(custInfoService.getCustData(fieldSets, 0, pname, cname, oname, custEntity));
			showDto.setCustInfo(custInfoService.getCustDto(custEntity, 0, null));
		} catch (Exception e) {
			logger.error("客户跟进 个人客户信息 异常！", e);
		}
		return JsonUtil.getJsonString(showDto);
	}
	
	/** 客户跟进 企业客户信息 */
	@ResponseBody
	@RequestMapping("/custFollowEnterInfo")
	public String custFollowEnterInfo(HttpServletRequest request) {
		CustFollowShowCustInfoDto showDto = new CustFollowShowCustInfoDto(); // 存储页面返回JSON数据
		try {
			String custId = request.getParameter("custId");
			ShiroUser user = ShiroUtil.getShiroUser();
			// 查询缓存 企业字段
			List<CustFieldSet> fieldSets = cachedService.getComFiledSets(user.getOrgId());
			showDto.setFieldSets(fieldSets);

			// 查询客户信息
			/*ResCustInfoBean resCustEntity = new ResCustInfoBean();
			resCustEntity.setResCustId(custId);
			resCustEntity.setOrgId(user.getOrgId());
			ResCustInfoBean custEntity = resCustInfoService.getByCondtion(resCustEntity);*/
			ResCustInfoBean custEntity = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custId);
			showDto.setCustEntity(custEntity);

			// 省市区
			String pname = "";
			String cname = "";
			String oname = "";
			if (custEntity != null) {
				Integer pid = custEntity.getProvinceId();
				if (pid != null) {
					ChinaProvinceBean cpb = areaService.getChinaProvinceByPid(pid);
					pname = cpb.getPname();
					Integer cid = custEntity.getCityId();
					if (cid != null) {
						ChinaCityBean ccb = areaService.getChinaCityByCid(cid);
						cname = ccb.getCname();
						Integer oid = custEntity.getCountyId();
						if (oid != null) {
							ChinaCountyBean cob = areaService.getChinaCountyByOid(oid);
							oname = cob.getOname();
						}
					}
				}
			}

			showDto.setPname(pname);
			showDto.setCname(cname);
			showDto.setOname(oname);
			// 查询联系人信息
			List<ResCustInfoDetailBean> details = resCustInfoDetailService.getCustsInfoDetails(user.getOrgId(), custId);
			showDto.setDetails(details);
			// 查询默认联系人
			if (details != null && details.size() > 0) {
				if (details.size() == 1) {
					showDto.setDetail(details.get(0));
				} else {
					for (ResCustInfoDetailBean detail : details) {
						if (detail.getIsDefault() == 1) {
							showDto.setDetail(detail);
							break;
						}
					}
				}
			}
			showDto.setCustData(custInfoService.getCustData(fieldSets, 1, pname, cname, oname, custEntity));
			showDto.setCustInfo(custInfoService.getCustDto(custEntity, 1, details));
			showDto.setIdReplaceWord(setIsReplaceWord(request, user));
		} catch (Exception e) {
			logger.error("客户跟进 企业客户信息 异常！", e);
		}
		return JsonUtil.getJsonString(showDto);
	}
	
	/** 客户跟进 联系记录 */
	@RequestMapping("/custFollowShowList1")
	public String custFollowShowList1(HttpServletRequest request) {
		try {
			String custId = request.getParameter("custId");
			ShiroUser user = ShiroUtil.getShiroUser();
			//根据客户id 查询所有 跟进信息
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", user.getOrgId());
			map.put("custId", custId);
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
				followDto.setShowLastActionDate(followDto.getLastActionDate() !=null?com.qftx.base.util.DateUtil.formatDate(followDto.getLastActionDate(), com.qftx.base.util.DateUtil.Data_ALL): "");			
				followDto.setShowNextActionDate(followDto.getNextActionDate() !=null?com.qftx.base.util.DateUtil.formatDate(followDto.getNextActionDate(), com.qftx.base.util.DateUtil.Data_ALL): "");
					if (optionMap != null) {
						followDto.setOptionName(optionMap.get(followDto.getOptionlistId()));
					}
				String labelName = followDto.getLabelName();
				if(StringUtils.isNotBlank(labelName)){
					labelName = labelName.replaceAll("#", "，");
					labelName = labelName.substring(0,labelName.length()-1);
					followDto.setLabelName(labelName);
				}
			}
			
			//  获取相关跟进ID 的录音记录
			if(followIdSbf != null && followIdSbf.size() > 0){
				/** 参数 */ 
				FollowCallQueryDto fcqd = new FollowCallQueryDto();
				fcqd.setOrgId(user.getOrgId());
				fcqd.setFollowIds(followIdSbf);
				List<TsmRecordCallBean> callLists  =  CallRecordGetUtil.getRecordeCallFollowList(fcqd);
				if(callLists != null && callLists.size() > 0){
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
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/follow/info_submodual/follow_lists";
	}

	
	/** 客户跟进 联系记录 */
	@ResponseBody
	@RequestMapping("/custFollowShowList")
	public String custFollowShowList(HttpServletRequest request) {
		CustFollowShowFollowRightDto showDto = new CustFollowShowFollowRightDto();
		try {
			String custId = request.getParameter("custId");
			ShiroUser user = ShiroUtil.getShiroUser();
			//根据客户id 查询所有 跟进信息
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", user.getOrgId());
			map.put("custId", custId);
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
				followDto.setShowLastActionDate(followDto.getLastActionDate() !=null?com.qftx.base.util.DateUtil.formatDate(followDto.getLastActionDate(), com.qftx.base.util.DateUtil.Data_ALL): "");			
				followDto.setShowNextActionDate(followDto.getNextActionDate() !=null?com.qftx.base.util.DateUtil.formatDate(followDto.getNextActionDate(), com.qftx.base.util.DateUtil.Data_ALL): "");
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
				List<TsmRecordCallBean> callLists = CallRecordGetUtil.getRecordeCallFollowList(fcqd);
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
				showDto.setPlayUrl(ConfigInfoUtils.getStringValue("call_play_url"));
		        // 服务地址，为了提供给客户端，弹出播放列表 	
				showDto.setProject_path(getProgetUtil(request));
			}
			showDto.setCustFollows(custFollows);
		} catch (Exception e) {
			logger.error("客户跟进 联系记录 异常！", e);
		}
		return JsonUtil.getJsonString(showDto);
	}
	
	@RequestMapping("/savePoolCustFollow")
	@ResponseBody
	public String savePoolCustFollow(HttpServletRequest request, @RequestBean("custGuide") TsmCustGuide custGuide,
			@RequestBean("custFollow") CustFollowBean custFollow){
		try {
			if(StringUtils.isEmpty(custFollow.getCustFollowId())) custFollow.setCustFollowId(SysBaseModelUtil.getModelId());
			ShiroUser user = ShiroUtil.getShiroUser();
			String suitProcId = request.getParameter("suitProcId"); // 适用产品Id s
			String custId = request.getParameter("custId"); // 资源ID
			String isTao = request.getParameter("isTao");
			isTao = StringUtils.isBlank(isTao) ? "0" : isTao;
			 //判断库容
			if(isTao.equals("1")){
				Map<String, Integer> map = isCustBeyondMax(user.getAccount(), user.getOrgId(), 1);
				if(map.get("flag") == 1){
					return "个人意向客户最大数为"+map.get("maxNum")+",库中已有"+map.get("custNum")+",超出最大意向客户数！";
				}
			}
			
            if (StringUtils.isNotBlank(custId) && StringUtils.isNotBlank(suitProcId) && custGuide != null && custFollow != null
					&& StringUtils.isNotBlank(custFollow.getCustFollowId())) {
            	custFollowService.savePoolCustFollow(custId, suitProcId, custGuide, custFollow, user, isTao);
            }
			return AppConstant.RESULT_SUCCESS;
		} catch (Exception e) {
			logger.error("客户卡片【公海客户】跟进记录保存异常！，msg="+e.getMessage(),e);
			return AppConstant.RESULT_FAILURE;
		}
	}
	
	/** 客户跟进 保存 */
	@RequestMapping("/saveCustFollow")
	@ResponseBody
	public String saveCustFollow(HttpServletRequest request, @RequestBean("custGuide") TsmCustGuide custGuide,
			@RequestBean("custFollow") CustFollowBean custFollow) {
		try {
			if(StringUtils.isEmpty(custFollow.getCustFollowId())) custFollow.setCustFollowId(SysBaseModelUtil.getModelId());
			ShiroUser user = ShiroUtil.getShiroUser();
			String isAddDate = request.getParameter("isAddDate");
			String suitProcId = request.getParameter("suitProcId"); // 适用产品Id s
			String custId = request.getParameter("custId"); // 资源ID
            String custSign = request.getParameter("custSign"); // 是否签约客户
            String custGiveUp = request.getParameter("custGiveUp"); // 是否放弃客户
            String phone = request.getParameter("phone");
            String signSetting = request.getParameter("signSetting"); // 读取签约是否与合同无关 0-需添加合同 1-无需添加合同
            
            if (StringUtils.isNotBlank(custId) && StringUtils.isNotBlank(suitProcId) && custGuide != null && custFollow != null
					&& StringUtils.isNotBlank(custFollow.getCustFollowId())) {
            	boolean isGiveUp = StringUtils.isNotBlank(custGiveUp) ? true : false;
                boolean isSign = StringUtils.isNotBlank(custSign) ? true : false;
                if (isSign && "0".equals(signSetting)) { // 签约客户 保存至session,当合同保存后，再做入库操作
                    Object[] obj = new Object[] { custId, suitProcId, custGuide, custFollow, isSign };
                    ShiroUtil.setSession(SysConstant._FOLLOW_SIGN, obj);
                }else {               
                	String reqId = SysBaseModelUtil.getModelId();
                    CustFollowBean follow = new CustFollowBean();
                    ResCustInfoBean custInfo = new ResCustInfoBean();
                    ResCustInfoBean custBean = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custId);
                    if (custBean != null && custBean.getType() == 1 && (custBean.getStatus() == 2 || custBean.getStatus() == 3)) {
						String name = "";
						if (custBean.getState() == 1) {
							Map<String,String> map = resCustInfoDetailService.getCustDetailName(user.getOrgId(),phone,custId);
							if (map != null) {
								name = map.get("name");
							}
						}
						custInfo.setResCustId(custId);
						follow.setCustFollowId(custFollow.getCustFollowId());
						follow.setLabelCode(custFollow.getLabelCode());
						follow.setLabelName(custFollow.getLabelName());
						follow.setCustDetailMobile(phone);
						follow.setCustDetailName(name);
						follow.setOldCustName(custBean.getName());
						follow.setFeedbackComment(custFollow.getFeedbackComment());
						follow.setFeedbackImg(custFollow.getFeedbackImg());
						follow.setEffectiveness(new Integer(custFollow.getEffectiveness()));
						follow.setFollowType(custFollow.getFollowType());
						follow.setFollowDate(custFollow.getFollowDate());
						follow.setSaleProcessId(custFollow.getSaleProcessId());
						custGuide.setSaleProcessId(custFollow.getSaleProcessId());
						TaoTagBean bean = cachedService.getLoction(user.getOrgId(), user.getAccount());
						custInfo.setLabelCode(custFollow.getLabelCode());
						custInfo.setLabelName(custFollow.getLabelName());
						String pool = "";
						if(bean!=null){
							pool = bean.getPool();
						}else {
							pool = "1";
						}
						taoCustService.addMyCust(reqId,user.getOrgId(),user.getAccount(),user.getGroupId(),user.getId(),user.getName(),user.getIsState(), custInfo, follow, custGuide, suitProcId, bean,follow.getCustDetailMobile(),follow.getCustDetailName(),pool,"1",1);
					} else {
						if(isSign && "1".equals(signSetting)){ // 不加合同，直接签约方法。
		                	contractService.sign(custId, user,"4",custFollow.getFollowDate());
		                }
						custFollowService.saveCustFollow(custId, suitProcId, custGuide, custFollow, isSign,isGiveUp,user,isAddDate);
					}
				}
			}
			return AppConstant.RESULT_SUCCESS;
		} catch (Exception e) {
			logger.error("客户跟进操作异常！", e);
			return AppConstant.RESULT_FAILURE;
		}
	}
	/** 保存前 先获取该用户是否进入公海客户池 */
	@RequestMapping("getCustStatus")
	@ResponseBody
	public String getCustStatus(HttpServletRequest request) {
		try {
			String custId = request.getParameter("custId");
			ShiroUser user = ShiroUtil.getShiroUser();
			// 查询客户信息
			ResCustInfoBean resCustEntity = new ResCustInfoBean();
			resCustEntity.setResCustId(custId);
			resCustEntity.setOrgId(user.getOrgId());
			ResCustInfoBean custEntity = resCustInfoService.getByCondtion(resCustEntity);
			if (custEntity != null) {
				if (Integer.valueOf(AppConstant.STATUS_4) == custEntity.getStatus()) { // 判断客户状态是否是公海客户
					return AppConstant.RESULT_SUCCESS;
				}
			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return AppConstant.RESULT_FAILURE;
	}

	/** 获取客户跟进 下次默认跟进时间 设置 */
	private String getCustCacheDate(HttpServletRequest request) throws Exception {
		ShiroUser user = ShiroUtil.getShiroUser();
		String orgId = user.getOrgId();
		List<DataDictionaryBean> dlist = cachedService.getDirList(AppConstant.DATA15, orgId);
		int hour = 0;// 默认关闭 1 天24小时
		int day = 7;
		int minsHour = 60;
		int nextContactValidation = 1;
		if (!dlist.isEmpty() && ("1".equals(dlist.get(0).getDictionaryValue()))) {
			// 判断是否开启
			List<DataDictionaryBean> dlist2 = cachedService.getDirList(AppConstant.DATA20, orgId);
			List<DataDictionaryBean> dlist3 = cachedService.getDirList(AppConstant.DATA21, orgId);
			if(!dlist2.isEmpty() && dlist2.get(0) != null){
				String isOpen = dlist2.get(0).getIsOpen();
				isOpen = StringUtils.isBlank(isOpen) ? "1" : isOpen;
				nextContactValidation = Integer.valueOf(isOpen);
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
		request.setAttribute("nextContactValidation", nextContactValidation);
		return com.qftx.common.util.DateUtil.addDateMinut(minsHour * (day * 24 + hour));
	}

	/**
	 * 客户跟进数据分类 列表查询不同处 集合
	 */
	private void followCustCations(RestDto restDto, String custCation,ShiroUser user) {
		try {
			// 上次跟进时间范围
			/*String lastStartActionDate_ = restDto.getLastStartActionDate();
			String lastEndActionDate_ = restDto.getLastEndActionDate();*/
			//上次所有者跟进时间范围
			String lastStartOwnerActionDate_ = restDto.getLastStartOwnerActionDate();
			String lastEndOwnerActionDate_ = restDto.getLastEndOwnerActionDate();
			// 下次跟进时间范围
			String nextStartActionDate_ = restDto.getNextStartActionDate();
			String nextEndActionDate_ = restDto.getNextEndActionDate();
			String today = com.qftx.common.util.DateUtil.format(new Date(), "yyyy-MM-dd");
			if (FollowCustEnum.CUST_TODAY_CONTACT_Y.getState().equals(custCation)) {
				// 【今日已联系】上次次跟进时间=今天
				if ((StringUtils.isBlank(lastStartOwnerActionDate_)) && (StringUtils.isBlank(lastEndOwnerActionDate_))) {
					lastStartOwnerActionDate_ = today;
					lastEndOwnerActionDate_ = today;
				}
				restDto.setLastStartOwnerActionDate(lastStartOwnerActionDate_);
				restDto.setLastEndOwnerActionDate(lastEndOwnerActionDate_);
			} else if (FollowCustEnum.CUST_WEEK_CONTACT_Y.getState().equals(custCation)) {
				// 【本周已联系】周一=>上次跟进时间=今天
				if ((StringUtils.isBlank(lastStartOwnerActionDate_)) && (StringUtils.isBlank(lastEndOwnerActionDate_))) {
					lastStartOwnerActionDate_ = com.qftx.base.util.DateUtil.getWeekFirstDay(new Date());
					lastEndOwnerActionDate_ = today;
				}
				restDto.setLastStartOwnerActionDate(lastStartOwnerActionDate_);
				restDto.setLastEndOwnerActionDate(lastEndOwnerActionDate_);
			} else if (FollowCustEnum.CUST_7_TODAY_CONTACT_W.getState().equals(custCation)) {
				// 【7天未联系】上次跟进时间<=7天前的时间
				if ((StringUtils.isBlank(lastStartOwnerActionDate_)) && (StringUtils.isBlank(lastEndOwnerActionDate_))) {
					lastStartOwnerActionDate_ = null;
					lastEndOwnerActionDate_ = com.qftx.common.util.DateUtil.format(com.qftx.common.util.DateUtil.parse(com.qftx.common.util.DateUtil.getOneDay(-7)),
							com.qftx.common.util.DateUtil.defaultPattern);
				}
				restDto.setLastStartOwnerActionDate(lastStartOwnerActionDate_);
				restDto.setLastEndOwnerActionDate(lastEndOwnerActionDate_);
			} else if (FollowCustEnum.CUST_30_TODAY_CONTACT_W.getState().equals(custCation)) {
				// 【30天未联系】上次跟进时间<=30天前的时间
				if ((StringUtils.isBlank(lastStartOwnerActionDate_)) && (StringUtils.isBlank(lastEndOwnerActionDate_))) {
					lastStartOwnerActionDate_ = null;
					lastEndOwnerActionDate_ = com.qftx.common.util.DateUtil.format(
							com.qftx.common.util.DateUtil.parse(com.qftx.common.util.DateUtil.getOneDay(-30)), com.qftx.common.util.DateUtil.defaultPattern);
				}
				restDto.setLastStartOwnerActionDate(lastStartOwnerActionDate_);
				restDto.setLastEndOwnerActionDate(lastEndOwnerActionDate_);
			} else if (FollowCustEnum.CUST_TODAY_CONTACT_X.getState().equals(custCation)) {
				// 【今日待联系】
				if (StringUtils.isBlank(nextStartActionDate_) && StringUtils.isBlank(nextEndActionDate_)) {
					nextStartActionDate_ = today;
					nextEndActionDate_ = today;
				}
				restDto.setNextStartActionDate(nextStartActionDate_);
				restDto.setNextEndActionDate(nextEndActionDate_);
				restDto.setContactStatus(1); 
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
					restDto.setResCustIds(resCustIds);
				}	*/	
			}else if(FollowCustEnum.CUST_CONTACT_IMPORT.getState().equals(custCation)){ // 重点客户			
				restDto.setIsMajor("1");	
			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
	}

	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, (PropertyEditor) new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}

	/**
	 * 设置是否开启用*替换电话号码中间四位
	 */
	private Integer setIsReplaceWord(HttpServletRequest request, ShiroUser user) {
		// 查询缓存
		List<DataDictionaryBean> dataMap = cachedService.getDirList(AppConstant.DATA24, user.getOrgId());
		Integer idReplaceWord = 0;
		if (!dataMap.isEmpty() && dataMap.get(0) != null && !cachedService.judgeHideWhiteList(user.getOrgId(), user.getAccount())) {
			idReplaceWord = new Integer(dataMap.get(0).getDictionaryValue());
		}
		return idReplaceWord;
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
		Map<String, String> map = cachedService.getAuthUrlButton(user);
		if (map.get("mySignCust_addContract") == null && signSetting == 0) {
			signSetting = 1;
		}
		request.setAttribute("signSetting", signSetting);
	}
	// 返回跟进警报相关设置值
	private String getFollowExpire() {
		String flg = "0";
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			// 查询 跟进警报提醒是否开启
			List<DataDictionaryBean> data1 = cachedService.getDirList(AppConstant.DATA15, user.getOrgId());
			List<DataDictionaryBean> data2 = cachedService.getDirList(AppConstant.DATA_FOLLOW_EXPIRE, user.getOrgId());
			if (!data1.isEmpty() && "1".equals(data1.get(0).getDictionaryValue()) && "1".equals(data2.get(0).getIsOpen())) {
				flg = data2.get(0).getDictionaryValue();
			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return flg;
	}

	// 返回跟进警报消息提前时间设置值
	private String getFollowExpire1() {
		String flg = "0";
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<DataDictionaryBean> data2 = cachedService.getDirList(AppConstant.DATA_20013, user.getOrgId());
			if (StringUtils.isNotBlank(data2.get(0).getDictionaryValue())) {
				flg = data2.get(0).getDictionaryValue();
			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return flg;
	}
	
	// 获取项目地址
	private String getProgetUtil(HttpServletRequest request) {
		if (80 == request.getServerPort()) {
			return request.getScheme() + "://" + request.getServerName() + request.getContextPath();
		} else {
			return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
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
	
	public List<CustFieldSet> getIsQueryList(String orgId, int isState) {
		List<CustFieldSet> fieldSets = null;
		if (isState == 1) {// 企业资源
			fieldSets = cachedService.getComFiledSets(orgId);
		} else {
			fieldSets = cachedService.getPersonFiledSets(orgId);
		}
		List<CustFieldSet> list = new ArrayList<CustFieldSet>();
		for (CustFieldSet filed : fieldSets) {
			if (filed.getIsQuery() == 1 && filed.getEnable() == 1) {
				list.add(filed);
			}
		}
		return list;
	}
	
	/** 
	 * 从缓存读取管理员是否可以对下属成员的客户进行“签约”、“取消签约”操作的设置
	 * @param request 
	 * @create  2016年12月15日 下午2:11:09 lixing
	 * @history  
	 */
	public Integer setAdminSignAuth(HttpServletRequest request){
		ShiroUser user = ShiroUtil.getShiroUser();
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_40041, user.getOrgId());
		Integer adminSignAuth = 0;
		if (!list.isEmpty() && list.get(0) != null){
			adminSignAuth = new Integer(list.get(0).getDictionaryValue());
		}
		return adminSignAuth;
	}
	
	/*******************************客户跟进详情右侧列表********************************/
	
	//客户跟进列表点击 跟进操作
	public List<RestDto> getCustFollowRight_follow(HttpServletRequest request,ShiroUser user,RestDto restDto){
		List<RestDto>restDtos = new ArrayList<RestDto>();
		try{
			String custId = request.getParameter("custId"); // 资源ID
			// 客户类型 跟进
			String custCation = request.getParameter("custCation"); // 跟进数据分类
			//模糊查询处理
	        if(StringUtils.isNotBlank(restDto.getQueryText())){
	        	String queryText = restDto.getQueryText().trim();
	        	restDto.setQueryText(queryText);
	        }
			//  联系时间
			if(StringUtils.isNotBlank(restDto.getdDateType()) && !"0".equals(restDto.getdDateType()) && !"5".equals(restDto.getdDateType())){
				restDto.setLastStartActionDate(getStartDateStr(Integer.parseInt(restDto.getdDateType())));
				restDto.setLastEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			//  下次联系时间
			if(StringUtils.isNotBlank(restDto.getnDateType()) && !"0".equals(restDto.getnDateType()) && !"5".equals(restDto.getnDateType())){
				restDto.setNextStartActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
				restDto.setNextEndActionDate(getEndDateStr(Integer.parseInt(restDto.getnDateType())));
			}
			//  开始联系时间
			if(StringUtils.isNotBlank(restDto.getmDateType()) && !"0".equals(restDto.getmDateType()) && !"5".equals(restDto.getmDateType())){
				restDto.setInitStartFollowDate(getStartDateStr(Integer.parseInt(restDto.getmDateType())));
				restDto.setInitEndFollowDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			if(restDto.getCustCation()==null){
				// 默认选中全部
				restDto.setCustCation(FollowCustEnum.CUST_ALL.getState());
			}
			String allLabels = request.getParameter("allLabels");
			// 管理者查询
			if(user.getIssys() != null && user.getIssys()==1){
				// 所有者查询方式 1-全部 2-只看自己 3-选中查询
				restDto.setOsType(StringUtils.isBlank(restDto.getOsType()) ? "1" : restDto.getOsType());
				if (StringUtils.isNotEmpty(restDto.getAccs()) && "3".equals(restDto.getOsType())) {
					String[] ownerAccs = restDto.getAccs().split(",");
					List<String> owaList = Arrays.asList(ownerAccs);
					restDto.setOwnerAccs(owaList);
				}else if("1".equals(restDto.getOsType())){
						List<String>list = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(),user.getAccount());
						if(list!=null && list.size()>0){
							StringBuffer sb = new StringBuffer();
							for(String str: list){
								sb.append(str);
								sb.append(",");
							}
							if(sb.length()>0){
								sb = sb.deleteCharAt(sb.length() - 1);
							}
							restDto.setAccs(sb.toString());
							restDto.setOwnerAccs(list);
						}					
					}	
			}	
			// 组装选中标签
			if(StringUtils.isNotBlank(allLabels)){
				restDto.setLabels(allLabels.split(","));
			}
			restDto.setState(user.getIsState());  // 0：个人客户,1:企业客户
			restDto.setRoleType(user.getIssys()); // 角色类别：0--销售，1--管理者
			restDto.setOrgId(user.getOrgId());
			restDto.setResCustId(custId);
			restDto.setOwnerAcc(user.getAccount());
			/*restDto.setStartPage(0);
			restDto.setEndPage(6);*/
			followCustCations(restDto, custCation,user);
			//查出多选项查询字段
			List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_1.getState());
			multiSearchList.add(AppConstant.SEARCH_LABEL);
			restDtos = custFollowService.getFollowRightViewByFollowRecordListPage(restDto,multiSearchList);
			return restDtos;
		}catch(Exception e){
			logger.error("客户跟进列表点击 跟进操作 异常！", e);
		}
		return null;
	}
	
	//  我的客户--意向客户 列表点击 跟进操作
	public List<RestDto> getCustFollowRight_mycust(HttpServletRequest request,ShiroUser user,RestDto restDto){
		List<RestDto>restDtos = new ArrayList<RestDto>();
		try{
			String custId = request.getParameter("custId"); // 资源ID
			restDto.setState(user.getIsState());
			restDto.setOrgId(user.getOrgId());
			restDto.setResCustId(custId);
			/*restDto.setStartPage(0);
			restDto.setEndPage(6);*/
			restDto.setOwnerAcc(user.getAccount());
			if (user.getIssys() != null && user.getIssys() == 1) {
				restDto.setOsType(StringUtils.isBlank(restDto.getOsType()) ? "1" : restDto.getOsType());
				if (restDto.getOsType().equals("1")) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					restDto.setOwnerAccs(accs);
					List<String> list = tsmGroupShareinfoService.getMemberIds(user.getOrgId(), user.getAccount());
					restDto.setOwnerUserIds(list);
				} else if (restDto.getOsType().equals("2")) {
					restDto.setOwnerUserIds(Arrays.asList(user.getId()));
					restDto.setOwnerAcc(user.getAccount());
				} else {
					// 处理拥有者
					if (StringUtils.isNotBlank(restDto.getOwnerAccsStr())) {
						restDto.setOwnerAccs(Arrays.asList(restDto.getOwnerAccsStr().split(",")));
						restDto.setOwnerUserIds(Arrays.asList(restDto.getOwnerUserIdsStr().split(",")));
					} else {
						restDto.setOwnerAccs(Arrays.asList(user.getAccount()));
						restDto.setOwnerUserIds(Arrays.asList(user.getId()));
						restDto.setOwnerUserIdsStr(user.getId());
						restDto.setOwnerAccsStr(user.getAccount());
					}
				}
			} else {
				restDto.setOwnerAcc(user.getAccount());
				restDto.setOwnerUserIds(Arrays.asList(user.getId()));
			}
			if (StringUtils.isBlank(restDto.getNoteType())) {
				restDto.setNoteType("1");
			}
			// 淘到客户时间
			if (StringUtils.isNotBlank(restDto.getoDateType()) && !"0".equals(restDto.getoDateType()) && !"5".equals(restDto.getoDateType())) {
				restDto.setPstartDate(getStartDateStr(Integer.parseInt(restDto.getoDateType())));
				restDto.setPendDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理最近联系时间
			if (StringUtils.isNotBlank(restDto.getlDateType()) && !"0".equals(restDto.getlDateType()) && !"5".equals(restDto.getlDateType())
					&& !"6".equals(restDto.getlDateType())) {
				restDto.setStartActionDate(getStartDateStr(Integer.parseInt(restDto.getlDateType())));
				restDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			if (StringUtils.isNotBlank(restDto.getnDateType()) && !"0".equals(restDto.getnDateType()) && !"5".equals(restDto.getnDateType())
					&& !"6".equals(restDto.getnDateType())) {
				restDto.setStartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
				restDto.setEndDate(getEndDateStr((Integer.parseInt(restDto.getnDateType()))));
			}
			if(getCommonOwnerOpenState(user.getOrgId()) == 1){//如果开启共有者  设置查询共有者
				restDto.setCommonAcc(user.getAccount());
				if(StringUtils.isNotBlank(restDto.getCommonAccsStr())){
					restDto.setCommonAccs(Arrays.asList(restDto.getCommonAccsStr().split(",")));
				}
			}
			
			// 处理标签
			if (StringUtils.isNotBlank(restDto.getAllLabels())) {
				restDto.setLabels(restDto.getAllLabels().split(","));
			}
			//查出多选项查询字段
			List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_5.getState());
			multiSearchList.add(AppConstant.SEARCH_LABEL);
			restDtos = custFollowService.getFollowRightViewByMyCustListPage(restDto,multiSearchList);
			return restDtos;
		}catch(Exception e){
			logger.error("我的客户--意向客户 列表点击 跟进操作 异常！",e);
		}
		return null;
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

	//  我的客户--签约客户 列表点击 跟进操作
	public List<RestDto> getCustFollowRight_mysign(HttpServletRequest request,ShiroUser user,RestDto restDto){
		List<RestDto>restDtos = new ArrayList<RestDto>();
		try{
			String custId = request.getParameter("custId"); // 资源ID
			restDto.setOrgId(user.getOrgId());
			restDto.setResCustId(custId);
			restDto.setState(user.getIsState());
			/*restDto.setStartPage(0);
			restDto.setEndPage(6);*/
			if (user.getIssys() != null && user.getIssys() == 1) {
				restDto.setOsType(StringUtils.isBlank(restDto.getOsType()) ? "1" : restDto.getOsType());
				if (restDto.getOsType().equals("1")) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					restDto.setOwnerAccs(accs);
				} else {
					// 处理拥有者
					if (StringUtils.isNotBlank(restDto.getOwnerAccsStr())) {
						restDto.setOwnerAccs(Arrays.asList(restDto.getOwnerAccsStr().split(",")));
					} else {
						restDto.setOwnerAccsStr(user.getAccount());
						restDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
			} else {
				restDto.setOwnerAcc(user.getAccount());
			}
			// 处理最近联系时间
			if (StringUtils.isNotBlank(restDto.getlDateType()) && !"0".equals(restDto.getlDateType()) && !"5".equals(restDto.getlDateType())
					&& !"6".equals(restDto.getlDateType())) {
				restDto.setStartActionDate(getStartDateStr(Integer.parseInt(restDto.getlDateType())));
				restDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 下次联系时间
			if (StringUtils.isNotBlank(restDto.getoDateType()) && !"0".equals(restDto.getoDateType()) && !"5".equals(restDto.getoDateType())
					&& !"6".equals(restDto.getoDateType())) {
				restDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
				restDto.setPendDate(getEndDateStr(Integer.parseInt(restDto.getoDateType())));
			}
			// 最近签约时间
			if (StringUtils.isNotBlank(restDto.getnDateType()) &&  !"0".equals(restDto.getnDateType()) &&  !"5".equals(restDto.getnDateType())) {
				restDto.setStartDate(getStartDateStr(Integer.parseInt(restDto.getnDateType())));
				restDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 默认选中全部标签
			if (StringUtils.isBlank(restDto.getNoteType())) {
				restDto.setNoteType("1");
			}
			// 处理标签
			if (StringUtils.isNotBlank(restDto.getAllLabels())) {
				restDto.setLabels(restDto.getAllLabels().split(","));
			}
			if(getCommonOwnerOpenState(user.getOrgId()) == 1){//如果开启共有者  设置查询共有者
				restDto.setCommonAcc(user.getAccount());
				if(StringUtils.isNotBlank(restDto.getCommonAccsStr())){
					restDto.setCommonAccs(Arrays.asList(restDto.getCommonAccsStr().split(",")));
				}
			}
			//查出多选项查询字段
			List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_6.getState());
			multiSearchList.add(AppConstant.SEARCH_LABEL);
			restDtos = custFollowService.getFollowRightViewByMySignListPage(restDto,multiSearchList);
			return restDtos;
		}catch(Exception e){
			logger.error("我的客户--签约客户 列表点击 跟进操作 异常！",e);
		}
		return null;
	}
	
	//  我的客户--沉默客户 列表点击 跟进操作
	public List<RestDto> getCustFollowRight_mySilent(HttpServletRequest request,ShiroUser user,RestDto restDto){
		List<RestDto>restDtos = new ArrayList<RestDto>();
		try{
			String custId = request.getParameter("custId"); // 资源ID
			restDto.setOrgId(user.getOrgId());
			restDto.setResCustId(custId);
			restDto.setState(user.getIsState());
			/*restDto.setStartPage(0);
			restDto.setEndPage(6);*/
			if (user.getIssys() != null && user.getIssys() == 1) {
				restDto.setOsType(StringUtils.isBlank(restDto.getOsType()) ? "1" : restDto.getOsType());	
				if (restDto.getOsType().equals("1")) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					restDto.setOwnerAccs(accs);
				} else {
					// 处理拥有者
					if (StringUtils.isNotBlank(restDto.getOwnerAccsStr())) {
						restDto.setOwnerAccs(Arrays.asList(restDto.getOwnerAccsStr().split(",")));
					} else {
						restDto.setOwnerAccsStr(user.getAccount());
						restDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
			} else {
				restDto.setOwnerAcc(user.getAccount());
			}
			if ((restDto.getNoteType() == null || restDto.getNoteType().equals("")) && restDto.getDays() == null) {
				restDto.setNoteType("1");
			}
			if (restDto.getDays() != null) {
				restDto.setNoteType("");
			}
			// 处理 合同截止时间
			if (StringUtils.isNotBlank(restDto.getoDateType()) && !"0".equals(restDto.getoDateType()) && !"5".equals(restDto.getoDateType())) {
				restDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
				restDto.setPendDate(getEndDateStr(Integer.parseInt(restDto.getoDateType())));
			}
			// 处理 最近联系时间
			if (StringUtils.isNotBlank(restDto.getlDateType()) && !"0".equals(restDto.getlDateType()) && !"5".equals(restDto.getlDateType())) {
				restDto.setStartActionDate(getStartDateStr(Integer.parseInt(restDto.getlDateType())));
				restDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理标签
			if (StringUtils.isNotBlank(restDto.getAllLabels())) {
				restDto.setLabels(restDto.getAllLabels().split(","));
			}
			restDtos = custFollowService.getFollowRightViewByMySilentListPage(restDto);
			return restDtos;
		}catch(Exception e){
			logger.error("我的客户--沉默客户 列表点击 跟进操作 异常！",e);
		}
		return null;
	}
	
	//  我的客户--流失客户 列表点击 跟进操作
	public List<RestDto> getCustFollowRight_myLos(HttpServletRequest request,ShiroUser user,RestDto restDto){
		List<RestDto>restDtos = new ArrayList<RestDto>();
		try{
			String custId = request.getParameter("custId"); // 资源ID
			restDto.setOrgId(user.getOrgId());
			restDto.setResCustId(custId);
			restDto.setState(user.getIsState());
			/*restDto.setStartPage(0);
			restDto.setEndPage(6);*/
			if (user.getIssys() != null && user.getIssys() == 1) {
				restDto.setOsType(StringUtils.isBlank(restDto.getOsType()) ? "1" : restDto.getOsType());
				if (restDto.getOsType().equals("1")) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					restDto.setOwnerAccs(accs);
				} else {
					// 处理拥有者
					if (StringUtils.isNotBlank(restDto.getOwnerAccsStr())) {
						restDto.setOwnerAccs(Arrays.asList(restDto.getOwnerAccsStr().split(",")));
					} else {
						restDto.setOwnerAccsStr(user.getAccount());
						restDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
			} else {
				restDto.setOwnerAcc(user.getAccount());
			}

			if ((restDto.getNoteType() == null || restDto.getNoteType().equals("")) && restDto.getDays() == null) {
				restDto.setNoteType("1");
			}
			if (restDto.getDays() != null) {
				restDto.setNoteType("");
			}
			// 处理 合同截止时间
			if (StringUtils.isNotBlank(restDto.getoDateType()) && !"0".equals(restDto.getoDateType()) && !"5".equals(restDto.getoDateType())) {
				restDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
				restDto.setPendDate(getEndDateStr(Integer.parseInt(restDto.getoDateType())));
			}
			// 处理 最近联系时间
			if (StringUtils.isNotBlank(restDto.getlDateType()) && !"0".equals(restDto.getlDateType()) && !"5".equals(restDto.getlDateType())) {
				restDto.setStartActionDate(getStartDateStr(Integer.parseInt(restDto.getlDateType())));
				restDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理标签
			if (StringUtils.isNotBlank(restDto.getAllLabels())) {
				restDto.setLabels(restDto.getAllLabels().split(","));
			}
			
			restDtos = custFollowService.getCustFollowRightByMyLosListPage(restDto);
			return restDtos;
		}catch(Exception e){
			logger.error("我的客户--签约客户 列表点击 跟进操作 异常！",e);
		}
		return null;
	}
	
	//  我的客户--全部客户 列表点击 跟进操作
	public List<RestDto> getCustFollowRight_myall(HttpServletRequest request,ShiroUser user,RestDto restDto){
		List<RestDto>restDtos = new ArrayList<RestDto>();
		try{
			String custId = request.getParameter("custId"); // 资源ID
			restDto.setOrgId(user.getOrgId());
			restDto.setResCustId(custId);
			restDto.setState(user.getIsState());
		/*	restDto.setStartPage(0);
			restDto.setEndPage(6);*/
			if (user.getIssys() != null && user.getIssys() == 1) {
				restDto.setOsType(StringUtils.isBlank(restDto.getOsType()) ? "1" : restDto.getOsType());
				if (restDto.getOsType().equals("1")) {
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					restDto.setOwnerAccs(accs);
				} else {
					// 处理拥有者
					if (StringUtils.isNotBlank(restDto.getOwnerAccsStr())) {
						restDto.setOwnerAccs(Arrays.asList(restDto.getOwnerAccsStr().split(",")));
					} else {
						restDto.setOwnerAccsStr(user.getAccount());
						restDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					}
				}
			} else {
				restDto.setOwnerAcc(user.getAccount());
			}

			// 默认选中全部标签
			if (StringUtils.isBlank(restDto.getNoteType())) {
				restDto.setNoteType("1");
			}
			// 默认全部
			if (StringUtils.isBlank(restDto.getCustType())) {
				restDto.setCustType("0");
			}
			// 处理 添加/分配时间
			if (StringUtils.isNotBlank(restDto.getoDateType()) && !"0".equals(restDto.getoDateType()) && !"5".equals(restDto.getoDateType())) {
				restDto.setPstartDate(getStartDateStr(Integer.parseInt(restDto.getoDateType())));
				restDto.setPendDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理 最近联系时间
			if (StringUtils.isNotBlank(restDto.getlDateType()) && !"0".equals(restDto.getlDateType()) && !"5".equals(restDto.getlDateType())) {
				restDto.setStartActionDate(getStartDateStr(Integer.parseInt(restDto.getlDateType())));
				restDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理 下次联系时间
			if (StringUtils.isNotBlank(restDto.getnDateType()) && !"0".equals(restDto.getnDateType()) && !"5".equals(restDto.getnDateType())) {
				restDto.setEndDate(getStartDateStr(Integer.parseInt(restDto.getnDateType())));
				restDto.setStartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理标签
			if (StringUtils.isNotBlank(restDto.getAllLabels())) {
				restDto.setLabels(restDto.getAllLabels().split(","));		
			}
			//查出多选项查询字段
			List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_7.getState());
			multiSearchList.add(AppConstant.SEARCH_LABEL);
			restDtos = custFollowService.getCustFollowRightByMyAllListPage(restDto,multiSearchList);
			return restDtos;
		}catch(Exception e){
			logger.error("我的客户--签约客户 列表点击 跟进操作 异常！",e);
		}
		return null;
	}
	
	
//  我的客户--共有客户 列表点击 跟进操作
	public List<RestDto> getCustFollowRight_myCom(HttpServletRequest request,ShiroUser user,RestDto restDto){
		List<RestDto>restDtos = new ArrayList<RestDto>();
		try{
			String custId = request.getParameter("custId"); // 资源ID
			restDto.setOrgId(user.getOrgId());
			restDto.setResCustId(custId);
			/*restDto.setStartPage(0);
			restDto.setEndPage(6);*/
			restDto.setState(user.getIsState());
			// 设置共有者是自己
			restDto.setCommonAcc(user.getAccount());
			// 默认选中全部标签
			if (StringUtils.isBlank(restDto.getNoteType())) restDto.setNoteType("1");
			// 处理最近联系时间
			if (StringUtils.isNotBlank(restDto.getlDateType()) && restDto.getlDateType() != "0" && restDto.getlDateType() != "5"
					&& restDto.getlDateType() != "6") {
				restDto.setStartActionDate(getStartDateStr(Integer.parseInt(restDto.getlDateType())));
				restDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 下次联系时间
			if (StringUtils.isNotBlank(restDto.getlDateType()) && restDto.getoDateType() != "0" && restDto.getoDateType() != "5"
					&& restDto.getoDateType() != "6") {
				restDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
				restDto.setPendDate(getEndDateStr(Integer.parseInt(restDto.getoDateType())));
			}
			// 签约时间
			if (StringUtils.isNotBlank(restDto.getlDateType()) && restDto.getnDateType() != "0" && restDto.getnDateType() != "5") {
				restDto.setStartDate(getStartDateStr(Integer.parseInt(restDto.getnDateType())));
				restDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			// 处理所有者查询
			if(StringUtils.isNotBlank(restDto.getCommonAccsStr())){
				restDto.setCommonAccs(Arrays.asList(restDto.getCommonAccsStr().split(",")));
			}
			// 处理标签
			if (StringUtils.isNotBlank(restDto.getAllLabels())) {
				restDto.setLabels(restDto.getAllLabels().split(","));
			}
			/*List<String> multiSearchList = new ArrayList<>();
			multiSearchList.add(AppConstant.SEARCH_LABEL);*/
			restDtos = custFollowService.getCustFollowRightByMyComListPage(restDto);
			return restDtos;
		}catch(Exception e){
			logger.error("我的客户--共有客户 列表点击 跟进操作 异常！",e);
		}
		return null;
	}
	
	/**
	 * 个人意向客户数量是否超过最大资源数(返回集合{flag:0-没有超出/1-超出最大数,bSize:操作数量,custNum:当前客户数,maxNum:
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
		
		Map<String, String> setMap = new HashMap<String, String>();
		setMap = getFollowSet(orgId, AppConstant.DATA03);
		Integer maxNum = 0;
		if ("1".equals(setMap.get("isOpen")) && "1".equals(setMap.get("subIsOpen"))) {
			setMap.put("ownerAcc", account);
			setMap.put("orgId", orgId);
			int custNum = comResourceMapper.findCustNum(setMap);// 个人拥有资源数量
			map.put("custNum", custNum);
			maxNum = new Integer(setMap.get("val"));
			if((custNum+bSize) > maxNum){
				map.put("flag", 1);
			}
		}
		map.put("maxNum", maxNum);
		return map;
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
}
