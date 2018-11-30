package com.qftx.tsm.plan.user.day.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.tsm.plan.CustomFieldUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.Enumeration;

public class PlanUserDayBaseAction extends BaseAction{
	@Autowired
	private CustomFieldUtils customFieldUtils;
	
	/*页面加载数据*/
	public void processRequest(HttpServletRequest httpServletRequest,ModelMap model){
		Enumeration<String> params = httpServletRequest.getParameterNames();
		while (params.hasMoreElements()) {
			String param = (String) params.nextElement();
			String value = httpServletRequest.getParameter(param);
			model.put(param, value);
		}
		
		String userAcc = (String)model.get("userAcc");
		String dateStr = (String)model.get("dateStr");
		
		ShiroUser user = ShiroUtil.getUser();
		model.put("isMine", user.getAccount().equals(userAcc));
		model.put("signSetting", getSignSetting());
		model.put("todayFlag", DateUtil.dateBegin(DateUtil.parseDate(dateStr)).compareTo(DateUtil.dateBegin(new Date())));
		model.put("isState",user.getIsState());//个人版 企业版
		model.put("idReplaceWord", customFieldUtils.getIsReplaceWord(user));
		model.put("filedMap", customFieldUtils.getCustomFiled(user));
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
	
}
