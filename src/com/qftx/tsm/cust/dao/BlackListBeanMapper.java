package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.BlackListBean;

import java.util.List;

public interface BlackListBeanMapper extends BaseDao<BlackListBean>{
	
	//插入黑名单
	public  void insertBlack (BlackListBean black); 
	
	//查询黑名单
	public List<BlackListBean> findBlackListPage(BlackListBean black);

	//查询黑名单电话,不分页
	public List<String> findBlackList(BlackListBean black);
	
	//删除
	public void deleteBlack (BlackListBean black);

	public String selectCountByOgrId(String orgId);

}
