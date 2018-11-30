package com.qftx.tsm.sys.scontrol;


import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.dto.HighSearchChildDto;
import com.qftx.tsm.sys.dto.HighSearchDto;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 高级查询
 */
@Controller
@RequestMapping("/highSearch")
public class HighSearchController{
	Logger logger=Logger.getLogger(HighSearchController.class);
	@Autowired private CachedService cachedService;

	@RequestMapping("/toPage")
	public String searchSetPage(HttpServletRequest request){
		try{
			String module = request.getParameter("module"); // 模块类型值
			request.setAttribute("module", module);
		}catch(Exception e){
			logger.error("/highSearch/toPage 异常！", e);
		}	
		return "/tsm/sys/advancedSearch/advancedQuery";
	}
	
	@RequestMapping("/toPageJson")
	@ResponseBody
	public String searchSetPageJson(HttpServletRequest request){
		List<HighSearchDto> dataList = null;
		String jsonStr = null;
		try{
			String module = request.getParameter("module"); // 模块类型值
			ShiroUser user = ShiroUtil.getShiroUser();
			dataList = cachedService.getHighSearchAccount(module,user.getOrgId(),user.getIssys(),user.getAccount());					
			jsonStr = JsonUtil.getJsonString(dataList);
		}catch(Exception e){
			logger.error("/highSearch/toPageJson 异常！", e);
		}	
		return jsonStr;
	}
	
	@RequestMapping("/toOtherPageJson")
	@ResponseBody
	public String searchOtherSetPageJson(HttpServletRequest request){
		List<HighSearchDto> dataList = new ArrayList<HighSearchDto>();
		String jsonStr = null;
		String[] silentAdminSearchs = {"上次联系时间#startActionDate#2","合同截止时间#pstartDate#2","所有者#ownerAccsStr#5","资源分组#resGroupId#7"};
		String[] silentSearchs = {"上次联系时间#startActionDate#2","合同截止时间#pstartDate#2","资源分组#resGroupId#7"};
		String[] losingAdminSearchs = {"上次联系时间#startActionDate#2","合同截止时间#pstartDate#2","流失原因#losingId#3#SALES_40001","所有者#ownerAccsStr#5","资源分组#resGroupId#7"};
		String[] losingSearchs = {"上次联系时间#startActionDate#2","合同截止时间#pstartDate#2","流失原因#losingId#3#SALES_40001","资源分组#resGroupId#7"};
		String[] callSearchs = {"通话日期#startTime#2","客户状态#custState#3","销售进程#saleProcessId#3#SALES_10001","联系人#ownerAccsStr#5","呼叫类型#callState#3","通话时长#timeLength#8"};		
		String [] searchFileds = {};
		try{
			String module = request.getParameter("module"); // 模块类型值
			ShiroUser user = ShiroUtil.getShiroUser();
			if(module.equals("cust_losing")){
				if(user.getIssys() == 1){
					searchFileds = losingAdminSearchs;
				}else{
					searchFileds = losingSearchs;
				}
			}else if(module.equals("cust_silent")){
				if(user.getIssys() == 1){
					searchFileds = silentAdminSearchs;
				}else{
					searchFileds = silentSearchs;
				}
			}else if(module.equals("call_record")){
					searchFileds = callSearchs;			
			}
			HighSearchDto searchDto;
			List<HighSearchChildDto> childs;
			int i = 0;
			for(String searchParam : searchFileds){
				String[] params = searchParam.split("#");
				searchDto = new HighSearchDto();
				searchDto.setDataType(Integer.parseInt(params[2]));
				if(params[1].equals("pstartDate")){
					searchDto.setDevelopCode("contractExpireTime");
				}else{
					searchDto.setDevelopCode(params[1]);
				}
				searchDto.setIsFiledSet(0);
				searchDto.setIsQuery((short)1);
				searchDto.setSearchCode(params[1]);
				searchDto.setSearchName(params[0]);
				searchDto.setSort((short)i);
				if(params[2].equals("2")){
					childs = new ArrayList<HighSearchChildDto>();
					HighSearchChildDto child1 = new HighSearchChildDto();
					child1.setIsCheck(0);
					child1.setName(params[1]);
					child1.setOrder(0);
					childs.add(child1);
					HighSearchChildDto child2 = new HighSearchChildDto();
					child2.setIsCheck(0);
					child2.setName(params[1].replace("start", "end"));
					child2.setOrder(1);
					childs.add(child2);
					searchDto.setChildList(childs);				
				}else if(params[2].equals("5")){
					searchDto.setParamValue("/orgGroup/get_group_user_json");
				}else if(params[2].equals("3")){
					if(module.equals("call_record") && ("custState".equals(params[1]) || "callState".equals(params[1]))){
						if("custState".equals(params[1])){ // 客户状态
							searchDto.setChildList(getCustStatus());
						}
						if("callState".equals(params[1])){ // 呼叫类型
							searchDto.setChildList(getCallState());
						}
					}else{
						List<OptionBean> options = cachedService.getOptionList(params[3], user.getOrgId());
						childs = new ArrayList<HighSearchChildDto>();
						for(int j=0;j<options.size();j++){
							HighSearchChildDto child1 = new HighSearchChildDto();
							child1.setIsCheck(0);
							child1.setName(options.get(j).getOptionName());
							child1.setOrder(j);
							child1.setValue(options.get(j).getOptionlistId());
							childs.add(child1);
						}
						searchDto.setChildList(childs);
					}
				}
				dataList.add(searchDto);
				i++;
			}
			jsonStr = JsonUtil.getJsonString(dataList);
		}catch(Exception e){
			logger.error("/highSearch/toPageJson 异常！", e);
		}	
		return jsonStr;
	}
	
	/** 修改 */
	@RequestMapping("/modify") 
    @ResponseBody  
    public String modfiy(HttpServletRequest request) { 
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String jsonData = request.getParameter("jsonData");
			String module = request.getParameter("module");
			Map<String,Class>map = new HashMap<String, Class>();
			map.put("childList", HighSearchChildDto.class);
			List<HighSearchDto> dataList = JsonUtil.getListJson(jsonData, HighSearchDto.class,map);			
			// 删除缓存
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR +user.getAccount()+CachedNames.HIGH_SEARCH_+module);
			cachedService.setHighSearchByAccount(module, user.getOrgId(), dataList,user.getAccount());
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e){
			logger.error("/highSearch/modfiy 异常！", e);
			return AppConstant.RESULT_EXCEPTION;
		}
    }
	
	// 客户状态
	private List<HighSearchChildDto>getCustStatus(){
		List<HighSearchChildDto> list = new ArrayList<HighSearchChildDto>();
		HighSearchChildDto dto = new HighSearchChildDto();
		dto.setName("意向客户");
		dto.setValue("3");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("签约客户");
		dto.setValue("6");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("沉默客户");
		dto.setValue("7");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("流失客户");
		dto.setValue("8");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("公海客户");
		dto.setValue("4");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("资源");
		dto.setValue("9");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("访客");
		dto.setValue("10");
		dto.setIsCheck(0);
		list.add(dto);
		return list;
	}
	
	
	// 呼叫类型
	private List<HighSearchChildDto>getCallState(){
		List<HighSearchChildDto> list = new ArrayList<HighSearchChildDto>();
		HighSearchChildDto dto = new HighSearchChildDto();
		dto.setName("已接来电");
		dto.setValue("1");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("已接去电");
		dto.setValue("2");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("未接来电");
		dto.setValue("3");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("未接去电");
		dto.setValue("4");
		dto.setIsCheck(0);
		list.add(dto);
		return list;
	}
}
