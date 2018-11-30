package com.qftx.tsm.main.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.dto.BaseSend;
import com.qftx.base.auth.dto.Menu;
import com.qftx.base.auth.dto.MessageObj;
import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.auth.service.ProductMenuService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.bean.TsmTeamGroupMemberBean;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.base.util.MenuUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.quaryz.ExpireFollowQuartz;
import com.qftx.common.quaryz.ResourceExpireQuartz;
import com.qftx.common.quaryz.SignCustOrderExpireQuartz;
import com.qftx.common.util.CodeUtils;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.IContants;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.common.util.constants.SysConstant;
import com.qftx.tsm.bill.util.HDUtils;
import com.qftx.tsm.callrecord.dto.CallNalysisDto;
import com.qftx.tsm.callrecord.util.CallRecordGetUtil;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.main.bean.AuthUserResourceQuickBean;
import com.qftx.tsm.main.bean.BarrageQueue;
import com.qftx.tsm.main.bean.CardQueue;
import com.qftx.tsm.main.dto.RankReportDto;
import com.qftx.tsm.main.service.AuthUserResourceQuickService;
import com.qftx.tsm.main.service.ContactDayDataService;
import com.qftx.tsm.main.service.MainInfoService;
import com.qftx.tsm.main.service.MainService;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.service.DataDictionaryService;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;
import com.qftx.tsm.plan.group.month.dto.PlanGroupmonthReportDto;
import com.qftx.tsm.plan.group.month.service.PlanGroupmonthService;
import com.qftx.tsm.plan.user.day.bean.*;
import com.qftx.tsm.plan.user.day.dto.ContactResult;
import com.qftx.tsm.plan.user.day.dto.TeamDayPlanReportDto;
import com.qftx.tsm.plan.user.day.service.PlanUserDayResourceService;
import com.qftx.tsm.plan.user.day.service.PlanUserDayService;
import com.qftx.tsm.plan.user.day.service.PlanUserdaySigncustService;
import com.qftx.tsm.plan.user.day.service.PlanUserdayWillcustService;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;
import com.qftx.tsm.plan.user.month.service.PlanUserMonthService;
import com.qftx.tsm.report.dto.CallReportDto;
import com.qftx.tsm.report.dto.RankingReportDto;
import com.qftx.tsm.report.service.CallReportService;
import com.qftx.tsm.report.service.RankingReportService;
import com.qftx.tsm.sms.bean.TsmMessageSend;
import com.qftx.tsm.sms.dto.TsmMessageSendDto;
import com.qftx.tsm.sms.service.TsmMessageSendService;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.Points;
import com.qftx.tsm.sys.service.PointsService;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping(value="/main/sale")
public class MainAction extends BaseAction {
	private Logger logger = Logger.getLogger(MainAction.class);
	private static final String SUCCESS_CODE ="_SUCCESS";
	@Autowired private UserService userService;
	@Autowired private ProductMenuService menuService;
	@Autowired private AuthUserResourceQuickService authUserResourceQuickService;
	@Autowired private MainService mainService;
	@Autowired private PlanUserMonthService planUserMonthService;
	@Autowired private MainInfoService mainInfoService;
	@Autowired private PlanUserDayService planService;
	@Autowired private ResCustInfoService resCustInfoService;
	@Autowired private PlanUserDayResourceService planUserDayResourceService;
	@Autowired private PlanUserdayWillcustService planUserdayWillcustService;
	@Autowired private PlanUserdaySigncustService planUserdaySigncustService;
	@Autowired private PointsService pointsService;
	@Autowired private DataDictionaryService dataDictionaryService;
	@Autowired private PlanGroupmonthService planGroupmonthService;
	@Autowired private CachedService cachedService;
	@Autowired private TsmMessageSendService tsmMessageSendService;
	@Autowired private RankingReportService rankingReportService;
	@Autowired private ContactDayDataService contactDayDataService;
	@Autowired private TsmMessageService tsmMessageService;
	@Autowired private TsmTeamGroupService tsmTeamGroupService;
	@Autowired private OrgGroupService orgGroupService;
 	@Autowired private OrgGroupUserService orgGroupUserService;
    @Autowired private CallReportService callReportService;
	@Resource  private ResourceExpireQuartz resourceExpireQuartz;
	@Resource  private ExpireFollowQuartz expireFollowQuartz;
	@Resource  private SignCustOrderExpireQuartz signCustOrderExpireQuartz;
    
    public static final String AVG_NAME = "平均哥";
	@RequestMapping("/home")
	public String home(HttpServletResponse response,HttpServletRequest request){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			
			//获取帐号剩余通话时长
//			getUserTimeLen(request,user);
			//查询通话时长统计url
//			String callCountUrl = ConfigInfoUtils.getStringValue(CALL_COUNT_QUERY);
//			request.setAttribute("callCountUrl", callCountUrl);
			User searchUser = userService.getByAccount(user.getAccount());
			request.setAttribute("searchUser", searchUser);
			Integer dayCount = user.getServerDay();
			request.setAttribute("dayCount", dayCount);
			//邮箱
//    		TsmEmailConfig config = new TsmEmailConfig();
//    		config.setOrgId(user.getOrgId());
//    		config.setUserId(user.getId());
//    		config.setIsDel(0);
//    		List<TsmEmailConfig> tecs = tsmEmailConfigService.getListByCondtion(config);
//    		if(tecs !=null && tecs.size() >0){
//    			request.setAttribute("bindEmail", tecs.get(0).getLoginUser());
//    		}
			//消息
			TsmMessageSendDto entity = new TsmMessageSendDto();
	      	entity.setOrgId(user.getOrgId());
	      	entity.setSendTo(user.getAccount());
			TsmMessageSendDto item = tsmMessageSendService.getNoReadByCount2(entity);
			request.setAttribute("message", item);
						
			if(user.getAccount().contains("admin@")){
				return "/home/admin_main_home";
			}else{
				OrgGroupUser usermeber = new OrgGroupUser();
	            usermeber.setOrgId(user.getOrgId());
	            usermeber.setUserId(user.getId());
	            OrgGroupUser newmember = orgGroupUserService.getByCondtion(usermeber);
	            logger.debug("是否有组织结构=" + JSON.toJSONString(newmember));
	            OrgGroup orgGroup = orgGroupService.getByGroupId(user.getOrgId(),newmember.getGroupId());
	            // 设置用户类型
	            ShiroUtil.setSession(SysConstant.USER_GROUP_TYPE, orgGroup.getGroupType());
	            if(orgGroup.getGroupType() == 2){ // 客服
	            	response.sendRedirect(ConfigInfoUtils.getStringValue("tsm_service_url")+"/main/sale/home");
	            }else{ // 销售
	            	//获取快捷菜单
	    			getQuickMenu(request,user);
	            	if(user.getIssys() != null && user.getIssys() == 1){//管理者
						List<TeamGroupBean> teamGroups = mainService.queryManageGroupList(user.getAccount(),user.getOrgId());
						request.setAttribute("teamGroups", teamGroups);					
						return "/home/admin_home";
	            	}else{//普通销售
						getUserLevel(request,searchUser);
						setIsReplaceWord(request);
						PlanUsermonthBean monthPlan = planUserMonthService.queryByUserAndMonth(user.getOrgId(), user.getId(),new Date());
						request.setAttribute("monthPlan", monthPlan);
						return "/home/sale_home";
				}
			}
		 }	
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return null;
	}
	
	/*
	 * 获得排行榜信息
	 * 签约冠军，新增意向冠军，呼出时长冠军，呼出次数冠军
	 * */
	@RequestMapping("/getbranking")
	@ResponseBody
	    public Map<String,Object> getranking(HttpServletRequest request){
		        Map<String,List<RankReportDto>> returnmap=new HashMap<String,List<RankReportDto>>();
				Map<String,Object> map2=new HashMap<String,Object>();
	    	try {
				ShiroUser user = ShiroUtil.getShiroUser();
				Calendar cal = Calendar.getInstance();
				String yearStr="";
				String monthStr="";
				if(StringUtils.isBlank(yearStr)){
					yearStr = cal.get(Calendar.YEAR)+"";
					monthStr = (cal.get(Calendar.MONTH))+"";//查询上一个月的
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
				rankingReportDtos = rankingReportService.getRankingList(user.getOrgId(), null, yearStr, monthStr,user.getAccount());
				
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
					rrd.setContent(pub.getUserName());
					rrd.setUserAccount(pub.getAccount());
					rrd.setContent(pub.getUserName());
					signDtos.add(rrd);
					RankReportDto rrd1 = new RankReportDto();
					rrd1.setUserName(pub.getUserName());
					rrd1.setSaleNum(pub.getSaleAmounts());
					rrd1.setUserAccount(pub.getAccount());
					rrd1.setContent(pub.getUserName());
					saleDtos.add(rrd1);
					RankReportDto rrd2 = new RankReportDto();
					rrd2.setUserName(pub.getUserName());
					rrd2.setWillNum(pub.getIntNums());
					rrd2.setUserAccount(pub.getAccount());
					rrd2.setContent(pub.getUserName());
					willDtos.add(rrd2);
					accounts.add(pub.getAccount());
				}
				// 查询 用户集合
				List<RankReportDto> dtos = rankingReportService.getCallRankingList(map);
				Map<String,CallReportDto> todayMap = new HashMap<String, CallReportDto>();
	    		if((yearStr.equals(cal.get(Calendar.YEAR)+"") && monthStr == null) || ((cal.get(Calendar.MONTH))+"").equals(monthStr)){
	    			todayMap = callReportService.getDayReportByAccountsMap(user.getOrgId(), accounts);
	    		}
	    		for(RankReportDto dto : dtos){
	    			if((yearStr.equals(cal.get(Calendar.YEAR)+"") && monthStr == null) || ((cal.get(Calendar.MONTH))+"").equals(monthStr)){
		    			CallReportDto td = todayMap.get(dto.getUserAccount());
		    			dto.setCallOutTotal(dto.getCallOutTotal()+td.getCalloutTotal());
		    			BigDecimal callTime = dto.getCallTime().add(BigDecimal.valueOf(td.getCalloutTime()));
		    			dto.setCallTime(callTime);
		    			dto.setContent(dto.getUserName());
	    			}
	    			callDtos.add(dto);
	    			RankReportDto numDto = new RankReportDto();
	    			BeanUtils.copyProperties(dto, numDto);
	    			calloutDtos.add(numDto);
	    		}
				//排名
				saleDtos = saleNumRank(saleDtos, user);
				RankReportDto saleTop3 = getTopThree(saleDtos);
				signDtos = signNumRank(signDtos,user);
				RankReportDto signTop3 = getTopThree(signDtos);
				willDtos = willNumRank(willDtos, user);
				RankReportDto willTop3 = getTopThree(willDtos);
				callDtos = callTimeRank(callDtos, user);
				RankReportDto callTop3 = getTopThree(callDtos);
				calloutDtos = callOutRank(calloutDtos, user);
				RankReportDto callOutTop3 = getTopThree(calloutDtos);
				//需要给数据添加一部分参数
				saleTop3=retRank(saleTop3);
				signTop3=retRank(signTop3);
				willTop3=retRank(willTop3);
				callTop3=retRank(callTop3);
				callOutTop3=retRank(callOutTop3);
				saleTop3.setCardType("2");
				signTop3.setCardType("2");
				willTop3.setCardType("2");
				callTop3.setCardType("2");
				callOutTop3.setCardType("2");
				List<Object> list2=new ArrayList<Object>();
//				List<RankReportDto> list =new ArrayList<RankReportDto>();
//				list.add(signTop3);
//				list.add(willTop3);
//				list.add(callTop3);
//				list.add(callOutTop3);
				CardQueue carQue_sale=new CardQueue();
				CardQueue carQue_sign=new CardQueue();
				CardQueue carQue_will=new CardQueue();
				CardQueue carQue_callTop=new CardQueue();
				CardQueue carQue_callOut=new CardQueue();
				//签约
				carQue_sale.setCardType("2");
				carQue_sale.setContent(saleTop3.getContent());
				carQue_sale.setImgUrl(saleTop3.getImgUrl());
				//签约
				carQue_sign.setCardType("2");
				carQue_sign.setContent(signTop3.getContent());
				carQue_sign.setImgUrl(signTop3.getImgUrl());
				//意向
				carQue_will.setCardType("2");
				carQue_will.setContent(willTop3.getContent());	
				carQue_will.setImgUrl(willTop3.getImgUrl());
				//通话
				carQue_callTop.setCardType("2");
				carQue_callTop.setContent(callTop3.getContent());	
				carQue_callTop.setImgUrl(callTop3.getImgUrl());
				//呼出
				carQue_callOut.setCardType("2");
				carQue_callOut.setContent(callOutTop3.getContent());	
				carQue_callOut.setImgUrl(callOutTop3.getImgUrl());
				
				List<CardQueue> list_CarQue =new ArrayList<CardQueue>();
				List<CardQueue> list_CarQue_new =new ArrayList<CardQueue>();
				List<CardQueue> list =new ArrayList<CardQueue>();
				
				list_CarQue_new=cachedService.getCardQueue(user.getOrgId());
				if(list_CarQue_new!=null && list_CarQue_new.size()>0){
					for(CardQueue car:list_CarQue_new){
						list_CarQue.add(car);
					}
				}
				list_CarQue.add(carQue_sale);
				list_CarQue.add(carQue_sign);
				list_CarQue.add(carQue_will);
				list_CarQue.add(carQue_callTop);
				list_CarQue.add(carQue_callOut);
				list.add(carQue_sign);
				list.add(carQue_will);
				list.add(carQue_callTop);
				list.add(carQue_callOut);
//				cachedService.removeCardQueue(user.getOrgId());
//				cachedService.setCardQueue(user.getOrgId(), list_CarQue);
				map2.put("status", true);
				map2.put("cardQueue", list);
				map2.put("barrageQueue", list2);
////			map2.put("saleTop", saleTop3);  //计算签约金额排名
//				map2.put("signTop", signTop3);  //签约客户排名
//				map2.put("willTop", willTop3); //意向客户排名
//				map2.put("callTop", callTop3); //呼出时长排名
//				map2.put("callOutTop", callOutTop3);//呼出电话数排名
			} catch (Exception e) {
				logger.error("getbranking:" + e.getMessage(), e);
				map2.put("status", false);
				map2.put("errorMsg", e.getMessage());
				return map2;
			}
	    	return map2;
	    }
	//给用户添加img
	public RankReportDto retRank(RankReportDto dto){
		ShiroUser user = ShiroUtil.getShiroUser();
		User users=userService.getByAccount(dto.getUserAccount());
		if(users !=null && !users.equals("")){
			dto.setImgUrl(users.getImgUrl());
		}

		return dto;
	}
	
	
	@RequestMapping("/branking")
	@ResponseBody
	public Map<String, Object> getRanking(){
		Map<String, Object> map;
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			map = cachedService.getRanking(user.getOrgId(), user.getGroupId());
			if(map == null){
				map = new HashMap<String, Object>();
				Calendar cal = Calendar.getInstance();
				List<RankingReportDto> rankingReportDtos = rankingReportService.getRankingList(user.getOrgId(), Arrays.asList(user.getGroupId()),cal.get(Calendar.YEAR)+"",(cal.get(Calendar.MONTH)+1)+"",null);
				 //排行榜
				List<RankReportDto> signDtos = new ArrayList<RankReportDto>();
				List<RankReportDto> saleDtos = new ArrayList<RankReportDto>();
				List<RankReportDto> willDtos = new ArrayList<RankReportDto>();
				List<RankReportDto> calloutDtos = new ArrayList<RankReportDto>();
				List<RankReportDto> callDtos = new ArrayList<RankReportDto>();
				for(RankingReportDto pub : rankingReportDtos){
					RankReportDto rrd = new RankReportDto();
					rrd.setUserName(pub.getUserName());
					rrd.setSignNum(pub.getSignNums());
					rrd.setUserAccount(pub.getAccount());
					signDtos.add(rrd);
					RankReportDto rrd1 = new RankReportDto();
					rrd1.setUserName(pub.getUserName());
					rrd1.setSaleNum(pub.getSaleAmounts());
					rrd1.setUserAccount(pub.getAccount());
					saleDtos.add(rrd1);
				}
				signDtos = signNumRank(signDtos,user);
				saleDtos = saleNumRank(saleDtos,user);
				for(RankReportDto rrd : saleDtos){
					rrd.setSaleNum(rrd.getSaleNum().divide(BigDecimal.valueOf(10000),2,BigDecimal.ROUND_HALF_UP));
				}
				map.put("signDtos", signDtos);
				map.put("saleDtos", saleDtos);

				cachedService.setRanking(user.getOrgId(), user.getGroupId(), map);
			}
		} catch (Exception e) {
			logger.error("销售首页获取排行榜数据异常",e);
			return null;
		}
		return map;
	}
	
	  public RankReportDto getTopThree(List<RankReportDto> dtos){
	    	Map<String, RankReportDto> map = new HashMap<String, RankReportDto>();
	    	RankReportDto dto=new RankReportDto();
	    	int z = 0;
	    	for(int i=0;i<dtos.size();i++){
	    		if(dtos.get(i).getUserName().equals(AVG_NAME)){
	    			continue;
	    		}
	    		if(z<1){
	    			map.put((z+1)+"", dtos.get(i));
	    			dto=dtos.get(i);
	    			z++;
	    		}
	    	}
	    	return dto;
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
//				if(dto.getUserAccount().equals(user.getAccount())){
//					dto.setUserAccount("curr_user_account");
//				}
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
//				if(dto.getUserAccount().equals(user.getAccount())){
//					dto.setUserAccount("curr_user_account");
//				}
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
//				if(dto.getUserAccount().equals(user.getAccount())){
//					dto.setUserAccount("curr_user_account");
//				}
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
	 *
	 * @return 
	 * @create  2016年12月12日 下午3:18:31 Administrator
	 * @history  
	 */
	@RequestMapping("/planNums")
	@ResponseBody
	public Map<String,Object> getPlanNums(){
		Map<String, Object> map;
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			map = cachedService.getPlanNums(user.getOrgId(), user.getAccount());
			if(map == null){
				map = new HashMap<String, Object>();
				PlanUserDayBean dayPlan = planService.getPlanUserDay(user.getOrgId(), user.getId(),new Date());
				Integer resNum=0;
				if(dayPlan != null){
					dayPlan = planService.queryPlanById(user.getOrgId(), dayPlan.getId());
					DataLableDTO dl =  planUserDayResourceService.queryDataLable(user.getOrgId(), dayPlan.getId());
					int tempPlanCount = 0;
					if(dl != null){
						tempPlanCount = dl.getTempPlanCount();
					}
//					PlanUserDayBean plan = planService.queryPlanById(user.getOrgId(), dayPlan.getId());
					if(!dayPlan.getAuthState().equals("1")){
						ResCustInfoDto resCustInfoDto = new ResCustInfoDto();
						resCustInfoDto.setOrgId(user.getOrgId());
						resCustInfoDto.setOwnerAcc(user.getAccount());
						resCustInfoDto.setStartDate(DateUtil.formatDate(new Date(),DateUtil.DATE_DAY));
						Map<String, Integer> numMap = resCustInfoService.getCustNums(resCustInfoDto);
						resNum = tempPlanCount;
						map.put("intNum", numMap.get("intNum"));
						map.put("signNum", numMap.get("signNum"));
					}else{
						resNum = dayPlan.getResourceCount()+tempPlanCount;
						map.put("planId", dayPlan.getId());
						map.put("intNum", dayPlan.getWillcustCount());
						map.put("signNum", dayPlan.getSigncustCount());
					}
				}else{
					ResCustInfoDto resCustInfoDto = new ResCustInfoDto();
					resCustInfoDto.setOrgId(user.getOrgId());
					resCustInfoDto.setOwnerAcc(user.getAccount());
					resCustInfoDto.setStartDate(DateUtil.formatDate(new Date(),DateUtil.DATE_DAY));
					Map<String, Integer> numMap = resCustInfoService.getCustNums(resCustInfoDto);
					map.put("intNum", numMap.get("intNum"));
					map.put("signNum", numMap.get("signNum"));
				}
				map.put("resNum", resNum);
				cachedService.setPlanNums(user.getOrgId(), user.getAccount(), map);
			}
		} catch (Exception e) {
			logger.error("销售首页获取计划数据异常",e);
			return null;
		}
		return map;
	}
	
	public void setCustomFiled(ShiroUser user,HttpServletRequest request){
		List<CustFieldSet> fieldSets = null;
		if (user.getIsState() == 1) {// 企业资源
			fieldSets = cachedService.getComFiledSet(user.getOrgId());
		}else{
			fieldSets = cachedService.getPersonFiledSet(user.getOrgId());
		}
		Map<String,String> filedMap = new HashMap<String, String>();
		for(CustFieldSet filed : fieldSets){
			filedMap.put(filed.getFieldCode(), filed.getFieldName());
		}
		request.setAttribute("filedMap", filedMap);
	}
	
	/** 
	 * 获取当前登陆人剩余通话时长
	 * @param request
	 * @param user 
	 * @create  2016年1月7日 上午10:32:58 lixing
	 * @history  
	 */
	@ResponseBody
	@RequestMapping("/getTimeLen")
	public Map<String, Object> getUserTimeLen(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Double timeLen = 0d;
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			//获取剩余通话时长
			Map<String, Object> requestMap = HDUtils.splitRequestParamOfUserLens(user.getAccount(),user.getOrgId());
			String modelName = "【用户时长余额请求】";
			String responeStr = HDUtils.sendMsgToHD(requestMap,modelName);
			if(!responeStr.equals("9008") && !responeStr.equals("9999")){
				Map<String, Object> return_map = com.alibaba.fastjson.JSONObject.parseObject(responeStr,Map.class);
				if(return_map.get("code").equals(SUCCESS_CODE)){
					timeLen = new Double(return_map.get("balance").toString());
					if(return_map.containsKey("isCdPkg")){
						map.put("isCdPkg", true);
					}
				}else{
					logger.error("*********"+modelName+"处理失败，返回结果（code:"+return_map.get("code")+"，desc:"+return_map.get("desc")+"）");
				}
			}
			map.put("totalLen", timeLen);
		} catch (Exception e) {
			logger.error("获取剩余时长失败"+e.getMessage());
		}
		return map;
	}
	
	public void getUserLevel(HttpServletRequest request,User user){
		Integer userPoints = user.getPoints() == null ? 0 : user.getPoints();
		Points entity = new Points();
		entity.setOrgId(user.getOrgId());
		List<Points> pointsList = pointsService.getListByCondtion(entity);
		Points curPoints = new Points();
		for(Points pt : pointsList){
			if(pt.getLevel() == 1){
				if(userPoints <= pt.getEndNumber()){
					curPoints = pt;
					break;
				}
			}else if(pt.getLevel() == 2 || pt.getLevel() == 3 || pt.getLevel() == 4){
				if(userPoints >= pt.getStartNumber() && userPoints <= pt.getEndNumber()){
					curPoints = pt;
					break;
				}
			}else{
				if(userPoints >= pt.getStartNumber()){
					curPoints = pt;
					break;
				}
			}
		}
		request.setAttribute("curPoints", curPoints);
		request.setAttribute("userPoints", userPoints);
		
		// 查询积分规则			
		DataDictionaryBean dictionary = new DataDictionaryBean();
		dictionary.setOrgId(user.getOrgId());
		
		List<DataDictionaryBean>dictionaryList = dataDictionaryService.getListByCondtion(dictionary);
		Map<String, DataDictionaryBean> dictionaryMap = new HashMap<String, DataDictionaryBean>();
		for (DataDictionaryBean obj : dictionaryList) {
			if(null != obj.getDictionaryCode()){
				dictionaryMap.put(obj.getDictionaryCode(), obj);
			}
		}
		dictionaryList = new ArrayList<DataDictionaryBean>();
		// 销售每签单xx 元获得1积分奖励
		dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40021));//下标[0]
		// 每月单项任务完成获得 xx 积分奖励
		dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40022));//下标[1]
		 // 完成月度计划，获得xx积分奖励
		dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40023));//下标[2]	
		 // 连续3个月完成月度计划，获得xx积分奖励
		dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40024));//下标[3]			
		request.setAttribute("dictionaryList", dictionaryList);
	}
	
	@RequestMapping("/levelGrowth")
	public String levelGrowth(HttpServletRequest request){
		try {
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			User user = userService.getByAccount(shiroUser.getAccount());
			Integer userPoints = user.getPoints() == null ? 0 : user.getPoints();
			Points entity = new Points();
			entity.setOrgId(user.getOrgId());
			List<Points> pointsList = pointsService.getListByCondtion(entity);
			Points curPoints = new Points();
			Map<Long,Points> map = new HashMap<Long,Points>();
			for(Points pt : pointsList){
				map.put(Long.valueOf(pt.getLevel().toString()), pt);
				if(pt.getLevel() == 1){
					if(userPoints <= pt.getEndNumber()){
						curPoints = pt;
					}
				}else if(pt.getLevel() == 2 || pt.getLevel() == 3 || pt.getLevel() == 4){
					if(userPoints >= pt.getStartNumber() && userPoints <= pt.getEndNumber()){
						curPoints = pt;
					}
				}else{
					if(userPoints >= pt.getStartNumber()){
						curPoints = pt;
					}
				}
			}
			request.setAttribute("curPoints", curPoints);
			request.setAttribute("userPoints", userPoints);
			request.setAttribute("levelMap", map);
			// 查询积分规则			
			DataDictionaryBean dictionary = new DataDictionaryBean();
			dictionary.setOrgId(user.getOrgId());
			
			List<DataDictionaryBean>dictionaryList = dataDictionaryService.getListByCondtion(dictionary);
			Map<String, DataDictionaryBean> dictionaryMap = new HashMap<String, DataDictionaryBean>();
			for (DataDictionaryBean obj : dictionaryList) {
				if(null != obj.getDictionaryCode()){
					dictionaryMap.put(obj.getDictionaryCode(), obj);
				}
			}
			dictionaryList = new ArrayList<DataDictionaryBean>();
			// 销售每签单xx 元获得1积分奖励
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40021));//下标[0]
			// 每月单项任务完成获得 xx 积分奖励
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40022));//下标[1]
			 // 完成月度计划，获得xx积分奖励
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40023));//下标[2]	
			 // 连续3个月完成月度计划，获得xx积分奖励
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40024));//下标[3]			
			request.setAttribute("dictionaryList", dictionaryList);
			request.setAttribute("date", new Date());
			request.setAttribute("user", user);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/home/level_growth";
	}
	
	/** 
	 * 销售者首页 今日关注 资源列表
	 * @param request
	 * @param sudId
	 * @return 
	 * @create  2016年1月6日 下午5:44:36 lixing
	 * @history  
	 */
	@RequestMapping("/resPlanList")
	public String resPlanList(HttpServletRequest request,PlanUserDayResourceBean bean){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			bean.setOrgId(user.getOrgId());
			bean.setContactResult(null);
			bean.setQueryStr(null);
			bean.setUserAcc(user.getAccount());
			if(StringUtils.isNotBlank(bean.getSudId())){
				List<PlanUserDayResourceBean> list = planUserDayResourceService.queryPageBySudId(bean);
				request.setAttribute("list", list);
//				request.setAttribute("pdrBean", bean);
			}else{
				PlanUserDayBean dayPlan = planService.getPlanUserDay(user.getOrgId(), user.getId(),new Date());
				if(dayPlan != null){
					bean.setSudId(dayPlan.getId());
					bean.setState(1);
					List<PlanUserDayResourceBean> list = planUserDayResourceService.queryPageBySudId(bean);
					request.setAttribute("list", list);
//					request.setAttribute("pdrBean", bean);
				}
			}
			request.setAttribute("shiroUser", user);
			setCustomFiled(user, request);
			super.getSignSetting(request);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/home/res_plan";
	}
	
	
	/** 
	 * 销售者首页 今日关注 意向客户列表
	 * @param request
	 * @param sudId
	 * @return 
	 * @create  2016年1月6日 下午5:46:16 lixing
	 * @history  
	 */
	@RequestMapping("/intPlanList")
	public String intPlanList(HttpServletRequest request,String sudId){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			if(StringUtils.isNotBlank(sudId)){
				PlanUserdayWillcustBean bean = new PlanUserdayWillcustBean(); 
				bean.setSudId(sudId);
				bean.setOrgId(user.getOrgId());
				bean.setContactResult(null);
				bean.setQueryStr(null);
				bean.setCustStatusId(null);
				bean.setUserAcc(user.getAccount());
				List<PlanUserdayWillcustBean> list = planUserdayWillcustService.queryPageBySudId(bean);
				request.setAttribute("list", list);
				request.setAttribute("hasPlan", 1);
			}else{
				ResCustInfoDto resCustInfoDto = new ResCustInfoDto();
				resCustInfoDto.setOwnerAcc(user.getAccount());
				resCustInfoDto.setOrgId(user.getOrgId());
				resCustInfoDto.setCustType("2");
				resCustInfoDto.setNextFollowDate(new Date());
				List<ResCustInfoDto> custs = resCustInfoService.getAllTypeCustListPage(resCustInfoDto,null);
				Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
				for(ResCustInfoDto ct : custs){
					if(StringUtils.isNotBlank(ct.getResGroupId())) ct.setGroupName(groupMap.get(ct.getResGroupId()));
				}
				request.setAttribute("list", custs);
				request.setAttribute("hasPlan", 0);
			}
			request.setAttribute("shiroUser", user);
			setCustomFiled(user, request);
			super.getSignSetting(request);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/home/int_plan";
	}
	
	@RequestMapping("/signPlanList")
	public String signPlanList(HttpServletRequest request,String sudId){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			if(StringUtils.isNotBlank(sudId)){
				PlanUserdaySigncustBean bean = new PlanUserdaySigncustBean();
				bean.setSudId(sudId);
				bean.setOrgId(user.getOrgId());
				bean.setContactResult(null);
				bean.setQueryStr(null);
				bean.setUserAcc(user.getAccount());
				List<PlanUserdaySigncustBean> list = planUserdaySigncustService.queryPageBySudId(bean);
				request.setAttribute("list", list);
				request.setAttribute("hasPlan", 1);
			}else {
				ResCustInfoDto resCustInfoDto = new ResCustInfoDto();
				resCustInfoDto.setOwnerAcc(user.getAccount());
				resCustInfoDto.setOrgId(user.getOrgId());
				resCustInfoDto.setCustType("3");
				resCustInfoDto.setNextFollowDate(new Date());
				List<ResCustInfoDto> custs = resCustInfoService.getAllTypeCustListPage(resCustInfoDto,null);
				Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
				for(ResCustInfoDto ct : custs){
					if(StringUtils.isNotBlank(ct.getResGroupId())) ct.setGroupName(groupMap.get(ct.getResGroupId()));
				}
				request.setAttribute("list", custs);
				request.setAttribute("hasPlan", 0);
			}
			request.setAttribute("shiroUser", user);
			setCustomFiled(user, request);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/home/sign_plan";
	}
	
	/** 
	 * 管理者首页-本月计划
	 * @param request 
	 * @create  2015年12月9日 下午8:22:48 lixing
	 * @history  
	 */
	@RequestMapping("/monthTeamPlan")
	@ResponseBody
	public String monthTeamPlan(HttpServletRequest request,HttpServletResponse response){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String groupId = request.getParameter("groupId");
			Calendar cal = Calendar.getInstance();
			Map<String, Object> reMap = new HashMap<String, Object>();
			List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
			List<String> labels = new ArrayList<String>();
			double planTotal = 0,factTotal=0,otherTotal = 0,otherFact = 0;
			if(StringUtils.isBlank(groupId)){//全部
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("orgId", user.getOrgId());
				queryMap.put("planYear", String.valueOf(cal.get(Calendar.YEAR)));
				queryMap.put("planMonth", String.valueOf(cal.get(Calendar.MONTH)+1));
//				queryMap.put("shareAcc", user.getAccount());
				List<TeamGroupBean> teamGroups = tsmTeamGroupService.queryManageTeamList(user.getOrgId(), user.getAccount());
				List<String> groupIds = new ArrayList<String>();
				for(TeamGroupBean tgb : teamGroups){
					groupIds.add(tgb.getGroupId());
				}
				queryMap.put("groupIds", groupIds);
				List<PlanGroupmonthReportDto> beans = mainService.queryShareTeamMonthPlan(queryMap);
				//组装
				Collections.sort(beans, Collections.reverseOrder(new Comparator<PlanGroupmonthReportDto>(){
					public int compare(PlanGroupmonthReportDto o1,
							PlanGroupmonthReportDto o2) {
						return Double.compare(o1.getFactMoney(), o2.getFactMoney());
					}
				}));
				for(int i = 0 ; i < beans.size() ; i++){
					PlanGroupmonthReportDto dto = beans.get(i);
					planTotal+=dto.getPlanMoney();
					factTotal+=dto.getFactMoney();
					if(dto.getFactMoney() <= 0){
						beans.remove(i);
						i--;
					}
				}
				for(int i = 0 ; i < beans.size() ; i++){
					PlanGroupmonthReportDto dto = beans.get(i);
					if((i+1) > 4 && beans.size() > 5){
						otherFact+=dto.getFactMoney();
						otherTotal+=dto.getPlanMoney();
					}else{
						Map<String, Object>  map = new HashMap<String, Object>();
						map.put("value", dto.getFactMoney());
						map.put("name", dto.getGroupName());
						map.put("plan", dto.getPlanMoney());
						maps.add(map);
						labels.add(dto.getGroupName());
					}
				}
				if(beans.size() > 5){
					Map<String, Object>  map = new HashMap<String, Object>();
					map.put("value", otherFact);
					map.put("name", "其他部门");
					map.put("plan", otherTotal);
					maps.add(map);
					labels.add("其他部门");
				}
			}else{//分组
				List<String> gids = tsmTeamGroupService.queryDeepGroupData(user.getOrgId(), groupId);
				if(gids.size() == 1 && gids.get(0).equals(groupId)){
					//组装
					List<PlanUsermonthBean> usermonthBeans = planUserMonthService.findByGroupId2(user.getOrgId(), groupId, String.valueOf(cal.get(Calendar.YEAR)), String.valueOf(cal.get(Calendar.MONTH)+1));
					Collections.sort(usermonthBeans, new Comparator<PlanUsermonthBean>(){
						public int compare(PlanUsermonthBean o1,
								PlanUsermonthBean o2) {
							return Double.compare(o1.getFactMoney(), o2.getFactMoney());
						}
					});
					
					for (int i = 0; i < usermonthBeans.size(); i++) {
						PlanUsermonthBean pub = usermonthBeans.get(i);
						planTotal+=pub.getPlanMoney();
						factTotal+=pub.getFactMoney();
						if(pub.getFactMoney() <= 0){
							usermonthBeans.remove(i);
							i--;
						}
					}
					
					for (int i = 0; i < usermonthBeans.size(); i++) {
						PlanUsermonthBean pub = usermonthBeans.get(i);
						if((i+1) > 4 && usermonthBeans.size() > 5){
							otherTotal+=pub.getPlanMoney();
							otherFact+=pub.getFactMoney();
						}else{
							Map<String, Object>  map = new HashMap<String, Object>();
							map.put("value", pub.getFactMoney());
							map.put("name", pub.getUserName());
							map.put("plan", pub.getPlanMoney());
							maps.add(map);
							labels.add(pub.getUserName());
						}
					}
					if(usermonthBeans.size() > 5){
						Map<String, Object>  map = new HashMap<String, Object>();
						map.put("value", otherFact);
						map.put("name", "其他成员");
						map.put("plan", otherTotal);
						maps.add(map);
						labels.add("其他成员");
					}
				}else{
					List<TeamGroupBean> teamGroups = mainService.queryManageGroupList(user.getAccount(),user.getOrgId());
					Map<String,String> idMap = new HashMap<String, String>();
					List<String> groupIds = new ArrayList<String>();
					for(String gid : gids){
						idMap.put(gid, gid);
					}
					for(TeamGroupBean g : teamGroups){
						if(idMap.get(g.getGroupId())!= null){
							groupIds.add(g.getGroupId());
						}
					}
					
					Map<String, Object> queryMap = new HashMap<String, Object>();
					queryMap.put("orgId", user.getOrgId());
					queryMap.put("planYear", String.valueOf(cal.get(Calendar.YEAR)));
					queryMap.put("planMonth", String.valueOf(cal.get(Calendar.MONTH)+1));
					queryMap.put("groupIds", groupIds);
					List<PlanGroupmonthReportDto> beans = mainService.queryShareTeamMonthPlan(queryMap);
					//组装
					Collections.sort(beans, Collections.reverseOrder(new Comparator<PlanGroupmonthReportDto>(){
						public int compare(PlanGroupmonthReportDto o1,
								PlanGroupmonthReportDto o2) {
							return Double.compare(o1.getFactMoney(), o2.getFactMoney());
						}
					}));
					for(int i = 0 ; i < beans.size() ; i++){
						PlanGroupmonthReportDto dto = beans.get(i);
						planTotal+=dto.getPlanMoney();
						factTotal+=dto.getFactMoney();
						if(dto.getFactMoney() <= 0){
							beans.remove(i);
							i--;
						}
					}
					for(int i = 0 ; i < beans.size() ; i++){
						PlanGroupmonthReportDto dto = beans.get(i);
						if((i+1) > 4 && beans.size() > 5){
							otherFact+=dto.getFactMoney();
							otherTotal+=dto.getPlanMoney();
						}else{
							Map<String, Object>  map = new HashMap<String, Object>();
							map.put("value", dto.getFactMoney());
							map.put("name", dto.getGroupName());
							map.put("plan", dto.getPlanMoney());
							maps.add(map);
							labels.add(dto.getGroupName());
						}
					}
					if(beans.size() > 5){
						Map<String, Object>  map = new HashMap<String, Object>();
						map.put("value", otherFact);
						map.put("name", "其他部门");
						map.put("plan", otherTotal);
						maps.add(map);
						labels.add("其他部门");
					}
				}
			}
			reMap.put("data", maps);
			reMap.put("legendData", labels);
			reMap.put("planTotal", planTotal);
			reMap.put("factTotal", factTotal);
			return JSONObject.fromObject(reMap).toString();
		} catch (Exception e) {
			logger.error("获取数据失败");
			return "";
		}
	}
	
	
	/** 
	 * 管理者首页-月度计划、新增意向
	 * @param request
	 * @return 
	 * @create  2015年12月9日 下午8:21:51 lixing
	 * @history  
	 */
	@RequestMapping("/monthReport")
	@ResponseBody
	public String monthReport(HttpServletRequest request,HttpServletResponse response){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String reportType = request.getParameter("reportType");
			String groupId = request.getParameter("groupId");
			Calendar cal = Calendar.getInstance();
			Date toDate = cal.getTime();
			cal.add(Calendar.MONTH, -5);
			Date fromDate = cal.getTime();
			List<PlanGroupmonthBean> pgbs = new ArrayList<PlanGroupmonthBean>();
			Map<String,Object> reMap = new HashMap<String, Object>();
			List<String> labels = new ArrayList<String>();
			List<Map<String, Object>> planMaps = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> factMaps = new ArrayList<Map<String,Object>>();
			if(StringUtils.isNotBlank(groupId)){
				pgbs = planGroupmonthService.queryHistory(user.getOrgId(), groupId, fromDate, toDate);
			}else{
				List<TeamGroupBean> teamGroups = tsmTeamGroupService.queryManageTeamList(user.getOrgId(), user.getAccount());
				String[] groupIds = new String[teamGroups.size()];
				for (int i = 0; i < teamGroups.size(); i++) {
					groupIds[i]=teamGroups.get(i).getGroupId();
				}
				pgbs = planGroupmonthService.queryHistory(user.getOrgId(),groupIds, fromDate, toDate);
			}
			//处理数据加补全
			Map<String, PlanGroupmonthBean> dm = new HashMap<String, PlanGroupmonthBean>();
			for (PlanGroupmonthBean pgb : pgbs) {
				dm.put(pgb.getPlanYear()+(Integer.parseInt(pgb.getPlanMonth()) < 10 ? "0" : "")+pgb.getPlanMonth(), pgb);
			}
			
			cal.setTime(toDate);
			for(int i=0;i<6;i++){
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH)+1;
				String month_str = month < 10 ? "0"+month : month+"";
				if(dm.get(year+month_str) == null){
					PlanGroupmonthBean pgb = new PlanGroupmonthBean();
					pgb.setPlanYear(year+"");
					pgb.setPlanMonth(month+"");
					pgb.setPlanMoney(Double.valueOf(0));
					pgb.setFactMoney(Double.valueOf(0));
					pgb.setPlanSigncustnum(0);
					pgb.setFactSigncustnum(0);
					pgb.setPlanWillcustnum(0);
					pgb.setFactWillcustnum(0);
					pgbs.add(pgb);
				}
				cal.add(Calendar.MONTH, -1);
			}
			
			Collections.sort(pgbs,new Comparator<PlanGroupmonthBean>(){
				public int compare(PlanGroupmonthBean o1, PlanGroupmonthBean o2) {
					String year_str1 = o1.getPlanYear();
					String year_str2 = o2.getPlanYear();
					if(!year_str1.equals(year_str2)){
						return Integer.parseInt(year_str1) - Integer.parseInt(year_str2);
					}else{
						String month_str1 = o1.getPlanMonth();
						String month_str2 = o2.getPlanMonth();
						return Integer.parseInt(month_str1) - Integer.parseInt(month_str2);
					}
				}
			});
			
			if(reportType.equals("1")){//签约金额
				for(PlanGroupmonthBean pgb : pgbs){
					labels.add(pgb.getPlanYear()+(Integer.parseInt(pgb.getPlanMonth()) < 10 ? "0" : "")+pgb.getPlanMonth());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("value", pgb.getPlanMoney());
					map.put("count", pgb.getPlanMoney()+"万");
					planMaps.add(map);
					Map<String, Object> fact = new HashMap<String, Object>();
					fact.put("value", pgb.getFactMoney());
					fact.put("count", pgb.getFactMoney()+"万");
					factMaps.add(fact);
				}
			}else if(reportType.equals("2")){
				for(PlanGroupmonthBean pgb : pgbs){
					labels.add(pgb.getPlanYear()+(Integer.parseInt(pgb.getPlanMonth()) < 10 ? "0" : "")+pgb.getPlanMonth());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("value", pgb.getPlanSigncustnum());
					map.put("count", pgb.getPlanSigncustnum()+"人");
					planMaps.add(map);
					Map<String, Object> fact = new HashMap<String, Object>();
					fact.put("value", pgb.getFactSigncustnum());
					fact.put("count", pgb.getFactSigncustnum()+"人");
					factMaps.add(fact);
				}
			}else{//意向新增
				for(PlanGroupmonthBean pgb : pgbs){
					labels.add(pgb.getPlanYear()+(Integer.parseInt(pgb.getPlanMonth()) < 10 ? "0" : "")+pgb.getPlanMonth());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("value", pgb.getPlanWillcustnum());
					map.put("count", pgb.getPlanWillcustnum()+"人");
					planMaps.add(map);
					Map<String, Object> fact = new HashMap<String, Object>();
					fact.put("value", pgb.getFactWillcustnum());
					fact.put("count", pgb.getFactWillcustnum()+"人");
					factMaps.add(fact);
				}
			}
			reMap.put("labels", labels);
			reMap.put("planData", planMaps);
			reMap.put("factData", factMaps);
			return JSONObject.fromObject(reMap).toString();
		} catch (Exception e) {
			logger.error("获取数据失败",e);
			return "";
		}
	}
	
	
	/** 
	 * 管理者首页-明日计划
	 * @param request
	 * @return 
	 * @create  2015年12月9日 下午8:21:13 lixing
	 * @history  
	 */
	@RequestMapping("/tomorrowPlan")
	@ResponseBody
	public String tomorrowPlan(HttpServletRequest request,HttpServletResponse response){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			Map<String, Object> reMap = cachedService.getTomorrowPlan(user.getOrgId(), user.getAccount());
			if(reMap == null){
				reMap = new HashMap<String, Object>();
				List<TeamDayPlanReportDto> dtos = mainService.queryTeamTomorrowPlanReportDtos(user.getOrgId(), user.getAccount(), DateUtil.formatDate(DateUtil.getAddDate(new Date(), 1), DateUtil.DATE_DAY));
				List<String> labels = new ArrayList<String>();
				List<Integer> nrData = new ArrayList<Integer>();
				List<Integer> cpData = new ArrayList<Integer>();
				List<Integer> uncpData = new ArrayList<Integer>();
				for(TeamDayPlanReportDto dto : dtos){
					labels.add(dto.getGroupName());
					nrData.add(dto.getNotReport());
					cpData.add(dto.getCheckedPlan());
					uncpData.add(dto.getUncheckedPlan());
				}
				reMap.put("labels", labels);
				reMap.put("nrData", nrData);
				reMap.put("cpData", cpData);
				reMap.put("uncpData", uncpData);
				cachedService.setTomorrowPlan(user.getOrgId(), user.getAccount(), reMap);
			}
			return JSONObject.fromObject(reMap).toString();
		} catch (Exception e) {
			logger.error("获取数据失败");
			return "";
		}
	}
	
	
	
	/** 
	 * 管理者首页 今日关注
	 * @param request
	 * @param response 
	 * @create  2016年1月4日 下午5:31:34 lixing
	 * @history  
	 */
	@RequestMapping("/todayView")
	@ResponseBody
	public String todayView(HttpServletRequest request,HttpServletResponse response){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String groupId = request.getParameter("groupId");
			Map<String,Object> reMap = cachedService.getTodayView(user.getOrgId(), user.getAccount(),groupId);
			if(reMap==null){
				reMap = new HashMap<String, Object>();
				List<String> groupIds = new ArrayList<String>();
				if(StringUtils.isNotBlank(groupId)){
					List<String> gids = tsmTeamGroupService.queryDeepGroupData(user.getOrgId(), groupId);
					if(gids.size() == 1 && gids.get(0).equals(groupId)){
						groupIds = gids;
					}else{
						List<TeamGroupBean> teamGroups = mainService.queryManageGroupList(user.getAccount(),user.getOrgId());
						Map<String,String> idMap = new HashMap<String, String>();
						for(String gid : gids){
							idMap.put(gid, gid);
						}
						for(TeamGroupBean g : teamGroups){
							if(idMap.get(g.getGroupId())!= null){
								groupIds.add(g.getGroupId());
							}
						}
					}
				}else{
					List<TeamGroupBean> teamGroups = tsmTeamGroupService.queryManageTeamList(user.getOrgId(), user.getAccount());
					for(TeamGroupBean tgb : teamGroups){
						groupIds.add(tgb.getGroupId());
					}
				}
				
				List<String> accs = new ArrayList<String>();
				String accStr = "";
				String userIds = "";
				List<TsmTeamGroupMemberBean> tgmBeans = mainInfoService.getUserManage(user.getOrgId(),user.getAccount(), null,groupIds);
				for(TsmTeamGroupMemberBean tgmb : tgmBeans){
					accs.add(tgmb.getMemberAcc());
					accStr+=tgmb.getMemberAcc()+",";
					userIds+=tgmb.getUserId()+",";
				}
				if(StringUtils.isNotBlank(accStr)){
					accStr=accStr.substring(0,accStr.length()-1);
					userIds = userIds.substring(0,userIds.length()-1);
				}
				ContactResult cr = planUserDayResourceService.queryContactResult(user.getOrgId(),userIds.split(","),new Date(),"1");
				ContactResult cr1 = planUserdayWillcustService.queryContactResult(user.getOrgId(), userIds.split(","),new Date(),"1");
				List<Map<String, Integer>> miBeans = new ArrayList<Map<String,Integer>>();
				if(accs.size() > 0){
					Map<String, Integer> resMap = contactDayDataService.getContactReport(user.getOrgId(), accs, DateUtil.formatDate(new Date(),DateUtil.DATE_DAY), 1);
					resMap.put("type", 1);
					miBeans.add(resMap);
					Map<String, Integer> custMap = contactDayDataService.getContactReport(user.getOrgId(), accs, DateUtil.formatDate(new Date(),DateUtil.DATE_DAY), 2);
					custMap.put("type", 2);
					miBeans.add(custMap);
				}
				//获取通话数据
				String startDate = DateUtil.formatDate(new Date(),DateUtil.DATE_DAY)+" 00:00:00";
				String endDate = DateUtil.formatDate(new Date());
				int resTimeLength = 0;
				int intTimeLength = 0;
				List<CallNalysisDto> callDtos = getCallTime(user.getOrgId(), accStr, startDate, endDate);
				if(callDtos != null){
					for(CallNalysisDto callDto : callDtos){
						BigDecimal timeLength = BigDecimal.valueOf(callDto.getTimeLength()!=null ? callDto.getTimeLength() : 0);
						if(callDto.getCustType().equals("1")) resTimeLength = timeLength.divide(BigDecimal.valueOf(60),0,BigDecimal.ROUND_UP).intValue();
						else if(callDto.getCustType().equals("2")) intTimeLength = timeLength.divide(BigDecimal.valueOf(60),0,BigDecimal.ROUND_UP).intValue();
					}
				}
				reMap = new HashMap<String, Object>();
				reMap.put("resPlan", cr.getTotalCount());
	//			reMap.put("resFact", cr.getContactCount());
				reMap.put("intPlan", cr1.getTotalCount());
	//			reMap.put("intFact", cr1.getContactCount());
				reMap.put("datas", miBeans);
				reMap.put("resTimeLength", resTimeLength);
				reMap.put("intTimeLength", intTimeLength);
				
				cachedService.setTodayView(user.getOrgId(), user.getAccount(), groupId, reMap);
			}
			return JSONObject.fromObject(reMap).toString();
		} catch (Exception e) {
			logger.error("获取数据失败",e);
			return "";
		}
	}
	
	public List<CallNalysisDto> getCallTime(String orgId,String accStr,String startDate,String endDate){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("startTimeBegin", startDate);
		map.put("startTimeEnd", endDate);
		map.put("inputAccs",Arrays.asList(accStr.split(",")));
		map.put("timeLengthBegin","1");
		List<CallNalysisDto> list = CallRecordGetUtil.getRecordeCallNalysisMain(map);
		return list;
	}
	
	/** 
	 * 销售者首页 今日关注
	 * @param request
	 * @param response 
	 * @create  2016年1月6日 下午3:01:07 lixing
	 * @history  
	 */
	@RequestMapping("/todayViewSale")
	@ResponseBody
	public String todayViewSale(HttpServletRequest request,HttpServletResponse response){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();

			Map<String,Object> reMap = cachedService.getTodayView(user.getOrgId(), user.getAccount(),null);
			if(reMap==null){
				reMap = new HashMap<String, Object>();
				List<Map<String, Integer>> miBeans = new ArrayList<Map<String,Integer>>();
				Map<String, Integer> resMap = contactDayDataService.getContactReport(user.getOrgId(), Arrays.asList(user.getAccount()), DateUtil.formatDate(new Date(),DateUtil.DATE_DAY), 1);
				resMap.put("type", 1);
				miBeans.add(resMap);
				Map<String, Integer> custMap = contactDayDataService.getContactReport(user.getOrgId(), Arrays.asList(user.getAccount()), DateUtil.formatDate(new Date(),DateUtil.DATE_DAY), 2);
				custMap.put("type", 2);
				miBeans.add(custMap);
				ContactResult cr = planUserDayResourceService.queryContactResult(user.getOrgId(),user.getId().split(","),new Date(),"1");
				ContactResult cr1 = planUserdayWillcustService.queryContactResult(user.getOrgId(), user.getId().split(","),new Date(),"1");
				//获取通话数据
				String startDate = DateUtil.formatDate(new Date(),DateUtil.DATE_DAY)+" 00:00:00";
				String endDate = DateUtil.formatDate(new Date());
				int resTimeLength = 0;
				int intTimeLength = 0;
				List<CallNalysisDto> callDtos = getCallTime(user.getOrgId(), user.getAccount(), startDate, endDate);
				if(callDtos != null){
					for(CallNalysisDto callDto : callDtos){
						BigDecimal timeLength = BigDecimal.valueOf(callDto.getTimeLength() !=null ? callDto.getTimeLength() : 0);
						if(callDto.getCustType().equals("1")) resTimeLength = timeLength.divide(BigDecimal.valueOf(60),0,BigDecimal.ROUND_UP).intValue();
						else if(callDto.getCustType().equals("2")) intTimeLength = timeLength.divide(BigDecimal.valueOf(60),0,BigDecimal.ROUND_UP).intValue();
					}
				}
				reMap = new HashMap<String, Object>();
				reMap.put("resPlan", cr.getTotalCount());
//				reMap.put("resFact", cr.getContactCount());
				reMap.put("intPlan", cr1.getTotalCount());
//				reMap.put("intFact", cr1.getContactCount());
				reMap.put("datas", miBeans);
				reMap.put("resTimeLength", resTimeLength);
				reMap.put("intTimeLength", intTimeLength);
				cachedService.setTodayView(user.getOrgId(), user.getAccount(), null, reMap);
			}
			return JSONObject.fromObject(reMap).toString();
		} catch (Exception e) {
			logger.error("获取数据失败");
			return "";
		}
	}
	
	/** 
	 * 读取首页快捷菜单
	 * @param request
	 * @param shiroUser 
	 * @create  2015年12月9日 下午5:24:16 lixing
	 * @history  
	 */
	public void getQuickMenu(HttpServletRequest request,ShiroUser shiroUser){
		if(request.getSession().getAttribute("quickMenu") == null){
			AuthUserResourceQuickBean arqBean = new AuthUserResourceQuickBean();
			arqBean.setUserId(shiroUser.getId());
			arqBean.setOrgId(shiroUser.getOrgId());
			List<AuthUserResourceQuickBean> aurqList = authUserResourceQuickService.getByCondition(arqBean);
			Map<String, AuthUserResourceQuickBean> map = new HashMap<String, AuthUserResourceQuickBean>();
			List<Menu> list = menuService.getMenusListByUserId(shiroUser);
			List<Menu> list2 = new ArrayList<Menu>();
			List<Menu> deList = new ArrayList<Menu>();
			for(AuthUserResourceQuickBean aur : aurqList){
				map.put(aur.getResourceId(), aur);
			}
			for(Menu m : list) {
				if(map.containsKey(m.getResourceId())){
					list2.add(m);
				}
			}
			list2.addAll(0, deList);
			request.getSession().setAttribute("quickMenu", list2);
		}
	}
	
	
	
	/** 
	 * 弹窗  跳转修改快捷菜单页面
	 * @param request
	 * @return 
	 * @create  2015年12月10日 下午7:44:48 lixing
	 * @history  
	 */
	@RequestMapping("/quickMenu")
	public String quickMenu(HttpServletRequest request){
		try {
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			AuthUserResourceQuickBean arqBean = new AuthUserResourceQuickBean();
			arqBean.setUserId(shiroUser.getId());
			arqBean.setOrgId(shiroUser.getOrgId());
			List<AuthUserResourceQuickBean> aurqList = authUserResourceQuickService.getByCondition(arqBean);
			Map<String, AuthUserResourceQuickBean> map = new HashMap<String, AuthUserResourceQuickBean>();
			for(AuthUserResourceQuickBean aur : aurqList){
				map.put(aur.getResourceId(), aur);
			}
	        List<Menu> deListTree = new ArrayList<Menu>();
	        List<Menu> quickList = new ArrayList<Menu>();
			List<Menu> listtree = (List<Menu>)ShiroUtil.getSession(SysConstant.USER_MENU_TREE);
			if(listtree == null){
	            logger.debug("shiroUser=" + JsonUtil.getJsonString(shiroUser));
	            LinkedHashMap<String, Menu> menu1Map = menuService.getMenuTree(shiroUser, 0);
	            listtree = new ArrayList<Menu>();
	            for (String s : menu1Map.keySet()) {
	            	listtree.add(menu1Map.get(s));
	            }
	            Collections.sort(listtree, new MenuUtil());
	            logger.debug("[首页快捷菜单设置] DB data>>>" + JSON.toJSONString(listtree));
	            ShiroUtil.setSession(SysConstant.USER_MENU_TREE, listtree);
			}else{
				logger.debug("[首页快捷菜单设置] session data >> "+JsonUtil.getJsonString(listtree));
			}
	        if(listtree != null){
			for(Menu mt : listtree){
	        	if(mt.getResourceName().equals("淘客户") || mt.getResourceName().equals("审核中心")){
	        		if(map.containsKey(mt.getResourceId())){
	        			quickList.add(mt);
	        			mt.setIsUserQuick(1);
	        		}else{
	        			mt.setIsUserQuick(0);
	        		}
	        	}
	        	if(mt.getChildList().size()>0){
	        		for(Menu mt1 : mt.getChildList()){
//	        			if(mt1.getQuickDefault() != null && mt1.getQuickDefault() == 1){
//	        				deListTree.add(mt1);
//	        				continue;
//	        			}
	        			if(map.containsKey(mt1.getResourceId())){
	        				quickList.add(mt1);
	        				mt1.setIsUserQuick(1);
	        			}else{
	        				mt1.setIsUserQuick(0);
	        			}
	        		}
	        	}
	        }
	        }
	        quickList.addAll(0,deListTree);
	        request.setAttribute("quickList", quickList);
	        request.setAttribute("listtree", listtree);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/home/quick_menu";
	}
	
	
	/** 
	 * 保存设置快捷菜单
	 * @param request
	 * @param response 
	 * @create  2015年12月10日 下午7:42:19 lixing
	 * @history  
	 */
	@RequestMapping("/saveQuickMenu")
	public void saveQuickMenu(HttpServletRequest request,HttpServletResponse response){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			authUserResourceQuickService.setUserQuickMenu(ids, user);
			request.getSession().removeAttribute("quickMenu");
			BASEAjaxExcute(response,"1");
		} catch (Exception e) {
			BASEAjaxExcute(response,"-1");
			throw new SysRunException(e);
		}
	}
	
	public void BASEAjaxExcute(HttpServletResponse response,String jsonStr) {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter wr = null;
		try {
			wr = response.getWriter();
			wr.println(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			wr.flush();
			wr.close();
		}
	}
	
	/** 生日员工信息,供前端调用测试 */
	@RequestMapping("/birthdayUsers")
	@ResponseBody
	public Map<String,Object> birthdayUsers(HttpServletRequest request){
		List<TsmMessageSend> list =new ArrayList();
		List<TsmMessageSend> list2 =new ArrayList();
		List<String> list3 =new ArrayList();
		List<CardQueue> list_Car=new ArrayList<CardQueue>();
		List<CardQueue> list_Car_new =new ArrayList<CardQueue>();

		Map<String, Object> map = new HashMap<String, Object>();
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			list=mainService.findAllUserByOrgId(shiroUser.getOrgId());	
			
			List<CardQueue> list_CarQue_new =new ArrayList<CardQueue>();
			list_CarQue_new=cachedService.getCardQueue(shiroUser.getOrgId());
			if(list_CarQue_new!=null && list_CarQue_new.size()>0){
				for(CardQueue car:list_CarQue_new){
					list_Car.add(car);
				}
			}

			if(list != null && list.size()>0){
				for(TsmMessageSend send:list){
					CardQueue carque=new CardQueue();
//					send.setCardType("1");
//					list2.add(send);
					carque.setCardType("1");
					carque.setContent(send.getContent());
					carque.setImgUrl(send.getImgUrl());
					list_Car.add(carque);
					list_Car_new.add(carque);
				}
			}
//			cachedService.removeCardQueue(shiroUser.getOrgId());
//			cachedService.setCardQueue(shiroUser.getOrgId(), list_Car);
			map.put("cardQueue", list_Car_new);
//			map.put("cardQueue", list_Car);
			map.put("status", true);
			map.put("barrageQueue", list3);
			map.put("msgType", "4");
			map.put("msg", "生日数据");
		}catch(Exception e){
			logger.error("birthdayUsers:" + e.getMessage(), e);
			map.put("status", false);
			map.put("errorMsg", e.getMessage());
			return map;
		}
		return map;
	}
	
	
	/** 发送生日弹幕消息 */
	@RequestMapping("/birthdaybarrage")
	public void birthdaybarrage(HttpServletRequest request){
		List<TsmMessageSend> list =new ArrayList<TsmMessageSend>();
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String content = request.getParameter("content");
//			String content ="大家好！我是新来的";
			StringBuffer stb = new StringBuffer();
			stb.append(content);
			stb.append("-");
			stb.append(StringUtils.isBlank(user.getName())?user.getAccount():user.getName());
		    list=tsmMessageService.sendBarrage(stb.toString(),user.getOrgId(),user.getAccount(),user.getName());
		    request.setAttribute("list", list);
		}catch(Exception e){
			logger.error("发送弹幕消息异常",e);
		}
		
	}
	
	/** 发送弹幕消息 */
	@RequestMapping("/barrage")
	@ResponseBody
	public Map<String,Object> sendBarrage(HttpServletRequest request){
		    Map<String,Object> map=new HashMap<String,Object>();
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String content = request.getParameter("content");
			String barrType=request.getParameter("barrType");
            mainService.sendBarrage(content,user.getOrgId(),user.getAccount(),user.getName());
			map.put("status", true);
		}catch(Exception e){
			logger.error("barrage:" + e.getMessage(), e);
			map.put("status", false);
			map.put("errorMsg", e.getMessage());
			return map;
		}
		return map;
	}
	
	/** 发送弹幕红包消息 */
	@RequestMapping("/Hbbarrage")
	@ResponseBody
	public Map<String,Object> Hbbarrage(HttpServletRequest request){
		    Map<String,Object> map=new HashMap<String,Object>();
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String content = request.getParameter("content");
            mainService.sendHbBarrage(content,user.getOrgId(),user.getAccount(),user.getName());
			map.put("status", true);
		}catch(Exception e){
			logger.error("barrage:" + e.getMessage(), e);
			map.put("status", false);
			map.put("errorMsg", e.getMessage());
			return map;
		}
		return map;
	}
	
	/** 发送弹幕系统消息(礼物) */
	@RequestMapping("/gift")
	@ResponseBody
	public Map<String,Object> sendgift(HttpServletRequest request){
		    Map<String,Object> map=new HashMap<String,Object>();
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String content = request.getParameter("gift");
			if(content!=null&&!"".endsWith(content)){
			content=user.getName()+"送出"+"【"+content+"】";
            mainService.sendSysBarrage(content,user.getOrgId(),user.getAccount(),user.getName());
			}
			map.put("status", true);
		}catch(Exception e){
			logger.error("barrage:" + e.getMessage(), e);
			map.put("status", false);
			map.put("errorMsg", e.getMessage());
			return map;
		}
		return map;
	}
	
//	/** 发送弹幕消息，供前端调试 */
//	@RequestMapping("/barrage")
//	@ResponseBody
//	public Map<String,Object> sendBarrage(HttpServletRequest request){
//		    Map<String,Object> map=new HashMap<String,Object>();
//		try{
//			ShiroUser user = ShiroUtil.getShiroUser();
//			String content = request.getParameter("content");
//			String barrType=request.getParameter("barrType");
//			List<BarrageQueue> list_new =new ArrayList<BarrageQueue>();
//			List<BarrageQueue> list =new ArrayList<BarrageQueue>();
//			List<Object> list2 =new ArrayList<Object>();
//			BarrageQueue barQue=new BarrageQueue();
//			if(barrType == "" ||barrType ==null){
//				barQue.setBarrType("1");
//			}
//			if(content == null){
//				content="";
//			}else{
//				content=content+"--"+user.getName();
//			}
//			barQue.setContent(content);
//			List<BarrageQueue> list_Barr =new ArrayList<BarrageQueue>();
//			list_Barr=cachedService.getBarrageQueue(user.getOrgId());
//			if(list_Barr !=null && list_Barr.size()>0){
//				for(BarrageQueue bq:list_Barr){
//					list_new.add(bq);
//				}
//			}
//			list_new.add(barQue);
//			list.add(barQue);
//			cachedService.removeBarrageQueue(user.getOrgId());
//			cachedService.setBarrageQueue(user.getOrgId(), list_new);
//			map.put("status", true);
//			map.put("barrageQueue", list);
//			map.put("cardQueue", list2);
//			map.put("msgType", "2");
//			map.put("msg", "消息数据");
//		}catch(Exception e){
//			logger.error("barrage:" + e.getMessage(), e);
//			map.put("status", false);
//			map.put("errorMsg", e.getMessage());
//			return map;
//		}
//		return map;
//	}
	
	/** 签约弹幕，供前端 */
	@RequestMapping("/signBarrage")
	@ResponseBody
	public Map<String,Object> signBarrage(HttpServletRequest request){
		    Map<String,Object> map=new HashMap<String,Object>();
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String content = request.getParameter("content");
			String cardType="3";
			List<CardQueue> list_new =new ArrayList<CardQueue>();
			List<CardQueue> list =new ArrayList<CardQueue>();
			List<Object> list2 =new ArrayList<Object>();
			CardQueue carQue=new CardQueue();
			carQue.setCardType(cardType);
			if(content == null){
				content="";
			}
			carQue.setContent(content);
			carQue.setImgUrl("http://m1.biz.itc.cn/pic/new/n/32/15/Img8231532_n.jpg");
			List<CardQueue> list_CarQue_new =new ArrayList<CardQueue>();
			list_CarQue_new=cachedService.getBarrageSign(user.getOrgId());
			if(list_CarQue_new!=null && list_CarQue_new.size()>0){
				for(CardQueue car:list_CarQue_new){
					list_new.add(car);
				}
			}
			list_new.add(carQue);
			list.add(carQue);
			cachedService.removeBarrageSign(user.getOrgId());
			cachedService.setBarrageSign(user.getOrgId(), list_new);
			map.put("status", true);
			map.put("barrageQueue", list2);
			map.put("cardQueue", list);
			map.put("msgType", "3");
			map.put("msg", "签约数据");
		}catch(Exception e){
			logger.error("barrage:" + e.getMessage(), e);
			map.put("status", false);
			map.put("errorMsg", e.getMessage());
			return map;
		}
		return map;
	}
	
	/** 打赏 */
	@RequestMapping("/payBarrage")
	@ResponseBody
	public Map<String,Object> payBarrage(HttpServletRequest request){
	    Map<String,Object> map=new HashMap<String,Object>();
			try{
				ShiroUser user = ShiroUtil.getShiroUser();
				String content = request.getParameter("content");
				String barrType=request.getParameter("barrType");
				List<BarrageQueue> list_new =new ArrayList<BarrageQueue>();
				List<BarrageQueue> list =new ArrayList<BarrageQueue>();
				List<Object> list2 =new ArrayList<Object>();
				BarrageQueue barQue=new BarrageQueue();
				if(barrType == "" ||barrType ==null){
					barQue.setBarrType("5");

				}
				if(content == null){
					content="";
				}
				barQue.setContent(content);
				List<BarrageQueue> list_Barr =new ArrayList<BarrageQueue>();
				list_Barr=cachedService.getBarrageQueue(user.getOrgId());
				if(list_Barr !=null && list_Barr.size()>0){
					for(BarrageQueue bq:list_Barr){
						list_new.add(bq);
					}
				}
				list_new.add(barQue);
				list.add(barQue);
				cachedService.removePayBarrage(user.getOrgId());
				cachedService.setPayBarrage(user.getOrgId(), list_new);
				map.put("status", true);
				map.put("barrageQueue", list);
				map.put("cardQueue", list2);
				map.put("msgType", "5");
				map.put("msg", "打赏数据");
			}catch(Exception e){ 
				logger.error("barrage:" + e.getMessage(), e);
				map.put("status", false);
				map.put("errorMsg", e.getMessage());
				return map;
			}
			return map;
		}
	
//	/** 发送弹幕消息 */
//	@RequestMapping("/barrage")
//	@ResponseBody
//	public String sendBarrage(HttpServletRequest request){
//		try{
//			ShiroUser user = ShiroUtil.getShiroUser();
////			String content = request.getParameter("content");
//			String content ="大家好！我是新来的";
//			StringBuffer stb = new StringBuffer();
//			stb.append(content);
//			stb.append("-");
//			stb.append(StringUtils.isBlank(user.getName())?user.getAccount():user.getName());
//			tsmMessageService.sendBarrage(stb.toString(),user.getOrgId(),user.getAccount(),user.getName());
//		}catch(Exception e){
//			logger.error("发送弹幕消息异常",e);
//		}
//		return "0";
//	}
//	
//	/** 解码弹幕消息 */
//	@RequestMapping("/barrageDecode")
//	public void barrageDecode(HttpServletResponse response,HttpServletRequest request){		
//		try{
//			String content = "";
//			String msgMain = request.getParameter("msgMain");
//			if(StringUtils.isNotBlank(msgMain)){
//				String msgXml = new String(CodeUtils.base64Decode(msgMain), IContants.CHAR_ENCODING);
//				BaseSend bs = JSON.parseObject(msgXml, BaseSend.class);
//				MessageObj mo = bs.getMessageinfo();
//				content = new String(CodeUtils.base64Decode(mo.getMsg()), IContants.CHAR_ENCODING);				
//			}	
//			System.out.println("content:"+content);
////			response.setContentType("text/html; charset=UTF-8");
////			response.getWriter().print(content);
//		}catch(Exception e){
//			logger.error("解码弹幕消息异常",e);
//		}		
//	}
	
	/** 解码弹幕消息 */
	@RequestMapping("/barrageDecode")
	@ResponseBody
	public Map<String,Object> barrageDecode(HttpServletResponse response,String message){
		Map<String,Object> map =new HashMap<String,Object>();
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			map=mainService.decodeBarrage(message);
//			response.setContentType("text/html; charset=UTF-8");
//			response.getWriter().print(content);

		}catch(Exception e){
			logger.error("解码弹幕消息异常",e);
		}
		return map;
	}
	
	/** 是否弹屏*/
	@RequestMapping("/usertBarOpen")
	@ResponseBody
	public String usertBarOpen(HttpServletRequest request){
		Map<String,Object> map =new HashMap<String,Object>();
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String open=request.getParameter("open");
			User user_new=new User();
			user_new.setOrgId(user.getOrgId());
			user_new.setUserAccount(user.getAccount());
			user_new.setIsBarOpen(Integer.valueOf(open));
			userService.updateUserIsBarOpen(user_new);
		}catch(Exception e){
			logger.error("更新是否弹幕失败",e);
			return "1";
		}
		return "0";
	}
	

	/** 初始化数据 */
	@RequestMapping("/initBarrageDate")
	@ResponseBody
	public Map<String,Object> initBarrageDate(HttpServletRequest request){
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String,Object> map=new HashMap<String,Object>();
		List<BarrageQueue> barQue=new ArrayList<BarrageQueue>();//消息、打赏
		List<BarrageQueue> list2=new ArrayList<BarrageQueue>();//
		List<CardQueue>   carQue=new ArrayList<CardQueue>();
		try{
		String orgId=user.getOrgId();
		List<CardQueue> birQue  =new ArrayList<CardQueue>(); //初始化生日数据
		List<CardQueue> CardQue  =new ArrayList<CardQueue>(); //初始化排行榜数据
		List<CardQueue> list  =new ArrayList<CardQueue>();
		List<CardQueue> list_sign  =new ArrayList<CardQueue>(); //签约
		List<CardQueue> list_sale  =new ArrayList<CardQueue>(); //签约
		List<String> listinit=new ArrayList<String>();
		List<DataDictionaryBean> dlist0 = cachedService.getDirList(AppConstant.DATA_50033, orgId);//弹幕总开关
       if (!dlist0.isEmpty() && "1".equals(dlist0.get(0).getDictionaryValue())) {//弹幕总开关
	   		List<DataDictionaryBean> dlist1 = cachedService.getDirList(AppConstant.DATA_50034, orgId);//生日弹幕开关 
	   		if (!dlist1.isEmpty() && "1".equals(dlist1.get(0).getDictionaryValue())) {
	   			birQue=initBirthdayUsers();
	   		}
	   		
	   		List<DataDictionaryBean> dlist2 = cachedService.getDirList(AppConstant.DATA_50037, orgId);//弹幕弹幕开关 
	   		if (!dlist2.isEmpty() && "1".equals(dlist2.get(0).getDictionaryValue())) {
			    CardQue =initGetranking();
	   		}
	   		    

				if(birQue!=null&&birQue.size()>0){
					for(CardQueue cq1:birQue){
						list.add(cq1);
					}
				}
				if(CardQue!=null&&CardQue.size()>0){
					for(CardQueue cq2:CardQue){
						list.add(cq2);
					}
				}
				
		   		List<DataDictionaryBean> dlist3 = cachedService.getDirList(AppConstant.DATA_50044, orgId);//签约弹幕开关 
		   		if (!dlist3.isEmpty() && "1".equals(dlist3.get(0).getDictionaryValue())) {
					//因为签约数据为不固定，需要累加
					list_sign=cachedService.getBarrageSign(orgId);
		   		}
				

				if(list_sign!=null&&list_sign.size()>0){
			        Collections.reverse(list_sign);
					for(CardQueue cq3:list_sign){
						list.add(cq3);
					}
				}
				
		   		List<DataDictionaryBean> dlist4 = cachedService.getDirList(AppConstant.DATA_50048, orgId);//回款弹幕开关 
		   		if (!dlist4.isEmpty() && "1".equals(dlist4.get(0).getDictionaryValue())) {
					//因为回款数据为不固定，需要累加
					list_sale=cachedService.getBarrageSale(orgId);
		   		}

				if(list_sale!=null&&list_sale.size()>0){
				        Collections.reverse(list_sale);
					for(CardQueue cq4:list_sale){
						list.add(cq4);
					}
				}

				cachedService.removeCardQueue(orgId);
				cachedService.setCardQueue(orgId, list);
				barQue=cachedService.getPtBarrageQueue(orgId);
				carQue=cachedService.getCardQueue(orgId);
		
				
				if(barQue!=null&&barQue.size()>0){
					for(BarrageQueue bq1:barQue){
						list2.add(bq1);
					}
				}
        }

		if(list2 !=null && list2.size()>0){
			map.put("barrageQueue",list2);
		}else {
			map.put("barrageQueue", listinit);
		}
			
		if(carQue !=null && carQue.size()>0){
			map.put("cardQueue", carQue);
		}else{
			map.put("cardQueue", listinit);//list为null时默认为[]
		}
		map.put("status", true);
		map.put("msgType", "1");
		map.put("msg", "初始化数据");
		}catch(Exception e){
			logger.error("initBarrageDate:" + e.getMessage(), e);
			map.put("status", false);
			map.put("errorMsg", e.getMessage());
			return map;
		}
		return map;
	}
	
	/** 提取数据 
	 * cardType=3签约数据
	 * barrType=1 消息数据
	 * barrType=5 打赏数据
	 * */
	@RequestMapping("/retData")
	@ResponseBody
	public Map<String,Object> retData(HttpServletRequest request){
		ShiroUser user = ShiroUtil.getShiroUser();
		String cardType = request.getParameter("cardType");
		String barrType = request.getParameter("barrType");
		Map<String,Object> map=new HashMap<String,Object>();
		List<BarrageQueue> barQue=new ArrayList<BarrageQueue>();
		List<CardQueue> carQue=new ArrayList<CardQueue>();
		try{
		String orgId=user.getOrgId();
		if(cardType!=null&&cardType.equals("3")){//签约3
			carQue=cachedService.getBarrageSign(orgId);
			map.put("msgType", "3");
			map.put("msg", "签约数据");
		}
		
		if(barrType !=null&& barrType.equals("1")){//消息数据
		barQue=cachedService.getBarrageQueue(orgId);
		map.put("msgType", "2");
		map.put("msg", "消息数据");
		}
		
		if(barrType !=null&& barrType.equals("5")){//打赏数据
		barQue=cachedService.getPayBarrage(orgId);
		map.put("msgType", "5");
		map.put("msg", "打赏数据");
		}
		map.put("status", true);
		map.put("cardQueue", carQue);
		map.put("barrageQueue", barQue);
		}catch(Exception e){
			logger.error("initBarrageDate:" + e.getMessage(), e);
			map.put("status", false);
			map.put("errorMsg", e.getMessage());
			return map;
		}
		return map;
	}
	
	/** 清除缓存数据 */
	@RequestMapping("/deleBarrageDate")
	@ResponseBody
	public Map<String,Object> deleBarrageDate(HttpServletRequest request){
		ShiroUser user = ShiroUtil.getShiroUser();
        Map<String,Object> map=new HashMap<String,Object>();
		try{
		String orgId=user.getOrgId();
		cachedService.removeBarrageQueue(orgId);
		cachedService.removeCardQueue(orgId);
		cachedService.removeBarrageSign(orgId);
		cachedService.removePayBarrage(orgId);	
		cachedService.removeBirthDayQueue(orgId);
		cachedService.removeBarrageSale(orgId);
		cachedService.removePtBarrageQueue(orgId);
		map.put("status", true);
		}catch(Exception e){
			logger.error("initBarrageDate:" + e.getMessage(), e);
			map.put("status", false);
			map.put("errorMsg", e.getMessage());
			return map;
		}
		return map;
	}
	
	/** 
	 * 领取积分
	 * @param request
	 * @param response
	 * @param rt 
	 * @create  2016年2月19日 下午2:27:05 lixing
	 * @history  
	 */
	@RequestMapping("/receivePoints")
	public void receivePoints(HttpServletRequest request,HttpServletResponse response,String rt){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			// 查询积分规则			
			DataDictionaryBean dictionary = new DataDictionaryBean();
			dictionary.setOrgId(user.getOrgId());
			
			List<DataDictionaryBean>dictionaryList = dataDictionaryService.getListByCondtion(dictionary);
			Map<String, DataDictionaryBean> dictionaryMap = new HashMap<String, DataDictionaryBean>();
			for (DataDictionaryBean obj : dictionaryList) {
				if(null != obj.getDictionaryCode()){
					dictionaryMap.put(obj.getDictionaryCode(), obj);
				}
			}
			
			//核对完成情况
			PlanUsermonthBean monthPlan = planUserMonthService.queryByUserAndMonth(user.getOrgId(), user.getId(),new Date());
			if(rt.equals("1")){//签约金额
				if(monthPlan.getPlanMoneyState() == 0){
					HttpUtil.writeTextResponse(response, "2");//未完成
					return;
				}else if(monthPlan.getPlanMoneyState() == 2){
					HttpUtil.writeTextResponse(response, "3");//已领取
					return;
				}
			}else if(rt.equals("2")){//签约客户
				if(monthPlan.getPlanSigncustnumState() == 0){
					HttpUtil.writeTextResponse(response, "2");//未完成
					return;
				}else if(monthPlan.getPlanSigncustnumState() == 2){
					HttpUtil.writeTextResponse(response, "3");//已领取
					return;
				}
			}else{//意向客户
				if(monthPlan.getPlanWillcustnumState() == 0){
					HttpUtil.writeTextResponse(response, "2");//未完成
					return;
				}else if(monthPlan.getPlanWillcustnumState() == 2){
					HttpUtil.writeTextResponse(response, "3");//已领取
					return;
				}
			}
			Integer userPoints = planUserMonthService.receivePoints(user.getOrgId(), user.getAccount(), monthPlan, rt, dictionaryMap);
			Points entity = new Points();
			entity.setOrgId(user.getOrgId());
			List<Points> pointsList = pointsService.getListByCondtion(entity);
			Points curPoints = new Points();
			for(Points pt : pointsList){
				if(pt.getLevel() == 1){
					if(userPoints <= pt.getEndNumber()){
						curPoints = pt;
						break;
					}
				}else if(pt.getLevel() == 2 || pt.getLevel() == 3 || pt.getLevel() == 4){
					if(userPoints >= pt.getStartNumber() && userPoints <= pt.getEndNumber()){
						curPoints = pt;
						break;
					}
				}else{
					if(userPoints >= pt.getStartNumber()){
						curPoints = pt;
						break;
					}
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("level", curPoints.getLevel());
			map.put("levelName", curPoints.getLevelName());
			map.put("startNumber", curPoints.getStartNumber());
			map.put("endNumber", curPoints.getEndNumber());
			map.put("beyondNumber", userPoints-curPoints.getStartNumber());
			HttpUtil.writeTextResponse(response, JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			HttpUtil.writeTextResponse(response, "-1");
			logger.error("领取积分失败!",e);
		}
	}
	
	/** 
	 * 计算签约数排名
	 * @param dtos
	 * @return 
	 * @create  2015年12月8日 上午11:34:56 lixing
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
		}
		if(dtos.size()>0){
			avgSignNum = totalSignNum/dtos.size();
		}
		return getSignViewRank(dtos,user,avgSignNum);
	}
	
	
	/** 
	 * 得到签约首页展示排行榜
	 * @param dtos 排名list
	 * @param user 当前登陆用户
	 * @param avg 平均值
	 * @return 
	 * @create  2015年12月9日 上午9:45:09 lixing
	 * @history  
	 */
	public List<RankReportDto> getSignViewRank(List<RankReportDto> dtos,ShiroUser user,Integer avg){
		List<RankReportDto> list = new ArrayList<RankReportDto>();
		for(int i = 0 ; i < dtos.size() ; i++){
			RankReportDto dto = dtos.get(i);
			String account = dto.getUserAccount();
			if(account.equals(user.getAccount())){//如果是当前用户
//				dto.setUserAccount("curr_user_account");
				if(i > 4){
					list.remove(4);
					list.remove(3);
					list.add(3,dtos.get(i-1));
					list.add(4,dto);
				}else{
					list.add(dto);
				}
			}else{
				if(list.size() <= 4){
					list.add(dto);
				}
			}
		}
		for(int i = 0 ; i < list.size() ; i++){
			RankReportDto dto = list.get(i);
			if(dto.getSignNum() < avg){
				RankReportDto rrd = new RankReportDto();
				rrd.setSignNum(avg);
				rrd.setUserName("平均哥");
				rrd.setUserAccount("sign_num_avg");
				list.add(i,rrd);
				break;
			}
			if(i== (list.size() -1)){
				RankReportDto rrd = new RankReportDto();
				rrd.setSignNum(avg);
				rrd.setUserName("平均哥");
				rrd.setUserAccount("sign_num_avg");
				list.add(rrd);
				break;
			}
		}
		return list;
	}
	
	/** 
	 * 计算签约金额排名
	 * @param dtos
	 * @return 
	 * @create  2015年12月8日 上午11:35:32 lixing
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
		}
		if(dtos.size() > 0){
			avgSaleNum = totalSaleNum.divide(BigDecimal.valueOf(dtos.size()),2,BigDecimal.ROUND_HALF_EVEN);
		}
		return getSaleViewRank(dtos,user,avgSaleNum);
	}
	
	/** 
	 * 得到销售首页展示排行榜
	 * @param dtos 排名list
	 * @param user 当前登陆用户
	 * @param avg 平均值
	 * @return 
	 * @create  2015年12月9日 上午9:45:09 lixing
	 * @history  
	 */
	public List<RankReportDto> getSaleViewRank(List<RankReportDto> dtos,ShiroUser user,BigDecimal avg){
		List<RankReportDto> list = new ArrayList<RankReportDto>();
		for(int i = 0 ; i < dtos.size() ; i++){
			RankReportDto dto = dtos.get(i);
			BigDecimal saleNum = dto.getSaleNum();
			dto.setSaleNum(saleNum);
			String account = dto.getUserAccount();
			if(account.equals(user.getAccount())){//如果是当前用户
//				dto.setUserAccount("curr_user_account");
				if(i > 4){
					list.remove(4);
					list.remove(3);
					list.add(3,dtos.get(i-1));
					list.add(4,dto);
				}else{
					list.add(dto);
				}
			}else{
				if(list.size() <= 4){
					list.add(dto);
				}
			}
		}
		for(int i = 0 ; i < list.size() ; i++){
			RankReportDto dto = list.get(i);
			if(dto.getSaleNum().compareTo(avg) == -1){
				RankReportDto rrd = new RankReportDto();
				rrd.setSaleNum(avg);
				rrd.setUserName("平均哥");
				rrd.setUserAccount("sale_num_avg");
				list.add(i,rrd);
				break;
			}
			if(i== (list.size() -1)){
				RankReportDto rrd = new RankReportDto();
				rrd.setSaleNum(avg);
				rrd.setUserName("平均哥");
				rrd.setUserAccount("sale_num_avg");
				list.add(rrd);
				break;
			}
		}
		return list;
	}
	
	/** 
	 * 设置是否需要模糊电话号码
	 * @param request 
	 * @create  2015年11月25日 下午2:45:20 lixing
	 * @history  
	 */
//	public void setIsReplaceWord(HttpServletRequest request){
//		ShiroUser user = ShiroUtil.getShiroUser();
//		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA24,user.getOrgId());
//		Integer idReplaceWord = 0;
//		if(list.size() > 0){
//			idReplaceWord = new Integer(list.get(0).getDictionaryValue());
//		}
//		request.setAttribute("idReplaceWord", idReplaceWord);
//	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, Exception {
		String msgMain = "eyJtZXNzYWdlaW5mbyI6eyJtc2ciOiI1b0d0NVphY2VHcDVNakF3TWVhSWtPV0tuK2V0dnVlNnB1\nYWZwZWVuZ09Xb255RT0iLCJ3YXYiOiIxIiwidGl0bGUiOiI1Ynk1NWJtVjc3eUIifX0=";
		String msgXml = new String(CodeUtils.base64Decode(msgMain), IContants.CHAR_ENCODING);
		BaseSend bs = JSON.parseObject(msgXml, BaseSend.class);
		MessageObj mo = bs.getMessageinfo();
		String content = new String(CodeUtils.base64Decode(mo.getMsg()), IContants.CHAR_ENCODING);				
		System.out.print(content);
	}
	
	
	//弹幕初始化数据
	/** 初始化生日员工信息 */
	public List<CardQueue> initBirthdayUsers(){
		List<TsmMessageSend> list =new ArrayList<TsmMessageSend>();
		List<CardQueue> list_Car=new ArrayList<CardQueue>();
		List<CardQueue> list_Car_new =new ArrayList<CardQueue>();
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			list=mainService.findAllUserByOrgId(shiroUser.getOrgId());	
			List<CardQueue> list_CarQue_new =new ArrayList<CardQueue>();

			if(list != null && list.size()>0){
				for(TsmMessageSend send:list){
					CardQueue carque=new CardQueue();
					carque.setCardType("1");
					carque.setContent("公司全体同仁祝"+send.getContent()+"生日快乐!");
					carque.setImgUrl(send.getImgUrl());
					carque.setUserAccount(send.getSendTo());
					carque.setUserName(send.getBirthDayUserAccount());
					list_Car.add(carque);
					list_Car_new.add(carque);
				}
			}
			if(list_Car!=null&&list_Car.size()>0){
				list_CarQue_new= cachedService.getBirthDayQueue(shiroUser.getOrgId());
			if(list_CarQue_new!=null && list_CarQue_new.size()>0){
				cachedService.removeBirthDayQueue(shiroUser.getOrgId());
			}
			cachedService.setBirthDayQueue(shiroUser.getOrgId(), list_Car);
			}
		}catch(Exception e){
			logger.error("birthdayUsers:" + e.getMessage(), e);

		}
		return list_Car_new;
	}
	
	
	/*
	 * 初始化排行榜信息
	 * 签约冠军，新增意向冠军，呼出时长冠军，呼出次数冠军
	 * */
	    public List<CardQueue> initGetranking(){
		        Map<String,List<RankReportDto>> returnmap=new HashMap<String,List<RankReportDto>>();
				Map<String,Object> map2=new HashMap<String,Object>();
				List<CardQueue> list =new ArrayList<CardQueue>();
	    	try {
				ShiroUser user = ShiroUtil.getShiroUser();
				Calendar cal = Calendar.getInstance();
				String yearStr="";
				String monthStr="";
				if(StringUtils.isBlank(yearStr)){
					yearStr = cal.get(Calendar.YEAR)+"";
					monthStr = (cal.get(Calendar.MONTH))+"";//查询上一个月的
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
                String last_yearStr="";
                String last_monthStr="";
				if("0".endsWith(monthStr)){
					last_yearStr=cal.get(Calendar.YEAR)-1+"";
					last_monthStr="12";
					rankingReportDtos = rankingReportService.getRankingList(user.getOrgId(), null, last_yearStr, last_monthStr,user.getAccount());
				}else{
					rankingReportDtos = rankingReportService.getRankingList(user.getOrgId(), null, yearStr, monthStr,user.getAccount());
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
					rrd.setContent(pub.getUserName());
					rrd.setUserAccount(pub.getAccount());
					rrd.setContent(pub.getUserName());
					signDtos.add(rrd);
					RankReportDto rrd1 = new RankReportDto();
					rrd1.setUserName(pub.getUserName());
					rrd1.setSaleNum(pub.getSaleAmounts());
					rrd1.setUserAccount(pub.getAccount());
					rrd1.setContent(pub.getUserName());
					saleDtos.add(rrd1);
					RankReportDto rrd2 = new RankReportDto();
					rrd2.setUserName(pub.getUserName());
					rrd2.setWillNum(pub.getIntNums());
					rrd2.setUserAccount(pub.getAccount());
					rrd2.setContent(pub.getUserName());
					willDtos.add(rrd2);
					accounts.add(pub.getAccount());
				}
				// 查询 用户集合
				List<RankReportDto> dtos = rankingReportService.getCallRankingList(map);
				Map<String,CallReportDto> todayMap = new HashMap<String, CallReportDto>();
	    		if((yearStr.equals(cal.get(Calendar.YEAR)+"") && monthStr == null) || ((cal.get(Calendar.MONTH))+"").equals(monthStr)){
	    			todayMap = callReportService.getDayReportByAccountsMap(user.getOrgId(), accounts);
	    		}
	    		for(RankReportDto dto : dtos){
	    			if((yearStr.equals(cal.get(Calendar.YEAR)+"") && monthStr == null) || ((cal.get(Calendar.MONTH))+"").equals(monthStr)){
		    			CallReportDto td = todayMap.get(dto.getUserAccount());
		    			dto.setCallOutTotal(dto.getCallOutTotal()+td.getCalloutTotal());
		    			BigDecimal callTime = dto.getCallTime().add(BigDecimal.valueOf(td.getCalloutTime()));
		    			dto.setCallTime(callTime);
		    			dto.setContent(dto.getUserName());
	    			}
	    			callDtos.add(dto);
	    			RankReportDto numDto = new RankReportDto();
	    			BeanUtils.copyProperties(dto, numDto);
	    			calloutDtos.add(numDto);
	    		}
				//排名
				saleDtos = saleNumRank(saleDtos, user);
				RankReportDto saleTop3 = getTopThree(saleDtos);
				signDtos = signNumRank(signDtos,user);
				RankReportDto signTop3 = getTopThree(signDtos);
				willDtos = willNumRank(willDtos, user);
				RankReportDto willTop3 = getTopThree(willDtos);
				callDtos = callTimeRank(callDtos, user);
				RankReportDto callTop3 = getTopThree(callDtos);
				calloutDtos = callOutRank(calloutDtos, user);
				RankReportDto callOutTop3 = getTopThree(calloutDtos);
				//需要给数据添加一部分参数
				saleTop3=retRank(saleTop3);
				signTop3=retRank(signTop3);
				willTop3=retRank(willTop3);
				callTop3=retRank(callTop3);
				callOutTop3=retRank(callOutTop3);
				signTop3.setCardType("2");
				willTop3.setCardType("2");
				callTop3.setCardType("2");
				callOutTop3.setCardType("2");
				List<Object> list2=new ArrayList<Object>();
				CardQueue carQue_sale=new CardQueue();
				CardQueue carQue_sign=new CardQueue();
				CardQueue carQue_will=new CardQueue();
				CardQueue carQue_callTop=new CardQueue();
				CardQueue carQue_callOut=new CardQueue();
				//回款
				carQue_sale.setCardType("2");
				carQue_sale.setContent(saleTop3.getContent());
				carQue_sale.setImgUrl(saleTop3.getImgUrl());
				carQue_sale.setUserAccount(saleTop3.getUserAccount());
				carQue_sale.setUserName(saleTop3.getUserName());
				//签约
				carQue_sign.setCardType("2");
				carQue_sign.setContent(signTop3.getContent());
				carQue_sign.setImgUrl(signTop3.getImgUrl());
				carQue_sign.setUserAccount(signTop3.getUserAccount());
				carQue_sign.setUserName(signTop3.getUserName());
				//意向
				carQue_will.setCardType("2");
				carQue_will.setContent(willTop3.getContent());	
				carQue_will.setImgUrl(willTop3.getImgUrl());
				carQue_will.setUserAccount(willTop3.getUserAccount());
				carQue_will.setUserName(willTop3.getUserName());
				//通话
				carQue_callTop.setCardType("2");
				carQue_callTop.setContent(callTop3.getContent());	
				carQue_callTop.setImgUrl(callTop3.getImgUrl());
				carQue_callTop.setUserAccount(callTop3.getUserAccount());
				carQue_callTop.setUserName(callTop3.getUserName());
				//呼出
				carQue_callOut.setCardType("2");
				carQue_callOut.setContent(callOutTop3.getContent());	
				carQue_callOut.setImgUrl(callOutTop3.getImgUrl());
				carQue_callOut.setUserAccount(callOutTop3.getUserAccount());
				carQue_callOut.setUserName(callOutTop3.getUserName());
				
				List<CardQueue> list_CarQue =new ArrayList<CardQueue>();
				List<CardQueue> list_CarQue_new =new ArrayList<CardQueue>();
				int newSale=getBarrageNewSale(user.getOrgId());
			    int newSign=getBarrageNewSign(user.getOrgId());
			    int callout=getBarrageCallout(user.getOrgId());
			    int newWill=getBarrageNewWill(user.getOrgId());
			    int call=getBarrageCall(user.getOrgId());
			    cachedService.setRangSaleCardQueue(user.getOrgId(), carQue_sale);//回款金额
			    cachedService.setRangSignCardQueue(user.getOrgId(), carQue_sign);//签约
			    cachedService.setRangWillCardQueue(user.getOrgId(), carQue_will);//意向
			    cachedService.setRangCallCardQueue(user.getOrgId(), carQue_callTop);//呼出时长
			    cachedService.setRangCallOutCardQueue(user.getOrgId(), carQue_callOut);//呼出次数
			    
			    SimpleDateFormat sdf = new SimpleDateFormat("MM");
				 Calendar cal2 = Calendar.getInstance();
				 Date date = new Date();
			     cal2.setTime(date);
			     cal2.add(Calendar.MONTH, -1);
			     sdf.format(cal2.getTime());
				  if(newSale==1){
				    	if(newSale==1&&saleTop3.getSaleNum().compareTo(BigDecimal.ZERO)==1){
				    	   carQue_sale.setContent("恭喜"+carQue_sale.getContent()+"赢得"+sdf.format(cal2.getTime())+"月回款冠军");
				    	   carQue_sale.setIfShow(1);
				    	   list.add(carQue_sale);  
				    	}
				    	
				    }
			    if(newSign==1){
			    	if(newSign==1&&signTop3.getSignNum()>0){
				    	carQue_sign.setContent("恭喜"+carQue_sign.getContent()+"赢得"+sdf.format(cal2.getTime())+"月签约冠军");
			    		carQue_sign.setIfShow(1);
				    	list.add(carQue_sign);  
			    	}

			    	}
			    if(callout==1){
			    	if(callout==1&&callOutTop3.getCallOutTotal()>0){
				    	carQue_callOut.setContent("恭喜"+carQue_callOut.getContent()+"赢得"+sdf.format(cal2.getTime())+"月呼出次数冠军");
			    		carQue_callOut.setIfShow(1);
				    	list.add(carQue_callOut);
			    	}

			    	}
			    if(newWill==1){
			    	if(newWill==1&&willTop3.getWillNum()>0){
				    	carQue_will.setContent("恭喜"+carQue_will.getContent()+"赢得"+sdf.format(cal2.getTime())+"月新增意向冠军");
			    		carQue_will.setIfShow(1);
				    	list.add(carQue_will);  
			    	}
 
			    }
			    if(call==1){
			    	if(call==1&&callTop3.getCallTime().compareTo(BigDecimal.ZERO)==1){
				    	carQue_callTop.setContent("恭喜"+carQue_callTop.getContent()+"赢得"+sdf.format(cal2.getTime())+"月呼出时长冠军");
			    		carQue_callTop.setIfShow(1);
			    		list.add(carQue_callTop);
			    	}
			    	  
			    }	
				
			} catch (Exception e) {
				logger.error("getbranking:" + e.getMessage(), e);

			}
	    	return list;
	    }
	
	
	    @RequestMapping("/ranking")
		@ResponseBody
		public Map<String, Object> getHomeRanking(){
			Map<String, Object> map;
			try {
				ShiroUser user = ShiroUtil.getShiroUser();
				map = cachedService.getRanking(user.getOrgId(), user.getGroupId());
				if(map == null){
					map = new HashMap<String, Object>();
					Calendar cal = Calendar.getInstance();
					List<RankingReportDto> rankingReportDtos = rankingReportService.getRankingList(user.getOrgId(), Arrays.asList(user.getGroupId()),cal.get(Calendar.YEAR)+"",(cal.get(Calendar.MONTH)+1)+"",null);
					List<RankReportDto> signDtos = new ArrayList<RankReportDto>();
					List<RankReportDto> saleDtos = new ArrayList<RankReportDto>();
					for(RankingReportDto pub : rankingReportDtos){
						RankReportDto rrd = new RankReportDto();
						rrd.setUserName(pub.getUserName());
						rrd.setSignNum(pub.getSignNums());
						rrd.setUserAccount(pub.getAccount());
						signDtos.add(rrd);
						RankReportDto rrd1 = new RankReportDto();
						rrd1.setUserName(pub.getUserName());
						rrd1.setSaleNum(pub.getSaleAmounts());
						rrd1.setUserAccount(pub.getAccount());
						saleDtos.add(rrd1);
					}
					signDtos = signNumRank(signDtos,user);
					saleDtos = saleNumRank(saleDtos,user);
					for(RankReportDto rrd : saleDtos){
						rrd.setSaleNum(rrd.getSaleNum().divide(BigDecimal.valueOf(10000),2,BigDecimal.ROUND_HALF_UP));
					}
					map.put("signDtos", signDtos);
					map.put("saleDtos", saleDtos);
					cachedService.setRanking(user.getOrgId(), user.getGroupId(), map);
				}
			} catch (Exception e) {
				logger.error("首页排行榜查询异常！",e);
				return null;
			}
			return map;
		}
	    
	    @RequestMapping("/tobarrage")
	    public String tobarrage(){
	    	try {
	    		ShiroUser user = ShiroUtil.getShiroUser();

	    	} catch (Exception e) {
	    		logger.error("首页排行榜查询异常！",e);
	    		return null;
	    	}
	    	return "/barrage";
	    }
	    
	    
	    /** 每天首次登陆 发送消息 */
		@RequestMapping("/followMsg")
		public void followMsg(){
			try{
				ShiroUser user = ShiroUtil.getShiroUser();
				// 资源回收 消息提前提醒
				resourceExpireQuartz.main(user.getOrgId(),"1",user.getAccount());
				// 意向客户回收 消息提前提醒
				expireFollowQuartz.main(user.getOrgId(),"1", user.getAccount());
				// 签约客户订单失效回收 消息提前提醒
				signCustOrderExpireQuartz.main(user.getOrgId(),1, user.getAccount());
			}catch(Exception e){
				logger.error("每天首次登陆 发送消息 异常! ", e);
			}
		}
		
	    
}


 /** 
 * 签约排名
 * @author: lixing
 * @since: 2015年12月8日  上午11:23:55
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
 * @since: 2015年12月8日  上午11:25:42
 * @history:
 */
class SaleAmountComparator implements Comparator<RankReportDto>{
	public int compare(RankReportDto o1, RankReportDto o2) {
		return o1.getSaleNum().compareTo(o2.getSaleNum());
	}
	
}

