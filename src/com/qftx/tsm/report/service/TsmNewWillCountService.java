package com.qftx.tsm.report.service;

import com.qftx.base.util.DateUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.report.bean.TsmNewWillCountBean;
import com.qftx.tsm.report.dao.TsmNewWillCountMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TsmNewWillCountService {
	
private Log log = LogFactory.getLog(TsmNewWillCountService.class);
	
	@Autowired
	private TsmNewWillCountMapper tsmNewWillCountMapper;
	
	public void updateTsmNewWillCount(String orgId,String account,String name,int count){
		TsmNewWillCountBean bean = new TsmNewWillCountBean();
		try {
			bean.setOrgId(orgId);
			bean.setAccount(account);
			TsmNewWillCountBean retbean=tsmNewWillCountMapper.findTsmNewWillCountData_new(bean);
		    if(retbean!=null){//当天的需要累加
		    	bean.setTsmCountId(retbean.getTsmCountId());
		    	bean.setCount(count);
		    	tsmNewWillCountMapper.updateNum(bean);	
		    }else{//没有数据需要新建
				TsmNewWillCountBean bean_new = new TsmNewWillCountBean();
				String tsmCountId = SysBaseModelUtil.getModelId();
				bean_new.setTsmCountId(tsmCountId);
				bean_new.setOrgId(orgId);
				bean_new.setAccount(account);
				bean_new.setCount(count);
				Date start =DateUtil.dateBegin(new Date());
				bean_new.setInputDate(start);
				bean_new.setName(name);
		    	tsmNewWillCountMapper.insertTsmNewWillCount(bean_new);
		    }
		} catch (Exception e) {
			log.error("新增资源是吧！",e);
		}

	}
	
	/*
	 * /dDateType：1是当天，2是本周，3是本月，4是半年 ,(1,2,3,4时startDate、endDate为空即可)
	 * 5是会根据传进来的startDate、endDate来查询
	 * 
	 * 
	 */
	public int findTsmNewWillCount(String orgId,String account,String dDateType,String startDate,String endDate){
		TsmNewWillCountBean bean = new TsmNewWillCountBean();
		int count=0;
		try {
			bean.setOrgId(orgId);
			bean.setAccount(account);
	        //  发送时间
        	if(StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)){
        		bean.setStartDate(getStartDateStr(Integer.parseInt(dDateType)));
        		bean.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
        	}else if(StringUtils.isNotBlank(dDateType)&&"5".equals(dDateType)){
        		bean.setStartDate(startDate);
        		bean.setEndDate(endDate);
        	}
			
		    String retcount=tsmNewWillCountMapper.findTsmNewWillCountDataBydate(bean);
		    if(retcount!=null&& Integer.valueOf(retcount) >0){//当天的需要累加
		    	count=Integer.valueOf(retcount);
		    }
		} catch (Exception e) {
			log.error("新增资源是吧！",e);
		}
		return count;

	}
	
	/**
	 * 获取第一天
	 * 
	 * @param type
	 *            1-当天 2-本周 3-本月 4-半年
	 * @return
	 * @create 2015年12月14日 下午3:48:05 
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
	
	public Integer getUserTodayNewWillCount(String orgId,String account){
		TsmNewWillCountBean bean = new TsmNewWillCountBean();
		bean.setOrgId(orgId);
		bean.setAccount(account);
	    String retcount=tsmNewWillCountMapper.findTsmNewWillCountData(bean);
	    Integer count = 0;
	    if(retcount != null && Integer.valueOf(retcount) >0) count = Integer.valueOf(retcount);
	    return count;
	}

	public Map<String, Integer> getUsersTodayNewWillCount(String orgId,List<String> accounts){
		Map<String, Integer> map = new HashMap<String, Integer>();
		if(accounts != null && accounts.size() > 0){
			List<TsmNewWillCountBean> beans = tsmNewWillCountMapper.getUsersCount(orgId, accounts);
			for(TsmNewWillCountBean bean : beans) map.put(bean.getAccount(), bean.getCount());
		}
		return map;
	}
	
	private Date getNeedTime(int hour,int minute,int second,int addDay,int ...args){
	    Calendar calendar = Calendar.getInstance();
	    if(addDay != 0){
	        calendar.add(Calendar.DATE,addDay);
	    }
	    calendar.set(Calendar.HOUR_OF_DAY,hour);
	    calendar.set(Calendar.MINUTE,minute);
	    calendar.set(Calendar.SECOND,second);
	    if(args.length==1){
	        calendar.set(Calendar.MILLISECOND,args[0]);
	    }
	    return calendar.getTime();
	}

}
