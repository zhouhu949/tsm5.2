package com.qftx.tsm.cust.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.log.query.bean.LogResOperateBatchQueryDto;
import com.qftx.log.tablestore.bean.TsGetRangePage;
import com.qftx.log.tablestore.bean.TsGetRangePageResp;
import com.qftx.tsm.cust.dto.LogBatchInfoDto;
import com.qftx.tsm.log.service.LogBatchInfoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

/**
 * 資源操作日誌
 *
 * @author: zjh
 * @history:
 */
@Controller
@RequestMapping(value = "resOperate/")
public class LogResOperateAction {
	private Logger logger = Logger.getLogger(LogResOperateAction.class);
	@Autowired
	private LogBatchInfoService logBatchInfoService;
	@Autowired private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Autowired private CachedService cachedService;
	
	@RequestMapping("/queryLogResOperateList")
	public String queryLogResOperateList(HttpServletRequest request,HttpServletResponse response,LogBatchInfoDto logBatchInfoDto) {
		return "bill/log_distribution_resource";
	}
	
	/** 
	 * 資源操作日志
	 */
	@ResponseBody
	@RequestMapping("/getLogResOperateJson")
	public  Map<String, Object> getLogResOperateJson(HttpServletRequest request,LogBatchInfoDto logBatchInfoDto,String pageStr){
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			TsGetRangePage pages=new TsGetRangePage();
			if(pageStr!=null&&!"".endsWith(pageStr)){
			    pages = JSON.parseObject(pageStr, TsGetRangePage.class);
			}
			LogResOperateBatchQueryDto dto = new LogResOperateBatchQueryDto();
			dto.setOrgId(user.getOrgId());
			List<LogBatchInfoDto> list = new ArrayList<>();
			dto.setResOperateId(logBatchInfoDto.getType());
			dto.setUserAcc(logBatchInfoDto.getOperateAcc());
			dto.setOwnerAcc(logBatchInfoDto.getOwnerAcc());
			/*List<String> shareAccList = cachedService.getMemberAccs(user.getOrgId(),user.getAccount());
			logBatchInfoDto.setOrgId(user.getOrgId());
			logBatchInfoDto.setShareAccList(shareAccList);*/
			/*if(StringUtils.isNotBlank(logBatchInfoDto.getAccountsStr())){
				logBatchInfoDto.setAccounts(Arrays.asList(logBatchInfoDto.getAccountsStr().split(",")));
			}*/
			if(logBatchInfoDto.getdDateType() != null && logBatchInfoDto.getdDateType() != 0 && logBatchInfoDto.getdDateType() != 5){
				logBatchInfoDto.setStartDate(getStartDateStr(logBatchInfoDto.getdDateType()));
				logBatchInfoDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
				dto.setInputTimeBegin(getStartDateStr(logBatchInfoDto.getdDateType()) + " 00:00:00");
				dto.setInputTimeEnd(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY)+" 23:59:59");
			}
			int pageSize=0;
        	int pageNo=0;
        	if(pages.getShowCount()!=null){
            	 pageSize=pages.getShowCount();
        	}        	
        	if(pages.getShowCount()!=null){
            	 pageNo=pages.getCurrentPage();
        	}
			/*List<LogBatchInfoDto> logBatchInfoDtos =logBatchInfoService.getLogResOperateListPage(logBatchInfoDto);*/
        	TsGetRangePageResp req = logBatchInfoService.logBatchInfoService(dto,pages,pageSize,pageNo);
        	TsGetRangePage retpage=(TsGetRangePage) req.getPage();
        	/*if(retpage == null) {
        		retpage = new TsGetRangePage();
        		retpage.setTotalPage(0);
        		retpage.setCurrentPage(0);
        		retpage.setShowCount(pageSize);
        		retpage.setTotalResult(0);
        		retpage.setCurPageSize(pageSize);
        	}*/
        	List<Map<String, Object>> lits2=req.getBeans();
        	Map<String, String> names = cachedService.getOrgUserNames(user.getOrgId());
        	if(lits2!=null&&lits2.size()>0){
        		for(Map<String, Object> maps:lits2){
        			LogBatchInfoDto bean = new LogBatchInfoDto();
        			bean.setId((String) maps.get("id"));
        			bean.setOrgId(user.getOrgId());
        			bean.setOperateAcc((String)maps.get("userAcc"));
        			bean.setOperateName((String) maps.get("userName"));
        			bean.setOperateDate(new Date((Long)maps.get("inputTime")));
        			bean.setContext((String)maps.get("resOperateName"));
        			bean.setSize(maps.get("operateNum").toString());
        			bean.setOwnerAcc((String)maps.get("ownerAcc"));
        			bean.setType((String)maps.get("resOperateId"));
        			bean.setData((String)maps.get("data"));
        			bean.setOwnerName(names.get(bean.getOwnerAcc()));
        			list.add(bean);
        			
    			}
    		}
        	Map<String,TsGetRangePage> maps=new HashMap<String,TsGetRangePage>();
        	maps.put("page", retpage);
			jsonMap.put("list", list);
			jsonMap.put("item", maps);
			jsonMap.put("status", "true");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonMap.put("status", false);
			jsonMap.put("errorMsg", e.getMessage());
			return jsonMap;
		}
		return jsonMap;
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
	public static String getEndDateStr(Integer type) {
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
