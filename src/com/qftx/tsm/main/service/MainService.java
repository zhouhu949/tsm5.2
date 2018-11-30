package com.qftx.tsm.main.service;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.dto.BaseSend;
import com.qftx.base.auth.dto.MessageObj;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.dao.TeamGroupMapper;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.CodeUtils;
import com.qftx.common.util.IContants;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.main.bean.BarrageQueue;
import com.qftx.tsm.main.bean.CardQueue;
import com.qftx.tsm.main.dao.MainMapper;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;
import com.qftx.tsm.plan.group.month.dao.PlanGroupmonthMapper;
import com.qftx.tsm.plan.group.month.dto.PlanGroupmonthReportDto;
import com.qftx.tsm.plan.user.day.dao.PlanUserDayMapper;
import com.qftx.tsm.plan.user.day.dto.TeamDayPlanReportDto;
import com.qftx.tsm.sms.bean.TsmMessageSend;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {
	@Autowired
	private TeamGroupMapper teamGroupMapper;
	@Autowired
	private PlanGroupmonthMapper planGroupmonthMapper;
	@Autowired
	private PlanUserDayMapper planUserDayMapper;
	@Autowired
	private MainMapper mainMapper;
	@Autowired
	private TsmMessageService tsmMessageService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private UserService userService;
	
	/** 
	 * 查询管理者管理分组列表
	 * @param userAccount
	 * @return 
	 * @create  2015年12月9日 下午4:45:10 lixing
	 * @history  
	 */
	public List<TeamGroupBean> queryManageGroupList(String userAccount,String orgId){
		return teamGroupMapper.queryManageGroupList(userAccount,orgId);
	}
	
	
	/** 
	 * 查询管理者管理分组月计划
	 * @param map
	 * @return 
	 * @create  2015年12月9日 下午4:44:35 lixing
	 * @history  
	 */
	public List<PlanGroupmonthReportDto> queryShareTeamMonthPlan(Map<String, Object> map){
		return planGroupmonthMapper.queryShareTeamMonthPlan(map);
	}
	
	
	/** 
	 * 查询管理者 月度业绩、新增意向
	 * @param map
	 * @return 
	 * @create  2015年12月11日 下午1:45:40 lixing
	 * @history  
	 */
	public List<PlanGroupmonthBean> queryTeamMonthPlanLineReport(Map<String, String> map){
		return planGroupmonthMapper.queryTeamMonthPlanLineReport(map);
	}
	
	/** 
	 * 管理者首页明日计划
	 * @param orgId
	 * @param shareAcc
	 * @param planDate
	 * @return 
	 * @create  2015年12月11日 上午11:40:19 lixing
	 * @history  
	 */
	public List<TeamDayPlanReportDto> queryTeamTomorrowPlanReportDtos(String orgId,String shareAcc,String planDate){
		List<TeamDayPlanReportDto> notReportDtos = planUserDayMapper.findTeamNotReportNums(orgId, shareAcc, planDate);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(TeamDayPlanReportDto t : notReportDtos){
			map.put(t.getGroupId(), t.getNotReport());
		}
		List<TeamDayPlanReportDto> reportDtos = planUserDayMapper.findTeamTomorrowPlans(orgId, shareAcc, planDate);
		for (TeamDayPlanReportDto td : reportDtos) {
			if(map.get(td.getGroupId()) != null){
				td.setNotReport(td.getNotReport()+map.get(td.getGroupId()));
			}
		}
		return reportDtos;
	}
	
    public List<TsmMessageSend> findAllUserByOrgId(String OrgId){
    	return mainMapper.findAllUserByOrgId(OrgId);
    }
    
    /** 生日员工信息 */
	public Map<String,Object> sendBirthdayUsers(String orgId){
		List<TsmMessageSend> list =new ArrayList();
		List<TsmMessageSend> list2 =new ArrayList();
		List<String> list3 =new ArrayList();
		List<CardQueue> list_Car=new ArrayList<CardQueue>();
		List<CardQueue> list_Car_new =new ArrayList<CardQueue>();
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			list=findAllUserByOrgId(orgId);	
			if(list != null && list.size()>0){
				for(TsmMessageSend send:list){
					CardQueue carque=new CardQueue();
					carque.setCardType("1");
					carque.setContent(send.getContent());
					carque.setUserAccount(send.getBirthDayUserAccount());
					carque.setUserName(send.getContent());
					if(send.getContent()==null||send.getContent()==""){
//					carque.setImgUrl("http://m1.biz.itc.cn/pic/new/n/32/15/Img8231532_n.jpg");		
					}else{
					carque.setImgUrl(send.getImgUrl());						
					}

					list_Car.add(carque);
					list_Car_new.add(carque);
				}
			}
			map.put("cardQueue", list_Car_new);
			map.put("status", true);
			map.put("barrageQueue", list3);
			map.put("msgType", "4");
			map.put("msg", "生日数据");
		}catch(Exception e){
			map.put("status", false);
			map.put("errorMsg", e.getMessage());
			return map;
		}
		return map;
	}
	
	/*
	 * 
	* 		msgType：1
	*	消息：barrType ：1
	*	系统消息（打赏）：barrType ：2
	*
	* msgType：2
	*	生日：cardType：1
	*	排行榜：cardType：2
	*	签约：cardType：3
	*	回款：cardType：4
	*	
	*	
	*	msgType：3
	*	生日弹幕打开推送
	*
	*	*	msgType：4
	*	排行榜弹幕打开推送
	* */
	
	
	/** 
	 * 发送弹幕消息 
	 * content 消息内容
	 * msgType 消息类型：1为消息数据,BarrType 1
	 * */
	public void sendBarrage(String content,String orgId,String account,String name){
		    Map<String,Object> map=new HashMap<String,Object>();
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String id=StringUtils.getDateAndRandStr(15);
		    String	barrType="";
			List<BarrageQueue> list_new =new ArrayList<BarrageQueue>();
			List<BarrageQueue> list =new ArrayList<BarrageQueue>();
			List<Object> list2 =new ArrayList<Object>();
			BarrageQueue barQue=new BarrageQueue();
			if(barrType == "" ||barrType ==null){
				barQue.setBarrType("1");
			}
			   if(content == null){
				   content="";
			    }
			barQue.setContent(content);
			barQue.setUserAccount(account);
			barQue.setUserName(name);
			barQue.setImgUrl(user.getImgUrl());
			barQue.setId(id);
			barQue.setShowType(getBarrageData(orgId,account));//设置showtype
			List<BarrageQueue> list_Barr =new ArrayList<BarrageQueue>();
			list_Barr=cachedService.getPtBarrageQueue(orgId);
			if(list_Barr !=null && list_Barr.size()>0){
				for(BarrageQueue bq:list_Barr){
					list_new.add(bq);
				}
			}
			list_new.add(barQue);
			cachedService.removePtBarrageQueue(orgId);
			cachedService.setPtBarrageQueue(orgId, list_new);
			List<String> listinit=new ArrayList<String>();
			list.add(barQue);
			
			
			if(list !=null && list.size()>0){
				map.put("barrageQueue", list);
			}else {
				map.put("barrageQueue", listinit);
			}
				
			if(list2 !=null && list2.size()>0){
				map.put("cardQueue", list2);
			}else{
				map.put("cardQueue", listinit);//list为null时默认为[]
			}
			map.put("msgType", "1");
			map.put("msg", "消息数据");
			map.put("status", true);
       	 JSONObject jsonObject = JSONObject.fromObject(map);
       	 String count=jsonObject.toString();
       	 tsmMessageService.sendBarrageMessage(count,orgId,account,name,id);

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/** 
	 * 发送弹幕,红包
	 * content 消息内容
	 * msgType：1 消息类型：红包,BarrType=4
	 * */
	public void sendHbBarrage(String content,String orgId,String account,String name){
		    Map<String,Object> map=new HashMap<String,Object>();
		try{
		    String	barrType="";
		    String id=StringUtils.getDateAndRandStr(15);
			List<BarrageQueue> list_new =new ArrayList<BarrageQueue>();
			List<BarrageQueue> list =new ArrayList<BarrageQueue>();
			List<Object> list2 =new ArrayList<Object>();
			BarrageQueue barQue=new BarrageQueue();
			if(barrType == "" ||barrType ==null){
				barQue.setBarrType("4");
			}
			barQue.setContent(content);
			barQue.setUserName("系统消息");
			barQue.setUserAccount("");
			barQue.setImgUrl("/static/images/systx.png");
			barQue.setId(id);

			List<BarrageQueue> list_Barr =new ArrayList<BarrageQueue>();
			list_Barr=cachedService.getBarrageQueue(orgId);
			if(list_Barr !=null && list_Barr.size()>0){
				for(BarrageQueue bq:list_Barr){
					list_new.add(bq);
				}
			}
			list_new.add(barQue);
			cachedService.removeBarrageQueue(orgId);
			cachedService.setBarrageQueue(orgId, list_new);
			List<String> listinit=new ArrayList<String>();
			list.add(barQue);
			
			
			if(list !=null && list.size()>0){
				map.put("barrageQueue", list);
			}else {
				map.put("barrageQueue", listinit);
			}
				
			if(list2 !=null && list2.size()>0){
				map.put("cardQueue", list2);
			}else{
				map.put("cardQueue", listinit);//list为null时默认为[]
			}
			map.put("msgType", "1");
			map.put("msg", "系统红包数据");
			map.put("status", true);
       	 JSONObject jsonObject = JSONObject.fromObject(map);
       	 String count=jsonObject.toString();
       	 tsmMessageService.sendBarrageMessage(count,orgId,account,name,id);

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/** 
	 * 发送系统消息(礼物)
	 * content 消息内容
	 * msgType 消息类型：1,BarrType 3
	 * */
	public void sendSysBarrage(String content,String orgId,String account,String name){
		    Map<String,Object> map=new HashMap<String,Object>();
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
		    String id=StringUtils.getDateAndRandStr(15);
		    String	barrType="";
		    String cardType="";
			List<BarrageQueue> list_new =new ArrayList<BarrageQueue>();
			List<BarrageQueue> list =new ArrayList<BarrageQueue>();
			List<Object> list2 =new ArrayList<Object>();
			BarrageQueue barQue=new BarrageQueue();
			if(barrType == "" ||barrType ==null){
				barQue.setBarrType("3");
			}
			   if(content == null){
				   content="";
			    }
			barQue.setContent(content);
			barQue.setUserName("系统消息");
			barQue.setUserAccount("");
			barQue.setImgUrl("/static/images/systx.png");
			barQue.setId(id);
//			barQue.setShowType(getBarrageData(orgId,account));//设置showtype
			List<BarrageQueue> list_Barr =new ArrayList<BarrageQueue>();
			list_Barr=cachedService.getBarrageQueue(orgId);
			if(list_Barr !=null && list_Barr.size()>0){
				for(BarrageQueue bq:list_Barr){
					list_new.add(bq);
				}
			}
			list_new.add(barQue);
			cachedService.removeBarrageQueue(orgId);
			cachedService.setBarrageQueue(orgId, list_new);
			List<String> listinit=new ArrayList<String>();
			list.add(barQue);
			
			
			if(list !=null && list.size()>0){
				map.put("barrageQueue", list);
			}else {
				map.put("barrageQueue", listinit);
			}
				
			if(list2 !=null && list2.size()>0){
				map.put("cardQueue", list2);
			}else{
				map.put("cardQueue", listinit);//list为null时默认为[]
			}
			map.put("msgType", "1");
			map.put("msg", "消息数据");
			map.put("status", true);
       	 JSONObject jsonObject = JSONObject.fromObject(map);
       	 String count=jsonObject.toString();
//       tsmMessageService.sendBarrage_new(count,orgId,account,name);
       	 tsmMessageService.sendBarrageMessage(count,orgId,account,name,id);

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/** 签约弹幕
	 * msgType 2 
	 * cardType：3为签约
	 * */
	public void sendSingBarrage(String content,String msgType,String orgId,String account,String name,String money,int moneyIfShow){
		    Map<String,Object> map=new HashMap<String,Object>();
		try{
			String cardType="3";
		    String id=StringUtils.getDateAndRandStr(15);
			List<CardQueue> list_new =new ArrayList<CardQueue>();
			List<CardQueue> cardQueue =new ArrayList<CardQueue>();
			List<Object> list2 =new ArrayList<Object>();
			CardQueue carQue=new CardQueue();
			carQue.setCardType(cardType);
			if(content == null){
				content="";
			}
			carQue.setContent(content);
			Map<String,String> map_img=new HashMap<String,String>();
			map_img.put("orgId", orgId);
			map_img.put("account", account);
		    String img=	userService.findUserImgByOrgId(map_img);
//		    if(img==null||"".equals(img)){
//				carQue.setImgUrl("http://m1.biz.itc.cn/pic/new/n/32/15/Img8231532_n.jpg");	
//		    }else{
		    	carQue.setImgUrl(img);	
//		    }
		    	carQue.setUserAccount(account);
		    	carQue.setUserName(name);
		    	carQue.setShowType(getBarrageData(orgId,account));
		    	carQue.setMoney(money);
		    	carQue.setMoneyIfShow(moneyIfShow);
		    	carQue.setId(id);
			List<CardQueue> list_CarQue_new =new ArrayList<CardQueue>();
			list_CarQue_new=cachedService.getBarrageSign(orgId);
			if(list_CarQue_new!=null && list_CarQue_new.size()>0){
				for(CardQueue car:list_CarQue_new){
					list_new.add(car);
				}
			}
			list_new.add(carQue);
			cardQueue.add(carQue);
			cachedService.removeBarrageSign(orgId);
			cachedService.setBarrageSign(orgId, list_new);
			List<String> listinit=new ArrayList();
			if(list2 !=null && list2.size()>0){
				map.put("barrageQueue", list2);
			}else {
				map.put("barrageQueue", listinit);
			}
				
			if(cardQueue !=null && cardQueue.size()>0){
				map.put("cardQueue", cardQueue);
			}else{
				map.put("cardQueue", listinit);//list为null时默认为[]
			}
			
			map.put("status", true);
			map.put("msgType", "2");
			map.put("msg", "签约");
		 	 JSONObject jsonObject = JSONObject.fromObject(map);
	       	 String count=jsonObject.toString();
//	       	 tsmMessageService.sendBarrage_new(count,orgId,account,name);
	       	 tsmMessageService.sendBarrageMessage(count,orgId,account,name,id);
		}catch(Exception e){

		}
		
	}
	
	
	/** 回款弹幕
	 * msgType 2
	 *  消息类型cardType：4为回款
	 * */
	public void sendSaleBarrage(String content,String msgType,String orgId,String account,String name){
		    Map<String,Object> map=new HashMap<String,Object>();
		try{
		    String id=StringUtils.getDateAndRandStr(15);
			String cardType="4";
			List<CardQueue> list_new =new ArrayList<CardQueue>();
			List<CardQueue> cardQueue =new ArrayList<CardQueue>();
			List<Object> list2 =new ArrayList<Object>();
			CardQueue carQue=new CardQueue();
			carQue.setCardType(cardType);
			if(content == null){
				content="";
			}
			carQue.setContent(content);
			Map<String,String> map_img=new HashMap<String,String>();
			map_img.put("orgId", orgId);
			map_img.put("account", account);
		    String img=	userService.findUserImgByOrgId(map_img);
		    	carQue.setImgUrl(img);	
		    	carQue.setUserAccount(account);
		    	carQue.setUserName(name);
		    	carQue.setShowType(getBarrageData(orgId,account));
		    	carQue.setId(id);

			List<CardQueue> list_CarQue_new =new ArrayList<CardQueue>();
			list_CarQue_new=cachedService.getBarrageSale(orgId);
			if(list_CarQue_new!=null && list_CarQue_new.size()>0){
				for(CardQueue car:list_CarQue_new){
					list_new.add(car);
				}
			}
			list_new.add(carQue);
			cardQueue.add(carQue);
			cachedService.removeBarrageSale(orgId);
			cachedService.setBarrageSale(orgId, list_new);
			List<String> listinit=new ArrayList<String>();
			if(list2 !=null && list2.size()>0){
				map.put("barrageQueue", list2);
			}else {
				map.put("barrageQueue", listinit);
			}
				
			if(cardQueue !=null && cardQueue.size()>0){
				map.put("cardQueue", cardQueue);
			}else{
				map.put("cardQueue", listinit);//list为null时默认为[]
			}
			
			map.put("status", true);
			map.put("msgType", "2");
			map.put("msg", "回款");
		 	 JSONObject jsonObject = JSONObject.fromObject(map);
	       	 String count=jsonObject.toString();
//	       	 tsmMessageService.sendBarrage_new(count,orgId,account,name);
	       	 tsmMessageService.sendBarrageMessage(count,orgId,account,name,id);
		}catch(Exception e){

		}
		
	}
	
	public int getBarrageData(String orgId,String account){
		List<CardQueue> list_Car_new =new ArrayList<CardQueue>();
		Map<String,Object> map=sendBirthdayUsers(orgId);
		int showType=0;
		if(map.get("cardQueue")!=null){
		list_Car_new=(List<CardQueue>) map.get("cardQueue");
			if(list_Car_new!=null&&list_Car_new.size()>0){
				for(CardQueue car:list_Car_new){
					if(account==car.getUserAccount()||account.endsWith(car.getUserAccount())){
						showType=1;
					}
				}
			}
		}
		    CardQueue SaleCardQueue=cachedService.getRangSaleCardQueue(orgId);
		    CardQueue SignCardQueue=cachedService.getRangSignCardQueue(orgId);
		    CardQueue WillCardQueue=cachedService.getRangWillCardQueue(orgId);
		    CardQueue CallCardQueue=cachedService.getRangCallCardQueue(orgId);
		    CardQueue CallOutCardQueue=cachedService.getRangCallOutCardQueue(orgId);
		    if(SaleCardQueue!=null&&SaleCardQueue.getUserAccount()!=null){
	    		if(account.endsWith(SaleCardQueue.getUserAccount())){//回款
	    			showType=2;
	    			return showType;
	          }
		    }

		    if(SignCardQueue!=null&&SignCardQueue.getUserAccount()!=null){//签约
	    		if(account.endsWith(SignCardQueue.getUserAccount())){//回款
	    			showType=2;
	    			return showType;
		          }

		    }
		    if(WillCardQueue!=null&&WillCardQueue.getUserAccount()!=null){//意向
	    		if(account.endsWith(WillCardQueue.getUserAccount())){//回款
	    			showType=2;
	    			return showType;
		          }
		    }
		    if(CallCardQueue!=null&&CallCardQueue.getUserAccount()!=null){//通话
	    		if(account.endsWith(CallCardQueue.getUserAccount())){//回款
	    			showType=2;
	    			return showType;
		          }
		    }
		    if(CallOutCardQueue!=null&&CallCardQueue.getUserAccount()!=null){//呼出
	    		if(account.endsWith(CallOutCardQueue.getUserAccount())){//回款
	    			showType=2;
	    			return showType;
		          }
		    }
		    
			return showType;
		
	}
	
	//解码数据
	public  Map<String,Object> decodeBarrage(String message){
		Map<String,Object> map=new HashMap<String,Object>();
		try {
		System.out.println("message"+message);
		BaseSend base = JSON.parseObject(message, BaseSend.class);
		String protocolXML = new String(CodeUtils.base64Decode(base.getMsgMain()), IContants.CHAR_ENCODING);
		BaseSend bs = JSON.parseObject(protocolXML, BaseSend.class);
		MessageObj mob=bs.getMessageinfo();
		String msg=new String(CodeUtils.base64Decode(mob.getMsg()), IContants.CHAR_ENCODING);
	    map=JsonUtil.getMapJson(msg);
	    String msgType=(String) map.get("msgType");
		System.out.println("msgType:"+map.get("msgType"));
	    List<CardQueue> cardQueues=(List<CardQueue>)map.get("cardQueue");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	
	/**弹幕设置：签约开关设置， 0-关闭 1-开启*/
	public int getBarrageSign(String orgId){
		int open = 0;
		List<DataDictionaryBean> dlist0 = cachedService.getDirList(AppConstant.DATA_50033, orgId);//弹幕总开关
		List<DataDictionaryBean> dlist1 = cachedService.getDirList(AppConstant.DATA_50044, orgId);//签约弹幕开关 
        if ((!dlist0.isEmpty() && "1".equals(dlist0.get(0).getDictionaryValue()) && (!dlist1.isEmpty() && "1".equals(dlist1.get(0).getDictionaryValue()) ))) {
        	open=1;
        	// 判断是否开启
        }
		return open;
	}
	
	/**弹幕设置：签约开关设置，是否显示客户信息， 0-关闭 1-开启，1不显示*/
	public int getBarrageSign2(String orgId){
		int open = 0;

		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50045, orgId);
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getDictionaryValue();
			open = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "0");
		}
		return open;
	}
	
	/**弹幕设置：签约开关设置，订单上报， 0-关闭 1-开启，1作用在订单上报，并且显示金额，
	 * 而且getBarrageSign2中不弹，即签约不发送弹幕*/
	public int getBarrageSign3(String orgId){
		int open = 0;

		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50046, orgId);
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getDictionaryValue();
			open = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "0");
		}
		return open;
	}
	
}
