package com.qftx.tsm.cust.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.cust.service.ComResourceService;
import com.qftx.tsm.cust.service.ResCustEventService;
import com.qftx.tsm.cust.service.ResCustInfoLogService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.log.service.LogContactDayDataService;
import com.qftx.tsm.log.util.ContactUtil;
import com.qftx.tsm.main.dao.ContactDayDataMapper;
import com.qftx.tsm.main.service.ContactDayDataService;
import com.qftx.tsm.main.service.MainInfoService;
import com.qftx.tsm.plan.facade.PlanFactService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/res/cust")
public class ResCustInfoLogAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ResCustInfoLogAction.class);
	@Resource
	private ResCustInfoLogService resCustInfoLogService;
	@Resource
	private ComResourceService comResourceService;
	@Resource
	private MainInfoService mainInfoService;
	@Resource
	private PlanFactService planFactService;
	@Autowired
	private ResCustEventService resCustEventService;
	@Autowired
	private ContactDayDataMapper contactDayDataMapper;
	@Autowired
	private ContactDayDataService contactDayDataService;
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private LogContactDayDataService logContactDayDataService;

	@RequestMapping(value = "toResLog")
	public String toResLog(HttpServletRequest request) {
		try {
			request.setAttribute("defDate", getCustCacheDate(request));
			request.setAttribute("concatName", request.getParameter("concatName"));
		} catch (Exception e) {
			logger.error("toResLog-->异常", e);
		}
		return "tsm/resource/resourcebz";
	}

	/**
	 * 添加资源备注
	 * 
	 * @param request
	 * @return
	 * @create 2015年12月26日 下午2:39:26 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "addResLog")
	public String addResLog(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getUser();
		String context = request.getParameter("context");
		String custId = request.getParameter("custId");
		String concatName = request.getParameter("concatName");
		String nextConcatTime = request.getParameter("nextConcatTime");
		Map<String, String> resMap = new HashMap<String, String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ResCustDto resCustDto = null;
		resMap.put("resId", custId);
		resMap.put("orgId", user.getOrgId());
		resCustDto = comResourceService.getResById(resMap);
		try {
			resCustInfoLogService.createResourceBZ(custId, context, nextConcatTime, user, concatName);
			
			if(nextConcatTime!= null && !"".equals(nextConcatTime)){
			DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = fmt.parse(nextConcatTime);
			resCustInfoService.updateCustsNextFollowDate(user.getOrgId(), custId,date);//修改资源
			boolean result=false;
			if(date!=null){
				 result = DateUtil.isNow(date);
				if(result==true){
					planFactService.updateContactResult(user.getOrgId(),user.getGroupId(),user.getId(),custId,"res",true,"res");
				}
			}
			}

			if (resCustDto != null) {
				resultMap.put("resId", resCustDto.getResCustId());
				resultMap.put("status", resCustDto.getStatus() + "");
				resultMap.put("name", resCustDto.getName());
				resultMap.put("isCall", 1);
				resultMap.put("phone", resCustDto.getMobilephone());
				resultMap.put("type", resCustDto.getType());
				resultMap.put("filterType", resCustDto.getFilterType());
			}
//			contactDayDataService.contactStatusLog(user.getOrgId(), user.getAccount(), custId, 1, 3);

			logContactDayDataService.addLogContactDayData(ContactUtil.RES_CONTACT, user.getOrgId(), resCustDto.getResCustId(),resCustDto.getOwnerAcc(), resCustDto.getLastOptionId(), resCustDto.getSaleProcessId());
			return JsonUtil.getJsonString(resultMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.debug("loginName =" + user.getName() + "添加资源备注异常！");
			return AppConstant.RESULT_EXCEPTION;
		}
	}

}
