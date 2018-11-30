package com.qftx.tsm.follow.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.service.ComResourceService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.follow.bean.CustSaleChanceBean;
import com.qftx.tsm.follow.dto.CustSaleChanceDto;
import com.qftx.tsm.follow.service.CustSaleChanceService;
import com.qftx.tsm.report.service.TsmReportPlanService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

@Controller  
@RequestMapping(value = "/cust/saleChance")
public class CustSaleChanceControl {
	private static Logger logger = Logger.getLogger(CustSaleChanceControl.class);
	@Autowired private CachedService cachedService;
	@Autowired private CustSaleChanceService custSaleChanceService;
	@Autowired private ComResourceService comResourceService;
	@Autowired private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Autowired private TsmReportPlanService tsmReportPlanService;
	@Autowired private ResCustInfoService resCustInfoService;
	/**  
	 * 客户销售进程变化
	 */
	@RequestMapping("/toCustSaleChance")
	public String toCustSaleChance(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("shiroUser", user);
			CustSaleChanceDto custSaleChanceDto = new CustSaleChanceDto();
			custSaleChanceDto.setRoleType(user.getIssys()); // 角色类别：0--销售，1--管理者
			// 管理者查询
			if(custSaleChanceDto.getRoleType() != null && custSaleChanceDto.getRoleType()==1){
				// 所有者查询方式 1-全部 2-只看自己 3-选中查询
				custSaleChanceDto.setOsType(StringUtils.isBlank(custSaleChanceDto.getOsType()) ? "1" : custSaleChanceDto.getOsType());			
			}			  
			request.setAttribute("item", custSaleChanceDto);
			request.setAttribute("shiroUser", user);
		}catch (Exception e) {
			logger.error(" 客户销售进程变化异常！",e);
		}
		return "/follow/saleChance";	
	}
	
	/**  
	 * 客户销售进程变化JSON
	 */
	@RequestMapping("/custSaleChanceJson")
	@ResponseBody
	public Map<String,Object> custSaleChanceJson(HttpServletRequest request,CustSaleChanceDto custSaleChanceDto){
		Map<String,Object>map = new HashMap<String, Object>();
		String key = null;
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String tDateType = request.getParameter("tDateType");
			String orgId=user.getOrgId();
			 //模糊查询处理
            if(StringUtils.isNotBlank(custSaleChanceDto.getQueryText())){
            	String queryText = custSaleChanceDto.getQueryText().trim();
            	custSaleChanceDto.setQueryText(queryText);
            }
            //  预计签单时间
			if(StringUtils.isNotBlank(tDateType) && !"0".equals(tDateType) && !"5".equals(tDateType)){
				if ("2".equals(tDateType) || "3".equals(tDateType)) {
					custSaleChanceDto.setTheoryStartSignDate(getStartDateStr(Integer.parseInt(tDateType)) + " 00:00:00");
					custSaleChanceDto.setTheoryEndSignDate(getEndDateStr(Integer.parseInt(tDateType)) + " 23:59:59");
				}else {
					custSaleChanceDto.setTheoryStartSignDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY) + " 00:00:00");
					custSaleChanceDto.setTheoryEndSignDate(getEndDateStr(Integer.parseInt(tDateType)) + " 23:59:59");
				}
			}else if("5".equals(tDateType)){
				custSaleChanceDto.setTheoryStartSignDate(custSaleChanceDto.getTheoryStartSignDate() + " 00:00:00");
				custSaleChanceDto.setTheoryEndSignDate(custSaleChanceDto.getTheoryEndSignDate() + " 23:59:59");
			}
            custSaleChanceDto.setRoleType(user.getIssys()); // 角色类别：0--销售，1--管理者
            // 管理者查询
 			/*if(custSaleChanceDto.getRoleType() != null && custSaleChanceDto.getRoleType()==1){
 				if(StringUtils.isNotEmpty(custSaleChanceDto.getAccs())){
 					String[] ownerAccs = custSaleChanceDto.getAccs().split(",");
 					List<String> owaList = Arrays.asList(ownerAccs);
 					custSaleChanceDto.setOwnerAccs(owaList);
 				}else{
 					// 所有者查询方式 1-全部 2-只看自己 3-选中查询
 					if (StringUtils.isNotEmpty(custSaleChanceDto.getAccs())) {
 						custSaleChanceDto.setOsType("3");
 					}else if (StringUtils.isEmpty(custSaleChanceDto.getAccs()) && StringUtils.isBlank(custSaleChanceDto.getOsType())){
 						custSaleChanceDto.setOsType("1");
 					}
 					//custSaleChanceDto.setOsType(StringUtils.isBlank(custSaleChanceDto.getOsType()) ? "1" : custSaleChanceDto.getOsType());
 					if("1".equals(custSaleChanceDto.getOsType())){
 						List<String>list = cachedService.getMemberAccs(user.getOrgId(),user.getAccount());
 						if(list!=null && list.size()>0){
 							StringBuffer sb = new StringBuffer();
 							for(String str: list){
 								sb.append(str);
 								sb.append(",");
 							}
 							if (!list.contains(user.getAccount())) {
 								list.add(user.getAccount());
 							}
 							if(sb.length()>0){
 								sb = sb.deleteCharAt(sb.length() - 1);
 							}
 							custSaleChanceDto.setAccs(sb.toString());
 							custSaleChanceDto.setOwnerAccs(list);
 							}else {
 							list.add(user.getAccount());
 							custSaleChanceDto.setOwnerAccs(list);
 						}					
 					}	
 				}
 			}*/
 			
 			 // 管理者查询
 			if(custSaleChanceDto.getRoleType() != null && custSaleChanceDto.getRoleType()==1){
 				if(StringUtils.isNotEmpty(custSaleChanceDto.getiAccs())){
 					String[] inputAccs = custSaleChanceDto.getiAccs().split(",");
 					List<String> inaList = Arrays.asList(inputAccs);
 					custSaleChanceDto.setInputAccs(inaList);
 				}else{
 					// 负责人查询方式 1-全部 2-只看自己 3-选中查询
 					/*if (StringUtils.isNotEmpty(custSaleChanceDto.getiAccs())) {
 						custSaleChanceDto.setIsType("3");
 					}else if (StringUtils.isEmpty(custSaleChanceDto.getiAccs()) && StringUtils.isBlank(custSaleChanceDto.getIsType())){
 						custSaleChanceDto.setIsType("1");
 					}*/
 					//if("1".equals(custSaleChanceDto.getIsType())){
					List<String>list = cachedService.getMemberAccs(user.getOrgId(),user.getAccount());
					if(list!=null && list.size()>0){
						StringBuffer sb = new StringBuffer();
						for(String str: list){
							sb.append(str);
							sb.append(",");
						}
						if (!list.contains(user.getAccount())) {
							list.add(user.getAccount());
						}
						if(sb.length()>0){
							sb = sb.deleteCharAt(sb.length() - 1);
						}
						custSaleChanceDto.setiAccs(sb.toString());
						custSaleChanceDto.setInputAccs(list);
					}else {
						list.add(user.getAccount());
						custSaleChanceDto.setInputAccs(list);
					}					
 					//}	
 				}
 			}
		    custSaleChanceDto.setOrgId(orgId);
		    custSaleChanceDto.setIsState(user.getIsState());
		    custSaleChanceDto.setOwnerAcc(user.getAccount());
		    custSaleChanceDto.setOrderKey("THEORY_SIGN_DATE desc ");
		   // List<CustSaleChanceDto> tsmcustSaleChanceDtoList = new ArrayList<>();
			List<CustSaleChanceDto> tsmcustSaleChanceDtoList = custSaleChanceService.queryCustSaleChanceListPage(custSaleChanceDto);
		    Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
		    if (tsmcustSaleChanceDtoList != null && tsmcustSaleChanceDtoList.size() > 0) {
				List<String> recustIds = new ArrayList<>();
				for(CustSaleChanceDto csd : tsmcustSaleChanceDtoList) {
					csd.setShowTheorySignDate(csd.getTheorySignDate() !=null?DateUtil.formatDate(csd.getTheorySignDate(), DateUtil.Data_ALL): "");
					recustIds.add(csd.getCustId());
				}
				if(recustIds != null && recustIds.size() >0){
					Map<String,CustSaleChanceDto>csdMap = new HashMap<String, CustSaleChanceDto>();
					custSaleChanceDto.setResCustIds(recustIds);
					List<CustSaleChanceDto> tsmCustList = custSaleChanceService.getResCustsByCustIds(custSaleChanceDto);
					for(CustSaleChanceDto fcd : tsmCustList){
						csdMap.put(fcd.getResCustId(), fcd);
					}
					for(CustSaleChanceDto csd : tsmcustSaleChanceDtoList) {
						if(csdMap.get(csd.getCustId()) != null){
							CustSaleChanceDto cfd1 = csdMap.get(csd.getCustId());
							csd.setOwnerAcc(cfd1.getOwnerAcc());
							csd.setOwnerName(nameMap.get(cfd1.getOwnerAcc()) == null ? cfd1.getOwnerAcc() : nameMap.get(cfd1.getOwnerAcc()));
							csd.setInputName(nameMap.get(csd.getInputAcc()) == null ? csd.getInputAcc() : nameMap.get(csd.getInputAcc()));
							csd.setCompany(cfd1.getCompany());
							csd.setCustName(cfd1.getCustName());
							csd.setFollowId(cfd1.getFollowId());
						}
					}
				}
			}
		    List<String>list = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(),user.getAccount());
		    map.put("list", tsmcustSaleChanceDtoList);
			map.put("item", custSaleChanceDto);
			map.put("isState",user.getIsState());
			map.put("status", "success");
			map.put("shareAccs",list);
	     	return map;
		}catch (Exception e) {
			map.put("status", "error");	
			logger.error("客户销售机会JSON异常！", e);
		}
		return map;	
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
	
	
	/**  
	 * 跳转添加销售机会页面
	 */
	@RequestMapping("/toAddSaleChance")
	public String toAddSaleChance(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("shiroUser", user);
		}catch (Exception e) {
			logger.error(" 添加销售机会异常！",e);
		}
		return "/follow/idialog/addSaleChance";	
	}
	
	/** 添加销售机会*/
	@RequestMapping("/addSaleChance")
	@ResponseBody
	public BaseJsonResult addSaleChance(HttpServletRequest request,CustSaleChanceBean custSaleChanceBean) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			custSaleChanceBean.setOrgId(user.getOrgId());
			ResCustInfoBean rcib = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custSaleChanceBean.getCustId());
			if (rcib != null) {
				custSaleChanceBean.setInputAcc(rcib.getOwnerAcc());
			}
			custSaleChanceService.addSaleChance(custSaleChanceBean);
			return BaseJsonResult.success();
		} catch (Exception e) {
			logger.error("添加销售机会异常！", e);
			return BaseJsonResult.error();
		}
	}
	
	/**  
	 * 跳转修改销售机会页面
	 */
	@RequestMapping("/toEditSaleChance")
	public String toEditSaleChance(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String saleChanceId = request.getParameter("saleChanceId");
			CustSaleChanceBean custSaleChanceBean = new CustSaleChanceBean();
			custSaleChanceBean.setOrgId(user.getOrgId());
			custSaleChanceBean.setSaleChanceId(saleChanceId);
			CustSaleChanceBean bean = custSaleChanceService.getByCondition(custSaleChanceBean);
			List<String> recustIds = new ArrayList<>();
			recustIds.add(bean.getCustId());
			CustSaleChanceDto dto = new CustSaleChanceDto();
			dto.setOrgId(user.getOrgId());
			dto.setResCustIds(recustIds);
			List<CustSaleChanceDto> tsmCustList = custSaleChanceService.getResCustsByCustIds(dto);
			if (tsmCustList != null && tsmCustList.size() > 0) {
				bean.setCustName(tsmCustList.get(0).getCustName());
				bean.setCompany(tsmCustList.get(0).getCompany());
			}
			request.setAttribute("item", bean);
			request.setAttribute("shiroUser", user);
		}catch (Exception e) {
			logger.error(" 修改销售机会异常！",e);
		}
		return "/follow/idialog/editSaleChance";	
	}
	
	/** 修改销售机会*/
	@RequestMapping("/editSaleChance")
	@ResponseBody
	public BaseJsonResult editSaleChance(HttpServletRequest request,CustSaleChanceBean custSaleChanceBean) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			custSaleChanceBean.setOrgId(user.getOrgId());
			CustSaleChanceBean bean = new CustSaleChanceBean();
			bean.setOrgId(user.getOrgId());
			bean.setSaleChanceId(custSaleChanceBean.getSaleChanceId());
			CustSaleChanceDto custSaleChanceDto = custSaleChanceService.getBySalechanceId(bean);
			
			custSaleChanceService.editSaleChance(custSaleChanceBean);
			return BaseJsonResult.success();
		} catch (Exception e) {
			logger.error("修改销售机会异常！", e);
			return BaseJsonResult.error();
		}
	}
	
	/**
	 * 删除销售机会
	 */
	@ResponseBody
	@RequestMapping("/delSaleChance")
	public String delSaleChance(HttpServletRequest request, String saleChanceIds) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String orgId = user.getOrgId();
			CustSaleChanceDto dto = new CustSaleChanceDto();
			dto.setOrgId(orgId);
			List<String> list = new ArrayList<>();
			if (saleChanceIds != null) {
				list = Arrays.asList(saleChanceIds.split(","));
			}
			dto.setSaleChanceIds(list);
			custSaleChanceService.delSaleChance(dto);
			/*for(String saleChanceId : list){
				CustSaleChanceBean bean = new CustSaleChanceBean();
				bean.setOrgId(user.getOrgId());
				bean.setSaleChanceId(saleChanceId);
				CustSaleChanceDto custSaleChanceDto = custSaleChanceService.getBySalechanceId(bean);
			}*/
			return AppConstant.RESULT_SUCCESS;
		} catch (Exception e) {
			logger.error("删除销售机会异常.saleChanceIds=" + saleChanceIds);
			return AppConstant.RESULT_EXCEPTION;
		}
	}
	
	
	/**
	 *根据queryText查询姓名或者公司名称为text的res
	 */
	@ResponseBody
	@RequestMapping("/queryResByNameOrCompany")
	public List<ResCustInfoBean> queryResByNameOrCompany(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String orgId = user.getOrgId();
			String queryText = request.getParameter("queryText");
			if (queryText == null || "".equals(queryText)) {
				return null;
			}
			List<String> list = new ArrayList<>(); 
			list = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(),user.getAccount());
			if(list!=null && list.size()>0){
				if (!list.contains(user.getAccount())) {
					list.add(user.getAccount());
				}
			}else {
				list.add(user.getAccount());
			}					
			List<ResCustInfoBean> list1 = comResourceService.queryResByNameOrCompany(queryText,orgId,user.getIsState(),list);
			return list1;
		} catch (Exception e) {
			logger.error("根据queryText查询姓名或者公司名称为text的res异常" + e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/getSaleChanceByCustId")
	public List<CustSaleChanceBean> getSaleChanceByCustId(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String orgId = user.getOrgId();
			String custId = request.getParameter("custId");
			CustSaleChanceDto dto = new CustSaleChanceDto();
			if(user.getIssys() != null && user.getIssys()==1){
				List<String>list = cachedService.getMemberAccs(user.getOrgId(),user.getAccount());
				if(list!=null && list.size()>0){
					StringBuffer sb = new StringBuffer();
					for(String str: list){
						sb.append(str);
						sb.append(",");
					}
					if (!list.contains(user.getAccount())) {
						list.add(user.getAccount());
					}
					if(sb.length()>0){
						sb = sb.deleteCharAt(sb.length() - 1);
					}
					dto.setInputAccs(list);
				}else {
					list.add(user.getAccount());
					dto.setInputAccs(list);
				}					
 			}
			List<CustSaleChanceBean> list = new ArrayList<>();
			dto.setOrgId(orgId);
			dto.setCustId(custId);
			dto.setIsDel(0);
			dto.setRoleType(user.getIssys());
			dto.setOwnerAcc(user.getAccount());
			list = custSaleChanceService.getSaleChanceByCustId(dto);
			return list;
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("获取销售机会列表异常" + e);
		}
		return null;
	}
	
	
}
