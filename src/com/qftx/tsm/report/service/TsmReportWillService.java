package com.qftx.tsm.report.service;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dao.OptionMapper;
import com.qftx.tsm.option.service.OptionService;
import com.qftx.tsm.report.bean.TsmReportWillBean;
import com.qftx.tsm.report.dao.TsmReportWillMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TsmReportWillService {
private Log log = LogFactory.getLog(TsmReportWillService.class);
	
	@Autowired
	private TsmReportWillMapper tsmReportWillMapper;
	@Autowired
	private OptionService optionService;
	@Autowired 
	private OptionMapper optionMapper;
	@Autowired
	private ResCustInfoMapper resCustInfoMapper;
	@Autowired
	private UserService userService;
	
	
	
	//即新增意向数又同时新增销售进程
	public void addTsmReportWillandOption(String orgId,String userAccount,String userName,int new_will_sum,String optionlistId,String optionName,int option_sum){
		try {
			addTsmReportWillSum(orgId, userAccount, userName, new_will_sum);
			addTsmReportWillOptionSum(orgId, userAccount, userName, optionlistId, optionName, option_sum);
		   
		} catch (Exception e) {
			log.error("新增意向！",e);
		}
	}
	
	//新增意向统计++
	public void addTsmReportWillSum(String orgId,String userAccount,String userName,int count){
		TsmReportWillBean bean = new TsmReportWillBean();
		try {
			bean.setOrgId(orgId);
			bean.setUserAccount(userAccount);
		    String retcount=tsmReportWillMapper.findTsmNewWillCountData(bean);
		    if(retcount!=null&& Integer.valueOf(retcount) >0){//当天的需要累加
		    	bean.setNewWillSum(count+Integer.valueOf(retcount));
		    	tsmReportWillMapper.updateNum(bean);	
		    }else{//没有数据需要新建
				TsmReportWillBean bean_new = new TsmReportWillBean();
				String Name=checkUserName(orgId,userAccount,userName);
				String id = SysBaseModelUtil.getModelId();
				bean_new.setId(id);
				bean_new.setOrgId(orgId);
				bean_new.setUserAccount(userAccount);
				bean_new.setUserName(Name);
				bean_new.setNewWillSum(count);
				bean_new.setType(0);

		    	tsmReportWillMapper.insertTsmReportNewWill(bean_new);
		    }
		} catch (Exception e) {
			log.error("新增意向！",e);
		}
	}
	
	
	//新增销售进程统计
	public void addTsmReportWillOptionSum(String orgId,String userAccount,String userName,String optionlistId,String optionName,int count){
		TsmReportWillBean bean = new TsmReportWillBean();
		try {
			bean.setOrgId(orgId);
			bean.setUserAccount(userAccount);
			bean.setOptionlistId(optionlistId);
		    String retcount=tsmReportWillMapper.findTsmNewWillOptincount(bean);
		    if(retcount!=null&& Integer.valueOf(retcount) >0){//当天的需要累加
		    	bean.setOptionSum(count+Integer.valueOf(retcount));
		    	tsmReportWillMapper.updateOptionNum(bean);	
		    }else{//没有数据需要新建
				TsmReportWillBean bean_new = new TsmReportWillBean();
			    String Name=checkUserName(orgId,userAccount,userName);
				String id = SysBaseModelUtil.getModelId();
				bean_new.setId(id);
				bean_new.setOrgId(orgId);
				bean_new.setUserAccount(userAccount);
				bean_new.setUserName(Name);
				bean_new.setOptionlistId(optionlistId);
				bean_new.setOptionName(optionName);
				bean_new.setOptionSum(count);
				bean_new.setType(1);
				
		    	tsmReportWillMapper.insertTsmReportNewWill(bean_new);
		    }
		} catch (Exception e) {
			log.error("新增意向！",e);
		}
	}
	
	//新增销售进程统计,并需要校验当前销售进程和历史销售进程，从低到到高，销售进程数才加，从高到低不加
	public void addTsmReportWillOptionSumCheck(String orgId,String userAccount,String userName,String old_optionlistId,String optionlistId,String optionName,String resCustId,int count){
//        ResCustInfoBean bean=resCustInfoMapper.getByPrimaryKey(orgId, resCustId);
//        if(bean!=null){
            OptionBean optionbean1=optionMapper.getByPrimaryKeyAndOrgId(old_optionlistId, orgId);
            int sort_old=optionbean1.getSort();
            OptionBean optionbean2=optionMapper.getByPrimaryKeyAndOrgId(optionlistId, orgId);
            int sort_new=optionbean2.getSort();
            if(sort_new>sort_old){
            	addTsmReportWillOptionSum(orgId, userAccount, userName, optionlistId, optionName, count);
//            }
        }    
	}
	

	public String checkUserName(String orgId,String userAccount,String userName){
		String returnName="";
		if(userName==""||"".equals(userName)){
		User u=new User();
		u.setOrgId(orgId);
		u.setUserAccount(userAccount);
		User user=userService.getByCondtion(u);
		returnName=user.getUserName();
		}else{
		returnName=userName;
		}
		return returnName;
	}

}
