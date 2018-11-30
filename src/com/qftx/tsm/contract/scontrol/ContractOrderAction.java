package com.qftx.tsm.contract.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.pay.api.PayApiFacade;
import com.qftx.pay.api.PayApiRequest;
import com.qftx.pay.api.PayApiTypeEnum;
import com.qftx.tsm.contract.bean.ContractBean;
import com.qftx.tsm.contract.bean.ContractOrderAuthlogBean;
import com.qftx.tsm.contract.bean.ContractOrderBean;
import com.qftx.tsm.contract.bean.ContractOrderDetailBean;
import com.qftx.tsm.contract.dto.ContractDto;
import com.qftx.tsm.contract.dto.ContractFileDto;
import com.qftx.tsm.contract.dto.ContractOrderAddDto;
import com.qftx.tsm.contract.dto.ContractOrderDto;
import com.qftx.tsm.contract.service.ContractService;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.follow.bean.CustSaleChanceBean;
import com.qftx.tsm.follow.service.CustSaleChanceService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.main.service.MainService;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.Product;

import net.sf.json.JSONObject;

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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

 /** 
 * 订单维护
 * @author: Administrator
 * @since: 2015年12月29日  下午3:51:48
 * @history:
 */
@Controller
@RequestMapping("/contract/order")
public class ContractOrderAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ContractOrderAction.class);
    @Autowired
    private ContractService contractService;
    @Autowired
    private CachedService cachedService;
    @Autowired
    private TsmMessageService tsmMessageService;
    @Autowired
    private ResCustInfoService resCustInfoService;
    @Autowired
    private LogUserOperateService logUserOperateService;
    @Resource
	private TsmGroupShareinfoService tsmGroupShareinfoService;
    @Autowired
    private CustSaleChanceService custSaleChanceService;
	@Autowired
	private MainService mainService;
	@Autowired
	private PayApiFacade payApiFacade;
    
    @RequestMapping("/list")
    public String index(HttpServletRequest request,ContractOrderDto contractOrderDto) {
        String pageView = request.getParameter("pageView");
        try {
          ShiroUser user = ShiroUtil.getShiroUser();
		  if(StringUtils.isNotBlank(pageView)){
			  pageView = "contract/order_check_list";
			  String type = request.getParameter("type");
			  request.setAttribute("type", StringUtils.isBlank(type) ? "1" : type);
		  }else{
			  pageView = "contract/order_list";
		  }
		  request.setAttribute("shiroUser", user);
		  request.setAttribute("contractOrderDto", contractOrderDto);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
    	return pageView;
    }
    
    @RequestMapping("/data")
    @ResponseBody
    public Map<String, Object> data(HttpServletRequest request,ContractOrderDto contractOrderDto){
    	Map<String, Object> map = new HashMap<String, Object>();
    	String pageView = request.getParameter("pageView");
    	try {
    	   ShiroUser user = ShiroUtil.getShiroUser();
      	   contractOrderDto.setOrgId(user.getOrgId());
      	   String isPageSearch = request.getParameter("isPageSearch");
  			if(StringUtils.isBlank(isPageSearch) && StringUtils.isNotBlank(pageView)){
  				contractOrderDto.setAuthState(1);
  			}
  			if(StringUtils.isBlank(pageView)){
  				//订单管理
  				if(user.getIssys() != null && user.getIssys() == 1){
  				   contractOrderDto.setOsType(StringUtils.isBlank(contractOrderDto.getOsType()) ? "1" : contractOrderDto.getOsType());
  	    		   if(contractOrderDto.getOsType().equals("1")){
//  	    			   contractOrderDto.setAdminAcc(user.getAccount());
  	    			   List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
  	    			   if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
  	    			   contractOrderDto.setOwnerAccs(accs);
  	    		   }else if(contractOrderDto.getOsType().equals("2")){
  	    			   contractOrderDto.setOwnerAcc(user.getAccount());
  	    		   }else{
  	    			   if(StringUtils.isNotBlank(contractOrderDto.getOwnerAccStr())){
  		    			   contractOrderDto.setOwnerAccs(Arrays.asList(contractOrderDto.getOwnerAccStr().split(",")));
  		    		   }else{
  		    			   contractOrderDto.setOwnerAccStr(user.getAccount());
  		    			   contractOrderDto.setOwnerAccs(Arrays.asList(user.getAccount()));
  		    		   }
  	    		   }
  	    	    }else{
  	    		   contractOrderDto.setOwnerAcc(user.getAccount());
  	    	    }
  				if(getCommonOwnerOpenState(user.getOrgId()) == 1){//如果开启共有者  设置查询共有者
  					contractOrderDto.setCommonAcc(user.getAccount());
  				}
  				if(StringUtils.isBlank(contractOrderDto.getNoteType())) contractOrderDto.setNoteType("1");
  			}else{
  				//订单审核
  				if(user.getIssys() != null && user.getIssys() == 1){
//  	    		   contractOrderDto.setAdminAcc(user.getAccount());
  	    		   List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
  	    		   if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
      			   contractOrderDto.setOwnerAccs(accs);
  	    		   if(StringUtils.isNotBlank(contractOrderDto.getOwnerAccStr())){
  	    			   contractOrderDto.setOwnerAccs(Arrays.asList(contractOrderDto.getOwnerAccStr().split(",")));
  	    		   }else if(StringUtils.isBlank(pageView)){
  	    			   contractOrderDto.setOwnerAccStr(user.getAccount());
  	    			   contractOrderDto.setOwnerAccs(Arrays.asList(user.getAccount()));
  	    		   }
  	    	    }else{
  	    		   contractOrderDto.setOwnerAcc(user.getAccount());
  	    	    }
  			}
  			
  			if(StringUtils.isNotBlank(contractOrderDto.getUserIdsStr())){
  				contractOrderDto.setUserIds(Arrays.asList(contractOrderDto.getUserIdsStr().split(",")));
  			}
  			
      	   if(contractOrderDto.getsDateType() != null && contractOrderDto.getsDateType() != 0 && contractOrderDto.getsDateType() != 5){
      		    contractOrderDto.setStartDate(getStartDateStr(contractOrderDto.getsDateType()));
      		    contractOrderDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
  	   	   }
  	   		
  	   	   if(contractOrderDto.geteDateType() != null && contractOrderDto.geteDateType() != 0 && contractOrderDto.geteDateType() != 5){
  		   		if(contractOrderDto.geteDateType() == 4){
  		   			contractOrderDto.setStartEffecDate(getStartDateStr(contractOrderDto.geteDateType()));
  		   			contractOrderDto.setEndEffecDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
  		   		}else{
  		   		contractOrderDto.setStartEffecDate(getStartDateStr(contractOrderDto.geteDateType()));
		   			contractOrderDto.setEndEffecDate(getEndDateStr(contractOrderDto.geteDateType()));
  		   		}
  	   	   }
  	   		
  	   	   if(contractOrderDto.getiDateType() != null && contractOrderDto.getiDateType() != 0 && contractOrderDto.getiDateType() != 5){
  		   		if(contractOrderDto.getiDateType() == 4){
  		   			contractOrderDto.setStartInvalidDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
  		   			contractOrderDto.setEndInvalidDate(getEndDateStr(contractOrderDto.getiDateType()));
  		   		}else{
  		   			contractOrderDto.setStartInvalidDate(getStartDateStr(contractOrderDto.getiDateType()));
		   			contractOrderDto.setEndInvalidDate(getEndDateStr(contractOrderDto.getiDateType()));
  		   		}
  	   	   }
  	   	   
  	   	   contractOrderDto.setShowCheck(StringUtils.isNotBlank(pageView) ? "2" : "1");
  	   	   
  	   	  List<ContractOrderDto> orderDtos = contractService.findContractOrderListPage(contractOrderDto);
	   	  Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
	   	  for (ContractOrderDto cod : orderDtos) {
				cod.setSaleAcc(StringUtils.isNotBlank(cod.getSaleAcc()) ? nameMap.get(cod.getSaleAcc()) : "");
				cod.setUserName(StringUtils.isNotBlank(cod.getUserId()) ? nameMap.get(cod.getUserId()) : "");
	   	  }
	      map.put("list", orderDtos);
	   	  map.put("item", contractOrderDto);
	   	  map.put("shiroUser", ShiroUtil.getShiroUser());
		  int commonOnwerSign = 0;
	      if(getCommonOwnerOpenState(user.getOrgId()) == 1){
	    	  commonOnwerSign = getCommonOwnerSignAuth(user.getOrgId());
	      }
	      map.put("commonOnwerSign", commonOnwerSign);
		} catch (Exception e) {
			logger.error("订单列表异常！",e);
		}
    	return map;
    }
    
    @RequestMapping("/check")
	@ResponseBody
	public String check(String id, String code) {
    	String re = "1";
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
    		List<ContractOrderBean> list = contractService.getContractOrderBeansByCode(code, user.getOrgId());
    		if (StringUtils.isNotBlank(id)) {
    			if(list.size()>0){
    				for (ContractOrderBean cob : list) {
    					if (!cob.getId().equals(id)) {
							re = "0";
						}
					}
    			}
    		}else {
				if (list.size() > 0) {
					re = "0";
				}
			}
    	} catch (Exception e) {
			logger.error("订单号校验失败", e);
		}
    	return re;
    }
    
    /** 
     * 订单管理 底部统计
     * @param request
     * @param contractOrderDto
     * @return 
     * @create  2016年5月9日 下午2:49:46 lixing
     * @history  
     */
    @RequestMapping("/count")
    public String count(HttpServletRequest request,ContractOrderDto contractOrderDto){
    	Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
    	try {
    	   ShiroUser user = ShiroUtil.getShiroUser();
      	   contractOrderDto.setOrgId(user.getOrgId());
      	   if(user.getIssys() != null && user.getIssys() == 1){
			   contractOrderDto.setOsType(StringUtils.isBlank(contractOrderDto.getOsType()) ? "1" : contractOrderDto.getOsType());
	  		   if(contractOrderDto.getOsType().equals("1")){
//	  			   contractOrderDto.setAdminAcc(user.getAccount());
	  			   List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
	  			   if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
  			       contractOrderDto.setOwnerAccs(accs);
	  		   }else if(contractOrderDto.getOsType().equals("2")){
	  			   contractOrderDto.setOwnerAcc(user.getAccount());
	  		   }else{
	  			   if(StringUtils.isNotBlank(contractOrderDto.getOwnerAccStr())){
		    			   contractOrderDto.setOwnerAccs(Arrays.asList(contractOrderDto.getOwnerAccStr().split(",")));
		    		   }else{
		    			   contractOrderDto.setOwnerAccStr(user.getAccount());
		    			   contractOrderDto.setOwnerAccs(Arrays.asList(user.getAccount()));
		    		   }
	  		   }
	  	    }else{
	  		   contractOrderDto.setOwnerAcc(user.getAccount());
	  	    }
      	   
      	   if(StringUtils.isNotBlank(contractOrderDto.getUserIdsStr())){
				contractOrderDto.setUserIds(Arrays.asList(contractOrderDto.getUserIdsStr().split(",")));
			}
      	   
      	   if(contractOrderDto.getsDateType() != null && contractOrderDto.getsDateType() != 0 && contractOrderDto.getsDateType() != 5){
      		    contractOrderDto.setStartDate(getStartDateStr(contractOrderDto.getsDateType()));
      		    contractOrderDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
  	   	   }
  	   		
      	  if(contractOrderDto.geteDateType() != null && contractOrderDto.geteDateType() != 0 && contractOrderDto.geteDateType() != 5){
		   		if(contractOrderDto.geteDateType() == 4){
		   			contractOrderDto.setStartEffecDate(getStartDateStr(contractOrderDto.geteDateType()));
  		   			contractOrderDto.setEndEffecDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		   		}else{
		   		contractOrderDto.setStartEffecDate(getStartDateStr(contractOrderDto.geteDateType()));
		   			contractOrderDto.setEndEffecDate(getEndDateStr(contractOrderDto.geteDateType()));
		   		}
	   	   }
	   		
	   	   if(contractOrderDto.getiDateType() != null && contractOrderDto.getiDateType() != 0 && contractOrderDto.getiDateType() != 5){
		   		if(contractOrderDto.getiDateType() == 4){
		   			contractOrderDto.setStartInvalidDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		   			contractOrderDto.setEndInvalidDate(getEndDateStr(contractOrderDto.getiDateType()));
		   		}else{
		   			contractOrderDto.setStartInvalidDate(getStartDateStr(contractOrderDto.getiDateType()));
		   			contractOrderDto.setEndInvalidDate(getEndDateStr(contractOrderDto.getiDateType()));
		   		}
	   	   }
	   	   
  	   	   contractOrderDto.setShowCheck("1");
	  	   if(getCommonOwnerOpenState(user.getOrgId()) == 1){//如果开启共有者  设置查询共有者
			 contractOrderDto.setCommonAcc(user.getAccount());
		   }
	  	   if(StringUtils.isBlank(contractOrderDto.getNoteType())) contractOrderDto.setNoteType("1");
  	   	   List<ContractOrderDto> orderDtos = contractService.getContractOrderCountData(contractOrderDto);
  	   	   BigDecimal totalMoney = BigDecimal.valueOf(0);
  	   	   for(ContractOrderDto orderDto : orderDtos){
  	   		   totalMoney = totalMoney.add(orderDto.getMoney() == null ? BigDecimal.valueOf(0) : orderDto.getMoney());
  	   		   map.put(orderDto.getAuthState().toString(), orderDto.getMoney().divide(BigDecimal.valueOf(10000),2,BigDecimal.ROUND_HALF_EVEN));
  	   	   }
  	   	   map.put("5", totalMoney.divide(BigDecimal.valueOf(10000),2,BigDecimal.ROUND_HALF_EVEN));
  	   	   request.setAttribute("countData", map);
		} catch (Exception e) {
			logger.error("订单管理底部统计出错!"+e.getMessage());
		}
    	return "contract/order_count";
    }
    
    /** 
     * 跳转新增订单
     * @param request
     * @param fromPage
     * @param contractId
     * @return 
     * @create  2015年12月28日 下午4:44:56 lixing
     * @history  
     */
    @RequestMapping("/addOrder")
    public String addOrder(HttpServletRequest request,String fromPage,String custId,String contractId,String custName,String module){
    	try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<Product> products = cachedService.getOpionProduct(user.getOrgId());
			if(StringUtils.isNotBlank(contractId)){
				ContractDto cDto = contractService.getContractInfoByIdAndOrg(contractId, user.getOrgId());
				request.setAttribute("cDto", cDto);
			}
			ResCustInfoBean custInfoBean = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custId);
			request.setAttribute("custInfoBean", custInfoBean);
			List<ContractBean> contracts = contractService.getContractBeansByCustId(user.getOrgId(), custId);
			List<CustSaleChanceBean> saleChances = custSaleChanceService.getSaleChanceByCustId(user.getOrgId(), custId);
			request.setAttribute("saleChances", saleChances);
			request.setAttribute("products", products);
			request.setAttribute("fromPage", fromPage);
			request.setAttribute("contracts", contracts);
			request.setAttribute("module", module);
			request.setAttribute("tsmUpLoadServiceUrl", ConfigInfoUtils.getStringValue("tsm_upload_service_url")); // 上传服务器路径
			setCustomFiled(user, request);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
    	return "contract/order_add";
    }

    @RequestMapping("/toEdit")
    public String toEdit(HttpServletRequest request,String fromPage,String orderId){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
    		List<Product> products = cachedService.getOpionProduct(user.getOrgId());
			ContractOrderBean orderBean = contractService.getOrderInfoByIdAndOrg(orderId, user.getOrgId());
			if(StringUtils.isNotBlank(orderBean.getCaId())){
				ContractDto dto = contractService.getContractInfoByIdAndOrg(orderBean.getCaId(), user.getOrgId());
				request.setAttribute("cDto", dto);
			}
			List<ContractOrderDetailBean> detailBeans = contractService.getOrderDetailBeans(orderId, user.getOrgId());
			List<ContractBean> contracts = contractService.getContractBeansByCustId(user.getOrgId(), orderBean.getCustId());
			List<ContractFileDto> conFileDtos = contractService.getContractFiles(orderId, user.getOrgId());
			List<CustSaleChanceBean> saleChances = custSaleChanceService.getSaleChanceByCustId(user.getOrgId(), orderBean.getCustId());
			request.setAttribute("saleChances", saleChances);
			request.setAttribute("products", products);
    		request.setAttribute("fromPage", fromPage);
    		request.setAttribute("orderBean", orderBean);
    		request.setAttribute("detailBeans", detailBeans);
    		request.setAttribute("contracts", contracts);
    		request.setAttribute("conFileDtos", conFileDtos);
    		setCustomFiled(user, request);
    		request.setAttribute("tsmUpLoadServiceUrl", ConfigInfoUtils.getStringValue("tsm_upload_service_url")); // 上传服务器路径
		} catch (Exception e) {
			throw new SysRunException(e);
		}
    	return "contract/order_update";
    }
    
    
    /** 
     * 跳转审核
     * @param request
     * @param orderId
     * @return 
     * @create  2015年12月29日 下午2:01:35 lixing
     * @history  
     */
    @RequestMapping("/toCheck")
    public String toCheck(HttpServletRequest request,String orderId,String type){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
    		ContractOrderBean orderBean = contractService.getOrderInfoByIdAndOrg(orderId, user.getOrgId());
    		if(StringUtils.isNotBlank(orderBean.getCaId())){
	    		ContractDto dto = contractService.getContractInfoByIdAndOrg(orderBean.getCaId(), user.getOrgId());
	    		request.setAttribute("cDto", dto);
    		}
    		List<ContractOrderDetailBean> detailBeans = contractService.getOrderDetailBeans(orderId, user.getOrgId());
    		ResCustInfoBean custInfo = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), orderBean.getCustId());
    		List<ContractFileDto> conFileDtos = contractService.getContractFiles(orderId, user.getOrgId());
    		// 从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
			if (StringUtils.isNotBlank(custInfo.getLastOptionId())) {
				request.setAttribute("lastOptionName", saleProcMap.get(custInfo.getLastOptionId()));
			}
    		request.setAttribute("type", StringUtils.isBlank(type) ? "1" : type);
    		request.setAttribute("orderBean", orderBean);
    		request.setAttribute("detailBeans", detailBeans);
    		request.setAttribute("custInfo", custInfo);
    		request.setAttribute("shiroUser", user);
    		request.setAttribute("conFileDtos", conFileDtos);
    		setCustomFiled(user, request);
    		setIsReplaceWord(request);
    		request.setAttribute("tsmUpLoadServiceUrl", ConfigInfoUtils.getStringValue("tsm_upload_service_url")); // 上传服务器路径
    		List<CustSaleChanceBean> saleChances = custSaleChanceService.getSaleChanceByCustId(user.getOrgId(), orderBean.getCustId());
			request.setAttribute("saleChances", saleChances);
    	} catch (Exception e) {
			throw new SysRunException(e);
		}
    	return "contract/order_check";
    }
    
    
    @RequestMapping("/deleteOrder")
    @ResponseBody
    public String deleteOrder(HttpServletRequest request,HttpServletResponse response){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
    		String idsStr = request.getParameter("idsStr");
    		ContractOrderDto cod = new ContractOrderDto();
    		cod.setOrgId(user.getOrgId());
    		cod.setIds(Arrays.asList(idsStr.split(",")));
    		contractService.deleteOrderBatch(cod);
    		return "1";
		} catch (Exception e) {
			logger.error("删除订单失败");
			return "-1";
		}
    }
    
    /** 
     * 审核
     * @param request
     * @param response
     * @param orderBean 
     * @create  2015年12月29日 下午3:51:50 lixing
     * @history  
     */
    @RequestMapping("/saveCheck")
    @ResponseBody
    public String saveCheck(HttpServletRequest request,HttpServletResponse response,ContractOrderBean orderBean){
    	try {
			ShiroUser user = ShiroUtil.getShiroUser();
			ContractOrderDto cod = new ContractOrderDto();
			cod.setAuthState(orderBean.getAuthState());
			cod.setAuthDesc(orderBean.getAuthDesc());
			cod.setOrgId(user.getOrgId());
			List<String> ids = new ArrayList<String>();
			ids.add(orderBean.getId());
			cod.setIds(ids);
			contractService.CheckOrder(cod,user);
    		return "1";
		} catch (Exception e) {
			logger.error("审核订单失败");
			return "-1";
		}
    }
    
    /** 
     * 批量审核
     * @param request
     * @param response 
     * @create  2015年12月29日 下午4:37:33 lixing
     * @history  
     */
    @RequestMapping("/batchCheck")
    @ResponseBody
    public String batchCheck(HttpServletRequest request,HttpServletResponse response){
    	try {
    		Integer authState = Integer.parseInt(request.getParameter("authState"));
    		String idsStr = request.getParameter("idsStr");
    		ShiroUser user = ShiroUtil.getShiroUser();
    		ContractOrderDto cod = new ContractOrderDto();
    		cod.setOrgId(user.getOrgId());
    		cod.setAuthState(authState);
    		cod.setIds(Arrays.asList(idsStr.split(",")));
			contractService.CheckOrder(cod,user);
    		return "1";
    	} catch (Exception e) {
			logger.error("批量审核订单失败",e);
			return "-1";
		}
    }
    
    /** 
     * 订单查看
     * @param request
     * @param orderId
     * @return 
     * @create  2015年12月29日 下午3:53:38 lixing
     * @history  
     */
    @RequestMapping("/orderView")
    public String orderView(HttpServletRequest request,String orderId){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
    		ContractOrderBean orderBean = contractService.getOrderInfoByIdAndOrg(orderId, user.getOrgId());
    		if(StringUtils.isNotBlank(orderBean.getCaId())){
    			ContractDto dto = contractService.getContractInfoByIdAndOrg(orderBean.getCaId(), user.getOrgId());
    			request.setAttribute("cDto", dto);
    		}
    		ResCustInfoBean custInfo = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), orderBean.getCustId());
    		List<ContractOrderDetailBean> detailBeans = contractService.getOrderDetailBeans(orderId, user.getOrgId());
    		List<ContractFileDto> conFileDtos = contractService.getContractFiles(orderId, user.getOrgId());
    		ContractOrderAuthlogBean coab = new ContractOrderAuthlogBean();
    		coab.setOrgId(user.getOrgId());
    		coab.setOrderId(orderId);
    		List<ContractOrderAuthlogBean> authlogBeans = contractService.getAuthlogBeans(coab);
    		request.setAttribute("orderBean", orderBean);
    		request.setAttribute("detailBeans", detailBeans);
    		request.setAttribute("authlogBeans", authlogBeans);
    		request.setAttribute("custInfo", custInfo);
    		request.setAttribute("conFileDtos", conFileDtos);
    		setCustomFiled(user, request);
    		// 从缓存读取销售进程列表
            List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
            Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
            if(StringUtils.isNotBlank(custInfo.getLastOptionId())){
            	request.setAttribute("lastOptionName", saleProcMap.get(custInfo.getLastOptionId()));
            }
            request.setAttribute("tsmUpLoadServiceUrl", ConfigInfoUtils.getStringValue("tsm_upload_service_url")); // 上传服务器路径
            List<CustSaleChanceBean> saleChances = custSaleChanceService.getSaleChanceByCustId(user.getOrgId(), orderBean.getCustId());
			request.setAttribute("saleChances", saleChances);
    	} catch (Exception e) {
			throw new SysRunException(e);
		}
    	return "contract/order_view";
    }
    
    
    /** 
     * 上报订单
     * @param request
     * @param response
     * @param orderId 
     * @create  2015年12月29日 下午4:25:39 lixing
     * @history  
     */
    @RequestMapping("/reportOrder")
    @ResponseBody
    public String reportOrder(HttpServletRequest request,HttpServletResponse response,String orderId){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
    		contractService.reportOrder(orderId, user.getOrgId(),user.getAccount(),user.getName());
    		return "1";
		} catch (Exception e) {
			logger.error("上报订单失败！");
			return "-1";
		}
    }
    
    /** 
     * 撤回订单
     * @param request
     * @param response
     * @param orderId 
     * @create  2015年12月29日 下午4:25:39 lixing
     * @history  
     */
    @RequestMapping("/rebackOrder")
    @ResponseBody
    public String rebackOrder(HttpServletRequest request,HttpServletResponse response,String orderId){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
    		contractService.rebackOrder(orderId, user.getOrgId());
    		return "1";
		} catch (Exception e) {
			logger.error("撤回订单失败！");
			return "-1";
		}
    }
    
    @RequestMapping("/cancelledOrder")
    @ResponseBody
    public String cancelledOrder(HttpServletRequest request,HttpServletResponse response,String orderId){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
    		contractService.cancelledOrder(orderId, user.getOrgId());
    		return "1";
		} catch (Exception e) {
			logger.error("作废订单失败！");
			return "-1";
		}
    }
    
    /** 
     * 保存订单
     * @param request
     * @param response
     * @param orderAddDto 
     * @create  2015年12月28日 下午4:44:25 lixing
     * @history  
     */
    @RequestMapping("/saveOrder")
    @ResponseBody
    public String saveOrder(HttpServletRequest request,HttpServletResponse response,ContractOrderAddDto orderAddDto){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
//    		ContractDto dto = contractService.getContractInfoByIdAndOrg(orderAddDto.getOrderBean().getCaId(), user.getOrgId());
//    		if(dto != null && dto.getIsDel() == 0){
    		Date opDate = new Date(); 
			ContractOrderBean orderBean = orderAddDto.getOrderBean();
			orderBean.setId(SysBaseModelUtil.getModelId());
			orderBean.setOrgId(user.getOrgId());
			orderBean.setGroupId(user.getGroupId());
			orderBean.setInputtime(opDate);
			orderBean.setUserId(user.getAccount());
			List<ContractOrderDetailBean> orderDetailBeans = orderAddDto.getOrderDetailBeans();
			List<ContractOrderDetailBean> removeBeans = new ArrayList<ContractOrderDetailBean>();
			for(ContractOrderDetailBean cdb : orderDetailBeans){
				if(StringUtils.isBlank(cdb.getProductId())){
					removeBeans.add(cdb);
				}else{
					cdb.setOrderId(orderBean.getId());
					cdb.setOrgId(user.getOrgId());
					cdb.setCode(orderBean.getCode());
					cdb.setInputtime(opDate);
					cdb.setIsDel(0);
					cdb.setId(SysBaseModelUtil.getModelId());
				}
			}
			for (ContractOrderDetailBean removeBean : removeBeans) {
				orderDetailBeans.remove(removeBean);
			}
			String module = request.getParameter("module");
			String fileIdsStr = request.getParameter("fileIdsStr");
			List<String> fileIds = new ArrayList<String>();
			if (StringUtils.isNotBlank(fileIdsStr)) {
				for (String fileId : fileIdsStr.split(",")) {
					if (StringUtils.isNotBlank(fileId)) {
						fileIds.add(fileId);
					}
				}
			}
			contractService.addOrder(orderBean,orderDetailBeans,fileIds,module);
			if(orderBean.getAuthState() != null  && orderBean.getAuthState() == 1){ // 未审核发送消息
				tsmMessageService.createAuditOrderMessage(orderBean.getId(),user.getOrgId(),user.getAccount(),user.getName());
				//根据配置，上报订单发送弹幕，需加上订单金额
				if(mainService.getBarrageSign(user.getOrgId())==1&&mainService.getBarrageSign3(user.getOrgId())==1){
					String retName="";
					if(mainService.getBarrageSign2(user.getOrgId())==0){
						retName=((orderBean.getCompany() == null || "".equals(orderBean.getCompany()))? orderBean.getCustName() : orderBean.getCompany());	
					}
					if(retName==null){
						retName="";
					}
					mainService.sendSingBarrage("恭喜"+(user.getName() == null ? user.getAccount() : user.getName())+"成功签约"+retName+"!", "1",user.getOrgId(),user.getAccount(),user.getName(),String.valueOf(orderBean.getMoney()),1);
					List<DataDictionaryBean> dlist0 = cachedService.getDirList(AppConstant.DATA_50047, user.getOrgId());//红包设置
					if ((!dlist0.isEmpty() && !"0".equals(dlist0.get(0).getDictionaryValue()))) {
						PayApiRequest payrequest=new PayApiRequest();
						
						payrequest.setOrgId(user.getOrgId());
						payrequest.setUserAcc(user.getAccount());
						payrequest.setPayApiEnum(PayApiTypeEnum.SIGN);
						payrequest.setMoney(Double.valueOf(dlist0.get(0).getDictionaryValue()));
						payrequest.setDesc((user.getName() == null ? user.getAccount() : user.getName())+ "获得签约奖励红包"+dlist0.get(0).getDictionaryValue()+"元！");
						payrequest.setData("恭喜"+(user.getName() == null ? user.getAccount() : user.getName())+"成功签约"+retName+orderBean.getMoney());
						BaseJsonResult json=payApiFacade.pay(payrequest);
						if((boolean) json.get("status")==false){
							logger.error("-------调用pay接口返回结果：--------"+JSONObject.fromObject(json)+"时间："+new Date()); 	
						}
			        }
				}
				
			}
			return "1";
//    		}else{
//    			return "2";
//    		}
		} catch (Exception e) {
			logger.error("保存订单失败",e);
			return "-1";
		}
    }
    
    
    /** 
     * 保存编辑
     * @param request
     * @param response
     * @param orderAddDto 
     * @create  2015年12月29日 下午4:36:49 lixing
     * @history  
     */
    @RequestMapping("/saveEdit")
    @ResponseBody
    public String saveEdit(HttpServletRequest request,HttpServletResponse response,ContractOrderAddDto orderAddDto){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
    		Date opDate = new Date();
			ContractOrderBean orderBean = orderAddDto.getOrderBean();
			orderBean.setOrgId(user.getOrgId());
			orderBean.setUpdatetime(opDate);
			List<ContractOrderDetailBean> orderDetailBeans = orderAddDto.getOrderDetailBeans();
			String delDetailIdsStr = request.getParameter("delDetailIdsStr");
			List<String> delDetailIds = new ArrayList<String>();
			if(StringUtils.isNotBlank(delDetailIdsStr)){
				delDetailIds = Arrays.asList(delDetailIdsStr.split(","));
			}
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
			contractService.editOrder(orderBean, orderDetailBeans, delDetailIds,fileIds,delIdsList);
			if(orderBean.getAuthState() != null  && orderBean.getAuthState() == 1){ // 未审核发送消息
				tsmMessageService.createAuditOrderMessage(orderBean.getId(),user.getOrgId(),user.getAccount(),user.getName());
			}
    		return "1";
    	} catch (Exception e) {
    		logger.error("编辑订单失败",e);
    		return "-1";
		}
    }
    
   	@InitBinder
    public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
