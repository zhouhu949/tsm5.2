package com.qftx.tsm.worklog.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.tsm.worklog.bean.WorkLogInfoBean;
import com.qftx.tsm.worklog.dto.WorkLogDto;
import com.qftx.tsm.worklog.service.WorkLogInfoService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/worklog")
public class WorkLogControl {
	@Autowired
	private WorkLogInfoService workLogInfoService;
	@Autowired
	private TsmTeamGroupService tsmTeamGroupService;
	Logger logger = Logger.getLogger(WorkLogControl.class);

	/* 我的日志主页面 */
	@RequestMapping("/main")
	public String main(ModelMap modelMap,Long dateTime) {
		try {
			if(dateTime==null) dateTime=System.currentTimeMillis();
			ShiroUser user = getUser();
			
			List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
			if(groups!=null && groups.size()>0){
				modelMap.put("isManager", true);
			}else{
				modelMap.put("isManager", false);
			}
			
			modelMap.put("dateTime", dateTime);
			modelMap.put("server_dateTime", System.currentTimeMillis());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/worklog/workLog";
	}
	
	/* 我的日志日历状态 */
	@RequestMapping("/myLogState")
	@ResponseBody
	public List<Date> calendarState(long dateTime) {
		try {
			ShiroUser user = getUser();
			return workLogInfoService.getCalendarState(user.getOrgId(),user.getId(),dateTime);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ArrayList<Date>();
		}
		
	}
	
	/* 我的日志日历状态 */
	@RequestMapping("/managerLogState")
	@ResponseBody
	public List<Date> managerCalendarState(long dateTime) {
		try {
			ShiroUser user = getUser();
			List<Date> list = workLogInfoService.getManagerCalendarState(user,dateTime);
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ArrayList<Date>();
		}
		
	}
	
	/* 我的日志页面刷新数据 */
	@RequestMapping("/myLog")
	public String myLog(ModelMap modelMap,WorkLogInfoBean item) {
		modelMap.put("item", item);
		return "/worklog/myLog";
	}
	
	/* 日志文件列表 */
	@RequestMapping("/fileList")
	public String fileList(ModelMap modelMap,String data) {
		modelMap.put("data", data);
		return "/worklog/logIdialogFileList";
	}
	
	@RequestMapping("/myLogJson")
	@ResponseBody
	public Map<String, Object> myLogJson(WorkLogInfoBean item,String type) {
		try {
			Map<String, Object> map= new HashMap<String, Object>();
			ShiroUser user = getUser();
			item.setUserId(user.getId());
			List<WorkLogInfoBean> logs =  workLogInfoService.findWorkLogByUserId(user.getOrgId(),item,type);
			
			workLogInfoService.processDelable(logs);
			List<WorkLogDto> dtos = workLogInfoService.processLog(user,logs);
			logger.info(JsonUtil.getJsonString(dtos));
			map.put("dtos", dtos);
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new HashMap<String, Object>();
		}
	}
	
	/* 新建日志弹窗 */
	@RequestMapping("/newLogWindow")
	public String newLogWindow(ModelMap modelMap,String wliId,String title) {
		try {
			ShiroUser user = getUser();
			String logContext=null;
			String workPlan = null;
			
			if(wliId!=null){
				WorkLogInfoBean query = new WorkLogInfoBean();
				query.setOrgId(user.getOrgId());
				query.setWliId(wliId);
				
				query = workLogInfoService.getByCondtion(query);
				if(query!=null){
					Date logDate = query.getLogDate();
					logContext = query.getContext();
					workPlan = query.getWorkPlan();
					//2015年12月5日,星期六
					title=DateUtil.year(logDate)+"年"+DateUtil.month(logDate)+"月"+DateUtil.day(logDate)+"日,"+DateUtil.dayOfWeek(logDate);
				}
			}
			modelMap.put("wliId", wliId);
			modelMap.put("title", title);
			modelMap.put("context", logContext);
			modelMap.put("workPlan", workPlan);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/worklog/newLogWindow";
	}

	/* 插入日志数据 */
	@RequestMapping("/insert")
	@ResponseBody
	public BaseJsonResult insertWorkLog(long dateTime,WorkLogInfoBean workLogInfo) {
		try {
			//日志时间 使用系统最新的时间（时分秒）年月日由参数传递
			ShiroUser user = getUser();
			Date date = new Date(dateTime);
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, DateUtil.year(date));
			calendar.set(Calendar.MONTH, DateUtil.month(date)-1);
			calendar.set(Calendar.DATE, DateUtil.day(date));
			workLogInfo.setLogDate(calendar.getTime());
			
			if(StringUtils.isBlank(workLogInfo.getWorkPlan())){
				workLogInfo.setWorkPlan(null);
			}
			
			return workLogInfoService.insertWorkLog(user,workLogInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return BaseJsonResult.error();
		}
	}
	
	/* 编辑日志数据 */
	@RequestMapping("/edit")
	@ResponseBody
	public BaseJsonResult editWorkLog(WorkLogInfoBean workLogInfo) {
		try {
			ShiroUser user = getUser();
			workLogInfo.setOrgId(user.getOrgId());
			
			workLogInfoService.updateWorkLog(workLogInfo);
			return BaseJsonResult.success();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return BaseJsonResult.error();
		}
	}
	
	/* 编辑日志数据 */
	@RequestMapping("/del")
	@ResponseBody
	public BaseJsonResult del(WorkLogInfoBean delWorkLog) {
		try {
			ShiroUser user = getUser();
			delWorkLog.setOrgId(user.getOrgId());
			
			if(!StringUtils.isBlank(delWorkLog.getWliId())){
				return workLogInfoService.delWorkLog(user,delWorkLog);
			}else{
				return BaseJsonResult.error("日志ID为空！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return BaseJsonResult.error();
		}
	}

	/* 查询日志数据 */
	@RequestMapping("/queryWorkLogById")
	@ResponseBody
	public WorkLogInfoBean queryWorkLogById(WorkLogInfoBean query) {
		try {
			ShiroUser user = getUser();
			query.setOrgId(user.getOrgId());
			return workLogInfoService.getByCondtion(query);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new WorkLogInfoBean();
		}
		
	}

	/* 查询是当前日期否存在日志 */
	@RequestMapping("/queryIfWorkLogExist")
	@ResponseBody
	public boolean queryIfWorkLogExist(long dateTime) {
		try {
			ShiroUser user = getUser();
			return workLogInfoService.queryIfWorkLogExist(user.getOrgId(),user.getId(), new Date(dateTime));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return false;
		}
	}
	
	/* 查询未提交日志人员 */
	@RequestMapping("/queryNoLogUsers")
	@ResponseBody
	public BaseJsonResult queryNoLogUsers(long dateTime) {
		try {
			ShiroUser user = getUser();
			
			List<Map<String, String>> userNames =  workLogInfoService.findNoLogUsers(user, dateTime);
			BaseJsonResult result = BaseJsonResult.success(userNames);
			Date date = new Date(dateTime);
			if(DateUtil.dateBegin(date).equals(DateUtil.dateBegin(new Date()))){
				result.put("dateFlag", "今日");
			}else{
				result.put("dateFlag", DateUtil.formatDate(date, "MM月dd日"));
			}
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return BaseJsonResult.error();
		}
		
	}

	public ShiroUser getUser() {
		return ShiroUtil.getShiroUser();
	}

}
