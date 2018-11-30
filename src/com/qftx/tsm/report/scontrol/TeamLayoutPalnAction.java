package com.qftx.tsm.report.scontrol;

import com.alibaba.fastjson.JSONArray;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysRunException;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.contract.service.ContractService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.main.service.MainService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.report.bean.LayoutCustOptionBean;
import com.qftx.tsm.report.bean.LayoutCustProductBean;
import com.qftx.tsm.report.bean.LayoutCustStateBean;
import com.qftx.tsm.report.service.LayoutCustOptionService;
import com.qftx.tsm.report.service.LayoutCustProductService;
import com.qftx.tsm.report.service.LayoutCustStateService;
import com.qftx.tsm.report.utils.GroupComparator;
import com.qftx.tsm.sys.bean.Product;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value="/layout/team")
public class TeamLayoutPalnAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(TeamLayoutPalnAction.class);
	@Autowired
	private MainService mainService;
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private LayoutCustStateService layoutCustStateService;
	@Autowired
	private LayoutCustOptionService layoutCustOptionService;
	@Autowired
	private LayoutCustProductService layoutCustProductService;
	
	@RequestMapping()
	public String report(HttpServletRequest request){
		return "report/teamLayout";
	}
	
	@RequestMapping("/custReport")
	public String custReport(HttpServletRequest request){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<TeamGroupBean> teamGroups = mainService.queryManageGroupList(user.getAccount(),user.getOrgId());
			request.setAttribute("teamGroups", teamGroups);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "report/teamCustLayout";
	}
	
	@RequestMapping("/custStatusReport")
	public String custStatusReport(HttpServletRequest request,String groupIds,String statusStr){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<LayoutCustStateBean> dtos = layoutCustStateService.getCustStateLayoutForGroup(user.getOrgId(),user.getAccount(),StringUtils.isNotBlank(groupIds) ? Arrays.asList(groupIds.split(",")) : null);
			for (LayoutCustStateBean dto : dtos) {
				List<String> ids = super.getChildGroupIds(dto.getGroupId());
				logger.info("ids>>"+JSONArray.toJSONString(ids));
				if(ids.size() > 1){
					LayoutCustStateBean  totalDto = layoutCustStateService.getCustStateLayoutForPGroup(user.getOrgId(),ids);
					dto.setResNum(totalDto.getResNum());
					dto.setCustNum(totalDto.getCustNum());
					dto.setSignNum(totalDto.getSignNum());
					dto.setSilentNum(totalDto.getSilentNum());
					dto.setLosingNum(totalDto.getLosingNum());
					dto.setAllNum(totalDto.getAllNum());
				}
			}
			Collections.sort(dtos,new Comparator<LayoutCustStateBean>() {
				@Override
				public int compare(LayoutCustStateBean o1,
						LayoutCustStateBean o2) {
					return o1.getGroupId().compareToIgnoreCase(o2.getGroupId());
				}
			});
			if(StringUtils.isNotBlank(statusStr)){
				Map<String, String> colMap = new HashMap<String, String>();
				for(String status : statusStr.split(",")){
					colMap.put(status, "1");
				}
				request.setAttribute("colMap", colMap);
			}
			request.setAttribute("dtos", dtos);
			request.setAttribute("groupIds", groupIds);
			request.setAttribute("statusStr", statusStr);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "report/custStatusLayout";
	}
	
	@RequestMapping("/custStatusReport/member")
	public String custStatusReportOfMember(HttpServletRequest request,String groupId,String statusStr){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<String> ids = super.getChildGroupIds(groupId);
			List<LayoutCustStateBean> dtos = layoutCustStateService.getCustStateLayoutForMember(user.getOrgId(), ids);
			if(StringUtils.isNotBlank(statusStr)){
				Map<String, String> colMap = new HashMap<String, String>();
				for(String status : statusStr.split(",")){
					colMap.put(status, "1");
				}
				request.setAttribute("colMap", colMap);
			}
			request.setAttribute("dtos", dtos);
			request.setAttribute("statusStr", statusStr);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "report/custStatusMemberLayout";
	}
	
	@RequestMapping("/saleProcReport")
	public String saleProcReport(HttpServletRequest request,String groupIds,String optionlistIds){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			List<LayoutCustOptionBean> custInfoDtos = layoutCustOptionService.getCustOptionLayoutForGroup(user.getOrgId(), user.getAccount(), StringUtils.isNotBlank(groupIds) ? Arrays.asList(groupIds.split(",")) : null,1);
			List<Map<String, Object>> list2 = new ArrayList<Map<String,Object>>();
			Map<String, Map<String, Object>> groupMap = new HashMap<String, Map<String,Object>>();
			for(LayoutCustOptionBean cust : custInfoDtos){
				Map<String, Object> gmap;
				//处理分组统计
				if(groupMap.containsKey(cust.getGroupId())){
					gmap = groupMap.get(cust.getGroupId());
					if(StringUtils.isNotBlank(cust.getOptionlistId())){
						if(gmap.get(cust.getOptionlistId()) != null){
							Integer gnum = Integer.parseInt(gmap.get(cust.getOptionlistId()).toString());
							gmap.put(cust.getOptionlistId(), gnum+cust.getCustNums());
						}else{
							gmap.put(cust.getOptionlistId(), cust.getCustNums());
						}
					}
				}else{
					gmap = new HashMap<String, Object>();
					gmap.put("groupName", cust.getGroupName());
					gmap.put("groupId", cust.getGroupId());
					if(StringUtils.isNotBlank(cust.getOptionlistId())){
						gmap.put(cust.getOptionlistId(), cust.getCustNums());
					}
					groupMap.put(cust.getGroupId(), gmap);
				}
			}
			
			for(String key : groupMap.keySet()){
				Map<String, Object> map = groupMap.get(key);
				Integer allNum = 0;
				for(OptionBean option : options){
					if(map.get(option.getOptionlistId()) == null){
						map.put(option.getOptionlistId(), 0);
					}else{
						allNum+=Integer.parseInt(map.get(option.getOptionlistId()).toString());
					}
				}
				map.put("allNum", allNum);
				list2.add(map);
			}
			
			for(Map<String, Object> dm : list2){
				List<String> ids = super.getChildGroupIds(dm.get("groupId").toString());
				if(ids.size() > 1){
					List<LayoutCustOptionBean> groupDtos = layoutCustOptionService.getCustOptionLayoutForPGroup(user.getOrgId(), ids, 1);
					Integer allNum = 0 ;
					for(LayoutCustOptionBean rcid : groupDtos){
						if(StringUtils.isNotBlank(rcid.getOptionlistId())){
							allNum+=rcid.getCustNums();
							dm.put(rcid.getOptionlistId(), rcid.getCustNums());
						}
					}
					dm.put("allNum", allNum);
				}
			}
			
			if(list2.size() > 0){
				Collections.sort(list2,new GroupComparator());
			}
			if(StringUtils.isNotBlank(optionlistIds)){
				Map<String, String> colMap = new HashMap<String, String>();
				for(String optionlistId : optionlistIds.split(",")){
					colMap.put(optionlistId, "1");
				}
				request.setAttribute("colMap", colMap);
			}
			request.setAttribute("list2", list2);
			request.setAttribute("options", options);
			request.setAttribute("groupIds", groupIds);
			request.setAttribute("optionlistIds", optionlistIds);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "report/saleProcLayout";
	}
	
	@RequestMapping("/saleProcReport/member")
	public String saleProcMemberReport(HttpServletRequest request,String groupId,String optionlistIds){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			List<String> ids = super.getChildGroupIds(groupId);
			List<LayoutCustOptionBean> custInfoDtos = layoutCustOptionService.getCustSaleProcLayoutForMember(user.getOrgId(),ids,1);
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Map<String, Object>> dataMap = new HashMap<String, Map<String,Object>>();
			for(LayoutCustOptionBean cust : custInfoDtos){
				Map<String, Object> map;
				//处理数据
				if(dataMap.containsKey(cust.getUserAccount())){
					map = dataMap.get(cust.getUserAccount());
					map.put(cust.getOptionlistId(), cust.getCustNums());
				}else{
					map = new HashMap<String, Object>();
					map.put("userName", cust.getUserName());
					map.put("userAccount",cust.getUserAccount());
					if(StringUtils.isNotBlank(cust.getOptionlistId())){
						map.put(cust.getOptionlistId(), cust.getCustNums());
					}
					dataMap.put(cust.getUserAccount(), map);
				}
			}
			
			for(String key : dataMap.keySet()){
				Map<String, Object> map = dataMap.get(key);
				Integer allNum = 0;
				for(OptionBean option : options){
					if(map.get(option.getOptionlistId()) == null){
						map.put(option.getOptionlistId(), 0);
					}else{
						allNum+=Integer.parseInt(map.get(option.getOptionlistId()).toString());
					}
				}
				map.put("allNum", allNum);
				list.add(map);
			}
			Collections.sort(list,new Comparator<Map<String,Object>>(){

				@Override
				public int compare(Map<String, Object> o1,
						Map<String, Object> o2) {
					// TODO Auto-generated method stub
					return o1.get("userAccount").toString().compareTo(o2.get("userAccount").toString());
				}
				
			});
			if(StringUtils.isNotBlank(optionlistIds)){
				Map<String, String> colMap = new HashMap<String, String>();
				for(String optionlistId : optionlistIds.split(",")){
					colMap.put(optionlistId, "1");
				}
				request.setAttribute("colMap", colMap);
			}
			request.setAttribute("list", list);
			request.setAttribute("options", options);
			request.setAttribute("optionlistIds", optionlistIds);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "report/saleProcMemberLayout";
	}
	
	@RequestMapping("/custTypeReport")
	public String custTypeReport(HttpServletRequest request,String groupIds,String optionlistIds){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			List<LayoutCustOptionBean> custInfoDtos = layoutCustOptionService.getCustOptionLayoutForGroup(user.getOrgId(), user.getAccount(), StringUtils.isNotBlank(groupIds) ? Arrays.asList(groupIds.split(",")) : null,2);
			List<Map<String, Object>> list2 = new ArrayList<Map<String,Object>>();
			Map<String, Map<String, Object>> groupMap = new HashMap<String, Map<String,Object>>();
			for(LayoutCustOptionBean cust : custInfoDtos){
				Map<String, Object> gmap;
				//处理分组统计
				if(groupMap.containsKey(cust.getGroupId())){
					gmap = groupMap.get(cust.getGroupId());
					if(StringUtils.isNotBlank(cust.getOptionlistId())){
						if(gmap.get(cust.getOptionlistId()) != null){
							Integer gnum = Integer.parseInt(gmap.get(cust.getOptionlistId()).toString());
							gmap.put(cust.getOptionlistId(), gnum+cust.getCustNums());
						}else{
							gmap.put(cust.getOptionlistId(), cust.getCustNums());
						}
					}
				}else{
					gmap = new HashMap<String, Object>();
					gmap.put("groupName", cust.getGroupName());
					gmap.put("groupId", cust.getGroupId());
					if(StringUtils.isNotBlank(cust.getOptionlistId())){
						gmap.put(cust.getOptionlistId(), cust.getCustNums());
					}
					groupMap.put(cust.getGroupId(), gmap);
				}
			}
			
			for(String key : groupMap.keySet()){
				Map<String, Object> map = groupMap.get(key);
				Integer allNum = 0;
				for(OptionBean option : options){
					if(map.get(option.getOptionlistId()) == null){
						map.put(option.getOptionlistId(), 0);
					}else{
						allNum+=Integer.parseInt(map.get(option.getOptionlistId()).toString());
					}
				}
				map.put("allNum", allNum);
				list2.add(map);
			}
			
			for(Map<String, Object> dm : list2){
				List<String> ids = super.getChildGroupIds(dm.get("groupId").toString());
				if(ids.size() > 1){
					List<LayoutCustOptionBean> groupDtos = layoutCustOptionService.getCustOptionLayoutForPGroup(user.getOrgId(), ids, 2);
					Integer allNum = 0 ;
					for(LayoutCustOptionBean rcid : groupDtos){
						if(StringUtils.isNotBlank(rcid.getOptionlistId())){
							allNum+=rcid.getCustNums();
							dm.put(rcid.getOptionlistId(), rcid.getCustNums());
						}
					}
					dm.put("allNum", allNum);
				}
			}
			
			if(list2.size() > 0){
				Collections.sort(list2,new GroupComparator());
			}
			if(StringUtils.isNotBlank(optionlistIds)){
				Map<String, String> colMap = new HashMap<String, String>();
				for(String optionlistId : optionlistIds.split(",")){
					colMap.put(optionlistId, "1");
				}
				request.setAttribute("colMap", colMap);
			}
			request.setAttribute("list2", list2);
			request.setAttribute("options", options);
			request.setAttribute("groupIds", groupIds);
			request.setAttribute("optionlistIds", optionlistIds);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "report/custTypeLayout";
	}
	
	@RequestMapping("/custTypeReport/member")
	public String custTypeMemberReport(HttpServletRequest request,String groupId,String optionlistIds){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			// 从缓存读取客户类型列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			List<String> ids = super.getChildGroupIds(groupId);
			List<LayoutCustOptionBean> custInfoDtos = layoutCustOptionService.getCustSaleProcLayoutForMember(user.getOrgId(),ids,2);
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Map<String, Object>> dataMap = new HashMap<String, Map<String,Object>>();
			for(LayoutCustOptionBean cust : custInfoDtos){
				Map<String, Object> map;
				//处理数据
				if(dataMap.containsKey(cust.getUserAccount())){
					map = dataMap.get(cust.getUserAccount());
					map.put(cust.getOptionlistId(), cust.getCustNums());
				}else{
					map = new HashMap<String, Object>();
					map.put("userName", cust.getUserName());
					map.put("userAccount",cust.getUserAccount());
					if(StringUtils.isNotBlank(cust.getOptionlistId())){
						map.put(cust.getOptionlistId(), cust.getCustNums());
					}
					dataMap.put(cust.getUserAccount(), map);
				}
			}
			
			for(String key : dataMap.keySet()){
				Map<String, Object> map = dataMap.get(key);
				Integer allNum = 0;
				for(OptionBean option : options){
					if(map.get(option.getOptionlistId()) == null){
						map.put(option.getOptionlistId(), 0);
					}else{
						allNum+=Integer.parseInt(map.get(option.getOptionlistId()).toString());
					}
				}
				map.put("allNum", allNum);
				list.add(map);
			}
			
			Collections.sort(list,new Comparator<Map<String,Object>>(){

				@Override
				public int compare(Map<String, Object> o1,
						Map<String, Object> o2) {
					// TODO Auto-generated method stub
					return o1.get("userAccount").toString().compareTo(o2.get("userAccount").toString());
				}
				
			});
			
			if(StringUtils.isNotBlank(optionlistIds)){
				Map<String, String> colMap = new HashMap<String, String>();
				for(String optionlistId : optionlistIds.split(",")){
					colMap.put(optionlistId, "1");
				}
				request.setAttribute("colMap", colMap);
			}
			request.setAttribute("list", list);
			request.setAttribute("options", options);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "report/custTypeMemberLayout";
	}
	
	@RequestMapping("/productReport")
	public String productReport(HttpServletRequest request,String groupIds,String productIds){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<Product> products = cachedService.getOpionProduct(user.getOrgId());
			List<LayoutCustProductBean> custInfoDtos = layoutCustProductService.getCustProductLayoutForGroup(user.getOrgId(), user.getAccount(), StringUtils.isNotBlank(groupIds) ? Arrays.asList(groupIds.split(",")) : null);
			List<Map<String, Object>> list2 = new ArrayList<Map<String,Object>>();
			Map<String, Map<String, Object>> groupMap = new HashMap<String, Map<String,Object>>();
			for(LayoutCustProductBean cust : custInfoDtos){
				Map<String, Object> gmap;
				if(!groupMap.containsKey(cust.getGroupId())){
					gmap = new HashMap<String, Object>();
					gmap.put("groupName", cust.getGroupName());
					gmap.put("groupId", cust.getGroupId());
					if(StringUtils.isNotBlank(cust.getProductId())){
						gmap.put(cust.getProductId(), cust.getCustNums());
					}
					groupMap.put(cust.getGroupId(), gmap);
				}else{
					Map<String, Object> dm = groupMap.get(cust.getGroupId());
					if(StringUtils.isNotBlank(cust.getProductId())){
						dm.put(cust.getProductId(), cust.getCustNums());
					}
				}
			}
			
			for(String key : groupMap.keySet()){
				Map<String, Object> map = groupMap.get(key);
				Integer allNum = 0;
				for(Product product : products){
					if(map.get(product.getId()) == null){
						map.put(product.getId(), 0);
					}else{
						allNum+=Integer.parseInt(map.get(product.getId()).toString());
					}
				}
				map.put("allNum", allNum);
				list2.add(map);
			}
			
			for(Map<String, Object> dm : list2){
				List<String> ids = super.getChildGroupIds(dm.get("groupId").toString());
				if(ids.size() > 1){
					List<LayoutCustProductBean> groupDtos = layoutCustProductService.getCustProductLayoutForPGroup(user.getOrgId(), ids);
					Integer allNum = 0 ;
					for(LayoutCustProductBean rcid : groupDtos){
						if(StringUtils.isNotBlank(rcid.getProductId())){
							allNum+=rcid.getCustNums();
							dm.put(rcid.getProductId(), rcid.getCustNums());
						}
					}
					dm.put("allNum", allNum);
				}
			}
			
			if(list2.size() > 0){
				Collections.sort(list2,new GroupComparator());
			}
			
			if(StringUtils.isNotBlank(productIds)){
				Map<String, String> colMap = new HashMap<String, String>();
				for(String productId : productIds.split(",")){
					colMap.put(productId, "1");
				}
				request.setAttribute("colMap", colMap);
			}
			request.setAttribute("list2", list2);
			request.setAttribute("products", products);
			request.setAttribute("groupIds", groupIds);
			request.setAttribute("productIds", productIds);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "report/productLayout";
	}
	
	@RequestMapping("/productReport/member")
	public String productMemberReport(HttpServletRequest request,String groupId,String productIds){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<Product> products = cachedService.getOpionProduct(user.getOrgId());
			List<String> ids = super.getChildGroupIds(groupId);
			List<LayoutCustProductBean> custInfoDtos = layoutCustProductService.getCustProductLayoutForMember(user.getOrgId(), ids);
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Map<String, Object>> dataMap = new HashMap<String, Map<String,Object>>();
			for(LayoutCustProductBean cust : custInfoDtos){
				Map<String, Object> map;
				if(!dataMap.containsKey(cust.getUserAccount())){
					map = new HashMap<String, Object>();
					map.put("userName", cust.getUserName());
					map.put("userAccount", cust.getUserAccount());
					if(StringUtils.isNotBlank(cust.getProductId())){
						map.put(cust.getProductId(), cust.getCustNums());
					}
					dataMap.put(cust.getUserAccount(), map);
				}else{
					Map<String, Object> dm = dataMap.get(cust.getUserAccount());
					if(StringUtils.isNotBlank(cust.getProductId())){
						dm.put(cust.getProductId(), cust.getCustNums());
					}
				}
			}
			
			for(String key : dataMap.keySet()){
				Map<String, Object> map = dataMap.get(key);
				Integer allNum = 0;
				for(Product product : products){
					if(map.get(product.getId()) == null){
						map.put(product.getId(), 0);
					}else{
						allNum+=Integer.parseInt(map.get(product.getId()).toString());
					}
				}
				map.put("allNum", allNum);
				list.add(map);
			}
			
			Collections.sort(list,new Comparator<Map<String,Object>>(){

				@Override
				public int compare(Map<String, Object> o1,
						Map<String, Object> o2) {
					// TODO Auto-generated method stub
					return o1.get("userAccount").toString().compareTo(o2.get("userAccount").toString());
				}
				
			});
			
			if(StringUtils.isNotBlank(productIds)){
				Map<String, String> colMap = new HashMap<String, String>();
				for(String productId : productIds.split(",")){
					colMap.put(productId, "1");
				}
				request.setAttribute("colMap", colMap);
			}
			request.setAttribute("list", list);
			request.setAttribute("products", products);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "report/productMemberLayout";
	}
}
