package com.qftx.tsm.contract.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.contract.dto.ContractOrderDetailDto;
import com.qftx.tsm.contract.service.ContractService;
import com.qftx.tsm.sys.bean.Product;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 订单详情
 */
@Controller
@RequestMapping("/contract/orderinfo")
public class ContractOrderInfoAction extends BaseAction {
    
	private Log log = LogFactory.getLog(ContractOrderInfoAction.class);
	
	@Autowired
    private ContractService contractService;
    @Autowired
    private CachedService cachedService;
    @Resource
	private TsmGroupShareinfoService tsmGroupShareinfoService;
    
    @RequestMapping("/list")
    public String index(HttpServletRequest request,ContractOrderDetailDto orderDetailDto) {
    	try {
        	ShiroUser user = ShiroUtil.getShiroUser();
        	List<Product> products = cachedService.getOpionProduct(user.getOrgId());
        	request.setAttribute("products", products);
        	request.setAttribute("orderDetailDto", orderDetailDto);
        	request.setAttribute("shiroUser", ShiroUtil.getShiroUser());
        	return "contract/order_info";
		} catch (Exception e) {
			throw new SysRunException(e);
		}
    }

    @RequestMapping("/data")
    @ResponseBody
    public Map<String, Object> data(HttpServletRequest request,ContractOrderDetailDto orderDetailDto) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
        	orderDetailDto.setOrgId(user.getOrgId());
        	if(user.getIssys() != null && user.getIssys() == 1){
        		orderDetailDto.setOsType(StringUtils.isBlank(orderDetailDto.getOsType()) ? "1" : orderDetailDto.getOsType());
        		if(orderDetailDto.getOsType().equals("1")){
        			List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
        			if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
        			orderDetailDto.setOwnerAccs(accs);
        		}else if(orderDetailDto.getOsType().equals("2")){
        			orderDetailDto.setOwnerAcc(user.getAccount());
        		}else{
        			if(StringUtils.isNotBlank(orderDetailDto.getOwnerAccsStr())){
    					orderDetailDto.setOwnerAccs(Arrays.asList(orderDetailDto.getOwnerAccsStr().split(",")));
    		    	}else{
    		    		orderDetailDto.setOwnerAccsStr(user.getAccount());
    					orderDetailDto.setOwnerAccs(Arrays.asList(user.getAccount()));
    		    	}
        		}
        	}else{
        		orderDetailDto.setOwnerAcc(user.getAccount());
        	}
        	
        	if(StringUtils.isNotBlank(orderDetailDto.getUserIdsStr())){
        		orderDetailDto.setUserIds(Arrays.asList(orderDetailDto.getUserIdsStr().split(",")));
        	}
        	
        	if(orderDetailDto.getsDateType() != null && orderDetailDto.getsDateType() != 0 && orderDetailDto.getsDateType() != 5){
        		orderDetailDto.setStartDate(getStartDateStr(orderDetailDto.getsDateType()));
        		orderDetailDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
 	   	    }
        	if(getCommonOwnerOpenState(user.getOrgId()) == 1){
        		orderDetailDto.setCommonAcc(user.getAccount());
        	}
        	if(StringUtils.isBlank(orderDetailDto.getNoteType())) orderDetailDto.setNoteType("1");
        	List<ContractOrderDetailDto> orderDetailDtos = contractService.getContractOrderDetailListPage(orderDetailDto);
        	Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
	      	for (ContractOrderDetailDto cod : orderDetailDtos) {
	  			cod.setUserName(StringUtils.isNotBlank(cod.getUserId()) ? nameMap.get(cod.getUserId()) : "");
	  			cod.setOwnerAcc(StringUtils.isNotBlank(cod.getOwnerAcc()) ? nameMap.get(cod.getOwnerAcc()) : "");
	      	}
        	map.put("list", orderDetailDtos);
        	map.put("item", orderDetailDto);
        	map.put("shiroUser", ShiroUtil.getShiroUser());
		} catch (Exception e) {
			log.error("订单详情列表异常！",e);
		}
    	return map;
    }
}
