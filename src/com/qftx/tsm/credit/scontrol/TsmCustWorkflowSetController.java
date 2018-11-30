package com.qftx.tsm.credit.scontrol;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.ExecutorUtils;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.credit.service.LoanReviewService;
import com.qftx.tsm.credit.service.TsmCustWorkflowSetService;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.credit.bean.TsmCustWorkflowSetBean;
import com.qftx.tsm.sys.service.CustFieldSetService;
/*
 * 工作流程设置
 * 
 * */
@Controller
@RequestMapping("/workflowSet")
public class TsmCustWorkflowSetController {
	Logger logger=Logger.getLogger(TsmCustWorkflowSetController.class);
	@Autowired private TsmCustWorkflowSetService tsmCustWorkflowSetService;
	@Autowired private LoanReviewService loanReviewService;
	@Autowired private CachedService cachedService;
	
	/** 
	 * 工作流程页面
	 */
	@RequestMapping("/toWorkflowSetPage")
	public String toWorkflowSetPage(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			TsmCustWorkflowSetBean entity=new TsmCustWorkflowSetBean();
			entity.setOrgId(user.getOrgId());
			entity=tsmCustWorkflowSetService.getByCondtion(entity);
				Map<String, String> map=cachedService.getOrgUserNames(user.getOrgId());
				if(entity.getAuditorAcc1()!=null){
					entity.setAuditorName1(map.get(entity.getAuditorAcc1()));
				}
				if(entity.getAuditorAcc2()!=null){
					entity.setAuditorName2(map.get(entity.getAuditorAcc2()));
				}
				if(entity.getAuditorAcc3()!=null){
					entity.setAuditorName3(map.get(entity.getAuditorAcc3()));
				}
			request.setAttribute("entity", entity);			
		}catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/qupai/sys/workflowSet/queryWorkflowSet";
	}
	
	
	
	/** 工作流程保存 */
	@RequestMapping("/saveData")
	@ResponseBody
	public String saveData(HttpServletRequest request,TsmCustWorkflowSetBean entity){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			
			if(StringUtils.isNotBlank(entity.getWorkflowId())){
				tsmCustWorkflowSetService.updateWorkset(entity, user);
				loanReviewService.initReviewInfo(user.getOrgId());
			}else{
				entity.setOrgId(user.getOrgId());
				tsmCustWorkflowSetService.insert(entity, user);
			}
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e){
			logger.error("工作流程 保存 异常！", e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}

}
