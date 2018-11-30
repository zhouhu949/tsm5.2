package com.qftx.tsm.report.scontrol;

import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysRunException;
import com.qftx.tsm.main.dto.RankReportDto;
import com.qftx.tsm.plan.user.month.service.PlanUserMonthService;
import com.qftx.tsm.report.dto.CallReportDto;
import com.qftx.tsm.report.dto.RankingReportDto;
import com.qftx.tsm.report.service.CallReportService;
import com.qftx.tsm.report.service.RankingReportService;
import com.qftx.tsm.rest.service.CallRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * ���а��ѯ
 * User�� bxl
 * Date�� 2015/12/18
 * Time�� 15:29
 */
@Controller
@RequestMapping(value = "/report/rankingList")
public class RankingListAction {
    private static final Logger logger = Logger.getLogger(RankingListAction.class.getName());
    public static final String AVG_NAME = "平均哥";
    @Autowired
    private UserService userService;
    @Autowired
    private CallRecordService callRecordService;
    @Autowired 
    private PlanUserMonthService planUserMonthService;
    @Autowired
    private OrgGroupUserService orgGroupUserService;
    @Autowired
    private RankingReportService rankingReportService;
    @Autowired
    private CallReportService callReportService;
    
    @RequestMapping()
    public String list(HttpServletRequest request, HttpServletResponse response,String yearStr,String monthStr) throws Exception {
    	return "report/rankingList";
    }
    
    @RequestMapping("/data")
    public String data(HttpServletRequest request,String yearStr,String monthStr,String groupIds){
    	try {
			ShiroUser user = ShiroUtil.getShiroUser();
			Calendar cal = Calendar.getInstance();
			if(StringUtils.isBlank(yearStr)){
				yearStr = cal.get(Calendar.YEAR)+"";
				monthStr = (cal.get(Calendar.MONTH)+1)+"";
			}
			if(StringUtils.isBlank(monthStr)){
				monthStr=null;
			}
			// 查询 用户集合
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("orgId", user.getOrgId());
    		if(StringUtils.isNotBlank(monthStr)){
        		map.put("startDate", DateUtil.getMonthFirst(Integer.parseInt(yearStr), Integer.parseInt(monthStr)));
        		map.put("endDate", DateUtil.getMonthLast(Integer.parseInt(yearStr), Integer.parseInt(monthStr)) + " 23:59:59");
        	}else{
        		map.put("startDate", DateUtil.getYearFirst(Integer.parseInt(yearStr)));
        		map.put("endDate", DateUtil.getYearLast(Integer.parseInt(yearStr)) + " 23:59:59");
        	}
    		
			List<RankingReportDto> rankingReportDtos = new ArrayList<RankingReportDto>();
			if(StringUtils.isBlank(groupIds)){
				rankingReportDtos = rankingReportService.getRankingList(user.getOrgId(), null, yearStr, monthStr,user.getAccount());
			}else{
				map.put("groupIds", Arrays.asList(groupIds.split(",")));
				rankingReportDtos = rankingReportService.getRankingList(user.getOrgId(), Arrays.asList(groupIds.split(",")), yearStr, monthStr,null);
			}
			
			//排行榜
			List<RankReportDto> signDtos = new ArrayList<RankReportDto>();
			List<RankReportDto> saleDtos = new ArrayList<RankReportDto>();
			List<RankReportDto> willDtos = new ArrayList<RankReportDto>();
			List<RankReportDto> callDtos = new ArrayList<RankReportDto>();
			List<RankReportDto> calloutDtos = new ArrayList<RankReportDto>();
			List<String> accounts = new ArrayList<String>();
			for(RankingReportDto pub : rankingReportDtos){
				RankReportDto rrd = new RankReportDto();
				rrd.setUserName(pub.getUserName());
				rrd.setSignNum(pub.getSignNums());
				rrd.setUserAccount(pub.getAccount());
				rrd.setImgUrl(pub.getImgUrl());
				signDtos.add(rrd);
				RankReportDto rrd1 = new RankReportDto();
				rrd1.setUserName(pub.getUserName());
				rrd1.setSaleNum(pub.getSaleAmounts());
				rrd1.setUserAccount(pub.getAccount());
				rrd1.setImgUrl(pub.getImgUrl());
				saleDtos.add(rrd1);
				RankReportDto rrd2 = new RankReportDto();
				rrd2.setUserName(pub.getUserName());
				rrd2.setWillNum(pub.getIntNums());
				rrd2.setUserAccount(pub.getAccount());
				rrd2.setImgUrl(pub.getImgUrl());
				willDtos.add(rrd2);
				accounts.add(pub.getAccount());
			}
			// 查询 用户集合
			List<RankReportDto> dtos = rankingReportService.getCallRankingList(map);
			Map<String,CallReportDto> todayMap = new HashMap<String, CallReportDto>();
    		if((yearStr.equals(cal.get(Calendar.YEAR)+"") && monthStr == null) || ((cal.get(Calendar.MONTH)+1)+"").equals(monthStr)){
    			todayMap = callReportService.getDayReportByAccountsMap(user.getOrgId(), accounts);
    		}
    		for(RankReportDto dto : dtos){
    			if((yearStr.equals(cal.get(Calendar.YEAR)+"") && monthStr == null) || ((cal.get(Calendar.MONTH)+1)+"").equals(monthStr)){
	    			CallReportDto td = todayMap.get(dto.getUserAccount());
	    			dto.setCallOutTotal(dto.getCallOutTotal()+td.getCalloutTotal());
	    			BigDecimal callTime = dto.getCallTime().add(BigDecimal.valueOf(td.getCalloutTime()));
	    			dto.setCallTime(callTime);
    			}
    			callDtos.add(dto);
    			RankReportDto numDto = new RankReportDto();
    			BeanUtils.copyProperties(dto, numDto);
    			calloutDtos.add(numDto);
    		}
			//排名
			saleDtos = saleNumRank(saleDtos, user);
			Map<String, RankReportDto> saleTop3 = getTopThree(saleDtos);
			signDtos = signNumRank(signDtos,user);
			Map<String, RankReportDto> signTop3 = getTopThree(signDtos);
			willDtos = willNumRank(willDtos, user);
			Map<String, RankReportDto> willTop3 = getTopThree(willDtos);
			callDtos = callTimeRank(callDtos, user);
			Map<String, RankReportDto> callTop3 = getTopThree(callDtos);
			calloutDtos = callOutRank(calloutDtos, user);
			Map<String, RankReportDto> callOutTop3 = getTopThree(calloutDtos);
			request.setAttribute("saleDtos", saleDtos);
			request.setAttribute("saleTop3", saleTop3);
			request.setAttribute("signDtos", signDtos);
			request.setAttribute("signTop3", signTop3);
			request.setAttribute("willDtos", willDtos);
			request.setAttribute("willTop3", willTop3);
			request.setAttribute("callDtos", callDtos);
			request.setAttribute("callTop3", callTop3);
			request.setAttribute("calloutDtos", calloutDtos);
			request.setAttribute("callOutTop3", callOutTop3);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
    	return "report/rank";
    }
    
    public Map<String, RankReportDto> getTopThree(List<RankReportDto> dtos){
    	Map<String, RankReportDto> map = new HashMap<String, RankReportDto>();
    	int z = 0;
    	for(int i=0;i<dtos.size();i++){
    		if(dtos.get(i).getUserName().equals(AVG_NAME)){
    			continue;
    		}
    		if(z<3){
    			map.put((z+1)+"", dtos.get(i));
    			z++;
    		}
    	}
    	return map;
    }
    
    /**
     * 签约客户排名
     * @param dtos
     * @param user
     * @return 
     * @create  2016年1月23日  下午3:03:24 lixing
     * @history  
     */
    public List<RankReportDto> signNumRank(List<RankReportDto> dtos,ShiroUser user){
		//先排序
		Comparator<RankReportDto> descComp =Collections.reverseOrder(new SignNumComparator());
		Collections.sort(dtos,descComp);
		//计算排名
		Integer rank = 0;
		Map<Integer, RankReportDto> maps = new HashMap<Integer, RankReportDto>();
		Integer totalSignNum = 0,avgSignNum = 0;
		for(RankReportDto dto : dtos){
			totalSignNum+=dto.getSignNum();
			if(maps.get(dto.getSignNum()) == null){
				rank++;
				dto.setRank(rank);
				maps.put(dto.getSignNum(), dto);
			}else{
				dto.setRank(rank);
			}
			if(dto.getUserAccount().equals(user.getAccount())){
				dto.setUserAccount("curr_user_account");
			}
		}
		if(dtos.size()>0){
			avgSignNum = totalSignNum/dtos.size();
			for(int i = 0 ; i < dtos.size() ; i++){
				RankReportDto dto = dtos.get(i);
				if(dto.getSignNum() < avgSignNum){
					RankReportDto rrd = new RankReportDto();
					rrd.setSignNum(avgSignNum);
					rrd.setUserName(AVG_NAME);
					rrd.setUserAccount("sign_num_avg");
					dtos.add(i,rrd);
					break;
				}
				if(i== (dtos.size() -1)){
					RankReportDto rrd = new RankReportDto();
					rrd.setSignNum(avgSignNum);
					rrd.setUserName(AVG_NAME);
					rrd.setUserAccount("sign_num_avg");
					dtos.add(rrd);
					break;
				}
			}
		}
		return dtos;
	}
    
    /** 
	 * 计算签约金额排名
	 * @param dtos
	 * @return 
	 * @create  2016年1月23日  下午3:03:24 lixing
	 * @history  
	 */
	public List<RankReportDto> saleNumRank(List<RankReportDto> dtos,ShiroUser user){
		//先排序
		Comparator<RankReportDto> descComp = Collections.reverseOrder(new SaleAmountComparator());
		Collections.sort(dtos,descComp);
		//计算排名
		Integer rank = 0;
		Map<BigDecimal, RankReportDto> maps = new HashMap<BigDecimal, RankReportDto>();
		BigDecimal totalSaleNum = BigDecimal.valueOf(0),avgSaleNum = BigDecimal.valueOf(0);
		for(RankReportDto dto : dtos){
			totalSaleNum=totalSaleNum.add(dto.getSaleNum());
			if(maps.get(dto.getSaleNum()) == null){
				rank++;
				dto.setRank(rank);
				maps.put(dto.getSaleNum(), dto);
			}else{
				dto.setRank(rank);
			}
			if(dto.getUserAccount().equals(user.getAccount())){
				dto.setUserAccount("curr_user_account");
			}
		}
		if(dtos.size() > 0){
			avgSaleNum = totalSaleNum.divide(BigDecimal.valueOf(dtos.size()),2,BigDecimal.ROUND_HALF_EVEN);
			for(int i = 0 ; i < dtos.size() ; i++){
				RankReportDto dto = dtos.get(i);
				if(dto.getSaleNum().compareTo(avgSaleNum) == -1){
					RankReportDto rrd = new RankReportDto();
					rrd.setSaleNum(avgSaleNum);
					rrd.setUserName(AVG_NAME);
					rrd.setUserAccount("sale_num_avg");
					dtos.add(i,rrd);
					break;
				}
				if(i== (dtos.size() -1)){
					RankReportDto rrd = new RankReportDto();
					rrd.setSaleNum(avgSaleNum);
					rrd.setUserName(AVG_NAME);
					rrd.setUserAccount("sale_num_avg");
					dtos.add(rrd);
					break;
				}
			}
		}
		return dtos;
	}
    
	
	/** 
	 * 意向客户排名
	 * @param dtos
	 * @param user
	 * @return 
	 * @create  2016年1月23日 下午3:13:29 lixing
	 * @history  
	 */
	public List<RankReportDto> willNumRank(List<RankReportDto> dtos,ShiroUser user){
		Comparator<RankReportDto> descComp = Collections.reverseOrder(new WillNumComparator());
		Collections.sort(dtos,descComp);
		//计算排名
		Integer rank = 0;
		Map<Integer, RankReportDto> maps = new HashMap<Integer, RankReportDto>();
		Integer totalWillNum=0,avgWillNum=0;
		for(RankReportDto dto : dtos){
			totalWillNum+=dto.getWillNum();
			if(maps.get(dto.getWillNum()) == null){
				rank++;
				dto.setRank(rank);
				maps.put(dto.getWillNum(), dto);
			}else{
				dto.setRank(rank);
			}
			if(dto.getUserAccount().equals(user.getAccount())){
				dto.setUserAccount("curr_user_account");
			}
		}
		if(dtos.size()>0){
			avgWillNum = totalWillNum/dtos.size();
			for(int i = 0 ; i < dtos.size() ; i++){
				RankReportDto dto = dtos.get(i);
				if(dto.getWillNum() < avgWillNum){
					RankReportDto rrd = new RankReportDto();
					rrd.setWillNum(avgWillNum);
					rrd.setUserName(AVG_NAME);
					rrd.setUserAccount("sign_num_avg");
					dtos.add(i,rrd);
					break;
				}
				if(i== (dtos.size() -1)){
					RankReportDto rrd = new RankReportDto();
					rrd.setWillNum(avgWillNum);
					rrd.setUserName(AVG_NAME);
					rrd.setUserAccount("sign_num_avg");
					dtos.add(rrd);
					break;
				}
			}
		}
		return dtos;
	}
	
	
	/** 
	 * 呼出电话数排名
	 * @param dtos
	 * @param user
	 * @return 
	 * @create  2016年1月23日 下午3:13:29 lixing
	 * @history  
	 */
	public List<RankReportDto> callOutRank(List<RankReportDto> dtos,ShiroUser user){
		Comparator<RankReportDto> descComp = Collections.reverseOrder(new CallOutNumComparator());
		Collections.sort(dtos,descComp);
		//计算排名
		Integer rank = 0;
		Map<Integer, RankReportDto> maps = new HashMap<Integer, RankReportDto>();
		Integer totalCallOut=0,avgCallOut=0;
		for(RankReportDto dto : dtos){
			totalCallOut+=dto.getCallOutTotal();
			if(maps.get(dto.getCallOutTotal()) == null){
				rank++;
				dto.setRank(rank);
				maps.put(dto.getCallOutTotal(), dto);
			}else{
				dto.setRank(rank);
			}
			if(dto.getUserAccount().equals(user.getAccount())){
				dto.setUserAccount("curr_user_account");
			}
		}
		if(dtos.size()>0){
			avgCallOut = totalCallOut/dtos.size();
			for(int i = 0 ; i < dtos.size() ; i++){
				RankReportDto dto = dtos.get(i);
				if(dto.getCallOutTotal() < avgCallOut){
					RankReportDto rrd = new RankReportDto();
					rrd.setCallOutTotal(avgCallOut);
					rrd.setUserName(AVG_NAME);
					rrd.setUserAccount("sign_num_avg");
					dtos.add(i,rrd);
					break;
				}
				if(i== (dtos.size() -1)){
					RankReportDto rrd = new RankReportDto();
					rrd.setCallOutTotal(avgCallOut);
					rrd.setUserName(AVG_NAME);
					rrd.setUserAccount("sign_num_avg");
					dtos.add(rrd);
					break;
				}
			}
		}
		return dtos;
	}
	
	/** 
	 * 通话时长排名
	 * @param dtos
	 * @param user
	 * @return 
	 * @create  2016年1月25日 下午7:21:44 lixing
	 * @history  
	 */
	public List<RankReportDto> callTimeRank(List<RankReportDto> dtos,ShiroUser user){
		Comparator<RankReportDto> descComp = Collections.reverseOrder(new CallTimeComparator());
		Collections.sort(dtos,descComp);
		//计算排名
		Integer rank = 0;
		Map<BigDecimal, RankReportDto> maps = new HashMap<BigDecimal, RankReportDto>();
		BigDecimal totalCallTime = BigDecimal.valueOf(0),avgCallTime = BigDecimal.valueOf(0);
		for(RankReportDto dto : dtos){
			totalCallTime=totalCallTime.add(dto.getCallTime());
			if(maps.get(dto.getCallTime()) == null){
				rank++;
				dto.setRank(rank);
				maps.put(dto.getCallTime(), dto);
			}else{
				dto.setRank(rank);
			}
			if(dto.getUserAccount().equals(user.getAccount())){
				dto.setUserAccount("curr_user_account");
			}
		}
		if(dtos.size() > 0){
			avgCallTime = totalCallTime.divide(BigDecimal.valueOf(dtos.size()),2);
			for(int i = 0 ; i < dtos.size() ; i++){
				RankReportDto dto = dtos.get(i);
				if(dto.getCallTime().compareTo(avgCallTime) == -1){
					RankReportDto rrd = new RankReportDto();
					rrd.setCallTime(avgCallTime);
					rrd.setUserName(AVG_NAME);
					rrd.setUserAccount("sale_num_avg");
					dtos.add(i,rrd);
					break;
				}
				if(i== (dtos.size() -1)){
					RankReportDto rrd = new RankReportDto();
					rrd.setCallTime(avgCallTime);
					rrd.setUserName(AVG_NAME);
					rrd.setUserAccount("sale_num_avg");
					dtos.add(rrd);
					break;
				}
			}
		}
		return dtos;
	}
	
}

/** 
* 签约排名
* @author: lixing
* @since: 2016年1月23日  下午3:03:24
* @history:
*/
class SignNumComparator implements Comparator<RankReportDto>{
	public int compare(RankReportDto o1, RankReportDto o2) {
		return o1.getSignNum()-o2.getSignNum();
	}
}


/** 
* 签约金额排名
* @author: lixing
* @since: 2016年1月23日  下午3:03:24
* @history:
*/
class SaleAmountComparator implements Comparator<RankReportDto>{
	public int compare(RankReportDto o1, RankReportDto o2) {
		return o1.getSaleNum().compareTo(o2.getSaleNum());
	}
}


 /** 
 * 意向客户排名
 * @author: lixing
 * @since: 2016年1月23日  下午3:03:24
 * @history:
 */
class WillNumComparator implements Comparator<RankReportDto>{
	public int compare(RankReportDto o1, RankReportDto o2) {
		return o1.getWillNum()-o2.getWillNum();
	}
}

/** 
 * 意向客户排名
 * @author: lixing
 * @since: 2016年1月23日  下午3:03:24
 * @history:
 */
class CallOutNumComparator implements Comparator<RankReportDto>{
	public int compare(RankReportDto o1, RankReportDto o2) {
		return o1.getCallOutTotal()-o2.getCallOutTotal();
	}
}

 /** 
 * 通话时长排名
 * @author: lixing
 * @since: 2016年1月25日  下午7:17:58
 * @history:
 */
class CallTimeComparator implements Comparator<RankReportDto>{
	public int compare(RankReportDto o1, RankReportDto o2) {
		if(o1.getCallTime().compareTo(o2.getCallTime()) == 0){
			return o2.getUserAccount().compareTo(o1.getUserAccount());
		}else{
			return o1.getCallTime().compareTo(o2.getCallTime());
		}
	}
	
}