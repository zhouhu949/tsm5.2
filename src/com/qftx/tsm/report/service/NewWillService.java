package com.qftx.tsm.report.service;

import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.bean.User;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.report.bean.NewWillBean;
import com.qftx.tsm.report.bean.TsmReportWillBean;
import com.qftx.tsm.report.bean.TsmReportWillSumBean;
import com.qftx.tsm.report.bean.TsmfindWillSumBean;
import com.qftx.tsm.report.dao.NewWillMapper;
import com.qftx.tsm.report.dao.TsmReportWillMapper;
import com.qftx.tsm.report.dao.TsmReportWillSumMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NewWillService {
	@Autowired NewWillMapper newWillMapper;
	@Autowired TsmReportWillMapper tsmReportWillMapper;
	@Autowired TsmReportWillSumMapper tsmReportWillSumMapper;
	@Autowired CachedService cachedService;
	@Autowired ResCustInfoService resCustInfoService;

	Logger logger = Logger.getLogger(NewWillService.class.getName());
	
	public List<NewWillBean> findNewWillCountDataBydate(NewWillBean bean){
		return newWillMapper.findNewWillCountDataBydate(bean);
	}
	
	public List<String> findNewWilldate(NewWillBean bean){
		return newWillMapper.findNewWilldate(bean);
	}
	
	public List<NewWillBean> findNewWilldateByGroup(NewWillBean bean){
		return newWillMapper.findNewWilldateByGroup(bean);
	}	
	
	public List<NewWillBean> findNewWillUserByGroup(NewWillBean bean){
		return newWillMapper.findNewWillUserByGroup(bean);
	}			
	
	
	public List<NewWillBean> findNewWilldateByUser(NewWillBean bean){
		return newWillMapper.findNewWilldateByUser(bean);
	}
	
	public List<NewWillBean> findNewWillUserByGroupByDay(NewWillBean bean){	
		return newWillMapper.findNewWillUserByGroupByDay(bean);
	}
	
	public List<NewWillBean> findNewWillAllUser(String orgId){
		return newWillMapper.findNewWillAllUser(orgId);
	}	
	
	/***************************意向客户统计新*******************************************/
	//今日新增意向统计
	public List<TsmReportWillBean> findNewWillUserByGroupByDay(TsmReportWillBean bean){
		return tsmReportWillMapper.findNewWillUserByGroupByDay(bean);
	}
	
	public List<TsmReportWillBean> findNewWilldateByUser(TsmReportWillBean bean){	
		return tsmReportWillMapper.findNewWilldateByUser(bean);
	}
	
	public List<TsmReportWillBean> findNewWilldateCount(TsmReportWillBean bean){	
		return tsmReportWillMapper.findNewWilldateCount(bean);
	}
	
	public List<String> findNewWillAllUser(TsmReportWillBean bean){
		return tsmReportWillMapper.findNewWillAllUser(bean);
	}	
	
	 public List<TsmReportWillBean>  findNewWillAllByAccount(TsmReportWillBean bean){
		 return tsmReportWillMapper.findNewWillAllByAccount(bean);
	 }
	 
	public List<String> findNewWilldate2(TsmReportWillBean bean){
		return tsmReportWillMapper.findNewWilldate(bean);
	}  
		
	public List<TsmReportWillBean>  findNewWillBydate(TsmReportWillBean bean){
		return tsmReportWillMapper.findNewWillBydate(bean);
	}
	
	public List<TsmReportWillBean>  findNewWillByGroup(TsmReportWillBean bean){
		return tsmReportWillMapper.findNewWillByGroup(bean);
	}	
	public List<TsmReportWillBean>  findNewWillUserByGroup(TsmReportWillBean bean){
		return tsmReportWillMapper.findNewWillUserByGroup(bean);  
	  }
	   
	public List<TsmReportWillBean>  findNewWillUserByGroupAndUser(TsmReportWillBean bean){
		 return tsmReportWillMapper.findNewWillUserByGroupAndUser(bean);  
	   }
	public List<TsmReportWillBean>  findAllUser (TsmReportWillBean bean){
		return tsmReportWillMapper.findAllUser(bean); 
	}
	
	/*************************************3.0*******************************************/
	public List<TsmReportWillBean>  findNewWillAllByAccount_new(TsmReportWillBean bean){
		return tsmReportWillMapper.findNewWillAllByAccount_new(bean);
	}
	   
	public List<TsmReportWillBean>  findNewWillAllByAccount_new_2(TsmReportWillBean bean){
		return tsmReportWillMapper.findNewWillAllByAccount_new_2(bean);
	}
	
	public List<TsmReportWillBean>  findNewWillAllByAccount_new_3(TsmReportWillBean bean){
		return tsmReportWillMapper.findNewWillAllByAccount_new_3(bean);
	}

	/*************************************5.1*******************************************/
	//当日新增意向数对应的资源信息
	   public List<TsmReportWillSumBean>  findNewWillSumByAccountsListPage(TsmReportWillSumBean bean){
		   List<TsmReportWillSumBean> list=new ArrayList<TsmReportWillSumBean>();
		   try {
		   list=tsmReportWillSumMapper.findNewWillSumByAccountsListPage(bean);
		   inittype(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		   return list;  
	   }
	   
	 //销售进程数对应的资源信息
	   public List<TsmReportWillSumBean>  findNewWillSumByOptionListPage(TsmReportWillSumBean bean){
		   List<TsmReportWillSumBean> list=new ArrayList<TsmReportWillSumBean>();
//		   List<TsmReportWillSumBean> list2=new ArrayList<TsmReportWillSumBean>();
		   try {
		   list=tsmReportWillSumMapper.findNewWillSumByAccount_new3ListPage(bean);
//		   list2=tsmReportWillSumMapper.findNewWillSumByAccount_new1ListPage(bean);
//		   if(list2!=null&&list2.size()>0){
//			   for(TsmReportWillSumBean willbean:list2){
//				   list.add(willbean);
//			   }
//		   }
		   
		   
		    inittype(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		   return list;  
	   }
	   
		//历史新增意向数对应的资源信息
	   public List<TsmReportWillSumBean>  findNewWillSumBydateListPage(TsmReportWillSumBean bean){
			   List<TsmReportWillSumBean>  list=tsmReportWillSumMapper.findOldNewWillSumByDateListPage(bean);
			   retrunList(list,bean.getOrgId());
		       inittype(list);
		   return list;  
	   }
	   
		 //历史销售进程数对应的资源信息
	   public List<TsmReportWillSumBean>  findNewWillSumByopt_date(TsmReportWillSumBean bean){
		   List<TsmReportWillSumBean> list=new ArrayList<TsmReportWillSumBean>();
//		   List<TsmReportWillSumBean> list2=new ArrayList<TsmReportWillSumBean>();
		   try {
		   list=tsmReportWillSumMapper.findNewWillSumByDate_new3ListPage(bean);
//		   list2=tsmReportWillSumMapper.findNewWillSumByDate_new1ListPage(bean);
//		   if(list2!=null&&list2.size()>0){
//			   for(TsmReportWillSumBean willbean:list2){
//				   list.add(willbean);
//			   }
//		   }
		   
		   //Map<String, User> usermap=cachedService.getOrgUserMapByAccount(orgId);//用户缓存
		   //List<OrgGroup> getOrgGroup(String orgId);//CachedNames.ORG_GROUP
		   //List<OrgGroupUser> getOrgGroupMember(String orgId);//CachedNames.ORG_GROUP_MEMBER
		   //		List<OptionBean> saleProcessList = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, orgId);//销售进程
		   retrunList(list,bean.getOrgId());
		    inittype(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		   return list;  
	   }
	   
	   
	   public void inittype(List<TsmReportWillSumBean> list){
		   if(list!=null&&list.size()>0){
			   for(TsmReportWillSumBean beans :list){
					  beans.setCustName(beans.getName()==null?beans.getCompany():beans.getName());
				  if(("1".endsWith(beans.getInitType())||"0".endsWith(beans.getInitType()))
				      &&"2".endsWith(beans.getInitStatus())){//资源
					  beans.setToDateInitType("资源");
				  }else if("4".endsWith(beans.getInitType())
						&&("3".endsWith(beans.getInitStatus())||"2".endsWith(beans.getInitStatus()))){
					  //公海
					  beans.setToDateInitType("公海");
				  }else if(("2".endsWith(beans.getInitType())||"3".endsWith(beans.getInitType()))
					      &&"3".endsWith(beans.getInitStatus())){//意向
					  beans.setToDateInitType("意向-"+beans.getInitProcessName());
					  
				  }
			   }
			   
		   }
		   
	   }
	   //不直接查询数据库，通过缓存查询数据
	   public void retrunList(List<TsmReportWillSumBean> list,String orgId){
		   Map<String, User> usermap=cachedService.getOrgUserMapByAccount(orgId);//用户缓存
		   List<OrgGroup> orgGroup=cachedService.getOrgGroup(orgId);//CachedNames.ORG_GROUP
		   List<OrgGroupUser> orgGroupUser=cachedService.getOrgGroupMember(orgId);//CachedNames.ORG_GROUP_MEMBER
		   List<OptionBean> saleProcessList = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,orgId);//销售进程
           List<String> custIds=new ArrayList<String>();
		   if(list!=null&&list.size()>0){
		    	for(TsmReportWillSumBean bean:list){
		    		custIds.add(bean.getCustId());
		    		
		    		if(bean.getUserAccount()!=null&&!"".endsWith(bean.getUserAccount())){
		    			User user=usermap.get(bean.getUserAccount());
		    			if(user!=null){
		    				if(user.getUserName()!=null&&!"".endsWith(user.getUserName())){
		    					bean.setOwnerAcc(user.getUserName());
		    				}
		    			}
		    			String groupId="";
		    			for(OrgGroupUser orggroupuser:orgGroupUser){
		    				if(orggroupuser.getMemberAcc()==bean.getUserAccount()||bean.getUserAccount().endsWith(orggroupuser.getMemberAcc())){
		    					 groupId=orggroupuser.getGroupId();
		    				}
		    			}
		    			if(!"".endsWith(groupId)){
		    				for(OrgGroup orggroup:orgGroup){
		    					if(groupId==orggroup.getGroupId()||groupId.endsWith(orggroup.getGroupId())){
		    						bean.setGroupName(orggroup.getGroupName());	
		    					}
		    				}
		    			}
		    		}
		    		
		    	if(bean.getInitProcessId()!=null&&!"".endsWith(bean.getInitProcessId())){
		    	         for(OptionBean option:saleProcessList){
		    			     if(bean.getInitProcessId()==option.getOptionlistId()||bean.getInitProcessId().endsWith(option.getOptionlistId())){
		    			    	 bean.setInitProcessName(option.getOptionName()); 
		    			     }
		    		     }
		    	}
		    	
		    	if(bean.getCurrProcessId()!=null&&!"".endsWith(bean.getCurrProcessId())){
		    	         for(OptionBean option:saleProcessList){
		    			     if(bean.getCurrProcessId()==option.getOptionlistId()||bean.getCurrProcessId().endsWith(option.getOptionlistId())){
		    			    	 bean.setCurrProcessName(option.getOptionName());; 
		    			     }
		    		     }
		    	}
		    	
		    	}
		    }
		   
		   
		   List<ResCustInfoBean> beanList=resCustInfoService.findAllByCustIds(orgId, custIds);
		   if(beanList!=null&&beanList.size()>0){
			   
		    	for(TsmReportWillSumBean bean:list){
				   for(ResCustInfoBean resbean:beanList){
					   if(bean.getCustId()==resbean.getResCustId()||bean.getCustId().endsWith(resbean.getResCustId())){
						   bean.setCompany(resbean.getCompany()); 
						   bean.setName(resbean.getName());
						   bean.setMainLinkman(resbean.getMainLinkman());
						   bean.setNowType(resbean.getType());
						   bean.setNowStatus(resbean.getStatus());
					   }
				   }
		    	}
		   }
	   }
	   
}
