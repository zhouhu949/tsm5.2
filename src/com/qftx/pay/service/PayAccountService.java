package com.qftx.pay.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.pay.bean.PayAccountBean;
import com.qftx.pay.dao.PayAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PayAccountService {
	@Autowired PayAccountMapper payAccountMapper;
	@Autowired CachedService cachedService;
	/* 查询*/
	public List<PayAccountBean> find(String orgId){
		PayAccountBean entity= new PayAccountBean();
		entity.setOrgId(orgId);
		
		return payAccountMapper.findByCondtion(entity);
	}
	
	/* 查询*/
	public PayAccountBean getAccount(ShiroUser user){
		PayAccountBean entity= new PayAccountBean();
		entity.setOrgId(user.getOrgId());
		entity.setAccount(user.getAccount());
		entity.setIsDel(0);
		entity.setStatus(1);
		
		return payAccountMapper.getByCondtion(entity);
	}
	
	/* 查询*/
	public PayAccountBean createAccountNotExist(String orgId,String account){
		//TODO　线程安全
		PayAccountBean query= new PayAccountBean();
		query.setOrgId(orgId);
		query.setAccount(account);
		query.setIsDel(0);
		query.setStatus(1);
		PayAccountBean dbAccount = payAccountMapper.getByCondtion(query);
		if(dbAccount == null){
				Map<String, String> nameMap = cachedService.getOrgUserNames(orgId);
				if(nameMap!=null && !nameMap.isEmpty() && nameMap.containsKey(account))
					query.setUserName(nameMap.get(account));
			dbAccount = insert(query);
		}
		return dbAccount;
	}
	
	/* 查询*/
	public PayAccountBean getAccountById(String id){
		PayAccountBean entity= new PayAccountBean();
		entity.setId(id);
		entity.setIsDel(0);
		entity.setStatus(1);
		return payAccountMapper.getByCondtion(entity);
	}
	
	/* 分页查询*/
	public List<PayAccountBean> findListPage(String orgId,PayAccountBean entity){
		entity.setOrgId(orgId);
		
		return payAccountMapper.findListPage(entity);
	}
	
	/* 分页查询*/
	public void updateTrends(PayAccountBean entity){
		entity.setUpdatetime(new Date());
		payAccountMapper.updateTrends(entity);
	}
	
	/* 账号初始化*/
	public void init(String orgId,String acc){
		PayAccountBean query = new PayAccountBean();
		query.setOrgId(orgId);
		query.setAccount(acc);
		query.setIsDel(0);
		query.setStatus(1);
		query = payAccountMapper.getByCondtion(query);
		
		if(query!=null){
			PayAccountBean update = new PayAccountBean();
			update.setId(query.getId());
			update.setStatus(0);
			update.setUpdatetime(new Date());
			payAccountMapper.updateTrends(update);
		}
	}
	
	/* 插入*/
	public PayAccountBean insert(PayAccountBean bean){
		String id=GuidUtil.getGuid();
		bean.setId(id);
		if(bean.getBalance()==null) bean.setBalance(0);
		
		bean.setStatus(1);
		bean.setIsDel(0);
		bean.setInputtime(new Date());
		payAccountMapper.insert(bean);
		return bean;
	}
}
