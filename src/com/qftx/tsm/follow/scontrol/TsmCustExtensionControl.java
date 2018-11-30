package com.qftx.tsm.follow.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.follow.bean.TsmCustExtension;
import com.qftx.tsm.follow.dto.TsmCustExtensionDto;
import com.qftx.tsm.follow.service.TsmCustExtensionService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.service.OptionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 延期审核
 * @author: zwj
 * @since: 2015-12-4  下午2:14:09
 * @history: 4.x  
 */
@Controller
@RequestMapping(value = "/cust/custFollow/extension")
public class TsmCustExtensionControl {
	private static Logger logger = Logger.getLogger(TsmCustExtensionControl.class);
	@Autowired
	private TsmCustExtensionService tsmCustExtensionService;
	@Autowired
	private TsmMessageService tsmMessageService;
	@Autowired
	private LogCustInfoService logCustInfoService;
	@Autowired
	private CachedService cachedService;
	
	/** 
	 * 延期审核
	 */
	@RequestMapping("/deferredAuditList")
	public String deferredAuditList(HttpServletRequest request){
		try {
			
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("shiroUser", user);
			String orgId=user.getOrgId();
			String itemCode=AppConstant.SALES_TYPE_ONE;
			TsmCustExtensionDto tsmCustExtensionDto = new TsmCustExtensionDto();
			tsmCustExtensionDto.setItemCode(itemCode);
			tsmCustExtensionDto.setOrgId(orgId);
			tsmCustExtensionDto.setOwnerAcc(user.getAccount());
			if(tsmCustExtensionDto.getStatusExtended() == null){
				tsmCustExtensionDto.setStatusExtended((short)2); // 默认为待审核
			}
			//获取销售信息下拉框
			List<OptionBean> options = cachedService.getOptionList(itemCode,user.getOrgId());
			request.setAttribute("options", options);		
			request.setAttribute("item", tsmCustExtensionDto);
			if(user.getIsState() == 0){ // 个人版
				return "/follow/deferredAudit_list_person";
			}	
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/follow/deferredAudit_list";
	}
	
	/** 
	 * 延期审核 JSON
	 */
	@RequestMapping("/deferredAuditListJson")
	@ResponseBody
	public Map<String,Object> deferredAuditListJson(HttpServletRequest request,TsmCustExtensionDto tsmCustExtensionDto){
		Map<String,Object>map = new HashMap<String, Object>();
		try {
			String dDateType = request.getParameter("dDateType");
			String sDateType = request.getParameter("sDateType");
			ShiroUser user = ShiroUtil.getShiroUser();
			String orgId=user.getOrgId();
			String itemCode=AppConstant.SALES_TYPE_ONE;
			tsmCustExtensionDto.setItemCode(itemCode);
			tsmCustExtensionDto.setOrgId(orgId);
			tsmCustExtensionDto.setState(user.getIsState());
			tsmCustExtensionDto.setOwnerAcc(user.getAccount());
			if(tsmCustExtensionDto.getStatusExtended() == null){
				tsmCustExtensionDto.setStatusExtended((short)2); // 默认为待审核
			}
			//  审核时间
			if(StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)){
				tsmCustExtensionDto.setAuditStartDate(getStartDateStr(Integer.parseInt(dDateType)));
				tsmCustExtensionDto.setAuditEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			//  申请时间
			if(StringUtils.isNotBlank(sDateType) && !"0".equals(sDateType) && !"5".equals(sDateType)){
				tsmCustExtensionDto.setStartDate(getStartDateStr(Integer.parseInt(sDateType)));
				tsmCustExtensionDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			tsmCustExtensionDto.setOrderKey("APPLICATION_TIME_EXTENSION DESC");
			List<TsmCustExtensionDto> dtos=tsmCustExtensionService.getDeferredAuditListPage(tsmCustExtensionDto);
			if(dtos != null && dtos.size() >0){
				Map<String, String> optionMap = cachedService.getOrgSaleProcess(user.getOrgId());	
				for(TsmCustExtensionDto dto : dtos){
					dto.setShowApplicationTimeExtension(dto.getApplicationTimeExtension() != null?DateUtil.formatDate(dto.getApplicationTimeExtension(), DateUtil.Data_ALL): "" );
					dto.setShowReviewerTime(dto.getReviewerTime() != null?DateUtil.formatDate(dto.getReviewerTime(), DateUtil.Data_ALL): "" );
					if (optionMap != null) {
						dto.setOptionName(optionMap.get(dto.getOptionId()));
					}
				}
			}
			map.put("list", dtos);
			map.put("item", tsmCustExtensionDto);
			map.put("status", "success");				
		} catch (Exception e) {
			logger.error("延期审核列表 JSON 异常！",e);
			map.put("status", "error");		
		}
		return map;
	}
	
	/**
	 * 批量修改状态
	 * @return
	 */
	@RequestMapping("/updateBatchStatus")
	@ResponseBody
	public String updateBatchStatus(HttpServletRequest request){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids=request.getParameter("ids");                   //操作id集合
			String statusExtended=request.getParameter("status");     //状态
			String updateAcc=user.getAccount();
			String updateName=user.getName();
			List<String>ids1 = new ArrayList<String>();
			StringBuffer sbf = new StringBuffer();
			for(String id: ids.split(",")){
				ids1.add(id.split("_")[0]);
				if(id.split("_").length > 1){
					sbf.append(id.split("_")[1]).append(",");
				}
			}
			tsmCustExtensionService.modifyBatchStatus(user.getOrgId(),statusExtended, updateAcc, updateName, ids1);
			// 新增用户操作日志
			LogBean logBean = new LogBean();
			logBean.setOrgId(user.getOrgId());
			logBean.setUserAcc(user.getAccount());
			logBean.setUserName(user.getName());
			logBean.setModuleId(AppConstant.Module_id114);
			logBean.setModuleName(AppConstant.Module_Name114);
			if("1".equals(statusExtended)){
				logBean.setOperateId(AppConstant.Operate_id31);
				logBean.setOperateName(AppConstant.Operate_Name31);
			}else{
				logBean.setOperateId(AppConstant.Operate_id32);
				logBean.setOperateName(AppConstant.Operate_Name32);
			}		
			logBean.setContent(sbf.toString());
			logCustInfoService.addTableStoreLog(logBean, null);
			
			return AppConstant.RESULT_SUCCESS;
		} catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	
	@RequestMapping("/toDeferredAudit")
	public String toDeferredAudit(HttpServletRequest request){
		String custId = request.getParameter("custId");
		request.setAttribute("custId", custId);
		return "/follow/idialog/deferredAudit";
	}
	
	/** 申请延期 */
	@RequestMapping("/deferredAudit")
	@ResponseBody
	public String deferredAudit(HttpServletRequest request,TsmCustExtension tsmCustExtension){
		ShiroUser user = ShiroUtil.getShiroUser();
		tsmCustExtension.setExtensionId(SysBaseModelUtil.getModelId());  //延期ID
		tsmCustExtension.setApplicantExtensionId(user.getAccount());  //延期申请人
		tsmCustExtension.setApplicationTimeExtension(new Date()); //延期申请时间    
		tsmCustExtension.setInputDate(new Date());                //录入时间                      
		tsmCustExtension.setInputAcc(user.getAccount());              //录入人   
		tsmCustExtension.setStatusExtended(new Short("2"));               //状态改为待审核
		tsmCustExtension.setApplicantExtensionName(user.getName());   //延期申请人姓名
		tsmCustExtension.setOrgId(user.getOrgId());
		try {
			tsmCustExtensionService.create(tsmCustExtension);
			tsmMessageService.createAuditDelayMessage(tsmCustExtension); // 发送消息
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return AppConstant.RESULT_SUCCESS;
	}
	
	/** 
	 * 获取查询时间
	 * @param type 1-当天 2-本周 3-本月 4-半年
	 * @return 
	 * @create  2015年12月14日 下午3:48:05 lixing
	 * @history  
	 */
	private String getStartDateStr(Integer type){
		String str = "";
		if(type == 1){
			str = DateUtil.formatDate(new Date(), DateUtil.DATE_DAY);
		}else if(type == 2){
			str = DateUtil.getWeekFirstDay(new Date());
		}else if(type == 3){
			str = DateUtil.getMonthFirstDay(new Date());
		}else if(type == 4){
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
}
