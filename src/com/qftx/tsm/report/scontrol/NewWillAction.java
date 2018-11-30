package com.qftx.tsm.report.scontrol;

import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.dao.OrgGroupUserMapper;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.main.service.MainService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.service.OptionService;
import com.qftx.tsm.report.bean.*;
import com.qftx.tsm.report.service.CallReportService;
import com.qftx.tsm.report.service.NewWillService;
import com.qftx.tsm.report.service.TsmReportCallInfoService;
import com.qftx.tsm.report.utils.DateSubUtil;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/newWill")
public class NewWillAction extends BaseAction{
    private static final Logger logger = Logger.getLogger(NewWillAction.class.getName());
    @Autowired
    private NewWillService newWillService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private CallReportService callReportService;
    @Autowired
    private OrgGroupUserMapper orgGroupUserMapper;
    @Autowired
    private TsmReportCallInfoService tsmReportCallInfoService;
    @Autowired
    private MainService mainService;
    @Autowired
    private CachedService cachedService;
    @Autowired
    private TsmTeamGroupService tsmTeamGroupService;
    @Autowired
    private ResCustInfoService resCustInfoService;
    
    // //新增意向统计,当日统计
    @RequestMapping(value = "/returndaydata")
    public String returndaydata(HttpServletRequest request){

	      return "report/willCustStatis/newWill_today";
    }
    
    // main首页
    @RequestMapping(value = "/initmian")
    public String initmian(HttpServletRequest request){

	      return "report/willCustStatis/mainPage";
    }
    public List<Map<String,Object>> getoptionByobject(List<Object> list,List<OptionBean> list_option){
    	List<Map<String,Object>> retlist=new ArrayList<Map<String,Object>>();
    	if(list!=null&&list.size()>1){
    		for(int i=0;i<list.size();i++){
    			if(i==0){
    				Map<String,Object> map=new HashMap<String,Object>();
    				map.put("num", list.get(i));
    				map.put("optionId", "");
    				map.put("optionName", "");
    				retlist.add(map);	
    			}else if(i>0){
    				Map<String,Object> map=new HashMap<String,Object>();
    				map.put("num", list.get(i));
    				map.put("optionId", list_option.get(i-1).getOptionlistId());
    				map.put("optionName",list_option.get(i-1).getOptionName());
    				retlist.add(map);	
    			}
    		}
    	}
    	return retlist;
    }
    
    
    public List<Map<String,Object>> getoptionByString(List<String> list,List<OptionBean> list_option){
    	List<Map<String,Object>> retlist=new ArrayList<Map<String,Object>>();
    	if(list!=null&&list.size()>1){
    		for(int i=0;i<list.size();i++){
    			if(i==0){
    				Map<String,Object> map=new HashMap<String,Object>();
    				map.put("num", list.get(i));
    				map.put("optionId", "");
    				map.put("optionName", "");
    				retlist.add(map);	
    			}else if(i>0){
    				Map<String,Object> map=new HashMap<String,Object>();
    				map.put("num", list.get(i));
    				map.put("optionId", list_option.get(i-1).getOptionlistId());
    				map.put("optionName",list_option.get(i-1).getOptionName());
    				retlist.add(map);	
    			}
    		}
    	}
    	return retlist;
    }
    
    public List<Map<String,Object>> getoptionByInt(List<Integer> list,List<OptionBean> list_option){
    	List<Map<String,Object>> retlist=new ArrayList<Map<String,Object>>();
    	if(list!=null&&list.size()>1){
    		for(int i=0;i<list.size();i++){
    			if(i==0){
    				Map<String,Object> map=new HashMap<String,Object>();
    				map.put("num", list.get(i));
    				map.put("optionId", "");
    				map.put("optionName", "");
    				retlist.add(map);	
    			}else if(i>0){
    				Map<String,Object> map=new HashMap<String,Object>();
    				map.put("num", list.get(i));
    				map.put("optionId", list_option.get(i-1).getOptionlistId());
    				map.put("optionName",list_option.get(i-1).getOptionName());
    				retlist.add(map);	
    			}
    		}
    	}
    	return retlist;
    }
    
    
    // //新增意向统计,当日统计
    @RequestMapping(value = "/testdaydata")
    @ResponseBody
    public Map<String,Object> testdaydata(HttpServletRequest request){
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	try {
		List<String> item=new ArrayList<String>();
		List<Object> returnList=new ArrayList<Object>();
		List<Object> List_object=new ArrayList<Object>();
		Map<String,Object>  map_object=new HashMap<String,Object>();
   	    Map<String,List<Integer>> map2=new HashMap<String,List<Integer>>();
   	    List<Integer> listcount=new ArrayList<Integer>();
		List<String> grouplist=new ArrayList<String>();
    		ShiroUser user = ShiroUtil.getUser();
    		String  dDateType = request.getParameter("dDateType");	
    		String  groupIds = request.getParameter("groupIds");	
    		TsmReportWillBean bean=new TsmReportWillBean();
			bean.setOrgId(user.getOrgId());
        	if (StringUtils.isNotEmpty(groupIds)) {
       		    String[] groupIdstr = groupIds.split(",");
				List<String> owaList  = Arrays.asList(groupIdstr);
				bean.setGroupIds(owaList);
        	}else{
    			List<TeamGroupBean> teamGroups = mainService.queryManageGroupList(user.getAccount(),user.getOrgId());

    			if(teamGroups!=null&&teamGroups.size()>0){
    				for(TeamGroupBean teamGroupBean : teamGroups){
    					grouplist.add(teamGroupBean.getGroupId());
    					bean.setGroupIds(grouplist);
    				}
    			}
//    	    	List<String> childGroupIds = tsmTeamGroupService.findAllSonGroupIds(user.getOrgId(), groupId);
//    	    	childGroupIds.add(groupId);
//    	    	bean.setGroupIds(childGroupIds);
        	}
        	
        	
        	
        	
        //  发送时间
        	bean.setStartDate(getStartDateStr(Integer.parseInt("1")));
        	bean.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
        	   	
        	
        	//查询单位下所有的销售进程
            List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
			 if(list_option!=null&&list_option.size()>0){
				 for(OptionBean option:list_option){
					 item.add(option.getOptionName()); 
				 }
			 }
            
            if(list_option!=null && list_option.size()>0){
              List<TsmReportWillBean> datas=newWillService.findAllUser(bean);//数据条数	

              //统计所有新增销售进程
              List<TsmReportWillBean> list1=newWillService.findNewWillAllByAccount_new(bean);
              //统计所有销售进程进阶
              List<TsmReportWillBean> list3=newWillService.findNewWillAllByAccount_new_3(bean);
              //统计所有新增意向数
              List<TsmReportWillBean> list2=newWillService.findNewWillAllByAccount_new_2(bean);
              List<TsmReportWillBean> list=new ArrayList<TsmReportWillBean>();
              
              List<TsmReportWillBean> list4=new ArrayList<TsmReportWillBean>();
              
              if(list1!=null && list1.size()>0){
            	  for(int i=0;i<list1.size();i++){
                      if(list3!=null && list3.size()>0){
                    	  for(TsmReportWillBean beans3: list3){
                    		  if((list1.get(i).getUserAccount()==beans3.getUserAccount()||list1.get(i).getUserAccount().equals(beans3.getUserAccount())
                    			  &&(list1.get(i).getOptionlistId()==beans3.getOptionlistId()||list1.get(i).getOptionlistId().equals(beans3.getOptionlistId())))){
                    			  list1.get(i).setOptionSum(list1.get(i).getOptionSum()+beans3.getOptionSum());
                    			  list4.add(beans3);
                    		  }
                    		           	
                    	  }
                      }          	
            	  }
            
              }
              
              if(list1!=null && list1.size()>0){
            	  for(TsmReportWillBean beans: list1){
            		  list.add(beans);           	
            	  }
              }
              
              if(list3!=null && list3.size()>0){
            	  for(TsmReportWillBean beans3: list3){
                      if(list4!=null && list4.size()>0){
                    	  for(TsmReportWillBean beans4: list4){
                    		  if((beans3.getUserAccount()!=beans4.getUserAccount()||!beans3.getUserAccount().equals(beans4.getUserAccount())
                        			  &&(beans3.getOptionlistId()!=beans4.getOptionlistId()||!beans3.getOptionlistId().equals(beans4.getOptionlistId())))){
                        		  list.add(beans3); 	  
                    		  }
          	
                    	  }
                      }else{
                    	  list.add(beans3); 
                      }
            	        	
            	  }
              }
//              }else{
//                  if(list3!=null && list3.size()>0){
//                	  for(TsmReportWillBean beans3: list3){
//                           		  list.add(beans3); 	                 	        	
//                	  }
//                  }
//              }
              
              if(list2!=null && list2.size()>0){
            	  for(TsmReportWillBean beans: list2){
            		  list.add(beans);
            	  }
              }
              
//	          List<TsmReportWillBean> list=	newWillService.findNewWillAllByAccount(bean);//数据
              if(datas!=null&& datas.size()>0){
            	     for(TsmReportWillBean s:datas){ //用户
            	    	 Map<String,List<String>> map=new HashMap<String,List<String>>();

            	    	 Map<String,String> map_new=new HashMap<String,String>();
            	    	 List<String> maplist =new ArrayList<String>();
//            	    	 bean.setUserAccount(s);

            	    	 
    	    			 if(list_option!=null&&list_option.size()>0){
//                	    	 List<String> countValue=new ArrayList<String>();
    	    				 for(OptionBean option:list_option){//遍历销售进程参数
            	    	       for(TsmReportWillBean newbean:list){ //遍历数据库数据
            	    	    	   if(newbean.getType()==1){
            	    		 //根据时间排
            	    		 if(s.getUserAccount()==newbean.getUserAccount()||s.getUserAccount().equals(newbean.getUserAccount())){ 

            	    			 if(newbean.getOptionlistId()==option.getOptionlistId()||newbean.getOptionlistId().equals(option.getOptionlistId())){   
                	    			    map_new.put(option.getOptionlistId(),String.valueOf(newbean.getOptionSum())); 


            	    					 }
            	    					 }
            	    	    	        }
            	    				 }

            	    			 } 

            	    		 }
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 int new_will_sum=0;
    	    				 for(TsmReportWillBean newbean:list){ 
    	    					 if(newbean.getType()==0 &&( s.getUserAccount()==newbean.getUserAccount()||s.getUserAccount().equals(newbean.getUserAccount()))){
    	    						 new_will_sum=newbean.getNewWillSum();
    	    					 }
    	    				 }
    	    				 maplist.add(String.valueOf(new_will_sum));
    	    				 for(OptionBean option:list_option){
    	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
    	    						 maplist.add(map_new.get(option.getOptionlistId()));
    	    					 }else{
    	    						 maplist.add("0");
    	    					 }
    	    				 }
    	    			 }
    	    	    	 

    	    			 Map<String,Object> map_new2=new HashMap<String,Object>();   	    			 	 
    	    			 map_new2.put("userAccount", s.getUserAccount());
    	    			 map_new2.put("userName", s.getUserName());
    	    			 map_new2.put("groupName", s.getGroupName());
    	    			 List<Map<String,Object>> maplists= getoptionByString(maplist, list_option);
    	    			 map_new2.put("returnlist", maplists);
    	    	    	 returnList.add(map_new2);
//    	    			 }
    	    	    	 if(listcount!=null&&listcount.size()>0){
    	    	    		 for(int i=0;i<maplist.size();i++){
    	    	    	    	 listcount.set(i, Integer.valueOf(maplist.get(i))+Integer.valueOf(listcount.get(i))) ;
    	    	    		 }
    	    	    	 }else{
    	    	    		 for(String st: maplist){
    	    	    	    	 listcount.add(Integer.valueOf(st)); 
    	    	    		 } 
    	    	    	 }

            	}
            	}
                }
			     	
			 Map<String,Object> map_new3=new HashMap<String,Object>();
			 map_new3.put("userAccount", "");
			 map_new3.put("userName", "合计");
			 map_new3.put("groupName", "");
			 List<Map<String,Object>> maplists= getoptionByInt(listcount, list_option);
			 map_new3.put("returnlist", maplists);
	    	 returnList.add(map_new3);
            returnMap.put("list", returnList);
            returnMap.put("item", item);
            returnMap.put("status", true);
            returnMap.put("msg", "查询成功！");

    	} catch (Exception e) {
//            returnMap.put("status", false);
//            returnMap.put("msg", e.getMessage());
//            return returnMap;
    		e.printStackTrace();
    	}
    	
	      return returnMap;
    }
    
    
    //新增意向统计,历史
    @RequestMapping(value = "/oldDaydata")
    public String newWillOldDaydata(ModelMap modelMap,String dataIndex,String dataName,Integer index,String fromDateStr,String toDateStr,String timeType,String  account,String processId){
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	try {
		List<String> item=new ArrayList<String>();
		List<Object> returnList=new ArrayList<Object>();
   	    Map<String,List<Integer>> map2=new HashMap<String,List<Integer>>();
   	    List<Integer> listcount=new ArrayList<Integer>();
    		ShiroUser user = ShiroUtil.getUser();
    		
	    	TsmReportWillBean bean=new TsmReportWillBean();
			bean.setOrgId(user.getOrgId());
			bean.setUserAccount(account);
			int isnull=0;
            List<String> datasabc=newWillService.findNewWilldate2(bean);
    		
        	Date fromDate= null;
        	Date toDate=null;
        	
        	if(StringUtils.isBlank(dataIndex)) dataIndex="line1";
        	
        	if(StringUtils.isBlank(dataName))dataName="呼出总数";
        	
        	if(index==null) index=0;
        	
        	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
        	
        	fromDate = array[0];
        	toDate = array[1];
        	
        	Date[] toWeekArray = DateSubUtil.toWeek();
	    	Date[] toMonthArray = DateSubUtil.toMonth();
        	
	    	if(StringUtils.isBlank(fromDateStr)||StringUtils.isBlank(toDateStr)){
				fromDateStr = DateUtil.getDateDay(fromDate);
				toDateStr = DateUtil.getDateDay(toDate);
			}
    				
            if(datasabc!=null&&datasabc.size()>0){
            	Date initdate=DateUtil.parseDate(datasabc.get(0));
            	if((initdate.after(fromDate)||initdate.equals(fromDate))&&(initdate.before(toDate)||initdate.equals(toDate))){
            		//initdate-toDate之间 
                    //  发送时间
                	bean.setStartDate(DateUtil.formatDate(DateUtil.dateBegin(initdate), DateUtil.DATE_DAY));
                	bean.setEndDate(DateUtil.formatDate(DateUtil.dateEnd(toDate), DateUtil.DATE_DAY));

            	}else if(initdate.after(toDate)){
            		//null
            		isnull=1;
            	}else if(initdate.before(fromDate)){
                	bean.setStartDate(DateUtil.formatDate(DateUtil.dateBegin(fromDate), DateUtil.DATE_DAY));
                	bean.setEndDate(DateUtil.formatDate(DateUtil.dateEnd(toDate), DateUtil.DATE_DAY));	
            	}
            	}
            

        	//查询单位下所有的销售进程
            List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
			 if(list_option!=null&&list_option.size()>0){
				 for(OptionBean option:list_option){
					 item.add(option.getOptionName()); 
				 }
			 }
            
            if(list_option!=null && list_option.size()>0){
            List<String> datas=new ArrayList<String>();
            if(isnull==0){
            	
                if(datasabc!=null&&datasabc.size()>0){
                	Date initdate=DateUtil.parseDate(datasabc.get(0));
                	if((initdate.after(fromDate)||initdate.equals(fromDate))&&(initdate.before(toDate)||initdate.equals(toDate))){
                		//initdate-toDate之间 
                        //  发送时间
                		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
                        datas=	getAlldate(format.format(initdate),toDateStr);	

                	}else{
                        datas=	getAlldate(fromDateStr,toDateStr);	
                	}
                	
               }
            	
            	

            }
            
            List<TsmReportWillBean> list=	newWillService.findNewWillBydate(bean);
            int ifnull=0;
            if(datas!=null&& datas.size()>0){
//            	 if(list!=null&& list.size()>0){
            	     for(String s:datas){ //遍历时间
            	    	 Map<String,List<String>> map=new HashMap<String,List<String>>();

            	    	 Map<String,String> map_new=new HashMap<String,String>();
            	    	 List<String> maplist =new ArrayList<String>();
            	    	 
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 for(OptionBean option:list_option){//遍历销售进程参数
            	    	       for(TsmReportWillBean newbean:list){ //遍历数据库数据
            	    	    	   if(newbean.getType()==1){
            	    		 //根据时间排
            	    		 if(s==new SimpleDateFormat("yyyy-MM-dd").format(newbean.getInputeDate())||s.equals(new SimpleDateFormat("yyyy-MM-dd").format(newbean.getInputeDate()))){         
            	    			if(newbean.getOptionlistId()==option.getOptionlistId()||newbean.getOptionlistId().equals(option.getOptionlistId())){
            	    				map_new.put(option.getOptionlistId(),String.valueOf(newbean.getOptionSum()));
            	    					 }
            	    					 }
            	    					 
            	    				 }
            	    	       }

            	    			 } 

            	    		 }
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 int new_will_sum=0;
    	    				 for(TsmReportWillBean newbean:list){ 
    	    					 if(newbean.getType()==0 &&(s==new SimpleDateFormat("yyyy-MM-dd").format(newbean.getInputeDate())||s.equals(new SimpleDateFormat("yyyy-MM-dd").format(newbean.getInputeDate())))){
    	    						 new_will_sum=newbean.getNewWillSum();
    	    					 }
    	    				 }
    	    				 maplist.add(String.valueOf(new_will_sum));
    	    				 for(OptionBean option:list_option){
    	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
    	    						 maplist.add(map_new.get(option.getOptionlistId()));
    	    					 }else{
    	    						 maplist.add("0");
    	    					 }
    	    				 }
    	    			 }
//    	    			 map.put(s, maplist) ;
    	    			 Map<String,Object> map_new2=new HashMap<String,Object>();
    	    			 map_new2.put("countvalue", s);
    	    			 List<Map<String,Object>> maplists= getoptionByString(maplist, list_option);
    	    			 map_new2.put("returnlist", maplists);
    	    	    	 returnList.add(map_new2);
    	    	    	 if(listcount!=null&&listcount.size()>0){
    	    	    		 for(int i=0;i<maplist.size();i++){
    	    	    	    	 listcount.set(i, Integer.valueOf(maplist.get(i))+Integer.valueOf(listcount.get(i))) ;
    	    	    		 }
    	    	    	 }else{
    	    	    		 for(String st: maplist){
    	    	    	    	 listcount.add(Integer.valueOf(st)); 
    	    	    		 } 
    	    	    	 }

            	}
//            	}
            	}
                }
            if(listcount!=null&&listcount.size()>0){
			 Map<String,Object> map_new3=new HashMap<String,Object>();
			 map_new3.put("countvalue", "合计");
			 List<Map<String,Object>> maplists= getoptionByInt(listcount, list_option);
			 map_new3.put("returnlist", maplists);
	    	 returnList.add(map_new3);
             }
//	    	map2.put("合计", listcount);
//	    	returnList.add(map2);
            
        	//查询单位下所有的销售进程
            List<Object> processlist=new ArrayList<Object>();
    		Map<String,String> maps2=new HashMap<String,String>();
    		maps2.put("processId", "1234567890");
    		maps2.put("process", "新增意向数");
    		processlist.add(maps2);
	            if(list_option!=null && list_option.size()>0){
	            	for(OptionBean optionBean:list_option){
	            		Map<String,String> maps=new HashMap<String,String>();
	            		maps.put("processId", optionBean.getOptionlistId());
	            		maps.put("process", optionBean.getOptionName());
	            		processlist.add(maps);
	            	}
	            }
	         
        	if(StringUtils.isBlank(timeType)) timeType="0";
        	String process="";
            if(processId==null||"".equals(processId)){
//            	processId=list_option.get(0).getOptionlistId();
//            	process=list_option.get(0).getOptionName();
//        		maps2.put("processId", "1234567890");
//        		maps2.put("process", "新增意向");
            	processId="1234567890";
            	process="新增意向数";
            }else{
            	for(OptionBean optionBean:list_option){
                 if(processId==optionBean.getOptionlistId()||processId.equals(optionBean.getOptionlistId())){
                	 process=optionBean.getOptionName();
                 }
 
            	if(processId=="1234567890"||"1234567890".equals(processId)){
            		process="新增意向数";
                }
            	}
            }
        	modelMap.put("processIds",processId);
        	modelMap.put("process",process);
        	modelMap.put("timeType", timeType);
        	modelMap.put("list", returnList);
        	modelMap.put("item", item);
        	modelMap.put("toMonthArray", toMonthArray);
        	modelMap.put("toWeekArray", toWeekArray);
        	modelMap.put("fromDateStr", fromDateStr);
        	modelMap.put("toDateStr", toDateStr);
        	
        	modelMap.put("index", index);
        	modelMap.put("dataIndex",dataIndex);
        	modelMap.put("dataName",dataName);
        	
        	modelMap.put("processlist", processlist);
        	
        	modelMap.put("timeElngth", tsmReportCallInfoService.getTimeElngth(user.getOrgId()));
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
        return "report/willCustStatis/newWill_oldday";
    } 
    
    
    //新增意向统计,历史统计，根据部门统计，统计所有部门
    @RequestMapping(value = "/oldDaydataByGroup")
    public String oldDaydataByGroup(ModelMap modelMap,String dataIndex,String dataName,Integer index,String fromDateStr,String toDateStr,String timeType){
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	try {
		List<String> item=new ArrayList<String>();
		List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
		List<Object> List_object=new ArrayList<Object>();
		Map<String,Object>  map_object=new HashMap<String,Object>();
   	    Map<String,List<Integer>> map2=new HashMap<String,List<Integer>>();
   	    List<Integer> listcount=new ArrayList<Integer>();
    		ShiroUser user = ShiroUtil.getUser();
    		
    		
        	Date fromDate= null;
        	Date toDate=null;
        	
        	if(StringUtils.isBlank(dataIndex)) dataIndex="line1";
        	
        	if(StringUtils.isBlank(dataName))dataName="呼出总数";
        	
        	if(index==null) index=0;
        	
        	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
        	
        	fromDate = array[0];
        	toDate = array[1];
        	
        	Date[] toWeekArray = DateSubUtil.toWeek();
	    	Date[] toMonthArray = DateSubUtil.toMonth();
        	
	    	if(StringUtils.isBlank(fromDateStr)||StringUtils.isBlank(toDateStr)){
				fromDateStr = DateUtil.getDateDay(fromDate);
				toDateStr = DateUtil.getDateDay(toDate);
			}
    				
	    	TsmReportWillBean bean=new TsmReportWillBean();
			bean.setOrgId(user.getOrgId());

        //  发送时间
        	bean.setStartDate(DateUtil.formatDate(DateUtil.dateBegin(fromDate), DateUtil.DATE_DAY));
        	bean.setEndDate(DateUtil.formatDate(DateUtil.dateEnd(toDate), DateUtil.DATE_DAY));
    		        	
        	List<String> shareGroupIds =new ArrayList<String>();
        	Map<String,String>map_group = new HashMap<String, String>();
        	map_group.put("orgId",user.getOrgId());
        	map_group.put("userId",user.getId());
		 	OrgGroupUser groupUser = orgGroupUserMapper.findGroupId(map_group);
		 	
        	//查询单位下所有的销售进程
            List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
			 if(list_option!=null&&list_option.size()>0){
				 for(OptionBean option:list_option){
					 item.add(option.getOptionName()); 
				 }
			 }
            
            if(list_option!=null && list_option.size()>0){
    			if(groupUser != null){
    	    		Map<String,String>map1 = new HashMap<String, String>();
    	    		map1.put("account",groupUser.getMemberAcc());
    	    		map1.put("orgId", user.getOrgId());
    	    		//查询权限下分组

    	    List<OrgGroupUser> datas = orgGroupUserMapper.findShareGroupIdBySall(map1);
            List<TsmReportWillBean> list=	newWillService.findNewWillByGroup(bean);
            if(datas!=null&& datas.size()>0){
//            	 if(list!=null&& list.size()>0){
            	     for(OrgGroupUser s:datas){ //遍历部门
            	    	 Map<String,List<String>> map=new HashMap<String,List<String>>();

            	    	 Map<String,String> map_new=new HashMap<String,String>();
            	    	 List<String> maplist =new ArrayList<String>();
    	    			 if(list_option!=null&&list_option.size()>0){
//                	    	 List<String> countValue=new ArrayList<String>();
    	    				 for(OptionBean option:list_option){//遍历销售进程参数
            	    	       for(TsmReportWillBean newbean:list){ //遍历数据库数据
            	    	    	   if(newbean.getType()==1){
            	    		 //根据时间排
            	    		 if(s.getGroupId()==newbean.getGroupId()||s.getGroupId().equals(newbean.getGroupId())){
            
            	    					 if(newbean.getOptionlistId()==option.getOptionlistId()||newbean.getOptionlistId().equals(option.getOptionlistId())){
//            	    						 countValue.add(newbean.getNum()) ;//数据库如果有值，传值
            	    						 map_new.put(option.getOptionlistId(),String.valueOf(newbean.getOptionSum()));
            	    					 }
            	    					 }
            	    					 
            	    				 }
            	    	          }
            	    			 } 

            	    		 }
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 int new_will_sum=0;
    	    				 for(TsmReportWillBean newbean:list){ 
    	    					 if(newbean.getType()==0 &&(s.getGroupId()==newbean.getGroupId()||s.getGroupId().equals(newbean.getGroupId()))){
    	    						 new_will_sum=newbean.getNewWillSum();
    	    					 }
    	    				 }
    	    				 maplist.add(String.valueOf(new_will_sum));
    	    				 for(OptionBean option:list_option){
    	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
    	    						 maplist.add(map_new.get(option.getOptionlistId()));
    	    					 }else{
    	    						 maplist.add("0");
    	    					 }
    	    				 }
    	    			 }
//    	    			 map.put(s.getGroupId()+","+s.getGroupName(), maplist) ;
//    	    	    	 returnList.add(map);
//    	    	    	 
    	    			 Map<String,Object> map_new2=new HashMap<String,Object>();
    	    			 map_new2.put("groupId", s.getGroupId());
    	    			 map_new2.put("groupName", s.getGroupName());
    	    			 map_new2.put("returnlist", maplist);
    	    	    	 returnList.add(map_new2);
    	    	    	 if(listcount!=null&&listcount.size()>0){
    	    	    		 for(int i=0;i<maplist.size();i++){
    	    	    	    	 listcount.set(i, Integer.valueOf(maplist.get(i))+Integer.valueOf(listcount.get(i))) ;
    	    	    		 }
    	    	    	 }else{
    	    	    		 for(String st: maplist){
    	    	    	    	 listcount.add(Integer.valueOf(st)); 
    	    	    		 } 
    	    	    	 }

            	}
//            	}
            	}
                }
			    }
//			 Map<String,Object> map_new3=new HashMap<String,Object>();
//			 map_new3.put("groupId", "");
//			 map_new3.put("groupName", "合计");
//			 map_new3.put("returnlist", listcount);
//	    	 returnList.add(map_new3);
            processTopGroupValue(user.getOrgId(),returnList);
	        if(StringUtils.isBlank(timeType)) timeType="0";
	        modelMap.put("timeType", timeType);
	        		if(returnList!=null&&returnList.size()>0){
	        			for(Map<String,Object> maps:returnList){
	        				 List<Map<String,Object>> maplists= getoptionByobject((List<Object>) maps.get("returnlist"), list_option);
	        				 maps.put("returnlist", maplists);
	        			}
	        		}
			
	        modelMap.put("list", returnList);
	        modelMap.put("item", item);
	        modelMap.put("toMonthArray", toMonthArray);
	        modelMap.put("toWeekArray", toWeekArray);
	        modelMap.put("fromDateStr", fromDateStr);
	        modelMap.put("toDateStr", toDateStr);
	        	
	        modelMap.put("index", index);
	        modelMap.put("dataIndex",dataIndex);
	        modelMap.put("dataName",dataName);
	        	
	        modelMap.put("timeElngth", tsmReportCallInfoService.getTimeElngth(user.getOrgId()));
	    	} catch (Exception e) {
	    		logger.error(e.getMessage(),e);
	    	}
	        return "report/willCustStatis/newWill_group";
    }
    
    //新增意向统计,历史统计，统计部门下用户销售进程
    @RequestMapping(value = "/oldDaydataByUser")
    public String oldDaydataByUser(ModelMap modelMap,String dataIndex,String dataName,Integer index,String fromDateStr,String toDateStr,String timeType,String groupId){
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	try {
		List<String> item=new ArrayList<String>();
		List<Object> returnList=new ArrayList<Object>();
		List<Object> List_object=new ArrayList<Object>();
		Map<String,Object>  map_object=new HashMap<String,Object>();
   	    Map<String,List<Integer>> map2=new HashMap<String,List<Integer>>();
   	    List<Integer> listcount=new ArrayList<Integer>();
    		ShiroUser user = ShiroUtil.getUser();
    		
        	Date fromDate= null;
        	Date toDate=null;
        	
        	if(StringUtils.isBlank(dataIndex)) dataIndex="line1";
        	
        	if(StringUtils.isBlank(dataName))dataName="呼出总数";
        	
        	if(index==null) index=0;
        	
        	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
        	
        	fromDate = array[0];
        	toDate = array[1];
        	
        	Date[] toWeekArray = DateSubUtil.toWeek();
	    	Date[] toMonthArray = DateSubUtil.toMonth();
        	
	    	if(StringUtils.isBlank(fromDateStr)||StringUtils.isBlank(toDateStr)){
				fromDateStr = DateUtil.getDateDay(fromDate);
				toDateStr = DateUtil.getDateDay(toDate);
			}
    				
	    	TsmReportWillBean bean=new TsmReportWillBean();
			bean.setOrgId(user.getOrgId());
			
	    	List<String> childGroupIds = tsmTeamGroupService.findAllSonGroupIds(user.getOrgId(), groupId);
	    	childGroupIds.add(groupId);
	    	bean.setGroupIds(childGroupIds);
//	    	List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findByGroupAndDate(user.getOrgId(), childGroupIds.toArray(new String[childGroupIds.size()]), fromDate, toDate, orderKey);
//			bean.setGroupId(groupId);

        //  发送时间
        	bean.setStartDate(DateUtil.formatDate(DateUtil.dateBegin(fromDate), DateUtil.DATE_DAY));
        	bean.setEndDate(DateUtil.formatDate(DateUtil.dateEnd(toDate), DateUtil.DATE_DAY));
    		  		       	   	
        	
        	//查询单位下所有的销售进程
            List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
			 if(list_option!=null&&list_option.size()>0){
				 for(OptionBean option:list_option){
					 item.add(option.getOptionName()); 
				 }
			 }
            
            if(list_option!=null && list_option.size()>0){

            List<TsmReportWillBean> datas=new ArrayList<TsmReportWillBean>();
//          datas=newWillService.findNewWillUserByGroup(bean);	
            datas=newWillService.findAllUser(bean);//数据条数
            List<TsmReportWillBean> list=	newWillService.findNewWillUserByGroupAndUser(bean);
            if(datas!=null&& datas.size()>0){
//            	 if(list!=null&& list.size()>0){
            	     for(TsmReportWillBean s:datas){ //遍历部门
            	    	 Map<String,List<String>> map=new HashMap<String,List<String>>();

            	    	 Map<String,String> map_new=new HashMap<String,String>();
            	    	 List<String> maplist =new ArrayList<String>();
    	    			 if(list_option!=null&&list_option.size()>0){
//                	    	 List<String> countValue=new ArrayList<String>();
    	    				 for(OptionBean option:list_option){//遍历销售进程参数
            	    	       for(TsmReportWillBean newbean:list){ //遍历数据库数据
            	    	    	   if(newbean.getType()==1){
            	    		 //根据时间排
            	    		 if(s.getUserAccount()==newbean.getUserAccount()||s.getUserAccount().equals(newbean.getUserAccount())){
            
            	    					 if(newbean.getOptionlistId()==option.getOptionlistId()||newbean.getOptionlistId().equals(option.getOptionlistId())){
//            	    						 countValue.add(newbean.getNum()) ;//数据库如果有值，传值
            	    						 map_new.put(option.getOptionlistId(),String.valueOf(newbean.getOptionSum()));
            	    					 }
            	    					 }
            	    					 
            	    				 }
            	    	       }
            	    			 } 

            	    		 }
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 int new_will_sum=0;
    	    				 for(TsmReportWillBean newbean:list){ 
    	    					 if(newbean.getType()==0 &&( s.getUserAccount()==newbean.getUserAccount()||s.getUserAccount().equals(newbean.getUserAccount()))){
    	    						 new_will_sum=newbean.getNewWillSum();
    	    					 }
    	    				 }
    	    				 maplist.add(String.valueOf(new_will_sum));
    	    				 for(OptionBean option:list_option){
    	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
    	    						 maplist.add(map_new.get(option.getOptionlistId()));
    	    					 }else{
    	    						 maplist.add("0");
    	    					 }
    	    				 }
    	    			 }
    	    			 Map<String,Object> map_new2=new HashMap<String,Object>();
    	    			 map_new2.put("groupId", s.getUserAccount());
    	    			 map_new2.put("groupName",s.getUserName());
    	    			 map_new2.put("userAccount", s.getUserAccount());
    	    			 map_new2.put("userName", s.getUserName());
                         List<Map<String,Object>> maplists= getoptionByString(maplist, list_option);
    	    			 map_new2.put("returnlist", maplists);
//    	    			 map_new2.put("returnlist", maplist);
    	    	    	 returnList.add(map_new2);
    	    	    	 
    	    	    	 if(listcount!=null&&listcount.size()>0){
    	    	    		 for(int i=0;i<maplist.size();i++){
    	    	    	    	 listcount.set(i, Integer.valueOf(maplist.get(i))+Integer.valueOf(listcount.get(i))) ;
    	    	    		 }
    	    	    	 }else{
    	    	    		 for(String st: maplist){
    	    	    	    	 listcount.add(Integer.valueOf(st)); 
    	    	    		 } 
    	    	    	 }

            	}
            	}
            	}
//                }
			    
//			 Map<String,Object> map_new3=new HashMap<String,Object>();
//			 map_new3.put("userAccount", "");
//			 map_new3.put("userName", "合计");
//			 map_new3.put("returnlist", listcount);
//	    	 returnList.add(map_new3);
	    	 
	        if(StringUtils.isBlank(timeType)) timeType="0";
	        modelMap.put("timeType", timeType);
	        modelMap.put("list", returnList);
	        modelMap.put("item", item);
	        modelMap.put("toMonthArray", toMonthArray);
	        modelMap.put("toWeekArray", toWeekArray);
	        modelMap.put("fromDateStr", fromDateStr);
	        modelMap.put("toDateStr", toDateStr);
	        	
	        modelMap.put("index", index);
	        modelMap.put("dataIndex",dataIndex);
	        modelMap.put("dataName",dataName);
	        modelMap.put("groupId",groupId);
	        	
	        modelMap.put("timeElngth", tsmReportCallInfoService.getTimeElngth(user.getOrgId()));
	    	} catch (Exception e) {
	    		logger.error(e.getMessage(),e);
	    	}
	        return "report/willCustStatis/newWill_user";
    }
    
    
    //新增意向统计,根据个人账号和时间来查
    @RequestMapping(value = "/findUserdataBygroup")
    public String findUserdataBygroup(ModelMap modelMap,String dataIndex,String dataName,Integer index,String fromDateStr,String toDateStr,String timeType,String  account){
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	try {
		List<String> item=new ArrayList<String>();
		List<Object> returnList=new ArrayList<Object>();
   	    Map<String,List<Integer>> map2=new HashMap<String,List<Integer>>();
   	    List<Integer> listcount=new ArrayList<Integer>();
    		ShiroUser user = ShiroUtil.getUser();
    		
    		
        	Date fromDate= null;
        	Date toDate=null;
        	
        	if(StringUtils.isBlank(dataIndex)) dataIndex="line1";
        	
        	if(StringUtils.isBlank(dataName))dataName="呼出总数";
        	
        	if(index==null) index=0;
        	
        	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
        	
        	fromDate = array[0];
        	toDate = array[1];
        	
        	Date[] toWeekArray = DateSubUtil.toWeek();
	    	Date[] toMonthArray = DateSubUtil.toMonth();
        	
	    	if(StringUtils.isBlank(fromDateStr)||StringUtils.isBlank(toDateStr)){
				fromDateStr = DateUtil.getDateDay(fromDate);
				toDateStr = DateUtil.getDateDay(toDate);
			}
    				
	    	TsmReportWillBean bean=new TsmReportWillBean();
			bean.setOrgId(user.getOrgId());
			bean.setUserAccount(account);
			int isnull=0;
            List<String> datasabc=newWillService.findNewWilldate2(bean);

            if(datasabc!=null&&datasabc.size()>0){
            	Date initdate=DateUtil.parseDate(datasabc.get(0));
            	if((initdate.after(fromDate)||initdate.equals(fromDate))&&(initdate.before(toDate)||initdate.equals(toDate))){
            		//initdate-toDate之间 
                    //  发送时间
                	bean.setStartDate(DateUtil.formatDate(DateUtil.dateBegin(initdate), DateUtil.DATE_DAY));
                	bean.setEndDate(DateUtil.formatDate(DateUtil.dateEnd(toDate), DateUtil.DATE_DAY));

            	}else if(initdate.after(toDate)){
            		//null
            		isnull=1;
            	}else if(initdate.before(fromDate)){
                	bean.setStartDate(DateUtil.formatDate(DateUtil.dateBegin(fromDate), DateUtil.DATE_DAY));
                	bean.setEndDate(DateUtil.formatDate(DateUtil.dateEnd(toDate), DateUtil.DATE_DAY));	
            	}
            	}
        	//查询单位下所有的销售进程
            List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
			 if(list_option!=null&&list_option.size()>0){
				 for(OptionBean option:list_option){
					 item.add(option.getOptionName()); 
				 }
			 }
            
            if(list_option!=null && list_option.size()>0){
//            List<String> datas=newWillService.findNewWilldate(bean);
//            List<NewWillBean> list=	newWillService.findNewWillCountDataBydate(bean);
//            List<String> datas=newWillService.findNewWilldate2(bean);
                List<String> datas=new ArrayList<String>();
                if(isnull==0){
                	
                    if(datasabc!=null&&datasabc.size()>0){
                    	Date initdate=DateUtil.parseDate(datasabc.get(0));
                    	if((initdate.after(fromDate)||initdate.equals(fromDate))&&(initdate.before(toDate)||initdate.equals(toDate))){
                    		//initdate-toDate之间 
                            //  发送时间
                    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
                            datas=	getAlldate(format.format(initdate),toDateStr);	

                    	}else{
                            datas=	getAlldate(fromDateStr,toDateStr);	
                    	}
                    	
                   }                	               
                }
              List<TsmReportWillBean> list=	newWillService.findNewWillBydate(bean);

            if(datas!=null&& datas.size()>0){
//            	 if(list!=null&& list.size()>0){
            	     for(String s:datas){ //遍历时间
            	    	 Map<String,List<String>> map=new HashMap<String,List<String>>();

            	    	 Map<String,String> map_new=new HashMap<String,String>();
            	    	 List<String> maplist =new ArrayList<String>();
            	    	 
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 for(OptionBean option:list_option){//遍历销售进程参数
            	    	       for(TsmReportWillBean newbean:list){ //遍历数据库数据
            	    	    	   if(newbean.getType()==1){
            	    		 //根据时间排
            	    		 if(s==new SimpleDateFormat("yyyy-MM-dd").format(newbean.getInputeDate())||s.equals(new SimpleDateFormat("yyyy-MM-dd").format(newbean.getInputeDate()))){         
            	    					 if(newbean.getOptionlistId()==option.getOptionlistId()||newbean.getOptionlistId().equals(option.getOptionlistId())){
            	    						 map_new.put(option.getOptionlistId(),String.valueOf(newbean.getOptionSum()));
            	    					 }
            	    					 }
            	    					 
            	    				 }
            	    	           }
            	    			 } 

            	    		 }
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 int new_will_sum=0;
    	    				 for(TsmReportWillBean newbean:list){ 
    	    					 if(newbean.getType()==0 &&(s==new SimpleDateFormat("yyyy-MM-dd").format(newbean.getInputeDate())||s.equals(new SimpleDateFormat("yyyy-MM-dd").format(newbean.getInputeDate())))){
    	    						 new_will_sum=newbean.getNewWillSum();
    	    					 }
    	    				 }
    	    				 maplist.add(String.valueOf(new_will_sum));
    	    				 for(OptionBean option:list_option){
    	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
    	    						 maplist.add(map_new.get(option.getOptionlistId()));
    	    					 }else{
    	    						 maplist.add("0");
    	    					 }
    	    				 }
    	    			 }
//    	    			 map.put(s, maplist) ;
    	    			 Map<String,Object> map_new2=new HashMap<String,Object>();
    	    			 map_new2.put("countvalue", s);
    	    			 map_new2.put("returnlist", maplist);
    	    	    	 returnList.add(map_new2);
    	    	    	 if(listcount!=null&&listcount.size()>0){
    	    	    		 for(int i=0;i<maplist.size();i++){
    	    	    	    	 listcount.set(i, Integer.valueOf(maplist.get(i))+Integer.valueOf(listcount.get(i))) ;
    	    	    		 }
    	    	    	 }else{
    	    	    		 for(String st: maplist){
    	    	    	    	 listcount.add(Integer.valueOf(st)); 
    	    	    		 } 
    	    	    	 }

            	}
//            	}
            	}
                }
            

			 
	            if(listcount!=null&&listcount.size()>0){
	   			 Map<String,Object> map_new3=new HashMap<String,Object>();
				 map_new3.put("countvalue", "合计");
				 map_new3.put("returnlist", listcount);
		    	 returnList.add(map_new3);
	                }			 

        	if(StringUtils.isBlank(timeType)) timeType="0";
        	modelMap.put("timeType", timeType);
        	modelMap.put("list", returnList);
        	modelMap.put("item", item);
        	modelMap.put("toMonthArray", toMonthArray);
        	modelMap.put("toWeekArray", toWeekArray);
        	modelMap.put("fromDateStr", fromDateStr);
        	modelMap.put("toDateStr", toDateStr);
        	
        	modelMap.put("index", index);
        	modelMap.put("dataIndex",dataIndex);
        	modelMap.put("dataName",dataName);
        	
        	modelMap.put("timeElngth", tsmReportCallInfoService.getTimeElngth(user.getOrgId()));
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
        return "report/willCustStatis/newWill_user_detail";
    } 
    
    
    //新增意向统计,历史,图表
    @RequestMapping(value = "/oldDaydataChart")
    public String oldDaydataChart(ModelMap modelMap,String dataIndex,String dataName,Integer index,String fromDateStr,String toDateStr,String timeType,String processId){
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	try {
		List<String> item=new ArrayList<String>();
		List<Object> returnList=new ArrayList<Object>();
		List<Object> list_new=new ArrayList<Object>();
   	    Map<String,List<Integer>> map2=new HashMap<String,List<Integer>>();
   	    List<Integer> listcount=new ArrayList<Integer>();
    		ShiroUser user = ShiroUtil.getUser();
    		
    		
        	Date fromDate= null;
        	Date toDate=null;
        	
        	if(StringUtils.isBlank(dataIndex)) dataIndex="line1";
        	
        	if(StringUtils.isBlank(dataName))dataName="呼出总数";
        	
        	if(index==null) index=0;
        	
        	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
        	
        	fromDate = array[0];
        	toDate = array[1];
        	
        	Date[] toWeekArray = DateSubUtil.toWeek();
	    	Date[] toMonthArray = DateSubUtil.toMonth();
        	
	    	if(StringUtils.isBlank(fromDateStr)||StringUtils.isBlank(toDateStr)){
				fromDateStr = DateUtil.getDateDay(fromDate);
				toDateStr = DateUtil.getDateDay(toDate);
			}
    				
	    	TsmReportWillBean bean=new TsmReportWillBean();
			bean.setOrgId(user.getOrgId());
        //  发送时间
        	bean.setStartDate(DateUtil.formatDate(DateUtil.dateBegin(fromDate), DateUtil.DATE_DAY));
        	bean.setEndDate(DateUtil.formatDate(DateUtil.dateEnd(toDate), DateUtil.DATE_DAY));
        	
        	item.add("新增意向数");
        	//查询单位下所有的销售进程
            List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
			 if(list_option!=null&&list_option.size()>0){
				 for(OptionBean option:list_option){
					 item.add(option.getOptionName()); 
				 }
			 }
            
            if(list_option!=null && list_option.size()>0){
//            List<String> datas=newWillService.findNewWilldate(bean);
//            List<NewWillBean> list=	newWillService.findNewWillCountDataBydate(bean);
            List<String> datas=newWillService.findNewWilldate2(bean);
            List<TsmReportWillBean> list=	newWillService.findNewWillBydate(bean);

            if(datas!=null&& datas.size()>0){
            	 if(list!=null&& list.size()>0){
            	     for(String s:datas){ //遍历时间
            	    	 Map<String,List<String>> map=new HashMap<String,List<String>>();

            	    	 Map<String,String> map_new=new HashMap<String,String>();
            	    	 List<String> maplist =new ArrayList<String>();
            	    	 
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 for(OptionBean option:list_option){//遍历销售进程参数
            	    	       for(TsmReportWillBean newbean:list){ //遍历数据库数据
            	    	    	   if(newbean.getType()==1){
            	    		 //根据时间排
            	    		 if(s==new SimpleDateFormat("yyyy-MM-dd").format(newbean.getInputeDate())||s.equals(new SimpleDateFormat("yyyy-MM-dd").format(newbean.getInputeDate()))){         
            	    					 if(newbean.getOptionlistId()==option.getOptionlistId()||newbean.getOptionlistId().equals(option.getOptionlistId())){
            	    						 map_new.put(option.getOptionlistId(),String.valueOf(newbean.getOptionSum()));
            	    					 }
            	    					 }
            	    					 
            	    				 }
            	    	           }
            	    			 } 

            	    		 }

    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 int new_will_sum=0;
    	    				 for(TsmReportWillBean newbean:list){ 
    	    					 if(newbean.getType()==0 &&(s==new SimpleDateFormat("yyyy-MM-dd").format(newbean.getInputeDate())||s.equals(new SimpleDateFormat("yyyy-MM-dd").format(newbean.getInputeDate())))){
    	    						 new_will_sum=newbean.getNewWillSum();
    	    					 }
    	    				 }
    	    				 maplist.add(String.valueOf(new_will_sum));
    	    				 for(OptionBean option:list_option){
    	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
    	    						 maplist.add(map_new.get(option.getOptionlistId()));
    	    					 }else{
    	    						 maplist.add("0");
    	    					 }
    	    				 }
    	    			 }

//    	    			 if(list_option!=null&&list_option.size()>0){
//    	    				 int new_will_sum=0;
//    	    				 for(TsmReportWillBean newbean:list){ 
//    	    					 if(newbean.getType()==0 &&(s==new SimpleDateFormat("yyyy-MM-dd").format(newbean.getInputeDate())||s.equals(new SimpleDateFormat("yyyy-MM-dd").format(newbean.getInputeDate())))){
//    	    						 new_will_sum=newbean.getNewWillSum();
//    	    					 }
//    	    				 }
//    	    				 maplist.add(String.valueOf(new_will_sum));
//    	    				 for(OptionBean option:list_option){
//    	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
//    	    						 maplist.add(map_new.get(option.getOptionlistId()));
//    	    					 }else{
//    	    						 maplist.add("0");
//    	    					 }
//    	    				 }
//    	    			 }
//	    					Map<String,Object> maps2=new HashMap<String,Object>();
//	    					maps2.put("process", "新增意向");
//	    					maps2.put("processId", "");
//	    					maps2.put("sort", 1);
////	    					maps.put("returnlist", maplist);
//	    					maps2.put("returnlist", maplist.get(i));
//	    					maps2.put("countvalue", s);
//	    	    			list_new.add(maps2);
	    	    			
	    	    			List<OptionBean> newOption=new ArrayList<OptionBean>();
	    	    			OptionBean newbean=new OptionBean();
	    	    			newbean.setOptionName("新增意向数");
	    	    			newbean.setOptionlistId("1234567890");
	    	    			newOption.add(newbean);
	      	    			 if(list_option!=null&&list_option.size()>0){
      	    					 for(OptionBean beanss:list_option){
      	    						newOption.add(beanss);
      	    				 }
      	    			 }
	    	    			
	    	    			
      	    			 if(newOption!=null&&newOption.size()>0){
      	    					 for(int i=0;i<newOption.size();i++){
      	    					Map<String,Object> maps=new HashMap<String,Object>();
      	    					maps.put("process", newOption.get(i).getOptionName());
      	    					maps.put("processId", newOption.get(i).getOptionlistId());
      	    					maps.put("sort", i+1);
//    	    					maps.put("returnlist", maplist);
    	    					maps.put("returnlist", maplist.get(i));
    	    					maps.put("countvalue", s);
      	    	    			list_new.add(maps);
      	    				 }
      	    			 }

            	}
            	}
            	}
                }
        	modelMap.put("processIds","1234567890");
        	modelMap.put("process","新增意向数");
        	String process="";
            if(processId==null||"".equals(processId)){
//            	processId=list_option.get(0).getOptionlistId();
//            	process=list_option.get(0).getOptionName();
            	processId="1234567890";
            	process="新增意向数";
            }else{
            	for(OptionBean optionBean:list_option){
                 if(processId==optionBean.getOptionlistId()||processId.equals(optionBean.getOptionlistId())){
                	 process=optionBean.getOptionName();
                 }
 
            	}
            	if(processId=="1234567890"||"1234567890".equals(processId)){
            		process="新增意向数";
                }
            }
        	modelMap.put("processIds",processId);
        	modelMap.put("process",process);
            
        	if(StringUtils.isBlank(timeType)) timeType="0";
        	modelMap.put("timeType", timeType);
        	modelMap.put("list", JsonUtil.getJsonString(list_new));
        	modelMap.put("item", JsonUtil.getJsonString(item));
        	modelMap.put("toMonthArray", toMonthArray);
        	modelMap.put("toWeekArray", toWeekArray);
        	modelMap.put("fromDateStr", fromDateStr);
        	modelMap.put("toDateStr", toDateStr);
        	
        	modelMap.put("index", index);
        	modelMap.put("dataIndex",dataIndex);
        	modelMap.put("dataName",dataName);
        	modelMap.put("processIds",processId);
        	
        	modelMap.put("timeElngth", tsmReportCallInfoService.getTimeElngth(user.getOrgId()));
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
        return "report/willCustStatis/chart/newWill_hist_line1";
    } 

    
//    // //新增意向统计,当日统计
//    @RequestMapping(value = "/testdaydata")
//    @ResponseBody
//    public Map<String,Object> daydata(HttpServletRequest request){
//	    Map<String,Object> returnMap=new HashMap<String,Object>();
//	try {
//		List<String> item=new ArrayList<String>();
//		List<Object> returnList=new ArrayList<Object>();
//		List<Object> List_object=new ArrayList<Object>();
//		Map<String,Object>  map_object=new HashMap<String,Object>();
//   	    Map<String,List<Integer>> map2=new HashMap<String,List<Integer>>();
//   	    List<Integer> listcount=new ArrayList<Integer>();
//    		ShiroUser user = ShiroUtil.getUser();
//    		String  dDateType = request.getParameter("dDateType");	
//    		String  groupIds = request.getParameter("groupIds");	
//			NewWillBean bean=new NewWillBean();
//			bean.setOrgId(user.getOrgId());
//        	if (StringUtils.isNotEmpty(groupIds)) {
//       		    String[] groupIdstr = groupIds.split(",");
//				List<String> owaList  = Arrays.asList(groupIdstr);
//				bean.setGroupIds(owaList);
//        	}
//        //  发送时间
//        	bean.setStartDate(getStartDateStr(Integer.parseInt("1")));
//        	bean.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
//        	   	
//        	
//        	//查询单位下所有的销售进程
//            List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
//			 if(list_option!=null&&list_option.size()>0){
//				 for(OptionBean option:list_option){
//					 item.add(option.getOptionName()); 
//				 }
//			 }
//            
//            if(list_option!=null && list_option.size()>0){
//
//            List<NewWillBean> datas=new ArrayList<NewWillBean>();
////            if(groupId!=null&&!"".equals(groupId)){
//                datas=newWillService.findNewWillUserByGroupByDay(bean);	
////            }else{
////                datas=newWillService.findNewWillAllUser(user.getOrgId());	
////            }
//
//            List<NewWillBean> list=	newWillService.findNewWilldateByUser(bean);
//            if(datas!=null&& datas.size()>0){
//            	 if(list!=null&& list.size()>0){
//            	     for(NewWillBean s:datas){ //用户
//            	    	 Map<String,List<String>> map=new HashMap<String,List<String>>();
//
//            	    	 Map<String,String> map_new=new HashMap<String,String>();
//            	    	 List<String> maplist =new ArrayList<String>();
//    	    			 if(list_option!=null&&list_option.size()>0){
////                	    	 List<String> countValue=new ArrayList<String>();
//    	    				 for(OptionBean option:list_option){//遍历销售进程参数
//            	    	       for(NewWillBean newbean:list){ //遍历数据库数据
//            	    		 //根据时间排
//            	    		 if(s.getOwenrAcc()==newbean.getOwenrAcc()||s.getOwenrAcc().equals(newbean.getOwenrAcc())){
//            
//            	    					 if(newbean.getLastOptionId()==option.getOptionlistId()||newbean.getLastOptionId().equals(option.getOptionlistId())){
////            	    						 countValue.add(newbean.getNum()) ;//数据库如果有值，传值
//            	    						 map_new.put(option.getOptionlistId(),newbean.getNum());
//            	    					 }
//            	    					 }
//            	    					 
//            	    				 }
//
//            	    			 } 
//
//            	    		 }
//    	    			 if(list_option!=null&&list_option.size()>0){
//    	    				 for(OptionBean option:list_option){
//    	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
//    	    						 maplist.add(map_new.get(option.getOptionlistId()));
//    	    					 }else{
//    	    						 maplist.add("0");
//    	    					 }
//    	    				 }
//    	    			 }
////    	    			 map.put(s.getOwenrAcc()+","+s.getMemberName()+","+s.getGroupName(), maplist) ;
////    	    	    	 returnList.add(map);
////    	    	    	 
//    	    			 Map<String,Object> map_new2=new HashMap<String,Object>();
//    	    			 map_new2.put("userAccount", s.getOwenrAcc());
//    	    			 map_new2.put("userName", s.getMemberName());
//    	    			 map_new2.put("groupName", s.getGroupName());
//    	    			 map_new2.put("returnlist", maplist);
//    	    	    	 returnList.add(map_new2);
//    	    	    	 if(listcount!=null&&listcount.size()>0){
//    	    	    		 for(int i=0;i<maplist.size();i++){
//    	    	    	    	 listcount.set(i, Integer.valueOf(maplist.get(i))+Integer.valueOf(listcount.get(i))) ;
//    	    	    		 }
//    	    	    	 }else{
//    	    	    		 for(String st: maplist){
//    	    	    	    	 listcount.add(Integer.valueOf(st)); 
//    	    	    		 } 
//    	    	    	 }
//
//            	}
//            	}
//            	}
//                }
//			    
////	    	map2.put("合计", listcount);
////	    	returnList.add(map2);
////	    	
////	    	
//			 Map<String,Object> map_new3=new HashMap<String,Object>();
//			 map_new3.put("userAccount", "");
//			 map_new3.put("userName", "合计");
//			 map_new3.put("groupName", "");
//			 map_new3.put("returnlist", listcount);
//	    	 returnList.add(map_new3);
//            returnMap.put("list", returnList);
//            returnMap.put("item", item);
//            returnMap.put("status", true);
//            returnMap.put("msg", "查询成功！");
//
//    	} catch (Exception e) {
//            returnMap.put("status", false);
//            returnMap.put("msg", e.getMessage());
//            return returnMap;
//    	}
//    	
//	      return returnMap;
//    }
//    
    // //新增意向统计,当日统计
    @RequestMapping(value = "/daydata")
    public String daydata(ModelMap modelMap,String dataIndex,String dataName,Integer index,String fromDateStr,String toDateStr,String timeType,String  groupIds){
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	try {
		List<String> item=new ArrayList<String>();
		List<Object> returnList=new ArrayList<Object>();
		List<Object> List_object=new ArrayList<Object>();
		Map<String,Object>  map_object=new HashMap<String,Object>();
   	    Map<String,List<Integer>> map2=new HashMap<String,List<Integer>>();
   	    List<Integer> listcount=new ArrayList<Integer>();
    		ShiroUser user = ShiroUtil.getUser();
    		
        	Date fromDate= null;
        	Date toDate=null;
        	
        	if(StringUtils.isBlank(dataIndex)) dataIndex="line1";
        	
        	if(StringUtils.isBlank(dataName))dataName="呼出总数";
        	
        	if(index==null) index=0;
        	
        	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
        	
        	fromDate = array[0];
        	toDate = array[1];
        	
        	Date[] toWeekArray = DateSubUtil.toWeek();
	    	Date[] toMonthArray = DateSubUtil.toMonth();
        	
	    	if(StringUtils.isBlank(fromDateStr)||StringUtils.isBlank(toDateStr)){
				fromDateStr = DateUtil.getDateDay(fromDate);
				toDateStr = DateUtil.getDateDay(toDate);
			}
			NewWillBean bean=new NewWillBean();
			bean.setOrgId(user.getOrgId());
//			bean.setGroupId(groupId);
	    	
        	if (StringUtils.isNotEmpty(groupIds)) {
       		    String[] groupIdstr = groupIds.split(",");
				List<String> owaList  = Arrays.asList(groupIdstr);
				bean.setGroupIds(owaList);
        	}
    				



        //  发送时间
        	bean.setStartDate(getStartDateStr(Integer.parseInt("1")));
        	bean.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
        	   	
        	
        	//查询单位下所有的销售进程
            List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
			 if(list_option!=null&&list_option.size()>0){
				 for(OptionBean option:list_option){
					 item.add(option.getOptionName()); 
				 }
			 }
            
            if(list_option!=null && list_option.size()>0){

            List<NewWillBean> datas=new ArrayList<NewWillBean>();
//            if(groupId!=null&&!"".equals(groupId)){
                datas=newWillService.findNewWillUserByGroupByDay(bean);	
//            }else{
//                datas=newWillService.findNewWillAllUser(user.getOrgId());	
//            }

            List<NewWillBean> list=	newWillService.findNewWilldateByUser(bean);
            if(datas!=null&& datas.size()>0){
            	 if(list!=null&& list.size()>0){
            	     for(NewWillBean s:datas){ //用户
            	    	 Map<String,List<String>> map=new HashMap<String,List<String>>();

            	    	 Map<String,String> map_new=new HashMap<String,String>();
            	    	 List<String> maplist =new ArrayList<String>();
    	    			 if(list_option!=null&&list_option.size()>0){
//                	    	 List<String> countValue=new ArrayList<String>();
    	    				 for(OptionBean option:list_option){//遍历销售进程参数
            	    	       for(NewWillBean newbean:list){ //遍历数据库数据
            	    		 //根据时间排
            	    		 if(s.getOwenrAcc()==newbean.getOwenrAcc()||s.getOwenrAcc().equals(newbean.getOwenrAcc())){
            
            	    					 if(newbean.getLastOptionId()==option.getOptionlistId()||newbean.getLastOptionId().equals(option.getOptionlistId())){
//            	    						 countValue.add(newbean.getNum()) ;//数据库如果有值，传值
            	    						 map_new.put(option.getOptionlistId(),newbean.getNum());
            	    					 }
            	    					 }
            	    					 
            	    				 }

            	    			 } 

            	    		 }
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 for(OptionBean option:list_option){
    	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
    	    						 maplist.add(map_new.get(option.getOptionlistId()));
    	    					 }else{
    	    						 maplist.add("0");
    	    					 }
    	    				 }
    	    			 }
    	    			 Map<String,Object> map_new2=new HashMap<String,Object>();
    	    			 map_new2.put("userAccount", s.getOwenrAcc());
    	    			 map_new2.put("userName", s.getMemberName());
    	    			 map_new2.put("groupName", s.getGroupName());
    	    			 map_new2.put("returnlist", maplist);
    	    	    	 returnList.add(map_new2);
    	    	    	 if(listcount!=null&&listcount.size()>0){
    	    	    		 for(int i=0;i<maplist.size();i++){
    	    	    	    	 listcount.set(i, Integer.valueOf(maplist.get(i))+Integer.valueOf(listcount.get(i))) ;
    	    	    		 }
    	    	    	 }else{
    	    	    		 for(String st: maplist){
    	    	    	    	 listcount.add(Integer.valueOf(st)); 
    	    	    		 } 
    	    	    	 }

            	}
            	}
            	}
                }
			    
			 Map<String,Object> map_new3=new HashMap<String,Object>();
			 map_new3.put("userAccount", "");
			 map_new3.put("userName", "合计");
			 map_new3.put("groupName", "");
			 map_new3.put("returnlist", listcount);
	    	 returnList.add(map_new3);
	        	if(StringUtils.isBlank(timeType)) timeType="0";
	        	modelMap.put("timeType", timeType);
	        	modelMap.put("list", returnList);
	        	modelMap.put("item", item);
	        	modelMap.put("toMonthArray", toMonthArray);
	        	modelMap.put("toWeekArray", toWeekArray);
	        	modelMap.put("fromDateStr", fromDateStr);
	        	modelMap.put("toDateStr", toDateStr);
	        	
	        	modelMap.put("index", index);
	        	modelMap.put("dataIndex",dataIndex);
	        	modelMap.put("dataName",dataName);
	        	
	        	modelMap.put("timeElngth", tsmReportCallInfoService.getTimeElngth(user.getOrgId()));
	    	} catch (Exception e) {
	    		logger.error(e.getMessage(),e);
	    	}
	        return "report/willCustStatis/newWill_today";
    }

    
    //新增意向统计,历史,测试图表
    @RequestMapping(value = "/processList")
    @ResponseBody
    public Map<String,Object> processList(HttpServletRequest request){
  	    Map<String,Object> returnMap=new HashMap<String,Object>();
  	try {
  		List<String> item=new ArrayList<String>();
  		List<Object> returnList=new ArrayList<Object>();
   	    Map<String,List<Integer>> map2=new HashMap<String,List<Integer>>();
   	    List<Integer> listcount=new ArrayList<Integer>();
    		ShiroUser user = ShiroUtil.getUser();
    		String  dDateType = request.getParameter("dDateType");		
  			NewWillBean bean=new NewWillBean();
  			bean.setOrgId(user.getOrgId());
     	
        	//查询单位下所有的销售进程
            List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
            List<Object> list=new ArrayList<Object>();
    		Map<String,String> maps2=new HashMap<String,String>();
    		maps2.put("processId", "1234567890");
    		maps2.put("process", "新增意向数");
    		list.add(maps2);
            if(list_option!=null && list_option.size()>0){
            	for(OptionBean optionBean:list_option){
            		Map<String,String> maps=new HashMap<String,String>();
            		maps.put("processId", optionBean.getOptionlistId());
            		maps.put("process", optionBean.getOptionName());
            		list.add(maps);
            	}
            }


            returnMap.put("list", list);
            returnMap.put("status", true);
            returnMap.put("msg", "查询成功！");
    	} catch (Exception e) {

    		logger.error(e.getMessage(),e);
    	}
    	

  	      return returnMap;
    }
    
  //新增意向统计,历史,测试图表
  @RequestMapping(value = "/testoldDaydata2")
  @ResponseBody
  public Map<String,Object> testoldDaydata2(HttpServletRequest request){
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	try {
		List<String> item=new ArrayList<String>();
		List<Object> returnList=new ArrayList<Object>();
		List<Object> list_new=new ArrayList<Object>();
 	    Map<String,List<Integer>> map2=new HashMap<String,List<Integer>>();
 	    List<Integer> listcount=new ArrayList<Integer>();
  		ShiroUser user = ShiroUtil.getUser();
  		String  dDateType = request.getParameter("dDateType");	
  		String processId=request.getParameter("processId");	
			NewWillBean bean=new NewWillBean();
			bean.setOrgId(user.getOrgId());
      //  发送时间
      	if(StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)){
      		bean.setStartDate(getStartDateStr(Integer.parseInt(dDateType)));
      		bean.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
      	}else{
      		bean.setStartDate(getStartDateStr(Integer.parseInt("1")));
      		bean.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
      	}        	
      	//查询单位下所有的销售进程
          List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
			 if(list_option!=null&&list_option.size()>0){
				 for(OptionBean option:list_option){
					 item.add(option.getOptionName()); 
				 }
			 }
          
          if(list_option!=null && list_option.size()>0){
          List<String> datas=newWillService.findNewWilldate(bean);
          List<NewWillBean> list=	newWillService.findNewWillCountDataBydate(bean);

          if(datas!=null&& datas.size()>0){
          	 if(list!=null&& list.size()>0){
          	     for(String s:datas){ //遍历时间
          	    	 Map<String,List<String>> map=new HashMap<String,List<String>>();

          	    	 Map<String,String> map_new=new HashMap<String,String>();
          	    	 List<String> maplist =new ArrayList<String>();
          	    	 
  	    			 if(list_option!=null&&list_option.size()>0){
  	    				 for(OptionBean option:list_option){//遍历销售进程参数
          	    	       for(NewWillBean newbean:list){ //遍历数据库数据
          	    		 //根据时间排
          	    		 if(s==newbean.getForTheCustomerTime()||s.equals(newbean.getForTheCustomerTime())){         
          	    					 if(newbean.getLastOptionId()==option.getOptionlistId()||newbean.getLastOptionId().equals(option.getOptionlistId())){
          	    						 map_new.put(option.getOptionlistId(),newbean.getNum());
          	    					 }
          	    					 }
          	    					 
          	    				 }

          	    			 } 

          	    		 }
  	    			 if(list_option!=null&&list_option.size()>0){
  	    				 for(OptionBean option:list_option){
  	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
  	    						 maplist.add(map_new.get(option.getOptionlistId()));
  	    					 }
  	    					 else{
  	    						 maplist.add("0");
  	    					 }
  	    				 }
  	    			 }

  	    			 if(list_option!=null&&list_option.size()>0){
  	    					 for(int i=0;i<list_option.size();i++){
  	    					Map<String,Object> maps=new HashMap<String,Object>();
  	    					maps.put("process", list_option.get(i).getOptionName());
  	    					maps.put("processId", list_option.get(i).getOptionlistId());
  	    					maps.put("sort", i+1);
	    					maps.put("returnlist", maplist.get(i));
	    					maps.put("countvalue", s);
  	    	    			list_new.add(maps);
  	    				 }
  	    			 }
        	}
        	}
        	}
            }
      	String process="";
        if(processId==null||"".equals(processId)){
        	processId=list_option.get(0).getOptionlistId();
        	process=list_option.get(0).getOptionName();
        }else{
        	for(OptionBean optionBean:list_option){
             if(processId==optionBean.getOptionlistId()||processId.equals(optionBean.getOptionlistId())){
            	 process=optionBean.getOptionName();
             }

        	}
        }
          returnMap.put("processIds",processId);
          returnMap.put("process",process);
          returnMap.put("list", JsonUtil.getJsonString(list_new));
          returnMap.put("item", item);
          returnMap.put("status", true);
          returnMap.put("msg", "查询成功！");
  	} catch (Exception e) {

  		logger.error(e.getMessage(),e);
  	}
	      return returnMap;
  }
    
    
    
    //新增意向统计,历史
    @RequestMapping(value = "/testoldDaydata")
    @ResponseBody
    public Map<String,Object> testoldDaydata(HttpServletRequest request){
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	try {
		List<String> item=new ArrayList<String>();
		List<Object> returnList=new ArrayList<Object>();
   	    Map<String,List<Integer>> map2=new HashMap<String,List<Integer>>();
   	    List<Integer> listcount=new ArrayList<Integer>();
    		ShiroUser user = ShiroUtil.getUser();
    		String  dDateType = request.getParameter("dDateType");	
    		String  account=request.getParameter("account");	
			NewWillBean bean=new NewWillBean();
			bean.setOrgId(user.getOrgId());
			bean.setOwenrAcc(account);
        //  发送时间
        	if(StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)){
        		bean.setStartDate(getStartDateStr(Integer.parseInt(dDateType)));
        		bean.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
        	}else{
        		bean.setStartDate(getStartDateStr(Integer.parseInt("1")));
        		bean.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
        	}        	
        	//查询单位下所有的销售进程
            List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
			 if(list_option!=null&&list_option.size()>0){
				 for(OptionBean option:list_option){
					 item.add(option.getOptionName()); 
				 }
			 }
            
            if(list_option!=null && list_option.size()>0){
            List<String> datas=newWillService.findNewWilldate(bean);
            List<NewWillBean> list=	newWillService.findNewWillCountDataBydate(bean);

            if(datas!=null&& datas.size()>0){
            	 if(list!=null&& list.size()>0){
            	     for(String s:datas){ //遍历时间
            	    	 Map<String,List<String>> map=new HashMap<String,List<String>>();

            	    	 Map<String,String> map_new=new HashMap<String,String>();
            	    	 List<String> maplist =new ArrayList<String>();
            	    	 
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 for(OptionBean option:list_option){//遍历销售进程参数
            	    	       for(NewWillBean newbean:list){ //遍历数据库数据
            	    		 //根据时间排
            	    		 if(s==newbean.getForTheCustomerTime()||s.equals(newbean.getForTheCustomerTime())){         
            	    					 if(newbean.getLastOptionId()==option.getOptionlistId()||newbean.getLastOptionId().equals(option.getOptionlistId())){
            	    						 map_new.put(option.getOptionlistId(),newbean.getNum());
            	    					 }
            	    					 }
            	    					 
            	    				 }

            	    			 } 

            	    		 }
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 for(OptionBean option:list_option){
    	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
    	    						 maplist.add(map_new.get(option.getOptionlistId()));
    	    					 }else{
    	    						 maplist.add("0");
    	    					 }
    	    				 }
    	    			 }

    	    			 Map<String,Object> map_new2=new HashMap<String,Object>();
    	    			 map_new2.put("countvalue", s);
    	    			 map_new2.put("returnlist", maplist);
    	    	    	 returnList.add(map_new2);
    	    	    	 if(listcount!=null&&listcount.size()>0){
    	    	    		 for(int i=0;i<maplist.size();i++){
    	    	    	    	 listcount.set(i, Integer.valueOf(maplist.get(i))+Integer.valueOf(listcount.get(i))) ;
    	    	    		 }
    	    	    	 }else{
    	    	    		 for(String st: maplist){
    	    	    	    	 listcount.add(Integer.valueOf(st)); 
    	    	    		 } 
    	    	    	 }

            	}
            	}
            	}
                }
			 Map<String,Object> map_new3=new HashMap<String,Object>();
			 map_new3.put("countvalue", "合计");
			 map_new3.put("returnlist", listcount);
	    	 returnList.add(map_new3);
            returnMap.put("list", returnList);
            returnMap.put("item", item);
            returnMap.put("status", true);
            returnMap.put("msg", "查询成功！");
    	} catch (Exception e) {

    		logger.error(e.getMessage(),e);
    	}
    	
	      return returnMap;
    }
    
    

    
    //新增意向统计,历史统计，根据部门统计，统计所有部门
    @RequestMapping(value = "/testoldDaydataByGroup")
    @ResponseBody
    public Map<String,Object> oldDaydataByGroup(HttpServletRequest request){
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	try {
		List<String> item=new ArrayList<String>();
		List<Object> returnList=new ArrayList<Object>();
		List<Object> List_object=new ArrayList<Object>();
		Map<String,Object>  map_object=new HashMap<String,Object>();
   	    Map<String,List<Integer>> map2=new HashMap<String,List<Integer>>();
   	    List<Integer> listcount=new ArrayList<Integer>();
    		ShiroUser user = ShiroUtil.getUser();
    		String  dDateType = request.getParameter("dDateType");	
			NewWillBean bean=new NewWillBean();
			bean.setOrgId(user.getOrgId());
        //  发送时间
        	if(StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)){
        		bean.setStartDate(getStartDateStr(Integer.parseInt(dDateType)));
        		bean.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
        	}else{
        		bean.setStartDate(getStartDateStr(Integer.parseInt("1")));
        		bean.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
        	}
        	
       	//查询权限下的部门
        	List<String> shareGroupIds =new ArrayList<String>();
        	Map<String,String>map_group = new HashMap<String, String>();
        	map_group.put("orgId",user.getOrgId());
        	map_group.put("userId",user.getId());
		 	OrgGroupUser groupUser = orgGroupUserMapper.findGroupId(map_group);

        	
        	
        	//查询单位下所有的销售进程
            List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
			 if(list_option!=null&&list_option.size()>0){
				 for(OptionBean option:list_option){
					 item.add(option.getOptionName()); 
				 }
			 }
            
            if(list_option!=null && list_option.size()>0){
    			if(groupUser != null){
    	    		Map<String,String>map1 = new HashMap<String, String>();
    	    		map1.put("account",groupUser.getMemberAcc());
    	    		map1.put("orgId", user.getOrgId());
    	    		//查询权限下分组

    	    List<OrgGroupUser> datas = orgGroupUserMapper.findShareGroupIdAndName(map1);
            List<NewWillBean> list=	newWillService.findNewWilldateByGroup(bean);
            if(datas!=null&& datas.size()>0){
            	 if(list!=null&& list.size()>0){
            	     for(OrgGroupUser s:datas){ //遍历部门
            	    	 Map<String,List<String>> map=new HashMap<String,List<String>>();

            	    	 Map<String,String> map_new=new HashMap<String,String>();
            	    	 List<String> maplist =new ArrayList<String>();
    	    			 if(list_option!=null&&list_option.size()>0){
//                	    	 List<String> countValue=new ArrayList<String>();
    	    				 for(OptionBean option:list_option){//遍历销售进程参数
            	    	       for(NewWillBean newbean:list){ //遍历数据库数据
            	    		 //根据时间排
            	    		 if(s.getGroupId()==newbean.getImporTdeptId()||s.getGroupId().equals(newbean.getImporTdeptId())){
            
            	    					 if(newbean.getLastOptionId()==option.getOptionlistId()||newbean.getLastOptionId().equals(option.getOptionlistId())){
//            	    						 countValue.add(newbean.getNum()) ;//数据库如果有值，传值
            	    						 map_new.put(option.getOptionlistId(),newbean.getNum());
            	    					 }
            	    					 }
            	    					 
            	    				 }

            	    			 } 

            	    		 }
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 for(OptionBean option:list_option){
    	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
    	    						 maplist.add(map_new.get(option.getOptionlistId()));
    	    					 }else{
    	    						 maplist.add("0");
    	    					 }
    	    				 }
    	    			 }
    	    			 Map<String,Object> map_new2=new HashMap<String,Object>();
    	    			 map_new2.put("groupId", s.getGroupId());
    	    			 map_new2.put("groupName", s.getGroupName());
    	    			 map_new2.put("returnlist", maplist);
    	    	    	 returnList.add(map_new2);
    	    	    	 if(listcount!=null&&listcount.size()>0){
    	    	    		 for(int i=0;i<maplist.size();i++){
    	    	    	    	 listcount.set(i, Integer.valueOf(maplist.get(i))+Integer.valueOf(listcount.get(i))) ;
    	    	    		 }
    	    	    	 }else{
    	    	    		 for(String st: maplist){
    	    	    	    	 listcount.add(Integer.valueOf(st)); 
    	    	    		 } 
    	    	    	 }

            	}
            	}
            	}
                }
			    }
			 Map<String,Object> map_new3=new HashMap<String,Object>();
			 map_new3.put("groupId", "");
			 map_new3.put("groupName", "合计");
			 map_new3.put("returnlist", listcount);
	    	 returnList.add(map_new3);
            returnMap.put("list", returnList);
            returnMap.put("item", item);
            returnMap.put("status", true);
            returnMap.put("msg", "查询成功！");
    	} catch (Exception e) {
            returnMap.put("status", false);
            returnMap.put("msg", e.getMessage());
            return returnMap;
    	}
    	

	      return returnMap;
    }
    
   
    
    //新增意向统计,历史统计，统计部门下用户销售进程
    @RequestMapping(value = "/testoldDaydataByUser")
    @ResponseBody
    public Map<String,Object> testoldDaydataByUser(HttpServletRequest request){
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	try {
		List<String> item=new ArrayList<String>();
		List<Object> returnList=new ArrayList<Object>();
		List<Object> List_object=new ArrayList<Object>();
		Map<String,Object>  map_object=new HashMap<String,Object>();
   	    Map<String,List<Integer>> map2=new HashMap<String,List<Integer>>();
   	    List<Integer> listcount=new ArrayList<Integer>();
    		ShiroUser user = ShiroUtil.getUser();
    		String  dDateType = request.getParameter("dDateType");	
    		String  groupId = request.getParameter("groupId");	
			NewWillBean bean=new NewWillBean();
			bean.setOrgId(user.getOrgId());
			bean.setGroupId(groupId);
        //  发送时间
        	if(StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)){
        		bean.setStartDate(getStartDateStr(Integer.parseInt(dDateType)));
        		bean.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
        	}else{
        		bean.setStartDate(getStartDateStr(Integer.parseInt("1")));
        		bean.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
        	}
        	   	
        	
        	//查询单位下所有的销售进程
            List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
			 if(list_option!=null&&list_option.size()>0){
				 for(OptionBean option:list_option){
					 item.add(option.getOptionName()); 
				 }
			 }
            
            if(list_option!=null && list_option.size()>0){
            List<NewWillBean> datas=new ArrayList<NewWillBean>();
            if(groupId!=null&&!"".equals(groupId)){
                datas=newWillService.findNewWillUserByGroup(bean);	
            }else{
                datas=newWillService.findNewWillAllUser(user.getOrgId());	
            }

            List<NewWillBean> list=	newWillService.findNewWilldateByUser(bean);
            if(datas!=null&& datas.size()>0){
            	 if(list!=null&& list.size()>0){
            	     for(NewWillBean s:datas){ //遍历部门
            	    	 Map<String,List<String>> map=new HashMap<String,List<String>>();

            	    	 Map<String,String> map_new=new HashMap<String,String>();
            	    	 List<String> maplist =new ArrayList<String>();
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 for(OptionBean option:list_option){//遍历销售进程参数
            	    	       for(NewWillBean newbean:list){ //遍历数据库数据
            	    		 //根据时间排
            	    		 if(s.getOwenrAcc()==newbean.getOwenrAcc()||s.getOwenrAcc().equals(newbean.getOwenrAcc())){            
            	    					 if(newbean.getLastOptionId()==option.getOptionlistId()||newbean.getLastOptionId().equals(option.getOptionlistId())){
//            	    						 countValue.add(newbean.getNum()) ;//数据库如果有值，传值
            	    						 map_new.put(option.getOptionlistId(),newbean.getNum());
            	    					 }
            	    					 }
            	    					 
            	    				 }

            	    			 } 

            	    		 }
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 for(OptionBean option:list_option){
    	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
    	    						 maplist.add(map_new.get(option.getOptionlistId()));
    	    					 }else{
    	    						 maplist.add("0");
    	    					 }
    	    				 }
    	    			 }
    	    			 Map<String,Object> map_new2=new HashMap<String,Object>();
    	    			 map_new2.put("userAccount", s.getOwenrAcc());
    	    			 map_new2.put("userName", s.getMemberName());
    	    			 map_new2.put("returnlist", listcount);
    	    	    	 returnList.add(map_new2);
    	    	    	 if(listcount!=null&&listcount.size()>0){
    	    	    		 for(int i=0;i<maplist.size();i++){
    	    	    	    	 listcount.set(i, Integer.valueOf(maplist.get(i))+Integer.valueOf(listcount.get(i))) ;
    	    	    		 }
    	    	    	 }else{
    	    	    		 for(String st: maplist){
    	    	    	    	 listcount.add(Integer.valueOf(st)); 
    	    	    		 } 
    	    	    	 }

            	}
            	}
            	}
                }
            returnMap.put("list", returnList);
            returnMap.put("item", item);
            returnMap.put("status", true);
            returnMap.put("msg", "查询成功！");

    	} catch (Exception e) {
            returnMap.put("status", false);
            returnMap.put("msg", e.getMessage());
            return returnMap;
    	}
    	
	      return returnMap;
    }


    /***************************导出文件*****************************************/
    
    @RequestMapping("/export")
    @ResponseBody
    public void export(HttpServletRequest request,HttpServletResponse response){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
    		String groupIds = request.getParameter("groupIds");
    		String expType = "1";
    		HSSFWorkbook workbook = new HSSFWorkbook();
    		/**表头样式**/
    		HSSFCellStyle headerStyle = workbook.createCellStyle();
    		HSSFFont font = workbook.createFont();  
    		font.setColor(HSSFColor.BLACK.index);  
    		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 粗体  
    		font.setFontHeightInPoints((short)12);
    		headerStyle.setFont(font);  
    		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中  
    		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中 
    		/**内容样式**/
    		HSSFCellStyle bodyStyle = workbook.createCellStyle();
    		HSSFFont bodyFont = workbook.createFont();
    		bodyFont.setColor(HSSFColor.BLACK.index);
    		bodyFont.setFontHeightInPoints((short)11);
    		bodyStyle.setFont(bodyFont);
    		bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中  
    		bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中 
    		String expName="";
				/**今日新增意向统计**/
	    		HSSFSheet callSheet = workbook.createSheet("今日新增意向统计");
	    		Map<String,Object> map=dayreturndata(groupIds);
	    		List<Object> list=(List<Object>) map.get("list");
	    		List<String> item=(List<String>) map.get("item");  		
	    	List<String> list_new =new ArrayList<String>();
	    	list_new.add("销售姓名");
	    	list_new.add("所属小组");
	    	list_new.add("意向客户数");
	    	if(item!=null&&item.size()>0){
	    		for(String s:item){
	    			list_new.add(s)	;
	    		}
	    	}
	    	createConSheet(list,callSheet,headerStyle,bodyStyle,list_new);
	    	expName="今日新增意向统计";
    		
    		response.setContentType("application/vnd.ms-excel");    
    		response.setHeader("Content-disposition", "attachment;filename="+DateUtil.formatDate(new Date(),"yyyyMMdd")+URLEncoder.encode("今天新增意向统计.xls","UTF-8"));
            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();    
            ouputStream.close();
    	} catch (Exception e) {
			throw new SysRunException(e);
		}
    }
	/**
	 * 
	 *
	 */
	public void createConSheet(List<Object> dtos,HSSFSheet sheet, HSSFCellStyle headerStyle, HSSFCellStyle bodyStyle,List<String> item) {
		HSSFRow rowm = sheet.createRow(0);
		for (int i = 0; i < item.size(); i++) {
			HSSFCell cell = rowm.createCell(i);
			cell.setCellValue(item.get(i));
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, 32 * 160);
		}
		for (int i = 0; i < dtos.size(); i++) {

			Map<String,Object> map1=(Map<String, Object>) dtos.get(i);
			HSSFRow datarow = sheet.createRow(i + 1);
			for (int j = 0; j < item.size(); j++) {
				HSSFCell cell = datarow.createCell(j);
				cell.setCellStyle(bodyStyle);
				if (j == 0) {
					cell.setCellValue(map1.get("userName").toString());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else	if (j == 1) {
					cell.setCellValue(map1.get("groupName").toString());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else if(j>=2){
				List<String> list=(List<String>) map1.get("returnlist");
				if(list!=null&&list.size()>0){
						cell.setCellValue(list.get(j-2));
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);							
				}

				}
			}
		}

	}

	
    public Map<String,Object> dayreturndata(String groupIds){
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	try {
		List<String> item=new ArrayList<String>();
		List<Object> returnList=new ArrayList<Object>();
		List<Object> List_object=new ArrayList<Object>();
		Map<String,Object>  map_object=new HashMap<String,Object>();
   	    Map<String,List<Integer>> map2=new HashMap<String,List<Integer>>();
   	    List<String> listcount=new ArrayList<String>();
		List<String> grouplist=new ArrayList<String>();
    		ShiroUser user = ShiroUtil.getUser();	
    		TsmReportWillBean bean=new TsmReportWillBean();
			bean.setOrgId(user.getOrgId());
        	if (StringUtils.isNotEmpty(groupIds)) {
       		    String[] groupIdstr = groupIds.split(",");
				List<String> owaList  = Arrays.asList(groupIdstr);
				bean.setGroupIds(owaList);
        	}else{
    			List<TeamGroupBean> teamGroups = mainService.queryManageGroupList(user.getAccount(),user.getOrgId());

    			if(teamGroups!=null&&teamGroups.size()>0){
    				for(TeamGroupBean teamGroupBean : teamGroups){
    					grouplist.add(teamGroupBean.getGroupId());
    					bean.setGroupIds(grouplist);
    				}
    			}
        	}    	
        	
        //  发送时间
        	bean.setStartDate(getStartDateStr(Integer.parseInt("1")));
        	bean.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
        	   	
        	
        	//查询单位下所有的销售进程
            List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
			 if(list_option!=null&&list_option.size()>0){
				 for(OptionBean option:list_option){
					 item.add(option.getOptionName()); 
				 }
			 }
            
            if(list_option!=null && list_option.size()>0){
              List<TsmReportWillBean> datas=newWillService.findAllUser(bean);//数据条数	

              //统计所有新增销售进程
              List<TsmReportWillBean> list1=newWillService.findNewWillAllByAccount_new(bean);
              //统计所有销售进程进阶
              List<TsmReportWillBean> list3=newWillService.findNewWillAllByAccount_new_3(bean);
              //统计所有新增意向数
              List<TsmReportWillBean> list2=newWillService.findNewWillAllByAccount_new_2(bean);
              List<TsmReportWillBean> list=new ArrayList<TsmReportWillBean>();
              
              List<TsmReportWillBean> list4=new ArrayList<TsmReportWillBean>();
              
              if(list1!=null && list1.size()>0){
            	  for(int i=0;i<list1.size();i++){
                      if(list3!=null && list3.size()>0){
                    	  for(TsmReportWillBean beans3: list3){
                    		  if((list1.get(i).getUserAccount()==beans3.getUserAccount()||list1.get(i).getUserAccount().equals(beans3.getUserAccount())
                    			  &&(list1.get(i).getOptionlistId()==beans3.getOptionlistId()||list1.get(i).getOptionlistId().equals(beans3.getOptionlistId())))){
                    			  list1.get(i).setOptionSum(list1.get(i).getOptionSum()+beans3.getOptionSum());
                    			  list4.add(beans3);
                    		  }
                    		           	
                    	  }
                      }          	
            	  }
            
              }
              
              if(list1!=null && list1.size()>0){
            	  for(TsmReportWillBean beans: list1){
            		  list.add(beans);           	
            	  }
              }
              
              if(list3!=null && list3.size()>0){
            	  for(TsmReportWillBean beans3: list3){
                      if(list4!=null && list4.size()>0){
                    	  for(TsmReportWillBean beans4: list4){
                    		  if((beans3.getUserAccount()!=beans4.getUserAccount()||!beans3.getUserAccount().equals(beans4.getUserAccount())
                        			  &&(beans3.getOptionlistId()!=beans4.getOptionlistId()||!beans3.getOptionlistId().equals(beans4.getOptionlistId())))){
                        		  list.add(beans3); 	  
                    		  }
          	
                    	  }
                      }else{
                    	  list.add(beans3); 
                      }
            	        	
            	  }
              }
//              }else{
//                  if(list3!=null && list3.size()>0){
//                	  for(TsmReportWillBean beans3: list3){
//                           		  list.add(beans3); 	                 	        	
//                	  }
//                  }
//              }
              
              if(list2!=null && list2.size()>0){
            	  for(TsmReportWillBean beans: list2){
            		  list.add(beans);
            	  }
              }
              
//	          List<TsmReportWillBean> list=	newWillService.findNewWillAllByAccount(bean);//数据
              if(datas!=null&& datas.size()>0){
            	     for(TsmReportWillBean s:datas){ //用户
            	    	 Map<String,List<String>> map=new HashMap<String,List<String>>();

            	    	 Map<String,String> map_new=new HashMap<String,String>();
            	    	 List<String> maplist =new ArrayList<String>();
//            	    	 bean.setUserAccount(s);

            	    	 
    	    			 if(list_option!=null&&list_option.size()>0){
//                	    	 List<String> countValue=new ArrayList<String>();
    	    				 for(OptionBean option:list_option){//遍历销售进程参数
            	    	       for(TsmReportWillBean newbean:list){ //遍历数据库数据
            	    	    	   if(newbean.getType()==1){
            	    		 //根据时间排
            	    		 if(s.getUserAccount()==newbean.getUserAccount()||s.getUserAccount().equals(newbean.getUserAccount())){ 

            	    			 if(newbean.getOptionlistId()==option.getOptionlistId()||newbean.getOptionlistId().equals(option.getOptionlistId())){   
                	    			    map_new.put(option.getOptionlistId(),String.valueOf(newbean.getOptionSum())); 


            	    					 }
            	    					 }
            	    	    	        }
            	    				 }

            	    			 } 

            	    		 }
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 int new_will_sum=0;
    	    				 for(TsmReportWillBean newbean:list){ 
    	    					 if(newbean.getType()==0 &&( s.getUserAccount()==newbean.getUserAccount()||s.getUserAccount().equals(newbean.getUserAccount()))){
    	    						 new_will_sum=newbean.getNewWillSum();
    	    					 }
    	    				 }
    	    				 maplist.add(String.valueOf(new_will_sum));
    	    				 for(OptionBean option:list_option){
    	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
    	    						 maplist.add(map_new.get(option.getOptionlistId()));
    	    					 }else{
    	    						 maplist.add("0");
    	    					 }
    	    				 }
    	    			 }
    	    	    	 

    	    			 Map<String,Object> map_new2=new HashMap<String,Object>();   	    			 	 
    	    			 map_new2.put("userAccount", s.getUserAccount());
    	    			 map_new2.put("userName", s.getUserName());
    	    			 map_new2.put("groupName", s.getGroupName());
    	    			 map_new2.put("returnlist", maplist);
    	    	    	 returnList.add(map_new2);
//    	    			 }
    	    	    	 if(listcount!=null&&listcount.size()>0){
    	    	    		 for(int i=0;i<maplist.size();i++){
    	    	    	    	 listcount.set(i, String.valueOf(Integer.valueOf(maplist.get(i))+Integer.valueOf(listcount.get(i)))) ;
    	    	    		 }
    	    	    	 }else{
    	    	    		 for(String st: maplist){
    	    	    	    	 listcount.add(String.valueOf(Integer.valueOf(st))); 
    	    	    		 } 
    	    	    	 }

            	}
            	}
                }
			     	
			 Map<String,Object> map_new3=new HashMap<String,Object>();
			 map_new3.put("userAccount", "");
			 map_new3.put("userName", "合计");
			 map_new3.put("groupName", "");
			 map_new3.put("returnlist", listcount);
	    	 returnList.add(map_new3);
            returnMap.put("list", returnList);
            returnMap.put("item", item);
            returnMap.put("status", true);
            returnMap.put("msg", "查询成功！");

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
	      return returnMap;
    }
    
	@RequestMapping("/exportbyGroup")
    public void exportbyGroup(String fromDateStr,String toDateStr,String timeType,HttpServletRequest request,HttpServletResponse response){
    	try {	    	
    		ShiroUser user = ShiroUtil.getShiroUser();
    		String groupIds = request.getParameter("groupIds");
    		String expType = "1";
    		HSSFWorkbook workbook = new HSSFWorkbook();
    		/**表头样式**/
    		HSSFCellStyle headerStyle = workbook.createCellStyle();
    		HSSFFont font = workbook.createFont();  
    		font.setColor(HSSFColor.BLACK.index);  
    		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 粗体  
    		font.setFontHeightInPoints((short)12);
    		headerStyle.setFont(font);  
    		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中  
    		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中 
    		/**内容样式**/
    		HSSFCellStyle bodyStyle = workbook.createCellStyle();
    		HSSFFont bodyFont = workbook.createFont();
    		bodyFont.setColor(HSSFColor.BLACK.index);
    		bodyFont.setFontHeightInPoints((short)11);
    		bodyStyle.setFont(bodyFont);
    		bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中  
    		bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中 
    		String expName="";
				/**今日新增意向统计**/
	    		HSSFSheet callSheet = workbook.createSheet("今日新增意向统计");
	    		Map<String,Object> map=oldDaydataByGroupByexcout(fromDateStr,toDateStr,timeType);
	    		List<Object> list=(List<Object>) map.get("list");
	    		List<String> item=(List<String>) map.get("item");  		
	    	List<String> list_new =new ArrayList<String>();
	    	list_new.add("部门名称");
	    	list_new.add("意向客户数");
	    	if(item!=null&&item.size()>0){
	    		for(String s:item){
	    			list_new.add(s)	;
	    		}
	    	}
	    	createConSheetbyGroup(list,callSheet,headerStyle,bodyStyle,list_new);
	    	expName="新增意向小组统计-历史";
    		
    		response.setContentType("application/vnd.ms-excel");    
    		response.setHeader("Content-disposition", "attachment;filename="+DateUtil.formatDate(new Date(),"yyyyMMdd")+URLEncoder.encode("新增意向小组统计-历史.xls","UTF-8"));
            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();    
            ouputStream.close();
    	} catch (Exception e) {
			throw new SysRunException(e);
		}
    }
	/**
	 * 
	 *
	 */
	public void createConSheetbyGroup(List<Object> dtos,HSSFSheet sheet, HSSFCellStyle headerStyle, HSSFCellStyle bodyStyle,List<String> item) {
		HSSFRow rowm = sheet.createRow(0);
		for (int i = 0; i < item.size(); i++) {
			HSSFCell cell = rowm.createCell(i);
			cell.setCellValue(item.get(i));
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, 32 * 160);
		}
		for (int i = 0; i < dtos.size(); i++) {

			Map<String,Object> map1=(Map<String, Object>) dtos.get(i);
			HSSFRow datarow = sheet.createRow(i + 1);
			for (int j = 0; j < item.size(); j++) {
				HSSFCell cell = datarow.createCell(j);
				cell.setCellStyle(bodyStyle);
				if (j == 0) {
					cell.setCellValue(map1.get("groupName").toString());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j>=1){
				List<String> list=(List<String>) map1.get("returnlist");
				if(list!=null&&list.size()>0){
						cell.setCellValue(list.get(j-1));
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);							
				}

				}
			}
		}

	}

	
    //新增意向统计,历史统计，根据部门统计，统计所有部门
    public Map<String,Object> oldDaydataByGroupByexcout(String fromDateStr,String toDateStr,String timeType){
		Map<String,Object> modelMap=new HashMap<String,Object>();
    	try {
		List<String> item=new ArrayList<String>();
		List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();

   	    List<Integer> listcount=new ArrayList<Integer>();
    		ShiroUser user = ShiroUtil.getUser();
    		
    		
        	Date fromDate= null;
        	Date toDate=null;
        	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
        	
        	fromDate = array[0];
        	toDate = array[1];
        	
        	Date[] toWeekArray = DateSubUtil.toWeek();
	    	Date[] toMonthArray = DateSubUtil.toMonth();
        	
	    	if(StringUtils.isBlank(fromDateStr)||StringUtils.isBlank(toDateStr)){
				fromDateStr = DateUtil.getDateDay(fromDate);
				toDateStr = DateUtil.getDateDay(toDate);
			}
    				
	    	TsmReportWillBean bean=new TsmReportWillBean();
			bean.setOrgId(user.getOrgId());

        //  发送时间
        	bean.setStartDate(DateUtil.formatDate(DateUtil.dateBegin(fromDate), DateUtil.DATE_DAY));
        	bean.setEndDate(DateUtil.formatDate(DateUtil.dateEnd(toDate), DateUtil.DATE_DAY));
        	Map<String,String>map_group = new HashMap<String, String>();
        	map_group.put("orgId",user.getOrgId());
        	map_group.put("userId",user.getId());
		 	OrgGroupUser groupUser = orgGroupUserMapper.findGroupId(map_group);
		 	
        	//查询单位下所有的销售进程
            List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
			 if(list_option!=null&&list_option.size()>0){
				 for(OptionBean option:list_option){
					 item.add(option.getOptionName()); 
				 }
			 }
            
            if(list_option!=null && list_option.size()>0){
    			if(groupUser != null){
    	    		Map<String,String>map1 = new HashMap<String, String>();
    	    		map1.put("account",groupUser.getMemberAcc());
    	    		map1.put("orgId", user.getOrgId());
    	    		//查询权限下分组

    	    List<OrgGroupUser> datas = orgGroupUserMapper.findShareGroupIdBySall(map1);
            List<TsmReportWillBean> list=	newWillService.findNewWillByGroup(bean);
            if(datas!=null&& datas.size()>0){
            	     for(OrgGroupUser s:datas){ //遍历部门
            	    	 Map<String,String> map_new=new HashMap<String,String>();
            	    	 List<String> maplist =new ArrayList<String>();
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 for(OptionBean option:list_option){//遍历销售进程参数
            	    	       for(TsmReportWillBean newbean:list){ //遍历数据库数据
            	    	    	   if(newbean.getType()==1){
            	    		 //根据时间排
            	    		 if(s.getGroupId()==newbean.getGroupId()||s.getGroupId().equals(newbean.getGroupId())){            
            	    					 if(newbean.getOptionlistId()==option.getOptionlistId()||newbean.getOptionlistId().equals(option.getOptionlistId())){
            	    						 map_new.put(option.getOptionlistId(),String.valueOf(newbean.getOptionSum()));
            	    					 }
            	    					 }
            	    					 
            	    				 }
            	    	          }
            	    			 } 

            	    		 }
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 int new_will_sum=0;
    	    				 for(TsmReportWillBean newbean:list){ 
    	    					 if(newbean.getType()==0 &&(s.getGroupId()==newbean.getGroupId()||s.getGroupId().equals(newbean.getGroupId()))){
    	    						 new_will_sum=newbean.getNewWillSum();
    	    					 }
    	    				 }
    	    				 maplist.add(String.valueOf(new_will_sum));
    	    				 for(OptionBean option:list_option){
    	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
    	    						 maplist.add(map_new.get(option.getOptionlistId()));
    	    					 }else{
    	    						 maplist.add("0");
    	    					 }
    	    				 }
    	    			 } 	    	    	 
    	    			 Map<String,Object> map_new2=new HashMap<String,Object>();
    	    			 map_new2.put("groupId", s.getGroupId());
    	    			 map_new2.put("groupName", s.getGroupName());
    	    			 map_new2.put("returnlist", maplist);
    	    	    	 returnList.add(map_new2);
    	    	    	 if(listcount!=null&&listcount.size()>0){
    	    	    		 for(int i=0;i<maplist.size();i++){
    	    	    	    	 listcount.set(i, Integer.valueOf(maplist.get(i))+Integer.valueOf(listcount.get(i))) ;
    	    	    		 }
    	    	    	 }else{
    	    	    		 for(String st: maplist){
    	    	    	    	 listcount.add(Integer.valueOf(st)); 
    	    	    		 } 
    	    	    	 }

            	}
            	}
                }
			    }
            processTopGroupValue(user.getOrgId(),returnList);
	        if(StringUtils.isBlank(timeType)) timeType="0";

	        modelMap.put("list", returnList);
	        modelMap.put("item", item);
	    	} catch (Exception e) {
	    		logger.error(e.getMessage(),e);
	    	}
	        return modelMap;
    }
    
	@RequestMapping("/exportbyUser")
    public void exportbyUser(String fromDateStr,String toDateStr,String timeType,String groupId,HttpServletRequest request,HttpServletResponse response){
    	try {	    	
    		ShiroUser user = ShiroUtil.getShiroUser();
    		String expType = "1";
    		HSSFWorkbook workbook = new HSSFWorkbook();
    		/**表头样式**/
    		HSSFCellStyle headerStyle = workbook.createCellStyle();
    		HSSFFont font = workbook.createFont();  
    		font.setColor(HSSFColor.BLACK.index);  
    		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 粗体  
    		font.setFontHeightInPoints((short)12);
    		headerStyle.setFont(font);  
    		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中  
    		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中 
    		/**内容样式**/
    		HSSFCellStyle bodyStyle = workbook.createCellStyle();
    		HSSFFont bodyFont = workbook.createFont();
    		bodyFont.setColor(HSSFColor.BLACK.index);
    		bodyFont.setFontHeightInPoints((short)11);
    		bodyStyle.setFont(bodyFont);
    		bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中  
    		bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中 
    		String expName="";
				/**今日新增意向统计**/
	    		HSSFSheet callSheet = workbook.createSheet("今日新增意向统计");
	    		Map<String,Object> map=oldDaydataByUserByexport(fromDateStr,toDateStr,timeType,groupId);
	    		List<Object> list=(List<Object>) map.get("list");
	    		List<String> item=(List<String>) map.get("item");  		
	    	List<String> list_new =new ArrayList<String>();
	    	list_new.add("销售姓名");
	    	list_new.add("意向客户数");
	    	if(item!=null&&item.size()>0){
	    		for(String s:item){
	    			list_new.add(s)	;
	    		}
	    	}
	    	createConSheetbyUser(list,callSheet,headerStyle,bodyStyle,list_new);
	    	expName="新增意向个人统计-历史";
    		
    		response.setContentType("application/vnd.ms-excel");    
    		response.setHeader("Content-disposition", "attachment;filename="+DateUtil.formatDate(new Date(),"yyyyMMdd")+URLEncoder.encode("新增意向个人统计-历史.xls","UTF-8"));
            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();    
            ouputStream.close();
    	} catch (Exception e) {
			throw new SysRunException(e);
		}
    }
	/**
	 * 
	 *
	 */
	public void createConSheetbyUser(List<Object> dtos,HSSFSheet sheet, HSSFCellStyle headerStyle, HSSFCellStyle bodyStyle,List<String> item) {
		HSSFRow rowm = sheet.createRow(0);
		for (int i = 0; i < item.size(); i++) {
			HSSFCell cell = rowm.createCell(i);
			cell.setCellValue(item.get(i));
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, 32 * 160);
		}
		for (int i = 0; i < dtos.size(); i++) {

			Map<String,Object> map1=(Map<String, Object>) dtos.get(i);
			HSSFRow datarow = sheet.createRow(i + 1);
			for (int j = 0; j < item.size(); j++) {
				HSSFCell cell = datarow.createCell(j);
				cell.setCellStyle(bodyStyle);
				if (j == 0) {
					cell.setCellValue(map1.get("userName").toString());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j>=1){
				List<String> list=(List<String>) map1.get("returnlist");
				if(list!=null&&list.size()>0){
						cell.setCellValue(list.get(j-1));
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);							
				}

				}
			}
		}

	}
    
    
    
    
    //新增意向统计,历史统计，统计部门下用户销售进程
    public Map<String,Object> oldDaydataByUserByexport(String fromDateStr,String toDateStr,String timeType,String groupId){
	    Map<String,Object> modelMap=new HashMap<String,Object>();
	try {
		List<String> item=new ArrayList<String>();
		List<Object> returnList=new ArrayList<Object>();
   	    List<Integer> listcount=new ArrayList<Integer>();
    		ShiroUser user = ShiroUtil.getUser();
    		
        	Date fromDate= null;
        	Date toDate=null;
        	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
        	
        	fromDate = array[0];
        	toDate = array[1];
        	
        	Date[] toWeekArray = DateSubUtil.toWeek();
	    	Date[] toMonthArray = DateSubUtil.toMonth();
        	
	    	if(StringUtils.isBlank(fromDateStr)||StringUtils.isBlank(toDateStr)){
				fromDateStr = DateUtil.getDateDay(fromDate);
				toDateStr = DateUtil.getDateDay(toDate);
			}
    				
	    	TsmReportWillBean bean=new TsmReportWillBean();
			bean.setOrgId(user.getOrgId());
			
	    	List<String> childGroupIds = tsmTeamGroupService.findAllSonGroupIds(user.getOrgId(), groupId);
	    	childGroupIds.add(groupId);
	    	bean.setGroupIds(childGroupIds);

        //  发送时间
        	bean.setStartDate(DateUtil.formatDate(DateUtil.dateBegin(fromDate), DateUtil.DATE_DAY));
        	bean.setEndDate(DateUtil.formatDate(DateUtil.dateEnd(toDate), DateUtil.DATE_DAY));
    		  		       	   	
        	
        	//查询单位下所有的销售进程
            List<OptionBean> list_option=optionService.findOptionListByAccount(user.getOrgId());
			 if(list_option!=null&&list_option.size()>0){
				 for(OptionBean option:list_option){
					 item.add(option.getOptionName()); 
				 }
			 }
            
            if(list_option!=null && list_option.size()>0){

            List<TsmReportWillBean> datas=new ArrayList<TsmReportWillBean>();
//          datas=newWillService.findNewWillUserByGroup(bean);	
            datas=newWillService.findAllUser(bean);//数据条数
            List<TsmReportWillBean> list=	newWillService.findNewWillUserByGroupAndUser(bean);
            if(datas!=null&& datas.size()>0){
//            	 if(list!=null&& list.size()>0){
            	     for(TsmReportWillBean s:datas){ //遍历部门
            	    	 Map<String,List<String>> map=new HashMap<String,List<String>>();

            	    	 Map<String,String> map_new=new HashMap<String,String>();
            	    	 List<String> maplist =new ArrayList<String>();
    	    			 if(list_option!=null&&list_option.size()>0){
//                	    	 List<String> countValue=new ArrayList<String>();
    	    				 for(OptionBean option:list_option){//遍历销售进程参数
            	    	       for(TsmReportWillBean newbean:list){ //遍历数据库数据
            	    	    	   if(newbean.getType()==1){
            	    		 //根据时间排
            	    		 if(s.getUserAccount()==newbean.getUserAccount()||s.getUserAccount().equals(newbean.getUserAccount())){
            
            	    					 if(newbean.getOptionlistId()==option.getOptionlistId()||newbean.getOptionlistId().equals(option.getOptionlistId())){
//            	    						 countValue.add(newbean.getNum()) ;//数据库如果有值，传值
            	    						 map_new.put(option.getOptionlistId(),String.valueOf(newbean.getOptionSum()));
            	    					 }
            	    					 }
            	    					 
            	    				 }
            	    	       }
            	    			 } 

            	    		 }
    	    			 if(list_option!=null&&list_option.size()>0){
    	    				 int new_will_sum=0;
    	    				 for(TsmReportWillBean newbean:list){ 
    	    					 if(newbean.getType()==0 &&( s.getUserAccount()==newbean.getUserAccount()||s.getUserAccount().equals(newbean.getUserAccount()))){
    	    						 new_will_sum=newbean.getNewWillSum();
    	    					 }
    	    				 }
    	    				 maplist.add(String.valueOf(new_will_sum));
    	    				 for(OptionBean option:list_option){
    	    					 if(map_new.get(option.getOptionlistId())!=null&&map_new.get(option.getOptionlistId()).length()>0){
    	    						 maplist.add(map_new.get(option.getOptionlistId()));
    	    					 }else{
    	    						 maplist.add("0");
    	    					 }
    	    				 }
    	    			 }
    	    			 Map<String,Object> map_new2=new HashMap<String,Object>();
    	    			 map_new2.put("groupId", s.getUserAccount());
    	    			 map_new2.put("groupName",s.getUserName());
    	    			 map_new2.put("userAccount", s.getUserAccount());
    	    			 map_new2.put("userName", s.getUserName());
    	    			 map_new2.put("returnlist", maplist);
//    	    			 map_new2.put("returnlist", maplist);
    	    	    	 returnList.add(map_new2);
    	    	    	 
    	    	    	 if(listcount!=null&&listcount.size()>0){
    	    	    		 for(int i=0;i<maplist.size();i++){
    	    	    	    	 listcount.set(i, Integer.valueOf(maplist.get(i))+Integer.valueOf(listcount.get(i))) ;
    	    	    		 }
    	    	    	 }else{
    	    	    		 for(String st: maplist){
    	    	    	    	 listcount.add(Integer.valueOf(st)); 
    	    	    		 } 
    	    	    	 }

            	}
            	}
            	}
	        modelMap.put("list", returnList);
	        modelMap.put("item", item);

	    	} catch (Exception e) {
	    		logger.error(e.getMessage(),e);
	    	}
	        return modelMap;
    }
    
    /******************************导出文件结束***********************************************/
    
	public List<String> getAlldate(String strDate,String endDate) throws ParseException{
		List<String> list=new ArrayList<String>();
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDates = df.parse(strDate);
        startCalendar.setTime(startDates);
        Date endDates = df.parse(endDate);
        endCalendar.setTime(endDates);

          list.add(strDate); 	

        while(true){
            startCalendar.add(Calendar.DAY_OF_MONTH, 1);
            if(startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()){
            list.add(df.format(startCalendar.getTime()));
        }else{
            break;
        }
        }
        if(!strDate.equals(endDate)){
        	list.add(endDate);
        }
        Collections.reverse(list);
		return list;
	}
	
	/*处理父子部门数据包含*/
	public void processTopGroupValue(String orgId,List<Map<String,Object>> list){
		List<String> pids = tsmTeamGroupService.findAllPid(orgId);
		Map<String, List<OrgGroup>> orgGroupPidMap = cachedService.getOrgGroupPidMap(orgId);
			
		if(pids!=null &&pids.size()>0&&orgGroupPidMap!=null &&!orgGroupPidMap.isEmpty()){
			Map<String, List<String>> map = list2Map2(list);
			
			Set<String> keys = map.keySet();
			List<String> teamGroupIds = new ArrayList<String>();
			for (String groupId : keys) {
				if(!pids.contains(groupId)) teamGroupIds.add(groupId);
			}
			List<String> fathers = tsmTeamGroupService.findPidsByGroupIds(orgId, teamGroupIds);
			if(fathers!=null && fathers.size()>0){
				process(orgId,fathers, map, orgGroupPidMap);
			}
			
			list = new ArrayList<Map<String,Object>>();
			for(String gid :map.keySet()){
				Map<String, Object> mpas=new HashMap<String,Object>();	
				mpas.put(gid, map.get(gid));
				list.add(mpas);
			}
		}
	}
	
	public Map<String, List<String>> list2Map2(List<Map<String,Object>> list){
		Map<String, List<String>> map = new LinkedHashMap<String,List<String>>();
		for (Map<String,Object> maps : list) {
			String groupId = (String) maps.get("groupId");
			map.put(groupId, (List<String>) maps.get("returnlist"));
		}
		return map;
	}
	
	public Map<String, TsmReportCallInfoBean> list2Map(List<TsmReportCallInfoBean> list){
		Map<String, TsmReportCallInfoBean> map = new LinkedHashMap<String,TsmReportCallInfoBean>();
		for (TsmReportCallInfoBean tsmReportCallInfoBean : list) {
			String groupId = tsmReportCallInfoBean.getGroupId();
			map.put(groupId, tsmReportCallInfoBean);
		}
		return map;
	}
	
	public void process(String orgId,List<String> groupIds,Map<String, List<String>> map,Map<String, List<OrgGroup>> orgGroupPidMap){
		List<String> fathers = tsmTeamGroupService.findPidsByGroupIds(orgId, groupIds);
		
		for (String gid : groupIds) {
			List<OrgGroup> sonGroupIds = orgGroupPidMap.get(gid);
			List<String> fatherReport = map.get(gid);
			if(fatherReport!=null){
				for (OrgGroup sonGroup : sonGroupIds) {
					List<String> sonReport = map.get(sonGroup.getGroupId());
					if(sonReport!=null)
						if(!fathers.contains(gid)){
						plusData(fatherReport, sonReport);
						}
				}
			}
		}

		
		if(fathers!=null && fathers.size()>0){
			process(orgId, fathers, map, orgGroupPidMap);
		}
	}
	
	/*将    source 中数据加到 target中*/
	public void plusData(List<String> target,List<String> source){
		if(source!=null&&source.size()>0){
			for(int i=0;i<source.size();i++){
				target.set(i,String.valueOf(Integer.valueOf(target.get(i))+Integer.valueOf(source.get(i))));
			}
		}
}
	
	
	/*****************************5.1*************************************************/
    //新增意向统计,当日新增数对应的资源信息
    @RequestMapping(value = "/toDayNewWillSum")
    @ResponseBody
    public Map<String,Object> toDayNewWillSum(HttpServletRequest request,TsmReportWillSumBean bean){
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	try {
    		ShiroUser user = ShiroUtil.getUser();	
    		String  userAccount =bean.getUserAccount();
//    		TsmfindWillSumBean bean=new TsmfindWillSumBean();
			bean.setOrgId(user.getOrgId());
        	if (StringUtils.isNotEmpty(userAccount)) {
       		    String[] userAccounts = userAccount.split(",");
				List<String> userAccountList  = Arrays.asList(userAccounts);
				bean.setUserAccounts(userAccountList);
        	}
        	List<TsmReportWillSumBean> list=newWillService.findNewWillSumByAccountsListPage(bean);
            if(list!=null&&list.size()>0){
            	for(TsmReportWillSumBean beans:list){
            		beans.setShowCard(true);
            	}
            }	
        	returnMap.put("isState", user.getIsState());
            returnMap.put("list", list);
            returnMap.put("item", bean);
            returnMap.put("status", true);
            returnMap.put("msg", "查询成功！");

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
	      return returnMap;
    }
    
    //新增意向统计,销售进程对应的资源信息
    @RequestMapping(value = "/toDayNewWillSumByOption")
    @ResponseBody
    public Map<String,Object> toDayNewWillSumByOption(HttpServletRequest request,TsmReportWillSumBean bean){
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	try {
    		ShiroUser user = ShiroUtil.getUser();	
    		String  userAccount = bean.getUserAccount();
//    		String optionlistId= request.getParameter("optionlistId");
//    		TsmfindWillSumBean bean=new TsmfindWillSumBean();
			bean.setOrgId(user.getOrgId());
        	if (StringUtils.isNotEmpty(userAccount)) {
       		    String[] userAccounts = userAccount.split(",");
				List<String> userAccountList  = Arrays.asList(userAccounts);
				bean.setUserAccounts(userAccountList);
        	}
//        	bean.setOptionlistId(optionlistId);
        	List<TsmReportWillSumBean> list=newWillService.findNewWillSumByOptionListPage(bean);
            if(list!=null&&list.size()>0){
            	for(TsmReportWillSumBean beans:list){
            		beans.setShowCard(true);
            	}
            }
        	returnMap.put("isState", user.getIsState());
            returnMap.put("list", list);
            returnMap.put("item", bean);
            returnMap.put("status", true);
            returnMap.put("msg", "查询成功！");

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
	      return returnMap;
    }
    
    /*****************************历史数据*************************************************/
    
    //新增意向统计,历史新增数对应的资源信息
    @RequestMapping(value = "/OldDayNewWillSum")
    @ResponseBody
    public Map<String,Object> OldDayNewWillSum(HttpServletRequest request,TsmReportWillSumBean bean){
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	    List<TsmReportWillSumBean> list=new ArrayList<TsmReportWillSumBean>();
	try {
    		ShiroUser user = ShiroUtil.getUser();	
    		String  userAccount = bean.getUserAccount();	
    		String  groupId = request.getParameter("groupId");
    		String  selectType = request.getParameter("selectType");//selectType：1为根据日期来查，2为根据分组,3是按人来查
			bean.setOrgId(user.getOrgId());
			
			if("1".endsWith(selectType)){//根据日期来查
				if(bean.getCurrDate()!=null&&!"".endsWith(bean.getCurrDate())){
					list=newWillService.findNewWillSumBydateListPage(bean);	
				}
				
			}else if("2".endsWith(selectType)){//根据分组
				TsmReportWillBean beans=new TsmReportWillBean();	
		    	List<String> childGroupIds = tsmTeamGroupService.findAllSonGroupIds(user.getOrgId(), groupId);
		    	childGroupIds.add(groupId);
		    	beans.setGroupIds(childGroupIds);
		    	beans.setOrgId(user.getOrgId());
		    	List<TsmReportWillBean> datas=newWillService.findAllUser(beans);//查询分组下面用户数
		    	List<String> userAccountList=new ArrayList<String>();
		    	if(datas!=null&&datas.size()>0){
		    		for(TsmReportWillBean willbean:datas){
		    			userAccountList.add(willbean.getUserAccount());
		    		}
		    	}
	        	
		    	if(userAccountList!=null&&userAccountList.size()>0){
		    		bean.setUserAccounts(userAccountList);	
		    	}
	        	list=newWillService.findNewWillSumBydateListPage(bean);	
			}else if("3".endsWith(selectType)){//根据人来查
	        	if (StringUtils.isNotEmpty(userAccount)) {
	       		    String[] userAccounts = userAccount.split(",");
					List<String> userAccountList  = Arrays.asList(userAccounts);
					bean.setUserAccounts(userAccountList);
	        	}
	        	list=newWillService.findNewWillSumBydateListPage(bean);	
			}else if("4".endsWith(selectType)){//时间区间来查
	        	list=newWillService.findNewWillSumBydateListPage(bean);	
			}
			toMakeList(list);
        	returnMap.put("isState", user.getIsState());
            returnMap.put("list", list);
            returnMap.put("item", bean);
            returnMap.put("status", true);
            returnMap.put("msg", "查询成功！");

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
	      return returnMap;
    }
    
    //历史新增意向统计,销售进程对应的资源信息
    @RequestMapping(value = "/OldDayNewWillSumByOption")
    @ResponseBody
    public Map<String,Object> OldDayNewWillSumByOption(HttpServletRequest request,TsmReportWillSumBean bean){
    	List<TsmReportWillSumBean> list=new ArrayList<TsmReportWillSumBean>();
	    Map<String,Object> returnMap=new HashMap<String,Object>();
	try {
    		ShiroUser user = ShiroUtil.getUser();	
    		String  userAccount = bean.getUserAccount();	
    		String  groupId = request.getParameter("groupId");
    		String  selectType = request.getParameter("selectType");//selectType：1为根据日期来查，2为根据分组或者人来查
			bean.setOrgId(user.getOrgId());
    		if("1".endsWith(selectType)){//根据日期来查
				if(bean.getCurrDate()!=null&&!"".endsWith(bean.getCurrDate())){
					list=newWillService.findNewWillSumByopt_date(bean);	
				}
				
			}else if("2".endsWith(selectType)){//根据分组
				TsmReportWillBean beans=new TsmReportWillBean();	
		    	List<String> childGroupIds = tsmTeamGroupService.findAllSonGroupIds(user.getOrgId(), groupId);
		    	childGroupIds.add(groupId);
		    	beans.setGroupIds(childGroupIds);
		    	beans.setOrgId(user.getOrgId());
		    	List<TsmReportWillBean> datas=newWillService.findAllUser(beans);//查询分组下面用户数
		    	List<String> userAccountList=new ArrayList<String>();
		    	if(datas!=null&&datas.size()>0){
		    		for(TsmReportWillBean willbean:datas){
		    			userAccountList.add(willbean.getUserAccount());
		    		}
		    	}
	        	
		    	if(userAccountList!=null&&userAccountList.size()>0){
		    		bean.setUserAccounts(userAccountList);	
		    	}
	        	list=newWillService.findNewWillSumByopt_date(bean);	
			}else if("3".endsWith(selectType)){//根据人来查
	        	if (StringUtils.isNotEmpty(userAccount)) {
	       		    String[] userAccounts = userAccount.split(",");
					List<String> userAccountList  = Arrays.asList(userAccounts);
					bean.setUserAccounts(userAccountList);
	        	}
	        	list=newWillService.findNewWillSumByopt_date(bean);	
			}else if("4".endsWith(selectType)){//根据时间区间
	        	list=newWillService.findNewWillSumByopt_date(bean);	
			}
    		toMakeList(list);
        	returnMap.put("isState", user.getIsState());
            returnMap.put("list", list);
            returnMap.put("item", bean);
            returnMap.put("status", true);
            returnMap.put("msg", "查询成功！");

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
	      return returnMap;
    }
	
    
    public void toMakeList(List<TsmReportWillSumBean> list){
    	ShiroUser user = ShiroUtil.getUser();
    	try {
    		if(list!=null&&list.size()>0){
    			for(TsmReportWillSumBean bean:list){
//    				ResCustInfoBean dto=new ResCustInfoBean();
//    				dto.setOrgId(user.getOrgId());
//    				dto.setResCustId(bean.getCustId());
//    				ResCustInfoBean resCust=resCustInfoService.getByCondtion(dto);
    				if (bean.getNowType() == 1 && bean.getNowStatus() == 1) {//待分配
    					bean.setShowCard(false);//不显示卡片
    				}else{
    					bean.setShowCard(true);//显示卡片
    				}
    				
    			}
    		}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
    	
    	
    }
    
    //今日弹窗
    @RequestMapping(value = "/newWillToday")
    public String newWillToday(HttpServletRequest request,String  userAccount,String optionlistId){
    	ShiroUser shiroUser = ShiroUtil.getUser();	
    	request.setAttribute("shiroUser", shiroUser);
    	request.setAttribute("userAccount", userAccount);
    	request.setAttribute("optionlistId", optionlistId);
        return "report/idialog/newWillTodayIdialog";
    } 
    
    //历史新增意向弹窗
    @RequestMapping(value = "/newWillHistoryId")
    public String newWillHistoryId(HttpServletRequest request,String  userAccount,String currDate,String selectType,String startDate,String endDate,String groupId){
    	ShiroUser shiroUser = ShiroUtil.getUser();	
    	request.setAttribute("shiroUser", shiroUser);
    	request.setAttribute("userAccount", userAccount);
    	request.setAttribute("currDate", currDate);
    	request.setAttribute("selectType", selectType);
    	request.setAttribute("startDate", startDate);
    	request.setAttribute("endDate", endDate);
    	request.setAttribute("groupId", groupId);
        return "report/idialog/newWillHistoryIdialogNewWill";
    } 
    
    //新增销售进程弹窗
    @RequestMapping(value = "/newWillHistoryIdialogNewWillStatus")
    public String newWillHistoryIdialogNewWillStatus(HttpServletRequest request,String  optionlistId,String currDate,String selectType,String startDate,String endDate,String groupId,String userAccount){
    	ShiroUser shiroUser = ShiroUtil.getUser();	
    	request.setAttribute("shiroUser", shiroUser);
    	request.setAttribute("optionlistId", optionlistId);
    	request.setAttribute("currDate", currDate);
    	request.setAttribute("selectType", selectType);
    	request.setAttribute("startDate", startDate);
    	request.setAttribute("endDate", endDate);
    	request.setAttribute("groupId", groupId);
    	request.setAttribute("userAccount", userAccount);
        return "report/idialog/newWillHistoryIdialogNewWillStatus";
    }

}
