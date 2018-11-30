package com.qftx.tsm.plan.user.day.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayResourceBean;
import com.qftx.tsm.plan.user.day.bean.PlanUserdaySigncustBean;
import com.qftx.tsm.plan.user.day.bean.PlanUserdayWillcustBean;


public class PlanUserDayBaseService {
	@Resource
	TsmGroupShareinfoService tsmGroupShareinfoService;
	
	
	public List<PlanUserDayResourceBean> processRes(List<PlanUserDayResourceBean> list,String userAcc){
		ShiroUser user = ShiroUtil.getUser();
		List<String> accs =new ArrayList<>();
		if(1==user.getIssys()){
			accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(),user.getAccount());
		}
		
		for (PlanUserDayResourceBean res : list) {
			String cust_acc = res.getCust_ownerAcc();
			String cust_id = res.getCust_custId();
			String cust_status = res.getCust_status();
			String cust_type = res.getCust_type();
			
			boolean permision = true;
			if(cust_id==null){
				permision = false;
			}else if(!userAcc.equals(cust_acc)
					//&& !"1".equals(cust_status)
					&& !"4".equals(cust_status)){
				if(1 == user.getIssys()){
					permision = accs.contains(cust_acc);
				}else{
					permision = false;
				}
			}

			if(permision) {
				if((!"1".equals(cust_type)&&!"3".equals(cust_type))||
						(!"2".equals(cust_status)&&!"3".equals(cust_status))
						){
					res.setTypeChange(true);
				}
			}else{
				res.setPermision(permision);
			}
			
		}
		
		return list;
	}
	
	public List<PlanUserdayWillcustBean> processWill(List<PlanUserdayWillcustBean> list,String userAcc){
		ShiroUser user = ShiroUtil.getUser();
		List<String> accs =new ArrayList<>();
		if(1==user.getIssys()){
			accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(),user.getAccount());
		}
		
		for (PlanUserdayWillcustBean res : list) {
			String cust_acc = res.getCust_ownerAcc();
			String cust_id = res.getCust_custId();
			String cust_status = res.getCust_status();
			String cust_type = res.getCust_type();
			
			boolean permision = true;
			if(cust_id==null){
				permision = false;
			}else if(!userAcc.equals(cust_acc)
					//&& !"1".equals(cust_status)
					&& !"4".equals(cust_status)){
				if(1 == user.getIssys()){
					permision = accs.contains(cust_acc);
				}else{
					permision = false;
				}
			}
			
			if(permision) {
				if(!"2".equals(cust_type)||(!"2".equals(cust_status)&&!"3".equals(cust_status))){
					res.setTypeChange(true);
				}
			}else{
				res.setPermision(permision);
			}
		}
		
		return list;
	}
	
	public List<PlanUserdaySigncustBean> processSign(List<PlanUserdaySigncustBean> list,String userAcc){
		ShiroUser user = ShiroUtil.getUser();
		List<String> accs =new ArrayList<>();
		if(1==user.getIssys()){
			accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(),user.getAccount());
		}
		
		for (PlanUserdaySigncustBean res : list) {
			String cust_acc = res.getCust_ownerAcc();
			String cust_id = res.getCust_custId();
			String cust_status = res.getCust_status();
			String cust_type = res.getCust_type();
			
			boolean permision = true;
			if(cust_id==null){
				permision = false;
			}else if(!userAcc.equals(cust_acc)
					//&& !"1".equals(cust_status)
					&& !"4".equals(cust_status)){
				if(1 == user.getIssys()){
					permision = accs.contains(cust_acc);
				}else{
					permision = false;
				}
			}
			
			if(permision) {
				if(!"2".equals(cust_type)||!"6".equals(cust_status)){
					res.setTypeChange(true);
				}
			}else{
				res.setPermision(permision);
			}
		}
		
		return list;
	}
}
