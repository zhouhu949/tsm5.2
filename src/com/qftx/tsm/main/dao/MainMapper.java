package com.qftx.tsm.main.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sms.bean.TsmMessageSend;

import java.util.List;

public interface MainMapper extends BaseDao<TsmMessageSend>{
	public List<TsmMessageSend> findAllUserByOrgId(String OrgId);

}
